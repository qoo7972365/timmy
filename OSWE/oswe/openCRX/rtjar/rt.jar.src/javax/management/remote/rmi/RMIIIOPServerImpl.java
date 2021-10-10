/*     */ package javax.management.remote.rmi;
/*     */ 
/*     */ import com.sun.jmx.remote.internal.IIOPHelper;
/*     */ import java.io.IOException;
/*     */ import java.rmi.Remote;
/*     */ import java.security.AccessControlContext;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedActionException;
/*     */ import java.security.PrivilegedExceptionAction;
/*     */ import java.util.Collections;
/*     */ import java.util.Map;
/*     */ import javax.security.auth.Subject;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class RMIIIOPServerImpl
/*     */   extends RMIServerImpl
/*     */ {
/*     */   private final Map<String, ?> env;
/*     */   private final AccessControlContext callerACC;
/*     */   
/*     */   public RMIIIOPServerImpl(Map<String, ?> paramMap) throws IOException {
/*  61 */     super(paramMap);
/*     */     
/*  63 */     this.env = (paramMap == null) ? Collections.emptyMap() : paramMap;
/*     */     
/*  65 */     this.callerACC = AccessController.getContext();
/*     */   }
/*     */   
/*     */   protected void export() throws IOException {
/*  69 */     IIOPHelper.exportObject(this);
/*     */   }
/*     */   
/*     */   protected String getProtocol() {
/*  73 */     return "iiop";
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
/*     */   public Remote toStub() throws IOException {
/*  87 */     return IIOPHelper.toStub(this);
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
/*     */   protected RMIConnection makeClient(String paramString, Subject paramSubject) throws IOException {
/* 115 */     if (paramString == null) {
/* 116 */       throw new NullPointerException("Null connectionId");
/*     */     }
/*     */     
/* 119 */     RMIConnectionImpl rMIConnectionImpl = new RMIConnectionImpl(this, paramString, getDefaultClassLoader(), paramSubject, this.env);
/*     */     
/* 121 */     IIOPHelper.exportObject(rMIConnectionImpl);
/* 122 */     return rMIConnectionImpl;
/*     */   }
/*     */   
/*     */   protected void closeClient(RMIConnection paramRMIConnection) throws IOException {
/* 126 */     IIOPHelper.unexportObject(paramRMIConnection);
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
/* 138 */     IIOPHelper.unexportObject(this);
/*     */   }
/*     */ 
/*     */   
/*     */   RMIConnection doNewClient(final Object credentials) throws IOException {
/* 143 */     if (this.callerACC == null) {
/* 144 */       throw new SecurityException("AccessControlContext cannot be null");
/*     */     }
/*     */     try {
/* 147 */       return AccessController.<RMIConnection>doPrivileged(new PrivilegedExceptionAction<RMIConnection>()
/*     */           {
/*     */             public RMIConnection run() throws IOException {
/* 150 */               return RMIIIOPServerImpl.this.superDoNewClient(credentials);
/*     */             }
/*     */           },  this.callerACC);
/* 153 */     } catch (PrivilegedActionException privilegedActionException) {
/* 154 */       throw (IOException)privilegedActionException.getCause();
/*     */     } 
/*     */   }
/*     */   
/*     */   RMIConnection superDoNewClient(Object paramObject) throws IOException {
/* 159 */     return super.doNewClient(paramObject);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/management/remote/rmi/RMIIIOPServerImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */