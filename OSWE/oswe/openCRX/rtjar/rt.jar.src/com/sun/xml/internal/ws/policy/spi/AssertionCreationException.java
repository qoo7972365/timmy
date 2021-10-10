/*    */ package com.sun.xml.internal.ws.policy.spi;
/*    */ 
/*    */ import com.sun.xml.internal.ws.policy.PolicyException;
/*    */ import com.sun.xml.internal.ws.policy.sourcemodel.AssertionData;
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
/*    */ public final class AssertionCreationException
/*    */   extends PolicyException
/*    */ {
/*    */   private final AssertionData assertionData;
/*    */   
/*    */   public AssertionCreationException(AssertionData assertionData, String message) {
/* 50 */     super(message);
/* 51 */     this.assertionData = assertionData;
/*    */   }
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
/*    */   public AssertionCreationException(AssertionData assertionData, String message, Throwable cause) {
/* 65 */     super(message, cause);
/* 66 */     this.assertionData = assertionData;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public AssertionCreationException(AssertionData assertionData, Throwable cause) {
/* 76 */     super(cause);
/* 77 */     this.assertionData = assertionData;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public AssertionData getAssertionData() {
/* 86 */     return this.assertionData;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/policy/spi/AssertionCreationException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */