/*    */ package com.sun.xml.internal.ws.server.sei;
/*    */ 
/*    */ import com.sun.istack.internal.NotNull;
/*    */ import com.sun.xml.internal.ws.api.message.Packet;
/*    */ import com.sun.xml.internal.ws.api.pipe.Tube;
/*    */ import com.sun.xml.internal.ws.api.pipe.TubeCloner;
/*    */ import com.sun.xml.internal.ws.api.pipe.helper.AbstractTubeImpl;
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
/*    */ public abstract class InvokerTube<T extends Invoker>
/*    */   extends AbstractTubeImpl
/*    */   implements InvokerSource<T>
/*    */ {
/*    */   protected final T invoker;
/*    */   
/*    */   protected InvokerTube(T invoker) {
/* 44 */     this.invoker = invoker;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected InvokerTube(InvokerTube<T> that, TubeCloner cloner) {
/* 51 */     cloner.add((Tube)that, (Tube)this);
/* 52 */     this.invoker = that.invoker;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   @NotNull
/*    */   public T getInvoker(Packet request) {
/* 59 */     return this.invoker;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/server/sei/InvokerTube.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */