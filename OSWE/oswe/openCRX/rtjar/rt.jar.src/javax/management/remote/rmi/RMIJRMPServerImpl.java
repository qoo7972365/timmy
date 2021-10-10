/*     */ package javax.management.remote.rmi;
/*     */ 
/*     */ import com.sun.jmx.remote.internal.RMIExporter;
/*     */ import com.sun.jmx.remote.util.EnvHelp;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectStreamClass;
/*     */ import java.lang.reflect.Method;
/*     */ import java.rmi.NoSuchObjectException;
/*     */ import java.rmi.Remote;
/*     */ import java.rmi.RemoteException;
/*     */ import java.rmi.server.RMIClientSocketFactory;
/*     */ import java.rmi.server.RMIServerSocketFactory;
/*     */ import java.rmi.server.RemoteObject;
/*     */ import java.rmi.server.UnicastRemoteObject;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import javax.security.auth.Subject;
/*     */ import sun.reflect.misc.ReflectUtil;
/*     */ import sun.rmi.server.DeserializationChecker;
/*     */ import sun.rmi.server.UnicastServerRef;
/*     */ import sun.rmi.server.UnicastServerRef2;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class RMIJRMPServerImpl
/*     */   extends RMIServerImpl
/*     */ {
/*     */   private final ExportedWrapper exportedWrapper;
/*     */   private final int port;
/*     */   private final RMIClientSocketFactory csf;
/*     */   private final RMIServerSocketFactory ssf;
/*     */   private final Map<String, ?> env;
/*     */   
/*     */   public RMIJRMPServerImpl(int paramInt, RMIClientSocketFactory paramRMIClientSocketFactory, RMIServerSocketFactory paramRMIServerSocketFactory, Map<String, ?> paramMap) throws IOException {
/*  92 */     super(paramMap);
/*     */     
/*  94 */     if (paramInt < 0) {
/*  95 */       throw new IllegalArgumentException("Negative port: " + paramInt);
/*     */     }
/*  97 */     this.port = paramInt;
/*  98 */     this.csf = paramRMIClientSocketFactory;
/*  99 */     this.ssf = paramRMIServerSocketFactory;
/* 100 */     this.env = (paramMap == null) ? Collections.emptyMap() : paramMap;
/*     */ 
/*     */     
/* 103 */     String[] arrayOfString = (String[])this.env.get("jmx.remote.rmi.server.credential.types");
/* 104 */     ArrayList<String> arrayList = null;
/* 105 */     if (arrayOfString != null) {
/* 106 */       arrayList = new ArrayList();
/* 107 */       for (String str : arrayOfString) {
/* 108 */         if (str == null) {
/* 109 */           throw new IllegalArgumentException("A credential type is null.");
/*     */         }
/* 111 */         ReflectUtil.checkPackageAccess(str);
/* 112 */         arrayList.add(str);
/*     */       } 
/*     */     } 
/* 115 */     this.exportedWrapper = (arrayList != null) ? new ExportedWrapper(this, arrayList) : null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void export() throws IOException {
/* 121 */     if (this.exportedWrapper != null) {
/* 122 */       export(this.exportedWrapper);
/*     */     } else {
/* 124 */       export(this);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void export(Remote paramRemote) throws RemoteException {
/* 130 */     RMIExporter rMIExporter = (RMIExporter)this.env.get("com.sun.jmx.remote.rmi.exporter");
/* 131 */     boolean bool = EnvHelp.isServerDaemon(this.env);
/*     */     
/* 133 */     if (bool && rMIExporter != null) {
/* 134 */       throw new IllegalArgumentException("If jmx.remote.x.daemon is specified as true, com.sun.jmx.remote.rmi.exporter cannot be used to specify an exporter!");
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 139 */     if (bool) {
/* 140 */       if (this.csf == null && this.ssf == null) {
/* 141 */         (new UnicastServerRef(this.port)).exportObject(paramRemote, null, true);
/*     */       } else {
/* 143 */         (new UnicastServerRef2(this.port, this.csf, this.ssf)).exportObject(paramRemote, null, true);
/*     */       } 
/* 145 */     } else if (rMIExporter != null) {
/* 146 */       rMIExporter.exportObject(paramRemote, this.port, this.csf, this.ssf);
/*     */     } else {
/* 148 */       UnicastRemoteObject.exportObject(paramRemote, this.port, this.csf, this.ssf);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void unexport(Remote paramRemote, boolean paramBoolean) throws NoSuchObjectException {
/* 155 */     RMIExporter rMIExporter = (RMIExporter)this.env.get("com.sun.jmx.remote.rmi.exporter");
/* 156 */     if (rMIExporter == null) {
/* 157 */       UnicastRemoteObject.unexportObject(paramRemote, paramBoolean);
/*     */     } else {
/* 159 */       rMIExporter.unexportObject(paramRemote, paramBoolean);
/*     */     } 
/*     */   }
/*     */   protected String getProtocol() {
/* 163 */     return "rmi";
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
/*     */   public Remote toStub() throws IOException {
/* 175 */     if (this.exportedWrapper != null) {
/* 176 */       return RemoteObject.toStub(this.exportedWrapper);
/*     */     }
/* 178 */     return RemoteObject.toStub(this);
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
/*     */   protected RMIConnection makeClient(String paramString, Subject paramSubject) throws IOException {
/* 203 */     if (paramString == null) {
/* 204 */       throw new NullPointerException("Null connectionId");
/*     */     }
/*     */     
/* 207 */     RMIConnectionImpl rMIConnectionImpl = new RMIConnectionImpl(this, paramString, getDefaultClassLoader(), paramSubject, this.env);
/*     */     
/* 209 */     export(rMIConnectionImpl);
/* 210 */     return rMIConnectionImpl;
/*     */   }
/*     */   
/*     */   protected void closeClient(RMIConnection paramRMIConnection) throws IOException {
/* 214 */     unexport(paramRMIConnection, true);
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
/*     */   protected void closeServer() throws IOException {
/* 226 */     if (this.exportedWrapper != null) {
/* 227 */       unexport(this.exportedWrapper, true);
/*     */     } else {
/* 229 */       unexport(this, true);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static class ExportedWrapper
/*     */     implements RMIServer, DeserializationChecker
/*     */   {
/*     */     private final RMIServer impl;
/*     */     
/*     */     private final List<String> allowedTypes;
/*     */     
/*     */     private ExportedWrapper(RMIServer param1RMIServer, List<String> param1List) {
/* 242 */       this.impl = param1RMIServer;
/* 243 */       this.allowedTypes = param1List;
/*     */     }
/*     */ 
/*     */     
/*     */     public String getVersion() throws RemoteException {
/* 248 */       return this.impl.getVersion();
/*     */     }
/*     */ 
/*     */     
/*     */     public RMIConnection newClient(Object param1Object) throws IOException {
/* 253 */       return this.impl.newClient(param1Object);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void check(Method param1Method, ObjectStreamClass param1ObjectStreamClass, int param1Int1, int param1Int2) {
/* 260 */       String str = param1ObjectStreamClass.getName();
/* 261 */       if (!this.allowedTypes.contains(str)) {
/* 262 */         throw new ClassCastException("Unsupported type: " + str);
/*     */       }
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void checkProxyClass(Method param1Method, String[] param1ArrayOfString, int param1Int1, int param1Int2) {
/* 269 */       if (param1ArrayOfString != null && param1ArrayOfString.length > 0)
/* 270 */         for (String str : param1ArrayOfString) {
/* 271 */           if (!this.allowedTypes.contains(str))
/* 272 */             throw new ClassCastException("Unsupported type: " + str); 
/*     */         }  
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/management/remote/rmi/RMIJRMPServerImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */