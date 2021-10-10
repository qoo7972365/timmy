/*     */ package com.sun.corba.se.impl.transport;
/*     */ 
/*     */ import com.sun.corba.se.impl.protocol.NotLocalLocalCRDImpl;
/*     */ import com.sun.corba.se.pept.transport.ContactInfo;
/*     */ import com.sun.corba.se.spi.ior.IOR;
/*     */ import com.sun.corba.se.spi.ior.iiop.IIOPProfile;
/*     */ import com.sun.corba.se.spi.ior.iiop.IIOPProfileTemplate;
/*     */ import com.sun.corba.se.spi.orb.ORB;
/*     */ import com.sun.corba.se.spi.protocol.LocalClientRequestDispatcher;
/*     */ import com.sun.corba.se.spi.protocol.LocalClientRequestDispatcherFactory;
/*     */ import com.sun.corba.se.spi.transport.CorbaContactInfoList;
/*     */ import com.sun.corba.se.spi.transport.SocketInfo;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
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
/*     */ public class CorbaContactInfoListImpl
/*     */   implements CorbaContactInfoList
/*     */ {
/*     */   protected ORB orb;
/*     */   protected LocalClientRequestDispatcher LocalClientRequestDispatcher;
/*     */   protected IOR targetIOR;
/*     */   protected IOR effectiveTargetIOR;
/*     */   protected List effectiveTargetIORContactInfoList;
/*     */   protected ContactInfo primaryContactInfo;
/*     */   
/*     */   public CorbaContactInfoListImpl(ORB paramORB) {
/*  64 */     this.orb = paramORB;
/*     */   }
/*     */ 
/*     */   
/*     */   public CorbaContactInfoListImpl(ORB paramORB, IOR paramIOR) {
/*  69 */     this(paramORB);
/*  70 */     setTargetIOR(paramIOR);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized Iterator iterator() {
/*  80 */     createContactInfoList();
/*  81 */     return (Iterator)new CorbaContactInfoListIteratorImpl(this.orb, this, this.primaryContactInfo, this.effectiveTargetIORContactInfoList);
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
/*     */   public synchronized void setTargetIOR(IOR paramIOR) {
/*  93 */     this.targetIOR = paramIOR;
/*  94 */     setEffectiveTargetIOR(paramIOR);
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized IOR getTargetIOR() {
/*  99 */     return this.targetIOR;
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized void setEffectiveTargetIOR(IOR paramIOR) {
/* 104 */     this.effectiveTargetIOR = paramIOR;
/* 105 */     this.effectiveTargetIORContactInfoList = null;
/* 106 */     if (this.primaryContactInfo != null && this.orb
/* 107 */       .getORBData().getIIOPPrimaryToContactInfo() != null)
/*     */     {
/* 109 */       this.orb.getORBData().getIIOPPrimaryToContactInfo()
/* 110 */         .reset(this.primaryContactInfo);
/*     */     }
/* 112 */     this.primaryContactInfo = null;
/* 113 */     setLocalSubcontract();
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized IOR getEffectiveTargetIOR() {
/* 118 */     return this.effectiveTargetIOR;
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized LocalClientRequestDispatcher getLocalClientRequestDispatcher() {
/* 123 */     return this.LocalClientRequestDispatcher;
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
/*     */   public synchronized int hashCode() {
/* 140 */     return this.targetIOR.hashCode();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void createContactInfoList() {
/* 150 */     if (this.effectiveTargetIORContactInfoList != null) {
/*     */       return;
/*     */     }
/*     */     
/* 154 */     this.effectiveTargetIORContactInfoList = new ArrayList();
/*     */     
/* 156 */     IIOPProfile iIOPProfile = this.effectiveTargetIOR.getProfile();
/*     */ 
/*     */     
/* 159 */     String str = ((IIOPProfileTemplate)iIOPProfile.getTaggedProfileTemplate()).getPrimaryAddress().getHost().toLowerCase();
/*     */ 
/*     */     
/* 162 */     int i = ((IIOPProfileTemplate)iIOPProfile.getTaggedProfileTemplate()).getPrimaryAddress().getPort();
/*     */     
/* 164 */     this
/* 165 */       .primaryContactInfo = createContactInfo("IIOP_CLEAR_TEXT", str, i);
/*     */     
/* 167 */     if (iIOPProfile.isLocal()) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 175 */       SharedCDRContactInfoImpl sharedCDRContactInfoImpl = new SharedCDRContactInfoImpl(this.orb, this, this.effectiveTargetIOR, this.orb.getORBData().getGIOPAddressDisposition());
/* 176 */       this.effectiveTargetIORContactInfoList.add(sharedCDRContactInfoImpl);
/*     */     } else {
/* 178 */       addRemoteContactInfos(this.effectiveTargetIOR, this.effectiveTargetIORContactInfoList);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void addRemoteContactInfos(IOR paramIOR, List<ContactInfo> paramList) {
/* 189 */     List list = this.orb.getORBData().getIORToSocketInfo().getSocketInfo(paramIOR);
/* 190 */     Iterator<SocketInfo> iterator = list.iterator();
/* 191 */     while (iterator.hasNext()) {
/* 192 */       SocketInfo socketInfo = iterator.next();
/* 193 */       String str1 = socketInfo.getType();
/* 194 */       String str2 = socketInfo.getHost().toLowerCase();
/* 195 */       int i = socketInfo.getPort();
/* 196 */       ContactInfo contactInfo = createContactInfo(str1, str2, i);
/* 197 */       paramList.add(contactInfo);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected ContactInfo createContactInfo(String paramString1, String paramString2, int paramInt) {
/* 204 */     return (ContactInfo)new SocketOrChannelContactInfoImpl(this.orb, this, this.effectiveTargetIOR, this.orb
/*     */ 
/*     */ 
/*     */         
/* 208 */         .getORBData().getGIOPAddressDisposition(), paramString1, paramString2, paramInt);
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
/*     */   protected void setLocalSubcontract() {
/* 222 */     if (!this.effectiveTargetIOR.getProfile().isLocal()) {
/* 223 */       this.LocalClientRequestDispatcher = (LocalClientRequestDispatcher)new NotLocalLocalCRDImpl();
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       return;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 234 */     int i = this.effectiveTargetIOR.getProfile().getObjectKeyTemplate().getSubcontractId();
/* 235 */     LocalClientRequestDispatcherFactory localClientRequestDispatcherFactory = this.orb.getRequestDispatcherRegistry().getLocalClientRequestDispatcherFactory(i);
/* 236 */     this.LocalClientRequestDispatcher = localClientRequestDispatcherFactory.create(i, this.effectiveTargetIOR);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/transport/CorbaContactInfoListImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */