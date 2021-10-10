/*     */ package sun.nio.ch;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.net.DatagramPacket;
/*     */ import java.net.DatagramSocket;
/*     */ import java.net.DatagramSocketImpl;
/*     */ import java.net.InetAddress;
/*     */ import java.net.InetSocketAddress;
/*     */ import java.net.NetworkInterface;
/*     */ import java.net.SocketAddress;
/*     */ import java.net.SocketException;
/*     */ import java.net.SocketOption;
/*     */ import java.net.SocketTimeoutException;
/*     */ import java.net.StandardSocketOptions;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.channels.ClosedChannelException;
/*     */ import java.nio.channels.DatagramChannel;
/*     */ import java.nio.channels.IllegalBlockingModeException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DatagramSocketAdaptor
/*     */   extends DatagramSocket
/*     */ {
/*     */   private final DatagramChannelImpl dc;
/*  61 */   private volatile int timeout = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private DatagramSocketAdaptor(DatagramChannelImpl paramDatagramChannelImpl) throws IOException {
/*  69 */     super(dummyDatagramSocket);
/*  70 */     this.dc = paramDatagramChannelImpl;
/*     */   }
/*     */   
/*     */   public static DatagramSocket create(DatagramChannelImpl paramDatagramChannelImpl) {
/*     */     try {
/*  75 */       return new DatagramSocketAdaptor(paramDatagramChannelImpl);
/*  76 */     } catch (IOException iOException) {
/*  77 */       throw new Error(iOException);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void connectInternal(SocketAddress paramSocketAddress) throws SocketException {
/*  84 */     InetSocketAddress inetSocketAddress = Net.asInetSocketAddress(paramSocketAddress);
/*  85 */     int i = inetSocketAddress.getPort();
/*  86 */     if (i < 0 || i > 65535)
/*  87 */       throw new IllegalArgumentException("connect: " + i); 
/*  88 */     if (paramSocketAddress == null)
/*  89 */       throw new IllegalArgumentException("connect: null address"); 
/*  90 */     if (isClosed())
/*     */       return; 
/*     */     try {
/*  93 */       this.dc.connect(paramSocketAddress);
/*  94 */     } catch (Exception exception) {
/*  95 */       Net.translateToSocketException(exception);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void bind(SocketAddress paramSocketAddress) throws SocketException {
/*     */     try {
/* 101 */       if (paramSocketAddress == null)
/* 102 */         paramSocketAddress = new InetSocketAddress(0); 
/* 103 */       this.dc.bind(paramSocketAddress);
/* 104 */     } catch (Exception exception) {
/* 105 */       Net.translateToSocketException(exception);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void connect(InetAddress paramInetAddress, int paramInt) {
/*     */     try {
/* 111 */       connectInternal(new InetSocketAddress(paramInetAddress, paramInt));
/* 112 */     } catch (SocketException socketException) {}
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void connect(SocketAddress paramSocketAddress) throws SocketException {
/* 118 */     if (paramSocketAddress == null)
/* 119 */       throw new IllegalArgumentException("Address can't be null"); 
/* 120 */     connectInternal(paramSocketAddress);
/*     */   }
/*     */   
/*     */   public void disconnect() {
/*     */     try {
/* 125 */       this.dc.disconnect();
/* 126 */     } catch (IOException iOException) {
/* 127 */       throw new Error(iOException);
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean isBound() {
/* 132 */     return (this.dc.localAddress() != null);
/*     */   }
/*     */   
/*     */   public boolean isConnected() {
/* 136 */     return (this.dc.remoteAddress() != null);
/*     */   }
/*     */   
/*     */   public InetAddress getInetAddress() {
/* 140 */     return isConnected() ? 
/* 141 */       Net.asInetSocketAddress(this.dc.remoteAddress()).getAddress() : null;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getPort() {
/* 146 */     return isConnected() ? 
/* 147 */       Net.asInetSocketAddress(this.dc.remoteAddress()).getPort() : -1;
/*     */   }
/*     */ 
/*     */   
/*     */   public void send(DatagramPacket paramDatagramPacket) throws IOException {
/* 152 */     synchronized (this.dc.blockingLock()) {
/* 153 */       if (!this.dc.isBlocking())
/* 154 */         throw new IllegalBlockingModeException(); 
/*     */       try {
/* 156 */         synchronized (paramDatagramPacket) {
/* 157 */           ByteBuffer byteBuffer = ByteBuffer.wrap(paramDatagramPacket.getData(), paramDatagramPacket
/* 158 */               .getOffset(), paramDatagramPacket
/* 159 */               .getLength());
/* 160 */           if (this.dc.isConnected()) {
/* 161 */             if (paramDatagramPacket.getAddress() == null) {
/*     */ 
/*     */ 
/*     */               
/* 165 */               InetSocketAddress inetSocketAddress = (InetSocketAddress)this.dc.remoteAddress();
/* 166 */               paramDatagramPacket.setPort(inetSocketAddress.getPort());
/* 167 */               paramDatagramPacket.setAddress(inetSocketAddress.getAddress());
/* 168 */               this.dc.write(byteBuffer);
/*     */             } else {
/*     */               
/* 171 */               this.dc.send(byteBuffer, paramDatagramPacket.getSocketAddress());
/*     */             } 
/*     */           } else {
/*     */             
/* 175 */             this.dc.send(byteBuffer, paramDatagramPacket.getSocketAddress());
/*     */           } 
/*     */         } 
/* 178 */       } catch (IOException iOException) {
/* 179 */         Net.translateException(iOException);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private SocketAddress receive(ByteBuffer paramByteBuffer) throws IOException {
/* 187 */     if (this.timeout == 0) {
/* 188 */       return this.dc.receive(paramByteBuffer);
/*     */     }
/*     */     
/* 191 */     this.dc.configureBlocking(false);
/*     */     try {
/*     */       SocketAddress socketAddress;
/* 194 */       if ((socketAddress = this.dc.receive(paramByteBuffer)) != null)
/* 195 */         return socketAddress; 
/* 196 */       long l = this.timeout;
/*     */       while (true) {
/* 198 */         if (!this.dc.isOpen())
/* 199 */           throw new ClosedChannelException(); 
/* 200 */         long l1 = System.currentTimeMillis();
/* 201 */         int i = this.dc.poll(Net.POLLIN, l);
/* 202 */         if (i > 0 && (i & Net.POLLIN) != 0 && (
/* 203 */           socketAddress = this.dc.receive(paramByteBuffer)) != null) {
/* 204 */           return socketAddress;
/*     */         }
/* 206 */         l -= System.currentTimeMillis() - l1;
/* 207 */         if (l <= 0L)
/* 208 */           throw new SocketTimeoutException(); 
/*     */       } 
/*     */     } finally {
/*     */       try {
/* 212 */         this.dc.configureBlocking(true);
/* 213 */       } catch (ClosedChannelException closedChannelException) {}
/*     */     } 
/*     */   }
/*     */   
/*     */   public void receive(DatagramPacket paramDatagramPacket) throws IOException {
/* 218 */     synchronized (this.dc.blockingLock()) {
/* 219 */       if (!this.dc.isBlocking())
/* 220 */         throw new IllegalBlockingModeException(); 
/*     */       try {
/* 222 */         synchronized (paramDatagramPacket) {
/* 223 */           ByteBuffer byteBuffer = ByteBuffer.wrap(paramDatagramPacket.getData(), paramDatagramPacket
/* 224 */               .getOffset(), paramDatagramPacket
/* 225 */               .getLength());
/* 226 */           SocketAddress socketAddress = receive(byteBuffer);
/* 227 */           paramDatagramPacket.setSocketAddress(socketAddress);
/* 228 */           paramDatagramPacket.setLength(byteBuffer.position() - paramDatagramPacket.getOffset());
/*     */         } 
/* 230 */       } catch (IOException iOException) {
/* 231 */         Net.translateException(iOException);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public InetAddress getLocalAddress() {
/* 237 */     if (isClosed())
/* 238 */       return null; 
/* 239 */     SocketAddress socketAddress = this.dc.localAddress();
/* 240 */     if (socketAddress == null)
/* 241 */       socketAddress = new InetSocketAddress(0); 
/* 242 */     InetAddress inetAddress = ((InetSocketAddress)socketAddress).getAddress();
/* 243 */     SecurityManager securityManager = System.getSecurityManager();
/* 244 */     if (securityManager != null) {
/*     */       try {
/* 246 */         securityManager.checkConnect(inetAddress.getHostAddress(), -1);
/* 247 */       } catch (SecurityException securityException) {
/* 248 */         return (new InetSocketAddress(0)).getAddress();
/*     */       } 
/*     */     }
/* 251 */     return inetAddress;
/*     */   }
/*     */   
/*     */   public int getLocalPort() {
/* 255 */     if (isClosed())
/* 256 */       return -1; 
/*     */     try {
/* 258 */       SocketAddress socketAddress = this.dc.getLocalAddress();
/* 259 */       if (socketAddress != null) {
/* 260 */         return ((InetSocketAddress)socketAddress).getPort();
/*     */       }
/* 262 */     } catch (Exception exception) {}
/*     */     
/* 264 */     return 0;
/*     */   }
/*     */   
/*     */   public void setSoTimeout(int paramInt) throws SocketException {
/* 268 */     this.timeout = paramInt;
/*     */   }
/*     */   
/*     */   public int getSoTimeout() throws SocketException {
/* 272 */     return this.timeout;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void setBooleanOption(SocketOption<Boolean> paramSocketOption, boolean paramBoolean) throws SocketException {
/*     */     try {
/* 279 */       this.dc.setOption(paramSocketOption, Boolean.valueOf(paramBoolean));
/* 280 */     } catch (IOException iOException) {
/* 281 */       Net.translateToSocketException(iOException);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void setIntOption(SocketOption<Integer> paramSocketOption, int paramInt) throws SocketException {
/*     */     try {
/* 289 */       this.dc.setOption(paramSocketOption, Integer.valueOf(paramInt));
/* 290 */     } catch (IOException iOException) {
/* 291 */       Net.translateToSocketException(iOException);
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean getBooleanOption(SocketOption<Boolean> paramSocketOption) throws SocketException {
/*     */     try {
/* 297 */       return ((Boolean)this.dc.<Boolean>getOption(paramSocketOption)).booleanValue();
/* 298 */     } catch (IOException iOException) {
/* 299 */       Net.translateToSocketException(iOException);
/* 300 */       return false;
/*     */     } 
/*     */   }
/*     */   
/*     */   private int getIntOption(SocketOption<Integer> paramSocketOption) throws SocketException {
/*     */     try {
/* 306 */       return ((Integer)this.dc.<Integer>getOption(paramSocketOption)).intValue();
/* 307 */     } catch (IOException iOException) {
/* 308 */       Net.translateToSocketException(iOException);
/* 309 */       return -1;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setSendBufferSize(int paramInt) throws SocketException {
/* 314 */     if (paramInt <= 0)
/* 315 */       throw new IllegalArgumentException("Invalid send size"); 
/* 316 */     setIntOption(StandardSocketOptions.SO_SNDBUF, paramInt);
/*     */   }
/*     */   
/*     */   public int getSendBufferSize() throws SocketException {
/* 320 */     return getIntOption(StandardSocketOptions.SO_SNDBUF);
/*     */   }
/*     */   
/*     */   public void setReceiveBufferSize(int paramInt) throws SocketException {
/* 324 */     if (paramInt <= 0)
/* 325 */       throw new IllegalArgumentException("Invalid receive size"); 
/* 326 */     setIntOption(StandardSocketOptions.SO_RCVBUF, paramInt);
/*     */   }
/*     */   
/*     */   public int getReceiveBufferSize() throws SocketException {
/* 330 */     return getIntOption(StandardSocketOptions.SO_RCVBUF);
/*     */   }
/*     */   
/*     */   public void setReuseAddress(boolean paramBoolean) throws SocketException {
/* 334 */     setBooleanOption(StandardSocketOptions.SO_REUSEADDR, paramBoolean);
/*     */   }
/*     */   
/*     */   public boolean getReuseAddress() throws SocketException {
/* 338 */     return getBooleanOption(StandardSocketOptions.SO_REUSEADDR);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setBroadcast(boolean paramBoolean) throws SocketException {
/* 343 */     setBooleanOption(StandardSocketOptions.SO_BROADCAST, paramBoolean);
/*     */   }
/*     */   
/*     */   public boolean getBroadcast() throws SocketException {
/* 347 */     return getBooleanOption(StandardSocketOptions.SO_BROADCAST);
/*     */   }
/*     */   
/*     */   public void setTrafficClass(int paramInt) throws SocketException {
/* 351 */     setIntOption(StandardSocketOptions.IP_TOS, paramInt);
/*     */   }
/*     */   
/*     */   public int getTrafficClass() throws SocketException {
/* 355 */     return getIntOption(StandardSocketOptions.IP_TOS);
/*     */   }
/*     */   
/*     */   public void close() {
/*     */     try {
/* 360 */       this.dc.close();
/* 361 */     } catch (IOException iOException) {
/* 362 */       throw new Error(iOException);
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean isClosed() {
/* 367 */     return !this.dc.isOpen();
/*     */   }
/*     */   
/*     */   public DatagramChannel getChannel() {
/* 371 */     return this.dc;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 379 */   private static final DatagramSocketImpl dummyDatagramSocket = new DatagramSocketImpl()
/*     */     {
/*     */       protected void create() throws SocketException {}
/*     */       
/*     */       protected void bind(int param1Int, InetAddress param1InetAddress) throws SocketException {}
/*     */       
/*     */       protected void send(DatagramPacket param1DatagramPacket) throws IOException {}
/*     */       
/*     */       protected int peek(InetAddress param1InetAddress) throws IOException {
/* 388 */         return 0;
/*     */       } protected int peekData(DatagramPacket param1DatagramPacket) throws IOException {
/* 390 */         return 0;
/*     */       }
/*     */       protected void receive(DatagramPacket param1DatagramPacket) throws IOException {}
/*     */       @Deprecated
/*     */       protected void setTTL(byte param1Byte) throws IOException {}
/*     */       
/*     */       @Deprecated
/*     */       protected byte getTTL() throws IOException {
/* 398 */         return 0;
/*     */       }
/*     */       protected void setTimeToLive(int param1Int) throws IOException {}
/*     */       protected int getTimeToLive() throws IOException {
/* 402 */         return 0;
/*     */       }
/*     */       
/*     */       protected void join(InetAddress param1InetAddress) throws IOException {}
/*     */       
/*     */       protected void leave(InetAddress param1InetAddress) throws IOException {}
/*     */       
/*     */       protected void joinGroup(SocketAddress param1SocketAddress, NetworkInterface param1NetworkInterface) throws IOException {}
/*     */       
/*     */       protected void leaveGroup(SocketAddress param1SocketAddress, NetworkInterface param1NetworkInterface) throws IOException {}
/*     */       
/*     */       protected void close() {}
/*     */       
/*     */       public Object getOption(int param1Int) throws SocketException {
/* 416 */         return null;
/*     */       }
/*     */       
/*     */       public void setOption(int param1Int, Object param1Object) throws SocketException {}
/*     */     };
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/nio/ch/DatagramSocketAdaptor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */