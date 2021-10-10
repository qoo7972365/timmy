/*     */ package sun.rmi.transport.proxy;
/*     */ 
/*     */ import java.io.DataInputStream;
/*     */ import java.io.EOFException;
/*     */ import java.io.FilterInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import sun.rmi.runtime.Log;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class HttpInputStream
/*     */   extends FilterInputStream
/*     */ {
/*     */   protected int bytesLeft;
/*     */   protected int bytesLeftAtMark;
/*     */   
/*     */   public HttpInputStream(InputStream paramInputStream) throws IOException {
/*  50 */     super(paramInputStream);
/*     */     String str2;
/*  52 */     if (paramInputStream.markSupported()) {
/*  53 */       paramInputStream.mark(0);
/*     */     }
/*     */ 
/*     */     
/*  57 */     DataInputStream dataInputStream = new DataInputStream(paramInputStream);
/*  58 */     String str1 = "Content-length:".toLowerCase();
/*  59 */     boolean bool = false;
/*     */     
/*     */     do {
/*  62 */       str2 = dataInputStream.readLine();
/*     */       
/*  64 */       if (RMIMasterSocketFactory.proxyLog.isLoggable(Log.VERBOSE)) {
/*  65 */         RMIMasterSocketFactory.proxyLog.log(Log.VERBOSE, "received header line: \"" + str2 + "\"");
/*     */       }
/*     */ 
/*     */       
/*  69 */       if (str2 == null) {
/*  70 */         throw new EOFException();
/*     */       }
/*  72 */       if (!str2.toLowerCase().startsWith(str1))
/*  73 */         continue;  if (bool) {
/*  74 */         throw new IOException("Multiple Content-length entries found.");
/*     */       }
/*     */       
/*  77 */       this
/*  78 */         .bytesLeft = Integer.parseInt(str2.substring(str1.length()).trim());
/*  79 */       bool = true;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     }
/*  88 */     while (str2.length() != 0 && str2
/*  89 */       .charAt(0) != '\r' && str2.charAt(0) != '\n');
/*     */     
/*  91 */     if (!bool || this.bytesLeft < 0)
/*     */     {
/*     */       
/*  94 */       this.bytesLeft = Integer.MAX_VALUE;
/*     */     }
/*  96 */     this.bytesLeftAtMark = this.bytesLeft;
/*     */     
/*  98 */     if (RMIMasterSocketFactory.proxyLog.isLoggable(Log.VERBOSE)) {
/*  99 */       RMIMasterSocketFactory.proxyLog.log(Log.VERBOSE, "content length: " + this.bytesLeft);
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
/*     */   public int available() throws IOException {
/* 111 */     int i = this.in.available();
/* 112 */     if (i > this.bytesLeft) {
/* 113 */       i = this.bytesLeft;
/*     */     }
/* 115 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int read() throws IOException {
/* 125 */     if (this.bytesLeft > 0) {
/* 126 */       int i = this.in.read();
/* 127 */       if (i != -1) {
/* 128 */         this.bytesLeft--;
/*     */       }
/* 130 */       if (RMIMasterSocketFactory.proxyLog.isLoggable(Log.VERBOSE)) {
/* 131 */         RMIMasterSocketFactory.proxyLog.log(Log.VERBOSE, "received byte: '" + (((i & 0x7F) < 32) ? " " : 
/*     */             
/* 133 */             String.valueOf((char)i)) + "' " + i);
/*     */       }
/*     */ 
/*     */       
/* 137 */       return i;
/*     */     } 
/*     */     
/* 140 */     RMIMasterSocketFactory.proxyLog.log(Log.VERBOSE, "read past content length");
/*     */ 
/*     */     
/* 143 */     return -1;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int read(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws IOException {
/* 149 */     if (this.bytesLeft == 0 && paramInt2 > 0) {
/* 150 */       RMIMasterSocketFactory.proxyLog.log(Log.VERBOSE, "read past content length");
/*     */ 
/*     */       
/* 153 */       return -1;
/*     */     } 
/* 155 */     if (paramInt2 > this.bytesLeft)
/* 156 */       paramInt2 = this.bytesLeft; 
/* 157 */     int i = this.in.read(paramArrayOfbyte, paramInt1, paramInt2);
/* 158 */     this.bytesLeft -= i;
/*     */     
/* 160 */     if (RMIMasterSocketFactory.proxyLog.isLoggable(Log.VERBOSE)) {
/* 161 */       RMIMasterSocketFactory.proxyLog.log(Log.VERBOSE, "read " + i + " bytes, " + this.bytesLeft + " remaining");
/*     */     }
/*     */ 
/*     */     
/* 165 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void mark(int paramInt) {
/* 176 */     this.in.mark(paramInt);
/* 177 */     if (this.in.markSupported()) {
/* 178 */       this.bytesLeftAtMark = this.bytesLeft;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void reset() throws IOException {
/* 187 */     this.in.reset();
/* 188 */     this.bytesLeft = this.bytesLeftAtMark;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long skip(long paramLong) throws IOException {
/* 198 */     if (paramLong > this.bytesLeft)
/* 199 */       paramLong = this.bytesLeft; 
/* 200 */     long l = this.in.skip(paramLong);
/* 201 */     this.bytesLeft = (int)(this.bytesLeft - l);
/* 202 */     return l;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/rmi/transport/proxy/HttpInputStream.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */