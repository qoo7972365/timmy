/*      */ package sun.nio.ch;
/*      */ 
/*      */ import java.io.FileDescriptor;
/*      */ import java.io.IOException;
/*      */ import java.net.InetAddress;
/*      */ import java.net.InetSocketAddress;
/*      */ import java.net.Socket;
/*      */ import java.net.SocketAddress;
/*      */ import java.net.SocketOption;
/*      */ import java.net.StandardProtocolFamily;
/*      */ import java.net.StandardSocketOptions;
/*      */ import java.nio.ByteBuffer;
/*      */ import java.nio.channels.AlreadyBoundException;
/*      */ import java.nio.channels.AlreadyConnectedException;
/*      */ import java.nio.channels.AsynchronousCloseException;
/*      */ import java.nio.channels.ClosedChannelException;
/*      */ import java.nio.channels.ConnectionPendingException;
/*      */ import java.nio.channels.NetworkChannel;
/*      */ import java.nio.channels.NoConnectionPendingException;
/*      */ import java.nio.channels.NotYetConnectedException;
/*      */ import java.nio.channels.SocketChannel;
/*      */ import java.nio.channels.spi.SelectorProvider;
/*      */ import java.util.Collections;
/*      */ import java.util.HashSet;
/*      */ import java.util.Set;
/*      */ import jdk.net.ExtendedSocketOptions;
/*      */ import sun.net.ExtendedOptionsImpl;
/*      */ import sun.net.NetHooks;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ class SocketChannelImpl
/*      */   extends SocketChannel
/*      */   implements SelChImpl
/*      */ {
/*   56 */   private volatile long readerThread = 0L;
/*   57 */   private volatile long writerThread = 0L;
/*      */ 
/*      */   
/*   60 */   private final Object readLock = new Object();
/*      */ 
/*      */   
/*   63 */   private final Object writeLock = new Object();
/*      */ 
/*      */ 
/*      */   
/*   67 */   private final Object stateLock = new Object();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   81 */   private int state = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean isInputOpen = true;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean isOutputOpen = true;
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean readyToConnect = false;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   SocketChannelImpl(SelectorProvider paramSelectorProvider) throws IOException {
/*  101 */     super(paramSelectorProvider);
/*  102 */     this.fd = Net.socket(true);
/*  103 */     this.fdVal = IOUtil.fdVal(this.fd);
/*  104 */     this.state = 0;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   SocketChannelImpl(SelectorProvider paramSelectorProvider, FileDescriptor paramFileDescriptor, boolean paramBoolean) throws IOException {
/*  112 */     super(paramSelectorProvider);
/*  113 */     this.fd = paramFileDescriptor;
/*  114 */     this.fdVal = IOUtil.fdVal(paramFileDescriptor);
/*  115 */     this.state = 0;
/*  116 */     if (paramBoolean) {
/*  117 */       this.localAddress = Net.localAddress(paramFileDescriptor);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   SocketChannelImpl(SelectorProvider paramSelectorProvider, FileDescriptor paramFileDescriptor, InetSocketAddress paramInetSocketAddress) throws IOException {
/*  126 */     super(paramSelectorProvider);
/*  127 */     this.fd = paramFileDescriptor;
/*  128 */     this.fdVal = IOUtil.fdVal(paramFileDescriptor);
/*  129 */     this.state = 2;
/*  130 */     this.localAddress = Net.localAddress(paramFileDescriptor);
/*  131 */     this.remoteAddress = paramInetSocketAddress;
/*      */   }
/*      */   
/*      */   public Socket socket() {
/*  135 */     synchronized (this.stateLock) {
/*  136 */       if (this.socket == null)
/*  137 */         this.socket = SocketAdaptor.create(this); 
/*  138 */       return this.socket;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public SocketAddress getLocalAddress() throws IOException {
/*  144 */     synchronized (this.stateLock) {
/*  145 */       if (!isOpen())
/*  146 */         throw new ClosedChannelException(); 
/*  147 */       return Net.getRevealedLocalAddress(this.localAddress);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public SocketAddress getRemoteAddress() throws IOException {
/*  153 */     synchronized (this.stateLock) {
/*  154 */       if (!isOpen())
/*  155 */         throw new ClosedChannelException(); 
/*  156 */       return this.remoteAddress;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public <T> SocketChannel setOption(SocketOption<T> paramSocketOption, T paramT) throws IOException {
/*  164 */     if (paramSocketOption == null)
/*  165 */       throw new NullPointerException(); 
/*  166 */     if (!supportedOptions().contains(paramSocketOption)) {
/*  167 */       throw new UnsupportedOperationException("'" + paramSocketOption + "' not supported");
/*      */     }
/*  169 */     synchronized (this.stateLock) {
/*  170 */       if (!isOpen()) {
/*  171 */         throw new ClosedChannelException();
/*      */       }
/*  173 */       if (paramSocketOption == StandardSocketOptions.IP_TOS) {
/*  174 */         StandardProtocolFamily standardProtocolFamily = Net.isIPv6Available() ? StandardProtocolFamily.INET6 : StandardProtocolFamily.INET;
/*      */         
/*  176 */         Net.setSocketOption(this.fd, standardProtocolFamily, paramSocketOption, paramT);
/*  177 */         return this;
/*      */       } 
/*      */       
/*  180 */       if (paramSocketOption == StandardSocketOptions.SO_REUSEADDR && Net.useExclusiveBind()) {
/*      */         
/*  182 */         this.isReuseAddress = ((Boolean)paramT).booleanValue();
/*  183 */         return this;
/*      */       } 
/*      */ 
/*      */       
/*  187 */       Net.setSocketOption(this.fd, Net.UNSPEC, paramSocketOption, paramT);
/*  188 */       return this;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public <T> T getOption(SocketOption<T> paramSocketOption) throws IOException {
/*  197 */     if (paramSocketOption == null)
/*  198 */       throw new NullPointerException(); 
/*  199 */     if (!supportedOptions().contains(paramSocketOption)) {
/*  200 */       throw new UnsupportedOperationException("'" + paramSocketOption + "' not supported");
/*      */     }
/*  202 */     synchronized (this.stateLock) {
/*  203 */       if (!isOpen()) {
/*  204 */         throw new ClosedChannelException();
/*      */       }
/*  206 */       if (paramSocketOption == StandardSocketOptions.SO_REUSEADDR && 
/*  207 */         Net.useExclusiveBind())
/*      */       {
/*      */         
/*  210 */         return (T)Boolean.valueOf(this.isReuseAddress);
/*      */       }
/*      */ 
/*      */       
/*  214 */       if (paramSocketOption == StandardSocketOptions.IP_TOS) {
/*  215 */         StandardProtocolFamily standardProtocolFamily = Net.isIPv6Available() ? StandardProtocolFamily.INET6 : StandardProtocolFamily.INET;
/*      */         
/*  217 */         return (T)Net.getSocketOption(this.fd, standardProtocolFamily, paramSocketOption);
/*      */       } 
/*      */ 
/*      */       
/*  221 */       return (T)Net.getSocketOption(this.fd, Net.UNSPEC, paramSocketOption);
/*      */     } 
/*      */   }
/*      */   
/*      */   private static class DefaultOptionsHolder {
/*  226 */     static final Set<SocketOption<?>> defaultOptions = defaultOptions();
/*      */     
/*      */     private static Set<SocketOption<?>> defaultOptions() {
/*  229 */       HashSet<SocketOption<Integer>> hashSet = new HashSet(8);
/*  230 */       hashSet.add(StandardSocketOptions.SO_SNDBUF);
/*  231 */       hashSet.add(StandardSocketOptions.SO_RCVBUF);
/*  232 */       hashSet.add(StandardSocketOptions.SO_KEEPALIVE);
/*  233 */       hashSet.add(StandardSocketOptions.SO_REUSEADDR);
/*  234 */       hashSet.add(StandardSocketOptions.SO_LINGER);
/*  235 */       hashSet.add(StandardSocketOptions.TCP_NODELAY);
/*      */       
/*  237 */       hashSet.add(StandardSocketOptions.IP_TOS);
/*  238 */       hashSet.add(ExtendedSocketOption.SO_OOBINLINE);
/*  239 */       if (ExtendedOptionsImpl.flowSupported()) {
/*  240 */         hashSet.add(ExtendedSocketOptions.SO_FLOW_SLA);
/*      */       }
/*  242 */       return Collections.unmodifiableSet(hashSet);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public final Set<SocketOption<?>> supportedOptions() {
/*  248 */     return DefaultOptionsHolder.defaultOptions;
/*      */   }
/*      */   
/*      */   private boolean ensureReadOpen() throws ClosedChannelException {
/*  252 */     synchronized (this.stateLock) {
/*  253 */       if (!isOpen())
/*  254 */         throw new ClosedChannelException(); 
/*  255 */       if (!isConnected())
/*  256 */         throw new NotYetConnectedException(); 
/*  257 */       if (!this.isInputOpen) {
/*  258 */         return false;
/*      */       }
/*  260 */       return true;
/*      */     } 
/*      */   }
/*      */   
/*      */   private void ensureWriteOpen() throws ClosedChannelException {
/*  265 */     synchronized (this.stateLock) {
/*  266 */       if (!isOpen())
/*  267 */         throw new ClosedChannelException(); 
/*  268 */       if (!this.isOutputOpen)
/*  269 */         throw new ClosedChannelException(); 
/*  270 */       if (!isConnected())
/*  271 */         throw new NotYetConnectedException(); 
/*      */     } 
/*      */   }
/*      */   
/*      */   private void readerCleanup() throws IOException {
/*  276 */     synchronized (this.stateLock) {
/*  277 */       this.readerThread = 0L;
/*  278 */       if (this.state == 3)
/*  279 */         kill(); 
/*      */     } 
/*      */   }
/*      */   
/*      */   private void writerCleanup() throws IOException {
/*  284 */     synchronized (this.stateLock) {
/*  285 */       this.writerThread = 0L;
/*  286 */       if (this.state == 3) {
/*  287 */         kill();
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public int read(ByteBuffer paramByteBuffer) throws IOException {
/*  293 */     if (paramByteBuffer == null) {
/*  294 */       throw new NullPointerException();
/*      */     }
/*  296 */     synchronized (this.readLock) {
/*  297 */       if (!ensureReadOpen())
/*  298 */         return -1; 
/*  299 */       int i = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       try {
/*  305 */         begin();
/*      */         
/*  307 */         synchronized (this.stateLock) {
/*  308 */           if (!isOpen())
/*      */           {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  316 */             return 0;
/*      */           }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  323 */           this.readerThread = NativeThread.current();
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         while (true) {
/*  377 */           i = IOUtil.read(this.fd, paramByteBuffer, -1L, nd);
/*  378 */           if (i == -3 && isOpen()) {
/*      */             continue;
/*      */           }
/*      */           break;
/*      */         } 
/*  383 */         return IOStatus.normalize(i);
/*      */       }
/*      */       finally {
/*      */         
/*  387 */         readerCleanup();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  404 */         end((i > 0 || i == -2));
/*      */ 
/*      */ 
/*      */         
/*  408 */         synchronized (this.stateLock) {
/*  409 */           if (i <= 0 && !this.isInputOpen) {
/*  410 */             return -1;
/*      */           }
/*      */         } 
/*  413 */         assert IOStatus.check(i);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public long read(ByteBuffer[] paramArrayOfByteBuffer, int paramInt1, int paramInt2) throws IOException {
/*  422 */     if (paramInt1 < 0 || paramInt2 < 0 || paramInt1 > paramArrayOfByteBuffer.length - paramInt2)
/*  423 */       throw new IndexOutOfBoundsException(); 
/*  424 */     synchronized (this.readLock) {
/*  425 */       if (!ensureReadOpen())
/*  426 */         return -1L; 
/*  427 */       long l = 0L;
/*      */       try {
/*  429 */         begin();
/*  430 */         synchronized (this.stateLock) {
/*  431 */           if (!isOpen())
/*  432 */             return 0L; 
/*  433 */           this.readerThread = NativeThread.current();
/*      */         } 
/*      */         
/*      */         while (true) {
/*  437 */           l = IOUtil.read(this.fd, paramArrayOfByteBuffer, paramInt1, paramInt2, nd);
/*  438 */           if (l == -3L && isOpen())
/*      */             continue;  break;
/*  440 */         }  return IOStatus.normalize(l);
/*      */       } finally {
/*      */         
/*  443 */         readerCleanup();
/*  444 */         end((l > 0L || l == -2L));
/*  445 */         synchronized (this.stateLock) {
/*  446 */           if (l <= 0L && !this.isInputOpen)
/*  447 */             return -1L; 
/*      */         } 
/*  449 */         assert IOStatus.check(l);
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   public int write(ByteBuffer paramByteBuffer) throws IOException {
/*  455 */     if (paramByteBuffer == null)
/*  456 */       throw new NullPointerException(); 
/*  457 */     synchronized (this.writeLock) {
/*  458 */       ensureWriteOpen();
/*  459 */       int i = 0;
/*      */       try {
/*  461 */         begin();
/*  462 */         synchronized (this.stateLock) {
/*  463 */           if (!isOpen())
/*  464 */             return 0; 
/*  465 */           this.writerThread = NativeThread.current();
/*      */         } 
/*      */         while (true) {
/*  468 */           i = IOUtil.write(this.fd, paramByteBuffer, -1L, nd);
/*  469 */           if (i == -3 && isOpen())
/*      */             continue;  break;
/*  471 */         }  return IOStatus.normalize(i);
/*      */       } finally {
/*      */         
/*  474 */         writerCleanup();
/*  475 */         end((i > 0 || i == -2));
/*  476 */         synchronized (this.stateLock) {
/*  477 */           if (i <= 0 && !this.isOutputOpen)
/*  478 */             throw new AsynchronousCloseException(); 
/*      */         } 
/*  480 */         assert IOStatus.check(i);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public long write(ByteBuffer[] paramArrayOfByteBuffer, int paramInt1, int paramInt2) throws IOException {
/*  488 */     if (paramInt1 < 0 || paramInt2 < 0 || paramInt1 > paramArrayOfByteBuffer.length - paramInt2)
/*  489 */       throw new IndexOutOfBoundsException(); 
/*  490 */     synchronized (this.writeLock) {
/*  491 */       ensureWriteOpen();
/*  492 */       long l = 0L;
/*      */       try {
/*  494 */         begin();
/*  495 */         synchronized (this.stateLock) {
/*  496 */           if (!isOpen())
/*  497 */             return 0L; 
/*  498 */           this.writerThread = NativeThread.current();
/*      */         } 
/*      */         while (true) {
/*  501 */           l = IOUtil.write(this.fd, paramArrayOfByteBuffer, paramInt1, paramInt2, nd);
/*  502 */           if (l == -3L && isOpen())
/*      */             continue;  break;
/*  504 */         }  return IOStatus.normalize(l);
/*      */       } finally {
/*      */         
/*  507 */         writerCleanup();
/*  508 */         end((l > 0L || l == -2L));
/*  509 */         synchronized (this.stateLock) {
/*  510 */           if (l <= 0L && !this.isOutputOpen)
/*  511 */             throw new AsynchronousCloseException(); 
/*      */         } 
/*  513 */         assert IOStatus.check(l);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   int sendOutOfBandData(byte paramByte) throws IOException {
/*  520 */     synchronized (this.writeLock) {
/*  521 */       ensureWriteOpen();
/*  522 */       int i = 0;
/*      */       try {
/*  524 */         begin();
/*  525 */         synchronized (this.stateLock) {
/*  526 */           if (!isOpen())
/*  527 */             return 0; 
/*  528 */           this.writerThread = NativeThread.current();
/*      */         } 
/*      */         while (true) {
/*  531 */           i = sendOutOfBandData(this.fd, paramByte);
/*  532 */           if (i == -3 && isOpen())
/*      */             continue;  break;
/*  534 */         }  return IOStatus.normalize(i);
/*      */       } finally {
/*      */         
/*  537 */         writerCleanup();
/*  538 */         end((i > 0 || i == -2));
/*  539 */         synchronized (this.stateLock) {
/*  540 */           if (i <= 0 && !this.isOutputOpen)
/*  541 */             throw new AsynchronousCloseException(); 
/*      */         } 
/*  543 */         assert IOStatus.check(i);
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   protected void implConfigureBlocking(boolean paramBoolean) throws IOException {
/*  549 */     IOUtil.configureBlocking(this.fd, paramBoolean);
/*      */   }
/*      */   
/*      */   public InetSocketAddress localAddress() {
/*  553 */     synchronized (this.stateLock) {
/*  554 */       return this.localAddress;
/*      */     } 
/*      */   }
/*      */   
/*      */   public SocketAddress remoteAddress() {
/*  559 */     synchronized (this.stateLock) {
/*  560 */       return this.remoteAddress;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public SocketChannel bind(SocketAddress paramSocketAddress) throws IOException {
/*  566 */     synchronized (this.readLock) {
/*  567 */       synchronized (this.writeLock) {
/*  568 */         synchronized (this.stateLock) {
/*  569 */           if (!isOpen())
/*  570 */             throw new ClosedChannelException(); 
/*  571 */           if (this.state == 1)
/*  572 */             throw new ConnectionPendingException(); 
/*  573 */           if (this.localAddress != null) {
/*  574 */             throw new AlreadyBoundException();
/*      */           }
/*  576 */           InetSocketAddress inetSocketAddress = (paramSocketAddress == null) ? new InetSocketAddress(0) : Net.checkAddress(paramSocketAddress);
/*  577 */           SecurityManager securityManager = System.getSecurityManager();
/*  578 */           if (securityManager != null) {
/*  579 */             securityManager.checkListen(inetSocketAddress.getPort());
/*      */           }
/*  581 */           NetHooks.beforeTcpBind(this.fd, inetSocketAddress.getAddress(), inetSocketAddress.getPort());
/*  582 */           Net.bind(this.fd, inetSocketAddress.getAddress(), inetSocketAddress.getPort());
/*  583 */           this.localAddress = Net.localAddress(this.fd);
/*      */         } 
/*      */       } 
/*      */     } 
/*  587 */     return this;
/*      */   }
/*      */   
/*      */   public boolean isConnected() {
/*  591 */     synchronized (this.stateLock) {
/*  592 */       return (this.state == 2);
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean isConnectionPending() {
/*  597 */     synchronized (this.stateLock) {
/*  598 */       return (this.state == 1);
/*      */     } 
/*      */   }
/*      */   
/*      */   void ensureOpenAndUnconnected() throws IOException {
/*  603 */     synchronized (this.stateLock) {
/*  604 */       if (!isOpen())
/*  605 */         throw new ClosedChannelException(); 
/*  606 */       if (this.state == 2)
/*  607 */         throw new AlreadyConnectedException(); 
/*  608 */       if (this.state == 1)
/*  609 */         throw new ConnectionPendingException(); 
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean connect(SocketAddress paramSocketAddress) throws IOException {
/*  614 */     boolean bool = false;
/*      */     
/*  616 */     synchronized (this.readLock) {
/*  617 */       synchronized (this.writeLock) {
/*  618 */         ensureOpenAndUnconnected();
/*  619 */         InetSocketAddress inetSocketAddress = Net.checkAddress(paramSocketAddress);
/*  620 */         SecurityManager securityManager = System.getSecurityManager();
/*  621 */         if (securityManager != null)
/*  622 */           securityManager.checkConnect(inetSocketAddress.getAddress().getHostAddress(), inetSocketAddress
/*  623 */               .getPort()); 
/*  624 */         synchronized (blockingLock()) {
/*  625 */           int i = 0;
/*      */           try {
/*      */             try {
/*  628 */               begin();
/*  629 */               synchronized (this.stateLock) {
/*  630 */                 if (!isOpen()) {
/*  631 */                   return false;
/*      */                 }
/*      */                 
/*  634 */                 if (this.localAddress == null) {
/*  635 */                   NetHooks.beforeTcpConnect(this.fd, inetSocketAddress
/*  636 */                       .getAddress(), inetSocketAddress
/*  637 */                       .getPort());
/*      */                 }
/*  639 */                 this.readerThread = NativeThread.current();
/*      */               } 
/*      */               while (true) {
/*  642 */                 InetAddress inetAddress = inetSocketAddress.getAddress();
/*  643 */                 if (inetAddress.isAnyLocalAddress())
/*  644 */                   inetAddress = InetAddress.getLocalHost(); 
/*  645 */                 i = Net.connect(this.fd, inetAddress, inetSocketAddress
/*      */                     
/*  647 */                     .getPort());
/*  648 */                 if (i == -3 && 
/*  649 */                   isOpen()) {
/*      */                   continue;
/*      */                 }
/*      */                 break;
/*      */               } 
/*      */             } finally {
/*  655 */               readerCleanup();
/*  656 */               end((i > 0 || i == -2));
/*  657 */               assert IOStatus.check(i);
/*      */             } 
/*  659 */           } catch (IOException iOException) {
/*      */ 
/*      */ 
/*      */             
/*  663 */             close();
/*  664 */             throw iOException;
/*      */           } 
/*  666 */           synchronized (this.stateLock) {
/*  667 */             this.remoteAddress = inetSocketAddress;
/*  668 */             if (i > 0) {
/*      */ 
/*      */ 
/*      */               
/*  672 */               this.state = 2;
/*  673 */               if (isOpen())
/*  674 */                 this.localAddress = Net.localAddress(this.fd); 
/*  675 */               return true;
/*      */             } 
/*      */ 
/*      */             
/*  679 */             if (!isBlocking()) {
/*  680 */               this.state = 1;
/*      */             } else {
/*      */               assert false;
/*      */             } 
/*      */           } 
/*  685 */         }  return false;
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean finishConnect() throws IOException {
/*  691 */     synchronized (this.readLock) {
/*  692 */       synchronized (this.writeLock) {
/*  693 */         synchronized (this.stateLock) {
/*  694 */           if (!isOpen())
/*  695 */             throw new ClosedChannelException(); 
/*  696 */           if (this.state == 2)
/*  697 */             return true; 
/*  698 */           if (this.state != 1)
/*  699 */             throw new NoConnectionPendingException(); 
/*      */         } 
/*  701 */         int i = 0;
/*      */         try {
/*      */           try {
/*  704 */             begin();
/*  705 */             synchronized (blockingLock()) {
/*  706 */               synchronized (this.stateLock) {
/*  707 */                 if (!isOpen()) {
/*  708 */                   return false;
/*      */                 }
/*  710 */                 this.readerThread = NativeThread.current();
/*      */               } 
/*  712 */               if (!isBlocking()) {
/*      */                 while (true) {
/*  714 */                   i = checkConnect(this.fd, false, this.readyToConnect);
/*      */                   
/*  716 */                   if (i == -3 && 
/*  717 */                     isOpen())
/*      */                     continue; 
/*      */                   break;
/*      */                 } 
/*      */               } else {
/*      */                 while (true) {
/*  723 */                   i = checkConnect(this.fd, true, this.readyToConnect);
/*      */                   
/*  725 */                   if (i == 0) {
/*      */                     continue;
/*      */                   }
/*      */ 
/*      */                   
/*  730 */                   if (i == -3 && 
/*  731 */                     isOpen())
/*      */                     continue; 
/*      */                   break;
/*      */                 } 
/*      */               } 
/*      */             } 
/*      */           } finally {
/*  738 */             synchronized (this.stateLock) {
/*  739 */               this.readerThread = 0L;
/*  740 */               if (this.state == 3) {
/*  741 */                 kill();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                 
/*  747 */                 i = 0;
/*      */               } 
/*      */             } 
/*  750 */             end((i > 0 || i == -2));
/*  751 */             assert IOStatus.check(i);
/*      */           } 
/*  753 */         } catch (IOException iOException) {
/*      */ 
/*      */ 
/*      */           
/*  757 */           close();
/*  758 */           throw iOException;
/*      */         } 
/*  760 */         if (i > 0) {
/*  761 */           synchronized (this.stateLock) {
/*  762 */             this.state = 2;
/*  763 */             if (isOpen())
/*  764 */               this.localAddress = Net.localAddress(this.fd); 
/*      */           } 
/*  766 */           return true;
/*      */         } 
/*  768 */         return false;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public SocketChannel shutdownInput() throws IOException {
/*  775 */     synchronized (this.stateLock) {
/*  776 */       if (!isOpen())
/*  777 */         throw new ClosedChannelException(); 
/*  778 */       if (!isConnected())
/*  779 */         throw new NotYetConnectedException(); 
/*  780 */       if (this.isInputOpen) {
/*  781 */         Net.shutdown(this.fd, 0);
/*  782 */         if (this.readerThread != 0L)
/*  783 */           NativeThread.signal(this.readerThread); 
/*  784 */         this.isInputOpen = false;
/*      */       } 
/*  786 */       return this;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public SocketChannel shutdownOutput() throws IOException {
/*  792 */     synchronized (this.stateLock) {
/*  793 */       if (!isOpen())
/*  794 */         throw new ClosedChannelException(); 
/*  795 */       if (!isConnected())
/*  796 */         throw new NotYetConnectedException(); 
/*  797 */       if (this.isOutputOpen) {
/*  798 */         Net.shutdown(this.fd, 1);
/*  799 */         if (this.writerThread != 0L)
/*  800 */           NativeThread.signal(this.writerThread); 
/*  801 */         this.isOutputOpen = false;
/*      */       } 
/*  803 */       return this;
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean isInputOpen() {
/*  808 */     synchronized (this.stateLock) {
/*  809 */       return this.isInputOpen;
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean isOutputOpen() {
/*  814 */     synchronized (this.stateLock) {
/*  815 */       return this.isOutputOpen;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void implCloseSelectableChannel() throws IOException {
/*  825 */     synchronized (this.stateLock) {
/*  826 */       this.isInputOpen = false;
/*  827 */       this.isOutputOpen = false;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  834 */       if (this.state != 4) {
/*  835 */         nd.preClose(this.fd);
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  841 */       if (this.readerThread != 0L) {
/*  842 */         NativeThread.signal(this.readerThread);
/*      */       }
/*  844 */       if (this.writerThread != 0L) {
/*  845 */         NativeThread.signal(this.writerThread);
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  857 */       if (!isRegistered())
/*  858 */         kill(); 
/*      */     } 
/*      */   }
/*      */   
/*      */   public void kill() throws IOException {
/*  863 */     synchronized (this.stateLock) {
/*  864 */       if (this.state == 4)
/*      */         return; 
/*  866 */       if (this.state == -1) {
/*  867 */         this.state = 4;
/*      */         return;
/*      */       } 
/*  870 */       assert !isOpen() && !isRegistered();
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  875 */       if (this.readerThread == 0L && this.writerThread == 0L) {
/*  876 */         nd.close(this.fd);
/*  877 */         this.state = 4;
/*      */       } else {
/*  879 */         this.state = 3;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean translateReadyOps(int paramInt1, int paramInt2, SelectionKeyImpl paramSelectionKeyImpl) {
/*  889 */     int i = paramSelectionKeyImpl.nioInterestOps();
/*  890 */     int j = paramSelectionKeyImpl.nioReadyOps();
/*  891 */     int k = paramInt2;
/*      */     
/*  893 */     if ((paramInt1 & Net.POLLNVAL) != 0)
/*      */     {
/*      */ 
/*      */       
/*  897 */       return false;
/*      */     }
/*      */     
/*  900 */     if ((paramInt1 & (Net.POLLERR | Net.POLLHUP)) != 0) {
/*  901 */       k = i;
/*  902 */       paramSelectionKeyImpl.nioReadyOps(k);
/*      */ 
/*      */       
/*  905 */       this.readyToConnect = true;
/*  906 */       return ((k & (j ^ 0xFFFFFFFF)) != 0);
/*      */     } 
/*      */     
/*  909 */     if ((paramInt1 & Net.POLLIN) != 0 && (i & 0x1) != 0 && this.state == 2)
/*      */     {
/*      */       
/*  912 */       k |= 0x1;
/*      */     }
/*  914 */     if ((paramInt1 & Net.POLLCONN) != 0 && (i & 0x8) != 0 && (this.state == 0 || this.state == 1)) {
/*      */ 
/*      */       
/*  917 */       k |= 0x8;
/*  918 */       this.readyToConnect = true;
/*      */     } 
/*      */     
/*  921 */     if ((paramInt1 & Net.POLLOUT) != 0 && (i & 0x4) != 0 && this.state == 2)
/*      */     {
/*      */       
/*  924 */       k |= 0x4;
/*      */     }
/*  926 */     paramSelectionKeyImpl.nioReadyOps(k);
/*  927 */     return ((k & (j ^ 0xFFFFFFFF)) != 0);
/*      */   }
/*      */   
/*      */   public boolean translateAndUpdateReadyOps(int paramInt, SelectionKeyImpl paramSelectionKeyImpl) {
/*  931 */     return translateReadyOps(paramInt, paramSelectionKeyImpl.nioReadyOps(), paramSelectionKeyImpl);
/*      */   }
/*      */   
/*      */   public boolean translateAndSetReadyOps(int paramInt, SelectionKeyImpl paramSelectionKeyImpl) {
/*  935 */     return translateReadyOps(paramInt, 0, paramSelectionKeyImpl);
/*      */   }
/*      */ 
/*      */   
/*      */   int poll(int paramInt, long paramLong) throws IOException {
/*  940 */     assert Thread.holdsLock(blockingLock()) && !isBlocking();
/*      */     
/*  942 */     synchronized (this.readLock) {
/*  943 */       int i = 0;
/*      */       try {
/*  945 */         begin();
/*  946 */         synchronized (this.stateLock) {
/*  947 */           if (!isOpen())
/*  948 */             return 0; 
/*  949 */           this.readerThread = NativeThread.current();
/*      */         } 
/*  951 */         i = Net.poll(this.fd, paramInt, paramLong);
/*      */       } finally {
/*  953 */         readerCleanup();
/*  954 */         end((i > 0));
/*      */       } 
/*  956 */       return i;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void translateAndSetInterestOps(int paramInt, SelectionKeyImpl paramSelectionKeyImpl) {
/*  964 */     int i = 0;
/*  965 */     if ((paramInt & 0x1) != 0)
/*  966 */       i |= Net.POLLIN; 
/*  967 */     if ((paramInt & 0x4) != 0)
/*  968 */       i |= Net.POLLOUT; 
/*  969 */     if ((paramInt & 0x8) != 0)
/*  970 */       i |= Net.POLLCONN; 
/*  971 */     paramSelectionKeyImpl.selector.putEventOps(paramSelectionKeyImpl, i);
/*      */   }
/*      */   
/*      */   public FileDescriptor getFD() {
/*  975 */     return this.fd;
/*      */   }
/*      */   
/*      */   public int getFDVal() {
/*  979 */     return this.fdVal;
/*      */   }
/*      */ 
/*      */   
/*      */   public String toString() {
/*  984 */     StringBuffer stringBuffer = new StringBuffer();
/*  985 */     stringBuffer.append(getClass().getSuperclass().getName());
/*  986 */     stringBuffer.append('[');
/*  987 */     if (!isOpen()) {
/*  988 */       stringBuffer.append("closed");
/*      */     } else {
/*  990 */       synchronized (this.stateLock) {
/*  991 */         switch (this.state) {
/*      */           case 0:
/*  993 */             stringBuffer.append("unconnected");
/*      */             break;
/*      */           case 1:
/*  996 */             stringBuffer.append("connection-pending");
/*      */             break;
/*      */           case 2:
/*  999 */             stringBuffer.append("connected");
/* 1000 */             if (!this.isInputOpen)
/* 1001 */               stringBuffer.append(" ishut"); 
/* 1002 */             if (!this.isOutputOpen)
/* 1003 */               stringBuffer.append(" oshut"); 
/*      */             break;
/*      */         } 
/* 1006 */         InetSocketAddress inetSocketAddress = localAddress();
/* 1007 */         if (inetSocketAddress != null) {
/* 1008 */           stringBuffer.append(" local=");
/* 1009 */           stringBuffer.append(Net.getRevealedLocalAddressAsString(inetSocketAddress));
/*      */         } 
/* 1011 */         if (remoteAddress() != null) {
/* 1012 */           stringBuffer.append(" remote=");
/* 1013 */           stringBuffer.append(remoteAddress().toString());
/*      */         } 
/*      */       } 
/*      */     } 
/* 1017 */     stringBuffer.append(']');
/* 1018 */     return stringBuffer.toString();
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
/*      */   static {
/* 1032 */     IOUtil.load();
/* 1033 */   } private static NativeDispatcher nd = new SocketDispatcher();
/*      */   private final FileDescriptor fd;
/*      */   private final int fdVal;
/*      */   private boolean isReuseAddress;
/*      */   private static final int ST_UNINITIALIZED = -1;
/*      */   private static final int ST_UNCONNECTED = 0;
/*      */   private static final int ST_PENDING = 1;
/*      */   private static final int ST_CONNECTED = 2;
/*      */   private static final int ST_KILLPENDING = 3;
/*      */   private static final int ST_KILLED = 4;
/*      */   private InetSocketAddress localAddress;
/*      */   private InetSocketAddress remoteAddress;
/*      */   private Socket socket;
/*      */   
/*      */   private static native int checkConnect(FileDescriptor paramFileDescriptor, boolean paramBoolean1, boolean paramBoolean2) throws IOException;
/*      */   
/*      */   private static native int sendOutOfBandData(FileDescriptor paramFileDescriptor, byte paramByte) throws IOException;
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/nio/ch/SocketChannelImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */