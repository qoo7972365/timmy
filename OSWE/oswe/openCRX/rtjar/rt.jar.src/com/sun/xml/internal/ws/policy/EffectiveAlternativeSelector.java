/*     */ package com.sun.xml.internal.ws.policy;
/*     */ 
/*     */ import com.sun.xml.internal.ws.policy.privateutil.LocalizationMessages;
/*     */ import com.sun.xml.internal.ws.policy.privateutil.PolicyLogger;
/*     */ import com.sun.xml.internal.ws.policy.spi.PolicyAssertionValidator;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EffectiveAlternativeSelector
/*     */ {
/*     */   private enum AlternativeFitness
/*     */   {
/*  48 */     UNEVALUATED {
/*     */       AlternativeFitness combine(PolicyAssertionValidator.Fitness assertionFitness) {
/*  50 */         switch (assertionFitness) {
/*     */           case INVALID:
/*  52 */             return UNKNOWN;
/*     */           case UNKNOWN:
/*  54 */             return UNSUPPORTED;
/*     */           case UNSUPPORTED:
/*  56 */             return SUPPORTED;
/*     */           case PARTIALLY_SUPPORTED:
/*  58 */             return INVALID;
/*     */         } 
/*  60 */         return UNEVALUATED;
/*     */       }
/*     */     },
/*     */     
/*  64 */     INVALID {
/*     */       AlternativeFitness combine(PolicyAssertionValidator.Fitness assertionFitness) {
/*  66 */         return INVALID;
/*     */       }
/*     */     },
/*  69 */     UNKNOWN {
/*     */       AlternativeFitness combine(PolicyAssertionValidator.Fitness assertionFitness) {
/*  71 */         switch (assertionFitness) {
/*     */           case INVALID:
/*  73 */             return UNKNOWN;
/*     */           case UNKNOWN:
/*  75 */             return UNSUPPORTED;
/*     */           case UNSUPPORTED:
/*  77 */             return PARTIALLY_SUPPORTED;
/*     */           case PARTIALLY_SUPPORTED:
/*  79 */             return INVALID;
/*     */         } 
/*  81 */         return UNEVALUATED;
/*     */       }
/*     */     },
/*     */     
/*  85 */     UNSUPPORTED {
/*     */       AlternativeFitness combine(PolicyAssertionValidator.Fitness assertionFitness) {
/*  87 */         switch (assertionFitness) {
/*     */           case INVALID:
/*     */           case UNKNOWN:
/*  90 */             return UNSUPPORTED;
/*     */           case UNSUPPORTED:
/*  92 */             return PARTIALLY_SUPPORTED;
/*     */           case PARTIALLY_SUPPORTED:
/*  94 */             return INVALID;
/*     */         } 
/*  96 */         return UNEVALUATED;
/*     */       }
/*     */     },
/*     */     
/* 100 */     PARTIALLY_SUPPORTED {
/*     */       AlternativeFitness combine(PolicyAssertionValidator.Fitness assertionFitness) {
/* 102 */         switch (assertionFitness) {
/*     */           case INVALID:
/*     */           case UNKNOWN:
/*     */           case UNSUPPORTED:
/* 106 */             return PARTIALLY_SUPPORTED;
/*     */           case PARTIALLY_SUPPORTED:
/* 108 */             return INVALID;
/*     */         } 
/* 110 */         return UNEVALUATED;
/*     */       }
/*     */     },
/*     */     
/* 114 */     SUPPORTED_EMPTY
/*     */     {
/*     */       AlternativeFitness combine(PolicyAssertionValidator.Fitness assertionFitness) {
/* 117 */         throw new UnsupportedOperationException("Combine operation was called unexpectedly on 'SUPPORTED_EMPTY' alternative fitness enumeration state.");
/*     */       }
/*     */     },
/* 120 */     SUPPORTED {
/*     */       AlternativeFitness combine(PolicyAssertionValidator.Fitness assertionFitness) {
/* 122 */         switch (assertionFitness) {
/*     */           case INVALID:
/*     */           case UNKNOWN:
/* 125 */             return PARTIALLY_SUPPORTED;
/*     */           case UNSUPPORTED:
/* 127 */             return SUPPORTED;
/*     */           case PARTIALLY_SUPPORTED:
/* 129 */             return INVALID;
/*     */         } 
/* 131 */         return UNEVALUATED;
/*     */       }
/*     */     };
/*     */ 
/*     */     
/*     */     abstract AlternativeFitness combine(PolicyAssertionValidator.Fitness param1Fitness);
/*     */   }
/*     */   
/* 139 */   private static final PolicyLogger LOGGER = PolicyLogger.getLogger(EffectiveAlternativeSelector.class);
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
/*     */   public static void doSelection(EffectivePolicyModifier modifier) throws PolicyException {
/* 152 */     AssertionValidationProcessor validationProcessor = AssertionValidationProcessor.getInstance();
/* 153 */     selectAlternatives(modifier, validationProcessor);
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
/*     */   protected static void selectAlternatives(EffectivePolicyModifier modifier, AssertionValidationProcessor validationProcessor) throws PolicyException {
/* 167 */     PolicyMap map = modifier.getMap();
/* 168 */     for (PolicyMapKey mapKey : map.getAllServiceScopeKeys()) {
/* 169 */       Policy oldPolicy = map.getServiceEffectivePolicy(mapKey);
/* 170 */       modifier.setNewEffectivePolicyForServiceScope(mapKey, selectBestAlternative(oldPolicy, validationProcessor));
/*     */     } 
/* 172 */     for (PolicyMapKey mapKey : map.getAllEndpointScopeKeys()) {
/* 173 */       Policy oldPolicy = map.getEndpointEffectivePolicy(mapKey);
/* 174 */       modifier.setNewEffectivePolicyForEndpointScope(mapKey, selectBestAlternative(oldPolicy, validationProcessor));
/*     */     } 
/* 176 */     for (PolicyMapKey mapKey : map.getAllOperationScopeKeys()) {
/* 177 */       Policy oldPolicy = map.getOperationEffectivePolicy(mapKey);
/* 178 */       modifier.setNewEffectivePolicyForOperationScope(mapKey, selectBestAlternative(oldPolicy, validationProcessor));
/*     */     } 
/* 180 */     for (PolicyMapKey mapKey : map.getAllInputMessageScopeKeys()) {
/* 181 */       Policy oldPolicy = map.getInputMessageEffectivePolicy(mapKey);
/* 182 */       modifier.setNewEffectivePolicyForInputMessageScope(mapKey, selectBestAlternative(oldPolicy, validationProcessor));
/*     */     } 
/* 184 */     for (PolicyMapKey mapKey : map.getAllOutputMessageScopeKeys()) {
/* 185 */       Policy oldPolicy = map.getOutputMessageEffectivePolicy(mapKey);
/* 186 */       modifier.setNewEffectivePolicyForOutputMessageScope(mapKey, selectBestAlternative(oldPolicy, validationProcessor));
/*     */     } 
/* 188 */     for (PolicyMapKey mapKey : map.getAllFaultMessageScopeKeys()) {
/* 189 */       Policy oldPolicy = map.getFaultMessageEffectivePolicy(mapKey);
/* 190 */       modifier.setNewEffectivePolicyForFaultMessageScope(mapKey, selectBestAlternative(oldPolicy, validationProcessor));
/*     */     } 
/*     */   }
/*     */   
/*     */   private static Policy selectBestAlternative(Policy policy, AssertionValidationProcessor validationProcessor) throws PolicyException {
/* 195 */     AssertionSet bestAlternative = null;
/* 196 */     AlternativeFitness bestAlternativeFitness = AlternativeFitness.UNEVALUATED;
/* 197 */     for (AssertionSet alternative : policy) {
/* 198 */       AlternativeFitness alternativeFitness = alternative.isEmpty() ? AlternativeFitness.SUPPORTED_EMPTY : AlternativeFitness.UNEVALUATED;
/* 199 */       for (PolicyAssertion assertion : alternative) {
/*     */         
/* 201 */         PolicyAssertionValidator.Fitness assertionFitness = validationProcessor.validateClientSide(assertion);
/* 202 */         switch (assertionFitness) {
/*     */           case INVALID:
/*     */           case UNKNOWN:
/*     */           case PARTIALLY_SUPPORTED:
/* 206 */             LOGGER.warning(LocalizationMessages.WSP_0075_PROBLEMATIC_ASSERTION_STATE(assertion.getName(), assertionFitness));
/*     */             break;
/*     */         } 
/*     */ 
/*     */ 
/*     */         
/* 212 */         alternativeFitness = alternativeFitness.combine(assertionFitness);
/*     */       } 
/*     */       
/* 215 */       if (bestAlternativeFitness.compareTo(alternativeFitness) < 0) {
/*     */         
/* 217 */         bestAlternative = alternative;
/* 218 */         bestAlternativeFitness = alternativeFitness;
/*     */       } 
/*     */       
/* 221 */       if (bestAlternativeFitness == AlternativeFitness.SUPPORTED) {
/*     */         break;
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 227 */     switch (bestAlternativeFitness) {
/*     */       case INVALID:
/* 229 */         throw (PolicyException)LOGGER.logSevereException(new PolicyException(LocalizationMessages.WSP_0053_INVALID_CLIENT_SIDE_ALTERNATIVE()));
/*     */       case UNKNOWN:
/*     */       case UNSUPPORTED:
/*     */       case PARTIALLY_SUPPORTED:
/* 233 */         LOGGER.warning(LocalizationMessages.WSP_0019_SUBOPTIMAL_ALTERNATIVE_SELECTED(bestAlternativeFitness));
/*     */         break;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 239 */     Collection<AssertionSet> alternativeSet = null;
/* 240 */     if (bestAlternative != null) {
/*     */       
/* 242 */       alternativeSet = new LinkedList<>();
/* 243 */       alternativeSet.add(bestAlternative);
/*     */     } 
/* 245 */     return Policy.createPolicy(policy.getNamespaceVersion(), policy.getName(), policy.getId(), alternativeSet);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/policy/EffectiveAlternativeSelector.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */