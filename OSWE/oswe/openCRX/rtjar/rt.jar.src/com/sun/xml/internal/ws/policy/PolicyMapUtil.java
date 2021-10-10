/*     */ package com.sun.xml.internal.ws.policy;
/*     */ 
/*     */ import com.sun.xml.internal.ws.policy.privateutil.LocalizationMessages;
/*     */ import com.sun.xml.internal.ws.policy.privateutil.PolicyLogger;
/*     */ import com.sun.xml.internal.ws.policy.subject.PolicyMapKeyConverter;
/*     */ import com.sun.xml.internal.ws.policy.subject.WsdlBindingSubject;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.LinkedList;
/*     */ import javax.xml.namespace.QName;
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
/*     */ public class PolicyMapUtil
/*     */ {
/*  46 */   private static final PolicyLogger LOGGER = PolicyLogger.getLogger(PolicyMapUtil.class);
/*     */   
/*  48 */   private static final PolicyMerger MERGER = PolicyMerger.getMerger();
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
/*     */   public static void rejectAlternatives(PolicyMap map) throws PolicyException {
/*  68 */     for (Policy policy : map) {
/*  69 */       if (policy.getNumberOfAssertionSets() > 1) {
/*  70 */         throw (PolicyException)LOGGER.logSevereException(new PolicyException(LocalizationMessages.WSP_0035_RECONFIGURE_ALTERNATIVES(policy.getIdOrName())));
/*     */       }
/*     */     } 
/*     */   }
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
/*     */   public static void insertPolicies(PolicyMap policyMap, Collection<PolicySubject> policySubjects, QName serviceName, QName portName) throws PolicyException {
/*  88 */     LOGGER.entering(new Object[] { policyMap, policySubjects, serviceName, portName });
/*     */     
/*  90 */     HashMap<WsdlBindingSubject, Collection<Policy>> subjectToPolicies = new HashMap<>();
/*  91 */     for (PolicySubject subject : policySubjects) {
/*  92 */       Object actualSubject = subject.getSubject();
/*  93 */       if (actualSubject instanceof WsdlBindingSubject) {
/*  94 */         WsdlBindingSubject wsdlSubject = (WsdlBindingSubject)actualSubject;
/*  95 */         Collection<Policy> subjectPolicies = new LinkedList<>();
/*  96 */         subjectPolicies.add(subject.getEffectivePolicy(MERGER));
/*  97 */         Collection<Policy> existingPolicies = subjectToPolicies.put(wsdlSubject, subjectPolicies);
/*  98 */         if (existingPolicies != null) {
/*  99 */           subjectPolicies.addAll(existingPolicies);
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 104 */     PolicyMapKeyConverter converter = new PolicyMapKeyConverter(serviceName, portName);
/* 105 */     for (WsdlBindingSubject wsdlSubject : subjectToPolicies.keySet()) {
/* 106 */       PolicySubject newSubject = new PolicySubject(wsdlSubject, subjectToPolicies.get(wsdlSubject));
/* 107 */       PolicyMapKey mapKey = converter.getPolicyMapKey(wsdlSubject);
/*     */       
/* 109 */       if (wsdlSubject.isBindingSubject()) {
/* 110 */         policyMap.putSubject(PolicyMap.ScopeType.ENDPOINT, mapKey, newSubject); continue;
/*     */       } 
/* 112 */       if (wsdlSubject.isBindingOperationSubject()) {
/* 113 */         policyMap.putSubject(PolicyMap.ScopeType.OPERATION, mapKey, newSubject); continue;
/*     */       } 
/* 115 */       if (wsdlSubject.isBindingMessageSubject()) {
/* 116 */         switch (wsdlSubject.getMessageType()) {
/*     */           case INPUT:
/* 118 */             policyMap.putSubject(PolicyMap.ScopeType.INPUT_MESSAGE, mapKey, newSubject);
/*     */           
/*     */           case OUTPUT:
/* 121 */             policyMap.putSubject(PolicyMap.ScopeType.OUTPUT_MESSAGE, mapKey, newSubject);
/*     */           
/*     */           case FAULT:
/* 124 */             policyMap.putSubject(PolicyMap.ScopeType.FAULT_MESSAGE, mapKey, newSubject);
/*     */         } 
/*     */ 
/*     */       
/*     */       }
/*     */     } 
/* 130 */     LOGGER.exiting();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/policy/PolicyMapUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */