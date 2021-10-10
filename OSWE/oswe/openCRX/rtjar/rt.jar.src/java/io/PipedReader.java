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
/*     */ public class PipedReader
/*     */   extends Reader
/*     */ {
/*     */   boolean closedByWriter = false;
/*     */   boolean closedByReader = false;
/*     */   boolean connected = false;
/*     */   Thread readSide;
/*     */   Thread writeSide;
/*     */   private static final int DEFAULT_PIPE_SIZE = 1024;
/*     */   char[] buffer;
/*  64 */   int in = -1;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  70 */   int out = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PipedReader(PipedWriter paramPipedWriter) throws IOException {
/*  82 */     this(paramPipedWriter, 1024);
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
/*     */   public PipedReader(PipedWriter paramPipedWriter, int paramInt) throws IOException {
/*  98 */     initPipe(paramInt);
/*  99 */     connect(paramPipedWriter);
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
/*     */   public PipedReader() {
/* 111 */     initPipe(1024);
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
/*     */   public PipedReader(int paramInt) {
/* 127 */     initPipe(paramInt);
/*     */   }
/*     */   
/*     */   private void initPipe(int paramInt) {
/* 131 */     if (paramInt <= 0) {
/* 132 */       throw new IllegalArgumentException("Pipe size <= 0");
/*     */     }
/* 134 */     this.buffer = new char[paramInt];
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
/*     */   public void connect(PipedWriter paramPipedWriter) throws IOException {
/* 161 */     paramPipedWriter.connect(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   synchronized void receive(int paramInt) throws IOException {
/* 169 */     if (!this.connected)
/* 170 */       throw new IOException("Pipe not connected"); 
/* 171 */     if (this.closedByWriter || this.closedByReader)
/* 172 */       throw new IOException("Pipe closed"); 
/* 173 */     if (this.readSide != null && !this.readSide.isAlive()) {
/* 174 */       throw new IOException("Read end dead");
/*     */     }
/*     */     
/* 177 */     this.writeSide = Thread.currentThread();
/* 178 */     while (this.in == this.out) {
/* 179 */       if (this.readSide != null && !this.readSide.isAlive()) {
/* 180 */         throw new IOException("Pipe broken");
/*     */       }
/*     */       
/* 183 */       notifyAll();
/*     */       try {
/* 185 */         wait(1000L);
/* 186 */       } catch (InterruptedException interruptedException) {
/* 187 */         throw new InterruptedIOException();
/*     */       } 
/*     */     } 
/* 190 */     if (this.in < 0) {
/* 191 */       this.in = 0;
/* 192 */       this.out = 0;
/*     */     } 
/* 194 */     this.buffer[this.in++] = (char)paramInt;
/* 195 */     if (this.in >= this.buffer.length) {
/* 196 */       this.in = 0;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   synchronized void receive(char[] paramArrayOfchar, int paramInt1, int paramInt2) throws IOException {
/* 205 */     while (--paramInt2 >= 0) {
/* 206 */       receive(paramArrayOfchar[paramInt1++]);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   synchronized void receivedLast() {
/* 215 */     this.closedByWriter = true;
/* 216 */     notifyAll();
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
/* 234 */     if (!this.connected)
/* 235 */       throw new IOException("Pipe not connected"); 
/* 236 */     if (this.closedByReader)
/* 237 */       throw new IOException("Pipe closed"); 
/* 238 */     if (this.writeSide != null && !this.writeSide.isAlive() && !this.closedByWriter && this.in < 0)
/*     */     {
/* 240 */       throw new IOException("Write end dead");
/*     */     }
/*     */     
/* 243 */     this.readSide = Thread.currentThread();
/* 244 */     byte b = 2;
/* 245 */     while (this.in < 0) {
/* 246 */       if (this.closedByWriter)
/*     */       {
/* 248 */         return -1;
/*     */       }
/* 250 */       if (this.writeSide != null && !this.writeSide.isAlive() && --b < 0) {
/* 251 */         throw new IOException("Pipe broken");
/*     */       }
/*     */       
/* 254 */       notifyAll();
/*     */       try {
/* 256 */         wait(1000L);
/* 257 */       } catch (InterruptedException interruptedException) {
/* 258 */         throw new InterruptedIOException();
/*     */       } 
/*     */     } 
/* 261 */     char c = this.buffer[this.out++];
/* 262 */     if (this.out >= this.buffer.length) {
/* 263 */       this.out = 0;
/*     */     }
/* 265 */     if (this.in == this.out)
/*     */     {
/* 267 */       this.in = -1;
/*     */     }
/* 269 */     return c;
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
/*     */   public synchronized int read(char[] paramArrayOfchar, int paramInt1, int paramInt2) throws IOException {
/* 291 */     if (!this.connected)
/* 292 */       throw new IOException("Pipe not connected"); 
/* 293 */     if (this.closedByReader)
/* 294 */       throw new IOException("Pipe closed"); 
/* 295 */     if (this.writeSide != null && !this.writeSide.isAlive() && !this.closedByWriter && this.in < 0)
/*     */     {
/* 297 */       throw new IOException("Write end dead");
/*     */     }
/*     */     
/* 300 */     if (paramInt1 < 0 || paramInt1 > paramArrayOfchar.length || paramInt2 < 0 || paramInt1 + paramInt2 > paramArrayOfchar.length || paramInt1 + paramInt2 < 0)
/*     */     {
/* 302 */       throw new IndexOutOfBoundsException(); } 
/* 303 */     if (paramInt2 == 0) {
/* 304 */       return 0;
/*     */     }
/*     */ 
/*     */     
/* 308 */     int i = read();
/* 309 */     if (i < 0) {
/* 310 */       return -1;
/*     */     }
/* 312 */     paramArrayOfchar[paramInt1] = (char)i;
/* 313 */     byte b = 1;
/* 314 */     while (this.in >= 0 && --paramInt2 > 0) {
/* 315 */       paramArrayOfchar[paramInt1 + b] = this.buffer[this.out++];
/* 316 */       b++;
/* 317 */       if (this.out >= this.buffer.length) {
/* 318 */         this.out = 0;
/*     */       }
/* 320 */       if (this.in == this.out)
/*     */       {
/* 322 */         this.in = -1;
/*     */       }
/*     */     } 
/* 325 */     return b;
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
/*     */   public synchronized boolean ready() throws IOException {
/* 337 */     if (!this.connected)
/* 338 */       throw new IOException("Pipe not connected"); 
/* 339 */     if (this.closedByReader)
/* 340 */       throw new IOException("Pipe closed"); 
/* 341 */     if (this.writeSide != null && !this.writeSide.isAlive() && !this.closedByWriter && this.in < 0)
/*     */     {
/* 343 */       throw new IOException("Write end dead");
/*     */     }
/* 345 */     if (this.in < 0) {
/* 346 */       return false;
/*     */     }
/* 348 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void close() throws IOException {
/* 359 */     this.in = -1;
/* 360 */     this.closedByReader = true;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/io/PipedReader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */