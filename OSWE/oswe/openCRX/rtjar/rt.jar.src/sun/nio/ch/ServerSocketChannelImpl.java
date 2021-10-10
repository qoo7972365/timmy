/*     */ package sun.nio.ch;
/*     */ 
/*     */ import java.io.FileDescriptor;
/*     */ import java.io.IOException;
/*     */ import java.net.InetSocketAddress;
/*     */ import java.net.ServerSocket;
/*     */ import java.net.SocketAddress;
/*     */ import java.net.SocketOption;
/*     */ import java.net.StandardProtocolFamily;
/*     */ import java.net.StandardSocketOptions;
/*     */ import java.nio.channels.AlreadyBoundException;
/*     */ import java.nio.channels.ClosedChannelException;
/*     */ import java.nio.channels.NetworkChannel;
/*     */ import java.nio.channels.NotYetBoundException;
/*     */ import java.nio.channels.ServerSocketChannel;
/*     */ import java.nio.channels.SocketChannel;
/*     */ import java.nio.channels.spi.SelectorProvider;
/*     */ import java.util.Collections;
/*     */ import java.util.HashSet;
/*     */ import java.util.Set;
/*     */ import sun.net.NetHooks;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class ServerSocketChannelImpl
/*     */   extends ServerSocketChannel
/*     */   implements SelChImpl
/*     */ {
/*  54 */   private volatile long thread = 0L;
/*     */ 
/*     */   
/*  57 */   private final Object lock = new Object();
/*     */ 
/*     */ 
/*     */   
/*  61 */   private final Object stateLock = new Object();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  69 */   private int state = -1;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   ServerSocketChannelImpl(SelectorProvider paramSelectorProvider) throws IOException {
/*  84 */     super(paramSelectorProvider);
/*  85 */     this.fd = Net.serverSocket(true);
/*  86 */     this.fdVal = IOUtil.fdVal(this.fd);
/*  87 */     this.state = 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   ServerSocketChannelImpl(SelectorProvider paramSelectorProvider, FileDescriptor paramFileDescriptor, boolean paramBoolean) throws IOException {
/*  95 */     super(paramSelectorProvider);
/*  96 */     this.fd = paramFileDescriptor;
/*  97 */     this.fdVal = IOUtil.fdVal(paramFileDescriptor);
/*  98 */     this.state = 0;
/*  99 */     if (paramBoolean)
/* 100 */       this.localAddress = Net.localAddress(paramFileDescriptor); 
/*     */   }
/*     */   
/*     */   public ServerSocket socket() {
/* 104 */     synchronized (this.stateLock) {
/* 105 */       if (this.socket == null)
/* 106 */         this.socket = ServerSocketAdaptor.create(this); 
/* 107 */       return this.socket;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public SocketAddress getLocalAddress() throws IOException {
/* 113 */     synchronized (this.stateLock) {
/* 114 */       if (!isOpen())
/* 115 */         throw new ClosedChannelException(); 
/* 116 */       return (this.localAddress == null) ? this.localAddress : 
/* 117 */         Net.getRevealedLocalAddress(
/* 118 */           Net.asInetSocketAddress(this.localAddress));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <T> ServerSocketChannel setOption(SocketOption<T> paramSocketOption, T paramT) throws IOException {
/* 126 */     if (paramSocketOption == null)
/* 127 */       throw new NullPointerException(); 
/* 128 */     if (!supportedOptions().contains(paramSocketOption))
/* 129 */       throw new UnsupportedOperationException("'" + paramSocketOption + "' not supported"); 
/* 130 */     synchronized (this.stateLock) {
/* 131 */       if (!isOpen()) {
/* 132 */         throw new ClosedChannelException();
/*     */       }
/* 134 */       if (paramSocketOption == StandardSocketOptions.IP_TOS) {
/* 135 */         StandardProtocolFamily standardProtocolFamily = Net.isIPv6Available() ? StandardProtocolFamily.INET6 : StandardProtocolFamily.INET;
/*     */         
/* 137 */         Net.setSocketOption(this.fd, standardProtocolFamily, paramSocketOption, paramT);
/* 138 */         return this;
/*     */       } 
/*     */       
/* 141 */       if (paramSocketOption == StandardSocketOptions.SO_REUSEADDR && 
/* 142 */         Net.useExclusiveBind()) {
/*     */ 
/*     */         
/* 145 */         this.isReuseAddress = ((Boolean)paramT).booleanValue();
/*     */       } else {
/*     */         
/* 148 */         Net.setSocketOption(this.fd, Net.UNSPEC, paramSocketOption, paramT);
/*     */       } 
/* 150 */       return this;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <T> T getOption(SocketOption<T> paramSocketOption) throws IOException {
/* 159 */     if (paramSocketOption == null)
/* 160 */       throw new NullPointerException(); 
/* 161 */     if (!supportedOptions().contains(paramSocketOption)) {
/* 162 */       throw new UnsupportedOperationException("'" + paramSocketOption + "' not supported");
/*     */     }
/* 164 */     synchronized (this.stateLock) {
/* 165 */       if (!isOpen())
/* 166 */         throw new ClosedChannelException(); 
/* 167 */       if (paramSocketOption == StandardSocketOptions.SO_REUSEADDR && 
/* 168 */         Net.useExclusiveBind())
/*     */       {
/*     */         
/* 171 */         return (T)Boolean.valueOf(this.isReuseAddress);
/*     */       }
/*     */       
/* 174 */       return (T)Net.getSocketOption(this.fd, Net.UNSPEC, paramSocketOption);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static class DefaultOptionsHolder {
/* 179 */     static final Set<SocketOption<?>> defaultOptions = defaultOptions();
/*     */     
/*     */     private static Set<SocketOption<?>> defaultOptions() {
/* 182 */       HashSet<SocketOption<Integer>> hashSet = new HashSet(2);
/* 183 */       hashSet.add(StandardSocketOptions.SO_RCVBUF);
/* 184 */       hashSet.add(StandardSocketOptions.SO_REUSEADDR);
/* 185 */       hashSet.add(StandardSocketOptions.IP_TOS);
/* 186 */       return Collections.unmodifiableSet(hashSet);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public final Set<SocketOption<?>> supportedOptions() {
/* 192 */     return DefaultOptionsHolder.defaultOptions;
/*     */   }
/*     */   
/*     */   public boolean isBound() {
/* 196 */     synchronized (this.stateLock) {
/* 197 */       return (this.localAddress != null);
/*     */     } 
/*     */   }
/*     */   
/*     */   public InetSocketAddress localAddress() {
/* 202 */     synchronized (this.stateLock) {
/* 203 */       return this.localAddress;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public ServerSocketChannel bind(SocketAddress paramSocketAddress, int paramInt) throws IOException {
/* 209 */     synchronized (this.lock) {
/* 210 */       if (!isOpen())
/* 211 */         throw new ClosedChannelException(); 
/* 212 */       if (isBound()) {
/* 213 */         throw new AlreadyBoundException();
/*     */       }
/* 215 */       InetSocketAddress inetSocketAddress = (paramSocketAddress == null) ? new InetSocketAddress(0) : Net.checkAddress(paramSocketAddress);
/* 216 */       SecurityManager securityManager = System.getSecurityManager();
/* 217 */       if (securityManager != null)
/* 218 */         securityManager.checkListen(inetSocketAddress.getPort()); 
/* 219 */       NetHooks.beforeTcpBind(this.fd, inetSocketAddress.getAddress(), inetSocketAddress.getPort());
/* 220 */       Net.bind(this.fd, inetSocketAddress.getAddress(), inetSocketAddress.getPort());
/* 221 */       Net.listen(this.fd, (paramInt < 1) ? 50 : paramInt);
/* 222 */       synchronized (this.stateLock) {
/* 223 */         this.localAddress = Net.localAddress(this.fd);
/*     */       } 
/*     */     } 
/* 226 */     return this;
/*     */   }
/*     */   
/*     */   public SocketChannel accept() throws IOException {
/* 230 */     synchronized (this.lock) {
/* 231 */       if (!isOpen())
/* 232 */         throw new ClosedChannelException(); 
/* 233 */       if (!isBound())
/* 234 */         throw new NotYetBoundException(); 
/* 235 */       SocketChannelImpl socketChannelImpl = null;
/*     */       
/* 237 */       int i = 0;
/* 238 */       FileDescriptor fileDescriptor = new FileDescriptor();
/* 239 */       InetSocketAddress[] arrayOfInetSocketAddress = new InetSocketAddress[1];
/*     */       
/*     */       try {
/* 242 */         begin();
/* 243 */         if (!isOpen())
/* 244 */           return null; 
/* 245 */         this.thread = NativeThread.current();
/*     */         while (true) {
/* 247 */           i = accept(this.fd, fileDescriptor, arrayOfInetSocketAddress);
/* 248 */           if (i == -3 && isOpen())
/*     */             continue; 
/*     */           break;
/*     */         } 
/*     */       } finally {
/* 253 */         this.thread = 0L;
/* 254 */         end((i > 0));
/* 255 */         assert IOStatus.check(i);
/*     */       } 
/*     */       
/* 258 */       if (i < 1) {
/* 259 */         return null;
/*     */       }
/* 261 */       IOUtil.configureBlocking(fileDescriptor, true);
/* 262 */       InetSocketAddress inetSocketAddress = arrayOfInetSocketAddress[0];
/* 263 */       socketChannelImpl = new SocketChannelImpl(provider(), fileDescriptor, inetSocketAddress);
/* 264 */       SecurityManager securityManager = System.getSecurityManager();
/* 265 */       if (securityManager != null) {
/*     */         try {
/* 267 */           securityManager.checkAccept(inetSocketAddress.getAddress().getHostAddress(), inetSocketAddress
/* 268 */               .getPort());
/* 269 */         } catch (SecurityException securityException) {
/* 270 */           socketChannelImpl.close();
/* 271 */           throw securityException;
/*     */         } 
/*     */       }
/* 274 */       return socketChannelImpl;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void implConfigureBlocking(boolean paramBoolean) throws IOException {
/* 280 */     IOUtil.configureBlocking(this.fd, paramBoolean);
/*     */   }
/*     */   
/*     */   protected void implCloseSelectableChannel() throws IOException {
/* 284 */     synchronized (this.stateLock) {
/* 285 */       if (this.state != 1)
/* 286 */         nd.preClose(this.fd); 
/* 287 */       long l = this.thread;
/* 288 */       if (l != 0L)
/* 289 */         NativeThread.signal(l); 
/* 290 */       if (!isRegistered())
/* 291 */         kill(); 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void kill() throws IOException {
/* 296 */     synchronized (this.stateLock) {
/* 297 */       if (this.state == 1)
/*     */         return; 
/* 299 */       if (this.state == -1) {
/* 300 */         this.state = 1;
/*     */         return;
/*     */       } 
/* 303 */       assert !isOpen() && !isRegistered();
/* 304 */       nd.close(this.fd);
/* 305 */       this.state = 1;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean translateReadyOps(int paramInt1, int paramInt2, SelectionKeyImpl paramSelectionKeyImpl) {
/* 314 */     int i = paramSelectionKeyImpl.nioInterestOps();
/* 315 */     int j = paramSelectionKeyImpl.nioReadyOps();
/* 316 */     int k = paramInt2;
/*     */     
/* 318 */     if ((paramInt1 & Net.POLLNVAL) != 0)
/*     */     {
/*     */ 
/*     */       
/* 322 */       return false;
/*     */     }
/*     */     
/* 325 */     if ((paramInt1 & (Net.POLLERR | Net.POLLHUP)) != 0) {
/* 326 */       k = i;
/* 327 */       paramSelectionKeyImpl.nioReadyOps(k);
/* 328 */       return ((k & (j ^ 0xFFFFFFFF)) != 0);
/*     */     } 
/*     */     
/* 331 */     if ((paramInt1 & Net.POLLIN) != 0 && (i & 0x10) != 0)
/*     */     {
/* 333 */       k |= 0x10;
/*     */     }
/* 335 */     paramSelectionKeyImpl.nioReadyOps(k);
/* 336 */     return ((k & (j ^ 0xFFFFFFFF)) != 0);
/*     */   }
/*     */   
/*     */   public boolean translateAndUpdateReadyOps(int paramInt, SelectionKeyImpl paramSelectionKeyImpl) {
/* 340 */     return translateReadyOps(paramInt, paramSelectionKeyImpl.nioReadyOps(), paramSelectionKeyImpl);
/*     */   }
/*     */   
/*     */   public boolean translateAndSetReadyOps(int paramInt, SelectionKeyImpl paramSelectionKeyImpl) {
/* 344 */     return translateReadyOps(paramInt, 0, paramSelectionKeyImpl);
/*     */   }
/*     */ 
/*     */   
/*     */   int poll(int paramInt, long paramLong) throws IOException {
/* 349 */     assert Thread.holdsLock(blockingLock()) && !isBlocking();
/*     */     
/* 351 */     synchronized (this.lock) {
/* 352 */       int i = 0;
/*     */       try {
/* 354 */         begin();
/* 355 */         synchronized (this.stateLock) {
/* 356 */           if (!isOpen())
/* 357 */             return 0; 
/* 358 */           this.thread = NativeThread.current();
/*     */         } 
/* 360 */         i = Net.poll(this.fd, paramInt, paramLong);
/*     */       } finally {
/* 362 */         this.thread = 0L;
/* 363 */         end((i > 0));
/*     */       } 
/* 365 */       return i;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void translateAndSetInterestOps(int paramInt, SelectionKeyImpl paramSelectionKeyImpl) {
/* 373 */     int i = 0;
/*     */ 
/*     */     
/* 376 */     if ((paramInt & 0x10) != 0) {
/* 377 */       i |= Net.POLLIN;
/*     */     }
/* 379 */     paramSelectionKeyImpl.selector.putEventOps(paramSelectionKeyImpl, i);
/*     */   }
/*     */   
/*     */   public FileDescriptor getFD() {
/* 383 */     return this.fd;
/*     */   }
/*     */   
/*     */   public int getFDVal() {
/* 387 */     return this.fdVal;
/*     */   }
/*     */   
/*     */   public String toString() {
/* 391 */     StringBuffer stringBuffer = new StringBuffer();
/* 392 */     stringBuffer.append(getClass().getName());
/* 393 */     stringBuffer.append('[');
/* 394 */     if (!isOpen()) {
/* 395 */       stringBuffer.append("closed");
/*     */     } else {
/* 397 */       synchronized (this.stateLock) {
/* 398 */         InetSocketAddress inetSocketAddress = localAddress();
/* 399 */         if (inetSocketAddress == null) {
/* 400 */           stringBuffer.append("unbound");
/*     */         } else {
/* 402 */           stringBuffer.append(Net.getRevealedLocalAddressAsString(inetSocketAddress));
/*     */         } 
/*     */       } 
/*     */     } 
/* 406 */     stringBuffer.append(']');
/* 407 */     return stringBuffer.toString();
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
/*     */   private int accept(FileDescriptor paramFileDescriptor1, FileDescriptor paramFileDescriptor2, InetSocketAddress[] paramArrayOfInetSocketAddress) throws IOException {
/* 419 */     return accept0(paramFileDescriptor1, paramFileDescriptor2, paramArrayOfInetSocketAddress);
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
/*     */   static {
/* 436 */     IOUtil.load();
/* 437 */     initIDs();
/* 438 */   } private static NativeDispatcher nd = new SocketDispatcher();
/*     */   private final FileDescriptor fd;
/*     */   private final int fdVal;
/*     */   private static final int ST_UNINITIALIZED = -1;
/*     */   private static final int ST_INUSE = 0;
/*     */   private static final int ST_KILLED = 1;
/*     */   private InetSocketAddress localAddress;
/*     */   private boolean isReuseAddress;
/*     */   ServerSocket socket;
/*     */   
/*     */   private native int accept0(FileDescriptor paramFileDescriptor1, FileDescriptor paramFileDescriptor2, InetSocketAddress[] paramArrayOfInetSocketAddress) throws IOException;
/*     */   
/*     */   private static native void initIDs();
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/nio/ch/ServerSocketChannelImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */