/*     */ package com.sun.corba.se.impl.transport;
/*     */ 
/*     */ import com.sun.corba.se.impl.orbutil.ORBUtility;
/*     */ import com.sun.corba.se.pept.transport.Acceptor;
/*     */ import com.sun.corba.se.pept.transport.ListenerThread;
/*     */ import com.sun.corba.se.pept.transport.Selector;
/*     */ import com.sun.corba.se.spi.orb.ORB;
/*     */ import com.sun.corba.se.spi.orbutil.threadpool.Work;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ListenerThreadImpl
/*     */   implements ListenerThread, Work
/*     */ {
/*     */   private ORB orb;
/*     */   private Acceptor acceptor;
/*     */   private Selector selector;
/*     */   private boolean keepRunning;
/*     */   private long enqueueTime;
/*     */   
/*     */   public ListenerThreadImpl(ORB paramORB, Acceptor paramAcceptor, Selector paramSelector) {
/*  53 */     this.orb = paramORB;
/*  54 */     this.acceptor = paramAcceptor;
/*  55 */     this.selector = paramSelector;
/*  56 */     this.keepRunning = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Acceptor getAcceptor() {
/*  66 */     return this.acceptor;
/*     */   }
/*     */ 
/*     */   
/*     */   public void close() {
/*  71 */     if (this.orb.transportDebugFlag) {
/*  72 */       dprint(".close: " + this.acceptor);
/*     */     }
/*     */     
/*  75 */     this.keepRunning = false;
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
/*     */   public void doWork() {
/*     */     try {
/*  88 */       if (this.orb.transportDebugFlag) {
/*  89 */         dprint(".doWork: Start ListenerThread: " + this.acceptor);
/*     */       }
/*  91 */       while (this.keepRunning) {
/*     */         try {
/*  93 */           if (this.orb.transportDebugFlag) {
/*  94 */             dprint(".doWork: BEFORE ACCEPT CYCLE: " + this.acceptor);
/*     */           }
/*     */           
/*  97 */           this.acceptor.accept();
/*     */           
/*  99 */           if (this.orb.transportDebugFlag) {
/* 100 */             dprint(".doWork: AFTER ACCEPT CYCLE: " + this.acceptor);
/*     */           }
/* 102 */         } catch (Throwable throwable) {
/* 103 */           if (this.orb.transportDebugFlag) {
/* 104 */             dprint(".doWork: Exception in accept: " + this.acceptor, throwable);
/*     */           }
/* 106 */           this.orb.getTransportManager().getSelector(0)
/* 107 */             .unregisterForEvent(getAcceptor().getEventHandler());
/* 108 */           getAcceptor().close();
/*     */         } 
/*     */       } 
/*     */     } finally {
/* 112 */       if (this.orb.transportDebugFlag) {
/* 113 */         dprint(".doWork: Terminated ListenerThread: " + this.acceptor);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void setEnqueueTime(long paramLong) {
/* 120 */     this.enqueueTime = paramLong;
/*     */   }
/*     */ 
/*     */   
/*     */   public long getEnqueueTime() {
/* 125 */     return this.enqueueTime;
/*     */   }
/*     */   public String getName() {
/* 128 */     return "ListenerThread";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void dprint(String paramString) {
/* 137 */     ORBUtility.dprint("ListenerThreadImpl", paramString);
/*     */   }
/*     */ 
/*     */   
/*     */   private void dprint(String paramString, Throwable paramThrowable) {
/* 142 */     dprint(paramString);
/* 143 */     paramThrowable.printStackTrace(System.out);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/transport/ListenerThreadImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */