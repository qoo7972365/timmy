/*     */ package sun.rmi.transport;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInput;
/*     */ import java.io.ObjectOutput;
/*     */ import java.rmi.Remote;
/*     */ import java.rmi.RemoteException;
/*     */ import java.rmi.server.ObjID;
/*     */ import java.rmi.server.RMIClientSocketFactory;
/*     */ import java.rmi.server.RMIServerSocketFactory;
/*     */ import java.util.Arrays;
/*     */ import sun.rmi.transport.tcp.TCPEndpoint;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class LiveRef
/*     */   implements Cloneable
/*     */ {
/*     */   private final Endpoint ep;
/*     */   private final ObjID id;
/*     */   private transient Channel ch;
/*     */   private final boolean isLocal;
/*     */   
/*     */   public LiveRef(ObjID paramObjID, Endpoint paramEndpoint, boolean paramBoolean) {
/*  64 */     this.ep = paramEndpoint;
/*  65 */     this.id = paramObjID;
/*  66 */     this.isLocal = paramBoolean;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LiveRef(int paramInt) {
/*  74 */     this(new ObjID(), paramInt);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LiveRef(int paramInt, RMIClientSocketFactory paramRMIClientSocketFactory, RMIServerSocketFactory paramRMIServerSocketFactory) {
/*  85 */     this(new ObjID(), paramInt, paramRMIClientSocketFactory, paramRMIServerSocketFactory);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LiveRef(ObjID paramObjID, int paramInt) {
/*  93 */     this(paramObjID, TCPEndpoint.getLocalEndpoint(paramInt), true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LiveRef(ObjID paramObjID, int paramInt, RMIClientSocketFactory paramRMIClientSocketFactory, RMIServerSocketFactory paramRMIServerSocketFactory) {
/* 103 */     this(paramObjID, TCPEndpoint.getLocalEndpoint(paramInt, paramRMIClientSocketFactory, paramRMIServerSocketFactory), true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object clone() {
/*     */     try {
/* 111 */       return super.clone();
/*     */     }
/* 113 */     catch (CloneNotSupportedException cloneNotSupportedException) {
/* 114 */       throw new InternalError(cloneNotSupportedException.toString(), cloneNotSupportedException);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getPort() {
/* 122 */     return ((TCPEndpoint)this.ep).getPort();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RMIClientSocketFactory getClientSocketFactory() {
/* 133 */     return ((TCPEndpoint)this.ep).getClientSocketFactory();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RMIServerSocketFactory getServerSocketFactory() {
/* 140 */     return ((TCPEndpoint)this.ep).getServerSocketFactory();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void exportObject(Target paramTarget) throws RemoteException {
/* 147 */     this.ep.exportObject(paramTarget);
/*     */   }
/*     */   
/*     */   public Channel getChannel() throws RemoteException {
/* 151 */     if (this.ch == null) {
/* 152 */       this.ch = this.ep.getChannel();
/*     */     }
/* 154 */     return this.ch;
/*     */   }
/*     */   
/*     */   public ObjID getObjID() {
/* 158 */     return this.id;
/*     */   }
/*     */   
/*     */   Endpoint getEndpoint() {
/* 162 */     return this.ep;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/*     */     String str;
/* 168 */     if (this.isLocal) {
/* 169 */       str = "local";
/*     */     } else {
/* 171 */       str = "remote";
/* 172 */     }  return "[endpoint:" + this.ep + "(" + str + "),objID:" + this.id + "]";
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 177 */     return this.id.hashCode();
/*     */   }
/*     */   
/*     */   public boolean equals(Object paramObject) {
/* 181 */     if (paramObject != null && paramObject instanceof LiveRef) {
/* 182 */       LiveRef liveRef = (LiveRef)paramObject;
/*     */       
/* 184 */       return (this.ep.equals(liveRef.ep) && this.id.equals(liveRef.id) && this.isLocal == liveRef.isLocal);
/*     */     } 
/*     */     
/* 187 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean remoteEquals(Object paramObject) {
/* 192 */     if (paramObject != null && paramObject instanceof LiveRef) {
/* 193 */       LiveRef liveRef = (LiveRef)paramObject;
/*     */       
/* 195 */       TCPEndpoint tCPEndpoint1 = (TCPEndpoint)this.ep;
/* 196 */       TCPEndpoint tCPEndpoint2 = (TCPEndpoint)liveRef.ep;
/*     */ 
/*     */       
/* 199 */       RMIClientSocketFactory rMIClientSocketFactory1 = tCPEndpoint1.getClientSocketFactory();
/*     */       
/* 201 */       RMIClientSocketFactory rMIClientSocketFactory2 = tCPEndpoint2.getClientSocketFactory();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 211 */       if (tCPEndpoint1.getPort() != tCPEndpoint2.getPort() || 
/* 212 */         !tCPEndpoint1.getHost().equals(tCPEndpoint2.getHost()))
/*     */       {
/* 214 */         return false;
/*     */       }
/* 216 */       if ((((rMIClientSocketFactory1 == null) ? 1 : 0) ^ ((rMIClientSocketFactory2 == null) ? 1 : 0)) != 0) {
/* 217 */         return false;
/*     */       }
/* 219 */       if (rMIClientSocketFactory1 != null && (rMIClientSocketFactory1
/* 220 */         .getClass() != rMIClientSocketFactory2
/* 221 */         .getClass() || 
/* 222 */         !rMIClientSocketFactory1.equals(rMIClientSocketFactory2)))
/*     */       {
/* 224 */         return false;
/*     */       }
/* 226 */       return this.id.equals(liveRef.id);
/*     */     } 
/* 228 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void write(ObjectOutput paramObjectOutput, boolean paramBoolean) throws IOException {
/* 235 */     boolean bool = false;
/* 236 */     if (paramObjectOutput instanceof ConnectionOutputStream) {
/* 237 */       ConnectionOutputStream connectionOutputStream = (ConnectionOutputStream)paramObjectOutput;
/* 238 */       bool = connectionOutputStream.isResultStream();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 256 */       if (this.isLocal) {
/*     */         
/* 258 */         ObjectEndpoint objectEndpoint = new ObjectEndpoint(this.id, this.ep.getInboundTransport());
/* 259 */         Target target = ObjectTable.getTarget(objectEndpoint);
/*     */         
/* 261 */         if (target != null) {
/* 262 */           Remote remote = target.getImpl();
/* 263 */           if (remote != null) {
/* 264 */             connectionOutputStream.saveObject(remote);
/*     */           }
/*     */         } 
/*     */       } else {
/* 268 */         connectionOutputStream.saveObject(this);
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 274 */     if (paramBoolean) {
/* 275 */       ((TCPEndpoint)this.ep).write(paramObjectOutput);
/*     */     } else {
/* 277 */       ((TCPEndpoint)this.ep).writeHostPortFormat(paramObjectOutput);
/*     */     } 
/* 279 */     this.id.write(paramObjectOutput);
/* 280 */     paramObjectOutput.writeBoolean(bool);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static LiveRef read(ObjectInput paramObjectInput, boolean paramBoolean) throws IOException, ClassNotFoundException {
/*     */     TCPEndpoint tCPEndpoint;
/* 291 */     if (paramBoolean) {
/* 292 */       tCPEndpoint = TCPEndpoint.read(paramObjectInput);
/*     */     } else {
/* 294 */       tCPEndpoint = TCPEndpoint.readHostPortFormat(paramObjectInput);
/*     */     } 
/* 296 */     ObjID objID = ObjID.read(paramObjectInput);
/* 297 */     boolean bool = paramObjectInput.readBoolean();
/*     */     
/* 299 */     LiveRef liveRef = new LiveRef(objID, tCPEndpoint, false);
/*     */     
/* 301 */     if (paramObjectInput instanceof ConnectionInputStream) {
/* 302 */       ConnectionInputStream connectionInputStream = (ConnectionInputStream)paramObjectInput;
/*     */ 
/*     */       
/* 305 */       connectionInputStream.saveRef(liveRef);
/* 306 */       if (bool)
/*     */       {
/*     */         
/* 309 */         connectionInputStream.setAckNeeded();
/*     */       }
/*     */     } else {
/* 312 */       DGCClient.registerRefs(tCPEndpoint, Arrays.asList(new LiveRef[] { liveRef }));
/*     */     } 
/*     */     
/* 315 */     return liveRef;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/rmi/transport/LiveRef.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */