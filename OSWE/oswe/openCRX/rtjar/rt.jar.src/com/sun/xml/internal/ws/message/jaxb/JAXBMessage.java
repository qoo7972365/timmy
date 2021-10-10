/*     */ package com.sun.xml.internal.ws.message.jaxb;
/*     */ 
/*     */ import com.sun.istack.internal.FragmentContentHandler;
/*     */ import com.sun.xml.internal.stream.buffer.MutableXMLStreamBuffer;
/*     */ import com.sun.xml.internal.stream.buffer.XMLStreamBuffer;
/*     */ import com.sun.xml.internal.stream.buffer.XMLStreamBufferResult;
/*     */ import com.sun.xml.internal.stream.buffer.stax.StreamReaderBufferProcessor;
/*     */ import com.sun.xml.internal.ws.api.SOAPVersion;
/*     */ import com.sun.xml.internal.ws.api.message.AttachmentSet;
/*     */ import com.sun.xml.internal.ws.api.message.Header;
/*     */ import com.sun.xml.internal.ws.api.message.HeaderList;
/*     */ import com.sun.xml.internal.ws.api.message.Message;
/*     */ import com.sun.xml.internal.ws.api.message.MessageHeaders;
/*     */ import com.sun.xml.internal.ws.api.message.StreamingSOAP;
/*     */ import com.sun.xml.internal.ws.message.AbstractMessageImpl;
/*     */ import com.sun.xml.internal.ws.message.AttachmentSetImpl;
/*     */ import com.sun.xml.internal.ws.message.RootElementSniffer;
/*     */ import com.sun.xml.internal.ws.message.stream.StreamMessage;
/*     */ import com.sun.xml.internal.ws.spi.db.BindingContext;
/*     */ import com.sun.xml.internal.ws.spi.db.BindingContextFactory;
/*     */ import com.sun.xml.internal.ws.spi.db.XMLBridge;
/*     */ import com.sun.xml.internal.ws.streaming.MtomStreamWriter;
/*     */ import com.sun.xml.internal.ws.streaming.XMLStreamReaderUtil;
/*     */ import com.sun.xml.internal.ws.streaming.XMLStreamWriterUtil;
/*     */ import com.sun.xml.internal.ws.util.xml.XMLReaderComposite;
/*     */ import java.io.OutputStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import javax.xml.bind.JAXBContext;
/*     */ import javax.xml.bind.JAXBException;
/*     */ import javax.xml.bind.Marshaller;
/*     */ import javax.xml.bind.Unmarshaller;
/*     */ import javax.xml.bind.attachment.AttachmentMarshaller;
/*     */ import javax.xml.bind.util.JAXBResult;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import javax.xml.stream.XMLStreamReader;
/*     */ import javax.xml.stream.XMLStreamWriter;
/*     */ import javax.xml.transform.Result;
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
/*     */ public final class JAXBMessage
/*     */   extends AbstractMessageImpl
/*     */   implements StreamingSOAP
/*     */ {
/*     */   private MessageHeaders headers;
/*     */   private final Object jaxbObject;
/*     */   private final XMLBridge bridge;
/*     */   private final JAXBContext rawContext;
/*     */   private String nsUri;
/*     */   private String localName;
/*     */   private XMLStreamBuffer infoset;
/*     */   
/*     */   public static Message create(BindingContext context, Object jaxbObject, SOAPVersion soapVersion, MessageHeaders headers, AttachmentSet attachments) {
/* 110 */     if (!context.hasSwaRef()) {
/* 111 */       return (Message)new JAXBMessage(context, jaxbObject, soapVersion, headers, attachments);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 119 */       MutableXMLStreamBuffer xsb = new MutableXMLStreamBuffer();
/*     */       
/* 121 */       Marshaller m = context.createMarshaller();
/* 122 */       AttachmentMarshallerImpl am = new AttachmentMarshallerImpl(attachments);
/* 123 */       m.setAttachmentMarshaller(am);
/* 124 */       am.cleanup();
/* 125 */       m.marshal(jaxbObject, xsb.createFromXMLStreamWriter());
/*     */ 
/*     */       
/* 128 */       return (Message)new StreamMessage(headers, attachments, (XMLStreamReader)xsb.readAsXMLStreamReader(), soapVersion);
/* 129 */     } catch (JAXBException e) {
/* 130 */       throw new WebServiceException(e);
/* 131 */     } catch (XMLStreamException e) {
/* 132 */       throw new WebServiceException(e);
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
/*     */   public static Message create(BindingContext context, Object jaxbObject, SOAPVersion soapVersion) {
/* 148 */     return create(context, jaxbObject, soapVersion, (MessageHeaders)null, (AttachmentSet)null);
/*     */   }
/*     */   
/*     */   public static Message create(JAXBContext context, Object jaxbObject, SOAPVersion soapVersion) {
/* 152 */     return create(BindingContextFactory.create(context), jaxbObject, soapVersion, (MessageHeaders)null, (AttachmentSet)null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Message createRaw(JAXBContext context, Object jaxbObject, SOAPVersion soapVersion) {
/* 162 */     return (Message)new JAXBMessage(context, jaxbObject, soapVersion, null, null);
/*     */   }
/*     */   
/*     */   private JAXBMessage(BindingContext context, Object jaxbObject, SOAPVersion soapVer, MessageHeaders headers, AttachmentSet attachments) {
/* 166 */     super(soapVer);
/*     */     
/* 168 */     this.bridge = context.createFragmentBridge();
/* 169 */     this.rawContext = null;
/* 170 */     this.jaxbObject = jaxbObject;
/* 171 */     this.headers = headers;
/* 172 */     this.attachmentSet = attachments;
/*     */   }
/*     */   
/*     */   private JAXBMessage(JAXBContext rawContext, Object jaxbObject, SOAPVersion soapVer, MessageHeaders headers, AttachmentSet attachments) {
/* 176 */     super(soapVer);
/*     */     
/* 178 */     this.rawContext = rawContext;
/* 179 */     this.bridge = null;
/* 180 */     this.jaxbObject = jaxbObject;
/* 181 */     this.headers = headers;
/* 182 */     this.attachmentSet = attachments;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Message create(XMLBridge bridge, Object jaxbObject, SOAPVersion soapVer) {
/* 193 */     if (!bridge.context().hasSwaRef()) {
/* 194 */       return (Message)new JAXBMessage(bridge, jaxbObject, soapVer);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 202 */       MutableXMLStreamBuffer xsb = new MutableXMLStreamBuffer();
/*     */       
/* 204 */       AttachmentSetImpl attachments = new AttachmentSetImpl();
/* 205 */       AttachmentMarshallerImpl am = new AttachmentMarshallerImpl((AttachmentSet)attachments);
/* 206 */       bridge.marshal(jaxbObject, xsb.createFromXMLStreamWriter(), am);
/* 207 */       am.cleanup();
/*     */ 
/*     */       
/* 210 */       return (Message)new StreamMessage(null, (AttachmentSet)attachments, (XMLStreamReader)xsb.readAsXMLStreamReader(), soapVer);
/* 211 */     } catch (JAXBException e) {
/* 212 */       throw new WebServiceException(e);
/* 213 */     } catch (XMLStreamException e) {
/* 214 */       throw new WebServiceException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   private JAXBMessage(XMLBridge bridge, Object jaxbObject, SOAPVersion soapVer) {
/* 219 */     super(soapVer);
/*     */     
/* 221 */     this.bridge = bridge;
/* 222 */     this.rawContext = null;
/* 223 */     this.jaxbObject = jaxbObject;
/* 224 */     QName tagName = (bridge.getTypeInfo()).tagName;
/* 225 */     this.nsUri = tagName.getNamespaceURI();
/* 226 */     this.localName = tagName.getLocalPart();
/* 227 */     this.attachmentSet = (AttachmentSet)new AttachmentSetImpl();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JAXBMessage(JAXBMessage that) {
/* 234 */     super(that);
/* 235 */     this.headers = that.headers;
/* 236 */     if (this.headers != null)
/* 237 */       this.headers = (MessageHeaders)new HeaderList(this.headers); 
/* 238 */     this.attachmentSet = that.attachmentSet;
/*     */     
/* 240 */     this.jaxbObject = that.jaxbObject;
/* 241 */     this.bridge = that.bridge;
/* 242 */     this.rawContext = that.rawContext;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasHeaders() {
/* 247 */     return (this.headers != null && this.headers.hasHeaders());
/*     */   }
/*     */ 
/*     */   
/*     */   public MessageHeaders getHeaders() {
/* 252 */     if (this.headers == null)
/* 253 */       this.headers = (MessageHeaders)new HeaderList(getSOAPVersion()); 
/* 254 */     return this.headers;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getPayloadLocalPart() {
/* 259 */     if (this.localName == null)
/* 260 */       sniff(); 
/* 261 */     return this.localName;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getPayloadNamespaceURI() {
/* 266 */     if (this.nsUri == null)
/* 267 */       sniff(); 
/* 268 */     return this.nsUri;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasPayload() {
/* 273 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void sniff() {
/* 280 */     RootElementSniffer sniffer = new RootElementSniffer(false);
/*     */     try {
/* 282 */       if (this.rawContext != null)
/* 283 */       { Marshaller m = this.rawContext.createMarshaller();
/* 284 */         m.setProperty("jaxb.fragment", Boolean.TRUE);
/* 285 */         m.marshal(this.jaxbObject, (ContentHandler)sniffer); }
/*     */       else
/* 287 */       { this.bridge.marshal(this.jaxbObject, (ContentHandler)sniffer, null); } 
/* 288 */     } catch (JAXBException e) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 295 */       this.nsUri = sniffer.getNsUri();
/* 296 */       this.localName = sniffer.getLocalName();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public Source readPayloadAsSource() {
/* 302 */     return new JAXBBridgeSource(this.bridge, this.jaxbObject);
/*     */   }
/*     */ 
/*     */   
/*     */   public <T> T readPayloadAsJAXB(Unmarshaller unmarshaller) throws JAXBException {
/* 307 */     JAXBResult out = new JAXBResult(unmarshaller);
/*     */     
/*     */     try {
/* 310 */       out.getHandler().startDocument();
/* 311 */       if (this.rawContext != null) {
/* 312 */         Marshaller m = this.rawContext.createMarshaller();
/* 313 */         m.setProperty("jaxb.fragment", Boolean.TRUE);
/* 314 */         m.marshal(this.jaxbObject, (Result)out);
/*     */       } else {
/* 316 */         this.bridge.marshal(this.jaxbObject, (Result)out);
/* 317 */       }  out.getHandler().endDocument();
/* 318 */     } catch (SAXException e) {
/* 319 */       throw new JAXBException(e);
/*     */     } 
/* 321 */     return (T)out.getResult();
/*     */   }
/*     */ 
/*     */   
/*     */   public XMLStreamReader readPayload() throws XMLStreamException {
/*     */     try {
/* 327 */       if (this.infoset == null) {
/* 328 */         if (this.rawContext != null) {
/* 329 */           XMLStreamBufferResult sbr = new XMLStreamBufferResult();
/* 330 */           Marshaller m = this.rawContext.createMarshaller();
/* 331 */           m.setProperty("jaxb.fragment", Boolean.TRUE);
/* 332 */           m.marshal(this.jaxbObject, (Result)sbr);
/* 333 */           this.infoset = (XMLStreamBuffer)sbr.getXMLStreamBuffer();
/*     */         } else {
/* 335 */           MutableXMLStreamBuffer buffer = new MutableXMLStreamBuffer();
/* 336 */           writePayloadTo(buffer.createFromXMLStreamWriter());
/* 337 */           this.infoset = (XMLStreamBuffer)buffer;
/*     */         } 
/*     */       }
/* 340 */       StreamReaderBufferProcessor streamReaderBufferProcessor = this.infoset.readAsXMLStreamReader();
/* 341 */       if (streamReaderBufferProcessor.getEventType() == 7)
/* 342 */         XMLStreamReaderUtil.nextElementContent((XMLStreamReader)streamReaderBufferProcessor); 
/* 343 */       return (XMLStreamReader)streamReaderBufferProcessor;
/* 344 */     } catch (JAXBException e) {
/*     */       
/* 346 */       throw new WebServiceException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void writePayloadTo(ContentHandler contentHandler, ErrorHandler errorHandler, boolean fragment) throws SAXException {
/*     */     try {
/*     */       FragmentContentHandler fragmentContentHandler;
/* 356 */       if (fragment)
/* 357 */         fragmentContentHandler = new FragmentContentHandler(contentHandler); 
/* 358 */       AttachmentMarshallerImpl am = new AttachmentMarshallerImpl(this.attachmentSet);
/* 359 */       if (this.rawContext != null) {
/* 360 */         Marshaller m = this.rawContext.createMarshaller();
/* 361 */         m.setProperty("jaxb.fragment", Boolean.TRUE);
/* 362 */         m.setAttachmentMarshaller(am);
/* 363 */         m.marshal(this.jaxbObject, (ContentHandler)fragmentContentHandler);
/*     */       } else {
/* 365 */         this.bridge.marshal(this.jaxbObject, (ContentHandler)fragmentContentHandler, am);
/* 366 */       }  am.cleanup();
/* 367 */     } catch (JAXBException e) {
/*     */ 
/*     */ 
/*     */       
/* 371 */       throw new WebServiceException(e.getMessage(), e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writePayloadTo(XMLStreamWriter sw) throws XMLStreamException {
/*     */     try {
/* 380 */       AttachmentMarshaller am = (sw instanceof MtomStreamWriter) ? ((MtomStreamWriter)sw).getAttachmentMarshaller() : new AttachmentMarshallerImpl(this.attachmentSet);
/*     */ 
/*     */ 
/*     */       
/* 384 */       String encoding = XMLStreamWriterUtil.getEncoding(sw);
/*     */ 
/*     */       
/* 387 */       OutputStream os = this.bridge.supportOutputStream() ? XMLStreamWriterUtil.getOutputStream(sw) : null;
/* 388 */       if (this.rawContext != null) {
/* 389 */         Marshaller m = this.rawContext.createMarshaller();
/* 390 */         m.setProperty("jaxb.fragment", Boolean.TRUE);
/* 391 */         m.setAttachmentMarshaller(am);
/* 392 */         if (os != null) {
/* 393 */           m.marshal(this.jaxbObject, os);
/*     */         } else {
/* 395 */           m.marshal(this.jaxbObject, sw);
/*     */         }
/*     */       
/* 398 */       } else if (os != null && encoding != null && encoding.equalsIgnoreCase("utf-8")) {
/* 399 */         this.bridge.marshal(this.jaxbObject, os, sw.getNamespaceContext(), am);
/*     */       } else {
/* 401 */         this.bridge.marshal(this.jaxbObject, sw, am);
/*     */       
/*     */       }
/*     */     
/*     */     }
/* 406 */     catch (JAXBException e) {
/*     */       
/* 408 */       throw new WebServiceException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public Message copy() {
/* 414 */     return (Message)new JAXBMessage(this);
/*     */   }
/*     */   public XMLStreamReader readEnvelope() {
/*     */     XMLReaderComposite xMLReaderComposite;
/* 418 */     int base = this.soapVersion.ordinal() * 3;
/* 419 */     this.envelopeTag = DEFAULT_TAGS.get(base);
/* 420 */     this.bodyTag = DEFAULT_TAGS.get(base + 2);
/* 421 */     List<XMLStreamReader> hReaders = new ArrayList<>();
/* 422 */     XMLReaderComposite.ElemInfo envElem = new XMLReaderComposite.ElemInfo(this.envelopeTag, null);
/* 423 */     XMLReaderComposite.ElemInfo bdyElem = new XMLReaderComposite.ElemInfo(this.bodyTag, envElem);
/* 424 */     for (Header h : getHeaders().asList()) {
/*     */       try {
/* 426 */         hReaders.add(h.readHeader());
/* 427 */       } catch (XMLStreamException e) {
/* 428 */         throw new RuntimeException(e);
/*     */       } 
/*     */     } 
/* 431 */     XMLStreamReader soapHeader = null;
/* 432 */     if (hReaders.size() > 0) {
/* 433 */       this.headerTag = DEFAULT_TAGS.get(base + 1);
/* 434 */       XMLReaderComposite.ElemInfo hdrElem = new XMLReaderComposite.ElemInfo(this.headerTag, envElem);
/* 435 */       xMLReaderComposite = new XMLReaderComposite(hdrElem, hReaders.<XMLStreamReader>toArray(new XMLStreamReader[hReaders.size()]));
/*     */     } 
/*     */     try {
/* 438 */       XMLStreamReader payload = readPayload();
/* 439 */       XMLReaderComposite xMLReaderComposite1 = new XMLReaderComposite(bdyElem, new XMLStreamReader[] { payload });
/* 440 */       (new XMLStreamReader[2])[0] = (XMLStreamReader)xMLReaderComposite; (new XMLStreamReader[2])[1] = (XMLStreamReader)xMLReaderComposite1; (new XMLStreamReader[1])[0] = (XMLStreamReader)xMLReaderComposite1; XMLStreamReader[] soapContent = (xMLReaderComposite != null) ? new XMLStreamReader[2] : new XMLStreamReader[1];
/* 441 */       return (XMLStreamReader)new XMLReaderComposite(envElem, soapContent);
/* 442 */     } catch (XMLStreamException e) {
/* 443 */       throw new RuntimeException(e);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/message/jaxb/JAXBMessage.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */