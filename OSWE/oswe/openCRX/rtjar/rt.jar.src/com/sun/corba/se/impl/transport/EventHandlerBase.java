/*     */ package com.sun.corba.se.impl.transport;
/*     */ 
/*     */ import com.sun.corba.se.impl.orbutil.ORBUtility;
/*     */ import com.sun.corba.se.pept.transport.EventHandler;
/*     */ import com.sun.corba.se.spi.orb.ORB;
/*     */ import com.sun.corba.se.spi.orbutil.threadpool.NoSuchThreadPoolException;
/*     */ import com.sun.corba.se.spi.orbutil.threadpool.NoSuchWorkQueueException;
/*     */ import com.sun.corba.se.spi.orbutil.threadpool.Work;
/*     */ import java.nio.channels.SelectionKey;
/*     */ import org.omg.CORBA.INTERNAL;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class EventHandlerBase
/*     */   implements EventHandler
/*     */ {
/*     */   protected ORB orb;
/*     */   protected Work work;
/*     */   protected boolean useWorkerThreadForEvent;
/*     */   protected boolean useSelectThreadToWait;
/*     */   protected SelectionKey selectionKey;
/*     */   
/*     */   public void setUseSelectThreadToWait(boolean paramBoolean) {
/*  60 */     this.useSelectThreadToWait = paramBoolean;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean shouldUseSelectThreadToWait() {
/*  65 */     return this.useSelectThreadToWait;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setSelectionKey(SelectionKey paramSelectionKey) {
/*  70 */     this.selectionKey = paramSelectionKey;
/*     */   }
/*     */ 
/*     */   
/*     */   public SelectionKey getSelectionKey() {
/*  75 */     return this.selectionKey;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void handleEvent() {
/*  86 */     if (this.orb.transportDebugFlag) {
/*  87 */       dprint(".handleEvent->: " + this);
/*     */     }
/*  89 */     getSelectionKey().interestOps(getSelectionKey().interestOps() & (
/*  90 */         getInterestOps() ^ 0xFFFFFFFF));
/*  91 */     if (shouldUseWorkerThreadForEvent()) {
/*  92 */       NoSuchWorkQueueException noSuchWorkQueueException; NoSuchThreadPoolException noSuchThreadPoolException = null;
/*     */       try {
/*  94 */         if (this.orb.transportDebugFlag) {
/*  95 */           dprint(".handleEvent: addWork to pool: 0");
/*     */         }
/*  97 */         this.orb.getThreadPoolManager().getThreadPool(0)
/*  98 */           .getWorkQueue(0).addWork(getWork());
/*  99 */       } catch (NoSuchThreadPoolException noSuchThreadPoolException1) {
/* 100 */         noSuchThreadPoolException = noSuchThreadPoolException1;
/* 101 */       } catch (NoSuchWorkQueueException noSuchWorkQueueException1) {
/* 102 */         noSuchWorkQueueException = noSuchWorkQueueException1;
/*     */       } 
/*     */       
/* 105 */       if (noSuchWorkQueueException != null) {
/* 106 */         if (this.orb.transportDebugFlag) {
/* 107 */           dprint(".handleEvent: " + noSuchWorkQueueException);
/*     */         }
/* 109 */         INTERNAL iNTERNAL = new INTERNAL("NoSuchThreadPoolException");
/* 110 */         iNTERNAL.initCause((Throwable)noSuchWorkQueueException);
/* 111 */         throw iNTERNAL;
/*     */       } 
/*     */     } else {
/* 114 */       if (this.orb.transportDebugFlag) {
/* 115 */         dprint(".handleEvent: doWork");
/*     */       }
/* 117 */       getWork().doWork();
/*     */     } 
/* 119 */     if (this.orb.transportDebugFlag) {
/* 120 */       dprint(".handleEvent<-: " + this);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean shouldUseWorkerThreadForEvent() {
/* 126 */     return this.useWorkerThreadForEvent;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setUseWorkerThreadForEvent(boolean paramBoolean) {
/* 131 */     this.useWorkerThreadForEvent = paramBoolean;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setWork(Work paramWork) {
/* 136 */     this.work = paramWork;
/*     */   }
/*     */ 
/*     */   
/*     */   public Work getWork() {
/* 141 */     return this.work;
/*     */   }
/*     */ 
/*     */   
/*     */   private void dprint(String paramString) {
/* 146 */     ORBUtility.dprint("EventHandlerBase", paramString);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/transport/EventHandlerBase.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */