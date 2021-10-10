/*     */ package java.net;
/*     */ 
/*     */ import java.io.FileDescriptor;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
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
/*     */ public abstract class SocketImpl
/*     */   implements SocketOptions
/*     */ {
/*  48 */   Socket socket = null;
/*  49 */   ServerSocket serverSocket = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected FileDescriptor fd;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected InetAddress address;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int port;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int localport;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract void create(boolean paramBoolean) throws IOException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract void connect(String paramString, int paramInt) throws IOException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract void connect(InetAddress paramInetAddress, int paramInt) throws IOException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract void connect(SocketAddress paramSocketAddress, int paramInt) throws IOException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract void bind(InetAddress paramInetAddress, int paramInt) throws IOException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract void listen(int paramInt) throws IOException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract void accept(SocketImpl paramSocketImpl) throws IOException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract InputStream getInputStream() throws IOException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract OutputStream getOutputStream() throws IOException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract int available() throws IOException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract void close() throws IOException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void shutdownInput() throws IOException {
/* 196 */     throw new IOException("Method not implemented!");
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
/*     */   protected void shutdownOutput() throws IOException {
/* 216 */     throw new IOException("Method not implemented!");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected FileDescriptor getFileDescriptor() {
/* 226 */     return this.fd;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected InetAddress getInetAddress() {
/* 236 */     return this.address;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int getPort() {
/* 246 */     return this.port;
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
/*     */   protected boolean supportsUrgentData() {
/* 259 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract void sendUrgentData(int paramInt) throws IOException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int getLocalPort() {
/* 279 */     return this.localport;
/*     */   }
/*     */   
/*     */   void setSocket(Socket paramSocket) {
/* 283 */     this.socket = paramSocket;
/*     */   }
/*     */   
/*     */   Socket getSocket() {
/* 287 */     return this.socket;
/*     */   }
/*     */   
/*     */   void setServerSocket(ServerSocket paramServerSocket) {
/* 291 */     this.serverSocket = paramServerSocket;
/*     */   }
/*     */   
/*     */   ServerSocket getServerSocket() {
/* 295 */     return this.serverSocket;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 304 */     return "Socket[addr=" + getInetAddress() + ",port=" + 
/* 305 */       getPort() + ",localport=" + getLocalPort() + "]";
/*     */   }
/*     */   
/*     */   void reset() throws IOException {
/* 309 */     this.address = null;
/* 310 */     this.port = 0;
/* 311 */     this.localport = 0;
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
/*     */   protected void setPerformancePreferences(int paramInt1, int paramInt2, int paramInt3) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   <T> void setOption(SocketOption<T> paramSocketOption, T paramT) throws IOException {
/* 360 */     if (paramSocketOption == StandardSocketOptions.SO_KEEPALIVE) {
/* 361 */       setOption(8, paramT);
/* 362 */     } else if (paramSocketOption == StandardSocketOptions.SO_SNDBUF) {
/* 363 */       setOption(4097, paramT);
/* 364 */     } else if (paramSocketOption == StandardSocketOptions.SO_RCVBUF) {
/* 365 */       setOption(4098, paramT);
/* 366 */     } else if (paramSocketOption == StandardSocketOptions.SO_REUSEADDR) {
/* 367 */       setOption(4, paramT);
/* 368 */     } else if (paramSocketOption == StandardSocketOptions.SO_LINGER) {
/* 369 */       setOption(128, paramT);
/* 370 */     } else if (paramSocketOption == StandardSocketOptions.IP_TOS) {
/* 371 */       setOption(3, paramT);
/* 372 */     } else if (paramSocketOption == StandardSocketOptions.TCP_NODELAY) {
/* 373 */       setOption(1, paramT);
/*     */     } else {
/* 375 */       throw new UnsupportedOperationException("unsupported option");
/*     */     } 
/*     */   }
/*     */   
/*     */   <T> T getOption(SocketOption<T> paramSocketOption) throws IOException {
/* 380 */     if (paramSocketOption == StandardSocketOptions.SO_KEEPALIVE)
/* 381 */       return (T)getOption(8); 
/* 382 */     if (paramSocketOption == StandardSocketOptions.SO_SNDBUF)
/* 383 */       return (T)getOption(4097); 
/* 384 */     if (paramSocketOption == StandardSocketOptions.SO_RCVBUF)
/* 385 */       return (T)getOption(4098); 
/* 386 */     if (paramSocketOption == StandardSocketOptions.SO_REUSEADDR)
/* 387 */       return (T)getOption(4); 
/* 388 */     if (paramSocketOption == StandardSocketOptions.SO_LINGER)
/* 389 */       return (T)getOption(128); 
/* 390 */     if (paramSocketOption == StandardSocketOptions.IP_TOS)
/* 391 */       return (T)getOption(3); 
/* 392 */     if (paramSocketOption == StandardSocketOptions.TCP_NODELAY) {
/* 393 */       return (T)getOption(1);
/*     */     }
/* 395 */     throw new UnsupportedOperationException("unsupported option");
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/net/SocketImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */