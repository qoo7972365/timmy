/*    */ package com.sun.corba.se.impl.oa.poa;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ abstract class POAPolicyMediatorFactory
/*    */ {
/*    */   static POAPolicyMediator create(Policies paramPolicies, POAImpl paramPOAImpl) {
/* 34 */     if (paramPolicies.retainServants()) {
/* 35 */       if (paramPolicies.useActiveMapOnly())
/* 36 */         return new POAPolicyMediatorImpl_R_AOM(paramPolicies, paramPOAImpl); 
/* 37 */       if (paramPolicies.useDefaultServant())
/* 38 */         return new POAPolicyMediatorImpl_R_UDS(paramPolicies, paramPOAImpl); 
/* 39 */       if (paramPolicies.useServantManager()) {
/* 40 */         return new POAPolicyMediatorImpl_R_USM(paramPolicies, paramPOAImpl);
/*    */       }
/* 42 */       throw paramPOAImpl.invocationWrapper().pmfCreateRetain();
/*    */     } 
/* 44 */     if (paramPolicies.useDefaultServant())
/* 45 */       return new POAPolicyMediatorImpl_NR_UDS(paramPolicies, paramPOAImpl); 
/* 46 */     if (paramPolicies.useServantManager()) {
/* 47 */       return new POAPolicyMediatorImpl_NR_USM(paramPolicies, paramPOAImpl);
/*    */     }
/* 49 */     throw paramPOAImpl.invocationWrapper().pmfCreateNonRetain();
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/oa/poa/POAPolicyMediatorFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */