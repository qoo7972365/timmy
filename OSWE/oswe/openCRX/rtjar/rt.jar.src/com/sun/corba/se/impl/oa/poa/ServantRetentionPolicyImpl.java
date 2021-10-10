/*    */ package com.sun.corba.se.impl.oa.poa;
/*    */ 
/*    */ import org.omg.CORBA.LocalObject;
/*    */ import org.omg.CORBA.Policy;
/*    */ import org.omg.PortableServer.ServantRetentionPolicy;
/*    */ import org.omg.PortableServer.ServantRetentionPolicyValue;
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
/*    */ final class ServantRetentionPolicyImpl
/*    */   extends LocalObject
/*    */   implements ServantRetentionPolicy
/*    */ {
/*    */   private ServantRetentionPolicyValue value;
/*    */   
/*    */   public ServantRetentionPolicyImpl(ServantRetentionPolicyValue paramServantRetentionPolicyValue) {
/* 35 */     this.value = paramServantRetentionPolicyValue;
/*    */   }
/*    */   
/*    */   public ServantRetentionPolicyValue value() {
/* 39 */     return this.value;
/*    */   }
/*    */ 
/*    */   
/*    */   public int policy_type() {
/* 44 */     return 21;
/*    */   }
/*    */   
/*    */   public Policy copy() {
/* 48 */     return (Policy)new ServantRetentionPolicyImpl(this.value);
/*    */   }
/*    */   
/*    */   public void destroy() {
/* 52 */     this.value = null;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String toString() {
/* 59 */     return "ServantRetentionPolicy[" + (
/* 60 */       (this.value.value() == 0) ? "RETAIN" : "NON_RETAIN]");
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/oa/poa/ServantRetentionPolicyImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */