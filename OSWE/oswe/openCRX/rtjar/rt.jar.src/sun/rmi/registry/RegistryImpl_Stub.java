/*     */ package sun.rmi.registry;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInput;
/*     */ import java.io.ObjectOutput;
/*     */ import java.rmi.AccessException;
/*     */ import java.rmi.AlreadyBoundException;
/*     */ import java.rmi.MarshalException;
/*     */ import java.rmi.NotBoundException;
/*     */ import java.rmi.Remote;
/*     */ import java.rmi.RemoteException;
/*     */ import java.rmi.UnexpectedException;
/*     */ import java.rmi.UnmarshalException;
/*     */ import java.rmi.registry.Registry;
/*     */ import java.rmi.server.Operation;
/*     */ import java.rmi.server.RemoteRef;
/*     */ import java.rmi.server.RemoteStub;
/*     */ import sun.rmi.transport.StreamRemoteCall;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class RegistryImpl_Stub
/*     */   extends RemoteStub
/*     */   implements Registry, Remote
/*     */ {
/*  40 */   private static final Operation[] operations = new Operation[] { new Operation("void bind(java.lang.String, java.rmi.Remote)"), new Operation("java.lang.String list()[]"), new Operation("java.rmi.Remote lookup(java.lang.String)"), new Operation("void rebind(java.lang.String, java.rmi.Remote)"), new Operation("void unbind(java.lang.String)") };
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final long interfaceHash = 4905912898345647071L;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RegistryImpl_Stub() {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RegistryImpl_Stub(RemoteRef paramRemoteRef) {
/*  56 */     super(paramRemoteRef);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void bind(String paramString, Remote paramRemote) throws AccessException, AlreadyBoundException, RemoteException {
/*     */     try {
/*  65 */       StreamRemoteCall streamRemoteCall = (StreamRemoteCall)this.ref.newCall(this, operations, 0, 4905912898345647071L);
/*     */       try {
/*  67 */         ObjectOutput objectOutput = streamRemoteCall.getOutputStream();
/*  68 */         objectOutput.writeObject(paramString);
/*  69 */         objectOutput.writeObject(paramRemote);
/*  70 */       } catch (IOException iOException) {
/*  71 */         throw new MarshalException("error marshalling arguments", iOException);
/*     */       } 
/*  73 */       this.ref.invoke(streamRemoteCall);
/*  74 */       this.ref.done(streamRemoteCall);
/*  75 */     } catch (RuntimeException runtimeException) {
/*  76 */       throw runtimeException;
/*  77 */     } catch (RemoteException remoteException) {
/*  78 */       throw remoteException;
/*  79 */     } catch (AlreadyBoundException alreadyBoundException) {
/*  80 */       throw alreadyBoundException;
/*  81 */     } catch (Exception exception) {
/*  82 */       throw new UnexpectedException("undeclared checked exception", exception);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public String[] list() throws AccessException, RemoteException {
/*     */     try {
/*     */       String[] arrayOfString;
/*  90 */       StreamRemoteCall streamRemoteCall = (StreamRemoteCall)this.ref.newCall(this, operations, 1, 4905912898345647071L);
/*  91 */       this.ref.invoke(streamRemoteCall);
/*     */       
/*     */       try {
/*  94 */         ObjectInput objectInput = streamRemoteCall.getInputStream();
/*  95 */         arrayOfString = (String[])objectInput.readObject();
/*  96 */       } catch (ClassCastException|IOException|ClassNotFoundException classCastException) {
/*  97 */         streamRemoteCall.discardPendingRefs();
/*  98 */         throw new UnmarshalException("error unmarshalling return", classCastException);
/*     */       } finally {
/* 100 */         this.ref.done(streamRemoteCall);
/*     */       } 
/* 102 */       return arrayOfString;
/* 103 */     } catch (RuntimeException runtimeException) {
/* 104 */       throw runtimeException;
/* 105 */     } catch (RemoteException remoteException) {
/* 106 */       throw remoteException;
/* 107 */     } catch (Exception exception) {
/* 108 */       throw new UnexpectedException("undeclared checked exception", exception);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public Remote lookup(String paramString) throws AccessException, NotBoundException, RemoteException {
/*     */     try {
/*     */       Remote remote;
/* 116 */       StreamRemoteCall streamRemoteCall = (StreamRemoteCall)this.ref.newCall(this, operations, 2, 4905912898345647071L);
/*     */       try {
/* 118 */         ObjectOutput objectOutput = streamRemoteCall.getOutputStream();
/* 119 */         objectOutput.writeObject(paramString);
/* 120 */       } catch (IOException iOException) {
/* 121 */         throw new MarshalException("error marshalling arguments", iOException);
/*     */       } 
/* 123 */       this.ref.invoke(streamRemoteCall);
/*     */       
/*     */       try {
/* 126 */         ObjectInput objectInput = streamRemoteCall.getInputStream();
/* 127 */         remote = (Remote)objectInput.readObject();
/* 128 */       } catch (ClassCastException|IOException|ClassNotFoundException classCastException) {
/* 129 */         streamRemoteCall.discardPendingRefs();
/* 130 */         throw new UnmarshalException("error unmarshalling return", classCastException);
/*     */       } finally {
/* 132 */         this.ref.done(streamRemoteCall);
/*     */       } 
/* 134 */       return remote;
/* 135 */     } catch (RuntimeException runtimeException) {
/* 136 */       throw runtimeException;
/* 137 */     } catch (RemoteException remoteException) {
/* 138 */       throw remoteException;
/* 139 */     } catch (NotBoundException notBoundException) {
/* 140 */       throw notBoundException;
/* 141 */     } catch (Exception exception) {
/* 142 */       throw new UnexpectedException("undeclared checked exception", exception);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void rebind(String paramString, Remote paramRemote) throws AccessException, RemoteException {
/*     */     try {
/* 150 */       StreamRemoteCall streamRemoteCall = (StreamRemoteCall)this.ref.newCall(this, operations, 3, 4905912898345647071L);
/*     */       try {
/* 152 */         ObjectOutput objectOutput = streamRemoteCall.getOutputStream();
/* 153 */         objectOutput.writeObject(paramString);
/* 154 */         objectOutput.writeObject(paramRemote);
/* 155 */       } catch (IOException iOException) {
/* 156 */         throw new MarshalException("error marshalling arguments", iOException);
/*     */       } 
/* 158 */       this.ref.invoke(streamRemoteCall);
/* 159 */       this.ref.done(streamRemoteCall);
/* 160 */     } catch (RuntimeException runtimeException) {
/* 161 */       throw runtimeException;
/* 162 */     } catch (RemoteException remoteException) {
/* 163 */       throw remoteException;
/* 164 */     } catch (Exception exception) {
/* 165 */       throw new UnexpectedException("undeclared checked exception", exception);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void unbind(String paramString) throws AccessException, NotBoundException, RemoteException {
/*     */     try {
/* 173 */       StreamRemoteCall streamRemoteCall = (StreamRemoteCall)this.ref.newCall(this, operations, 4, 4905912898345647071L);
/*     */       try {
/* 175 */         ObjectOutput objectOutput = streamRemoteCall.getOutputStream();
/* 176 */         objectOutput.writeObject(paramString);
/* 177 */       } catch (IOException iOException) {
/* 178 */         throw new MarshalException("error marshalling arguments", iOException);
/*     */       } 
/* 180 */       this.ref.invoke(streamRemoteCall);
/* 181 */       this.ref.done(streamRemoteCall);
/* 182 */     } catch (RuntimeException runtimeException) {
/* 183 */       throw runtimeException;
/* 184 */     } catch (RemoteException remoteException) {
/* 185 */       throw remoteException;
/* 186 */     } catch (NotBoundException notBoundException) {
/* 187 */       throw notBoundException;
/* 188 */     } catch (Exception exception) {
/* 189 */       throw new UnexpectedException("undeclared checked exception", exception);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/rmi/registry/RegistryImpl_Stub.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */