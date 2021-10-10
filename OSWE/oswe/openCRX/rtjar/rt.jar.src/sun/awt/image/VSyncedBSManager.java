/*     */ package sun.awt.image;
/*     */ 
/*     */ import java.awt.image.BufferStrategy;
/*     */ import java.lang.ref.WeakReference;
/*     */ import java.security.AccessController;
/*     */ import sun.security.action.GetPropertyAction;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class VSyncedBSManager
/*     */ {
/*     */   private static VSyncedBSManager theInstance;
/*  39 */   private static final boolean vSyncLimit = Boolean.valueOf(AccessController.<String>doPrivileged(new GetPropertyAction("sun.java2d.vsynclimit", "true"))).booleanValue();
/*     */ 
/*     */ 
/*     */   
/*     */   private static VSyncedBSManager getInstance(boolean paramBoolean) {
/*  44 */     if (theInstance == null && paramBoolean) {
/*  45 */       theInstance = vSyncLimit ? new SingleVSyncedBSMgr() : new NoLimitVSyncBSMgr();
/*     */     }
/*     */     
/*  48 */     return theInstance;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   abstract boolean checkAllowed(BufferStrategy paramBufferStrategy);
/*     */ 
/*     */ 
/*     */   
/*     */   abstract void relinquishVsync(BufferStrategy paramBufferStrategy);
/*     */ 
/*     */   
/*     */   public static boolean vsyncAllowed(BufferStrategy paramBufferStrategy) {
/*  61 */     VSyncedBSManager vSyncedBSManager = getInstance(true);
/*  62 */     return vSyncedBSManager.checkAllowed(paramBufferStrategy);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static synchronized void releaseVsync(BufferStrategy paramBufferStrategy) {
/*  70 */     VSyncedBSManager vSyncedBSManager = getInstance(false);
/*  71 */     if (vSyncedBSManager != null) {
/*  72 */       vSyncedBSManager.relinquishVsync(paramBufferStrategy);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private static final class NoLimitVSyncBSMgr
/*     */     extends VSyncedBSManager
/*     */   {
/*     */     private NoLimitVSyncBSMgr() {}
/*     */     
/*     */     boolean checkAllowed(BufferStrategy param1BufferStrategy) {
/*  83 */       return true;
/*     */     }
/*     */ 
/*     */     
/*     */     void relinquishVsync(BufferStrategy param1BufferStrategy) {}
/*     */   }
/*     */ 
/*     */   
/*     */   private static final class SingleVSyncedBSMgr
/*     */     extends VSyncedBSManager
/*     */   {
/*     */     private WeakReference<BufferStrategy> strategy;
/*     */ 
/*     */     
/*     */     private SingleVSyncedBSMgr() {}
/*     */     
/*     */     public synchronized boolean checkAllowed(BufferStrategy param1BufferStrategy) {
/* 100 */       if (this.strategy != null) {
/* 101 */         BufferStrategy bufferStrategy = this.strategy.get();
/* 102 */         if (bufferStrategy != null) {
/* 103 */           return (bufferStrategy == param1BufferStrategy);
/*     */         }
/*     */       } 
/* 106 */       this.strategy = new WeakReference<>(param1BufferStrategy);
/* 107 */       return true;
/*     */     }
/*     */ 
/*     */     
/*     */     public synchronized void relinquishVsync(BufferStrategy param1BufferStrategy) {
/* 112 */       if (this.strategy != null) {
/* 113 */         BufferStrategy bufferStrategy = this.strategy.get();
/* 114 */         if (bufferStrategy == param1BufferStrategy) {
/* 115 */           this.strategy.clear();
/* 116 */           this.strategy = null;
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/awt/image/VSyncedBSManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */