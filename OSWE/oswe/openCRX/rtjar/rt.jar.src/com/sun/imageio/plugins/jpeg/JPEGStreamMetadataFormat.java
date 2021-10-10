/*    */ package com.sun.imageio.plugins.jpeg;
/*    */ 
/*    */ import javax.imageio.ImageTypeSpecifier;
/*    */ import javax.imageio.metadata.IIOMetadataFormat;
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
/*    */ public class JPEGStreamMetadataFormat
/*    */   extends JPEGMetadataFormat
/*    */ {
/* 33 */   private static JPEGStreamMetadataFormat theInstance = null;
/*    */   
/*    */   private JPEGStreamMetadataFormat() {
/* 36 */     super("javax_imageio_jpeg_stream_1.0", 4);
/*    */     
/* 38 */     addStreamElements(getRootName());
/*    */   }
/*    */   
/*    */   public static synchronized IIOMetadataFormat getInstance() {
/* 42 */     if (theInstance == null) {
/* 43 */       theInstance = new JPEGStreamMetadataFormat();
/*    */     }
/* 45 */     return theInstance;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/imageio/plugins/jpeg/JPEGStreamMetadataFormat.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */