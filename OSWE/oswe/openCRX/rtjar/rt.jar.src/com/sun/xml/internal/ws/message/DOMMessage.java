/*     */ package com.sun.xml.internal.ws.message;
/*     */ 
/*     */ import com.sun.istack.internal.FragmentContentHandler;
/*     */ import com.sun.xml.internal.bind.api.Bridge;
/*     */ import com.sun.xml.internal.bind.unmarshaller.DOMScanner;
/*     */ import com.sun.xml.internal.ws.api.SOAPVersion;
/*     */ import com.sun.xml.internal.ws.api.message.AttachmentSet;
/*     */ import com.sun.xml.internal.ws.api.message.HeaderList;
/*     */ import com.sun.xml.internal.ws.api.message.Message;
/*     */ import com.sun.xml.internal.ws.api.message.MessageHeaders;
/*     */ import com.sun.xml.internal.ws.streaming.DOMStreamReader;
/*     */ import com.sun.xml.internal.ws.util.DOMUtil;
/*     */ import javax.xml.bind.JAXBException;
/*     */ import javax.xml.bind.Unmarshaller;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import javax.xml.stream.XMLStreamReader;
/*     */ import javax.xml.stream.XMLStreamWriter;
/*     */ import javax.xml.transform.Source;
/*     */ import javax.xml.transform.dom.DOMSource;
/*     */ import javax.xml.ws.WebServiceException;
/*     */ import org.w3c.dom.Element;
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
/*     */ public final class DOMMessage
/*     */   extends AbstractMessageImpl
/*     */ {
/*     */   private MessageHeaders headers;
/*     */   private final Element payload;
/*     */   
/*     */   public DOMMessage(SOAPVersion ver, Element payload) {
/*  62 */     this(ver, (MessageHeaders)null, payload);
/*     */   }
/*     */   
/*     */   public DOMMessage(SOAPVersion ver, MessageHeaders headers, Element payload) {
/*  66 */     this(ver, headers, payload, (AttachmentSet)null);
/*     */   }
/*     */   
/*     */   public DOMMessage(SOAPVersion ver, MessageHeaders headers, Element payload, AttachmentSet attachments) {
/*  70 */     super(ver);
/*  71 */     this.headers = headers;
/*  72 */     this.payload = payload;
/*  73 */     this.attachmentSet = attachments;
/*  74 */     assert payload != null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private DOMMessage(DOMMessage that) {
/*  80 */     super(that);
/*  81 */     this.headers = (MessageHeaders)HeaderList.copy(that.headers);
/*  82 */     this.payload = that.payload;
/*     */   }
/*     */   
/*     */   public boolean hasHeaders() {
/*  86 */     return getHeaders().hasHeaders();
/*     */   }
/*     */   
/*     */   public MessageHeaders getHeaders() {
/*  90 */     if (this.headers == null) {
/*  91 */       this.headers = (MessageHeaders)new HeaderList(getSOAPVersion());
/*     */     }
/*  93 */     return this.headers;
/*     */   }
/*     */   
/*     */   public String getPayloadLocalPart() {
/*  97 */     return this.payload.getLocalName();
/*     */   }
/*     */   
/*     */   public String getPayloadNamespaceURI() {
/* 101 */     return this.payload.getNamespaceURI();
/*     */   }
/*     */   
/*     */   public boolean hasPayload() {
/* 105 */     return true;
/*     */   }
/*     */   
/*     */   public Source readPayloadAsSource() {
/* 109 */     return new DOMSource(this.payload);
/*     */   }
/*     */   
/*     */   public <T> T readPayloadAsJAXB(Unmarshaller unmarshaller) throws JAXBException {
/* 113 */     if (hasAttachments())
/* 114 */       unmarshaller.setAttachmentUnmarshaller(new AttachmentUnmarshallerImpl(getAttachments())); 
/*     */     try {
/* 116 */       return (T)unmarshaller.unmarshal(this.payload);
/*     */     } finally {
/* 118 */       unmarshaller.setAttachmentUnmarshaller(null);
/*     */     } 
/*     */   }
/*     */   
/*     */   public <T> T readPayloadAsJAXB(Bridge<T> bridge) throws JAXBException {
/* 123 */     return (T)bridge.unmarshal(this.payload, 
/* 124 */         hasAttachments() ? new AttachmentUnmarshallerImpl(getAttachments()) : null);
/*     */   }
/*     */   
/*     */   public XMLStreamReader readPayload() throws XMLStreamException {
/* 128 */     DOMStreamReader dss = new DOMStreamReader();
/* 129 */     dss.setCurrentNode(this.payload);
/* 130 */     dss.nextTag();
/* 131 */     assert dss.getEventType() == 1;
/* 132 */     return (XMLStreamReader)dss;
/*     */   }
/*     */   
/*     */   public void writePayloadTo(XMLStreamWriter sw) {
/*     */     try {
/* 137 */       if (this.payload != null)
/* 138 */         DOMUtil.serializeNode(this.payload, sw); 
/* 139 */     } catch (XMLStreamException e) {
/* 140 */       throw new WebServiceException(e);
/*     */     } 
/*     */   }
/*     */   protected void writePayloadTo(ContentHandler contentHandler, ErrorHandler errorHandler, boolean fragment) throws SAXException {
/*     */     FragmentContentHandler fragmentContentHandler;
/* 145 */     if (fragment)
/* 146 */       fragmentContentHandler = new FragmentContentHandler(contentHandler); 
/* 147 */     DOMScanner ds = new DOMScanner();
/* 148 */     ds.setContentHandler((ContentHandler)fragmentContentHandler);
/* 149 */     ds.scan(this.payload);
/*     */   }
/*     */   
/*     */   public Message copy() {
/* 153 */     return new DOMMessage(this);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/message/DOMMessage.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */