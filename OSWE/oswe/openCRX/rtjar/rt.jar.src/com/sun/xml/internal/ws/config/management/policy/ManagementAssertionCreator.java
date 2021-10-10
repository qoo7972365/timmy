/*    */ package com.sun.xml.internal.ws.config.management.policy;
/*    */ 
/*    */ import com.sun.xml.internal.ws.api.config.management.policy.ManagedClientAssertion;
/*    */ import com.sun.xml.internal.ws.api.config.management.policy.ManagedServiceAssertion;
/*    */ import com.sun.xml.internal.ws.policy.AssertionSet;
/*    */ import com.sun.xml.internal.ws.policy.PolicyAssertion;
/*    */ import com.sun.xml.internal.ws.policy.sourcemodel.AssertionData;
/*    */ import com.sun.xml.internal.ws.policy.spi.AssertionCreationException;
/*    */ import com.sun.xml.internal.ws.policy.spi.PolicyAssertionCreator;
/*    */ import java.util.Collection;
/*    */ import javax.xml.namespace.QName;
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
/*    */ public class ManagementAssertionCreator
/*    */   implements PolicyAssertionCreator
/*    */ {
/*    */   public String[] getSupportedDomainNamespaceURIs() {
/* 48 */     return new String[] { "http://java.sun.com/xml/ns/metro/management" };
/*    */   }
/*    */ 
/*    */   
/*    */   public PolicyAssertion createAssertion(AssertionData data, Collection<PolicyAssertion> assertionParameters, AssertionSet nestedAlternative, PolicyAssertionCreator defaultCreator) throws AssertionCreationException {
/* 53 */     QName name = data.getName();
/* 54 */     if (ManagedServiceAssertion.MANAGED_SERVICE_QNAME.equals(name)) {
/* 55 */       return (PolicyAssertion)new ManagedServiceAssertion(data, assertionParameters);
/*    */     }
/* 57 */     if (ManagedClientAssertion.MANAGED_CLIENT_QNAME.equals(name)) {
/* 58 */       return (PolicyAssertion)new ManagedClientAssertion(data, assertionParameters);
/*    */     }
/*    */     
/* 61 */     return defaultCreator.createAssertion(data, assertionParameters, nestedAlternative, null);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/config/management/policy/ManagementAssertionCreator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */