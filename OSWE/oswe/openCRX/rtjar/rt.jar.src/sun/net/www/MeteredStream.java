/*     */ package sun.net.www;
/*     */ 
/*     */ import java.io.FilterInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import sun.net.ProgressSource;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MeteredStream
/*     */   extends FilterInputStream
/*     */ {
/*     */   protected boolean closed = false;
/*     */   protected long expected;
/*  43 */   protected long count = 0L;
/*  44 */   protected long markedCount = 0L;
/*  45 */   protected int markLimit = -1;
/*     */   
/*     */   protected ProgressSource pi;
/*     */   
/*     */   public MeteredStream(InputStream paramInputStream, ProgressSource paramProgressSource, long paramLong) {
/*  50 */     super(paramInputStream);
/*     */     
/*  52 */     this.pi = paramProgressSource;
/*  53 */     this.expected = paramLong;
/*     */     
/*  55 */     if (paramProgressSource != null) {
/*  56 */       paramProgressSource.updateProgress(0L, paramLong);
/*     */     }
/*     */   }
/*     */   
/*     */   private final void justRead(long paramLong) throws IOException {
/*  61 */     if (paramLong == -1L) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  67 */       if (!isMarked()) {
/*  68 */         close();
/*     */       }
/*     */       
/*     */       return;
/*     */     } 
/*  73 */     this.count += paramLong;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  78 */     if (this.count - this.markedCount > this.markLimit) {
/*  79 */       this.markLimit = -1;
/*     */     }
/*     */     
/*  82 */     if (this.pi != null) {
/*  83 */       this.pi.updateProgress(this.count, this.expected);
/*     */     }
/*  85 */     if (isMarked()) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*  91 */     if (this.expected > 0L && 
/*  92 */       this.count >= this.expected) {
/*  93 */       close();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isMarked() {
/* 103 */     if (this.markLimit < 0) {
/* 104 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 108 */     if (this.count - this.markedCount > this.markLimit) {
/* 109 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 113 */     return true;
/*     */   }
/*     */   
/*     */   public synchronized int read() throws IOException {
/* 117 */     if (this.closed) {
/* 118 */       return -1;
/*     */     }
/* 120 */     int i = this.in.read();
/* 121 */     if (i != -1) {
/* 122 */       justRead(1L);
/*     */     } else {
/* 124 */       justRead(i);
/*     */     } 
/* 126 */     return i;
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized int read(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws IOException {
/* 131 */     if (this.closed) {
/* 132 */       return -1;
/*     */     }
/* 134 */     int i = this.in.read(paramArrayOfbyte, paramInt1, paramInt2);
/* 135 */     justRead(i);
/* 136 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized long skip(long paramLong) throws IOException {
/* 142 */     if (this.closed) {
/* 143 */       return 0L;
/*     */     }
/*     */     
/* 146 */     if (this.in instanceof sun.net.www.http.ChunkedInputStream) {
/* 147 */       paramLong = this.in.skip(paramLong);
/*     */     }
/*     */     else {
/*     */       
/* 151 */       long l = (paramLong > this.expected - this.count) ? (this.expected - this.count) : paramLong;
/* 152 */       paramLong = this.in.skip(l);
/*     */     } 
/* 154 */     justRead(paramLong);
/* 155 */     return paramLong;
/*     */   }
/*     */   
/*     */   public void close() throws IOException {
/* 159 */     if (this.closed) {
/*     */       return;
/*     */     }
/* 162 */     if (this.pi != null) {
/* 163 */       this.pi.finishTracking();
/*     */     }
/* 165 */     this.closed = true;
/* 166 */     this.in.close();
/*     */   }
/*     */   
/*     */   public synchronized int available() throws IOException {
/* 170 */     return this.closed ? 0 : this.in.available();
/*     */   }
/*     */   
/*     */   public synchronized void mark(int paramInt) {
/* 174 */     if (this.closed) {
/*     */       return;
/*     */     }
/* 177 */     super.mark(paramInt);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 182 */     this.markedCount = this.count;
/* 183 */     this.markLimit = paramInt;
/*     */   }
/*     */   
/*     */   public synchronized void reset() throws IOException {
/* 187 */     if (this.closed) {
/*     */       return;
/*     */     }
/*     */     
/* 191 */     if (!isMarked()) {
/* 192 */       throw new IOException("Resetting to an invalid mark");
/*     */     }
/*     */     
/* 195 */     this.count = this.markedCount;
/* 196 */     super.reset();
/*     */   }
/*     */   
/*     */   public boolean markSupported() {
/* 200 */     if (this.closed) {
/* 201 */       return false;
/*     */     }
/* 203 */     return super.markSupported();
/*     */   }
/*     */   
/*     */   protected void finalize() throws Throwable {
/*     */     try {
/* 208 */       close();
/* 209 */       if (this.pi != null) {
/* 210 */         this.pi.close();
/*     */       }
/*     */     } finally {
/*     */       
/* 214 */       super.finalize();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/net/www/MeteredStream.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */