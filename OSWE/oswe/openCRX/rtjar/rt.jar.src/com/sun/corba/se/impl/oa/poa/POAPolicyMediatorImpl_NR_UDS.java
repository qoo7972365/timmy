/*     */ package com.sun.corba.se.impl.oa.poa;
/*     */ 
/*     */ import org.omg.PortableServer.ForwardRequest;
/*     */ import org.omg.PortableServer.POAPackage.NoServant;
/*     */ import org.omg.PortableServer.POAPackage.ObjectAlreadyActive;
/*     */ import org.omg.PortableServer.POAPackage.ObjectNotActive;
/*     */ import org.omg.PortableServer.POAPackage.ServantAlreadyActive;
/*     */ import org.omg.PortableServer.POAPackage.ServantNotActive;
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
/*     */ public class POAPolicyMediatorImpl_NR_UDS
/*     */   extends POAPolicyMediatorBase
/*     */ {
/*     */   private Servant defaultServant;
/*     */   
/*     */   POAPolicyMediatorImpl_NR_UDS(Policies paramPolicies, POAImpl paramPOAImpl) {
/*  52 */     super(paramPolicies, paramPOAImpl);
/*     */ 
/*     */     
/*  55 */     if (paramPolicies.retainServants()) {
/*  56 */       throw paramPOAImpl.invocationWrapper().policyMediatorBadPolicyInFactory();
/*     */     }
/*  58 */     if (!paramPolicies.useDefaultServant()) {
/*  59 */       throw paramPOAImpl.invocationWrapper().policyMediatorBadPolicyInFactory();
/*     */     }
/*  61 */     this.defaultServant = null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected Object internalGetServant(byte[] paramArrayOfbyte, String paramString) throws ForwardRequest {
/*  67 */     if (this.defaultServant == null) {
/*  68 */       throw this.poa.invocationWrapper().poaNoDefaultServant();
/*     */     }
/*  70 */     return this.defaultServant;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void returnServant() {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void etherealizeAll() {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void clearAOM() {}
/*     */ 
/*     */ 
/*     */   
/*     */   public ServantManager getServantManager() throws WrongPolicy {
/*  90 */     throw new WrongPolicy();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setServantManager(ServantManager paramServantManager) throws WrongPolicy {
/*  95 */     throw new WrongPolicy();
/*     */   }
/*     */ 
/*     */   
/*     */   public Servant getDefaultServant() throws NoServant, WrongPolicy {
/* 100 */     if (this.defaultServant == null)
/* 101 */       throw new NoServant(); 
/* 102 */     return this.defaultServant;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setDefaultServant(Servant paramServant) throws WrongPolicy {
/* 107 */     this.defaultServant = paramServant;
/* 108 */     setDelegate(this.defaultServant, "DefaultServant".getBytes());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public final void activateObject(byte[] paramArrayOfbyte, Servant paramServant) throws WrongPolicy, ServantAlreadyActive, ObjectAlreadyActive {
/* 114 */     throw new WrongPolicy();
/*     */   }
/*     */ 
/*     */   
/*     */   public Servant deactivateObject(byte[] paramArrayOfbyte) throws ObjectNotActive, WrongPolicy {
/* 119 */     throw new WrongPolicy();
/*     */   }
/*     */ 
/*     */   
/*     */   public byte[] servantToId(Servant paramServant) throws ServantNotActive, WrongPolicy {
/* 124 */     throw new WrongPolicy();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Servant idToServant(byte[] paramArrayOfbyte) throws WrongPolicy, ObjectNotActive {
/* 130 */     if (this.defaultServant != null) {
/* 131 */       return this.defaultServant;
/*     */     }
/* 133 */     throw new ObjectNotActive();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/oa/poa/POAPolicyMediatorImpl_NR_UDS.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */