/*    */ package com.sun.corba.se.impl.protocol;
/*    */ 
/*    */ import com.sun.corba.se.spi.oa.ObjectAdapter;
/*    */ import com.sun.corba.se.spi.protocol.CorbaMessageMediator;
/*    */ import org.omg.CORBA.portable.OutputStream;
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
/*    */ class NonExistent
/*    */   extends SpecialMethod
/*    */ {
/*    */   public boolean isNonExistentMethod() {
/* 74 */     return true;
/*    */   }
/*    */   
/*    */   public String getName() {
/* 78 */     return "_non_existent";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public CorbaMessageMediator invoke(Object paramObject, CorbaMessageMediator paramCorbaMessageMediator, byte[] paramArrayOfbyte, ObjectAdapter paramObjectAdapter) {
/* 86 */     boolean bool = (paramObject == null || paramObject instanceof com.sun.corba.se.spi.oa.NullServant) ? true : false;
/*    */     
/* 88 */     CorbaMessageMediator corbaMessageMediator = paramCorbaMessageMediator.getProtocolHandler().createResponse(paramCorbaMessageMediator, null);
/* 89 */     ((OutputStream)corbaMessageMediator.getOutputObject()).write_boolean(bool);
/* 90 */     return corbaMessageMediator;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/protocol/NonExistent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */