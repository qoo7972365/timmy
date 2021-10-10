/*    */ package com.sun.xml.internal.ws.server.provider;
/*    */ 
/*    */ import com.sun.xml.internal.ws.api.SOAPVersion;
/*    */ import com.sun.xml.internal.ws.api.message.Message;
/*    */ import com.sun.xml.internal.ws.api.message.Packet;
/*    */ import com.sun.xml.internal.ws.fault.SOAPFaultBuilder;
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
/*    */ final class MessageProviderArgumentBuilder
/*    */   extends ProviderArgumentsBuilder<Message>
/*    */ {
/*    */   private final SOAPVersion soapVersion;
/*    */   
/*    */   public MessageProviderArgumentBuilder(SOAPVersion soapVersion) {
/* 40 */     this.soapVersion = soapVersion;
/*    */   }
/*    */ 
/*    */   
/*    */   public Message getParameter(Packet packet) {
/* 45 */     return packet.getMessage();
/*    */   }
/*    */ 
/*    */   
/*    */   protected Message getResponseMessage(Message returnValue) {
/* 50 */     return returnValue;
/*    */   }
/*    */ 
/*    */   
/*    */   protected Message getResponseMessage(Exception e) {
/* 55 */     return SOAPFaultBuilder.createSOAPFaultMessage(this.soapVersion, null, e);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/server/provider/MessageProviderArgumentBuilder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */