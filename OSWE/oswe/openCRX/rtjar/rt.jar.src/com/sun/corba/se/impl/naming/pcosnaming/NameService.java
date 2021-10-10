/*     */ package com.sun.corba.se.impl.naming.pcosnaming;
/*     */ 
/*     */ import com.sun.corba.se.spi.orb.ORB;
/*     */ import java.io.File;
/*     */ import org.omg.CORBA.Object;
/*     */ import org.omg.CORBA.Policy;
/*     */ import org.omg.CORBA.SystemException;
/*     */ import org.omg.CosNaming.NamingContext;
/*     */ import org.omg.CosNaming.NamingContextHelper;
/*     */ import org.omg.PortableServer.IdAssignmentPolicyValue;
/*     */ import org.omg.PortableServer.LifespanPolicyValue;
/*     */ import org.omg.PortableServer.POA;
/*     */ import org.omg.PortableServer.POAPackage.WrongAdapter;
/*     */ import org.omg.PortableServer.POAPackage.WrongPolicy;
/*     */ import org.omg.PortableServer.RequestProcessingPolicyValue;
/*     */ import org.omg.PortableServer.ServantManager;
/*     */ import org.omg.PortableServer.ServantRetentionPolicyValue;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class NameService
/*     */ {
/*  52 */   private NamingContext rootContext = null;
/*  53 */   private POA nsPOA = null;
/*     */ 
/*     */ 
/*     */   
/*     */   private ServantManagerImpl contextMgr;
/*     */ 
/*     */ 
/*     */   
/*     */   private ORB theorb;
/*     */ 
/*     */ 
/*     */   
/*     */   public NameService(ORB paramORB, File paramFile) throws Exception {
/*  66 */     this.theorb = paramORB;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  78 */     POA pOA = (POA)paramORB.resolve_initial_references("RootPOA");
/*     */     
/*  80 */     pOA.the_POAManager().activate();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  87 */     byte b = 0;
/*  88 */     Policy[] arrayOfPolicy = new Policy[4];
/*  89 */     arrayOfPolicy[b++] = (Policy)pOA.create_lifespan_policy(LifespanPolicyValue.PERSISTENT);
/*     */     
/*  91 */     arrayOfPolicy[b++] = (Policy)pOA.create_request_processing_policy(RequestProcessingPolicyValue.USE_SERVANT_MANAGER);
/*     */     
/*  93 */     arrayOfPolicy[b++] = (Policy)pOA.create_id_assignment_policy(IdAssignmentPolicyValue.USER_ID);
/*     */     
/*  95 */     arrayOfPolicy[b++] = (Policy)pOA.create_servant_retention_policy(ServantRetentionPolicyValue.NON_RETAIN);
/*     */ 
/*     */ 
/*     */     
/*  99 */     this.nsPOA = pOA.create_POA("NameService", null, arrayOfPolicy);
/* 100 */     this.nsPOA.the_POAManager().activate();
/*     */ 
/*     */     
/* 103 */     this.contextMgr = new ServantManagerImpl(paramORB, paramFile, this);
/*     */ 
/*     */ 
/*     */     
/* 107 */     String str = ServantManagerImpl.getRootObjectKey();
/*     */     
/* 109 */     NamingContextImpl namingContextImpl = new NamingContextImpl(paramORB, str, this, this.contextMgr);
/*     */     
/* 111 */     namingContextImpl = this.contextMgr.addContext(str, namingContextImpl);
/* 112 */     namingContextImpl.setServantManagerImpl(this.contextMgr);
/* 113 */     namingContextImpl.setORB(paramORB);
/* 114 */     namingContextImpl.setRootNameService(this);
/*     */     
/* 116 */     this.nsPOA.set_servant_manager((ServantManager)this.contextMgr);
/* 117 */     this.rootContext = NamingContextHelper.narrow(this.nsPOA
/* 118 */         .create_reference_with_id(str.getBytes(), 
/* 119 */           NamingContextHelper.id()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NamingContext initialNamingContext() {
/* 127 */     return this.rootContext;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   POA getNSPOA() {
/* 135 */     return this.nsPOA;
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
/*     */   public NamingContext NewContext() throws SystemException {
/*     */     try {
/* 152 */       String str = this.contextMgr.getNewObjectKey();
/*     */ 
/*     */       
/* 155 */       NamingContextImpl namingContextImpl1 = new NamingContextImpl(this.theorb, str, this, this.contextMgr);
/*     */ 
/*     */       
/* 158 */       NamingContextImpl namingContextImpl2 = this.contextMgr.addContext(str, namingContextImpl1);
/*     */       
/* 160 */       if (namingContextImpl2 != null)
/*     */       {
/* 162 */         namingContextImpl1 = namingContextImpl2;
/*     */       }
/*     */ 
/*     */       
/* 166 */       namingContextImpl1.setServantManagerImpl(this.contextMgr);
/* 167 */       namingContextImpl1.setORB(this.theorb);
/* 168 */       namingContextImpl1.setRootNameService(this);
/*     */       
/* 170 */       return NamingContextHelper.narrow(this.nsPOA
/* 171 */           .create_reference_with_id(str.getBytes(), 
/* 172 */             NamingContextHelper.id()));
/*     */     
/*     */     }
/* 175 */     catch (SystemException systemException) {
/*     */       
/* 177 */       throw systemException;
/*     */     }
/* 179 */     catch (Exception exception) {
/*     */ 
/*     */ 
/*     */       
/* 183 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   Object getObjectReferenceFromKey(String paramString) {
/* 193 */     Object object = null;
/*     */     
/*     */     try {
/* 196 */       object = this.nsPOA.create_reference_with_id(paramString.getBytes(), NamingContextHelper.id());
/*     */     }
/* 198 */     catch (Exception exception) {
/*     */       
/* 200 */       object = null;
/*     */     } 
/* 202 */     return object;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   String getObjectKey(Object paramObject) {
/*     */     byte[] arrayOfByte;
/*     */     try {
/* 215 */       arrayOfByte = this.nsPOA.reference_to_id(paramObject);
/*     */     }
/* 217 */     catch (WrongAdapter wrongAdapter) {
/*     */       
/* 219 */       return null;
/*     */     }
/* 221 */     catch (WrongPolicy wrongPolicy) {
/*     */       
/* 223 */       return null;
/*     */     }
/* 225 */     catch (Exception exception) {
/*     */       
/* 227 */       return null;
/*     */     } 
/* 229 */     return new String(arrayOfByte);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/naming/pcosnaming/NameService.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */