/*     */ package com.sun.corba.se.impl.protocol;
/*     */ 
/*     */ import com.sun.corba.se.impl.logging.ORBUtilSystemException;
/*     */ import com.sun.corba.se.impl.logging.POASystemException;
/*     */ import com.sun.corba.se.spi.ior.IOR;
/*     */ import com.sun.corba.se.spi.oa.OADestroyed;
/*     */ import com.sun.corba.se.spi.oa.OAInvocationInfo;
/*     */ import com.sun.corba.se.spi.oa.ObjectAdapter;
/*     */ import com.sun.corba.se.spi.orb.ORB;
/*     */ import com.sun.corba.se.spi.protocol.ForwardException;
/*     */ import org.omg.CORBA.Object;
/*     */ import org.omg.CORBA.SystemException;
/*     */ import org.omg.CORBA.portable.ServantObject;
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
/*     */ public class POALocalCRDImpl
/*     */   extends LocalClientRequestDispatcherBase
/*     */ {
/*     */   private ORBUtilSystemException wrapper;
/*     */   private POASystemException poaWrapper;
/*     */   
/*     */   public POALocalCRDImpl(ORB paramORB, int paramInt, IOR paramIOR) {
/*  61 */     super(paramORB, paramInt, paramIOR);
/*  62 */     this.wrapper = ORBUtilSystemException.get(paramORB, "rpc.protocol");
/*     */     
/*  64 */     this.poaWrapper = POASystemException.get(paramORB, "rpc.protocol");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private OAInvocationInfo servantEnter(ObjectAdapter paramObjectAdapter) throws OADestroyed {
/*  70 */     paramObjectAdapter.enter();
/*     */     
/*  72 */     OAInvocationInfo oAInvocationInfo = paramObjectAdapter.makeInvocationInfo(this.objectId);
/*  73 */     this.orb.pushInvocationInfo(oAInvocationInfo);
/*     */     
/*  75 */     return oAInvocationInfo;
/*     */   }
/*     */ 
/*     */   
/*     */   private void servantExit(ObjectAdapter paramObjectAdapter) {
/*     */     try {
/*  81 */       paramObjectAdapter.returnServant();
/*     */     } finally {
/*  83 */       paramObjectAdapter.exit();
/*  84 */       this.orb.popInvocationInfo();
/*     */     } 
/*     */   }
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
/*     */   public ServantObject servant_preinvoke(Object paramObject, String paramString, Class paramClass) {
/*  99 */     ObjectAdapter objectAdapter = this.oaf.find(this.oaid);
/* 100 */     OAInvocationInfo oAInvocationInfo = null;
/*     */     
/*     */     try {
/* 103 */       oAInvocationInfo = servantEnter(objectAdapter);
/* 104 */       oAInvocationInfo.setOperation(paramString);
/* 105 */     } catch (OADestroyed oADestroyed) {
/*     */ 
/*     */       
/* 108 */       return servant_preinvoke(paramObject, paramString, paramClass);
/*     */     } 
/*     */ 
/*     */     
/*     */     try {
/* 113 */       objectAdapter.getInvocationServant(oAInvocationInfo);
/* 114 */       if (!checkForCompatibleServant((ServantObject)oAInvocationInfo, paramClass))
/* 115 */         return null; 
/* 116 */     } catch (Throwable throwable) {
/*     */ 
/*     */       
/* 119 */       servantExit(objectAdapter);
/* 120 */       throw throwable;
/*     */     }
/* 122 */     catch (ForwardException forwardException) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 129 */       RuntimeException runtimeException = new RuntimeException("deal with this.");
/* 130 */       runtimeException.initCause((Throwable)forwardException);
/* 131 */       throw runtimeException;
/* 132 */     } catch (ThreadDeath threadDeath) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 139 */       throw this.wrapper.runtimeexception(threadDeath);
/* 140 */     } catch (Throwable throwable) {
/* 141 */       if (throwable instanceof SystemException) {
/* 142 */         throw (SystemException)throwable;
/*     */       }
/* 144 */       throw this.poaWrapper.localServantLookup(throwable);
/*     */     } 
/*     */     
/* 147 */     if (!checkForCompatibleServant((ServantObject)oAInvocationInfo, paramClass)) {
/* 148 */       servantExit(objectAdapter);
/* 149 */       return null;
/*     */     } 
/*     */     
/* 152 */     return (ServantObject)oAInvocationInfo;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void servant_postinvoke(Object paramObject, ServantObject paramServantObject) {
/* 158 */     ObjectAdapter objectAdapter = this.orb.peekInvocationInfo().oa();
/* 159 */     servantExit(objectAdapter);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/protocol/POALocalCRDImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */