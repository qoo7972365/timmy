/*     */ package sun.rmi.transport;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInput;
/*     */ import java.io.ObjectOutput;
/*     */ import java.rmi.MarshalException;
/*     */ import java.rmi.Remote;
/*     */ import java.rmi.UnmarshalException;
/*     */ import java.rmi.dgc.Lease;
/*     */ import java.rmi.dgc.VMID;
/*     */ import java.rmi.server.ObjID;
/*     */ import java.rmi.server.Operation;
/*     */ import java.rmi.server.RemoteCall;
/*     */ import java.rmi.server.Skeleton;
/*     */ import java.rmi.server.SkeletonMismatchException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class DGCImpl_Skel
/*     */   implements Skeleton
/*     */ {
/*  37 */   private static final Operation[] operations = new Operation[] { new Operation("void clean(java.rmi.server.ObjID[], long, java.rmi.dgc.VMID, boolean)"), new Operation("java.rmi.dgc.Lease dirty(java.rmi.server.ObjID[], long, java.rmi.dgc.Lease)") };
/*     */ 
/*     */   
/*     */   private static final long interfaceHash = -669196253586618813L;
/*     */ 
/*     */ 
/*     */   
/*     */   public Operation[] getOperations() {
/*  45 */     return (Operation[])operations.clone(); } public void dispatch(Remote paramRemote, RemoteCall paramRemoteCall, int paramInt, long paramLong) throws Exception { ObjID[] arrayOfObjID; long l;
/*     */     VMID vMID;
/*     */     Lease lease1;
/*     */     boolean bool;
/*     */     Lease lease2;
/*  50 */     if (paramLong != -669196253586618813L) {
/*  51 */       throw new SkeletonMismatchException("interface hash mismatch");
/*     */     }
/*  53 */     DGCImpl dGCImpl = (DGCImpl)paramRemote;
/*  54 */     StreamRemoteCall streamRemoteCall = (StreamRemoteCall)paramRemoteCall;
/*  55 */     switch (paramInt) {
/*     */       
/*     */       case 0:
/*     */         
/*     */         try {
/*     */ 
/*     */ 
/*     */           
/*  63 */           ObjectInput objectInput = streamRemoteCall.getInputStream();
/*  64 */           arrayOfObjID = (ObjID[])objectInput.readObject();
/*  65 */           l = objectInput.readLong();
/*  66 */           vMID = (VMID)objectInput.readObject();
/*  67 */           bool = objectInput.readBoolean();
/*  68 */         } catch (ClassCastException|IOException|ClassNotFoundException classCastException) {
/*  69 */           streamRemoteCall.discardPendingRefs();
/*  70 */           throw new UnmarshalException("error unmarshalling arguments", classCastException);
/*     */         } finally {
/*  72 */           streamRemoteCall.releaseInputStream();
/*     */         } 
/*  74 */         dGCImpl.clean(arrayOfObjID, l, vMID, bool);
/*     */         try {
/*  76 */           streamRemoteCall.getResultStream(true);
/*  77 */         } catch (IOException iOException) {
/*  78 */           throw new MarshalException("error marshalling return", iOException);
/*     */         } 
/*     */         return;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       case 1:
/*     */         try {
/*  89 */           ObjectInput objectInput = streamRemoteCall.getInputStream();
/*  90 */           arrayOfObjID = (ObjID[])objectInput.readObject();
/*  91 */           l = objectInput.readLong();
/*  92 */           lease1 = (Lease)objectInput.readObject();
/*  93 */         } catch (ClassCastException|IOException|ClassNotFoundException classCastException) {
/*  94 */           streamRemoteCall.discardPendingRefs();
/*  95 */           throw new UnmarshalException("error unmarshalling arguments", classCastException);
/*     */         } finally {
/*  97 */           streamRemoteCall.releaseInputStream();
/*     */         } 
/*  99 */         lease2 = dGCImpl.dirty(arrayOfObjID, l, lease1);
/*     */         try {
/* 101 */           ObjectOutput objectOutput = streamRemoteCall.getResultStream(true);
/* 102 */           objectOutput.writeObject(lease2);
/* 103 */         } catch (IOException iOException) {
/* 104 */           throw new MarshalException("error marshalling return", iOException);
/*     */         } 
/*     */         return;
/*     */     } 
/*     */ 
/*     */     
/* 110 */     throw new UnmarshalException("invalid method number"); }
/*     */ 
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/rmi/transport/DGCImpl_Skel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */