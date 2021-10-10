/*    */ package com.sun.imageio.plugins.common;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import javax.imageio.stream.ImageInputStream;
/*    */ import javax.imageio.stream.ImageInputStreamImpl;
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
/*    */ public final class SubImageInputStream
/*    */   extends ImageInputStreamImpl
/*    */ {
/*    */   ImageInputStream stream;
/*    */   long startingPos;
/*    */   int startingLength;
/*    */   int length;
/*    */   
/*    */   public SubImageInputStream(ImageInputStream paramImageInputStream, int paramInt) throws IOException {
/* 41 */     this.stream = paramImageInputStream;
/* 42 */     this.startingPos = paramImageInputStream.getStreamPosition();
/* 43 */     this.startingLength = this.length = paramInt;
/*    */   }
/*    */   
/*    */   public int read() throws IOException {
/* 47 */     if (this.length == 0) {
/* 48 */       return -1;
/*    */     }
/* 50 */     this.length--;
/* 51 */     return this.stream.read();
/*    */   }
/*    */ 
/*    */   
/*    */   public int read(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws IOException {
/* 56 */     if (this.length == 0) {
/* 57 */       return -1;
/*    */     }
/*    */     
/* 60 */     paramInt2 = Math.min(paramInt2, this.length);
/* 61 */     int i = this.stream.read(paramArrayOfbyte, paramInt1, paramInt2);
/* 62 */     this.length -= i;
/* 63 */     return i;
/*    */   }
/*    */   
/*    */   public long length() {
/* 67 */     return this.startingLength;
/*    */   }
/*    */   
/*    */   public void seek(long paramLong) throws IOException {
/* 71 */     this.stream.seek(paramLong - this.startingPos);
/* 72 */     this.streamPos = paramLong;
/*    */   }
/*    */   
/*    */   protected void finalize() throws Throwable {}
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/imageio/plugins/common/SubImageInputStream.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */