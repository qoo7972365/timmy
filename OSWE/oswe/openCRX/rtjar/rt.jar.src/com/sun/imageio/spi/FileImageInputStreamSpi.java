/*    */ package com.sun.imageio.spi;
/*    */ 
/*    */ import java.io.File;
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
/*    */ public class FileImageInputStreamSpi
/*    */   extends ImageInputStreamSpi
/*    */ {
/*    */   private static final String vendorName = "Oracle Corporation";
/*    */   private static final String version = "1.0";
/* 40 */   private static final Class inputClass = File.class;
/*    */   
/*    */   public FileImageInputStreamSpi() {
/* 43 */     super("Oracle Corporation", "1.0", inputClass);
/*    */   }
/*    */   
/*    */   public String getDescription(Locale paramLocale) {
/* 47 */     return "Service provider that instantiates a FileImageInputStream from a File";
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public ImageInputStream createInputStreamInstance(Object paramObject, boolean paramBoolean, File paramFile) {
/* 53 */     if (paramObject instanceof File) {
/*    */       try {
/* 55 */         return new FileImageInputStream((File)paramObject);
/* 56 */       } catch (Exception exception) {
/* 57 */         return null;
/*    */       } 
/*    */     }
/* 60 */     throw new IllegalArgumentException();
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/imageio/spi/FileImageInputStreamSpi.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */