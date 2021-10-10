/*    */ package com.sun.xml.internal.ws.encoding.policy;
/*    */ 
/*    */ import com.sun.xml.internal.ws.api.client.SelectOptimalEncodingFeature;
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
/*    */ import javax.xml.namespace.QName;
/*    */ import javax.xml.ws.WebServiceFeature;
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
/*    */ public class SelectOptimalEncodingFeatureConfigurator
/*    */   implements PolicyFeatureConfigurator
/*    */ {
/* 52 */   public static final QName enabled = new QName("enabled");
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Collection<WebServiceFeature> getFeatures(PolicyMapKey key, PolicyMap policyMap) throws PolicyException {
/* 62 */     Collection<WebServiceFeature> features = new LinkedList<>();
/* 63 */     if (key != null && policyMap != null) {
/* 64 */       Policy policy = policyMap.getEndpointEffectivePolicy(key);
/* 65 */       if (null != policy && policy.contains(EncodingConstants.SELECT_OPTIMAL_ENCODING_ASSERTION)) {
/* 66 */         Iterator<AssertionSet> assertions = policy.iterator();
/* 67 */         while (assertions.hasNext()) {
/* 68 */           AssertionSet assertionSet = assertions.next();
/* 69 */           Iterator<PolicyAssertion> policyAssertion = assertionSet.iterator();
/* 70 */           while (policyAssertion.hasNext()) {
/* 71 */             PolicyAssertion assertion = policyAssertion.next();
/* 72 */             if (EncodingConstants.SELECT_OPTIMAL_ENCODING_ASSERTION.equals(assertion.getName())) {
/* 73 */               String value = assertion.getAttributeValue(enabled);
/* 74 */               boolean isSelectOptimalEncodingEnabled = (value == null || Boolean.valueOf(value.trim()).booleanValue());
/* 75 */               features.add(new SelectOptimalEncodingFeature(isSelectOptimalEncodingEnabled));
/*    */             } 
/*    */           } 
/*    */         } 
/*    */       } 
/*    */     } 
/* 81 */     return features;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/encoding/policy/SelectOptimalEncodingFeatureConfigurator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */