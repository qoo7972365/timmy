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
/*    */ public class MinimalServantCacheLocalCRDImpl
/*    */   extends ServantCacheLocalCRDBase
/*    */ {
/*    */   public MinimalServantCacheLocalCRDImpl(ORB paramORB, int paramInt, IOR paramIOR) {
/* 44 */     super(paramORB, paramInt, paramIOR);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public ServantObject servant_preinvoke(Object paramObject, String paramString, Class paramClass) {
/* 50 */     OAInvocationInfo oAInvocationInfo = getCachedInfo();
/* 51 */     if (checkForCompatibleServant((ServantObject)oAInvocationInfo, paramClass)) {
/* 52 */       return (ServantObject)oAInvocationInfo;
/*    */     }
/* 54 */     return null;
/*    */   }
/*    */   
/*    */   public void servant_postinvoke(Object paramObject, ServantObject paramServantObject) {}
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/protocol/MinimalServantCacheLocalCRDImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */