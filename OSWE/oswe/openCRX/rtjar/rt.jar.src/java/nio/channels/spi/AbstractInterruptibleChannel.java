/*     */ package java.nio.channels.spi;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.nio.channels.AsynchronousCloseException;
/*     */ import java.nio.channels.Channel;
/*     */ import java.nio.channels.ClosedByInterruptException;
/*     */ import java.nio.channels.InterruptibleChannel;
/*     */ import sun.misc.SharedSecrets;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class AbstractInterruptibleChannel
/*     */   implements Channel, InterruptibleChannel
/*     */ {
/*  91 */   private final Object closeLock = new Object();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private volatile boolean open = true;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Interruptible interruptor;
/*     */ 
/*     */ 
/*     */   
/*     */   private volatile Thread interrupted;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void close() throws IOException {
/* 111 */     synchronized (this.closeLock) {
/* 112 */       if (!this.open)
/*     */         return; 
/* 114 */       this.open = false;
/* 115 */       implCloseChannel();
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final boolean isOpen() {
/* 138 */     return this.open;
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
/*     */   protected final void begin() {
/* 156 */     if (this.interruptor == null)
/* 157 */       this.interruptor = new Interruptible() {
/*     */           public void interrupt(Thread param1Thread) {
/* 159 */             synchronized (AbstractInterruptibleChannel.this.closeLock) {
/* 160 */               if (!AbstractInterruptibleChannel.this.open)
/*     */                 return; 
/* 162 */               AbstractInterruptibleChannel.this.open = false;
/* 163 */               AbstractInterruptibleChannel.this.interrupted = param1Thread;
/*     */               try {
/* 165 */                 AbstractInterruptibleChannel.this.implCloseChannel();
/* 166 */               } catch (IOException iOException) {}
/*     */             } 
/*     */           }
/*     */         }; 
/* 170 */     blockedOn(this.interruptor);
/* 171 */     Thread thread = Thread.currentThread();
/* 172 */     if (thread.isInterrupted()) {
/* 173 */       this.interruptor.interrupt(thread);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final void end(boolean paramBoolean) throws AsynchronousCloseException {
/* 198 */     blockedOn(null);
/* 199 */     Thread thread = this.interrupted;
/* 200 */     if (thread != null && thread == Thread.currentThread()) {
/* 201 */       thread = null;
/* 202 */       throw new ClosedByInterruptException();
/*     */     } 
/* 204 */     if (!paramBoolean && !this.open) {
/* 205 */       throw new AsynchronousCloseException();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   static void blockedOn(Interruptible paramInterruptible) {
/* 211 */     SharedSecrets.getJavaLangAccess().blockedOn(Thread.currentThread(), paramInterruptible);
/*     */   }
/*     */   
/*     */   protected abstract void implCloseChannel() throws IOException;
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/nio/channels/spi/AbstractInterruptibleChannel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */