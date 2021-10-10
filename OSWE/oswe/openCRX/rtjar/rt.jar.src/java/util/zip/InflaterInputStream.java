/*     */ package java.util.zip;
/*     */ 
/*     */ import java.io.EOFException;
/*     */ import java.io.FilterInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
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
/*     */ public class InflaterInputStream
/*     */   extends FilterInputStream
/*     */ {
/*     */   protected Inflater inf;
/*     */   protected byte[] buf;
/*     */   protected int len;
/*     */   private boolean closed = false;
/*     */   private boolean reachEOF = false;
/*     */   boolean usesDefaultInflater;
/*     */   private byte[] singleByteBuf;
/*     */   private byte[] b;
/*     */   
/*     */   private void ensureOpen() throws IOException {
/*  66 */     if (this.closed) {
/*  67 */       throw new IOException("Stream closed");
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
/*     */   public InflaterInputStream(InputStream paramInputStream, Inflater paramInflater, int paramInt) {
/*  81 */     super(paramInputStream);
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
/* 101 */     this.usesDefaultInflater = false;
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
/* 112 */     this.singleByteBuf = new byte[1];
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
/* 187 */     this.b = new byte[512];
/*     */     if (paramInputStream == null || paramInflater == null)
/*     */       throw new NullPointerException(); 
/*     */     if (paramInt <= 0)
/*     */       throw new IllegalArgumentException("buffer size <= 0"); 
/*     */     this.inf = paramInflater;
/*     */     this.buf = new byte[paramInt];
/*     */   } public InflaterInputStream(InputStream paramInputStream, Inflater paramInflater) { this(paramInputStream, paramInflater, 512); }
/*     */   public InflaterInputStream(InputStream paramInputStream) { this(paramInputStream, new Inflater());
/*     */     this.usesDefaultInflater = true; }
/* 197 */   public long skip(long paramLong) throws IOException { if (paramLong < 0L) {
/* 198 */       throw new IllegalArgumentException("negative skip length");
/*     */     }
/* 200 */     ensureOpen();
/* 201 */     int i = (int)Math.min(paramLong, 2147483647L);
/* 202 */     int j = 0;
/* 203 */     while (j < i) {
/* 204 */       int k = i - j;
/* 205 */       if (k > this.b.length) {
/* 206 */         k = this.b.length;
/*     */       }
/* 208 */       k = read(this.b, 0, k);
/* 209 */       if (k == -1) {
/* 210 */         this.reachEOF = true;
/*     */         break;
/*     */       } 
/* 213 */       j += k;
/*     */     } 
/* 215 */     return j; }
/*     */ 
/*     */   
/*     */   public int read() throws IOException {
/*     */     ensureOpen();
/*     */     return (read(this.singleByteBuf, 0, 1) == -1) ? -1 : Byte.toUnsignedInt(this.singleByteBuf[0]);
/*     */   }
/*     */   
/*     */   public void close() throws IOException {
/* 224 */     if (!this.closed) {
/* 225 */       if (this.usesDefaultInflater)
/* 226 */         this.inf.end(); 
/* 227 */       this.in.close();
/* 228 */       this.closed = true;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void fill() throws IOException {
/* 237 */     ensureOpen();
/* 238 */     this.len = this.in.read(this.buf, 0, this.buf.length);
/* 239 */     if (this.len == -1) {
/* 240 */       throw new EOFException("Unexpected end of ZLIB input stream");
/*     */     }
/* 242 */     this.inf.setInput(this.buf, 0, this.len);
/*     */   }
/*     */   public int read(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws IOException { ensureOpen(); if (paramArrayOfbyte == null)
/*     */       throw new NullPointerException();  if (paramInt1 < 0 || paramInt2 < 0 || paramInt2 > paramArrayOfbyte.length - paramInt1)
/*     */       throw new IndexOutOfBoundsException();  if (paramInt2 == 0)
/*     */       return 0;  try {
/*     */       int i; while ((i = this.inf.inflate(paramArrayOfbyte, paramInt1, paramInt2)) == 0) {
/*     */         if (this.inf.finished() || this.inf.needsDictionary()) {
/*     */           this.reachEOF = true; return -1;
/*     */         }  if (this.inf.needsInput())
/*     */           fill(); 
/*     */       }  return i;
/*     */     } catch (DataFormatException dataFormatException) {
/*     */       String str = dataFormatException.getMessage(); throw new ZipException((str != null) ? str : "Invalid ZLIB data format");
/*     */     }  } public int available() throws IOException { ensureOpen(); if (this.reachEOF)
/* 257 */       return 0;  return 1; } public boolean markSupported() { return false; }
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
/*     */   public synchronized void mark(int paramInt) {}
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
/*     */   public synchronized void reset() throws IOException {
/* 286 */     throw new IOException("mark/reset not supported");
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/util/zip/InflaterInputStream.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */