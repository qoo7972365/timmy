/*      */ package java.net;
/*      */ 
/*      */ import java.io.Closeable;
/*      */ import java.io.IOException;
/*      */ import java.nio.channels.DatagramChannel;
/*      */ import java.security.AccessController;
/*      */ import java.security.PrivilegedActionException;
/*      */ import java.security.PrivilegedExceptionAction;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class DatagramSocket
/*      */   implements Closeable
/*      */ {
/*      */   private boolean created = false;
/*      */   private boolean bound = false;
/*      */   private boolean closed = false;
/*   74 */   private Object closeLock = new Object();
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   DatagramSocketImpl impl;
/*      */ 
/*      */ 
/*      */   
/*      */   boolean oldImpl = false;
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean explicitFilter = false;
/*      */ 
/*      */ 
/*      */   
/*      */   private int bytesLeftToFilter;
/*      */ 
/*      */ 
/*      */   
/*      */   static final int ST_NOT_CONNECTED = 0;
/*      */ 
/*      */ 
/*      */   
/*      */   static final int ST_CONNECTED = 1;
/*      */ 
/*      */ 
/*      */   
/*      */   static final int ST_CONNECTED_NO_IMPL = 2;
/*      */ 
/*      */ 
/*      */   
/*  107 */   int connectState = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  112 */   InetAddress connectedAddress = null;
/*  113 */   int connectedPort = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private synchronized void connectInternal(InetAddress paramInetAddress, int paramInt) throws SocketException {
/*  124 */     if (paramInt < 0 || paramInt > 65535) {
/*  125 */       throw new IllegalArgumentException("connect: " + paramInt);
/*      */     }
/*  127 */     if (paramInetAddress == null) {
/*  128 */       throw new IllegalArgumentException("connect: null address");
/*      */     }
/*  130 */     checkAddress(paramInetAddress, "connect");
/*  131 */     if (isClosed())
/*      */       return; 
/*  133 */     SecurityManager securityManager = System.getSecurityManager();
/*  134 */     if (securityManager != null) {
/*  135 */       if (paramInetAddress.isMulticastAddress()) {
/*  136 */         securityManager.checkMulticast(paramInetAddress);
/*      */       } else {
/*  138 */         securityManager.checkConnect(paramInetAddress.getHostAddress(), paramInt);
/*  139 */         securityManager.checkAccept(paramInetAddress.getHostAddress(), paramInt);
/*      */       } 
/*      */     }
/*      */     
/*  143 */     if (!isBound()) {
/*  144 */       bind(new InetSocketAddress(0));
/*      */     }
/*      */     
/*  147 */     if (this.oldImpl || (this.impl instanceof AbstractPlainDatagramSocketImpl && ((AbstractPlainDatagramSocketImpl)this.impl)
/*  148 */       .nativeConnectDisabled())) {
/*  149 */       this.connectState = 2;
/*      */     } else {
/*      */       try {
/*  152 */         getImpl().connect(paramInetAddress, paramInt);
/*      */ 
/*      */         
/*  155 */         this.connectState = 1;
/*      */         
/*  157 */         int i = getImpl().dataAvailable();
/*  158 */         if (i == -1) {
/*  159 */           throw new SocketException();
/*      */         }
/*  161 */         this.explicitFilter = (i > 0);
/*  162 */         if (this.explicitFilter) {
/*  163 */           this.bytesLeftToFilter = getReceiveBufferSize();
/*      */         }
/*  165 */       } catch (SocketException socketException) {
/*      */ 
/*      */         
/*  168 */         this.connectState = 2;
/*      */       } 
/*      */     } 
/*      */     
/*  172 */     this.connectedAddress = paramInetAddress;
/*  173 */     this.connectedPort = paramInt;
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
/*      */   public DatagramSocket() throws SocketException {
/*  196 */     this(new InetSocketAddress(0));
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
/*      */   protected DatagramSocket(DatagramSocketImpl paramDatagramSocketImpl) {
/*  208 */     if (paramDatagramSocketImpl == null)
/*  209 */       throw new NullPointerException(); 
/*  210 */     this.impl = paramDatagramSocketImpl;
/*  211 */     checkOldImpl();
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
/*      */   public DatagramSocket(SocketAddress paramSocketAddress) throws SocketException {
/*  239 */     createImpl();
/*  240 */     if (paramSocketAddress != null) {
/*      */       try {
/*  242 */         bind(paramSocketAddress);
/*      */       } finally {
/*  244 */         if (!isBound()) {
/*  245 */           close();
/*      */         }
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
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DatagramSocket(int paramInt) throws SocketException {
/*  271 */     this(paramInt, null);
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
/*      */   public DatagramSocket(int paramInt, InetAddress paramInetAddress) throws SocketException {
/*  299 */     this(new InetSocketAddress(paramInetAddress, paramInt));
/*      */   }
/*      */   
/*      */   private void checkOldImpl() {
/*  303 */     if (this.impl == null) {
/*      */       return;
/*      */     }
/*      */     
/*      */     try {
/*  308 */       AccessController.doPrivileged(new PrivilegedExceptionAction<Void>()
/*      */           {
/*      */             public Void run() throws NoSuchMethodException {
/*  311 */               Class[] arrayOfClass = new Class[1];
/*  312 */               arrayOfClass[0] = DatagramPacket.class;
/*  313 */               DatagramSocket.this.impl.getClass().getDeclaredMethod("peekData", arrayOfClass);
/*  314 */               return null;
/*      */             }
/*      */           });
/*  317 */     } catch (PrivilegedActionException privilegedActionException) {
/*  318 */       this.oldImpl = true;
/*      */     } 
/*      */   }
/*      */   
/*  322 */   static Class<?> implClass = null;
/*      */   
/*      */   void createImpl() throws SocketException {
/*  325 */     if (this.impl == null) {
/*  326 */       if (factory != null) {
/*  327 */         this.impl = factory.createDatagramSocketImpl();
/*  328 */         checkOldImpl();
/*      */       } else {
/*  330 */         boolean bool = (this instanceof MulticastSocket) ? true : false;
/*  331 */         this.impl = DefaultDatagramSocketImplFactory.createDatagramSocketImpl(bool);
/*      */         
/*  333 */         checkOldImpl();
/*      */       } 
/*      */     }
/*      */     
/*  337 */     this.impl.create();
/*  338 */     this.impl.setDatagramSocket(this);
/*  339 */     this.created = true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static DatagramSocketImplFactory factory;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   DatagramSocketImpl getImpl() throws SocketException {
/*  352 */     if (!this.created)
/*  353 */       createImpl(); 
/*  354 */     return this.impl;
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
/*      */   public synchronized void bind(SocketAddress paramSocketAddress) throws SocketException {
/*  373 */     if (isClosed())
/*  374 */       throw new SocketException("Socket is closed"); 
/*  375 */     if (isBound())
/*  376 */       throw new SocketException("already bound"); 
/*  377 */     if (paramSocketAddress == null)
/*  378 */       paramSocketAddress = new InetSocketAddress(0); 
/*  379 */     if (!(paramSocketAddress instanceof InetSocketAddress))
/*  380 */       throw new IllegalArgumentException("Unsupported address type!"); 
/*  381 */     InetSocketAddress inetSocketAddress = (InetSocketAddress)paramSocketAddress;
/*  382 */     if (inetSocketAddress.isUnresolved())
/*  383 */       throw new SocketException("Unresolved address"); 
/*  384 */     InetAddress inetAddress = inetSocketAddress.getAddress();
/*  385 */     int i = inetSocketAddress.getPort();
/*  386 */     checkAddress(inetAddress, "bind");
/*  387 */     SecurityManager securityManager = System.getSecurityManager();
/*  388 */     if (securityManager != null) {
/*  389 */       securityManager.checkListen(i);
/*      */     }
/*      */     try {
/*  392 */       getImpl().bind(i, inetAddress);
/*  393 */     } catch (SocketException socketException) {
/*  394 */       getImpl().close();
/*  395 */       throw socketException;
/*      */     } 
/*  397 */     this.bound = true;
/*      */   }
/*      */   
/*      */   void checkAddress(InetAddress paramInetAddress, String paramString) {
/*  401 */     if (paramInetAddress == null) {
/*      */       return;
/*      */     }
/*  404 */     if (!(paramInetAddress instanceof Inet4Address) && !(paramInetAddress instanceof Inet6Address)) {
/*  405 */       throw new IllegalArgumentException(paramString + ": invalid address type");
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void connect(InetAddress paramInetAddress, int paramInt) {
/*      */     try {
/*  458 */       connectInternal(paramInetAddress, paramInt);
/*  459 */     } catch (SocketException socketException) {
/*  460 */       throw new Error("connect failed", socketException);
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
/*      */ 
/*      */ 
/*      */   
/*      */   public void connect(SocketAddress paramSocketAddress) throws SocketException {
/*  487 */     if (paramSocketAddress == null)
/*  488 */       throw new IllegalArgumentException("Address can't be null"); 
/*  489 */     if (!(paramSocketAddress instanceof InetSocketAddress))
/*  490 */       throw new IllegalArgumentException("Unsupported address type"); 
/*  491 */     InetSocketAddress inetSocketAddress = (InetSocketAddress)paramSocketAddress;
/*  492 */     if (inetSocketAddress.isUnresolved())
/*  493 */       throw new SocketException("Unresolved address"); 
/*  494 */     connectInternal(inetSocketAddress.getAddress(), inetSocketAddress.getPort());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void disconnect() {
/*  504 */     synchronized (this) {
/*  505 */       if (isClosed())
/*      */         return; 
/*  507 */       if (this.connectState == 1) {
/*  508 */         this.impl.disconnect();
/*      */       }
/*  510 */       this.connectedAddress = null;
/*  511 */       this.connectedPort = -1;
/*  512 */       this.connectState = 0;
/*  513 */       this.explicitFilter = false;
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
/*      */   public boolean isBound() {
/*  528 */     return this.bound;
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
/*      */   public boolean isConnected() {
/*  542 */     return (this.connectState != 0);
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
/*  556 */     return this.connectedAddress;
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
/*  570 */     return this.connectedPort;
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
/*  590 */     if (!isConnected())
/*  591 */       return null; 
/*  592 */     return new InetSocketAddress(getInetAddress(), getPort());
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
/*      */   public SocketAddress getLocalSocketAddress() {
/*  607 */     if (isClosed())
/*  608 */       return null; 
/*  609 */     if (!isBound())
/*  610 */       return null; 
/*  611 */     return new InetSocketAddress(getLocalAddress(), getLocalPort());
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
/*      */   public void send(DatagramPacket paramDatagramPacket) throws IOException {
/*  655 */     InetAddress inetAddress = null;
/*  656 */     synchronized (paramDatagramPacket) {
/*  657 */       if (isClosed())
/*  658 */         throw new SocketException("Socket is closed"); 
/*  659 */       checkAddress(paramDatagramPacket.getAddress(), "send");
/*  660 */       if (this.connectState == 0) {
/*      */         
/*  662 */         SecurityManager securityManager = System.getSecurityManager();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  668 */         if (securityManager != null) {
/*  669 */           if (paramDatagramPacket.getAddress().isMulticastAddress()) {
/*  670 */             securityManager.checkMulticast(paramDatagramPacket.getAddress());
/*      */           } else {
/*  672 */             securityManager.checkConnect(paramDatagramPacket.getAddress().getHostAddress(), paramDatagramPacket
/*  673 */                 .getPort());
/*      */           } 
/*      */         }
/*      */       } else {
/*      */         
/*  678 */         inetAddress = paramDatagramPacket.getAddress();
/*  679 */         if (inetAddress == null) {
/*  680 */           paramDatagramPacket.setAddress(this.connectedAddress);
/*  681 */           paramDatagramPacket.setPort(this.connectedPort);
/*  682 */         } else if (!inetAddress.equals(this.connectedAddress) || paramDatagramPacket
/*  683 */           .getPort() != this.connectedPort) {
/*  684 */           throw new IllegalArgumentException("connected address and packet address differ");
/*      */         } 
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/*  690 */       if (!isBound()) {
/*  691 */         bind(new InetSocketAddress(0));
/*      */       }
/*  693 */       getImpl().send(paramDatagramPacket);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized void receive(DatagramPacket paramDatagramPacket) throws IOException {
/*  729 */     synchronized (paramDatagramPacket) {
/*  730 */       if (!isBound())
/*  731 */         bind(new InetSocketAddress(0)); 
/*  732 */       if (this.connectState == 0) {
/*      */         
/*  734 */         SecurityManager securityManager = System.getSecurityManager();
/*  735 */         if (securityManager != null) {
/*      */           while (true) {
/*  737 */             String str = null;
/*  738 */             int i = 0;
/*      */             
/*  740 */             if (!this.oldImpl) {
/*      */               
/*  742 */               DatagramPacket datagramPacket1 = new DatagramPacket(new byte[1], 1);
/*  743 */               i = getImpl().peekData(datagramPacket1);
/*  744 */               str = datagramPacket1.getAddress().getHostAddress();
/*      */             } else {
/*  746 */               InetAddress inetAddress = new InetAddress();
/*  747 */               i = getImpl().peek(inetAddress);
/*  748 */               str = inetAddress.getHostAddress();
/*      */             } 
/*      */             try {
/*  751 */               securityManager.checkAccept(str, i);
/*      */ 
/*      */               
/*      */               break;
/*  755 */             } catch (SecurityException securityException) {
/*      */ 
/*      */               
/*  758 */               DatagramPacket datagramPacket1 = new DatagramPacket(new byte[1], 1);
/*  759 */               getImpl().receive(datagramPacket1);
/*      */             } 
/*      */           } 
/*      */         }
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  772 */       DatagramPacket datagramPacket = null;
/*  773 */       if (this.connectState == 2 || this.explicitFilter) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  779 */         boolean bool = false;
/*  780 */         while (!bool) {
/*  781 */           InetAddress inetAddress = null;
/*  782 */           int i = -1;
/*      */           
/*  784 */           if (!this.oldImpl) {
/*      */             
/*  786 */             DatagramPacket datagramPacket1 = new DatagramPacket(new byte[1], 1);
/*  787 */             i = getImpl().peekData(datagramPacket1);
/*  788 */             inetAddress = datagramPacket1.getAddress();
/*      */           } else {
/*      */             
/*  791 */             inetAddress = new InetAddress();
/*  792 */             i = getImpl().peek(inetAddress);
/*      */           } 
/*  794 */           if (!this.connectedAddress.equals(inetAddress) || this.connectedPort != i) {
/*      */ 
/*      */             
/*  797 */             datagramPacket = new DatagramPacket(new byte[1024], 1024);
/*      */             
/*  799 */             getImpl().receive(datagramPacket);
/*  800 */             if (this.explicitFilter && 
/*  801 */               checkFiltering(datagramPacket)) {
/*  802 */               bool = true;
/*      */             }
/*      */             continue;
/*      */           } 
/*  806 */           bool = true;
/*      */         } 
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/*  812 */       getImpl().receive(paramDatagramPacket);
/*  813 */       if (this.explicitFilter && datagramPacket == null)
/*      */       {
/*  815 */         checkFiltering(paramDatagramPacket);
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   private boolean checkFiltering(DatagramPacket paramDatagramPacket) throws SocketException {
/*  821 */     this.bytesLeftToFilter -= paramDatagramPacket.getLength();
/*  822 */     if (this.bytesLeftToFilter <= 0 || getImpl().dataAvailable() <= 0) {
/*  823 */       this.explicitFilter = false;
/*  824 */       return true;
/*      */     } 
/*  826 */     return false;
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
/*      */   public InetAddress getLocalAddress() {
/*  848 */     if (isClosed())
/*  849 */       return null; 
/*  850 */     InetAddress inetAddress = null;
/*      */     try {
/*  852 */       inetAddress = (InetAddress)getImpl().getOption(15);
/*  853 */       if (inetAddress.isAnyLocalAddress()) {
/*  854 */         inetAddress = InetAddress.anyLocalAddress();
/*      */       }
/*  856 */       SecurityManager securityManager = System.getSecurityManager();
/*  857 */       if (securityManager != null) {
/*  858 */         securityManager.checkConnect(inetAddress.getHostAddress(), -1);
/*      */       }
/*  860 */     } catch (Exception exception) {
/*  861 */       inetAddress = InetAddress.anyLocalAddress();
/*      */     } 
/*  863 */     return inetAddress;
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
/*      */   public int getLocalPort() {
/*  875 */     if (isClosed())
/*  876 */       return -1; 
/*      */     try {
/*  878 */       return getImpl().getLocalPort();
/*  879 */     } catch (Exception exception) {
/*  880 */       return 0;
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
/*      */   public synchronized void setSoTimeout(int paramInt) throws SocketException {
/*  900 */     if (isClosed())
/*  901 */       throw new SocketException("Socket is closed"); 
/*  902 */     getImpl().setOption(4102, new Integer(paramInt));
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
/*      */   public synchronized int getSoTimeout() throws SocketException {
/*  915 */     if (isClosed())
/*  916 */       throw new SocketException("Socket is closed"); 
/*  917 */     if (getImpl() == null)
/*  918 */       return 0; 
/*  919 */     Object object = getImpl().getOption(4102);
/*      */     
/*  921 */     if (object instanceof Integer) {
/*  922 */       return ((Integer)object).intValue();
/*      */     }
/*  924 */     return 0;
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
/*      */   public synchronized void setSendBufferSize(int paramInt) throws SocketException {
/*  959 */     if (paramInt <= 0) {
/*  960 */       throw new IllegalArgumentException("negative send size");
/*      */     }
/*  962 */     if (isClosed())
/*  963 */       throw new SocketException("Socket is closed"); 
/*  964 */     getImpl().setOption(4097, new Integer(paramInt));
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
/*      */   public synchronized int getSendBufferSize() throws SocketException {
/*  977 */     if (isClosed())
/*  978 */       throw new SocketException("Socket is closed"); 
/*  979 */     int i = 0;
/*  980 */     Object object = getImpl().getOption(4097);
/*  981 */     if (object instanceof Integer) {
/*  982 */       i = ((Integer)object).intValue();
/*      */     }
/*  984 */     return i;
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
/*      */   public synchronized void setReceiveBufferSize(int paramInt) throws SocketException {
/* 1017 */     if (paramInt <= 0) {
/* 1018 */       throw new IllegalArgumentException("invalid receive size");
/*      */     }
/* 1020 */     if (isClosed())
/* 1021 */       throw new SocketException("Socket is closed"); 
/* 1022 */     getImpl().setOption(4098, new Integer(paramInt));
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
/*      */   public synchronized int getReceiveBufferSize() throws SocketException {
/* 1035 */     if (isClosed())
/* 1036 */       throw new SocketException("Socket is closed"); 
/* 1037 */     int i = 0;
/* 1038 */     Object object = getImpl().getOption(4098);
/* 1039 */     if (object instanceof Integer) {
/* 1040 */       i = ((Integer)object).intValue();
/*      */     }
/* 1042 */     return i;
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
/*      */   public synchronized void setReuseAddress(boolean paramBoolean) throws SocketException {
/* 1080 */     if (isClosed()) {
/* 1081 */       throw new SocketException("Socket is closed");
/*      */     }
/* 1083 */     if (this.oldImpl) {
/* 1084 */       getImpl().setOption(4, new Integer(paramBoolean ? -1 : 0));
/*      */     } else {
/* 1086 */       getImpl().setOption(4, Boolean.valueOf(paramBoolean));
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
/*      */   public synchronized boolean getReuseAddress() throws SocketException {
/* 1099 */     if (isClosed())
/* 1100 */       throw new SocketException("Socket is closed"); 
/* 1101 */     Object object = getImpl().getOption(4);
/* 1102 */     return ((Boolean)object).booleanValue();
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
/*      */   public synchronized void setBroadcast(boolean paramBoolean) throws SocketException {
/* 1123 */     if (isClosed())
/* 1124 */       throw new SocketException("Socket is closed"); 
/* 1125 */     getImpl().setOption(32, Boolean.valueOf(paramBoolean));
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
/*      */   public synchronized boolean getBroadcast() throws SocketException {
/* 1137 */     if (isClosed())
/* 1138 */       throw new SocketException("Socket is closed"); 
/* 1139 */     return ((Boolean)getImpl().getOption(32)).booleanValue();
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
/*      */   public synchronized void setTrafficClass(int paramInt) throws SocketException {
/* 1180 */     if (paramInt < 0 || paramInt > 255) {
/* 1181 */       throw new IllegalArgumentException("tc is not in range 0 -- 255");
/*      */     }
/* 1183 */     if (isClosed())
/* 1184 */       throw new SocketException("Socket is closed"); 
/*      */     try {
/* 1186 */       getImpl().setOption(3, Integer.valueOf(paramInt));
/* 1187 */     } catch (SocketException socketException) {
/*      */ 
/*      */       
/* 1190 */       if (!isConnected()) {
/* 1191 */         throw socketException;
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
/*      */   public synchronized int getTrafficClass() throws SocketException {
/* 1212 */     if (isClosed())
/* 1213 */       throw new SocketException("Socket is closed"); 
/* 1214 */     return ((Integer)getImpl().getOption(3)).intValue();
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
/*      */   public void close() {
/* 1230 */     synchronized (this.closeLock) {
/* 1231 */       if (isClosed())
/*      */         return; 
/* 1233 */       this.impl.close();
/* 1234 */       this.closed = true;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isClosed() {
/* 1245 */     synchronized (this.closeLock) {
/* 1246 */       return this.closed;
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
/*      */   public DatagramChannel getChannel() {
/* 1265 */     return null;
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
/*      */   public static synchronized void setDatagramSocketImplFactory(DatagramSocketImplFactory paramDatagramSocketImplFactory) throws IOException {
/* 1305 */     if (factory != null) {
/* 1306 */       throw new SocketException("factory already defined");
/*      */     }
/* 1308 */     SecurityManager securityManager = System.getSecurityManager();
/* 1309 */     if (securityManager != null) {
/* 1310 */       securityManager.checkSetFactory();
/*      */     }
/* 1312 */     factory = paramDatagramSocketImplFactory;
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/net/DatagramSocket.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */