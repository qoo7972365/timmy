/*     */ package sun.nio.ch;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.nio.channels.ClosedSelectorException;
/*     */ import java.nio.channels.Selector;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class PollSelectorImpl
/*     */   extends AbstractPollSelectorImpl
/*     */ {
/*     */   private int fd0;
/*     */   private int fd1;
/*  48 */   private Object interruptLock = new Object();
/*     */ 
/*     */   
/*     */   private boolean interruptTriggered = false;
/*     */ 
/*     */ 
/*     */   
/*     */   PollSelectorImpl(SelectorProvider paramSelectorProvider) {
/*  56 */     super(paramSelectorProvider, 1, 1);
/*  57 */     long l = IOUtil.makePipe(false);
/*  58 */     this.fd0 = (int)(l >>> 32L);
/*  59 */     this.fd1 = (int)l;
/*     */     try {
/*  61 */       this.pollWrapper = new PollArrayWrapper(10);
/*  62 */       this.pollWrapper.initInterrupt(this.fd0, this.fd1);
/*  63 */       this.channelArray = new SelectionKeyImpl[10];
/*  64 */     } catch (Throwable throwable) {
/*     */       try {
/*  66 */         FileDispatcherImpl.closeIntFD(this.fd0);
/*  67 */       } catch (IOException iOException) {
/*  68 */         throwable.addSuppressed(iOException);
/*     */       } 
/*     */       try {
/*  71 */         FileDispatcherImpl.closeIntFD(this.fd1);
/*  72 */       } catch (IOException iOException) {
/*  73 */         throwable.addSuppressed(iOException);
/*     */       } 
/*  75 */       throw throwable;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected int doSelect(long paramLong) throws IOException {
/*  82 */     if (this.channelArray == null)
/*  83 */       throw new ClosedSelectorException(); 
/*  84 */     processDeregisterQueue();
/*     */     try {
/*  86 */       begin();
/*  87 */       this.pollWrapper.poll(this.totalChannels, 0, paramLong);
/*     */     } finally {
/*  89 */       end();
/*     */     } 
/*  91 */     processDeregisterQueue();
/*  92 */     int i = updateSelectedKeys();
/*  93 */     if (this.pollWrapper.getReventOps(0) != 0) {
/*     */       
/*  95 */       this.pollWrapper.putReventOps(0, 0);
/*  96 */       synchronized (this.interruptLock) {
/*  97 */         IOUtil.drain(this.fd0);
/*  98 */         this.interruptTriggered = false;
/*     */       } 
/*     */     } 
/* 101 */     return i;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void implCloseInterrupt() throws IOException {
/* 106 */     synchronized (this.interruptLock) {
/* 107 */       this.interruptTriggered = true;
/*     */     } 
/* 109 */     FileDispatcherImpl.closeIntFD(this.fd0);
/* 110 */     FileDispatcherImpl.closeIntFD(this.fd1);
/* 111 */     this.fd0 = -1;
/* 112 */     this.fd1 = -1;
/* 113 */     this.pollWrapper.release(0);
/*     */   }
/*     */   
/*     */   public Selector wakeup() {
/* 117 */     synchronized (this.interruptLock) {
/* 118 */       if (!this.interruptTriggered) {
/* 119 */         this.pollWrapper.interrupt();
/* 120 */         this.interruptTriggered = true;
/*     */       } 
/*     */     } 
/* 123 */     return this;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/nio/ch/PollSelectorImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */