/*     */ package com.sun.corba.se.impl.legacy.connection;
/*     */ 
/*     */ import com.sun.corba.se.impl.transport.CorbaContactInfoListIteratorImpl;
/*     */ import com.sun.corba.se.impl.transport.SharedCDRContactInfoImpl;
/*     */ import com.sun.corba.se.pept.transport.ContactInfo;
/*     */ import com.sun.corba.se.spi.legacy.connection.GetEndPointInfoAgainException;
/*     */ import com.sun.corba.se.spi.orb.ORB;
/*     */ import com.sun.corba.se.spi.transport.CorbaContactInfo;
/*     */ import com.sun.corba.se.spi.transport.CorbaContactInfoList;
/*     */ import com.sun.corba.se.spi.transport.SocketInfo;
/*     */ import org.omg.CORBA.CompletionStatus;
/*     */ import org.omg.CORBA.SystemException;
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
/*     */ public class SocketFactoryContactInfoListIteratorImpl
/*     */   extends CorbaContactInfoListIteratorImpl
/*     */ {
/*     */   private SocketInfo socketInfoCookie;
/*     */   
/*     */   public SocketFactoryContactInfoListIteratorImpl(ORB paramORB, CorbaContactInfoList paramCorbaContactInfoList) {
/*  51 */     super(paramORB, paramCorbaContactInfoList, null, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasNext() {
/*  61 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public Object next() {
/*  66 */     if (this.contactInfoList.getEffectiveTargetIOR().getProfile().isLocal()) {
/*  67 */       return new SharedCDRContactInfoImpl(this.orb, this.contactInfoList, this.contactInfoList
/*     */           
/*  69 */           .getEffectiveTargetIOR(), this.orb
/*  70 */           .getORBData().getGIOPAddressDisposition());
/*     */     }
/*     */ 
/*     */     
/*  74 */     return new SocketFactoryContactInfoImpl(this.orb, this.contactInfoList, this.contactInfoList
/*     */         
/*  76 */         .getEffectiveTargetIOR(), this.orb
/*  77 */         .getORBData().getGIOPAddressDisposition(), this.socketInfoCookie);
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
/*     */   public boolean reportException(ContactInfo paramContactInfo, RuntimeException paramRuntimeException) {
/*  90 */     this.failureContactInfo = (CorbaContactInfo)paramContactInfo;
/*  91 */     this.failureException = paramRuntimeException;
/*  92 */     if (paramRuntimeException instanceof org.omg.CORBA.COMM_FAILURE) {
/*     */       
/*  94 */       if (paramRuntimeException.getCause() instanceof GetEndPointInfoAgainException) {
/*  95 */         this
/*     */           
/*  97 */           .socketInfoCookie = ((GetEndPointInfoAgainException)paramRuntimeException.getCause()).getEndPointInfo();
/*  98 */         return true;
/*     */       } 
/*     */       
/* 101 */       SystemException systemException = (SystemException)paramRuntimeException;
/* 102 */       if (systemException.completed == CompletionStatus.COMPLETED_NO && 
/* 103 */         this.contactInfoList.getEffectiveTargetIOR() != this.contactInfoList
/* 104 */         .getTargetIOR()) {
/*     */ 
/*     */         
/* 107 */         this.contactInfoList.setEffectiveTargetIOR(this.contactInfoList
/* 108 */             .getTargetIOR());
/* 109 */         return true;
/*     */       } 
/*     */     } 
/*     */     
/* 113 */     return false;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/legacy/connection/SocketFactoryContactInfoListIteratorImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */