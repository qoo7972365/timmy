/*     */ package java.io;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PipedInputStream
/*     */   extends InputStream
/*     */ {
/*     */   boolean closedByWriter = false;
/*     */   volatile boolean closedByReader = false;
/*     */   boolean connected = false;
/*     */   Thread readSide;
/*     */   Thread writeSide;
/*     */   private static final int DEFAULT_PIPE_SIZE = 1024;
/*     */   protected static final int PIPE_SIZE = 1024;
/*     */   protected byte[] buffer;
/*  86 */   protected int in = -1;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  93 */   protected int out = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PipedInputStream(PipedOutputStream paramPipedOutputStream) throws IOException {
/* 106 */     this(paramPipedOutputStream, 1024);
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
/*     */   public PipedInputStream(PipedOutputStream paramPipedOutputStream, int paramInt) throws IOException {
/* 125 */     initPipe(paramInt);
/* 126 */     connect(paramPipedOutputStream);
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
/*     */   public PipedInputStream() {
/* 138 */     initPipe(1024);
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
/*     */   public PipedInputStream(int paramInt) {
/* 154 */     initPipe(paramInt);
/*     */   }
/*     */   
/*     */   private void initPipe(int paramInt) {
/* 158 */     if (paramInt <= 0) {
/* 159 */       throw new IllegalArgumentException("Pipe Size <= 0");
/*     */     }
/* 161 */     this.buffer = new byte[paramInt];
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
/*     */   public void connect(PipedOutputStream paramPipedOutputStream) throws IOException {
/* 188 */     paramPipedOutputStream.connect(this);
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
/*     */   protected synchronized void receive(int paramInt) throws IOException {
/* 201 */     checkStateForReceive();
/* 202 */     this.writeSide = Thread.currentThread();
/* 203 */     if (this.in == this.out)
/* 204 */       awaitSpace(); 
/* 205 */     if (this.in < 0) {
/* 206 */       this.in = 0;
/* 207 */       this.out = 0;
/*     */     } 
/* 209 */     this.buffer[this.in++] = (byte)(paramInt & 0xFF);
/* 210 */     if (this.in >= this.buffer.length) {
/* 211 */       this.in = 0;
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
/*     */   synchronized void receive(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws IOException {
/* 226 */     checkStateForReceive();
/* 227 */     this.writeSide = Thread.currentThread();
/* 228 */     int i = paramInt2;
/* 229 */     while (i > 0) {
/* 230 */       if (this.in == this.out)
/* 231 */         awaitSpace(); 
/* 232 */       int j = 0;
/* 233 */       if (this.out < this.in) {
/* 234 */         j = this.buffer.length - this.in;
/* 235 */       } else if (this.in < this.out) {
/* 236 */         if (this.in == -1) {
/* 237 */           this.in = this.out = 0;
/* 238 */           j = this.buffer.length - this.in;
/*     */         } else {
/* 240 */           j = this.out - this.in;
/*     */         } 
/*     */       } 
/* 243 */       if (j > i)
/* 244 */         j = i; 
/* 245 */       assert j > 0;
/* 246 */       System.arraycopy(paramArrayOfbyte, paramInt1, this.buffer, this.in, j);
/* 247 */       i -= j;
/* 248 */       paramInt1 += j;
/* 249 */       this.in += j;
/* 250 */       if (this.in >= this.buffer.length) {
/* 251 */         this.in = 0;
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private void checkStateForReceive() throws IOException {
/* 257 */     if (!this.connected)
/* 258 */       throw new IOException("Pipe not connected"); 
/* 259 */     if (this.closedByWriter || this.closedByReader)
/* 260 */       throw new IOException("Pipe closed"); 
/* 261 */     if (this.readSide != null && !this.readSide.isAlive()) {
/* 262 */       throw new IOException("Read end dead");
/*     */     }
/*     */   }
/*     */   
/*     */   private void awaitSpace() throws IOException {
/* 267 */     while (this.in == this.out) {
/* 268 */       checkStateForReceive();
/*     */ 
/*     */       
/* 271 */       notifyAll();
/*     */       try {
/* 273 */         wait(1000L);
/* 274 */       } catch (InterruptedException interruptedException) {
/* 275 */         throw new InterruptedIOException();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   synchronized void receivedLast() {
/* 285 */     this.closedByWriter = true;
/* 286 */     notifyAll();
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
/*     */   public synchronized int read() throws IOException {
/* 304 */     if (!this.connected)
/* 305 */       throw new IOException("Pipe not connected"); 
/* 306 */     if (this.closedByReader)
/* 307 */       throw new IOException("Pipe closed"); 
/* 308 */     if (this.writeSide != null && !this.writeSide.isAlive() && !this.closedByWriter && this.in < 0)
/*     */     {
/* 310 */       throw new IOException("Write end dead");
/*     */     }
/*     */     
/* 313 */     this.readSide = Thread.currentThread();
/* 314 */     byte b = 2;
/* 315 */     while (this.in < 0) {
/* 316 */       if (this.closedByWriter)
/*     */       {
/* 318 */         return -1;
/*     */       }
/* 320 */       if (this.writeSide != null && !this.writeSide.isAlive() && --b < 0) {
/* 321 */         throw new IOException("Pipe broken");
/*     */       }
/*     */       
/* 324 */       notifyAll();
/*     */       try {
/* 326 */         wait(1000L);
/* 327 */       } catch (InterruptedException interruptedException) {
/* 328 */         throw new InterruptedIOException();
/*     */       } 
/*     */     } 
/* 331 */     int i = this.buffer[this.out++] & 0xFF;
/* 332 */     if (this.out >= this.buffer.length) {
/* 333 */       this.out = 0;
/*     */     }
/* 335 */     if (this.in == this.out)
/*     */     {
/* 337 */       this.in = -1;
/*     */     }
/*     */     
/* 340 */     return i;
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
/*     */   public synchronized int read(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws IOException {
/* 368 */     if (paramArrayOfbyte == null)
/* 369 */       throw new NullPointerException(); 
/* 370 */     if (paramInt1 < 0 || paramInt2 < 0 || paramInt2 > paramArrayOfbyte.length - paramInt1)
/* 371 */       throw new IndexOutOfBoundsException(); 
/* 372 */     if (paramInt2 == 0) {
/* 373 */       return 0;
/*     */     }
/*     */ 
/*     */     
/* 377 */     int i = read();
/* 378 */     if (i < 0) {
/* 379 */       return -1;
/*     */     }
/* 381 */     paramArrayOfbyte[paramInt1] = (byte)i;
/* 382 */     int j = 1;
/* 383 */     while (this.in >= 0 && paramInt2 > 1) {
/*     */       int k;
/*     */ 
/*     */       
/* 387 */       if (this.in > this.out) {
/* 388 */         k = Math.min(this.buffer.length - this.out, this.in - this.out);
/*     */       } else {
/* 390 */         k = this.buffer.length - this.out;
/*     */       } 
/*     */ 
/*     */       
/* 394 */       if (k > paramInt2 - 1) {
/* 395 */         k = paramInt2 - 1;
/*     */       }
/* 397 */       System.arraycopy(this.buffer, this.out, paramArrayOfbyte, paramInt1 + j, k);
/* 398 */       this.out += k;
/* 399 */       j += k;
/* 400 */       paramInt2 -= k;
/*     */       
/* 402 */       if (this.out >= this.buffer.length) {
/* 403 */         this.out = 0;
/*     */       }
/* 405 */       if (this.in == this.out)
/*     */       {
/* 407 */         this.in = -1;
/*     */       }
/*     */     } 
/* 410 */     return j;
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
/*     */   public synchronized int available() throws IOException {
/* 427 */     if (this.in < 0)
/* 428 */       return 0; 
/* 429 */     if (this.in == this.out)
/* 430 */       return this.buffer.length; 
/* 431 */     if (this.in > this.out) {
/* 432 */       return this.in - this.out;
/*     */     }
/* 434 */     return this.in + this.buffer.length - this.out;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void close() throws IOException {
/* 444 */     this.closedByReader = true;
/* 445 */     synchronized (this) {
/* 446 */       this.in = -1;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/io/PipedInputStream.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */