/*    */ package com.sun.xml.internal.ws.handler;
/*    */ 
/*    */ import com.sun.xml.internal.ws.api.WSBinding;
/*    */ import com.sun.xml.internal.ws.api.message.Message;
/*    */ import com.sun.xml.internal.ws.api.message.Packet;
/*    */ import com.sun.xml.internal.ws.spi.db.BindingContext;
/*    */ import javax.xml.ws.LogicalMessage;
/*    */ import javax.xml.ws.handler.LogicalMessageContext;
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
/*    */ class LogicalMessageContextImpl
/*    */   extends MessageUpdatableContext
/*    */   implements LogicalMessageContext
/*    */ {
/*    */   private LogicalMessageImpl lm;
/*    */   private WSBinding binding;
/*    */   private BindingContext defaultJaxbContext;
/*    */   
/*    */   public LogicalMessageContextImpl(WSBinding binding, BindingContext defaultJAXBContext, Packet packet) {
/* 52 */     super(packet);
/* 53 */     this.binding = binding;
/* 54 */     this.defaultJaxbContext = defaultJAXBContext;
/*    */   }
/*    */   
/*    */   public LogicalMessage getMessage() {
/* 58 */     if (this.lm == null)
/* 59 */       this.lm = new LogicalMessageImpl(this.defaultJaxbContext, this.packet); 
/* 60 */     return this.lm;
/*    */   }
/*    */   
/*    */   void setPacketMessage(Message newMessage) {
/* 64 */     if (newMessage != null) {
/* 65 */       this.packet.setMessage(newMessage);
/* 66 */       this.lm = null;
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void updateMessage() {
/* 73 */     if (this.lm != null) {
/*    */ 
/*    */       
/* 76 */       if (this.lm.isPayloadModifed()) {
/* 77 */         Message msg = this.packet.getMessage();
/* 78 */         Message updatedMsg = this.lm.getMessage(msg.getHeaders(), msg.getAttachments(), this.binding);
/* 79 */         this.packet.setMessage(updatedMsg);
/*    */       } 
/* 81 */       this.lm = null;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/handler/LogicalMessageContextImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */