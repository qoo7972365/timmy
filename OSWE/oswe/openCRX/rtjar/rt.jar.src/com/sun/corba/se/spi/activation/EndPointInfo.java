/*    */ package com.sun.corba.se.spi.activation;
/*    */ 
/*    */ import org.omg.CORBA.portable.IDLEntity;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class EndPointInfo
/*    */   implements IDLEntity
/*    */ {
/* 13 */   public String endpointType = null;
/* 14 */   public int port = 0;
/*    */ 
/*    */ 
/*    */   
/*    */   public EndPointInfo() {}
/*    */ 
/*    */   
/*    */   public EndPointInfo(String paramString, int paramInt) {
/* 22 */     this.endpointType = paramString;
/* 23 */     this.port = paramInt;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/spi/activation/EndPointInfo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */