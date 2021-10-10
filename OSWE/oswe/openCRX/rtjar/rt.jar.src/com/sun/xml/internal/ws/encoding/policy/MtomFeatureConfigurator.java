/*    */ package com.sun.xml.internal.ws.encoding.policy;
/*    */ 
/*    */ import com.sun.xml.internal.ws.policy.AssertionSet;
/*    */ import com.sun.xml.internal.ws.policy.Policy;
/*    */ import com.sun.xml.internal.ws.policy.PolicyAssertion;
/*    */ import com.sun.xml.internal.ws.policy.PolicyException;
/*    */ import com.sun.xml.internal.ws.policy.PolicyMap;
/*    */ import com.sun.xml.internal.ws.policy.PolicyMapKey;
/*    */ import com.sun.xml.internal.ws.policy.jaxws.spi.PolicyFeatureConfigurator;
/*    */ import java.util.Collection;
/*    */ import java.util.Iterator;
/*    */ import java.util.LinkedList;
/*    */ import javax.xml.ws.WebServiceFeature;
/*    */ import javax.xml.ws.soap.MTOMFeature;
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
/*    */ public class MtomFeatureConfigurator
/*    */   implements PolicyFeatureConfigurator
/*    */ {
/*    */   public Collection<WebServiceFeature> getFeatures(PolicyMapKey key, PolicyMap policyMap) throws PolicyException {
/* 65 */     Collection<WebServiceFeature> features = new LinkedList<>();
/* 66 */     if (key != null && policyMap != null) {
/* 67 */       Policy policy = policyMap.getEndpointEffectivePolicy(key);
/* 68 */       if (null != policy && policy.contains(EncodingConstants.OPTIMIZED_MIME_SERIALIZATION_ASSERTION)) {
/* 69 */         Iterator<AssertionSet> assertions = policy.iterator();
/* 70 */         while (assertions.hasNext()) {
/* 71 */           AssertionSet assertionSet = assertions.next();
/* 72 */           Iterator<PolicyAssertion> policyAssertion = assertionSet.iterator();
/* 73 */           while (policyAssertion.hasNext()) {
/* 74 */             PolicyAssertion assertion = policyAssertion.next();
/* 75 */             if (EncodingConstants.OPTIMIZED_MIME_SERIALIZATION_ASSERTION.equals(assertion.getName())) {
/* 76 */               features.add(new MTOMFeature(true));
/*    */             }
/*    */           } 
/*    */         } 
/*    */       } 
/*    */     } 
/* 82 */     return features;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/encoding/policy/MtomFeatureConfigurator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */