/*     */ package java.util.zip;
/*     */ 
/*     */ import java.io.EOFException;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.PushbackInputStream;
/*     */ import java.nio.charset.Charset;
/*     */ import java.nio.charset.StandardCharsets;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ZipInputStream
/*     */   extends InflaterInputStream
/*     */   implements ZipConstants
/*     */ {
/*     */   private ZipEntry entry;
/*     */   private int flag;
/*  48 */   private CRC32 crc = new CRC32();
/*     */   private long remaining;
/*  50 */   private byte[] tmpbuf = new byte[512];
/*     */   
/*     */   private static final int STORED = 0;
/*     */   
/*     */   private static final int DEFLATED = 8;
/*     */   
/*     */   private boolean closed = false;
/*     */   
/*     */   private boolean entryEOF = false;
/*     */   
/*     */   private ZipCoder zc;
/*     */   
/*     */   private byte[] b;
/*     */ 
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
/*     */   public ZipInputStream(InputStream paramInputStream) {
/*  80 */     this(paramInputStream, StandardCharsets.UTF_8);
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
/*     */ 
/*     */   
/*     */   public ZipInputStream(InputStream paramInputStream, Charset paramCharset) {
/*  98 */     super(new PushbackInputStream(paramInputStream, 512), new Inflater(true), 512);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 271 */     this.b = new byte[256]; this.usesDefaultInflater = true; if (paramInputStream == null) throw new NullPointerException("in is null");  if (paramCharset == null) throw new NullPointerException("charset is null");  this.zc = ZipCoder.get(paramCharset);
/*     */   } public ZipEntry getNextEntry() throws IOException { ensureOpen(); if (this.entry != null)
/*     */       closeEntry();  this.crc.reset(); this.inf.reset(); if ((this.entry = readLOC()) == null)
/*     */       return null;  if (this.entry.method == 0)
/*     */       this.remaining = this.entry.size;  this.entryEOF = false; return this.entry; } public void closeEntry() throws IOException { ensureOpen(); while (read(this.tmpbuf, 0, this.tmpbuf.length) != -1); this.entryEOF = true; }
/*     */   public int available() throws IOException { ensureOpen(); if (this.entryEOF)
/*     */       return 0;  return 1; }
/* 278 */   private ZipEntry readLOC() throws IOException { try { readFully(this.tmpbuf, 0, 30); }
/* 279 */     catch (EOFException eOFException)
/* 280 */     { return null; }
/*     */     
/* 282 */     if (ZipUtils.get32(this.tmpbuf, 0) != 67324752L) {
/* 283 */       return null;
/*     */     }
/*     */     
/* 286 */     this.flag = ZipUtils.get16(this.tmpbuf, 6);
/*     */     
/* 288 */     int i = ZipUtils.get16(this.tmpbuf, 26);
/* 289 */     int j = this.b.length;
/* 290 */     if (i > j)
/*     */       while (true) {
/* 292 */         j *= 2;
/* 293 */         if (i <= j) {
/* 294 */           this.b = new byte[j]; break;
/*     */         } 
/* 296 */       }   readFully(this.b, 0, i);
/*     */     
/* 298 */     ZipEntry zipEntry = createZipEntry(((this.flag & 0x800) != 0) ? this.zc
/* 299 */         .toStringUTF8(this.b, i) : this.zc
/* 300 */         .toString(this.b, i));
/*     */     
/* 302 */     if ((this.flag & 0x1) == 1) {
/* 303 */       throw new ZipException("encrypted ZIP entry not supported");
/*     */     }
/* 305 */     zipEntry.method = ZipUtils.get16(this.tmpbuf, 8);
/* 306 */     zipEntry.xdostime = ZipUtils.get32(this.tmpbuf, 10);
/* 307 */     if ((this.flag & 0x8) == 8) {
/*     */       
/* 309 */       if (zipEntry.method != 8) {
/* 310 */         throw new ZipException("only DEFLATED entries can have EXT descriptor");
/*     */       }
/*     */     } else {
/*     */       
/* 314 */       zipEntry.crc = ZipUtils.get32(this.tmpbuf, 14);
/* 315 */       zipEntry.csize = ZipUtils.get32(this.tmpbuf, 18);
/* 316 */       zipEntry.size = ZipUtils.get32(this.tmpbuf, 22);
/*     */     } 
/* 318 */     i = ZipUtils.get16(this.tmpbuf, 28);
/* 319 */     if (i > 0) {
/* 320 */       byte[] arrayOfByte = new byte[i];
/* 321 */       readFully(arrayOfByte, 0, i);
/* 322 */       zipEntry.setExtra0(arrayOfByte, (zipEntry.csize == 4294967295L || zipEntry.size == 4294967295L));
/*     */     } 
/*     */     
/* 325 */     return zipEntry; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected ZipEntry createZipEntry(String paramString) {
/* 336 */     return new ZipEntry(paramString);
/*     */   }
/*     */   public int read(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws IOException { ensureOpen(); if (paramInt1 < 0 || paramInt2 < 0 || paramInt1 > paramArrayOfbyte.length - paramInt2) throw new IndexOutOfBoundsException();  if (paramInt2 == 0) return 0;  if (this.entry == null) return -1;  switch (this.entry.method) { case 8: paramInt2 = super.read(paramArrayOfbyte, paramInt1, paramInt2); if (paramInt2 == -1) { readEnd(this.entry); this.entryEOF = true; this.entry = null; } else { this.crc.update(paramArrayOfbyte, paramInt1, paramInt2); }  return paramInt2;case 0: if (this.remaining <= 0L) { this.entryEOF = true; this.entry = null; return -1; }  if (paramInt2 > this.remaining) paramInt2 = (int)this.remaining;  paramInt2 = this.in.read(paramArrayOfbyte, paramInt1, paramInt2); if (paramInt2 == -1)
/*     */           throw new ZipException("unexpected EOF");  this.crc.update(paramArrayOfbyte, paramInt1, paramInt2); this.remaining -= paramInt2; if (this.remaining == 0L && this.entry.crc != this.crc.getValue())
/*     */           throw new ZipException("invalid entry CRC (expected 0x" + Long.toHexString(this.entry.crc) + " but got 0x" + Long.toHexString(this.crc.getValue()) + ")");  return paramInt2; }  throw new ZipException("invalid compression method"); }
/*     */   public long skip(long paramLong) throws IOException { if (paramLong < 0L)
/*     */       throw new IllegalArgumentException("negative skip length");  ensureOpen(); int i = (int)Math.min(paramLong, 2147483647L); int j = 0; while (j < i) { int k = i - j; if (k > this.tmpbuf.length)
/* 343 */         k = this.tmpbuf.length;  k = read(this.tmpbuf, 0, k); if (k == -1) { this.entryEOF = true; break; }  j += k; }  return j; } public void close() throws IOException { if (!this.closed) { super.close(); this.closed = true; }  } private void readEnd(ZipEntry paramZipEntry) throws IOException { int i = this.inf.getRemaining();
/* 344 */     if (i > 0) {
/* 345 */       ((PushbackInputStream)this.in).unread(this.buf, this.len - i, i);
/*     */     }
/* 347 */     if ((this.flag & 0x8) == 8)
/*     */     {
/* 349 */       if (this.inf.getBytesWritten() > 4294967295L || this.inf
/* 350 */         .getBytesRead() > 4294967295L) {
/*     */         
/* 352 */         readFully(this.tmpbuf, 0, 24);
/* 353 */         long l = ZipUtils.get32(this.tmpbuf, 0);
/* 354 */         if (l != 134695760L) {
/* 355 */           paramZipEntry.crc = l;
/* 356 */           paramZipEntry.csize = ZipUtils.get64(this.tmpbuf, 4);
/* 357 */           paramZipEntry.size = ZipUtils.get64(this.tmpbuf, 12);
/* 358 */           ((PushbackInputStream)this.in).unread(this.tmpbuf, 19, 4);
/*     */         } else {
/*     */           
/* 361 */           paramZipEntry.crc = ZipUtils.get32(this.tmpbuf, 4);
/* 362 */           paramZipEntry.csize = ZipUtils.get64(this.tmpbuf, 8);
/* 363 */           paramZipEntry.size = ZipUtils.get64(this.tmpbuf, 16);
/*     */         } 
/*     */       } else {
/* 366 */         readFully(this.tmpbuf, 0, 16);
/* 367 */         long l = ZipUtils.get32(this.tmpbuf, 0);
/* 368 */         if (l != 134695760L) {
/* 369 */           paramZipEntry.crc = l;
/* 370 */           paramZipEntry.csize = ZipUtils.get32(this.tmpbuf, 4);
/* 371 */           paramZipEntry.size = ZipUtils.get32(this.tmpbuf, 8);
/* 372 */           ((PushbackInputStream)this.in).unread(this.tmpbuf, 11, 4);
/*     */         } else {
/*     */           
/* 375 */           paramZipEntry.crc = ZipUtils.get32(this.tmpbuf, 4);
/* 376 */           paramZipEntry.csize = ZipUtils.get32(this.tmpbuf, 8);
/* 377 */           paramZipEntry.size = ZipUtils.get32(this.tmpbuf, 12);
/*     */         } 
/*     */       } 
/*     */     }
/* 381 */     if (paramZipEntry.size != this.inf.getBytesWritten()) {
/* 382 */       throw new ZipException("invalid entry size (expected " + paramZipEntry.size + " but got " + this.inf
/*     */           
/* 384 */           .getBytesWritten() + " bytes)");
/*     */     }
/* 386 */     if (paramZipEntry.csize != this.inf.getBytesRead()) {
/* 387 */       throw new ZipException("invalid entry compressed size (expected " + paramZipEntry.csize + " but got " + this.inf
/*     */           
/* 389 */           .getBytesRead() + " bytes)");
/*     */     }
/* 391 */     if (paramZipEntry.crc != this.crc.getValue()) {
/* 392 */       throw new ZipException("invalid entry CRC (expected 0x" + 
/* 393 */           Long.toHexString(paramZipEntry.crc) + " but got 0x" + 
/* 394 */           Long.toHexString(this.crc.getValue()) + ")");
/*     */     } }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void readFully(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws IOException {
/* 402 */     while (paramInt2 > 0) {
/* 403 */       int i = this.in.read(paramArrayOfbyte, paramInt1, paramInt2);
/* 404 */       if (i == -1) {
/* 405 */         throw new EOFException();
/*     */       }
/* 407 */       paramInt1 += i;
/* 408 */       paramInt2 -= i;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/util/zip/ZipInputStream.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */