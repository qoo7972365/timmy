/*     */ package com.sun.xml.internal.ws.api.message;
/*     */ 
/*     */ import com.sun.istack.internal.NotNull;
/*     */ import com.sun.istack.internal.Nullable;
/*     */ import com.sun.xml.internal.bind.api.Bridge;
/*     */ import com.sun.xml.internal.ws.api.SOAPVersion;
/*     */ import com.sun.xml.internal.ws.api.WSBinding;
/*     */ import com.sun.xml.internal.ws.api.addressing.AddressingVersion;
/*     */ import com.sun.xml.internal.ws.api.model.JavaMethod;
/*     */ import com.sun.xml.internal.ws.api.model.SEIModel;
/*     */ import com.sun.xml.internal.ws.api.model.WSDLOperationMapping;
/*     */ import com.sun.xml.internal.ws.api.model.wsdl.WSDLBoundOperation;
/*     */ import com.sun.xml.internal.ws.api.model.wsdl.WSDLBoundPortType;
/*     */ import com.sun.xml.internal.ws.api.model.wsdl.WSDLPort;
/*     */ import com.sun.xml.internal.ws.fault.SOAPFaultBuilder;
/*     */ import com.sun.xml.internal.ws.message.AttachmentSetImpl;
/*     */ import com.sun.xml.internal.ws.message.StringHeader;
/*     */ import com.sun.xml.internal.ws.spi.db.XMLBridge;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.UUID;
/*     */ import javax.xml.bind.JAXBException;
/*     */ import javax.xml.bind.Unmarshaller;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.soap.MimeHeaders;
/*     */ import javax.xml.soap.SOAPException;
/*     */ import javax.xml.soap.SOAPMessage;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import javax.xml.stream.XMLStreamReader;
/*     */ import javax.xml.stream.XMLStreamWriter;
/*     */ import javax.xml.transform.Source;
/*     */ import javax.xml.ws.WebServiceException;
/*     */ import org.xml.sax.ContentHandler;
/*     */ import org.xml.sax.ErrorHandler;
/*     */ import org.xml.sax.SAXException;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class Message
/*     */ {
/*     */   protected AttachmentSet attachmentSet;
/*     */   
/*     */   public abstract boolean hasHeaders();
/*     */   
/*     */   @NotNull
/*     */   public abstract MessageHeaders getHeaders();
/*     */   
/*     */   @NotNull
/*     */   public AttachmentSet getAttachments() {
/* 227 */     if (this.attachmentSet == null) {
/* 228 */       this.attachmentSet = (AttachmentSet)new AttachmentSetImpl();
/*     */     }
/* 230 */     return this.attachmentSet;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean hasAttachments() {
/* 238 */     return (this.attachmentSet != null);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 243 */   private WSDLBoundOperation operation = null;
/*     */   
/* 245 */   private WSDLOperationMapping wsdlOperationMapping = null;
/*     */   
/* 247 */   private MessageMetadata messageMetadata = null;
/*     */   
/*     */   public void setMessageMedadata(MessageMetadata metadata) {
/* 250 */     this.messageMetadata = metadata;
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
/*     */   private Boolean isOneWay;
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
/*     */   @Deprecated
/*     */   @Nullable
/*     */   public final WSDLBoundOperation getOperation(@NotNull WSDLBoundPortType boundPortType) {
/* 283 */     if (this.operation == null && this.messageMetadata != null) {
/* 284 */       if (this.wsdlOperationMapping == null) this.wsdlOperationMapping = this.messageMetadata.getWSDLOperationMapping(); 
/* 285 */       if (this.wsdlOperationMapping != null) this.operation = this.wsdlOperationMapping.getWSDLBoundOperation(); 
/*     */     } 
/* 287 */     if (this.operation == null)
/* 288 */       this.operation = boundPortType.getOperation(getPayloadNamespaceURI(), getPayloadLocalPart()); 
/* 289 */     return this.operation;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   @Nullable
/*     */   public final WSDLBoundOperation getOperation(@NotNull WSDLPort port) {
/* 302 */     return getOperation(port.getBinding());
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   @Nullable
/*     */   public final JavaMethod getMethod(@NotNull SEIModel seiModel) {
/*     */     String nsUri;
/* 332 */     if (this.wsdlOperationMapping == null && this.messageMetadata != null) {
/* 333 */       this.wsdlOperationMapping = this.messageMetadata.getWSDLOperationMapping();
/*     */     }
/* 335 */     if (this.wsdlOperationMapping != null) {
/* 336 */       return this.wsdlOperationMapping.getJavaMethod();
/*     */     }
/*     */     
/* 339 */     String localPart = getPayloadLocalPart();
/*     */     
/* 341 */     if (localPart == null) {
/* 342 */       localPart = "";
/* 343 */       nsUri = "";
/*     */     } else {
/* 345 */       nsUri = getPayloadNamespaceURI();
/*     */     } 
/* 347 */     QName name = new QName(nsUri, localPart);
/* 348 */     return seiModel.getJavaMethod(name);
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
/*     */   public boolean isOneWay(@NotNull WSDLPort port) {
/* 377 */     if (this.isOneWay == null) {
/*     */       
/* 379 */       WSDLBoundOperation op = getOperation(port);
/* 380 */       if (op != null) {
/* 381 */         this.isOneWay = Boolean.valueOf(op.getOperation().isOneWay());
/*     */       } else {
/*     */         
/* 384 */         this.isOneWay = Boolean.valueOf(false);
/*     */       } 
/* 386 */     }  return this.isOneWay.booleanValue();
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
/*     */   public final void assertOneWay(boolean value) {
/* 415 */     assert this.isOneWay == null || this.isOneWay.booleanValue() == value;
/*     */     
/* 417 */     this.isOneWay = Boolean.valueOf(value);
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
/*     */   @Nullable
/*     */   public abstract String getPayloadLocalPart();
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
/*     */   public abstract String getPayloadNamespaceURI();
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
/*     */   public abstract boolean hasPayload();
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
/*     */   public boolean isFault() {
/* 465 */     String localPart = getPayloadLocalPart();
/* 466 */     if (localPart == null || !localPart.equals("Fault")) {
/* 467 */       return false;
/*     */     }
/* 469 */     String nsUri = getPayloadNamespaceURI();
/* 470 */     return (nsUri.equals(SOAPVersion.SOAP_11.nsUri) || nsUri.equals(SOAPVersion.SOAP_12.nsUri));
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
/*     */   @Nullable
/*     */   public QName getFirstDetailEntryName() {
/* 484 */     assert isFault();
/* 485 */     Message msg = copy();
/*     */     try {
/* 487 */       SOAPFaultBuilder fault = SOAPFaultBuilder.create(msg);
/* 488 */       return fault.getFirstDetailEntryName();
/* 489 */     } catch (JAXBException e) {
/* 490 */       throw new WebServiceException(e);
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
/*     */   public abstract Source readEnvelopeAsSource();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract Source readPayloadAsSource();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract SOAPMessage readAsSOAPMessage() throws SOAPException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SOAPMessage readAsSOAPMessage(Packet packet, boolean inbound) throws SOAPException {
/* 533 */     return readAsSOAPMessage();
/*     */   }
/*     */   
/*     */   public static Map<String, List<String>> getTransportHeaders(Packet packet) {
/* 537 */     return getTransportHeaders(packet, packet.getState().isInbound());
/*     */   }
/*     */   
/*     */   public static Map<String, List<String>> getTransportHeaders(Packet packet, boolean inbound) {
/* 541 */     Map<String, List<String>> headers = null;
/* 542 */     String key = inbound ? "com.sun.xml.internal.ws.api.message.packet.inbound.transport.headers" : "com.sun.xml.internal.ws.api.message.packet.outbound.transport.headers";
/* 543 */     if (packet.supports(key)) {
/* 544 */       headers = (Map<String, List<String>>)packet.get(key);
/*     */     }
/* 546 */     return headers;
/*     */   }
/*     */   
/*     */   public static void addSOAPMimeHeaders(MimeHeaders mh, Map<String, List<String>> headers) {
/* 550 */     for (Map.Entry<String, List<String>> e : headers.entrySet()) {
/* 551 */       if (!((String)e.getKey()).equalsIgnoreCase("Content-Type")) {
/* 552 */         for (String value : e.getValue()) {
/* 553 */           mh.addHeader(e.getKey(), value);
/*     */         }
/*     */       }
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract <T> T readPayloadAsJAXB(Unmarshaller paramUnmarshaller) throws JAXBException;
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
/*     */   public abstract <T> T readPayloadAsJAXB(Bridge<T> paramBridge) throws JAXBException;
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
/*     */   public abstract <T> T readPayloadAsJAXB(XMLBridge<T> paramXMLBridge) throws JAXBException;
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
/*     */   public abstract XMLStreamReader readPayload() throws XMLStreamException;
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
/*     */   public void consume() {}
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
/*     */   public abstract void writePayloadTo(XMLStreamWriter paramXMLStreamWriter) throws XMLStreamException;
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
/*     */   public abstract void writeTo(XMLStreamWriter paramXMLStreamWriter) throws XMLStreamException;
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
/*     */   public abstract void writeTo(ContentHandler paramContentHandler, ErrorHandler paramErrorHandler) throws SAXException;
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
/*     */   public abstract Message copy();
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
/*     */   @NotNull
/*     */   public String getID(@NotNull WSBinding binding) {
/* 754 */     return getID(binding.getAddressingVersion(), binding.getSOAPVersion());
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
/*     */   public String getID(AddressingVersion av, SOAPVersion sv) {
/* 767 */     String uuid = null;
/* 768 */     if (av != null) {
/* 769 */       uuid = AddressingUtils.getMessageID(getHeaders(), av, sv);
/*     */     }
/* 771 */     if (uuid == null) {
/* 772 */       uuid = generateMessageID();
/* 773 */       getHeaders().add((Header)new StringHeader(av.messageIDTag, uuid));
/*     */     } 
/* 775 */     return uuid;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String generateMessageID() {
/* 783 */     return "uuid:" + UUID.randomUUID().toString();
/*     */   }
/*     */   
/*     */   public SOAPVersion getSOAPVersion() {
/* 787 */     return null;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/api/message/Message.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */