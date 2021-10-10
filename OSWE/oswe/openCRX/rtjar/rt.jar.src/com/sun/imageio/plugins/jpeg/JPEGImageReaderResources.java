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
/*    */ public class JPEGImageReaderResources
/*    */   extends ListResourceBundle
/*    */ {
/*    */   protected Object[][] getContents() {
/* 35 */     return new Object[][] {
/*    */         {
/* 37 */           Integer.toString(0), "Truncated File - Missing EOI marker"
/*    */         
/* 39 */         }, { Integer.toString(1), "JFIF markers not allowed in JFIF JPEG thumbnail; ignored"
/*    */         },
/* 41 */         { Integer.toString(2), "Embedded color profile is invalid; ignored" }
/*    */       };
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/imageio/plugins/jpeg/JPEGImageReaderResources.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */