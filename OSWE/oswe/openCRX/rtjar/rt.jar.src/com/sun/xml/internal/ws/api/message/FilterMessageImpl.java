/*     */ package com.sun.xml.internal.ws.api.message;
/*     */ 
/*     */ import com.sun.istack.internal.NotNull;
/*     */ import com.sun.istack.internal.Nullable;
/*     */ import com.sun.xml.internal.bind.api.Bridge;
/*     */ import com.sun.xml.internal.ws.api.SOAPVersion;
/*     */ import com.sun.xml.internal.ws.api.WSBinding;
/*     */ import com.sun.xml.internal.ws.api.addressing.AddressingVersion;
/*     */ import com.sun.xml.internal.ws.api.model.wsdl.WSDLPort;
/*     */ import com.sun.xml.internal.ws.spi.db.XMLBridge;
/*     */ import javax.xml.bind.JAXBException;
/*     */ import javax.xml.bind.Unmarshaller;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.soap.SOAPException;
/*     */ import javax.xml.soap.SOAPMessage;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import javax.xml.stream.XMLStreamReader;
/*     */ import javax.xml.stream.XMLStreamWriter;
/*     */ import javax.xml.transform.Source;
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
/*     */ public class FilterMessageImpl
/*     */   extends Message
/*     */ {
/*     */   private final Message delegate;
/*     */   
/*     */   protected FilterMessageImpl(Message delegate) {
/*  69 */     this.delegate = delegate;
/*     */   }
/*     */   
/*     */   public boolean hasHeaders() {
/*  73 */     return this.delegate.hasHeaders();
/*     */   }
/*     */   @NotNull
/*     */   public MessageHeaders getHeaders() {
/*  77 */     return this.delegate.getHeaders();
/*     */   }
/*     */   @NotNull
/*     */   public AttachmentSet getAttachments() {
/*  81 */     return this.delegate.getAttachments();
/*     */   }
/*     */   
/*     */   protected boolean hasAttachments() {
/*  85 */     return this.delegate.hasAttachments();
/*     */   }
/*     */   
/*     */   public boolean isOneWay(@NotNull WSDLPort port) {
/*  89 */     return this.delegate.isOneWay(port);
/*     */   }
/*     */   @Nullable
/*     */   public String getPayloadLocalPart() {
/*  93 */     return this.delegate.getPayloadLocalPart();
/*     */   }
/*     */   
/*     */   public String getPayloadNamespaceURI() {
/*  97 */     return this.delegate.getPayloadNamespaceURI();
/*     */   }
/*     */   
/*     */   public boolean hasPayload() {
/* 101 */     return this.delegate.hasPayload();
/*     */   }
/*     */   
/*     */   public boolean isFault() {
/* 105 */     return this.delegate.isFault();
/*     */   }
/*     */   @Nullable
/*     */   public QName getFirstDetailEntryName() {
/* 109 */     return this.delegate.getFirstDetailEntryName();
/*     */   }
/*     */   
/*     */   public Source readEnvelopeAsSource() {
/* 113 */     return this.delegate.readEnvelopeAsSource();
/*     */   }
/*     */   
/*     */   public Source readPayloadAsSource() {
/* 117 */     return this.delegate.readPayloadAsSource();
/*     */   }
/*     */   
/*     */   public SOAPMessage readAsSOAPMessage() throws SOAPException {
/* 121 */     return this.delegate.readAsSOAPMessage();
/*     */   }
/*     */   
/*     */   public SOAPMessage readAsSOAPMessage(Packet packet, boolean inbound) throws SOAPException {
/* 125 */     return this.delegate.readAsSOAPMessage(packet, inbound);
/*     */   }
/*     */   
/*     */   public <T> T readPayloadAsJAXB(Unmarshaller unmarshaller) throws JAXBException {
/* 129 */     return this.delegate.readPayloadAsJAXB(unmarshaller);
/*     */   }
/*     */   
/*     */   public <T> T readPayloadAsJAXB(Bridge<T> bridge) throws JAXBException {
/* 133 */     return this.delegate.readPayloadAsJAXB(bridge);
/*     */   }
/*     */   
/*     */   public <T> T readPayloadAsJAXB(XMLBridge<T> bridge) throws JAXBException {
/* 137 */     return this.delegate.readPayloadAsJAXB(bridge);
/*     */   }
/*     */   
/*     */   public XMLStreamReader readPayload() throws XMLStreamException {
/* 141 */     return this.delegate.readPayload();
/*     */   }
/*     */   
/*     */   public void consume() {
/* 145 */     this.delegate.consume();
/*     */   }
/*     */   
/*     */   public void writePayloadTo(XMLStreamWriter sw) throws XMLStreamException {
/* 149 */     this.delegate.writePayloadTo(sw);
/*     */   }
/*     */   
/*     */   public void writeTo(XMLStreamWriter sw) throws XMLStreamException {
/* 153 */     this.delegate.writeTo(sw);
/*     */   }
/*     */   
/*     */   public void writeTo(ContentHandler contentHandler, ErrorHandler errorHandler) throws SAXException {
/* 157 */     this.delegate.writeTo(contentHandler, errorHandler);
/*     */   }
/*     */   
/*     */   public Message copy() {
/* 161 */     return this.delegate.copy();
/*     */   }
/*     */   @NotNull
/*     */   public String getID(@NotNull WSBinding binding) {
/* 165 */     return this.delegate.getID(binding);
/*     */   }
/*     */   @NotNull
/*     */   public String getID(AddressingVersion av, SOAPVersion sv) {
/* 169 */     return this.delegate.getID(av, sv);
/*     */   }
/*     */   
/*     */   public SOAPVersion getSOAPVersion() {
/* 173 */     return this.delegate.getSOAPVersion();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/api/message/FilterMessageImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */