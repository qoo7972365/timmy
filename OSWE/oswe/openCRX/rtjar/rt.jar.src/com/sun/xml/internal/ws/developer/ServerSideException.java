/*    */ package com.sun.xml.internal.ws.developer;
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
/*    */ public class ServerSideException
/*    */   extends Exception
/*    */ {
/*    */   private final String className;
/*    */   
/*    */   public ServerSideException(String className, String message) {
/* 43 */     super(message);
/* 44 */     this.className = className;
/*    */   }
/*    */   
/*    */   public String getMessage() {
/* 48 */     return "Client received an exception from server: " + super
/* 49 */       .getMessage() + " Please see the server log to find more detail regarding exact cause of the failure.";
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 54 */     String s = this.className;
/* 55 */     String message = getLocalizedMessage();
/* 56 */     return (message != null) ? (s + ": " + message) : s;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/developer/ServerSideException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */