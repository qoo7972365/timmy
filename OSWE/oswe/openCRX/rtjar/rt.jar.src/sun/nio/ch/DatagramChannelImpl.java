/*      */ package sun.nio.ch;
/*      */ 
/*      */ import java.io.FileDescriptor;
/*      */ import java.io.IOException;
/*      */ import java.net.DatagramSocket;
/*      */ import java.net.Inet4Address;
/*      */ import java.net.InetAddress;
/*      */ import java.net.InetSocketAddress;
/*      */ import java.net.NetworkInterface;
/*      */ import java.net.PortUnreachableException;
/*      */ import java.net.ProtocolFamily;
/*      */ import java.net.SocketAddress;
/*      */ import java.net.SocketOption;
/*      */ import java.net.StandardProtocolFamily;
/*      */ import java.net.StandardSocketOptions;
/*      */ import java.nio.ByteBuffer;
/*      */ import java.nio.channels.AlreadyBoundException;
/*      */ import java.nio.channels.ClosedChannelException;
/*      */ import java.nio.channels.DatagramChannel;
/*      */ import java.nio.channels.MembershipKey;
/*      */ import java.nio.channels.NetworkChannel;
/*      */ import java.nio.channels.NotYetConnectedException;
/*      */ import java.nio.channels.UnsupportedAddressTypeException;
/*      */ import java.nio.channels.spi.SelectorProvider;
/*      */ import java.util.Collections;
/*      */ import java.util.HashSet;
/*      */ import java.util.Set;
/*      */ import jdk.net.ExtendedSocketOptions;
/*      */ import sun.net.ExtendedOptionsImpl;
/*      */ import sun.net.ResourceManager;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ class DatagramChannelImpl
/*      */   extends DatagramChannel
/*      */   implements SelChImpl
/*      */ {
/*   48 */   private static NativeDispatcher nd = new DatagramDispatcher();
/*      */ 
/*      */   
/*      */   private final FileDescriptor fd;
/*      */ 
/*      */   
/*      */   private final int fdVal;
/*      */   
/*      */   private final ProtocolFamily family;
/*      */   
/*   58 */   private volatile long readerThread = 0L;
/*   59 */   private volatile long writerThread = 0L;
/*      */ 
/*      */   
/*      */   private InetAddress cachedSenderInetAddress;
/*      */ 
/*      */   
/*      */   private int cachedSenderPort;
/*      */   
/*   67 */   private final Object readLock = new Object();
/*      */ 
/*      */   
/*   70 */   private final Object writeLock = new Object();
/*      */ 
/*      */ 
/*      */   
/*   74 */   private final Object stateLock = new Object();
/*      */   
/*      */   private static final int ST_UNINITIALIZED = -1;
/*      */   
/*      */   private static final int ST_UNCONNECTED = 0;
/*      */   
/*      */   private static final int ST_CONNECTED = 1;
/*      */   
/*      */   private static final int ST_KILLED = 2;
/*   83 */   private int state = -1;
/*      */ 
/*      */   
/*      */   private InetSocketAddress localAddress;
/*      */ 
/*      */   
/*      */   private InetSocketAddress remoteAddress;
/*      */ 
/*      */   
/*      */   private DatagramSocket socket;
/*      */ 
/*      */   
/*      */   private MembershipRegistry registry;
/*      */ 
/*      */   
/*      */   private boolean reuseAddressEmulated;
/*      */   
/*      */   private boolean isReuseAddress;
/*      */   
/*      */   private SocketAddress sender;
/*      */ 
/*      */   
/*      */   public DatagramChannelImpl(SelectorProvider paramSelectorProvider) throws IOException {
/*  106 */     super(paramSelectorProvider);
/*  107 */     ResourceManager.beforeUdpCreate();
/*      */     try {
/*  109 */       this.family = Net.isIPv6Available() ? StandardProtocolFamily.INET6 : StandardProtocolFamily.INET;
/*      */       
/*  111 */       this.fd = Net.socket(this.family, false);
/*  112 */       this.fdVal = IOUtil.fdVal(this.fd);
/*  113 */       this.state = 0;
/*  114 */     } catch (IOException iOException) {
/*  115 */       ResourceManager.afterUdpClose();
/*  116 */       throw iOException;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public DatagramChannelImpl(SelectorProvider paramSelectorProvider, ProtocolFamily paramProtocolFamily) throws IOException {
/*  123 */     super(paramSelectorProvider);
/*  124 */     if (paramProtocolFamily != StandardProtocolFamily.INET && paramProtocolFamily != StandardProtocolFamily.INET6) {
/*      */ 
/*      */       
/*  127 */       if (paramProtocolFamily == null) {
/*  128 */         throw new NullPointerException("'family' is null");
/*      */       }
/*  130 */       throw new UnsupportedOperationException("Protocol family not supported");
/*      */     } 
/*  132 */     if (paramProtocolFamily == StandardProtocolFamily.INET6 && 
/*  133 */       !Net.isIPv6Available()) {
/*  134 */       throw new UnsupportedOperationException("IPv6 not available");
/*      */     }
/*      */ 
/*      */     
/*  138 */     ResourceManager.beforeUdpCreate();
/*      */     try {
/*  140 */       this.family = paramProtocolFamily;
/*  141 */       this.fd = Net.socket(paramProtocolFamily, false);
/*  142 */       this.fdVal = IOUtil.fdVal(this.fd);
/*  143 */       this.state = 0;
/*  144 */     } catch (IOException iOException) {
/*  145 */       ResourceManager.afterUdpClose();
/*  146 */       throw iOException;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public DatagramChannelImpl(SelectorProvider paramSelectorProvider, FileDescriptor paramFileDescriptor) throws IOException {
/*  153 */     super(paramSelectorProvider);
/*      */ 
/*      */     
/*  156 */     ResourceManager.beforeUdpCreate();
/*      */     
/*  158 */     this.family = Net.isIPv6Available() ? StandardProtocolFamily.INET6 : StandardProtocolFamily.INET;
/*      */     
/*  160 */     this.fd = paramFileDescriptor;
/*  161 */     this.fdVal = IOUtil.fdVal(paramFileDescriptor);
/*  162 */     this.state = 0;
/*  163 */     this.localAddress = Net.localAddress(paramFileDescriptor);
/*      */   }
/*      */   
/*      */   public DatagramSocket socket() {
/*  167 */     synchronized (this.stateLock) {
/*  168 */       if (this.socket == null)
/*  169 */         this.socket = DatagramSocketAdaptor.create(this); 
/*  170 */       return this.socket;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public SocketAddress getLocalAddress() throws IOException {
/*  176 */     synchronized (this.stateLock) {
/*  177 */       if (!isOpen()) {
/*  178 */         throw new ClosedChannelException();
/*      */       }
/*  180 */       return Net.getRevealedLocalAddress(this.localAddress);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public SocketAddress getRemoteAddress() throws IOException {
/*  186 */     synchronized (this.stateLock) {
/*  187 */       if (!isOpen())
/*  188 */         throw new ClosedChannelException(); 
/*  189 */       return this.remoteAddress;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public <T> DatagramChannel setOption(SocketOption<T> paramSocketOption, T paramT) throws IOException {
/*  197 */     if (paramSocketOption == null)
/*  198 */       throw new NullPointerException(); 
/*  199 */     if (!supportedOptions().contains(paramSocketOption)) {
/*  200 */       throw new UnsupportedOperationException("'" + paramSocketOption + "' not supported");
/*      */     }
/*  202 */     synchronized (this.stateLock) {
/*  203 */       ensureOpen();
/*      */       
/*  205 */       if (paramSocketOption == StandardSocketOptions.IP_TOS || paramSocketOption == StandardSocketOptions.IP_MULTICAST_TTL || paramSocketOption == StandardSocketOptions.IP_MULTICAST_LOOP) {
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  210 */         Net.setSocketOption(this.fd, this.family, paramSocketOption, paramT);
/*  211 */         return this;
/*      */       } 
/*      */       
/*  214 */       if (paramSocketOption == StandardSocketOptions.IP_MULTICAST_IF) {
/*  215 */         if (paramT == null)
/*  216 */           throw new IllegalArgumentException("Cannot set IP_MULTICAST_IF to 'null'"); 
/*  217 */         NetworkInterface networkInterface = (NetworkInterface)paramT;
/*  218 */         if (this.family == StandardProtocolFamily.INET6) {
/*  219 */           int i = networkInterface.getIndex();
/*  220 */           if (i == -1)
/*  221 */             throw new IOException("Network interface cannot be identified"); 
/*  222 */           Net.setInterface6(this.fd, i);
/*      */         } else {
/*      */           
/*  225 */           Inet4Address inet4Address = Net.anyInet4Address(networkInterface);
/*  226 */           if (inet4Address == null)
/*  227 */             throw new IOException("Network interface not configured for IPv4"); 
/*  228 */           int i = Net.inet4AsInt(inet4Address);
/*  229 */           Net.setInterface4(this.fd, i);
/*      */         } 
/*  231 */         return this;
/*      */       } 
/*  233 */       if (paramSocketOption == StandardSocketOptions.SO_REUSEADDR && 
/*  234 */         Net.useExclusiveBind() && this.localAddress != null) {
/*      */         
/*  236 */         this.reuseAddressEmulated = true;
/*  237 */         this.isReuseAddress = ((Boolean)paramT).booleanValue();
/*      */       } 
/*      */ 
/*      */       
/*  241 */       Net.setSocketOption(this.fd, Net.UNSPEC, paramSocketOption, paramT);
/*  242 */       return this;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public <T> T getOption(SocketOption<T> paramSocketOption) throws IOException {
/*  251 */     if (paramSocketOption == null)
/*  252 */       throw new NullPointerException(); 
/*  253 */     if (!supportedOptions().contains(paramSocketOption)) {
/*  254 */       throw new UnsupportedOperationException("'" + paramSocketOption + "' not supported");
/*      */     }
/*  256 */     synchronized (this.stateLock) {
/*  257 */       ensureOpen();
/*      */       
/*  259 */       if (paramSocketOption == StandardSocketOptions.IP_TOS || paramSocketOption == StandardSocketOptions.IP_MULTICAST_TTL || paramSocketOption == StandardSocketOptions.IP_MULTICAST_LOOP)
/*      */       {
/*      */ 
/*      */         
/*  263 */         return (T)Net.getSocketOption(this.fd, this.family, paramSocketOption);
/*      */       }
/*      */       
/*  266 */       if (paramSocketOption == StandardSocketOptions.IP_MULTICAST_IF) {
/*  267 */         if (this.family == StandardProtocolFamily.INET) {
/*  268 */           int j = Net.getInterface4(this.fd);
/*  269 */           if (j == 0) {
/*  270 */             return null;
/*      */           }
/*  272 */           InetAddress inetAddress = Net.inet4FromInt(j);
/*  273 */           NetworkInterface networkInterface1 = NetworkInterface.getByInetAddress(inetAddress);
/*  274 */           if (networkInterface1 == null)
/*  275 */             throw new IOException("Unable to map address to interface"); 
/*  276 */           return (T)networkInterface1;
/*      */         } 
/*  278 */         int i = Net.getInterface6(this.fd);
/*  279 */         if (i == 0) {
/*  280 */           return null;
/*      */         }
/*  282 */         NetworkInterface networkInterface = NetworkInterface.getByIndex(i);
/*  283 */         if (networkInterface == null)
/*  284 */           throw new IOException("Unable to map index to interface"); 
/*  285 */         return (T)networkInterface;
/*      */       } 
/*      */ 
/*      */       
/*  289 */       if (paramSocketOption == StandardSocketOptions.SO_REUSEADDR && this.reuseAddressEmulated)
/*      */       {
/*      */         
/*  292 */         return (T)Boolean.valueOf(this.isReuseAddress);
/*      */       }
/*      */ 
/*      */       
/*  296 */       return (T)Net.getSocketOption(this.fd, Net.UNSPEC, paramSocketOption);
/*      */     } 
/*      */   }
/*      */   
/*      */   private static class DefaultOptionsHolder {
/*  301 */     static final Set<SocketOption<?>> defaultOptions = defaultOptions();
/*      */     
/*      */     private static Set<SocketOption<?>> defaultOptions() {
/*  304 */       HashSet<SocketOption<Integer>> hashSet = new HashSet(8);
/*  305 */       hashSet.add(StandardSocketOptions.SO_SNDBUF);
/*  306 */       hashSet.add(StandardSocketOptions.SO_RCVBUF);
/*  307 */       hashSet.add(StandardSocketOptions.SO_REUSEADDR);
/*  308 */       hashSet.add(StandardSocketOptions.SO_BROADCAST);
/*  309 */       hashSet.add(StandardSocketOptions.IP_TOS);
/*  310 */       hashSet.add(StandardSocketOptions.IP_MULTICAST_IF);
/*  311 */       hashSet.add(StandardSocketOptions.IP_MULTICAST_TTL);
/*  312 */       hashSet.add(StandardSocketOptions.IP_MULTICAST_LOOP);
/*  313 */       if (ExtendedOptionsImpl.flowSupported()) {
/*  314 */         hashSet.add(ExtendedSocketOptions.SO_FLOW_SLA);
/*      */       }
/*  316 */       return Collections.unmodifiableSet(hashSet);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public final Set<SocketOption<?>> supportedOptions() {
/*  322 */     return DefaultOptionsHolder.defaultOptions;
/*      */   }
/*      */   
/*      */   private void ensureOpen() throws ClosedChannelException {
/*  326 */     if (!isOpen()) {
/*  327 */       throw new ClosedChannelException();
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public SocketAddress receive(ByteBuffer paramByteBuffer) throws IOException {
/*  333 */     if (paramByteBuffer.isReadOnly())
/*  334 */       throw new IllegalArgumentException("Read-only buffer"); 
/*  335 */     if (paramByteBuffer == null)
/*  336 */       throw new NullPointerException(); 
/*  337 */     synchronized (this.readLock) {
/*  338 */       ensureOpen();
/*      */       
/*  340 */       if (localAddress() == null)
/*  341 */         bind((SocketAddress)null); 
/*  342 */       int i = 0;
/*  343 */       ByteBuffer byteBuffer = null;
/*      */       try {
/*  345 */         begin();
/*  346 */         if (!isOpen())
/*  347 */           return null; 
/*  348 */         SecurityManager securityManager = System.getSecurityManager();
/*  349 */         this.readerThread = NativeThread.current();
/*  350 */         if (isConnected() || securityManager == null) {
/*      */           do {
/*  352 */             i = receive(this.fd, paramByteBuffer);
/*  353 */           } while (i == -3 && isOpen());
/*  354 */           if (i == -2)
/*  355 */             return null; 
/*      */         } else {
/*  357 */           byteBuffer = Util.getTemporaryDirectBuffer(paramByteBuffer.remaining());
/*      */           
/*      */           while (true) {
/*  360 */             i = receive(this.fd, byteBuffer);
/*  361 */             if (i != -3 || !isOpen()) {
/*  362 */               if (i == -2)
/*  363 */                 return null; 
/*  364 */               InetSocketAddress inetSocketAddress = (InetSocketAddress)this.sender;
/*      */               try {
/*  366 */                 securityManager.checkAccept(inetSocketAddress
/*  367 */                     .getAddress().getHostAddress(), inetSocketAddress
/*  368 */                     .getPort()); break;
/*  369 */               } catch (SecurityException securityException) {
/*      */                 
/*  371 */                 byteBuffer.clear();
/*  372 */                 i = 0;
/*      */               } 
/*      */             } 
/*  375 */           }  byteBuffer.flip();
/*  376 */           paramByteBuffer.put(byteBuffer);
/*      */         } 
/*      */ 
/*      */         
/*  380 */         return this.sender;
/*      */       } finally {
/*  382 */         if (byteBuffer != null)
/*  383 */           Util.releaseTemporaryDirectBuffer(byteBuffer); 
/*  384 */         this.readerThread = 0L;
/*  385 */         end((i > 0 || i == -2));
/*  386 */         assert IOStatus.check(i);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private int receive(FileDescriptor paramFileDescriptor, ByteBuffer paramByteBuffer) throws IOException {
/*  394 */     int i = paramByteBuffer.position();
/*  395 */     int j = paramByteBuffer.limit();
/*  396 */     assert i <= j;
/*  397 */     boolean bool = (i <= j) ? (j - i) : false;
/*  398 */     if (paramByteBuffer instanceof DirectBuffer && bool) {
/*  399 */       return receiveIntoNativeBuffer(paramFileDescriptor, paramByteBuffer, bool, i);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  404 */     int k = Math.max(bool, 1);
/*  405 */     ByteBuffer byteBuffer = Util.getTemporaryDirectBuffer(k);
/*      */     try {
/*  407 */       int m = receiveIntoNativeBuffer(paramFileDescriptor, byteBuffer, k, 0);
/*  408 */       byteBuffer.flip();
/*  409 */       if (m > 0 && bool)
/*  410 */         paramByteBuffer.put(byteBuffer); 
/*  411 */       return m;
/*      */     } finally {
/*  413 */       Util.releaseTemporaryDirectBuffer(byteBuffer);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int receiveIntoNativeBuffer(FileDescriptor paramFileDescriptor, ByteBuffer paramByteBuffer, int paramInt1, int paramInt2) throws IOException {
/*  421 */     int i = receive0(paramFileDescriptor, ((DirectBuffer)paramByteBuffer).address() + paramInt2, paramInt1, 
/*  422 */         isConnected());
/*  423 */     if (i > 0)
/*  424 */       paramByteBuffer.position(paramInt2 + i); 
/*  425 */     return i;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public int send(ByteBuffer paramByteBuffer, SocketAddress paramSocketAddress) throws IOException {
/*  431 */     if (paramByteBuffer == null) {
/*  432 */       throw new NullPointerException();
/*      */     }
/*  434 */     synchronized (this.writeLock) {
/*  435 */       ensureOpen();
/*  436 */       InetSocketAddress inetSocketAddress = Net.checkAddress(paramSocketAddress);
/*  437 */       InetAddress inetAddress = inetSocketAddress.getAddress();
/*  438 */       if (inetAddress == null)
/*  439 */         throw new IOException("Target address not resolved"); 
/*  440 */       synchronized (this.stateLock) {
/*  441 */         if (!isConnected()) {
/*  442 */           if (paramSocketAddress == null)
/*  443 */             throw new NullPointerException(); 
/*  444 */           SecurityManager securityManager = System.getSecurityManager();
/*  445 */           if (securityManager != null) {
/*  446 */             if (inetAddress.isMulticastAddress()) {
/*  447 */               securityManager.checkMulticast(inetAddress);
/*      */             } else {
/*  449 */               securityManager.checkConnect(inetAddress.getHostAddress(), inetSocketAddress
/*  450 */                   .getPort());
/*      */             } 
/*      */           }
/*      */         } else {
/*  454 */           if (!paramSocketAddress.equals(this.remoteAddress)) {
/*  455 */             throw new IllegalArgumentException("Connected address not equal to target address");
/*      */           }
/*      */           
/*  458 */           return write(paramByteBuffer);
/*      */         } 
/*      */       } 
/*      */       
/*  462 */       int i = 0;
/*      */       try {
/*  464 */         begin();
/*  465 */         if (!isOpen())
/*  466 */           return 0; 
/*  467 */         this.writerThread = NativeThread.current();
/*      */         do {
/*  469 */           i = send(this.fd, paramByteBuffer, inetSocketAddress);
/*  470 */         } while (i == -3 && isOpen());
/*      */         
/*  472 */         synchronized (this.stateLock) {
/*  473 */           if (isOpen() && this.localAddress == null) {
/*  474 */             this.localAddress = Net.localAddress(this.fd);
/*      */           }
/*      */         } 
/*  477 */         return IOStatus.normalize(i);
/*      */       } finally {
/*  479 */         this.writerThread = 0L;
/*  480 */         end((i > 0 || i == -2));
/*  481 */         assert IOStatus.check(i);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private int send(FileDescriptor paramFileDescriptor, ByteBuffer paramByteBuffer, InetSocketAddress paramInetSocketAddress) throws IOException {
/*  489 */     if (paramByteBuffer instanceof DirectBuffer) {
/*  490 */       return sendFromNativeBuffer(paramFileDescriptor, paramByteBuffer, paramInetSocketAddress);
/*      */     }
/*      */     
/*  493 */     int i = paramByteBuffer.position();
/*  494 */     int j = paramByteBuffer.limit();
/*  495 */     assert i <= j;
/*  496 */     boolean bool = (i <= j) ? (j - i) : false;
/*      */     
/*  498 */     ByteBuffer byteBuffer = Util.getTemporaryDirectBuffer(bool);
/*      */     try {
/*  500 */       byteBuffer.put(paramByteBuffer);
/*  501 */       byteBuffer.flip();
/*      */       
/*  503 */       paramByteBuffer.position(i);
/*      */       
/*  505 */       int k = sendFromNativeBuffer(paramFileDescriptor, byteBuffer, paramInetSocketAddress);
/*  506 */       if (k > 0)
/*      */       {
/*  508 */         paramByteBuffer.position(i + k);
/*      */       }
/*  510 */       return k;
/*      */     } finally {
/*  512 */       Util.releaseTemporaryDirectBuffer(byteBuffer);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private int sendFromNativeBuffer(FileDescriptor paramFileDescriptor, ByteBuffer paramByteBuffer, InetSocketAddress paramInetSocketAddress) throws IOException {
/*      */     byte b2;
/*  520 */     int i = paramByteBuffer.position();
/*  521 */     int j = paramByteBuffer.limit();
/*  522 */     assert i <= j;
/*  523 */     byte b1 = (i <= j) ? (j - i) : 0;
/*      */     
/*  525 */     boolean bool = (this.family != StandardProtocolFamily.INET) ? true : false;
/*      */     
/*      */     try {
/*  528 */       b2 = send0(bool, paramFileDescriptor, ((DirectBuffer)paramByteBuffer).address() + i, b1, paramInetSocketAddress
/*  529 */           .getAddress(), paramInetSocketAddress.getPort());
/*  530 */     } catch (PortUnreachableException portUnreachableException) {
/*  531 */       if (isConnected())
/*  532 */         throw portUnreachableException; 
/*  533 */       b2 = b1;
/*      */     } 
/*  535 */     if (b2)
/*  536 */       paramByteBuffer.position(i + b2); 
/*  537 */     return b2;
/*      */   }
/*      */   
/*      */   public int read(ByteBuffer paramByteBuffer) throws IOException {
/*  541 */     if (paramByteBuffer == null)
/*  542 */       throw new NullPointerException(); 
/*  543 */     synchronized (this.readLock) {
/*  544 */       synchronized (this.stateLock) {
/*  545 */         ensureOpen();
/*  546 */         if (!isConnected())
/*  547 */           throw new NotYetConnectedException(); 
/*      */       } 
/*  549 */       int i = 0;
/*      */       try {
/*  551 */         begin();
/*  552 */         if (!isOpen())
/*  553 */           return 0; 
/*  554 */         this.readerThread = NativeThread.current();
/*      */         do {
/*  556 */           i = IOUtil.read(this.fd, paramByteBuffer, -1L, nd);
/*  557 */         } while (i == -3 && isOpen());
/*  558 */         return IOStatus.normalize(i);
/*      */       } finally {
/*  560 */         this.readerThread = 0L;
/*  561 */         end((i > 0 || i == -2));
/*  562 */         assert IOStatus.check(i);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public long read(ByteBuffer[] paramArrayOfByteBuffer, int paramInt1, int paramInt2) throws IOException {
/*  570 */     if (paramInt1 < 0 || paramInt2 < 0 || paramInt1 > paramArrayOfByteBuffer.length - paramInt2)
/*  571 */       throw new IndexOutOfBoundsException(); 
/*  572 */     synchronized (this.readLock) {
/*  573 */       synchronized (this.stateLock) {
/*  574 */         ensureOpen();
/*  575 */         if (!isConnected())
/*  576 */           throw new NotYetConnectedException(); 
/*      */       } 
/*  578 */       long l = 0L;
/*      */       try {
/*  580 */         begin();
/*  581 */         if (!isOpen())
/*  582 */           return 0L; 
/*  583 */         this.readerThread = NativeThread.current();
/*      */         do {
/*  585 */           l = IOUtil.read(this.fd, paramArrayOfByteBuffer, paramInt1, paramInt2, nd);
/*  586 */         } while (l == -3L && isOpen());
/*  587 */         return IOStatus.normalize(l);
/*      */       } finally {
/*  589 */         this.readerThread = 0L;
/*  590 */         end((l > 0L || l == -2L));
/*  591 */         assert IOStatus.check(l);
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   public int write(ByteBuffer paramByteBuffer) throws IOException {
/*  597 */     if (paramByteBuffer == null)
/*  598 */       throw new NullPointerException(); 
/*  599 */     synchronized (this.writeLock) {
/*  600 */       synchronized (this.stateLock) {
/*  601 */         ensureOpen();
/*  602 */         if (!isConnected())
/*  603 */           throw new NotYetConnectedException(); 
/*      */       } 
/*  605 */       int i = 0;
/*      */       try {
/*  607 */         begin();
/*  608 */         if (!isOpen())
/*  609 */           return 0; 
/*  610 */         this.writerThread = NativeThread.current();
/*      */         do {
/*  612 */           i = IOUtil.write(this.fd, paramByteBuffer, -1L, nd);
/*  613 */         } while (i == -3 && isOpen());
/*  614 */         return IOStatus.normalize(i);
/*      */       } finally {
/*  616 */         this.writerThread = 0L;
/*  617 */         end((i > 0 || i == -2));
/*  618 */         assert IOStatus.check(i);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public long write(ByteBuffer[] paramArrayOfByteBuffer, int paramInt1, int paramInt2) throws IOException {
/*  626 */     if (paramInt1 < 0 || paramInt2 < 0 || paramInt1 > paramArrayOfByteBuffer.length - paramInt2)
/*  627 */       throw new IndexOutOfBoundsException(); 
/*  628 */     synchronized (this.writeLock) {
/*  629 */       synchronized (this.stateLock) {
/*  630 */         ensureOpen();
/*  631 */         if (!isConnected())
/*  632 */           throw new NotYetConnectedException(); 
/*      */       } 
/*  634 */       long l = 0L;
/*      */       try {
/*  636 */         begin();
/*  637 */         if (!isOpen())
/*  638 */           return 0L; 
/*  639 */         this.writerThread = NativeThread.current();
/*      */         do {
/*  641 */           l = IOUtil.write(this.fd, paramArrayOfByteBuffer, paramInt1, paramInt2, nd);
/*  642 */         } while (l == -3L && isOpen());
/*  643 */         return IOStatus.normalize(l);
/*      */       } finally {
/*  645 */         this.writerThread = 0L;
/*  646 */         end((l > 0L || l == -2L));
/*  647 */         assert IOStatus.check(l);
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   protected void implConfigureBlocking(boolean paramBoolean) throws IOException {
/*  653 */     IOUtil.configureBlocking(this.fd, paramBoolean);
/*      */   }
/*      */   
/*      */   public SocketAddress localAddress() {
/*  657 */     synchronized (this.stateLock) {
/*  658 */       return this.localAddress;
/*      */     } 
/*      */   }
/*      */   
/*      */   public SocketAddress remoteAddress() {
/*  663 */     synchronized (this.stateLock) {
/*  664 */       return this.remoteAddress;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public DatagramChannel bind(SocketAddress paramSocketAddress) throws IOException {
/*  670 */     synchronized (this.readLock) {
/*  671 */       synchronized (this.writeLock) {
/*  672 */         synchronized (this.stateLock) {
/*  673 */           InetSocketAddress inetSocketAddress; ensureOpen();
/*  674 */           if (this.localAddress != null) {
/*  675 */             throw new AlreadyBoundException();
/*      */           }
/*  677 */           if (paramSocketAddress == null) {
/*      */             
/*  679 */             if (this.family == StandardProtocolFamily.INET) {
/*  680 */               inetSocketAddress = new InetSocketAddress(InetAddress.getByName("0.0.0.0"), 0);
/*      */             } else {
/*  682 */               inetSocketAddress = new InetSocketAddress(0);
/*      */             } 
/*      */           } else {
/*  685 */             inetSocketAddress = Net.checkAddress(paramSocketAddress);
/*      */ 
/*      */             
/*  688 */             if (this.family == StandardProtocolFamily.INET) {
/*  689 */               InetAddress inetAddress = inetSocketAddress.getAddress();
/*  690 */               if (!(inetAddress instanceof Inet4Address))
/*  691 */                 throw new UnsupportedAddressTypeException(); 
/*      */             } 
/*      */           } 
/*  694 */           SecurityManager securityManager = System.getSecurityManager();
/*  695 */           if (securityManager != null) {
/*  696 */             securityManager.checkListen(inetSocketAddress.getPort());
/*      */           }
/*  698 */           Net.bind(this.family, this.fd, inetSocketAddress.getAddress(), inetSocketAddress.getPort());
/*  699 */           this.localAddress = Net.localAddress(this.fd);
/*      */         } 
/*      */       } 
/*      */     } 
/*  703 */     return this;
/*      */   }
/*      */   
/*      */   public boolean isConnected() {
/*  707 */     synchronized (this.stateLock) {
/*  708 */       return (this.state == 1);
/*      */     } 
/*      */   }
/*      */   
/*      */   void ensureOpenAndUnconnected() throws IOException {
/*  713 */     synchronized (this.stateLock) {
/*  714 */       if (!isOpen())
/*  715 */         throw new ClosedChannelException(); 
/*  716 */       if (this.state != 0) {
/*  717 */         throw new IllegalStateException("Connect already invoked");
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public DatagramChannel connect(SocketAddress paramSocketAddress) throws IOException {
/*  723 */     boolean bool = false;
/*      */     
/*  725 */     synchronized (this.readLock) {
/*  726 */       synchronized (this.writeLock) {
/*  727 */         synchronized (this.stateLock) {
/*  728 */           ensureOpenAndUnconnected();
/*  729 */           InetSocketAddress inetSocketAddress = Net.checkAddress(paramSocketAddress);
/*  730 */           SecurityManager securityManager = System.getSecurityManager();
/*  731 */           if (securityManager != null)
/*  732 */             securityManager.checkConnect(inetSocketAddress.getAddress().getHostAddress(), inetSocketAddress
/*  733 */                 .getPort()); 
/*  734 */           int i = Net.connect(this.family, this.fd, inetSocketAddress
/*      */               
/*  736 */               .getAddress(), inetSocketAddress
/*  737 */               .getPort());
/*  738 */           if (i <= 0) {
/*  739 */             throw new Error();
/*      */           }
/*      */           
/*  742 */           this.state = 1;
/*  743 */           this.remoteAddress = inetSocketAddress;
/*  744 */           this.sender = inetSocketAddress;
/*  745 */           this.cachedSenderInetAddress = inetSocketAddress.getAddress();
/*  746 */           this.cachedSenderPort = inetSocketAddress.getPort();
/*      */ 
/*      */           
/*  749 */           this.localAddress = Net.localAddress(this.fd);
/*      */ 
/*      */           
/*  752 */           synchronized (blockingLock()) {
/*  753 */             boolean bool1 = isBlocking();
/*      */             
/*      */             try {
/*  756 */               ByteBuffer byteBuffer = ByteBuffer.allocate(1);
/*  757 */               if (bool1) {
/*  758 */                 configureBlocking(false);
/*      */               }
/*      */               do {
/*  761 */                 byteBuffer.clear();
/*  762 */               } while (receive(byteBuffer) != null);
/*      */             } finally {
/*  764 */               if (bool1) {
/*  765 */                 configureBlocking(true);
/*      */               }
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*  772 */     return this;
/*      */   }
/*      */   
/*      */   public DatagramChannel disconnect() throws IOException {
/*  776 */     synchronized (this.readLock) {
/*  777 */       synchronized (this.writeLock) {
/*  778 */         synchronized (this.stateLock) {
/*  779 */           if (!isConnected() || !isOpen())
/*  780 */             return this; 
/*  781 */           InetSocketAddress inetSocketAddress = this.remoteAddress;
/*  782 */           SecurityManager securityManager = System.getSecurityManager();
/*  783 */           if (securityManager != null)
/*  784 */             securityManager.checkConnect(inetSocketAddress.getAddress().getHostAddress(), inetSocketAddress
/*  785 */                 .getPort()); 
/*  786 */           boolean bool = (this.family == StandardProtocolFamily.INET6) ? true : false;
/*  787 */           disconnect0(this.fd, bool);
/*  788 */           this.remoteAddress = null;
/*  789 */           this.state = 0;
/*      */ 
/*      */           
/*  792 */           this.localAddress = Net.localAddress(this.fd);
/*      */         } 
/*      */       } 
/*      */     } 
/*  796 */     return this;
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
/*      */   private MembershipKey innerJoin(InetAddress paramInetAddress1, NetworkInterface paramNetworkInterface, InetAddress paramInetAddress2) throws IOException {
/*  808 */     if (!paramInetAddress1.isMulticastAddress()) {
/*  809 */       throw new IllegalArgumentException("Group not a multicast address");
/*      */     }
/*      */     
/*  812 */     if (paramInetAddress1 instanceof Inet4Address) {
/*  813 */       if (this.family == StandardProtocolFamily.INET6 && !Net.canIPv6SocketJoinIPv4Group())
/*  814 */         throw new IllegalArgumentException("IPv6 socket cannot join IPv4 multicast group"); 
/*  815 */     } else if (paramInetAddress1 instanceof java.net.Inet6Address) {
/*  816 */       if (this.family != StandardProtocolFamily.INET6)
/*  817 */         throw new IllegalArgumentException("Only IPv6 sockets can join IPv6 multicast group"); 
/*      */     } else {
/*  819 */       throw new IllegalArgumentException("Address type not supported");
/*      */     } 
/*      */ 
/*      */     
/*  823 */     if (paramInetAddress2 != null) {
/*  824 */       if (paramInetAddress2.isAnyLocalAddress())
/*  825 */         throw new IllegalArgumentException("Source address is a wildcard address"); 
/*  826 */       if (paramInetAddress2.isMulticastAddress())
/*  827 */         throw new IllegalArgumentException("Source address is multicast address"); 
/*  828 */       if (paramInetAddress2.getClass() != paramInetAddress1.getClass()) {
/*  829 */         throw new IllegalArgumentException("Source address is different type to group");
/*      */       }
/*      */     } 
/*  832 */     SecurityManager securityManager = System.getSecurityManager();
/*  833 */     if (securityManager != null) {
/*  834 */       securityManager.checkMulticast(paramInetAddress1);
/*      */     }
/*  836 */     synchronized (this.stateLock) {
/*  837 */       MembershipKey membershipKey; if (!isOpen()) {
/*  838 */         throw new ClosedChannelException();
/*      */       }
/*      */       
/*  841 */       if (this.registry == null) {
/*  842 */         this.registry = new MembershipRegistry();
/*      */       } else {
/*      */         
/*  845 */         membershipKey = this.registry.checkMembership(paramInetAddress1, paramNetworkInterface, paramInetAddress2);
/*  846 */         if (membershipKey != null) {
/*  847 */           return membershipKey;
/*      */         }
/*      */       } 
/*      */       
/*  851 */       if (this.family == StandardProtocolFamily.INET6 && (paramInetAddress1 instanceof java.net.Inet6Address || 
/*  852 */         Net.canJoin6WithIPv4Group())) {
/*      */         
/*  854 */         int i = paramNetworkInterface.getIndex();
/*  855 */         if (i == -1) {
/*  856 */           throw new IOException("Network interface cannot be identified");
/*      */         }
/*      */         
/*  859 */         byte[] arrayOfByte1 = Net.inet6AsByteArray(paramInetAddress1);
/*      */         
/*  861 */         byte[] arrayOfByte2 = (paramInetAddress2 == null) ? null : Net.inet6AsByteArray(paramInetAddress2);
/*      */ 
/*      */         
/*  864 */         int j = Net.join6(this.fd, arrayOfByte1, i, arrayOfByte2);
/*  865 */         if (j == -2) {
/*  866 */           throw new UnsupportedOperationException();
/*      */         }
/*  868 */         membershipKey = new MembershipKeyImpl.Type6(this, paramInetAddress1, paramNetworkInterface, paramInetAddress2, arrayOfByte1, i, arrayOfByte2);
/*      */       
/*      */       }
/*      */       else {
/*      */         
/*  873 */         Inet4Address inet4Address = Net.anyInet4Address(paramNetworkInterface);
/*  874 */         if (inet4Address == null) {
/*  875 */           throw new IOException("Network interface not configured for IPv4");
/*      */         }
/*  877 */         int i = Net.inet4AsInt(paramInetAddress1);
/*  878 */         int j = Net.inet4AsInt(inet4Address);
/*  879 */         boolean bool = (paramInetAddress2 == null) ? false : Net.inet4AsInt(paramInetAddress2);
/*      */ 
/*      */         
/*  882 */         int k = Net.join4(this.fd, i, j, bool);
/*  883 */         if (k == -2) {
/*  884 */           throw new UnsupportedOperationException();
/*      */         }
/*  886 */         membershipKey = new MembershipKeyImpl.Type4(this, paramInetAddress1, paramNetworkInterface, paramInetAddress2, i, j, bool);
/*      */       } 
/*      */ 
/*      */       
/*  890 */       this.registry.add((MembershipKeyImpl)membershipKey);
/*  891 */       return membershipKey;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public MembershipKey join(InetAddress paramInetAddress, NetworkInterface paramNetworkInterface) throws IOException {
/*  900 */     return innerJoin(paramInetAddress, paramNetworkInterface, (InetAddress)null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public MembershipKey join(InetAddress paramInetAddress1, NetworkInterface paramNetworkInterface, InetAddress paramInetAddress2) throws IOException {
/*  909 */     if (paramInetAddress2 == null)
/*  910 */       throw new NullPointerException("source address is null"); 
/*  911 */     return innerJoin(paramInetAddress1, paramNetworkInterface, paramInetAddress2);
/*      */   }
/*      */ 
/*      */   
/*      */   void drop(MembershipKeyImpl paramMembershipKeyImpl) {
/*  916 */     assert paramMembershipKeyImpl.channel() == this;
/*      */     
/*  918 */     synchronized (this.stateLock) {
/*  919 */       if (!paramMembershipKeyImpl.isValid()) {
/*      */         return;
/*      */       }
/*      */       try {
/*  923 */         if (paramMembershipKeyImpl instanceof MembershipKeyImpl.Type6) {
/*  924 */           MembershipKeyImpl.Type6 type6 = (MembershipKeyImpl.Type6)paramMembershipKeyImpl;
/*      */           
/*  926 */           Net.drop6(this.fd, type6.groupAddress(), type6.index(), type6.source());
/*      */         } else {
/*  928 */           MembershipKeyImpl.Type4 type4 = (MembershipKeyImpl.Type4)paramMembershipKeyImpl;
/*  929 */           Net.drop4(this.fd, type4.groupAddress(), type4.interfaceAddress(), type4
/*  930 */               .source());
/*      */         } 
/*  932 */       } catch (IOException iOException) {
/*      */         
/*  934 */         throw new AssertionError(iOException);
/*      */       } 
/*      */       
/*  937 */       paramMembershipKeyImpl.invalidate();
/*  938 */       this.registry.remove(paramMembershipKeyImpl);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void block(MembershipKeyImpl paramMembershipKeyImpl, InetAddress paramInetAddress) throws IOException {
/*  949 */     assert paramMembershipKeyImpl.channel() == this;
/*  950 */     assert paramMembershipKeyImpl.sourceAddress() == null;
/*      */     
/*  952 */     synchronized (this.stateLock) {
/*  953 */       int i; if (!paramMembershipKeyImpl.isValid())
/*  954 */         throw new IllegalStateException("key is no longer valid"); 
/*  955 */       if (paramInetAddress.isAnyLocalAddress())
/*  956 */         throw new IllegalArgumentException("Source address is a wildcard address"); 
/*  957 */       if (paramInetAddress.isMulticastAddress())
/*  958 */         throw new IllegalArgumentException("Source address is multicast address"); 
/*  959 */       if (paramInetAddress.getClass() != paramMembershipKeyImpl.group().getClass()) {
/*  960 */         throw new IllegalArgumentException("Source address is different type to group");
/*      */       }
/*      */       
/*  963 */       if (paramMembershipKeyImpl instanceof MembershipKeyImpl.Type6) {
/*  964 */         MembershipKeyImpl.Type6 type6 = (MembershipKeyImpl.Type6)paramMembershipKeyImpl;
/*      */         
/*  966 */         i = Net.block6(this.fd, type6.groupAddress(), type6.index(), 
/*  967 */             Net.inet6AsByteArray(paramInetAddress));
/*      */       } else {
/*  969 */         MembershipKeyImpl.Type4 type4 = (MembershipKeyImpl.Type4)paramMembershipKeyImpl;
/*      */         
/*  971 */         i = Net.block4(this.fd, type4.groupAddress(), type4.interfaceAddress(), 
/*  972 */             Net.inet4AsInt(paramInetAddress));
/*      */       } 
/*  974 */       if (i == -2)
/*      */       {
/*  976 */         throw new UnsupportedOperationException();
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void unblock(MembershipKeyImpl paramMembershipKeyImpl, InetAddress paramInetAddress) {
/*  985 */     assert paramMembershipKeyImpl.channel() == this;
/*  986 */     assert paramMembershipKeyImpl.sourceAddress() == null;
/*      */     
/*  988 */     synchronized (this.stateLock) {
/*  989 */       if (!paramMembershipKeyImpl.isValid()) {
/*  990 */         throw new IllegalStateException("key is no longer valid");
/*      */       }
/*      */       try {
/*  993 */         if (paramMembershipKeyImpl instanceof MembershipKeyImpl.Type6) {
/*  994 */           MembershipKeyImpl.Type6 type6 = (MembershipKeyImpl.Type6)paramMembershipKeyImpl;
/*      */           
/*  996 */           Net.unblock6(this.fd, type6.groupAddress(), type6.index(), 
/*  997 */               Net.inet6AsByteArray(paramInetAddress));
/*      */         } else {
/*  999 */           MembershipKeyImpl.Type4 type4 = (MembershipKeyImpl.Type4)paramMembershipKeyImpl;
/*      */           
/* 1001 */           Net.unblock4(this.fd, type4.groupAddress(), type4.interfaceAddress(), 
/* 1002 */               Net.inet4AsInt(paramInetAddress));
/*      */         } 
/* 1004 */       } catch (IOException iOException) {
/*      */         
/* 1006 */         throw new AssertionError(iOException);
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   protected void implCloseSelectableChannel() throws IOException {
/* 1012 */     synchronized (this.stateLock) {
/* 1013 */       if (this.state != 2)
/* 1014 */         nd.preClose(this.fd); 
/* 1015 */       ResourceManager.afterUdpClose();
/*      */ 
/*      */       
/* 1018 */       if (this.registry != null) {
/* 1019 */         this.registry.invalidateAll();
/*      */       }
/*      */       long l;
/* 1022 */       if ((l = this.readerThread) != 0L)
/* 1023 */         NativeThread.signal(l); 
/* 1024 */       if ((l = this.writerThread) != 0L)
/* 1025 */         NativeThread.signal(l); 
/* 1026 */       if (!isRegistered())
/* 1027 */         kill(); 
/*      */     } 
/*      */   }
/*      */   
/*      */   public void kill() throws IOException {
/* 1032 */     synchronized (this.stateLock) {
/* 1033 */       if (this.state == 2)
/*      */         return; 
/* 1035 */       if (this.state == -1) {
/* 1036 */         this.state = 2;
/*      */         return;
/*      */       } 
/* 1039 */       assert !isOpen() && !isRegistered();
/* 1040 */       nd.close(this.fd);
/* 1041 */       this.state = 2;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected void finalize() throws IOException {
/* 1047 */     if (this.fd != null) {
/* 1048 */       close();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean translateReadyOps(int paramInt1, int paramInt2, SelectionKeyImpl paramSelectionKeyImpl) {
/* 1056 */     int i = paramSelectionKeyImpl.nioInterestOps();
/* 1057 */     int j = paramSelectionKeyImpl.nioReadyOps();
/* 1058 */     int k = paramInt2;
/*      */     
/* 1060 */     if ((paramInt1 & Net.POLLNVAL) != 0)
/*      */     {
/*      */ 
/*      */       
/* 1064 */       return false;
/*      */     }
/*      */     
/* 1067 */     if ((paramInt1 & (Net.POLLERR | Net.POLLHUP)) != 0) {
/* 1068 */       k = i;
/* 1069 */       paramSelectionKeyImpl.nioReadyOps(k);
/* 1070 */       return ((k & (j ^ 0xFFFFFFFF)) != 0);
/*      */     } 
/*      */     
/* 1073 */     if ((paramInt1 & Net.POLLIN) != 0 && (i & 0x1) != 0)
/*      */     {
/* 1075 */       k |= 0x1;
/*      */     }
/* 1077 */     if ((paramInt1 & Net.POLLOUT) != 0 && (i & 0x4) != 0)
/*      */     {
/* 1079 */       k |= 0x4;
/*      */     }
/* 1081 */     paramSelectionKeyImpl.nioReadyOps(k);
/* 1082 */     return ((k & (j ^ 0xFFFFFFFF)) != 0);
/*      */   }
/*      */   
/*      */   public boolean translateAndUpdateReadyOps(int paramInt, SelectionKeyImpl paramSelectionKeyImpl) {
/* 1086 */     return translateReadyOps(paramInt, paramSelectionKeyImpl.nioReadyOps(), paramSelectionKeyImpl);
/*      */   }
/*      */   
/*      */   public boolean translateAndSetReadyOps(int paramInt, SelectionKeyImpl paramSelectionKeyImpl) {
/* 1090 */     return translateReadyOps(paramInt, 0, paramSelectionKeyImpl);
/*      */   }
/*      */ 
/*      */   
/*      */   int poll(int paramInt, long paramLong) throws IOException {
/* 1095 */     assert Thread.holdsLock(blockingLock()) && !isBlocking();
/*      */     
/* 1097 */     synchronized (this.readLock) {
/* 1098 */       int i = 0;
/*      */       try {
/* 1100 */         begin();
/* 1101 */         synchronized (this.stateLock) {
/* 1102 */           if (!isOpen())
/* 1103 */             return 0; 
/* 1104 */           this.readerThread = NativeThread.current();
/*      */         } 
/* 1106 */         i = Net.poll(this.fd, paramInt, paramLong);
/*      */       } finally {
/* 1108 */         this.readerThread = 0L;
/* 1109 */         end((i > 0));
/*      */       } 
/* 1111 */       return i;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void translateAndSetInterestOps(int paramInt, SelectionKeyImpl paramSelectionKeyImpl) {
/* 1119 */     int i = 0;
/*      */     
/* 1121 */     if ((paramInt & 0x1) != 0)
/* 1122 */       i |= Net.POLLIN; 
/* 1123 */     if ((paramInt & 0x4) != 0)
/* 1124 */       i |= Net.POLLOUT; 
/* 1125 */     if ((paramInt & 0x8) != 0)
/* 1126 */       i |= Net.POLLIN; 
/* 1127 */     paramSelectionKeyImpl.selector.putEventOps(paramSelectionKeyImpl, i);
/*      */   }
/*      */   
/*      */   public FileDescriptor getFD() {
/* 1131 */     return this.fd;
/*      */   }
/*      */   
/*      */   public int getFDVal() {
/* 1135 */     return this.fdVal;
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
/*      */   static {
/* 1155 */     IOUtil.load();
/* 1156 */     initIDs();
/*      */   }
/*      */   
/*      */   private static native void initIDs();
/*      */   
/*      */   private static native void disconnect0(FileDescriptor paramFileDescriptor, boolean paramBoolean) throws IOException;
/*      */   
/*      */   private native int receive0(FileDescriptor paramFileDescriptor, long paramLong, int paramInt, boolean paramBoolean) throws IOException;
/*      */   
/*      */   private native int send0(boolean paramBoolean, FileDescriptor paramFileDescriptor, long paramLong, int paramInt1, InetAddress paramInetAddress, int paramInt2) throws IOException;
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/nio/ch/DatagramChannelImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */