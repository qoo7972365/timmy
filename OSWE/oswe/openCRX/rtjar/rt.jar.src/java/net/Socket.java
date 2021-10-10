/*      */ package java.net;
/*      */ 
/*      */ import java.io.Closeable;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.io.OutputStream;
/*      */ import java.nio.channels.SocketChannel;
/*      */ import java.security.AccessController;
/*      */ import java.security.PrivilegedAction;
/*      */ import java.security.PrivilegedActionException;
/*      */ import java.security.PrivilegedExceptionAction;
/*      */ import sun.net.ApplicationProxy;
/*      */ import sun.security.util.SecurityConstants;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class Socket
/*      */   implements Closeable
/*      */ {
/*      */   private boolean created = false;
/*      */   private boolean bound = false;
/*      */   private boolean connected = false;
/*      */   private boolean closed = false;
/*   64 */   private Object closeLock = new Object();
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean shutIn = false;
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean shutOut = false;
/*      */ 
/*      */ 
/*      */   
/*      */   SocketImpl impl;
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean oldImpl = false;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Socket() {
/*   86 */     setImpl();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Socket(Proxy paramProxy) {
/*  119 */     if (paramProxy == null) {
/*  120 */       throw new IllegalArgumentException("Invalid Proxy");
/*      */     }
/*      */     
/*  123 */     Proxy proxy = (paramProxy == Proxy.NO_PROXY) ? Proxy.NO_PROXY : ApplicationProxy.create(paramProxy);
/*  124 */     Proxy.Type type = proxy.type();
/*  125 */     if (type == Proxy.Type.SOCKS || type == Proxy.Type.HTTP)
/*  126 */     { SecurityManager securityManager = System.getSecurityManager();
/*  127 */       InetSocketAddress inetSocketAddress = (InetSocketAddress)proxy.address();
/*  128 */       if (inetSocketAddress.getAddress() != null) {
/*  129 */         checkAddress(inetSocketAddress.getAddress(), "Socket");
/*      */       }
/*  131 */       if (securityManager != null) {
/*  132 */         if (inetSocketAddress.isUnresolved())
/*  133 */           inetSocketAddress = new InetSocketAddress(inetSocketAddress.getHostName(), inetSocketAddress.getPort()); 
/*  134 */         if (inetSocketAddress.isUnresolved()) {
/*  135 */           securityManager.checkConnect(inetSocketAddress.getHostName(), inetSocketAddress.getPort());
/*      */         } else {
/*  137 */           securityManager.checkConnect(inetSocketAddress.getAddress().getHostAddress(), inetSocketAddress
/*  138 */               .getPort());
/*      */         } 
/*  140 */       }  this.impl = (type == Proxy.Type.SOCKS) ? new SocksSocketImpl(proxy) : new HttpConnectSocketImpl(proxy);
/*      */       
/*  142 */       this.impl.setSocket(this); }
/*      */     
/*  144 */     else if (proxy == Proxy.NO_PROXY)
/*  145 */     { if (factory == null) {
/*  146 */         this.impl = new PlainSocketImpl();
/*  147 */         this.impl.setSocket(this);
/*      */       } else {
/*  149 */         setImpl();
/*      */       }  }
/*  151 */     else { throw new IllegalArgumentException("Invalid Proxy"); }
/*      */   
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Socket(SocketImpl paramSocketImpl) throws SocketException {
/*  171 */     checkPermission(paramSocketImpl);
/*  172 */     this.impl = paramSocketImpl;
/*  173 */     if (paramSocketImpl != null) {
/*  174 */       checkOldImpl();
/*  175 */       this.impl.setSocket(this);
/*      */     } 
/*      */   }
/*      */   
/*      */   private static Void checkPermission(SocketImpl paramSocketImpl) {
/*  180 */     if (paramSocketImpl == null) {
/*  181 */       return null;
/*      */     }
/*  183 */     SecurityManager securityManager = System.getSecurityManager();
/*  184 */     if (securityManager != null) {
/*  185 */       securityManager.checkPermission(SecurityConstants.SET_SOCKETIMPL_PERMISSION);
/*      */     }
/*  187 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Socket(String paramString, int paramInt) throws UnknownHostException, IOException {
/*  229 */     this((paramString != null) ? new InetSocketAddress(paramString, paramInt) : new InetSocketAddress(
/*  230 */           InetAddress.getByName(null), paramInt), (SocketAddress)null, true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Socket(InetAddress paramInetAddress, int paramInt) throws IOException {
/*  262 */     this((paramInetAddress != null) ? new InetSocketAddress(paramInetAddress, paramInt) : null, (SocketAddress)null, true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Socket(String paramString, int paramInt1, InetAddress paramInetAddress, int paramInt2) throws IOException {
/*  304 */     this((paramString != null) ? new InetSocketAddress(paramString, paramInt1) : new InetSocketAddress(
/*  305 */           InetAddress.getByName(null), paramInt1), new InetSocketAddress(paramInetAddress, paramInt2), true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Socket(InetAddress paramInetAddress1, int paramInt1, InetAddress paramInetAddress2, int paramInt2) throws IOException {
/*  346 */     this((paramInetAddress1 != null) ? new InetSocketAddress(paramInetAddress1, paramInt1) : null, new InetSocketAddress(paramInetAddress2, paramInt2), true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public Socket(String paramString, int paramInt, boolean paramBoolean) throws IOException {
/*  393 */     this((paramString != null) ? new InetSocketAddress(paramString, paramInt) : new InetSocketAddress(
/*  394 */           InetAddress.getByName(null), paramInt), (SocketAddress)null, paramBoolean);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public Socket(InetAddress paramInetAddress, int paramInt, boolean paramBoolean) throws IOException {
/*  436 */     this((paramInetAddress != null) ? new InetSocketAddress(paramInetAddress, paramInt) : null, new InetSocketAddress(0), paramBoolean);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private Socket(SocketAddress paramSocketAddress1, SocketAddress paramSocketAddress2, boolean paramBoolean) throws IOException {
/*  442 */     setImpl();
/*      */ 
/*      */     
/*  445 */     if (paramSocketAddress1 == null) {
/*  446 */       throw new NullPointerException();
/*      */     }
/*      */     try {
/*  449 */       createImpl(paramBoolean);
/*  450 */       if (paramSocketAddress2 != null)
/*  451 */         bind(paramSocketAddress2); 
/*  452 */       connect(paramSocketAddress1);
/*  453 */     } catch (IOException|IllegalArgumentException|SecurityException iOException) {
/*      */       try {
/*  455 */         close();
/*  456 */       } catch (IOException iOException1) {
/*  457 */         iOException.addSuppressed(iOException1);
/*      */       } 
/*  459 */       throw iOException;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void createImpl(boolean paramBoolean) throws SocketException {
/*  472 */     if (this.impl == null)
/*  473 */       setImpl(); 
/*      */     try {
/*  475 */       this.impl.create(paramBoolean);
/*  476 */       this.created = true;
/*  477 */     } catch (IOException iOException) {
/*  478 */       throw new SocketException(iOException.getMessage());
/*      */     } 
/*      */   }
/*      */   
/*      */   private void checkOldImpl() {
/*  483 */     if (this.impl == null) {
/*      */       return;
/*      */     }
/*      */ 
/*      */     
/*  488 */     this
/*  489 */       .oldImpl = ((Boolean)AccessController.<Boolean>doPrivileged(new PrivilegedAction<Boolean>() {
/*      */           public Boolean run() {
/*  491 */             Class<?> clazz = Socket.this.impl.getClass();
/*      */             while (true) {
/*      */               try {
/*  494 */                 clazz.getDeclaredMethod("connect", new Class[] { SocketAddress.class, int.class });
/*  495 */                 return Boolean.FALSE;
/*  496 */               } catch (NoSuchMethodException noSuchMethodException) {
/*  497 */                 clazz = clazz.getSuperclass();
/*      */ 
/*      */ 
/*      */                 
/*  501 */                 if (clazz.equals(SocketImpl.class)) {
/*  502 */                   return Boolean.TRUE;
/*      */                 }
/*      */               } 
/*      */             } 
/*      */           }
/*      */         })).booleanValue();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void setImpl() {
/*  515 */     if (factory != null) {
/*  516 */       this.impl = factory.createSocketImpl();
/*  517 */       checkOldImpl();
/*      */     }
/*      */     else {
/*      */       
/*  521 */       this.impl = new SocksSocketImpl();
/*      */     } 
/*  523 */     if (this.impl != null) {
/*  524 */       this.impl.setSocket(this);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   SocketImpl getImpl() throws SocketException {
/*  537 */     if (!this.created)
/*  538 */       createImpl(true); 
/*  539 */     return this.impl;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void connect(SocketAddress paramSocketAddress) throws IOException {
/*  556 */     connect(paramSocketAddress, 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void connect(SocketAddress paramSocketAddress, int paramInt) throws IOException {
/*  577 */     if (paramSocketAddress == null) {
/*  578 */       throw new IllegalArgumentException("connect: The address can't be null");
/*      */     }
/*  580 */     if (paramInt < 0) {
/*  581 */       throw new IllegalArgumentException("connect: timeout can't be negative");
/*      */     }
/*  583 */     if (isClosed()) {
/*  584 */       throw new SocketException("Socket is closed");
/*      */     }
/*  586 */     if (!this.oldImpl && isConnected()) {
/*  587 */       throw new SocketException("already connected");
/*      */     }
/*  589 */     if (!(paramSocketAddress instanceof InetSocketAddress)) {
/*  590 */       throw new IllegalArgumentException("Unsupported address type");
/*      */     }
/*  592 */     InetSocketAddress inetSocketAddress = (InetSocketAddress)paramSocketAddress;
/*  593 */     InetAddress inetAddress = inetSocketAddress.getAddress();
/*  594 */     int i = inetSocketAddress.getPort();
/*  595 */     checkAddress(inetAddress, "connect");
/*      */     
/*  597 */     SecurityManager securityManager = System.getSecurityManager();
/*  598 */     if (securityManager != null)
/*  599 */       if (inetSocketAddress.isUnresolved()) {
/*  600 */         securityManager.checkConnect(inetSocketAddress.getHostName(), i);
/*      */       } else {
/*  602 */         securityManager.checkConnect(inetAddress.getHostAddress(), i);
/*      */       }  
/*  604 */     if (!this.created)
/*  605 */       createImpl(true); 
/*  606 */     if (!this.oldImpl)
/*  607 */     { this.impl.connect(inetSocketAddress, paramInt); }
/*  608 */     else if (paramInt == 0)
/*  609 */     { if (inetSocketAddress.isUnresolved()) {
/*  610 */         this.impl.connect(inetAddress.getHostName(), i);
/*      */       } else {
/*  612 */         this.impl.connect(inetAddress, i);
/*      */       }  }
/*  614 */     else { throw new UnsupportedOperationException("SocketImpl.connect(addr, timeout)"); }
/*  615 */      this.connected = true;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  620 */     this.bound = true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void bind(SocketAddress paramSocketAddress) throws IOException {
/*  642 */     if (isClosed())
/*  643 */       throw new SocketException("Socket is closed"); 
/*  644 */     if (!this.oldImpl && isBound()) {
/*  645 */       throw new SocketException("Already bound");
/*      */     }
/*  647 */     if (paramSocketAddress != null && !(paramSocketAddress instanceof InetSocketAddress))
/*  648 */       throw new IllegalArgumentException("Unsupported address type"); 
/*  649 */     InetSocketAddress inetSocketAddress = (InetSocketAddress)paramSocketAddress;
/*  650 */     if (inetSocketAddress != null && inetSocketAddress.isUnresolved())
/*  651 */       throw new SocketException("Unresolved address"); 
/*  652 */     if (inetSocketAddress == null) {
/*  653 */       inetSocketAddress = new InetSocketAddress(0);
/*      */     }
/*  655 */     InetAddress inetAddress = inetSocketAddress.getAddress();
/*  656 */     int i = inetSocketAddress.getPort();
/*  657 */     checkAddress(inetAddress, "bind");
/*  658 */     SecurityManager securityManager = System.getSecurityManager();
/*  659 */     if (securityManager != null) {
/*  660 */       securityManager.checkListen(i);
/*      */     }
/*  662 */     getImpl().bind(inetAddress, i);
/*  663 */     this.bound = true;
/*      */   }
/*      */   
/*      */   private void checkAddress(InetAddress paramInetAddress, String paramString) {
/*  667 */     if (paramInetAddress == null) {
/*      */       return;
/*      */     }
/*  670 */     if (!(paramInetAddress instanceof Inet4Address) && !(paramInetAddress instanceof Inet6Address)) {
/*  671 */       throw new IllegalArgumentException(paramString + ": invalid address type");
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   final void postAccept() {
/*  679 */     this.connected = true;
/*  680 */     this.created = true;
/*  681 */     this.bound = true;
/*      */   }
/*      */   
/*      */   void setCreated() {
/*  685 */     this.created = true;
/*      */   }
/*      */   
/*      */   void setBound() {
/*  689 */     this.bound = true;
/*      */   }
/*      */   
/*      */   void setConnected() {
/*  693 */     this.connected = true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public InetAddress getInetAddress() {
/*  707 */     if (!isConnected())
/*  708 */       return null; 
/*      */     try {
/*  710 */       return getImpl().getInetAddress();
/*  711 */     } catch (SocketException socketException) {
/*      */       
/*  713 */       return null;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public InetAddress getLocalAddress() {
/*  733 */     if (!isBound())
/*  734 */       return InetAddress.anyLocalAddress(); 
/*  735 */     InetAddress inetAddress = null;
/*      */     try {
/*  737 */       inetAddress = (InetAddress)getImpl().getOption(15);
/*  738 */       SecurityManager securityManager = System.getSecurityManager();
/*  739 */       if (securityManager != null)
/*  740 */         securityManager.checkConnect(inetAddress.getHostAddress(), -1); 
/*  741 */       if (inetAddress.isAnyLocalAddress()) {
/*  742 */         inetAddress = InetAddress.anyLocalAddress();
/*      */       }
/*  744 */     } catch (SecurityException securityException) {
/*  745 */       inetAddress = InetAddress.getLoopbackAddress();
/*  746 */     } catch (Exception exception) {
/*  747 */       inetAddress = InetAddress.anyLocalAddress();
/*      */     } 
/*  749 */     return inetAddress;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getPort() {
/*  763 */     if (!isConnected())
/*  764 */       return 0; 
/*      */     try {
/*  766 */       return getImpl().getPort();
/*  767 */     } catch (SocketException socketException) {
/*      */ 
/*      */       
/*  770 */       return -1;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getLocalPort() {
/*  784 */     if (!isBound())
/*  785 */       return -1; 
/*      */     try {
/*  787 */       return getImpl().getLocalPort();
/*  788 */     } catch (SocketException socketException) {
/*      */ 
/*      */       
/*  791 */       return -1;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public SocketAddress getRemoteSocketAddress() {
/*  812 */     if (!isConnected())
/*  813 */       return null; 
/*  814 */     return new InetSocketAddress(getInetAddress(), getPort());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public SocketAddress getLocalSocketAddress() {
/*  848 */     if (!isBound())
/*  849 */       return null; 
/*  850 */     return new InetSocketAddress(getLocalAddress(), getLocalPort());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public SocketChannel getChannel() {
/*  871 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public InputStream getInputStream() throws IOException {
/*  920 */     if (isClosed())
/*  921 */       throw new SocketException("Socket is closed"); 
/*  922 */     if (!isConnected())
/*  923 */       throw new SocketException("Socket is not connected"); 
/*  924 */     if (isInputShutdown())
/*  925 */       throw new SocketException("Socket input is shutdown"); 
/*  926 */     Socket socket = this;
/*  927 */     InputStream inputStream = null;
/*      */     try {
/*  929 */       inputStream = AccessController.<InputStream>doPrivileged(new PrivilegedExceptionAction<InputStream>()
/*      */           {
/*      */             public InputStream run() throws IOException {
/*  932 */               return Socket.this.impl.getInputStream();
/*      */             }
/*      */           });
/*  935 */     } catch (PrivilegedActionException privilegedActionException) {
/*  936 */       throw (IOException)privilegedActionException.getException();
/*      */     } 
/*  938 */     return inputStream;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public OutputStream getOutputStream() throws IOException {
/*  960 */     if (isClosed())
/*  961 */       throw new SocketException("Socket is closed"); 
/*  962 */     if (!isConnected())
/*  963 */       throw new SocketException("Socket is not connected"); 
/*  964 */     if (isOutputShutdown())
/*  965 */       throw new SocketException("Socket output is shutdown"); 
/*  966 */     Socket socket = this;
/*  967 */     OutputStream outputStream = null;
/*      */     try {
/*  969 */       outputStream = AccessController.<OutputStream>doPrivileged(new PrivilegedExceptionAction<OutputStream>()
/*      */           {
/*      */             public OutputStream run() throws IOException {
/*  972 */               return Socket.this.impl.getOutputStream();
/*      */             }
/*      */           });
/*  975 */     } catch (PrivilegedActionException privilegedActionException) {
/*  976 */       throw (IOException)privilegedActionException.getException();
/*      */     } 
/*  978 */     return outputStream;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setTcpNoDelay(boolean paramBoolean) throws SocketException {
/*  996 */     if (isClosed())
/*  997 */       throw new SocketException("Socket is closed"); 
/*  998 */     getImpl().setOption(1, Boolean.valueOf(paramBoolean));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getTcpNoDelay() throws SocketException {
/* 1012 */     if (isClosed())
/* 1013 */       throw new SocketException("Socket is closed"); 
/* 1014 */     return ((Boolean)getImpl().getOption(1)).booleanValue();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setSoLinger(boolean paramBoolean, int paramInt) throws SocketException {
/* 1033 */     if (isClosed())
/* 1034 */       throw new SocketException("Socket is closed"); 
/* 1035 */     if (!paramBoolean) {
/* 1036 */       getImpl().setOption(128, new Boolean(paramBoolean));
/*      */     } else {
/* 1038 */       if (paramInt < 0) {
/* 1039 */         throw new IllegalArgumentException("invalid value for SO_LINGER");
/*      */       }
/* 1041 */       if (paramInt > 65535)
/* 1042 */         paramInt = 65535; 
/* 1043 */       getImpl().setOption(128, new Integer(paramInt));
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getSoLinger() throws SocketException {
/* 1061 */     if (isClosed())
/* 1062 */       throw new SocketException("Socket is closed"); 
/* 1063 */     Object object = getImpl().getOption(128);
/* 1064 */     if (object instanceof Integer) {
/* 1065 */       return ((Integer)object).intValue();
/*      */     }
/* 1067 */     return -1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void sendUrgentData(int paramInt) throws IOException {
/* 1082 */     if (!getImpl().supportsUrgentData()) {
/* 1083 */       throw new SocketException("Urgent data not supported");
/*      */     }
/* 1085 */     getImpl().sendUrgentData(paramInt);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setOOBInline(boolean paramBoolean) throws SocketException {
/* 1114 */     if (isClosed())
/* 1115 */       throw new SocketException("Socket is closed"); 
/* 1116 */     getImpl().setOption(4099, Boolean.valueOf(paramBoolean));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getOOBInline() throws SocketException {
/* 1131 */     if (isClosed())
/* 1132 */       throw new SocketException("Socket is closed"); 
/* 1133 */     return ((Boolean)getImpl().getOption(4099)).booleanValue();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized void setSoTimeout(int paramInt) throws SocketException {
/* 1154 */     if (isClosed())
/* 1155 */       throw new SocketException("Socket is closed"); 
/* 1156 */     if (paramInt < 0) {
/* 1157 */       throw new IllegalArgumentException("timeout can't be negative");
/*      */     }
/* 1159 */     getImpl().setOption(4102, new Integer(paramInt));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized int getSoTimeout() throws SocketException {
/* 1174 */     if (isClosed())
/* 1175 */       throw new SocketException("Socket is closed"); 
/* 1176 */     Object object = getImpl().getOption(4102);
/*      */     
/* 1178 */     if (object instanceof Integer) {
/* 1179 */       return ((Integer)object).intValue();
/*      */     }
/* 1181 */     return 0;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized void setSendBufferSize(int paramInt) throws SocketException {
/* 1210 */     if (paramInt <= 0) {
/* 1211 */       throw new IllegalArgumentException("negative send size");
/*      */     }
/* 1213 */     if (isClosed())
/* 1214 */       throw new SocketException("Socket is closed"); 
/* 1215 */     getImpl().setOption(4097, new Integer(paramInt));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized int getSendBufferSize() throws SocketException {
/* 1232 */     if (isClosed())
/* 1233 */       throw new SocketException("Socket is closed"); 
/* 1234 */     int i = 0;
/* 1235 */     Object object = getImpl().getOption(4097);
/* 1236 */     if (object instanceof Integer) {
/* 1237 */       i = ((Integer)object).intValue();
/*      */     }
/* 1239 */     return i;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized void setReceiveBufferSize(int paramInt) throws SocketException {
/* 1284 */     if (paramInt <= 0) {
/* 1285 */       throw new IllegalArgumentException("invalid receive size");
/*      */     }
/* 1287 */     if (isClosed())
/* 1288 */       throw new SocketException("Socket is closed"); 
/* 1289 */     getImpl().setOption(4098, new Integer(paramInt));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized int getReceiveBufferSize() throws SocketException {
/* 1306 */     if (isClosed())
/* 1307 */       throw new SocketException("Socket is closed"); 
/* 1308 */     int i = 0;
/* 1309 */     Object object = getImpl().getOption(4098);
/* 1310 */     if (object instanceof Integer) {
/* 1311 */       i = ((Integer)object).intValue();
/*      */     }
/* 1313 */     return i;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setKeepAlive(boolean paramBoolean) throws SocketException {
/* 1326 */     if (isClosed())
/* 1327 */       throw new SocketException("Socket is closed"); 
/* 1328 */     getImpl().setOption(8, Boolean.valueOf(paramBoolean));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getKeepAlive() throws SocketException {
/* 1342 */     if (isClosed())
/* 1343 */       throw new SocketException("Socket is closed"); 
/* 1344 */     return ((Boolean)getImpl().getOption(8)).booleanValue();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setTrafficClass(int paramInt) throws SocketException {
/* 1394 */     if (paramInt < 0 || paramInt > 255) {
/* 1395 */       throw new IllegalArgumentException("tc is not in range 0 -- 255");
/*      */     }
/* 1397 */     if (isClosed())
/* 1398 */       throw new SocketException("Socket is closed"); 
/*      */     try {
/* 1400 */       getImpl().setOption(3, Integer.valueOf(paramInt));
/* 1401 */     } catch (SocketException socketException) {
/*      */ 
/*      */       
/* 1404 */       if (!isConnected()) {
/* 1405 */         throw socketException;
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getTrafficClass() throws SocketException {
/* 1426 */     return ((Integer)getImpl().getOption(3)).intValue();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setReuseAddress(boolean paramBoolean) throws SocketException {
/* 1465 */     if (isClosed())
/* 1466 */       throw new SocketException("Socket is closed"); 
/* 1467 */     getImpl().setOption(4, Boolean.valueOf(paramBoolean));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getReuseAddress() throws SocketException {
/* 1481 */     if (isClosed())
/* 1482 */       throw new SocketException("Socket is closed"); 
/* 1483 */     return ((Boolean)getImpl().getOption(4)).booleanValue();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized void close() throws IOException {
/* 1509 */     synchronized (this.closeLock) {
/* 1510 */       if (isClosed())
/*      */         return; 
/* 1512 */       if (this.created)
/* 1513 */         this.impl.close(); 
/* 1514 */       this.closed = true;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void shutdownInput() throws IOException {
/* 1538 */     if (isClosed())
/* 1539 */       throw new SocketException("Socket is closed"); 
/* 1540 */     if (!isConnected())
/* 1541 */       throw new SocketException("Socket is not connected"); 
/* 1542 */     if (isInputShutdown())
/* 1543 */       throw new SocketException("Socket input is already shutdown"); 
/* 1544 */     getImpl().shutdownInput();
/* 1545 */     this.shutIn = true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void shutdownOutput() throws IOException {
/* 1568 */     if (isClosed())
/* 1569 */       throw new SocketException("Socket is closed"); 
/* 1570 */     if (!isConnected())
/* 1571 */       throw new SocketException("Socket is not connected"); 
/* 1572 */     if (isOutputShutdown())
/* 1573 */       throw new SocketException("Socket output is already shutdown"); 
/* 1574 */     getImpl().shutdownOutput();
/* 1575 */     this.shutOut = true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String toString() {
/*      */     try {
/* 1585 */       if (isConnected())
/* 1586 */         return "Socket[addr=" + getImpl().getInetAddress() + ",port=" + 
/* 1587 */           getImpl().getPort() + ",localport=" + 
/* 1588 */           getImpl().getLocalPort() + "]"; 
/* 1589 */     } catch (SocketException socketException) {}
/*      */     
/* 1591 */     return "Socket[unconnected]";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isConnected() {
/* 1607 */     return (this.connected || this.oldImpl);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isBound() {
/* 1624 */     return (this.bound || this.oldImpl);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isClosed() {
/* 1635 */     synchronized (this.closeLock) {
/* 1636 */       return this.closed;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isInputShutdown() {
/* 1648 */     return this.shutIn;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isOutputShutdown() {
/* 1659 */     return this.shutOut;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1665 */   private static SocketImplFactory factory = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static synchronized void setSocketImplFactory(SocketImplFactory paramSocketImplFactory) throws IOException {
/* 1694 */     if (factory != null) {
/* 1695 */       throw new SocketException("factory already defined");
/*      */     }
/* 1697 */     SecurityManager securityManager = System.getSecurityManager();
/* 1698 */     if (securityManager != null) {
/* 1699 */       securityManager.checkSetFactory();
/*      */     }
/* 1701 */     factory = paramSocketImplFactory;
/*      */   }
/*      */   
/*      */   public void setPerformancePreferences(int paramInt1, int paramInt2, int paramInt3) {}
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/net/Socket.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */