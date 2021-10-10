/*     */ package com.sun.jmx.remote.protocol.iiop;
/*     */ 
/*     */ import com.sun.jmx.remote.internal.IIOPProxy;
/*     */ import java.io.SerializablePermission;
/*     */ import java.rmi.NoSuchObjectException;
/*     */ import java.rmi.Remote;
/*     */ import java.rmi.RemoteException;
/*     */ import java.security.AccessControlContext;
/*     */ import java.security.AccessController;
/*     */ import java.security.Permissions;
/*     */ import java.security.PrivilegedActionException;
/*     */ import java.security.PrivilegedExceptionAction;
/*     */ import java.security.ProtectionDomain;
/*     */ import java.util.Properties;
/*     */ import javax.rmi.CORBA.Stub;
/*     */ import javax.rmi.PortableRemoteObject;
/*     */ import org.omg.CORBA.BAD_OPERATION;
/*     */ import org.omg.CORBA.ORB;
/*     */ import org.omg.CORBA.Object;
/*     */ import org.omg.CORBA.portable.Delegate;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class IIOPProxyImpl
/*     */   implements IIOPProxy
/*     */ {
/*     */   private static final AccessControlContext STUB_ACC;
/*     */   
/*     */   static {
/*  58 */     Permissions permissions = new Permissions();
/*  59 */     permissions.add(new SerializablePermission("enableSubclassImplementation"));
/*  60 */     STUB_ACC = new AccessControlContext(new ProtectionDomain[] { new ProtectionDomain(null, permissions) });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isStub(Object paramObject) {
/*  71 */     return paramObject instanceof Stub;
/*     */   }
/*     */ 
/*     */   
/*     */   public Object getDelegate(Object paramObject) {
/*  76 */     return ((Stub)paramObject)._get_delegate();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setDelegate(Object paramObject1, Object paramObject2) {
/*  81 */     ((Stub)paramObject1)._set_delegate((Delegate)paramObject2);
/*     */   }
/*     */ 
/*     */   
/*     */   public Object getOrb(Object paramObject) {
/*     */     try {
/*  87 */       return ((Stub)paramObject)._orb();
/*  88 */     } catch (BAD_OPERATION bAD_OPERATION) {
/*  89 */       throw new UnsupportedOperationException(bAD_OPERATION);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void connect(Object paramObject1, Object paramObject2) throws RemoteException {
/*  97 */     ((Stub)paramObject1).connect((ORB)paramObject2);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isOrb(Object paramObject) {
/* 102 */     return paramObject instanceof ORB;
/*     */   }
/*     */ 
/*     */   
/*     */   public Object createOrb(String[] paramArrayOfString, Properties paramProperties) {
/* 107 */     return ORB.init(paramArrayOfString, paramProperties);
/*     */   }
/*     */ 
/*     */   
/*     */   public Object stringToObject(Object paramObject, String paramString) {
/* 112 */     return ((ORB)paramObject).string_to_object(paramString);
/*     */   }
/*     */ 
/*     */   
/*     */   public String objectToString(Object paramObject1, Object paramObject2) {
/* 117 */     return ((ORB)paramObject1).object_to_string((Object)paramObject2);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public <T> T narrow(Object paramObject, Class<T> paramClass) {
/* 123 */     return (T)PortableRemoteObject.narrow(paramObject, paramClass);
/*     */   }
/*     */ 
/*     */   
/*     */   public void exportObject(Remote paramRemote) throws RemoteException {
/* 128 */     PortableRemoteObject.exportObject(paramRemote);
/*     */   }
/*     */ 
/*     */   
/*     */   public void unexportObject(Remote paramRemote) throws NoSuchObjectException {
/* 133 */     PortableRemoteObject.unexportObject(paramRemote);
/*     */   }
/*     */ 
/*     */   
/*     */   public Remote toStub(final Remote obj) throws NoSuchObjectException {
/* 138 */     if (System.getSecurityManager() == null) {
/* 139 */       return PortableRemoteObject.toStub(obj);
/*     */     }
/*     */     try {
/* 142 */       return AccessController.<Remote>doPrivileged(new PrivilegedExceptionAction<Remote>()
/*     */           {
/*     */             public Remote run() throws Exception
/*     */             {
/* 146 */               return PortableRemoteObject.toStub(obj);
/*     */             }
/*     */           },  STUB_ACC);
/* 149 */     } catch (PrivilegedActionException privilegedActionException) {
/* 150 */       if (privilegedActionException.getException() instanceof NoSuchObjectException) {
/* 151 */         throw (NoSuchObjectException)privilegedActionException.getException();
/*     */       }
/* 153 */       throw new RuntimeException("Unexpected exception type", privilegedActionException.getException());
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/jmx/remote/protocol/iiop/IIOPProxyImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */