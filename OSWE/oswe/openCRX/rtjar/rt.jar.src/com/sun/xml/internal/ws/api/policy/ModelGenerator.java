/*    */ package com.sun.xml.internal.ws.api.policy;
/*    */ 
/*    */ import com.sun.xml.internal.ws.policy.Policy;
/*    */ import com.sun.xml.internal.ws.policy.sourcemodel.PolicyModelGenerator;
/*    */ import com.sun.xml.internal.ws.policy.sourcemodel.PolicySourceModel;
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
/*    */ public abstract class ModelGenerator
/*    */   extends PolicyModelGenerator
/*    */ {
/* 38 */   private static final SourceModelCreator CREATOR = new SourceModelCreator();
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
/*    */   public static PolicyModelGenerator getGenerator() {
/* 53 */     return PolicyModelGenerator.getCompactGenerator(CREATOR);
/*    */   }
/*    */ 
/*    */   
/*    */   protected static class SourceModelCreator
/*    */     extends PolicyModelGenerator.PolicySourceModelCreator
/*    */   {
/*    */     protected PolicySourceModel create(Policy policy) {
/* 61 */       return SourceModel.createPolicySourceModel(policy.getNamespaceVersion(), policy
/* 62 */           .getId(), policy.getName());
/*    */     }
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/api/policy/ModelGenerator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */