/*     */ package com.sun.corba.se.impl.javax.rmi;
/*     */ 
/*     */ import com.sun.corba.se.impl.util.RepositoryId;
/*     */ import com.sun.corba.se.impl.util.Utility;
/*     */ import com.sun.corba.se.spi.presentation.rmi.StubAdapter;
/*     */ import java.io.Externalizable;
/*     */ import java.io.Serializable;
/*     */ import java.rmi.NoSuchObjectException;
/*     */ import java.rmi.Remote;
/*     */ import java.rmi.RemoteException;
/*     */ import java.rmi.server.ExportException;
/*     */ import java.rmi.server.RemoteObject;
/*     */ import java.rmi.server.UnicastRemoteObject;
/*     */ import javax.rmi.CORBA.PortableRemoteObjectDelegate;
/*     */ import javax.rmi.CORBA.Tie;
/*     */ import javax.rmi.CORBA.Util;
/*     */ import org.omg.CORBA.ORB;
/*     */ import org.omg.CORBA.Object;
/*     */ import org.omg.CORBA.SystemException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PortableRemoteObject
/*     */   implements PortableRemoteObjectDelegate
/*     */ {
/*     */   public void exportObject(Remote paramRemote) throws RemoteException {
/*  91 */     if (paramRemote == null) {
/*  92 */       throw new NullPointerException("invalid argument");
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*  97 */     if (Util.getTie(paramRemote) != null)
/*     */     {
/*     */ 
/*     */       
/* 101 */       throw new ExportException(paramRemote.getClass().getName() + " already exported");
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 106 */     Tie tie = Utility.loadTie(paramRemote);
/*     */     
/* 108 */     if (tie != null) {
/*     */ 
/*     */ 
/*     */       
/* 112 */       Util.registerTarget(tie, paramRemote);
/*     */ 
/*     */     
/*     */     }
/*     */     else {
/*     */ 
/*     */       
/* 119 */       UnicastRemoteObject.exportObject(paramRemote);
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
/*     */   public Remote toStub(Remote paramRemote) throws NoSuchObjectException {
/* 134 */     Remote remote = null;
/* 135 */     if (paramRemote == null) {
/* 136 */       throw new NullPointerException("invalid argument");
/*     */     }
/*     */ 
/*     */     
/* 140 */     if (StubAdapter.isStub(paramRemote)) {
/* 141 */       return paramRemote;
/*     */     }
/*     */ 
/*     */     
/* 145 */     if (paramRemote instanceof java.rmi.server.RemoteStub) {
/* 146 */       return paramRemote;
/*     */     }
/*     */ 
/*     */     
/* 150 */     Tie tie = Util.getTie(paramRemote);
/*     */     
/* 152 */     if (tie != null) {
/* 153 */       remote = Utility.loadStub(tie, null, null, true);
/*     */     }
/* 155 */     else if (Utility.loadTie(paramRemote) == null) {
/* 156 */       remote = RemoteObject.toStub(paramRemote);
/*     */     } 
/*     */ 
/*     */     
/* 160 */     if (remote == null) {
/* 161 */       throw new NoSuchObjectException("object not exported");
/*     */     }
/*     */     
/* 164 */     return remote;
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
/*     */   public void unexportObject(Remote paramRemote) throws NoSuchObjectException {
/* 177 */     if (paramRemote == null) {
/* 178 */       throw new NullPointerException("invalid argument");
/*     */     }
/*     */     
/* 181 */     if (StubAdapter.isStub(paramRemote) || paramRemote instanceof java.rmi.server.RemoteStub)
/*     */     {
/* 183 */       throw new NoSuchObjectException("Can only unexport a server object.");
/*     */     }
/*     */ 
/*     */     
/* 187 */     Tie tie = Util.getTie(paramRemote);
/* 188 */     if (tie != null) {
/* 189 */       Util.unexportObject(paramRemote);
/*     */     }
/* 191 */     else if (Utility.loadTie(paramRemote) == null) {
/* 192 */       UnicastRemoteObject.unexportObject(paramRemote, true);
/*     */     } else {
/* 194 */       throw new NoSuchObjectException("Object not exported.");
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
/*     */   public Object narrow(Object paramObject, Class<Serializable> paramClass) throws ClassCastException {
/* 210 */     Object object = null;
/*     */     
/* 212 */     if (paramObject == null) {
/* 213 */       return null;
/*     */     }
/* 215 */     if (paramClass == null) {
/* 216 */       throw new NullPointerException("invalid argument");
/*     */     }
/*     */     try {
/* 219 */       if (paramClass.isAssignableFrom(paramObject.getClass())) {
/* 220 */         return paramObject;
/*     */       }
/*     */ 
/*     */       
/* 224 */       if (paramClass.isInterface() && paramClass != Serializable.class && paramClass != Externalizable.class) {
/*     */ 
/*     */ 
/*     */         
/* 228 */         Object object1 = (Object)paramObject;
/*     */ 
/*     */ 
/*     */         
/* 232 */         String str = RepositoryId.createForAnyType(paramClass);
/*     */         
/* 234 */         if (object1._is_a(str)) {
/* 235 */           return Utility.loadStub(object1, paramClass);
/*     */         }
/* 237 */         throw new ClassCastException("Object is not of remote type " + paramClass
/* 238 */             .getName());
/*     */       } 
/*     */       
/* 241 */       throw new ClassCastException("Class " + paramClass.getName() + " is not a valid remote interface");
/*     */     
/*     */     }
/* 244 */     catch (Exception exception) {
/* 245 */       ClassCastException classCastException = new ClassCastException();
/* 246 */       classCastException.initCause(exception);
/* 247 */       throw classCastException;
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
/*     */   public void connect(Remote paramRemote1, Remote paramRemote2) throws RemoteException {
/* 266 */     if (paramRemote1 == null || paramRemote2 == null) {
/* 267 */       throw new NullPointerException("invalid argument");
/*     */     }
/*     */     
/* 270 */     ORB oRB = null;
/*     */     try {
/* 272 */       if (StubAdapter.isStub(paramRemote2)) {
/* 273 */         oRB = StubAdapter.getORB(paramRemote2);
/*     */       } else {
/*     */         
/* 276 */         Tie tie1 = Util.getTie(paramRemote2);
/* 277 */         if (tie1 != null)
/*     */         {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 289 */           oRB = tie1.orb();
/*     */         }
/*     */       } 
/* 292 */     } catch (SystemException systemException) {
/* 293 */       throw new RemoteException("'source' object not connected", systemException);
/*     */     } 
/*     */     
/* 296 */     boolean bool = false;
/* 297 */     Tie tie = null;
/* 298 */     if (StubAdapter.isStub(paramRemote1)) {
/* 299 */       bool = true;
/*     */     } else {
/* 301 */       tie = Util.getTie(paramRemote1);
/* 302 */       if (tie != null) {
/* 303 */         bool = true;
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 313 */     if (!bool) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 318 */       if (oRB != null) {
/* 319 */         throw new RemoteException("'source' object exported to IIOP, 'target' is JRMP");
/*     */       
/*     */       }
/*     */     }
/*     */     else {
/*     */       
/* 325 */       if (oRB == null) {
/* 326 */         throw new RemoteException("'source' object is JRMP, 'target' is IIOP");
/*     */       }
/*     */ 
/*     */ 
/*     */       
/*     */       try {
/* 332 */         if (tie != null)
/*     */         
/*     */         { try {
/* 335 */             ORB oRB1 = tie.orb();
/*     */ 
/*     */             
/* 338 */             if (oRB1 == oRB) {
/*     */               return;
/*     */             }
/*     */ 
/*     */ 
/*     */             
/* 344 */             throw new RemoteException("'target' object was already connected");
/*     */           
/*     */           }
/* 347 */           catch (SystemException systemException) {
/*     */ 
/*     */             
/* 350 */             tie.orb(oRB);
/*     */           }  }
/* 352 */         else { StubAdapter.connect(paramRemote1, oRB); }
/*     */       
/* 354 */       } catch (SystemException systemException) {
/*     */ 
/*     */         
/* 357 */         throw new RemoteException("'target' object was already connected", systemException);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/javax/rmi/PortableRemoteObject.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */