/*     */ package sun.net.httpserver;
/*     */ 
/*     */ import com.sun.net.httpserver.HttpsConfigurator;
/*     */ import com.sun.net.httpserver.HttpsParameters;
/*     */ import java.io.IOException;
/*     */ import java.net.InetSocketAddress;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.channels.SocketChannel;
/*     */ import java.util.concurrent.locks.Lock;
/*     */ import java.util.concurrent.locks.ReentrantLock;
/*     */ import javax.net.ssl.SSLContext;
/*     */ import javax.net.ssl.SSLEngine;
/*     */ import javax.net.ssl.SSLEngineResult;
/*     */ import javax.net.ssl.SSLException;
/*     */ import javax.net.ssl.SSLParameters;
/*     */ import javax.net.ssl.SSLSession;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class SSLStreams
/*     */ {
/*     */   SSLContext sslctx;
/*     */   SocketChannel chan;
/*     */   TimeSource time;
/*     */   ServerImpl server;
/*     */   SSLEngine engine;
/*     */   EngineWrapper wrapper;
/*     */   OutputStream os;
/*     */   InputStream is;
/*  55 */   Lock handshaking = new ReentrantLock(); int app_buf_size;
/*     */   
/*     */   SSLStreams(ServerImpl paramServerImpl, SSLContext paramSSLContext, SocketChannel paramSocketChannel) throws IOException {
/*  58 */     this.server = paramServerImpl;
/*  59 */     this.time = paramServerImpl;
/*  60 */     this.sslctx = paramSSLContext;
/*  61 */     this.chan = paramSocketChannel;
/*     */     
/*  63 */     InetSocketAddress inetSocketAddress = (InetSocketAddress)paramSocketChannel.socket().getRemoteSocketAddress();
/*  64 */     this.engine = paramSSLContext.createSSLEngine(inetSocketAddress.getHostName(), inetSocketAddress.getPort());
/*  65 */     this.engine.setUseClientMode(false);
/*  66 */     HttpsConfigurator httpsConfigurator = paramServerImpl.getHttpsConfigurator();
/*  67 */     configureEngine(httpsConfigurator, inetSocketAddress);
/*  68 */     this.wrapper = new EngineWrapper(paramSocketChannel, this.engine);
/*     */   }
/*     */   int packet_buf_size;
/*     */   private void configureEngine(HttpsConfigurator paramHttpsConfigurator, InetSocketAddress paramInetSocketAddress) {
/*  72 */     if (paramHttpsConfigurator != null) {
/*  73 */       Parameters parameters = new Parameters(paramHttpsConfigurator, paramInetSocketAddress);
/*     */       
/*  75 */       paramHttpsConfigurator.configure(parameters);
/*  76 */       SSLParameters sSLParameters = parameters.getSSLParameters();
/*  77 */       if (sSLParameters != null) {
/*  78 */         this.engine.setSSLParameters(sSLParameters);
/*     */       
/*     */       }
/*     */       else {
/*     */         
/*  83 */         if (parameters.getCipherSuites() != null) {
/*     */           try {
/*  85 */             this.engine.setEnabledCipherSuites(parameters
/*  86 */                 .getCipherSuites());
/*     */           }
/*  88 */           catch (IllegalArgumentException illegalArgumentException) {}
/*     */         }
/*  90 */         this.engine.setNeedClientAuth(parameters.getNeedClientAuth());
/*  91 */         this.engine.setWantClientAuth(parameters.getWantClientAuth());
/*  92 */         if (parameters.getProtocols() != null)
/*     */           try {
/*  94 */             this.engine.setEnabledProtocols(parameters
/*  95 */                 .getProtocols());
/*     */           }
/*  97 */           catch (IllegalArgumentException illegalArgumentException) {} 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   class Parameters extends HttpsParameters {
/*     */     InetSocketAddress addr;
/*     */     HttpsConfigurator cfg;
/*     */     SSLParameters params;
/*     */     
/*     */     Parameters(HttpsConfigurator param1HttpsConfigurator, InetSocketAddress param1InetSocketAddress) {
/* 108 */       this.addr = param1InetSocketAddress;
/* 109 */       this.cfg = param1HttpsConfigurator;
/*     */     }
/*     */     public InetSocketAddress getClientAddress() {
/* 112 */       return this.addr;
/*     */     }
/*     */     public HttpsConfigurator getHttpsConfigurator() {
/* 115 */       return this.cfg;
/*     */     }
/*     */ 
/*     */     
/*     */     public void setSSLParameters(SSLParameters param1SSLParameters) {
/* 120 */       this.params = param1SSLParameters;
/*     */     }
/*     */     SSLParameters getSSLParameters() {
/* 123 */       return this.params;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void close() throws IOException {
/* 132 */     this.wrapper.close();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   InputStream getInputStream() throws IOException {
/* 139 */     if (this.is == null) {
/* 140 */       this.is = new InputStream();
/*     */     }
/* 142 */     return this.is;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   OutputStream getOutputStream() throws IOException {
/* 149 */     if (this.os == null) {
/* 150 */       this.os = new OutputStream();
/*     */     }
/* 152 */     return this.os;
/*     */   }
/*     */   
/*     */   SSLEngine getSSLEngine() {
/* 156 */     return this.engine;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void beginHandshake() throws SSLException {
/* 165 */     this.engine.beginHandshake();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   class WrapperResult
/*     */   {
/*     */     SSLEngineResult result;
/*     */ 
/*     */     
/*     */     ByteBuffer buf;
/*     */   }
/*     */ 
/*     */   
/*     */   enum BufType
/*     */   {
/* 181 */     PACKET, APPLICATION;
/*     */   }
/*     */   
/*     */   private ByteBuffer allocate(BufType paramBufType) {
/* 185 */     return allocate(paramBufType, -1);
/*     */   }
/*     */   
/*     */   private ByteBuffer allocate(BufType paramBufType, int paramInt) {
/* 189 */     assert this.engine != null;
/* 190 */     synchronized (this) {
/*     */       int i;
/* 192 */       if (paramBufType == BufType.PACKET) {
/* 193 */         if (this.packet_buf_size == 0) {
/* 194 */           SSLSession sSLSession = this.engine.getSession();
/* 195 */           this.packet_buf_size = sSLSession.getPacketBufferSize();
/*     */         } 
/* 197 */         if (paramInt > this.packet_buf_size) {
/* 198 */           this.packet_buf_size = paramInt;
/*     */         }
/* 200 */         i = this.packet_buf_size;
/*     */       } else {
/* 202 */         if (this.app_buf_size == 0) {
/* 203 */           SSLSession sSLSession = this.engine.getSession();
/* 204 */           this.app_buf_size = sSLSession.getApplicationBufferSize();
/*     */         } 
/* 206 */         if (paramInt > this.app_buf_size) {
/* 207 */           this.app_buf_size = paramInt;
/*     */         }
/* 209 */         i = this.app_buf_size;
/*     */       } 
/* 211 */       return ByteBuffer.allocate(i);
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
/*     */   private ByteBuffer realloc(ByteBuffer paramByteBuffer, boolean paramBoolean, BufType paramBufType) {
/* 224 */     synchronized (this) {
/* 225 */       int i = 2 * paramByteBuffer.capacity();
/* 226 */       ByteBuffer byteBuffer = allocate(paramBufType, i);
/* 227 */       if (paramBoolean) {
/* 228 */         paramByteBuffer.flip();
/*     */       }
/* 230 */       byteBuffer.put(paramByteBuffer);
/* 231 */       paramByteBuffer = byteBuffer;
/*     */     } 
/* 233 */     return paramByteBuffer;
/*     */   }
/*     */ 
/*     */   
/*     */   class EngineWrapper
/*     */   {
/*     */     SocketChannel chan;
/*     */     
/*     */     SSLEngine engine;
/*     */     
/*     */     Object wrapLock;
/*     */     
/*     */     Object unwrapLock;
/*     */     
/*     */     ByteBuffer unwrap_src;
/*     */     
/*     */     ByteBuffer wrap_dst;
/*     */     boolean closed = false;
/*     */     int u_remaining;
/*     */     
/*     */     EngineWrapper(SocketChannel param1SocketChannel, SSLEngine param1SSLEngine) throws IOException {
/* 254 */       this.chan = param1SocketChannel;
/* 255 */       this.engine = param1SSLEngine;
/* 256 */       this.wrapLock = new Object();
/* 257 */       this.unwrapLock = new Object();
/* 258 */       this.unwrap_src = SSLStreams.this.allocate(SSLStreams.BufType.PACKET);
/* 259 */       this.wrap_dst = SSLStreams.this.allocate(SSLStreams.BufType.PACKET);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     void close() throws IOException {}
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     SSLStreams.WrapperResult wrapAndSend(ByteBuffer param1ByteBuffer) throws IOException {
/* 271 */       return wrapAndSendX(param1ByteBuffer, false);
/*     */     }
/*     */     
/*     */     SSLStreams.WrapperResult wrapAndSendX(ByteBuffer param1ByteBuffer, boolean param1Boolean) throws IOException {
/* 275 */       if (this.closed && !param1Boolean) {
/* 276 */         throw new IOException("Engine is closed");
/*     */       }
/*     */       
/* 279 */       SSLStreams.WrapperResult wrapperResult = new SSLStreams.WrapperResult();
/* 280 */       synchronized (this.wrapLock) {
/* 281 */         this.wrap_dst.clear();
/*     */         while (true) {
/* 283 */           wrapperResult.result = this.engine.wrap(param1ByteBuffer, this.wrap_dst);
/* 284 */           SSLEngineResult.Status status = wrapperResult.result.getStatus();
/* 285 */           if (status == SSLEngineResult.Status.BUFFER_OVERFLOW) {
/* 286 */             this.wrap_dst = SSLStreams.this.realloc(this.wrap_dst, true, SSLStreams.BufType.PACKET);
/*     */           }
/* 288 */           if (status != SSLEngineResult.Status.BUFFER_OVERFLOW) {
/* 289 */             if (status == SSLEngineResult.Status.CLOSED && !param1Boolean) {
/* 290 */               this.closed = true;
/* 291 */               return wrapperResult;
/*     */             } 
/* 293 */             if (wrapperResult.result.bytesProduced() > 0) {
/* 294 */               this.wrap_dst.flip();
/* 295 */               int i = this.wrap_dst.remaining();
/* 296 */               assert i == wrapperResult.result.bytesProduced();
/* 297 */               while (i > 0) {
/* 298 */                 i -= this.chan.write(this.wrap_dst);
/*     */               }
/*     */             } 
/*     */             
/* 302 */             return wrapperResult;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     SSLStreams.WrapperResult recvAndUnwrap(ByteBuffer param1ByteBuffer) throws IOException {
/*     */       boolean bool;
/* 311 */       SSLEngineResult.Status status = SSLEngineResult.Status.OK;
/* 312 */       SSLStreams.WrapperResult wrapperResult = new SSLStreams.WrapperResult();
/* 313 */       wrapperResult.buf = param1ByteBuffer;
/* 314 */       if (this.closed) {
/* 315 */         throw new IOException("Engine is closed");
/*     */       }
/*     */       
/* 318 */       if (this.u_remaining > 0) {
/* 319 */         this.unwrap_src.compact();
/* 320 */         this.unwrap_src.flip();
/* 321 */         bool = false;
/*     */       } else {
/* 323 */         this.unwrap_src.clear();
/* 324 */         bool = true;
/*     */       } 
/* 326 */       synchronized (this.unwrapLock) {
/*     */         
/*     */         while (true) {
/* 329 */           if (bool)
/*     */             while (true) {
/* 331 */               int i = this.chan.read(this.unwrap_src);
/* 332 */               if (i != 0) {
/* 333 */                 if (i == -1) {
/* 334 */                   throw new IOException("connection closed for reading");
/*     */                 }
/* 336 */                 this.unwrap_src.flip(); break;
/*     */               } 
/* 338 */             }   wrapperResult.result = this.engine.unwrap(this.unwrap_src, wrapperResult.buf);
/* 339 */           status = wrapperResult.result.getStatus();
/* 340 */           if (status == SSLEngineResult.Status.BUFFER_UNDERFLOW) {
/* 341 */             if (this.unwrap_src.limit() == this.unwrap_src.capacity()) {
/*     */               
/* 343 */               this.unwrap_src = SSLStreams.this.realloc(this.unwrap_src, false, SSLStreams.BufType.PACKET);
/*     */ 
/*     */             
/*     */             }
/*     */             else {
/*     */ 
/*     */ 
/*     */               
/* 351 */               this.unwrap_src.position(this.unwrap_src.limit());
/* 352 */               this.unwrap_src.limit(this.unwrap_src.capacity());
/*     */             } 
/* 354 */             bool = true;
/* 355 */           } else if (status == SSLEngineResult.Status.BUFFER_OVERFLOW) {
/* 356 */             wrapperResult.buf = SSLStreams.this.realloc(wrapperResult.buf, true, SSLStreams.BufType.APPLICATION);
/* 357 */             bool = false;
/* 358 */           } else if (status == SSLEngineResult.Status.CLOSED) {
/* 359 */             this.closed = true;
/* 360 */             wrapperResult.buf.flip();
/* 361 */             return wrapperResult;
/*     */           } 
/* 363 */           if (status == SSLEngineResult.Status.OK) {
/*     */             
/* 365 */             this.u_remaining = this.unwrap_src.remaining();
/* 366 */             return wrapperResult;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public WrapperResult sendData(ByteBuffer paramByteBuffer) throws IOException {
/* 377 */     WrapperResult wrapperResult = null;
/* 378 */     while (paramByteBuffer.remaining() > 0) {
/* 379 */       wrapperResult = this.wrapper.wrapAndSend(paramByteBuffer);
/* 380 */       SSLEngineResult.Status status = wrapperResult.result.getStatus();
/* 381 */       if (status == SSLEngineResult.Status.CLOSED) {
/* 382 */         doClosure();
/* 383 */         return wrapperResult;
/*     */       } 
/* 385 */       SSLEngineResult.HandshakeStatus handshakeStatus = wrapperResult.result.getHandshakeStatus();
/* 386 */       if (handshakeStatus != SSLEngineResult.HandshakeStatus.FINISHED && handshakeStatus != SSLEngineResult.HandshakeStatus.NOT_HANDSHAKING)
/*     */       {
/*     */         
/* 389 */         doHandshake(handshakeStatus);
/*     */       }
/*     */     } 
/* 392 */     return wrapperResult;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public WrapperResult recvData(ByteBuffer paramByteBuffer) throws IOException {
/* 403 */     WrapperResult wrapperResult = null;
/* 404 */     assert paramByteBuffer.position() == 0;
/* 405 */     while (paramByteBuffer.position() == 0) {
/* 406 */       wrapperResult = this.wrapper.recvAndUnwrap(paramByteBuffer);
/* 407 */       paramByteBuffer = (wrapperResult.buf != paramByteBuffer) ? wrapperResult.buf : paramByteBuffer;
/* 408 */       SSLEngineResult.Status status = wrapperResult.result.getStatus();
/* 409 */       if (status == SSLEngineResult.Status.CLOSED) {
/* 410 */         doClosure();
/* 411 */         return wrapperResult;
/*     */       } 
/*     */       
/* 414 */       SSLEngineResult.HandshakeStatus handshakeStatus = wrapperResult.result.getHandshakeStatus();
/* 415 */       if (handshakeStatus != SSLEngineResult.HandshakeStatus.FINISHED && handshakeStatus != SSLEngineResult.HandshakeStatus.NOT_HANDSHAKING)
/*     */       {
/*     */         
/* 418 */         doHandshake(handshakeStatus);
/*     */       }
/*     */     } 
/* 421 */     paramByteBuffer.flip();
/* 422 */     return wrapperResult;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   void doClosure() throws IOException {
/*     */     try {
/*     */       WrapperResult wrapperResult;
/* 430 */       this.handshaking.lock();
/* 431 */       ByteBuffer byteBuffer = allocate(BufType.APPLICATION);
/*     */       
/*     */       do {
/* 434 */         byteBuffer.clear();
/* 435 */         byteBuffer.flip();
/* 436 */         wrapperResult = this.wrapper.wrapAndSendX(byteBuffer, true);
/* 437 */       } while (wrapperResult.result.getStatus() != SSLEngineResult.Status.CLOSED);
/*     */     } finally {
/* 439 */       this.handshaking.unlock();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void doHandshake(SSLEngineResult.HandshakeStatus paramHandshakeStatus) throws IOException {
/*     */     try {
/* 451 */       this.handshaking.lock();
/* 452 */       ByteBuffer byteBuffer = allocate(BufType.APPLICATION);
/* 453 */       while (paramHandshakeStatus != SSLEngineResult.HandshakeStatus.FINISHED && paramHandshakeStatus != SSLEngineResult.HandshakeStatus.NOT_HANDSHAKING) {
/*     */         Runnable runnable;
/*     */         
/* 456 */         WrapperResult wrapperResult = null;
/* 457 */         switch (paramHandshakeStatus) {
/*     */           
/*     */           case NEED_TASK:
/* 460 */             while ((runnable = this.engine.getDelegatedTask()) != null)
/*     */             {
/*     */ 
/*     */               
/* 464 */               runnable.run();
/*     */             }
/*     */           
/*     */           case NEED_WRAP:
/* 468 */             byteBuffer.clear();
/* 469 */             byteBuffer.flip();
/* 470 */             wrapperResult = this.wrapper.wrapAndSend(byteBuffer);
/*     */             break;
/*     */           
/*     */           case NEED_UNWRAP:
/* 474 */             byteBuffer.clear();
/* 475 */             wrapperResult = this.wrapper.recvAndUnwrap(byteBuffer);
/* 476 */             if (wrapperResult.buf != byteBuffer) {
/* 477 */               byteBuffer = wrapperResult.buf;
/*     */             }
/* 479 */             assert byteBuffer.position() == 0;
/*     */             break;
/*     */         } 
/* 482 */         paramHandshakeStatus = wrapperResult.result.getHandshakeStatus();
/*     */       } 
/*     */     } finally {
/* 485 */       this.handshaking.unlock();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   class InputStream
/*     */     extends java.io.InputStream
/*     */   {
/*     */     ByteBuffer bbuf;
/*     */ 
/*     */     
/*     */     boolean closed = false;
/*     */ 
/*     */     
/*     */     boolean eof = false;
/*     */ 
/*     */     
/*     */     boolean needData = true;
/*     */     
/*     */     byte[] single;
/*     */ 
/*     */     
/*     */     public int read(byte[] param1ArrayOfbyte, int param1Int1, int param1Int2) throws IOException {
/* 509 */       if (this.closed) {
/* 510 */         throw new IOException("SSL stream is closed");
/*     */       }
/* 512 */       if (this.eof) {
/* 513 */         return 0;
/*     */       }
/* 515 */       int i = 0;
/* 516 */       if (!this.needData) {
/* 517 */         i = this.bbuf.remaining();
/* 518 */         this.needData = (i == 0);
/*     */       } 
/* 520 */       if (this.needData) {
/* 521 */         this.bbuf.clear();
/* 522 */         SSLStreams.WrapperResult wrapperResult = SSLStreams.this.recvData(this.bbuf);
/* 523 */         this.bbuf = (wrapperResult.buf == this.bbuf) ? this.bbuf : wrapperResult.buf;
/* 524 */         if ((i = this.bbuf.remaining()) == 0) {
/* 525 */           this.eof = true;
/* 526 */           return 0;
/*     */         } 
/* 528 */         this.needData = false;
/*     */       } 
/*     */ 
/*     */       
/* 532 */       if (param1Int2 > i) {
/* 533 */         param1Int2 = i;
/*     */       }
/* 535 */       this.bbuf.get(param1ArrayOfbyte, param1Int1, param1Int2);
/* 536 */       return param1Int2;
/*     */     }
/*     */     
/*     */     public int available() throws IOException {
/* 540 */       return this.bbuf.remaining();
/*     */     }
/*     */     
/*     */     public boolean markSupported() {
/* 544 */       return false;
/*     */     }
/*     */     
/*     */     public void reset() throws IOException {
/* 548 */       throw new IOException("mark/reset not supported");
/*     */     }
/*     */     
/*     */     public long skip(long param1Long) throws IOException {
/* 552 */       int i = (int)param1Long;
/* 553 */       if (this.closed) {
/* 554 */         throw new IOException("SSL stream is closed");
/*     */       }
/* 556 */       if (this.eof) {
/* 557 */         return 0L;
/*     */       }
/* 559 */       int j = i;
/* 560 */       while (i > 0) {
/* 561 */         if (this.bbuf.remaining() >= i) {
/* 562 */           this.bbuf.position(this.bbuf.position() + i);
/* 563 */           return j;
/*     */         } 
/* 565 */         i -= this.bbuf.remaining();
/* 566 */         this.bbuf.clear();
/* 567 */         SSLStreams.WrapperResult wrapperResult = SSLStreams.this.recvData(this.bbuf);
/* 568 */         this.bbuf = (wrapperResult.buf == this.bbuf) ? this.bbuf : wrapperResult.buf;
/*     */       } 
/*     */       
/* 571 */       return j;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void close() throws IOException {
/* 580 */       this.eof = true;
/* 581 */       SSLStreams.this.engine.closeInbound();
/*     */     }
/*     */     
/*     */     public int read(byte[] param1ArrayOfbyte) throws IOException {
/* 585 */       return read(param1ArrayOfbyte, 0, param1ArrayOfbyte.length);
/*     */     }
/*     */     InputStream() {
/* 588 */       this.single = new byte[1];
/*     */       this.bbuf = SSLStreams.this.allocate(SSLStreams.BufType.APPLICATION);
/*     */     } public int read() throws IOException {
/* 591 */       int i = read(this.single, 0, 1);
/* 592 */       if (i == 0) {
/* 593 */         return -1;
/*     */       }
/* 595 */       return this.single[0] & 0xFF;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   class OutputStream
/*     */     extends java.io.OutputStream
/*     */   {
/*     */     ByteBuffer buf;
/*     */     
/*     */     boolean closed = false;
/*     */     
/* 608 */     byte[] single = new byte[1];
/*     */     
/*     */     OutputStream() {
/* 611 */       this.buf = SSLStreams.this.allocate(SSLStreams.BufType.APPLICATION);
/*     */     }
/*     */     
/*     */     public void write(int param1Int) throws IOException {
/* 615 */       this.single[0] = (byte)param1Int;
/* 616 */       write(this.single, 0, 1);
/*     */     }
/*     */     
/*     */     public void write(byte[] param1ArrayOfbyte) throws IOException {
/* 620 */       write(param1ArrayOfbyte, 0, param1ArrayOfbyte.length);
/*     */     }
/*     */     public void write(byte[] param1ArrayOfbyte, int param1Int1, int param1Int2) throws IOException {
/* 623 */       if (this.closed) {
/* 624 */         throw new IOException("output stream is closed");
/*     */       }
/* 626 */       while (param1Int2 > 0) {
/* 627 */         int i = (param1Int2 > this.buf.capacity()) ? this.buf.capacity() : param1Int2;
/* 628 */         this.buf.clear();
/* 629 */         this.buf.put(param1ArrayOfbyte, param1Int1, i);
/* 630 */         param1Int2 -= i;
/* 631 */         param1Int1 += i;
/* 632 */         this.buf.flip();
/* 633 */         SSLStreams.WrapperResult wrapperResult = SSLStreams.this.sendData(this.buf);
/* 634 */         if (wrapperResult.result.getStatus() == SSLEngineResult.Status.CLOSED) {
/* 635 */           this.closed = true;
/* 636 */           if (param1Int2 > 0) {
/* 637 */             throw new IOException("output stream is closed");
/*     */           }
/*     */         } 
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void flush() throws IOException {}
/*     */ 
/*     */     
/*     */     public void close() throws IOException {
/* 648 */       SSLStreams.WrapperResult wrapperResult = null;
/* 649 */       SSLStreams.this.engine.closeOutbound();
/* 650 */       this.closed = true;
/* 651 */       SSLEngineResult.HandshakeStatus handshakeStatus = SSLEngineResult.HandshakeStatus.NEED_WRAP;
/* 652 */       this.buf.clear();
/* 653 */       while (handshakeStatus == SSLEngineResult.HandshakeStatus.NEED_WRAP) {
/* 654 */         wrapperResult = SSLStreams.this.wrapper.wrapAndSend(this.buf);
/* 655 */         handshakeStatus = wrapperResult.result.getHandshakeStatus();
/*     */       } 
/* 657 */       assert wrapperResult.result.getStatus() == SSLEngineResult.Status.CLOSED;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/net/httpserver/SSLStreams.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */