/*     */ package com.sun.imageio.plugins.wbmp;
/*     */ 
/*     */ import com.sun.imageio.plugins.common.ReaderUtil;
/*     */ import java.io.IOException;
/*     */ import java.util.Locale;
/*     */ import javax.imageio.IIOException;
/*     */ import javax.imageio.ImageReader;
/*     */ import javax.imageio.spi.ImageReaderSpi;
/*     */ import javax.imageio.spi.ServiceRegistry;
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
/*     */ public class WBMPImageReaderSpi
/*     */   extends ImageReaderSpi
/*     */ {
/*     */   private static final int MAX_WBMP_WIDTH = 1024;
/*     */   private static final int MAX_WBMP_HEIGHT = 768;
/*  43 */   private static String[] writerSpiNames = new String[] { "com.sun.imageio.plugins.wbmp.WBMPImageWriterSpi" };
/*     */   
/*  45 */   private static String[] formatNames = new String[] { "wbmp", "WBMP" };
/*  46 */   private static String[] entensions = new String[] { "wbmp" };
/*  47 */   private static String[] mimeType = new String[] { "image/vnd.wap.wbmp" };
/*     */   
/*     */   private boolean registered = false;
/*     */   
/*     */   public WBMPImageReaderSpi() {
/*  52 */     super("Oracle Corporation", "1.0", formatNames, entensions, mimeType, "com.sun.imageio.plugins.wbmp.WBMPImageReader", new Class[] { ImageInputStream.class }, writerSpiNames, true, null, null, null, null, true, "javax_imageio_wbmp_1.0", "com.sun.imageio.plugins.wbmp.WBMPMetadataFormat", null, null);
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
/*     */   public void onRegistration(ServiceRegistry paramServiceRegistry, Class<?> paramClass) {
/*  70 */     if (this.registered) {
/*     */       return;
/*     */     }
/*  73 */     this.registered = true;
/*     */   }
/*     */   
/*     */   public String getDescription(Locale paramLocale) {
/*  77 */     return "Standard WBMP Image Reader";
/*     */   }
/*     */   
/*     */   public boolean canDecodeInput(Object paramObject) throws IOException {
/*  81 */     if (!(paramObject instanceof ImageInputStream)) {
/*  82 */       return false;
/*     */     }
/*     */     
/*  85 */     ImageInputStream imageInputStream = (ImageInputStream)paramObject;
/*     */     
/*  87 */     imageInputStream.mark();
/*     */     try {
/*  89 */       byte b1 = imageInputStream.readByte();
/*  90 */       byte b2 = imageInputStream.readByte();
/*     */       
/*  92 */       if (b1 != 0 || b2 != 0)
/*     */       {
/*  94 */         return false;
/*     */       }
/*     */       
/*  97 */       int i = ReaderUtil.readMultiByteInteger(imageInputStream);
/*  98 */       int j = ReaderUtil.readMultiByteInteger(imageInputStream);
/*     */       
/* 100 */       if (i <= 0 || j <= 0) {
/* 101 */         return false;
/*     */       }
/*     */       
/* 104 */       long l1 = imageInputStream.length();
/* 105 */       if (l1 == -1L)
/*     */       {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 113 */         return (i < 1024 && j < 768);
/*     */       }
/*     */       
/* 116 */       l1 -= imageInputStream.getStreamPosition();
/*     */       
/* 118 */       long l2 = (i / 8 + ((i % 8 == 0) ? 0 : 1));
/*     */       
/* 120 */       return (l1 == l2 * j);
/*     */     } finally {
/* 122 */       imageInputStream.reset();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public ImageReader createReaderInstance(Object paramObject) throws IIOException {
/* 128 */     return new WBMPImageReader(this);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/imageio/plugins/wbmp/WBMPImageReaderSpi.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */