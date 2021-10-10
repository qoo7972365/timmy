/*    */ package com.sun.corba.se.impl.oa.poa;
/*    */ 
/*    */ import org.omg.CORBA.LocalObject;
/*    */ import org.omg.CORBA.Policy;
/*    */ import org.omg.PortableServer.RequestProcessingPolicy;
/*    */ import org.omg.PortableServer.RequestProcessingPolicyValue;
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
/*    */ public class RequestProcessingPolicyImpl
/*    */   extends LocalObject
/*    */   implements RequestProcessingPolicy
/*    */ {
/*    */   private RequestProcessingPolicyValue value;
/*    */   
/*    */   public RequestProcessingPolicyImpl(RequestProcessingPolicyValue paramRequestProcessingPolicyValue) {
/* 36 */     this.value = paramRequestProcessingPolicyValue;
/*    */   }
/*    */   
/*    */   public RequestProcessingPolicyValue value() {
/* 40 */     return this.value;
/*    */   }
/*    */ 
/*    */   
/*    */   public int policy_type() {
/* 45 */     return 22;
/*    */   }
/*    */   
/*    */   public Policy copy() {
/* 49 */     return (Policy)new RequestProcessingPolicyImpl(this.value);
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
/* 60 */     String str = null;
/* 61 */     switch (this.value.value()) {
/*    */       case 0:
/* 63 */         str = "USE_ACTIVE_OBJECT_MAP_ONLY";
/*    */         break;
/*    */       case 1:
/* 66 */         str = "USE_DEFAULT_SERVANT";
/*    */         break;
/*    */       case 2:
/* 69 */         str = "USE_SERVANT_MANAGER";
/*    */         break;
/*    */     } 
/*    */     
/* 73 */     return "RequestProcessingPolicy[" + str + "]";
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/oa/poa/RequestProcessingPolicyImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */