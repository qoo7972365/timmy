/*    */ package com.sun.corba.se.spi.activation;
/*    */ 
/*    */ import org.omg.CORBA.UserException;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class ServerNotActive
/*    */   extends UserException
/*    */ {
/* 13 */   public int serverId = 0;
/*    */ 
/*    */   
/*    */   public ServerNotActive() {
/* 17 */     super(ServerNotActiveHelper.id());
/*    */   }
/*    */ 
/*    */   
/*    */   public ServerNotActive(int paramInt) {
/* 22 */     super(ServerNotActiveHelper.id());
/* 23 */     this.serverId = paramInt;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public ServerNotActive(String paramString, int paramInt) {
/* 29 */     super(ServerNotActiveHelper.id() + "  " + paramString);
/* 30 */     this.serverId = paramInt;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/spi/activation/ServerNotActive.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */