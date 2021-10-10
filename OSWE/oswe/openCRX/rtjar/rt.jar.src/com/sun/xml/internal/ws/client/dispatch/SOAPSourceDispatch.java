/*     */ package com.sun.xml.internal.ws.client.dispatch;
/*     */ 
/*     */ import com.sun.xml.internal.ws.api.WSBinding;
/*     */ import com.sun.xml.internal.ws.api.addressing.WSEndpointReference;
/*     */ import com.sun.xml.internal.ws.api.client.WSPortInfo;
/*     */ import com.sun.xml.internal.ws.api.message.Message;
/*     */ import com.sun.xml.internal.ws.api.message.Messages;
/*     */ import com.sun.xml.internal.ws.api.message.Packet;
/*     */ import com.sun.xml.internal.ws.api.pipe.Tube;
/*     */ import com.sun.xml.internal.ws.binding.BindingImpl;
/*     */ import com.sun.xml.internal.ws.client.WSServiceDelegate;
/*     */ import com.sun.xml.internal.ws.message.source.PayloadSourceMessage;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.transform.Source;
/*     */ import javax.xml.ws.Service;
/*     */ import javax.xml.ws.WebServiceException;
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
/*     */ final class SOAPSourceDispatch
/*     */   extends DispatchImpl<Source>
/*     */ {
/*     */   @Deprecated
/*     */   public SOAPSourceDispatch(QName port, Service.Mode mode, WSServiceDelegate owner, Tube pipe, BindingImpl binding, WSEndpointReference epr) {
/*  59 */     super(port, mode, owner, pipe, binding, epr);
/*  60 */     assert !isXMLHttp((WSBinding)binding);
/*     */   }
/*     */   
/*     */   public SOAPSourceDispatch(WSPortInfo portInfo, Service.Mode mode, BindingImpl binding, WSEndpointReference epr) {
/*  64 */     super(portInfo, mode, binding, epr);
/*  65 */     assert !isXMLHttp((WSBinding)binding);
/*     */   }
/*     */ 
/*     */   
/*     */   Source toReturnValue(Packet response) {
/*  70 */     Message msg = response.getMessage();
/*     */     
/*  72 */     switch (this.mode) {
/*     */       case PAYLOAD:
/*  74 */         return msg.readPayloadAsSource();
/*     */       case MESSAGE:
/*  76 */         return msg.readEnvelopeAsSource();
/*     */     } 
/*  78 */     throw new WebServiceException("Unrecognized dispatch mode");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   Packet createPacket(Source msg) {
/*     */     Message message;
/*  87 */     if (msg == null)
/*  88 */     { message = Messages.createEmpty(this.soapVersion); }
/*     */     else
/*  90 */     { PayloadSourceMessage payloadSourceMessage; switch (this.mode)
/*     */       { case PAYLOAD:
/*  92 */           payloadSourceMessage = new PayloadSourceMessage(null, msg, setOutboundAttachments(), this.soapVersion);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 102 */           return new Packet((Message)payloadSourceMessage);case MESSAGE: message = Messages.create(msg, this.soapVersion); return new Packet(message); }  throw new WebServiceException("Unrecognized message mode"); }  return new Packet(message);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/client/dispatch/SOAPSourceDispatch.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */