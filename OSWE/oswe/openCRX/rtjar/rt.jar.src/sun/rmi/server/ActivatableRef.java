/*     */ package sun.rmi.server;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInput;
/*     */ import java.io.ObjectOutput;
/*     */ import java.lang.reflect.Method;
/*     */ import java.lang.reflect.Proxy;
/*     */ import java.net.MalformedURLException;
/*     */ import java.rmi.ConnectException;
/*     */ import java.rmi.ConnectIOException;
/*     */ import java.rmi.MarshalException;
/*     */ import java.rmi.NoSuchObjectException;
/*     */ import java.rmi.Remote;
/*     */ import java.rmi.RemoteException;
/*     */ import java.rmi.ServerError;
/*     */ import java.rmi.ServerException;
/*     */ import java.rmi.StubNotFoundException;
/*     */ import java.rmi.UnknownHostException;
/*     */ import java.rmi.UnmarshalException;
/*     */ import java.rmi.activation.ActivateFailedException;
/*     */ import java.rmi.activation.ActivationDesc;
/*     */ import java.rmi.activation.ActivationException;
/*     */ import java.rmi.activation.ActivationID;
/*     */ import java.rmi.activation.UnknownObjectException;
/*     */ import java.rmi.server.Operation;
/*     */ import java.rmi.server.RMIClassLoader;
/*     */ import java.rmi.server.RemoteCall;
/*     */ import java.rmi.server.RemoteObject;
/*     */ import java.rmi.server.RemoteObjectInvocationHandler;
/*     */ import java.rmi.server.RemoteRef;
/*     */ import java.rmi.server.RemoteStub;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ActivatableRef
/*     */   implements RemoteRef
/*     */ {
/*     */   private static final long serialVersionUID = 7579060052569229166L;
/*     */   protected ActivationID id;
/*     */   protected RemoteRef ref;
/*     */   transient boolean force = false;
/*     */   private static final int MAX_RETRIES = 3;
/*     */   private static final String versionComplaint = "activation requires 1.2 stubs";
/*     */   
/*     */   public ActivatableRef() {}
/*     */   
/*     */   public ActivatableRef(ActivationID paramActivationID, RemoteRef paramRemoteRef) {
/*  68 */     this.id = paramActivationID;
/*  69 */     this.ref = paramRemoteRef;
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
/*     */   public static Remote getStub(ActivationDesc paramActivationDesc, ActivationID paramActivationID) throws StubNotFoundException {
/*  81 */     String str = paramActivationDesc.getClassName();
/*     */ 
/*     */     
/*     */     try {
/*  85 */       Class<?> clazz = RMIClassLoader.loadClass(paramActivationDesc.getLocation(), str);
/*  86 */       ActivatableRef activatableRef = new ActivatableRef(paramActivationID, null);
/*  87 */       return Util.createProxy(clazz, activatableRef, false);
/*     */     }
/*  89 */     catch (IllegalArgumentException illegalArgumentException) {
/*  90 */       throw new StubNotFoundException("class implements an illegal remote interface", illegalArgumentException);
/*     */     
/*     */     }
/*  93 */     catch (ClassNotFoundException classNotFoundException) {
/*  94 */       throw new StubNotFoundException("unable to load class: " + str, classNotFoundException);
/*     */     }
/*  96 */     catch (MalformedURLException malformedURLException) {
/*  97 */       throw new StubNotFoundException("malformed URL", malformedURLException);
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
/*     */   public Object invoke(Remote paramRemote, Method paramMethod, Object[] paramArrayOfObject, long paramLong) throws Exception {
/*     */     RemoteRef remoteRef;
/*     */     ConnectIOException connectIOException;
/* 123 */     boolean bool = false;
/*     */     
/* 125 */     NoSuchObjectException noSuchObjectException = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 131 */     synchronized (this) {
/* 132 */       if (this.ref == null) {
/* 133 */         remoteRef = activate(bool);
/* 134 */         bool = true;
/*     */       } else {
/* 136 */         remoteRef = this.ref;
/*     */       } 
/*     */     } 
/*     */     
/* 140 */     for (byte b = 3; b > 0; b--) {
/*     */       
/*     */       try {
/* 143 */         return remoteRef.invoke(paramRemote, paramMethod, paramArrayOfObject, paramLong);
/* 144 */       } catch (NoSuchObjectException noSuchObjectException1) {
/*     */ 
/*     */ 
/*     */         
/* 148 */         noSuchObjectException = noSuchObjectException1;
/* 149 */       } catch (ConnectException connectException2) {
/*     */ 
/*     */ 
/*     */         
/* 153 */         ConnectException connectException1 = connectException2;
/* 154 */       } catch (UnknownHostException unknownHostException2) {
/*     */ 
/*     */ 
/*     */         
/* 158 */         UnknownHostException unknownHostException1 = unknownHostException2;
/* 159 */       } catch (ConnectIOException connectIOException1) {
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 164 */         connectIOException = connectIOException1;
/* 165 */       } catch (MarshalException marshalException) {
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 170 */         throw marshalException;
/* 171 */       } catch (ServerError serverError) {
/*     */ 
/*     */ 
/*     */         
/* 175 */         throw serverError;
/* 176 */       } catch (ServerException serverException) {
/*     */ 
/*     */ 
/*     */         
/* 180 */         throw serverException;
/* 181 */       } catch (RemoteException remoteException) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 195 */         synchronized (this) {
/* 196 */           if (remoteRef == this.ref) {
/* 197 */             this.ref = null;
/*     */           }
/*     */         } 
/*     */         
/* 201 */         throw remoteException;
/*     */       } 
/*     */       
/* 204 */       if (b > 1)
/*     */       {
/*     */ 
/*     */         
/* 208 */         synchronized (this) {
/* 209 */           if (remoteRef.remoteEquals(this.ref) || this.ref == null) {
/* 210 */             RemoteRef remoteRef1 = activate(bool);
/*     */             
/* 212 */             if (remoteRef1.remoteEquals(remoteRef) && connectIOException instanceof NoSuchObjectException && !bool)
/*     */             {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */               
/* 220 */               remoteRef1 = activate(true);
/*     */             }
/*     */             
/* 223 */             remoteRef = remoteRef1;
/* 224 */             bool = true;
/*     */           } else {
/* 226 */             remoteRef = this.ref;
/* 227 */             bool = false;
/*     */           } 
/*     */         } 
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 236 */     throw connectIOException;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private synchronized RemoteRef getRef() throws RemoteException {
/* 245 */     if (this.ref == null) {
/* 246 */       this.ref = activate(false);
/*     */     }
/*     */     
/* 249 */     return this.ref;
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
/*     */   private RemoteRef activate(boolean paramBoolean) throws RemoteException {
/* 261 */     assert Thread.holdsLock(this);
/*     */     
/* 263 */     this.ref = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 277 */       Remote remote = this.id.activate(paramBoolean);
/* 278 */       ActivatableRef activatableRef = null;
/*     */       
/* 280 */       if (remote instanceof RemoteStub) {
/* 281 */         activatableRef = (ActivatableRef)((RemoteStub)remote).getRef();
/*     */ 
/*     */ 
/*     */       
/*     */       }
/*     */       else {
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 291 */         RemoteObjectInvocationHandler remoteObjectInvocationHandler = (RemoteObjectInvocationHandler)Proxy.getInvocationHandler(remote);
/* 292 */         activatableRef = (ActivatableRef)remoteObjectInvocationHandler.getRef();
/*     */       } 
/* 294 */       this.ref = activatableRef.ref;
/* 295 */       return this.ref;
/*     */     }
/* 297 */     catch (ConnectException connectException) {
/* 298 */       throw new ConnectException("activation failed", connectException);
/* 299 */     } catch (RemoteException remoteException) {
/* 300 */       throw new ConnectIOException("activation failed", remoteException);
/* 301 */     } catch (UnknownObjectException unknownObjectException) {
/* 302 */       throw new NoSuchObjectException("object not registered");
/* 303 */     } catch (ActivationException activationException) {
/* 304 */       throw new ActivateFailedException("activation failed", activationException);
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
/*     */   public synchronized RemoteCall newCall(RemoteObject paramRemoteObject, Operation[] paramArrayOfOperation, int paramInt, long paramLong) throws RemoteException {
/* 318 */     throw new UnsupportedOperationException("activation requires 1.2 stubs");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void invoke(RemoteCall paramRemoteCall) throws Exception {
/* 327 */     throw new UnsupportedOperationException("activation requires 1.2 stubs");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void done(RemoteCall paramRemoteCall) throws RemoteException {
/* 335 */     throw new UnsupportedOperationException("activation requires 1.2 stubs");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getRefClass(ObjectOutput paramObjectOutput) {
/* 343 */     return "ActivatableRef";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeExternal(ObjectOutput paramObjectOutput) throws IOException {
/* 351 */     RemoteRef remoteRef = this.ref;
/*     */     
/* 353 */     paramObjectOutput.writeObject(this.id);
/* 354 */     if (remoteRef == null) {
/* 355 */       paramObjectOutput.writeUTF("");
/*     */     } else {
/* 357 */       paramObjectOutput.writeUTF(remoteRef.getRefClass(paramObjectOutput));
/* 358 */       remoteRef.writeExternal(paramObjectOutput);
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
/*     */   public void readExternal(ObjectInput paramObjectInput) throws IOException, ClassNotFoundException {
/* 370 */     this.id = (ActivationID)paramObjectInput.readObject();
/* 371 */     this.ref = null;
/* 372 */     String str = paramObjectInput.readUTF();
/*     */     
/* 374 */     if (str.equals(""))
/*     */       return; 
/*     */     try {
/* 377 */       Class<?> clazz = Class.forName("sun.rmi.server." + str);
/*     */       
/* 379 */       this.ref = (RemoteRef)clazz.newInstance();
/* 380 */       this.ref.readExternal(paramObjectInput);
/* 381 */     } catch (InstantiationException instantiationException) {
/* 382 */       throw new UnmarshalException("Unable to create remote reference", instantiationException);
/*     */     }
/* 384 */     catch (IllegalAccessException illegalAccessException) {
/* 385 */       throw new UnmarshalException("Illegal access creating remote reference");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String remoteToString() {
/* 394 */     return Util.getUnqualifiedName(getClass()) + " [remoteRef: " + this.ref + "]";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int remoteHashCode() {
/* 402 */     return this.id.hashCode();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean remoteEquals(RemoteRef paramRemoteRef) {
/* 408 */     if (paramRemoteRef instanceof ActivatableRef)
/* 409 */       return this.id.equals(((ActivatableRef)paramRemoteRef).id); 
/* 410 */     return false;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/rmi/server/ActivatableRef.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */