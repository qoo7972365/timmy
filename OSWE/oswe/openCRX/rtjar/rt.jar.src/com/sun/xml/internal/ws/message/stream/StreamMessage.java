/*     */ package com.sun.xml.internal.ws.message.stream;
/*     */ 
/*     */ import com.sun.istack.internal.NotNull;
/*     */ import com.sun.istack.internal.Nullable;
/*     */ import com.sun.istack.internal.XMLStreamReaderToContentHandler;
/*     */ import com.sun.xml.internal.bind.api.Bridge;
/*     */ import com.sun.xml.internal.stream.buffer.AbstractCreatorProcessor;
/*     */ import com.sun.xml.internal.stream.buffer.MutableXMLStreamBuffer;
/*     */ import com.sun.xml.internal.stream.buffer.XMLStreamBuffer;
/*     */ import com.sun.xml.internal.stream.buffer.XMLStreamBufferMark;
/*     */ import com.sun.xml.internal.stream.buffer.stax.StreamReaderBufferCreator;
/*     */ import com.sun.xml.internal.stream.buffer.stax.StreamReaderBufferProcessor;
/*     */ import com.sun.xml.internal.ws.api.SOAPVersion;
/*     */ import com.sun.xml.internal.ws.api.message.AttachmentSet;
/*     */ import com.sun.xml.internal.ws.api.message.Header;
/*     */ import com.sun.xml.internal.ws.api.message.HeaderList;
/*     */ import com.sun.xml.internal.ws.api.message.Message;
/*     */ import com.sun.xml.internal.ws.api.message.MessageHeaders;
/*     */ import com.sun.xml.internal.ws.api.message.StreamingSOAP;
/*     */ import com.sun.xml.internal.ws.api.streaming.XMLStreamReaderFactory;
/*     */ import com.sun.xml.internal.ws.encoding.TagInfoset;
/*     */ import com.sun.xml.internal.ws.message.AbstractMessageImpl;
/*     */ import com.sun.xml.internal.ws.message.AttachmentUnmarshallerImpl;
/*     */ import com.sun.xml.internal.ws.protocol.soap.VersionMismatchException;
/*     */ import com.sun.xml.internal.ws.spi.db.XMLBridge;
/*     */ import com.sun.xml.internal.ws.streaming.XMLStreamReaderUtil;
/*     */ import com.sun.xml.internal.ws.util.xml.DummyLocation;
/*     */ import com.sun.xml.internal.ws.util.xml.StAXSource;
/*     */ import com.sun.xml.internal.ws.util.xml.XMLReaderComposite;
/*     */ import com.sun.xml.internal.ws.util.xml.XMLStreamReaderToXMLStreamWriter;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Enumeration;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import javax.xml.bind.JAXBException;
/*     */ import javax.xml.bind.Unmarshaller;
/*     */ import javax.xml.bind.attachment.AttachmentUnmarshaller;
/*     */ import javax.xml.stream.Location;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import javax.xml.stream.XMLStreamReader;
/*     */ import javax.xml.stream.XMLStreamWriter;
/*     */ import javax.xml.transform.Source;
/*     */ import javax.xml.ws.WebServiceException;
/*     */ import org.xml.sax.ContentHandler;
/*     */ import org.xml.sax.ErrorHandler;
/*     */ import org.xml.sax.SAXException;
/*     */ import org.xml.sax.SAXParseException;
/*     */ import org.xml.sax.helpers.NamespaceSupport;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class StreamMessage
/*     */   extends AbstractMessageImpl
/*     */   implements StreamingSOAP
/*     */ {
/*     */   @NotNull
/*     */   private XMLStreamReader reader;
/*     */   @Nullable
/*     */   private MessageHeaders headers;
/*  99 */   private String bodyPrologue = null;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 104 */   private String bodyEpilogue = null;
/*     */   
/*     */   private String payloadLocalName;
/*     */   
/*     */   private String payloadNamespaceURI;
/*     */   
/*     */   private Throwable consumedAt;
/*     */   
/*     */   private XMLStreamReader envelopeReader;
/*     */   private static final String SOAP_ENVELOPE = "Envelope";
/*     */   private static final String SOAP_HEADER = "Header";
/*     */   private static final String SOAP_BODY = "Body";
/*     */   
/*     */   public StreamMessage(SOAPVersion v) {
/* 118 */     super(v);
/* 119 */     this.payloadLocalName = null;
/* 120 */     this.payloadNamespaceURI = null;
/*     */   }
/*     */   
/*     */   public StreamMessage(SOAPVersion v, @NotNull XMLStreamReader envelope, @NotNull AttachmentSet attachments) {
/* 124 */     super(v);
/* 125 */     this.envelopeReader = envelope;
/* 126 */     this.attachmentSet = attachments;
/*     */   }
/*     */   
/*     */   public XMLStreamReader readEnvelope() {
/* 130 */     if (this.envelopeReader == null) {
/* 131 */       List<XMLStreamReader> hReaders = new ArrayList<>();
/* 132 */       XMLReaderComposite.ElemInfo envElem = new XMLReaderComposite.ElemInfo(this.envelopeTag, null);
/* 133 */       XMLReaderComposite.ElemInfo hdrElem = (this.headerTag != null) ? new XMLReaderComposite.ElemInfo(this.headerTag, envElem) : null;
/* 134 */       XMLReaderComposite.ElemInfo bdyElem = new XMLReaderComposite.ElemInfo(this.bodyTag, envElem);
/* 135 */       for (Header h : getHeaders().asList()) {
/*     */         try {
/* 137 */           hReaders.add(h.readHeader());
/* 138 */         } catch (XMLStreamException e) {
/* 139 */           throw new RuntimeException(e);
/*     */         } 
/*     */       } 
/* 142 */       XMLReaderComposite xMLReaderComposite1 = (hdrElem != null) ? new XMLReaderComposite(hdrElem, hReaders.<XMLStreamReader>toArray(new XMLStreamReader[hReaders.size()])) : null;
/* 143 */       XMLStreamReader[] payload = { readPayload() };
/* 144 */       XMLReaderComposite xMLReaderComposite2 = new XMLReaderComposite(bdyElem, payload);
/* 145 */       (new XMLStreamReader[2])[0] = (XMLStreamReader)xMLReaderComposite1; (new XMLStreamReader[2])[1] = (XMLStreamReader)xMLReaderComposite2; (new XMLStreamReader[1])[0] = (XMLStreamReader)xMLReaderComposite2; XMLStreamReader[] soapContent = (xMLReaderComposite1 != null) ? new XMLStreamReader[2] : new XMLStreamReader[1];
/* 146 */       return (XMLStreamReader)new XMLReaderComposite(envElem, soapContent);
/*     */     } 
/* 148 */     return this.envelopeReader;
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
/*     */   public StreamMessage(@Nullable MessageHeaders headers, @NotNull AttachmentSet attachmentSet, @NotNull XMLStreamReader reader, @NotNull SOAPVersion soapVersion) {
/* 166 */     super(soapVersion);
/* 167 */     init(headers, attachmentSet, reader, soapVersion);
/*     */   }
/*     */   
/*     */   private void init(@Nullable MessageHeaders headers, @NotNull AttachmentSet attachmentSet, @NotNull XMLStreamReader reader, @NotNull SOAPVersion soapVersion) {
/* 171 */     this.headers = headers;
/* 172 */     this.attachmentSet = attachmentSet;
/* 173 */     this.reader = reader;
/*     */     
/* 175 */     if (reader.getEventType() == 7) {
/* 176 */       XMLStreamReaderUtil.nextElementContent(reader);
/*     */     }
/*     */ 
/*     */     
/* 180 */     if (reader.getEventType() == 2) {
/* 181 */       String body = reader.getLocalName();
/* 182 */       String nsUri = reader.getNamespaceURI();
/* 183 */       assert body != null;
/* 184 */       assert nsUri != null;
/*     */       
/* 186 */       if (body.equals("Body") && nsUri.equals(soapVersion.nsUri)) {
/* 187 */         this.payloadLocalName = null;
/* 188 */         this.payloadNamespaceURI = null;
/*     */       } else {
/* 190 */         throw new WebServiceException("Malformed stream: {" + nsUri + "}" + body);
/*     */       } 
/*     */     } else {
/* 193 */       this.payloadLocalName = reader.getLocalName();
/* 194 */       this.payloadNamespaceURI = reader.getNamespaceURI();
/*     */     } 
/*     */ 
/*     */     
/* 198 */     int base = soapVersion.ordinal() * 3;
/* 199 */     this.envelopeTag = DEFAULT_TAGS.get(base);
/* 200 */     this.headerTag = DEFAULT_TAGS.get(base + 1);
/* 201 */     this.bodyTag = DEFAULT_TAGS.get(base + 2);
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
/*     */   public StreamMessage(@NotNull TagInfoset envelopeTag, @Nullable TagInfoset headerTag, @NotNull AttachmentSet attachmentSet, @Nullable MessageHeaders headers, @NotNull TagInfoset bodyTag, @NotNull XMLStreamReader reader, @NotNull SOAPVersion soapVersion) {
/* 217 */     this(envelopeTag, headerTag, attachmentSet, headers, (String)null, bodyTag, (String)null, reader, soapVersion);
/*     */   }
/*     */   
/*     */   public StreamMessage(@NotNull TagInfoset envelopeTag, @Nullable TagInfoset headerTag, @NotNull AttachmentSet attachmentSet, @Nullable MessageHeaders headers, @Nullable String bodyPrologue, @NotNull TagInfoset bodyTag, @Nullable String bodyEpilogue, @NotNull XMLStreamReader reader, @NotNull SOAPVersion soapVersion) {
/* 221 */     super(soapVersion);
/* 222 */     init(envelopeTag, headerTag, attachmentSet, headers, bodyPrologue, bodyTag, bodyEpilogue, reader, soapVersion);
/*     */   }
/*     */   
/*     */   private void init(@NotNull TagInfoset envelopeTag, @Nullable TagInfoset headerTag, @NotNull AttachmentSet attachmentSet, @Nullable MessageHeaders headers, @Nullable String bodyPrologue, @NotNull TagInfoset bodyTag, @Nullable String bodyEpilogue, @NotNull XMLStreamReader reader, @NotNull SOAPVersion soapVersion) {
/* 226 */     init(headers, attachmentSet, reader, soapVersion);
/* 227 */     if (envelopeTag == null) {
/* 228 */       throw new IllegalArgumentException("EnvelopeTag TagInfoset cannot be null");
/*     */     }
/* 230 */     if (bodyTag == null) {
/* 231 */       throw new IllegalArgumentException("BodyTag TagInfoset cannot be null");
/*     */     }
/* 233 */     this.envelopeTag = envelopeTag;
/* 234 */     this.headerTag = headerTag;
/* 235 */     this.bodyTag = bodyTag;
/* 236 */     this.bodyPrologue = bodyPrologue;
/* 237 */     this.bodyEpilogue = bodyEpilogue;
/*     */   }
/*     */   
/*     */   public boolean hasHeaders() {
/* 241 */     if (this.envelopeReader != null) readEnvelope(this); 
/* 242 */     return (this.headers != null && this.headers.hasHeaders());
/*     */   }
/*     */   
/*     */   public MessageHeaders getHeaders() {
/* 246 */     if (this.envelopeReader != null) readEnvelope(this); 
/* 247 */     if (this.headers == null) {
/* 248 */       this.headers = (MessageHeaders)new HeaderList(getSOAPVersion());
/*     */     }
/* 250 */     return this.headers;
/*     */   }
/*     */   
/*     */   public String getPayloadLocalPart() {
/* 254 */     if (this.envelopeReader != null) readEnvelope(this); 
/* 255 */     return this.payloadLocalName;
/*     */   }
/*     */   
/*     */   public String getPayloadNamespaceURI() {
/* 259 */     if (this.envelopeReader != null) readEnvelope(this); 
/* 260 */     return this.payloadNamespaceURI;
/*     */   }
/*     */   
/*     */   public boolean hasPayload() {
/* 264 */     if (this.envelopeReader != null) readEnvelope(this); 
/* 265 */     return (this.payloadLocalName != null);
/*     */   }
/*     */   
/*     */   public Source readPayloadAsSource() {
/* 269 */     if (hasPayload()) {
/* 270 */       assert unconsumed();
/* 271 */       return (Source)new StAXSource(this.reader, true, getInscopeNamespaces());
/*     */     } 
/* 273 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String[] getInscopeNamespaces() {
/* 284 */     NamespaceSupport nss = new NamespaceSupport();
/*     */     
/* 286 */     nss.pushContext(); int i;
/* 287 */     for (i = 0; i < this.envelopeTag.ns.length; i += 2) {
/* 288 */       nss.declarePrefix(this.envelopeTag.ns[i], this.envelopeTag.ns[i + 1]);
/*     */     }
/*     */     
/* 291 */     nss.pushContext();
/* 292 */     for (i = 0; i < this.bodyTag.ns.length; i += 2) {
/* 293 */       nss.declarePrefix(this.bodyTag.ns[i], this.bodyTag.ns[i + 1]);
/*     */     }
/*     */     
/* 296 */     List<String> inscope = new ArrayList<>();
/* 297 */     for (Enumeration<String> en = nss.getPrefixes(); en.hasMoreElements(); ) {
/* 298 */       String prefix = en.nextElement();
/* 299 */       inscope.add(prefix);
/* 300 */       inscope.add(nss.getURI(prefix));
/*     */     } 
/* 302 */     return inscope.<String>toArray(new String[inscope.size()]);
/*     */   }
/*     */   
/*     */   public Object readPayloadAsJAXB(Unmarshaller unmarshaller) throws JAXBException {
/* 306 */     if (!hasPayload())
/* 307 */       return null; 
/* 308 */     assert unconsumed();
/*     */     
/* 310 */     if (hasAttachments())
/* 311 */       unmarshaller.setAttachmentUnmarshaller((AttachmentUnmarshaller)new AttachmentUnmarshallerImpl(getAttachments())); 
/*     */     try {
/* 313 */       return unmarshaller.unmarshal(this.reader);
/*     */     } finally {
/* 315 */       unmarshaller.setAttachmentUnmarshaller(null);
/* 316 */       XMLStreamReaderUtil.readRest(this.reader);
/* 317 */       XMLStreamReaderUtil.close(this.reader);
/* 318 */       XMLStreamReaderFactory.recycle(this.reader);
/*     */     } 
/*     */   }
/*     */   
/*     */   public <T> T readPayloadAsJAXB(Bridge<T> bridge) throws JAXBException {
/* 323 */     if (!hasPayload())
/* 324 */       return null; 
/* 325 */     assert unconsumed();
/* 326 */     T r = (T)bridge.unmarshal(this.reader, 
/* 327 */         hasAttachments() ? (AttachmentUnmarshaller)new AttachmentUnmarshallerImpl(getAttachments()) : null);
/* 328 */     XMLStreamReaderUtil.readRest(this.reader);
/* 329 */     XMLStreamReaderUtil.close(this.reader);
/* 330 */     XMLStreamReaderFactory.recycle(this.reader);
/* 331 */     return r;
/*     */   }
/*     */   
/*     */   public <T> T readPayloadAsJAXB(XMLBridge<T> bridge) throws JAXBException {
/* 335 */     if (!hasPayload())
/* 336 */       return null; 
/* 337 */     assert unconsumed();
/* 338 */     T r = (T)bridge.unmarshal(this.reader, 
/* 339 */         hasAttachments() ? (AttachmentUnmarshaller)new AttachmentUnmarshallerImpl(getAttachments()) : null);
/* 340 */     XMLStreamReaderUtil.readRest(this.reader);
/* 341 */     XMLStreamReaderUtil.close(this.reader);
/* 342 */     XMLStreamReaderFactory.recycle(this.reader);
/* 343 */     return r;
/*     */   }
/*     */ 
/*     */   
/*     */   public void consume() {
/* 348 */     assert unconsumed();
/* 349 */     XMLStreamReaderUtil.readRest(this.reader);
/* 350 */     XMLStreamReaderUtil.close(this.reader);
/* 351 */     XMLStreamReaderFactory.recycle(this.reader);
/*     */   }
/*     */   
/*     */   public XMLStreamReader readPayload() {
/* 355 */     if (!hasPayload()) {
/* 356 */       return null;
/*     */     }
/* 358 */     assert unconsumed();
/* 359 */     return this.reader;
/*     */   }
/*     */   
/*     */   public void writePayloadTo(XMLStreamWriter writer) throws XMLStreamException {
/* 363 */     if (this.envelopeReader != null) readEnvelope(this); 
/* 364 */     assert unconsumed();
/*     */     
/* 366 */     if (this.payloadLocalName == null) {
/*     */       return;
/*     */     }
/*     */     
/* 370 */     if (this.bodyPrologue != null) {
/* 371 */       writer.writeCharacters(this.bodyPrologue);
/*     */     }
/*     */     
/* 374 */     XMLStreamReaderToXMLStreamWriter conv = new XMLStreamReaderToXMLStreamWriter();
/*     */     
/* 376 */     while (this.reader.getEventType() != 8) {
/* 377 */       String name = this.reader.getLocalName();
/* 378 */       String nsUri = this.reader.getNamespaceURI();
/*     */ 
/*     */ 
/*     */       
/* 382 */       if (this.reader.getEventType() == 2) {
/*     */         
/* 384 */         if (!isBodyElement(name, nsUri)) {
/*     */ 
/*     */           
/* 387 */           String whiteSpaces = XMLStreamReaderUtil.nextWhiteSpaceContent(this.reader);
/* 388 */           if (whiteSpaces != null) {
/* 389 */             this.bodyEpilogue = whiteSpaces;
/*     */             
/* 391 */             writer.writeCharacters(whiteSpaces);
/*     */           } 
/*     */           
/*     */           continue;
/*     */         } 
/*     */         
/*     */         break;
/*     */       } 
/*     */       
/* 400 */       conv.bridge(this.reader, writer);
/*     */     } 
/*     */ 
/*     */     
/* 404 */     XMLStreamReaderUtil.readRest(this.reader);
/* 405 */     XMLStreamReaderUtil.close(this.reader);
/* 406 */     XMLStreamReaderFactory.recycle(this.reader);
/*     */   }
/*     */   
/*     */   private boolean isBodyElement(String name, String nsUri) {
/* 410 */     return (name.equals("Body") && nsUri.equals(this.soapVersion.nsUri));
/*     */   }
/*     */   
/*     */   public void writeTo(XMLStreamWriter sw) throws XMLStreamException {
/* 414 */     if (this.envelopeReader != null) readEnvelope(this); 
/* 415 */     writeEnvelope(sw);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void writeEnvelope(XMLStreamWriter writer) throws XMLStreamException {
/* 423 */     if (this.envelopeReader != null) readEnvelope(this); 
/* 424 */     writer.writeStartDocument();
/* 425 */     this.envelopeTag.writeStart(writer);
/*     */ 
/*     */     
/* 428 */     MessageHeaders hl = getHeaders();
/* 429 */     if (hl.hasHeaders() && this.headerTag == null) this.headerTag = new TagInfoset(this.envelopeTag.nsUri, "Header", this.envelopeTag.prefix, EMPTY_ATTS, new String[0]); 
/* 430 */     if (this.headerTag != null) {
/* 431 */       this.headerTag.writeStart(writer);
/* 432 */       if (hl.hasHeaders()) {
/* 433 */         for (Header h : hl.asList()) {
/* 434 */           h.writeTo(writer);
/*     */         }
/*     */       }
/* 437 */       writer.writeEndElement();
/*     */     } 
/* 439 */     this.bodyTag.writeStart(writer);
/* 440 */     if (hasPayload())
/* 441 */       writePayloadTo(writer); 
/* 442 */     writer.writeEndElement();
/* 443 */     writer.writeEndElement();
/* 444 */     writer.writeEndDocument();
/*     */   }
/*     */   
/*     */   public void writePayloadTo(ContentHandler contentHandler, ErrorHandler errorHandler, boolean fragment) throws SAXException {
/* 448 */     if (this.envelopeReader != null) readEnvelope(this); 
/* 449 */     assert unconsumed();
/*     */     
/*     */     try {
/* 452 */       if (this.payloadLocalName == null) {
/*     */         return;
/*     */       }
/* 455 */       if (this.bodyPrologue != null) {
/* 456 */         char[] chars = this.bodyPrologue.toCharArray();
/* 457 */         contentHandler.characters(chars, 0, chars.length);
/*     */       } 
/*     */       
/* 460 */       XMLStreamReaderToContentHandler conv = new XMLStreamReaderToContentHandler(this.reader, contentHandler, true, fragment, getInscopeNamespaces());
/*     */       
/* 462 */       while (this.reader.getEventType() != 8) {
/* 463 */         String name = this.reader.getLocalName();
/* 464 */         String nsUri = this.reader.getNamespaceURI();
/*     */ 
/*     */ 
/*     */         
/* 468 */         if (this.reader.getEventType() == 2) {
/*     */           
/* 470 */           if (!isBodyElement(name, nsUri)) {
/*     */ 
/*     */             
/* 473 */             String whiteSpaces = XMLStreamReaderUtil.nextWhiteSpaceContent(this.reader);
/* 474 */             if (whiteSpaces != null) {
/* 475 */               this.bodyEpilogue = whiteSpaces;
/*     */               
/* 477 */               char[] chars = whiteSpaces.toCharArray();
/* 478 */               contentHandler.characters(chars, 0, chars.length);
/*     */             } 
/*     */             
/*     */             continue;
/*     */           } 
/*     */           
/*     */           break;
/*     */         } 
/*     */         
/* 487 */         conv.bridge();
/*     */       } 
/*     */       
/* 490 */       XMLStreamReaderUtil.readRest(this.reader);
/* 491 */       XMLStreamReaderUtil.close(this.reader);
/* 492 */       XMLStreamReaderFactory.recycle(this.reader);
/* 493 */     } catch (XMLStreamException e) {
/* 494 */       Location loc = e.getLocation();
/* 495 */       if (loc == null) loc = DummyLocation.INSTANCE;
/*     */ 
/*     */       
/* 498 */       SAXParseException x = new SAXParseException(e.getMessage(), loc.getPublicId(), loc.getSystemId(), loc.getLineNumber(), loc.getColumnNumber(), e);
/* 499 */       errorHandler.error(x);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Message copy() {
/* 506 */     if (this.envelopeReader != null) readEnvelope(this); 
/*     */     try {
/* 508 */       assert unconsumed();
/* 509 */       this.consumedAt = null;
/* 510 */       MutableXMLStreamBuffer xsb = new MutableXMLStreamBuffer();
/* 511 */       StreamReaderBufferCreator c = new StreamReaderBufferCreator(xsb);
/*     */ 
/*     */ 
/*     */       
/* 515 */       c.storeElement(this.envelopeTag.nsUri, this.envelopeTag.localName, this.envelopeTag.prefix, this.envelopeTag.ns);
/* 516 */       c.storeElement(this.bodyTag.nsUri, this.bodyTag.localName, this.bodyTag.prefix, this.bodyTag.ns);
/*     */       
/* 518 */       if (hasPayload())
/*     */       {
/* 520 */         while (this.reader.getEventType() != 8) {
/* 521 */           String name = this.reader.getLocalName();
/* 522 */           String nsUri = this.reader.getNamespaceURI();
/* 523 */           if (isBodyElement(name, nsUri) || this.reader.getEventType() == 8)
/*     */             break; 
/* 525 */           c.create(this.reader);
/*     */ 
/*     */ 
/*     */           
/* 529 */           if (this.reader.isWhiteSpace()) {
/* 530 */             this.bodyEpilogue = XMLStreamReaderUtil.currentWhiteSpaceContent(this.reader);
/*     */             
/*     */             continue;
/*     */           } 
/* 534 */           this.bodyEpilogue = null;
/*     */         } 
/*     */       }
/*     */       
/* 538 */       c.storeEndElement();
/* 539 */       c.storeEndElement();
/* 540 */       c.storeEndElement();
/*     */       
/* 542 */       XMLStreamReaderUtil.readRest(this.reader);
/* 543 */       XMLStreamReaderUtil.close(this.reader);
/* 544 */       XMLStreamReaderFactory.recycle(this.reader);
/*     */       
/* 546 */       this.reader = (XMLStreamReader)xsb.readAsXMLStreamReader();
/* 547 */       StreamReaderBufferProcessor streamReaderBufferProcessor = xsb.readAsXMLStreamReader();
/*     */ 
/*     */       
/* 550 */       proceedToRootElement(this.reader);
/* 551 */       proceedToRootElement((XMLStreamReader)streamReaderBufferProcessor);
/*     */       
/* 553 */       return (Message)new StreamMessage(this.envelopeTag, this.headerTag, this.attachmentSet, (MessageHeaders)HeaderList.copy(this.headers), this.bodyPrologue, this.bodyTag, this.bodyEpilogue, (XMLStreamReader)streamReaderBufferProcessor, this.soapVersion);
/* 554 */     } catch (XMLStreamException e) {
/* 555 */       throw new WebServiceException("Failed to copy a message", e);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void proceedToRootElement(XMLStreamReader xsr) throws XMLStreamException {
/* 560 */     assert xsr.getEventType() == 7;
/* 561 */     xsr.nextTag();
/* 562 */     xsr.nextTag();
/* 563 */     xsr.nextTag();
/* 564 */     assert xsr.getEventType() == 1 || xsr.getEventType() == 2;
/*     */   }
/*     */   
/*     */   public void writeTo(ContentHandler contentHandler, ErrorHandler errorHandler) throws SAXException {
/* 568 */     if (this.envelopeReader != null) readEnvelope(this); 
/* 569 */     contentHandler.setDocumentLocator(NULL_LOCATOR);
/* 570 */     contentHandler.startDocument();
/* 571 */     this.envelopeTag.writeStart(contentHandler);
/* 572 */     if (hasHeaders() && this.headerTag == null) this.headerTag = new TagInfoset(this.envelopeTag.nsUri, "Header", this.envelopeTag.prefix, EMPTY_ATTS, new String[0]); 
/* 573 */     if (this.headerTag != null) {
/* 574 */       this.headerTag.writeStart(contentHandler);
/* 575 */       if (hasHeaders()) {
/* 576 */         MessageHeaders headers = getHeaders();
/* 577 */         for (Header h : headers.asList())
/*     */         {
/* 579 */           h.writeTo(contentHandler, errorHandler);
/*     */         }
/*     */       } 
/* 582 */       this.headerTag.writeEnd(contentHandler);
/*     */     } 
/* 584 */     this.bodyTag.writeStart(contentHandler);
/* 585 */     writePayloadTo(contentHandler, errorHandler, true);
/* 586 */     this.bodyTag.writeEnd(contentHandler);
/* 587 */     this.envelopeTag.writeEnd(contentHandler);
/* 588 */     contentHandler.endDocument();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean unconsumed() {
/* 599 */     if (this.payloadLocalName == null) {
/* 600 */       return true;
/*     */     }
/* 602 */     if (this.reader.getEventType() != 1) {
/* 603 */       AssertionError error = new AssertionError("StreamMessage has been already consumed. See the nested exception for where it's consumed");
/* 604 */       error.initCause(this.consumedAt);
/* 605 */       throw error;
/*     */     } 
/* 607 */     this.consumedAt = (new Exception()).fillInStackTrace();
/* 608 */     return true;
/*     */   }
/*     */   
/*     */   public String getBodyPrologue() {
/* 612 */     if (this.envelopeReader != null) readEnvelope(this); 
/* 613 */     return this.bodyPrologue;
/*     */   }
/*     */   
/*     */   public String getBodyEpilogue() {
/* 617 */     if (this.envelopeReader != null) readEnvelope(this); 
/* 618 */     return this.bodyEpilogue;
/*     */   }
/*     */   
/*     */   public XMLStreamReader getReader() {
/* 622 */     if (this.envelopeReader != null) readEnvelope(this); 
/* 623 */     assert unconsumed();
/* 624 */     return this.reader;
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
/* 636 */   static final StreamHeaderDecoder SOAP12StreamHeaderDecoder = new StreamHeaderDecoder()
/*     */     {
/*     */       public Header decodeHeader(XMLStreamReader reader, XMLStreamBuffer mark) {
/* 639 */         return (Header)new StreamHeader12(reader, mark);
/*     */       }
/*     */     };
/*     */   
/* 643 */   static final StreamHeaderDecoder SOAP11StreamHeaderDecoder = new StreamHeaderDecoder()
/*     */     {
/*     */       public Header decodeHeader(XMLStreamReader reader, XMLStreamBuffer mark) {
/* 646 */         return (Header)new StreamHeader11(reader, mark);
/*     */       }
/*     */     };
/*     */   
/*     */   private static void readEnvelope(StreamMessage message) {
/* 651 */     if (message.envelopeReader == null)
/* 652 */       return;  XMLStreamReader reader = message.envelopeReader;
/* 653 */     message.envelopeReader = null;
/* 654 */     SOAPVersion soapVersion = message.soapVersion;
/*     */     
/* 656 */     if (reader.getEventType() != 1)
/* 657 */       XMLStreamReaderUtil.nextElementContent(reader); 
/* 658 */     XMLStreamReaderUtil.verifyReaderState(reader, 1);
/* 659 */     if ("Envelope".equals(reader.getLocalName()) && !soapVersion.nsUri.equals(reader.getNamespaceURI())) {
/* 660 */       throw new VersionMismatchException(soapVersion, new Object[] { soapVersion.nsUri, reader.getNamespaceURI() });
/*     */     }
/* 662 */     XMLStreamReaderUtil.verifyTag(reader, soapVersion.nsUri, "Envelope");
/*     */     
/* 664 */     TagInfoset envelopeTag = new TagInfoset(reader);
/*     */ 
/*     */     
/* 667 */     Map<String, String> namespaces = new HashMap<>();
/* 668 */     for (int i = 0; i < reader.getNamespaceCount(); i++) {
/* 669 */       namespaces.put(reader.getNamespacePrefix(i), reader.getNamespaceURI(i));
/*     */     }
/*     */ 
/*     */     
/* 673 */     XMLStreamReaderUtil.nextElementContent(reader);
/* 674 */     XMLStreamReaderUtil.verifyReaderState(reader, 1);
/*     */ 
/*     */     
/* 677 */     HeaderList headers = null;
/* 678 */     TagInfoset headerTag = null;
/*     */     
/* 680 */     if (reader.getLocalName().equals("Header") && reader
/* 681 */       .getNamespaceURI().equals(soapVersion.nsUri)) {
/* 682 */       headerTag = new TagInfoset(reader);
/*     */ 
/*     */       
/* 685 */       for (int j = 0; j < reader.getNamespaceCount(); j++) {
/* 686 */         namespaces.put(reader.getNamespacePrefix(j), reader.getNamespaceURI(j));
/*     */       }
/*     */       
/* 689 */       XMLStreamReaderUtil.nextElementContent(reader);
/*     */ 
/*     */       
/* 692 */       if (reader.getEventType() == 1) {
/* 693 */         headers = new HeaderList(soapVersion);
/*     */ 
/*     */         
/*     */         try {
/* 697 */           StreamHeaderDecoder headerDecoder = SOAPVersion.SOAP_11.equals(soapVersion) ? SOAP11StreamHeaderDecoder : SOAP12StreamHeaderDecoder;
/* 698 */           cacheHeaders(reader, namespaces, headers, headerDecoder);
/* 699 */         } catch (XMLStreamException e) {
/*     */           
/* 701 */           throw new WebServiceException(e);
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 706 */       XMLStreamReaderUtil.nextElementContent(reader);
/*     */     } 
/*     */ 
/*     */     
/* 710 */     XMLStreamReaderUtil.verifyTag(reader, soapVersion.nsUri, "Body");
/* 711 */     TagInfoset bodyTag = new TagInfoset(reader);
/*     */     
/* 713 */     String bodyPrologue = XMLStreamReaderUtil.nextWhiteSpaceContent(reader);
/* 714 */     message.init(envelopeTag, headerTag, message.attachmentSet, (MessageHeaders)headers, bodyPrologue, bodyTag, (String)null, reader, soapVersion);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static XMLStreamBuffer cacheHeaders(XMLStreamReader reader, Map<String, String> namespaces, HeaderList headers, StreamHeaderDecoder headerDecoder) throws XMLStreamException {
/* 724 */     MutableXMLStreamBuffer buffer = createXMLStreamBuffer();
/* 725 */     StreamReaderBufferCreator creator = new StreamReaderBufferCreator();
/* 726 */     creator.setXMLStreamBuffer(buffer);
/*     */ 
/*     */     
/* 729 */     while (reader.getEventType() == 1) {
/* 730 */       Map<String, String> headerBlockNamespaces = namespaces;
/*     */ 
/*     */       
/* 733 */       if (reader.getNamespaceCount() > 0) {
/* 734 */         headerBlockNamespaces = new HashMap<>(namespaces);
/* 735 */         for (int i = 0; i < reader.getNamespaceCount(); i++) {
/* 736 */           headerBlockNamespaces.put(reader.getNamespacePrefix(i), reader.getNamespaceURI(i));
/*     */         }
/*     */       } 
/*     */ 
/*     */       
/* 741 */       XMLStreamBufferMark xMLStreamBufferMark = new XMLStreamBufferMark(headerBlockNamespaces, (AbstractCreatorProcessor)creator);
/*     */       
/* 743 */       headers.add(headerDecoder.decodeHeader(reader, (XMLStreamBuffer)xMLStreamBufferMark));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 749 */       creator.createElementFragment(reader, false);
/* 750 */       if (reader.getEventType() != 1 && reader
/* 751 */         .getEventType() != 2) {
/* 752 */         XMLStreamReaderUtil.nextElementContent(reader);
/*     */       }
/*     */     } 
/*     */     
/* 756 */     return (XMLStreamBuffer)buffer;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static MutableXMLStreamBuffer createXMLStreamBuffer() {
/* 764 */     return new MutableXMLStreamBuffer();
/*     */   }
/*     */   
/*     */   protected static interface StreamHeaderDecoder {
/*     */     Header decodeHeader(XMLStreamReader param1XMLStreamReader, XMLStreamBuffer param1XMLStreamBuffer);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/message/stream/StreamMessage.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */