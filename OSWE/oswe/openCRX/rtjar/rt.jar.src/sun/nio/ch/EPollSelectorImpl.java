/*     */ package sun.nio.ch;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.nio.channels.ClosedSelectorException;
/*     */ import java.nio.channels.SelectableChannel;
/*     */ import java.nio.channels.SelectionKey;
/*     */ import java.nio.channels.Selector;
/*     */ import java.nio.channels.spi.SelectorProvider;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class EPollSelectorImpl
/*     */   extends SelectorImpl
/*     */ {
/*     */   protected int fd0;
/*     */   protected int fd1;
/*     */   EPollArrayWrapper pollWrapper;
/*     */   private Map<Integer, SelectionKeyImpl> fdToKey;
/*     */   private volatile boolean closed = false;
/*  56 */   private final Object interruptLock = new Object();
/*     */ 
/*     */   
/*     */   private boolean interruptTriggered = false;
/*     */ 
/*     */ 
/*     */   
/*     */   EPollSelectorImpl(SelectorProvider paramSelectorProvider) throws IOException {
/*  64 */     super(paramSelectorProvider);
/*  65 */     long l = IOUtil.makePipe(false);
/*  66 */     this.fd0 = (int)(l >>> 32L);
/*  67 */     this.fd1 = (int)l;
/*     */     try {
/*  69 */       this.pollWrapper = new EPollArrayWrapper();
/*  70 */       this.pollWrapper.initInterrupt(this.fd0, this.fd1);
/*  71 */       this.fdToKey = new HashMap<>();
/*  72 */     } catch (Throwable throwable) {
/*     */       try {
/*  74 */         FileDispatcherImpl.closeIntFD(this.fd0);
/*  75 */       } catch (IOException iOException) {
/*  76 */         throwable.addSuppressed(iOException);
/*     */       } 
/*     */       try {
/*  79 */         FileDispatcherImpl.closeIntFD(this.fd1);
/*  80 */       } catch (IOException iOException) {
/*  81 */         throwable.addSuppressed(iOException);
/*     */       } 
/*  83 */       throw throwable;
/*     */     } 
/*     */   }
/*     */   
/*     */   protected int doSelect(long paramLong) throws IOException {
/*  88 */     if (this.closed)
/*  89 */       throw new ClosedSelectorException(); 
/*  90 */     processDeregisterQueue();
/*     */     try {
/*  92 */       begin();
/*  93 */       this.pollWrapper.poll(paramLong);
/*     */     } finally {
/*  95 */       end();
/*     */     } 
/*  97 */     processDeregisterQueue();
/*  98 */     int i = updateSelectedKeys();
/*  99 */     if (this.pollWrapper.interrupted()) {
/*     */       
/* 101 */       this.pollWrapper.putEventOps(this.pollWrapper.interruptedIndex(), 0);
/* 102 */       synchronized (this.interruptLock) {
/* 103 */         this.pollWrapper.clearInterrupted();
/* 104 */         IOUtil.drain(this.fd0);
/* 105 */         this.interruptTriggered = false;
/*     */       } 
/*     */     } 
/* 108 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int updateSelectedKeys() {
/* 116 */     int i = this.pollWrapper.updated;
/* 117 */     byte b1 = 0;
/* 118 */     for (byte b2 = 0; b2 < i; b2++) {
/* 119 */       int j = this.pollWrapper.getDescriptor(b2);
/* 120 */       SelectionKeyImpl selectionKeyImpl = this.fdToKey.get(Integer.valueOf(j));
/*     */       
/* 122 */       if (selectionKeyImpl != null) {
/* 123 */         int k = this.pollWrapper.getEventOps(b2);
/* 124 */         if (this.selectedKeys.contains(selectionKeyImpl)) {
/* 125 */           if (selectionKeyImpl.channel.translateAndSetReadyOps(k, selectionKeyImpl)) {
/* 126 */             b1++;
/*     */           }
/*     */         } else {
/* 129 */           selectionKeyImpl.channel.translateAndSetReadyOps(k, selectionKeyImpl);
/* 130 */           if ((selectionKeyImpl.nioReadyOps() & selectionKeyImpl.nioInterestOps()) != 0) {
/* 131 */             this.selectedKeys.add(selectionKeyImpl);
/* 132 */             b1++;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/* 137 */     return b1;
/*     */   }
/*     */   
/*     */   protected void implClose() throws IOException {
/* 141 */     if (this.closed)
/*     */       return; 
/* 143 */     this.closed = true;
/*     */ 
/*     */     
/* 146 */     synchronized (this.interruptLock) {
/* 147 */       this.interruptTriggered = true;
/*     */     } 
/*     */     
/* 150 */     FileDispatcherImpl.closeIntFD(this.fd0);
/* 151 */     FileDispatcherImpl.closeIntFD(this.fd1);
/*     */     
/* 153 */     this.pollWrapper.closeEPollFD();
/*     */     
/* 155 */     this.selectedKeys = null;
/*     */ 
/*     */     
/* 158 */     Iterator<SelectionKey> iterator = this.keys.iterator();
/* 159 */     while (iterator.hasNext()) {
/* 160 */       SelectionKeyImpl selectionKeyImpl = (SelectionKeyImpl)iterator.next();
/* 161 */       deregister(selectionKeyImpl);
/* 162 */       SelectableChannel selectableChannel = selectionKeyImpl.channel();
/* 163 */       if (!selectableChannel.isOpen() && !selectableChannel.isRegistered())
/* 164 */         ((SelChImpl)selectableChannel).kill(); 
/* 165 */       iterator.remove();
/*     */     } 
/*     */     
/* 168 */     this.fd0 = -1;
/* 169 */     this.fd1 = -1;
/*     */   }
/*     */   
/*     */   protected void implRegister(SelectionKeyImpl paramSelectionKeyImpl) {
/* 173 */     if (this.closed)
/* 174 */       throw new ClosedSelectorException(); 
/* 175 */     SelChImpl selChImpl = paramSelectionKeyImpl.channel;
/* 176 */     int i = Integer.valueOf(selChImpl.getFDVal()).intValue();
/* 177 */     this.fdToKey.put(Integer.valueOf(i), paramSelectionKeyImpl);
/* 178 */     this.pollWrapper.add(i);
/* 179 */     this.keys.add(paramSelectionKeyImpl);
/*     */   }
/*     */   
/*     */   protected void implDereg(SelectionKeyImpl paramSelectionKeyImpl) throws IOException {
/* 183 */     assert paramSelectionKeyImpl.getIndex() >= 0;
/* 184 */     SelChImpl selChImpl = paramSelectionKeyImpl.channel;
/* 185 */     int i = selChImpl.getFDVal();
/* 186 */     this.fdToKey.remove(Integer.valueOf(i));
/* 187 */     this.pollWrapper.remove(i);
/* 188 */     paramSelectionKeyImpl.setIndex(-1);
/* 189 */     this.keys.remove(paramSelectionKeyImpl);
/* 190 */     this.selectedKeys.remove(paramSelectionKeyImpl);
/* 191 */     deregister(paramSelectionKeyImpl);
/* 192 */     SelectableChannel selectableChannel = paramSelectionKeyImpl.channel();
/* 193 */     if (!selectableChannel.isOpen() && !selectableChannel.isRegistered())
/* 194 */       ((SelChImpl)selectableChannel).kill(); 
/*     */   }
/*     */   
/*     */   public void putEventOps(SelectionKeyImpl paramSelectionKeyImpl, int paramInt) {
/* 198 */     if (this.closed)
/* 199 */       throw new ClosedSelectorException(); 
/* 200 */     SelChImpl selChImpl = paramSelectionKeyImpl.channel;
/* 201 */     this.pollWrapper.setInterest(selChImpl.getFDVal(), paramInt);
/*     */   }
/*     */   
/*     */   public Selector wakeup() {
/* 205 */     synchronized (this.interruptLock) {
/* 206 */       if (!this.interruptTriggered) {
/* 207 */         this.pollWrapper.interrupt();
/* 208 */         this.interruptTriggered = true;
/*     */       } 
/*     */     } 
/* 211 */     return this;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/nio/ch/EPollSelectorImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */