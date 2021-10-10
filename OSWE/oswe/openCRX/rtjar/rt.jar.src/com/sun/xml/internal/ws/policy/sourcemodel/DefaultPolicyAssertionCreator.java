/*    */ package com.sun.xml.internal.ws.policy.sourcemodel;
/*    */ 
/*    */ import com.sun.xml.internal.ws.policy.AssertionSet;
/*    */ import com.sun.xml.internal.ws.policy.PolicyAssertion;
/*    */ import com.sun.xml.internal.ws.policy.spi.AssertionCreationException;
/*    */ import com.sun.xml.internal.ws.policy.spi.PolicyAssertionCreator;
/*    */ import java.util.Collection;
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
/*    */ class DefaultPolicyAssertionCreator
/*    */   implements PolicyAssertionCreator
/*    */ {
/*    */   private static final class DefaultPolicyAssertion
/*    */     extends PolicyAssertion
/*    */   {
/*    */     DefaultPolicyAssertion(AssertionData data, Collection<PolicyAssertion> assertionParameters, AssertionSet nestedAlternative) {
/* 46 */       super(data, assertionParameters, nestedAlternative);
/*    */     }
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
/*    */ 
/*    */   
/*    */   public String[] getSupportedDomainNamespaceURIs() {
/* 61 */     return null;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public PolicyAssertion createAssertion(AssertionData data, Collection<PolicyAssertion> assertionParameters, AssertionSet nestedAlternative, PolicyAssertionCreator defaultCreator) throws AssertionCreationException {
/* 68 */     return new DefaultPolicyAssertion(data, assertionParameters, nestedAlternative);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/policy/sourcemodel/DefaultPolicyAssertionCreator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */