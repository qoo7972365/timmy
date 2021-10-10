/*    */ package com.sun.imageio.plugins.common;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
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
/*    */ public class InputStreamAdapter
/*    */   extends InputStream
/*    */ {
/*    */   ImageInputStream stream;
/*    */   
/*    */   public InputStreamAdapter(ImageInputStream paramImageInputStream) {
/* 39 */     this.stream = paramImageInputStream;
/*    */   }
/*    */   
/*    */   public int read() throws IOException {
/* 43 */     return this.stream.read();
/*    */   }
/*    */   
/*    */   public int read(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws IOException {
/* 47 */     return this.stream.read(paramArrayOfbyte, paramInt1, paramInt2);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/imageio/plugins/common/InputStreamAdapter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */