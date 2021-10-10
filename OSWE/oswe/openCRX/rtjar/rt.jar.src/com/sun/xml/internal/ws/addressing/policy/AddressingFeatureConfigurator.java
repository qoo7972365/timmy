/*     */ package com.sun.xml.internal.ws.addressing.policy;
/*     */ 
/*     */ import com.sun.xml.internal.bind.util.Which;
/*     */ import com.sun.xml.internal.ws.addressing.W3CAddressingMetadataConstants;
/*     */ import com.sun.xml.internal.ws.api.addressing.AddressingVersion;
/*     */ import com.sun.xml.internal.ws.policy.AssertionSet;
/*     */ import com.sun.xml.internal.ws.policy.NestedPolicy;
/*     */ import com.sun.xml.internal.ws.policy.Policy;
/*     */ import com.sun.xml.internal.ws.policy.PolicyAssertion;
/*     */ import com.sun.xml.internal.ws.policy.PolicyException;
/*     */ import com.sun.xml.internal.ws.policy.PolicyMap;
/*     */ import com.sun.xml.internal.ws.policy.PolicyMapKey;
/*     */ import com.sun.xml.internal.ws.policy.jaxws.spi.PolicyFeatureConfigurator;
/*     */ import com.sun.xml.internal.ws.policy.privateutil.PolicyLogger;
/*     */ import com.sun.xml.internal.ws.resources.ModelerMessages;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedList;
/*     */ import java.util.logging.Level;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.ws.WebServiceException;
/*     */ import javax.xml.ws.WebServiceFeature;
/*     */ import javax.xml.ws.soap.AddressingFeature;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AddressingFeatureConfigurator
/*     */   implements PolicyFeatureConfigurator
/*     */ {
/*  60 */   private static final PolicyLogger LOGGER = PolicyLogger.getLogger(AddressingFeatureConfigurator.class);
/*     */   
/*  62 */   private static final QName[] ADDRESSING_ASSERTIONS = new QName[] { new QName(AddressingVersion.MEMBER.policyNsUri, "UsingAddressing") };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Collection<WebServiceFeature> getFeatures(PolicyMapKey key, PolicyMap policyMap) throws PolicyException {
/*  72 */     LOGGER.entering(new Object[] { key, policyMap });
/*  73 */     Collection<WebServiceFeature> features = new LinkedList<>();
/*  74 */     if (key != null && policyMap != null) {
/*  75 */       Policy policy = policyMap.getEndpointEffectivePolicy(key);
/*  76 */       for (QName addressingAssertionQName : ADDRESSING_ASSERTIONS) {
/*  77 */         if (policy != null && policy.contains(addressingAssertionQName)) {
/*  78 */           Iterator<AssertionSet> assertions = policy.iterator();
/*  79 */           while (assertions.hasNext()) {
/*  80 */             AssertionSet assertionSet = assertions.next();
/*  81 */             Iterator<PolicyAssertion> policyAssertion = assertionSet.iterator();
/*  82 */             while (policyAssertion.hasNext()) {
/*  83 */               PolicyAssertion assertion = policyAssertion.next();
/*  84 */               if (assertion.getName().equals(addressingAssertionQName)) {
/*  85 */                 WebServiceFeature feature = AddressingVersion.getFeature(addressingAssertionQName.getNamespaceURI(), true, !assertion.isOptional());
/*  86 */                 if (LOGGER.isLoggable(Level.FINE)) {
/*  87 */                   LOGGER.fine("Added addressing feature \"" + feature + "\" for element \"" + key + "\"");
/*     */                 }
/*  89 */                 features.add(feature);
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/*  97 */       if (policy != null && policy.contains(W3CAddressingMetadataConstants.WSAM_ADDRESSING_ASSERTION)) {
/*  98 */         for (AssertionSet assertions : policy) {
/*  99 */           for (PolicyAssertion assertion : assertions) {
/* 100 */             if (assertion.getName().equals(W3CAddressingMetadataConstants.WSAM_ADDRESSING_ASSERTION)) {
/* 101 */               AddressingFeature addressingFeature; NestedPolicy nestedPolicy = assertion.getNestedPolicy();
/* 102 */               boolean requiresAnonymousResponses = false;
/* 103 */               boolean requiresNonAnonymousResponses = false;
/* 104 */               if (nestedPolicy != null) {
/* 105 */                 requiresAnonymousResponses = nestedPolicy.contains(W3CAddressingMetadataConstants.WSAM_ANONYMOUS_NESTED_ASSERTION);
/* 106 */                 requiresNonAnonymousResponses = nestedPolicy.contains(W3CAddressingMetadataConstants.WSAM_NONANONYMOUS_NESTED_ASSERTION);
/*     */               } 
/* 108 */               if (requiresAnonymousResponses && requiresNonAnonymousResponses) {
/* 109 */                 throw new WebServiceException("Only one among AnonymousResponses and NonAnonymousResponses can be nested in an Addressing assertion");
/*     */               }
/*     */ 
/*     */               
/*     */               try {
/* 114 */                 if (requiresAnonymousResponses) {
/* 115 */                   addressingFeature = new AddressingFeature(true, !assertion.isOptional(), AddressingFeature.Responses.ANONYMOUS);
/* 116 */                 } else if (requiresNonAnonymousResponses) {
/* 117 */                   addressingFeature = new AddressingFeature(true, !assertion.isOptional(), AddressingFeature.Responses.NON_ANONYMOUS);
/*     */                 } else {
/* 119 */                   addressingFeature = new AddressingFeature(true, !assertion.isOptional());
/*     */                 } 
/* 121 */               } catch (NoSuchMethodError e) {
/* 122 */                 throw (PolicyException)LOGGER.logSevereException(new PolicyException(ModelerMessages.RUNTIME_MODELER_ADDRESSING_RESPONSES_NOSUCHMETHOD(toJar(Which.which(AddressingFeature.class))), e));
/*     */               } 
/* 124 */               if (LOGGER.isLoggable(Level.FINE)) {
/* 125 */                 LOGGER.fine("Added addressing feature \"" + addressingFeature + "\" for element \"" + key + "\"");
/*     */               }
/* 127 */               features.add(addressingFeature);
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       }
/*     */     } 
/* 133 */     LOGGER.exiting(features);
/* 134 */     return features;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static String toJar(String url) {
/* 141 */     if (!url.startsWith("jar:"))
/* 142 */       return url; 
/* 143 */     url = url.substring(4);
/* 144 */     return url.substring(0, url.lastIndexOf('!'));
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/addressing/policy/AddressingFeatureConfigurator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */