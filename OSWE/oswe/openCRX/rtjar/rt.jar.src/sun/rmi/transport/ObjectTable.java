/*     */ package sun.rmi.transport;
/*     */ 
/*     */ import java.lang.ref.ReferenceQueue;
/*     */ import java.rmi.NoSuchObjectException;
/*     */ import java.rmi.Remote;
/*     */ import java.rmi.dgc.VMID;
/*     */ import java.rmi.server.ExportException;
/*     */ import java.rmi.server.ObjID;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import sun.misc.GC;
/*     */ import sun.rmi.runtime.Log;
/*     */ import sun.rmi.runtime.NewThreadAction;
/*     */ import sun.security.action.GetLongAction;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class ObjectTable
/*     */ {
/*  54 */   private static final long gcInterval = ((Long)AccessController.<Long>doPrivileged(new GetLongAction("sun.rmi.dgc.server.gcInterval", 3600000L))).longValue();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  61 */   private static final Object tableLock = new Object();
/*     */ 
/*     */   
/*  64 */   private static final Map<ObjectEndpoint, Target> objTable = new HashMap<>();
/*     */   
/*  66 */   private static final Map<WeakRef, Target> implTable = new HashMap<>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  73 */   private static final Object keepAliveLock = new Object();
/*     */ 
/*     */   
/*  76 */   private static int keepAliveCount = 0;
/*     */ 
/*     */   
/*  79 */   private static Thread reaper = null;
/*     */ 
/*     */   
/*  82 */   static final ReferenceQueue<Object> reapQueue = new ReferenceQueue();
/*     */ 
/*     */   
/*  85 */   private static GC.LatencyRequest gcLatencyRequest = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static Target getTarget(ObjectEndpoint paramObjectEndpoint) {
/*  96 */     synchronized (tableLock) {
/*  97 */       return objTable.get(paramObjectEndpoint);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Target getTarget(Remote paramRemote) {
/* 105 */     synchronized (tableLock) {
/* 106 */       return implTable.get(new WeakRef(paramRemote));
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
/*     */   public static Remote getStub(Remote paramRemote) throws NoSuchObjectException {
/* 122 */     Target target = getTarget(paramRemote);
/* 123 */     if (target == null) {
/* 124 */       throw new NoSuchObjectException("object not exported");
/*     */     }
/* 126 */     return target.getStub();
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
/*     */   public static boolean unexportObject(Remote paramRemote, boolean paramBoolean) throws NoSuchObjectException {
/* 150 */     synchronized (tableLock) {
/* 151 */       Target target = getTarget(paramRemote);
/* 152 */       if (target == null) {
/* 153 */         throw new NoSuchObjectException("object not exported");
/*     */       }
/* 155 */       if (target.unexport(paramBoolean)) {
/* 156 */         removeTarget(target);
/* 157 */         return true;
/*     */       } 
/* 159 */       return false;
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
/*     */   static void putTarget(Target paramTarget) throws ExportException {
/* 171 */     ObjectEndpoint objectEndpoint = paramTarget.getObjectEndpoint();
/* 172 */     WeakRef weakRef = paramTarget.getWeakImpl();
/*     */     
/* 174 */     if (DGCImpl.dgcLog.isLoggable(Log.VERBOSE)) {
/* 175 */       DGCImpl.dgcLog.log(Log.VERBOSE, "add object " + objectEndpoint);
/*     */     }
/*     */     
/* 178 */     synchronized (tableLock) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 184 */       if (paramTarget.getImpl() != null) {
/* 185 */         if (objTable.containsKey(objectEndpoint)) {
/* 186 */           throw new ExportException("internal error: ObjID already in use");
/*     */         }
/* 188 */         if (implTable.containsKey(weakRef)) {
/* 189 */           throw new ExportException("object already exported");
/*     */         }
/*     */         
/* 192 */         objTable.put(objectEndpoint, paramTarget);
/* 193 */         implTable.put(weakRef, paramTarget);
/*     */         
/* 195 */         if (!paramTarget.isPermanent()) {
/* 196 */           incrementKeepAliveCount();
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
/*     */   private static void removeTarget(Target paramTarget) {
/* 211 */     ObjectEndpoint objectEndpoint = paramTarget.getObjectEndpoint();
/* 212 */     WeakRef weakRef = paramTarget.getWeakImpl();
/*     */     
/* 214 */     if (DGCImpl.dgcLog.isLoggable(Log.VERBOSE)) {
/* 215 */       DGCImpl.dgcLog.log(Log.VERBOSE, "remove object " + objectEndpoint);
/*     */     }
/*     */     
/* 218 */     objTable.remove(objectEndpoint);
/* 219 */     implTable.remove(weakRef);
/*     */     
/* 221 */     paramTarget.markRemoved();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static void referenced(ObjID paramObjID, long paramLong, VMID paramVMID) {
/* 230 */     synchronized (tableLock) {
/*     */       
/* 232 */       ObjectEndpoint objectEndpoint = new ObjectEndpoint(paramObjID, Transport.currentTransport());
/* 233 */       Target target = objTable.get(objectEndpoint);
/* 234 */       if (target != null) {
/* 235 */         target.referenced(paramLong, paramVMID);
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
/*     */   static void unreferenced(ObjID paramObjID, long paramLong, VMID paramVMID, boolean paramBoolean) {
/* 248 */     synchronized (tableLock) {
/*     */       
/* 250 */       ObjectEndpoint objectEndpoint = new ObjectEndpoint(paramObjID, Transport.currentTransport());
/* 251 */       Target target = objTable.get(objectEndpoint);
/* 252 */       if (target != null) {
/* 253 */         target.unreferenced(paramLong, paramVMID, paramBoolean);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static void incrementKeepAliveCount() {
/* 275 */     synchronized (keepAliveLock) {
/* 276 */       keepAliveCount++;
/*     */       
/* 278 */       if (reaper == null) {
/* 279 */         reaper = AccessController.<Thread>doPrivileged(new NewThreadAction(new Reaper(), "Reaper", false));
/*     */         
/* 281 */         reaper.start();
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 291 */       if (gcLatencyRequest == null) {
/* 292 */         gcLatencyRequest = GC.requestLatency(gcInterval);
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static void decrementKeepAliveCount() {
/* 311 */     synchronized (keepAliveLock) {
/* 312 */       keepAliveCount--;
/*     */       
/* 314 */       if (keepAliveCount == 0) {
/* 315 */         if (reaper == null) throw new AssertionError(); 
/* 316 */         AccessController.doPrivileged(new PrivilegedAction<Void>() {
/*     */               public Void run() {
/* 318 */                 ObjectTable.reaper.interrupt();
/* 319 */                 return null;
/*     */               }
/*     */             });
/* 322 */         reaper = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 329 */         gcLatencyRequest.cancel();
/* 330 */         gcLatencyRequest = null;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static class Reaper
/*     */     implements Runnable
/*     */   {
/*     */     private Reaper() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void run() {
/*     */       try {
/*     */         do {
/* 351 */           WeakRef weakRef = (WeakRef)ObjectTable.reapQueue.remove();
/*     */           
/* 353 */           synchronized (ObjectTable.tableLock) {
/* 354 */             Target target = (Target)ObjectTable.implTable.get(weakRef);
/* 355 */             if (target != null) {
/* 356 */               if (!target.isEmpty()) {
/* 357 */                 throw new Error("object with known references collected");
/*     */               }
/* 359 */               if (target.isPermanent()) {
/* 360 */                 throw new Error("permanent object collected");
/*     */               }
/* 362 */               ObjectTable.removeTarget(target);
/*     */             } 
/*     */           } 
/* 365 */         } while (!Thread.interrupted());
/* 366 */       } catch (InterruptedException interruptedException) {}
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/rmi/transport/ObjectTable.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */