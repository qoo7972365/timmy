/*     */ package sun.nio.ch.sctp;
/*     */ 
/*     */ import com.sun.nio.sctp.IllegalUnbindException;
/*     */ import com.sun.nio.sctp.SctpChannel;
/*     */ import com.sun.nio.sctp.SctpServerChannel;
/*     */ import com.sun.nio.sctp.SctpSocketOption;
/*     */ import com.sun.nio.sctp.SctpStandardSocketOptions;
/*     */ import java.io.FileDescriptor;
/*     */ import java.io.IOException;
/*     */ import java.net.InetAddress;
/*     */ import java.net.InetSocketAddress;
/*     */ import java.net.SocketAddress;
/*     */ import java.nio.channels.ClosedChannelException;
/*     */ import java.nio.channels.NotYetBoundException;
/*     */ import java.nio.channels.spi.SelectorProvider;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.util.Collections;
/*     */ import java.util.HashSet;
/*     */ import java.util.Set;
/*     */ import sun.nio.ch.IOStatus;
/*     */ import sun.nio.ch.IOUtil;
/*     */ import sun.nio.ch.NativeThread;
/*     */ import sun.nio.ch.Net;
/*     */ import sun.nio.ch.SelChImpl;
/*     */ import sun.nio.ch.SelectionKeyImpl;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SctpServerChannelImpl
/*     */   extends SctpServerChannel
/*     */   implements SelChImpl
/*     */ {
/*     */   private final FileDescriptor fd;
/*     */   private final int fdVal;
/*  65 */   private volatile long thread = 0L;
/*     */ 
/*     */   
/*  68 */   private final Object lock = new Object();
/*     */ 
/*     */ 
/*     */   
/*  72 */   private final Object stateLock = new Object();
/*     */   
/*     */   private enum ChannelState {
/*  75 */     UNINITIALIZED,
/*  76 */     INUSE,
/*  77 */     KILLPENDING,
/*  78 */     KILLED;
/*     */   }
/*     */   
/*  81 */   private ChannelState state = ChannelState.UNINITIALIZED;
/*     */ 
/*     */   
/*  84 */   int port = -1;
/*  85 */   private HashSet<InetSocketAddress> localAddresses = new HashSet<>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean wildcard;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SctpServerChannelImpl(SelectorProvider paramSelectorProvider) throws IOException {
/*  97 */     super(paramSelectorProvider);
/*  98 */     this.fd = SctpNet.socket(true);
/*  99 */     this.fdVal = IOUtil.fdVal(this.fd);
/* 100 */     this.state = ChannelState.INUSE;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public SctpServerChannel bind(SocketAddress paramSocketAddress, int paramInt) throws IOException {
/* 106 */     synchronized (this.lock) {
/* 107 */       synchronized (this.stateLock) {
/* 108 */         if (!isOpen())
/* 109 */           throw new ClosedChannelException(); 
/* 110 */         if (isBound()) {
/* 111 */           SctpNet.throwAlreadyBoundException();
/*     */         }
/*     */         
/* 114 */         InetSocketAddress inetSocketAddress1 = (paramSocketAddress == null) ? new InetSocketAddress(0) : Net.checkAddress(paramSocketAddress);
/* 115 */         SecurityManager securityManager = System.getSecurityManager();
/* 116 */         if (securityManager != null)
/* 117 */           securityManager.checkListen(inetSocketAddress1.getPort()); 
/* 118 */         Net.bind(this.fd, inetSocketAddress1.getAddress(), inetSocketAddress1.getPort());
/*     */         
/* 120 */         InetSocketAddress inetSocketAddress2 = Net.localAddress(this.fd);
/* 121 */         this.port = inetSocketAddress2.getPort();
/* 122 */         this.localAddresses.add(inetSocketAddress1);
/* 123 */         if (inetSocketAddress1.getAddress().isAnyLocalAddress()) {
/* 124 */           this.wildcard = true;
/*     */         }
/* 126 */         SctpNet.listen(this.fdVal, (paramInt < 1) ? 50 : paramInt);
/*     */       } 
/*     */     } 
/* 129 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public SctpServerChannel bindAddress(InetAddress paramInetAddress) throws IOException {
/* 135 */     return bindUnbindAddress(paramInetAddress, true);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public SctpServerChannel unbindAddress(InetAddress paramInetAddress) throws IOException {
/* 141 */     return bindUnbindAddress(paramInetAddress, false);
/*     */   }
/*     */ 
/*     */   
/*     */   private SctpServerChannel bindUnbindAddress(InetAddress paramInetAddress, boolean paramBoolean) throws IOException {
/* 146 */     if (paramInetAddress == null) {
/* 147 */       throw new IllegalArgumentException();
/*     */     }
/* 149 */     synchronized (this.lock) {
/* 150 */       synchronized (this.stateLock) {
/* 151 */         if (!isOpen())
/* 152 */           throw new ClosedChannelException(); 
/* 153 */         if (!isBound())
/* 154 */           throw new NotYetBoundException(); 
/* 155 */         if (this.wildcard) {
/* 156 */           throw new IllegalStateException("Cannot add or remove addresses from a channel that is bound to the wildcard address");
/*     */         }
/* 158 */         if (paramInetAddress.isAnyLocalAddress()) {
/* 159 */           throw new IllegalArgumentException("Cannot add or remove the wildcard address");
/*     */         }
/* 161 */         if (paramBoolean) {
/* 162 */           for (InetSocketAddress inetSocketAddress : this.localAddresses) {
/* 163 */             if (inetSocketAddress.getAddress().equals(paramInetAddress)) {
/* 164 */               SctpNet.throwAlreadyBoundException();
/*     */             }
/*     */           }
/*     */         
/*     */         } else {
/*     */           
/* 170 */           if (this.localAddresses.size() <= 1)
/* 171 */             throw new IllegalUnbindException("Cannot remove address from a channel with only one address bound"); 
/* 172 */           boolean bool = false;
/* 173 */           for (InetSocketAddress inetSocketAddress : this.localAddresses) {
/* 174 */             if (inetSocketAddress.getAddress().equals(paramInetAddress)) {
/* 175 */               bool = true;
/*     */               break;
/*     */             } 
/*     */           } 
/* 179 */           if (!bool) {
/* 180 */             throw new IllegalUnbindException("Cannot remove address from a channel that is not bound to that address");
/*     */           }
/*     */         } 
/* 183 */         SctpNet.bindx(this.fdVal, new InetAddress[] { paramInetAddress }, this.port, paramBoolean);
/*     */ 
/*     */         
/* 186 */         if (paramBoolean) {
/* 187 */           this.localAddresses.add(new InetSocketAddress(paramInetAddress, this.port));
/*     */         } else {
/* 189 */           for (InetSocketAddress inetSocketAddress : this.localAddresses) {
/* 190 */             if (inetSocketAddress.getAddress().equals(paramInetAddress)) {
/* 191 */               this.localAddresses.remove(inetSocketAddress);
/*     */               break;
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/* 198 */     return this;
/*     */   }
/*     */   
/*     */   private boolean isBound() {
/* 202 */     synchronized (this.stateLock) {
/* 203 */       return !(this.port == -1);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void acceptCleanup() throws IOException {
/* 208 */     synchronized (this.stateLock) {
/* 209 */       this.thread = 0L;
/* 210 */       if (this.state == ChannelState.KILLPENDING) {
/* 211 */         kill();
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public SctpChannel accept() throws IOException {
/* 217 */     synchronized (this.lock) {
/* 218 */       if (!isOpen())
/* 219 */         throw new ClosedChannelException(); 
/* 220 */       if (!isBound())
/* 221 */         throw new NotYetBoundException(); 
/* 222 */       SctpChannelImpl sctpChannelImpl = null;
/*     */       
/* 224 */       int i = 0;
/* 225 */       FileDescriptor fileDescriptor = new FileDescriptor();
/* 226 */       InetSocketAddress[] arrayOfInetSocketAddress = new InetSocketAddress[1];
/*     */       
/*     */       try {
/* 229 */         begin();
/* 230 */         if (!isOpen())
/* 231 */           return null; 
/* 232 */         this.thread = NativeThread.current();
/*     */         while (true) {
/* 234 */           i = accept0(this.fd, fileDescriptor, arrayOfInetSocketAddress);
/* 235 */           if (i == -3 && isOpen())
/*     */             continue; 
/*     */           break;
/*     */         } 
/*     */       } finally {
/* 240 */         acceptCleanup();
/* 241 */         end((i > 0));
/* 242 */         assert IOStatus.check(i);
/*     */       } 
/*     */       
/* 245 */       if (i < 1) {
/* 246 */         return null;
/*     */       }
/* 248 */       IOUtil.configureBlocking(fileDescriptor, true);
/* 249 */       InetSocketAddress inetSocketAddress = arrayOfInetSocketAddress[0];
/* 250 */       sctpChannelImpl = new SctpChannelImpl(provider(), fileDescriptor);
/*     */       
/* 252 */       SecurityManager securityManager = System.getSecurityManager();
/* 253 */       if (securityManager != null) {
/* 254 */         securityManager.checkAccept(inetSocketAddress.getAddress().getHostAddress(), inetSocketAddress
/* 255 */             .getPort());
/*     */       }
/* 257 */       return sctpChannelImpl;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void implConfigureBlocking(boolean paramBoolean) throws IOException {
/* 263 */     IOUtil.configureBlocking(this.fd, paramBoolean);
/*     */   }
/*     */ 
/*     */   
/*     */   public void implCloseSelectableChannel() throws IOException {
/* 268 */     synchronized (this.stateLock) {
/* 269 */       SctpNet.preClose(this.fdVal);
/* 270 */       if (this.thread != 0L)
/* 271 */         NativeThread.signal(this.thread); 
/* 272 */       if (!isRegistered()) {
/* 273 */         kill();
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public void kill() throws IOException {
/* 279 */     synchronized (this.stateLock) {
/* 280 */       if (this.state == ChannelState.KILLED)
/*     */         return; 
/* 282 */       if (this.state == ChannelState.UNINITIALIZED) {
/* 283 */         this.state = ChannelState.KILLED;
/*     */         return;
/*     */       } 
/* 286 */       assert !isOpen() && !isRegistered();
/*     */ 
/*     */       
/* 289 */       if (this.thread == 0L) {
/* 290 */         SctpNet.close(this.fdVal);
/* 291 */         this.state = ChannelState.KILLED;
/*     */       } else {
/* 293 */         this.state = ChannelState.KILLPENDING;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public FileDescriptor getFD() {
/* 300 */     return this.fd;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getFDVal() {
/* 305 */     return this.fdVal;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean translateReadyOps(int paramInt1, int paramInt2, SelectionKeyImpl paramSelectionKeyImpl) {
/* 313 */     int i = paramSelectionKeyImpl.nioInterestOps();
/* 314 */     int j = paramSelectionKeyImpl.nioReadyOps();
/* 315 */     int k = paramInt2;
/*     */     
/* 317 */     if ((paramInt1 & Net.POLLNVAL) != 0)
/*     */     {
/*     */ 
/*     */       
/* 321 */       return false;
/*     */     }
/*     */     
/* 324 */     if ((paramInt1 & (Net.POLLERR | Net.POLLHUP)) != 0) {
/* 325 */       k = i;
/* 326 */       paramSelectionKeyImpl.nioReadyOps(k);
/* 327 */       return ((k & (j ^ 0xFFFFFFFF)) != 0);
/*     */     } 
/*     */     
/* 330 */     if ((paramInt1 & Net.POLLIN) != 0 && (i & 0x10) != 0)
/*     */     {
/* 332 */       k |= 0x10;
/*     */     }
/* 334 */     paramSelectionKeyImpl.nioReadyOps(k);
/* 335 */     return ((k & (j ^ 0xFFFFFFFF)) != 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean translateAndUpdateReadyOps(int paramInt, SelectionKeyImpl paramSelectionKeyImpl) {
/* 340 */     return translateReadyOps(paramInt, paramSelectionKeyImpl.nioReadyOps(), paramSelectionKeyImpl);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean translateAndSetReadyOps(int paramInt, SelectionKeyImpl paramSelectionKeyImpl) {
/* 345 */     return translateReadyOps(paramInt, 0, paramSelectionKeyImpl);
/*     */   }
/*     */ 
/*     */   
/*     */   public void translateAndSetInterestOps(int paramInt, SelectionKeyImpl paramSelectionKeyImpl) {
/* 350 */     int i = 0;
/*     */ 
/*     */     
/* 353 */     if ((paramInt & 0x10) != 0) {
/* 354 */       i |= Net.POLLIN;
/*     */     }
/* 356 */     paramSelectionKeyImpl.selector.putEventOps(paramSelectionKeyImpl, i);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <T> SctpServerChannel setOption(SctpSocketOption<T> paramSctpSocketOption, T paramT) throws IOException {
/* 363 */     if (paramSctpSocketOption == null)
/* 364 */       throw new NullPointerException(); 
/* 365 */     if (!supportedOptions().contains(paramSctpSocketOption)) {
/* 366 */       throw new UnsupportedOperationException("'" + paramSctpSocketOption + "' not supported");
/*     */     }
/* 368 */     synchronized (this.stateLock) {
/* 369 */       if (!isOpen()) {
/* 370 */         throw new ClosedChannelException();
/*     */       }
/* 372 */       SctpNet.setSocketOption(this.fdVal, paramSctpSocketOption, paramT, 0);
/* 373 */       return this;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public <T> T getOption(SctpSocketOption<T> paramSctpSocketOption) throws IOException {
/* 380 */     if (paramSctpSocketOption == null)
/* 381 */       throw new NullPointerException(); 
/* 382 */     if (!supportedOptions().contains(paramSctpSocketOption)) {
/* 383 */       throw new UnsupportedOperationException("'" + paramSctpSocketOption + "' not supported");
/*     */     }
/* 385 */     synchronized (this.stateLock) {
/* 386 */       if (!isOpen()) {
/* 387 */         throw new ClosedChannelException();
/*     */       }
/* 389 */       return (T)SctpNet.getSocketOption(this.fdVal, paramSctpSocketOption, 0);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static class DefaultOptionsHolder {
/* 394 */     static final Set<SctpSocketOption<?>> defaultOptions = defaultOptions();
/*     */     
/*     */     private static Set<SctpSocketOption<?>> defaultOptions() {
/* 397 */       HashSet<SctpSocketOption<SctpStandardSocketOptions.InitMaxStreams>> hashSet = new HashSet(1);
/* 398 */       hashSet.add(SctpStandardSocketOptions.SCTP_INIT_MAXSTREAMS);
/* 399 */       return Collections.unmodifiableSet(hashSet);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public final Set<SctpSocketOption<?>> supportedOptions() {
/* 405 */     return DefaultOptionsHolder.defaultOptions;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Set<SocketAddress> getAllLocalAddresses() throws IOException {
/* 411 */     synchronized (this.stateLock) {
/* 412 */       if (!isOpen())
/* 413 */         throw new ClosedChannelException(); 
/* 414 */       if (!isBound()) {
/* 415 */         return Collections.emptySet();
/*     */       }
/* 417 */       return SctpNet.getLocalAddresses(this.fdVal);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static native void initIDs();
/*     */ 
/*     */   
/*     */   private static native int accept0(FileDescriptor paramFileDescriptor1, FileDescriptor paramFileDescriptor2, InetSocketAddress[] paramArrayOfInetSocketAddress) throws IOException;
/*     */   
/*     */   static {
/* 428 */     IOUtil.load();
/* 429 */     AccessController.doPrivileged(new PrivilegedAction<Void>()
/*     */         {
/*     */           public Void run() {
/* 432 */             System.loadLibrary("sctp");
/* 433 */             return null;
/*     */           }
/*     */         });
/* 436 */     initIDs();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/nio/ch/sctp/SctpServerChannelImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */