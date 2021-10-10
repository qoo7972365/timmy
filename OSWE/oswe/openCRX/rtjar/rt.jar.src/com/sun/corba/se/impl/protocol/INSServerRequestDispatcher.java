/*    */ package com.sun.corba.se.impl.protocol;
/*    */ 
/*    */ import com.sun.corba.se.impl.logging.ORBUtilSystemException;
/*    */ import com.sun.corba.se.impl.orbutil.ORBUtility;
/*    */ import com.sun.corba.se.pept.protocol.MessageMediator;
/*    */ import com.sun.corba.se.spi.ior.IOR;
/*    */ import com.sun.corba.se.spi.ior.ObjectKey;
/*    */ import com.sun.corba.se.spi.orb.ORB;
/*    */ import com.sun.corba.se.spi.protocol.CorbaMessageMediator;
/*    */ import com.sun.corba.se.spi.protocol.CorbaServerRequestDispatcher;
/*    */ import org.omg.CORBA.ORB;
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
/*    */ public class INSServerRequestDispatcher
/*    */   implements CorbaServerRequestDispatcher
/*    */ {
/* 58 */   private ORB orb = null;
/*    */   private ORBUtilSystemException wrapper;
/*    */   
/*    */   public INSServerRequestDispatcher(ORB paramORB) {
/* 62 */     this.orb = paramORB;
/* 63 */     this.wrapper = ORBUtilSystemException.get(paramORB, "rpc.protocol");
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public IOR locate(ObjectKey paramObjectKey) {
/* 71 */     String str = new String(paramObjectKey.getBytes((ORB)this.orb));
/* 72 */     return getINSReference(str);
/*    */   }
/*    */ 
/*    */   
/*    */   public void dispatch(MessageMediator paramMessageMediator) {
/* 77 */     CorbaMessageMediator corbaMessageMediator = (CorbaMessageMediator)paramMessageMediator;
/*    */ 
/*    */     
/* 80 */     String str = new String(corbaMessageMediator.getObjectKey().getBytes((ORB)this.orb));
/* 81 */     corbaMessageMediator.getProtocolHandler()
/* 82 */       .createLocationForward(corbaMessageMediator, getINSReference(str), null);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private IOR getINSReference(String paramString) {
/* 90 */     IOR iOR = ORBUtility.getIOR(this.orb.getLocalResolver().resolve(paramString));
/* 91 */     if (iOR != null)
/*    */     {
/*    */       
/* 94 */       return iOR;
/*    */     }
/*    */     
/* 97 */     throw this.wrapper.servantNotFound();
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/protocol/INSServerRequestDispatcher.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */