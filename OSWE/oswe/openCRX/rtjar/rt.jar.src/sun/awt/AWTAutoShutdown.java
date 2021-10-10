/*     */ package sun.awt;
/*     */ 
/*     */ import java.awt.AWTEvent;
/*     */ import java.security.AccessController;
/*     */ import java.util.HashSet;
/*     */ import java.util.IdentityHashMap;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import sun.misc.ThreadGroupUtils;
/*     */ import sun.util.logging.PlatformLogger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class AWTAutoShutdown
/*     */   implements Runnable
/*     */ {
/*  68 */   private static final AWTAutoShutdown theInstance = new AWTAutoShutdown();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  73 */   private final Object mainLock = new Object();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  81 */   private final Object activationLock = new Object();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  89 */   private final Set<Thread> busyThreadSet = new HashSet<>(7);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean toolkitThreadBusy = false;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 101 */   private final Map<Object, Object> peerMap = new IdentityHashMap<>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 107 */   private Thread blockerThread = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean timeoutPassed = false;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final int SAFETY_TIMEOUT = 1000;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static AWTAutoShutdown getInstance() {
/* 133 */     return theInstance;
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
/*     */   public static void notifyToolkitThreadBusy() {
/* 145 */     getInstance().setToolkitBusy(true);
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
/*     */   public static void notifyToolkitThreadFree() {
/* 157 */     getInstance().setToolkitBusy(false);
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
/*     */   public void notifyThreadBusy(Thread paramThread) {
/* 170 */     if (paramThread == null) {
/*     */       return;
/*     */     }
/* 173 */     synchronized (this.activationLock) {
/* 174 */       synchronized (this.mainLock) {
/* 175 */         if (this.blockerThread == null) {
/* 176 */           activateBlockerThread();
/* 177 */         } else if (isReadyToShutdown()) {
/* 178 */           this.mainLock.notifyAll();
/* 179 */           this.timeoutPassed = false;
/*     */         } 
/* 181 */         this.busyThreadSet.add(paramThread);
/*     */       } 
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
/*     */   public void notifyThreadFree(Thread paramThread) {
/* 196 */     if (paramThread == null) {
/*     */       return;
/*     */     }
/* 199 */     synchronized (this.activationLock) {
/* 200 */       synchronized (this.mainLock) {
/* 201 */         this.busyThreadSet.remove(paramThread);
/* 202 */         if (isReadyToShutdown()) {
/* 203 */           this.mainLock.notifyAll();
/* 204 */           this.timeoutPassed = false;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void notifyPeerMapUpdated() {
/* 217 */     synchronized (this.activationLock) {
/* 218 */       synchronized (this.mainLock) {
/* 219 */         if (!isReadyToShutdown() && this.blockerThread == null) {
/* 220 */           AccessController.doPrivileged(() -> {
/*     */                 activateBlockerThread();
/*     */                 return null;
/*     */               });
/*     */         } else {
/* 225 */           this.mainLock.notifyAll();
/* 226 */           this.timeoutPassed = false;
/*     */         } 
/*     */       } 
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
/*     */   private boolean isReadyToShutdown() {
/* 241 */     return (!this.toolkitThreadBusy && this.peerMap
/* 242 */       .isEmpty() && this.busyThreadSet
/* 243 */       .isEmpty());
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
/*     */   private void setToolkitBusy(boolean paramBoolean) {
/* 256 */     if (paramBoolean != this.toolkitThreadBusy) {
/* 257 */       synchronized (this.activationLock) {
/* 258 */         synchronized (this.mainLock) {
/* 259 */           if (paramBoolean != this.toolkitThreadBusy) {
/* 260 */             if (paramBoolean) {
/* 261 */               if (this.blockerThread == null) {
/* 262 */                 activateBlockerThread();
/* 263 */               } else if (isReadyToShutdown()) {
/* 264 */                 this.mainLock.notifyAll();
/* 265 */                 this.timeoutPassed = false;
/*     */               } 
/* 267 */               this.toolkitThreadBusy = paramBoolean;
/*     */             } else {
/* 269 */               this.toolkitThreadBusy = paramBoolean;
/* 270 */               if (isReadyToShutdown()) {
/* 271 */                 this.mainLock.notifyAll();
/* 272 */                 this.timeoutPassed = false;
/*     */               } 
/*     */             } 
/*     */           }
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void run() {
/* 288 */     Thread thread = Thread.currentThread();
/* 289 */     boolean bool = false;
/* 290 */     synchronized (this.mainLock) {
/*     */       
/*     */       try {
/* 293 */         this.mainLock.notifyAll();
/* 294 */         while (this.blockerThread == thread) {
/* 295 */           this.mainLock.wait();
/* 296 */           this.timeoutPassed = false;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 307 */           while (isReadyToShutdown()) {
/* 308 */             if (this.timeoutPassed) {
/* 309 */               this.timeoutPassed = false;
/* 310 */               this.blockerThread = null;
/*     */               break;
/*     */             } 
/* 313 */             this.timeoutPassed = true;
/* 314 */             this.mainLock.wait(1000L);
/*     */           } 
/*     */         } 
/* 317 */       } catch (InterruptedException interruptedException) {
/* 318 */         bool = true;
/*     */       } finally {
/* 320 */         if (this.blockerThread == thread) {
/* 321 */           this.blockerThread = null;
/*     */         }
/*     */       } 
/*     */     } 
/* 325 */     if (!bool) {
/* 326 */       AppContext.stopEventDispatchThreads();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   static AWTEvent getShutdownEvent() {
/* 332 */     return new AWTEvent(getInstance(), 0)
/*     */       {
/*     */       
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void activateBlockerThread() {
/* 343 */     Thread thread = new Thread(ThreadGroupUtils.getRootThreadGroup(), this, "AWT-Shutdown");
/* 344 */     thread.setContextClassLoader(null);
/* 345 */     thread.setDaemon(false);
/* 346 */     this.blockerThread = thread;
/* 347 */     thread.start();
/*     */     
/*     */     try {
/* 350 */       this.mainLock.wait();
/* 351 */     } catch (InterruptedException interruptedException) {
/* 352 */       System.err.println("AWT blocker activation interrupted:");
/* 353 */       interruptedException.printStackTrace();
/*     */     } 
/*     */   }
/*     */   
/*     */   final void registerPeer(Object paramObject1, Object paramObject2) {
/* 358 */     synchronized (this.activationLock) {
/* 359 */       synchronized (this.mainLock) {
/* 360 */         this.peerMap.put(paramObject1, paramObject2);
/* 361 */         notifyPeerMapUpdated();
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   final void unregisterPeer(Object paramObject1, Object paramObject2) {
/* 367 */     synchronized (this.activationLock) {
/* 368 */       synchronized (this.mainLock) {
/* 369 */         if (this.peerMap.get(paramObject1) == paramObject2) {
/* 370 */           this.peerMap.remove(paramObject1);
/* 371 */           notifyPeerMapUpdated();
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   final Object getPeer(Object paramObject) {
/* 378 */     synchronized (this.activationLock) {
/* 379 */       synchronized (this.mainLock) {
/* 380 */         return this.peerMap.get(paramObject);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   final void dumpPeers(PlatformLogger paramPlatformLogger) {
/* 386 */     if (paramPlatformLogger.isLoggable(PlatformLogger.Level.FINE))
/* 387 */       synchronized (this.activationLock) {
/* 388 */         synchronized (this.mainLock) {
/* 389 */           paramPlatformLogger.fine("Mapped peers:");
/* 390 */           for (String str : this.peerMap.keySet())
/* 391 */             paramPlatformLogger.fine(str + "->" + this.peerMap.get(str)); 
/*     */         } 
/*     */       }  
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/awt/AWTAutoShutdown.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */