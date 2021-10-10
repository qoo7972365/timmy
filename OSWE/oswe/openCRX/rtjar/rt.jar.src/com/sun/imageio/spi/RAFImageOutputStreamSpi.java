/*    */ package com.sun.imageio.spi;
/*    */ 
/*    */ import java.io.File;
/*    */ import java.io.RandomAccessFile;
/*    */ import java.util.Locale;
/*    */ import javax.imageio.spi.ImageOutputStreamSpi;
/*    */ import javax.imageio.stream.FileImageOutputStream;
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
/*    */ public class RAFImageOutputStreamSpi
/*    */   extends ImageOutputStreamSpi
/*    */ {
/*    */   private static final String vendorName = "Oracle Corporation";
/*    */   private static final String version = "1.0";
/* 41 */   private static final Class outputClass = RandomAccessFile.class;
/*    */   
/*    */   public RAFImageOutputStreamSpi() {
/* 44 */     super("Oracle Corporation", "1.0", outputClass);
/*    */   }
/*    */   
/*    */   public String getDescription(Locale paramLocale) {
/* 48 */     return "Service provider that instantiates a FileImageOutputStream from a RandomAccessFile";
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public ImageOutputStream createOutputStreamInstance(Object paramObject, boolean paramBoolean, File paramFile) {
/* 54 */     if (paramObject instanceof RandomAccessFile) {
/*    */       try {
/* 56 */         return new FileImageOutputStream((RandomAccessFile)paramObject);
/* 57 */       } catch (Exception exception) {
/* 58 */         exception.printStackTrace();
/* 59 */         return null;
/*    */       } 
/*    */     }
/* 62 */     throw new IllegalArgumentException("input not a RandomAccessFile!");
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/imageio/spi/RAFImageOutputStreamSpi.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */