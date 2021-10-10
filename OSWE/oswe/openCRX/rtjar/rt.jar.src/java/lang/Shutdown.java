/*     */ package java.lang;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class Shutdown
/*     */ {
/*     */   private static final int RUNNING = 0;
/*     */   private static final int HOOKS = 1;
/*     */   private static final int FINALIZERS = 2;
/*  43 */   private static int state = 0;
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean runFinalizersOnExit = false;
/*     */ 
/*     */ 
/*     */   
/*     */   private static final int MAX_SYSTEM_HOOKS = 10;
/*     */ 
/*     */   
/*  54 */   private static final Runnable[] hooks = new Runnable[10];
/*     */ 
/*     */   
/*  57 */   private static int currentRunningHook = 0;
/*     */ 
/*     */ 
/*     */   
/*  61 */   private static Object lock = new Lock();
/*     */   private static class Lock {
/*     */     private Lock() {} }
/*  64 */   private static Object haltLock = new Lock();
/*     */ 
/*     */   
/*     */   static void setRunFinalizersOnExit(boolean paramBoolean) {
/*  68 */     synchronized (lock) {
/*  69 */       runFinalizersOnExit = paramBoolean;
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
/*     */   
/*     */   static void add(int paramInt, boolean paramBoolean, Runnable paramRunnable) {
/*  95 */     synchronized (lock) {
/*  96 */       if (hooks[paramInt] != null) {
/*  97 */         throw new InternalError("Shutdown hook at slot " + paramInt + " already registered");
/*     */       }
/*  99 */       if (!paramBoolean) {
/* 100 */         if (state > 0) {
/* 101 */           throw new IllegalStateException("Shutdown in progress");
/*     */         }
/* 103 */       } else if (state > 1 || (state == 1 && paramInt <= currentRunningHook)) {
/* 104 */         throw new IllegalStateException("Shutdown in progress");
/*     */       } 
/*     */       
/* 107 */       hooks[paramInt] = paramRunnable;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static void runHooks() {
/* 114 */     for (byte b = 0; b < 10; b++) {
/*     */       try {
/*     */         Runnable runnable;
/* 117 */         synchronized (lock) {
/*     */ 
/*     */           
/* 120 */           currentRunningHook = b;
/* 121 */           runnable = hooks[b];
/*     */         } 
/* 123 */         if (runnable != null) runnable.run(); 
/* 124 */       } catch (Throwable throwable) {
/* 125 */         if (throwable instanceof ThreadDeath) {
/* 126 */           ThreadDeath threadDeath = (ThreadDeath)throwable;
/* 127 */           throw threadDeath;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static void halt(int paramInt) {
/* 138 */     synchronized (haltLock) {
/* 139 */       halt0(paramInt);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static native void halt0(int paramInt);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static native void runAllFinalizers();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void sequence() {
/*     */     boolean bool;
/* 161 */     synchronized (lock) {
/*     */ 
/*     */ 
/*     */       
/* 165 */       if (state != 1)
/*     */         return; 
/* 167 */     }  runHooks();
/*     */     
/* 169 */     synchronized (lock) {
/* 170 */       state = 2;
/* 171 */       bool = runFinalizersOnExit;
/*     */     } 
/* 173 */     if (bool) runAllFinalizers();
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static void exit(int paramInt) {
/* 182 */     boolean bool = false;
/* 183 */     synchronized (lock) {
/* 184 */       if (paramInt != 0) runFinalizersOnExit = false; 
/* 185 */       switch (state) {
/*     */         case 0:
/* 187 */           state = 1;
/*     */           break;
/*     */ 
/*     */         
/*     */         case 2:
/* 192 */           if (paramInt != 0) {
/*     */             
/* 194 */             halt(paramInt);
/*     */             
/*     */             break;
/*     */           } 
/*     */           
/* 199 */           bool = runFinalizersOnExit;
/*     */           break;
/*     */       } 
/*     */     
/*     */     } 
/* 204 */     if (bool) {
/* 205 */       runAllFinalizers();
/* 206 */       halt(paramInt);
/*     */     } 
/* 208 */     synchronized (Shutdown.class) {
/*     */ 
/*     */ 
/*     */       
/* 212 */       sequence();
/* 213 */       halt(paramInt);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static void shutdown() {
/* 223 */     synchronized (lock) {
/* 224 */       switch (state) {
/*     */         case 0:
/* 226 */           state = 1;
/*     */           break;
/*     */       } 
/*     */ 
/*     */ 
/*     */     
/*     */     } 
/* 233 */     synchronized (Shutdown.class) {
/* 234 */       sequence();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/lang/Shutdown.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */