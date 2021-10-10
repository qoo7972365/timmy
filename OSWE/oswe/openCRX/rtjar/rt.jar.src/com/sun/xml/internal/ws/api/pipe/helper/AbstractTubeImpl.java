/*     */ package com.sun.xml.internal.ws.api.pipe.helper;
/*     */ 
/*     */ import com.sun.xml.internal.ws.api.message.Packet;
/*     */ import com.sun.xml.internal.ws.api.pipe.Fiber;
/*     */ import com.sun.xml.internal.ws.api.pipe.NextAction;
/*     */ import com.sun.xml.internal.ws.api.pipe.Pipe;
/*     */ import com.sun.xml.internal.ws.api.pipe.PipeCloner;
/*     */ import com.sun.xml.internal.ws.api.pipe.Tube;
/*     */ import com.sun.xml.internal.ws.api.pipe.TubeCloner;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class AbstractTubeImpl
/*     */   implements Tube, Pipe
/*     */ {
/*     */   protected AbstractTubeImpl() {}
/*     */   
/*     */   protected AbstractTubeImpl(AbstractTubeImpl that, TubeCloner cloner) {
/*  57 */     cloner.add(that, this);
/*     */   }
/*     */   
/*     */   protected final NextAction doInvoke(Tube next, Packet packet) {
/*  61 */     NextAction na = new NextAction();
/*  62 */     na.invoke(next, packet);
/*  63 */     return na;
/*     */   }
/*     */   
/*     */   protected final NextAction doInvokeAndForget(Tube next, Packet packet) {
/*  67 */     NextAction na = new NextAction();
/*  68 */     na.invokeAndForget(next, packet);
/*  69 */     return na;
/*     */   }
/*     */   
/*     */   protected final NextAction doReturnWith(Packet response) {
/*  73 */     NextAction na = new NextAction();
/*  74 */     na.returnWith(response);
/*  75 */     return na;
/*     */   }
/*     */   
/*     */   protected final NextAction doThrow(Packet response, Throwable t) {
/*  79 */     NextAction na = new NextAction();
/*  80 */     na.throwException(response, t);
/*  81 */     return na;
/*     */   }
/*     */   
/*     */   @Deprecated
/*     */   protected final NextAction doSuspend() {
/*  86 */     NextAction na = new NextAction();
/*  87 */     na.suspend();
/*  88 */     return na;
/*     */   }
/*     */   
/*     */   protected final NextAction doSuspend(Runnable onExitRunnable) {
/*  92 */     NextAction na = new NextAction();
/*  93 */     na.suspend(onExitRunnable);
/*  94 */     return na;
/*     */   }
/*     */   
/*     */   @Deprecated
/*     */   protected final NextAction doSuspend(Tube next) {
/*  99 */     NextAction na = new NextAction();
/* 100 */     na.suspend(next);
/* 101 */     return na;
/*     */   }
/*     */   
/*     */   protected final NextAction doSuspend(Tube next, Runnable onExitRunnable) {
/* 105 */     NextAction na = new NextAction();
/* 106 */     na.suspend(next, onExitRunnable);
/* 107 */     return na;
/*     */   }
/*     */   
/*     */   protected final NextAction doThrow(Throwable t) {
/* 111 */     NextAction na = new NextAction();
/* 112 */     na.throwException(t);
/* 113 */     return na;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Packet process(Packet p) {
/* 121 */     return Fiber.current().runSync(this, p);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final AbstractTubeImpl copy(PipeCloner cloner) {
/* 129 */     return copy((TubeCloner)cloner);
/*     */   }
/*     */   
/*     */   public abstract AbstractTubeImpl copy(TubeCloner paramTubeCloner);
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/api/pipe/helper/AbstractTubeImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */