/*     */ package com.sun.corba.se.impl.protocol;
/*     */ 
/*     */ import com.sun.corba.se.impl.logging.ORBUtilSystemException;
/*     */ import com.sun.corba.se.spi.oa.ObjectAdapter;
/*     */ import com.sun.corba.se.spi.orb.ORB;
/*     */ import com.sun.corba.se.spi.protocol.CorbaMessageMediator;
/*     */ import org.omg.CORBA.SystemException;
/*     */ import org.omg.CORBA.portable.InputStream;
/*     */ import org.omg.CORBA.portable.OutputStream;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class IsA
/*     */   extends SpecialMethod
/*     */ {
/*     */   public boolean isNonExistentMethod() {
/* 103 */     return false;
/*     */   }
/*     */   
/*     */   public String getName() {
/* 107 */     return "_is_a";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CorbaMessageMediator invoke(Object paramObject, CorbaMessageMediator paramCorbaMessageMediator, byte[] paramArrayOfbyte, ObjectAdapter paramObjectAdapter) {
/* 114 */     if (paramObject == null || paramObject instanceof com.sun.corba.se.spi.oa.NullServant) {
/* 115 */       ORB oRB = (ORB)paramCorbaMessageMediator.getBroker();
/* 116 */       ORBUtilSystemException oRBUtilSystemException = ORBUtilSystemException.get(oRB, "oa.invocation");
/*     */ 
/*     */       
/* 119 */       return paramCorbaMessageMediator.getProtocolHandler().createSystemExceptionResponse(paramCorbaMessageMediator, (SystemException)oRBUtilSystemException
/* 120 */           .badSkeleton(), null);
/*     */     } 
/*     */     
/* 123 */     String[] arrayOfString = paramObjectAdapter.getInterfaces(paramObject, paramArrayOfbyte);
/*     */     
/* 125 */     String str = ((InputStream)paramCorbaMessageMediator.getInputObject()).read_string();
/* 126 */     boolean bool = false;
/* 127 */     for (byte b = 0; b < arrayOfString.length; b++) {
/* 128 */       if (arrayOfString[b].equals(str)) {
/* 129 */         bool = true;
/*     */         
/*     */         break;
/*     */       } 
/*     */     } 
/* 134 */     CorbaMessageMediator corbaMessageMediator = paramCorbaMessageMediator.getProtocolHandler().createResponse(paramCorbaMessageMediator, null);
/* 135 */     ((OutputStream)corbaMessageMediator.getOutputObject()).write_boolean(bool);
/* 136 */     return corbaMessageMediator;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/protocol/IsA.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */