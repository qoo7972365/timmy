/*    */ package com.sun.imageio.spi;
/*    */ 
/*    */ import java.io.File;
/*    */ import java.io.IOException;
/*    */ import java.io.OutputStream;
/*    */ import java.util.Locale;
/*    */ import javax.imageio.spi.ImageOutputStreamSpi;
/*    */ import javax.imageio.stream.FileCacheImageOutputStream;
/*    */ import javax.imageio.stream.ImageOutputStream;
/*    */ import javax.imageio.stream.MemoryCacheImageOutputStream;
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
/*    */ public class OutputStreamImageOutputStreamSpi
/*    */   extends ImageOutputStreamSpi
/*    */ {
/*    */   private static final String vendorName = "Oracle Corporation";
/*    */   private static final String version = "1.0";
/* 43 */   private static final Class outputClass = OutputStream.class;
/*    */   
/*    */   public OutputStreamImageOutputStreamSpi() {
/* 46 */     super("Oracle Corporation", "1.0", outputClass);
/*    */   }
/*    */   
/*    */   public String getDescription(Locale paramLocale) {
/* 50 */     return "Service provider that instantiates an OutputStreamImageOutputStream from an OutputStream";
/*    */   }
/*    */   
/*    */   public boolean canUseCacheFile() {
/* 54 */     return true;
/*    */   }
/*    */   
/*    */   public boolean needsCacheFile() {
/* 58 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ImageOutputStream createOutputStreamInstance(Object paramObject, boolean paramBoolean, File paramFile) throws IOException {
/* 65 */     if (paramObject instanceof OutputStream) {
/* 66 */       OutputStream outputStream = (OutputStream)paramObject;
/* 67 */       if (paramBoolean) {
/* 68 */         return new FileCacheImageOutputStream(outputStream, paramFile);
/*    */       }
/* 70 */       return new MemoryCacheImageOutputStream(outputStream);
/*    */     } 
/*    */     
/* 73 */     throw new IllegalArgumentException();
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/imageio/spi/OutputStreamImageOutputStreamSpi.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */