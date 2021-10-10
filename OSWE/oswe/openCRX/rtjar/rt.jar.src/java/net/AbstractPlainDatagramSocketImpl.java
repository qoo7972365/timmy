/*     */ package java.net;
/*     */ 
/*     */ import java.io.FileDescriptor;
/*     */ import java.io.IOException;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import sun.net.ResourceManager;
/*     */ import sun.security.action.GetPropertyAction;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ abstract class AbstractPlainDatagramSocketImpl
/*     */   extends DatagramSocketImpl
/*     */ {
/*  45 */   int timeout = 0;
/*     */   boolean connected = false;
/*  47 */   private int trafficClass = 0;
/*  48 */   protected InetAddress connectedAddress = null;
/*  49 */   private int connectedPort = -1;
/*     */   
/*  51 */   private static final String os = AccessController.<String>doPrivileged(new GetPropertyAction("os.name"));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  58 */   private static final boolean connectDisabled = os.contains("OS X");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/*  64 */     AccessController.doPrivileged(new PrivilegedAction<Void>()
/*     */         {
/*     */           public Void run() {
/*  67 */             System.loadLibrary("net");
/*  68 */             return null;
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected synchronized void create() throws SocketException {
/*  77 */     ResourceManager.beforeUdpCreate();
/*  78 */     this.fd = new FileDescriptor();
/*     */     try {
/*  80 */       datagramSocketCreate();
/*  81 */     } catch (SocketException socketException) {
/*  82 */       ResourceManager.afterUdpClose();
/*  83 */       this.fd = null;
/*  84 */       throw socketException;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected synchronized void bind(int paramInt, InetAddress paramInetAddress) throws SocketException {
/*  93 */     bind0(paramInt, paramInetAddress);
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
/*     */   protected void connect(InetAddress paramInetAddress, int paramInt) throws SocketException {
/* 114 */     connect0(paramInetAddress, paramInt);
/* 115 */     this.connectedAddress = paramInetAddress;
/* 116 */     this.connectedPort = paramInt;
/* 117 */     this.connected = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void disconnect() {
/* 125 */     disconnect0(this.connectedAddress.holder().getFamily());
/* 126 */     this.connected = false;
/* 127 */     this.connectedAddress = null;
/* 128 */     this.connectedPort = -1;
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
/*     */   protected synchronized void receive(DatagramPacket paramDatagramPacket) throws IOException {
/* 143 */     receive0(paramDatagramPacket);
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
/*     */   protected void join(InetAddress paramInetAddress) throws IOException {
/* 178 */     join(paramInetAddress, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void leave(InetAddress paramInetAddress) throws IOException {
/* 186 */     leave(paramInetAddress, null);
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
/*     */   protected void joinGroup(SocketAddress paramSocketAddress, NetworkInterface paramNetworkInterface) throws IOException {
/* 200 */     if (paramSocketAddress == null || !(paramSocketAddress instanceof InetSocketAddress))
/* 201 */       throw new IllegalArgumentException("Unsupported address type"); 
/* 202 */     join(((InetSocketAddress)paramSocketAddress).getAddress(), paramNetworkInterface);
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
/*     */   protected void leaveGroup(SocketAddress paramSocketAddress, NetworkInterface paramNetworkInterface) throws IOException {
/* 218 */     if (paramSocketAddress == null || !(paramSocketAddress instanceof InetSocketAddress))
/* 219 */       throw new IllegalArgumentException("Unsupported address type"); 
/* 220 */     leave(((InetSocketAddress)paramSocketAddress).getAddress(), paramNetworkInterface);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void close() {
/* 230 */     if (this.fd != null) {
/* 231 */       datagramSocketClose();
/* 232 */       ResourceManager.afterUdpClose();
/* 233 */       this.fd = null;
/*     */     } 
/*     */   }
/*     */   
/*     */   protected boolean isClosed() {
/* 238 */     return (this.fd == null);
/*     */   }
/*     */   
/*     */   protected void finalize() {
/* 242 */     close();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setOption(int paramInt, Object paramObject) throws SocketException {
/*     */     int i;
/* 251 */     if (isClosed()) {
/* 252 */       throw new SocketException("Socket Closed");
/*     */     }
/* 254 */     switch (paramInt) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       case 4102:
/* 260 */         if (paramObject == null || !(paramObject instanceof Integer)) {
/* 261 */           throw new SocketException("bad argument for SO_TIMEOUT");
/*     */         }
/* 263 */         i = ((Integer)paramObject).intValue();
/* 264 */         if (i < 0)
/* 265 */           throw new IllegalArgumentException("timeout < 0"); 
/* 266 */         this.timeout = i;
/*     */         return;
/*     */       case 3:
/* 269 */         if (paramObject == null || !(paramObject instanceof Integer)) {
/* 270 */           throw new SocketException("bad argument for IP_TOS");
/*     */         }
/* 272 */         this.trafficClass = ((Integer)paramObject).intValue();
/*     */         break;
/*     */       case 4:
/* 275 */         if (paramObject == null || !(paramObject instanceof Boolean)) {
/* 276 */           throw new SocketException("bad argument for SO_REUSEADDR");
/*     */         }
/*     */         break;
/*     */       case 32:
/* 280 */         if (paramObject == null || !(paramObject instanceof Boolean)) {
/* 281 */           throw new SocketException("bad argument for SO_BROADCAST");
/*     */         }
/*     */         break;
/*     */       case 15:
/* 285 */         throw new SocketException("Cannot re-bind Socket");
/*     */       case 4097:
/*     */       case 4098:
/* 288 */         if (paramObject == null || !(paramObject instanceof Integer) || ((Integer)paramObject)
/* 289 */           .intValue() < 0) {
/* 290 */           throw new SocketException("bad argument for SO_SNDBUF or SO_RCVBUF");
/*     */         }
/*     */         break;
/*     */       
/*     */       case 16:
/* 295 */         if (paramObject == null || !(paramObject instanceof InetAddress))
/* 296 */           throw new SocketException("bad argument for IP_MULTICAST_IF"); 
/*     */         break;
/*     */       case 31:
/* 299 */         if (paramObject == null || !(paramObject instanceof NetworkInterface))
/* 300 */           throw new SocketException("bad argument for IP_MULTICAST_IF2"); 
/*     */         break;
/*     */       case 18:
/* 303 */         if (paramObject == null || !(paramObject instanceof Boolean))
/* 304 */           throw new SocketException("bad argument for IP_MULTICAST_LOOP"); 
/*     */         break;
/*     */       default:
/* 307 */         throw new SocketException("invalid option: " + paramInt);
/*     */     } 
/* 309 */     socketSetOption(paramInt, paramObject);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Object getOption(int paramInt) throws SocketException {
/*     */     Integer integer;
/*     */     Object object;
/* 317 */     if (isClosed()) {
/* 318 */       throw new SocketException("Socket Closed");
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 323 */     switch (paramInt) {
/*     */       case 4102:
/* 325 */         return new Integer(this.timeout);
/*     */ 
/*     */       
/*     */       case 3:
/* 329 */         object = socketGetOption(paramInt);
/* 330 */         if (((Integer)object).intValue() == -1) {
/* 331 */           object = new Integer(this.trafficClass);
/*     */         }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 350 */         return object;case 4: case 15: case 16: case 18: case 31: case 32: case 4097: case 4098: object = socketGetOption(paramInt); return object;
/*     */     } 
/*     */     throw new SocketException("invalid option: " + paramInt);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean nativeConnectDisabled() {
/* 363 */     return connectDisabled;
/*     */   }
/*     */   
/*     */   protected abstract void bind0(int paramInt, InetAddress paramInetAddress) throws SocketException;
/*     */   
/*     */   protected abstract void send(DatagramPacket paramDatagramPacket) throws IOException;
/*     */   
/*     */   protected abstract int peek(InetAddress paramInetAddress) throws IOException;
/*     */   
/*     */   protected abstract int peekData(DatagramPacket paramDatagramPacket) throws IOException;
/*     */   
/*     */   protected abstract void receive0(DatagramPacket paramDatagramPacket) throws IOException;
/*     */   
/*     */   protected abstract void setTimeToLive(int paramInt) throws IOException;
/*     */   
/*     */   protected abstract int getTimeToLive() throws IOException;
/*     */   
/*     */   @Deprecated
/*     */   protected abstract void setTTL(byte paramByte) throws IOException;
/*     */   
/*     */   @Deprecated
/*     */   protected abstract byte getTTL() throws IOException;
/*     */   
/*     */   protected abstract void join(InetAddress paramInetAddress, NetworkInterface paramNetworkInterface) throws IOException;
/*     */   
/*     */   protected abstract void leave(InetAddress paramInetAddress, NetworkInterface paramNetworkInterface) throws IOException;
/*     */   
/*     */   protected abstract void datagramSocketCreate() throws SocketException;
/*     */   
/*     */   protected abstract void datagramSocketClose();
/*     */   
/*     */   protected abstract void socketSetOption(int paramInt, Object paramObject) throws SocketException;
/*     */   
/*     */   protected abstract Object socketGetOption(int paramInt) throws SocketException;
/*     */   
/*     */   protected abstract void connect0(InetAddress paramInetAddress, int paramInt) throws SocketException;
/*     */   
/*     */   protected abstract void disconnect0(int paramInt);
/*     */   
/*     */   abstract int dataAvailable();
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/net/AbstractPlainDatagramSocketImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */