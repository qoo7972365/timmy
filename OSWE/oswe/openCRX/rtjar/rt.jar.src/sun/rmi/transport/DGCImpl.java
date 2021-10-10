/*     */ package sun.rmi.transport;
/*     */ 
/*     */ import java.net.SocketPermission;
/*     */ import java.rmi.Remote;
/*     */ import java.rmi.RemoteException;
/*     */ import java.rmi.dgc.DGC;
/*     */ import java.rmi.dgc.Lease;
/*     */ import java.rmi.dgc.VMID;
/*     */ import java.rmi.server.LogStream;
/*     */ import java.rmi.server.ObjID;
/*     */ import java.rmi.server.RemoteServer;
/*     */ import java.rmi.server.ServerNotActiveException;
/*     */ import java.rmi.server.UID;
/*     */ import java.security.AccessControlContext;
/*     */ import java.security.AccessController;
/*     */ import java.security.Permissions;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.security.ProtectionDomain;
/*     */ import java.security.Security;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.Future;
/*     */ import java.util.concurrent.ScheduledExecutorService;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import sun.misc.ObjectInputFilter;
/*     */ import sun.rmi.runtime.Log;
/*     */ import sun.rmi.runtime.RuntimeUtil;
/*     */ import sun.rmi.server.UnicastRef;
/*     */ import sun.rmi.server.UnicastServerRef;
/*     */ import sun.rmi.server.Util;
/*     */ import sun.security.action.GetLongAction;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class DGCImpl
/*     */   implements DGC
/*     */ {
/*  75 */   static final Log dgcLog = Log.getLog("sun.rmi.dgc", "dgc", 
/*  76 */       LogStream.parseLevel(AccessController.<String>doPrivileged(new GetPropertyAction("sun.rmi.dgc.logLevel"))));
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  81 */   private static final long leaseValue = ((Long)AccessController.<Long>doPrivileged(new GetLongAction("java.rmi.dgc.leaseValue", 600000L))).longValue();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  86 */   private static final long leaseCheckInterval = ((Long)AccessController.<Long>doPrivileged(new GetLongAction("sun.rmi.dgc.checkInterval", leaseValue / 2L))).longValue();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  91 */   private static final ScheduledExecutorService scheduler = ((RuntimeUtil)AccessController.<RuntimeUtil>doPrivileged(new RuntimeUtil.GetInstanceAction()))
/*  92 */     .getScheduler();
/*     */ 
/*     */   
/*     */   private static DGCImpl dgc;
/*     */   
/*  97 */   private Map<VMID, LeaseInfo> leaseTable = new HashMap<>();
/*     */   
/*  99 */   private Future<?> checker = null;
/*     */ 
/*     */   
/*     */   private static final String DGC_FILTER_PROPNAME = "sun.rmi.transport.dgcFilter";
/*     */ 
/*     */   
/*     */   static DGCImpl getDGCImpl() {
/* 106 */     return dgc;
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
/* 118 */   private static int DGC_MAX_DEPTH = 5;
/*     */ 
/*     */   
/* 121 */   private static int DGC_MAX_ARRAY_SIZE = 10000;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 127 */   private static final ObjectInputFilter dgcFilter = AccessController.<ObjectInputFilter>doPrivileged(DGCImpl::initDgcFilter);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static ObjectInputFilter initDgcFilter() {
/* 134 */     ObjectInputFilter objectInputFilter = null;
/* 135 */     String str = System.getProperty("sun.rmi.transport.dgcFilter");
/* 136 */     if (str == null) {
/* 137 */       str = Security.getProperty("sun.rmi.transport.dgcFilter");
/*     */     }
/* 139 */     if (str != null) {
/* 140 */       objectInputFilter = ObjectInputFilter.Config.createFilter(str);
/* 141 */       if (dgcLog.isLoggable(Log.BRIEF)) {
/* 142 */         dgcLog.log(Log.BRIEF, "dgcFilter = " + objectInputFilter);
/*     */       }
/*     */     } 
/* 145 */     return objectInputFilter;
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
/*     */   public Lease dirty(ObjID[] paramArrayOfObjID, long paramLong, Lease paramLease) {
/* 168 */     VMID vMID = paramLease.getVMID();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 173 */     long l = leaseValue;
/*     */     
/* 175 */     if (dgcLog.isLoggable(Log.VERBOSE)) {
/* 176 */       dgcLog.log(Log.VERBOSE, "vmid = " + vMID);
/*     */     }
/*     */ 
/*     */     
/* 180 */     if (vMID == null) {
/* 181 */       vMID = new VMID();
/*     */       
/* 183 */       if (dgcLog.isLoggable(Log.BRIEF)) {
/*     */         String str;
/*     */         try {
/* 186 */           str = RemoteServer.getClientHost();
/* 187 */         } catch (ServerNotActiveException serverNotActiveException) {
/* 188 */           str = "<unknown host>";
/*     */         } 
/* 190 */         dgcLog.log(Log.BRIEF, " assigning vmid " + vMID + " to client " + str);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 195 */     paramLease = new Lease(vMID, l);
/*     */     
/* 197 */     synchronized (this.leaseTable) {
/* 198 */       LeaseInfo leaseInfo = this.leaseTable.get(vMID);
/* 199 */       if (leaseInfo == null) {
/* 200 */         this.leaseTable.put(vMID, new LeaseInfo(vMID, l));
/* 201 */         if (this.checker == null) {
/* 202 */           this.checker = scheduler.scheduleWithFixedDelay(new Runnable()
/*     */               {
/*     */                 public void run() {
/* 205 */                   DGCImpl.this.checkLeases();
/*     */                 }
/*     */               }, 
/*     */               
/*     */               leaseCheckInterval, leaseCheckInterval, TimeUnit.MILLISECONDS);
/*     */         }
/*     */       } else {
/* 212 */         leaseInfo.renew(l);
/*     */       } 
/*     */     } 
/*     */     
/* 216 */     for (ObjID objID : paramArrayOfObjID) {
/* 217 */       if (dgcLog.isLoggable(Log.VERBOSE)) {
/* 218 */         dgcLog.log(Log.VERBOSE, "id = " + objID + ", vmid = " + vMID + ", duration = " + l);
/*     */       }
/*     */ 
/*     */       
/* 222 */       ObjectTable.referenced(objID, paramLong, vMID);
/*     */     } 
/*     */ 
/*     */     
/* 226 */     return paramLease;
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
/*     */   public void clean(ObjID[] paramArrayOfObjID, long paramLong, VMID paramVMID, boolean paramBoolean) {
/* 239 */     for (ObjID objID : paramArrayOfObjID) {
/* 240 */       if (dgcLog.isLoggable(Log.VERBOSE)) {
/* 241 */         dgcLog.log(Log.VERBOSE, "id = " + objID + ", vmid = " + paramVMID + ", strong = " + paramBoolean);
/*     */       }
/*     */ 
/*     */       
/* 245 */       ObjectTable.unreferenced(objID, paramLong, paramVMID, paramBoolean);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void registerTarget(VMID paramVMID, Target paramTarget) {
/* 254 */     synchronized (this.leaseTable) {
/* 255 */       LeaseInfo leaseInfo = this.leaseTable.get(paramVMID);
/* 256 */       if (leaseInfo == null) {
/* 257 */         paramTarget.vmidDead(paramVMID);
/*     */       } else {
/* 259 */         leaseInfo.notifySet.add(paramTarget);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void unregisterTarget(VMID paramVMID, Target paramTarget) {
/* 268 */     synchronized (this.leaseTable) {
/* 269 */       LeaseInfo leaseInfo = this.leaseTable.get(paramVMID);
/* 270 */       if (leaseInfo != null) {
/* 271 */         leaseInfo.notifySet.remove(paramTarget);
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
/*     */   private void checkLeases() {
/* 285 */     long l = System.currentTimeMillis();
/*     */ 
/*     */     
/* 288 */     ArrayList<LeaseInfo> arrayList = new ArrayList();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 294 */     synchronized (this.leaseTable) {
/* 295 */       Iterator<LeaseInfo> iterator = this.leaseTable.values().iterator();
/* 296 */       while (iterator.hasNext()) {
/* 297 */         LeaseInfo leaseInfo = iterator.next();
/* 298 */         if (leaseInfo.expired(l)) {
/* 299 */           arrayList.add(leaseInfo);
/* 300 */           iterator.remove();
/*     */         } 
/*     */       } 
/*     */       
/* 304 */       if (this.leaseTable.isEmpty()) {
/* 305 */         this.checker.cancel(false);
/* 306 */         this.checker = null;
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 313 */     for (LeaseInfo leaseInfo : arrayList) {
/* 314 */       for (Target target : leaseInfo.notifySet) {
/* 315 */         target.vmidDead(leaseInfo.vmid);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/* 325 */     AccessController.doPrivileged(new PrivilegedAction<Void>()
/*     */         {
/*     */           public Void run() {
/* 328 */             ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
/*     */             try {
/* 330 */               Thread.currentThread().setContextClassLoader(
/* 331 */                   ClassLoader.getSystemClassLoader());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */               
/*     */               try {
/* 339 */                 DGCImpl.dgc = new DGCImpl();
/* 340 */                 final ObjID dgcID = new ObjID(2);
/* 341 */                 LiveRef liveRef = new LiveRef(objID, 0);
/* 342 */                 final UnicastServerRef disp = new UnicastServerRef(liveRef, param1FilterInfo -> DGCImpl.checkInput(param1FilterInfo));
/*     */ 
/*     */                 
/* 345 */                 final Remote stub = Util.createProxy(DGCImpl.class, new UnicastRef(liveRef), true);
/*     */                 
/* 347 */                 unicastServerRef.setSkeleton(DGCImpl.dgc);
/*     */                 
/* 349 */                 Permissions permissions = new Permissions();
/* 350 */                 permissions.add(new SocketPermission("*", "accept,resolve"));
/* 351 */                 ProtectionDomain[] arrayOfProtectionDomain = { new ProtectionDomain(null, permissions) };
/* 352 */                 AccessControlContext accessControlContext = new AccessControlContext(arrayOfProtectionDomain);
/*     */                 
/* 354 */                 Target target = AccessController.<Target>doPrivileged(new PrivilegedAction<Target>()
/*     */                     {
/*     */                       public Target run() {
/* 357 */                         return new Target(DGCImpl.dgc, disp, stub, dgcID, true);
/*     */                       }
/*     */                     }accessControlContext);
/*     */                 
/* 361 */                 ObjectTable.putTarget(target);
/* 362 */               } catch (RemoteException remoteException) {
/* 363 */                 throw new Error("exception initializing server-side DGC", remoteException);
/*     */               } 
/*     */             } finally {
/*     */               
/* 367 */               Thread.currentThread().setContextClassLoader(classLoader);
/*     */             } 
/* 369 */             return null;
/*     */           }
/*     */         });
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
/*     */   private static ObjectInputFilter.Status checkInput(ObjectInputFilter.FilterInfo paramFilterInfo) {
/* 385 */     if (dgcFilter != null) {
/* 386 */       ObjectInputFilter.Status status = dgcFilter.checkInput(paramFilterInfo);
/* 387 */       if (status != ObjectInputFilter.Status.UNDECIDED)
/*     */       {
/* 389 */         return status;
/*     */       }
/*     */     } 
/*     */     
/* 393 */     if (paramFilterInfo.depth() > DGC_MAX_DEPTH) {
/* 394 */       return ObjectInputFilter.Status.REJECTED;
/*     */     }
/* 396 */     Class<?> clazz = paramFilterInfo.serialClass();
/* 397 */     if (clazz != null) {
/* 398 */       while (clazz.isArray()) {
/* 399 */         if (paramFilterInfo.arrayLength() >= 0L && paramFilterInfo.arrayLength() > DGC_MAX_ARRAY_SIZE) {
/* 400 */           return ObjectInputFilter.Status.REJECTED;
/*     */         }
/*     */         
/* 403 */         clazz = clazz.getComponentType();
/*     */       } 
/* 405 */       if (clazz.isPrimitive())
/*     */       {
/* 407 */         return ObjectInputFilter.Status.ALLOWED;
/*     */       }
/* 409 */       return (clazz == ObjID.class || clazz == UID.class || clazz == VMID.class || clazz == Lease.class) ? ObjectInputFilter.Status.ALLOWED : ObjectInputFilter.Status.REJECTED;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 417 */     return ObjectInputFilter.Status.UNDECIDED;
/*     */   }
/*     */   
/*     */   private DGCImpl() {}
/*     */   
/*     */   private static class LeaseInfo {
/*     */     VMID vmid;
/* 424 */     Set<Target> notifySet = new HashSet<>(); long expiration;
/*     */     
/*     */     LeaseInfo(VMID param1VMID, long param1Long) {
/* 427 */       this.vmid = param1VMID;
/* 428 */       this.expiration = System.currentTimeMillis() + param1Long;
/*     */     }
/*     */     
/*     */     synchronized void renew(long param1Long) {
/* 432 */       long l = System.currentTimeMillis() + param1Long;
/* 433 */       if (l > this.expiration)
/* 434 */         this.expiration = l; 
/*     */     }
/*     */     
/*     */     boolean expired(long param1Long) {
/* 438 */       if (this.expiration < param1Long) {
/* 439 */         if (DGCImpl.dgcLog.isLoggable(Log.BRIEF)) {
/* 440 */           DGCImpl.dgcLog.log(Log.BRIEF, this.vmid.toString());
/*     */         }
/* 442 */         return true;
/*     */       } 
/* 444 */       return false;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/rmi/transport/DGCImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */