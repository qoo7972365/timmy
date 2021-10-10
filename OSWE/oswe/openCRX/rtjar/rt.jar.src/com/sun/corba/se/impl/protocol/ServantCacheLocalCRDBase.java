/*    */ package com.sun.corba.se.impl.protocol;
/*    */ 
/*    */ import com.sun.corba.se.impl.logging.POASystemException;
/*    */ import com.sun.corba.se.spi.ior.IOR;
/*    */ import com.sun.corba.se.spi.oa.OADestroyed;
/*    */ import com.sun.corba.se.spi.oa.OAInvocationInfo;
/*    */ import com.sun.corba.se.spi.oa.ObjectAdapter;
/*    */ import com.sun.corba.se.spi.orb.ORB;
/*    */ import com.sun.corba.se.spi.protocol.ForwardException;
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
/*    */ public abstract class ServantCacheLocalCRDBase
/*    */   extends LocalClientRequestDispatcherBase
/*    */ {
/*    */   private OAInvocationInfo cachedInfo;
/*    */   protected POASystemException wrapper;
/*    */   
/*    */   protected ServantCacheLocalCRDBase(ORB paramORB, int paramInt, IOR paramIOR) {
/* 59 */     super(paramORB, paramInt, paramIOR);
/* 60 */     this.wrapper = POASystemException.get(paramORB, "rpc.protocol");
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected synchronized OAInvocationInfo getCachedInfo() {
/* 66 */     if (!this.servantIsLocal) {
/* 67 */       throw this.wrapper.servantMustBeLocal();
/*    */     }
/* 69 */     if (this.cachedInfo == null) {
/* 70 */       ObjectAdapter objectAdapter = this.oaf.find(this.oaid);
/* 71 */       this.cachedInfo = objectAdapter.makeInvocationInfo(this.objectId);
/*    */ 
/*    */       
/* 74 */       this.orb.pushInvocationInfo(this.cachedInfo);
/*    */       
/*    */       try {
/* 77 */         objectAdapter.enter();
/* 78 */         objectAdapter.getInvocationServant(this.cachedInfo);
/* 79 */       } catch (ForwardException forwardException) {
/* 80 */         throw this.wrapper.illegalForwardRequest(forwardException);
/* 81 */       } catch (OADestroyed oADestroyed) {
/*    */ 
/*    */         
/* 84 */         throw this.wrapper.adapterDestroyed(oADestroyed);
/*    */       } finally {
/* 86 */         objectAdapter.returnServant();
/* 87 */         objectAdapter.exit();
/* 88 */         this.orb.popInvocationInfo();
/*    */       } 
/*    */     } 
/*    */     
/* 92 */     return this.cachedInfo;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/protocol/ServantCacheLocalCRDBase.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */