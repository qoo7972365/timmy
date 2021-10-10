/*      */ package sun.nio.ch;
/*      */ 
/*      */ import java.io.Closeable;
/*      */ import java.io.FileDescriptor;
/*      */ import java.io.IOException;
/*      */ import java.nio.ByteBuffer;
/*      */ import java.nio.MappedByteBuffer;
/*      */ import java.nio.channels.ClosedByInterruptException;
/*      */ import java.nio.channels.ClosedChannelException;
/*      */ import java.nio.channels.FileChannel;
/*      */ import java.nio.channels.FileLock;
/*      */ import java.nio.channels.FileLockInterruptionException;
/*      */ import java.nio.channels.NonReadableChannelException;
/*      */ import java.nio.channels.NonWritableChannelException;
/*      */ import java.nio.channels.OverlappingFileLockException;
/*      */ import java.nio.channels.ReadableByteChannel;
/*      */ import java.nio.channels.SeekableByteChannel;
/*      */ import java.nio.channels.SelectableChannel;
/*      */ import java.nio.channels.WritableByteChannel;
/*      */ import java.security.AccessController;
/*      */ import java.util.ArrayList;
/*      */ import java.util.List;
/*      */ import sun.misc.Cleaner;
/*      */ import sun.misc.JavaNioAccess;
/*      */ import sun.security.action.GetPropertyAction;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class FileChannelImpl
/*      */   extends FileChannel
/*      */ {
/*      */   private static final long allocationGranularity;
/*      */   private final FileDispatcher nd;
/*      */   private final FileDescriptor fd;
/*      */   private final boolean writable;
/*      */   private final boolean readable;
/*      */   private final boolean append;
/*      */   private final Object parent;
/*      */   private final String path;
/*   75 */   private final NativeThreadSet threads = new NativeThreadSet(2);
/*      */ 
/*      */   
/*   78 */   private final Object positionLock = new Object();
/*      */ 
/*      */ 
/*      */   
/*      */   private FileChannelImpl(FileDescriptor paramFileDescriptor, String paramString, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, Object paramObject) {
/*   83 */     this.fd = paramFileDescriptor;
/*   84 */     this.readable = paramBoolean1;
/*   85 */     this.writable = paramBoolean2;
/*   86 */     this.append = paramBoolean3;
/*   87 */     this.parent = paramObject;
/*   88 */     this.path = paramString;
/*   89 */     this.nd = new FileDispatcherImpl(paramBoolean3);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static FileChannel open(FileDescriptor paramFileDescriptor, String paramString, boolean paramBoolean1, boolean paramBoolean2, Object paramObject) {
/*   97 */     return new FileChannelImpl(paramFileDescriptor, paramString, paramBoolean1, paramBoolean2, false, paramObject);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static FileChannel open(FileDescriptor paramFileDescriptor, String paramString, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, Object paramObject) {
/*  105 */     return new FileChannelImpl(paramFileDescriptor, paramString, paramBoolean1, paramBoolean2, paramBoolean3, paramObject);
/*      */   }
/*      */   
/*      */   private void ensureOpen() throws IOException {
/*  109 */     if (!isOpen()) {
/*  110 */       throw new ClosedChannelException();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void implCloseChannel() throws IOException {
/*  118 */     if (this.fileLockTable != null) {
/*  119 */       for (FileLock fileLock : this.fileLockTable.removeAll()) {
/*  120 */         synchronized (fileLock) {
/*  121 */           if (fileLock.isValid()) {
/*  122 */             this.nd.release(this.fd, fileLock.position(), fileLock.size());
/*  123 */             ((FileLockImpl)fileLock).invalidate();
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*  130 */     this.threads.signalAndWait();
/*      */     
/*  132 */     if (this.parent != null) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  139 */       ((Closeable)this.parent).close();
/*      */     } else {
/*  141 */       this.nd.close(this.fd);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public int read(ByteBuffer paramByteBuffer) throws IOException {
/*  147 */     ensureOpen();
/*  148 */     if (!this.readable)
/*  149 */       throw new NonReadableChannelException(); 
/*  150 */     synchronized (this.positionLock) {
/*  151 */       int i = 0;
/*  152 */       int j = -1;
/*      */       try {
/*  154 */         begin();
/*  155 */         j = this.threads.add();
/*  156 */         if (!isOpen())
/*  157 */           return 0; 
/*      */         do {
/*  159 */           i = IOUtil.read(this.fd, paramByteBuffer, -1L, this.nd);
/*  160 */         } while (i == -3 && isOpen());
/*  161 */         return IOStatus.normalize(i);
/*      */       } finally {
/*  163 */         this.threads.remove(j);
/*  164 */         end((i > 0));
/*  165 */         assert IOStatus.check(i);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public long read(ByteBuffer[] paramArrayOfByteBuffer, int paramInt1, int paramInt2) throws IOException {
/*  173 */     if (paramInt1 < 0 || paramInt2 < 0 || paramInt1 > paramArrayOfByteBuffer.length - paramInt2)
/*  174 */       throw new IndexOutOfBoundsException(); 
/*  175 */     ensureOpen();
/*  176 */     if (!this.readable)
/*  177 */       throw new NonReadableChannelException(); 
/*  178 */     synchronized (this.positionLock) {
/*  179 */       long l = 0L;
/*  180 */       int i = -1;
/*      */       try {
/*  182 */         begin();
/*  183 */         i = this.threads.add();
/*  184 */         if (!isOpen())
/*  185 */           return 0L; 
/*      */         do {
/*  187 */           l = IOUtil.read(this.fd, paramArrayOfByteBuffer, paramInt1, paramInt2, this.nd);
/*  188 */         } while (l == -3L && isOpen());
/*  189 */         return IOStatus.normalize(l);
/*      */       } finally {
/*  191 */         this.threads.remove(i);
/*  192 */         end((l > 0L));
/*  193 */         assert IOStatus.check(l);
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   public int write(ByteBuffer paramByteBuffer) throws IOException {
/*  199 */     ensureOpen();
/*  200 */     if (!this.writable)
/*  201 */       throw new NonWritableChannelException(); 
/*  202 */     synchronized (this.positionLock) {
/*  203 */       int i = 0;
/*  204 */       int j = -1;
/*      */       try {
/*  206 */         begin();
/*  207 */         j = this.threads.add();
/*  208 */         if (!isOpen())
/*  209 */           return 0; 
/*      */         do {
/*  211 */           i = IOUtil.write(this.fd, paramByteBuffer, -1L, this.nd);
/*  212 */         } while (i == -3 && isOpen());
/*  213 */         return IOStatus.normalize(i);
/*      */       } finally {
/*  215 */         this.threads.remove(j);
/*  216 */         end((i > 0));
/*  217 */         assert IOStatus.check(i);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public long write(ByteBuffer[] paramArrayOfByteBuffer, int paramInt1, int paramInt2) throws IOException {
/*  225 */     if (paramInt1 < 0 || paramInt2 < 0 || paramInt1 > paramArrayOfByteBuffer.length - paramInt2)
/*  226 */       throw new IndexOutOfBoundsException(); 
/*  227 */     ensureOpen();
/*  228 */     if (!this.writable)
/*  229 */       throw new NonWritableChannelException(); 
/*  230 */     synchronized (this.positionLock) {
/*  231 */       long l = 0L;
/*  232 */       int i = -1;
/*      */       try {
/*  234 */         begin();
/*  235 */         i = this.threads.add();
/*  236 */         if (!isOpen())
/*  237 */           return 0L; 
/*      */         do {
/*  239 */           l = IOUtil.write(this.fd, paramArrayOfByteBuffer, paramInt1, paramInt2, this.nd);
/*  240 */         } while (l == -3L && isOpen());
/*  241 */         return IOStatus.normalize(l);
/*      */       } finally {
/*  243 */         this.threads.remove(i);
/*  244 */         end((l > 0L));
/*  245 */         assert IOStatus.check(l);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public long position() throws IOException {
/*  253 */     ensureOpen();
/*  254 */     synchronized (this.positionLock) {
/*  255 */       long l = -1L;
/*  256 */       int i = -1;
/*      */       try {
/*  258 */         begin();
/*  259 */         i = this.threads.add();
/*  260 */         if (!isOpen()) {
/*  261 */           return 0L;
/*      */         }
/*      */         do {
/*  264 */           l = this.append ? this.nd.size(this.fd) : this.nd.seek(this.fd, -1L);
/*  265 */         } while (l == -3L && isOpen());
/*  266 */         return IOStatus.normalize(l);
/*      */       } finally {
/*  268 */         this.threads.remove(i);
/*  269 */         end((l > -1L));
/*  270 */         assert IOStatus.check(l);
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   public FileChannel position(long paramLong) throws IOException {
/*  276 */     ensureOpen();
/*  277 */     if (paramLong < 0L)
/*  278 */       throw new IllegalArgumentException(); 
/*  279 */     synchronized (this.positionLock) {
/*  280 */       long l = -1L;
/*  281 */       int i = -1;
/*      */       try {
/*  283 */         begin();
/*  284 */         i = this.threads.add();
/*  285 */         if (!isOpen())
/*  286 */           return null; 
/*      */         do {
/*  288 */           l = this.nd.seek(this.fd, paramLong);
/*  289 */         } while (l == -3L && isOpen());
/*  290 */         return this;
/*      */       } finally {
/*  292 */         this.threads.remove(i);
/*  293 */         end((l > -1L));
/*  294 */         assert IOStatus.check(l);
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   public long size() throws IOException {
/*  300 */     ensureOpen();
/*  301 */     synchronized (this.positionLock) {
/*  302 */       long l = -1L;
/*  303 */       int i = -1;
/*      */       try {
/*  305 */         begin();
/*  306 */         i = this.threads.add();
/*  307 */         if (!isOpen())
/*  308 */           return -1L; 
/*      */         do {
/*  310 */           l = this.nd.size(this.fd);
/*  311 */         } while (l == -3L && isOpen());
/*  312 */         return IOStatus.normalize(l);
/*      */       } finally {
/*  314 */         this.threads.remove(i);
/*  315 */         end((l > -1L));
/*  316 */         assert IOStatus.check(l);
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   public FileChannel truncate(long paramLong) throws IOException {
/*  322 */     ensureOpen();
/*  323 */     if (paramLong < 0L)
/*  324 */       throw new IllegalArgumentException("Negative size"); 
/*  325 */     if (!this.writable)
/*  326 */       throw new NonWritableChannelException(); 
/*  327 */     synchronized (this.positionLock) {
/*  328 */       int i = -1;
/*  329 */       long l1 = -1L;
/*  330 */       int j = -1;
/*  331 */       long l2 = -1L; try {
/*      */         long l;
/*  333 */         begin();
/*  334 */         j = this.threads.add();
/*  335 */         if (!isOpen()) {
/*  336 */           return null;
/*      */         }
/*      */ 
/*      */         
/*      */         do {
/*  341 */           l = this.nd.size(this.fd);
/*  342 */         } while (l == -3L && isOpen());
/*  343 */         if (!isOpen()) {
/*  344 */           return null;
/*      */         }
/*      */         
/*      */         do {
/*  348 */           l1 = this.nd.seek(this.fd, -1L);
/*  349 */         } while (l1 == -3L && isOpen());
/*  350 */         if (!isOpen())
/*  351 */           return null; 
/*  352 */         assert l1 >= 0L;
/*      */ 
/*      */         
/*  355 */         if (paramLong < l) {
/*      */           do {
/*  357 */             i = this.nd.truncate(this.fd, paramLong);
/*  358 */           } while (i == -3 && isOpen());
/*  359 */           if (!isOpen()) {
/*  360 */             return null;
/*      */           }
/*      */         } 
/*      */         
/*  364 */         if (l1 > paramLong)
/*  365 */           l1 = paramLong; 
/*      */         do {
/*  367 */           l2 = this.nd.seek(this.fd, l1);
/*  368 */         } while (l2 == -3L && isOpen());
/*  369 */         return this;
/*      */       } finally {
/*  371 */         this.threads.remove(j);
/*  372 */         end((i > -1));
/*  373 */         assert IOStatus.check(i);
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   public void force(boolean paramBoolean) throws IOException {
/*  379 */     ensureOpen();
/*  380 */     int i = -1;
/*  381 */     int j = -1;
/*      */     try {
/*  383 */       begin();
/*  384 */       j = this.threads.add();
/*  385 */       if (!isOpen())
/*      */         return; 
/*      */       do {
/*  388 */         i = this.nd.force(this.fd, paramBoolean);
/*  389 */       } while (i == -3 && isOpen());
/*      */     } finally {
/*  391 */       this.threads.remove(j);
/*  392 */       end((i > -1));
/*  393 */       assert IOStatus.check(i);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private static volatile boolean transferSupported = true;
/*      */   
/*      */   private static volatile boolean pipeSupported = true;
/*      */   
/*      */   private static volatile boolean fileSupported = true;
/*      */   
/*      */   private static final long MAPPED_TRANSFER_SIZE = 8388608L;
/*      */   
/*      */   private static final int TRANSFER_SIZE = 8192;
/*      */   
/*      */   private static final int MAP_RO = 0;
/*      */   
/*      */   private static final int MAP_RW = 1;
/*      */   private static final int MAP_PV = 2;
/*      */   private volatile FileLockTable fileLockTable;
/*      */   private static boolean isSharedFileLockTable;
/*      */   private static volatile boolean propertyChecked;
/*      */   
/*      */   private long transferToDirectlyInternal(long paramLong, int paramInt, WritableByteChannel paramWritableByteChannel, FileDescriptor paramFileDescriptor) throws IOException {
/*  417 */     assert !this.nd.transferToDirectlyNeedsPositionLock() || 
/*  418 */       Thread.holdsLock(this.positionLock);
/*      */     
/*  420 */     long l = -1L;
/*  421 */     int i = -1;
/*      */     try {
/*  423 */       begin();
/*  424 */       i = this.threads.add();
/*  425 */       if (!isOpen())
/*  426 */         return -1L; 
/*      */       do {
/*  428 */         l = transferTo0(this.fd, paramLong, paramInt, paramFileDescriptor);
/*  429 */       } while (l == -3L && isOpen());
/*  430 */       if (l == -6L) {
/*  431 */         if (paramWritableByteChannel instanceof SinkChannelImpl)
/*  432 */           pipeSupported = false; 
/*  433 */         if (paramWritableByteChannel instanceof FileChannelImpl)
/*  434 */           fileSupported = false; 
/*  435 */         return -6L;
/*      */       } 
/*  437 */       if (l == -4L) {
/*      */         
/*  439 */         transferSupported = false;
/*  440 */         return -4L;
/*      */       } 
/*  442 */       return IOStatus.normalize(l);
/*      */     } finally {
/*  444 */       this.threads.remove(i);
/*  445 */       end((l > -1L));
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private long transferToDirectly(long paramLong, int paramInt, WritableByteChannel paramWritableByteChannel) throws IOException {
/*  453 */     if (!transferSupported) {
/*  454 */       return -4L;
/*      */     }
/*  456 */     FileDescriptor fileDescriptor = null;
/*  457 */     if (paramWritableByteChannel instanceof FileChannelImpl) {
/*  458 */       if (!fileSupported)
/*  459 */         return -6L; 
/*  460 */       fileDescriptor = ((FileChannelImpl)paramWritableByteChannel).fd;
/*  461 */     } else if (paramWritableByteChannel instanceof SelChImpl) {
/*      */       
/*  463 */       if (paramWritableByteChannel instanceof SinkChannelImpl && !pipeSupported) {
/*  464 */         return -6L;
/*      */       }
/*      */ 
/*      */       
/*  468 */       SelectableChannel selectableChannel = (SelectableChannel)paramWritableByteChannel;
/*  469 */       if (!this.nd.canTransferToDirectly(selectableChannel)) {
/*  470 */         return -6L;
/*      */       }
/*  472 */       fileDescriptor = ((SelChImpl)paramWritableByteChannel).getFD();
/*      */     } 
/*      */     
/*  475 */     if (fileDescriptor == null)
/*  476 */       return -4L; 
/*  477 */     int i = IOUtil.fdVal(this.fd);
/*  478 */     int j = IOUtil.fdVal(fileDescriptor);
/*  479 */     if (i == j) {
/*  480 */       return -4L;
/*      */     }
/*  482 */     if (this.nd.transferToDirectlyNeedsPositionLock()) {
/*  483 */       synchronized (this.positionLock) {
/*  484 */         long l = position();
/*      */         try {
/*  486 */           return transferToDirectlyInternal(paramLong, paramInt, paramWritableByteChannel, fileDescriptor);
/*      */         } finally {
/*      */           
/*  489 */           position(l);
/*      */         } 
/*      */       } 
/*      */     }
/*  493 */     return transferToDirectlyInternal(paramLong, paramInt, paramWritableByteChannel, fileDescriptor);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private long transferToTrustedChannel(long paramLong1, long paramLong2, WritableByteChannel paramWritableByteChannel) throws IOException {
/*  504 */     boolean bool = paramWritableByteChannel instanceof SelChImpl;
/*  505 */     if (!(paramWritableByteChannel instanceof FileChannelImpl) && !bool) {
/*  506 */       return -4L;
/*      */     }
/*      */     
/*  509 */     long l = paramLong2;
/*  510 */     while (l > 0L)
/*  511 */     { long l1 = Math.min(l, 8388608L);
/*      */       try {
/*  513 */         MappedByteBuffer mappedByteBuffer = map(FileChannel.MapMode.READ_ONLY, paramLong1, l1);
/*      */ 
/*      */         
/*  516 */         try { int i = paramWritableByteChannel.write(mappedByteBuffer);
/*  517 */           assert i >= 0;
/*  518 */           l -= i;
/*  519 */           if (bool)
/*      */           
/*      */           { 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  526 */             unmap(mappedByteBuffer); break; }  assert i > 0; paramLong1 += i; } finally { unmap(mappedByteBuffer); }
/*      */          continue;
/*  528 */       } catch (ClosedByInterruptException closedByInterruptException) {
/*      */ 
/*      */         
/*  531 */         assert !paramWritableByteChannel.isOpen();
/*      */         try {
/*  533 */           close();
/*  534 */         } catch (Throwable throwable) {
/*  535 */           closedByInterruptException.addSuppressed(throwable);
/*      */         } 
/*  537 */         throw closedByInterruptException;
/*  538 */       } catch (IOException iOException) {
/*      */         
/*  540 */         if (l == paramLong2) {
/*  541 */           throw iOException;
/*      */         }
/*      */       } 
/*      */       
/*  545 */       return paramLong2 - l; }  return paramLong2 - l;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private long transferToArbitraryChannel(long paramLong, int paramInt, WritableByteChannel paramWritableByteChannel) throws IOException {
/*  553 */     int i = Math.min(paramInt, 8192);
/*  554 */     ByteBuffer byteBuffer = ByteBuffer.allocate(i);
/*  555 */     long l1 = 0L;
/*  556 */     long l2 = paramLong;
/*      */     try {
/*  558 */       while (l1 < paramInt) {
/*  559 */         byteBuffer.limit(Math.min((int)(paramInt - l1), 8192));
/*  560 */         int j = read(byteBuffer, l2);
/*  561 */         if (j <= 0)
/*      */           break; 
/*  563 */         byteBuffer.flip();
/*      */ 
/*      */         
/*  566 */         int k = paramWritableByteChannel.write(byteBuffer);
/*  567 */         l1 += k;
/*  568 */         if (k != j)
/*      */           break; 
/*  570 */         l2 += k;
/*  571 */         byteBuffer.clear();
/*      */       } 
/*  573 */       return l1;
/*  574 */     } catch (IOException iOException) {
/*  575 */       if (l1 > 0L)
/*  576 */         return l1; 
/*  577 */       throw iOException;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public long transferTo(long paramLong1, long paramLong2, WritableByteChannel paramWritableByteChannel) throws IOException {
/*  585 */     ensureOpen();
/*  586 */     if (!paramWritableByteChannel.isOpen())
/*  587 */       throw new ClosedChannelException(); 
/*  588 */     if (!this.readable)
/*  589 */       throw new NonReadableChannelException(); 
/*  590 */     if (paramWritableByteChannel instanceof FileChannelImpl && !((FileChannelImpl)paramWritableByteChannel).writable)
/*      */     {
/*  592 */       throw new NonWritableChannelException(); } 
/*  593 */     if (paramLong1 < 0L || paramLong2 < 0L)
/*  594 */       throw new IllegalArgumentException(); 
/*  595 */     long l1 = size();
/*  596 */     if (paramLong1 > l1)
/*  597 */       return 0L; 
/*  598 */     int i = (int)Math.min(paramLong2, 2147483647L);
/*  599 */     if (l1 - paramLong1 < i) {
/*  600 */       i = (int)(l1 - paramLong1);
/*      */     }
/*      */     
/*      */     long l2;
/*      */     
/*  605 */     if ((l2 = transferToDirectly(paramLong1, i, paramWritableByteChannel)) >= 0L) {
/*  606 */       return l2;
/*      */     }
/*      */     
/*  609 */     if ((l2 = transferToTrustedChannel(paramLong1, i, paramWritableByteChannel)) >= 0L) {
/*  610 */       return l2;
/*      */     }
/*      */     
/*  613 */     return transferToArbitraryChannel(paramLong1, i, paramWritableByteChannel);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private long transferFromFileChannel(FileChannelImpl paramFileChannelImpl, long paramLong1, long paramLong2) throws IOException {
/*  620 */     if (!paramFileChannelImpl.readable)
/*  621 */       throw new NonReadableChannelException(); 
/*  622 */     synchronized (paramFileChannelImpl.positionLock) {
/*  623 */       long l1 = paramFileChannelImpl.position();
/*  624 */       long l2 = Math.min(paramLong2, paramFileChannelImpl.size() - l1);
/*      */       
/*  626 */       long l3 = l2;
/*  627 */       long l4 = l1;
/*  628 */       while (l3 > 0L) {
/*  629 */         long l = Math.min(l3, 8388608L);
/*      */         
/*  631 */         MappedByteBuffer mappedByteBuffer = paramFileChannelImpl.map(FileChannel.MapMode.READ_ONLY, l4, l);
/*      */         try {
/*  633 */           long l6 = write(mappedByteBuffer, paramLong1);
/*  634 */           assert l6 > 0L;
/*  635 */           l4 += l6;
/*  636 */           paramLong1 += l6;
/*  637 */           l3 -= l6;
/*  638 */         } catch (IOException iOException) {
/*      */           
/*  640 */           if (l3 == l2)
/*  641 */             throw iOException; 
/*      */           break;
/*      */         } finally {
/*  644 */           unmap(mappedByteBuffer);
/*      */         } 
/*      */       } 
/*  647 */       long l5 = l2 - l3;
/*  648 */       paramFileChannelImpl.position(l1 + l5);
/*  649 */       return l5;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private long transferFromArbitraryChannel(ReadableByteChannel paramReadableByteChannel, long paramLong1, long paramLong2) throws IOException {
/*  660 */     int i = (int)Math.min(paramLong2, 8192L);
/*  661 */     ByteBuffer byteBuffer = ByteBuffer.allocate(i);
/*  662 */     long l1 = 0L;
/*  663 */     long l2 = paramLong1;
/*      */     try {
/*  665 */       while (l1 < paramLong2) {
/*  666 */         byteBuffer.limit((int)Math.min(paramLong2 - l1, 8192L));
/*      */ 
/*      */         
/*  669 */         int j = paramReadableByteChannel.read(byteBuffer);
/*  670 */         if (j <= 0)
/*      */           break; 
/*  672 */         byteBuffer.flip();
/*  673 */         int k = write(byteBuffer, l2);
/*  674 */         l1 += k;
/*  675 */         if (k != j)
/*      */           break; 
/*  677 */         l2 += k;
/*  678 */         byteBuffer.clear();
/*      */       } 
/*  680 */       return l1;
/*  681 */     } catch (IOException iOException) {
/*  682 */       if (l1 > 0L)
/*  683 */         return l1; 
/*  684 */       throw iOException;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public long transferFrom(ReadableByteChannel paramReadableByteChannel, long paramLong1, long paramLong2) throws IOException {
/*  692 */     ensureOpen();
/*  693 */     if (!paramReadableByteChannel.isOpen())
/*  694 */       throw new ClosedChannelException(); 
/*  695 */     if (!this.writable)
/*  696 */       throw new NonWritableChannelException(); 
/*  697 */     if (paramLong1 < 0L || paramLong2 < 0L)
/*  698 */       throw new IllegalArgumentException(); 
/*  699 */     if (paramLong1 > size())
/*  700 */       return 0L; 
/*  701 */     if (paramReadableByteChannel instanceof FileChannelImpl) {
/*  702 */       return transferFromFileChannel((FileChannelImpl)paramReadableByteChannel, paramLong1, paramLong2);
/*      */     }
/*      */     
/*  705 */     return transferFromArbitraryChannel(paramReadableByteChannel, paramLong1, paramLong2);
/*      */   }
/*      */   
/*      */   public int read(ByteBuffer paramByteBuffer, long paramLong) throws IOException {
/*  709 */     if (paramByteBuffer == null)
/*  710 */       throw new NullPointerException(); 
/*  711 */     if (paramLong < 0L)
/*  712 */       throw new IllegalArgumentException("Negative position"); 
/*  713 */     if (!this.readable)
/*  714 */       throw new NonReadableChannelException(); 
/*  715 */     ensureOpen();
/*  716 */     if (this.nd.needsPositionLock()) {
/*  717 */       synchronized (this.positionLock) {
/*  718 */         return readInternal(paramByteBuffer, paramLong);
/*      */       } 
/*      */     }
/*  721 */     return readInternal(paramByteBuffer, paramLong);
/*      */   }
/*      */ 
/*      */   
/*      */   private int readInternal(ByteBuffer paramByteBuffer, long paramLong) throws IOException {
/*  726 */     assert !this.nd.needsPositionLock() || Thread.holdsLock(this.positionLock);
/*  727 */     int i = 0;
/*  728 */     int j = -1;
/*      */     try {
/*  730 */       begin();
/*  731 */       j = this.threads.add();
/*  732 */       if (!isOpen())
/*  733 */         return -1; 
/*      */       do {
/*  735 */         i = IOUtil.read(this.fd, paramByteBuffer, paramLong, this.nd);
/*  736 */       } while (i == -3 && isOpen());
/*  737 */       return IOStatus.normalize(i);
/*      */     } finally {
/*  739 */       this.threads.remove(j);
/*  740 */       end((i > 0));
/*  741 */       assert IOStatus.check(i);
/*      */     } 
/*      */   }
/*      */   
/*      */   public int write(ByteBuffer paramByteBuffer, long paramLong) throws IOException {
/*  746 */     if (paramByteBuffer == null)
/*  747 */       throw new NullPointerException(); 
/*  748 */     if (paramLong < 0L)
/*  749 */       throw new IllegalArgumentException("Negative position"); 
/*  750 */     if (!this.writable)
/*  751 */       throw new NonWritableChannelException(); 
/*  752 */     ensureOpen();
/*  753 */     if (this.nd.needsPositionLock()) {
/*  754 */       synchronized (this.positionLock) {
/*  755 */         return writeInternal(paramByteBuffer, paramLong);
/*      */       } 
/*      */     }
/*  758 */     return writeInternal(paramByteBuffer, paramLong);
/*      */   }
/*      */ 
/*      */   
/*      */   private int writeInternal(ByteBuffer paramByteBuffer, long paramLong) throws IOException {
/*  763 */     assert !this.nd.needsPositionLock() || Thread.holdsLock(this.positionLock);
/*  764 */     int i = 0;
/*  765 */     int j = -1;
/*      */     try {
/*  767 */       begin();
/*  768 */       j = this.threads.add();
/*  769 */       if (!isOpen())
/*  770 */         return -1; 
/*      */       do {
/*  772 */         i = IOUtil.write(this.fd, paramByteBuffer, paramLong, this.nd);
/*  773 */       } while (i == -3 && isOpen());
/*  774 */       return IOStatus.normalize(i);
/*      */     } finally {
/*  776 */       this.threads.remove(j);
/*  777 */       end((i > 0));
/*  778 */       assert IOStatus.check(i);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static class Unmapper
/*      */     implements Runnable
/*      */   {
/*  789 */     private static final NativeDispatcher nd = new FileDispatcherImpl();
/*      */     
/*      */     static volatile int count;
/*      */     
/*      */     static volatile long totalSize;
/*      */     
/*      */     static volatile long totalCapacity;
/*      */     
/*      */     private volatile long address;
/*      */     
/*      */     private final long size;
/*      */     private final int cap;
/*      */     private final FileDescriptor fd;
/*      */     
/*      */     private Unmapper(long param1Long1, long param1Long2, int param1Int, FileDescriptor param1FileDescriptor) {
/*  804 */       assert param1Long1 != 0L;
/*  805 */       this.address = param1Long1;
/*  806 */       this.size = param1Long2;
/*  807 */       this.cap = param1Int;
/*  808 */       this.fd = param1FileDescriptor;
/*      */       
/*  810 */       synchronized (Unmapper.class) {
/*  811 */         count++;
/*  812 */         totalSize += param1Long2;
/*  813 */         totalCapacity += param1Int;
/*      */       } 
/*      */     }
/*      */     
/*      */     public void run() {
/*  818 */       if (this.address == 0L)
/*      */         return; 
/*  820 */       FileChannelImpl.unmap0(this.address, this.size);
/*  821 */       this.address = 0L;
/*      */ 
/*      */       
/*  824 */       if (this.fd.valid()) {
/*      */         try {
/*  826 */           nd.close(this.fd);
/*  827 */         } catch (IOException iOException) {}
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*  832 */       synchronized (Unmapper.class) {
/*  833 */         count--;
/*  834 */         totalSize -= this.size;
/*  835 */         totalCapacity -= this.cap;
/*      */       } 
/*      */     }
/*      */   }
/*      */   
/*      */   private static void unmap(MappedByteBuffer paramMappedByteBuffer) {
/*  841 */     Cleaner cleaner = ((DirectBuffer)paramMappedByteBuffer).cleaner();
/*  842 */     if (cleaner != null) {
/*  843 */       cleaner.clean();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public MappedByteBuffer map(FileChannel.MapMode paramMapMode, long paramLong1, long paramLong2) throws IOException {
/*  853 */     ensureOpen();
/*  854 */     if (paramMapMode == null)
/*  855 */       throw new NullPointerException("Mode is null"); 
/*  856 */     if (paramLong1 < 0L)
/*  857 */       throw new IllegalArgumentException("Negative position"); 
/*  858 */     if (paramLong2 < 0L)
/*  859 */       throw new IllegalArgumentException("Negative size"); 
/*  860 */     if (paramLong1 + paramLong2 < 0L)
/*  861 */       throw new IllegalArgumentException("Position + size overflow"); 
/*  862 */     if (paramLong2 > 2147483647L) {
/*  863 */       throw new IllegalArgumentException("Size exceeds Integer.MAX_VALUE");
/*      */     }
/*  865 */     byte b = -1;
/*  866 */     if (paramMapMode == FileChannel.MapMode.READ_ONLY) {
/*  867 */       b = 0;
/*  868 */     } else if (paramMapMode == FileChannel.MapMode.READ_WRITE) {
/*  869 */       b = 1;
/*  870 */     } else if (paramMapMode == FileChannel.MapMode.PRIVATE) {
/*  871 */       b = 2;
/*  872 */     }  assert b >= 0;
/*  873 */     if (paramMapMode != FileChannel.MapMode.READ_ONLY && !this.writable)
/*  874 */       throw new NonWritableChannelException(); 
/*  875 */     if (!this.readable) {
/*  876 */       throw new NonReadableChannelException();
/*      */     }
/*  878 */     long l = -1L;
/*  879 */     int i = -1; try {
/*      */       long l1; int j; FileDescriptor fileDescriptor;
/*  881 */       begin();
/*  882 */       i = this.threads.add();
/*  883 */       if (!isOpen()) {
/*  884 */         return null;
/*      */       }
/*      */ 
/*      */       
/*  888 */       synchronized (this.positionLock) {
/*      */         long l2;
/*      */         do {
/*  891 */           l2 = this.nd.size(this.fd);
/*  892 */         } while (l2 == -3L && isOpen());
/*  893 */         if (!isOpen()) {
/*  894 */           return null;
/*      */         }
/*  896 */         if (l2 < paramLong1 + paramLong2) {
/*  897 */           int m; if (!this.writable) {
/*  898 */             throw new IOException("Channel not open for writing - cannot extend file to required size");
/*      */           }
/*      */ 
/*      */           
/*      */           do {
/*  903 */             m = this.nd.truncate(this.fd, paramLong1 + paramLong2);
/*  904 */           } while (m == -3 && isOpen());
/*  905 */           if (!isOpen()) {
/*  906 */             return null;
/*      */           }
/*      */         } 
/*  909 */         if (paramLong2 == 0L) {
/*  910 */           l = 0L;
/*      */           
/*  912 */           FileDescriptor fileDescriptor1 = new FileDescriptor();
/*  913 */           if (!this.writable || b == 0) {
/*  914 */             return Util.newMappedByteBufferR(0, 0L, fileDescriptor1, null);
/*      */           }
/*  916 */           return Util.newMappedByteBuffer(0, 0L, fileDescriptor1, null);
/*      */         } 
/*      */         
/*  919 */         j = (int)(paramLong1 % allocationGranularity);
/*  920 */         long l3 = paramLong1 - j;
/*  921 */         l1 = paramLong2 + j;
/*      */         
/*      */         try {
/*  924 */           l = map0(b, l3, l1);
/*  925 */         } catch (OutOfMemoryError outOfMemoryError) {
/*      */ 
/*      */           
/*  928 */           System.gc();
/*      */           try {
/*  930 */             Thread.sleep(100L);
/*  931 */           } catch (InterruptedException interruptedException) {
/*  932 */             Thread.currentThread().interrupt();
/*      */           } 
/*      */           try {
/*  935 */             l = map0(b, l3, l1);
/*  936 */           } catch (OutOfMemoryError outOfMemoryError1) {
/*      */             
/*  938 */             throw new IOException("Map failed", outOfMemoryError1);
/*      */           } 
/*      */         } 
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       try {
/*  947 */         fileDescriptor = this.nd.duplicateForMapping(this.fd);
/*  948 */       } catch (IOException iOException) {
/*  949 */         unmap0(l, l1);
/*  950 */         throw iOException;
/*      */       } 
/*      */       
/*  953 */       assert IOStatus.checkAll(l);
/*  954 */       assert l % allocationGranularity == 0L;
/*  955 */       int k = (int)paramLong2;
/*  956 */       Unmapper unmapper = new Unmapper(l, l1, k, fileDescriptor);
/*  957 */       if (!this.writable || b == 0) {
/*  958 */         return Util.newMappedByteBufferR(k, l + j, fileDescriptor, unmapper);
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*  963 */       return Util.newMappedByteBuffer(k, l + j, fileDescriptor, unmapper);
/*      */     
/*      */     }
/*      */     finally {
/*      */ 
/*      */       
/*  969 */       this.threads.remove(i);
/*  970 */       end(IOStatus.checkAll(l));
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static JavaNioAccess.BufferPool getMappedBufferPool() {
/*  979 */     return new JavaNioAccess.BufferPool()
/*      */       {
/*      */         public String getName() {
/*  982 */           return "mapped";
/*      */         }
/*      */         
/*      */         public long getCount() {
/*  986 */           return FileChannelImpl.Unmapper.count;
/*      */         }
/*      */         
/*      */         public long getTotalCapacity() {
/*  990 */           return FileChannelImpl.Unmapper.totalCapacity;
/*      */         }
/*      */         
/*      */         public long getMemoryUsed() {
/*  994 */           return FileChannelImpl.Unmapper.totalSize;
/*      */         }
/*      */       };
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
/*      */   private static boolean isSharedFileLockTable() {
/* 1017 */     if (!propertyChecked) {
/* 1018 */       synchronized (FileChannelImpl.class) {
/* 1019 */         if (!propertyChecked) {
/* 1020 */           String str = AccessController.<String>doPrivileged(new GetPropertyAction("sun.nio.ch.disableSystemWideOverlappingFileLockCheck"));
/*      */ 
/*      */           
/* 1023 */           isSharedFileLockTable = (str == null || str.equals("false"));
/* 1024 */           propertyChecked = true;
/*      */         } 
/*      */       } 
/*      */     }
/* 1028 */     return isSharedFileLockTable;
/*      */   }
/*      */   
/*      */   private FileLockTable fileLockTable() throws IOException {
/* 1032 */     if (this.fileLockTable == null) {
/* 1033 */       synchronized (this) {
/* 1034 */         if (this.fileLockTable == null) {
/* 1035 */           if (isSharedFileLockTable()) {
/* 1036 */             int i = this.threads.add();
/*      */             try {
/* 1038 */               ensureOpen();
/* 1039 */               this.fileLockTable = FileLockTable.newSharedFileLockTable(this, this.fd);
/*      */             } finally {
/* 1041 */               this.threads.remove(i);
/*      */             } 
/*      */           } else {
/* 1044 */             this.fileLockTable = new SimpleFileLockTable();
/*      */           } 
/*      */         }
/*      */       } 
/*      */     }
/* 1049 */     return this.fileLockTable;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public FileLock lock(long paramLong1, long paramLong2, boolean paramBoolean) throws IOException {
/* 1055 */     ensureOpen();
/* 1056 */     if (paramBoolean && !this.readable)
/* 1057 */       throw new NonReadableChannelException(); 
/* 1058 */     if (!paramBoolean && !this.writable)
/* 1059 */       throw new NonWritableChannelException(); 
/* 1060 */     FileLockImpl fileLockImpl = new FileLockImpl(this, paramLong1, paramLong2, paramBoolean);
/* 1061 */     FileLockTable fileLockTable = fileLockTable();
/* 1062 */     fileLockTable.add(fileLockImpl);
/* 1063 */     boolean bool = false;
/* 1064 */     int i = -1; try {
/*      */       int j;
/* 1066 */       begin();
/* 1067 */       i = this.threads.add();
/* 1068 */       if (!isOpen()) {
/* 1069 */         return null;
/*      */       }
/*      */       do {
/* 1072 */         j = this.nd.lock(this.fd, true, paramLong1, paramLong2, paramBoolean);
/* 1073 */       } while (j == 2 && isOpen());
/* 1074 */       if (isOpen()) {
/* 1075 */         if (j == 1) {
/* 1076 */           assert paramBoolean;
/* 1077 */           FileLockImpl fileLockImpl1 = new FileLockImpl(this, paramLong1, paramLong2, false);
/*      */           
/* 1079 */           fileLockTable.replace(fileLockImpl, fileLockImpl1);
/* 1080 */           fileLockImpl = fileLockImpl1;
/*      */         } 
/* 1082 */         bool = true;
/*      */       } 
/*      */     } finally {
/* 1085 */       if (!bool)
/* 1086 */         fileLockTable.remove(fileLockImpl); 
/* 1087 */       this.threads.remove(i);
/*      */       try {
/* 1089 */         end(bool);
/* 1090 */       } catch (ClosedByInterruptException closedByInterruptException) {
/* 1091 */         throw new FileLockInterruptionException();
/*      */       } 
/*      */     } 
/* 1094 */     return fileLockImpl;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public FileLock tryLock(long paramLong1, long paramLong2, boolean paramBoolean) throws IOException {
/* 1100 */     ensureOpen();
/* 1101 */     if (paramBoolean && !this.readable)
/* 1102 */       throw new NonReadableChannelException(); 
/* 1103 */     if (!paramBoolean && !this.writable)
/* 1104 */       throw new NonWritableChannelException(); 
/* 1105 */     FileLockImpl fileLockImpl = new FileLockImpl(this, paramLong1, paramLong2, paramBoolean);
/* 1106 */     FileLockTable fileLockTable = fileLockTable();
/* 1107 */     fileLockTable.add(fileLockImpl);
/*      */ 
/*      */     
/* 1110 */     int i = this.threads.add(); try {
/*      */       int j;
/*      */       try {
/* 1113 */         ensureOpen();
/* 1114 */         j = this.nd.lock(this.fd, false, paramLong1, paramLong2, paramBoolean);
/* 1115 */       } catch (IOException iOException) {
/* 1116 */         fileLockTable.remove(fileLockImpl);
/* 1117 */         throw iOException;
/*      */       } 
/* 1119 */       if (j == -1) {
/* 1120 */         fileLockTable.remove(fileLockImpl);
/* 1121 */         return null;
/*      */       } 
/* 1123 */       if (j == 1) {
/* 1124 */         assert paramBoolean;
/* 1125 */         FileLockImpl fileLockImpl1 = new FileLockImpl(this, paramLong1, paramLong2, false);
/*      */         
/* 1127 */         fileLockTable.replace(fileLockImpl, fileLockImpl1);
/* 1128 */         return fileLockImpl1;
/*      */       } 
/* 1130 */       return fileLockImpl;
/*      */     } finally {
/* 1132 */       this.threads.remove(i);
/*      */     } 
/*      */   }
/*      */   
/*      */   void release(FileLockImpl paramFileLockImpl) throws IOException {
/* 1137 */     int i = this.threads.add();
/*      */     try {
/* 1139 */       ensureOpen();
/* 1140 */       this.nd.release(this.fd, paramFileLockImpl.position(), paramFileLockImpl.size());
/*      */     } finally {
/* 1142 */       this.threads.remove(i);
/*      */     } 
/* 1144 */     assert this.fileLockTable != null;
/* 1145 */     this.fileLockTable.remove(paramFileLockImpl);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static class SimpleFileLockTable
/*      */     extends FileLockTable
/*      */   {
/* 1156 */     private final List<FileLock> lockList = new ArrayList<>(2);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private void checkList(long param1Long1, long param1Long2) throws OverlappingFileLockException {
/* 1164 */       assert Thread.holdsLock(this.lockList);
/* 1165 */       for (FileLock fileLock : this.lockList) {
/* 1166 */         if (fileLock.overlaps(param1Long1, param1Long2)) {
/* 1167 */           throw new OverlappingFileLockException();
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/*      */     public void add(FileLock param1FileLock) throws OverlappingFileLockException {
/* 1173 */       synchronized (this.lockList) {
/* 1174 */         checkList(param1FileLock.position(), param1FileLock.size());
/* 1175 */         this.lockList.add(param1FileLock);
/*      */       } 
/*      */     }
/*      */     
/*      */     public void remove(FileLock param1FileLock) {
/* 1180 */       synchronized (this.lockList) {
/* 1181 */         this.lockList.remove(param1FileLock);
/*      */       } 
/*      */     }
/*      */     
/*      */     public List<FileLock> removeAll() {
/* 1186 */       synchronized (this.lockList) {
/* 1187 */         ArrayList<FileLock> arrayList = new ArrayList<>(this.lockList);
/* 1188 */         this.lockList.clear();
/* 1189 */         return arrayList;
/*      */       } 
/*      */     }
/*      */     
/*      */     public void replace(FileLock param1FileLock1, FileLock param1FileLock2) {
/* 1194 */       synchronized (this.lockList) {
/* 1195 */         this.lockList.remove(param1FileLock1);
/* 1196 */         this.lockList.add(param1FileLock2);
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
/*      */   static {
/* 1218 */     IOUtil.load();
/* 1219 */     allocationGranularity = initIDs();
/*      */   }
/*      */   
/*      */   private native long map0(int paramInt, long paramLong1, long paramLong2) throws IOException;
/*      */   
/*      */   private static native int unmap0(long paramLong1, long paramLong2);
/*      */   
/*      */   private native long transferTo0(FileDescriptor paramFileDescriptor1, long paramLong1, long paramLong2, FileDescriptor paramFileDescriptor2);
/*      */   
/*      */   private static native long initIDs();
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/nio/ch/FileChannelImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */