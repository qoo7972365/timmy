/*    */ package com.sun.imageio.plugins.jpeg;
/*    */ 
/*    */ import java.util.ListResourceBundle;
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
/*    */ public class JPEGImageWriterResources
/*    */   extends ListResourceBundle
/*    */ {
/*    */   protected Object[][] getContents() {
/* 35 */     return new Object[][] {
/*    */         
/* 37 */         { Integer.toString(0), "Only Rasters or band subsets may be written with a destination type. Destination type ignored."
/*    */         },
/*    */         {
/* 40 */           Integer.toString(1), "Stream metadata ignored on write"
/*    */         
/* 42 */         }, { Integer.toString(2), "Metadata component ids incompatible with destination type. Metadata modified."
/*    */         },
/*    */         {
/* 45 */           Integer.toString(3), "Metadata JFIF settings incompatible with destination type. Metadata modified."
/*    */         
/*    */         },
/* 48 */         { Integer.toString(4), "Metadata Adobe settings incompatible with destination type. Metadata modified."
/*    */         },
/*    */         {
/* 51 */           Integer.toString(5), "Metadata JFIF settings incompatible with image type. Metadata modified."
/*    */         
/*    */         },
/* 54 */         { Integer.toString(6), "Metadata Adobe settings incompatible with image type. Metadata modified."
/*    */         },
/*    */         {
/* 57 */           Integer.toString(7), "Metadata must be JPEGMetadata when writing a Raster. Metadata ignored."
/*    */         
/*    */         },
/* 60 */         { Integer.toString(8), "Band subset not allowed for an IndexColorModel image.  Band subset ignored."
/*    */         },
/*    */         {
/* 63 */           Integer.toString(9), "Thumbnails must be simple (possibly index) RGB or grayscale.  Incompatible thumbnail ignored."
/*    */         
/*    */         }, 
/* 66 */         { Integer.toString(10), "Thumbnails ignored for non-JFIF-compatible image."
/*    */         },
/* 68 */         { Integer.toString(11), "Thumbnails require JFIF marker segment.  Missing node added to metadata."
/*    */         },
/*    */         {
/* 71 */           Integer.toString(12), "Thumbnail clipped."
/*    */         
/* 73 */         }, { Integer.toString(13), "Metadata adjusted (made JFIF-compatible) for thumbnail."
/*    */         },
/* 75 */         { Integer.toString(14), "RGB thumbnail can't be written as indexed.  Written as RGB"
/*    */         },
/* 77 */         { Integer.toString(15), "Grayscale thumbnail can't be written as indexed.  Written as JPEG" }
/*    */       };
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/imageio/plugins/jpeg/JPEGImageWriterResources.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */