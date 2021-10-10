/*     */ package com.sun.xml.internal.ws.message.source;
/*     */ 
/*     */ import com.sun.xml.internal.bind.api.Bridge;
/*     */ import com.sun.xml.internal.ws.api.SOAPVersion;
/*     */ import com.sun.xml.internal.ws.api.message.Message;
/*     */ import com.sun.xml.internal.ws.api.message.MessageHeaders;
/*     */ import com.sun.xml.internal.ws.api.message.Packet;
/*     */ import com.sun.xml.internal.ws.api.pipe.Codecs;
/*     */ import com.sun.xml.internal.ws.api.pipe.StreamSOAPCodec;
/*     */ import com.sun.xml.internal.ws.spi.db.XMLBridge;
/*     */ import com.sun.xml.internal.ws.streaming.SourceReaderFactory;
/*     */ import javax.xml.bind.JAXBException;
/*     */ import javax.xml.bind.Unmarshaller;
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
/*     */ public class ProtocolSourceMessage
/*     */   extends Message
/*     */ {
/*     */   private final Message sm;
/*     */   
/*     */   public ProtocolSourceMessage(Source source, SOAPVersion soapVersion) {
/*  63 */     XMLStreamReader reader = SourceReaderFactory.createSourceReader(source, true);
/*  64 */     StreamSOAPCodec codec = Codecs.createSOAPEnvelopeXmlCodec(soapVersion);
/*  65 */     this.sm = codec.decode(reader);
/*     */   }
/*     */   
/*     */   public boolean hasHeaders() {
/*  69 */     return this.sm.hasHeaders();
/*     */   }
/*     */   
/*     */   public String getPayloadLocalPart() {
/*  73 */     return this.sm.getPayloadLocalPart();
/*     */   }
/*     */   
/*     */   public String getPayloadNamespaceURI() {
/*  77 */     return this.sm.getPayloadNamespaceURI();
/*     */   }
/*     */   
/*     */   public boolean hasPayload() {
/*  81 */     return this.sm.hasPayload();
/*     */   }
/*     */   
/*     */   public Source readPayloadAsSource() {
/*  85 */     return this.sm.readPayloadAsSource();
/*     */   }
/*     */   
/*     */   public XMLStreamReader readPayload() throws XMLStreamException {
/*  89 */     return this.sm.readPayload();
/*     */   }
/*     */   
/*     */   public void writePayloadTo(XMLStreamWriter sw) throws XMLStreamException {
/*  93 */     this.sm.writePayloadTo(sw);
/*     */   }
/*     */   
/*     */   public void writeTo(XMLStreamWriter sw) throws XMLStreamException {
/*  97 */     this.sm.writeTo(sw);
/*     */   }
/*     */   
/*     */   public Message copy() {
/* 101 */     return this.sm.copy();
/*     */   }
/*     */   
/*     */   public Source readEnvelopeAsSource() {
/* 105 */     return this.sm.readEnvelopeAsSource();
/*     */   }
/*     */   
/*     */   public SOAPMessage readAsSOAPMessage() throws SOAPException {
/* 109 */     return this.sm.readAsSOAPMessage();
/*     */   }
/*     */   
/*     */   public SOAPMessage readAsSOAPMessage(Packet packet, boolean inbound) throws SOAPException {
/* 113 */     return this.sm.readAsSOAPMessage(packet, inbound);
/*     */   }
/*     */   
/*     */   public <T> T readPayloadAsJAXB(Unmarshaller unmarshaller) throws JAXBException {
/* 117 */     return (T)this.sm.readPayloadAsJAXB(unmarshaller);
/*     */   }
/*     */   
/*     */   public <T> T readPayloadAsJAXB(Bridge<T> bridge) throws JAXBException {
/* 121 */     return (T)this.sm.readPayloadAsJAXB(bridge);
/*     */   }
/*     */   public <T> T readPayloadAsJAXB(XMLBridge<T> bridge) throws JAXBException {
/* 124 */     return (T)this.sm.readPayloadAsJAXB(bridge);
/*     */   }
/*     */   
/*     */   public void writeTo(ContentHandler contentHandler, ErrorHandler errorHandler) throws SAXException {
/* 128 */     this.sm.writeTo(contentHandler, errorHandler);
/*     */   }
/*     */   
/*     */   public SOAPVersion getSOAPVersion() {
/* 132 */     return this.sm.getSOAPVersion();
/*     */   }
/*     */ 
/*     */   
/*     */   public MessageHeaders getHeaders() {
/* 137 */     return this.sm.getHeaders();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/message/source/ProtocolSourceMessage.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */