/*    */ package com.sun.xml.internal.ws.policy;
/*    */ 
/*    */ import com.sun.xml.internal.ws.policy.sourcemodel.AssertionData;
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
/*    */ 
/*    */ public abstract class SimpleAssertion
/*    */   extends PolicyAssertion
/*    */ {
/*    */   protected SimpleAssertion() {}
/*    */   
/*    */   protected SimpleAssertion(AssertionData data, Collection<? extends PolicyAssertion> assertionParameters) {
/* 43 */     super(data, assertionParameters);
/*    */   }
/*    */ 
/*    */   
/*    */   public final boolean hasNestedPolicy() {
/* 48 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public final NestedPolicy getNestedPolicy() {
/* 53 */     return null;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/policy/SimpleAssertion.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */