/*     */ package sun.rmi.transport;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInput;
/*     */ import java.io.ObjectOutput;
/*     */ import java.rmi.MarshalException;
/*     */ import java.rmi.RemoteException;
/*     */ import java.rmi.UnexpectedException;
/*     */ import java.rmi.UnmarshalException;
/*     */ import java.rmi.dgc.DGC;
/*     */ import java.rmi.dgc.Lease;
/*     */ import java.rmi.dgc.VMID;
/*     */ import java.rmi.server.ObjID;
/*     */ import java.rmi.server.Operation;
/*     */ import java.rmi.server.RemoteRef;
/*     */ import java.rmi.server.RemoteStub;
/*     */ import java.rmi.server.UID;
/*     */ import java.util.ArrayList;
/*     */ import sun.misc.ObjectInputFilter;
/*     */ import sun.rmi.transport.tcp.TCPConnection;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class DGCImpl_Stub
/*     */   extends RemoteStub
/*     */   implements DGC
/*     */ {
/*  46 */   private static final Operation[] operations = new Operation[] { new Operation("void clean(java.rmi.server.ObjID[], long, java.rmi.dgc.VMID, boolean)"), new Operation("java.rmi.dgc.Lease dirty(java.rmi.server.ObjID[], long, java.rmi.dgc.Lease)") };
/*     */ 
/*     */ 
/*     */   
/*     */   private static final long interfaceHash = -669196253586618813L;
/*     */ 
/*     */ 
/*     */   
/*  54 */   private static int DGCCLIENT_MAX_DEPTH = 6;
/*     */ 
/*     */   
/*  57 */   private static int DGCCLIENT_MAX_ARRAY_SIZE = 10000;
/*     */ 
/*     */ 
/*     */   
/*     */   public DGCImpl_Stub() {}
/*     */ 
/*     */   
/*     */   public DGCImpl_Stub(RemoteRef paramRemoteRef) {
/*  65 */     super(paramRemoteRef);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clean(ObjID[] paramArrayOfObjID, long paramLong, VMID paramVMID, boolean paramBoolean) throws RemoteException {
/*     */     try {
/*  74 */       StreamRemoteCall streamRemoteCall = (StreamRemoteCall)this.ref.newCall(this, operations, 0, -669196253586618813L);
/*     */       
/*  76 */       streamRemoteCall.setObjectInputFilter(DGCImpl_Stub::leaseFilter);
/*     */       try {
/*  78 */         ObjectOutput objectOutput = streamRemoteCall.getOutputStream();
/*  79 */         objectOutput.writeObject(paramArrayOfObjID);
/*  80 */         objectOutput.writeLong(paramLong);
/*  81 */         objectOutput.writeObject(paramVMID);
/*  82 */         objectOutput.writeBoolean(paramBoolean);
/*  83 */       } catch (IOException iOException) {
/*  84 */         throw new MarshalException("error marshalling arguments", iOException);
/*     */       } 
/*  86 */       this.ref.invoke(streamRemoteCall);
/*  87 */       this.ref.done(streamRemoteCall);
/*  88 */     } catch (RuntimeException runtimeException) {
/*  89 */       throw runtimeException;
/*  90 */     } catch (RemoteException remoteException) {
/*  91 */       throw remoteException;
/*  92 */     } catch (Exception exception) {
/*  93 */       throw new UnexpectedException("undeclared checked exception", exception);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Lease dirty(ObjID[] paramArrayOfObjID, long paramLong, Lease paramLease) throws RemoteException {
/*     */     try {
/*     */       Lease lease;
/* 102 */       StreamRemoteCall streamRemoteCall = (StreamRemoteCall)this.ref.newCall(this, operations, 1, -669196253586618813L);
/*     */       
/* 104 */       streamRemoteCall.setObjectInputFilter(DGCImpl_Stub::leaseFilter);
/*     */       try {
/* 106 */         ObjectOutput objectOutput = streamRemoteCall.getOutputStream();
/* 107 */         objectOutput.writeObject(paramArrayOfObjID);
/* 108 */         objectOutput.writeLong(paramLong);
/* 109 */         objectOutput.writeObject(paramLease);
/* 110 */       } catch (IOException iOException) {
/* 111 */         throw new MarshalException("error marshalling arguments", iOException);
/*     */       } 
/* 113 */       this.ref.invoke(streamRemoteCall);
/*     */       
/* 115 */       Connection connection = streamRemoteCall.getConnection();
/*     */       try {
/* 117 */         ObjectInput objectInput = streamRemoteCall.getInputStream();
/*     */         
/* 119 */         lease = (Lease)objectInput.readObject();
/* 120 */       } catch (ClassCastException|IOException|ClassNotFoundException classCastException) {
/* 121 */         if (connection instanceof TCPConnection)
/*     */         {
/* 123 */           ((TCPConnection)connection).getChannel().free(connection, false);
/*     */         }
/* 125 */         streamRemoteCall.discardPendingRefs();
/* 126 */         throw new UnmarshalException("error unmarshalling return", classCastException);
/*     */       } finally {
/* 128 */         this.ref.done(streamRemoteCall);
/*     */       } 
/* 130 */       return lease;
/* 131 */     } catch (RuntimeException runtimeException) {
/* 132 */       throw runtimeException;
/* 133 */     } catch (RemoteException remoteException) {
/* 134 */       throw remoteException;
/* 135 */     } catch (Exception exception) {
/* 136 */       throw new UnexpectedException("undeclared checked exception", exception);
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
/*     */   private static ObjectInputFilter.Status leaseFilter(ObjectInputFilter.FilterInfo paramFilterInfo) {
/* 156 */     if (paramFilterInfo.depth() > DGCCLIENT_MAX_DEPTH) {
/* 157 */       return ObjectInputFilter.Status.REJECTED;
/*     */     }
/* 159 */     Class<?> clazz = paramFilterInfo.serialClass();
/* 160 */     if (clazz != null) {
/* 161 */       while (clazz.isArray()) {
/* 162 */         if (paramFilterInfo.arrayLength() >= 0L && paramFilterInfo.arrayLength() > DGCCLIENT_MAX_ARRAY_SIZE) {
/* 163 */           return ObjectInputFilter.Status.REJECTED;
/*     */         }
/*     */         
/* 166 */         clazz = clazz.getComponentType();
/*     */       } 
/* 168 */       if (clazz.isPrimitive())
/*     */       {
/* 170 */         return ObjectInputFilter.Status.ALLOWED;
/*     */       }
/* 172 */       return (clazz == UID.class || clazz == VMID.class || clazz == Lease.class || (Throwable.class
/*     */ 
/*     */         
/* 175 */         .isAssignableFrom(clazz) && clazz
/* 176 */         .getClassLoader() == Object.class
/* 177 */         .getClassLoader()) || clazz == StackTraceElement.class || clazz == ArrayList.class || clazz == Object.class || clazz
/*     */ 
/*     */ 
/*     */         
/* 181 */         .getName().equals("java.util.Collections$UnmodifiableList") || clazz
/* 182 */         .getName().equals("java.util.Collections$UnmodifiableCollection") || clazz
/* 183 */         .getName().equals("java.util.Collections$UnmodifiableRandomAccessList")) ? ObjectInputFilter.Status.ALLOWED : ObjectInputFilter.Status.REJECTED;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 188 */     return ObjectInputFilter.Status.UNDECIDED;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/rmi/transport/DGCImpl_Stub.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */