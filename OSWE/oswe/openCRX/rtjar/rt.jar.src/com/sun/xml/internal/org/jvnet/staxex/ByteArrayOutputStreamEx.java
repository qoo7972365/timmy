/*    */ package com.sun.xml.internal.org.jvnet.staxex;
/*    */ 
/*    */ import java.io.ByteArrayOutputStream;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ final class ByteArrayOutputStreamEx
/*    */   extends ByteArrayOutputStream
/*    */ {
/*    */   public ByteArrayOutputStreamEx() {}
/*    */   
/*    */   public ByteArrayOutputStreamEx(int size) {
/* 42 */     super(size);
/*    */   }
/*    */   
/*    */   public void set(Base64Data dt, String mimeType) {
/* 46 */     dt.set(this.buf, this.count, mimeType);
/*    */   }
/*    */   
/*    */   public byte[] getBuffer() {
/* 50 */     return this.buf;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void readFrom(InputStream is) throws IOException {
/*    */     while (true) {
/* 58 */       if (this.count == this.buf.length) {
/*    */         
/* 60 */         byte[] data = new byte[this.buf.length * 2];
/* 61 */         System.arraycopy(this.buf, 0, data, 0, this.buf.length);
/* 62 */         this.buf = data;
/*    */       } 
/*    */       
/* 65 */       int sz = is.read(this.buf, this.count, this.buf.length - this.count);
/* 66 */       if (sz < 0)
/* 67 */         return;  this.count += sz;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/org/jvnet/staxex/ByteArrayOutputStreamEx.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */