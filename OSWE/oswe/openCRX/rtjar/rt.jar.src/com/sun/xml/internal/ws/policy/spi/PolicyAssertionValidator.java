/*    */ package com.sun.xml.internal.ws.policy.spi;
/*    */ 
/*    */ import com.sun.xml.internal.ws.policy.PolicyAssertion;
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
/*    */ public interface PolicyAssertionValidator
/*    */ {
/*    */   Fitness validateClientSide(PolicyAssertion paramPolicyAssertion);
/*    */   
/*    */   Fitness validateServerSide(PolicyAssertion paramPolicyAssertion);
/*    */   
/*    */   String[] declareSupportedDomains();
/*    */   
/*    */   public enum Fitness
/*    */   {
/* 38 */     UNKNOWN,
/* 39 */     INVALID,
/* 40 */     UNSUPPORTED,
/* 41 */     SUPPORTED;
/*    */     
/*    */     public Fitness combine(Fitness other) {
/* 44 */       if (compareTo(other) < 0) {
/* 45 */         return other;
/*    */       }
/* 47 */       return this;
/*    */     }
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/policy/spi/PolicyAssertionValidator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */