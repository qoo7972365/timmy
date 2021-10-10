/*    */ package com.sun.corba.se.impl.legacy.connection;
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
/*    */ public class USLPort
/*    */ {
/*    */   private String type;
/*    */   private int port;
/*    */   
/*    */   public USLPort(String paramString, int paramInt) {
/* 35 */     this.type = paramString;
/* 36 */     this.port = paramInt;
/*    */   }
/*    */   
/* 39 */   public String getType() { return this.type; }
/* 40 */   public int getPort() { return this.port; } public String toString() {
/* 41 */     return this.type + ":" + this.port;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/legacy/connection/USLPort.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */