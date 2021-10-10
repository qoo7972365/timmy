/*    */ package com.sun.xml.internal.ws.server;
/*    */ 
/*    */ import com.sun.istack.internal.NotNull;
/*    */ import com.sun.xml.internal.ws.api.message.Packet;
/*    */ import com.sun.xml.internal.ws.api.server.WSEndpoint;
/*    */ import com.sun.xml.internal.ws.api.server.WSWebServiceContext;
/*    */ import java.security.Principal;
/*    */ import javax.xml.ws.EndpointReference;
/*    */ import javax.xml.ws.handler.MessageContext;
/*    */ import javax.xml.ws.wsaddressing.W3CEndpointReference;
/*    */ import org.w3c.dom.Element;
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
/*    */ public abstract class AbstractWebServiceContext
/*    */   implements WSWebServiceContext
/*    */ {
/*    */   private final WSEndpoint endpoint;
/*    */   
/*    */   public AbstractWebServiceContext(@NotNull WSEndpoint endpoint) {
/* 55 */     this.endpoint = endpoint;
/*    */   }
/*    */   
/*    */   public MessageContext getMessageContext() {
/* 59 */     Packet packet = getRequestPacket();
/* 60 */     if (packet == null) {
/* 61 */       throw new IllegalStateException("getMessageContext() can only be called while servicing a request");
/*    */     }
/* 63 */     return new EndpointMessageContextImpl(packet);
/*    */   }
/*    */   
/*    */   public Principal getUserPrincipal() {
/* 67 */     Packet packet = getRequestPacket();
/* 68 */     if (packet == null) {
/* 69 */       throw new IllegalStateException("getUserPrincipal() can only be called while servicing a request");
/*    */     }
/* 71 */     return packet.webServiceContextDelegate.getUserPrincipal(packet);
/*    */   }
/*    */   
/*    */   public boolean isUserInRole(String role) {
/* 75 */     Packet packet = getRequestPacket();
/* 76 */     if (packet == null) {
/* 77 */       throw new IllegalStateException("isUserInRole() can only be called while servicing a request");
/*    */     }
/* 79 */     return packet.webServiceContextDelegate.isUserInRole(packet, role);
/*    */   }
/*    */   
/*    */   public EndpointReference getEndpointReference(Element... referenceParameters) {
/* 83 */     return (EndpointReference)getEndpointReference(W3CEndpointReference.class, referenceParameters);
/*    */   }
/*    */   
/*    */   public <T extends EndpointReference> T getEndpointReference(Class<T> clazz, Element... referenceParameters) {
/* 87 */     Packet packet = getRequestPacket();
/* 88 */     if (packet == null) {
/* 89 */       throw new IllegalStateException("getEndpointReference() can only be called while servicing a request");
/*    */     }
/* 91 */     String address = packet.webServiceContextDelegate.getEPRAddress(packet, this.endpoint);
/* 92 */     String wsdlAddress = null;
/* 93 */     if (this.endpoint.getServiceDefinition() != null) {
/* 94 */       wsdlAddress = packet.webServiceContextDelegate.getWSDLAddress(packet, this.endpoint);
/*    */     }
/* 96 */     return clazz.cast(this.endpoint.getEndpointReference(clazz, address, wsdlAddress, referenceParameters));
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/server/AbstractWebServiceContext.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */