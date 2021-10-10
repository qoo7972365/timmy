/*     */ package sun.net.www.http;
/*     */ 
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.DataInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import sun.net.ProgressSource;
/*     */ import sun.net.www.MeteredStream;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class KeepAliveStream
/*     */   extends MeteredStream
/*     */   implements Hurryable
/*     */ {
/*     */   HttpClient hc;
/*     */   boolean hurried;
/*     */   protected boolean queuedForCleanup = false;
/*  50 */   private static final KeepAliveStreamCleaner queue = new KeepAliveStreamCleaner();
/*     */ 
/*     */   
/*     */   private static Thread cleanerThread;
/*     */ 
/*     */   
/*     */   public KeepAliveStream(InputStream paramInputStream, ProgressSource paramProgressSource, long paramLong, HttpClient paramHttpClient) {
/*  57 */     super(paramInputStream, paramProgressSource, paramLong);
/*  58 */     this.hc = paramHttpClient;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void close() throws IOException {
/*  66 */     if (this.closed) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/*  71 */     if (this.queuedForCleanup) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/*  83 */       if (this.expected > this.count) {
/*  84 */         long l = this.expected - this.count;
/*  85 */         if (l <= available()) { do {  }
/*  86 */           while ((l = this.expected - this.count) > 0L && 
/*  87 */             skip(Math.min(l, available())) > 0L); }
/*  88 */         else if (this.expected <= KeepAliveStreamCleaner.MAX_DATA_REMAINING && !this.hurried)
/*     */         
/*     */         { 
/*  91 */           queueForCleanup(new KeepAliveCleanerEntry(this, this.hc)); }
/*     */         else
/*  93 */         { this.hc.closeServer(); }
/*     */       
/*     */       } 
/*  96 */       if (!this.closed && !this.hurried && !this.queuedForCleanup) {
/*  97 */         this.hc.finished();
/*     */       }
/*     */     } finally {
/* 100 */       if (this.pi != null) {
/* 101 */         this.pi.finishTracking();
/*     */       }
/* 103 */       if (!this.queuedForCleanup) {
/*     */ 
/*     */         
/* 106 */         this.in = null;
/* 107 */         this.hc = null;
/* 108 */         this.closed = true;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean markSupported() {
/* 116 */     return false;
/*     */   }
/*     */   
/*     */   public void mark(int paramInt) {}
/*     */   
/*     */   public void reset() throws IOException {
/* 122 */     throw new IOException("mark/reset not supported");
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized boolean hurry() {
/*     */     try {
/* 128 */       if (this.closed || this.count >= this.expected)
/* 129 */         return false; 
/* 130 */       if (this.in.available() < this.expected - this.count)
/*     */       {
/* 132 */         return false;
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 137 */       int i = (int)(this.expected - this.count);
/* 138 */       byte[] arrayOfByte = new byte[i];
/* 139 */       DataInputStream dataInputStream = new DataInputStream(this.in);
/* 140 */       dataInputStream.readFully(arrayOfByte);
/* 141 */       this.in = new ByteArrayInputStream(arrayOfByte);
/* 142 */       this.hurried = true;
/* 143 */       return true;
/*     */     }
/* 145 */     catch (IOException iOException) {
/*     */       
/* 147 */       return false;
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void queueForCleanup(KeepAliveCleanerEntry paramKeepAliveCleanerEntry) {
/* 152 */     synchronized (queue) {
/* 153 */       if (!paramKeepAliveCleanerEntry.getQueuedForCleanup()) {
/* 154 */         if (!queue.offer(paramKeepAliveCleanerEntry)) {
/* 155 */           paramKeepAliveCleanerEntry.getHttpClient().closeServer();
/*     */           
/*     */           return;
/*     */         } 
/* 159 */         paramKeepAliveCleanerEntry.setQueuedForCleanup();
/* 160 */         queue.notifyAll();
/*     */       } 
/*     */       
/* 163 */       boolean bool = (cleanerThread == null) ? true : false;
/* 164 */       if (!bool && 
/* 165 */         !cleanerThread.isAlive()) {
/* 166 */         bool = true;
/*     */       }
/*     */ 
/*     */       
/* 170 */       if (bool) {
/* 171 */         AccessController.doPrivileged(new PrivilegedAction<Void>()
/*     */             {
/*     */               
/*     */               public Void run()
/*     */               {
/* 176 */                 ThreadGroup threadGroup1 = Thread.currentThread().getThreadGroup();
/* 177 */                 ThreadGroup threadGroup2 = null;
/* 178 */                 while ((threadGroup2 = threadGroup1.getParent()) != null) {
/* 179 */                   threadGroup1 = threadGroup2;
/*     */                 }
/*     */                 
/* 182 */                 KeepAliveStream.cleanerThread = new Thread(threadGroup1, KeepAliveStream.queue, "Keep-Alive-SocketCleaner");
/* 183 */                 KeepAliveStream.cleanerThread.setDaemon(true);
/* 184 */                 KeepAliveStream.cleanerThread.setPriority(8);
/*     */ 
/*     */                 
/* 187 */                 KeepAliveStream.cleanerThread.setContextClassLoader(null);
/* 188 */                 KeepAliveStream.cleanerThread.start();
/* 189 */                 return null;
/*     */               }
/*     */             });
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   protected long remainingToRead() {
/* 197 */     return this.expected - this.count;
/*     */   }
/*     */   
/*     */   protected void setClosed() {
/* 201 */     this.in = null;
/* 202 */     this.hc = null;
/* 203 */     this.closed = true;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/net/www/http/KeepAliveStream.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */