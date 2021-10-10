/*    */ package com.sun.imageio.spi;
/*    */ 
/*    */ import java.io.File;
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
/*    */ public class FileImageOutputStreamSpi
/*    */   extends ImageOutputStreamSpi
/*    */ {
/*    */   private static final String vendorName = "Oracle Corporation";
/*    */   private static final String version = "1.0";
/* 40 */   private static final Class outputClass = File.class;
/*    */   
/*    */   public FileImageOutputStreamSpi() {
/* 43 */     super("Oracle Corporation", "1.0", outputClass);
/*    */   }
/*    */   
/*    */   public String getDescription(Locale paramLocale) {
/* 47 */     return "Service provider that instantiates a FileImageOutputStream from a File";
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public ImageOutputStream createOutputStreamInstance(Object paramObject, boolean paramBoolean, File paramFile) {
/* 53 */     if (paramObject instanceof File) {
/*    */       try {
/* 55 */         return new FileImageOutputStream((File)paramObject);
/* 56 */       } catch (Exception exception) {
/* 57 */         exception.printStackTrace();
/* 58 */         return null;
/*    */       } 
/*    */     }
/* 61 */     throw new IllegalArgumentException();
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/imageio/spi/FileImageOutputStreamSpi.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */