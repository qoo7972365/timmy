/*    */ package com.sun.corba.se.spi.activation.LocatorPackage;
/*    */ 
/*    */ import com.sun.corba.se.spi.activation.EndPointInfo;
/*    */ import org.omg.CORBA.portable.IDLEntity;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class ServerLocationPerORB
/*    */   implements IDLEntity
/*    */ {
/* 13 */   public String hostname = null;
/* 14 */   public EndPointInfo[] ports = null;
/*    */ 
/*    */ 
/*    */   
/*    */   public ServerLocationPerORB() {}
/*    */ 
/*    */   
/*    */   public ServerLocationPerORB(String paramString, EndPointInfo[] paramArrayOfEndPointInfo) {
/* 22 */     this.hostname = paramString;
/* 23 */     this.ports = paramArrayOfEndPointInfo;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/spi/activation/LocatorPackage/ServerLocationPerORB.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */