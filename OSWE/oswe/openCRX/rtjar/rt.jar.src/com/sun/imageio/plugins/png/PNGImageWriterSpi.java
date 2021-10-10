/*     */ package com.sun.imageio.plugins.png;
/*     */ 
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PNGImageWriterSpi
/*     */   extends ImageWriterSpi
/*     */ {
/*     */   private static final String vendorName = "Oracle Corporation";
/*     */   private static final String version = "1.0";
/*  45 */   private static final String[] names = new String[] { "png", "PNG" };
/*     */   
/*  47 */   private static final String[] suffixes = new String[] { "png" };
/*     */   
/*  49 */   private static final String[] MIMETypes = new String[] { "image/png", "image/x-png" };
/*     */ 
/*     */   
/*     */   private static final String writerClassName = "com.sun.imageio.plugins.png.PNGImageWriter";
/*     */   
/*  54 */   private static final String[] readerSpiNames = new String[] { "com.sun.imageio.plugins.png.PNGImageReaderSpi" };
/*     */ 
/*     */ 
/*     */   
/*     */   public PNGImageWriterSpi() {
/*  59 */     super("Oracle Corporation", "1.0", names, suffixes, MIMETypes, "com.sun.imageio.plugins.png.PNGImageWriter", new Class[] { ImageOutputStream.class }, readerSpiNames, false, null, null, null, null, true, "javax_imageio_png_1.0", "com.sun.imageio.plugins.png.PNGMetadataFormat", null, null);
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
/*     */   public boolean canEncodeImage(ImageTypeSpecifier paramImageTypeSpecifier) {
/*  78 */     SampleModel sampleModel = paramImageTypeSpecifier.getSampleModel();
/*  79 */     ColorModel colorModel = paramImageTypeSpecifier.getColorModel();
/*     */ 
/*     */     
/*  82 */     int[] arrayOfInt = sampleModel.getSampleSize();
/*  83 */     int i = arrayOfInt[0]; int j;
/*  84 */     for (j = 1; j < arrayOfInt.length; j++) {
/*  85 */       if (arrayOfInt[j] > i) {
/*  86 */         i = arrayOfInt[j];
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/*  91 */     if (i < 1 || i > 16) {
/*  92 */       return false;
/*     */     }
/*     */ 
/*     */     
/*  96 */     j = sampleModel.getNumBands();
/*  97 */     if (j < 1 || j > 4) {
/*  98 */       return false;
/*     */     }
/*     */     
/* 101 */     boolean bool = colorModel.hasAlpha();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 106 */     if (colorModel instanceof java.awt.image.IndexColorModel) {
/* 107 */       return true;
/*     */     }
/* 109 */     if ((j == 1 || j == 3) && bool) {
/* 110 */       return false;
/*     */     }
/* 112 */     if ((j == 2 || j == 4) && !bool) {
/* 113 */       return false;
/*     */     }
/*     */     
/* 116 */     return true;
/*     */   }
/*     */   
/*     */   public String getDescription(Locale paramLocale) {
/* 120 */     return "Standard PNG image writer";
/*     */   }
/*     */   
/*     */   public ImageWriter createWriterInstance(Object paramObject) {
/* 124 */     return new PNGImageWriter(this);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/imageio/plugins/png/PNGImageWriterSpi.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */