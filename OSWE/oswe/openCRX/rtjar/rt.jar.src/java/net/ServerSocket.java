/*     */ package java.net;
/*     */ 
/*     */ import java.io.Closeable;
/*     */ import java.io.FileDescriptor;
/*     */ import java.io.IOException;
/*     */ import java.nio.channels.ServerSocketChannel;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedActionException;
/*     */ import java.security.PrivilegedExceptionAction;
/*     */ import sun.security.util.SecurityConstants;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ServerSocket
/*     */   implements Closeable
/*     */ {
/*     */   private boolean created = false;
/*     */   private boolean bound = false;
/*     */   private boolean closed = false;
/*  61 */   private Object closeLock = new Object();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private SocketImpl impl;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean oldImpl = false;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   ServerSocket(SocketImpl paramSocketImpl) {
/*  82 */     checkPermission();
/*  83 */     this.impl = paramSocketImpl;
/*  84 */     paramSocketImpl.setServerSocket(this);
/*     */   }
/*     */   
/*     */   private static Void checkPermission() {
/*  88 */     SecurityManager securityManager = System.getSecurityManager();
/*  89 */     if (securityManager != null) {
/*  90 */       securityManager.checkPermission(SecurityConstants.SET_SOCKETIMPL_PERMISSION);
/*     */     }
/*  92 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ServerSocket() throws IOException {
/* 102 */     setImpl();
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ServerSocket(int paramInt) throws IOException {
/* 143 */     this(paramInt, 50, null);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ServerSocket(int paramInt1, int paramInt2) throws IOException {
/* 196 */     this(paramInt1, paramInt2, null);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ServerSocket(int paramInt1, int paramInt2, InetAddress paramInetAddress) throws IOException {
/* 245 */     setImpl();
/* 246 */     if (paramInt1 < 0 || paramInt1 > 65535) {
/* 247 */       throw new IllegalArgumentException("Port value out of range: " + paramInt1);
/*     */     }
/* 249 */     if (paramInt2 < 1)
/* 250 */       paramInt2 = 50; 
/*     */     try {
/* 252 */       bind(new InetSocketAddress(paramInetAddress, paramInt1), paramInt2);
/* 253 */     } catch (SecurityException securityException) {
/* 254 */       close();
/* 255 */       throw securityException;
/* 256 */     } catch (IOException iOException) {
/* 257 */       close();
/* 258 */       throw iOException;
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
/*     */   SocketImpl getImpl() throws SocketException {
/* 271 */     if (!this.created)
/* 272 */       createImpl(); 
/* 273 */     return this.impl;
/*     */   }
/*     */   
/*     */   private void checkOldImpl() {
/* 277 */     if (this.impl == null) {
/*     */       return;
/*     */     }
/*     */     
/*     */     try {
/* 282 */       AccessController.doPrivileged(new PrivilegedExceptionAction<Void>()
/*     */           {
/*     */             public Void run() throws NoSuchMethodException {
/* 285 */               ServerSocket.this.impl.getClass().getDeclaredMethod("connect", new Class[] { SocketAddress.class, int.class });
/*     */ 
/*     */               
/* 288 */               return null;
/*     */             }
/*     */           });
/* 291 */     } catch (PrivilegedActionException privilegedActionException) {
/* 292 */       this.oldImpl = true;
/*     */     } 
/*     */   }
/*     */   
/*     */   private void setImpl() {
/* 297 */     if (factory != null) {
/* 298 */       this.impl = factory.createSocketImpl();
/* 299 */       checkOldImpl();
/*     */     }
/*     */     else {
/*     */       
/* 303 */       this.impl = new SocksSocketImpl();
/*     */     } 
/* 305 */     if (this.impl != null) {
/* 306 */       this.impl.setServerSocket(this);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void createImpl() throws SocketException {
/* 316 */     if (this.impl == null)
/* 317 */       setImpl(); 
/*     */     try {
/* 319 */       this.impl.create(true);
/* 320 */       this.created = true;
/* 321 */     } catch (IOException iOException) {
/* 322 */       throw new SocketException(iOException.getMessage());
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void bind(SocketAddress paramSocketAddress) throws IOException {
/* 344 */     bind(paramSocketAddress, 50);
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
/*     */ 
/*     */   
/*     */   public void bind(SocketAddress paramSocketAddress, int paramInt) throws IOException {
/* 373 */     if (isClosed())
/* 374 */       throw new SocketException("Socket is closed"); 
/* 375 */     if (!this.oldImpl && isBound())
/* 376 */       throw new SocketException("Already bound"); 
/* 377 */     if (paramSocketAddress == null)
/* 378 */       paramSocketAddress = new InetSocketAddress(0); 
/* 379 */     if (!(paramSocketAddress instanceof InetSocketAddress))
/* 380 */       throw new IllegalArgumentException("Unsupported address type"); 
/* 381 */     InetSocketAddress inetSocketAddress = (InetSocketAddress)paramSocketAddress;
/* 382 */     if (inetSocketAddress.isUnresolved())
/* 383 */       throw new SocketException("Unresolved address"); 
/* 384 */     if (paramInt < 1)
/* 385 */       paramInt = 50; 
/*     */     try {
/* 387 */       SecurityManager securityManager = System.getSecurityManager();
/* 388 */       if (securityManager != null)
/* 389 */         securityManager.checkListen(inetSocketAddress.getPort()); 
/* 390 */       getImpl().bind(inetSocketAddress.getAddress(), inetSocketAddress.getPort());
/* 391 */       getImpl().listen(paramInt);
/* 392 */       this.bound = true;
/* 393 */     } catch (SecurityException securityException) {
/* 394 */       this.bound = false;
/* 395 */       throw securityException;
/* 396 */     } catch (IOException iOException) {
/* 397 */       this.bound = false;
/* 398 */       throw iOException;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public InetAddress getInetAddress() {
/* 421 */     if (!isBound())
/* 422 */       return null; 
/*     */     try {
/* 424 */       InetAddress inetAddress = getImpl().getInetAddress();
/* 425 */       SecurityManager securityManager = System.getSecurityManager();
/* 426 */       if (securityManager != null)
/* 427 */         securityManager.checkConnect(inetAddress.getHostAddress(), -1); 
/* 428 */       return inetAddress;
/* 429 */     } catch (SecurityException securityException) {
/* 430 */       return InetAddress.getLoopbackAddress();
/* 431 */     } catch (SocketException socketException) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 436 */       return null;
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
/*     */   public int getLocalPort() {
/* 450 */     if (!isBound())
/* 451 */       return -1; 
/*     */     try {
/* 453 */       return getImpl().getLocalPort();
/* 454 */     } catch (SocketException socketException) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 459 */       return -1;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SocketAddress getLocalSocketAddress() {
/* 489 */     if (!isBound())
/* 490 */       return null; 
/* 491 */     return new InetSocketAddress(getInetAddress(), getLocalPort());
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Socket accept() throws IOException {
/* 523 */     if (isClosed())
/* 524 */       throw new SocketException("Socket is closed"); 
/* 525 */     if (!isBound())
/* 526 */       throw new SocketException("Socket is not bound yet"); 
/* 527 */     Socket socket = new Socket((SocketImpl)null);
/* 528 */     implAccept(socket);
/* 529 */     return socket;
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
/*     */   protected final void implAccept(Socket paramSocket) throws IOException {
/* 549 */     SocketImpl socketImpl = null;
/*     */     try {
/* 551 */       if (paramSocket.impl == null) {
/* 552 */         paramSocket.setImpl();
/*     */       } else {
/* 554 */         paramSocket.impl.reset();
/*     */       } 
/* 556 */       socketImpl = paramSocket.impl;
/* 557 */       paramSocket.impl = null;
/* 558 */       socketImpl.address = new InetAddress();
/* 559 */       socketImpl.fd = new FileDescriptor();
/* 560 */       getImpl().accept(socketImpl);
/*     */       
/* 562 */       SecurityManager securityManager = System.getSecurityManager();
/* 563 */       if (securityManager != null) {
/* 564 */         securityManager.checkAccept(socketImpl.getInetAddress().getHostAddress(), socketImpl
/* 565 */             .getPort());
/*     */       }
/* 567 */     } catch (IOException iOException) {
/* 568 */       if (socketImpl != null)
/* 569 */         socketImpl.reset(); 
/* 570 */       paramSocket.impl = socketImpl;
/* 571 */       throw iOException;
/* 572 */     } catch (SecurityException securityException) {
/* 573 */       if (socketImpl != null)
/* 574 */         socketImpl.reset(); 
/* 575 */       paramSocket.impl = socketImpl;
/* 576 */       throw securityException;
/*     */     } 
/* 578 */     paramSocket.impl = socketImpl;
/* 579 */     paramSocket.postAccept();
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
/*     */   public void close() throws IOException {
/* 596 */     synchronized (this.closeLock) {
/* 597 */       if (isClosed())
/*     */         return; 
/* 599 */       if (this.created)
/* 600 */         this.impl.close(); 
/* 601 */       this.closed = true;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ServerSocketChannel getChannel() {
/* 622 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isBound() {
/* 633 */     return (this.bound || this.oldImpl);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isClosed() {
/* 643 */     synchronized (this.closeLock) {
/* 644 */       return this.closed;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void setSoTimeout(int paramInt) throws SocketException {
/* 665 */     if (isClosed())
/* 666 */       throw new SocketException("Socket is closed"); 
/* 667 */     getImpl().setOption(4102, new Integer(paramInt));
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
/*     */   public synchronized int getSoTimeout() throws IOException {
/* 679 */     if (isClosed())
/* 680 */       throw new SocketException("Socket is closed"); 
/* 681 */     Object object = getImpl().getOption(4102);
/*     */     
/* 683 */     if (object instanceof Integer) {
/* 684 */       return ((Integer)object).intValue();
/*     */     }
/* 686 */     return 0;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setReuseAddress(boolean paramBoolean) throws SocketException {
/* 727 */     if (isClosed())
/* 728 */       throw new SocketException("Socket is closed"); 
/* 729 */     getImpl().setOption(4, Boolean.valueOf(paramBoolean));
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
/*     */   public boolean getReuseAddress() throws SocketException {
/* 743 */     if (isClosed())
/* 744 */       throw new SocketException("Socket is closed"); 
/* 745 */     return ((Boolean)getImpl().getOption(4)).booleanValue();
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
/*     */   public String toString() {
/*     */     InetAddress inetAddress;
/* 762 */     if (!isBound()) {
/* 763 */       return "ServerSocket[unbound]";
/*     */     }
/* 765 */     if (System.getSecurityManager() != null) {
/* 766 */       inetAddress = InetAddress.getLoopbackAddress();
/*     */     } else {
/* 768 */       inetAddress = this.impl.getInetAddress();
/* 769 */     }  return "ServerSocket[addr=" + inetAddress + ",localport=" + this.impl
/* 770 */       .getLocalPort() + "]";
/*     */   }
/*     */   
/*     */   void setBound() {
/* 774 */     this.bound = true;
/*     */   }
/*     */   
/*     */   void setCreated() {
/* 778 */     this.created = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 784 */   private static SocketImplFactory factory = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static synchronized void setSocketFactory(SocketImplFactory paramSocketImplFactory) throws IOException {
/* 812 */     if (factory != null) {
/* 813 */       throw new SocketException("factory already defined");
/*     */     }
/* 815 */     SecurityManager securityManager = System.getSecurityManager();
/* 816 */     if (securityManager != null) {
/* 817 */       securityManager.checkSetFactory();
/*     */     }
/* 819 */     factory = paramSocketImplFactory;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void setReceiveBufferSize(int paramInt) throws SocketException {
/* 859 */     if (paramInt <= 0) {
/* 860 */       throw new IllegalArgumentException("negative receive size");
/*     */     }
/* 862 */     if (isClosed())
/* 863 */       throw new SocketException("Socket is closed"); 
/* 864 */     getImpl().setOption(4098, new Integer(paramInt));
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
/*     */   public synchronized int getReceiveBufferSize() throws SocketException {
/* 883 */     if (isClosed())
/* 884 */       throw new SocketException("Socket is closed"); 
/* 885 */     int i = 0;
/* 886 */     Object object = getImpl().getOption(4098);
/* 887 */     if (object instanceof Integer) {
/* 888 */       i = ((Integer)object).intValue();
/*     */     }
/* 890 */     return i;
/*     */   }
/*     */   
/*     */   public void setPerformancePreferences(int paramInt1, int paramInt2, int paramInt3) {}
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/net/ServerSocket.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */