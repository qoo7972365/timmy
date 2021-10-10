/*     */ package sun.rmi.server;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.net.ServerSocket;
/*     */ import java.rmi.MarshalledObject;
/*     */ import java.rmi.NoSuchObjectException;
/*     */ import java.rmi.Remote;
/*     */ import java.rmi.RemoteException;
/*     */ import java.rmi.activation.Activatable;
/*     */ import java.rmi.activation.ActivationDesc;
/*     */ import java.rmi.activation.ActivationException;
/*     */ import java.rmi.activation.ActivationGroup;
/*     */ import java.rmi.activation.ActivationGroupID;
/*     */ import java.rmi.activation.ActivationID;
/*     */ import java.rmi.activation.UnknownObjectException;
/*     */ import java.rmi.server.RMIClassLoader;
/*     */ import java.rmi.server.RMIServerSocketFactory;
/*     */ import java.rmi.server.RMISocketFactory;
/*     */ import java.rmi.server.UnicastRemoteObject;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedActionException;
/*     */ import java.security.PrivilegedExceptionAction;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Hashtable;
/*     */ import java.util.List;
/*     */ import sun.rmi.registry.RegistryImpl;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ActivationGroupImpl
/*     */   extends ActivationGroup
/*     */ {
/*     */   private static final long serialVersionUID = 5758693559430427303L;
/*  68 */   private final Hashtable<ActivationID, ActiveEntry> active = new Hashtable<>();
/*     */   
/*     */   private boolean groupInactive = false;
/*     */   private final ActivationGroupID groupID;
/*  72 */   private final List<ActivationID> lockedIDs = new ArrayList<>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ActivationGroupImpl(ActivationGroupID paramActivationGroupID, MarshalledObject<?> paramMarshalledObject) throws RemoteException {
/*  83 */     super(paramActivationGroupID);
/*  84 */     this.groupID = paramActivationGroupID;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  90 */     unexportObject((Remote)this, true);
/*  91 */     ServerSocketFactoryImpl serverSocketFactoryImpl = new ServerSocketFactoryImpl();
/*  92 */     UnicastRemoteObject.exportObject((Remote)this, 0, null, serverSocketFactoryImpl);
/*     */     
/*  94 */     if (System.getSecurityManager() == null) {
/*     */       
/*     */       try {
/*  97 */         System.setSecurityManager(new SecurityManager());
/*     */       }
/*  99 */       catch (Exception exception) {
/* 100 */         throw new RemoteException("unable to set security manager", exception);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static class ServerSocketFactoryImpl
/*     */     implements RMIServerSocketFactory
/*     */   {
/*     */     private ServerSocketFactoryImpl() {}
/*     */ 
/*     */     
/*     */     public ServerSocket createServerSocket(int param1Int) throws IOException {
/* 114 */       RMISocketFactory rMISocketFactory = RMISocketFactory.getSocketFactory();
/* 115 */       if (rMISocketFactory == null) {
/* 116 */         rMISocketFactory = RMISocketFactory.getDefaultSocketFactory();
/*     */       }
/* 118 */       return rMISocketFactory.createServerSocket(param1Int);
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
/*     */   private void acquireLock(ActivationID paramActivationID) {
/*     */     while (true) {
/*     */       ActivationID activationID;
/* 145 */       synchronized (this.lockedIDs) {
/* 146 */         int i = this.lockedIDs.indexOf(paramActivationID);
/* 147 */         if (i < 0) {
/* 148 */           this.lockedIDs.add(paramActivationID);
/*     */           return;
/*     */         } 
/* 151 */         activationID = this.lockedIDs.get(i);
/*     */       } 
/*     */ 
/*     */       
/* 155 */       synchronized (activationID) {
/* 156 */         synchronized (this.lockedIDs) {
/* 157 */           int i = this.lockedIDs.indexOf(activationID);
/* 158 */           if (i < 0)
/* 159 */             continue;  ActivationID activationID1 = this.lockedIDs.get(i);
/* 160 */           if (activationID1 != activationID) {
/*     */             continue;
/*     */           }
/*     */         } 
/*     */ 
/*     */ 
/*     */         
/*     */         try {
/* 168 */           activationID.wait();
/* 169 */         } catch (InterruptedException interruptedException) {}
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
/*     */   private void releaseLock(ActivationID paramActivationID) {
/* 181 */     synchronized (this.lockedIDs) {
/* 182 */       paramActivationID = this.lockedIDs.remove(this.lockedIDs.indexOf(paramActivationID));
/*     */     } 
/*     */     
/* 185 */     synchronized (paramActivationID) {
/* 186 */       paramActivationID.notifyAll();
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
/*     */   public MarshalledObject<? extends Remote> newInstance(final ActivationID id, final ActivationDesc desc) throws ActivationException, RemoteException {
/* 211 */     RegistryImpl.checkAccess("ActivationInstantiator.newInstance");
/*     */     
/* 213 */     if (!this.groupID.equals(desc.getGroupID())) {
/* 214 */       throw new ActivationException("newInstance in wrong group");
/*     */     }
/*     */     try {
/* 217 */       acquireLock(id);
/* 218 */       synchronized (this) {
/* 219 */         if (this.groupInactive == true) {
/* 220 */           throw new InactiveGroupException("group is inactive");
/*     */         }
/*     */       } 
/* 223 */       ActiveEntry activeEntry = this.active.get(id);
/* 224 */       if (activeEntry != null) {
/* 225 */         return activeEntry.mobj;
/*     */       }
/* 227 */       String str = desc.getClassName();
/*     */ 
/*     */ 
/*     */       
/* 231 */       final Class<? extends Remote> cl = RMIClassLoader.loadClass(desc.getLocation(), str).asSubclass(Remote.class);
/* 232 */       Remote remote = null;
/*     */       
/* 234 */       final Thread t = Thread.currentThread();
/* 235 */       final ClassLoader savedCcl = thread.getContextClassLoader();
/* 236 */       ClassLoader classLoader2 = clazz.getClassLoader();
/* 237 */       final ClassLoader ccl = covers(classLoader2, classLoader1) ? classLoader2 : classLoader1;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       try {
/* 251 */         remote = AccessController.<Remote>doPrivileged(new PrivilegedExceptionAction<Remote>()
/*     */             {
/*     */ 
/*     */ 
/*     */               
/*     */               public Remote run() throws InstantiationException, NoSuchMethodException, IllegalAccessException, InvocationTargetException
/*     */               {
/* 258 */                 Constructor<Remote> constructor = cl.getDeclaredConstructor(new Class[] { ActivationID.class, MarshalledObject.class });
/*     */                 
/* 260 */                 constructor.setAccessible(true);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */                 
/*     */                 try {
/* 268 */                   t.setContextClassLoader(ccl);
/* 269 */                   return constructor.newInstance(new Object[] { this.val$id, this.val$desc
/* 270 */                         .getData() });
/*     */                 } finally {
/* 272 */                   t.setContextClassLoader(savedCcl);
/*     */                 } 
/*     */               }
/*     */             });
/* 276 */       } catch (PrivilegedActionException privilegedActionException) {
/* 277 */         Exception exception = privilegedActionException.getException();
/*     */ 
/*     */         
/* 280 */         if (exception instanceof InstantiationException)
/* 281 */           throw (InstantiationException)exception; 
/* 282 */         if (exception instanceof NoSuchMethodException)
/* 283 */           throw (NoSuchMethodException)exception; 
/* 284 */         if (exception instanceof IllegalAccessException)
/* 285 */           throw (IllegalAccessException)exception; 
/* 286 */         if (exception instanceof InvocationTargetException)
/* 287 */           throw (InvocationTargetException)exception; 
/* 288 */         if (exception instanceof RuntimeException)
/* 289 */           throw (RuntimeException)exception; 
/* 290 */         if (exception instanceof Error) {
/* 291 */           throw (Error)exception;
/*     */         }
/*     */       } 
/*     */       
/* 295 */       activeEntry = new ActiveEntry(remote);
/* 296 */       this.active.put(id, activeEntry);
/* 297 */       return activeEntry.mobj;
/*     */     }
/* 299 */     catch (NoSuchMethodException|NoSuchMethodError noSuchMethodException) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 304 */       throw new ActivationException("Activatable object must provide an activation constructor", noSuchMethodException);
/*     */ 
/*     */     
/*     */     }
/* 308 */     catch (InvocationTargetException invocationTargetException) {
/* 309 */       throw new ActivationException("exception in object constructor", invocationTargetException
/* 310 */           .getTargetException());
/*     */     }
/* 312 */     catch (Exception exception) {
/* 313 */       throw new ActivationException("unable to activate object", exception);
/*     */     } finally {
/* 315 */       releaseLock(id);
/* 316 */       checkInactiveGroup();
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */     
/* 362 */     try { acquireLock(paramActivationID);
/* 363 */       synchronized (this) {
/* 364 */         if (this.groupInactive == true) {
/* 365 */           throw new ActivationException("group is inactive");
/*     */         }
/*     */       } 
/* 368 */       ActiveEntry activeEntry = this.active.get(paramActivationID);
/* 369 */       if (activeEntry == null)
/*     */       {
/* 371 */         throw new UnknownObjectException("object not active");
/*     */       }
/*     */       
/*     */       try {
/* 375 */         if (!Activatable.unexportObject(activeEntry.impl, false))
/* 376 */           return false; 
/* 377 */       } catch (NoSuchObjectException noSuchObjectException) {}
/*     */ 
/*     */       
/*     */       try {
/* 381 */         super.inactiveObject(paramActivationID);
/* 382 */       } catch (UnknownObjectException unknownObjectException) {}
/*     */ 
/*     */       
/* 385 */       this.active.remove(paramActivationID);
/*     */ 
/*     */       
/* 388 */       releaseLock(paramActivationID);
/* 389 */       checkInactiveGroup(); } finally { releaseLock(paramActivationID); checkInactiveGroup(); }
/*     */ 
/*     */     
/* 392 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void checkInactiveGroup() {
/* 400 */     boolean bool = false;
/* 401 */     synchronized (this) {
/* 402 */       if (this.active.size() == 0 && this.lockedIDs.size() == 0 && !this.groupInactive) {
/*     */ 
/*     */         
/* 405 */         this.groupInactive = true;
/* 406 */         bool = true;
/*     */       } 
/*     */     } 
/*     */     
/* 410 */     if (bool) {
/*     */       try {
/* 412 */         inactiveGroup();
/* 413 */       } catch (Exception exception) {}
/*     */ 
/*     */       
/*     */       try {
/* 417 */         UnicastRemoteObject.unexportObject((Remote)this, true);
/* 418 */       } catch (NoSuchObjectException noSuchObjectException) {}
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
/*     */   public void activeObject(ActivationID paramActivationID, Remote paramRemote) throws ActivationException, UnknownObjectException, RemoteException {
/*     */     try {
/* 442 */       acquireLock(paramActivationID);
/* 443 */       synchronized (this) {
/* 444 */         if (this.groupInactive == true)
/* 445 */           throw new ActivationException("group is inactive"); 
/*     */       } 
/* 447 */       if (!this.active.contains(paramActivationID)) {
/* 448 */         ActiveEntry activeEntry = new ActiveEntry(paramRemote);
/* 449 */         this.active.put(paramActivationID, activeEntry);
/*     */         
/*     */         try {
/* 452 */           activeObject(paramActivationID, activeEntry.mobj);
/* 453 */         } catch (RemoteException remoteException) {}
/*     */       }
/*     */     
/*     */     } finally {
/*     */       
/* 458 */       releaseLock(paramActivationID);
/* 459 */       checkInactiveGroup();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static class ActiveEntry
/*     */   {
/*     */     Remote impl;
/*     */     
/*     */     MarshalledObject<Remote> mobj;
/*     */     
/*     */     ActiveEntry(Remote param1Remote) throws ActivationException {
/* 471 */       this.impl = param1Remote;
/*     */       try {
/* 473 */         this.mobj = new MarshalledObject<>(param1Remote);
/* 474 */       } catch (IOException iOException) {
/* 475 */         throw new ActivationException("failed to marshal remote object", iOException);
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
/*     */   private static boolean covers(ClassLoader paramClassLoader1, ClassLoader paramClassLoader2) {
/* 487 */     if (paramClassLoader2 == null)
/* 488 */       return true; 
/* 489 */     if (paramClassLoader1 == null) {
/* 490 */       return false;
/*     */     }
/*     */     while (true) {
/* 493 */       if (paramClassLoader1 == paramClassLoader2) {
/* 494 */         return true;
/*     */       }
/* 496 */       paramClassLoader1 = paramClassLoader1.getParent();
/* 497 */       if (paramClassLoader1 == null)
/* 498 */         return false; 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/rmi/server/ActivationGroupImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */