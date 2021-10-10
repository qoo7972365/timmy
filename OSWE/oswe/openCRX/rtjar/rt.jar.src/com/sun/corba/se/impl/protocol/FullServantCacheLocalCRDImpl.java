/*    */ package com.sun.corba.se.impl.protocol;
/*    */ 
/*    */ import com.sun.corba.se.spi.ior.IOR;
/*    */ import com.sun.corba.se.spi.oa.OADestroyed;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class FullServantCacheLocalCRDImpl
/*    */   extends ServantCacheLocalCRDBase
/*    */ {
/*    */   public FullServantCacheLocalCRDImpl(ORB paramORB, int paramInt, IOR paramIOR) {
/* 49 */     super(paramORB, paramInt, paramIOR);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public ServantObject servant_preinvoke(Object paramObject, String paramString, Class paramClass) {
/* 55 */     OAInvocationInfo oAInvocationInfo1 = getCachedInfo();
/* 56 */     if (!checkForCompatibleServant((ServantObject)oAInvocationInfo1, paramClass)) {
/* 57 */       return null;
/*    */     }
/*    */ 
/*    */ 
/*    */     
/* 62 */     OAInvocationInfo oAInvocationInfo2 = new OAInvocationInfo(oAInvocationInfo1, paramString);
/* 63 */     this.orb.pushInvocationInfo(oAInvocationInfo2);
/*    */     
/*    */     try {
/* 66 */       oAInvocationInfo2.oa().enter();
/* 67 */     } catch (OADestroyed oADestroyed) {
/* 68 */       throw this.wrapper.preinvokePoaDestroyed(oADestroyed);
/*    */     } 
/*    */     
/* 71 */     return (ServantObject)oAInvocationInfo2;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void servant_postinvoke(Object paramObject, ServantObject paramServantObject) {
/* 77 */     OAInvocationInfo oAInvocationInfo = getCachedInfo();
/* 78 */     oAInvocationInfo.oa().exit();
/* 79 */     this.orb.popInvocationInfo();
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/protocol/FullServantCacheLocalCRDImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */