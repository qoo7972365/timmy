/*     */ package sun.nio.ch;
/*     */ 
/*     */ import java.io.FileDescriptor;
/*     */ import java.io.IOException;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.channels.AsynchronousCloseException;
/*     */ import java.nio.channels.AsynchronousFileChannel;
/*     */ import java.nio.channels.ClosedChannelException;
/*     */ import java.nio.channels.CompletionHandler;
/*     */ import java.nio.channels.FileLock;
/*     */ import java.nio.channels.NonReadableChannelException;
/*     */ import java.nio.channels.NonWritableChannelException;
/*     */ import java.util.concurrent.ExecutorService;
/*     */ import java.util.concurrent.Future;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SimpleAsynchronousFileChannelImpl
/*     */   extends AsynchronousFileChannelImpl
/*     */ {
/*     */   private static class DefaultExecutorHolder
/*     */   {
/*  47 */     static final ExecutorService defaultExecutor = ThreadPool.createDefault().executor();
/*     */   }
/*     */ 
/*     */   
/*  51 */   private static final FileDispatcher nd = new FileDispatcherImpl();
/*     */ 
/*     */   
/*  54 */   private final NativeThreadSet threads = new NativeThreadSet(2);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   SimpleAsynchronousFileChannelImpl(FileDescriptor paramFileDescriptor, boolean paramBoolean1, boolean paramBoolean2, ExecutorService paramExecutorService) {
/*  62 */     super(paramFileDescriptor, paramBoolean1, paramBoolean2, paramExecutorService);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static AsynchronousFileChannel open(FileDescriptor paramFileDescriptor, boolean paramBoolean1, boolean paramBoolean2, ThreadPool paramThreadPool) {
/*  72 */     ExecutorService executorService = (paramThreadPool == null) ? DefaultExecutorHolder.defaultExecutor : paramThreadPool.executor();
/*  73 */     return new SimpleAsynchronousFileChannelImpl(paramFileDescriptor, paramBoolean1, paramBoolean2, executorService);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void close() throws IOException {
/*  79 */     synchronized (this.fdObj) {
/*  80 */       if (this.closed)
/*     */         return; 
/*  82 */       this.closed = true;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  88 */     invalidateAllLocks();
/*     */ 
/*     */     
/*  91 */     this.threads.signalAndWait();
/*     */ 
/*     */     
/*  94 */     this.closeLock.writeLock().lock();
/*     */ 
/*     */ 
/*     */     
/*  98 */     this.closeLock.writeLock().unlock();
/*     */ 
/*     */ 
/*     */     
/* 102 */     nd.close(this.fdObj);
/*     */   }
/*     */ 
/*     */   
/*     */   public long size() throws IOException {
/* 107 */     int i = this.threads.add();
/*     */     try {
/* 109 */       long l = 0L;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     }
/*     */     finally {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 120 */       this.threads.remove(i);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public AsynchronousFileChannel truncate(long paramLong) throws IOException {
/* 126 */     if (paramLong < 0L)
/* 127 */       throw new IllegalArgumentException("Negative size"); 
/* 128 */     if (!this.writing)
/* 129 */       throw new NonWritableChannelException(); 
/* 130 */     int i = this.threads.add();
/*     */     try {
/* 132 */       long l = 0L;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     }
/*     */     finally {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 150 */       this.threads.remove(i);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void force(boolean paramBoolean) throws IOException {
/* 156 */     int i = this.threads.add();
/*     */     try {
/* 158 */       int j = 0;
/*     */       try {
/* 160 */         begin();
/*     */         do {
/* 162 */           j = nd.force(this.fdObj, paramBoolean);
/* 163 */         } while (j == -3 && isOpen());
/*     */       } finally {
/* 165 */         end((j >= 0));
/*     */       } 
/*     */     } finally {
/* 168 */       this.threads.remove(i);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   <A> Future<FileLock> implLock(final long position, final long size, final boolean shared, final A attachment, final CompletionHandler<FileLock, ? super A> handler) {
/* 179 */     if (shared && !this.reading)
/* 180 */       throw new NonReadableChannelException(); 
/* 181 */     if (!shared && !this.writing) {
/* 182 */       throw new NonWritableChannelException();
/*     */     }
/*     */     
/* 185 */     final FileLockImpl fli = addToFileLockTable(position, size, shared);
/* 186 */     if (fileLockImpl == null) {
/* 187 */       ClosedChannelException closedChannelException = new ClosedChannelException();
/* 188 */       if (handler == null)
/* 189 */         return CompletedFuture.withFailure(closedChannelException); 
/* 190 */       Invoker.invokeIndirectly(handler, attachment, (FileLock)null, closedChannelException, this.executor);
/* 191 */       return null;
/*     */     } 
/*     */     
/* 194 */     final PendingFuture<FileLock> result = (handler == null) ? new PendingFuture<>(this) : null;
/*     */     
/* 196 */     Runnable runnable = new Runnable() {
/*     */         public void run() {
/* 198 */           IOException iOException = null;
/*     */           
/* 200 */           int i = SimpleAsynchronousFileChannelImpl.this.threads.add();
/*     */           try {
/*     */             try {
/*     */               int j;
/* 204 */               SimpleAsynchronousFileChannelImpl.this.begin();
/*     */               do {
/* 206 */                 j = SimpleAsynchronousFileChannelImpl.nd.lock(SimpleAsynchronousFileChannelImpl.this.fdObj, true, position, size, shared);
/* 207 */               } while (j == 2 && SimpleAsynchronousFileChannelImpl.this.isOpen());
/* 208 */               if (j != 0 || !SimpleAsynchronousFileChannelImpl.this.isOpen()) {
/* 209 */                 throw new AsynchronousCloseException();
/*     */               }
/* 211 */             } catch (IOException iOException1) {
/* 212 */               SimpleAsynchronousFileChannelImpl.this.removeFromFileLockTable(fli);
/* 213 */               if (!SimpleAsynchronousFileChannelImpl.this.isOpen())
/* 214 */                 iOException1 = new AsynchronousCloseException(); 
/* 215 */               iOException = iOException1;
/*     */             } finally {
/* 217 */               SimpleAsynchronousFileChannelImpl.this.end();
/*     */             } 
/*     */           } finally {
/* 220 */             SimpleAsynchronousFileChannelImpl.this.threads.remove(i);
/*     */           } 
/* 222 */           if (handler == null) {
/* 223 */             result.setResult(fli, iOException);
/*     */           } else {
/* 225 */             Invoker.invokeUnchecked(handler, attachment, fli, iOException);
/*     */           } 
/*     */         }
/*     */       };
/* 229 */     boolean bool = false;
/*     */     try {
/* 231 */       this.executor.execute(runnable);
/* 232 */       bool = true;
/*     */     } finally {
/* 234 */       if (!bool)
/*     */       {
/* 236 */         removeFromFileLockTable(fileLockImpl);
/*     */       }
/*     */     } 
/* 239 */     return pendingFuture;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FileLock tryLock(long paramLong1, long paramLong2, boolean paramBoolean) throws IOException {
/* 246 */     if (paramBoolean && !this.reading)
/* 247 */       throw new NonReadableChannelException(); 
/* 248 */     if (!paramBoolean && !this.writing) {
/* 249 */       throw new NonWritableChannelException();
/*     */     }
/*     */     
/* 252 */     FileLockImpl fileLockImpl = addToFileLockTable(paramLong1, paramLong2, paramBoolean);
/* 253 */     if (fileLockImpl == null) {
/* 254 */       throw new ClosedChannelException();
/*     */     }
/* 256 */     int i = this.threads.add();
/* 257 */     boolean bool = false; try {
/*     */       int j;
/* 259 */       begin();
/*     */       
/*     */       do {
/* 262 */         j = nd.lock(this.fdObj, false, paramLong1, paramLong2, paramBoolean);
/* 263 */       } while (j == 2 && isOpen());
/* 264 */       if (j == 0 && isOpen()) {
/* 265 */         bool = true;
/* 266 */         return fileLockImpl;
/*     */       } 
/* 268 */       if (j == -1)
/* 269 */         return null; 
/* 270 */       if (j == 2) {
/* 271 */         throw new AsynchronousCloseException();
/*     */       }
/* 273 */       throw new AssertionError();
/*     */     } finally {
/* 275 */       if (!bool)
/* 276 */         removeFromFileLockTable(fileLockImpl); 
/* 277 */       end();
/* 278 */       this.threads.remove(i);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void implRelease(FileLockImpl paramFileLockImpl) throws IOException {
/* 284 */     nd.release(this.fdObj, paramFileLockImpl.position(), paramFileLockImpl.size());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   <A> Future<Integer> implRead(final ByteBuffer dst, final long position, final A attachment, final CompletionHandler<Integer, ? super A> handler) {
/* 293 */     if (position < 0L)
/* 294 */       throw new IllegalArgumentException("Negative position"); 
/* 295 */     if (!this.reading)
/* 296 */       throw new NonReadableChannelException(); 
/* 297 */     if (dst.isReadOnly()) {
/* 298 */       throw new IllegalArgumentException("Read-only buffer");
/*     */     }
/*     */     
/* 301 */     if (!isOpen() || dst.remaining() == 0) {
/* 302 */       ClosedChannelException closedChannelException = isOpen() ? null : new ClosedChannelException();
/* 303 */       if (handler == null)
/* 304 */         return CompletedFuture.withResult(Integer.valueOf(0), closedChannelException); 
/* 305 */       Invoker.invokeIndirectly(handler, attachment, Integer.valueOf(0), closedChannelException, this.executor);
/* 306 */       return null;
/*     */     } 
/*     */     
/* 309 */     final PendingFuture<Integer> result = (handler == null) ? new PendingFuture<>(this) : null;
/*     */     
/* 311 */     Runnable runnable = new Runnable() {
/*     */         public void run() {
/* 313 */           int i = 0;
/* 314 */           IOException iOException = null;
/*     */           
/* 316 */           int j = SimpleAsynchronousFileChannelImpl.this.threads.add();
/*     */           try {
/* 318 */             SimpleAsynchronousFileChannelImpl.this.begin();
/*     */             do {
/* 320 */               i = IOUtil.read(SimpleAsynchronousFileChannelImpl.this.fdObj, dst, position, SimpleAsynchronousFileChannelImpl.nd);
/* 321 */             } while (i == -3 && SimpleAsynchronousFileChannelImpl.this.isOpen());
/* 322 */             if (i < 0 && !SimpleAsynchronousFileChannelImpl.this.isOpen())
/* 323 */               throw new AsynchronousCloseException(); 
/* 324 */           } catch (IOException iOException1) {
/* 325 */             if (!SimpleAsynchronousFileChannelImpl.this.isOpen())
/* 326 */               iOException1 = new AsynchronousCloseException(); 
/* 327 */             iOException = iOException1;
/*     */           } finally {
/* 329 */             SimpleAsynchronousFileChannelImpl.this.end();
/* 330 */             SimpleAsynchronousFileChannelImpl.this.threads.remove(j);
/*     */           } 
/* 332 */           if (handler == null) {
/* 333 */             result.setResult(Integer.valueOf(i), iOException);
/*     */           } else {
/* 335 */             Invoker.invokeUnchecked(handler, attachment, Integer.valueOf(i), iOException);
/*     */           } 
/*     */         }
/*     */       };
/* 339 */     this.executor.execute(runnable);
/* 340 */     return pendingFuture;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   <A> Future<Integer> implWrite(final ByteBuffer src, final long position, final A attachment, final CompletionHandler<Integer, ? super A> handler) {
/* 349 */     if (position < 0L)
/* 350 */       throw new IllegalArgumentException("Negative position"); 
/* 351 */     if (!this.writing) {
/* 352 */       throw new NonWritableChannelException();
/*     */     }
/*     */     
/* 355 */     if (!isOpen() || src.remaining() == 0) {
/* 356 */       ClosedChannelException closedChannelException = isOpen() ? null : new ClosedChannelException();
/* 357 */       if (handler == null)
/* 358 */         return CompletedFuture.withResult(Integer.valueOf(0), closedChannelException); 
/* 359 */       Invoker.invokeIndirectly(handler, attachment, Integer.valueOf(0), closedChannelException, this.executor);
/* 360 */       return null;
/*     */     } 
/*     */     
/* 363 */     final PendingFuture<Integer> result = (handler == null) ? new PendingFuture<>(this) : null;
/*     */     
/* 365 */     Runnable runnable = new Runnable() {
/*     */         public void run() {
/* 367 */           int i = 0;
/* 368 */           IOException iOException = null;
/*     */           
/* 370 */           int j = SimpleAsynchronousFileChannelImpl.this.threads.add();
/*     */           try {
/* 372 */             SimpleAsynchronousFileChannelImpl.this.begin();
/*     */             do {
/* 374 */               i = IOUtil.write(SimpleAsynchronousFileChannelImpl.this.fdObj, src, position, SimpleAsynchronousFileChannelImpl.nd);
/* 375 */             } while (i == -3 && SimpleAsynchronousFileChannelImpl.this.isOpen());
/* 376 */             if (i < 0 && !SimpleAsynchronousFileChannelImpl.this.isOpen())
/* 377 */               throw new AsynchronousCloseException(); 
/* 378 */           } catch (IOException iOException1) {
/* 379 */             if (!SimpleAsynchronousFileChannelImpl.this.isOpen())
/* 380 */               iOException1 = new AsynchronousCloseException(); 
/* 381 */             iOException = iOException1;
/*     */           } finally {
/* 383 */             SimpleAsynchronousFileChannelImpl.this.end();
/* 384 */             SimpleAsynchronousFileChannelImpl.this.threads.remove(j);
/*     */           } 
/* 386 */           if (handler == null) {
/* 387 */             result.setResult(Integer.valueOf(i), iOException);
/*     */           } else {
/* 389 */             Invoker.invokeUnchecked(handler, attachment, Integer.valueOf(i), iOException);
/*     */           } 
/*     */         }
/*     */       };
/* 393 */     this.executor.execute(runnable);
/* 394 */     return pendingFuture;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/nio/ch/SimpleAsynchronousFileChannelImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */