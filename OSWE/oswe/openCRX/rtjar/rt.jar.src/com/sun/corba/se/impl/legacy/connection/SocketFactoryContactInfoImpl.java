/*     */ package com.sun.corba.se.impl.legacy.connection;
/*     */ 
/*     */ import com.sun.corba.se.impl.logging.ORBUtilSystemException;
/*     */ import com.sun.corba.se.impl.transport.SocketOrChannelContactInfoImpl;
/*     */ import com.sun.corba.se.pept.transport.Connection;
/*     */ import com.sun.corba.se.spi.ior.IOR;
/*     */ import com.sun.corba.se.spi.orb.ORB;
/*     */ import com.sun.corba.se.spi.transport.CorbaContactInfo;
/*     */ import com.sun.corba.se.spi.transport.CorbaContactInfoList;
/*     */ import com.sun.corba.se.spi.transport.SocketInfo;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SocketFactoryContactInfoImpl
/*     */   extends SocketOrChannelContactInfoImpl
/*     */ {
/*     */   protected ORBUtilSystemException wrapper;
/*     */   protected SocketInfo socketInfo;
/*     */   
/*     */   public SocketFactoryContactInfoImpl() {}
/*     */   
/*     */   public SocketFactoryContactInfoImpl(ORB paramORB, CorbaContactInfoList paramCorbaContactInfoList, IOR paramIOR, short paramShort, SocketInfo paramSocketInfo) {
/*  65 */     super(paramORB, paramCorbaContactInfoList);
/*  66 */     this.effectiveTargetIOR = paramIOR;
/*  67 */     this.addressingDisposition = paramShort;
/*     */     
/*  69 */     this.wrapper = ORBUtilSystemException.get(paramORB, "rpc.transport");
/*     */ 
/*     */     
/*  72 */     this
/*     */       
/*  74 */       .socketInfo = paramORB.getORBData().getLegacySocketFactory().getEndPointInfo((ORB)paramORB, paramIOR, paramSocketInfo);
/*     */     
/*  76 */     this.socketType = this.socketInfo.getType();
/*  77 */     this.hostname = this.socketInfo.getHost();
/*  78 */     this.port = this.socketInfo.getPort();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Connection createConnection() {
/*  88 */     return (Connection)new SocketFactoryConnectionImpl(this.orb, (CorbaContactInfo)this, this.orb
/*     */ 
/*     */         
/*  91 */         .getORBData().connectionSocketUseSelectThreadToWait(), this.orb
/*  92 */         .getORBData().connectionSocketUseWorkerThreadForEvent());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 103 */     return "SocketFactoryContactInfoImpl[" + this.socketType + " " + this.hostname + " " + this.port + "]";
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/legacy/connection/SocketFactoryContactInfoImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */