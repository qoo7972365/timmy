/*     */ package sun.nio.ch;
/*     */ 
/*     */ import java.io.FileDescriptor;
/*     */ import java.io.IOException;
/*     */ import java.net.InetSocketAddress;
/*     */ import java.net.SocketAddress;
/*     */ import java.net.SocketOption;
/*     */ import java.net.StandardSocketOptions;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.channels.AlreadyBoundException;
/*     */ import java.nio.channels.AsynchronousSocketChannel;
/*     */ import java.nio.channels.ClosedChannelException;
/*     */ import java.nio.channels.CompletionHandler;
/*     */ import java.nio.channels.ConnectionPendingException;
/*     */ import java.nio.channels.NetworkChannel;
/*     */ import java.nio.channels.NotYetConnectedException;
/*     */ import java.nio.channels.ReadPendingException;
/*     */ import java.nio.channels.WritePendingException;
/*     */ import java.util.Collections;
/*     */ import java.util.HashSet;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.Future;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import java.util.concurrent.locks.ReadWriteLock;
/*     */ import java.util.concurrent.locks.ReentrantReadWriteLock;
/*     */ import jdk.net.ExtendedSocketOptions;
/*     */ import sun.net.ExtendedOptionsImpl;
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
/*     */ abstract class AsynchronousSocketChannelImpl
/*     */   extends AsynchronousSocketChannel
/*     */   implements Cancellable, Groupable
/*     */ {
/*     */   protected final FileDescriptor fd;
/*  55 */   protected final Object stateLock = new Object();
/*     */   
/*  57 */   protected volatile InetSocketAddress localAddress = null;
/*  58 */   protected volatile InetSocketAddress remoteAddress = null;
/*     */   
/*     */   static final int ST_UNINITIALIZED = -1;
/*     */   
/*     */   static final int ST_UNCONNECTED = 0;
/*     */   static final int ST_PENDING = 1;
/*     */   static final int ST_CONNECTED = 2;
/*  65 */   protected volatile int state = -1;
/*     */ 
/*     */   
/*  68 */   private final Object readLock = new Object();
/*     */   
/*     */   private boolean reading;
/*     */   
/*     */   private boolean readShutdown;
/*     */   private boolean readKilled;
/*  74 */   private final Object writeLock = new Object();
/*     */   
/*     */   private boolean writing;
/*     */   
/*     */   private boolean writeShutdown;
/*     */   private boolean writeKilled;
/*  80 */   private final ReadWriteLock closeLock = new ReentrantReadWriteLock();
/*     */ 
/*     */   
/*     */   private volatile boolean open = true;
/*     */   
/*     */   private boolean isReuseAddress;
/*     */ 
/*     */   
/*     */   AsynchronousSocketChannelImpl(AsynchronousChannelGroupImpl paramAsynchronousChannelGroupImpl) throws IOException {
/*  89 */     super(paramAsynchronousChannelGroupImpl.provider());
/*  90 */     this.fd = Net.socket(true);
/*  91 */     this.state = 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   AsynchronousSocketChannelImpl(AsynchronousChannelGroupImpl paramAsynchronousChannelGroupImpl, FileDescriptor paramFileDescriptor, InetSocketAddress paramInetSocketAddress) throws IOException {
/* 100 */     super(paramAsynchronousChannelGroupImpl.provider());
/* 101 */     this.fd = paramFileDescriptor;
/* 102 */     this.state = 2;
/* 103 */     this.localAddress = Net.localAddress(paramFileDescriptor);
/* 104 */     this.remoteAddress = paramInetSocketAddress;
/*     */   }
/*     */ 
/*     */   
/*     */   public final boolean isOpen() {
/* 109 */     return this.open;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   final void begin() throws IOException {
/* 116 */     this.closeLock.readLock().lock();
/* 117 */     if (!isOpen()) {
/* 118 */       throw new ClosedChannelException();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   final void end() {
/* 125 */     this.closeLock.readLock().unlock();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void close() throws IOException {
/* 136 */     this.closeLock.writeLock().lock();
/*     */     try {
/* 138 */       if (!this.open)
/*     */         return; 
/* 140 */       this.open = false;
/*     */     } finally {
/* 142 */       this.closeLock.writeLock().unlock();
/*     */     } 
/* 144 */     implClose();
/*     */   }
/*     */   
/*     */   final void enableReading(boolean paramBoolean) {
/* 148 */     synchronized (this.readLock) {
/* 149 */       this.reading = false;
/* 150 */       if (paramBoolean)
/* 151 */         this.readKilled = true; 
/*     */     } 
/*     */   }
/*     */   
/*     */   final void enableReading() {
/* 156 */     enableReading(false);
/*     */   }
/*     */   
/*     */   final void enableWriting(boolean paramBoolean) {
/* 160 */     synchronized (this.writeLock) {
/* 161 */       this.writing = false;
/* 162 */       if (paramBoolean)
/* 163 */         this.writeKilled = true; 
/*     */     } 
/*     */   }
/*     */   
/*     */   final void enableWriting() {
/* 168 */     enableWriting(false);
/*     */   }
/*     */   
/*     */   final void killReading() {
/* 172 */     synchronized (this.readLock) {
/* 173 */       this.readKilled = true;
/*     */     } 
/*     */   }
/*     */   
/*     */   final void killWriting() {
/* 178 */     synchronized (this.writeLock) {
/* 179 */       this.writeKilled = true;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   final void killConnect() {
/* 186 */     killReading();
/* 187 */     killWriting();
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
/*     */   public final Future<Void> connect(SocketAddress paramSocketAddress) {
/* 199 */     return implConnect(paramSocketAddress, null, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final <A> void connect(SocketAddress paramSocketAddress, A paramA, CompletionHandler<Void, ? super A> paramCompletionHandler) {
/* 207 */     if (paramCompletionHandler == null)
/* 208 */       throw new NullPointerException("'handler' is null"); 
/* 209 */     implConnect(paramSocketAddress, paramA, paramCompletionHandler);
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
/*     */   private <V extends Number, A> Future<V> read(boolean paramBoolean, ByteBuffer paramByteBuffer, ByteBuffer[] paramArrayOfByteBuffer, long paramLong, TimeUnit paramTimeUnit, A paramA, CompletionHandler<V, ? super A> paramCompletionHandler) {
/* 232 */     if (!isOpen()) {
/* 233 */       ClosedChannelException closedChannelException = new ClosedChannelException();
/* 234 */       if (paramCompletionHandler == null)
/* 235 */         return CompletedFuture.withFailure(closedChannelException); 
/* 236 */       Invoker.invoke(this, paramCompletionHandler, paramA, null, closedChannelException);
/* 237 */       return null;
/*     */     } 
/*     */     
/* 240 */     if (this.remoteAddress == null) {
/* 241 */       throw new NotYetConnectedException();
/*     */     }
/* 243 */     boolean bool1 = (paramBoolean || paramByteBuffer.hasRemaining()) ? true : false;
/* 244 */     boolean bool2 = false;
/*     */ 
/*     */     
/* 247 */     synchronized (this.readLock) {
/* 248 */       if (this.readKilled)
/* 249 */         throw new IllegalStateException("Reading not allowed due to timeout or cancellation"); 
/* 250 */       if (this.reading)
/* 251 */         throw new ReadPendingException(); 
/* 252 */       if (this.readShutdown) {
/* 253 */         bool2 = true;
/*     */       }
/* 255 */       else if (bool1) {
/* 256 */         this.reading = true;
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 263 */     if (bool2 || !bool1) {
/*     */       Integer integer;
/* 265 */       if (paramBoolean) {
/* 266 */         Long long_ = bool2 ? Long.valueOf(-1L) : Long.valueOf(0L);
/*     */       } else {
/* 268 */         integer = Integer.valueOf(bool2 ? -1 : 0);
/*     */       } 
/* 270 */       if (paramCompletionHandler == null)
/* 271 */         return CompletedFuture.withResult((V)integer); 
/* 272 */       Invoker.invoke(this, paramCompletionHandler, paramA, (V)integer, null);
/* 273 */       return null;
/*     */     } 
/*     */     
/* 276 */     return implRead(paramBoolean, paramByteBuffer, paramArrayOfByteBuffer, paramLong, paramTimeUnit, paramA, paramCompletionHandler);
/*     */   }
/*     */ 
/*     */   
/*     */   public final Future<Integer> read(ByteBuffer paramByteBuffer) {
/* 281 */     if (paramByteBuffer.isReadOnly())
/* 282 */       throw new IllegalArgumentException("Read-only buffer"); 
/* 283 */     return read(false, paramByteBuffer, (ByteBuffer[])null, 0L, TimeUnit.MILLISECONDS, (Object)null, (CompletionHandler<Integer, ?>)null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final <A> void read(ByteBuffer paramByteBuffer, long paramLong, TimeUnit paramTimeUnit, A paramA, CompletionHandler<Integer, ? super A> paramCompletionHandler) {
/* 293 */     if (paramCompletionHandler == null)
/* 294 */       throw new NullPointerException("'handler' is null"); 
/* 295 */     if (paramByteBuffer.isReadOnly())
/* 296 */       throw new IllegalArgumentException("Read-only buffer"); 
/* 297 */     read(false, paramByteBuffer, (ByteBuffer[])null, paramLong, paramTimeUnit, paramA, paramCompletionHandler);
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
/*     */   public final <A> void read(ByteBuffer[] paramArrayOfByteBuffer, int paramInt1, int paramInt2, long paramLong, TimeUnit paramTimeUnit, A paramA, CompletionHandler<Long, ? super A> paramCompletionHandler) {
/* 309 */     if (paramCompletionHandler == null)
/* 310 */       throw new NullPointerException("'handler' is null"); 
/* 311 */     if (paramInt1 < 0 || paramInt2 < 0 || paramInt1 > paramArrayOfByteBuffer.length - paramInt2)
/* 312 */       throw new IndexOutOfBoundsException(); 
/* 313 */     ByteBuffer[] arrayOfByteBuffer = Util.subsequence(paramArrayOfByteBuffer, paramInt1, paramInt2);
/* 314 */     for (byte b = 0; b < arrayOfByteBuffer.length; b++) {
/* 315 */       if (arrayOfByteBuffer[b].isReadOnly())
/* 316 */         throw new IllegalArgumentException("Read-only buffer"); 
/*     */     } 
/* 318 */     read(true, (ByteBuffer)null, arrayOfByteBuffer, paramLong, paramTimeUnit, paramA, paramCompletionHandler);
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
/*     */   private <V extends Number, A> Future<V> write(boolean paramBoolean, ByteBuffer paramByteBuffer, ByteBuffer[] paramArrayOfByteBuffer, long paramLong, TimeUnit paramTimeUnit, A paramA, CompletionHandler<V, ? super A> paramCompletionHandler) {
/* 341 */     boolean bool1 = (paramBoolean || paramByteBuffer.hasRemaining()) ? true : false;
/*     */     
/* 343 */     boolean bool2 = false;
/* 344 */     if (isOpen()) {
/* 345 */       if (this.remoteAddress == null) {
/* 346 */         throw new NotYetConnectedException();
/*     */       }
/* 348 */       synchronized (this.writeLock) {
/* 349 */         if (this.writeKilled)
/* 350 */           throw new IllegalStateException("Writing not allowed due to timeout or cancellation"); 
/* 351 */         if (this.writing)
/* 352 */           throw new WritePendingException(); 
/* 353 */         if (this.writeShutdown) {
/* 354 */           bool2 = true;
/*     */         }
/* 356 */         else if (bool1) {
/* 357 */           this.writing = true;
/*     */         } 
/*     */       } 
/*     */     } else {
/* 361 */       bool2 = true;
/*     */     } 
/*     */ 
/*     */     
/* 365 */     if (bool2) {
/* 366 */       ClosedChannelException closedChannelException = new ClosedChannelException();
/* 367 */       if (paramCompletionHandler == null)
/* 368 */         return CompletedFuture.withFailure(closedChannelException); 
/* 369 */       Invoker.invoke(this, paramCompletionHandler, paramA, null, closedChannelException);
/* 370 */       return null;
/*     */     } 
/*     */ 
/*     */     
/* 374 */     if (!bool1) {
/* 375 */       V v = (V)(paramBoolean ? Long.valueOf(0L) : Integer.valueOf(0));
/* 376 */       if (paramCompletionHandler == null)
/* 377 */         return CompletedFuture.withResult(v); 
/* 378 */       Invoker.invoke(this, paramCompletionHandler, paramA, v, null);
/* 379 */       return null;
/*     */     } 
/*     */     
/* 382 */     return implWrite(paramBoolean, paramByteBuffer, paramArrayOfByteBuffer, paramLong, paramTimeUnit, paramA, paramCompletionHandler);
/*     */   }
/*     */ 
/*     */   
/*     */   public final Future<Integer> write(ByteBuffer paramByteBuffer) {
/* 387 */     return write(false, paramByteBuffer, (ByteBuffer[])null, 0L, TimeUnit.MILLISECONDS, (Object)null, (CompletionHandler<Integer, ?>)null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final <A> void write(ByteBuffer paramByteBuffer, long paramLong, TimeUnit paramTimeUnit, A paramA, CompletionHandler<Integer, ? super A> paramCompletionHandler) {
/* 397 */     if (paramCompletionHandler == null)
/* 398 */       throw new NullPointerException("'handler' is null"); 
/* 399 */     write(false, paramByteBuffer, (ByteBuffer[])null, paramLong, paramTimeUnit, paramA, paramCompletionHandler);
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
/*     */   public final <A> void write(ByteBuffer[] paramArrayOfByteBuffer, int paramInt1, int paramInt2, long paramLong, TimeUnit paramTimeUnit, A paramA, CompletionHandler<Long, ? super A> paramCompletionHandler) {
/* 411 */     if (paramCompletionHandler == null)
/* 412 */       throw new NullPointerException("'handler' is null"); 
/* 413 */     if (paramInt1 < 0 || paramInt2 < 0 || paramInt1 > paramArrayOfByteBuffer.length - paramInt2)
/* 414 */       throw new IndexOutOfBoundsException(); 
/* 415 */     paramArrayOfByteBuffer = Util.subsequence(paramArrayOfByteBuffer, paramInt1, paramInt2);
/* 416 */     write(true, (ByteBuffer)null, paramArrayOfByteBuffer, paramLong, paramTimeUnit, paramA, paramCompletionHandler);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final AsynchronousSocketChannel bind(SocketAddress paramSocketAddress) throws IOException {
/*     */     try {
/* 424 */       begin();
/* 425 */       synchronized (this.stateLock) {
/* 426 */         if (this.state == 1)
/* 427 */           throw new ConnectionPendingException(); 
/* 428 */         if (this.localAddress != null) {
/* 429 */           throw new AlreadyBoundException();
/*     */         }
/* 431 */         InetSocketAddress inetSocketAddress = (paramSocketAddress == null) ? new InetSocketAddress(0) : Net.checkAddress(paramSocketAddress);
/* 432 */         SecurityManager securityManager = System.getSecurityManager();
/* 433 */         if (securityManager != null) {
/* 434 */           securityManager.checkListen(inetSocketAddress.getPort());
/*     */         }
/* 436 */         NetHooks.beforeTcpBind(this.fd, inetSocketAddress.getAddress(), inetSocketAddress.getPort());
/* 437 */         Net.bind(this.fd, inetSocketAddress.getAddress(), inetSocketAddress.getPort());
/* 438 */         this.localAddress = Net.localAddress(this.fd);
/*     */       } 
/*     */     } finally {
/* 441 */       end();
/*     */     } 
/* 443 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public final SocketAddress getLocalAddress() throws IOException {
/* 448 */     if (!isOpen())
/* 449 */       throw new ClosedChannelException(); 
/* 450 */     return Net.getRevealedLocalAddress(this.localAddress);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final <T> AsynchronousSocketChannel setOption(SocketOption<T> paramSocketOption, T paramT) throws IOException {
/* 457 */     if (paramSocketOption == null)
/* 458 */       throw new NullPointerException(); 
/* 459 */     if (!supportedOptions().contains(paramSocketOption)) {
/* 460 */       throw new UnsupportedOperationException("'" + paramSocketOption + "' not supported");
/*     */     }
/*     */     try {
/* 463 */       begin();
/* 464 */       if (this.writeShutdown)
/* 465 */         throw new IOException("Connection has been shutdown for writing"); 
/* 466 */       if (paramSocketOption == StandardSocketOptions.SO_REUSEADDR && 
/* 467 */         Net.useExclusiveBind()) {
/*     */ 
/*     */         
/* 470 */         this.isReuseAddress = ((Boolean)paramT).booleanValue();
/*     */       } else {
/* 472 */         Net.setSocketOption(this.fd, Net.UNSPEC, paramSocketOption, paramT);
/*     */       } 
/* 474 */       return this;
/*     */     } finally {
/* 476 */       end();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public final <T> T getOption(SocketOption<T> paramSocketOption) throws IOException {
/* 483 */     if (paramSocketOption == null)
/* 484 */       throw new NullPointerException(); 
/* 485 */     if (!supportedOptions().contains(paramSocketOption)) {
/* 486 */       throw new UnsupportedOperationException("'" + paramSocketOption + "' not supported");
/*     */     }
/*     */     try {
/* 489 */       begin();
/* 490 */       if (paramSocketOption == StandardSocketOptions.SO_REUSEADDR && 
/* 491 */         Net.useExclusiveBind())
/*     */       {
/*     */         
/* 494 */         return (T)Boolean.valueOf(this.isReuseAddress);
/*     */       }
/* 496 */       return (T)Net.getSocketOption(this.fd, Net.UNSPEC, paramSocketOption);
/*     */     } finally {
/* 498 */       end();
/*     */     } 
/*     */   }
/*     */   
/*     */   private static class DefaultOptionsHolder {
/* 503 */     static final Set<SocketOption<?>> defaultOptions = defaultOptions();
/*     */     
/*     */     private static Set<SocketOption<?>> defaultOptions() {
/* 506 */       HashSet<SocketOption<Integer>> hashSet = new HashSet(5);
/* 507 */       hashSet.add(StandardSocketOptions.SO_SNDBUF);
/* 508 */       hashSet.add(StandardSocketOptions.SO_RCVBUF);
/* 509 */       hashSet.add(StandardSocketOptions.SO_KEEPALIVE);
/* 510 */       hashSet.add(StandardSocketOptions.SO_REUSEADDR);
/* 511 */       hashSet.add(StandardSocketOptions.TCP_NODELAY);
/* 512 */       if (ExtendedOptionsImpl.flowSupported()) {
/* 513 */         hashSet.add(ExtendedSocketOptions.SO_FLOW_SLA);
/*     */       }
/* 515 */       return Collections.unmodifiableSet(hashSet);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public final Set<SocketOption<?>> supportedOptions() {
/* 521 */     return DefaultOptionsHolder.defaultOptions;
/*     */   }
/*     */ 
/*     */   
/*     */   public final SocketAddress getRemoteAddress() throws IOException {
/* 526 */     if (!isOpen())
/* 527 */       throw new ClosedChannelException(); 
/* 528 */     return this.remoteAddress;
/*     */   }
/*     */ 
/*     */   
/*     */   public final AsynchronousSocketChannel shutdownInput() throws IOException {
/*     */     try {
/* 534 */       begin();
/* 535 */       if (this.remoteAddress == null)
/* 536 */         throw new NotYetConnectedException(); 
/* 537 */       synchronized (this.readLock) {
/* 538 */         if (!this.readShutdown) {
/* 539 */           Net.shutdown(this.fd, 0);
/* 540 */           this.readShutdown = true;
/*     */         } 
/*     */       } 
/*     */     } finally {
/* 544 */       end();
/*     */     } 
/* 546 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public final AsynchronousSocketChannel shutdownOutput() throws IOException {
/*     */     try {
/* 552 */       begin();
/* 553 */       if (this.remoteAddress == null)
/* 554 */         throw new NotYetConnectedException(); 
/* 555 */       synchronized (this.writeLock) {
/* 556 */         if (!this.writeShutdown) {
/* 557 */           Net.shutdown(this.fd, 1);
/* 558 */           this.writeShutdown = true;
/*     */         } 
/*     */       } 
/*     */     } finally {
/* 562 */       end();
/*     */     } 
/* 564 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public final String toString() {
/* 569 */     StringBuilder stringBuilder = new StringBuilder();
/* 570 */     stringBuilder.append(getClass().getName());
/* 571 */     stringBuilder.append('[');
/* 572 */     synchronized (this.stateLock) {
/* 573 */       if (!isOpen()) {
/* 574 */         stringBuilder.append("closed");
/*     */       } else {
/* 576 */         switch (this.state) {
/*     */           case 0:
/* 578 */             stringBuilder.append("unconnected");
/*     */             break;
/*     */           case 1:
/* 581 */             stringBuilder.append("connection-pending");
/*     */             break;
/*     */           case 2:
/* 584 */             stringBuilder.append("connected");
/* 585 */             if (this.readShutdown)
/* 586 */               stringBuilder.append(" ishut"); 
/* 587 */             if (this.writeShutdown)
/* 588 */               stringBuilder.append(" oshut"); 
/*     */             break;
/*     */         } 
/* 591 */         if (this.localAddress != null) {
/* 592 */           stringBuilder.append(" local=");
/* 593 */           stringBuilder.append(
/* 594 */               Net.getRevealedLocalAddressAsString(this.localAddress));
/*     */         } 
/* 596 */         if (this.remoteAddress != null) {
/* 597 */           stringBuilder.append(" remote=");
/* 598 */           stringBuilder.append(this.remoteAddress.toString());
/*     */         } 
/*     */       } 
/*     */     } 
/* 602 */     stringBuilder.append(']');
/* 603 */     return stringBuilder.toString();
/*     */   }
/*     */   
/*     */   abstract void implClose() throws IOException;
/*     */   
/*     */   abstract <A> Future<Void> implConnect(SocketAddress paramSocketAddress, A paramA, CompletionHandler<Void, ? super A> paramCompletionHandler);
/*     */   
/*     */   abstract <V extends Number, A> Future<V> implRead(boolean paramBoolean, ByteBuffer paramByteBuffer, ByteBuffer[] paramArrayOfByteBuffer, long paramLong, TimeUnit paramTimeUnit, A paramA, CompletionHandler<V, ? super A> paramCompletionHandler);
/*     */   
/*     */   abstract <V extends Number, A> Future<V> implWrite(boolean paramBoolean, ByteBuffer paramByteBuffer, ByteBuffer[] paramArrayOfByteBuffer, long paramLong, TimeUnit paramTimeUnit, A paramA, CompletionHandler<V, ? super A> paramCompletionHandler);
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/nio/ch/AsynchronousSocketChannelImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */