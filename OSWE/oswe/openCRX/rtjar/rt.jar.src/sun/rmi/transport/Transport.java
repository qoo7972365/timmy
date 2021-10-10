/*     */ package sun.rmi.transport;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectOutput;
/*     */ import java.rmi.MarshalException;
/*     */ import java.rmi.NoSuchObjectException;
/*     */ import java.rmi.Remote;
/*     */ import java.rmi.RemoteException;
/*     */ import java.rmi.server.LogStream;
/*     */ import java.rmi.server.ObjID;
/*     */ import java.rmi.server.RemoteCall;
/*     */ import java.rmi.server.RemoteServer;
/*     */ import java.rmi.server.ServerNotActiveException;
/*     */ import java.security.AccessControlContext;
/*     */ import java.security.AccessController;
/*     */ import java.security.Permissions;
/*     */ import java.security.PrivilegedActionException;
/*     */ import java.security.PrivilegedExceptionAction;
/*     */ import java.security.ProtectionDomain;
/*     */ import sun.rmi.runtime.Log;
/*     */ import sun.rmi.server.Dispatcher;
/*     */ import sun.rmi.server.UnicastServerRef;
/*     */ import sun.security.action.GetPropertyAction;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class Transport
/*     */ {
/*  58 */   static final int logLevel = LogStream.parseLevel(getLogLevel());
/*     */   
/*     */   private static String getLogLevel() {
/*  61 */     return AccessController.<String>doPrivileged(new GetPropertyAction("sun.rmi.transport.logLevel"));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  67 */   static final Log transportLog = Log.getLog("sun.rmi.transport.misc", "transport", logLevel);
/*     */ 
/*     */   
/*  70 */   private static final ThreadLocal<Transport> currentTransport = new ThreadLocal<>();
/*     */ 
/*     */   
/*  73 */   private static final ObjID dgcID = new ObjID(2);
/*     */   
/*     */   private static final AccessControlContext SETCCL_ACC;
/*     */   
/*     */   static {
/*  78 */     Permissions permissions = new Permissions();
/*  79 */     permissions.add(new RuntimePermission("setContextClassLoader"));
/*  80 */     ProtectionDomain[] arrayOfProtectionDomain = { new ProtectionDomain(null, permissions) };
/*  81 */     SETCCL_ACC = new AccessControlContext(arrayOfProtectionDomain);
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
/*     */   public void exportObject(Target paramTarget) throws RemoteException {
/* 105 */     paramTarget.setExportedTransport(this);
/* 106 */     ObjectTable.putTarget(paramTarget);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void targetUnexported() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static Transport currentTransport() {
/* 121 */     return currentTransport.get();
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
/*     */   private static void setContextClassLoader(ClassLoader paramClassLoader) {
/* 136 */     AccessController.doPrivileged(() -> { Thread.currentThread().setContextClassLoader(paramClassLoader); return null; }SETCCL_ACC);
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
/*     */   public boolean serviceCall(final RemoteCall call) {
/*     */     try {
/*     */       ObjID objID;
/*     */       try {
/* 166 */         objID = ObjID.read(call.getInputStream());
/* 167 */       } catch (IOException iOException) {
/* 168 */         throw new MarshalException("unable to read objID", iOException);
/*     */       } 
/*     */ 
/*     */       
/* 172 */       Transport transport = objID.equals(dgcID) ? null : this;
/*     */       
/* 174 */       Target target = ObjectTable.getTarget(new ObjectEndpoint(objID, transport));
/*     */       final Remote impl;
/* 176 */       if (target == null || (remote = target.getImpl()) == null) {
/* 177 */         throw new NoSuchObjectException("no such object in table");
/*     */       }
/*     */       
/* 180 */       final Dispatcher disp = target.getDispatcher();
/* 181 */       target.incrementCallCount();
/*     */       
/*     */       try {
/* 184 */         transportLog.log(Log.VERBOSE, "call dispatcher");
/*     */ 
/*     */         
/* 187 */         final AccessControlContext acc = target.getAccessControlContext();
/* 188 */         ClassLoader classLoader1 = target.getContextClassLoader();
/*     */         
/* 190 */         ClassLoader classLoader2 = Thread.currentThread().getContextClassLoader();
/*     */         
/*     */         try {
/* 193 */           setContextClassLoader(classLoader1);
/* 194 */           currentTransport.set(this);
/*     */           try {
/* 196 */             AccessController.doPrivileged(new PrivilegedExceptionAction<Void>()
/*     */                 {
/*     */                   public Void run() throws IOException {
/* 199 */                     Transport.this.checkAcceptPermission(acc);
/* 200 */                     disp.dispatch(impl, call);
/* 201 */                     return null;
/*     */                   }
/*     */                 }accessControlContext);
/* 204 */           } catch (PrivilegedActionException privilegedActionException) {
/* 205 */             throw (IOException)privilegedActionException.getException();
/*     */           } 
/*     */         } finally {
/* 208 */           setContextClassLoader(classLoader2);
/* 209 */           currentTransport.set(null);
/*     */         }
/*     */       
/* 212 */       } catch (IOException iOException) {
/* 213 */         transportLog.log(Log.BRIEF, "exception thrown by dispatcher: ", iOException);
/*     */         
/* 215 */         return false;
/*     */       } finally {
/* 217 */         target.decrementCallCount();
/*     */       }
/*     */     
/* 220 */     } catch (RemoteException remoteException) {
/*     */ 
/*     */       
/* 223 */       if (UnicastServerRef.callLog.isLoggable(Log.BRIEF)) {
/*     */         
/* 225 */         String str1 = "";
/*     */         
/*     */         try {
/* 228 */           str1 = "[" + RemoteServer.getClientHost() + "] ";
/* 229 */         } catch (ServerNotActiveException serverNotActiveException) {}
/*     */         
/* 231 */         String str2 = str1 + "exception: ";
/* 232 */         UnicastServerRef.callLog.log(Log.BRIEF, str2, remoteException);
/*     */       } 
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
/* 244 */         ObjectOutput objectOutput = call.getResultStream(false);
/* 245 */         UnicastServerRef.clearStackTraces(remoteException);
/* 246 */         objectOutput.writeObject(remoteException);
/* 247 */         call.releaseOutputStream();
/*     */       }
/* 249 */       catch (IOException iOException) {
/* 250 */         transportLog.log(Log.BRIEF, "exception thrown marshalling exception: ", iOException);
/*     */         
/* 252 */         return false;
/*     */       } 
/*     */     } 
/*     */     
/* 256 */     return true;
/*     */   }
/*     */   
/*     */   public abstract Channel getChannel(Endpoint paramEndpoint);
/*     */   
/*     */   public abstract void free(Endpoint paramEndpoint);
/*     */   
/*     */   protected abstract void checkAcceptPermission(AccessControlContext paramAccessControlContext);
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/rmi/transport/Transport.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */