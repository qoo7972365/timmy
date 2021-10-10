/*     */ package javax.swing.text;
/*     */ 
/*     */ import java.util.Vector;
/*     */ import sun.awt.AppContext;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class LayoutQueue
/*     */ {
/*  39 */   private static final Object DEFAULT_QUEUE = new Object();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  48 */   private Vector<Runnable> tasks = new Vector<>();
/*     */ 
/*     */   
/*     */   private Thread worker;
/*     */ 
/*     */   
/*     */   public static LayoutQueue getDefaultQueue() {
/*  55 */     AppContext appContext = AppContext.getAppContext();
/*  56 */     synchronized (DEFAULT_QUEUE) {
/*  57 */       LayoutQueue layoutQueue = (LayoutQueue)appContext.get(DEFAULT_QUEUE);
/*  58 */       if (layoutQueue == null) {
/*  59 */         layoutQueue = new LayoutQueue();
/*  60 */         appContext.put(DEFAULT_QUEUE, layoutQueue);
/*     */       } 
/*  62 */       return layoutQueue;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void setDefaultQueue(LayoutQueue paramLayoutQueue) {
/*  72 */     synchronized (DEFAULT_QUEUE) {
/*  73 */       AppContext.getAppContext().put(DEFAULT_QUEUE, paramLayoutQueue);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void addTask(Runnable paramRunnable) {
/*  82 */     if (this.worker == null) {
/*  83 */       this.worker = new LayoutThread();
/*  84 */       this.worker.start();
/*     */     } 
/*  86 */     this.tasks.addElement(paramRunnable);
/*  87 */     notifyAll();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected synchronized Runnable waitForWork() {
/*  94 */     while (this.tasks.size() == 0) {
/*     */       try {
/*  96 */         wait();
/*  97 */       } catch (InterruptedException interruptedException) {
/*  98 */         return null;
/*     */       } 
/*     */     } 
/* 101 */     Runnable runnable = this.tasks.firstElement();
/* 102 */     this.tasks.removeElementAt(0);
/* 103 */     return runnable;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   class LayoutThread
/*     */     extends Thread
/*     */   {
/*     */     LayoutThread() {
/* 112 */       super("text-layout");
/* 113 */       setPriority(1);
/*     */     }
/*     */     
/*     */     public void run() {
/*     */       Runnable runnable;
/*     */       do {
/* 119 */         runnable = LayoutQueue.this.waitForWork();
/* 120 */         if (runnable == null)
/* 121 */           continue;  runnable.run();
/*     */       }
/* 123 */       while (runnable != null);
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/text/LayoutQueue.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */