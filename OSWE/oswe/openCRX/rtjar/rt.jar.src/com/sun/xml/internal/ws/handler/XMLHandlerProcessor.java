/*    */ package com.sun.xml.internal.ws.handler;
/*    */ 
/*    */ import com.sun.xml.internal.ws.api.WSBinding;
/*    */ import com.sun.xml.internal.ws.api.message.Messages;
/*    */ import java.util.List;
/*    */ import javax.xml.ws.ProtocolException;
/*    */ import javax.xml.ws.handler.Handler;
/*    */ import javax.xml.ws.http.HTTPException;
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
/*    */ final class XMLHandlerProcessor<C extends MessageUpdatableContext>
/*    */   extends HandlerProcessor<C>
/*    */ {
/*    */   public XMLHandlerProcessor(HandlerTube owner, WSBinding binding, List<? extends Handler> chain) {
/* 53 */     super(owner, binding, chain);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   final void insertFaultMessage(C context, ProtocolException exception) {
/* 62 */     if (exception instanceof HTTPException) {
/* 63 */       context.put("javax.xml.ws.http.response.code", Integer.valueOf(((HTTPException)exception).getStatusCode()));
/*    */     }
/* 65 */     if (context != null)
/*    */     {
/* 67 */       context.setPacketMessage(Messages.createEmpty(this.binding.getSOAPVersion()));
/*    */     }
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/handler/XMLHandlerProcessor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */