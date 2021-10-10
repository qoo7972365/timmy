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
/*     */ public class PipedOutputStream
/*     */   extends OutputStream
/*     */ {
/*     */   private PipedInputStream sink;
/*     */   
/*     */   public PipedOutputStream(PipedInputStream paramPipedInputStream) throws IOException {
/*  64 */     connect(paramPipedInputStream);
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
/*     */   public PipedOutputStream() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void connect(PipedInputStream paramPipedInputStream) throws IOException {
/*  97 */     if (paramPipedInputStream == null)
/*  98 */       throw new NullPointerException(); 
/*  99 */     if (this.sink != null || paramPipedInputStream.connected) {
/* 100 */       throw new IOException("Already connected");
/*     */     }
/* 102 */     this.sink = paramPipedInputStream;
/* 103 */     paramPipedInputStream.in = -1;
/* 104 */     paramPipedInputStream.out = 0;
/* 105 */     paramPipedInputStream.connected = true;
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
/*     */   public void write(int paramInt) throws IOException {
/* 119 */     if (this.sink == null) {
/* 120 */       throw new IOException("Pipe not connected");
/*     */     }
/* 122 */     this.sink.receive(paramInt);
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
/*     */   public void write(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws IOException {
/* 139 */     if (this.sink == null)
/* 140 */       throw new IOException("Pipe not connected"); 
/* 141 */     if (paramArrayOfbyte == null)
/* 142 */       throw new NullPointerException(); 
/* 143 */     if (paramInt1 < 0 || paramInt1 > paramArrayOfbyte.length || paramInt2 < 0 || paramInt1 + paramInt2 > paramArrayOfbyte.length || paramInt1 + paramInt2 < 0)
/*     */     {
/* 145 */       throw new IndexOutOfBoundsException(); } 
/* 146 */     if (paramInt2 == 0) {
/*     */       return;
/*     */     }
/* 149 */     this.sink.receive(paramArrayOfbyte, paramInt1, paramInt2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void flush() throws IOException {
/* 160 */     if (this.sink != null) {
/* 161 */       synchronized (this.sink) {
/* 162 */         this.sink.notifyAll();
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
/*     */   public void close() throws IOException {
/* 175 */     if (this.sink != null)
/* 176 */       this.sink.receivedLast(); 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/io/PipedOutputStream.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */