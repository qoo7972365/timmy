/*    */ package com.sun.org.apache.xml.internal.security.utils;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.io.OutputStream;
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
/*    */ public class UnsyncBufferedOutputStream
/*    */   extends OutputStream
/*    */ {
/*    */   static final int size = 8192;
/* 35 */   private int pointer = 0;
/*    */ 
/*    */   
/*    */   private final OutputStream out;
/*    */ 
/*    */   
/*    */   private final byte[] buf;
/*    */ 
/*    */   
/*    */   public UnsyncBufferedOutputStream(OutputStream paramOutputStream) {
/* 45 */     this.buf = new byte[8192];
/* 46 */     this.out = paramOutputStream;
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(byte[] paramArrayOfbyte) throws IOException {
/* 51 */     write(paramArrayOfbyte, 0, paramArrayOfbyte.length);
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws IOException {
/* 56 */     int i = this.pointer + paramInt2;
/* 57 */     if (i > 8192) {
/* 58 */       flushBuffer();
/* 59 */       if (paramInt2 > 8192) {
/* 60 */         this.out.write(paramArrayOfbyte, paramInt1, paramInt2);
/*    */         return;
/*    */       } 
/* 63 */       i = paramInt2;
/*    */     } 
/* 65 */     System.arraycopy(paramArrayOfbyte, paramInt1, this.buf, this.pointer, paramInt2);
/* 66 */     this.pointer = i;
/*    */   }
/*    */   
/*    */   private void flushBuffer() throws IOException {
/* 70 */     if (this.pointer > 0) {
/* 71 */       this.out.write(this.buf, 0, this.pointer);
/*    */     }
/* 73 */     this.pointer = 0;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void write(int paramInt) throws IOException {
/* 79 */     if (this.pointer >= 8192) {
/* 80 */       flushBuffer();
/*    */     }
/* 82 */     this.buf[this.pointer++] = (byte)paramInt;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void flush() throws IOException {
/* 88 */     flushBuffer();
/* 89 */     this.out.flush();
/*    */   }
/*    */ 
/*    */   
/*    */   public void close() throws IOException {
/* 94 */     flush();
/* 95 */     this.out.close();
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xml/internal/security/utils/UnsyncBufferedOutputStream.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */