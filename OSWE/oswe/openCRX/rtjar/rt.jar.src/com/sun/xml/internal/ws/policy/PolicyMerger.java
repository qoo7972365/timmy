/*     */ package com.sun.xml.internal.ws.policy;
/*     */ 
/*     */ import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
/*     */ import com.sun.xml.internal.ws.policy.sourcemodel.wspolicy.NamespaceVersion;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.LinkedList;
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
/*     */ public final class PolicyMerger
/*     */ {
/*  40 */   private static final PolicyMerger merger = new PolicyMerger();
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
/*     */   public static PolicyMerger getMerger() {
/*  55 */     return merger;
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
/*     */   public Policy merge(Collection<Policy> policies) {
/*  71 */     if (policies == null || policies.isEmpty())
/*  72 */       return null; 
/*  73 */     if (policies.size() == 1) {
/*  74 */       return policies.iterator().next();
/*     */     }
/*     */     
/*  77 */     Collection<Collection<AssertionSet>> alternativeSets = new LinkedList<>();
/*  78 */     StringBuilder id = new StringBuilder();
/*  79 */     NamespaceVersion mergedVersion = ((Policy)policies.iterator().next()).getNamespaceVersion();
/*  80 */     for (Policy policy : policies) {
/*  81 */       alternativeSets.add(policy.getContent());
/*  82 */       if (mergedVersion.compareTo((Enum)policy.getNamespaceVersion()) < 0) {
/*  83 */         mergedVersion = policy.getNamespaceVersion();
/*     */       }
/*  85 */       String policyId = policy.getId();
/*  86 */       if (policyId != null) {
/*  87 */         if (id.length() > 0) {
/*  88 */           id.append('-');
/*     */         }
/*  90 */         id.append(policyId);
/*     */       } 
/*     */     } 
/*     */     
/*  94 */     Collection<Collection<AssertionSet>> combinedAlternatives = PolicyUtils.Collections.combine(null, alternativeSets, false);
/*     */     
/*  96 */     if (combinedAlternatives == null || combinedAlternatives.isEmpty()) {
/*  97 */       return Policy.createNullPolicy(mergedVersion, null, (id.length() == 0) ? null : id.toString());
/*     */     }
/*  99 */     Collection<AssertionSet> mergedSetList = new ArrayList<>(combinedAlternatives.size());
/* 100 */     for (Collection<AssertionSet> toBeMerged : combinedAlternatives) {
/* 101 */       mergedSetList.add(AssertionSet.createMergedAssertionSet(toBeMerged));
/*     */     }
/* 103 */     return Policy.createPolicy(mergedVersion, null, (id.length() == 0) ? null : id.toString(), mergedSetList);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/policy/PolicyMerger.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */