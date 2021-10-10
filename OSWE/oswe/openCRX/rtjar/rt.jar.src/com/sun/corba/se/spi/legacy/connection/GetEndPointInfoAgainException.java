/*    */ package com.sun.corba.se.spi.legacy.connection;
/*    */ 
/*    */ import com.sun.corba.se.spi.transport.SocketInfo;
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
/*    */ public class GetEndPointInfoAgainException
/*    */   extends Exception
/*    */ {
/*    */   private SocketInfo socketInfo;
/*    */   
/*    */   public GetEndPointInfoAgainException(SocketInfo paramSocketInfo) {
/* 45 */     this.socketInfo = paramSocketInfo;
/*    */   }
/*    */ 
/*    */   
/*    */   public SocketInfo getEndPointInfo() {
/* 50 */     return this.socketInfo;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/spi/legacy/connection/GetEndPointInfoAgainException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */