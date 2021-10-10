/*     */ package com.sun.corba.se.impl.transport;
/*     */ 
/*     */ import com.sun.corba.se.impl.logging.ORBUtilSystemException;
/*     */ import com.sun.corba.se.impl.protocol.CorbaInvocationInfo;
/*     */ import com.sun.corba.se.pept.transport.ContactInfo;
/*     */ import com.sun.corba.se.pept.transport.ContactInfoList;
/*     */ import com.sun.corba.se.spi.ior.IOR;
/*     */ import com.sun.corba.se.spi.orb.ORB;
/*     */ import com.sun.corba.se.spi.transport.CorbaContactInfo;
/*     */ import com.sun.corba.se.spi.transport.CorbaContactInfoList;
/*     */ import com.sun.corba.se.spi.transport.CorbaContactInfoListIterator;
/*     */ import com.sun.corba.se.spi.transport.IIOPPrimaryToContactInfo;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
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
/*     */ public class CorbaContactInfoListIteratorImpl
/*     */   implements CorbaContactInfoListIterator
/*     */ {
/*     */   protected ORB orb;
/*     */   protected CorbaContactInfoList contactInfoList;
/*     */   protected CorbaContactInfo successContactInfo;
/*     */   protected CorbaContactInfo failureContactInfo;
/*     */   protected RuntimeException failureException;
/*     */   protected Iterator effectiveTargetIORIterator;
/*     */   protected CorbaContactInfo previousContactInfo;
/*     */   protected boolean isAddrDispositionRetry;
/*     */   protected IIOPPrimaryToContactInfo primaryToContactInfo;
/*     */   protected ContactInfo primaryContactInfo;
/*     */   protected List listOfContactInfos;
/*     */   
/*     */   public CorbaContactInfoListIteratorImpl(ORB paramORB, CorbaContactInfoList paramCorbaContactInfoList, ContactInfo paramContactInfo, List paramList) {
/*  77 */     this.orb = paramORB;
/*  78 */     this.contactInfoList = paramCorbaContactInfoList;
/*  79 */     this.primaryContactInfo = paramContactInfo;
/*  80 */     if (paramList != null)
/*     */     {
/*     */       
/*  83 */       this.effectiveTargetIORIterator = paramList.iterator();
/*     */     }
/*     */     
/*  86 */     this.listOfContactInfos = paramList;
/*     */     
/*  88 */     this.previousContactInfo = null;
/*  89 */     this.isAddrDispositionRetry = false;
/*     */     
/*  91 */     this.successContactInfo = null;
/*  92 */     this.failureContactInfo = null;
/*  93 */     this.failureException = null;
/*     */     
/*  95 */     this.primaryToContactInfo = paramORB.getORBData().getIIOPPrimaryToContactInfo();
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
/*     */   public boolean hasNext() {
/*     */     boolean bool;
/* 109 */     if (this.isAddrDispositionRetry) {
/* 110 */       return true;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 115 */     if (this.primaryToContactInfo != null) {
/* 116 */       bool = this.primaryToContactInfo.hasNext(this.primaryContactInfo, (ContactInfo)this.previousContactInfo, this.listOfContactInfos);
/*     */     }
/*     */     else {
/*     */       
/* 120 */       bool = this.effectiveTargetIORIterator.hasNext();
/*     */     } 
/*     */     
/* 123 */     return bool;
/*     */   }
/*     */ 
/*     */   
/*     */   public Object next() {
/* 128 */     if (this.isAddrDispositionRetry) {
/* 129 */       this.isAddrDispositionRetry = false;
/* 130 */       return this.previousContactInfo;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 138 */     if (this.primaryToContactInfo != null) {
/* 139 */       this
/* 140 */         .previousContactInfo = (CorbaContactInfo)this.primaryToContactInfo.next(this.primaryContactInfo, (ContactInfo)this.previousContactInfo, this.listOfContactInfos);
/*     */     }
/*     */     else {
/*     */       
/* 144 */       this
/* 145 */         .previousContactInfo = this.effectiveTargetIORIterator.next();
/*     */     } 
/*     */     
/* 148 */     return this.previousContactInfo;
/*     */   }
/*     */ 
/*     */   
/*     */   public void remove() {
/* 153 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ContactInfoList getContactInfoList() {
/* 163 */     return (ContactInfoList)this.contactInfoList;
/*     */   }
/*     */ 
/*     */   
/*     */   public void reportSuccess(ContactInfo paramContactInfo) {
/* 168 */     this.successContactInfo = (CorbaContactInfo)paramContactInfo;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean reportException(ContactInfo paramContactInfo, RuntimeException paramRuntimeException) {
/* 174 */     this.failureContactInfo = (CorbaContactInfo)paramContactInfo;
/* 175 */     this.failureException = paramRuntimeException;
/* 176 */     if (paramRuntimeException instanceof org.omg.CORBA.COMM_FAILURE) {
/* 177 */       SystemException systemException = (SystemException)paramRuntimeException;
/* 178 */       if (systemException.completed == CompletionStatus.COMPLETED_NO) {
/* 179 */         if (hasNext()) {
/* 180 */           return true;
/*     */         }
/* 182 */         if (this.contactInfoList.getEffectiveTargetIOR() != this.contactInfoList
/* 183 */           .getTargetIOR()) {
/*     */ 
/*     */           
/* 186 */           updateEffectiveTargetIOR(this.contactInfoList.getTargetIOR());
/* 187 */           return true;
/*     */         } 
/*     */       } 
/*     */     } 
/* 191 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public RuntimeException getFailureException() {
/* 196 */     if (this.failureException == null) {
/* 197 */       return 
/* 198 */         (RuntimeException)ORBUtilSystemException.get(this.orb, "rpc.transport")
/*     */         
/* 200 */         .invalidContactInfoListIteratorFailureException();
/*     */     }
/* 202 */     return this.failureException;
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
/*     */   public void reportAddrDispositionRetry(CorbaContactInfo paramCorbaContactInfo, short paramShort) {
/* 214 */     this.previousContactInfo.setAddressingDisposition(paramShort);
/* 215 */     this.isAddrDispositionRetry = true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void reportRedirect(CorbaContactInfo paramCorbaContactInfo, IOR paramIOR) {
/* 221 */     updateEffectiveTargetIOR(paramIOR);
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateEffectiveTargetIOR(IOR paramIOR) {
/* 245 */     this.contactInfoList.setEffectiveTargetIOR(paramIOR);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 254 */     ((CorbaInvocationInfo)this.orb.getInvocationInfo())
/* 255 */       .setContactInfoListIterator(this.contactInfoList.iterator());
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/transport/CorbaContactInfoListIteratorImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */