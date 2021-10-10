/*    */ package com.sun.imageio.spi;
/*    */ 
/*    */ import java.io.File;
/*    */ import java.io.RandomAccessFile;
/*    */ import java.util.Locale;
/*    */ import javax.imageio.spi.ImageInputStreamSpi;
/*    */ import javax.imageio.stream.FileImageInputStream;
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
/*    */ public class RAFImageInputStreamSpi
/*    */   extends ImageInputStreamSpi
/*    */ {
/*    */   private static final String vendorName = "Oracle Corporation";
/*    */   private static final String version = "1.0";
/* 41 */   private static final Class inputClass = RandomAccessFile.class;
/*    */   
/*    */   public RAFImageInputStreamSpi() {
/* 44 */     super("Oracle Corporation", "1.0", inputClass);
/*    */   }
/*    */   
/*    */   public String getDescription(Locale paramLocale) {
/* 48 */     return "Service provider that instantiates a FileImageInputStream from a RandomAccessFile";
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public ImageInputStream createInputStreamInstance(Object paramObject, boolean paramBoolean, File paramFile) {
/* 54 */     if (paramObject instanceof RandomAccessFile) {
/*    */       try {
/* 56 */         return new FileImageInputStream((RandomAccessFile)paramObject);
/* 57 */       } catch (Exception exception) {
/* 58 */         return null;
/*    */       } 
/*    */     }
/* 61 */     throw new IllegalArgumentException("input not a RandomAccessFile!");
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/imageio/spi/RAFImageInputStreamSpi.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */