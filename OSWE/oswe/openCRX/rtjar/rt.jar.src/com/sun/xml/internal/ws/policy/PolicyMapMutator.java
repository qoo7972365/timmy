/*    */ package com.sun.xml.internal.ws.policy;
/*    */ 
/*    */ import com.sun.xml.internal.ws.policy.privateutil.LocalizationMessages;
/*    */ import com.sun.xml.internal.ws.policy.privateutil.PolicyLogger;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class PolicyMapMutator
/*    */ {
/* 39 */   private static final PolicyLogger LOGGER = PolicyLogger.getLogger(PolicyMapMutator.class);
/*    */   
/* 41 */   private PolicyMap map = null;
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
/*    */   public void connect(PolicyMap map) {
/* 57 */     if (isConnected()) {
/* 58 */       throw (IllegalStateException)LOGGER.logSevereException(new IllegalStateException(LocalizationMessages.WSP_0044_POLICY_MAP_MUTATOR_ALREADY_CONNECTED()));
/*    */     }
/*    */     
/* 61 */     this.map = map;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public PolicyMap getMap() {
/* 73 */     return this.map;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void disconnect() {
/* 84 */     this.map = null;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isConnected() {
/* 93 */     return (this.map != null);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/policy/PolicyMapMutator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */