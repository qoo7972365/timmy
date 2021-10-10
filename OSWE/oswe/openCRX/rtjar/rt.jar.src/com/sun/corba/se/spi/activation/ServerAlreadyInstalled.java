/*    */ package com.sun.corba.se.spi.activation;
/*    */ 
/*    */ import org.omg.CORBA.UserException;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class ServerAlreadyInstalled
/*    */   extends UserException
/*    */ {
/* 13 */   public int serverId = 0;
/*    */ 
/*    */   
/*    */   public ServerAlreadyInstalled() {
/* 17 */     super(ServerAlreadyInstalledHelper.id());
/*    */   }
/*    */ 
/*    */   
/*    */   public ServerAlreadyInstalled(int paramInt) {
/* 22 */     super(ServerAlreadyInstalledHelper.id());
/* 23 */     this.serverId = paramInt;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public ServerAlreadyInstalled(String paramString, int paramInt) {
/* 29 */     super(ServerAlreadyInstalledHelper.id() + "  " + paramString);
/* 30 */     this.serverId = paramInt;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/spi/activation/ServerAlreadyInstalled.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */