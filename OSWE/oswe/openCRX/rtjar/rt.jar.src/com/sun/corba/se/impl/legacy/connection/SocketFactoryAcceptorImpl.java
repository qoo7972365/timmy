/*    */ package com.sun.corba.se.impl.legacy.connection;
/*    */ 
/*    */ import com.sun.corba.se.impl.orbutil.ORBUtility;
/*    */ import com.sun.corba.se.impl.transport.SocketOrChannelAcceptorImpl;
/*    */ import com.sun.corba.se.spi.orb.ORB;
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
/*    */ public class SocketFactoryAcceptorImpl
/*    */   extends SocketOrChannelAcceptorImpl
/*    */ {
/*    */   public SocketFactoryAcceptorImpl(ORB paramORB, int paramInt, String paramString1, String paramString2) {
/* 51 */     super(paramORB, paramInt, paramString1, paramString2);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean initialize() {
/* 61 */     if (this.initialized) {
/* 62 */       return false;
/*    */     }
/* 64 */     if (this.orb.transportDebugFlag) {
/* 65 */       dprint("initialize: " + this);
/*    */     }
/*    */     try {
/* 68 */       this
/* 69 */         .serverSocket = this.orb.getORBData().getLegacySocketFactory().createServerSocket(this.type, this.port);
/* 70 */       internalInitialize();
/* 71 */     } catch (Throwable throwable) {
/* 72 */       throw this.wrapper.createListenerFailed(throwable, Integer.toString(this.port));
/*    */     } 
/* 74 */     this.initialized = true;
/* 75 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected String toStringName() {
/* 85 */     return "SocketFactoryAcceptorImpl";
/*    */   }
/*    */ 
/*    */   
/*    */   protected void dprint(String paramString) {
/* 90 */     ORBUtility.dprint(toStringName(), paramString);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/legacy/connection/SocketFactoryAcceptorImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */