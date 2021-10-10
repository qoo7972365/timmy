/*     */ package sun.rmi.transport;
/*     */ 
/*     */ import java.rmi.NoSuchObjectException;
/*     */ import java.rmi.Remote;
/*     */ import java.rmi.dgc.VMID;
/*     */ import java.rmi.server.ObjID;
/*     */ import java.rmi.server.Unreferenced;
/*     */ import java.security.AccessControlContext;
/*     */ import java.security.AccessController;
/*     */ import java.util.Enumeration;
/*     */ import java.util.Hashtable;
/*     */ import java.util.Vector;
/*     */ import sun.rmi.runtime.Log;
/*     */ import sun.rmi.runtime.NewThreadAction;
/*     */ import sun.rmi.server.Dispatcher;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class Target
/*     */ {
/*     */   private final ObjID id;
/*     */   private final boolean permanent;
/*     */   private final WeakRef weakImpl;
/*     */   private volatile Dispatcher disp;
/*     */   private final Remote stub;
/*  57 */   private final Vector<VMID> refSet = new Vector<>();
/*     */   
/*  59 */   private final Hashtable<VMID, SequenceEntry> sequenceTable = new Hashtable<>(5);
/*     */ 
/*     */   
/*     */   private final AccessControlContext acc;
/*     */   
/*     */   private final ClassLoader ccl;
/*     */   
/*  66 */   private int callCount = 0;
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean removed = false;
/*     */ 
/*     */   
/*  73 */   private volatile Transport exportedTransport = null;
/*     */ 
/*     */   
/*  76 */   private static int nextThreadNum = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Target(Remote paramRemote1, Dispatcher paramDispatcher, Remote paramRemote2, ObjID paramObjID, boolean paramBoolean) {
/*  91 */     this.weakImpl = new WeakRef(paramRemote1, ObjectTable.reapQueue);
/*  92 */     this.disp = paramDispatcher;
/*  93 */     this.stub = paramRemote2;
/*  94 */     this.id = paramObjID;
/*  95 */     this.acc = AccessController.getContext();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 110 */     ClassLoader classLoader1 = Thread.currentThread().getContextClassLoader();
/* 111 */     ClassLoader classLoader2 = paramRemote1.getClass().getClassLoader();
/* 112 */     if (checkLoaderAncestry(classLoader1, classLoader2)) {
/* 113 */       this.ccl = classLoader1;
/*     */     } else {
/* 115 */       this.ccl = classLoader2;
/*     */     } 
/*     */     
/* 118 */     this.permanent = paramBoolean;
/* 119 */     if (paramBoolean) {
/* 120 */       pinImpl();
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
/*     */   private static boolean checkLoaderAncestry(ClassLoader paramClassLoader1, ClassLoader paramClassLoader2) {
/* 134 */     if (paramClassLoader2 == null)
/* 135 */       return true; 
/* 136 */     if (paramClassLoader1 == null) {
/* 137 */       return false;
/*     */     }
/* 139 */     ClassLoader classLoader = paramClassLoader1;
/* 140 */     for (; classLoader != null; 
/* 141 */       classLoader = classLoader.getParent()) {
/*     */       
/* 143 */       if (classLoader == paramClassLoader2) {
/* 144 */         return true;
/*     */       }
/*     */     } 
/* 147 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Remote getStub() {
/* 154 */     return this.stub;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   ObjectEndpoint getObjectEndpoint() {
/* 161 */     return new ObjectEndpoint(this.id, this.exportedTransport);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   WeakRef getWeakImpl() {
/* 168 */     return this.weakImpl;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   Dispatcher getDispatcher() {
/* 175 */     return this.disp;
/*     */   }
/*     */   
/*     */   AccessControlContext getAccessControlContext() {
/* 179 */     return this.acc;
/*     */   }
/*     */   
/*     */   ClassLoader getContextClassLoader() {
/* 183 */     return this.ccl;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   Remote getImpl() {
/* 192 */     return (Remote)this.weakImpl.get();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   boolean isPermanent() {
/* 199 */     return this.permanent;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   synchronized void pinImpl() {
/* 209 */     this.weakImpl.pin();
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
/*     */   synchronized void unpinImpl() {
/* 224 */     if (!this.permanent && this.refSet.isEmpty()) {
/* 225 */       this.weakImpl.unpin();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void setExportedTransport(Transport paramTransport) {
/* 234 */     if (this.exportedTransport == null) {
/* 235 */       this.exportedTransport = paramTransport;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   synchronized void referenced(long paramLong, VMID paramVMID) {
/* 246 */     SequenceEntry sequenceEntry = this.sequenceTable.get(paramVMID);
/* 247 */     if (sequenceEntry == null) {
/* 248 */       this.sequenceTable.put(paramVMID, new SequenceEntry(paramLong));
/* 249 */     } else if (sequenceEntry.sequenceNum < paramLong) {
/* 250 */       sequenceEntry.update(paramLong);
/*     */     } else {
/*     */       return;
/*     */     } 
/*     */ 
/*     */     
/* 256 */     if (!this.refSet.contains(paramVMID)) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 264 */       pinImpl();
/* 265 */       if (getImpl() == null) {
/*     */         return;
/*     */       }
/* 268 */       if (DGCImpl.dgcLog.isLoggable(Log.VERBOSE)) {
/* 269 */         DGCImpl.dgcLog.log(Log.VERBOSE, "add to dirty set: " + paramVMID);
/*     */       }
/*     */       
/* 272 */       this.refSet.addElement(paramVMID);
/*     */       
/* 274 */       DGCImpl.getDGCImpl().registerTarget(paramVMID, this);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   synchronized void unreferenced(long paramLong, VMID paramVMID, boolean paramBoolean) {
/* 285 */     SequenceEntry sequenceEntry = this.sequenceTable.get(paramVMID);
/* 286 */     if (sequenceEntry == null || sequenceEntry.sequenceNum > paramLong) {
/*     */       return;
/*     */     }
/* 289 */     if (paramBoolean) {
/*     */       
/* 291 */       sequenceEntry.retain(paramLong);
/* 292 */     } else if (!sequenceEntry.keep) {
/*     */       
/* 294 */       this.sequenceTable.remove(paramVMID);
/*     */     } 
/*     */     
/* 297 */     if (DGCImpl.dgcLog.isLoggable(Log.VERBOSE)) {
/* 298 */       DGCImpl.dgcLog.log(Log.VERBOSE, "remove from dirty set: " + paramVMID);
/*     */     }
/*     */     
/* 301 */     refSetRemove(paramVMID);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private synchronized void refSetRemove(VMID paramVMID) {
/* 309 */     DGCImpl.getDGCImpl().unregisterTarget(paramVMID, this);
/*     */     
/* 311 */     if (this.refSet.removeElement(paramVMID) && this.refSet.isEmpty()) {
/*     */ 
/*     */       
/* 314 */       if (DGCImpl.dgcLog.isLoggable(Log.VERBOSE)) {
/* 315 */         DGCImpl.dgcLog.log(Log.VERBOSE, "reference set is empty: target = " + this);
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 323 */       Remote remote = getImpl();
/* 324 */       if (remote instanceof Unreferenced) {
/* 325 */         Unreferenced unreferenced = (Unreferenced)remote;
/* 326 */         ((Thread)AccessController.<Thread>doPrivileged(new NewThreadAction(() -> { Thread.currentThread().setContextClassLoader(this.ccl); AccessController.doPrivileged((), this.acc); }"Unreferenced-" + nextThreadNum++, false, true)))
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 333 */           .start();
/*     */       } 
/*     */ 
/*     */       
/* 337 */       unpinImpl();
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
/*     */   synchronized boolean unexport(boolean paramBoolean) {
/* 350 */     if (paramBoolean == true || this.callCount == 0 || this.disp == null) {
/* 351 */       this.disp = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 357 */       unpinImpl();
/* 358 */       DGCImpl dGCImpl = DGCImpl.getDGCImpl();
/* 359 */       Enumeration<VMID> enumeration = this.refSet.elements();
/* 360 */       while (enumeration.hasMoreElements()) {
/* 361 */         VMID vMID = enumeration.nextElement();
/* 362 */         dGCImpl.unregisterTarget(vMID, this);
/*     */       } 
/* 364 */       return true;
/*     */     } 
/* 366 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   synchronized void markRemoved() {
/* 374 */     if (this.removed) throw new AssertionError();
/*     */     
/* 376 */     this.removed = true;
/* 377 */     if (!this.permanent && this.callCount == 0) {
/* 378 */       ObjectTable.decrementKeepAliveCount();
/*     */     }
/*     */     
/* 381 */     if (this.exportedTransport != null) {
/* 382 */       this.exportedTransport.targetUnexported();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   synchronized void incrementCallCount() throws NoSuchObjectException {
/* 391 */     if (this.disp != null) {
/* 392 */       this.callCount++;
/*     */     } else {
/* 394 */       throw new NoSuchObjectException("object not accepting new calls");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   synchronized void decrementCallCount() {
/* 403 */     if (--this.callCount < 0) {
/* 404 */       throw new Error("internal error: call count less than zero");
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
/* 415 */     if (!this.permanent && this.removed && this.callCount == 0) {
/* 416 */       ObjectTable.decrementKeepAliveCount();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   boolean isEmpty() {
/* 425 */     return this.refSet.isEmpty();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void vmidDead(VMID paramVMID) {
/* 434 */     if (DGCImpl.dgcLog.isLoggable(Log.BRIEF)) {
/* 435 */       DGCImpl.dgcLog.log(Log.BRIEF, "removing endpoint " + paramVMID + " from reference set");
/*     */     }
/*     */ 
/*     */     
/* 439 */     this.sequenceTable.remove(paramVMID);
/* 440 */     refSetRemove(paramVMID);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/rmi/transport/Target.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */