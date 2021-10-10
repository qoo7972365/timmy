/*     */ package com.sun.corba.se.impl.oa.toa;
/*     */ 
/*     */ import com.sun.corba.se.impl.ior.ObjectKeyTemplateBase;
/*     */ import com.sun.corba.se.impl.javax.rmi.CORBA.Util;
/*     */ import com.sun.corba.se.impl.logging.ORBUtilSystemException;
/*     */ import com.sun.corba.se.spi.ior.ObjectAdapterId;
/*     */ import com.sun.corba.se.spi.oa.ObjectAdapter;
/*     */ import com.sun.corba.se.spi.oa.ObjectAdapterFactory;
/*     */ import com.sun.corba.se.spi.orb.ORB;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import org.omg.CORBA.ORB;
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
/*     */ public class TOAFactory
/*     */   implements ObjectAdapterFactory
/*     */ {
/*     */   private ORB orb;
/*     */   private ORBUtilSystemException wrapper;
/*     */   private TOAImpl toa;
/*     */   private Map codebaseToTOA;
/*     */   private TransientObjectManager tom;
/*     */   
/*     */   public ObjectAdapter find(ObjectAdapterId paramObjectAdapterId) {
/*  62 */     if (paramObjectAdapterId.equals(ObjectKeyTemplateBase.JIDL_OAID))
/*     */     {
/*     */       
/*  65 */       return getTOA();
/*     */     }
/*  67 */     throw this.wrapper.badToaOaid();
/*     */   }
/*     */ 
/*     */   
/*     */   public void init(ORB paramORB) {
/*  72 */     this.orb = paramORB;
/*  73 */     this.wrapper = ORBUtilSystemException.get(paramORB, "oa.lifecycle");
/*     */     
/*  75 */     this.tom = new TransientObjectManager(paramORB);
/*  76 */     this.codebaseToTOA = new HashMap<>();
/*     */   }
/*     */ 
/*     */   
/*     */   public void shutdown(boolean paramBoolean) {
/*  81 */     if (Util.isInstanceDefined()) {
/*  82 */       Util.getInstance().unregisterTargetsForORB((ORB)this.orb);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized TOA getTOA(String paramString) {
/*  88 */     TOA tOA = (TOA)this.codebaseToTOA.get(paramString);
/*  89 */     if (tOA == null) {
/*  90 */       tOA = new TOAImpl(this.orb, this.tom, paramString);
/*     */       
/*  92 */       this.codebaseToTOA.put(paramString, tOA);
/*     */     } 
/*     */     
/*  95 */     return tOA;
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized TOA getTOA() {
/* 100 */     if (this.toa == null)
/*     */     {
/*     */ 
/*     */       
/* 104 */       this.toa = new TOAImpl(this.orb, this.tom, null);
/*     */     }
/* 106 */     return this.toa;
/*     */   }
/*     */ 
/*     */   
/*     */   public ORB getORB() {
/* 111 */     return this.orb;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/oa/toa/TOAFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */