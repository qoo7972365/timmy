/*    */ package com.sun.corba.se.impl.protocol;
/*    */ 
/*    */ import com.sun.corba.se.spi.ior.IOR;
/*    */ import com.sun.corba.se.spi.oa.OAInvocationInfo;
/*    */ import com.sun.corba.se.spi.orb.ORB;
/*    */ import org.omg.CORBA.Object;
/*    */ import org.omg.CORBA.portable.ServantObject;
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
/*    */ public class InfoOnlyServantCacheLocalCRDImpl
/*    */   extends ServantCacheLocalCRDBase
/*    */ {
/*    */   public InfoOnlyServantCacheLocalCRDImpl(ORB paramORB, int paramInt, IOR paramIOR) {
/* 44 */     super(paramORB, paramInt, paramIOR);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public ServantObject servant_preinvoke(Object paramObject, String paramString, Class paramClass) {
/* 50 */     OAInvocationInfo oAInvocationInfo1 = getCachedInfo();
/* 51 */     if (!checkForCompatibleServant((ServantObject)oAInvocationInfo1, paramClass)) {
/* 52 */       return null;
/*    */     }
/*    */ 
/*    */ 
/*    */     
/* 57 */     OAInvocationInfo oAInvocationInfo2 = new OAInvocationInfo(oAInvocationInfo1, paramString);
/* 58 */     this.orb.pushInvocationInfo(oAInvocationInfo2);
/*    */     
/* 60 */     return (ServantObject)oAInvocationInfo2;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void servant_postinvoke(Object paramObject, ServantObject paramServantObject) {
/* 66 */     this.orb.popInvocationInfo();
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/protocol/InfoOnlyServantCacheLocalCRDImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */