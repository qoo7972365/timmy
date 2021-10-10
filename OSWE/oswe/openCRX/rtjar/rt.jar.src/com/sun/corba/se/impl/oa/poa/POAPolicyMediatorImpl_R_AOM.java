/*     */ package com.sun.corba.se.impl.oa.poa;
/*     */ 
/*     */ import com.sun.corba.se.impl.oa.NullServantImpl;
/*     */ import org.omg.CORBA.SystemException;
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
/*     */ 
/*     */ 
/*     */ public class POAPolicyMediatorImpl_R_AOM
/*     */   extends POAPolicyMediatorBase_R
/*     */ {
/*     */   POAPolicyMediatorImpl_R_AOM(Policies paramPolicies, POAImpl paramPOAImpl) {
/*  55 */     super(paramPolicies, paramPOAImpl);
/*     */ 
/*     */     
/*  58 */     if (!paramPolicies.useActiveMapOnly()) {
/*  59 */       throw paramPOAImpl.invocationWrapper().policyMediatorBadPolicyInFactory();
/*     */     }
/*     */   }
/*     */   
/*     */   protected Object internalGetServant(byte[] paramArrayOfbyte, String paramString) throws ForwardRequest {
/*     */     NullServantImpl nullServantImpl;
/*  65 */     Servant servant = internalIdToServant(paramArrayOfbyte);
/*  66 */     if (servant == null)
/*     */     {
/*  68 */       nullServantImpl = new NullServantImpl((SystemException)this.poa.invocationWrapper().nullServant()); } 
/*  69 */     return nullServantImpl;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void etherealizeAll() {}
/*     */ 
/*     */ 
/*     */   
/*     */   public ServantManager getServantManager() throws WrongPolicy {
/*  79 */     throw new WrongPolicy();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setServantManager(ServantManager paramServantManager) throws WrongPolicy {
/*  85 */     throw new WrongPolicy();
/*     */   }
/*     */ 
/*     */   
/*     */   public Servant getDefaultServant() throws NoServant, WrongPolicy {
/*  90 */     throw new WrongPolicy();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setDefaultServant(Servant paramServant) throws WrongPolicy {
/*  95 */     throw new WrongPolicy();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Servant idToServant(byte[] paramArrayOfbyte) throws WrongPolicy, ObjectNotActive {
/* 101 */     Servant servant = internalIdToServant(paramArrayOfbyte);
/*     */     
/* 103 */     if (servant == null) {
/* 104 */       throw new ObjectNotActive();
/*     */     }
/* 106 */     return servant;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/oa/poa/POAPolicyMediatorImpl_R_AOM.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */