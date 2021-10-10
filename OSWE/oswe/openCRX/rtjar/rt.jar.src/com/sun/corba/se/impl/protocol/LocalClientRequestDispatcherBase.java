/*     */ package com.sun.corba.se.impl.protocol;
/*     */ 
/*     */ import com.sun.corba.se.spi.ior.IOR;
/*     */ import com.sun.corba.se.spi.ior.ObjectAdapterId;
/*     */ import com.sun.corba.se.spi.ior.ObjectId;
/*     */ import com.sun.corba.se.spi.ior.ObjectKeyTemplate;
/*     */ import com.sun.corba.se.spi.ior.iiop.IIOPProfile;
/*     */ import com.sun.corba.se.spi.oa.ObjectAdapterFactory;
/*     */ import com.sun.corba.se.spi.orb.ORB;
/*     */ import com.sun.corba.se.spi.protocol.LocalClientRequestDispatcher;
/*     */ import com.sun.corba.se.spi.protocol.RequestDispatcherRegistry;
/*     */ import org.omg.CORBA.Object;
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
/*     */ public abstract class LocalClientRequestDispatcherBase
/*     */   implements LocalClientRequestDispatcher
/*     */ {
/*     */   protected ORB orb;
/*     */   int scid;
/*     */   protected boolean servantIsLocal;
/*     */   protected ObjectAdapterFactory oaf;
/*     */   protected ObjectAdapterId oaid;
/*     */   protected byte[] objectId;
/*     */   
/*  55 */   private static final ThreadLocal isNextCallValid = new ThreadLocal() {
/*     */       protected synchronized Object initialValue() {
/*  57 */         return Boolean.TRUE;
/*     */       }
/*     */     };
/*     */ 
/*     */   
/*     */   protected LocalClientRequestDispatcherBase(ORB paramORB, int paramInt, IOR paramIOR) {
/*  63 */     this.orb = paramORB;
/*     */     
/*  65 */     IIOPProfile iIOPProfile = paramIOR.getProfile();
/*  66 */     this
/*  67 */       .servantIsLocal = (paramORB.getORBData().isLocalOptimizationAllowed() && iIOPProfile.isLocal());
/*     */     
/*  69 */     ObjectKeyTemplate objectKeyTemplate = iIOPProfile.getObjectKeyTemplate();
/*  70 */     this.scid = objectKeyTemplate.getSubcontractId();
/*  71 */     RequestDispatcherRegistry requestDispatcherRegistry = paramORB.getRequestDispatcherRegistry();
/*  72 */     this.oaf = requestDispatcherRegistry.getObjectAdapterFactory(paramInt);
/*  73 */     this.oaid = objectKeyTemplate.getObjectAdapterId();
/*  74 */     ObjectId objectId = iIOPProfile.getObjectId();
/*  75 */     this.objectId = objectId.getId();
/*     */   }
/*     */ 
/*     */   
/*     */   public byte[] getObjectId() {
/*  80 */     return this.objectId;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean is_local(Object paramObject) {
/*  85 */     return false;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean useLocalInvocation(Object paramObject) {
/* 105 */     if (isNextCallValid.get() == Boolean.TRUE) {
/* 106 */       return this.servantIsLocal;
/*     */     }
/* 108 */     isNextCallValid.set(Boolean.TRUE);
/*     */     
/* 110 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean checkForCompatibleServant(ServantObject paramServantObject, Class paramClass) {
/* 120 */     if (paramServantObject == null) {
/* 121 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 126 */     if (!paramClass.isInstance(paramServantObject.servant)) {
/* 127 */       isNextCallValid.set(Boolean.FALSE);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 132 */       return false;
/*     */     } 
/*     */     
/* 135 */     return true;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/protocol/LocalClientRequestDispatcherBase.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */