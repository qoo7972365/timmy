/*     */ package java.nio.channels.spi;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.nio.channels.SelectionKey;
/*     */ import java.nio.channels.Selector;
/*     */ import java.util.HashSet;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.atomic.AtomicBoolean;
/*     */ import sun.nio.ch.Interruptible;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class AbstractSelector
/*     */   extends Selector
/*     */ {
/*  73 */   private AtomicBoolean selectorOpen = new AtomicBoolean(true);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final SelectorProvider provider;
/*     */ 
/*     */ 
/*     */   
/*     */   private final Set<SelectionKey> cancelledKeys;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected AbstractSelector(SelectorProvider paramSelectorProvider) {
/*  88 */     this.cancelledKeys = new HashSet<>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 194 */     this.interruptor = null;
/*     */     this.provider = paramSelectorProvider;
/*     */   } private Interruptible interruptor; void cancel(SelectionKey paramSelectionKey) {
/*     */     synchronized (this.cancelledKeys) {
/*     */       this.cancelledKeys.add(paramSelectionKey);
/*     */     } 
/*     */   } public final void close() throws IOException {
/*     */     boolean bool = this.selectorOpen.getAndSet(false);
/*     */     if (!bool)
/*     */       return; 
/*     */     implCloseSelector();
/*     */   } protected abstract void implCloseSelector() throws IOException;
/*     */   public final boolean isOpen() {
/*     */     return this.selectorOpen.get();
/*     */   }
/*     */   protected final void begin() {
/* 210 */     if (this.interruptor == null)
/* 211 */       this.interruptor = new Interruptible() {
/*     */           public void interrupt(Thread param1Thread) {
/* 213 */             AbstractSelector.this.wakeup();
/*     */           }
/*     */         }; 
/* 216 */     AbstractInterruptibleChannel.blockedOn(this.interruptor);
/* 217 */     Thread thread = Thread.currentThread();
/* 218 */     if (thread.isInterrupted())
/* 219 */       this.interruptor.interrupt(thread); 
/*     */   }
/*     */   
/*     */   public final SelectorProvider provider() {
/*     */     return this.provider;
/*     */   }
/*     */   
/*     */   protected final Set<SelectionKey> cancelledKeys() {
/*     */     return this.cancelledKeys;
/*     */   }
/*     */   
/*     */   protected final void end() {
/* 231 */     AbstractInterruptibleChannel.blockedOn(null);
/*     */   }
/*     */   
/*     */   protected abstract SelectionKey register(AbstractSelectableChannel paramAbstractSelectableChannel, int paramInt, Object paramObject);
/*     */   
/*     */   protected final void deregister(AbstractSelectionKey paramAbstractSelectionKey) {
/*     */     ((AbstractSelectableChannel)paramAbstractSelectionKey.channel()).removeKey(paramAbstractSelectionKey);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/nio/channels/spi/AbstractSelector.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */