/*    */ package com.sun.xml.internal.ws.handler;
/*    */ 
/*    */ import com.sun.xml.internal.ws.api.SOAPVersion;
/*    */ import com.sun.xml.internal.ws.api.WSBinding;
/*    */ import com.sun.xml.internal.ws.api.message.Message;
/*    */ import com.sun.xml.internal.ws.api.message.Messages;
/*    */ import java.util.List;
/*    */ import java.util.logging.Level;
/*    */ import javax.xml.namespace.QName;
/*    */ import javax.xml.ws.ProtocolException;
/*    */ import javax.xml.ws.handler.Handler;
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
/*    */ 
/*    */ final class SOAPHandlerProcessor<C extends MessageUpdatableContext>
/*    */   extends HandlerProcessor<C>
/*    */ {
/*    */   public SOAPHandlerProcessor(boolean isClient, HandlerTube owner, WSBinding binding, List<? extends Handler> chain) {
/* 58 */     super(owner, binding, chain);
/* 59 */     this.isClient = isClient;
/*    */   }
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
/*    */   final void insertFaultMessage(C context, ProtocolException exception) {
/*    */     try {
/* 79 */       if (!context.getPacketMessage().isFault()) {
/* 80 */         Message faultMessage = Messages.create(this.binding.getSOAPVersion(), exception, 
/* 81 */             determineFaultCode(this.binding.getSOAPVersion()));
/* 82 */         context.setPacketMessage(faultMessage);
/*    */       } 
/* 84 */     } catch (Exception e) {
/*    */       
/* 86 */       logger.log(Level.SEVERE, "exception while creating fault message in handler chain", e);
/*    */       
/* 88 */       throw new RuntimeException(e);
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private QName determineFaultCode(SOAPVersion soapVersion) {
/* 98 */     return this.isClient ? soapVersion.faultCodeClient : soapVersion.faultCodeServer;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/handler/SOAPHandlerProcessor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */