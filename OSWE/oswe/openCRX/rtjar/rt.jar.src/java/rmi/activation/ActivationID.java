/*     */ package java.rmi.activation;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InvalidObjectException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.Serializable;
/*     */ import java.lang.reflect.InvocationHandler;
/*     */ import java.lang.reflect.Proxy;
/*     */ import java.rmi.MarshalledObject;
/*     */ import java.rmi.Remote;
/*     */ import java.rmi.RemoteException;
/*     */ import java.rmi.UnmarshalException;
/*     */ import java.rmi.server.RemoteObject;
/*     */ import java.rmi.server.RemoteObjectInvocationHandler;
/*     */ import java.rmi.server.RemoteRef;
/*     */ import java.rmi.server.UID;
/*     */ import java.security.AccessControlContext;
/*     */ import java.security.AccessController;
/*     */ import java.security.Permissions;
/*     */ import java.security.PrivilegedActionException;
/*     */ import java.security.PrivilegedExceptionAction;
/*     */ import java.security.ProtectionDomain;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ActivationID
/*     */   implements Serializable
/*     */ {
/*     */   private transient Activator activator;
/*  85 */   private transient UID uid = new UID();
/*     */   
/*     */   private static final long serialVersionUID = -4608673054848209235L;
/*     */   
/*     */   private static final AccessControlContext NOPERMS_ACC;
/*     */ 
/*     */   
/*     */   static {
/*  93 */     Permissions permissions = new Permissions();
/*  94 */     ProtectionDomain[] arrayOfProtectionDomain = { new ProtectionDomain(null, permissions) };
/*  95 */     NOPERMS_ACC = new AccessControlContext(arrayOfProtectionDomain);
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
/*     */   public ActivationID(Activator paramActivator) {
/* 112 */     this.activator = paramActivator;
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
/*     */   public Remote activate(boolean paramBoolean) throws ActivationException, UnknownObjectException, RemoteException {
/*     */     try {
/* 132 */       final MarshalledObject<? extends Remote> mobj = this.activator.activate(this, paramBoolean);
/* 133 */       return AccessController.<Remote>doPrivileged(new PrivilegedExceptionAction<Remote>()
/*     */           {
/*     */             public Remote run() throws IOException, ClassNotFoundException {
/* 136 */               return mobj.get();
/*     */             }
/*     */           },  NOPERMS_ACC);
/* 139 */     } catch (PrivilegedActionException privilegedActionException) {
/* 140 */       Exception exception = privilegedActionException.getException();
/* 141 */       if (exception instanceof RemoteException) {
/* 142 */         throw (RemoteException)exception;
/*     */       }
/* 144 */       throw new UnmarshalException("activation failed", exception);
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
/*     */   public int hashCode() {
/* 158 */     return this.uid.hashCode();
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
/*     */   public boolean equals(Object paramObject) {
/* 174 */     if (paramObject instanceof ActivationID) {
/* 175 */       ActivationID activationID = (ActivationID)paramObject;
/* 176 */       return (this.uid.equals(activationID.uid) && this.activator.equals(activationID.activator));
/*     */     } 
/* 178 */     return false;
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
/*     */ 
/*     */ 
/*     */   
/*     */   private void writeObject(ObjectOutputStream paramObjectOutputStream) throws IOException, ClassNotFoundException {
/*     */     RemoteRef remoteRef;
/* 230 */     paramObjectOutputStream.writeObject(this.uid);
/*     */ 
/*     */     
/* 233 */     if (this.activator instanceof RemoteObject) {
/* 234 */       remoteRef = ((RemoteObject)this.activator).getRef();
/* 235 */     } else if (Proxy.isProxyClass(this.activator.getClass())) {
/* 236 */       InvocationHandler invocationHandler = Proxy.getInvocationHandler(this.activator);
/* 237 */       if (!(invocationHandler instanceof RemoteObjectInvocationHandler)) {
/* 238 */         throw new InvalidObjectException("unexpected invocation handler");
/*     */       }
/*     */       
/* 241 */       remoteRef = ((RemoteObjectInvocationHandler)invocationHandler).getRef();
/*     */     } else {
/*     */       
/* 244 */       throw new InvalidObjectException("unexpected activator type");
/*     */     } 
/* 246 */     paramObjectOutputStream.writeUTF(remoteRef.getRefClass(paramObjectOutputStream));
/* 247 */     remoteRef.writeExternal(paramObjectOutputStream);
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
/*     */   private void readObject(ObjectInputStream paramObjectInputStream) throws IOException, ClassNotFoundException {
/* 289 */     this.uid = (UID)paramObjectInputStream.readObject();
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 294 */       Class<? extends RemoteRef> clazz = Class.forName("sun.rmi.server." + paramObjectInputStream.readUTF()).asSubclass(RemoteRef.class);
/* 295 */       RemoteRef remoteRef = clazz.newInstance();
/* 296 */       remoteRef.readExternal(paramObjectInputStream);
/* 297 */       this
/* 298 */         .activator = (Activator)Proxy.newProxyInstance((ClassLoader)null, new Class[] { Activator.class }, new RemoteObjectInvocationHandler(remoteRef));
/*     */ 
/*     */     
/*     */     }
/* 302 */     catch (InstantiationException instantiationException) {
/* 303 */       throw (IOException)(new InvalidObjectException("Unable to create remote reference"))
/*     */         
/* 305 */         .initCause(instantiationException);
/* 306 */     } catch (IllegalAccessException illegalAccessException) {
/* 307 */       throw (IOException)(new InvalidObjectException("Unable to create remote reference"))
/*     */         
/* 309 */         .initCause(illegalAccessException);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/rmi/activation/ActivationID.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */