/*     */ package com.sun.xml.internal.ws.client.dispatch;
/*     */ 
/*     */ import com.sun.xml.internal.ws.api.addressing.WSEndpointReference;
/*     */ import com.sun.xml.internal.ws.api.client.WSPortInfo;
/*     */ import com.sun.xml.internal.ws.api.message.Header;
/*     */ import com.sun.xml.internal.ws.api.message.Headers;
/*     */ import com.sun.xml.internal.ws.api.message.Message;
/*     */ import com.sun.xml.internal.ws.api.message.Messages;
/*     */ import com.sun.xml.internal.ws.api.message.Packet;
/*     */ import com.sun.xml.internal.ws.api.pipe.Tube;
/*     */ import com.sun.xml.internal.ws.binding.BindingImpl;
/*     */ import com.sun.xml.internal.ws.client.WSServiceDelegate;
/*     */ import com.sun.xml.internal.ws.message.jaxb.JAXBDispatchMessage;
/*     */ import com.sun.xml.internal.ws.spi.db.BindingContextFactory;
/*     */ import javax.xml.bind.JAXBContext;
/*     */ import javax.xml.bind.JAXBException;
/*     */ import javax.xml.bind.Unmarshaller;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class JAXBDispatch
/*     */   extends DispatchImpl<Object>
/*     */ {
/*     */   private final JAXBContext jaxbcontext;
/*     */   private final boolean isContextSupported;
/*     */   
/*     */   @Deprecated
/*     */   public JAXBDispatch(QName port, JAXBContext jc, Service.Mode mode, WSServiceDelegate service, Tube pipe, BindingImpl binding, WSEndpointReference epr) {
/*  71 */     super(port, mode, service, pipe, binding, epr);
/*  72 */     this.jaxbcontext = jc;
/*  73 */     this.isContextSupported = BindingContextFactory.isContextSupported(jc);
/*     */   }
/*     */   
/*     */   public JAXBDispatch(WSPortInfo portInfo, JAXBContext jc, Service.Mode mode, BindingImpl binding, WSEndpointReference epr) {
/*  77 */     super(portInfo, mode, binding, epr);
/*  78 */     this.jaxbcontext = jc;
/*  79 */     this.isContextSupported = BindingContextFactory.isContextSupported(jc);
/*     */   }
/*     */   Object toReturnValue(Packet response) {
/*     */     try {
/*     */       Source result;
/*  84 */       Unmarshaller unmarshaller = this.jaxbcontext.createUnmarshaller();
/*  85 */       Message msg = response.getMessage();
/*  86 */       switch (this.mode) {
/*     */         case PAYLOAD:
/*  88 */           return msg.readPayloadAsJAXB(unmarshaller);
/*     */         case MESSAGE:
/*  90 */           result = msg.readEnvelopeAsSource();
/*  91 */           return unmarshaller.unmarshal(result);
/*     */       } 
/*  93 */       throw new WebServiceException("Unrecognized dispatch mode");
/*     */     }
/*  95 */     catch (JAXBException e) {
/*  96 */       throw new WebServiceException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   Packet createPacket(Object msg) {
/*     */     Message message;
/* 102 */     assert this.jaxbcontext != null;
/*     */ 
/*     */     
/* 105 */     if (this.mode == Service.Mode.MESSAGE) {
/*     */       
/* 107 */       JAXBDispatchMessage jAXBDispatchMessage = this.isContextSupported ? new JAXBDispatchMessage(BindingContextFactory.create(this.jaxbcontext), msg, this.soapVersion) : new JAXBDispatchMessage(this.jaxbcontext, msg, this.soapVersion);
/*     */     
/*     */     }
/* 110 */     else if (msg == null) {
/* 111 */       message = Messages.createEmpty(this.soapVersion);
/*     */     }
/*     */     else {
/*     */       
/* 115 */       message = this.isContextSupported ? Messages.create(this.jaxbcontext, msg, this.soapVersion) : Messages.createRaw(this.jaxbcontext, msg, this.soapVersion);
/*     */     } 
/*     */ 
/*     */     
/* 119 */     return new Packet(message);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setOutboundHeaders(Object... headers) {
/* 124 */     if (headers == null)
/* 125 */       throw new IllegalArgumentException(); 
/* 126 */     Header[] hl = new Header[headers.length];
/* 127 */     for (int i = 0; i < hl.length; i++) {
/* 128 */       if (headers[i] == null) {
/* 129 */         throw new IllegalArgumentException();
/*     */       }
/* 131 */       hl[i] = Headers.create(this.jaxbcontext, headers[i]);
/*     */     } 
/* 133 */     setOutboundHeaders(hl);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/client/dispatch/JAXBDispatch.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */