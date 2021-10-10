/*    */ package com.sun.xml.internal.ws.api.model;
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
/*    */ public enum MEP
/*    */ {
/* 34 */   REQUEST_RESPONSE(false),
/* 35 */   ONE_WAY(false),
/* 36 */   ASYNC_POLL(true),
/* 37 */   ASYNC_CALLBACK(true);
/*    */ 
/*    */   
/*    */   public final boolean isAsync;
/*    */ 
/*    */ 
/*    */   
/*    */   MEP(boolean async) {
/* 45 */     this.isAsync = async;
/*    */   }
/*    */   
/*    */   public final boolean isOneWay() {
/* 49 */     return (this == ONE_WAY);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/api/model/MEP.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */