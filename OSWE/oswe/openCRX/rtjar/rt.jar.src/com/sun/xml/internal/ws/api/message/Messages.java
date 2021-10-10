/*     */ package com.sun.xml.internal.ws.api.message;
/*     */ 
/*     */ import com.sun.istack.internal.NotNull;
/*     */ import com.sun.istack.internal.Nullable;
/*     */ import com.sun.xml.internal.stream.buffer.XMLStreamBuffer;
/*     */ import com.sun.xml.internal.ws.addressing.WsaTubeHelper;
/*     */ import com.sun.xml.internal.ws.addressing.model.MissingAddressingHeaderException;
/*     */ import com.sun.xml.internal.ws.api.SOAPVersion;
/*     */ import com.sun.xml.internal.ws.api.WSBinding;
/*     */ import com.sun.xml.internal.ws.api.addressing.AddressingVersion;
/*     */ import com.sun.xml.internal.ws.api.message.saaj.SAAJFactory;
/*     */ import com.sun.xml.internal.ws.api.pipe.Codecs;
/*     */ import com.sun.xml.internal.ws.fault.SOAPFaultBuilder;
/*     */ import com.sun.xml.internal.ws.message.AttachmentSetImpl;
/*     */ import com.sun.xml.internal.ws.message.DOMMessage;
/*     */ import com.sun.xml.internal.ws.message.EmptyMessageImpl;
/*     */ import com.sun.xml.internal.ws.message.ProblemActionHeader;
/*     */ import com.sun.xml.internal.ws.message.jaxb.JAXBMessage;
/*     */ import com.sun.xml.internal.ws.message.source.PayloadSourceMessage;
/*     */ import com.sun.xml.internal.ws.message.source.ProtocolSourceMessage;
/*     */ import com.sun.xml.internal.ws.message.stream.PayloadStreamReaderMessage;
/*     */ import com.sun.xml.internal.ws.resources.AddressingMessages;
/*     */ import com.sun.xml.internal.ws.spi.db.BindingContextFactory;
/*     */ import com.sun.xml.internal.ws.streaming.XMLStreamReaderException;
/*     */ import com.sun.xml.internal.ws.streaming.XMLStreamReaderUtil;
/*     */ import com.sun.xml.internal.ws.util.DOMUtil;
/*     */ import javax.xml.bind.JAXBContext;
/*     */ import javax.xml.bind.Marshaller;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.soap.Detail;
/*     */ import javax.xml.soap.SOAPConstants;
/*     */ import javax.xml.soap.SOAPElement;
/*     */ import javax.xml.soap.SOAPException;
/*     */ import javax.xml.soap.SOAPFault;
/*     */ import javax.xml.soap.SOAPMessage;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import javax.xml.stream.XMLStreamReader;
/*     */ import javax.xml.transform.Source;
/*     */ import javax.xml.transform.dom.DOMSource;
/*     */ import javax.xml.transform.sax.SAXSource;
/*     */ import javax.xml.transform.stream.StreamSource;
/*     */ import javax.xml.ws.ProtocolException;
/*     */ import javax.xml.ws.WebServiceException;
/*     */ import org.w3c.dom.Element;
/*     */ import org.w3c.dom.Node;
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
/*     */ public abstract class Messages
/*     */ {
/*     */   public static Message create(JAXBContext context, Object jaxbObject, SOAPVersion soapVersion) {
/* 108 */     return JAXBMessage.create(context, jaxbObject, soapVersion);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Message createRaw(JAXBContext context, Object jaxbObject, SOAPVersion soapVersion) {
/* 118 */     return JAXBMessage.createRaw(context, jaxbObject, soapVersion);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Message create(Marshaller marshaller, Object jaxbObject, SOAPVersion soapVersion) {
/* 126 */     return create(BindingContextFactory.getBindingContext(marshaller).getJAXBContext(), jaxbObject, soapVersion);
/*     */   }
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
/*     */   public static Message create(SOAPMessage saaj) {
/* 143 */     return SAAJFactory.create(saaj);
/*     */   }
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
/*     */   public static Message createUsingPayload(Source payload, SOAPVersion ver) {
/* 159 */     if (payload instanceof DOMSource) {
/* 160 */       if (((DOMSource)payload).getNode() == null) {
/* 161 */         return (Message)new EmptyMessageImpl(ver);
/*     */       }
/* 163 */     } else if (payload instanceof StreamSource) {
/* 164 */       StreamSource ss = (StreamSource)payload;
/* 165 */       if (ss.getInputStream() == null && ss.getReader() == null && ss.getSystemId() == null) {
/* 166 */         return (Message)new EmptyMessageImpl(ver);
/*     */       }
/* 168 */     } else if (payload instanceof SAXSource) {
/* 169 */       SAXSource ss = (SAXSource)payload;
/* 170 */       if (ss.getInputSource() == null && ss.getXMLReader() == null) {
/* 171 */         return (Message)new EmptyMessageImpl(ver);
/*     */       }
/*     */     } 
/* 174 */     return (Message)new PayloadSourceMessage(payload, ver);
/*     */   }
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
/*     */   public static Message createUsingPayload(XMLStreamReader payload, SOAPVersion ver) {
/* 190 */     return (Message)new PayloadStreamReaderMessage(payload, ver);
/*     */   }
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
/*     */   public static Message createUsingPayload(Element payload, SOAPVersion ver) {
/* 205 */     return (Message)new DOMMessage(ver, payload);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Message create(Element soapEnvelope) {
/* 216 */     SOAPVersion ver = SOAPVersion.fromNsUri(soapEnvelope.getNamespaceURI());
/*     */     
/* 218 */     Element header = DOMUtil.getFirstChild(soapEnvelope, ver.nsUri, "Header");
/* 219 */     HeaderList headers = null;
/* 220 */     if (header != null) {
/* 221 */       for (Node n = header.getFirstChild(); n != null; n = n.getNextSibling()) {
/* 222 */         if (n.getNodeType() == 1) {
/* 223 */           if (headers == null)
/* 224 */             headers = new HeaderList(ver); 
/* 225 */           headers.add(Headers.create((Element)n));
/*     */         } 
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/* 231 */     Element body = DOMUtil.getFirstChild(soapEnvelope, ver.nsUri, "Body");
/* 232 */     if (body == null)
/* 233 */       throw new WebServiceException("Message doesn't have <S:Body> " + soapEnvelope); 
/* 234 */     Element payload = DOMUtil.getFirstChild(soapEnvelope, ver.nsUri, "Body");
/*     */     
/* 236 */     if (payload == null) {
/* 237 */       return (Message)new EmptyMessageImpl(headers, (AttachmentSet)new AttachmentSetImpl(), ver);
/*     */     }
/* 239 */     return (Message)new DOMMessage(ver, headers, payload);
/*     */   }
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
/*     */   public static Message create(Source envelope, SOAPVersion soapVersion) {
/* 254 */     return (Message)new ProtocolSourceMessage(envelope, soapVersion);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Message createEmpty(SOAPVersion soapVersion) {
/* 262 */     return (Message)new EmptyMessageImpl(soapVersion);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public static Message create(@NotNull XMLStreamReader reader) {
/* 274 */     if (reader.getEventType() != 1)
/* 275 */       XMLStreamReaderUtil.nextElementContent(reader); 
/* 276 */     assert reader.getEventType() == 1 : reader.getEventType();
/*     */     
/* 278 */     SOAPVersion ver = SOAPVersion.fromNsUri(reader.getNamespaceURI());
/*     */     
/* 280 */     return Codecs.createSOAPEnvelopeXmlCodec(ver).decode(reader);
/*     */   }
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
/*     */   @NotNull
/*     */   public static Message create(@NotNull XMLStreamBuffer xsb) {
/*     */     try {
/* 296 */       return create((XMLStreamReader)xsb.readAsXMLStreamReader());
/* 297 */     } catch (XMLStreamException e) {
/* 298 */       throw new XMLStreamReaderException(e);
/*     */     } 
/*     */   }
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
/*     */   public static Message create(Throwable t, SOAPVersion soapVersion) {
/* 314 */     return SOAPFaultBuilder.createSOAPFaultMessage(soapVersion, null, t);
/*     */   }
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
/*     */   public static Message create(SOAPFault fault) {
/* 332 */     SOAPVersion ver = SOAPVersion.fromNsUri(fault.getNamespaceURI());
/* 333 */     return (Message)new DOMMessage(ver, (Element)fault);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Message createAddressingFaultMessage(WSBinding binding, QName missingHeader) {
/* 341 */     return createAddressingFaultMessage(binding, null, missingHeader);
/*     */   }
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
/*     */   public static Message createAddressingFaultMessage(WSBinding binding, Packet p, QName missingHeader) {
/* 357 */     AddressingVersion av = binding.getAddressingVersion();
/* 358 */     if (av == null)
/*     */     {
/* 360 */       throw new WebServiceException(AddressingMessages.ADDRESSING_SHOULD_BE_ENABLED());
/*     */     }
/* 362 */     WsaTubeHelper helper = av.getWsaHelper(null, null, binding);
/* 363 */     return create(helper.newMapRequiredFault(new MissingAddressingHeaderException(missingHeader, p)));
/*     */   }
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
/*     */   public static Message create(@NotNull String unsupportedAction, @NotNull AddressingVersion av, @NotNull SOAPVersion sv) {
/*     */     Message faultMessage;
/* 377 */     QName subcode = av.actionNotSupportedTag;
/* 378 */     String faultstring = String.format(av.actionNotSupportedText, new Object[] { unsupportedAction });
/*     */     
/*     */     try {
/*     */       SOAPFault fault;
/*     */       
/* 383 */       if (sv == SOAPVersion.SOAP_12) {
/* 384 */         fault = SOAPVersion.SOAP_12.getSOAPFactory().createFault();
/* 385 */         fault.setFaultCode(SOAPConstants.SOAP_SENDER_FAULT);
/* 386 */         fault.appendFaultSubcode(subcode);
/* 387 */         Detail detail = fault.addDetail();
/* 388 */         SOAPElement se = detail.addChildElement(av.problemActionTag);
/* 389 */         se = se.addChildElement(av.actionTag);
/* 390 */         se.addTextNode(unsupportedAction);
/*     */       } else {
/* 392 */         fault = SOAPVersion.SOAP_11.getSOAPFactory().createFault();
/* 393 */         fault.setFaultCode(subcode);
/*     */       } 
/* 395 */       fault.setFaultString(faultstring);
/*     */       
/* 397 */       faultMessage = SOAPFaultBuilder.createSOAPFaultMessage(sv, fault);
/* 398 */       if (sv == SOAPVersion.SOAP_11) {
/* 399 */         faultMessage.getHeaders().add((Header)new ProblemActionHeader(unsupportedAction, av));
/*     */       }
/* 401 */     } catch (SOAPException e) {
/* 402 */       throw new WebServiceException(e);
/*     */     } 
/*     */     
/* 405 */     return faultMessage;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public static Message create(@NotNull SOAPVersion soapVersion, @NotNull ProtocolException pex, @Nullable QName faultcode) {
/* 418 */     return SOAPFaultBuilder.createSOAPFaultMessage(soapVersion, pex, faultcode);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/api/message/Messages.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */