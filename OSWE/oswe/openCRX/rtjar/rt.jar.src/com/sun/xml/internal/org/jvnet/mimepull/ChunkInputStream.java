/*    */ package com.sun.xml.internal.org.jvnet.mimepull;
/*    */ 
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
/*    */ final class ChunkInputStream
/*    */   extends InputStream
/*    */ {
/*    */   Chunk current;
/*    */   int offset;
/*    */   int len;
/*    */   final MIMEMessage msg;
/*    */   final MIMEPart part;
/*    */   byte[] buf;
/*    */   
/*    */   public ChunkInputStream(MIMEMessage msg, MIMEPart part, Chunk startPos) {
/* 46 */     this.current = startPos;
/* 47 */     this.len = this.current.data.size();
/* 48 */     this.buf = this.current.data.read();
/* 49 */     this.msg = msg;
/* 50 */     this.part = part;
/*    */   }
/*    */ 
/*    */   
/*    */   public int read(byte[] b, int off, int sz) throws IOException {
/* 55 */     if (!fetch()) {
/* 56 */       return -1;
/*    */     }
/*    */     
/* 59 */     sz = Math.min(sz, this.len - this.offset);
/* 60 */     System.arraycopy(this.buf, this.offset, b, off, sz);
/* 61 */     return sz;
/*    */   }
/*    */   
/*    */   public int read() throws IOException {
/* 65 */     if (!fetch()) {
/* 66 */       return -1;
/*    */     }
/* 68 */     return this.buf[this.offset++] & 0xFF;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private boolean fetch() {
/* 76 */     if (this.current == null) {
/* 77 */       throw new IllegalStateException("Stream already closed");
/*    */     }
/* 79 */     while (this.offset == this.len) {
/* 80 */       while (!this.part.parsed && this.current.next == null) {
/* 81 */         this.msg.makeProgress();
/*    */       }
/* 83 */       this.current = this.current.next;
/*    */       
/* 85 */       if (this.current == null) {
/* 86 */         return false;
/*    */       }
/* 88 */       this.offset = 0;
/* 89 */       this.buf = this.current.data.read();
/* 90 */       this.len = this.current.data.size();
/*    */     } 
/* 92 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public void close() throws IOException {
/* 97 */     super.close();
/* 98 */     this.current = null;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/org/jvnet/mimepull/ChunkInputStream.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */