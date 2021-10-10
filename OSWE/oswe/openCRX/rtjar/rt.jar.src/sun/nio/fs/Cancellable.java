/*     */ package sun.nio.fs;
/*     */ 
/*     */ import java.util.concurrent.ExecutionException;
/*     */ import sun.misc.Unsafe;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ abstract class Cancellable
/*     */   implements Runnable
/*     */ {
/*  39 */   private static final Unsafe unsafe = Unsafe.getUnsafe();
/*     */   
/*     */   private final long pollingAddress;
/*  42 */   private final Object lock = new Object();
/*     */   
/*     */   private boolean completed;
/*     */   
/*     */   private Throwable exception;
/*     */   
/*     */   protected Cancellable() {
/*  49 */     this.pollingAddress = unsafe.allocateMemory(4L);
/*  50 */     unsafe.putIntVolatile(null, this.pollingAddress, 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected long addressToPollForCancel() {
/*  58 */     return this.pollingAddress;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int cancelValue() {
/*  67 */     return Integer.MAX_VALUE;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   final void cancel() {
/*  75 */     synchronized (this.lock) {
/*  76 */       if (!this.completed) {
/*  77 */         unsafe.putIntVolatile(null, this.pollingAddress, cancelValue());
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Throwable exception() {
/*  87 */     synchronized (this.lock) {
/*  88 */       return this.exception;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public final void run() {
/*     */     try {
/*  95 */       implRun();
/*  96 */     } catch (Throwable throwable) {
/*  97 */       synchronized (this.lock) {
/*  98 */         this.exception = throwable;
/*     */       } 
/*     */     } finally {
/* 101 */       synchronized (this.lock) {
/* 102 */         this.completed = true;
/* 103 */         unsafe.freeMemory(this.pollingAddress);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   abstract void implRun() throws Throwable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static void runInterruptibly(Cancellable paramCancellable) throws ExecutionException {
/* 120 */     Thread thread = new Thread(paramCancellable);
/* 121 */     thread.start();
/* 122 */     boolean bool = false;
/* 123 */     while (thread.isAlive()) {
/*     */       try {
/* 125 */         thread.join();
/* 126 */       } catch (InterruptedException interruptedException) {
/* 127 */         bool = true;
/* 128 */         paramCancellable.cancel();
/*     */       } 
/*     */     } 
/* 131 */     if (bool)
/* 132 */       Thread.currentThread().interrupt(); 
/* 133 */     Throwable throwable = paramCancellable.exception();
/* 134 */     if (throwable != null)
/* 135 */       throw new ExecutionException(throwable); 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/nio/fs/Cancellable.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */