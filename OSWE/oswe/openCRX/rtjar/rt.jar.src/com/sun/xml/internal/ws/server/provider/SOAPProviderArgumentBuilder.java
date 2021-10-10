/*     */ package com.sun.xml.internal.ws.server.provider;
/*     */ 
/*     */ import com.sun.istack.internal.Nullable;
/*     */ import com.sun.xml.internal.ws.api.SOAPVersion;
/*     */ import com.sun.xml.internal.ws.api.WSBinding;
/*     */ import com.sun.xml.internal.ws.api.message.Message;
/*     */ import com.sun.xml.internal.ws.api.message.Messages;
/*     */ import com.sun.xml.internal.ws.api.message.Packet;
/*     */ import com.sun.xml.internal.ws.api.model.wsdl.WSDLPort;
/*     */ import com.sun.xml.internal.ws.fault.SOAPFaultBuilder;
/*     */ import com.sun.xml.internal.ws.resources.ServerMessages;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import javax.xml.soap.MimeHeader;
/*     */ import javax.xml.soap.MimeHeaders;
/*     */ import javax.xml.soap.SOAPException;
/*     */ import javax.xml.soap.SOAPMessage;
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
/*     */ abstract class SOAPProviderArgumentBuilder<T>
/*     */   extends ProviderArgumentsBuilder<T>
/*     */ {
/*     */   protected final SOAPVersion soapVersion;
/*     */   
/*     */   private SOAPProviderArgumentBuilder(SOAPVersion soapVersion) {
/*  58 */     this.soapVersion = soapVersion;
/*     */   }
/*     */   
/*     */   static ProviderArgumentsBuilder create(ProviderEndpointModel model, SOAPVersion soapVersion) {
/*  62 */     if (model.mode == Service.Mode.PAYLOAD) {
/*  63 */       return new PayloadSource(soapVersion);
/*     */     }
/*  65 */     if (model.datatype == Source.class)
/*  66 */       return new MessageSource(soapVersion); 
/*  67 */     if (model.datatype == SOAPMessage.class)
/*  68 */       return new SOAPMessageParameter(soapVersion); 
/*  69 */     if (model.datatype == Message.class)
/*  70 */       return new MessageProviderArgumentBuilder(soapVersion); 
/*  71 */     throw new WebServiceException(ServerMessages.PROVIDER_INVALID_PARAMETER_TYPE(model.implClass, model.datatype));
/*     */   }
/*     */   
/*     */   private static final class PayloadSource
/*     */     extends SOAPProviderArgumentBuilder<Source> {
/*     */     PayloadSource(SOAPVersion soapVersion) {
/*  77 */       super(soapVersion);
/*     */     }
/*     */     
/*     */     public Source getParameter(Packet packet) {
/*  81 */       return packet.getMessage().readPayloadAsSource();
/*     */     }
/*     */     
/*     */     protected Message getResponseMessage(Source source) {
/*  85 */       return Messages.createUsingPayload(source, this.soapVersion);
/*     */     }
/*     */     
/*     */     protected Message getResponseMessage(Exception e) {
/*  89 */       return SOAPFaultBuilder.createSOAPFaultMessage(this.soapVersion, null, e);
/*     */     }
/*     */   }
/*     */   
/*     */   private static final class MessageSource
/*     */     extends SOAPProviderArgumentBuilder<Source> {
/*     */     MessageSource(SOAPVersion soapVersion) {
/*  96 */       super(soapVersion);
/*     */     }
/*     */     
/*     */     public Source getParameter(Packet packet) {
/* 100 */       return packet.getMessage().readEnvelopeAsSource();
/*     */     }
/*     */     
/*     */     protected Message getResponseMessage(Source source) {
/* 104 */       return Messages.create(source, this.soapVersion);
/*     */     }
/*     */     
/*     */     protected Message getResponseMessage(Exception e) {
/* 108 */       return SOAPFaultBuilder.createSOAPFaultMessage(this.soapVersion, null, e);
/*     */     }
/*     */   }
/*     */   
/*     */   private static final class SOAPMessageParameter extends SOAPProviderArgumentBuilder<SOAPMessage> {
/*     */     SOAPMessageParameter(SOAPVersion soapVersion) {
/* 114 */       super(soapVersion);
/*     */     }
/*     */     
/*     */     public SOAPMessage getParameter(Packet packet) {
/*     */       try {
/* 119 */         return packet.getMessage().readAsSOAPMessage(packet, true);
/* 120 */       } catch (SOAPException se) {
/* 121 */         throw new WebServiceException(se);
/*     */       } 
/*     */     }
/*     */     
/*     */     protected Message getResponseMessage(SOAPMessage soapMsg) {
/* 126 */       return Messages.create(soapMsg);
/*     */     }
/*     */     
/*     */     protected Message getResponseMessage(Exception e) {
/* 130 */       return SOAPFaultBuilder.createSOAPFaultMessage(this.soapVersion, null, e);
/*     */     }
/*     */ 
/*     */     
/*     */     protected Packet getResponse(Packet request, @Nullable SOAPMessage returnValue, WSDLPort port, WSBinding binding) {
/* 135 */       Packet response = super.getResponse(request, returnValue, port, binding);
/*     */       
/* 137 */       if (returnValue != null && response.supports("com.sun.xml.internal.ws.api.message.packet.outbound.transport.headers")) {
/* 138 */         MimeHeaders hdrs = returnValue.getMimeHeaders();
/* 139 */         Map<String, List<String>> headers = new HashMap<>();
/* 140 */         Iterator<MimeHeader> i = hdrs.getAllHeaders();
/* 141 */         while (i.hasNext()) {
/* 142 */           MimeHeader header = i.next();
/* 143 */           if (header.getName().equalsIgnoreCase("SOAPAction")) {
/*     */             continue;
/*     */           }
/*     */ 
/*     */           
/* 148 */           List<String> list = headers.get(header.getName());
/* 149 */           if (list == null) {
/* 150 */             list = new ArrayList<>();
/* 151 */             headers.put(header.getName(), list);
/*     */           } 
/* 153 */           list.add(header.getValue());
/*     */         } 
/* 155 */         response.put("com.sun.xml.internal.ws.api.message.packet.outbound.transport.headers", headers);
/*     */       } 
/* 157 */       return response;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/server/provider/SOAPProviderArgumentBuilder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */