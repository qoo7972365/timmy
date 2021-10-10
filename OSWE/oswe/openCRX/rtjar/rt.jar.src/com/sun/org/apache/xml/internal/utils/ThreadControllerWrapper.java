/*     */ package com.sun.org.apache.xml.internal.utils;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ThreadControllerWrapper
/*     */ {
/*  33 */   private static ThreadController m_tpool = new ThreadController();
/*     */ 
/*     */   
/*     */   public static Thread runThread(Runnable runnable, int priority) {
/*  37 */     return m_tpool.run(runnable, priority);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void waitThread(Thread worker, Runnable task) throws InterruptedException {
/*  43 */     m_tpool.waitThread(worker, task);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class ThreadController
/*     */   {
/*     */     final class SafeThread
/*     */       extends Thread
/*     */     {
/*     */       private volatile boolean ran = false;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       public SafeThread(Runnable target) {
/*  62 */         super(target);
/*     */       }
/*     */       
/*     */       public final void run() {
/*  66 */         if (Thread.currentThread() != this) {
/*  67 */           throw new IllegalStateException("The run() method in a SafeThread cannot be called from another thread.");
/*     */         }
/*     */         
/*  70 */         synchronized (this) {
/*  71 */           if (!this.ran) {
/*  72 */             this.ran = true;
/*     */           } else {
/*     */             
/*  75 */             throw new IllegalStateException("The run() method in a SafeThread cannot be called more than once.");
/*     */           } 
/*     */         } 
/*     */         
/*  79 */         super.run();
/*     */       }
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Thread run(Runnable task, int priority) {
/*  99 */       Thread t = new SafeThread(task);
/*     */       
/* 101 */       t.start();
/*     */ 
/*     */ 
/*     */       
/* 105 */       return t;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void waitThread(Thread worker, Runnable task) throws InterruptedException {
/* 122 */       worker.join();
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xml/internal/utils/ThreadControllerWrapper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */