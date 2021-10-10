/*     */ package sun.rmi.transport;
/*     */ 
/*     */ import java.lang.ref.PhantomReference;
/*     */ import java.lang.ref.ReferenceQueue;
/*     */ import java.net.SocketPermission;
/*     */ import java.rmi.RemoteException;
/*     */ import java.rmi.dgc.DGC;
/*     */ import java.rmi.dgc.Lease;
/*     */ import java.rmi.dgc.VMID;
/*     */ import java.rmi.server.ObjID;
/*     */ import java.security.AccessControlContext;
/*     */ import java.security.AccessController;
/*     */ import java.security.Permissions;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.security.ProtectionDomain;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import sun.misc.GC;
/*     */ import sun.rmi.runtime.Log;
/*     */ import sun.rmi.runtime.NewThreadAction;
/*     */ import sun.rmi.server.UnicastRef;
/*     */ import sun.rmi.server.Util;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class DGCClient
/*     */ {
/*  90 */   private static long nextSequenceNum = Long.MIN_VALUE;
/*     */ 
/*     */   
/*  93 */   private static VMID vmid = new VMID();
/*     */ 
/*     */ 
/*     */   
/*  97 */   private static final long leaseValue = ((Long)AccessController.<Long>doPrivileged(new GetLongAction("java.rmi.dgc.leaseValue", 600000L)))
/*     */     
/*  99 */     .longValue();
/*     */ 
/*     */ 
/*     */   
/* 103 */   private static final long cleanInterval = ((Long)AccessController.<Long>doPrivileged(new GetLongAction("sun.rmi.dgc.cleanInterval", 180000L)))
/*     */     
/* 105 */     .longValue();
/*     */ 
/*     */ 
/*     */   
/* 109 */   private static final long gcInterval = ((Long)AccessController.<Long>doPrivileged(new GetLongAction("sun.rmi.dgc.client.gcInterval", 3600000L)))
/*     */     
/* 111 */     .longValue();
/*     */ 
/*     */   
/*     */   private static final int dirtyFailureRetries = 5;
/*     */ 
/*     */   
/*     */   private static final int cleanFailureRetries = 5;
/*     */ 
/*     */   
/* 120 */   private static final ObjID[] emptyObjIDArray = new ObjID[0];
/*     */ 
/*     */   
/* 123 */   private static final ObjID dgcID = new ObjID(2);
/*     */ 
/*     */   
/*     */   private static final AccessControlContext SOCKET_ACC;
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/* 131 */     Permissions permissions = new Permissions();
/* 132 */     permissions.add(new SocketPermission("*", "connect,resolve"));
/* 133 */     ProtectionDomain[] arrayOfProtectionDomain = { new ProtectionDomain(null, permissions) };
/* 134 */     SOCKET_ACC = new AccessControlContext(arrayOfProtectionDomain);
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
/*     */   static void registerRefs(Endpoint paramEndpoint, List<LiveRef> paramList) {
/*     */     EndpointEntry endpointEntry;
/*     */     do {
/* 159 */       endpointEntry = EndpointEntry.lookup(paramEndpoint);
/* 160 */     } while (!endpointEntry.registerRefs(paramList));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static synchronized long getNextSequenceNum() {
/* 170 */     return nextSequenceNum++;
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
/*     */   private static long computeRenewTime(long paramLong1, long paramLong2) {
/* 183 */     return paramLong1 + paramLong2 / 2L;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static class EndpointEntry
/*     */   {
/*     */     private Endpoint endpoint;
/*     */ 
/*     */ 
/*     */     
/*     */     private DGC dgc;
/*     */ 
/*     */ 
/*     */     
/* 200 */     private Map<LiveRef, RefEntry> refTable = new HashMap<>(5);
/*     */     
/* 202 */     private Set<RefEntry> invalidRefs = new HashSet<>(5);
/*     */ 
/*     */     
/*     */     private boolean removed = false;
/*     */ 
/*     */     
/* 208 */     private long renewTime = Long.MAX_VALUE;
/*     */     
/* 210 */     private long expirationTime = Long.MIN_VALUE;
/*     */     
/* 212 */     private int dirtyFailures = 0;
/*     */ 
/*     */     
/*     */     private long dirtyFailureStartTime;
/*     */ 
/*     */     
/*     */     private long dirtyFailureDuration;
/*     */     
/*     */     private Thread renewCleanThread;
/*     */     
/*     */     private boolean interruptible = false;
/*     */     
/* 224 */     private ReferenceQueue<LiveRef> refQueue = new ReferenceQueue<>();
/*     */     
/* 226 */     private Set<CleanRequest> pendingCleans = new HashSet<>(5);
/*     */ 
/*     */     
/* 229 */     private static Map<Endpoint, EndpointEntry> endpointTable = new HashMap<>(5);
/*     */     
/* 231 */     private static GC.LatencyRequest gcLatencyRequest = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public static EndpointEntry lookup(Endpoint param1Endpoint) {
/* 238 */       synchronized (endpointTable) {
/* 239 */         EndpointEntry endpointEntry = endpointTable.get(param1Endpoint);
/* 240 */         if (endpointEntry == null) {
/* 241 */           endpointEntry = new EndpointEntry(param1Endpoint);
/* 242 */           endpointTable.put(param1Endpoint, endpointEntry);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 250 */           if (gcLatencyRequest == null) {
/* 251 */             gcLatencyRequest = GC.requestLatency(DGCClient.gcInterval);
/*     */           }
/*     */         } 
/* 254 */         return endpointEntry;
/*     */       } 
/*     */     }
/*     */     
/*     */     private EndpointEntry(Endpoint param1Endpoint) {
/* 259 */       this.endpoint = param1Endpoint;
/*     */       try {
/* 261 */         LiveRef liveRef = new LiveRef(DGCClient.dgcID, param1Endpoint, false);
/* 262 */         this.dgc = (DGC)Util.createProxy(DGCImpl.class, new UnicastRef(liveRef), true);
/*     */       }
/* 264 */       catch (RemoteException remoteException) {
/* 265 */         throw new Error("internal error creating DGC stub");
/*     */       } 
/* 267 */       this.renewCleanThread = AccessController.<Thread>doPrivileged(new NewThreadAction(new RenewCleanThread(), "RenewClean-" + param1Endpoint, true));
/*     */ 
/*     */       
/* 270 */       this.renewCleanThread.start();
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
/*     */     public boolean registerRefs(List<LiveRef> param1List) {
/*     */       long l;
/* 285 */       assert !Thread.holdsLock(this);
/*     */       
/* 287 */       HashSet<RefEntry> hashSet = null;
/*     */ 
/*     */       
/* 290 */       synchronized (this) {
/* 291 */         if (this.removed) {
/* 292 */           return false;
/*     */         }
/*     */         
/* 295 */         Iterator<LiveRef> iterator = param1List.iterator();
/* 296 */         while (iterator.hasNext()) {
/* 297 */           LiveRef liveRef = iterator.next();
/* 298 */           assert liveRef.getEndpoint().equals(this.endpoint);
/*     */           
/* 300 */           RefEntry refEntry = this.refTable.get(liveRef);
/* 301 */           if (refEntry == null) {
/* 302 */             LiveRef liveRef1 = (LiveRef)liveRef.clone();
/* 303 */             refEntry = new RefEntry(liveRef1);
/* 304 */             this.refTable.put(liveRef1, refEntry);
/* 305 */             if (hashSet == null) {
/* 306 */               hashSet = new HashSet(5);
/*     */             }
/* 308 */             hashSet.add(refEntry);
/*     */           } 
/*     */           
/* 311 */           refEntry.addInstanceToRefSet(liveRef);
/*     */         } 
/*     */         
/* 314 */         if (hashSet == null) {
/* 315 */           return true;
/*     */         }
/*     */         
/* 318 */         hashSet.addAll(this.invalidRefs);
/* 319 */         this.invalidRefs.clear();
/*     */         
/* 321 */         l = DGCClient.getNextSequenceNum();
/*     */       } 
/*     */       
/* 324 */       makeDirtyCall(hashSet, l);
/* 325 */       return true;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private void removeRefEntry(RefEntry param1RefEntry) {
/* 336 */       assert Thread.holdsLock(this);
/* 337 */       assert !this.removed;
/* 338 */       assert this.refTable.containsKey(param1RefEntry.getRef());
/*     */       
/* 340 */       this.refTable.remove(param1RefEntry.getRef());
/* 341 */       this.invalidRefs.remove(param1RefEntry);
/* 342 */       if (this.refTable.isEmpty()) {
/* 343 */         synchronized (endpointTable) {
/* 344 */           endpointTable.remove(this.endpoint);
/* 345 */           Transport transport = this.endpoint.getOutboundTransport();
/* 346 */           transport.free(this.endpoint);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 352 */           if (endpointTable.isEmpty()) {
/* 353 */             assert gcLatencyRequest != null;
/* 354 */             gcLatencyRequest.cancel();
/* 355 */             gcLatencyRequest = null;
/*     */           } 
/* 357 */           this.removed = true;
/*     */         } 
/*     */       }
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private void makeDirtyCall(Set<RefEntry> param1Set, long param1Long) {
/*     */       ObjID[] arrayOfObjID;
/* 370 */       assert !Thread.holdsLock(this);
/*     */ 
/*     */       
/* 373 */       if (param1Set != null) {
/* 374 */         arrayOfObjID = createObjIDArray(param1Set);
/*     */       } else {
/* 376 */         arrayOfObjID = DGCClient.emptyObjIDArray;
/*     */       } 
/*     */       
/* 379 */       long l = System.currentTimeMillis();
/*     */       
/*     */       try {
/* 382 */         Lease lease = this.dgc.dirty(arrayOfObjID, param1Long, new Lease(DGCClient.vmid, DGCClient.leaseValue));
/* 383 */         long l1 = lease.getValue();
/*     */         
/* 385 */         long l2 = DGCClient.computeRenewTime(l, l1);
/* 386 */         long l3 = l + l1;
/*     */         
/* 388 */         synchronized (this) {
/* 389 */           this.dirtyFailures = 0;
/* 390 */           setRenewTime(l2);
/* 391 */           this.expirationTime = l3;
/*     */         }
/*     */       
/* 394 */       } catch (Exception exception) {
/* 395 */         long l1 = System.currentTimeMillis();
/*     */         
/* 397 */         synchronized (this) {
/* 398 */           this.dirtyFailures++;
/*     */           
/* 400 */           if (exception instanceof java.rmi.UnmarshalException && exception
/* 401 */             .getCause() instanceof java.io.InvalidClassException) {
/* 402 */             DGCImpl.dgcLog.log(Log.BRIEF, "InvalidClassException exception in DGC dirty call", exception);
/*     */             
/*     */             return;
/*     */           } 
/* 406 */           if (this.dirtyFailures == 1) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 414 */             this.dirtyFailureStartTime = l;
/* 415 */             this.dirtyFailureDuration = l1 - l;
/* 416 */             setRenewTime(l1);
/*     */ 
/*     */           
/*     */           }
/*     */           else {
/*     */ 
/*     */             
/* 423 */             int i = this.dirtyFailures - 2;
/* 424 */             if (i == 0)
/*     */             {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */               
/* 431 */               this
/* 432 */                 .dirtyFailureDuration = Math.max(this.dirtyFailureDuration + l1 - l >> 1L, 1000L);
/*     */             }
/*     */             
/* 435 */             long l2 = l1 + (this.dirtyFailureDuration << i);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 444 */             if (l2 < this.expirationTime || this.dirtyFailures < 5 || l2 < this.dirtyFailureStartTime + DGCClient
/*     */               
/* 446 */               .leaseValue) {
/*     */               
/* 448 */               setRenewTime(l2);
/*     */             
/*     */             }
/*     */             else {
/*     */ 
/*     */               
/* 454 */               setRenewTime(Long.MAX_VALUE);
/*     */             } 
/*     */           } 
/*     */           
/* 458 */           if (param1Set != null) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 466 */             this.invalidRefs.addAll(param1Set);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 473 */             Iterator<RefEntry> iterator = param1Set.iterator();
/* 474 */             while (iterator.hasNext()) {
/* 475 */               RefEntry refEntry = iterator.next();
/* 476 */               refEntry.markDirtyFailed();
/*     */             } 
/*     */           } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 484 */           if (this.renewTime >= this.expirationTime) {
/* 485 */             this.invalidRefs.addAll(this.refTable.values());
/*     */           }
/*     */         } 
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private void setRenewTime(long param1Long) {
/* 498 */       assert Thread.holdsLock(this);
/*     */       
/* 500 */       if (param1Long < this.renewTime) {
/* 501 */         this.renewTime = param1Long;
/* 502 */         if (this.interruptible) {
/* 503 */           AccessController.doPrivileged(new PrivilegedAction<Void>()
/*     */               {
/*     */                 public Void run() {
/* 506 */                   DGCClient.EndpointEntry.this.renewCleanThread.interrupt();
/* 507 */                   return null;
/*     */                 }
/*     */               });
/*     */         }
/*     */       } else {
/* 512 */         this.renewTime = param1Long;
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     private class RenewCleanThread
/*     */       implements Runnable
/*     */     {
/*     */       private RenewCleanThread() {}
/*     */       
/*     */       public void run() {
/*     */         do {
/*     */           long l1;
/* 525 */           DGCClient.EndpointEntry.RefEntry.PhantomLiveRef phantomLiveRef = null;
/* 526 */           boolean bool1 = false;
/* 527 */           Set set1 = null;
/* 528 */           long l2 = Long.MIN_VALUE;
/*     */           
/* 530 */           synchronized (DGCClient.EndpointEntry.this) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 542 */             long l = DGCClient.EndpointEntry.this.renewTime - System.currentTimeMillis();
/* 543 */             l1 = Math.max(l, 1L);
/* 544 */             if (!DGCClient.EndpointEntry.this.pendingCleans.isEmpty()) {
/* 545 */               l1 = Math.min(l1, DGCClient.cleanInterval);
/*     */             }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 554 */             DGCClient.EndpointEntry.this.interruptible = true;
/*     */           } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           try {
/* 563 */             phantomLiveRef = (DGCClient.EndpointEntry.RefEntry.PhantomLiveRef)DGCClient.EndpointEntry.this.refQueue.remove(l1);
/* 564 */           } catch (InterruptedException interruptedException) {}
/*     */ 
/*     */           
/* 567 */           synchronized (DGCClient.EndpointEntry.this) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 574 */             DGCClient.EndpointEntry.this.interruptible = false;
/* 575 */             Thread.interrupted();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 582 */             if (phantomLiveRef != null) {
/* 583 */               DGCClient.EndpointEntry.this.processPhantomRefs(phantomLiveRef);
/*     */             }
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 589 */             long l = System.currentTimeMillis();
/* 590 */             if (l > DGCClient.EndpointEntry.this.renewTime) {
/* 591 */               bool1 = true;
/* 592 */               if (!DGCClient.EndpointEntry.this.invalidRefs.isEmpty()) {
/* 593 */                 set1 = DGCClient.EndpointEntry.this.invalidRefs;
/* 594 */                 DGCClient.EndpointEntry.this.invalidRefs = new HashSet(5);
/*     */               } 
/* 596 */               l2 = DGCClient.getNextSequenceNum();
/*     */             } 
/*     */           } 
/*     */           
/* 600 */           final boolean needRenewal_ = bool1;
/* 601 */           final Set refsToDirty_ = set1;
/* 602 */           final long sequenceNum_ = l2;
/* 603 */           AccessController.doPrivileged(new PrivilegedAction<Void>() {
/*     */                 public Void run() {
/* 605 */                   if (needRenewal_) {
/* 606 */                     DGCClient.EndpointEntry.this.makeDirtyCall(refsToDirty_, sequenceNum_);
/*     */                   }
/*     */                   
/* 609 */                   if (!DGCClient.EndpointEntry.this.pendingCleans.isEmpty()) {
/* 610 */                     DGCClient.EndpointEntry.this.makeCleanCalls();
/*     */                   }
/* 612 */                   return null; }
/* 613 */               }DGCClient.SOCKET_ACC);
/* 614 */         } while (!DGCClient.EndpointEntry.this.removed || !DGCClient.EndpointEntry.this.pendingCleans.isEmpty());
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
/*     */     private void processPhantomRefs(RefEntry.PhantomLiveRef param1PhantomLiveRef) {
/* 629 */       assert Thread.holdsLock(this);
/*     */       
/* 631 */       HashSet<RefEntry> hashSet1 = null;
/* 632 */       HashSet<RefEntry> hashSet2 = null;
/*     */       
/*     */       do {
/* 635 */         RefEntry refEntry = param1PhantomLiveRef.getRefEntry();
/* 636 */         refEntry.removeInstanceFromRefSet(param1PhantomLiveRef);
/* 637 */         if (!refEntry.isRefSetEmpty())
/* 638 */           continue;  if (refEntry.hasDirtyFailed()) {
/* 639 */           if (hashSet1 == null) {
/* 640 */             hashSet1 = new HashSet(5);
/*     */           }
/* 642 */           hashSet1.add(refEntry);
/*     */         } else {
/* 644 */           if (hashSet2 == null) {
/* 645 */             hashSet2 = new HashSet(5);
/*     */           }
/* 647 */           hashSet2.add(refEntry);
/*     */         } 
/* 649 */         removeRefEntry(refEntry);
/*     */       
/*     */       }
/* 652 */       while ((param1PhantomLiveRef = (RefEntry.PhantomLiveRef)this.refQueue.poll()) != null);
/*     */       
/* 654 */       if (hashSet1 != null) {
/* 655 */         this.pendingCleans.add(new CleanRequest(
/* 656 */               createObjIDArray(hashSet1), DGCClient
/* 657 */               .getNextSequenceNum(), true));
/*     */       }
/* 659 */       if (hashSet2 != null) {
/* 660 */         this.pendingCleans.add(new CleanRequest(
/* 661 */               createObjIDArray(hashSet2), DGCClient
/* 662 */               .getNextSequenceNum(), false));
/*     */       }
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     private static class CleanRequest
/*     */     {
/*     */       final ObjID[] objIDs;
/*     */ 
/*     */       
/*     */       final long sequenceNum;
/*     */       
/*     */       final boolean strong;
/*     */       
/* 677 */       int failures = 0;
/*     */       
/*     */       CleanRequest(ObjID[] param2ArrayOfObjID, long param2Long, boolean param2Boolean) {
/* 680 */         this.objIDs = param2ArrayOfObjID;
/* 681 */         this.sequenceNum = param2Long;
/* 682 */         this.strong = param2Boolean;
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
/*     */     private void makeCleanCalls() {
/* 694 */       assert !Thread.holdsLock(this);
/*     */       
/* 696 */       Iterator<CleanRequest> iterator = this.pendingCleans.iterator();
/* 697 */       while (iterator.hasNext()) {
/* 698 */         CleanRequest cleanRequest = iterator.next();
/*     */         try {
/* 700 */           this.dgc.clean(cleanRequest.objIDs, cleanRequest.sequenceNum, DGCClient.vmid, cleanRequest.strong);
/*     */           
/* 702 */           iterator.remove();
/* 703 */         } catch (Exception exception) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 709 */           if (++cleanRequest.failures >= 5) {
/* 710 */             iterator.remove();
/*     */           }
/*     */         } 
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private static ObjID[] createObjIDArray(Set<RefEntry> param1Set) {
/* 721 */       ObjID[] arrayOfObjID = new ObjID[param1Set.size()];
/* 722 */       Iterator<RefEntry> iterator = param1Set.iterator();
/* 723 */       for (byte b = 0; b < arrayOfObjID.length; b++) {
/* 724 */         arrayOfObjID[b] = ((RefEntry)iterator.next()).getRef().getObjID();
/*     */       }
/* 726 */       return arrayOfObjID;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private class RefEntry
/*     */     {
/*     */       private LiveRef ref;
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 741 */       private Set<PhantomLiveRef> refSet = new HashSet<>(5);
/*     */       
/*     */       private boolean dirtyFailed = false;
/*     */       
/*     */       public RefEntry(LiveRef param2LiveRef) {
/* 746 */         this.ref = param2LiveRef;
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       public LiveRef getRef() {
/* 754 */         return this.ref;
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       public void addInstanceToRefSet(LiveRef param2LiveRef) {
/* 764 */         assert Thread.holdsLock(DGCClient.EndpointEntry.this);
/* 765 */         assert param2LiveRef.equals(this.ref);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 772 */         this.refSet.add(new PhantomLiveRef(param2LiveRef));
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       public void removeInstanceFromRefSet(PhantomLiveRef param2PhantomLiveRef) {
/* 782 */         assert Thread.holdsLock(DGCClient.EndpointEntry.this);
/* 783 */         assert this.refSet.contains(param2PhantomLiveRef);
/* 784 */         this.refSet.remove(param2PhantomLiveRef);
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       public boolean isRefSetEmpty() {
/* 795 */         assert Thread.holdsLock(DGCClient.EndpointEntry.this);
/* 796 */         return (this.refSet.size() == 0);
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       public void markDirtyFailed() {
/* 807 */         assert Thread.holdsLock(DGCClient.EndpointEntry.this);
/* 808 */         this.dirtyFailed = true;
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       public boolean hasDirtyFailed() {
/* 820 */         assert Thread.holdsLock(DGCClient.EndpointEntry.this);
/* 821 */         return this.dirtyFailed;
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       private class PhantomLiveRef
/*     */         extends PhantomReference<LiveRef>
/*     */       {
/*     */         public PhantomLiveRef(LiveRef param3LiveRef) {
/* 832 */           super(param3LiveRef, DGCClient.EndpointEntry.this.refQueue);
/*     */         }
/*     */         
/*     */         public DGCClient.EndpointEntry.RefEntry getRefEntry() {
/* 836 */           return DGCClient.EndpointEntry.RefEntry.this; } } } private class PhantomLiveRef extends PhantomReference<LiveRef> { public DGCClient.EndpointEntry.RefEntry getRefEntry() { return this.this$1; }
/*     */ 
/*     */       
/*     */       public PhantomLiveRef(LiveRef param2LiveRef) {
/*     */         super(param2LiveRef, DGCClient.EndpointEntry.this.refQueue);
/*     */       } }
/*     */   
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/rmi/transport/DGCClient.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */