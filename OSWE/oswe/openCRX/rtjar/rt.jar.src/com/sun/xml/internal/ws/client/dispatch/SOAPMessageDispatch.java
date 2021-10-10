/*    */ package com.sun.xml.internal.ws.client.dispatch;
/*    */ 
/*    */ import com.sun.xml.internal.ws.api.addressing.WSEndpointReference;
/*    */ import com.sun.xml.internal.ws.api.client.WSPortInfo;
/*    */ import com.sun.xml.internal.ws.api.message.Packet;
/*    */ import com.sun.xml.internal.ws.api.message.saaj.SAAJFactory;
/*    */ import com.sun.xml.internal.ws.api.pipe.Tube;
/*    */ import com.sun.xml.internal.ws.binding.BindingImpl;
/*    */ import com.sun.xml.internal.ws.client.WSServiceDelegate;
/*    */ import com.sun.xml.internal.ws.resources.DispatchMessages;
/*    */ import com.sun.xml.internal.ws.transport.Headers;
/*    */ import java.util.Iterator;
/*    */ import javax.xml.namespace.QName;
/*    */ import javax.xml.soap.MimeHeader;
/*    */ import javax.xml.soap.SOAPException;
/*    */ import javax.xml.soap.SOAPMessage;
/*    */ import javax.xml.ws.Service;
/*    */ import javax.xml.ws.WebServiceException;
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
/*    */ public class SOAPMessageDispatch
/*    */   extends DispatchImpl<SOAPMessage>
/*    */ {
/*    */   @Deprecated
/*    */   public SOAPMessageDispatch(QName port, Service.Mode mode, WSServiceDelegate owner, Tube pipe, BindingImpl binding, WSEndpointReference epr) {
/* 63 */     super(port, mode, owner, pipe, binding, epr);
/*    */   }
/*    */   
/*    */   public SOAPMessageDispatch(WSPortInfo portInfo, Service.Mode mode, BindingImpl binding, WSEndpointReference epr) {
/* 67 */     super(portInfo, mode, binding, epr);
/*    */   }
/*    */   
/*    */   Packet createPacket(SOAPMessage arg) {
/* 71 */     Iterator<MimeHeader> iter = arg.getMimeHeaders().getAllHeaders();
/* 72 */     Headers ch = new Headers();
/* 73 */     while (iter.hasNext()) {
/* 74 */       MimeHeader mh = iter.next();
/* 75 */       ch.add(mh.getName(), mh.getValue());
/*    */     } 
/* 77 */     Packet packet = new Packet(SAAJFactory.create(arg));
/* 78 */     packet.invocationProperties.put("javax.xml.ws.http.request.headers", ch);
/* 79 */     return packet;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   SOAPMessage toReturnValue(Packet response) {
/*    */     try {
/* 86 */       if (response == null || response.getMessage() == null) {
/* 87 */         throw new WebServiceException(DispatchMessages.INVALID_RESPONSE());
/*    */       }
/* 89 */       return response.getMessage().readAsSOAPMessage();
/* 90 */     } catch (SOAPException e) {
/* 91 */       throw new WebServiceException(e);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/client/dispatch/SOAPMessageDispatch.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */