/*     */ package sun.rmi.registry;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutput;
/*     */ import java.rmi.MarshalException;
/*     */ import java.rmi.Remote;
/*     */ import java.rmi.UnmarshalException;
/*     */ import java.rmi.server.Operation;
/*     */ import java.rmi.server.RemoteCall;
/*     */ import java.rmi.server.Skeleton;
/*     */ import java.rmi.server.SkeletonMismatchException;
/*     */ import sun.misc.SharedSecrets;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class RegistryImpl_Skel
/*     */   implements Skeleton
/*     */ {
/*  42 */   private static final Operation[] operations = new Operation[] { new Operation("void bind(java.lang.String, java.rmi.Remote)"), new Operation("java.lang.String list()[]"), new Operation("java.rmi.Remote lookup(java.lang.String)"), new Operation("void rebind(java.lang.String, java.rmi.Remote)"), new Operation("void unbind(java.lang.String)") };
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final long interfaceHash = 4905912898345647071L;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Operation[] getOperations() {
/*  53 */     return (Operation[])operations.clone();
/*     */   }
/*     */   public void dispatch(Remote paramRemote, RemoteCall paramRemoteCall, int paramInt, long paramLong) throws Exception {
/*     */     String str2, arrayOfString[], str1;
/*     */     Remote remote;
/*  58 */     if (paramInt < 0) {
/*  59 */       if (paramLong == 7583982177005850366L) {
/*  60 */         paramInt = 0;
/*  61 */       } else if (paramLong == 2571371476350237748L) {
/*  62 */         paramInt = 1;
/*  63 */       } else if (paramLong == -7538657168040752697L) {
/*  64 */         paramInt = 2;
/*  65 */       } else if (paramLong == -8381844669958460146L) {
/*  66 */         paramInt = 3;
/*  67 */       } else if (paramLong == 7305022919901907578L) {
/*  68 */         paramInt = 4;
/*     */       } else {
/*  70 */         throw new UnmarshalException("invalid method hash");
/*     */       }
/*     */     
/*  73 */     } else if (paramLong != 4905912898345647071L) {
/*  74 */       throw new SkeletonMismatchException("interface hash mismatch");
/*     */     } 
/*     */     
/*  77 */     RegistryImpl registryImpl = (RegistryImpl)paramRemote;
/*  78 */     StreamRemoteCall streamRemoteCall = (StreamRemoteCall)paramRemoteCall;
/*  79 */     switch (paramInt) {
/*     */ 
/*     */       
/*     */       case 0:
/*  83 */         RegistryImpl.checkAccess("Registry.bind");
/*     */ 
/*     */ 
/*     */         
/*     */         try {
/*  88 */           ObjectInputStream objectInputStream = (ObjectInputStream)streamRemoteCall.getInputStream();
/*     */           
/*  90 */           str2 = SharedSecrets.getJavaObjectInputStreamReadString().readString(objectInputStream);
/*  91 */           remote = (Remote)objectInputStream.readObject();
/*  92 */         } catch (ClassCastException|IOException|ClassNotFoundException classCastException) {
/*  93 */           streamRemoteCall.discardPendingRefs();
/*  94 */           throw new UnmarshalException("error unmarshalling arguments", classCastException);
/*     */         } finally {
/*  96 */           streamRemoteCall.releaseInputStream();
/*     */         } 
/*  98 */         registryImpl.bind(str2, remote);
/*     */         try {
/* 100 */           streamRemoteCall.getResultStream(true);
/* 101 */         } catch (IOException iOException) {
/* 102 */           throw new MarshalException("error marshalling return", iOException);
/*     */         } 
/*     */         return;
/*     */ 
/*     */ 
/*     */       
/*     */       case 1:
/* 109 */         streamRemoteCall.releaseInputStream();
/* 110 */         arrayOfString = registryImpl.list();
/*     */         try {
/* 112 */           ObjectOutput objectOutput = streamRemoteCall.getResultStream(true);
/* 113 */           objectOutput.writeObject(arrayOfString);
/* 114 */         } catch (IOException iOException) {
/* 115 */           throw new MarshalException("error marshalling return", iOException);
/*     */         } 
/*     */         return;
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       case 2:
/*     */         try {
/* 124 */           ObjectInputStream objectInputStream = (ObjectInputStream)streamRemoteCall.getInputStream();
/*     */           
/* 126 */           str1 = SharedSecrets.getJavaObjectInputStreamReadString().readString(objectInputStream);
/* 127 */         } catch (ClassCastException|IOException classCastException) {
/* 128 */           streamRemoteCall.discardPendingRefs();
/* 129 */           throw new UnmarshalException("error unmarshalling arguments", classCastException);
/*     */         } finally {
/* 131 */           streamRemoteCall.releaseInputStream();
/*     */         } 
/* 133 */         remote = registryImpl.lookup(str1);
/*     */         try {
/* 135 */           ObjectOutput objectOutput = streamRemoteCall.getResultStream(true);
/* 136 */           objectOutput.writeObject(remote);
/* 137 */         } catch (IOException iOException) {
/* 138 */           throw new MarshalException("error marshalling return", iOException);
/*     */         } 
/*     */         return;
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       case 3:
/* 146 */         RegistryImpl.checkAccess("Registry.rebind");
/*     */ 
/*     */ 
/*     */         
/*     */         try {
/* 151 */           ObjectInputStream objectInputStream = (ObjectInputStream)streamRemoteCall.getInputStream();
/*     */           
/* 153 */           str1 = SharedSecrets.getJavaObjectInputStreamReadString().readString(objectInputStream);
/* 154 */           remote = (Remote)objectInputStream.readObject();
/* 155 */         } catch (ClassCastException|IOException|ClassNotFoundException classCastException) {
/* 156 */           streamRemoteCall.discardPendingRefs();
/* 157 */           throw new UnmarshalException("error unmarshalling arguments", classCastException);
/*     */         } finally {
/* 159 */           streamRemoteCall.releaseInputStream();
/*     */         } 
/* 161 */         registryImpl.rebind(str1, remote);
/*     */         try {
/* 163 */           streamRemoteCall.getResultStream(true);
/* 164 */         } catch (IOException iOException) {
/* 165 */           throw new MarshalException("error marshalling return", iOException);
/*     */         } 
/*     */         return;
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       case 4:
/* 173 */         RegistryImpl.checkAccess("Registry.unbind");
/*     */ 
/*     */         
/*     */         try {
/* 177 */           ObjectInputStream objectInputStream = (ObjectInputStream)streamRemoteCall.getInputStream();
/*     */           
/* 179 */           str1 = SharedSecrets.getJavaObjectInputStreamReadString().readString(objectInputStream);
/* 180 */         } catch (ClassCastException|IOException classCastException) {
/* 181 */           streamRemoteCall.discardPendingRefs();
/* 182 */           throw new UnmarshalException("error unmarshalling arguments", classCastException);
/*     */         } finally {
/* 184 */           streamRemoteCall.releaseInputStream();
/*     */         } 
/* 186 */         registryImpl.unbind(str1);
/*     */         try {
/* 188 */           streamRemoteCall.getResultStream(true);
/* 189 */         } catch (IOException iOException) {
/* 190 */           throw new MarshalException("error marshalling return", iOException);
/*     */         } 
/*     */         return;
/*     */     } 
/*     */ 
/*     */     
/* 196 */     throw new UnmarshalException("invalid method number");
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/rmi/registry/RegistryImpl_Skel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */