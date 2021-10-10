/*     */ package sun.nio.ch;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.net.InetAddress;
/*     */ import java.net.InetSocketAddress;
/*     */ import java.net.Socket;
/*     */ import java.net.SocketAddress;
/*     */ import java.net.SocketException;
/*     */ import java.net.SocketImpl;
/*     */ import java.net.SocketOption;
/*     */ import java.net.SocketTimeoutException;
/*     */ import java.net.StandardSocketOptions;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.channels.Channels;
/*     */ import java.nio.channels.ClosedChannelException;
/*     */ import java.nio.channels.IllegalBlockingModeException;
/*     */ import java.nio.channels.SocketChannel;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedActionException;
/*     */ import java.security.PrivilegedExceptionAction;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class SocketAdaptor
/*     */   extends Socket
/*     */ {
/*     */   private final SocketChannelImpl sc;
/*  70 */   private volatile int timeout = 0;
/*     */   private InputStream socketInputStream;
/*     */   
/*  73 */   private SocketAdaptor(SocketChannelImpl paramSocketChannelImpl) throws SocketException { super((SocketImpl)null);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 235 */     this.socketInputStream = null; this.sc = paramSocketChannelImpl; }
/*     */   public static Socket create(SocketChannelImpl paramSocketChannelImpl) { try { return new SocketAdaptor(paramSocketChannelImpl); } catch (SocketException socketException) { throw new InternalError("Should not reach here"); }  }
/*     */   public SocketChannel getChannel() { return this.sc; }
/* 238 */   public void connect(SocketAddress paramSocketAddress) throws IOException { connect(paramSocketAddress, 0); } public void connect(SocketAddress paramSocketAddress, int paramInt) throws IOException { if (paramSocketAddress == null) throw new IllegalArgumentException("connect: The address can't be null");  if (paramInt < 0) throw new IllegalArgumentException("connect: timeout can't be negative");  synchronized (this.sc.blockingLock()) { if (!this.sc.isBlocking()) throw new IllegalBlockingModeException();  try { if (paramInt == 0) { this.sc.connect(paramSocketAddress); return; }  this.sc.configureBlocking(false); try { if (this.sc.connect(paramSocketAddress)) return;  long l = paramInt; while (true) { if (!this.sc.isOpen()) throw new ClosedChannelException();  long l1 = System.currentTimeMillis(); int i = this.sc.poll(Net.POLLCONN, l); if (i > 0 && this.sc.finishConnect()) break;  l -= System.currentTimeMillis() - l1; if (l <= 0L) { try { this.sc.close(); } catch (IOException iOException) {} throw new SocketTimeoutException(); }  }  } finally { try { this.sc.configureBlocking(true); } catch (ClosedChannelException closedChannelException) {} }  } catch (Exception exception) { Net.translateException(exception, true); }  }  } public void bind(SocketAddress paramSocketAddress) throws IOException { try { this.sc.bind(paramSocketAddress); } catch (Exception exception) { Net.translateException(exception); }  } public InputStream getInputStream() throws IOException { if (!this.sc.isOpen())
/* 239 */       throw new SocketException("Socket is closed"); 
/* 240 */     if (!this.sc.isConnected())
/* 241 */       throw new SocketException("Socket is not connected"); 
/* 242 */     if (!this.sc.isInputOpen())
/* 243 */       throw new SocketException("Socket input is shutdown"); 
/* 244 */     if (this.socketInputStream == null) {
/*     */       try {
/* 246 */         this.socketInputStream = AccessController.<InputStream>doPrivileged(new PrivilegedExceptionAction<InputStream>()
/*     */             {
/*     */               public InputStream run() throws IOException {
/* 249 */                 return new SocketAdaptor.SocketInputStream();
/*     */               }
/*     */             });
/* 252 */       } catch (PrivilegedActionException privilegedActionException) {
/* 253 */         throw (IOException)privilegedActionException.getException();
/*     */       } 
/*     */     }
/* 256 */     return this.socketInputStream; }
/*     */   public InetAddress getInetAddress() { SocketAddress socketAddress = this.sc.remoteAddress(); if (socketAddress == null) return null;  return ((InetSocketAddress)socketAddress).getAddress(); }
/*     */   public InetAddress getLocalAddress() { if (this.sc.isOpen()) { InetSocketAddress inetSocketAddress = this.sc.localAddress(); if (inetSocketAddress != null) return Net.getRevealedLocalAddress(inetSocketAddress).getAddress();  }  return (new InetSocketAddress(0)).getAddress(); }
/*     */   public int getPort() { SocketAddress socketAddress = this.sc.remoteAddress(); if (socketAddress == null) return 0;  return ((InetSocketAddress)socketAddress).getPort(); } public int getLocalPort() { InetSocketAddress inetSocketAddress = this.sc.localAddress(); if (inetSocketAddress == null) return -1;  return inetSocketAddress.getPort(); } private class SocketInputStream extends ChannelInputStream {
/* 260 */     private SocketInputStream() { super(SocketAdaptor.this.sc); } protected int read(ByteBuffer param1ByteBuffer) throws IOException { synchronized (SocketAdaptor.this.sc.blockingLock()) { if (!SocketAdaptor.this.sc.isBlocking()) throw new IllegalBlockingModeException();  if (SocketAdaptor.this.timeout == 0) return SocketAdaptor.this.sc.read(param1ByteBuffer);  SocketAdaptor.this.sc.configureBlocking(false); try { int i; if ((i = SocketAdaptor.this.sc.read(param1ByteBuffer)) != 0) return i;  long l = SocketAdaptor.this.timeout; while (true) { if (!SocketAdaptor.this.sc.isOpen()) throw new ClosedChannelException();  long l1 = System.currentTimeMillis(); int j = SocketAdaptor.this.sc.poll(Net.POLLIN, l); if (j > 0 && (i = SocketAdaptor.this.sc.read(param1ByteBuffer)) != 0) return i;  l -= System.currentTimeMillis() - l1; if (l <= 0L) throw new SocketTimeoutException();  }  } finally { try { SocketAdaptor.this.sc.configureBlocking(true); } catch (ClosedChannelException closedChannelException) {} }  }  } } public OutputStream getOutputStream() throws IOException { if (!this.sc.isOpen())
/* 261 */       throw new SocketException("Socket is closed"); 
/* 262 */     if (!this.sc.isConnected())
/* 263 */       throw new SocketException("Socket is not connected"); 
/* 264 */     if (!this.sc.isOutputOpen())
/* 265 */       throw new SocketException("Socket output is shutdown"); 
/* 266 */     OutputStream outputStream = null;
/*     */     try {
/* 268 */       outputStream = AccessController.<OutputStream>doPrivileged(new PrivilegedExceptionAction<OutputStream>()
/*     */           {
/*     */             public OutputStream run() throws IOException {
/* 271 */               return Channels.newOutputStream(SocketAdaptor.this.sc);
/*     */             }
/*     */           });
/* 274 */     } catch (PrivilegedActionException privilegedActionException) {
/* 275 */       throw (IOException)privilegedActionException.getException();
/*     */     } 
/* 277 */     return outputStream; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void setBooleanOption(SocketOption<Boolean> paramSocketOption, boolean paramBoolean) throws SocketException {
/*     */     try {
/* 284 */       this.sc.setOption(paramSocketOption, Boolean.valueOf(paramBoolean));
/* 285 */     } catch (IOException iOException) {
/* 286 */       Net.translateToSocketException(iOException);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void setIntOption(SocketOption<Integer> paramSocketOption, int paramInt) throws SocketException {
/*     */     try {
/* 294 */       this.sc.setOption(paramSocketOption, Integer.valueOf(paramInt));
/* 295 */     } catch (IOException iOException) {
/* 296 */       Net.translateToSocketException(iOException);
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean getBooleanOption(SocketOption<Boolean> paramSocketOption) throws SocketException {
/*     */     try {
/* 302 */       return ((Boolean)this.sc.<Boolean>getOption(paramSocketOption)).booleanValue();
/* 303 */     } catch (IOException iOException) {
/* 304 */       Net.translateToSocketException(iOException);
/* 305 */       return false;
/*     */     } 
/*     */   }
/*     */   
/*     */   private int getIntOption(SocketOption<Integer> paramSocketOption) throws SocketException {
/*     */     try {
/* 311 */       return ((Integer)this.sc.<Integer>getOption(paramSocketOption)).intValue();
/* 312 */     } catch (IOException iOException) {
/* 313 */       Net.translateToSocketException(iOException);
/* 314 */       return -1;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setTcpNoDelay(boolean paramBoolean) throws SocketException {
/* 319 */     setBooleanOption(StandardSocketOptions.TCP_NODELAY, paramBoolean);
/*     */   }
/*     */   
/*     */   public boolean getTcpNoDelay() throws SocketException {
/* 323 */     return getBooleanOption(StandardSocketOptions.TCP_NODELAY);
/*     */   }
/*     */   
/*     */   public void setSoLinger(boolean paramBoolean, int paramInt) throws SocketException {
/* 327 */     if (!paramBoolean)
/* 328 */       paramInt = -1; 
/* 329 */     setIntOption(StandardSocketOptions.SO_LINGER, paramInt);
/*     */   }
/*     */   
/*     */   public int getSoLinger() throws SocketException {
/* 333 */     return getIntOption(StandardSocketOptions.SO_LINGER);
/*     */   }
/*     */   
/*     */   public void sendUrgentData(int paramInt) throws IOException {
/* 337 */     int i = this.sc.sendOutOfBandData((byte)paramInt);
/* 338 */     if (i == 0)
/* 339 */       throw new IOException("Socket buffer full"); 
/*     */   }
/*     */   
/*     */   public void setOOBInline(boolean paramBoolean) throws SocketException {
/* 343 */     setBooleanOption(ExtendedSocketOption.SO_OOBINLINE, paramBoolean);
/*     */   }
/*     */   
/*     */   public boolean getOOBInline() throws SocketException {
/* 347 */     return getBooleanOption(ExtendedSocketOption.SO_OOBINLINE);
/*     */   }
/*     */   
/*     */   public void setSoTimeout(int paramInt) throws SocketException {
/* 351 */     if (paramInt < 0)
/* 352 */       throw new IllegalArgumentException("timeout can't be negative"); 
/* 353 */     this.timeout = paramInt;
/*     */   }
/*     */   
/*     */   public int getSoTimeout() throws SocketException {
/* 357 */     return this.timeout;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setSendBufferSize(int paramInt) throws SocketException {
/* 362 */     if (paramInt <= 0)
/* 363 */       throw new IllegalArgumentException("Invalid send size"); 
/* 364 */     setIntOption(StandardSocketOptions.SO_SNDBUF, paramInt);
/*     */   }
/*     */   
/*     */   public int getSendBufferSize() throws SocketException {
/* 368 */     return getIntOption(StandardSocketOptions.SO_SNDBUF);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setReceiveBufferSize(int paramInt) throws SocketException {
/* 373 */     if (paramInt <= 0)
/* 374 */       throw new IllegalArgumentException("Invalid receive size"); 
/* 375 */     setIntOption(StandardSocketOptions.SO_RCVBUF, paramInt);
/*     */   }
/*     */   
/*     */   public int getReceiveBufferSize() throws SocketException {
/* 379 */     return getIntOption(StandardSocketOptions.SO_RCVBUF);
/*     */   }
/*     */   
/*     */   public void setKeepAlive(boolean paramBoolean) throws SocketException {
/* 383 */     setBooleanOption(StandardSocketOptions.SO_KEEPALIVE, paramBoolean);
/*     */   }
/*     */   
/*     */   public boolean getKeepAlive() throws SocketException {
/* 387 */     return getBooleanOption(StandardSocketOptions.SO_KEEPALIVE);
/*     */   }
/*     */   
/*     */   public void setTrafficClass(int paramInt) throws SocketException {
/* 391 */     setIntOption(StandardSocketOptions.IP_TOS, paramInt);
/*     */   }
/*     */   
/*     */   public int getTrafficClass() throws SocketException {
/* 395 */     return getIntOption(StandardSocketOptions.IP_TOS);
/*     */   }
/*     */   
/*     */   public void setReuseAddress(boolean paramBoolean) throws SocketException {
/* 399 */     setBooleanOption(StandardSocketOptions.SO_REUSEADDR, paramBoolean);
/*     */   }
/*     */   
/*     */   public boolean getReuseAddress() throws SocketException {
/* 403 */     return getBooleanOption(StandardSocketOptions.SO_REUSEADDR);
/*     */   }
/*     */   
/*     */   public void close() throws IOException {
/* 407 */     this.sc.close();
/*     */   }
/*     */   
/*     */   public void shutdownInput() throws IOException {
/*     */     try {
/* 412 */       this.sc.shutdownInput();
/* 413 */     } catch (Exception exception) {
/* 414 */       Net.translateException(exception);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void shutdownOutput() throws IOException {
/*     */     try {
/* 420 */       this.sc.shutdownOutput();
/* 421 */     } catch (Exception exception) {
/* 422 */       Net.translateException(exception);
/*     */     } 
/*     */   }
/*     */   
/*     */   public String toString() {
/* 427 */     if (this.sc.isConnected())
/* 428 */       return "Socket[addr=" + getInetAddress() + ",port=" + 
/* 429 */         getPort() + ",localport=" + 
/* 430 */         getLocalPort() + "]"; 
/* 431 */     return "Socket[unconnected]";
/*     */   }
/*     */   
/*     */   public boolean isConnected() {
/* 435 */     return this.sc.isConnected();
/*     */   }
/*     */   
/*     */   public boolean isBound() {
/* 439 */     return (this.sc.localAddress() != null);
/*     */   }
/*     */   
/*     */   public boolean isClosed() {
/* 443 */     return !this.sc.isOpen();
/*     */   }
/*     */   
/*     */   public boolean isInputShutdown() {
/* 447 */     return !this.sc.isInputOpen();
/*     */   }
/*     */   
/*     */   public boolean isOutputShutdown() {
/* 451 */     return !this.sc.isOutputOpen();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/nio/ch/SocketAdaptor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */