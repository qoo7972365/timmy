/*     */ package com.sun.imageio.plugins.png;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.Locale;
/*     */ import javax.imageio.ImageReader;
/*     */ import javax.imageio.spi.ImageReaderSpi;
/*     */ import javax.imageio.stream.ImageInputStream;
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
/*     */ public class PNGImageReaderSpi
/*     */   extends ImageReaderSpi
/*     */ {
/*     */   private static final String vendorName = "Oracle Corporation";
/*     */   private static final String version = "1.0";
/*  43 */   private static final String[] names = new String[] { "png", "PNG" };
/*     */   
/*  45 */   private static final String[] suffixes = new String[] { "png" };
/*     */   
/*  47 */   private static final String[] MIMETypes = new String[] { "image/png", "image/x-png" };
/*     */ 
/*     */   
/*     */   private static final String readerClassName = "com.sun.imageio.plugins.png.PNGImageReader";
/*     */   
/*  52 */   private static final String[] writerSpiNames = new String[] { "com.sun.imageio.plugins.png.PNGImageWriterSpi" };
/*     */ 
/*     */ 
/*     */   
/*     */   public PNGImageReaderSpi() {
/*  57 */     super("Oracle Corporation", "1.0", names, suffixes, MIMETypes, "com.sun.imageio.plugins.png.PNGImageReader", new Class[] { ImageInputStream.class }, writerSpiNames, false, null, null, null, null, true, "javax_imageio_png_1.0", "com.sun.imageio.plugins.png.PNGMetadataFormat", null, null);
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
/*     */   public String getDescription(Locale paramLocale) {
/*  76 */     return "Standard PNG image reader";
/*     */   }
/*     */   
/*     */   public boolean canDecodeInput(Object paramObject) throws IOException {
/*  80 */     if (!(paramObject instanceof ImageInputStream)) {
/*  81 */       return false;
/*     */     }
/*     */     
/*  84 */     ImageInputStream imageInputStream = (ImageInputStream)paramObject;
/*  85 */     byte[] arrayOfByte = new byte[8];
/*  86 */     imageInputStream.mark();
/*  87 */     imageInputStream.readFully(arrayOfByte);
/*  88 */     imageInputStream.reset();
/*     */     
/*  90 */     return (arrayOfByte[0] == -119 && arrayOfByte[1] == 80 && arrayOfByte[2] == 78 && arrayOfByte[3] == 71 && arrayOfByte[4] == 13 && arrayOfByte[5] == 10 && arrayOfByte[6] == 26 && arrayOfByte[7] == 10);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ImageReader createReaderInstance(Object paramObject) {
/* 101 */     return new PNGImageReader(this);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/imageio/plugins/png/PNGImageReaderSpi.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */