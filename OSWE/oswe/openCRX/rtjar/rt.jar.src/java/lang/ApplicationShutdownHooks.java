/*     */ package java.lang;
/*     */ 
/*     */ import java.util.IdentityHashMap;
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
/*     */ 
/*     */ 
/*     */ class ApplicationShutdownHooks
/*     */ {
/*     */   private static IdentityHashMap<Thread, Thread> hooks;
/*     */   
/*     */   static {
/*     */     try {
/*  42 */       Shutdown.add(1, false, new Runnable()
/*     */           {
/*     */             public void run()
/*     */             {
/*  46 */               ApplicationShutdownHooks.runHooks();
/*     */             }
/*     */           });
/*     */       
/*  50 */       hooks = new IdentityHashMap<>();
/*  51 */     } catch (IllegalStateException illegalStateException) {
/*     */ 
/*     */       
/*  54 */       hooks = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static synchronized void add(Thread paramThread) {
/*  65 */     if (hooks == null) {
/*  66 */       throw new IllegalStateException("Shutdown in progress");
/*     */     }
/*  68 */     if (paramThread.isAlive()) {
/*  69 */       throw new IllegalArgumentException("Hook already running");
/*     */     }
/*  71 */     if (hooks.containsKey(paramThread)) {
/*  72 */       throw new IllegalArgumentException("Hook previously registered");
/*     */     }
/*  74 */     hooks.put(paramThread, paramThread);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static synchronized boolean remove(Thread paramThread) {
/*  81 */     if (hooks == null) {
/*  82 */       throw new IllegalStateException("Shutdown in progress");
/*     */     }
/*  84 */     if (paramThread == null) {
/*  85 */       throw new NullPointerException();
/*     */     }
/*  87 */     return (hooks.remove(paramThread) != null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static void runHooks() {
/*     */     Set<Thread> set;
/*  96 */     synchronized (ApplicationShutdownHooks.class) {
/*  97 */       set = hooks.keySet();
/*  98 */       hooks = null;
/*     */     } 
/*     */     
/* 101 */     for (Thread thread : set) {
/* 102 */       thread.start();
/*     */     }
/* 104 */     label23: for (Thread thread : set) {
/*     */       while (true) {
/*     */         try {
/* 107 */           thread.join();
/*     */         }
/* 109 */         catch (InterruptedException interruptedException) {
/*     */           continue;
/*     */         } 
/*     */         continue label23;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/lang/ApplicationShutdownHooks.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */