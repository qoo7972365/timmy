/*    */ package com.sun.imageio.plugins.bmp;
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
/*    */ public class BMPCompressionTypes
/*    */ {
/* 30 */   private static final String[] compressionTypeNames = new String[] { "BI_RGB", "BI_RLE8", "BI_RLE4", "BI_BITFIELDS", "BI_JPEG", "BI_PNG" };
/*    */ 
/*    */   
/*    */   static int getType(String paramString) {
/* 34 */     for (byte b = 0; b < compressionTypeNames.length; b++) {
/* 35 */       if (compressionTypeNames[b].equals(paramString))
/* 36 */         return b; 
/* 37 */     }  return 0;
/*    */   }
/*    */   
/*    */   static String getName(int paramInt) {
/* 41 */     return compressionTypeNames[paramInt];
/*    */   }
/*    */   
/*    */   public static String[] getCompressionTypes() {
/* 45 */     return (String[])compressionTypeNames.clone();
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/imageio/plugins/bmp/BMPCompressionTypes.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */