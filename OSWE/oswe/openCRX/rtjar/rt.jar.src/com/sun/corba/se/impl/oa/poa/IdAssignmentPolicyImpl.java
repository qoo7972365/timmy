/*    */ package com.sun.corba.se.impl.oa.poa;
/*    */ 
/*    */ import org.omg.CORBA.LocalObject;
/*    */ import org.omg.CORBA.Policy;
/*    */ import org.omg.PortableServer.IdAssignmentPolicy;
/*    */ import org.omg.PortableServer.IdAssignmentPolicyValue;
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
/*    */ final class IdAssignmentPolicyImpl
/*    */   extends LocalObject
/*    */   implements IdAssignmentPolicy
/*    */ {
/*    */   private IdAssignmentPolicyValue value;
/*    */   
/*    */   public IdAssignmentPolicyImpl(IdAssignmentPolicyValue paramIdAssignmentPolicyValue) {
/* 36 */     this.value = paramIdAssignmentPolicyValue;
/*    */   }
/*    */   
/*    */   public IdAssignmentPolicyValue value() {
/* 40 */     return this.value;
/*    */   }
/*    */ 
/*    */   
/*    */   public int policy_type() {
/* 45 */     return 19;
/*    */   }
/*    */   
/*    */   public Policy copy() {
/* 49 */     return (Policy)new IdAssignmentPolicyImpl(this.value);
/*    */   }
/*    */   
/*    */   public void destroy() {
/* 53 */     this.value = null;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String toString() {
/* 60 */     return "IdAssignmentPolicy[" + (
/* 61 */       (this.value.value() == 0) ? "USER_ID" : "SYSTEM_ID]");
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/oa/poa/IdAssignmentPolicyImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */