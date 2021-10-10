/*     */ package com.sun.imageio.plugins.png;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import javax.imageio.stream.ImageOutputStream;
/*     */ import javax.imageio.stream.ImageOutputStreamImpl;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class ChunkStream
/*     */   extends ImageOutputStreamImpl
/*     */ {
/*     */   private ImageOutputStream stream;
/*     */   private long startPos;
/* 101 */   private CRC crc = new CRC();
/*     */   
/*     */   public ChunkStream(int paramInt, ImageOutputStream paramImageOutputStream) throws IOException {
/* 104 */     this.stream = paramImageOutputStream;
/* 105 */     this.startPos = paramImageOutputStream.getStreamPosition();
/*     */     
/* 107 */     paramImageOutputStream.writeInt(-1);
/* 108 */     writeInt(paramInt);
/*     */   }
/*     */   
/*     */   public int read() throws IOException {
/* 112 */     throw new RuntimeException("Method not available");
/*     */   }
/*     */   
/*     */   public int read(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws IOException {
/* 116 */     throw new RuntimeException("Method not available");
/*     */   }
/*     */   
/*     */   public void write(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws IOException {
/* 120 */     this.crc.update(paramArrayOfbyte, paramInt1, paramInt2);
/* 121 */     this.stream.write(paramArrayOfbyte, paramInt1, paramInt2);
/*     */   }
/*     */   
/*     */   public void write(int paramInt) throws IOException {
/* 125 */     this.crc.update(paramInt);
/* 126 */     this.stream.write(paramInt);
/*     */   }
/*     */ 
/*     */   
/*     */   public void finish() throws IOException {
/* 131 */     this.stream.writeInt(this.crc.getValue());
/*     */ 
/*     */     
/* 134 */     long l = this.stream.getStreamPosition();
/* 135 */     this.stream.seek(this.startPos);
/* 136 */     this.stream.writeInt((int)(l - this.startPos) - 12);
/*     */ 
/*     */     
/* 139 */     this.stream.seek(l);
/* 140 */     this.stream.flushBefore(l);
/*     */   }
/*     */   
/*     */   protected void finalize() throws Throwable {}
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/imageio/plugins/png/ChunkStream.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */