/*     */ package sun.nio.ch;
/*     */ 
/*     */ import java.io.FileDescriptor;
/*     */ import java.io.IOException;
/*     */ import java.net.InetSocketAddress;
/*     */ import java.net.SocketAddress;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.channels.AlreadyConnectedException;
/*     */ import java.nio.channels.AsynchronousCloseException;
/*     */ import java.nio.channels.ClosedChannelException;
/*     */ import java.nio.channels.CompletionHandler;
/*     */ import java.nio.channels.ConnectionPendingException;
/*     */ import java.nio.channels.InterruptedByTimeoutException;
/*     */ import java.nio.channels.ShutdownChannelGroupException;
/*     */ import java.security.AccessController;
/*     */ import java.util.concurrent.Future;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import sun.net.NetHooks;
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
/*     */ class UnixAsynchronousSocketChannelImpl
/*     */   extends AsynchronousSocketChannelImpl
/*     */   implements Port.PollableChannel
/*     */ {
/*     */   private static final boolean disableSynchronousRead;
/*     */   private final Port port;
/*     */   private final int fdVal;
/*  45 */   private static final NativeDispatcher nd = new SocketDispatcher();
/*  46 */   private enum OpType { CONNECT, READ, WRITE; }
/*     */ 
/*     */   
/*     */   static {
/*  50 */     String str = AccessController.<String>doPrivileged(new GetPropertyAction("sun.nio.ch.disableSynchronousRead", "false"));
/*     */ 
/*     */     
/*  53 */     disableSynchronousRead = (str.length() == 0) ? true : Boolean.valueOf(str).booleanValue();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 754 */     IOUtil.load();
/*     */   }
/*     */   
/*     */   private final Object updateLock = new Object();
/*     */   private boolean connectPending;
/*     */   private CompletionHandler<Void, Object> connectHandler;
/*     */   private Object connectAttachment;
/*     */   private PendingFuture<Void, Object> connectFuture;
/*     */   private SocketAddress pendingRemote;
/*     */   private boolean readPending;
/*     */   private boolean isScatteringRead;
/*     */   private ByteBuffer readBuffer;
/*     */   private ByteBuffer[] readBuffers;
/*     */   private CompletionHandler<Number, Object> readHandler;
/*     */   private Object readAttachment;
/*     */   private PendingFuture<Number, Object> readFuture;
/*     */   private Future<?> readTimer;
/*     */   private boolean writePending;
/*     */   private boolean isGatheringWrite;
/*     */   private ByteBuffer writeBuffer;
/*     */   private ByteBuffer[] writeBuffers;
/*     */   private CompletionHandler<Number, Object> writeHandler;
/*     */   private Object writeAttachment;
/*     */   private PendingFuture<Number, Object> writeFuture;
/*     */   private Future<?> writeTimer;
/*     */   private Runnable readTimeoutTask;
/*     */   private Runnable writeTimeoutTask;
/*     */   
/*     */   UnixAsynchronousSocketChannelImpl(Port paramPort) throws IOException {
/*     */     super(paramPort);
/*     */     this.readTimeoutTask = new Runnable() {
/*     */         public void run() {
/*     */           CompletionHandler<?, ? super Object> completionHandler = null;
/*     */           Object object = null;
/*     */           PendingFuture pendingFuture = null;
/*     */           synchronized (UnixAsynchronousSocketChannelImpl.this.updateLock) {
/*     */             if (!UnixAsynchronousSocketChannelImpl.this.readPending)
/*     */               return; 
/*     */             UnixAsynchronousSocketChannelImpl.this.readPending = false;
/*     */             completionHandler = UnixAsynchronousSocketChannelImpl.this.readHandler;
/*     */             object = UnixAsynchronousSocketChannelImpl.this.readAttachment;
/*     */             pendingFuture = UnixAsynchronousSocketChannelImpl.this.readFuture;
/*     */           } 
/*     */           UnixAsynchronousSocketChannelImpl.this.enableReading(true);
/*     */           InterruptedByTimeoutException interruptedByTimeoutException = new InterruptedByTimeoutException();
/*     */           if (completionHandler == null) {
/*     */             pendingFuture.setFailure(interruptedByTimeoutException);
/*     */           } else {
/*     */             UnixAsynchronousSocketChannelImpl unixAsynchronousSocketChannelImpl = UnixAsynchronousSocketChannelImpl.this;
/*     */             Invoker.invokeIndirectly(unixAsynchronousSocketChannelImpl, completionHandler, object, (Object)null, interruptedByTimeoutException);
/*     */           } 
/*     */         }
/*     */       };
/*     */     this.writeTimeoutTask = new Runnable() {
/*     */         public void run() {
/*     */           CompletionHandler<?, ? super Object> completionHandler = null;
/*     */           Object object = null;
/*     */           PendingFuture pendingFuture = null;
/*     */           synchronized (UnixAsynchronousSocketChannelImpl.this.updateLock) {
/*     */             if (!UnixAsynchronousSocketChannelImpl.this.writePending)
/*     */               return; 
/*     */             UnixAsynchronousSocketChannelImpl.this.writePending = false;
/*     */             completionHandler = UnixAsynchronousSocketChannelImpl.this.writeHandler;
/*     */             object = UnixAsynchronousSocketChannelImpl.this.writeAttachment;
/*     */             pendingFuture = UnixAsynchronousSocketChannelImpl.this.writeFuture;
/*     */           } 
/*     */           UnixAsynchronousSocketChannelImpl.this.enableWriting(true);
/*     */           InterruptedByTimeoutException interruptedByTimeoutException = new InterruptedByTimeoutException();
/*     */           if (completionHandler != null) {
/*     */             Invoker.invokeIndirectly(UnixAsynchronousSocketChannelImpl.this, completionHandler, object, (Object)null, interruptedByTimeoutException);
/*     */           } else {
/*     */             pendingFuture.setFailure(interruptedByTimeoutException);
/*     */           } 
/*     */         }
/*     */       };
/*     */     try {
/*     */       IOUtil.configureBlocking(this.fd, false);
/*     */     } catch (IOException iOException) {
/*     */       nd.close(this.fd);
/*     */       throw iOException;
/*     */     } 
/*     */     this.port = paramPort;
/*     */     this.fdVal = IOUtil.fdVal(this.fd);
/*     */     paramPort.register(this.fdVal, this);
/*     */   }
/*     */   
/*     */   UnixAsynchronousSocketChannelImpl(Port paramPort, FileDescriptor paramFileDescriptor, InetSocketAddress paramInetSocketAddress) throws IOException {
/*     */     super(paramPort, paramFileDescriptor, paramInetSocketAddress);
/*     */     this.readTimeoutTask = new Runnable() {
/*     */         public void run() {
/*     */           CompletionHandler<?, ? super Object> completionHandler = null;
/*     */           Object object = null;
/*     */           PendingFuture pendingFuture = null;
/*     */           synchronized (UnixAsynchronousSocketChannelImpl.this.updateLock) {
/*     */             if (!UnixAsynchronousSocketChannelImpl.this.readPending)
/*     */               return; 
/*     */             UnixAsynchronousSocketChannelImpl.this.readPending = false;
/*     */             completionHandler = UnixAsynchronousSocketChannelImpl.this.readHandler;
/*     */             object = UnixAsynchronousSocketChannelImpl.this.readAttachment;
/*     */             pendingFuture = UnixAsynchronousSocketChannelImpl.this.readFuture;
/*     */           } 
/*     */           UnixAsynchronousSocketChannelImpl.this.enableReading(true);
/*     */           InterruptedByTimeoutException interruptedByTimeoutException = new InterruptedByTimeoutException();
/*     */           if (completionHandler == null) {
/*     */             pendingFuture.setFailure(interruptedByTimeoutException);
/*     */           } else {
/*     */             UnixAsynchronousSocketChannelImpl unixAsynchronousSocketChannelImpl = UnixAsynchronousSocketChannelImpl.this;
/*     */             Invoker.invokeIndirectly(unixAsynchronousSocketChannelImpl, completionHandler, object, (Object)null, interruptedByTimeoutException);
/*     */           } 
/*     */         }
/*     */       };
/*     */     this.writeTimeoutTask = new Runnable() {
/*     */         public void run() {
/*     */           CompletionHandler<?, ? super Object> completionHandler = null;
/*     */           Object object = null;
/*     */           PendingFuture pendingFuture = null;
/*     */           synchronized (UnixAsynchronousSocketChannelImpl.this.updateLock) {
/*     */             if (!UnixAsynchronousSocketChannelImpl.this.writePending)
/*     */               return; 
/*     */             UnixAsynchronousSocketChannelImpl.this.writePending = false;
/*     */             completionHandler = UnixAsynchronousSocketChannelImpl.this.writeHandler;
/*     */             object = UnixAsynchronousSocketChannelImpl.this.writeAttachment;
/*     */             pendingFuture = UnixAsynchronousSocketChannelImpl.this.writeFuture;
/*     */           } 
/*     */           UnixAsynchronousSocketChannelImpl.this.enableWriting(true);
/*     */           InterruptedByTimeoutException interruptedByTimeoutException = new InterruptedByTimeoutException();
/*     */           if (completionHandler != null) {
/*     */             Invoker.invokeIndirectly(UnixAsynchronousSocketChannelImpl.this, completionHandler, object, (Object)null, interruptedByTimeoutException);
/*     */           } else {
/*     */             pendingFuture.setFailure(interruptedByTimeoutException);
/*     */           } 
/*     */         }
/*     */       };
/*     */     this.fdVal = IOUtil.fdVal(paramFileDescriptor);
/*     */     IOUtil.configureBlocking(paramFileDescriptor, false);
/*     */     try {
/*     */       paramPort.register(this.fdVal, this);
/*     */     } catch (ShutdownChannelGroupException shutdownChannelGroupException) {
/*     */       throw new IOException(shutdownChannelGroupException);
/*     */     } 
/*     */     this.port = paramPort;
/*     */   }
/*     */   
/*     */   public AsynchronousChannelGroupImpl group() {
/*     */     return this.port;
/*     */   }
/*     */   
/*     */   private void updateEvents() {
/*     */     assert Thread.holdsLock(this.updateLock);
/*     */     int i = 0;
/*     */     if (this.readPending)
/*     */       i |= Net.POLLIN; 
/*     */     if (this.connectPending || this.writePending)
/*     */       i |= Net.POLLOUT; 
/*     */     if (i != 0)
/*     */       this.port.startPoll(this.fdVal, i); 
/*     */   }
/*     */   
/*     */   private void lockAndUpdateEvents() {
/*     */     synchronized (this.updateLock) {
/*     */       updateEvents();
/*     */     } 
/*     */   }
/*     */   
/*     */   private void finish(boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3) {
/*     */     boolean bool1 = false;
/*     */     boolean bool2 = false;
/*     */     boolean bool3 = false;
/*     */     synchronized (this.updateLock) {
/*     */       if (paramBoolean2 && this.readPending) {
/*     */         this.readPending = false;
/*     */         bool1 = true;
/*     */       } 
/*     */       if (paramBoolean3)
/*     */         if (this.writePending) {
/*     */           this.writePending = false;
/*     */           bool2 = true;
/*     */         } else if (this.connectPending) {
/*     */           this.connectPending = false;
/*     */           bool3 = true;
/*     */         }  
/*     */     } 
/*     */     if (bool1) {
/*     */       if (bool2)
/*     */         finishWrite(false); 
/*     */       finishRead(paramBoolean1);
/*     */       return;
/*     */     } 
/*     */     if (bool2)
/*     */       finishWrite(paramBoolean1); 
/*     */     if (bool3)
/*     */       finishConnect(paramBoolean1); 
/*     */   }
/*     */   
/*     */   public void onEvent(int paramInt, boolean paramBoolean) {
/*     */     boolean bool1 = ((paramInt & Net.POLLIN) > 0) ? true : false;
/*     */     boolean bool2 = ((paramInt & Net.POLLOUT) > 0) ? true : false;
/*     */     if ((paramInt & (Net.POLLERR | Net.POLLHUP)) > 0) {
/*     */       bool1 = true;
/*     */       bool2 = true;
/*     */     } 
/*     */     finish(paramBoolean, bool1, bool2);
/*     */   }
/*     */   
/*     */   void implClose() throws IOException {
/*     */     this.port.unregister(this.fdVal);
/*     */     nd.close(this.fd);
/*     */     finish(false, true, true);
/*     */   }
/*     */   
/*     */   public void onCancel(PendingFuture<?, ?> paramPendingFuture) {
/*     */     if (paramPendingFuture.getContext() == OpType.CONNECT)
/*     */       killConnect(); 
/*     */     if (paramPendingFuture.getContext() == OpType.READ)
/*     */       killReading(); 
/*     */     if (paramPendingFuture.getContext() == OpType.WRITE)
/*     */       killWriting(); 
/*     */   }
/*     */   
/*     */   private void setConnected() throws IOException {
/*     */     synchronized (this.stateLock) {
/*     */       this.state = 2;
/*     */       this.localAddress = Net.localAddress(this.fd);
/*     */       this.remoteAddress = (InetSocketAddress)this.pendingRemote;
/*     */     } 
/*     */   }
/*     */   
/*     */   private void finishConnect(boolean paramBoolean) {
/*     */     Throwable throwable = null;
/*     */     try {
/*     */       begin();
/*     */       checkConnect(this.fdVal);
/*     */       setConnected();
/*     */     } catch (Throwable throwable1) {
/*     */       if (throwable1 instanceof ClosedChannelException)
/*     */         throwable1 = new AsynchronousCloseException(); 
/*     */       throwable = throwable1;
/*     */     } finally {
/*     */       end();
/*     */     } 
/*     */     if (throwable != null)
/*     */       try {
/*     */         close();
/*     */       } catch (Throwable throwable1) {
/*     */         throwable.addSuppressed(throwable1);
/*     */       }  
/*     */     CompletionHandler<Void, Object> completionHandler = this.connectHandler;
/*     */     this.connectHandler = null;
/*     */     Object object = this.connectAttachment;
/*     */     PendingFuture<Void, Object> pendingFuture = this.connectFuture;
/*     */     if (completionHandler == null) {
/*     */       pendingFuture.setResult(null, throwable);
/*     */     } else if (paramBoolean) {
/*     */       Invoker.invokeUnchecked(completionHandler, object, null, throwable);
/*     */     } else {
/*     */       Invoker.invokeIndirectly(this, completionHandler, object, (Void)null, throwable);
/*     */     } 
/*     */   }
/*     */   
/*     */   <A> Future<Void> implConnect(SocketAddress paramSocketAddress, A paramA, CompletionHandler<Void, ? super A> paramCompletionHandler) {
/*     */     boolean bool;
/*     */     if (!isOpen()) {
/*     */       ClosedChannelException closedChannelException = new ClosedChannelException();
/*     */       if (paramCompletionHandler == null)
/*     */         return CompletedFuture.withFailure(closedChannelException); 
/*     */       Invoker.invoke(this, paramCompletionHandler, paramA, null, closedChannelException);
/*     */       return null;
/*     */     } 
/*     */     InetSocketAddress inetSocketAddress = Net.checkAddress(paramSocketAddress);
/*     */     SecurityManager securityManager = System.getSecurityManager();
/*     */     if (securityManager != null)
/*     */       securityManager.checkConnect(inetSocketAddress.getAddress().getHostAddress(), inetSocketAddress.getPort()); 
/*     */     synchronized (this.stateLock) {
/*     */       if (this.state == 2)
/*     */         throw new AlreadyConnectedException(); 
/*     */       if (this.state == 1)
/*     */         throw new ConnectionPendingException(); 
/*     */       this.state = 1;
/*     */       this.pendingRemote = paramSocketAddress;
/*     */       bool = (this.localAddress == null) ? true : false;
/*     */     } 
/*     */     Throwable throwable = null;
/*     */     try {
/*     */       begin();
/*     */       if (bool)
/*     */         NetHooks.beforeTcpConnect(this.fd, inetSocketAddress.getAddress(), inetSocketAddress.getPort()); 
/*     */       int i = Net.connect(this.fd, inetSocketAddress.getAddress(), inetSocketAddress.getPort());
/*     */       if (i == -2) {
/*     */         PendingFuture<Object, Object> pendingFuture = null;
/*     */         synchronized (this.updateLock) {
/*     */           if (paramCompletionHandler == null) {
/*     */             pendingFuture = new PendingFuture<>(this, OpType.CONNECT);
/*     */             this.connectFuture = (PendingFuture)pendingFuture;
/*     */           } else {
/*     */             this.connectHandler = (CompletionHandler)paramCompletionHandler;
/*     */             this.connectAttachment = paramA;
/*     */           } 
/*     */           this.connectPending = true;
/*     */           updateEvents();
/*     */         } 
/*     */         return pendingFuture;
/*     */       } 
/*     */       setConnected();
/*     */     } catch (Throwable throwable1) {
/*     */       if (throwable1 instanceof ClosedChannelException)
/*     */         throwable1 = new AsynchronousCloseException(); 
/*     */       throwable = throwable1;
/*     */     } finally {
/*     */       end();
/*     */     } 
/*     */     if (throwable != null)
/*     */       try {
/*     */         close();
/*     */       } catch (Throwable throwable1) {
/*     */         throwable.addSuppressed(throwable1);
/*     */       }  
/*     */     if (paramCompletionHandler == null)
/*     */       return CompletedFuture.withResult(null, throwable); 
/*     */     Invoker.invoke(this, paramCompletionHandler, paramA, null, throwable);
/*     */     return null;
/*     */   }
/*     */   
/*     */   private void finishRead(boolean paramBoolean) {
/*     */     int i = -1;
/*     */     Throwable throwable = null;
/*     */     boolean bool = this.isScatteringRead;
/*     */     CompletionHandler<Number, Object> completionHandler = this.readHandler;
/*     */     Object object = this.readAttachment;
/*     */     PendingFuture<Number, Object> pendingFuture = this.readFuture;
/*     */     Future<?> future = this.readTimer;
/*     */     try {
/*     */       begin();
/*     */       if (bool) {
/*     */         i = (int)IOUtil.read(this.fd, this.readBuffers, nd);
/*     */       } else {
/*     */         i = IOUtil.read(this.fd, this.readBuffer, -1L, nd);
/*     */       } 
/*     */       if (i == -2) {
/*     */         synchronized (this.updateLock) {
/*     */           this.readPending = true;
/*     */         } 
/*     */         return;
/*     */       } 
/*     */       this.readBuffer = null;
/*     */       this.readBuffers = null;
/*     */       this.readAttachment = null;
/*     */       this.readHandler = null;
/*     */       enableReading();
/*     */     } catch (Throwable throwable1) {
/*     */       enableReading();
/*     */       if (throwable1 instanceof ClosedChannelException)
/*     */         throwable1 = new AsynchronousCloseException(); 
/*     */       throwable = throwable1;
/*     */     } finally {
/*     */       if (!(throwable instanceof AsynchronousCloseException))
/*     */         lockAndUpdateEvents(); 
/*     */       end();
/*     */     } 
/*     */     if (future != null)
/*     */       future.cancel(false); 
/*     */     Number number = (Number)((throwable != null) ? null : (bool ? Long.valueOf(i) : Integer.valueOf(i)));
/*     */     if (completionHandler == null) {
/*     */       pendingFuture.setResult(number, throwable);
/*     */     } else if (paramBoolean) {
/*     */       Invoker.invokeUnchecked(completionHandler, object, number, throwable);
/*     */     } else {
/*     */       Invoker.invokeIndirectly(this, completionHandler, object, number, throwable);
/*     */     } 
/*     */   }
/*     */   
/*     */   <V extends Number, A> Future<V> implRead(boolean paramBoolean, ByteBuffer paramByteBuffer, ByteBuffer[] paramArrayOfByteBuffer, long paramLong, TimeUnit paramTimeUnit, A paramA, CompletionHandler<V, ? super A> paramCompletionHandler) {
/*     */     Invoker.GroupAndInvokeCount groupAndInvokeCount = null;
/*     */     boolean bool = false;
/*     */     boolean bool1 = false;
/*     */     if (!disableSynchronousRead)
/*     */       if (paramCompletionHandler == null) {
/*     */         bool1 = true;
/*     */       } else {
/*     */         groupAndInvokeCount = Invoker.getGroupAndInvokeCount();
/*     */         bool = Invoker.mayInvokeDirect(groupAndInvokeCount, this.port);
/*     */         bool1 = (bool || !this.port.isFixedThreadPool()) ? true : false;
/*     */       }  
/*     */     int i = -2;
/*     */     Throwable throwable = null;
/*     */     boolean bool2 = false;
/*     */     try {
/*     */       begin();
/*     */       if (bool1)
/*     */         if (paramBoolean) {
/*     */           i = (int)IOUtil.read(this.fd, paramArrayOfByteBuffer, nd);
/*     */         } else {
/*     */           i = IOUtil.read(this.fd, paramByteBuffer, -1L, nd);
/*     */         }  
/*     */       if (i == -2) {
/*     */         PendingFuture<Object, Object> pendingFuture = null;
/*     */         synchronized (this.updateLock) {
/*     */           this.isScatteringRead = paramBoolean;
/*     */           this.readBuffer = paramByteBuffer;
/*     */           this.readBuffers = paramArrayOfByteBuffer;
/*     */           if (paramCompletionHandler == null) {
/*     */             this.readHandler = null;
/*     */             pendingFuture = new PendingFuture<>(this, OpType.READ);
/*     */             this.readFuture = (PendingFuture)pendingFuture;
/*     */             this.readAttachment = null;
/*     */           } else {
/*     */             this.readHandler = (CompletionHandler)paramCompletionHandler;
/*     */             this.readAttachment = paramA;
/*     */             this.readFuture = null;
/*     */           } 
/*     */           if (paramLong > 0L)
/*     */             this.readTimer = this.port.schedule(this.readTimeoutTask, paramLong, paramTimeUnit); 
/*     */           this.readPending = true;
/*     */           updateEvents();
/*     */         } 
/*     */         bool2 = true;
/*     */         return pendingFuture;
/*     */       } 
/*     */     } catch (Throwable throwable1) {
/*     */       if (throwable1 instanceof ClosedChannelException)
/*     */         throwable1 = new AsynchronousCloseException(); 
/*     */       throwable = throwable1;
/*     */     } finally {
/*     */       if (!bool2)
/*     */         enableReading(); 
/*     */       end();
/*     */     } 
/*     */     V v = (V)((throwable != null) ? null : (paramBoolean ? Long.valueOf(i) : Integer.valueOf(i)));
/*     */     if (paramCompletionHandler != null) {
/*     */       if (bool) {
/*     */         Invoker.invokeDirect(groupAndInvokeCount, paramCompletionHandler, paramA, v, throwable);
/*     */       } else {
/*     */         Invoker.invokeIndirectly(this, paramCompletionHandler, paramA, v, throwable);
/*     */       } 
/*     */       return null;
/*     */     } 
/*     */     return CompletedFuture.withResult(v, throwable);
/*     */   }
/*     */   
/*     */   private void finishWrite(boolean paramBoolean) {
/*     */     int i = -1;
/*     */     Throwable throwable = null;
/*     */     boolean bool = this.isGatheringWrite;
/*     */     CompletionHandler<Number, Object> completionHandler = this.writeHandler;
/*     */     Object object = this.writeAttachment;
/*     */     PendingFuture<Number, Object> pendingFuture = this.writeFuture;
/*     */     Future<?> future = this.writeTimer;
/*     */     try {
/*     */       begin();
/*     */       if (bool) {
/*     */         i = (int)IOUtil.write(this.fd, this.writeBuffers, nd);
/*     */       } else {
/*     */         i = IOUtil.write(this.fd, this.writeBuffer, -1L, nd);
/*     */       } 
/*     */       if (i == -2) {
/*     */         synchronized (this.updateLock) {
/*     */           this.writePending = true;
/*     */         } 
/*     */         return;
/*     */       } 
/*     */       this.writeBuffer = null;
/*     */       this.writeBuffers = null;
/*     */       this.writeAttachment = null;
/*     */       this.writeHandler = null;
/*     */       enableWriting();
/*     */     } catch (Throwable throwable1) {
/*     */       enableWriting();
/*     */       if (throwable1 instanceof ClosedChannelException)
/*     */         throwable1 = new AsynchronousCloseException(); 
/*     */       throwable = throwable1;
/*     */     } finally {
/*     */       if (!(throwable instanceof AsynchronousCloseException))
/*     */         lockAndUpdateEvents(); 
/*     */       end();
/*     */     } 
/*     */     if (future != null)
/*     */       future.cancel(false); 
/*     */     Number number = (Number)((throwable != null) ? null : (bool ? Long.valueOf(i) : Integer.valueOf(i)));
/*     */     if (completionHandler == null) {
/*     */       pendingFuture.setResult(number, throwable);
/*     */     } else if (paramBoolean) {
/*     */       Invoker.invokeUnchecked(completionHandler, object, number, throwable);
/*     */     } else {
/*     */       Invoker.invokeIndirectly(this, completionHandler, object, number, throwable);
/*     */     } 
/*     */   }
/*     */   
/*     */   <V extends Number, A> Future<V> implWrite(boolean paramBoolean, ByteBuffer paramByteBuffer, ByteBuffer[] paramArrayOfByteBuffer, long paramLong, TimeUnit paramTimeUnit, A paramA, CompletionHandler<V, ? super A> paramCompletionHandler) {
/*     */     Invoker.GroupAndInvokeCount groupAndInvokeCount = Invoker.getGroupAndInvokeCount();
/*     */     boolean bool = Invoker.mayInvokeDirect(groupAndInvokeCount, this.port);
/*     */     boolean bool1 = (paramCompletionHandler == null || bool || !this.port.isFixedThreadPool()) ? true : false;
/*     */     int i = -2;
/*     */     Throwable throwable = null;
/*     */     boolean bool2 = false;
/*     */     try {
/*     */       begin();
/*     */       if (bool1)
/*     */         if (paramBoolean) {
/*     */           i = (int)IOUtil.write(this.fd, paramArrayOfByteBuffer, nd);
/*     */         } else {
/*     */           i = IOUtil.write(this.fd, paramByteBuffer, -1L, nd);
/*     */         }  
/*     */       if (i == -2) {
/*     */         PendingFuture<Object, Object> pendingFuture = null;
/*     */         synchronized (this.updateLock) {
/*     */           this.isGatheringWrite = paramBoolean;
/*     */           this.writeBuffer = paramByteBuffer;
/*     */           this.writeBuffers = paramArrayOfByteBuffer;
/*     */           if (paramCompletionHandler == null) {
/*     */             this.writeHandler = null;
/*     */             pendingFuture = new PendingFuture<>(this, OpType.WRITE);
/*     */             this.writeFuture = (PendingFuture)pendingFuture;
/*     */             this.writeAttachment = null;
/*     */           } else {
/*     */             this.writeHandler = (CompletionHandler)paramCompletionHandler;
/*     */             this.writeAttachment = paramA;
/*     */             this.writeFuture = null;
/*     */           } 
/*     */           if (paramLong > 0L)
/*     */             this.writeTimer = this.port.schedule(this.writeTimeoutTask, paramLong, paramTimeUnit); 
/*     */           this.writePending = true;
/*     */           updateEvents();
/*     */         } 
/*     */         bool2 = true;
/*     */         return pendingFuture;
/*     */       } 
/*     */     } catch (Throwable throwable1) {
/*     */       if (throwable1 instanceof ClosedChannelException)
/*     */         throwable1 = new AsynchronousCloseException(); 
/*     */       throwable = throwable1;
/*     */     } finally {
/*     */       if (!bool2)
/*     */         enableWriting(); 
/*     */       end();
/*     */     } 
/*     */     V v = (V)((throwable != null) ? null : (paramBoolean ? Long.valueOf(i) : Integer.valueOf(i)));
/*     */     if (paramCompletionHandler != null) {
/*     */       if (bool) {
/*     */         Invoker.invokeDirect(groupAndInvokeCount, paramCompletionHandler, paramA, v, throwable);
/*     */       } else {
/*     */         Invoker.invokeIndirectly(this, paramCompletionHandler, paramA, v, throwable);
/*     */       } 
/*     */       return null;
/*     */     } 
/*     */     return CompletedFuture.withResult(v, throwable);
/*     */   }
/*     */   
/*     */   private static native void checkConnect(int paramInt) throws IOException;
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/nio/ch/UnixAsynchronousSocketChannelImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */