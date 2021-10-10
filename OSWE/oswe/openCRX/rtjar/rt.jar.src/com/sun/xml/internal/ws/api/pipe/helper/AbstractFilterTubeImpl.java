/*    */ package com.sun.xml.internal.ws.api.pipe.helper;
/*    */ 
/*    */ import com.sun.istack.internal.NotNull;
/*    */ import com.sun.xml.internal.ws.api.message.Packet;
/*    */ import com.sun.xml.internal.ws.api.pipe.NextAction;
/*    */ import com.sun.xml.internal.ws.api.pipe.Tube;
/*    */ import com.sun.xml.internal.ws.api.pipe.TubeCloner;
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
/*    */ public abstract class AbstractFilterTubeImpl
/*    */   extends AbstractTubeImpl
/*    */ {
/*    */   protected final Tube next;
/*    */   
/*    */   protected AbstractFilterTubeImpl(Tube next) {
/* 48 */     this.next = next;
/*    */   }
/*    */   
/*    */   protected AbstractFilterTubeImpl(AbstractFilterTubeImpl that, TubeCloner cloner) {
/* 52 */     super(that, cloner);
/* 53 */     if (that.next != null) {
/* 54 */       this.next = cloner.copy(that.next);
/*    */     } else {
/* 56 */       this.next = null;
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   @NotNull
/*    */   public NextAction processRequest(Packet request) {
/* 64 */     return doInvoke(this.next, request);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   @NotNull
/*    */   public NextAction processResponse(Packet response) {
/* 71 */     return doReturnWith(response);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   @NotNull
/*    */   public NextAction processException(Throwable t) {
/* 78 */     return doThrow(t);
/*    */   }
/*    */   
/*    */   public void preDestroy() {
/* 82 */     if (this.next != null)
/* 83 */       this.next.preDestroy(); 
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/api/pipe/helper/AbstractFilterTubeImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */