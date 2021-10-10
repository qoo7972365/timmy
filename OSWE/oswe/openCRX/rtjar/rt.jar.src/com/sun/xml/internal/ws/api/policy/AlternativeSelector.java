/*    */ package com.sun.xml.internal.ws.api.policy;
/*    */ 
/*    */ import com.sun.xml.internal.ws.policy.EffectiveAlternativeSelector;
/*    */ import com.sun.xml.internal.ws.policy.EffectivePolicyModifier;
/*    */ import com.sun.xml.internal.ws.policy.PolicyException;
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
/*    */ public class AlternativeSelector
/*    */   extends EffectiveAlternativeSelector
/*    */ {
/*    */   public static void doSelection(EffectivePolicyModifier modifier) throws PolicyException {
/* 39 */     ValidationProcessor validationProcessor = ValidationProcessor.getInstance();
/* 40 */     selectAlternatives(modifier, validationProcessor);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/api/policy/AlternativeSelector.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */