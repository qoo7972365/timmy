/*     */ package com.sun.xml.internal.ws.api.message;
/*     */ 
/*     */ import com.sun.istack.internal.NotNull;
/*     */ import com.sun.xml.internal.bind.api.Bridge;
/*     */ import com.sun.xml.internal.ws.api.SOAPVersion;
/*     */ import com.sun.xml.internal.ws.api.WSBinding;
/*     */ import com.sun.xml.internal.ws.api.addressing.AddressingVersion;
/*     */ import com.sun.xml.internal.ws.api.model.wsdl.WSDLPort;
/*     */ import com.sun.xml.internal.ws.message.stream.StreamMessage;
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
/*     */ class MessageWrapper
/*     */   extends StreamMessage
/*     */ {
/*     */   Packet packet;
/*     */   Message delegate;
/*     */   StreamMessage streamDelegate;
/*     */   
/*     */   public void writePayloadTo(ContentHandler contentHandler, ErrorHandler errorHandler, boolean fragment) throws SAXException {
/*  65 */     this.streamDelegate.writePayloadTo(contentHandler, errorHandler, fragment);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getBodyPrologue() {
/*  70 */     return this.streamDelegate.getBodyPrologue();
/*     */   }
/*     */ 
/*     */   
/*     */   public String getBodyEpilogue() {
/*  75 */     return this.streamDelegate.getBodyEpilogue();
/*     */   }
/*     */   
/*     */   MessageWrapper(Packet p, Message m) {
/*  79 */     super(m.getSOAPVersion());
/*  80 */     this.packet = p;
/*  81 */     this.delegate = m;
/*  82 */     this.streamDelegate = (m instanceof StreamMessage) ? (StreamMessage)m : null;
/*  83 */     setMessageMedadata(p);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/*  88 */     return this.delegate.hashCode();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*  93 */     return this.delegate.equals(obj);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasHeaders() {
/*  98 */     return this.delegate.hasHeaders();
/*     */   }
/*     */ 
/*     */   
/*     */   public AttachmentSet getAttachments() {
/* 103 */     return this.delegate.getAttachments();
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 108 */     return this.delegate.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isOneWay(WSDLPort port) {
/* 113 */     return this.delegate.isOneWay(port);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getPayloadLocalPart() {
/* 118 */     return this.delegate.getPayloadLocalPart();
/*     */   }
/*     */ 
/*     */   
/*     */   public String getPayloadNamespaceURI() {
/* 123 */     return this.delegate.getPayloadNamespaceURI();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasPayload() {
/* 128 */     return this.delegate.hasPayload();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isFault() {
/* 133 */     return this.delegate.isFault();
/*     */   }
/*     */ 
/*     */   
/*     */   public QName getFirstDetailEntryName() {
/* 138 */     return this.delegate.getFirstDetailEntryName();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Source readEnvelopeAsSource() {
/* 144 */     return this.delegate.readEnvelopeAsSource();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Source readPayloadAsSource() {
/* 150 */     return this.delegate.readPayloadAsSource();
/*     */   }
/*     */ 
/*     */   
/*     */   public SOAPMessage readAsSOAPMessage() throws SOAPException {
/* 155 */     if (!(this.delegate instanceof com.sun.xml.internal.ws.message.saaj.SAAJMessage)) {
/* 156 */       this.delegate = toSAAJ(this.packet, null);
/*     */     }
/* 158 */     return this.delegate.readAsSOAPMessage();
/*     */   }
/*     */ 
/*     */   
/*     */   public SOAPMessage readAsSOAPMessage(Packet p, boolean inbound) throws SOAPException {
/* 163 */     if (!(this.delegate instanceof com.sun.xml.internal.ws.message.saaj.SAAJMessage)) {
/* 164 */       this.delegate = toSAAJ(p, Boolean.valueOf(inbound));
/*     */     }
/* 166 */     return this.delegate.readAsSOAPMessage();
/*     */   }
/*     */ 
/*     */   
/*     */   public Object readPayloadAsJAXB(Unmarshaller unmarshaller) throws JAXBException {
/* 171 */     return this.delegate.readPayloadAsJAXB(unmarshaller);
/*     */   }
/*     */ 
/*     */   
/*     */   public <T> T readPayloadAsJAXB(Bridge<T> bridge) throws JAXBException {
/* 176 */     return this.delegate.readPayloadAsJAXB(bridge);
/*     */   }
/*     */ 
/*     */   
/*     */   public <T> T readPayloadAsJAXB(XMLBridge<T> bridge) throws JAXBException {
/* 181 */     return this.delegate.readPayloadAsJAXB(bridge);
/*     */   }
/*     */ 
/*     */   
/*     */   public XMLStreamReader readPayload() {
/*     */     try {
/* 187 */       return this.delegate.readPayload();
/* 188 */     } catch (XMLStreamException e) {
/* 189 */       e.printStackTrace();
/*     */       
/* 191 */       return null;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void consume() {
/* 196 */     this.delegate.consume();
/*     */   }
/*     */ 
/*     */   
/*     */   public void writePayloadTo(XMLStreamWriter sw) throws XMLStreamException {
/* 201 */     this.delegate.writePayloadTo(sw);
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeTo(XMLStreamWriter sw) throws XMLStreamException {
/* 206 */     this.delegate.writeTo(sw);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeTo(ContentHandler contentHandler, ErrorHandler errorHandler) throws SAXException {
/* 212 */     this.delegate.writeTo(contentHandler, errorHandler);
/*     */   }
/*     */ 
/*     */   
/*     */   public Message copy() {
/* 217 */     return this.delegate.copy();
/*     */   }
/*     */ 
/*     */   
/*     */   public String getID(WSBinding binding) {
/* 222 */     return this.delegate.getID(binding);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getID(AddressingVersion av, SOAPVersion sv) {
/* 227 */     return this.delegate.getID(av, sv);
/*     */   }
/*     */ 
/*     */   
/*     */   public SOAPVersion getSOAPVersion() {
/* 232 */     return this.delegate.getSOAPVersion();
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public MessageHeaders getHeaders() {
/* 237 */     return this.delegate.getHeaders();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setMessageMedadata(MessageMetadata metadata) {
/* 242 */     super.setMessageMedadata(metadata);
/* 243 */     this.delegate.setMessageMedadata(metadata);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/api/message/MessageWrapper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */