/*    */ package com.sun.imageio.plugins.jpeg;
/*    */ 
/*    */ import java.awt.image.SampleModel;
/*    */ import java.util.Locale;
/*    */ import javax.imageio.IIOException;
/*    */ import javax.imageio.ImageTypeSpecifier;
/*    */ import javax.imageio.ImageWriter;
/*    */ import javax.imageio.spi.ImageWriterSpi;
/*    */ import javax.imageio.stream.ImageOutputStream;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class JPEGImageWriterSpi
/*    */   extends ImageWriterSpi
/*    */ {
/* 43 */   private static String[] readerSpiNames = new String[] { "com.sun.imageio.plugins.jpeg.JPEGImageReaderSpi" };
/*    */ 
/*    */   
/*    */   public JPEGImageWriterSpi() {
/* 47 */     super("Oracle Corporation", "0.5", JPEG.names, JPEG.suffixes, JPEG.MIMETypes, "com.sun.imageio.plugins.jpeg.JPEGImageWriter", new Class[] { ImageOutputStream.class }, readerSpiNames, true, "javax_imageio_jpeg_stream_1.0", "com.sun.imageio.plugins.jpeg.JPEGStreamMetadataFormat", null, null, true, "javax_imageio_jpeg_image_1.0", "com.sun.imageio.plugins.jpeg.JPEGImageMetadataFormat", null, null);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getDescription(Locale paramLocale) {
/* 67 */     return "Standard JPEG Image Writer";
/*    */   }
/*    */   
/*    */   public boolean isFormatLossless() {
/* 71 */     return false;
/*    */   }
/*    */   
/*    */   public boolean canEncodeImage(ImageTypeSpecifier paramImageTypeSpecifier) {
/* 75 */     SampleModel sampleModel = paramImageTypeSpecifier.getSampleModel();
/*    */ 
/*    */     
/* 78 */     int[] arrayOfInt = sampleModel.getSampleSize();
/* 79 */     int i = arrayOfInt[0];
/* 80 */     for (byte b = 1; b < arrayOfInt.length; b++) {
/* 81 */       if (arrayOfInt[b] > i) {
/* 82 */         i = arrayOfInt[b];
/*    */       }
/*    */     } 
/*    */ 
/*    */     
/* 87 */     if (i < 1 || i > 8) {
/* 88 */       return false;
/*    */     }
/*    */     
/* 91 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public ImageWriter createWriterInstance(Object paramObject) throws IIOException {
/* 96 */     return new JPEGImageWriter(this);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/imageio/plugins/jpeg/JPEGImageWriterSpi.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */