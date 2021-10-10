/*     */ package java.rmi.activation;
/*     */ 
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.rmi.MarshalledObject;
/*     */ import java.rmi.Naming;
/*     */ import java.rmi.Remote;
/*     */ import java.rmi.RemoteException;
/*     */ import java.rmi.server.RMIClassLoader;
/*     */ import java.rmi.server.UnicastRemoteObject;
/*     */ import java.security.AccessController;
/*     */ import sun.rmi.server.ActivationGroupImpl;
/*     */ import sun.security.action.GetIntegerAction;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class ActivationGroup
/*     */   extends UnicastRemoteObject
/*     */   implements ActivationInstantiator
/*     */ {
/*     */   private ActivationGroupID groupID;
/*     */   private ActivationMonitor monitor;
/*     */   private long incarnation;
/*     */   private static ActivationGroup currGroup;
/*     */   private static ActivationGroupID currGroupID;
/*     */   private static ActivationSystem currSystem;
/*     */   private static boolean canCreate = true;
/*     */   private static final long serialVersionUID = -7696947875314805420L;
/*     */   
/*     */   protected ActivationGroup(ActivationGroupID paramActivationGroupID) throws RemoteException {
/* 145 */     this.groupID = paramActivationGroupID;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean inactiveObject(ActivationID paramActivationID) throws ActivationException, UnknownObjectException, RemoteException {
/* 189 */     getMonitor().inactiveObject(paramActivationID);
/* 190 */     return true;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract void activeObject(ActivationID paramActivationID, Remote paramRemote) throws ActivationException, UnknownObjectException, RemoteException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static synchronized ActivationGroup createGroup(ActivationGroupID paramActivationGroupID, ActivationGroupDesc paramActivationGroupDesc, long paramLong) throws ActivationException {
/* 283 */     SecurityManager securityManager = System.getSecurityManager();
/* 284 */     if (securityManager != null) {
/* 285 */       securityManager.checkSetFactory();
/*     */     }
/* 287 */     if (currGroup != null) {
/* 288 */       throw new ActivationException("group already exists");
/*     */     }
/* 290 */     if (!canCreate) {
/* 291 */       throw new ActivationException("group deactivated and cannot be recreated");
/*     */     }
/*     */     
/*     */     try {
/*     */       Class<? extends ActivationGroup> clazz;
/* 296 */       String str = paramActivationGroupDesc.getClassName();
/*     */       
/* 298 */       Class<ActivationGroupImpl> clazz1 = ActivationGroupImpl.class;
/*     */       
/* 300 */       if (str == null || str
/* 301 */         .equals(clazz1.getName())) {
/*     */         
/* 303 */         Class<ActivationGroupImpl> clazz2 = clazz1;
/*     */       } else {
/*     */         Class<?> clazz2;
/*     */         try {
/* 307 */           clazz2 = RMIClassLoader.loadClass(paramActivationGroupDesc.getLocation(), str);
/*     */         }
/* 309 */         catch (Exception exception) {
/* 310 */           throw new ActivationException("Could not load group implementation class", exception);
/*     */         } 
/*     */         
/* 313 */         if (ActivationGroup.class.isAssignableFrom(clazz2)) {
/* 314 */           clazz = clazz2.asSubclass(ActivationGroup.class);
/*     */         } else {
/* 316 */           throw new ActivationException("group not correct class: " + clazz2
/* 317 */               .getName());
/*     */         } 
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 323 */       Constructor<? extends ActivationGroup> constructor = clazz.getConstructor(new Class[] { ActivationGroupID.class, MarshalledObject.class });
/*     */ 
/*     */       
/* 326 */       ActivationGroup activationGroup = constructor.newInstance(new Object[] { paramActivationGroupID, paramActivationGroupDesc.getData() });
/* 327 */       currSystem = paramActivationGroupID.getSystem();
/* 328 */       activationGroup.incarnation = paramLong;
/* 329 */       activationGroup
/* 330 */         .monitor = currSystem.activeGroup(paramActivationGroupID, activationGroup, paramLong);
/* 331 */       currGroup = activationGroup;
/* 332 */       currGroupID = paramActivationGroupID;
/* 333 */       canCreate = false;
/* 334 */     } catch (InvocationTargetException invocationTargetException) {
/* 335 */       invocationTargetException.getTargetException().printStackTrace();
/* 336 */       throw new ActivationException("exception in group constructor", invocationTargetException
/* 337 */           .getTargetException());
/*     */     }
/* 339 */     catch (ActivationException activationException) {
/* 340 */       throw activationException;
/*     */     }
/* 342 */     catch (Exception exception) {
/* 343 */       throw new ActivationException("exception creating group", exception);
/*     */     } 
/*     */     
/* 346 */     return currGroup;
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
/*     */   public static synchronized ActivationGroupID currentGroupID() {
/* 358 */     return currGroupID;
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
/*     */   static synchronized ActivationGroupID internalCurrentGroupID() throws ActivationException {
/* 375 */     if (currGroupID == null) {
/* 376 */       throw new ActivationException("nonexistent group");
/*     */     }
/* 378 */     return currGroupID;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static synchronized void setSystem(ActivationSystem paramActivationSystem) throws ActivationException {
/* 412 */     SecurityManager securityManager = System.getSecurityManager();
/* 413 */     if (securityManager != null) {
/* 414 */       securityManager.checkSetFactory();
/*     */     }
/* 416 */     if (currSystem != null) {
/* 417 */       throw new ActivationException("activation system already set");
/*     */     }
/* 419 */     currSystem = paramActivationSystem;
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
/*     */ 
/*     */ 
/*     */   
/*     */   public static synchronized ActivationSystem getSystem() throws ActivationException {
/* 447 */     if (currSystem == null) {
/*     */       try {
/* 449 */         int i = ((Integer)AccessController.<Integer>doPrivileged(new GetIntegerAction("java.rmi.activation.port", 1098))).intValue();
/*     */ 
/*     */ 
/*     */         
/* 453 */         currSystem = (ActivationSystem)Naming.lookup("//:" + i + "/java.rmi.activation.ActivationSystem");
/*     */       }
/* 455 */       catch (Exception exception) {
/* 456 */         throw new ActivationException("unable to obtain ActivationSystem", exception);
/*     */       } 
/*     */     }
/*     */     
/* 460 */     return currSystem;
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
/*     */   protected void activeObject(ActivationID paramActivationID, MarshalledObject<? extends Remote> paramMarshalledObject) throws ActivationException, UnknownObjectException, RemoteException {
/* 480 */     getMonitor().activeObject(paramActivationID, paramMarshalledObject);
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
/*     */   protected void inactiveGroup() throws UnknownGroupException, RemoteException {
/*     */     try {
/* 498 */       getMonitor().inactiveGroup(this.groupID, this.incarnation);
/*     */     } finally {
/* 500 */       destroyGroup();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private ActivationMonitor getMonitor() throws RemoteException {
/* 508 */     synchronized (ActivationGroup.class) {
/* 509 */       if (this.monitor != null) {
/* 510 */         return this.monitor;
/*     */       }
/*     */     } 
/* 513 */     throw new RemoteException("monitor not received");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static synchronized void destroyGroup() {
/* 520 */     currGroup = null;
/* 521 */     currGroupID = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static synchronized ActivationGroup currentGroup() throws ActivationException {
/* 532 */     if (currGroup == null) {
/* 533 */       throw new ActivationException("group is not active");
/*     */     }
/* 535 */     return currGroup;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/rmi/activation/ActivationGroup.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */