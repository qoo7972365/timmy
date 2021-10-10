/*     */ package com.sun.corba.se.impl.oa.poa;
/*     */ 
/*     */ import com.sun.corba.se.impl.oa.NullServantImpl;
/*     */ import com.sun.corba.se.spi.oa.OAInvocationInfo;
/*     */ import org.omg.CORBA.SystemException;
/*     */ import org.omg.PortableServer.ForwardRequest;
/*     */ import org.omg.PortableServer.POA;
/*     */ import org.omg.PortableServer.POAPackage.NoServant;
/*     */ import org.omg.PortableServer.POAPackage.ObjectAlreadyActive;
/*     */ import org.omg.PortableServer.POAPackage.ObjectNotActive;
/*     */ import org.omg.PortableServer.POAPackage.ServantAlreadyActive;
/*     */ import org.omg.PortableServer.POAPackage.ServantNotActive;
/*     */ import org.omg.PortableServer.POAPackage.WrongPolicy;
/*     */ import org.omg.PortableServer.Servant;
/*     */ import org.omg.PortableServer.ServantLocator;
/*     */ import org.omg.PortableServer.ServantLocatorPackage.CookieHolder;
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
/*     */ public class POAPolicyMediatorImpl_NR_USM
/*     */   extends POAPolicyMediatorBase
/*     */ {
/*     */   private ServantLocator locator;
/*     */   
/*     */   POAPolicyMediatorImpl_NR_USM(Policies paramPolicies, POAImpl paramPOAImpl) {
/*  58 */     super(paramPolicies, paramPOAImpl);
/*     */ 
/*     */     
/*  61 */     if (paramPolicies.retainServants()) {
/*  62 */       throw paramPOAImpl.invocationWrapper().policyMediatorBadPolicyInFactory();
/*     */     }
/*  64 */     if (!paramPolicies.useServantManager()) {
/*  65 */       throw paramPOAImpl.invocationWrapper().policyMediatorBadPolicyInFactory();
/*     */     }
/*  67 */     this.locator = null;
/*     */   }
/*     */ 
/*     */   
/*     */   protected Object internalGetServant(byte[] paramArrayOfbyte, String paramString) throws ForwardRequest {
/*     */     NullServantImpl nullServantImpl;
/*  73 */     if (this.locator == null) {
/*  74 */       throw this.poa.invocationWrapper().poaNoServantManager();
/*     */     }
/*  76 */     CookieHolder cookieHolder = this.orb.peekInvocationInfo().getCookieHolder();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  81 */     try { this.poa.unlock();
/*     */       
/*  83 */       Servant servant = this.locator.preinvoke(paramArrayOfbyte, this.poa, paramString, cookieHolder);
/*  84 */       if (servant == null) {
/*  85 */         nullServantImpl = new NullServantImpl((SystemException)this.poa.omgInvocationWrapper().nullServantReturned());
/*     */       } else {
/*  87 */         setDelegate((Servant)nullServantImpl, paramArrayOfbyte);
/*     */       }  }
/*  89 */     finally { this.poa.lock(); }
/*     */ 
/*     */     
/*  92 */     return nullServantImpl;
/*     */   }
/*     */ 
/*     */   
/*     */   public void returnServant() {
/*  97 */     OAInvocationInfo oAInvocationInfo = this.orb.peekInvocationInfo();
/*  98 */     if (this.locator == null) {
/*     */       return;
/*     */     }
/*     */     try {
/* 102 */       this.poa.unlock();
/* 103 */       this.locator.postinvoke(oAInvocationInfo.id(), (POA)oAInvocationInfo.oa(), oAInvocationInfo
/* 104 */           .getOperation(), (oAInvocationInfo.getCookieHolder()).value, (Servant)oAInvocationInfo
/* 105 */           .getServantContainer());
/*     */     } finally {
/* 107 */       this.poa.lock();
/*     */     } 
/*     */   }
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
/* 123 */     return (ServantManager)this.locator;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setServantManager(ServantManager paramServantManager) throws WrongPolicy {
/* 128 */     if (this.locator != null) {
/* 129 */       throw this.poa.invocationWrapper().servantManagerAlreadySet();
/*     */     }
/* 131 */     if (paramServantManager instanceof ServantLocator) {
/* 132 */       this.locator = (ServantLocator)paramServantManager;
/*     */     } else {
/* 134 */       throw this.poa.invocationWrapper().servantManagerBadType();
/*     */     } 
/*     */   }
/*     */   
/*     */   public Servant getDefaultServant() throws NoServant, WrongPolicy {
/* 139 */     throw new WrongPolicy();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setDefaultServant(Servant paramServant) throws WrongPolicy {
/* 144 */     throw new WrongPolicy();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public final void activateObject(byte[] paramArrayOfbyte, Servant paramServant) throws WrongPolicy, ServantAlreadyActive, ObjectAlreadyActive {
/* 150 */     throw new WrongPolicy();
/*     */   }
/*     */ 
/*     */   
/*     */   public Servant deactivateObject(byte[] paramArrayOfbyte) throws ObjectNotActive, WrongPolicy {
/* 155 */     throw new WrongPolicy();
/*     */   }
/*     */ 
/*     */   
/*     */   public byte[] servantToId(Servant paramServant) throws ServantNotActive, WrongPolicy {
/* 160 */     throw new WrongPolicy();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Servant idToServant(byte[] paramArrayOfbyte) throws WrongPolicy, ObjectNotActive {
/* 166 */     throw new WrongPolicy();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/oa/poa/POAPolicyMediatorImpl_NR_USM.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */