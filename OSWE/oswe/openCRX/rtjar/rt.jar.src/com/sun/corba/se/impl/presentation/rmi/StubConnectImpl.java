/*     */ package com.sun.corba.se.impl.presentation.rmi;
/*     */ 
/*     */ import com.sun.corba.se.impl.corba.CORBAObjectImpl;
/*     */ import com.sun.corba.se.impl.ior.StubIORImpl;
/*     */ import com.sun.corba.se.impl.logging.UtilSystemException;
/*     */ import com.sun.corba.se.impl.util.Utility;
/*     */ import com.sun.corba.se.spi.presentation.rmi.StubAdapter;
/*     */ import java.rmi.RemoteException;
/*     */ import javax.rmi.CORBA.Tie;
/*     */ import org.omg.CORBA.BAD_INV_ORDER;
/*     */ import org.omg.CORBA.BAD_OPERATION;
/*     */ import org.omg.CORBA.ORB;
/*     */ import org.omg.CORBA.Object;
/*     */ import org.omg.CORBA.SystemException;
/*     */ import org.omg.CORBA.portable.Delegate;
/*     */ import org.omg.CORBA.portable.ObjectImpl;
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
/*     */ public abstract class StubConnectImpl
/*     */ {
/*  54 */   static UtilSystemException wrapper = UtilSystemException.get("rmiiiop");
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
/*     */   public static StubIORImpl connect(StubIORImpl paramStubIORImpl, Object paramObject, ObjectImpl paramObjectImpl, ORB paramORB) throws RemoteException {
/*  66 */     Delegate delegate = null;
/*     */     
/*     */     try {
/*     */       try {
/*  70 */         delegate = StubAdapter.getDelegate(paramObjectImpl);
/*     */         
/*  72 */         if (delegate.orb((Object)paramObjectImpl) != paramORB)
/*  73 */           throw wrapper.connectWrongOrb(); 
/*  74 */       } catch (BAD_OPERATION bAD_OPERATION) {
/*  75 */         if (paramStubIORImpl == null) {
/*     */           
/*  77 */           Tie tie = Utility.getAndForgetTie(paramObject);
/*  78 */           if (tie == null) {
/*  79 */             throw wrapper.connectNoTie();
/*     */           }
/*     */ 
/*     */           
/*  83 */           ORB oRB = paramORB;
/*     */           try {
/*  85 */             oRB = tie.orb();
/*  86 */           } catch (BAD_OPERATION bAD_OPERATION1) {
/*     */             
/*  88 */             tie.orb(paramORB);
/*  89 */           } catch (BAD_INV_ORDER bAD_INV_ORDER) {
/*     */             
/*  91 */             tie.orb(paramORB);
/*     */           } 
/*     */           
/*  94 */           if (oRB != paramORB) {
/*  95 */             throw wrapper.connectTieWrongOrb();
/*     */           }
/*     */           
/*  98 */           delegate = StubAdapter.getDelegate(tie);
/*  99 */           CORBAObjectImpl cORBAObjectImpl = new CORBAObjectImpl();
/* 100 */           cORBAObjectImpl._set_delegate(delegate);
/* 101 */           paramStubIORImpl = new StubIORImpl((Object)cORBAObjectImpl);
/*     */         }
/*     */         else {
/*     */           
/* 105 */           delegate = paramStubIORImpl.getDelegate(paramORB);
/*     */         } 
/*     */         
/* 108 */         StubAdapter.setDelegate(paramObjectImpl, delegate);
/*     */       } 
/* 110 */     } catch (SystemException systemException) {
/* 111 */       throw new RemoteException("CORBA SystemException", systemException);
/*     */     } 
/*     */     
/* 114 */     return paramStubIORImpl;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/presentation/rmi/StubConnectImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */