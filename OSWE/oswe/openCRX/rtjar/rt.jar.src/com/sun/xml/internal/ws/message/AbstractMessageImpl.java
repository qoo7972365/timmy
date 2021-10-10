/*     */ package com.sun.xml.internal.ws.message;
/*     */ 
/*     */ import com.sun.istack.internal.NotNull;
/*     */ import com.sun.xml.internal.bind.api.Bridge;
/*     */ import com.sun.xml.internal.ws.api.SOAPVersion;
/*     */ import com.sun.xml.internal.ws.api.message.Header;
/*     */ import com.sun.xml.internal.ws.api.message.Message;
/*     */ import com.sun.xml.internal.ws.api.message.MessageHeaders;
/*     */ import com.sun.xml.internal.ws.api.message.MessageWritable;
/*     */ import com.sun.xml.internal.ws.api.message.Packet;
/*     */ import com.sun.xml.internal.ws.api.message.saaj.SAAJFactory;
/*     */ import com.sun.xml.internal.ws.encoding.TagInfoset;
/*     */ import com.sun.xml.internal.ws.message.saaj.SAAJMessage;
/*     */ import com.sun.xml.internal.ws.spi.db.XMLBridge;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import javax.xml.bind.JAXBException;
/*     */ import javax.xml.bind.Unmarshaller;
/*     */ import javax.xml.soap.SOAPException;
/*     */ import javax.xml.soap.SOAPMessage;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import javax.xml.stream.XMLStreamWriter;
/*     */ import javax.xml.transform.Source;
/*     */ import javax.xml.transform.sax.SAXSource;
/*     */ import org.xml.sax.ContentHandler;
/*     */ import org.xml.sax.ErrorHandler;
/*     */ import org.xml.sax.SAXException;
/*     */ import org.xml.sax.helpers.AttributesImpl;
/*     */ import org.xml.sax.helpers.LocatorImpl;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class AbstractMessageImpl
/*     */   extends Message
/*     */ {
/*     */   protected final SOAPVersion soapVersion;
/*     */   @NotNull
/*     */   protected TagInfoset envelopeTag;
/*     */   @NotNull
/*     */   protected TagInfoset headerTag;
/*     */   @NotNull
/*     */   protected TagInfoset bodyTag;
/*     */   protected static final AttributesImpl EMPTY_ATTS;
/*  92 */   protected static final LocatorImpl NULL_LOCATOR = new LocatorImpl();
/*     */   protected static final List<TagInfoset> DEFAULT_TAGS;
/*     */   
/*     */   static void create(SOAPVersion v, List<TagInfoset> c) {
/*  96 */     int base = v.ordinal() * 3;
/*  97 */     c.add(base, new TagInfoset(v.nsUri, "Envelope", "S", EMPTY_ATTS, new String[] { "S", v.nsUri }));
/*  98 */     c.add(base + 1, new TagInfoset(v.nsUri, "Header", "S", EMPTY_ATTS, new String[0]));
/*  99 */     c.add(base + 2, new TagInfoset(v.nsUri, "Body", "S", EMPTY_ATTS, new String[0]));
/*     */   }
/*     */   
/*     */   static {
/* 103 */     EMPTY_ATTS = new AttributesImpl();
/* 104 */     List<TagInfoset> tagList = new ArrayList<>();
/* 105 */     create(SOAPVersion.SOAP_11, tagList);
/* 106 */     create(SOAPVersion.SOAP_12, tagList);
/* 107 */     DEFAULT_TAGS = Collections.unmodifiableList(tagList);
/*     */   }
/*     */   
/*     */   protected AbstractMessageImpl(SOAPVersion soapVersion) {
/* 111 */     this.soapVersion = soapVersion;
/*     */   }
/*     */ 
/*     */   
/*     */   public SOAPVersion getSOAPVersion() {
/* 116 */     return this.soapVersion;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected AbstractMessageImpl(AbstractMessageImpl that) {
/* 122 */     this.soapVersion = that.soapVersion;
/*     */   }
/*     */ 
/*     */   
/*     */   public Source readEnvelopeAsSource() {
/* 127 */     return new SAXSource(new XMLReaderImpl(this), XMLReaderImpl.THE_SOURCE);
/*     */   }
/*     */ 
/*     */   
/*     */   public <T> T readPayloadAsJAXB(Unmarshaller unmarshaller) throws JAXBException {
/* 132 */     if (hasAttachments())
/* 133 */       unmarshaller.setAttachmentUnmarshaller(new AttachmentUnmarshallerImpl(getAttachments())); 
/*     */     try {
/* 135 */       return (T)unmarshaller.unmarshal(readPayloadAsSource());
/*     */     } finally {
/* 137 */       unmarshaller.setAttachmentUnmarshaller(null);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public <T> T readPayloadAsJAXB(Bridge<T> bridge) throws JAXBException {
/* 143 */     return (T)bridge.unmarshal(readPayloadAsSource(), 
/* 144 */         hasAttachments() ? new AttachmentUnmarshallerImpl(getAttachments()) : null);
/*     */   }
/*     */ 
/*     */   
/*     */   public <T> T readPayloadAsJAXB(XMLBridge<T> bridge) throws JAXBException {
/* 149 */     return (T)bridge.unmarshal(readPayloadAsSource(), 
/* 150 */         hasAttachments() ? new AttachmentUnmarshallerImpl(getAttachments()) : null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeTo(XMLStreamWriter w) throws XMLStreamException {
/* 158 */     String soapNsUri = this.soapVersion.nsUri;
/* 159 */     w.writeStartDocument();
/* 160 */     w.writeStartElement("S", "Envelope", soapNsUri);
/* 161 */     w.writeNamespace("S", soapNsUri);
/* 162 */     if (hasHeaders()) {
/* 163 */       w.writeStartElement("S", "Header", soapNsUri);
/* 164 */       MessageHeaders headers = getHeaders();
/* 165 */       for (Header h : headers.asList()) {
/* 166 */         h.writeTo(w);
/*     */       }
/* 168 */       w.writeEndElement();
/*     */     } 
/*     */     
/* 171 */     w.writeStartElement("S", "Body", soapNsUri);
/*     */     
/* 173 */     writePayloadTo(w);
/*     */     
/* 175 */     w.writeEndElement();
/* 176 */     w.writeEndElement();
/* 177 */     w.writeEndDocument();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeTo(ContentHandler contentHandler, ErrorHandler errorHandler) throws SAXException {
/* 185 */     String soapNsUri = this.soapVersion.nsUri;
/*     */     
/* 187 */     contentHandler.setDocumentLocator(NULL_LOCATOR);
/* 188 */     contentHandler.startDocument();
/* 189 */     contentHandler.startPrefixMapping("S", soapNsUri);
/* 190 */     contentHandler.startElement(soapNsUri, "Envelope", "S:Envelope", EMPTY_ATTS);
/* 191 */     if (hasHeaders()) {
/* 192 */       contentHandler.startElement(soapNsUri, "Header", "S:Header", EMPTY_ATTS);
/* 193 */       MessageHeaders headers = getHeaders();
/* 194 */       for (Header h : headers.asList()) {
/* 195 */         h.writeTo(contentHandler, errorHandler);
/*     */       }
/* 197 */       contentHandler.endElement(soapNsUri, "Header", "S:Header");
/*     */     } 
/*     */     
/* 200 */     contentHandler.startElement(soapNsUri, "Body", "S:Body", EMPTY_ATTS);
/* 201 */     writePayloadTo(contentHandler, errorHandler, true);
/* 202 */     contentHandler.endElement(soapNsUri, "Body", "S:Body");
/* 203 */     contentHandler.endElement(soapNsUri, "Envelope", "S:Envelope");
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
/*     */   public Message toSAAJ(Packet p, Boolean inbound) throws SOAPException {
/* 217 */     SAAJMessage message = SAAJFactory.read(p);
/* 218 */     if (message instanceof MessageWritable)
/* 219 */       ((MessageWritable)message)
/* 220 */         .setMTOMConfiguration(p.getMtomFeature()); 
/* 221 */     if (inbound != null) transportHeaders(p, inbound.booleanValue(), message.readAsSOAPMessage()); 
/* 222 */     return (Message)message;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SOAPMessage readAsSOAPMessage() throws SOAPException {
/* 230 */     return SAAJFactory.read(this.soapVersion, this);
/*     */   }
/*     */ 
/*     */   
/*     */   public SOAPMessage readAsSOAPMessage(Packet packet, boolean inbound) throws SOAPException {
/* 235 */     SOAPMessage msg = SAAJFactory.read(this.soapVersion, this, packet);
/* 236 */     transportHeaders(packet, inbound, msg);
/* 237 */     return msg;
/*     */   }
/*     */   
/*     */   private void transportHeaders(Packet packet, boolean inbound, SOAPMessage msg) throws SOAPException {
/* 241 */     Map<String, List<String>> headers = getTransportHeaders(packet, inbound);
/* 242 */     if (headers != null) {
/* 243 */       addSOAPMimeHeaders(msg.getMimeHeaders(), headers);
/*     */     }
/* 245 */     if (msg.saveRequired()) msg.saveChanges(); 
/*     */   }
/*     */   
/*     */   protected abstract void writePayloadTo(ContentHandler paramContentHandler, ErrorHandler paramErrorHandler, boolean paramBoolean) throws SAXException;
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/message/AbstractMessageImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */