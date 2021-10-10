/*     */ package java.io;
/*     */ 
/*     */ import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BufferedInputStream
/*     */   extends FilterInputStream
/*     */ {
/*  53 */   private static int DEFAULT_BUFFER_SIZE = 8192;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  61 */   private static int MAX_BUFFER_SIZE = 2147483639;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected volatile byte[] buf;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  79 */   private static final AtomicReferenceFieldUpdater<BufferedInputStream, byte[]> bufUpdater = (AtomicReferenceFieldUpdater)AtomicReferenceFieldUpdater.newUpdater(BufferedInputStream.class, (Class)byte[].class, "buf");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int count;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int pos;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 136 */   protected int markpos = -1;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int marklimit;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private InputStream getInIfOpen() throws IOException {
/* 157 */     InputStream inputStream = this.in;
/* 158 */     if (inputStream == null)
/* 159 */       throw new IOException("Stream closed"); 
/* 160 */     return inputStream;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private byte[] getBufIfOpen() throws IOException {
/* 168 */     byte[] arrayOfByte = this.buf;
/* 169 */     if (arrayOfByte == null)
/* 170 */       throw new IOException("Stream closed"); 
/* 171 */     return arrayOfByte;
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
/*     */   public BufferedInputStream(InputStream paramInputStream) {
/* 183 */     this(paramInputStream, DEFAULT_BUFFER_SIZE);
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
/*     */   public BufferedInputStream(InputStream paramInputStream, int paramInt) {
/* 199 */     super(paramInputStream);
/* 200 */     if (paramInt <= 0) {
/* 201 */       throw new IllegalArgumentException("Buffer size <= 0");
/*     */     }
/* 203 */     this.buf = new byte[paramInt];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void fill() throws IOException {
/* 214 */     byte[] arrayOfByte = getBufIfOpen();
/* 215 */     if (this.markpos < 0) {
/* 216 */       this.pos = 0;
/* 217 */     } else if (this.pos >= arrayOfByte.length) {
/* 218 */       if (this.markpos > 0)
/* 219 */       { int j = this.pos - this.markpos;
/* 220 */         System.arraycopy(arrayOfByte, this.markpos, arrayOfByte, 0, j);
/* 221 */         this.pos = j;
/* 222 */         this.markpos = 0; }
/* 223 */       else if (arrayOfByte.length >= this.marklimit)
/* 224 */       { this.markpos = -1;
/* 225 */         this.pos = 0; }
/* 226 */       else { if (arrayOfByte.length >= MAX_BUFFER_SIZE) {
/* 227 */           throw new OutOfMemoryError("Required array size too large");
/*     */         }
/* 229 */         int j = (this.pos <= MAX_BUFFER_SIZE - this.pos) ? (this.pos * 2) : MAX_BUFFER_SIZE;
/*     */         
/* 231 */         if (j > this.marklimit)
/* 232 */           j = this.marklimit; 
/* 233 */         byte[] arrayOfByte1 = new byte[j];
/* 234 */         System.arraycopy(arrayOfByte, 0, arrayOfByte1, 0, this.pos);
/* 235 */         if (!bufUpdater.compareAndSet(this, arrayOfByte, arrayOfByte1))
/*     */         {
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 241 */           throw new IOException("Stream closed");
/*     */         }
/* 243 */         arrayOfByte = arrayOfByte1; }
/*     */     
/* 245 */     }  this.count = this.pos;
/* 246 */     int i = getInIfOpen().read(arrayOfByte, this.pos, arrayOfByte.length - this.pos);
/* 247 */     if (i > 0) {
/* 248 */       this.count = i + this.pos;
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
/*     */   
/*     */   public synchronized int read() throws IOException {
/* 264 */     if (this.pos >= this.count) {
/* 265 */       fill();
/* 266 */       if (this.pos >= this.count)
/* 267 */         return -1; 
/*     */     } 
/* 269 */     return getBufIfOpen()[this.pos++] & 0xFF;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int read1(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws IOException {
/* 277 */     int i = this.count - this.pos;
/* 278 */     if (i <= 0) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 283 */       if (paramInt2 >= (getBufIfOpen()).length && this.markpos < 0) {
/* 284 */         return getInIfOpen().read(paramArrayOfbyte, paramInt1, paramInt2);
/*     */       }
/* 286 */       fill();
/* 287 */       i = this.count - this.pos;
/* 288 */       if (i <= 0) return -1; 
/*     */     } 
/* 290 */     int j = (i < paramInt2) ? i : paramInt2;
/* 291 */     System.arraycopy(getBufIfOpen(), this.pos, paramArrayOfbyte, paramInt1, j);
/* 292 */     this.pos += j;
/* 293 */     return j;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized int read(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws IOException {
/* 336 */     getBufIfOpen();
/* 337 */     if ((paramInt1 | paramInt2 | paramInt1 + paramInt2 | paramArrayOfbyte.length - paramInt1 + paramInt2) < 0)
/* 338 */       throw new IndexOutOfBoundsException(); 
/* 339 */     if (paramInt2 == 0) {
/* 340 */       return 0;
/*     */     }
/*     */     
/* 343 */     int i = 0;
/*     */     while (true) {
/* 345 */       int j = read1(paramArrayOfbyte, paramInt1 + i, paramInt2 - i);
/* 346 */       if (j <= 0)
/* 347 */         return (i == 0) ? j : i; 
/* 348 */       i += j;
/* 349 */       if (i >= paramInt2) {
/* 350 */         return i;
/*     */       }
/* 352 */       InputStream inputStream = this.in;
/* 353 */       if (inputStream != null && inputStream.available() <= 0) {
/* 354 */         return i;
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
/*     */   public synchronized long skip(long paramLong) throws IOException {
/* 368 */     getBufIfOpen();
/* 369 */     if (paramLong <= 0L) {
/* 370 */       return 0L;
/*     */     }
/* 372 */     long l1 = (this.count - this.pos);
/*     */     
/* 374 */     if (l1 <= 0L) {
/*     */       
/* 376 */       if (this.markpos < 0) {
/* 377 */         return getInIfOpen().skip(paramLong);
/*     */       }
/*     */       
/* 380 */       fill();
/* 381 */       l1 = (this.count - this.pos);
/* 382 */       if (l1 <= 0L) {
/* 383 */         return 0L;
/*     */       }
/*     */     } 
/* 386 */     long l2 = (l1 < paramLong) ? l1 : paramLong;
/* 387 */     this.pos = (int)(this.pos + l2);
/* 388 */     return l2;
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
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized int available() throws IOException {
/* 409 */     int i = this.count - this.pos;
/* 410 */     int j = getInIfOpen().available();
/* 411 */     return (i > Integer.MAX_VALUE - j) ? Integer.MAX_VALUE : (i + j);
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
/*     */   public synchronized void mark(int paramInt) {
/* 425 */     this.marklimit = paramInt;
/* 426 */     this.markpos = this.pos;
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
/*     */ 
/*     */   
/*     */   public synchronized void reset() throws IOException {
/* 446 */     getBufIfOpen();
/* 447 */     if (this.markpos < 0)
/* 448 */       throw new IOException("Resetting to invalid mark"); 
/* 449 */     this.pos = this.markpos;
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
/*     */   public boolean markSupported() {
/* 464 */     return true;
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
/*     */   public void close() throws IOException {
/*     */     byte[] arrayOfByte;
/* 478 */     while ((arrayOfByte = this.buf) != null) {
/* 479 */       if (bufUpdater.compareAndSet(this, arrayOfByte, null)) {
/* 480 */         InputStream inputStream = this.in;
/* 481 */         this.in = null;
/* 482 */         if (inputStream != null)
/* 483 */           inputStream.close(); 
/*     */         return;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/io/BufferedInputStream.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */