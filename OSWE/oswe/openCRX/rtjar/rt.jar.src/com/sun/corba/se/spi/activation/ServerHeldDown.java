/*    */ package com.sun.corba.se.spi.activation;
/*    */ 
/*    */ import org.omg.CORBA.UserException;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class ServerHeldDown
/*    */   extends UserException
/*    */ {
/* 13 */   public int serverId = 0;
/*    */ 
/*    */   
/*    */   public ServerHeldDown() {
/* 17 */     super(ServerHeldDownHelper.id());
/*    */   }
/*    */ 
/*    */   
/*    */   public ServerHeldDown(int paramInt) {
/* 22 */     super(ServerHeldDownHelper.id());
/* 23 */     this.serverId = paramInt;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public ServerHeldDown(String paramString, int paramInt) {
/* 29 */     super(ServerHeldDownHelper.id() + "  " + paramString);
/* 30 */     this.serverId = paramInt;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/spi/activation/ServerHeldDown.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */