/*     */ package com.sun.imageio.plugins.gif;
/*     */ 
/*     */ import com.sun.imageio.plugins.common.PaletteBuilder;
/*     */ import java.awt.image.ColorModel;
/*     */ import java.awt.image.SampleModel;
/*     */ import java.util.Locale;
/*     */ import javax.imageio.ImageTypeSpecifier;
/*     */ import javax.imageio.ImageWriter;
/*     */ import javax.imageio.spi.ImageWriterSpi;
/*     */ import javax.imageio.stream.ImageOutputStream;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class GIFImageWriterSpi
/*     */   extends ImageWriterSpi
/*     */ {
/*     */   private static final String vendorName = "Oracle Corporation";
/*     */   private static final String version = "1.0";
/*  43 */   private static final String[] names = new String[] { "gif", "GIF" };
/*     */   
/*  45 */   private static final String[] suffixes = new String[] { "gif" };
/*     */   
/*  47 */   private static final String[] MIMETypes = new String[] { "image/gif" };
/*     */ 
/*     */   
/*     */   private static final String writerClassName = "com.sun.imageio.plugins.gif.GIFImageWriter";
/*     */   
/*  52 */   private static final String[] readerSpiNames = new String[] { "com.sun.imageio.plugins.gif.GIFImageReaderSpi" };
/*     */ 
/*     */ 
/*     */   
/*     */   public GIFImageWriterSpi() {
/*  57 */     super("Oracle Corporation", "1.0", names, suffixes, MIMETypes, "com.sun.imageio.plugins.gif.GIFImageWriter", new Class[] { ImageOutputStream.class }, readerSpiNames, true, "javax_imageio_gif_stream_1.0", "com.sun.imageio.plugins.gif.GIFStreamMetadataFormat", null, null, true, "javax_imageio_gif_image_1.0", "com.sun.imageio.plugins.gif.GIFImageMetadataFormat", null, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canEncodeImage(ImageTypeSpecifier paramImageTypeSpecifier) {
/*  77 */     if (paramImageTypeSpecifier == null) {
/*  78 */       throw new IllegalArgumentException("type == null!");
/*     */     }
/*     */     
/*  81 */     SampleModel sampleModel = paramImageTypeSpecifier.getSampleModel();
/*  82 */     ColorModel colorModel = paramImageTypeSpecifier.getColorModel();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  88 */     boolean bool = (sampleModel.getNumBands() == 1 && sampleModel.getSampleSize(0) <= 8 && sampleModel.getWidth() <= 65535 && sampleModel.getHeight() <= 65535 && (colorModel == null || colorModel.getComponentSize()[0] <= 8)) ? true : false;
/*     */     
/*  90 */     if (bool) {
/*  91 */       return true;
/*     */     }
/*  93 */     return PaletteBuilder.canCreatePalette(paramImageTypeSpecifier);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getDescription(Locale paramLocale) {
/*  98 */     return "Standard GIF image writer";
/*     */   }
/*     */   
/*     */   public ImageWriter createWriterInstance(Object paramObject) {
/* 102 */     return new GIFImageWriter(this);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/imageio/plugins/gif/GIFImageWriterSpi.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */