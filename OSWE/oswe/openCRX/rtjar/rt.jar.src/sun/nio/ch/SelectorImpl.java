/*     */ package sun.nio.ch;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.net.SocketException;
/*     */ import java.nio.channels.ClosedSelectorException;
/*     */ import java.nio.channels.IllegalSelectorException;
/*     */ import java.nio.channels.SelectionKey;
/*     */ import java.nio.channels.Selector;
/*     */ import java.nio.channels.spi.AbstractSelectableChannel;
/*     */ import java.nio.channels.spi.AbstractSelector;
/*     */ import java.nio.channels.spi.SelectorProvider;
/*     */ import java.util.Collections;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.Set;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class SelectorImpl
/*     */   extends AbstractSelector
/*     */ {
/*     */   protected Set<SelectionKey> selectedKeys;
/*     */   protected HashSet<SelectionKey> keys;
/*     */   private Set<SelectionKey> publicKeys;
/*     */   private Set<SelectionKey> publicSelectedKeys;
/*     */   
/*     */   protected SelectorImpl(SelectorProvider paramSelectorProvider) {
/*  54 */     super(paramSelectorProvider);
/*  55 */     this.keys = new HashSet<>();
/*  56 */     this.selectedKeys = new HashSet<>();
/*  57 */     if (Util.atBugLevel("1.4")) {
/*  58 */       this.publicKeys = this.keys;
/*  59 */       this.publicSelectedKeys = this.selectedKeys;
/*     */     } else {
/*  61 */       this.publicKeys = Collections.unmodifiableSet(this.keys);
/*  62 */       this.publicSelectedKeys = Util.ungrowableSet(this.selectedKeys);
/*     */     } 
/*     */   }
/*     */   
/*     */   public Set<SelectionKey> keys() {
/*  67 */     if (!isOpen() && !Util.atBugLevel("1.4"))
/*  68 */       throw new ClosedSelectorException(); 
/*  69 */     return this.publicKeys;
/*     */   }
/*     */   
/*     */   public Set<SelectionKey> selectedKeys() {
/*  73 */     if (!isOpen() && !Util.atBugLevel("1.4"))
/*  74 */       throw new ClosedSelectorException(); 
/*  75 */     return this.publicSelectedKeys;
/*     */   }
/*     */   
/*     */   protected abstract int doSelect(long paramLong) throws IOException;
/*     */   
/*     */   private int lockAndDoSelect(long paramLong) throws IOException {
/*  81 */     synchronized (this) {
/*  82 */       if (!isOpen())
/*  83 */         throw new ClosedSelectorException(); 
/*  84 */       synchronized (this.publicKeys) {
/*  85 */         synchronized (this.publicSelectedKeys) {
/*  86 */           return doSelect(paramLong);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int select(long paramLong) throws IOException {
/*  95 */     if (paramLong < 0L)
/*  96 */       throw new IllegalArgumentException("Negative timeout"); 
/*  97 */     return lockAndDoSelect((paramLong == 0L) ? -1L : paramLong);
/*     */   }
/*     */   
/*     */   public int select() throws IOException {
/* 101 */     return select(0L);
/*     */   }
/*     */   
/*     */   public int selectNow() throws IOException {
/* 105 */     return lockAndDoSelect(0L);
/*     */   }
/*     */   
/*     */   public void implCloseSelector() throws IOException {
/* 109 */     wakeup();
/* 110 */     synchronized (this) {
/* 111 */       synchronized (this.publicKeys) {
/* 112 */         synchronized (this.publicSelectedKeys) {
/* 113 */           implClose();
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected abstract void implClose() throws IOException;
/*     */ 
/*     */   
/*     */   public void putEventOps(SelectionKeyImpl paramSelectionKeyImpl, int paramInt) {}
/*     */ 
/*     */   
/*     */   protected final SelectionKey register(AbstractSelectableChannel paramAbstractSelectableChannel, int paramInt, Object paramObject) {
/* 127 */     if (!(paramAbstractSelectableChannel instanceof SelChImpl))
/* 128 */       throw new IllegalSelectorException(); 
/* 129 */     SelectionKeyImpl selectionKeyImpl = new SelectionKeyImpl((SelChImpl)paramAbstractSelectableChannel, this);
/* 130 */     selectionKeyImpl.attach(paramObject);
/* 131 */     synchronized (this.publicKeys) {
/* 132 */       implRegister(selectionKeyImpl);
/*     */     } 
/* 134 */     selectionKeyImpl.interestOps(paramInt);
/* 135 */     return selectionKeyImpl;
/*     */   }
/*     */ 
/*     */   
/*     */   protected abstract void implRegister(SelectionKeyImpl paramSelectionKeyImpl);
/*     */   
/*     */   void processDeregisterQueue() throws IOException {
/* 142 */     Set<SelectionKey> set = cancelledKeys();
/* 143 */     synchronized (set) {
/* 144 */       if (!set.isEmpty()) {
/* 145 */         Iterator<SelectionKey> iterator = set.iterator();
/* 146 */         while (iterator.hasNext()) {
/* 147 */           SelectionKeyImpl selectionKeyImpl = (SelectionKeyImpl)iterator.next();
/*     */           try {
/* 149 */             implDereg(selectionKeyImpl);
/* 150 */           } catch (SocketException socketException) {
/* 151 */             throw new IOException("Error deregistering key", socketException);
/*     */           } finally {
/* 153 */             iterator.remove();
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   protected abstract void implDereg(SelectionKeyImpl paramSelectionKeyImpl) throws IOException;
/*     */   
/*     */   public abstract Selector wakeup();
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/nio/ch/SelectorImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */