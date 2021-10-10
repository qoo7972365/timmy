/*     */ package sun.nio.ch;
/*     */ 
/*     */ import java.io.FileDescriptor;
/*     */ import java.io.IOException;
/*     */ import java.net.InetSocketAddress;
/*     */ import java.nio.channels.AcceptPendingException;
/*     */ import java.nio.channels.AsynchronousCloseException;
/*     */ import java.nio.channels.AsynchronousSocketChannel;
/*     */ import java.nio.channels.ClosedChannelException;
/*     */ import java.nio.channels.CompletionHandler;
/*     */ import java.nio.channels.NotYetBoundException;
/*     */ import java.security.AccessControlContext;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.util.concurrent.Future;
/*     */ import java.util.concurrent.atomic.AtomicBoolean;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class UnixAsynchronousServerSocketChannelImpl
/*     */   extends AsynchronousServerSocketChannelImpl
/*     */   implements Port.PollableChannel
/*     */ {
/*  46 */   private static final NativeDispatcher nd = new SocketDispatcher();
/*     */   
/*     */   private final Port port;
/*     */   
/*     */   private final int fdVal;
/*     */   
/*  52 */   private final AtomicBoolean accepting = new AtomicBoolean();
/*     */   private void enableAccept() {
/*  54 */     this.accepting.set(false);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*  59 */   private final Object updateLock = new Object();
/*     */ 
/*     */   
/*     */   private boolean acceptPending;
/*     */   
/*     */   private CompletionHandler<AsynchronousSocketChannel, Object> acceptHandler;
/*     */   
/*     */   private Object acceptAttachment;
/*     */   
/*     */   private PendingFuture<AsynchronousSocketChannel, Object> acceptFuture;
/*     */   
/*     */   private AccessControlContext acceptAcc;
/*     */ 
/*     */   
/*     */   UnixAsynchronousServerSocketChannelImpl(Port paramPort) throws IOException {
/*  74 */     super(paramPort);
/*     */     
/*     */     try {
/*  77 */       IOUtil.configureBlocking(this.fd, false);
/*  78 */     } catch (IOException iOException) {
/*  79 */       nd.close(this.fd);
/*  80 */       throw iOException;
/*     */     } 
/*  82 */     this.port = paramPort;
/*  83 */     this.fdVal = IOUtil.fdVal(this.fd);
/*     */ 
/*     */     
/*  86 */     paramPort.register(this.fdVal, this);
/*     */   }
/*     */   void implClose() throws IOException {
/*     */     CompletionHandler<AsynchronousSocketChannel, Object> completionHandler;
/*     */     Object object;
/*     */     PendingFuture<AsynchronousSocketChannel, Object> pendingFuture;
/*  92 */     this.port.unregister(this.fdVal);
/*     */ 
/*     */     
/*  95 */     nd.close(this.fd);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 101 */     synchronized (this.updateLock) {
/* 102 */       if (!this.acceptPending)
/*     */         return; 
/* 104 */       this.acceptPending = false;
/* 105 */       completionHandler = this.acceptHandler;
/* 106 */       object = this.acceptAttachment;
/* 107 */       pendingFuture = this.acceptFuture;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 112 */     AsynchronousCloseException asynchronousCloseException = new AsynchronousCloseException();
/* 113 */     asynchronousCloseException.setStackTrace(new StackTraceElement[0]);
/* 114 */     if (completionHandler == null) {
/* 115 */       pendingFuture.setFailure(asynchronousCloseException);
/*     */     } else {
/*     */       
/* 118 */       Invoker.invokeIndirectly(this, completionHandler, object, (AsynchronousSocketChannel)null, asynchronousCloseException);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public AsynchronousChannelGroupImpl group() {
/* 124 */     return this.port;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onEvent(int paramInt, boolean paramBoolean) {
/* 132 */     synchronized (this.updateLock) {
/* 133 */       if (!this.acceptPending)
/*     */         return; 
/* 135 */       this.acceptPending = false;
/*     */     } 
/*     */ 
/*     */     
/* 139 */     FileDescriptor fileDescriptor = new FileDescriptor();
/* 140 */     InetSocketAddress[] arrayOfInetSocketAddress = new InetSocketAddress[1];
/* 141 */     Throwable throwable = null;
/*     */     try {
/* 143 */       begin();
/* 144 */       int i = accept(this.fd, fileDescriptor, arrayOfInetSocketAddress);
/*     */ 
/*     */       
/* 147 */       if (i == -2) {
/* 148 */         synchronized (this.updateLock) {
/* 149 */           this.acceptPending = true;
/*     */         } 
/* 151 */         this.port.startPoll(this.fdVal, Net.POLLIN);
/*     */         
/*     */         return;
/*     */       } 
/* 155 */     } catch (Throwable throwable1) {
/* 156 */       if (throwable1 instanceof ClosedChannelException)
/* 157 */         throwable1 = new AsynchronousCloseException(); 
/* 158 */       throwable = throwable1;
/*     */     } finally {
/* 160 */       end();
/*     */     } 
/*     */ 
/*     */     
/* 164 */     AsynchronousSocketChannel asynchronousSocketChannel = null;
/* 165 */     if (throwable == null) {
/*     */       try {
/* 167 */         asynchronousSocketChannel = finishAccept(fileDescriptor, arrayOfInetSocketAddress[0], this.acceptAcc);
/* 168 */       } catch (Throwable throwable1) {
/* 169 */         if (!(throwable1 instanceof IOException) && !(throwable1 instanceof SecurityException))
/* 170 */           throwable1 = new IOException(throwable1); 
/* 171 */         throwable = throwable1;
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/* 176 */     CompletionHandler<AsynchronousSocketChannel, Object> completionHandler = this.acceptHandler;
/* 177 */     Object object = this.acceptAttachment;
/* 178 */     PendingFuture<AsynchronousSocketChannel, Object> pendingFuture = this.acceptFuture;
/*     */ 
/*     */     
/* 181 */     enableAccept();
/*     */     
/* 183 */     if (completionHandler == null) {
/* 184 */       pendingFuture.setResult(asynchronousSocketChannel, throwable);
/*     */ 
/*     */       
/* 187 */       if (asynchronousSocketChannel != null && pendingFuture.isCancelled()) {
/*     */         try {
/* 189 */           asynchronousSocketChannel.close();
/* 190 */         } catch (IOException iOException) {}
/*     */       }
/*     */     } else {
/* 193 */       Invoker.invoke(this, completionHandler, object, asynchronousSocketChannel, throwable);
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
/*     */   private AsynchronousSocketChannel finishAccept(FileDescriptor paramFileDescriptor, final InetSocketAddress remote, AccessControlContext paramAccessControlContext) throws IOException, SecurityException {
/* 208 */     UnixAsynchronousSocketChannelImpl unixAsynchronousSocketChannelImpl = null;
/*     */     try {
/* 210 */       unixAsynchronousSocketChannelImpl = new UnixAsynchronousSocketChannelImpl(this.port, paramFileDescriptor, remote);
/* 211 */     } catch (IOException iOException) {
/* 212 */       nd.close(paramFileDescriptor);
/* 213 */       throw iOException;
/*     */     } 
/*     */ 
/*     */     
/*     */     try {
/* 218 */       if (paramAccessControlContext != null) {
/* 219 */         AccessController.doPrivileged(new PrivilegedAction<Void>() {
/*     */               public Void run() {
/* 221 */                 SecurityManager securityManager = System.getSecurityManager();
/* 222 */                 if (securityManager != null) {
/* 223 */                   securityManager.checkAccept(remote.getAddress().getHostAddress(), remote
/* 224 */                       .getPort());
/*     */                 }
/* 226 */                 return null;
/*     */               }
/*     */             }paramAccessControlContext);
/*     */       } else {
/* 230 */         SecurityManager securityManager = System.getSecurityManager();
/* 231 */         if (securityManager != null) {
/* 232 */           securityManager.checkAccept(remote.getAddress().getHostAddress(), remote
/* 233 */               .getPort());
/*     */         }
/*     */       } 
/* 236 */     } catch (SecurityException securityException) {
/*     */       try {
/* 238 */         unixAsynchronousSocketChannelImpl.close();
/* 239 */       } catch (Throwable throwable) {
/* 240 */         securityException.addSuppressed(throwable);
/*     */       } 
/* 242 */       throw securityException;
/*     */     } 
/* 244 */     return unixAsynchronousSocketChannelImpl;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   Future<AsynchronousSocketChannel> implAccept(Object paramObject, CompletionHandler<AsynchronousSocketChannel, Object> paramCompletionHandler) {
/* 252 */     if (!isOpen()) {
/* 253 */       ClosedChannelException closedChannelException = new ClosedChannelException();
/* 254 */       if (paramCompletionHandler == null) {
/* 255 */         return CompletedFuture.withFailure(closedChannelException);
/*     */       }
/* 257 */       Invoker.invoke(this, paramCompletionHandler, paramObject, null, closedChannelException);
/* 258 */       return null;
/*     */     } 
/*     */     
/* 261 */     if (this.localAddress == null) {
/* 262 */       throw new NotYetBoundException();
/*     */     }
/*     */ 
/*     */     
/* 266 */     if (isAcceptKilled()) {
/* 267 */       throw new RuntimeException("Accept not allowed due cancellation");
/*     */     }
/*     */     
/* 270 */     if (!this.accepting.compareAndSet(false, true)) {
/* 271 */       throw new AcceptPendingException();
/*     */     }
/*     */     
/* 274 */     FileDescriptor fileDescriptor = new FileDescriptor();
/* 275 */     InetSocketAddress[] arrayOfInetSocketAddress = new InetSocketAddress[1];
/* 276 */     Throwable throwable = null;
/*     */     try {
/* 278 */       begin();
/*     */       
/* 280 */       int i = accept(this.fd, fileDescriptor, arrayOfInetSocketAddress);
/* 281 */       if (i == -2) {
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 286 */         PendingFuture<Object, Object> pendingFuture = null;
/* 287 */         synchronized (this.updateLock) {
/* 288 */           if (paramCompletionHandler == null) {
/* 289 */             this.acceptHandler = null;
/* 290 */             pendingFuture = new PendingFuture<>(this);
/* 291 */             this.acceptFuture = (PendingFuture)pendingFuture;
/*     */           } else {
/* 293 */             this.acceptHandler = paramCompletionHandler;
/* 294 */             this.acceptAttachment = paramObject;
/*     */           } 
/* 296 */           this
/* 297 */             .acceptAcc = (System.getSecurityManager() == null) ? null : AccessController.getContext();
/* 298 */           this.acceptPending = true;
/*     */         } 
/*     */ 
/*     */         
/* 302 */         this.port.startPoll(this.fdVal, Net.POLLIN);
/* 303 */         return pendingFuture;
/*     */       } 
/* 305 */     } catch (Throwable throwable1) {
/*     */       
/* 307 */       if (throwable1 instanceof ClosedChannelException)
/* 308 */         throwable1 = new AsynchronousCloseException(); 
/* 309 */       throwable = throwable1;
/*     */     } finally {
/* 311 */       end();
/*     */     } 
/*     */     
/* 314 */     AsynchronousSocketChannel asynchronousSocketChannel = null;
/* 315 */     if (throwable == null) {
/*     */       
/*     */       try {
/* 318 */         asynchronousSocketChannel = finishAccept(fileDescriptor, arrayOfInetSocketAddress[0], (AccessControlContext)null);
/* 319 */       } catch (Throwable throwable1) {
/* 320 */         throwable = throwable1;
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/* 325 */     enableAccept();
/*     */     
/* 327 */     if (paramCompletionHandler == null) {
/* 328 */       return CompletedFuture.withResult(asynchronousSocketChannel, throwable);
/*     */     }
/* 330 */     Invoker.invokeIndirectly(this, paramCompletionHandler, paramObject, asynchronousSocketChannel, throwable);
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
/*     */ 
/*     */   
/*     */   private int accept(FileDescriptor paramFileDescriptor1, FileDescriptor paramFileDescriptor2, InetSocketAddress[] paramArrayOfInetSocketAddress) throws IOException {
/* 344 */     return accept0(paramFileDescriptor1, paramFileDescriptor2, paramArrayOfInetSocketAddress);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static native void initIDs();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private native int accept0(FileDescriptor paramFileDescriptor1, FileDescriptor paramFileDescriptor2, InetSocketAddress[] paramArrayOfInetSocketAddress) throws IOException;
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/* 360 */     IOUtil.load();
/* 361 */     initIDs();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/nio/ch/UnixAsynchronousServerSocketChannelImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */