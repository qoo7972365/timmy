/*     */ package com.sun.corba.se.impl.oa.poa;
/*     */ 
/*     */ import org.omg.PortableServer.ForwardRequest;
/*     */ import org.omg.PortableServer.POAPackage.NoServant;
/*     */ import org.omg.PortableServer.POAPackage.ObjectNotActive;
/*     */ import org.omg.PortableServer.POAPackage.WrongPolicy;
/*     */ import org.omg.PortableServer.Servant;
/*     */ import org.omg.PortableServer.ServantManager;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class POAPolicyMediatorImpl_R_UDS
/*     */   extends POAPolicyMediatorBase_R
/*     */ {
/*     */   private Servant defaultServant;
/*     */   
/*     */   POAPolicyMediatorImpl_R_UDS(Policies paramPolicies, POAImpl paramPOAImpl) {
/*  53 */     super(paramPolicies, paramPOAImpl);
/*  54 */     this.defaultServant = null;
/*     */ 
/*     */     
/*  57 */     if (!paramPolicies.useDefaultServant()) {
/*  58 */       throw paramPOAImpl.invocationWrapper().policyMediatorBadPolicyInFactory();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected Object internalGetServant(byte[] paramArrayOfbyte, String paramString) throws ForwardRequest {
/*  64 */     Servant servant = internalIdToServant(paramArrayOfbyte);
/*  65 */     if (servant == null) {
/*  66 */       servant = this.defaultServant;
/*     */     }
/*  68 */     if (servant == null) {
/*  69 */       throw this.poa.invocationWrapper().poaNoDefaultServant();
/*     */     }
/*  71 */     return servant;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void etherealizeAll() {}
/*     */ 
/*     */ 
/*     */   
/*     */   public ServantManager getServantManager() throws WrongPolicy {
/*  81 */     throw new WrongPolicy();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setServantManager(ServantManager paramServantManager) throws WrongPolicy {
/*  86 */     throw new WrongPolicy();
/*     */   }
/*     */ 
/*     */   
/*     */   public Servant getDefaultServant() throws NoServant, WrongPolicy {
/*  91 */     if (this.defaultServant == null) {
/*  92 */       throw new NoServant();
/*     */     }
/*  94 */     return this.defaultServant;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setDefaultServant(Servant paramServant) throws WrongPolicy {
/*  99 */     this.defaultServant = paramServant;
/* 100 */     setDelegate(this.defaultServant, "DefaultServant".getBytes());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Servant idToServant(byte[] paramArrayOfbyte) throws WrongPolicy, ObjectNotActive {
/* 106 */     ActiveObjectMap.Key key = new ActiveObjectMap.Key(paramArrayOfbyte);
/* 107 */     Servant servant = internalKeyToServant(key);
/*     */     
/* 109 */     if (servant == null && 
/* 110 */       this.defaultServant != null) {
/* 111 */       servant = this.defaultServant;
/*     */     }
/* 113 */     if (servant == null) {
/* 114 */       throw new ObjectNotActive();
/*     */     }
/* 116 */     return servant;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/oa/poa/POAPolicyMediatorImpl_R_UDS.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */