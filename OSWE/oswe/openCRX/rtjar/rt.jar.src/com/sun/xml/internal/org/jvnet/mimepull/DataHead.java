/*     */ package com.sun.xml.internal.org.jvnet.mimepull;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.nio.ByteBuffer;
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
/*     */ final class DataHead
/*     */ {
/*     */   volatile Chunk head;
/*     */   volatile Chunk tail;
/*     */   DataFile dataFile;
/*     */   private final MIMEPart part;
/*     */   boolean readOnce;
/*     */   volatile long inMemory;
/*     */   private Throwable consumedAt;
/*     */   
/*     */   DataHead(MIMEPart part) {
/*  64 */     this.part = part;
/*     */   }
/*     */   
/*     */   void addBody(ByteBuffer buf) {
/*  68 */     synchronized (this) {
/*  69 */       this.inMemory += buf.limit();
/*     */     } 
/*  71 */     if (this.tail != null) {
/*  72 */       this.tail = this.tail.createNext(this, buf);
/*     */     } else {
/*  74 */       this.head = this.tail = new Chunk(new MemoryData(buf, this.part.msg.config));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   void doneParsing() {}
/*     */   
/*     */   void moveTo(File f) {
/*  82 */     if (this.dataFile != null) {
/*  83 */       this.dataFile.renameTo(f);
/*     */     } else {
/*     */       try {
/*  86 */         OutputStream os = new FileOutputStream(f);
/*     */         try {
/*  88 */           InputStream in = readOnce();
/*  89 */           byte[] buf = new byte[8192];
/*     */           int len;
/*  91 */           while ((len = in.read(buf)) != -1) {
/*  92 */             os.write(buf, 0, len);
/*     */           }
/*     */         } finally {
/*  95 */           if (os != null) {
/*  96 */             os.close();
/*     */           }
/*     */         } 
/*  99 */       } catch (IOException ioe) {
/* 100 */         throw new MIMEParsingException(ioe);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   void close() {
/* 106 */     this.head = this.tail = null;
/* 107 */     if (this.dataFile != null) {
/* 108 */       this.dataFile.close();
/*     */     }
/*     */   }
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
/*     */   public InputStream read() {
/* 123 */     if (this.readOnce) {
/* 124 */       throw new IllegalStateException("readOnce() is called before, read() cannot be called later.");
/*     */     }
/*     */ 
/*     */     
/* 128 */     while (this.tail == null) {
/* 129 */       if (!this.part.msg.makeProgress()) {
/* 130 */         throw new IllegalStateException("No such MIME Part: " + this.part);
/*     */       }
/*     */     } 
/*     */     
/* 134 */     if (this.head == null) {
/* 135 */       throw new IllegalStateException("Already read. Probably readOnce() is called before.");
/*     */     }
/* 137 */     return new ReadMultiStream();
/*     */   }
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
/*     */   private boolean unconsumed() {
/* 151 */     if (this.consumedAt != null) {
/* 152 */       AssertionError error = new AssertionError("readOnce() is already called before. See the nested exception from where it's called.");
/* 153 */       error.initCause(this.consumedAt);
/* 154 */       throw error;
/*     */     } 
/* 156 */     this.consumedAt = (new Exception()).fillInStackTrace();
/* 157 */     return true;
/*     */   }
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
/*     */   public InputStream readOnce() {
/* 173 */     assert unconsumed();
/* 174 */     if (this.readOnce) {
/* 175 */       throw new IllegalStateException("readOnce() is called before. It can only be called once.");
/*     */     }
/* 177 */     this.readOnce = true;
/*     */     
/* 179 */     while (this.tail == null) {
/* 180 */       if (!this.part.msg.makeProgress() && this.tail == null) {
/* 181 */         throw new IllegalStateException("No such Part: " + this.part);
/*     */       }
/*     */     } 
/* 184 */     InputStream in = new ReadOnceStream();
/* 185 */     this.head = null;
/* 186 */     return in;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   class ReadMultiStream
/*     */     extends InputStream
/*     */   {
/* 197 */     Chunk current = DataHead.this.head;
/* 198 */     int len = this.current.data.size(); int offset;
/* 199 */     byte[] buf = this.current.data.read();
/*     */     
/*     */     boolean closed;
/*     */     
/*     */     public int read(byte[] b, int off, int sz) throws IOException {
/* 204 */       if (!fetch()) {
/* 205 */         return -1;
/*     */       }
/*     */       
/* 208 */       sz = Math.min(sz, this.len - this.offset);
/* 209 */       System.arraycopy(this.buf, this.offset, b, off, sz);
/* 210 */       this.offset += sz;
/* 211 */       return sz;
/*     */     }
/*     */ 
/*     */     
/*     */     public int read() throws IOException {
/* 216 */       if (!fetch()) {
/* 217 */         return -1;
/*     */       }
/* 219 */       return this.buf[this.offset++] & 0xFF;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     void adjustInMemoryUsage() {}
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private boolean fetch() throws IOException {
/* 232 */       if (this.closed) {
/* 233 */         throw new IOException("Stream already closed");
/*     */       }
/* 235 */       if (this.current == null) {
/* 236 */         return false;
/*     */       }
/*     */       
/* 239 */       while (this.offset == this.len) {
/* 240 */         while (!DataHead.this.part.parsed && this.current.next == null) {
/* 241 */           DataHead.this.part.msg.makeProgress();
/*     */         }
/* 243 */         this.current = this.current.next;
/*     */         
/* 245 */         if (this.current == null) {
/* 246 */           return false;
/*     */         }
/* 248 */         adjustInMemoryUsage();
/* 249 */         this.offset = 0;
/* 250 */         this.buf = this.current.data.read();
/* 251 */         this.len = this.current.data.size();
/*     */       } 
/* 253 */       return true;
/*     */     }
/*     */ 
/*     */     
/*     */     public void close() throws IOException {
/* 258 */       super.close();
/* 259 */       this.current = null;
/* 260 */       this.closed = true;
/*     */     }
/*     */   }
/*     */   
/*     */   final class ReadOnceStream
/*     */     extends ReadMultiStream
/*     */   {
/*     */     void adjustInMemoryUsage() {
/* 268 */       synchronized (DataHead.this) {
/* 269 */         DataHead.this.inMemory -= this.current.data.size();
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/org/jvnet/mimepull/DataHead.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */