/*     */ package java.rmi.registry;
/*     */ 
/*     */ import java.net.InetAddress;
/*     */ import java.rmi.RemoteException;
/*     */ import java.rmi.server.ObjID;
/*     */ import java.rmi.server.RMIClientSocketFactory;
/*     */ import java.rmi.server.RMIServerSocketFactory;
/*     */ import sun.rmi.registry.RegistryImpl;
/*     */ import sun.rmi.server.UnicastRef;
/*     */ import sun.rmi.server.UnicastRef2;
/*     */ import sun.rmi.server.Util;
/*     */ import sun.rmi.transport.LiveRef;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class LocateRegistry
/*     */ {
/*     */   public static Registry getRegistry() throws RemoteException {
/*  75 */     return getRegistry(null, 1099);
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
/*     */   public static Registry getRegistry(int paramInt) throws RemoteException {
/*  90 */     return getRegistry(null, paramInt);
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
/*     */   public static Registry getRegistry(String paramString) throws RemoteException {
/* 106 */     return getRegistry(paramString, 1099);
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
/*     */   public static Registry getRegistry(String paramString, int paramInt) throws RemoteException {
/* 123 */     return getRegistry(paramString, paramInt, null);
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
/*     */   public static Registry getRegistry(String paramString, int paramInt, RMIClientSocketFactory paramRMIClientSocketFactory) throws RemoteException {
/* 148 */     Object object = null;
/*     */     
/* 150 */     if (paramInt <= 0) {
/* 151 */       paramInt = 1099;
/*     */     }
/* 153 */     if (paramString == null || paramString.length() == 0) {
/*     */       
/*     */       try {
/*     */ 
/*     */         
/* 158 */         paramString = InetAddress.getLocalHost().getHostAddress();
/* 159 */       } catch (Exception exception) {
/*     */         
/* 161 */         paramString = "";
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 175 */     LiveRef liveRef = new LiveRef(new ObjID(0), new TCPEndpoint(paramString, paramInt, paramRMIClientSocketFactory, null), false);
/*     */ 
/*     */ 
/*     */     
/* 179 */     UnicastRef unicastRef = (paramRMIClientSocketFactory == null) ? new UnicastRef(liveRef) : new UnicastRef2(liveRef);
/*     */ 
/*     */     
/* 182 */     return (Registry)Util.createProxy(RegistryImpl.class, unicastRef, false);
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
/*     */   public static Registry createRegistry(int paramInt) throws RemoteException {
/* 203 */     return new RegistryImpl(paramInt);
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
/*     */   public static Registry createRegistry(int paramInt, RMIClientSocketFactory paramRMIClientSocketFactory, RMIServerSocketFactory paramRMIServerSocketFactory) throws RemoteException {
/* 239 */     return new RegistryImpl(paramInt, paramRMIClientSocketFactory, paramRMIServerSocketFactory);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/rmi/registry/LocateRegistry.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */