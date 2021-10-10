/*     */ package com.sun.xml.internal.ws.util;
/*     */ 
/*     */ import com.sun.istack.internal.NotNull;
/*     */ import com.sun.istack.internal.Nullable;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
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
/*     */ public class ReadAllStream
/*     */   extends InputStream
/*     */ {
/*  53 */   private static final Logger LOGGER = Logger.getLogger(ReadAllStream.class.getName());
/*     */   
/*     */   @NotNull
/*  56 */   private final MemoryStream memStream = new MemoryStream(); @NotNull
/*  57 */   private final FileStream fileStream = new FileStream();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean readAll;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean closed;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readAll(InputStream in, long inMemory) throws IOException {
/*  73 */     assert !this.readAll;
/*  74 */     this.readAll = true;
/*     */     
/*  76 */     boolean eof = this.memStream.readAll(in, inMemory);
/*  77 */     if (!eof) {
/*  78 */       this.fileStream.readAll(in);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public int read() throws IOException {
/*  84 */     int ch = this.memStream.read();
/*  85 */     if (ch == -1) {
/*  86 */       ch = this.fileStream.read();
/*     */     }
/*  88 */     return ch;
/*     */   }
/*     */ 
/*     */   
/*     */   public int read(byte[] b, int off, int sz) throws IOException {
/*  93 */     int len = this.memStream.read(b, off, sz);
/*  94 */     if (len == -1) {
/*  95 */       len = this.fileStream.read(b, off, sz);
/*     */     }
/*  97 */     return len;
/*     */   }
/*     */ 
/*     */   
/*     */   public void close() throws IOException {
/* 102 */     if (!this.closed) {
/* 103 */       this.memStream.close();
/* 104 */       this.fileStream.close();
/* 105 */       this.closed = true;
/*     */     } 
/*     */   }
/*     */   
/*     */   private static class FileStream
/*     */     extends InputStream {
/*     */     @Nullable
/*     */     private File tempFile;
/*     */     
/*     */     void readAll(InputStream in) throws IOException {
/* 115 */       this.tempFile = File.createTempFile("jaxws", ".bin");
/* 116 */       FileOutputStream fileOut = new FileOutputStream(this.tempFile);
/*     */       try {
/* 118 */         byte[] buf = new byte[8192];
/*     */         int len;
/* 120 */         while ((len = in.read(buf)) != -1) {
/* 121 */           fileOut.write(buf, 0, len);
/*     */         }
/*     */       } finally {
/* 124 */         fileOut.close();
/*     */       } 
/* 126 */       this.fin = new FileInputStream(this.tempFile);
/*     */     } @Nullable
/*     */     private FileInputStream fin;
/*     */     private FileStream() {}
/*     */     public int read() throws IOException {
/* 131 */       return (this.fin != null) ? this.fin.read() : -1;
/*     */     }
/*     */ 
/*     */     
/*     */     public int read(byte[] b, int off, int sz) throws IOException {
/* 136 */       return (this.fin != null) ? this.fin.read(b, off, sz) : -1;
/*     */     }
/*     */ 
/*     */     
/*     */     public void close() throws IOException {
/* 141 */       if (this.fin != null) {
/* 142 */         this.fin.close();
/*     */       }
/* 144 */       if (this.tempFile != null) {
/* 145 */         boolean success = this.tempFile.delete();
/* 146 */         if (!success)
/* 147 */           ReadAllStream.LOGGER.log(Level.INFO, "File {0} could not be deleted", this.tempFile); 
/*     */       } 
/*     */     } }
/*     */   
/*     */   private static class MemoryStream extends InputStream {
/*     */     private Chunk head;
/*     */     private Chunk tail;
/*     */     private int curOff;
/*     */     
/*     */     private MemoryStream() {}
/*     */     
/*     */     private void add(byte[] buf, int len) {
/* 159 */       if (this.tail != null) {
/* 160 */         this.tail = this.tail.createNext(buf, 0, len);
/*     */       } else {
/* 162 */         this.head = this.tail = new Chunk(buf, 0, len);
/*     */       } 
/*     */     }
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
/*     */     boolean readAll(InputStream in, long inMemory) throws IOException {
/* 176 */       long total = 0L;
/*     */       while (true) {
/* 178 */         byte[] buf = new byte[8192];
/* 179 */         int read = fill(in, buf);
/* 180 */         total += read;
/* 181 */         if (read != 0) {
/* 182 */           add(buf, read);
/*     */         }
/* 184 */         if (read != buf.length) {
/* 185 */           return true;
/*     */         }
/* 187 */         if (total > inMemory) {
/* 188 */           return false;
/*     */         }
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     private int fill(InputStream in, byte[] buf) throws IOException {
/* 195 */       int total = 0; int read;
/* 196 */       while (total < buf.length && (read = in.read(buf, total, buf.length - total)) != -1) {
/* 197 */         total += read;
/*     */       }
/* 199 */       return total;
/*     */     }
/*     */ 
/*     */     
/*     */     public int read() throws IOException {
/* 204 */       if (!fetch()) {
/* 205 */         return -1;
/*     */       }
/* 207 */       return this.head.buf[this.curOff++] & 0xFF;
/*     */     }
/*     */ 
/*     */     
/*     */     public int read(byte[] b, int off, int sz) throws IOException {
/* 212 */       if (!fetch()) {
/* 213 */         return -1;
/*     */       }
/* 215 */       sz = Math.min(sz, this.head.len - this.curOff - this.head.off);
/* 216 */       System.arraycopy(this.head.buf, this.curOff, b, off, sz);
/* 217 */       this.curOff += sz;
/* 218 */       return sz;
/*     */     }
/*     */ 
/*     */     
/*     */     private boolean fetch() {
/* 223 */       if (this.head == null) {
/* 224 */         return false;
/*     */       }
/* 226 */       if (this.curOff == this.head.off + this.head.len) {
/* 227 */         this.head = this.head.next;
/* 228 */         if (this.head == null) {
/* 229 */           return false;
/*     */         }
/* 231 */         this.curOff = this.head.off;
/*     */       } 
/* 233 */       return true;
/*     */     }
/*     */     
/*     */     private static final class Chunk {
/*     */       Chunk next;
/*     */       final byte[] buf;
/*     */       final int off;
/*     */       final int len;
/*     */       
/*     */       public Chunk(byte[] buf, int off, int len) {
/* 243 */         this.buf = buf;
/* 244 */         this.off = off;
/* 245 */         this.len = len;
/*     */       }
/*     */       
/*     */       public Chunk createNext(byte[] buf, int off, int len) {
/* 249 */         return this.next = new Chunk(buf, off, len);
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/util/ReadAllStream.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */