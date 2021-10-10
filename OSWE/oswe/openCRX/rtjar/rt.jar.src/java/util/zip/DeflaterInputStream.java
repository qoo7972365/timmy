/*     */ package java.util.zip;
/*     */ 
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
/*     */ public class DeflaterInputStream
/*     */   extends FilterInputStream
/*     */ {
/*     */   protected final Deflater def;
/*     */   protected final byte[] buf;
/*  52 */   private byte[] rbuf = new byte[1];
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean usesDefaultDeflater = false;
/*     */ 
/*     */   
/*     */   private boolean reachEOF = false;
/*     */ 
/*     */ 
/*     */   
/*     */   private void ensureOpen() throws IOException {
/*  64 */     if (this.in == null) {
/*  65 */       throw new IOException("Stream closed");
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
/*     */   public DeflaterInputStream(InputStream paramInputStream) {
/*  77 */     this(paramInputStream, new Deflater());
/*  78 */     this.usesDefaultDeflater = true;
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
/*     */   public DeflaterInputStream(InputStream paramInputStream, Deflater paramDeflater) {
/*  90 */     this(paramInputStream, paramDeflater, 512);
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
/*     */   public DeflaterInputStream(InputStream paramInputStream, Deflater paramDeflater, int paramInt) {
/* 104 */     super(paramInputStream);
/*     */ 
/*     */     
/* 107 */     if (paramInputStream == null)
/* 108 */       throw new NullPointerException("Null input"); 
/* 109 */     if (paramDeflater == null)
/* 110 */       throw new NullPointerException("Null deflater"); 
/* 111 */     if (paramInt < 1) {
/* 112 */       throw new IllegalArgumentException("Buffer size < 1");
/*     */     }
/*     */     
/* 115 */     this.def = paramDeflater;
/* 116 */     this.buf = new byte[paramInt];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void close() throws IOException {
/* 126 */     if (this.in != null) {
/*     */       
/*     */       try {
/* 129 */         if (this.usesDefaultDeflater) {
/* 130 */           this.def.end();
/*     */         }
/*     */         
/* 133 */         this.in.close();
/*     */       } finally {
/* 135 */         this.in = null;
/*     */       } 
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
/*     */   public int read() throws IOException {
/* 151 */     int i = read(this.rbuf, 0, 1);
/* 152 */     if (i <= 0)
/* 153 */       return -1; 
/* 154 */     return this.rbuf[0] & 0xFF;
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
/*     */   public int read(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws IOException {
/* 172 */     ensureOpen();
/* 173 */     if (paramArrayOfbyte == null)
/* 174 */       throw new NullPointerException("Null buffer for read"); 
/* 175 */     if (paramInt1 < 0 || paramInt2 < 0 || paramInt2 > paramArrayOfbyte.length - paramInt1)
/* 176 */       throw new IndexOutOfBoundsException(); 
/* 177 */     if (paramInt2 == 0) {
/* 178 */       return 0;
/*     */     }
/*     */ 
/*     */     
/* 182 */     int i = 0;
/* 183 */     while (paramInt2 > 0 && !this.def.finished()) {
/*     */ 
/*     */ 
/*     */       
/* 187 */       if (this.def.needsInput()) {
/* 188 */         int k = this.in.read(this.buf, 0, this.buf.length);
/* 189 */         if (k < 0) {
/*     */           
/* 191 */           this.def.finish();
/* 192 */         } else if (k > 0) {
/* 193 */           this.def.setInput(this.buf, 0, k);
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 198 */       int j = this.def.deflate(paramArrayOfbyte, paramInt1, paramInt2);
/* 199 */       i += j;
/* 200 */       paramInt1 += j;
/* 201 */       paramInt2 -= j;
/*     */     } 
/* 203 */     if (i == 0 && this.def.finished()) {
/* 204 */       this.reachEOF = true;
/* 205 */       i = -1;
/*     */     } 
/*     */     
/* 208 */     return i;
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
/*     */   public long skip(long paramLong) throws IOException {
/* 224 */     if (paramLong < 0L) {
/* 225 */       throw new IllegalArgumentException("negative skip length");
/*     */     }
/* 227 */     ensureOpen();
/*     */ 
/*     */     
/* 230 */     if (this.rbuf.length < 512) {
/* 231 */       this.rbuf = new byte[512];
/*     */     }
/* 233 */     int i = (int)Math.min(paramLong, 2147483647L);
/* 234 */     long l = 0L;
/* 235 */     while (i > 0) {
/*     */       
/* 237 */       int j = read(this.rbuf, 0, (i <= this.rbuf.length) ? i : this.rbuf.length);
/*     */       
/* 239 */       if (j < 0) {
/*     */         break;
/*     */       }
/* 242 */       l += j;
/* 243 */       i -= j;
/*     */     } 
/* 245 */     return l;
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
/*     */   public int available() throws IOException {
/* 259 */     ensureOpen();
/* 260 */     if (this.reachEOF) {
/* 261 */       return 0;
/*     */     }
/* 263 */     return 1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean markSupported() {
/* 273 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void mark(int paramInt) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void reset() throws IOException {
/* 291 */     throw new IOException("mark/reset not supported");
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/util/zip/DeflaterInputStream.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */