/*     */ package sun.rmi.transport;
/*     */ 
/*     */ import java.io.DataOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.rmi.RemoteException;
/*     */ import java.rmi.server.UID;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import sun.rmi.runtime.Log;
/*     */ import sun.rmi.server.MarshalInputStream;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class ConnectionInputStream
/*     */   extends MarshalInputStream
/*     */ {
/*     */   private boolean dgcAckNeeded = false;
/*  46 */   private Map<Endpoint, List<LiveRef>> incomingRefTable = new HashMap<>(5);
/*     */ 
/*     */ 
/*     */   
/*     */   private UID ackID;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   ConnectionInputStream(InputStream paramInputStream) throws IOException {
/*  56 */     super(paramInputStream);
/*     */   }
/*     */   
/*     */   void readID() throws IOException {
/*  60 */     this.ackID = UID.read(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void saveRef(LiveRef paramLiveRef) {
/*  70 */     Endpoint endpoint = paramLiveRef.getEndpoint();
/*     */ 
/*     */     
/*  73 */     List<LiveRef> list = this.incomingRefTable.get(endpoint);
/*     */     
/*  75 */     if (list == null) {
/*  76 */       list = new ArrayList();
/*  77 */       this.incomingRefTable.put(endpoint, list);
/*     */     } 
/*     */ 
/*     */     
/*  81 */     list.add(paramLiveRef);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void discardRefs() {
/*  89 */     this.incomingRefTable.clear();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void registerRefs() throws IOException {
/*  99 */     if (!this.incomingRefTable.isEmpty())
/*     */     {
/* 101 */       for (Map.Entry<Endpoint, List<LiveRef>> entry : this.incomingRefTable.entrySet()) {
/* 102 */         DGCClient.registerRefs((Endpoint)entry.getKey(), (List<LiveRef>)entry.getValue());
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void setAckNeeded() {
/* 112 */     this.dgcAckNeeded = true;
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
/*     */   void done(Connection paramConnection) {
/* 125 */     if (this.dgcAckNeeded) {
/* 126 */       Connection connection = null;
/* 127 */       Channel channel = null;
/* 128 */       boolean bool = true;
/*     */       
/* 130 */       DGCImpl.dgcLog.log(Log.VERBOSE, "send ack");
/*     */       
/*     */       try {
/* 133 */         channel = paramConnection.getChannel();
/* 134 */         connection = channel.newConnection();
/*     */         
/* 136 */         DataOutputStream dataOutputStream = new DataOutputStream(connection.getOutputStream());
/* 137 */         dataOutputStream.writeByte(84);
/* 138 */         if (this.ackID == null) {
/* 139 */           this.ackID = new UID();
/*     */         }
/* 141 */         this.ackID.write(dataOutputStream);
/* 142 */         connection.releaseOutputStream();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 151 */         connection.getInputStream().available();
/* 152 */         connection.releaseInputStream();
/* 153 */       } catch (RemoteException remoteException) {
/* 154 */         bool = false;
/* 155 */       } catch (IOException iOException) {
/* 156 */         bool = false;
/*     */       } 
/*     */       try {
/* 159 */         if (connection != null)
/* 160 */           channel.free(connection, bool); 
/* 161 */       } catch (RemoteException remoteException) {}
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/rmi/transport/ConnectionInputStream.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */