/*     */ package java.net;
/*     */ 
/*     */ import java.io.FileDescriptor;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import sun.net.ConnectionResetException;
/*     */ import sun.net.NetHooks;
/*     */ import sun.net.ResourceManager;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ abstract class AbstractPlainSocketImpl
/*     */   extends SocketImpl
/*     */ {
/*     */   int timeout;
/*     */   private int trafficClass;
/*     */   private boolean shut_rd = false;
/*     */   private boolean shut_wr = false;
/*  54 */   private SocketInputStream socketInputStream = null;
/*  55 */   private SocketOutputStream socketOutputStream = null;
/*     */ 
/*     */   
/*  58 */   protected int fdUseCount = 0;
/*     */ 
/*     */   
/*  61 */   protected final Object fdLock = new Object();
/*     */ 
/*     */   
/*     */   protected boolean closePending = false;
/*     */ 
/*     */   
/*  67 */   private int CONNECTION_NOT_RESET = 0;
/*  68 */   private int CONNECTION_RESET_PENDING = 1;
/*  69 */   private int CONNECTION_RESET = 2;
/*     */   private int resetState;
/*  71 */   private final Object resetLock = new Object();
/*     */   
/*     */   protected boolean stream;
/*     */   
/*     */   public static final int SHUT_RD = 0;
/*     */   
/*     */   public static final int SHUT_WR = 1;
/*     */ 
/*     */   
/*     */   static {
/*  81 */     AccessController.doPrivileged(new PrivilegedAction<Void>()
/*     */         {
/*     */           public Void run() {
/*  84 */             System.loadLibrary("net");
/*  85 */             return null;
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected synchronized void create(boolean paramBoolean) throws IOException {
/*  95 */     this.stream = paramBoolean;
/*  96 */     if (!paramBoolean) {
/*  97 */       ResourceManager.beforeUdpCreate();
/*     */       
/*  99 */       this.fd = new FileDescriptor();
/*     */       try {
/* 101 */         socketCreate(false);
/* 102 */       } catch (IOException iOException) {
/* 103 */         ResourceManager.afterUdpClose();
/* 104 */         this.fd = null;
/* 105 */         throw iOException;
/*     */       } 
/*     */     } else {
/* 108 */       this.fd = new FileDescriptor();
/* 109 */       socketCreate(true);
/*     */     } 
/* 111 */     if (this.socket != null)
/* 112 */       this.socket.setCreated(); 
/* 113 */     if (this.serverSocket != null) {
/* 114 */       this.serverSocket.setCreated();
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
/*     */   protected void connect(String paramString, int paramInt) throws UnknownHostException, IOException {
/* 126 */     boolean bool = false;
/*     */     try {
/* 128 */       InetAddress inetAddress = InetAddress.getByName(paramString);
/* 129 */       this.port = paramInt;
/* 130 */       this.address = inetAddress;
/*     */       
/* 132 */       connectToAddress(inetAddress, paramInt, this.timeout);
/* 133 */       bool = true;
/*     */     } finally {
/* 135 */       if (!bool) {
/*     */         try {
/* 137 */           close();
/* 138 */         } catch (IOException iOException) {}
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
/*     */ 
/*     */   
/*     */   protected void connect(InetAddress paramInetAddress, int paramInt) throws IOException {
/* 153 */     this.port = paramInt;
/* 154 */     this.address = paramInetAddress;
/*     */     
/*     */     try {
/* 157 */       connectToAddress(paramInetAddress, paramInt, this.timeout);
/*     */       return;
/* 159 */     } catch (IOException iOException) {
/*     */       
/* 161 */       close();
/* 162 */       throw iOException;
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
/*     */   
/*     */   protected void connect(SocketAddress paramSocketAddress, int paramInt) throws IOException {
/* 178 */     boolean bool = false;
/*     */     try {
/* 180 */       if (paramSocketAddress == null || !(paramSocketAddress instanceof InetSocketAddress))
/* 181 */         throw new IllegalArgumentException("unsupported address type"); 
/* 182 */       InetSocketAddress inetSocketAddress = (InetSocketAddress)paramSocketAddress;
/* 183 */       if (inetSocketAddress.isUnresolved())
/* 184 */         throw new UnknownHostException(inetSocketAddress.getHostName()); 
/* 185 */       this.port = inetSocketAddress.getPort();
/* 186 */       this.address = inetSocketAddress.getAddress();
/*     */       
/* 188 */       connectToAddress(this.address, this.port, paramInt);
/* 189 */       bool = true;
/*     */     } finally {
/* 191 */       if (!bool) {
/*     */         try {
/* 193 */           close();
/* 194 */         } catch (IOException iOException) {}
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void connectToAddress(InetAddress paramInetAddress, int paramInt1, int paramInt2) throws IOException {
/* 203 */     if (paramInetAddress.isAnyLocalAddress()) {
/* 204 */       doConnect(InetAddress.getLocalHost(), paramInt1, paramInt2);
/*     */     } else {
/* 206 */       doConnect(paramInetAddress, paramInt1, paramInt2);
/*     */     } 
/*     */   }
/*     */   public void setOption(int paramInt, Object paramObject) throws SocketException {
/*     */     int i;
/* 211 */     if (isClosedOrPending()) {
/* 212 */       throw new SocketException("Socket Closed");
/*     */     }
/* 214 */     boolean bool = true;
/* 215 */     switch (paramInt) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       case 128:
/* 221 */         if (paramObject == null || (!(paramObject instanceof Integer) && !(paramObject instanceof Boolean)))
/* 222 */           throw new SocketException("Bad parameter for option"); 
/* 223 */         if (paramObject instanceof Boolean)
/*     */         {
/* 225 */           bool = false;
/*     */         }
/*     */         break;
/*     */       case 4102:
/* 229 */         if (paramObject == null || !(paramObject instanceof Integer))
/* 230 */           throw new SocketException("Bad parameter for SO_TIMEOUT"); 
/* 231 */         i = ((Integer)paramObject).intValue();
/* 232 */         if (i < 0)
/* 233 */           throw new IllegalArgumentException("timeout < 0"); 
/* 234 */         this.timeout = i;
/*     */         break;
/*     */       case 3:
/* 237 */         if (paramObject == null || !(paramObject instanceof Integer)) {
/* 238 */           throw new SocketException("bad argument for IP_TOS");
/*     */         }
/* 240 */         this.trafficClass = ((Integer)paramObject).intValue();
/*     */         break;
/*     */       case 15:
/* 243 */         throw new SocketException("Cannot re-bind socket");
/*     */       case 1:
/* 245 */         if (paramObject == null || !(paramObject instanceof Boolean))
/* 246 */           throw new SocketException("bad parameter for TCP_NODELAY"); 
/* 247 */         bool = ((Boolean)paramObject).booleanValue();
/*     */         break;
/*     */       case 4097:
/*     */       case 4098:
/* 251 */         if (paramObject == null || !(paramObject instanceof Integer) || ((Integer)paramObject)
/* 252 */           .intValue() <= 0) {
/* 253 */           throw new SocketException("bad parameter for SO_SNDBUF or SO_RCVBUF");
/*     */         }
/*     */         break;
/*     */       
/*     */       case 8:
/* 258 */         if (paramObject == null || !(paramObject instanceof Boolean))
/* 259 */           throw new SocketException("bad parameter for SO_KEEPALIVE"); 
/* 260 */         bool = ((Boolean)paramObject).booleanValue();
/*     */         break;
/*     */       case 4099:
/* 263 */         if (paramObject == null || !(paramObject instanceof Boolean))
/* 264 */           throw new SocketException("bad parameter for SO_OOBINLINE"); 
/* 265 */         bool = ((Boolean)paramObject).booleanValue();
/*     */         break;
/*     */       case 4:
/* 268 */         if (paramObject == null || !(paramObject instanceof Boolean))
/* 269 */           throw new SocketException("bad parameter for SO_REUSEADDR"); 
/* 270 */         bool = ((Boolean)paramObject).booleanValue();
/*     */         break;
/*     */       default:
/* 273 */         throw new SocketException("unrecognized TCP option: " + paramInt);
/*     */     } 
/* 275 */     socketSetOption(paramInt, bool, paramObject);
/*     */   } public Object getOption(int paramInt) throws SocketException {
/*     */     InetAddressContainer inetAddressContainer;
/* 278 */     if (isClosedOrPending()) {
/* 279 */       throw new SocketException("Socket Closed");
/*     */     }
/* 281 */     if (paramInt == 4102) {
/* 282 */       return new Integer(this.timeout);
/*     */     }
/* 284 */     int i = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 293 */     switch (paramInt) {
/*     */       case 1:
/* 295 */         i = socketGetOption(paramInt, (Object)null);
/* 296 */         return Boolean.valueOf((i != -1));
/*     */       case 4099:
/* 298 */         i = socketGetOption(paramInt, (Object)null);
/* 299 */         return Boolean.valueOf((i != -1));
/*     */       case 128:
/* 301 */         i = socketGetOption(paramInt, (Object)null);
/* 302 */         return (i == -1) ? Boolean.FALSE : new Integer(i);
/*     */       case 4:
/* 304 */         i = socketGetOption(paramInt, (Object)null);
/* 305 */         return Boolean.valueOf((i != -1));
/*     */       case 15:
/* 307 */         inetAddressContainer = new InetAddressContainer();
/* 308 */         i = socketGetOption(paramInt, inetAddressContainer);
/* 309 */         return inetAddressContainer.addr;
/*     */       case 4097:
/*     */       case 4098:
/* 312 */         i = socketGetOption(paramInt, (Object)null);
/* 313 */         return new Integer(i);
/*     */       case 3:
/*     */         try {
/* 316 */           i = socketGetOption(paramInt, (Object)null);
/* 317 */           if (i == -1) {
/* 318 */             return Integer.valueOf(this.trafficClass);
/*     */           }
/* 320 */           return Integer.valueOf(i);
/*     */         }
/* 322 */         catch (SocketException socketException) {
/*     */           
/* 324 */           return Integer.valueOf(this.trafficClass);
/*     */         } 
/*     */       case 8:
/* 327 */         i = socketGetOption(paramInt, (Object)null);
/* 328 */         return Boolean.valueOf((i != -1));
/*     */     } 
/*     */     
/* 331 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   synchronized void doConnect(InetAddress paramInetAddress, int paramInt1, int paramInt2) throws IOException {
/* 342 */     synchronized (this.fdLock) {
/* 343 */       if (!this.closePending && (this.socket == null || !this.socket.isBound())) {
/* 344 */         NetHooks.beforeTcpConnect(this.fd, paramInetAddress, paramInt1);
/*     */       }
/*     */     } 
/*     */     try {
/* 348 */       acquireFD();
/*     */       try {
/* 350 */         socketConnect(paramInetAddress, paramInt1, paramInt2);
/*     */         
/* 352 */         synchronized (this.fdLock) {
/* 353 */           if (this.closePending) {
/* 354 */             throw new SocketException("Socket closed");
/*     */           }
/*     */         } 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 361 */         if (this.socket != null) {
/* 362 */           this.socket.setBound();
/* 363 */           this.socket.setConnected();
/*     */         } 
/*     */       } finally {
/* 366 */         releaseFD();
/*     */       } 
/* 368 */     } catch (IOException iOException) {
/* 369 */       close();
/* 370 */       throw iOException;
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
/*     */   protected synchronized void bind(InetAddress paramInetAddress, int paramInt) throws IOException {
/* 382 */     synchronized (this.fdLock) {
/* 383 */       if (!this.closePending && (this.socket == null || !this.socket.isBound())) {
/* 384 */         NetHooks.beforeTcpBind(this.fd, paramInetAddress, paramInt);
/*     */       }
/*     */     } 
/* 387 */     socketBind(paramInetAddress, paramInt);
/* 388 */     if (this.socket != null)
/* 389 */       this.socket.setBound(); 
/* 390 */     if (this.serverSocket != null) {
/* 391 */       this.serverSocket.setBound();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected synchronized void listen(int paramInt) throws IOException {
/* 399 */     socketListen(paramInt);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void accept(SocketImpl paramSocketImpl) throws IOException {
/* 407 */     acquireFD();
/*     */     try {
/* 409 */       socketAccept(paramSocketImpl);
/*     */     } finally {
/* 411 */       releaseFD();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected synchronized InputStream getInputStream() throws IOException {
/* 419 */     synchronized (this.fdLock) {
/* 420 */       if (isClosedOrPending())
/* 421 */         throw new IOException("Socket Closed"); 
/* 422 */       if (this.shut_rd)
/* 423 */         throw new IOException("Socket input is shutdown"); 
/* 424 */       if (this.socketInputStream == null)
/* 425 */         this.socketInputStream = new SocketInputStream(this); 
/*     */     } 
/* 427 */     return this.socketInputStream;
/*     */   }
/*     */   
/*     */   void setInputStream(SocketInputStream paramSocketInputStream) {
/* 431 */     this.socketInputStream = paramSocketInputStream;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected synchronized OutputStream getOutputStream() throws IOException {
/* 438 */     synchronized (this.fdLock) {
/* 439 */       if (isClosedOrPending())
/* 440 */         throw new IOException("Socket Closed"); 
/* 441 */       if (this.shut_wr)
/* 442 */         throw new IOException("Socket output is shutdown"); 
/* 443 */       if (this.socketOutputStream == null)
/* 444 */         this.socketOutputStream = new SocketOutputStream(this); 
/*     */     } 
/* 446 */     return this.socketOutputStream;
/*     */   }
/*     */   
/*     */   void setFileDescriptor(FileDescriptor paramFileDescriptor) {
/* 450 */     this.fd = paramFileDescriptor;
/*     */   }
/*     */   
/*     */   void setAddress(InetAddress paramInetAddress) {
/* 454 */     this.address = paramInetAddress;
/*     */   }
/*     */   
/*     */   void setPort(int paramInt) {
/* 458 */     this.port = paramInt;
/*     */   }
/*     */   
/*     */   void setLocalPort(int paramInt) {
/* 462 */     this.localport = paramInt;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected synchronized int available() throws IOException {
/* 469 */     if (isClosedOrPending()) {
/* 470 */       throw new IOException("Stream closed.");
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 477 */     if (isConnectionReset() || this.shut_rd) {
/* 478 */       return 0;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 488 */     int i = 0;
/*     */     try {
/* 490 */       i = socketAvailable();
/* 491 */       if (i == 0 && isConnectionResetPending()) {
/* 492 */         setConnectionReset();
/*     */       }
/* 494 */     } catch (ConnectionResetException connectionResetException) {
/* 495 */       setConnectionResetPending();
/*     */       try {
/* 497 */         i = socketAvailable();
/* 498 */         if (i == 0) {
/* 499 */           setConnectionReset();
/*     */         }
/* 501 */       } catch (ConnectionResetException connectionResetException1) {}
/*     */     } 
/*     */     
/* 504 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void close() throws IOException {
/* 511 */     synchronized (this.fdLock) {
/* 512 */       if (this.fd != null) {
/* 513 */         if (!this.stream) {
/* 514 */           ResourceManager.afterUdpClose();
/*     */         }
/* 516 */         if (this.fdUseCount == 0) {
/* 517 */           if (this.closePending) {
/*     */             return;
/*     */           }
/* 520 */           this.closePending = true;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           try {
/* 530 */             socketPreClose();
/*     */           } finally {
/* 532 */             socketClose();
/*     */           } 
/* 534 */           this.fd = null;
/*     */ 
/*     */ 
/*     */           
/*     */           return;
/*     */         } 
/*     */ 
/*     */ 
/*     */         
/* 543 */         if (!this.closePending) {
/* 544 */           this.closePending = true;
/* 545 */           this.fdUseCount--;
/* 546 */           socketPreClose();
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   void reset() throws IOException {
/* 554 */     if (this.fd != null) {
/* 555 */       socketClose();
/*     */     }
/* 557 */     this.fd = null;
/* 558 */     super.reset();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void shutdownInput() throws IOException {
/* 566 */     if (this.fd != null) {
/* 567 */       socketShutdown(0);
/* 568 */       if (this.socketInputStream != null) {
/* 569 */         this.socketInputStream.setEOF(true);
/*     */       }
/* 571 */       this.shut_rd = true;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void shutdownOutput() throws IOException {
/* 579 */     if (this.fd != null) {
/* 580 */       socketShutdown(1);
/* 581 */       this.shut_wr = true;
/*     */     } 
/*     */   }
/*     */   
/*     */   protected boolean supportsUrgentData() {
/* 586 */     return true;
/*     */   }
/*     */   
/*     */   protected void sendUrgentData(int paramInt) throws IOException {
/* 590 */     if (this.fd == null) {
/* 591 */       throw new IOException("Socket Closed");
/*     */     }
/* 593 */     socketSendUrgentData(paramInt);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void finalize() throws IOException {
/* 600 */     close();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   FileDescriptor acquireFD() {
/* 610 */     synchronized (this.fdLock) {
/* 611 */       this.fdUseCount++;
/* 612 */       return this.fd;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void releaseFD() {
/* 622 */     synchronized (this.fdLock) {
/* 623 */       this.fdUseCount--;
/* 624 */       if (this.fdUseCount == -1 && 
/* 625 */         this.fd != null) {
/*     */         
/* 627 */         try { socketClose(); }
/* 628 */         catch (IOException iOException) {  }
/*     */         finally
/* 630 */         { this.fd = null; }
/*     */       
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isConnectionReset() {
/* 638 */     synchronized (this.resetLock) {
/* 639 */       return (this.resetState == this.CONNECTION_RESET);
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean isConnectionResetPending() {
/* 644 */     synchronized (this.resetLock) {
/* 645 */       return (this.resetState == this.CONNECTION_RESET_PENDING);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setConnectionReset() {
/* 650 */     synchronized (this.resetLock) {
/* 651 */       this.resetState = this.CONNECTION_RESET;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setConnectionResetPending() {
/* 656 */     synchronized (this.resetLock) {
/* 657 */       if (this.resetState == this.CONNECTION_NOT_RESET) {
/* 658 */         this.resetState = this.CONNECTION_RESET_PENDING;
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
/*     */   
/*     */   public boolean isClosedOrPending() {
/* 672 */     synchronized (this.fdLock) {
/* 673 */       if (this.closePending || this.fd == null) {
/* 674 */         return true;
/*     */       }
/* 676 */       return false;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getTimeout() {
/* 685 */     return this.timeout;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void socketPreClose() throws IOException {
/* 693 */     socketClose0(true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void socketClose() throws IOException {
/* 700 */     socketClose0(false);
/*     */   }
/*     */   
/*     */   abstract void socketCreate(boolean paramBoolean) throws IOException;
/*     */   
/*     */   abstract void socketConnect(InetAddress paramInetAddress, int paramInt1, int paramInt2) throws IOException;
/*     */   
/*     */   abstract void socketBind(InetAddress paramInetAddress, int paramInt) throws IOException;
/*     */   
/*     */   abstract void socketListen(int paramInt) throws IOException;
/*     */   
/*     */   abstract void socketAccept(SocketImpl paramSocketImpl) throws IOException;
/*     */   
/*     */   abstract int socketAvailable() throws IOException;
/*     */   
/*     */   abstract void socketClose0(boolean paramBoolean) throws IOException;
/*     */   
/*     */   abstract void socketShutdown(int paramInt) throws IOException;
/*     */   
/*     */   abstract void socketSetOption(int paramInt, boolean paramBoolean, Object paramObject) throws SocketException;
/*     */   
/*     */   abstract int socketGetOption(int paramInt, Object paramObject) throws SocketException;
/*     */   
/*     */   abstract void socketSendUrgentData(int paramInt) throws IOException;
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/net/AbstractPlainSocketImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */