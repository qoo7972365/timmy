/*     */ package sun.nio.ch;
/*     */ 
/*     */ import java.io.FileDescriptor;
/*     */ import java.io.IOException;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.channels.ClosedChannelException;
/*     */ import java.nio.channels.Pipe;
/*     */ import java.nio.channels.spi.SelectorProvider;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class SourceChannelImpl
/*     */   extends Pipe.SourceChannel
/*     */   implements SelChImpl
/*     */ {
/*  40 */   private static final NativeDispatcher nd = new FileDispatcherImpl();
/*     */ 
/*     */ 
/*     */   
/*     */   FileDescriptor fd;
/*     */ 
/*     */   
/*     */   int fdVal;
/*     */ 
/*     */   
/*  50 */   private volatile long thread = 0L;
/*     */ 
/*     */   
/*  53 */   private final Object lock = new Object();
/*     */ 
/*     */ 
/*     */   
/*  57 */   private final Object stateLock = new Object();
/*     */   
/*     */   private static final int ST_UNINITIALIZED = -1;
/*     */   
/*     */   private static final int ST_INUSE = 0;
/*     */   
/*     */   private static final int ST_KILLED = 1;
/*     */   
/*  65 */   private volatile int state = -1;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FileDescriptor getFD() {
/*  71 */     return this.fd;
/*     */   }
/*     */   
/*     */   public int getFDVal() {
/*  75 */     return this.fdVal;
/*     */   }
/*     */   
/*     */   SourceChannelImpl(SelectorProvider paramSelectorProvider, FileDescriptor paramFileDescriptor) {
/*  79 */     super(paramSelectorProvider);
/*  80 */     this.fd = paramFileDescriptor;
/*  81 */     this.fdVal = IOUtil.fdVal(paramFileDescriptor);
/*  82 */     this.state = 0;
/*     */   }
/*     */   
/*     */   protected void implCloseSelectableChannel() throws IOException {
/*  86 */     synchronized (this.stateLock) {
/*  87 */       if (this.state != 1)
/*  88 */         nd.preClose(this.fd); 
/*  89 */       long l = this.thread;
/*  90 */       if (l != 0L)
/*  91 */         NativeThread.signal(l); 
/*  92 */       if (!isRegistered())
/*  93 */         kill(); 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void kill() throws IOException {
/*  98 */     synchronized (this.stateLock) {
/*  99 */       if (this.state == 1)
/*     */         return; 
/* 101 */       if (this.state == -1) {
/* 102 */         this.state = 1;
/*     */         return;
/*     */       } 
/* 105 */       assert !isOpen() && !isRegistered();
/* 106 */       nd.close(this.fd);
/* 107 */       this.state = 1;
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void implConfigureBlocking(boolean paramBoolean) throws IOException {
/* 112 */     IOUtil.configureBlocking(this.fd, paramBoolean);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean translateReadyOps(int paramInt1, int paramInt2, SelectionKeyImpl paramSelectionKeyImpl) {
/* 117 */     int i = paramSelectionKeyImpl.nioInterestOps();
/* 118 */     int j = paramSelectionKeyImpl.nioReadyOps();
/* 119 */     int k = paramInt2;
/*     */     
/* 121 */     if ((paramInt1 & Net.POLLNVAL) != 0) {
/* 122 */       throw new Error("POLLNVAL detected");
/*     */     }
/* 124 */     if ((paramInt1 & (Net.POLLERR | Net.POLLHUP)) != 0) {
/* 125 */       k = i;
/* 126 */       paramSelectionKeyImpl.nioReadyOps(k);
/* 127 */       return ((k & (j ^ 0xFFFFFFFF)) != 0);
/*     */     } 
/*     */     
/* 130 */     if ((paramInt1 & Net.POLLIN) != 0 && (i & 0x1) != 0)
/*     */     {
/* 132 */       k |= 0x1;
/*     */     }
/* 134 */     paramSelectionKeyImpl.nioReadyOps(k);
/* 135 */     return ((k & (j ^ 0xFFFFFFFF)) != 0);
/*     */   }
/*     */   
/*     */   public boolean translateAndUpdateReadyOps(int paramInt, SelectionKeyImpl paramSelectionKeyImpl) {
/* 139 */     return translateReadyOps(paramInt, paramSelectionKeyImpl.nioReadyOps(), paramSelectionKeyImpl);
/*     */   }
/*     */   
/*     */   public boolean translateAndSetReadyOps(int paramInt, SelectionKeyImpl paramSelectionKeyImpl) {
/* 143 */     return translateReadyOps(paramInt, 0, paramSelectionKeyImpl);
/*     */   }
/*     */   
/*     */   public void translateAndSetInterestOps(int paramInt, SelectionKeyImpl paramSelectionKeyImpl) {
/* 147 */     if (paramInt == 1)
/* 148 */       paramInt = Net.POLLIN; 
/* 149 */     paramSelectionKeyImpl.selector.putEventOps(paramSelectionKeyImpl, paramInt);
/*     */   }
/*     */   
/*     */   private void ensureOpen() throws IOException {
/* 153 */     if (!isOpen())
/* 154 */       throw new ClosedChannelException(); 
/*     */   }
/*     */   
/*     */   public int read(ByteBuffer paramByteBuffer) throws IOException {
/* 158 */     ensureOpen();
/* 159 */     synchronized (this.lock) {
/* 160 */       int i = 0;
/*     */       try {
/* 162 */         begin();
/* 163 */         if (!isOpen())
/* 164 */           return 0; 
/* 165 */         this.thread = NativeThread.current();
/*     */         do {
/* 167 */           i = IOUtil.read(this.fd, paramByteBuffer, -1L, nd);
/* 168 */         } while (i == -3 && isOpen());
/* 169 */         return IOStatus.normalize(i);
/*     */       } finally {
/* 171 */         this.thread = 0L;
/* 172 */         end((i > 0 || i == -2));
/* 173 */         assert IOStatus.check(i);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public long read(ByteBuffer[] paramArrayOfByteBuffer, int paramInt1, int paramInt2) throws IOException {
/* 181 */     if (paramInt1 < 0 || paramInt2 < 0 || paramInt1 > paramArrayOfByteBuffer.length - paramInt2)
/* 182 */       throw new IndexOutOfBoundsException(); 
/* 183 */     return read(Util.subsequence(paramArrayOfByteBuffer, paramInt1, paramInt2));
/*     */   }
/*     */   
/*     */   public long read(ByteBuffer[] paramArrayOfByteBuffer) throws IOException {
/* 187 */     if (paramArrayOfByteBuffer == null)
/* 188 */       throw new NullPointerException(); 
/* 189 */     ensureOpen();
/* 190 */     synchronized (this.lock) {
/* 191 */       long l = 0L;
/*     */       try {
/* 193 */         begin();
/* 194 */         if (!isOpen())
/* 195 */           return 0L; 
/* 196 */         this.thread = NativeThread.current();
/*     */         do {
/* 198 */           l = IOUtil.read(this.fd, paramArrayOfByteBuffer, nd);
/* 199 */         } while (l == -3L && isOpen());
/* 200 */         return IOStatus.normalize(l);
/*     */       } finally {
/* 202 */         this.thread = 0L;
/* 203 */         end((l > 0L || l == -2L));
/* 204 */         assert IOStatus.check(l);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/nio/ch/SourceChannelImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */