/*    */ package com.sun.corba.se.spi.activation;
/*    */ 
/*    */ import org.omg.CORBA.UserException;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class NoSuchEndPoint
/*    */   extends UserException
/*    */ {
/*    */   public NoSuchEndPoint() {
/* 16 */     super(NoSuchEndPointHelper.id());
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public NoSuchEndPoint(String paramString) {
/* 22 */     super(NoSuchEndPointHelper.id() + "  " + paramString);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/spi/activation/NoSuchEndPoint.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */