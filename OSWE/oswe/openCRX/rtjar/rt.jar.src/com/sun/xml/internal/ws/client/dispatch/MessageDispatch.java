/*    */ package com.sun.xml.internal.ws.client.dispatch;
/*    */ 
/*    */ import com.sun.xml.internal.ws.api.addressing.WSEndpointReference;
/*    */ import com.sun.xml.internal.ws.api.client.WSPortInfo;
/*    */ import com.sun.xml.internal.ws.api.message.Message;
/*    */ import com.sun.xml.internal.ws.api.message.Packet;
/*    */ import com.sun.xml.internal.ws.api.pipe.Tube;
/*    */ import com.sun.xml.internal.ws.binding.BindingImpl;
/*    */ import com.sun.xml.internal.ws.client.WSServiceDelegate;
/*    */ import javax.xml.namespace.QName;
/*    */ import javax.xml.ws.Service;
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
/*    */ public class MessageDispatch
/*    */   extends DispatchImpl<Message>
/*    */ {
/*    */   @Deprecated
/*    */   public MessageDispatch(QName port, WSServiceDelegate service, Tube pipe, BindingImpl binding, WSEndpointReference epr) {
/* 50 */     super(port, Service.Mode.MESSAGE, service, pipe, binding, epr);
/*    */   }
/*    */   
/*    */   public MessageDispatch(WSPortInfo portInfo, BindingImpl binding, WSEndpointReference epr) {
/* 54 */     super(portInfo, Service.Mode.MESSAGE, binding, epr, true);
/*    */   }
/*    */ 
/*    */   
/*    */   Message toReturnValue(Packet response) {
/* 59 */     return response.getMessage();
/*    */   }
/*    */ 
/*    */   
/*    */   Packet createPacket(Message msg) {
/* 64 */     return new Packet(msg);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/client/dispatch/MessageDispatch.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */