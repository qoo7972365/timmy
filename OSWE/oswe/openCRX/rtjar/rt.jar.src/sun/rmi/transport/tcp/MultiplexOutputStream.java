/*     */ package sun.rmi.transport.tcp;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class MultiplexOutputStream
/*     */   extends OutputStream
/*     */ {
/*     */   private ConnectionMultiplexer manager;
/*     */   private MultiplexConnectionInfo info;
/*     */   private byte[] buffer;
/*  54 */   private int pos = 0;
/*     */ 
/*     */   
/*  57 */   private int requested = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean disconnected = false;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  71 */   private Object lock = new Object();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   MultiplexOutputStream(ConnectionMultiplexer paramConnectionMultiplexer, MultiplexConnectionInfo paramMultiplexConnectionInfo, int paramInt) {
/*  84 */     this.manager = paramConnectionMultiplexer;
/*  85 */     this.info = paramMultiplexConnectionInfo;
/*     */     
/*  87 */     this.buffer = new byte[paramInt];
/*  88 */     this.pos = 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void write(int paramInt) throws IOException {
/*  97 */     while (this.pos >= this.buffer.length)
/*  98 */       push(); 
/*  99 */     this.buffer[this.pos++] = (byte)paramInt;
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
/*     */   public synchronized void write(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws IOException {
/* 111 */     if (paramInt2 <= 0) {
/*     */       return;
/*     */     }
/*     */     
/* 115 */     int i = this.buffer.length - this.pos;
/* 116 */     if (paramInt2 <= i) {
/* 117 */       System.arraycopy(paramArrayOfbyte, paramInt1, this.buffer, this.pos, paramInt2);
/* 118 */       this.pos += paramInt2;
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 123 */     flush();
/*     */     while (true) {
/*     */       int j;
/* 126 */       synchronized (this.lock) {
/* 127 */         while ((j = this.requested) < 1 && !this.disconnected) {
/*     */           try {
/* 129 */             this.lock.wait();
/* 130 */           } catch (InterruptedException interruptedException) {}
/*     */         } 
/*     */         
/* 133 */         if (this.disconnected) {
/* 134 */           throw new IOException("Connection closed");
/*     */         }
/*     */       } 
/* 137 */       if (j < paramInt2) {
/* 138 */         this.manager.sendTransmit(this.info, paramArrayOfbyte, paramInt1, j);
/* 139 */         paramInt1 += j;
/* 140 */         paramInt2 -= j;
/* 141 */         synchronized (this.lock) {
/* 142 */           this.requested -= j;
/*     */         }  continue;
/*     */       }  break;
/*     */     } 
/* 146 */     this.manager.sendTransmit(this.info, paramArrayOfbyte, paramInt1, paramInt2);
/* 147 */     synchronized (this.lock) {
/* 148 */       this.requested -= paramInt2;
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
/*     */   public synchronized void flush() throws IOException {
/* 161 */     while (this.pos > 0) {
/* 162 */       push();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void close() throws IOException {
/* 170 */     this.manager.sendClose(this.info);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void request(int paramInt) {
/* 179 */     synchronized (this.lock) {
/* 180 */       this.requested += paramInt;
/* 181 */       this.lock.notifyAll();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void disconnect() {
/* 190 */     synchronized (this.lock) {
/* 191 */       this.disconnected = true;
/* 192 */       this.lock.notifyAll();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void push() throws IOException {
/*     */     int i;
/* 203 */     synchronized (this.lock) {
/* 204 */       while ((i = this.requested) < 1 && !this.disconnected) {
/*     */         try {
/* 206 */           this.lock.wait();
/* 207 */         } catch (InterruptedException interruptedException) {}
/*     */       } 
/*     */       
/* 210 */       if (this.disconnected) {
/* 211 */         throw new IOException("Connection closed");
/*     */       }
/*     */     } 
/* 214 */     if (i < this.pos) {
/* 215 */       this.manager.sendTransmit(this.info, this.buffer, 0, i);
/* 216 */       System.arraycopy(this.buffer, i, this.buffer, 0, this.pos - i);
/*     */       
/* 218 */       this.pos -= i;
/* 219 */       synchronized (this.lock) {
/* 220 */         this.requested -= i;
/*     */       } 
/*     */     } else {
/*     */       
/* 224 */       this.manager.sendTransmit(this.info, this.buffer, 0, this.pos);
/* 225 */       synchronized (this.lock) {
/* 226 */         this.requested -= this.pos;
/*     */       } 
/* 228 */       this.pos = 0;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/rmi/transport/tcp/MultiplexOutputStream.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */