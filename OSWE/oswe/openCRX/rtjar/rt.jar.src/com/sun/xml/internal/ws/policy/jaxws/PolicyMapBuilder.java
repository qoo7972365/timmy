/*     */ package com.sun.xml.internal.ws.policy.jaxws;
/*     */ 
/*     */ import com.sun.xml.internal.ws.policy.PolicyException;
/*     */ import com.sun.xml.internal.ws.policy.PolicyMap;
/*     */ import com.sun.xml.internal.ws.policy.PolicyMapExtender;
/*     */ import com.sun.xml.internal.ws.policy.PolicyMapMutator;
/*     */ import java.util.Arrays;
/*     */ import java.util.HashSet;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class PolicyMapBuilder
/*     */ {
/*  50 */   private List<BuilderHandler> policyBuilders = new LinkedList<>();
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
/*     */   void registerHandler(BuilderHandler builder) {
/*  65 */     if (null != builder) {
/*  66 */       this.policyBuilders.add(builder);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   PolicyMap getPolicyMap(PolicyMapMutator... externalMutators) throws PolicyException {
/*  76 */     return getNewPolicyMap(externalMutators);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private PolicyMap getNewPolicyMap(PolicyMapMutator... externalMutators) throws PolicyException {
/*  86 */     HashSet<PolicyMapMutator> mutators = new HashSet<>();
/*  87 */     PolicyMapExtender myExtender = PolicyMapExtender.createPolicyMapExtender();
/*  88 */     mutators.add(myExtender);
/*  89 */     if (null != externalMutators) {
/*  90 */       mutators.addAll(Arrays.asList(externalMutators));
/*     */     }
/*  92 */     PolicyMap policyMap = PolicyMap.createPolicyMap(mutators);
/*  93 */     for (BuilderHandler builder : this.policyBuilders) {
/*  94 */       builder.populate(myExtender);
/*     */     }
/*  96 */     return policyMap;
/*     */   }
/*     */   
/*     */   void unregisterAll() {
/* 100 */     this.policyBuilders = null;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/policy/jaxws/PolicyMapBuilder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */