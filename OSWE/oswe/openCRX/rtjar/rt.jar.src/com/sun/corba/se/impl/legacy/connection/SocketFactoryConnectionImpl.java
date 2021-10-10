/*     */ package com.sun.corba.se.impl.legacy.connection;
/*     */ 
/*     */ import com.sun.corba.se.impl.orbutil.ORBUtility;
/*     */ import com.sun.corba.se.impl.transport.SocketOrChannelConnectionImpl;
/*     */ import com.sun.corba.se.spi.legacy.connection.GetEndPointInfoAgainException;
/*     */ import com.sun.corba.se.spi.orb.ORB;
/*     */ import com.sun.corba.se.spi.transport.CorbaContactInfo;
/*     */ import com.sun.corba.se.spi.transport.SocketInfo;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SocketFactoryConnectionImpl
/*     */   extends SocketOrChannelConnectionImpl
/*     */ {
/*     */   public SocketFactoryConnectionImpl(ORB paramORB, CorbaContactInfo paramCorbaContactInfo, boolean paramBoolean1, boolean paramBoolean2) {
/*  62 */     super(paramORB, paramBoolean1, paramBoolean2);
/*     */ 
/*     */ 
/*     */     
/*  66 */     this.contactInfo = paramCorbaContactInfo;
/*     */     
/*  68 */     boolean bool = !paramBoolean1 ? true : false;
/*  69 */     SocketInfo socketInfo = ((SocketFactoryContactInfoImpl)paramCorbaContactInfo).socketInfo;
/*     */ 
/*     */     
/*     */     try {
/*  73 */       this
/*  74 */         .socket = paramORB.getORBData().getLegacySocketFactory().createSocket(socketInfo);
/*  75 */       this.socketChannel = this.socket.getChannel();
/*  76 */       if (this.socketChannel != null) {
/*  77 */         this.socketChannel.configureBlocking(bool);
/*     */       }
/*     */       else {
/*     */         
/*  81 */         setUseSelectThreadToWait(false);
/*     */       } 
/*  83 */       if (paramORB.transportDebugFlag) {
/*  84 */         dprint(".initialize: connection created: " + this.socket);
/*     */       }
/*  86 */     } catch (GetEndPointInfoAgainException getEndPointInfoAgainException) {
/*  87 */       throw this.wrapper.connectFailure(getEndPointInfoAgainException, socketInfo
/*  88 */           .getType(), socketInfo.getHost(), 
/*  89 */           Integer.toString(socketInfo.getPort()));
/*  90 */     } catch (Exception exception) {
/*  91 */       throw this.wrapper.connectFailure(exception, socketInfo
/*  92 */           .getType(), socketInfo.getHost(), 
/*  93 */           Integer.toString(socketInfo.getPort()));
/*     */     } 
/*  95 */     this.state = 1;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 100 */     synchronized (this.stateEvent) {
/* 101 */       return "SocketFactoryConnectionImpl[ " + ((this.socketChannel == null) ? this.socket
/*     */ 
/*     */         
/* 104 */         .toString() : this.socketChannel.toString()) + " " + 
/* 105 */         getStateString(this.state) + " " + 
/* 106 */         shouldUseSelectThreadToWait() + " " + 
/* 107 */         shouldUseWorkerThreadForEvent() + "]";
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void dprint(String paramString) {
/* 115 */     ORBUtility.dprint("SocketFactoryConnectionImpl", paramString);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/legacy/connection/SocketFactoryConnectionImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */