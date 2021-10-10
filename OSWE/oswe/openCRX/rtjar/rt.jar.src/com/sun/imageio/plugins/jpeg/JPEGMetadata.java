/*      */ package com.sun.imageio.plugins.jpeg;
/*      */ 
/*      */ import java.awt.Point;
/*      */ import java.awt.color.ColorSpace;
/*      */ import java.awt.color.ICC_ColorSpace;
/*      */ import java.awt.color.ICC_Profile;
/*      */ import java.awt.image.ColorModel;
/*      */ import java.io.IOException;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.ListIterator;
/*      */ import javax.imageio.IIOException;
/*      */ import javax.imageio.ImageTypeSpecifier;
/*      */ import javax.imageio.ImageWriteParam;
/*      */ import javax.imageio.metadata.IIOInvalidTreeException;
/*      */ import javax.imageio.metadata.IIOMetadata;
/*      */ import javax.imageio.metadata.IIOMetadataNode;
/*      */ import javax.imageio.plugins.jpeg.JPEGHuffmanTable;
/*      */ import javax.imageio.plugins.jpeg.JPEGImageWriteParam;
/*      */ import javax.imageio.stream.ImageInputStream;
/*      */ import javax.imageio.stream.ImageOutputStream;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class JPEGMetadata
/*      */   extends IIOMetadata
/*      */   implements Cloneable
/*      */ {
/*      */   private static final boolean debug = false;
/*   72 */   private List resetSequence = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean inThumb = false;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean hasAlpha;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  103 */   List markerSequence = new ArrayList();
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   final boolean isStream;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean transparencyDone;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   JPEGMetadata(boolean paramBoolean1, boolean paramBoolean2) {
/*  119 */     super(true, "javax_imageio_jpeg_image_1.0", "com.sun.imageio.plugins.jpeg.JPEGImageMetadataFormat", null, null);
/*      */ 
/*      */ 
/*      */     
/*  123 */     this.inThumb = paramBoolean2;
/*      */     
/*  125 */     this.isStream = paramBoolean1;
/*  126 */     if (paramBoolean1) {
/*  127 */       this.nativeMetadataFormatName = "javax_imageio_jpeg_stream_1.0";
/*  128 */       this.nativeMetadataFormatClassName = "com.sun.imageio.plugins.jpeg.JPEGStreamMetadataFormat";
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
/*      */   JPEGMetadata(boolean paramBoolean1, boolean paramBoolean2, ImageInputStream paramImageInputStream, JPEGImageReader paramJPEGImageReader) throws IOException {
/*  151 */     this(paramBoolean1, paramBoolean2);
/*      */     
/*  153 */     JPEGBuffer jPEGBuffer = new JPEGBuffer(paramImageInputStream);
/*      */     
/*  155 */     jPEGBuffer.loadBuf(0);
/*      */ 
/*      */     
/*  158 */     if ((jPEGBuffer.buf[0] & 0xFF) != 255 || (jPEGBuffer.buf[1] & 0xFF) != 216 || (jPEGBuffer.buf[2] & 0xFF) != 255)
/*      */     {
/*      */       
/*  161 */       throw new IIOException("Image format error");
/*      */     }
/*      */     
/*  164 */     boolean bool = false;
/*  165 */     jPEGBuffer.bufAvail -= 2;
/*  166 */     jPEGBuffer.bufPtr = 2;
/*  167 */     SOFMarkerSegment sOFMarkerSegment = null;
/*  168 */     while (!bool) {
/*      */       DQTMarkerSegment dQTMarkerSegment; DHTMarkerSegment dHTMarkerSegment; DRIMarkerSegment dRIMarkerSegment; MarkerSegment markerSegment; byte[] arrayOfByte;
/*      */       int i;
/*  171 */       jPEGBuffer.loadBuf(1);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  176 */       jPEGBuffer.scanForFF(paramJPEGImageReader);
/*  177 */       switch (jPEGBuffer.buf[jPEGBuffer.bufPtr] & 0xFF) {
/*      */ 
/*      */ 
/*      */         
/*      */         case 0:
/*  182 */           jPEGBuffer.bufAvail--;
/*  183 */           jPEGBuffer.bufPtr++;
/*      */           break;
/*      */         case 192:
/*      */         case 193:
/*      */         case 194:
/*  188 */           if (paramBoolean1) {
/*  189 */             throw new IIOException("SOF not permitted in stream metadata");
/*      */           }
/*      */           
/*  192 */           sOFMarkerSegment = new SOFMarkerSegment(jPEGBuffer);
/*      */           break;
/*      */         case 219:
/*  195 */           dQTMarkerSegment = new DQTMarkerSegment(jPEGBuffer);
/*      */           break;
/*      */         case 196:
/*  198 */           dHTMarkerSegment = new DHTMarkerSegment(jPEGBuffer);
/*      */           break;
/*      */         case 221:
/*  201 */           dRIMarkerSegment = new DRIMarkerSegment(jPEGBuffer);
/*      */           break;
/*      */         
/*      */         case 224:
/*  205 */           jPEGBuffer.loadBuf(8);
/*  206 */           arrayOfByte = jPEGBuffer.buf;
/*  207 */           i = jPEGBuffer.bufPtr;
/*  208 */           if (arrayOfByte[i + 3] == 74 && arrayOfByte[i + 4] == 70 && arrayOfByte[i + 5] == 73 && arrayOfByte[i + 6] == 70 && arrayOfByte[i + 7] == 0) {
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  213 */             if (this.inThumb) {
/*  214 */               paramJPEGImageReader
/*  215 */                 .warningOccurred(1);
/*      */ 
/*      */               
/*  218 */               JFIFMarkerSegment jFIFMarkerSegment1 = new JFIFMarkerSegment(jPEGBuffer); break;
/*      */             } 
/*  220 */             if (paramBoolean1) {
/*  221 */               throw new IIOException("JFIF not permitted in stream metadata");
/*      */             }
/*  223 */             if (!this.markerSequence.isEmpty()) {
/*  224 */               throw new IIOException("JFIF APP0 must be first marker after SOI");
/*      */             }
/*      */             
/*  227 */             JFIFMarkerSegment jFIFMarkerSegment = new JFIFMarkerSegment(jPEGBuffer); break;
/*      */           } 
/*  229 */           if (arrayOfByte[i + 3] == 74 && arrayOfByte[i + 4] == 70 && arrayOfByte[i + 5] == 88 && arrayOfByte[i + 6] == 88 && arrayOfByte[i + 7] == 0) {
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  234 */             if (paramBoolean1) {
/*  235 */               throw new IIOException("JFXX not permitted in stream metadata");
/*      */             }
/*      */             
/*  238 */             if (this.inThumb) {
/*  239 */               throw new IIOException("JFXX markers not allowed in JFIF JPEG thumbnail");
/*      */             }
/*      */ 
/*      */ 
/*      */             
/*  244 */             JFIFMarkerSegment jFIFMarkerSegment = (JFIFMarkerSegment)findMarkerSegment(JFIFMarkerSegment.class, true);
/*  245 */             if (jFIFMarkerSegment == null) {
/*  246 */               throw new IIOException("JFXX encountered without prior JFIF!");
/*      */             }
/*      */             
/*  249 */             jFIFMarkerSegment.addJFXX(jPEGBuffer, paramJPEGImageReader);
/*      */             break;
/*      */           } 
/*  252 */           markerSegment = new MarkerSegment(jPEGBuffer);
/*  253 */           markerSegment.loadData(jPEGBuffer);
/*      */           break;
/*      */ 
/*      */         
/*      */         case 226:
/*  258 */           jPEGBuffer.loadBuf(15);
/*  259 */           if (jPEGBuffer.buf[jPEGBuffer.bufPtr + 3] == 73 && jPEGBuffer.buf[jPEGBuffer.bufPtr + 4] == 67 && jPEGBuffer.buf[jPEGBuffer.bufPtr + 5] == 67 && jPEGBuffer.buf[jPEGBuffer.bufPtr + 6] == 95 && jPEGBuffer.buf[jPEGBuffer.bufPtr + 7] == 80 && jPEGBuffer.buf[jPEGBuffer.bufPtr + 8] == 82 && jPEGBuffer.buf[jPEGBuffer.bufPtr + 9] == 79 && jPEGBuffer.buf[jPEGBuffer.bufPtr + 10] == 70 && jPEGBuffer.buf[jPEGBuffer.bufPtr + 11] == 73 && jPEGBuffer.buf[jPEGBuffer.bufPtr + 12] == 76 && jPEGBuffer.buf[jPEGBuffer.bufPtr + 13] == 69 && jPEGBuffer.buf[jPEGBuffer.bufPtr + 14] == 0) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  272 */             if (paramBoolean1) {
/*  273 */               throw new IIOException("ICC profiles not permitted in stream metadata");
/*      */             }
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  279 */             JFIFMarkerSegment jFIFMarkerSegment = (JFIFMarkerSegment)findMarkerSegment(JFIFMarkerSegment.class, true);
/*  280 */             if (jFIFMarkerSegment == null) {
/*  281 */               markerSegment = new MarkerSegment(jPEGBuffer);
/*  282 */               markerSegment.loadData(jPEGBuffer); break;
/*      */             } 
/*  284 */             jFIFMarkerSegment.addICC(jPEGBuffer);
/*      */             
/*      */             break;
/*      */           } 
/*  288 */           markerSegment = new MarkerSegment(jPEGBuffer);
/*  289 */           markerSegment.loadData(jPEGBuffer);
/*      */           break;
/*      */ 
/*      */         
/*      */         case 238:
/*  294 */           jPEGBuffer.loadBuf(8);
/*  295 */           if (jPEGBuffer.buf[jPEGBuffer.bufPtr + 3] == 65 && jPEGBuffer.buf[jPEGBuffer.bufPtr + 4] == 100 && jPEGBuffer.buf[jPEGBuffer.bufPtr + 5] == 111 && jPEGBuffer.buf[jPEGBuffer.bufPtr + 6] == 98 && jPEGBuffer.buf[jPEGBuffer.bufPtr + 7] == 101) {
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  300 */             if (paramBoolean1) {
/*  301 */               throw new IIOException("Adobe APP14 markers not permitted in stream metadata");
/*      */             }
/*      */             
/*  304 */             markerSegment = new AdobeMarkerSegment(jPEGBuffer); break;
/*      */           } 
/*  306 */           markerSegment = new MarkerSegment(jPEGBuffer);
/*  307 */           markerSegment.loadData(jPEGBuffer);
/*      */           break;
/*      */ 
/*      */         
/*      */         case 254:
/*  312 */           markerSegment = new COMMarkerSegment(jPEGBuffer);
/*      */           break;
/*      */         case 218:
/*  315 */           if (paramBoolean1) {
/*  316 */             throw new IIOException("SOS not permitted in stream metadata");
/*      */           }
/*      */           
/*  319 */           markerSegment = new SOSMarkerSegment(jPEGBuffer);
/*      */           break;
/*      */ 
/*      */ 
/*      */         
/*      */         case 208:
/*      */         case 209:
/*      */         case 210:
/*      */         case 211:
/*      */         case 212:
/*      */         case 213:
/*      */         case 214:
/*      */         case 215:
/*  332 */           jPEGBuffer.bufPtr++;
/*  333 */           jPEGBuffer.bufAvail--;
/*      */           break;
/*      */         case 217:
/*  336 */           bool = true;
/*  337 */           jPEGBuffer.bufPtr++;
/*  338 */           jPEGBuffer.bufAvail--;
/*      */           break;
/*      */         default:
/*  341 */           markerSegment = new MarkerSegment(jPEGBuffer);
/*  342 */           markerSegment.loadData(jPEGBuffer);
/*  343 */           markerSegment.unknown = true;
/*      */           break;
/*      */       } 
/*  346 */       if (markerSegment != null) {
/*  347 */         this.markerSequence.add(markerSegment);
/*      */ 
/*      */ 
/*      */         
/*  351 */         markerSegment = null;
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  359 */     jPEGBuffer.pushBack();
/*      */     
/*  361 */     if (!isConsistent()) {
/*  362 */       throw new IIOException("Inconsistent metadata read from stream");
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   JPEGMetadata(ImageWriteParam paramImageWriteParam, JPEGImageWriter paramJPEGImageWriter) {
/*  371 */     this(true, false);
/*      */     
/*  373 */     JPEGImageWriteParam jPEGImageWriteParam = null;
/*      */     
/*  375 */     if (paramImageWriteParam != null && paramImageWriteParam instanceof JPEGImageWriteParam) {
/*  376 */       jPEGImageWriteParam = (JPEGImageWriteParam)paramImageWriteParam;
/*  377 */       if (!jPEGImageWriteParam.areTablesSet()) {
/*  378 */         jPEGImageWriteParam = null;
/*      */       }
/*      */     } 
/*  381 */     if (jPEGImageWriteParam != null) {
/*  382 */       this.markerSequence.add(new DQTMarkerSegment(jPEGImageWriteParam.getQTables()));
/*  383 */       this.markerSequence
/*  384 */         .add(new DHTMarkerSegment(jPEGImageWriteParam.getDCHuffmanTables(), jPEGImageWriteParam
/*  385 */             .getACHuffmanTables()));
/*      */     } else {
/*      */       
/*  388 */       this.markerSequence.add(new DQTMarkerSegment(JPEG.getDefaultQTables()));
/*  389 */       this.markerSequence.add(new DHTMarkerSegment(JPEG.getDefaultHuffmanTables(true), 
/*  390 */             JPEG.getDefaultHuffmanTables(false)));
/*      */     } 
/*      */ 
/*      */     
/*  394 */     if (!isConsistent()) {
/*  395 */       throw new InternalError("Default stream metadata is inconsistent");
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   JPEGMetadata(ImageTypeSpecifier paramImageTypeSpecifier, ImageWriteParam paramImageWriteParam, JPEGImageWriter paramJPEGImageWriter) {
/*  406 */     this(false, false);
/*      */     
/*  408 */     boolean bool1 = true;
/*  409 */     boolean bool2 = false;
/*  410 */     byte b = 0;
/*  411 */     boolean bool3 = true;
/*  412 */     boolean bool4 = false;
/*  413 */     boolean bool5 = false;
/*  414 */     boolean bool = false;
/*  415 */     boolean bool6 = false;
/*  416 */     boolean bool7 = true;
/*  417 */     boolean bool8 = true;
/*  418 */     float f = 0.75F;
/*  419 */     byte[] arrayOfByte = { 1, 2, 3, 4 };
/*  420 */     int i = 0;
/*      */     
/*  422 */     ImageTypeSpecifier imageTypeSpecifier = null;
/*      */     
/*  424 */     if (paramImageWriteParam != null) {
/*  425 */       imageTypeSpecifier = paramImageWriteParam.getDestinationType();
/*  426 */       if (imageTypeSpecifier != null && 
/*  427 */         paramImageTypeSpecifier != null) {
/*      */         
/*  429 */         paramJPEGImageWriter
/*  430 */           .warningOccurred(0);
/*  431 */         imageTypeSpecifier = null;
/*      */       } 
/*      */ 
/*      */       
/*  435 */       if (paramImageWriteParam.canWriteProgressive())
/*      */       {
/*      */         
/*  438 */         if (paramImageWriteParam.getProgressiveMode() == 1) {
/*  439 */           bool5 = true;
/*  440 */           bool = true;
/*  441 */           bool8 = false;
/*      */         } 
/*      */       }
/*      */       
/*  445 */       if (paramImageWriteParam instanceof JPEGImageWriteParam) {
/*  446 */         JPEGImageWriteParam jPEGImageWriteParam = (JPEGImageWriteParam)paramImageWriteParam;
/*  447 */         if (jPEGImageWriteParam.areTablesSet()) {
/*  448 */           bool7 = false;
/*  449 */           bool8 = false;
/*  450 */           if ((jPEGImageWriteParam.getDCHuffmanTables()).length > 2 || (jPEGImageWriteParam
/*  451 */             .getACHuffmanTables()).length > 2) {
/*  452 */             bool6 = true;
/*      */           }
/*      */         } 
/*      */ 
/*      */         
/*  457 */         if (!bool5) {
/*  458 */           bool = jPEGImageWriteParam.getOptimizeHuffmanTables();
/*  459 */           if (bool) {
/*  460 */             bool8 = false;
/*      */           }
/*      */         } 
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  469 */       if (paramImageWriteParam.canWriteCompressed() && 
/*  470 */         paramImageWriteParam.getCompressionMode() == 2) {
/*  471 */         f = paramImageWriteParam.getCompressionQuality();
/*      */       }
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  478 */     ColorSpace colorSpace = null;
/*  479 */     if (imageTypeSpecifier != null) {
/*  480 */       ColorModel colorModel = imageTypeSpecifier.getColorModel();
/*  481 */       i = colorModel.getNumComponents();
/*  482 */       boolean bool9 = (colorModel.getNumColorComponents() != i) ? true : false;
/*  483 */       boolean bool10 = colorModel.hasAlpha();
/*  484 */       colorSpace = colorModel.getColorSpace();
/*  485 */       int j = colorSpace.getType();
/*  486 */       switch (j) {
/*      */         case 6:
/*  488 */           bool3 = false;
/*  489 */           if (bool9) {
/*  490 */             bool1 = false;
/*      */           }
/*      */           break;
/*      */         case 13:
/*  494 */           if (colorSpace == JPEG.JCS.getYCC()) {
/*  495 */             bool1 = false;
/*  496 */             arrayOfByte[0] = 89;
/*  497 */             arrayOfByte[1] = 67;
/*  498 */             arrayOfByte[2] = 99;
/*  499 */             if (bool10) {
/*  500 */               arrayOfByte[3] = 65;
/*      */             }
/*      */           } 
/*      */           break;
/*      */         case 3:
/*  505 */           if (bool9) {
/*  506 */             bool1 = false;
/*  507 */             if (!bool10) {
/*  508 */               bool2 = true;
/*  509 */               b = 2;
/*      */             } 
/*      */           } 
/*      */           break;
/*      */         case 5:
/*  514 */           bool1 = false;
/*  515 */           bool2 = true;
/*  516 */           bool3 = false;
/*  517 */           arrayOfByte[0] = 82;
/*  518 */           arrayOfByte[1] = 71;
/*  519 */           arrayOfByte[2] = 66;
/*  520 */           if (bool10) {
/*  521 */             arrayOfByte[3] = 65;
/*      */           }
/*      */           break;
/*      */ 
/*      */         
/*      */         default:
/*  527 */           bool1 = false;
/*  528 */           bool3 = false; break;
/*      */       } 
/*  530 */     } else if (paramImageTypeSpecifier != null) {
/*  531 */       ColorModel colorModel = paramImageTypeSpecifier.getColorModel();
/*  532 */       i = colorModel.getNumComponents();
/*  533 */       boolean bool9 = (colorModel.getNumColorComponents() != i) ? true : false;
/*  534 */       boolean bool10 = colorModel.hasAlpha();
/*  535 */       colorSpace = colorModel.getColorSpace();
/*  536 */       int j = colorSpace.getType();
/*  537 */       switch (j) {
/*      */         case 6:
/*  539 */           bool3 = false;
/*  540 */           if (bool9) {
/*  541 */             bool1 = false;
/*      */           }
/*      */           break;
/*      */         
/*      */         case 5:
/*  546 */           if (bool10) {
/*  547 */             bool1 = false;
/*      */           }
/*      */           break;
/*      */         case 13:
/*  551 */           bool1 = false;
/*  552 */           bool3 = false;
/*  553 */           if (colorSpace.equals(ColorSpace.getInstance(1002))) {
/*  554 */             bool3 = true;
/*  555 */             bool2 = true;
/*  556 */             arrayOfByte[0] = 89;
/*  557 */             arrayOfByte[1] = 67;
/*  558 */             arrayOfByte[2] = 99;
/*  559 */             if (bool10) {
/*  560 */               arrayOfByte[3] = 65;
/*      */             }
/*      */           } 
/*      */           break;
/*      */         case 3:
/*  565 */           if (bool9) {
/*  566 */             bool1 = false;
/*  567 */             if (!bool10) {
/*  568 */               bool2 = true;
/*  569 */               b = 2;
/*      */             } 
/*      */           } 
/*      */           break;
/*      */         case 9:
/*  574 */           bool1 = false;
/*  575 */           bool2 = true;
/*  576 */           b = 2;
/*      */           break;
/*      */ 
/*      */ 
/*      */         
/*      */         default:
/*  582 */           bool1 = false;
/*  583 */           bool3 = false;
/*      */           break;
/*      */       } 
/*      */ 
/*      */     
/*      */     } 
/*  589 */     if (bool1 && JPEG.isNonStandardICC(colorSpace)) {
/*  590 */       bool4 = true;
/*      */     }
/*      */ 
/*      */     
/*  594 */     if (bool1) {
/*  595 */       JFIFMarkerSegment jFIFMarkerSegment = new JFIFMarkerSegment();
/*  596 */       this.markerSequence.add(jFIFMarkerSegment);
/*  597 */       if (bool4) {
/*      */         try {
/*  599 */           jFIFMarkerSegment.addICC((ICC_ColorSpace)colorSpace);
/*  600 */         } catch (IOException iOException) {}
/*      */       }
/*      */     } 
/*      */     
/*  604 */     if (bool2) {
/*  605 */       this.markerSequence.add(new AdobeMarkerSegment(b));
/*      */     }
/*      */ 
/*      */     
/*  609 */     if (bool7) {
/*  610 */       this.markerSequence.add(new DQTMarkerSegment(f, bool3));
/*      */     }
/*      */ 
/*      */     
/*  614 */     if (bool8) {
/*  615 */       this.markerSequence.add(new DHTMarkerSegment(bool3));
/*      */     }
/*      */ 
/*      */     
/*  619 */     this.markerSequence.add(new SOFMarkerSegment(bool5, bool6, bool3, arrayOfByte, i));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  626 */     if (!bool5) {
/*  627 */       this.markerSequence.add(new SOSMarkerSegment(bool3, arrayOfByte, i));
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  633 */     if (!isConsistent()) {
/*  634 */       throw new InternalError("Default image metadata is inconsistent");
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
/*      */   MarkerSegment findMarkerSegment(int paramInt) {
/*  648 */     Iterator<MarkerSegment> iterator = this.markerSequence.iterator();
/*  649 */     while (iterator.hasNext()) {
/*  650 */       MarkerSegment markerSegment = iterator.next();
/*  651 */       if (markerSegment.tag == paramInt) {
/*  652 */         return markerSegment;
/*      */       }
/*      */     } 
/*  655 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   MarkerSegment findMarkerSegment(Class paramClass, boolean paramBoolean) {
/*  663 */     if (paramBoolean) {
/*  664 */       Iterator<MarkerSegment> iterator = this.markerSequence.iterator();
/*  665 */       while (iterator.hasNext()) {
/*  666 */         MarkerSegment markerSegment = iterator.next();
/*  667 */         if (paramClass.isInstance(markerSegment)) {
/*  668 */           return markerSegment;
/*      */         }
/*      */       } 
/*      */     } else {
/*  672 */       ListIterator<MarkerSegment> listIterator = this.markerSequence.listIterator(this.markerSequence.size());
/*  673 */       while (listIterator.hasPrevious()) {
/*  674 */         MarkerSegment markerSegment = listIterator.previous();
/*  675 */         if (paramClass.isInstance(markerSegment)) {
/*  676 */           return markerSegment;
/*      */         }
/*      */       } 
/*      */     } 
/*  680 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int findMarkerSegmentPosition(Class paramClass, boolean paramBoolean) {
/*  688 */     if (paramBoolean) {
/*  689 */       ListIterator<MarkerSegment> listIterator = this.markerSequence.listIterator();
/*  690 */       for (byte b = 0; listIterator.hasNext(); b++) {
/*  691 */         MarkerSegment markerSegment = listIterator.next();
/*  692 */         if (paramClass.isInstance(markerSegment)) {
/*  693 */           return b;
/*      */         }
/*      */       } 
/*      */     } else {
/*  697 */       ListIterator<MarkerSegment> listIterator = this.markerSequence.listIterator(this.markerSequence.size());
/*  698 */       for (int i = this.markerSequence.size() - 1; listIterator.hasPrevious(); i--) {
/*  699 */         MarkerSegment markerSegment = listIterator.previous();
/*  700 */         if (paramClass.isInstance(markerSegment)) {
/*  701 */           return i;
/*      */         }
/*      */       } 
/*      */     } 
/*  705 */     return -1;
/*      */   }
/*      */   
/*      */   private int findLastUnknownMarkerSegmentPosition() {
/*  709 */     ListIterator<MarkerSegment> listIterator = this.markerSequence.listIterator(this.markerSequence.size());
/*  710 */     for (int i = this.markerSequence.size() - 1; listIterator.hasPrevious(); i--) {
/*  711 */       MarkerSegment markerSegment = listIterator.previous();
/*  712 */       if (markerSegment.unknown == true) {
/*  713 */         return i;
/*      */       }
/*      */     } 
/*  716 */     return -1;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected Object clone() {
/*  722 */     JPEGMetadata jPEGMetadata = null;
/*      */     try {
/*  724 */       jPEGMetadata = (JPEGMetadata)super.clone();
/*  725 */     } catch (CloneNotSupportedException cloneNotSupportedException) {}
/*  726 */     if (this.markerSequence != null) {
/*  727 */       jPEGMetadata.markerSequence = cloneSequence();
/*      */     }
/*  729 */     jPEGMetadata.resetSequence = null;
/*  730 */     return jPEGMetadata;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private List cloneSequence() {
/*  737 */     if (this.markerSequence == null) {
/*  738 */       return null;
/*      */     }
/*  740 */     ArrayList<Object> arrayList = new ArrayList(this.markerSequence.size());
/*  741 */     Iterator<MarkerSegment> iterator = this.markerSequence.iterator();
/*  742 */     while (iterator.hasNext()) {
/*  743 */       MarkerSegment markerSegment = iterator.next();
/*  744 */       arrayList.add(markerSegment.clone());
/*      */     } 
/*      */     
/*  747 */     return arrayList;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Node getAsTree(String paramString) {
/*  754 */     if (paramString == null) {
/*  755 */       throw new IllegalArgumentException("null formatName!");
/*      */     }
/*  757 */     if (this.isStream) {
/*  758 */       if (paramString.equals("javax_imageio_jpeg_stream_1.0")) {
/*  759 */         return getNativeTree();
/*      */       }
/*      */     } else {
/*  762 */       if (paramString.equals("javax_imageio_jpeg_image_1.0")) {
/*  763 */         return getNativeTree();
/*      */       }
/*  765 */       if (paramString
/*  766 */         .equals("javax_imageio_1.0")) {
/*  767 */         return getStandardTree();
/*      */       }
/*      */     } 
/*  770 */     throw new IllegalArgumentException("Unsupported format name: " + paramString);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   IIOMetadataNode getNativeTree() {
/*      */     IIOMetadataNode iIOMetadataNode1, iIOMetadataNode2;
/*  777 */     Iterator<MarkerSegment> iterator = this.markerSequence.iterator();
/*  778 */     if (this.isStream) {
/*  779 */       iIOMetadataNode1 = new IIOMetadataNode("javax_imageio_jpeg_stream_1.0");
/*  780 */       iIOMetadataNode2 = iIOMetadataNode1;
/*      */     } else {
/*  782 */       IIOMetadataNode iIOMetadataNode = new IIOMetadataNode("markerSequence");
/*  783 */       if (!this.inThumb) {
/*  784 */         iIOMetadataNode1 = new IIOMetadataNode("javax_imageio_jpeg_image_1.0");
/*  785 */         IIOMetadataNode iIOMetadataNode3 = new IIOMetadataNode("JPEGvariety");
/*  786 */         iIOMetadataNode1.appendChild(iIOMetadataNode3);
/*      */         
/*  788 */         JFIFMarkerSegment jFIFMarkerSegment = (JFIFMarkerSegment)findMarkerSegment(JFIFMarkerSegment.class, true);
/*  789 */         if (jFIFMarkerSegment != null) {
/*  790 */           iterator.next();
/*  791 */           iIOMetadataNode3.appendChild(jFIFMarkerSegment.getNativeNode());
/*      */         } 
/*  793 */         iIOMetadataNode1.appendChild(iIOMetadataNode);
/*      */       } else {
/*  795 */         iIOMetadataNode1 = iIOMetadataNode;
/*      */       } 
/*  797 */       iIOMetadataNode2 = iIOMetadataNode;
/*      */     } 
/*  799 */     while (iterator.hasNext()) {
/*  800 */       MarkerSegment markerSegment = iterator.next();
/*  801 */       iIOMetadataNode2.appendChild(markerSegment.getNativeNode());
/*      */     } 
/*  803 */     return iIOMetadataNode1;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected IIOMetadataNode getStandardChromaNode() {
/*  809 */     this.hasAlpha = false;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  814 */     SOFMarkerSegment sOFMarkerSegment = (SOFMarkerSegment)findMarkerSegment(SOFMarkerSegment.class, true);
/*  815 */     if (sOFMarkerSegment == null)
/*      */     {
/*  817 */       return null;
/*      */     }
/*      */     
/*  820 */     IIOMetadataNode iIOMetadataNode1 = new IIOMetadataNode("Chroma");
/*  821 */     IIOMetadataNode iIOMetadataNode2 = new IIOMetadataNode("ColorSpaceType");
/*  822 */     iIOMetadataNode1.appendChild(iIOMetadataNode2);
/*      */ 
/*      */     
/*  825 */     int i = sOFMarkerSegment.componentSpecs.length;
/*      */     
/*  827 */     IIOMetadataNode iIOMetadataNode3 = new IIOMetadataNode("NumChannels");
/*  828 */     iIOMetadataNode1.appendChild(iIOMetadataNode3);
/*  829 */     iIOMetadataNode3.setAttribute("value", Integer.toString(i));
/*      */ 
/*      */     
/*  832 */     if (findMarkerSegment(JFIFMarkerSegment.class, true) != null) {
/*  833 */       if (i == 1) {
/*  834 */         iIOMetadataNode2.setAttribute("name", "GRAY");
/*      */       } else {
/*  836 */         iIOMetadataNode2.setAttribute("name", "YCbCr");
/*      */       } 
/*  838 */       return iIOMetadataNode1;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  843 */     AdobeMarkerSegment adobeMarkerSegment = (AdobeMarkerSegment)findMarkerSegment(AdobeMarkerSegment.class, true);
/*  844 */     if (adobeMarkerSegment != null) {
/*  845 */       switch (adobeMarkerSegment.transform) {
/*      */         case 2:
/*  847 */           iIOMetadataNode2.setAttribute("name", "YCCK");
/*      */           break;
/*      */         case 1:
/*  850 */           iIOMetadataNode2.setAttribute("name", "YCbCr");
/*      */           break;
/*      */         case 0:
/*  853 */           if (i == 3) {
/*  854 */             iIOMetadataNode2.setAttribute("name", "RGB"); break;
/*  855 */           }  if (i == 4) {
/*  856 */             iIOMetadataNode2.setAttribute("name", "CMYK");
/*      */           }
/*      */           break;
/*      */       } 
/*  860 */       return iIOMetadataNode1;
/*      */     } 
/*      */ 
/*      */     
/*  864 */     if (i < 3) {
/*  865 */       iIOMetadataNode2.setAttribute("name", "GRAY");
/*  866 */       if (i == 2) {
/*  867 */         this.hasAlpha = true;
/*      */       }
/*  869 */       return iIOMetadataNode1;
/*      */     } 
/*      */     
/*  872 */     boolean bool = true;
/*      */     byte b1;
/*  874 */     for (b1 = 0; b1 < sOFMarkerSegment.componentSpecs.length; b1++) {
/*  875 */       int m = (sOFMarkerSegment.componentSpecs[b1]).componentId;
/*  876 */       if (m < 1 || m >= sOFMarkerSegment.componentSpecs.length) {
/*  877 */         bool = false;
/*      */       }
/*      */     } 
/*      */     
/*  881 */     if (bool) {
/*  882 */       iIOMetadataNode2.setAttribute("name", "YCbCr");
/*  883 */       if (i == 4) {
/*  884 */         this.hasAlpha = true;
/*      */       }
/*  886 */       return iIOMetadataNode1;
/*      */     } 
/*      */ 
/*      */     
/*  890 */     if ((sOFMarkerSegment.componentSpecs[0]).componentId == 82 && (sOFMarkerSegment.componentSpecs[1]).componentId == 71 && (sOFMarkerSegment.componentSpecs[2]).componentId == 66) {
/*      */ 
/*      */ 
/*      */       
/*  894 */       iIOMetadataNode2.setAttribute("name", "RGB");
/*  895 */       if (i == 4 && (sOFMarkerSegment.componentSpecs[3]).componentId == 65)
/*      */       {
/*  897 */         this.hasAlpha = true;
/*      */       }
/*  899 */       return iIOMetadataNode1;
/*      */     } 
/*      */     
/*  902 */     if ((sOFMarkerSegment.componentSpecs[0]).componentId == 89 && (sOFMarkerSegment.componentSpecs[1]).componentId == 67 && (sOFMarkerSegment.componentSpecs[2]).componentId == 99) {
/*      */ 
/*      */ 
/*      */       
/*  906 */       iIOMetadataNode2.setAttribute("name", "PhotoYCC");
/*  907 */       if (i == 4 && (sOFMarkerSegment.componentSpecs[3]).componentId == 65)
/*      */       {
/*  909 */         this.hasAlpha = true;
/*      */       }
/*  911 */       return iIOMetadataNode1;
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  917 */     b1 = 0;
/*      */     
/*  919 */     int j = (sOFMarkerSegment.componentSpecs[0]).HsamplingFactor;
/*  920 */     int k = (sOFMarkerSegment.componentSpecs[0]).VsamplingFactor;
/*      */     
/*  922 */     for (byte b2 = 1; b2 < sOFMarkerSegment.componentSpecs.length; b2++) {
/*  923 */       if ((sOFMarkerSegment.componentSpecs[b2]).HsamplingFactor != j || (sOFMarkerSegment.componentSpecs[b2]).VsamplingFactor != k) {
/*      */         
/*  925 */         b1 = 1;
/*      */         
/*      */         break;
/*      */       } 
/*      */     } 
/*  930 */     if (b1 != 0) {
/*  931 */       iIOMetadataNode2.setAttribute("name", "YCbCr");
/*  932 */       if (i == 4) {
/*  933 */         this.hasAlpha = true;
/*      */       }
/*  935 */       return iIOMetadataNode1;
/*      */     } 
/*      */ 
/*      */     
/*  939 */     if (i == 3) {
/*  940 */       iIOMetadataNode2.setAttribute("name", "RGB");
/*      */     } else {
/*  942 */       iIOMetadataNode2.setAttribute("name", "CMYK");
/*      */     } 
/*      */     
/*  945 */     return iIOMetadataNode1;
/*      */   }
/*      */ 
/*      */   
/*      */   protected IIOMetadataNode getStandardCompressionNode() {
/*  950 */     IIOMetadataNode iIOMetadataNode1 = new IIOMetadataNode("Compression");
/*      */ 
/*      */     
/*  953 */     IIOMetadataNode iIOMetadataNode2 = new IIOMetadataNode("CompressionTypeName");
/*  954 */     iIOMetadataNode2.setAttribute("value", "JPEG");
/*  955 */     iIOMetadataNode1.appendChild(iIOMetadataNode2);
/*      */ 
/*      */     
/*  958 */     IIOMetadataNode iIOMetadataNode3 = new IIOMetadataNode("Lossless");
/*  959 */     iIOMetadataNode3.setAttribute("value", "FALSE");
/*  960 */     iIOMetadataNode1.appendChild(iIOMetadataNode3);
/*      */ 
/*      */     
/*  963 */     byte b = 0;
/*  964 */     Iterator<MarkerSegment> iterator = this.markerSequence.iterator();
/*  965 */     while (iterator.hasNext()) {
/*  966 */       MarkerSegment markerSegment = iterator.next();
/*  967 */       if (markerSegment.tag == 218) {
/*  968 */         b++;
/*      */       }
/*      */     } 
/*  971 */     if (b != 0) {
/*  972 */       IIOMetadataNode iIOMetadataNode = new IIOMetadataNode("NumProgressiveScans");
/*  973 */       iIOMetadataNode.setAttribute("value", Integer.toString(b));
/*  974 */       iIOMetadataNode1.appendChild(iIOMetadataNode);
/*      */     } 
/*      */     
/*  977 */     return iIOMetadataNode1;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected IIOMetadataNode getStandardDimensionNode() {
/*  983 */     IIOMetadataNode iIOMetadataNode1 = new IIOMetadataNode("Dimension");
/*  984 */     IIOMetadataNode iIOMetadataNode2 = new IIOMetadataNode("ImageOrientation");
/*  985 */     iIOMetadataNode2.setAttribute("value", "normal");
/*  986 */     iIOMetadataNode1.appendChild(iIOMetadataNode2);
/*      */ 
/*      */     
/*  989 */     JFIFMarkerSegment jFIFMarkerSegment = (JFIFMarkerSegment)findMarkerSegment(JFIFMarkerSegment.class, true);
/*  990 */     if (jFIFMarkerSegment != null) {
/*      */       float f;
/*      */ 
/*      */       
/*  994 */       if (jFIFMarkerSegment.resUnits == 0) {
/*      */         
/*  996 */         f = jFIFMarkerSegment.Xdensity / jFIFMarkerSegment.Ydensity;
/*      */       } else {
/*      */         
/*  999 */         f = jFIFMarkerSegment.Ydensity / jFIFMarkerSegment.Xdensity;
/*      */       } 
/* 1001 */       IIOMetadataNode iIOMetadataNode = new IIOMetadataNode("PixelAspectRatio");
/* 1002 */       iIOMetadataNode.setAttribute("value", Float.toString(f));
/* 1003 */       iIOMetadataNode1.insertBefore(iIOMetadataNode, iIOMetadataNode2);
/*      */ 
/*      */       
/* 1006 */       if (jFIFMarkerSegment.resUnits != 0) {
/*      */         
/* 1008 */         float f1 = (jFIFMarkerSegment.resUnits == 1) ? 25.4F : 10.0F;
/*      */         
/* 1010 */         IIOMetadataNode iIOMetadataNode3 = new IIOMetadataNode("HorizontalPixelSize");
/*      */         
/* 1012 */         iIOMetadataNode3.setAttribute("value", 
/* 1013 */             Float.toString(f1 / jFIFMarkerSegment.Xdensity));
/* 1014 */         iIOMetadataNode1.appendChild(iIOMetadataNode3);
/*      */         
/* 1016 */         IIOMetadataNode iIOMetadataNode4 = new IIOMetadataNode("VerticalPixelSize");
/*      */         
/* 1018 */         iIOMetadataNode4.setAttribute("value", 
/* 1019 */             Float.toString(f1 / jFIFMarkerSegment.Ydensity));
/* 1020 */         iIOMetadataNode1.appendChild(iIOMetadataNode4);
/*      */       } 
/*      */     } 
/* 1023 */     return iIOMetadataNode1;
/*      */   }
/*      */   
/*      */   protected IIOMetadataNode getStandardTextNode() {
/* 1027 */     IIOMetadataNode iIOMetadataNode = null;
/*      */     
/* 1029 */     if (findMarkerSegment(254) != null) {
/* 1030 */       iIOMetadataNode = new IIOMetadataNode("Text");
/* 1031 */       Iterator<MarkerSegment> iterator = this.markerSequence.iterator();
/* 1032 */       while (iterator.hasNext()) {
/* 1033 */         MarkerSegment markerSegment = iterator.next();
/* 1034 */         if (markerSegment.tag == 254) {
/* 1035 */           COMMarkerSegment cOMMarkerSegment = (COMMarkerSegment)markerSegment;
/* 1036 */           IIOMetadataNode iIOMetadataNode1 = new IIOMetadataNode("TextEntry");
/* 1037 */           iIOMetadataNode1.setAttribute("keyword", "comment");
/* 1038 */           iIOMetadataNode1.setAttribute("value", cOMMarkerSegment.getComment());
/* 1039 */           iIOMetadataNode.appendChild(iIOMetadataNode1);
/*      */         } 
/*      */       } 
/*      */     } 
/* 1043 */     return iIOMetadataNode;
/*      */   }
/*      */   
/*      */   protected IIOMetadataNode getStandardTransparencyNode() {
/* 1047 */     IIOMetadataNode iIOMetadataNode = null;
/* 1048 */     if (this.hasAlpha == true) {
/* 1049 */       iIOMetadataNode = new IIOMetadataNode("Transparency");
/* 1050 */       IIOMetadataNode iIOMetadataNode1 = new IIOMetadataNode("Alpha");
/* 1051 */       iIOMetadataNode1.setAttribute("value", "nonpremultiplied");
/* 1052 */       iIOMetadataNode.appendChild(iIOMetadataNode1);
/*      */     } 
/* 1054 */     return iIOMetadataNode;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isReadOnly() {
/* 1060 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public void mergeTree(String paramString, Node paramNode) throws IIOInvalidTreeException {
/* 1065 */     if (paramString == null) {
/* 1066 */       throw new IllegalArgumentException("null formatName!");
/*      */     }
/* 1068 */     if (paramNode == null) {
/* 1069 */       throw new IllegalArgumentException("null root!");
/*      */     }
/* 1071 */     List list = null;
/* 1072 */     if (this.resetSequence == null) {
/* 1073 */       this.resetSequence = cloneSequence();
/* 1074 */       list = this.resetSequence;
/*      */     } else {
/* 1076 */       list = cloneSequence();
/*      */     } 
/* 1078 */     if (this.isStream && paramString
/* 1079 */       .equals("javax_imageio_jpeg_stream_1.0")) {
/* 1080 */       mergeNativeTree(paramNode);
/* 1081 */     } else if (!this.isStream && paramString
/* 1082 */       .equals("javax_imageio_jpeg_image_1.0")) {
/* 1083 */       mergeNativeTree(paramNode);
/* 1084 */     } else if (!this.isStream && paramString
/*      */       
/* 1086 */       .equals("javax_imageio_1.0")) {
/* 1087 */       mergeStandardTree(paramNode);
/*      */     } else {
/* 1089 */       throw new IllegalArgumentException("Unsupported format name: " + paramString);
/*      */     } 
/*      */     
/* 1092 */     if (!isConsistent()) {
/* 1093 */       this.markerSequence = list;
/* 1094 */       throw new IIOInvalidTreeException("Merged tree is invalid; original restored", paramNode);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void mergeNativeTree(Node paramNode) throws IIOInvalidTreeException {
/* 1100 */     String str = paramNode.getNodeName();
/* 1101 */     if (str != (this.isStream ? "javax_imageio_jpeg_stream_1.0" : "javax_imageio_jpeg_image_1.0"))
/*      */     {
/* 1103 */       throw new IIOInvalidTreeException("Invalid root node name: " + str, paramNode);
/*      */     }
/*      */     
/* 1106 */     if (paramNode.getChildNodes().getLength() != 2) {
/* 1107 */       throw new IIOInvalidTreeException("JPEGvariety and markerSequence nodes must be present", paramNode);
/*      */     }
/*      */     
/* 1110 */     mergeJFIFsubtree(paramNode.getFirstChild());
/* 1111 */     mergeSequenceSubtree(paramNode.getLastChild());
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
/*      */   private void mergeJFIFsubtree(Node paramNode) throws IIOInvalidTreeException {
/* 1123 */     if (paramNode.getChildNodes().getLength() != 0) {
/* 1124 */       Node node = paramNode.getFirstChild();
/*      */ 
/*      */       
/* 1127 */       JFIFMarkerSegment jFIFMarkerSegment = (JFIFMarkerSegment)findMarkerSegment(JFIFMarkerSegment.class, true);
/* 1128 */       if (jFIFMarkerSegment != null) {
/* 1129 */         jFIFMarkerSegment.updateFromNativeNode(node, false);
/*      */       } else {
/*      */         
/* 1132 */         this.markerSequence.add(0, new JFIFMarkerSegment(node));
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void mergeSequenceSubtree(Node paramNode) throws IIOInvalidTreeException {
/* 1139 */     NodeList nodeList = paramNode.getChildNodes();
/* 1140 */     for (byte b = 0; b < nodeList.getLength(); b++) {
/* 1141 */       Node node = nodeList.item(b);
/* 1142 */       String str = node.getNodeName();
/* 1143 */       if (str.equals("dqt")) {
/* 1144 */         mergeDQTNode(node);
/* 1145 */       } else if (str.equals("dht")) {
/* 1146 */         mergeDHTNode(node);
/* 1147 */       } else if (str.equals("dri")) {
/* 1148 */         mergeDRINode(node);
/* 1149 */       } else if (str.equals("com")) {
/* 1150 */         mergeCOMNode(node);
/* 1151 */       } else if (str.equals("app14Adobe")) {
/* 1152 */         mergeAdobeNode(node);
/* 1153 */       } else if (str.equals("unknown")) {
/* 1154 */         mergeUnknownNode(node);
/* 1155 */       } else if (str.equals("sof")) {
/* 1156 */         mergeSOFNode(node);
/* 1157 */       } else if (str.equals("sos")) {
/* 1158 */         mergeSOSNode(node);
/*      */       } else {
/* 1160 */         throw new IIOInvalidTreeException("Invalid node: " + str, node);
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
/*      */   private void mergeDQTNode(Node paramNode) throws IIOInvalidTreeException {
/* 1184 */     ArrayList<MarkerSegment> arrayList = new ArrayList();
/* 1185 */     Iterator<MarkerSegment> iterator = this.markerSequence.iterator();
/* 1186 */     while (iterator.hasNext()) {
/* 1187 */       MarkerSegment markerSegment = iterator.next();
/* 1188 */       if (markerSegment instanceof DQTMarkerSegment) {
/* 1189 */         arrayList.add(markerSegment);
/*      */       }
/*      */     } 
/* 1192 */     if (!arrayList.isEmpty()) {
/* 1193 */       NodeList nodeList = paramNode.getChildNodes();
/* 1194 */       for (byte b = 0; b < nodeList.getLength(); b++) {
/* 1195 */         Node node = nodeList.item(b);
/* 1196 */         int i = MarkerSegment.getAttributeValue(node, null, "qtableId", 0, 3, true);
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1201 */         DQTMarkerSegment dQTMarkerSegment = null;
/* 1202 */         byte b1 = -1;
/* 1203 */         for (byte b2 = 0; b2 < arrayList.size(); b2++) {
/* 1204 */           DQTMarkerSegment dQTMarkerSegment1 = (DQTMarkerSegment)arrayList.get(b2);
/* 1205 */           for (byte b3 = 0; b3 < dQTMarkerSegment1.tables.size(); b3++) {
/*      */             
/* 1207 */             DQTMarkerSegment.Qtable qtable = dQTMarkerSegment1.tables.get(b3);
/* 1208 */             if (i == qtable.tableID) {
/* 1209 */               dQTMarkerSegment = dQTMarkerSegment1;
/* 1210 */               b1 = b3;
/*      */               break;
/*      */             } 
/*      */           } 
/* 1214 */           if (dQTMarkerSegment != null)
/*      */             break; 
/* 1216 */         }  if (dQTMarkerSegment != null) {
/* 1217 */           dQTMarkerSegment.tables.set(b1, dQTMarkerSegment.getQtableFromNode(node));
/*      */         } else {
/* 1219 */           dQTMarkerSegment = (DQTMarkerSegment)arrayList.get(arrayList.size() - 1);
/* 1220 */           dQTMarkerSegment.tables.add(dQTMarkerSegment.getQtableFromNode(node));
/*      */         } 
/*      */       } 
/*      */     } else {
/* 1224 */       DQTMarkerSegment dQTMarkerSegment = new DQTMarkerSegment(paramNode);
/* 1225 */       int i = findMarkerSegmentPosition(DHTMarkerSegment.class, true);
/* 1226 */       int j = findMarkerSegmentPosition(SOFMarkerSegment.class, true);
/* 1227 */       int k = findMarkerSegmentPosition(SOSMarkerSegment.class, true);
/* 1228 */       if (i != -1) {
/* 1229 */         this.markerSequence.add(i, dQTMarkerSegment);
/* 1230 */       } else if (j != -1) {
/* 1231 */         this.markerSequence.add(j, dQTMarkerSegment);
/* 1232 */       } else if (k != -1) {
/* 1233 */         this.markerSequence.add(k, dQTMarkerSegment);
/*      */       } else {
/* 1235 */         this.markerSequence.add(dQTMarkerSegment);
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
/*      */   private void mergeDHTNode(Node paramNode) throws IIOInvalidTreeException {
/* 1260 */     ArrayList<MarkerSegment> arrayList = new ArrayList();
/* 1261 */     Iterator<MarkerSegment> iterator = this.markerSequence.iterator();
/* 1262 */     while (iterator.hasNext()) {
/* 1263 */       MarkerSegment markerSegment = iterator.next();
/* 1264 */       if (markerSegment instanceof DHTMarkerSegment) {
/* 1265 */         arrayList.add(markerSegment);
/*      */       }
/*      */     } 
/* 1268 */     if (!arrayList.isEmpty()) {
/* 1269 */       NodeList nodeList = paramNode.getChildNodes();
/* 1270 */       for (byte b = 0; b < nodeList.getLength(); b++) {
/* 1271 */         Node node = nodeList.item(b);
/* 1272 */         NamedNodeMap namedNodeMap = node.getAttributes();
/* 1273 */         int i = MarkerSegment.getAttributeValue(node, namedNodeMap, "htableId", 0, 3, true);
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1278 */         int j = MarkerSegment.getAttributeValue(node, namedNodeMap, "class", 0, 1, true);
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1283 */         DHTMarkerSegment dHTMarkerSegment = null;
/* 1284 */         byte b1 = -1;
/* 1285 */         for (byte b2 = 0; b2 < arrayList.size(); b2++) {
/* 1286 */           DHTMarkerSegment dHTMarkerSegment1 = (DHTMarkerSegment)arrayList.get(b2);
/* 1287 */           for (byte b3 = 0; b3 < dHTMarkerSegment1.tables.size(); b3++) {
/*      */             
/* 1289 */             DHTMarkerSegment.Htable htable = dHTMarkerSegment1.tables.get(b3);
/* 1290 */             if (i == htable.tableID && j == htable.tableClass) {
/*      */               
/* 1292 */               dHTMarkerSegment = dHTMarkerSegment1;
/* 1293 */               b1 = b3;
/*      */               break;
/*      */             } 
/*      */           } 
/* 1297 */           if (dHTMarkerSegment != null)
/*      */             break; 
/* 1299 */         }  if (dHTMarkerSegment != null) {
/* 1300 */           dHTMarkerSegment.tables.set(b1, dHTMarkerSegment.getHtableFromNode(node));
/*      */         } else {
/* 1302 */           dHTMarkerSegment = (DHTMarkerSegment)arrayList.get(arrayList.size() - 1);
/* 1303 */           dHTMarkerSegment.tables.add(dHTMarkerSegment.getHtableFromNode(node));
/*      */         } 
/*      */       } 
/*      */     } else {
/* 1307 */       DHTMarkerSegment dHTMarkerSegment = new DHTMarkerSegment(paramNode);
/* 1308 */       int i = findMarkerSegmentPosition(DQTMarkerSegment.class, false);
/* 1309 */       int j = findMarkerSegmentPosition(SOFMarkerSegment.class, true);
/* 1310 */       int k = findMarkerSegmentPosition(SOSMarkerSegment.class, true);
/* 1311 */       if (i != -1) {
/* 1312 */         this.markerSequence.add(i + 1, dHTMarkerSegment);
/* 1313 */       } else if (j != -1) {
/* 1314 */         this.markerSequence.add(j, dHTMarkerSegment);
/* 1315 */       } else if (k != -1) {
/* 1316 */         this.markerSequence.add(k, dHTMarkerSegment);
/*      */       } else {
/* 1318 */         this.markerSequence.add(dHTMarkerSegment);
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
/*      */   private void mergeDRINode(Node paramNode) throws IIOInvalidTreeException {
/* 1338 */     DRIMarkerSegment dRIMarkerSegment = (DRIMarkerSegment)findMarkerSegment(DRIMarkerSegment.class, true);
/* 1339 */     if (dRIMarkerSegment != null) {
/* 1340 */       dRIMarkerSegment.updateFromNativeNode(paramNode, false);
/*      */     } else {
/* 1342 */       DRIMarkerSegment dRIMarkerSegment1 = new DRIMarkerSegment(paramNode);
/* 1343 */       int i = findMarkerSegmentPosition(SOFMarkerSegment.class, true);
/* 1344 */       int j = findMarkerSegmentPosition(SOSMarkerSegment.class, true);
/* 1345 */       if (i != -1) {
/* 1346 */         this.markerSequence.add(i, dRIMarkerSegment1);
/* 1347 */       } else if (j != -1) {
/* 1348 */         this.markerSequence.add(j, dRIMarkerSegment1);
/*      */       } else {
/* 1350 */         this.markerSequence.add(dRIMarkerSegment1);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void mergeCOMNode(Node paramNode) throws IIOInvalidTreeException {
/* 1361 */     COMMarkerSegment cOMMarkerSegment = new COMMarkerSegment(paramNode);
/* 1362 */     insertCOMMarkerSegment(cOMMarkerSegment);
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
/*      */   private void insertCOMMarkerSegment(COMMarkerSegment paramCOMMarkerSegment) {
/* 1378 */     int i = findMarkerSegmentPosition(COMMarkerSegment.class, false);
/* 1379 */     boolean bool = (findMarkerSegment(JFIFMarkerSegment.class, true) != null) ? true : false;
/* 1380 */     int j = findMarkerSegmentPosition(AdobeMarkerSegment.class, true);
/* 1381 */     if (i != -1) {
/* 1382 */       this.markerSequence.add(i + 1, paramCOMMarkerSegment);
/* 1383 */     } else if (bool) {
/* 1384 */       this.markerSequence.add(1, paramCOMMarkerSegment);
/* 1385 */     } else if (j != -1) {
/* 1386 */       this.markerSequence.add(j + 1, paramCOMMarkerSegment);
/*      */     } else {
/* 1388 */       this.markerSequence.add(0, paramCOMMarkerSegment);
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
/*      */   private void mergeAdobeNode(Node paramNode) throws IIOInvalidTreeException {
/* 1401 */     AdobeMarkerSegment adobeMarkerSegment = (AdobeMarkerSegment)findMarkerSegment(AdobeMarkerSegment.class, true);
/* 1402 */     if (adobeMarkerSegment != null) {
/* 1403 */       adobeMarkerSegment.updateFromNativeNode(paramNode, false);
/*      */     } else {
/* 1405 */       AdobeMarkerSegment adobeMarkerSegment1 = new AdobeMarkerSegment(paramNode);
/* 1406 */       insertAdobeMarkerSegment(adobeMarkerSegment1);
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
/*      */   private void insertAdobeMarkerSegment(AdobeMarkerSegment paramAdobeMarkerSegment) {
/* 1422 */     boolean bool = (findMarkerSegment(JFIFMarkerSegment.class, true) != null) ? true : false;
/* 1423 */     int i = findLastUnknownMarkerSegmentPosition();
/* 1424 */     if (bool) {
/* 1425 */       this.markerSequence.add(1, paramAdobeMarkerSegment);
/* 1426 */     } else if (i != -1) {
/* 1427 */       this.markerSequence.add(i + 1, paramAdobeMarkerSegment);
/*      */     } else {
/* 1429 */       this.markerSequence.add(0, paramAdobeMarkerSegment);
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
/*      */   private void mergeUnknownNode(Node paramNode) throws IIOInvalidTreeException {
/* 1447 */     MarkerSegment markerSegment = new MarkerSegment(paramNode);
/* 1448 */     int i = findLastUnknownMarkerSegmentPosition();
/* 1449 */     boolean bool = (findMarkerSegment(JFIFMarkerSegment.class, true) != null) ? true : false;
/* 1450 */     int j = findMarkerSegmentPosition(AdobeMarkerSegment.class, true);
/* 1451 */     if (i != -1) {
/* 1452 */       this.markerSequence.add(i + 1, markerSegment);
/* 1453 */     } else if (bool) {
/* 1454 */       this.markerSequence.add(1, markerSegment);
/* 1455 */     }  if (j != -1) {
/* 1456 */       this.markerSequence.add(j, markerSegment);
/*      */     } else {
/* 1458 */       this.markerSequence.add(0, markerSegment);
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
/*      */   private void mergeSOFNode(Node paramNode) throws IIOInvalidTreeException {
/* 1476 */     SOFMarkerSegment sOFMarkerSegment = (SOFMarkerSegment)findMarkerSegment(SOFMarkerSegment.class, true);
/* 1477 */     if (sOFMarkerSegment != null) {
/* 1478 */       sOFMarkerSegment.updateFromNativeNode(paramNode, false);
/*      */     } else {
/* 1480 */       SOFMarkerSegment sOFMarkerSegment1 = new SOFMarkerSegment(paramNode);
/* 1481 */       int i = findMarkerSegmentPosition(SOSMarkerSegment.class, true);
/* 1482 */       if (i != -1) {
/* 1483 */         this.markerSequence.add(i, sOFMarkerSegment1);
/*      */       } else {
/* 1485 */         this.markerSequence.add(sOFMarkerSegment1);
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
/*      */   private void mergeSOSNode(Node paramNode) throws IIOInvalidTreeException {
/* 1502 */     SOSMarkerSegment sOSMarkerSegment1 = (SOSMarkerSegment)findMarkerSegment(SOSMarkerSegment.class, true);
/*      */     
/* 1504 */     SOSMarkerSegment sOSMarkerSegment2 = (SOSMarkerSegment)findMarkerSegment(SOSMarkerSegment.class, false);
/* 1505 */     if (sOSMarkerSegment1 != null) {
/* 1506 */       if (sOSMarkerSegment1 != sOSMarkerSegment2) {
/* 1507 */         throw new IIOInvalidTreeException("Can't merge SOS node into a tree with > 1 SOS node", paramNode);
/*      */       }
/*      */       
/* 1510 */       sOSMarkerSegment1.updateFromNativeNode(paramNode, false);
/*      */     } else {
/* 1512 */       this.markerSequence.add(new SOSMarkerSegment(paramNode));
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void mergeStandardTree(Node paramNode) throws IIOInvalidTreeException {
/* 1519 */     this.transparencyDone = false;
/* 1520 */     NodeList nodeList = paramNode.getChildNodes();
/* 1521 */     for (byte b = 0; b < nodeList.getLength(); b++) {
/* 1522 */       Node node = nodeList.item(b);
/* 1523 */       String str = node.getNodeName();
/* 1524 */       if (str.equals("Chroma")) {
/* 1525 */         mergeStandardChromaNode(node, nodeList);
/* 1526 */       } else if (str.equals("Compression")) {
/* 1527 */         mergeStandardCompressionNode(node);
/* 1528 */       } else if (str.equals("Data")) {
/* 1529 */         mergeStandardDataNode(node);
/* 1530 */       } else if (str.equals("Dimension")) {
/* 1531 */         mergeStandardDimensionNode(node);
/* 1532 */       } else if (str.equals("Document")) {
/* 1533 */         mergeStandardDocumentNode(node);
/* 1534 */       } else if (str.equals("Text")) {
/* 1535 */         mergeStandardTextNode(node);
/* 1536 */       } else if (str.equals("Transparency")) {
/* 1537 */         mergeStandardTransparencyNode(node);
/*      */       } else {
/* 1539 */         throw new IIOInvalidTreeException("Invalid node: " + str, node);
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
/*      */   private void mergeStandardChromaNode(Node paramNode, NodeList paramNodeList) throws IIOInvalidTreeException {
/* 1563 */     if (this.transparencyDone) {
/* 1564 */       throw new IIOInvalidTreeException("Transparency node must follow Chroma node", paramNode);
/*      */     }
/*      */ 
/*      */     
/* 1568 */     Node node = paramNode.getFirstChild();
/* 1569 */     if (node == null || !node.getNodeName().equals("ColorSpaceType")) {
/*      */       return;
/*      */     }
/*      */ 
/*      */     
/* 1574 */     String str = node.getAttributes().getNamedItem("name").getNodeValue();
/*      */     
/* 1576 */     byte b1 = 0;
/* 1577 */     boolean bool1 = false;
/* 1578 */     boolean bool2 = false;
/* 1579 */     byte b2 = 0;
/* 1580 */     boolean bool3 = false;
/* 1581 */     byte[] arrayOfByte = { 1, 2, 3, 4 };
/* 1582 */     if (str.equals("GRAY")) {
/* 1583 */       b1 = 1;
/* 1584 */       bool1 = true;
/* 1585 */     } else if (str.equals("YCbCr")) {
/* 1586 */       b1 = 3;
/* 1587 */       bool1 = true;
/* 1588 */       bool3 = true;
/* 1589 */     } else if (str.equals("PhotoYCC")) {
/* 1590 */       b1 = 3;
/* 1591 */       bool2 = true;
/* 1592 */       b2 = 1;
/* 1593 */       arrayOfByte[0] = 89;
/* 1594 */       arrayOfByte[1] = 67;
/* 1595 */       arrayOfByte[2] = 99;
/* 1596 */     } else if (str.equals("RGB")) {
/* 1597 */       b1 = 3;
/* 1598 */       bool2 = true;
/* 1599 */       b2 = 0;
/* 1600 */       arrayOfByte[0] = 82;
/* 1601 */       arrayOfByte[1] = 71;
/* 1602 */       arrayOfByte[2] = 66;
/* 1603 */     } else if (str.equals("XYZ") || str
/* 1604 */       .equals("Lab") || str
/* 1605 */       .equals("Luv") || str
/* 1606 */       .equals("YxY") || str
/* 1607 */       .equals("HSV") || str
/* 1608 */       .equals("HLS") || str
/* 1609 */       .equals("CMY") || str
/* 1610 */       .equals("3CLR")) {
/* 1611 */       b1 = 3;
/* 1612 */     } else if (str.equals("YCCK")) {
/* 1613 */       b1 = 4;
/* 1614 */       bool2 = true;
/* 1615 */       b2 = 2;
/* 1616 */       bool3 = true;
/* 1617 */     } else if (str.equals("CMYK")) {
/* 1618 */       b1 = 4;
/* 1619 */       bool2 = true;
/* 1620 */       b2 = 0;
/* 1621 */     } else if (str.equals("4CLR")) {
/* 1622 */       b1 = 4;
/*      */     } else {
/*      */       return;
/*      */     } 
/*      */     
/* 1627 */     boolean bool = false;
/* 1628 */     for (byte b3 = 0; b3 < paramNodeList.getLength(); b3++) {
/* 1629 */       Node node1 = paramNodeList.item(b3);
/* 1630 */       if (node1.getNodeName().equals("Transparency")) {
/* 1631 */         bool = wantAlpha(node1);
/*      */         
/*      */         break;
/*      */       } 
/*      */     } 
/* 1636 */     if (bool) {
/* 1637 */       b1++;
/* 1638 */       bool1 = false;
/* 1639 */       if (arrayOfByte[0] == 82) {
/* 1640 */         arrayOfByte[3] = 65;
/* 1641 */         bool2 = false;
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 1646 */     JFIFMarkerSegment jFIFMarkerSegment = (JFIFMarkerSegment)findMarkerSegment(JFIFMarkerSegment.class, true);
/*      */     
/* 1648 */     AdobeMarkerSegment adobeMarkerSegment = (AdobeMarkerSegment)findMarkerSegment(AdobeMarkerSegment.class, true);
/*      */     
/* 1650 */     SOFMarkerSegment sOFMarkerSegment = (SOFMarkerSegment)findMarkerSegment(SOFMarkerSegment.class, true);
/*      */     
/* 1652 */     SOSMarkerSegment sOSMarkerSegment = (SOSMarkerSegment)findMarkerSegment(SOSMarkerSegment.class, true);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1660 */     if (sOFMarkerSegment != null && sOFMarkerSegment.tag == 194 && 
/* 1661 */       sOFMarkerSegment.componentSpecs.length != b1 && sOSMarkerSegment != null) {
/*      */       return;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 1667 */     if (!bool1 && jFIFMarkerSegment != null) {
/* 1668 */       this.markerSequence.remove(jFIFMarkerSegment);
/*      */     }
/*      */ 
/*      */     
/* 1672 */     if (bool1 && !this.isStream) {
/* 1673 */       this.markerSequence.add(0, new JFIFMarkerSegment());
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 1678 */     if (bool2) {
/* 1679 */       if (adobeMarkerSegment == null && !this.isStream) {
/* 1680 */         adobeMarkerSegment = new AdobeMarkerSegment(b2);
/* 1681 */         insertAdobeMarkerSegment(adobeMarkerSegment);
/*      */       } else {
/* 1683 */         adobeMarkerSegment.transform = b2;
/*      */       } 
/* 1685 */     } else if (adobeMarkerSegment != null) {
/* 1686 */       this.markerSequence.remove(adobeMarkerSegment);
/*      */     } 
/*      */     
/* 1689 */     boolean bool4 = false;
/* 1690 */     boolean bool5 = false;
/*      */     
/* 1692 */     boolean bool6 = false;
/*      */     
/* 1694 */     int[] arrayOfInt1 = { 0, 1, 1, 0 };
/* 1695 */     int[] arrayOfInt2 = { 0, 0, 0, 0 };
/*      */     
/* 1697 */     int[] arrayOfInt3 = bool3 ? arrayOfInt1 : arrayOfInt2;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1702 */     SOFMarkerSegment.ComponentSpec[] arrayOfComponentSpec = null;
/*      */     
/* 1704 */     if (sOFMarkerSegment != null) {
/* 1705 */       arrayOfComponentSpec = sOFMarkerSegment.componentSpecs;
/* 1706 */       bool6 = (sOFMarkerSegment.tag == 194) ? true : false;
/*      */ 
/*      */       
/* 1709 */       this.markerSequence.set(this.markerSequence.indexOf(sOFMarkerSegment), new SOFMarkerSegment(bool6, false, bool3, arrayOfByte, b1));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       byte b;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1721 */       for (b = 0; b < arrayOfComponentSpec.length; b++) {
/* 1722 */         if ((arrayOfComponentSpec[b]).QtableSelector != arrayOfInt3[b]) {
/* 1723 */           bool4 = true;
/*      */         }
/*      */       } 
/*      */       
/* 1727 */       if (bool6) {
/*      */ 
/*      */         
/* 1730 */         b = 0;
/* 1731 */         for (byte b4 = 0; b4 < arrayOfComponentSpec.length; b4++) {
/* 1732 */           if (arrayOfByte[b4] != (arrayOfComponentSpec[b4]).componentId) {
/* 1733 */             b = 1;
/*      */           }
/*      */         } 
/* 1736 */         if (b != 0)
/*      */         {
/* 1738 */           for (MarkerSegment markerSegment : this.markerSequence) {
/*      */             
/* 1740 */             if (markerSegment instanceof SOSMarkerSegment) {
/* 1741 */               SOSMarkerSegment sOSMarkerSegment1 = (SOSMarkerSegment)markerSegment;
/* 1742 */               for (byte b5 = 0; b5 < sOSMarkerSegment1.componentSpecs.length; b5++) {
/* 1743 */                 int i = (sOSMarkerSegment1.componentSpecs[b5]).componentSelector;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                 
/* 1751 */                 for (byte b6 = 0; b6 < arrayOfComponentSpec.length; b6++) {
/* 1752 */                   if ((arrayOfComponentSpec[b6]).componentId == i) {
/* 1753 */                     (sOSMarkerSegment1.componentSpecs[b5]).componentSelector = arrayOfByte[b6];
/*      */                   }
/*      */                 }
/*      */               
/*      */               }
/*      */             
/*      */             } 
/*      */           } 
/*      */         }
/* 1762 */       } else if (sOSMarkerSegment != null) {
/*      */ 
/*      */         
/* 1765 */         for (b = 0; b < sOSMarkerSegment.componentSpecs.length; b++) {
/* 1766 */           if ((sOSMarkerSegment.componentSpecs[b]).dcHuffTable != arrayOfInt3[b] || (sOSMarkerSegment.componentSpecs[b]).acHuffTable != arrayOfInt3[b])
/*      */           {
/*      */ 
/*      */             
/* 1770 */             bool5 = true;
/*      */           }
/*      */         } 
/*      */ 
/*      */         
/* 1775 */         this.markerSequence.set(this.markerSequence.indexOf(sOSMarkerSegment), new SOSMarkerSegment(bool3, arrayOfByte, b1));
/*      */ 
/*      */       
/*      */       }
/*      */ 
/*      */ 
/*      */     
/*      */     }
/* 1783 */     else if (this.isStream) {
/*      */       
/* 1785 */       bool4 = true;
/* 1786 */       bool5 = true;
/*      */     } 
/*      */ 
/*      */     
/* 1790 */     if (bool4) {
/* 1791 */       ArrayList<MarkerSegment> arrayList = new ArrayList();
/* 1792 */       for (MarkerSegment markerSegment : this.markerSequence) {
/*      */         
/* 1794 */         if (markerSegment instanceof DQTMarkerSegment) {
/* 1795 */           arrayList.add(markerSegment);
/*      */         }
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 1801 */       if (!arrayList.isEmpty() && bool3) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1808 */         boolean bool7 = false;
/* 1809 */         for (DQTMarkerSegment dQTMarkerSegment : arrayList) {
/*      */           
/* 1811 */           Iterator<DQTMarkerSegment.Qtable> iterator = dQTMarkerSegment.tables.iterator();
/* 1812 */           while (iterator.hasNext()) {
/*      */             
/* 1814 */             DQTMarkerSegment.Qtable qtable = iterator.next();
/* 1815 */             if (qtable.tableID == 1) {
/* 1816 */               bool7 = true;
/*      */             }
/*      */           } 
/*      */         } 
/* 1820 */         if (!bool7) {
/*      */           
/* 1822 */           DQTMarkerSegment.Qtable qtable = null;
/* 1823 */           for (DQTMarkerSegment dQTMarkerSegment1 : arrayList) {
/*      */             
/* 1825 */             Iterator<DQTMarkerSegment.Qtable> iterator = dQTMarkerSegment1.tables.iterator();
/* 1826 */             while (iterator.hasNext()) {
/*      */               
/* 1828 */               DQTMarkerSegment.Qtable qtable1 = iterator.next();
/* 1829 */               if (qtable1.tableID == 0) {
/* 1830 */                 qtable = qtable1;
/*      */               }
/*      */             } 
/*      */           } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 1839 */           DQTMarkerSegment dQTMarkerSegment = (DQTMarkerSegment)arrayList.get(arrayList.size() - 1);
/* 1840 */           dQTMarkerSegment.tables.add(dQTMarkerSegment.getChromaForLuma(qtable));
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 1845 */     if (bool5) {
/* 1846 */       ArrayList<MarkerSegment> arrayList = new ArrayList();
/* 1847 */       for (MarkerSegment markerSegment : this.markerSequence) {
/*      */         
/* 1849 */         if (markerSegment instanceof DHTMarkerSegment) {
/* 1850 */           arrayList.add(markerSegment);
/*      */         }
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 1856 */       if (!arrayList.isEmpty() && bool3) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1862 */         boolean bool7 = false;
/* 1863 */         for (DHTMarkerSegment dHTMarkerSegment : arrayList) {
/*      */           
/* 1865 */           Iterator<DHTMarkerSegment.Htable> iterator = dHTMarkerSegment.tables.iterator();
/* 1866 */           while (iterator.hasNext()) {
/*      */             
/* 1868 */             DHTMarkerSegment.Htable htable = iterator.next();
/* 1869 */             if (htable.tableID == 1) {
/* 1870 */               bool7 = true;
/*      */             }
/*      */           } 
/*      */         } 
/* 1874 */         if (!bool7) {
/*      */ 
/*      */ 
/*      */           
/* 1878 */           DHTMarkerSegment dHTMarkerSegment = (DHTMarkerSegment)arrayList.get(arrayList.size() - 1);
/* 1879 */           dHTMarkerSegment.addHtable(JPEGHuffmanTable.StdDCLuminance, true, 1);
/* 1880 */           dHTMarkerSegment.addHtable(JPEGHuffmanTable.StdACLuminance, true, 1);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   private boolean wantAlpha(Node paramNode) {
/* 1887 */     boolean bool = false;
/* 1888 */     Node node = paramNode.getFirstChild();
/* 1889 */     if (node.getNodeName().equals("Alpha") && 
/* 1890 */       node.hasAttributes()) {
/*      */       
/* 1892 */       String str = node.getAttributes().getNamedItem("value").getNodeValue();
/* 1893 */       if (!str.equals("none")) {
/* 1894 */         bool = true;
/*      */       }
/*      */     } 
/*      */     
/* 1898 */     this.transparencyDone = true;
/* 1899 */     return bool;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void mergeStandardCompressionNode(Node paramNode) throws IIOInvalidTreeException {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void mergeStandardDataNode(Node paramNode) throws IIOInvalidTreeException {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void mergeStandardDimensionNode(Node paramNode) throws IIOInvalidTreeException {
/* 1919 */     JFIFMarkerSegment jFIFMarkerSegment = (JFIFMarkerSegment)findMarkerSegment(JFIFMarkerSegment.class, true);
/* 1920 */     if (jFIFMarkerSegment == null) {
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1925 */       boolean bool = false;
/*      */       
/* 1927 */       SOFMarkerSegment sOFMarkerSegment = (SOFMarkerSegment)findMarkerSegment(SOFMarkerSegment.class, true);
/* 1928 */       if (sOFMarkerSegment != null) {
/* 1929 */         int i = sOFMarkerSegment.componentSpecs.length;
/* 1930 */         if (i == 1 || i == 3) {
/* 1931 */           bool = true;
/* 1932 */           for (byte b = 0; b < sOFMarkerSegment.componentSpecs.length; b++) {
/* 1933 */             if ((sOFMarkerSegment.componentSpecs[b]).componentId != b + 1) {
/* 1934 */               bool = false;
/*      */             }
/*      */           } 
/*      */ 
/*      */           
/* 1939 */           AdobeMarkerSegment adobeMarkerSegment = (AdobeMarkerSegment)findMarkerSegment(AdobeMarkerSegment.class, true);
/*      */           
/* 1941 */           if (adobeMarkerSegment != null && 
/* 1942 */             adobeMarkerSegment.transform != ((i == 1) ? 0 : 1))
/*      */           {
/*      */             
/* 1945 */             bool = false;
/*      */           }
/*      */         } 
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 1952 */       if (bool) {
/* 1953 */         jFIFMarkerSegment = new JFIFMarkerSegment();
/* 1954 */         this.markerSequence.add(0, jFIFMarkerSegment);
/*      */       } 
/*      */     } 
/* 1957 */     if (jFIFMarkerSegment != null) {
/* 1958 */       NodeList nodeList = paramNode.getChildNodes();
/* 1959 */       for (byte b = 0; b < nodeList.getLength(); b++) {
/* 1960 */         Node node = nodeList.item(b);
/* 1961 */         NamedNodeMap namedNodeMap = node.getAttributes();
/* 1962 */         String str = node.getNodeName();
/* 1963 */         if (str.equals("PixelAspectRatio")) {
/* 1964 */           String str1 = namedNodeMap.getNamedItem("value").getNodeValue();
/* 1965 */           float f = Float.parseFloat(str1);
/* 1966 */           Point point = findIntegerRatio(f);
/* 1967 */           jFIFMarkerSegment.resUnits = 0;
/* 1968 */           jFIFMarkerSegment.Xdensity = point.x;
/* 1969 */           jFIFMarkerSegment.Xdensity = point.y;
/* 1970 */         } else if (str.equals("HorizontalPixelSize")) {
/* 1971 */           String str1 = namedNodeMap.getNamedItem("value").getNodeValue();
/* 1972 */           float f = Float.parseFloat(str1);
/*      */           
/* 1974 */           int i = (int)Math.round(1.0D / f * 10.0D);
/* 1975 */           jFIFMarkerSegment.resUnits = 2;
/* 1976 */           jFIFMarkerSegment.Xdensity = i;
/* 1977 */         } else if (str.equals("VerticalPixelSize")) {
/* 1978 */           String str1 = namedNodeMap.getNamedItem("value").getNodeValue();
/* 1979 */           float f = Float.parseFloat(str1);
/*      */           
/* 1981 */           int i = (int)Math.round(1.0D / f * 10.0D);
/* 1982 */           jFIFMarkerSegment.resUnits = 2;
/* 1983 */           jFIFMarkerSegment.Ydensity = i;
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
/*      */   private static Point findIntegerRatio(float paramFloat) {
/* 1995 */     float f1 = 0.005F;
/*      */ 
/*      */     
/* 1998 */     paramFloat = Math.abs(paramFloat);
/*      */ 
/*      */     
/* 2001 */     if (paramFloat <= f1) {
/* 2002 */       return new Point(1, 255);
/*      */     }
/*      */ 
/*      */     
/* 2006 */     if (paramFloat >= 255.0F) {
/* 2007 */       return new Point(255, 1);
/*      */     }
/*      */ 
/*      */     
/* 2011 */     boolean bool = false;
/* 2012 */     if (paramFloat < 1.0D) {
/* 2013 */       paramFloat = 1.0F / paramFloat;
/* 2014 */       bool = true;
/*      */     } 
/*      */ 
/*      */     
/* 2018 */     byte b = 1;
/* 2019 */     int i = Math.round(paramFloat);
/*      */     
/* 2021 */     float f2 = i;
/* 2022 */     float f3 = Math.abs(paramFloat - f2);
/* 2023 */     while (f3 > f1) {
/*      */       
/* 2025 */       b++;
/* 2026 */       i = Math.round(b * paramFloat);
/* 2027 */       f2 = i / b;
/* 2028 */       f3 = Math.abs(paramFloat - f2);
/*      */     } 
/* 2030 */     return bool ? new Point(b, i) : new Point(i, b);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void mergeStandardDocumentNode(Node paramNode) throws IIOInvalidTreeException {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void mergeStandardTextNode(Node paramNode) throws IIOInvalidTreeException {
/* 2043 */     NodeList nodeList = paramNode.getChildNodes();
/* 2044 */     for (byte b = 0; b < nodeList.getLength(); b++) {
/* 2045 */       Node node1 = nodeList.item(b);
/* 2046 */       NamedNodeMap namedNodeMap = node1.getAttributes();
/* 2047 */       Node node2 = namedNodeMap.getNamedItem("compression");
/* 2048 */       boolean bool = true;
/* 2049 */       if (node2 != null) {
/* 2050 */         String str = node2.getNodeValue();
/* 2051 */         if (!str.equals("none")) {
/* 2052 */           bool = false;
/*      */         }
/*      */       } 
/* 2055 */       if (bool) {
/* 2056 */         String str = namedNodeMap.getNamedItem("value").getNodeValue();
/* 2057 */         COMMarkerSegment cOMMarkerSegment = new COMMarkerSegment(str);
/* 2058 */         insertCOMMarkerSegment(cOMMarkerSegment);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void mergeStandardTransparencyNode(Node paramNode) throws IIOInvalidTreeException {
/* 2069 */     if (!this.transparencyDone && !this.isStream) {
/* 2070 */       boolean bool = wantAlpha(paramNode);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2075 */       JFIFMarkerSegment jFIFMarkerSegment = (JFIFMarkerSegment)findMarkerSegment(JFIFMarkerSegment.class, true);
/*      */       
/* 2077 */       AdobeMarkerSegment adobeMarkerSegment = (AdobeMarkerSegment)findMarkerSegment(AdobeMarkerSegment.class, true);
/*      */       
/* 2079 */       SOFMarkerSegment sOFMarkerSegment = (SOFMarkerSegment)findMarkerSegment(SOFMarkerSegment.class, true);
/*      */       
/* 2081 */       SOSMarkerSegment sOSMarkerSegment = (SOSMarkerSegment)findMarkerSegment(SOSMarkerSegment.class, true);
/*      */ 
/*      */ 
/*      */       
/* 2085 */       if (sOFMarkerSegment != null && sOFMarkerSegment.tag == 194) {
/*      */         return;
/*      */       }
/*      */ 
/*      */ 
/*      */       
/* 2091 */       if (sOFMarkerSegment != null) {
/* 2092 */         int i = sOFMarkerSegment.componentSpecs.length;
/* 2093 */         boolean bool1 = (i == 2 || i == 4);
/*      */         
/* 2095 */         if (bool1 != bool) {
/* 2096 */           if (bool) {
/* 2097 */             i++;
/* 2098 */             if (jFIFMarkerSegment != null) {
/* 2099 */               this.markerSequence.remove(jFIFMarkerSegment);
/*      */             }
/*      */ 
/*      */             
/* 2103 */             if (adobeMarkerSegment != null) {
/* 2104 */               adobeMarkerSegment.transform = 0;
/*      */             }
/*      */ 
/*      */             
/* 2108 */             SOFMarkerSegment.ComponentSpec[] arrayOfComponentSpec = new SOFMarkerSegment.ComponentSpec[i];
/*      */             byte b;
/* 2110 */             for (b = 0; b < sOFMarkerSegment.componentSpecs.length; b++) {
/* 2111 */               arrayOfComponentSpec[b] = sOFMarkerSegment.componentSpecs[b];
/*      */             }
/* 2113 */             b = (byte)(sOFMarkerSegment.componentSpecs[0]).componentId;
/* 2114 */             byte b1 = (byte)((b > 1) ? 65 : 4);
/* 2115 */             arrayOfComponentSpec[i - 1] = sOFMarkerSegment
/* 2116 */               .getComponentSpec(b1, (sOFMarkerSegment.componentSpecs[0]).HsamplingFactor, (sOFMarkerSegment.componentSpecs[0]).QtableSelector);
/*      */ 
/*      */ 
/*      */             
/* 2120 */             sOFMarkerSegment.componentSpecs = arrayOfComponentSpec;
/*      */ 
/*      */             
/* 2123 */             SOSMarkerSegment.ScanComponentSpec[] arrayOfScanComponentSpec = new SOSMarkerSegment.ScanComponentSpec[i];
/*      */             
/* 2125 */             for (byte b2 = 0; b2 < sOSMarkerSegment.componentSpecs.length; b2++) {
/* 2126 */               arrayOfScanComponentSpec[b2] = sOSMarkerSegment.componentSpecs[b2];
/*      */             }
/* 2128 */             arrayOfScanComponentSpec[i - 1] = sOSMarkerSegment
/* 2129 */               .getScanComponentSpec(b1, 0);
/* 2130 */             sOSMarkerSegment.componentSpecs = arrayOfScanComponentSpec;
/*      */           } else {
/* 2132 */             i--;
/*      */             
/* 2134 */             SOFMarkerSegment.ComponentSpec[] arrayOfComponentSpec = new SOFMarkerSegment.ComponentSpec[i];
/*      */             
/* 2136 */             for (byte b1 = 0; b1 < i; b1++) {
/* 2137 */               arrayOfComponentSpec[b1] = sOFMarkerSegment.componentSpecs[b1];
/*      */             }
/* 2139 */             sOFMarkerSegment.componentSpecs = arrayOfComponentSpec;
/*      */ 
/*      */             
/* 2142 */             SOSMarkerSegment.ScanComponentSpec[] arrayOfScanComponentSpec = new SOSMarkerSegment.ScanComponentSpec[i];
/*      */             
/* 2144 */             for (byte b2 = 0; b2 < i; b2++) {
/* 2145 */               arrayOfScanComponentSpec[b2] = sOSMarkerSegment.componentSpecs[b2];
/*      */             }
/* 2147 */             sOSMarkerSegment.componentSpecs = arrayOfScanComponentSpec;
/*      */           } 
/*      */         }
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void setFromTree(String paramString, Node paramNode) throws IIOInvalidTreeException {
/* 2157 */     if (paramString == null) {
/* 2158 */       throw new IllegalArgumentException("null formatName!");
/*      */     }
/* 2160 */     if (paramNode == null) {
/* 2161 */       throw new IllegalArgumentException("null root!");
/*      */     }
/* 2163 */     if (this.isStream && paramString
/* 2164 */       .equals("javax_imageio_jpeg_stream_1.0")) {
/* 2165 */       setFromNativeTree(paramNode);
/* 2166 */     } else if (!this.isStream && paramString
/* 2167 */       .equals("javax_imageio_jpeg_image_1.0")) {
/* 2168 */       setFromNativeTree(paramNode);
/* 2169 */     } else if (!this.isStream && paramString
/*      */       
/* 2171 */       .equals("javax_imageio_1.0")) {
/*      */       
/* 2173 */       super.setFromTree(paramString, paramNode);
/*      */     } else {
/* 2175 */       throw new IllegalArgumentException("Unsupported format name: " + paramString);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void setFromNativeTree(Node paramNode) throws IIOInvalidTreeException {
/* 2181 */     if (this.resetSequence == null) {
/* 2182 */       this.resetSequence = this.markerSequence;
/*      */     }
/* 2184 */     this.markerSequence = new ArrayList();
/*      */ 
/*      */ 
/*      */     
/* 2188 */     String str = paramNode.getNodeName();
/* 2189 */     if (str != (this.isStream ? "javax_imageio_jpeg_stream_1.0" : "javax_imageio_jpeg_image_1.0"))
/*      */     {
/* 2191 */       throw new IIOInvalidTreeException("Invalid root node name: " + str, paramNode);
/*      */     }
/*      */     
/* 2194 */     if (!this.isStream) {
/* 2195 */       if (paramNode.getChildNodes().getLength() != 2) {
/* 2196 */         throw new IIOInvalidTreeException("JPEGvariety and markerSequence nodes must be present", paramNode);
/*      */       }
/*      */ 
/*      */       
/* 2200 */       Node node1 = paramNode.getFirstChild();
/*      */       
/* 2202 */       if (node1.getChildNodes().getLength() != 0) {
/* 2203 */         this.markerSequence.add(new JFIFMarkerSegment(node1.getFirstChild()));
/*      */       }
/*      */     } 
/*      */     
/* 2207 */     Node node = this.isStream ? paramNode : paramNode.getLastChild();
/* 2208 */     setFromMarkerSequenceNode(node);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void setFromMarkerSequenceNode(Node paramNode) throws IIOInvalidTreeException {
/* 2215 */     NodeList nodeList = paramNode.getChildNodes();
/*      */     
/* 2217 */     for (byte b = 0; b < nodeList.getLength(); b++) {
/* 2218 */       Node node = nodeList.item(b);
/* 2219 */       String str = node.getNodeName();
/* 2220 */       if (str.equals("dqt")) {
/* 2221 */         this.markerSequence.add(new DQTMarkerSegment(node));
/* 2222 */       } else if (str.equals("dht")) {
/* 2223 */         this.markerSequence.add(new DHTMarkerSegment(node));
/* 2224 */       } else if (str.equals("dri")) {
/* 2225 */         this.markerSequence.add(new DRIMarkerSegment(node));
/* 2226 */       } else if (str.equals("com")) {
/* 2227 */         this.markerSequence.add(new COMMarkerSegment(node));
/* 2228 */       } else if (str.equals("app14Adobe")) {
/* 2229 */         this.markerSequence.add(new AdobeMarkerSegment(node));
/* 2230 */       } else if (str.equals("unknown")) {
/* 2231 */         this.markerSequence.add(new MarkerSegment(node));
/* 2232 */       } else if (str.equals("sof")) {
/* 2233 */         this.markerSequence.add(new SOFMarkerSegment(node));
/* 2234 */       } else if (str.equals("sos")) {
/* 2235 */         this.markerSequence.add(new SOSMarkerSegment(node));
/*      */       } else {
/* 2237 */         throw new IIOInvalidTreeException("Invalid " + (this.isStream ? "stream " : "image ") + "child: " + str, node);
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
/*      */   private boolean isConsistent() {
/* 2253 */     SOFMarkerSegment sOFMarkerSegment = (SOFMarkerSegment)findMarkerSegment(SOFMarkerSegment.class, true);
/*      */ 
/*      */     
/* 2256 */     JFIFMarkerSegment jFIFMarkerSegment = (JFIFMarkerSegment)findMarkerSegment(JFIFMarkerSegment.class, true);
/*      */ 
/*      */     
/* 2259 */     AdobeMarkerSegment adobeMarkerSegment = (AdobeMarkerSegment)findMarkerSegment(AdobeMarkerSegment.class, true);
/*      */     
/* 2261 */     boolean bool = true;
/* 2262 */     if (!this.isStream) {
/* 2263 */       if (sOFMarkerSegment != null) {
/*      */         
/* 2265 */         int i = sOFMarkerSegment.componentSpecs.length;
/* 2266 */         int j = countScanBands();
/* 2267 */         if (j != 0 && 
/* 2268 */           j != i) {
/* 2269 */           bool = false;
/*      */         }
/*      */ 
/*      */         
/* 2273 */         if (jFIFMarkerSegment != null) {
/* 2274 */           if (i != 1 && i != 3) {
/* 2275 */             bool = false;
/*      */           }
/* 2277 */           for (byte b = 0; b < i; b++) {
/* 2278 */             if ((sOFMarkerSegment.componentSpecs[b]).componentId != b + 1) {
/* 2279 */               bool = false;
/*      */             }
/*      */           } 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 2286 */           if (adobeMarkerSegment != null && ((i == 1 && adobeMarkerSegment.transform != 0) || (i == 3 && adobeMarkerSegment.transform != 1)))
/*      */           {
/*      */ 
/*      */ 
/*      */             
/* 2291 */             bool = false;
/*      */           }
/*      */         }
/*      */       
/*      */       } else {
/*      */         
/* 2297 */         SOSMarkerSegment sOSMarkerSegment = (SOSMarkerSegment)findMarkerSegment(SOSMarkerSegment.class, true);
/*      */         
/* 2299 */         if (jFIFMarkerSegment != null || adobeMarkerSegment != null || sOFMarkerSegment != null || sOSMarkerSegment != null)
/*      */         {
/* 2301 */           bool = false;
/*      */         }
/*      */       } 
/*      */     }
/* 2305 */     return bool;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int countScanBands() {
/* 2313 */     ArrayList<Integer> arrayList = new ArrayList();
/* 2314 */     Iterator<MarkerSegment> iterator = this.markerSequence.iterator();
/* 2315 */     while (iterator.hasNext()) {
/* 2316 */       MarkerSegment markerSegment = iterator.next();
/* 2317 */       if (markerSegment instanceof SOSMarkerSegment) {
/* 2318 */         SOSMarkerSegment sOSMarkerSegment = (SOSMarkerSegment)markerSegment;
/* 2319 */         SOSMarkerSegment.ScanComponentSpec[] arrayOfScanComponentSpec = sOSMarkerSegment.componentSpecs;
/* 2320 */         for (byte b = 0; b < arrayOfScanComponentSpec.length; b++) {
/* 2321 */           Integer integer = new Integer((arrayOfScanComponentSpec[b]).componentSelector);
/* 2322 */           if (!arrayList.contains(integer)) {
/* 2323 */             arrayList.add(integer);
/*      */           }
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 2329 */     return arrayList.size();
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
/*      */   void writeToStream(ImageOutputStream paramImageOutputStream, boolean paramBoolean1, boolean paramBoolean2, List paramList, ICC_Profile paramICC_Profile, boolean paramBoolean3, int paramInt, JPEGImageWriter paramJPEGImageWriter) throws IOException {
/* 2343 */     if (paramBoolean2) {
/*      */ 
/*      */ 
/*      */       
/* 2347 */       JFIFMarkerSegment.writeDefaultJFIF(paramImageOutputStream, paramList, paramICC_Profile, paramJPEGImageWriter);
/*      */ 
/*      */ 
/*      */       
/* 2351 */       if (!paramBoolean3 && paramInt != -1)
/*      */       {
/* 2353 */         if (paramInt != 0 && paramInt != 1) {
/*      */ 
/*      */           
/* 2356 */           paramBoolean3 = true;
/* 2357 */           paramJPEGImageWriter
/* 2358 */             .warningOccurred(13);
/*      */         } 
/*      */       }
/*      */     } 
/*      */     
/* 2363 */     Iterator<MarkerSegment> iterator = this.markerSequence.iterator();
/* 2364 */     while (iterator.hasNext()) {
/* 2365 */       MarkerSegment markerSegment = iterator.next();
/* 2366 */       if (markerSegment instanceof JFIFMarkerSegment) {
/* 2367 */         if (!paramBoolean1) {
/* 2368 */           JFIFMarkerSegment jFIFMarkerSegment = (JFIFMarkerSegment)markerSegment;
/* 2369 */           jFIFMarkerSegment.writeWithThumbs(paramImageOutputStream, paramList, paramJPEGImageWriter);
/* 2370 */           if (paramICC_Profile != null)
/* 2371 */             JFIFMarkerSegment.writeICC(paramICC_Profile, paramImageOutputStream); 
/*      */         }  continue;
/*      */       } 
/* 2374 */       if (markerSegment instanceof AdobeMarkerSegment) {
/* 2375 */         if (!paramBoolean3) {
/* 2376 */           if (paramInt != -1) {
/*      */             
/* 2378 */             AdobeMarkerSegment adobeMarkerSegment = (AdobeMarkerSegment)markerSegment.clone();
/* 2379 */             adobeMarkerSegment.transform = paramInt;
/* 2380 */             adobeMarkerSegment.write(paramImageOutputStream); continue;
/* 2381 */           }  if (paramBoolean2) {
/*      */             
/* 2383 */             AdobeMarkerSegment adobeMarkerSegment = (AdobeMarkerSegment)markerSegment;
/* 2384 */             if (adobeMarkerSegment.transform == 0 || adobeMarkerSegment.transform == 1) {
/*      */               
/* 2386 */               adobeMarkerSegment.write(paramImageOutputStream); continue;
/*      */             } 
/* 2388 */             paramJPEGImageWriter
/* 2389 */               .warningOccurred(13);
/*      */             continue;
/*      */           } 
/* 2392 */           markerSegment.write(paramImageOutputStream);
/*      */         } 
/*      */         continue;
/*      */       } 
/* 2396 */       markerSegment.write(paramImageOutputStream);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void reset() {
/* 2404 */     if (this.resetSequence != null) {
/* 2405 */       this.markerSequence = this.resetSequence;
/* 2406 */       this.resetSequence = null;
/*      */     } 
/*      */   }
/*      */   
/*      */   public void print() {
/* 2411 */     for (byte b = 0; b < this.markerSequence.size(); b++) {
/* 2412 */       MarkerSegment markerSegment = this.markerSequence.get(b);
/* 2413 */       markerSegment.print();
/*      */     } 
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/imageio/plugins/jpeg/JPEGMetadata.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */