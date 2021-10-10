/*     */ package com.sun.xml.internal.ws.message.saaj;
/*     */ 
/*     */ import com.sun.istack.internal.FragmentContentHandler;
/*     */ import com.sun.istack.internal.NotNull;
/*     */ import com.sun.istack.internal.Nullable;
/*     */ import com.sun.istack.internal.XMLStreamException2;
/*     */ import com.sun.xml.internal.bind.api.Bridge;
/*     */ import com.sun.xml.internal.bind.unmarshaller.DOMScanner;
/*     */ import com.sun.xml.internal.ws.api.SOAPVersion;
/*     */ import com.sun.xml.internal.ws.api.message.Attachment;
/*     */ import com.sun.xml.internal.ws.api.message.AttachmentEx;
/*     */ import com.sun.xml.internal.ws.api.message.AttachmentSet;
/*     */ import com.sun.xml.internal.ws.api.message.Header;
/*     */ import com.sun.xml.internal.ws.api.message.HeaderList;
/*     */ import com.sun.xml.internal.ws.api.message.Message;
/*     */ import com.sun.xml.internal.ws.api.message.MessageHeaders;
/*     */ import com.sun.xml.internal.ws.message.AttachmentUnmarshallerImpl;
/*     */ import com.sun.xml.internal.ws.spi.db.XMLBridge;
/*     */ import com.sun.xml.internal.ws.streaming.DOMStreamReader;
/*     */ import com.sun.xml.internal.ws.util.ASCIIUtility;
/*     */ import com.sun.xml.internal.ws.util.DOMUtil;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import javax.activation.DataHandler;
/*     */ import javax.xml.bind.JAXBException;
/*     */ import javax.xml.bind.Unmarshaller;
/*     */ import javax.xml.bind.attachment.AttachmentUnmarshaller;
/*     */ import javax.xml.soap.AttachmentPart;
/*     */ import javax.xml.soap.MimeHeader;
/*     */ import javax.xml.soap.SOAPBody;
/*     */ import javax.xml.soap.SOAPElement;
/*     */ import javax.xml.soap.SOAPEnvelope;
/*     */ import javax.xml.soap.SOAPException;
/*     */ import javax.xml.soap.SOAPHeader;
/*     */ import javax.xml.soap.SOAPHeaderElement;
/*     */ import javax.xml.soap.SOAPMessage;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import javax.xml.stream.XMLStreamReader;
/*     */ import javax.xml.stream.XMLStreamWriter;
/*     */ import javax.xml.transform.Source;
/*     */ import javax.xml.transform.dom.DOMSource;
/*     */ import javax.xml.transform.stream.StreamSource;
/*     */ import javax.xml.ws.WebServiceException;
/*     */ import org.w3c.dom.Attr;
/*     */ import org.w3c.dom.Document;
/*     */ import org.w3c.dom.Element;
/*     */ import org.w3c.dom.NamedNodeMap;
/*     */ import org.w3c.dom.Node;
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
/*     */ public class SAAJMessage
/*     */   extends Message
/*     */ {
/*     */   private boolean parsedMessage;
/*     */   private boolean accessedMessage;
/*     */   private final SOAPMessage sm;
/*     */   private MessageHeaders headers;
/*     */   private List<Element> bodyParts;
/*     */   private Element payload;
/*     */   private String payloadLocalName;
/*     */   private String payloadNamespace;
/*     */   private SOAPVersion soapVersion;
/*     */   private NamedNodeMap bodyAttrs;
/*     */   private NamedNodeMap headerAttrs;
/*     */   private NamedNodeMap envelopeAttrs;
/*     */   
/*     */   public SAAJMessage(SOAPMessage sm) {
/*  96 */     this.sm = sm;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private SAAJMessage(MessageHeaders headers, AttachmentSet as, SOAPMessage sm, SOAPVersion version) {
/*     */     HeaderList headerList;
/* 106 */     this.sm = sm;
/* 107 */     parse();
/* 108 */     if (headers == null)
/* 109 */       headerList = new HeaderList(version); 
/* 110 */     this.headers = (MessageHeaders)headerList;
/* 111 */     this.attachmentSet = as;
/*     */   }
/*     */   
/*     */   private void parse() {
/* 115 */     if (!this.parsedMessage) {
/*     */       try {
/* 117 */         access();
/* 118 */         if (this.headers == null)
/* 119 */           this.headers = (MessageHeaders)new HeaderList(getSOAPVersion()); 
/* 120 */         SOAPHeader header = this.sm.getSOAPHeader();
/* 121 */         if (header != null) {
/* 122 */           this.headerAttrs = header.getAttributes();
/* 123 */           Iterator<SOAPHeaderElement> iter = header.examineAllHeaderElements();
/* 124 */           while (iter.hasNext()) {
/* 125 */             this.headers.add((Header)new SAAJHeader(iter.next()));
/*     */           }
/*     */         } 
/* 128 */         this.attachmentSet = new SAAJAttachmentSet(this.sm);
/*     */         
/* 130 */         this.parsedMessage = true;
/* 131 */       } catch (SOAPException e) {
/* 132 */         throw new WebServiceException(e);
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   protected void access() {
/* 138 */     if (!this.accessedMessage) {
/*     */       try {
/* 140 */         this.envelopeAttrs = this.sm.getSOAPPart().getEnvelope().getAttributes();
/* 141 */         SOAPBody sOAPBody = this.sm.getSOAPBody();
/* 142 */         this.bodyAttrs = sOAPBody.getAttributes();
/* 143 */         this.soapVersion = SOAPVersion.fromNsUri(sOAPBody.getNamespaceURI());
/*     */         
/* 145 */         this.bodyParts = DOMUtil.getChildElements((Node)sOAPBody);
/*     */         
/* 147 */         this.payload = (this.bodyParts.size() > 0) ? this.bodyParts.get(0) : null;
/*     */ 
/*     */ 
/*     */         
/* 151 */         if (this.payload != null) {
/* 152 */           this.payloadLocalName = this.payload.getLocalName();
/* 153 */           this.payloadNamespace = this.payload.getNamespaceURI();
/*     */         } 
/* 155 */         this.accessedMessage = true;
/* 156 */       } catch (SOAPException e) {
/* 157 */         throw new WebServiceException(e);
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean hasHeaders() {
/* 163 */     parse();
/* 164 */     return this.headers.hasHeaders();
/*     */   }
/*     */   @NotNull
/*     */   public MessageHeaders getHeaders() {
/* 168 */     parse();
/* 169 */     return this.headers;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public AttachmentSet getAttachments() {
/* 178 */     if (this.attachmentSet == null) this.attachmentSet = new SAAJAttachmentSet(this.sm); 
/* 179 */     return this.attachmentSet;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean hasAttachments() {
/* 188 */     return !getAttachments().isEmpty();
/*     */   }
/*     */   @Nullable
/*     */   public String getPayloadLocalPart() {
/* 192 */     soapBodyFirstChild();
/* 193 */     return this.payloadLocalName;
/*     */   }
/*     */   
/*     */   public String getPayloadNamespaceURI() {
/* 197 */     soapBodyFirstChild();
/* 198 */     return this.payloadNamespace;
/*     */   }
/*     */   
/*     */   public boolean hasPayload() {
/* 202 */     return (soapBodyFirstChild() != null);
/*     */   }
/*     */   
/*     */   private void addAttributes(Element e, NamedNodeMap attrs) {
/* 206 */     if (attrs == null)
/*     */       return; 
/* 208 */     String elPrefix = e.getPrefix();
/* 209 */     for (int i = 0; i < attrs.getLength(); i++) {
/* 210 */       Attr a = (Attr)attrs.item(i);
/*     */       
/* 212 */       if ("xmlns".equals(a.getPrefix()) || "xmlns".equals(a.getLocalName())) {
/* 213 */         if (elPrefix != null || !a.getLocalName().equals("xmlns"))
/*     */         {
/*     */           
/* 216 */           if (elPrefix == null || !"xmlns".equals(a.getPrefix()) || !elPrefix.equals(a.getLocalName()))
/*     */           {
/*     */ 
/*     */             
/* 220 */             e.setAttributeNS(a.getNamespaceURI(), a.getName(), a.getValue()); } 
/*     */         }
/*     */       } else {
/* 223 */         e.setAttributeNS(a.getNamespaceURI(), a.getName(), a.getValue());
/*     */       } 
/*     */     } 
/*     */   }
/*     */   public Source readEnvelopeAsSource() {
/*     */     try {
/* 229 */       if (!this.parsedMessage) {
/* 230 */         SOAPEnvelope sOAPEnvelope = this.sm.getSOAPPart().getEnvelope();
/* 231 */         return new DOMSource((Node)sOAPEnvelope);
/*     */       } 
/*     */       
/* 234 */       SOAPMessage msg = this.soapVersion.getMessageFactory().createMessage();
/* 235 */       addAttributes((Element)msg.getSOAPPart().getEnvelope(), this.envelopeAttrs);
/*     */       
/* 237 */       SOAPBody newBody = msg.getSOAPPart().getEnvelope().getBody();
/* 238 */       addAttributes((Element)newBody, this.bodyAttrs);
/* 239 */       for (Element part : this.bodyParts) {
/* 240 */         Node n = newBody.getOwnerDocument().importNode(part, true);
/* 241 */         newBody.appendChild(n);
/*     */       } 
/* 243 */       addAttributes((Element)msg.getSOAPHeader(), this.headerAttrs);
/* 244 */       for (Header header : this.headers.asList()) {
/* 245 */         header.writeTo(msg);
/*     */       }
/* 247 */       SOAPEnvelope se = msg.getSOAPPart().getEnvelope();
/* 248 */       return new DOMSource((Node)se);
/*     */     }
/* 250 */     catch (SOAPException e) {
/* 251 */       throw new WebServiceException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public SOAPMessage readAsSOAPMessage() throws SOAPException {
/* 256 */     if (!this.parsedMessage) {
/* 257 */       return this.sm;
/*     */     }
/* 259 */     SOAPMessage msg = this.soapVersion.getMessageFactory().createMessage();
/* 260 */     addAttributes((Element)msg.getSOAPPart().getEnvelope(), this.envelopeAttrs);
/* 261 */     SOAPBody newBody = msg.getSOAPPart().getEnvelope().getBody();
/* 262 */     addAttributes((Element)newBody, this.bodyAttrs);
/* 263 */     for (Element part : this.bodyParts) {
/* 264 */       Node n = newBody.getOwnerDocument().importNode(part, true);
/* 265 */       newBody.appendChild(n);
/*     */     } 
/* 267 */     addAttributes((Element)msg.getSOAPHeader(), this.headerAttrs);
/* 268 */     for (Header header : this.headers.asList()) {
/* 269 */       header.writeTo(msg);
/*     */     }
/* 271 */     for (Attachment att : getAttachments()) {
/* 272 */       AttachmentPart part = msg.createAttachmentPart();
/* 273 */       part.setDataHandler(att.asDataHandler());
/* 274 */       part.setContentId('<' + att.getContentId() + '>');
/* 275 */       addCustomMimeHeaders(att, part);
/* 276 */       msg.addAttachmentPart(part);
/*     */     } 
/* 278 */     msg.saveChanges();
/* 279 */     return msg;
/*     */   }
/*     */ 
/*     */   
/*     */   private void addCustomMimeHeaders(Attachment att, AttachmentPart part) {
/* 284 */     if (att instanceof AttachmentEx) {
/* 285 */       Iterator<AttachmentEx.MimeHeader> allMimeHeaders = ((AttachmentEx)att).getMimeHeaders();
/* 286 */       while (allMimeHeaders.hasNext()) {
/* 287 */         AttachmentEx.MimeHeader mh = allMimeHeaders.next();
/* 288 */         String name = mh.getName();
/* 289 */         if (!"Content-Type".equalsIgnoreCase(name) && 
/* 290 */           !"Content-Id".equalsIgnoreCase(name)) {
/* 291 */           part.addMimeHeader(name, mh.getValue());
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public Source readPayloadAsSource() {
/* 298 */     access();
/* 299 */     return (this.payload != null) ? new DOMSource(this.payload) : null;
/*     */   }
/*     */   
/*     */   public <T> T readPayloadAsJAXB(Unmarshaller unmarshaller) throws JAXBException {
/* 303 */     access();
/* 304 */     if (this.payload != null) {
/* 305 */       if (hasAttachments())
/* 306 */         unmarshaller.setAttachmentUnmarshaller((AttachmentUnmarshaller)new AttachmentUnmarshallerImpl(getAttachments())); 
/* 307 */       return (T)unmarshaller.unmarshal(this.payload);
/*     */     } 
/*     */     
/* 310 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public <T> T readPayloadAsJAXB(Bridge<T> bridge) throws JAXBException {
/* 315 */     access();
/* 316 */     if (this.payload != null)
/* 317 */       return (T)bridge.unmarshal(this.payload, hasAttachments() ? (AttachmentUnmarshaller)new AttachmentUnmarshallerImpl(getAttachments()) : null); 
/* 318 */     return null;
/*     */   }
/*     */   public <T> T readPayloadAsJAXB(XMLBridge<T> bridge) throws JAXBException {
/* 321 */     access();
/* 322 */     if (this.payload != null)
/* 323 */       return (T)bridge.unmarshal(this.payload, hasAttachments() ? (AttachmentUnmarshaller)new AttachmentUnmarshallerImpl(getAttachments()) : null); 
/* 324 */     return null;
/*     */   }
/*     */   
/*     */   public XMLStreamReader readPayload() throws XMLStreamException {
/* 328 */     return soapBodyFirstChildReader();
/*     */   }
/*     */   
/*     */   public void writePayloadTo(XMLStreamWriter sw) throws XMLStreamException {
/* 332 */     access();
/*     */     try {
/* 334 */       for (Element part : this.bodyParts)
/* 335 */         DOMUtil.serializeNode(part, sw); 
/* 336 */     } catch (XMLStreamException e) {
/* 337 */       throw new WebServiceException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void writeTo(XMLStreamWriter writer) throws XMLStreamException {
/*     */     try {
/* 343 */       writer.writeStartDocument();
/* 344 */       if (!this.parsedMessage) {
/* 345 */         DOMUtil.serializeNode((Element)this.sm.getSOAPPart().getEnvelope(), writer);
/*     */       } else {
/* 347 */         SOAPEnvelope env = this.sm.getSOAPPart().getEnvelope();
/* 348 */         DOMUtil.writeTagWithAttributes((Element)env, writer);
/* 349 */         if (hasHeaders()) {
/* 350 */           if (env.getHeader() != null) {
/* 351 */             DOMUtil.writeTagWithAttributes((Element)env.getHeader(), writer);
/*     */           } else {
/* 353 */             writer.writeStartElement(env.getPrefix(), "Header", env.getNamespaceURI());
/*     */           } 
/* 355 */           for (Header h : this.headers.asList()) {
/* 356 */             h.writeTo(writer);
/*     */           }
/* 358 */           writer.writeEndElement();
/*     */         } 
/*     */         
/* 361 */         DOMUtil.serializeNode((Element)this.sm.getSOAPBody(), writer);
/* 362 */         writer.writeEndElement();
/*     */       } 
/* 364 */       writer.writeEndDocument();
/* 365 */       writer.flush();
/* 366 */     } catch (SOAPException ex) {
/* 367 */       throw new XMLStreamException2(ex);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeTo(ContentHandler contentHandler, ErrorHandler errorHandler) throws SAXException {
/* 373 */     String soapNsUri = this.soapVersion.nsUri;
/* 374 */     if (!this.parsedMessage) {
/* 375 */       DOMScanner ds = new DOMScanner();
/* 376 */       ds.setContentHandler(contentHandler);
/* 377 */       ds.scan((Document)this.sm.getSOAPPart());
/*     */     } else {
/* 379 */       contentHandler.setDocumentLocator(NULL_LOCATOR);
/* 380 */       contentHandler.startDocument();
/* 381 */       contentHandler.startPrefixMapping("S", soapNsUri);
/* 382 */       startPrefixMapping(contentHandler, this.envelopeAttrs, "S");
/* 383 */       contentHandler.startElement(soapNsUri, "Envelope", "S:Envelope", getAttributes(this.envelopeAttrs));
/* 384 */       if (hasHeaders()) {
/* 385 */         startPrefixMapping(contentHandler, this.headerAttrs, "S");
/* 386 */         contentHandler.startElement(soapNsUri, "Header", "S:Header", getAttributes(this.headerAttrs));
/* 387 */         MessageHeaders headers = getHeaders();
/* 388 */         for (Header h : headers.asList()) {
/* 389 */           h.writeTo(contentHandler, errorHandler);
/*     */         }
/* 391 */         endPrefixMapping(contentHandler, this.headerAttrs, "S");
/* 392 */         contentHandler.endElement(soapNsUri, "Header", "S:Header");
/*     */       } 
/*     */       
/* 395 */       startPrefixMapping(contentHandler, this.bodyAttrs, "S");
/*     */       
/* 397 */       contentHandler.startElement(soapNsUri, "Body", "S:Body", getAttributes(this.bodyAttrs));
/* 398 */       writePayloadTo(contentHandler, errorHandler, true);
/* 399 */       endPrefixMapping(contentHandler, this.bodyAttrs, "S");
/* 400 */       contentHandler.endElement(soapNsUri, "Body", "S:Body");
/* 401 */       endPrefixMapping(contentHandler, this.envelopeAttrs, "S");
/* 402 */       contentHandler.endElement(soapNsUri, "Envelope", "S:Envelope");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private AttributesImpl getAttributes(NamedNodeMap attrs) {
/* 411 */     AttributesImpl atts = new AttributesImpl();
/* 412 */     if (attrs == null)
/* 413 */       return EMPTY_ATTS; 
/* 414 */     for (int i = 0; i < attrs.getLength(); i++) {
/* 415 */       Attr a = (Attr)attrs.item(i);
/*     */       
/* 417 */       if (!"xmlns".equals(a.getPrefix()) && !"xmlns".equals(a.getLocalName()))
/*     */       {
/*     */         
/* 420 */         atts.addAttribute(fixNull(a.getNamespaceURI()), a.getLocalName(), a.getName(), a.getSchemaTypeInfo().getTypeName(), a.getValue()); } 
/*     */     } 
/* 422 */     return atts;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void startPrefixMapping(ContentHandler contentHandler, NamedNodeMap attrs, String excludePrefix) throws SAXException {
/* 433 */     if (attrs == null)
/*     */       return; 
/* 435 */     for (int i = 0; i < attrs.getLength(); i++) {
/* 436 */       Attr a = (Attr)attrs.item(i);
/*     */       
/* 438 */       if (("xmlns".equals(a.getPrefix()) || "xmlns".equals(a.getLocalName())) && 
/* 439 */         !fixNull(a.getPrefix()).equals(excludePrefix)) {
/* 440 */         contentHandler.startPrefixMapping(fixNull(a.getPrefix()), a.getNamespaceURI());
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void endPrefixMapping(ContentHandler contentHandler, NamedNodeMap attrs, String excludePrefix) throws SAXException {
/* 447 */     if (attrs == null)
/*     */       return; 
/* 449 */     for (int i = 0; i < attrs.getLength(); i++) {
/* 450 */       Attr a = (Attr)attrs.item(i);
/*     */       
/* 452 */       if (("xmlns".equals(a.getPrefix()) || "xmlns".equals(a.getLocalName())) && 
/* 453 */         !fixNull(a.getPrefix()).equals(excludePrefix)) {
/* 454 */         contentHandler.endPrefixMapping(fixNull(a.getPrefix()));
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static String fixNull(String s) {
/* 461 */     if (s == null) return ""; 
/* 462 */     return s;
/*     */   }
/*     */   private void writePayloadTo(ContentHandler contentHandler, ErrorHandler errorHandler, boolean fragment) throws SAXException {
/*     */     FragmentContentHandler fragmentContentHandler;
/* 466 */     if (fragment)
/* 467 */       fragmentContentHandler = new FragmentContentHandler(contentHandler); 
/* 468 */     DOMScanner ds = new DOMScanner();
/* 469 */     ds.setContentHandler((ContentHandler)fragmentContentHandler);
/* 470 */     ds.scan(this.payload);
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
/*     */   public Message copy() {
/*     */     try {
/* 496 */       if (!this.parsedMessage) {
/* 497 */         return new SAAJMessage(readAsSOAPMessage());
/*     */       }
/* 499 */       SOAPMessage msg = this.soapVersion.getMessageFactory().createMessage();
/* 500 */       SOAPBody newBody = msg.getSOAPPart().getEnvelope().getBody();
/* 501 */       for (Element part : this.bodyParts) {
/* 502 */         Node n = newBody.getOwnerDocument().importNode(part, true);
/* 503 */         newBody.appendChild(n);
/*     */       } 
/* 505 */       addAttributes((Element)newBody, this.bodyAttrs);
/* 506 */       return new SAAJMessage(getHeaders(), getAttachments(), msg, this.soapVersion);
/*     */     }
/* 508 */     catch (SOAPException e) {
/* 509 */       throw new WebServiceException(e);
/*     */     } 
/*     */   }
/* 512 */   private static final AttributesImpl EMPTY_ATTS = new AttributesImpl();
/* 513 */   private static final LocatorImpl NULL_LOCATOR = new LocatorImpl();
/*     */   private XMLStreamReader soapBodyFirstChildReader;
/*     */   private SOAPElement soapBodyFirstChild;
/*     */   
/*     */   protected static class SAAJAttachment implements AttachmentEx {
/*     */     final AttachmentPart ap;
/*     */     String contentIdNoAngleBracket;
/*     */     
/*     */     public SAAJAttachment(AttachmentPart part) {
/* 522 */       this.ap = part;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String getContentId() {
/* 529 */       if (this.contentIdNoAngleBracket == null) {
/* 530 */         this.contentIdNoAngleBracket = this.ap.getContentId();
/* 531 */         if (this.contentIdNoAngleBracket != null && this.contentIdNoAngleBracket.charAt(0) == '<')
/* 532 */           this.contentIdNoAngleBracket = this.contentIdNoAngleBracket.substring(1, this.contentIdNoAngleBracket.length() - 1); 
/*     */       } 
/* 534 */       return this.contentIdNoAngleBracket;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String getContentType() {
/* 541 */       return this.ap.getContentType();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public byte[] asByteArray() {
/*     */       try {
/* 549 */         return this.ap.getRawContentBytes();
/* 550 */       } catch (SOAPException e) {
/* 551 */         throw new WebServiceException(e);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public DataHandler asDataHandler() {
/*     */       try {
/* 560 */         return this.ap.getDataHandler();
/* 561 */       } catch (SOAPException e) {
/* 562 */         throw new WebServiceException(e);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Source asSource() {
/*     */       try {
/* 572 */         return new StreamSource(this.ap.getRawContent());
/* 573 */       } catch (SOAPException e) {
/* 574 */         throw new WebServiceException(e);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public InputStream asInputStream() {
/*     */       try {
/* 583 */         return this.ap.getRawContent();
/* 584 */       } catch (SOAPException e) {
/* 585 */         throw new WebServiceException(e);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void writeTo(OutputStream os) throws IOException {
/*     */       try {
/* 594 */         ASCIIUtility.copyStream(this.ap.getRawContent(), os);
/* 595 */       } catch (SOAPException e) {
/* 596 */         throw new WebServiceException(e);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void writeTo(SOAPMessage saaj) {
/* 604 */       saaj.addAttachmentPart(this.ap);
/*     */     }
/*     */     
/*     */     AttachmentPart asAttachmentPart() {
/* 608 */       return this.ap;
/*     */     }
/*     */     
/*     */     public Iterator<AttachmentEx.MimeHeader> getMimeHeaders() {
/* 612 */       final Iterator it = this.ap.getAllMimeHeaders();
/* 613 */       return new Iterator<AttachmentEx.MimeHeader>() {
/*     */           public boolean hasNext() {
/* 615 */             return it.hasNext();
/*     */           }
/*     */           
/*     */           public AttachmentEx.MimeHeader next() {
/* 619 */             final MimeHeader mh = it.next();
/* 620 */             return new AttachmentEx.MimeHeader() {
/*     */                 public String getName() {
/* 622 */                   return mh.getName();
/*     */                 }
/*     */                 
/*     */                 public String getValue() {
/* 626 */                   return mh.getValue();
/*     */                 }
/*     */               };
/*     */           }
/*     */           
/*     */           public void remove() {
/* 632 */             throw new UnsupportedOperationException();
/*     */           }
/*     */         };
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected static class SAAJAttachmentSet
/*     */     implements AttachmentSet
/*     */   {
/*     */     private Map<String, Attachment> attMap;
/*     */ 
/*     */     
/*     */     private Iterator attIter;
/*     */ 
/*     */     
/*     */     public SAAJAttachmentSet(SOAPMessage sm) {
/* 650 */       this.attIter = sm.getAttachments();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Attachment get(String contentId) {
/* 661 */       if (this.attMap == null) {
/* 662 */         if (!this.attIter.hasNext())
/* 663 */           return null; 
/* 664 */         this.attMap = createAttachmentMap();
/*     */       } 
/* 666 */       if (contentId.charAt(0) != '<') {
/* 667 */         return this.attMap.get('<' + contentId + '>');
/*     */       }
/* 669 */       return this.attMap.get(contentId);
/*     */     }
/*     */     
/*     */     public boolean isEmpty() {
/* 673 */       if (this.attMap != null) {
/* 674 */         return this.attMap.isEmpty();
/*     */       }
/* 676 */       return !this.attIter.hasNext();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Iterator<Attachment> iterator() {
/* 685 */       if (this.attMap == null) {
/* 686 */         this.attMap = createAttachmentMap();
/*     */       }
/* 688 */       return this.attMap.values().iterator();
/*     */     }
/*     */     
/*     */     private Map<String, Attachment> createAttachmentMap() {
/* 692 */       HashMap<String, Attachment> map = new HashMap<>();
/* 693 */       while (this.attIter.hasNext()) {
/* 694 */         AttachmentPart ap = this.attIter.next();
/* 695 */         map.put(ap.getContentId(), new SAAJMessage.SAAJAttachment(ap));
/*     */       } 
/* 697 */       return map;
/*     */     }
/*     */     
/*     */     public void add(Attachment att) {
/* 701 */       this.attMap.put('<' + att.getContentId() + '>', att);
/*     */     }
/*     */   }
/*     */   
/*     */   public SOAPVersion getSOAPVersion() {
/* 706 */     return this.soapVersion;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected XMLStreamReader getXMLStreamReader(SOAPElement soapElement) {
/* 715 */     return null;
/*     */   }
/*     */   
/*     */   protected XMLStreamReader createXMLStreamReader(SOAPElement soapElement) {
/* 719 */     DOMStreamReader dss = new DOMStreamReader();
/* 720 */     dss.setCurrentNode((Node)soapElement);
/* 721 */     return (XMLStreamReader)dss;
/*     */   }
/*     */   
/*     */   protected XMLStreamReader soapBodyFirstChildReader() {
/* 725 */     if (this.soapBodyFirstChildReader != null) return this.soapBodyFirstChildReader; 
/* 726 */     soapBodyFirstChild();
/* 727 */     if (this.soapBodyFirstChild != null) {
/* 728 */       this.soapBodyFirstChildReader = getXMLStreamReader(this.soapBodyFirstChild);
/* 729 */       if (this.soapBodyFirstChildReader == null) this
/* 730 */           .soapBodyFirstChildReader = createXMLStreamReader(this.soapBodyFirstChild); 
/* 731 */       if (this.soapBodyFirstChildReader.getEventType() == 7) {
/*     */         try {
/* 733 */           while (this.soapBodyFirstChildReader.getEventType() != 1)
/* 734 */             this.soapBodyFirstChildReader.next(); 
/* 735 */         } catch (XMLStreamException e) {
/* 736 */           throw new RuntimeException(e);
/*     */         } 
/*     */       }
/* 739 */       return this.soapBodyFirstChildReader;
/*     */     } 
/* 741 */     this.payloadLocalName = null;
/* 742 */     this.payloadNamespace = null;
/* 743 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   SOAPElement soapBodyFirstChild() {
/* 750 */     if (this.soapBodyFirstChild != null) return this.soapBodyFirstChild; 
/*     */     try {
/* 752 */       boolean foundElement = false;
/* 753 */       for (Node n = this.sm.getSOAPBody().getFirstChild(); n != null && !foundElement; n = n.getNextSibling()) {
/* 754 */         if (n.getNodeType() == 1) {
/* 755 */           foundElement = true;
/* 756 */           if (n instanceof SOAPElement) {
/* 757 */             this.soapBodyFirstChild = (SOAPElement)n;
/* 758 */             this.payloadLocalName = this.soapBodyFirstChild.getLocalName();
/* 759 */             this.payloadNamespace = this.soapBodyFirstChild.getNamespaceURI();
/* 760 */             return this.soapBodyFirstChild;
/*     */           } 
/*     */         } 
/*     */       } 
/* 764 */       if (foundElement) for (Iterator i = this.sm.getSOAPBody().getChildElements(); i.hasNext(); ) {
/* 765 */           Object o = i.next();
/* 766 */           if (o instanceof SOAPElement) {
/* 767 */             this.soapBodyFirstChild = (SOAPElement)o;
/* 768 */             this.payloadLocalName = this.soapBodyFirstChild.getLocalName();
/* 769 */             this.payloadNamespace = this.soapBodyFirstChild.getNamespaceURI();
/* 770 */             return this.soapBodyFirstChild;
/*     */           } 
/*     */         }  
/* 773 */     } catch (SOAPException e) {
/* 774 */       throw new RuntimeException(e);
/*     */     } 
/* 776 */     return this.soapBodyFirstChild;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/message/saaj/SAAJMessage.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */