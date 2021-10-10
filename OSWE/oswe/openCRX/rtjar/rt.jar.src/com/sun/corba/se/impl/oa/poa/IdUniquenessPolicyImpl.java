/*    */ package com.sun.corba.se.impl.oa.poa;
/*    */ 
/*    */ import org.omg.CORBA.LocalObject;
/*    */ import org.omg.CORBA.Policy;
/*    */ import org.omg.PortableServer.IdUniquenessPolicy;
/*    */ import org.omg.PortableServer.IdUniquenessPolicyValue;
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
/*    */ final class IdUniquenessPolicyImpl
/*    */   extends LocalObject
/*    */   implements IdUniquenessPolicy
/*    */ {
/*    */   private IdUniquenessPolicyValue value;
/*    */   
/*    */   public IdUniquenessPolicyImpl(IdUniquenessPolicyValue paramIdUniquenessPolicyValue) {
/* 35 */     this.value = paramIdUniquenessPolicyValue;
/*    */   }
/*    */   
/*    */   public IdUniquenessPolicyValue value() {
/* 39 */     return this.value;
/*    */   }
/*    */ 
/*    */   
/*    */   public int policy_type() {
/* 44 */     return 18;
/*    */   }
/*    */   
/*    */   public Policy copy() {
/* 48 */     return (Policy)new IdUniquenessPolicyImpl(this.value);
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
/* 59 */     return "IdUniquenessPolicy[" + (
/* 60 */       (this.value.value() == 0) ? "UNIQUE_ID" : "MULTIPLE_ID]");
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/oa/poa/IdUniquenessPolicyImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */