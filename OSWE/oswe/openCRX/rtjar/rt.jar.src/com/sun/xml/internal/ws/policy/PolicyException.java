/*    */ package com.sun.xml.internal.ws.policy;
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
/*    */ public class PolicyException
/*    */   extends Exception
/*    */ {
/*    */   public PolicyException(String message) {
/* 34 */     super(message);
/*    */   }
/*    */ 
/*    */   
/*    */   public PolicyException(String message, Throwable cause) {
/* 39 */     super(message, cause);
/*    */   }
/*    */ 
/*    */   
/*    */   public PolicyException(Throwable cause) {
/* 44 */     super(cause);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/policy/PolicyException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */