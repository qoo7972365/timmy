/*    */ package com.sun.imageio.plugins.wbmp;
/*    */ 
/*    */ import java.awt.image.SampleModel;
/*    */ import java.util.Locale;
/*    */ import javax.imageio.IIOException;
/*    */ import javax.imageio.ImageTypeSpecifier;
/*    */ import javax.imageio.ImageWriter;
/*    */ import javax.imageio.spi.ImageWriterSpi;
/*    */ import javax.imageio.spi.ServiceRegistry;
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
/*    */ public class WBMPImageWriterSpi
/*    */   extends ImageWriterSpi
/*    */ {
/* 43 */   private static String[] readerSpiNames = new String[] { "com.sun.imageio.plugins.wbmp.WBMPImageReaderSpi" };
/*    */   
/* 45 */   private static String[] formatNames = new String[] { "wbmp", "WBMP" };
/* 46 */   private static String[] entensions = new String[] { "wbmp" };
/* 47 */   private static String[] mimeType = new String[] { "image/vnd.wap.wbmp" };
/*    */   
/*    */   private boolean registered = false;
/*    */   
/*    */   public WBMPImageWriterSpi() {
/* 52 */     super("Oracle Corporation", "1.0", formatNames, entensions, mimeType, "com.sun.imageio.plugins.wbmp.WBMPImageWriter", new Class[] { ImageOutputStream.class }, readerSpiNames, true, null, null, null, null, true, null, null, null, null);
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
/*    */   public String getDescription(Locale paramLocale) {
/* 67 */     return "Standard WBMP Image Writer";
/*    */   }
/*    */ 
/*    */   
/*    */   public void onRegistration(ServiceRegistry paramServiceRegistry, Class<?> paramClass) {
/* 72 */     if (this.registered) {
/*    */       return;
/*    */     }
/*    */     
/* 76 */     this.registered = true;
/*    */   }
/*    */   
/*    */   public boolean canEncodeImage(ImageTypeSpecifier paramImageTypeSpecifier) {
/* 80 */     SampleModel sampleModel = paramImageTypeSpecifier.getSampleModel();
/* 81 */     if (!(sampleModel instanceof java.awt.image.MultiPixelPackedSampleModel))
/* 82 */       return false; 
/* 83 */     if (sampleModel.getSampleSize(0) != 1) {
/* 84 */       return false;
/*    */     }
/* 86 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public ImageWriter createWriterInstance(Object paramObject) throws IIOException {
/* 91 */     return new WBMPImageWriter(this);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/imageio/plugins/wbmp/WBMPImageWriterSpi.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */