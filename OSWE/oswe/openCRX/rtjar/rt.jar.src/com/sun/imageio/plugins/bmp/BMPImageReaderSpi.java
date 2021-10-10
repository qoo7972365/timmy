/*    */ package com.sun.imageio.plugins.bmp;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.util.Locale;
/*    */ import javax.imageio.IIOException;
/*    */ import javax.imageio.ImageReader;
/*    */ import javax.imageio.spi.ImageReaderSpi;
/*    */ import javax.imageio.spi.ServiceRegistry;
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
/*    */ public class BMPImageReaderSpi
/*    */   extends ImageReaderSpi
/*    */ {
/* 39 */   private static String[] writerSpiNames = new String[] { "com.sun.imageio.plugins.bmp.BMPImageWriterSpi" };
/*    */   
/* 41 */   private static String[] formatNames = new String[] { "bmp", "BMP" };
/* 42 */   private static String[] entensions = new String[] { "bmp" };
/* 43 */   private static String[] mimeType = new String[] { "image/bmp" };
/*    */   
/*    */   private boolean registered = false;
/*    */   
/*    */   public BMPImageReaderSpi() {
/* 48 */     super("Oracle Corporation", "1.0", formatNames, entensions, mimeType, "com.sun.imageio.plugins.bmp.BMPImageReader", new Class[] { ImageInputStream.class }, writerSpiNames, false, null, null, null, null, true, "javax_imageio_bmp_1.0", "com.sun.imageio.plugins.bmp.BMPMetadataFormat", null, null);
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
/*    */   public void onRegistration(ServiceRegistry paramServiceRegistry, Class<?> paramClass) {
/* 66 */     if (this.registered) {
/*    */       return;
/*    */     }
/* 69 */     this.registered = true;
/*    */   }
/*    */   
/*    */   public String getDescription(Locale paramLocale) {
/* 73 */     return "Standard BMP Image Reader";
/*    */   }
/*    */   
/*    */   public boolean canDecodeInput(Object paramObject) throws IOException {
/* 77 */     if (!(paramObject instanceof ImageInputStream)) {
/* 78 */       return false;
/*    */     }
/*    */     
/* 81 */     ImageInputStream imageInputStream = (ImageInputStream)paramObject;
/* 82 */     byte[] arrayOfByte = new byte[2];
/* 83 */     imageInputStream.mark();
/* 84 */     imageInputStream.readFully(arrayOfByte);
/* 85 */     imageInputStream.reset();
/*    */     
/* 87 */     return (arrayOfByte[0] == 66 && arrayOfByte[1] == 77);
/*    */   }
/*    */ 
/*    */   
/*    */   public ImageReader createReaderInstance(Object paramObject) throws IIOException {
/* 92 */     return new BMPImageReader(this);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/imageio/plugins/bmp/BMPImageReaderSpi.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */