/*    */ package com.sun.imageio.plugins.jpeg;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.util.Locale;
/*    */ import javax.imageio.IIOException;
/*    */ import javax.imageio.ImageReader;
/*    */ import javax.imageio.spi.ImageReaderSpi;
/*    */ import javax.imageio.stream.ImageInputStream;
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
/*    */ public class JPEGImageReaderSpi
/*    */   extends ImageReaderSpi
/*    */ {
/* 39 */   private static String[] writerSpiNames = new String[] { "com.sun.imageio.plugins.jpeg.JPEGImageWriterSpi" };
/*    */ 
/*    */   
/*    */   public JPEGImageReaderSpi() {
/* 43 */     super("Oracle Corporation", "0.5", JPEG.names, JPEG.suffixes, JPEG.MIMETypes, "com.sun.imageio.plugins.jpeg.JPEGImageReader", new Class[] { ImageInputStream.class }, writerSpiNames, true, "javax_imageio_jpeg_stream_1.0", "com.sun.imageio.plugins.jpeg.JPEGStreamMetadataFormat", null, null, true, "javax_imageio_jpeg_image_1.0", "com.sun.imageio.plugins.jpeg.JPEGImageMetadataFormat", null, null);
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
/* 63 */     return "Standard JPEG Image Reader";
/*    */   }
/*    */   
/*    */   public boolean canDecodeInput(Object paramObject) throws IOException {
/* 67 */     if (!(paramObject instanceof ImageInputStream)) {
/* 68 */       return false;
/*    */     }
/* 70 */     ImageInputStream imageInputStream = (ImageInputStream)paramObject;
/* 71 */     imageInputStream.mark();
/*    */ 
/*    */     
/* 74 */     int i = imageInputStream.read();
/* 75 */     int j = imageInputStream.read();
/* 76 */     imageInputStream.reset();
/* 77 */     if (i == 255 && j == 216) {
/* 78 */       return true;
/*    */     }
/* 80 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public ImageReader createReaderInstance(Object paramObject) throws IIOException {
/* 85 */     return new JPEGImageReader(this);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/imageio/plugins/jpeg/JPEGImageReaderSpi.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */