/*     */ package com.sun.xml.internal.ws.encoding.xml;
/*     */ 
/*     */ import com.sun.istack.internal.NotNull;
/*     */ import com.sun.xml.internal.bind.api.Bridge;
/*     */ import com.sun.xml.internal.ws.api.SOAPVersion;
/*     */ import com.sun.xml.internal.ws.api.WSFeatureList;
/*     */ import com.sun.xml.internal.ws.api.message.AttachmentSet;
/*     */ import com.sun.xml.internal.ws.api.message.HeaderList;
/*     */ import com.sun.xml.internal.ws.api.message.Message;
/*     */ import com.sun.xml.internal.ws.api.message.MessageHeaders;
/*     */ import com.sun.xml.internal.ws.api.message.Messages;
/*     */ import com.sun.xml.internal.ws.api.message.Packet;
/*     */ import com.sun.xml.internal.ws.api.model.wsdl.WSDLPort;
/*     */ import com.sun.xml.internal.ws.api.pipe.ContentType;
/*     */ import com.sun.xml.internal.ws.api.streaming.XMLStreamWriterFactory;
/*     */ import com.sun.xml.internal.ws.developer.StreamingAttachmentFeature;
/*     */ import com.sun.xml.internal.ws.encoding.ContentType;
/*     */ import com.sun.xml.internal.ws.encoding.MimeMultipartParser;
/*     */ import com.sun.xml.internal.ws.encoding.XMLHTTPBindingCodec;
/*     */ import com.sun.xml.internal.ws.message.AbstractMessageImpl;
/*     */ import com.sun.xml.internal.ws.message.EmptyMessageImpl;
/*     */ import com.sun.xml.internal.ws.message.MimeAttachmentSet;
/*     */ import com.sun.xml.internal.ws.message.source.PayloadSourceMessage;
/*     */ import com.sun.xml.internal.ws.util.ByteArrayBuffer;
/*     */ import com.sun.xml.internal.ws.util.StreamUtils;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import javax.activation.DataSource;
/*     */ import javax.xml.bind.JAXBException;
/*     */ import javax.xml.bind.Unmarshaller;
/*     */ import javax.xml.soap.SOAPException;
/*     */ import javax.xml.soap.SOAPMessage;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import javax.xml.stream.XMLStreamReader;
/*     */ import javax.xml.stream.XMLStreamWriter;
/*     */ import javax.xml.transform.Source;
/*     */ import javax.xml.transform.stream.StreamSource;
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
/*     */ public final class XMLMessage
/*     */ {
/*     */   private static final int PLAIN_XML_FLAG = 1;
/*     */   private static final int MIME_MULTIPART_FLAG = 2;
/*     */   private static final int FI_ENCODED_FLAG = 16;
/*     */   
/*     */   public static Message create(String ct, InputStream in, WSFeatureList f) {
/*     */     UnknownContent unknownContent;
/*     */     try {
/*  82 */       in = StreamUtils.hasSomeData(in);
/*  83 */       if (in == null) {
/*  84 */         return Messages.createEmpty(SOAPVersion.SOAP_11);
/*     */       }
/*     */       
/*  87 */       if (ct != null) {
/*  88 */         ContentType contentType = new ContentType(ct);
/*  89 */         int contentTypeId = identifyContentType(contentType);
/*  90 */         if ((contentTypeId & 0x2) != 0) {
/*  91 */           XMLMultiPart xMLMultiPart = new XMLMultiPart(ct, in, f);
/*  92 */         } else if ((contentTypeId & 0x1) != 0) {
/*  93 */           XmlContent xmlContent = new XmlContent(ct, in, f);
/*     */         } else {
/*  95 */           unknownContent = new UnknownContent(ct, in);
/*     */         }
/*     */       
/*     */       } else {
/*     */         
/* 100 */         unknownContent = new UnknownContent("application/octet-stream", in);
/*     */       } 
/* 102 */     } catch (Exception ex) {
/* 103 */       throw new WebServiceException(ex);
/*     */     } 
/* 105 */     return (Message)unknownContent;
/*     */   }
/*     */ 
/*     */   
/*     */   public static Message create(Source source) {
/* 110 */     return (source == null) ? 
/* 111 */       Messages.createEmpty(SOAPVersion.SOAP_11) : 
/* 112 */       Messages.createUsingPayload(source, SOAPVersion.SOAP_11);
/*     */   }
/*     */   
/*     */   public static Message create(DataSource ds, WSFeatureList f) {
/*     */     try {
/* 117 */       return (ds == null) ? 
/* 118 */         Messages.createEmpty(SOAPVersion.SOAP_11) : 
/* 119 */         create(ds.getContentType(), ds.getInputStream(), f);
/* 120 */     } catch (IOException ioe) {
/* 121 */       throw new WebServiceException(ioe);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static Message create(Exception e) {
/* 126 */     return (Message)new FaultMessage(SOAPVersion.SOAP_11);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int getContentId(String ct) {
/*     */     try {
/* 134 */       ContentType contentType = new ContentType(ct);
/* 135 */       return identifyContentType(contentType);
/* 136 */     } catch (Exception ex) {
/* 137 */       throw new WebServiceException(ex);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isFastInfoset(String ct) {
/* 145 */     return ((getContentId(ct) & 0x10) != 0);
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
/*     */   public static int identifyContentType(ContentType contentType) {
/* 159 */     String primary = contentType.getPrimaryType();
/* 160 */     String sub = contentType.getSubType();
/*     */     
/* 162 */     if (primary.equalsIgnoreCase("multipart") && sub.equalsIgnoreCase("related")) {
/* 163 */       String type = contentType.getParameter("type");
/* 164 */       if (type != null) {
/* 165 */         if (isXMLType(type))
/* 166 */           return 3; 
/* 167 */         if (isFastInfosetType(type)) {
/* 168 */           return 18;
/*     */         }
/*     */       } 
/* 171 */       return 0;
/* 172 */     }  if (isXMLType(primary, sub))
/* 173 */       return 1; 
/* 174 */     if (isFastInfosetType(primary, sub)) {
/* 175 */       return 16;
/*     */     }
/* 177 */     return 0;
/*     */   }
/*     */   
/*     */   protected static boolean isXMLType(@NotNull String primary, @NotNull String sub) {
/* 181 */     return ((primary.equalsIgnoreCase("text") && sub.equalsIgnoreCase("xml")) || (primary
/* 182 */       .equalsIgnoreCase("application") && sub.equalsIgnoreCase("xml")) || (primary
/* 183 */       .equalsIgnoreCase("application") && sub.toLowerCase().endsWith("+xml")));
/*     */   }
/*     */   
/*     */   protected static boolean isXMLType(String type) {
/* 187 */     String lowerType = type.toLowerCase();
/* 188 */     return (lowerType.startsWith("text/xml") || lowerType
/* 189 */       .startsWith("application/xml") || (lowerType
/* 190 */       .startsWith("application/") && lowerType.indexOf("+xml") != -1));
/*     */   }
/*     */   
/*     */   protected static boolean isFastInfosetType(String primary, String sub) {
/* 194 */     return (primary.equalsIgnoreCase("application") && sub.equalsIgnoreCase("fastinfoset"));
/*     */   }
/*     */   
/*     */   protected static boolean isFastInfosetType(String type) {
/* 198 */     return type.toLowerCase().startsWith("application/fastinfoset");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static interface MessageDataSource
/*     */   {
/*     */     boolean hasUnconsumedDataSource();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     DataSource getDataSource();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static class XmlContent
/*     */     extends AbstractMessageImpl
/*     */     implements MessageDataSource
/*     */   {
/*     */     private final XMLMessage.XmlDataSource dataSource;
/*     */ 
/*     */     
/*     */     private boolean consumed;
/*     */ 
/*     */     
/*     */     private Message delegate;
/*     */ 
/*     */     
/*     */     private final HeaderList headerList;
/*     */ 
/*     */     
/*     */     private WSFeatureList features;
/*     */ 
/*     */ 
/*     */     
/*     */     public XmlContent(String ct, InputStream in, WSFeatureList f) {
/* 237 */       super(SOAPVersion.SOAP_11);
/* 238 */       this.dataSource = new XMLMessage.XmlDataSource(ct, in);
/* 239 */       this.headerList = new HeaderList(SOAPVersion.SOAP_11);
/*     */       
/* 241 */       this.features = f;
/*     */     }
/*     */     
/*     */     private Message getMessage() {
/* 245 */       if (this.delegate == null) {
/* 246 */         InputStream in = this.dataSource.getInputStream();
/* 247 */         assert in != null;
/* 248 */         this.delegate = Messages.createUsingPayload(new StreamSource(in), SOAPVersion.SOAP_11);
/* 249 */         this.consumed = true;
/*     */       } 
/* 251 */       return this.delegate;
/*     */     }
/*     */     
/*     */     public boolean hasUnconsumedDataSource() {
/* 255 */       return (!this.dataSource.consumed() && !this.consumed);
/*     */     }
/*     */     
/*     */     public DataSource getDataSource() {
/* 259 */       return hasUnconsumedDataSource() ? this.dataSource : 
/* 260 */         XMLMessage.getDataSource(getMessage(), this.features);
/*     */     }
/*     */     
/*     */     public boolean hasHeaders() {
/* 264 */       return false;
/*     */     }
/*     */     @NotNull
/*     */     public MessageHeaders getHeaders() {
/* 268 */       return (MessageHeaders)this.headerList;
/*     */     }
/*     */     
/*     */     public String getPayloadLocalPart() {
/* 272 */       return getMessage().getPayloadLocalPart();
/*     */     }
/*     */     
/*     */     public String getPayloadNamespaceURI() {
/* 276 */       return getMessage().getPayloadNamespaceURI();
/*     */     }
/*     */     
/*     */     public boolean hasPayload() {
/* 280 */       return true;
/*     */     }
/*     */     
/*     */     public boolean isFault() {
/* 284 */       return false;
/*     */     }
/*     */     
/*     */     public Source readEnvelopeAsSource() {
/* 288 */       return getMessage().readEnvelopeAsSource();
/*     */     }
/*     */     
/*     */     public Source readPayloadAsSource() {
/* 292 */       return getMessage().readPayloadAsSource();
/*     */     }
/*     */     
/*     */     public SOAPMessage readAsSOAPMessage() throws SOAPException {
/* 296 */       return getMessage().readAsSOAPMessage();
/*     */     }
/*     */     
/*     */     public SOAPMessage readAsSOAPMessage(Packet packet, boolean inbound) throws SOAPException {
/* 300 */       return getMessage().readAsSOAPMessage(packet, inbound);
/*     */     }
/*     */     
/*     */     public <T> T readPayloadAsJAXB(Unmarshaller unmarshaller) throws JAXBException {
/* 304 */       return (T)getMessage().readPayloadAsJAXB(unmarshaller);
/*     */     }
/*     */     
/*     */     public <T> T readPayloadAsJAXB(Bridge<T> bridge) throws JAXBException {
/* 308 */       return (T)getMessage().readPayloadAsJAXB(bridge);
/*     */     }
/*     */     
/*     */     public XMLStreamReader readPayload() throws XMLStreamException {
/* 312 */       return getMessage().readPayload();
/*     */     }
/*     */ 
/*     */     
/*     */     public void writePayloadTo(XMLStreamWriter sw) throws XMLStreamException {
/* 317 */       getMessage().writePayloadTo(sw);
/*     */     }
/*     */     
/*     */     public void writeTo(XMLStreamWriter sw) throws XMLStreamException {
/* 321 */       getMessage().writeTo(sw);
/*     */     }
/*     */     
/*     */     public void writeTo(ContentHandler contentHandler, ErrorHandler errorHandler) throws SAXException {
/* 325 */       getMessage().writeTo(contentHandler, errorHandler);
/*     */     }
/*     */     
/*     */     public Message copy() {
/* 329 */       return getMessage().copy();
/*     */     }
/*     */     
/*     */     protected void writePayloadTo(ContentHandler contentHandler, ErrorHandler errorHandler, boolean fragment) throws SAXException {
/* 333 */       throw new UnsupportedOperationException();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static final class XMLMultiPart
/*     */     extends AbstractMessageImpl
/*     */     implements MessageDataSource
/*     */   {
/*     */     private final DataSource dataSource;
/*     */     
/*     */     private final StreamingAttachmentFeature feature;
/*     */     
/*     */     private Message delegate;
/*     */     
/*     */     private HeaderList headerList;
/*     */     
/*     */     private final WSFeatureList features;
/*     */ 
/*     */     
/*     */     public XMLMultiPart(String contentType, InputStream is, WSFeatureList f) {
/* 355 */       super(SOAPVersion.SOAP_11);
/* 356 */       this.headerList = new HeaderList(SOAPVersion.SOAP_11);
/* 357 */       this.dataSource = XMLMessage.createDataSource(contentType, is);
/* 358 */       this.feature = (StreamingAttachmentFeature)f.get(StreamingAttachmentFeature.class);
/* 359 */       this.features = f;
/*     */     }
/*     */     
/*     */     private Message getMessage() {
/* 363 */       if (this.delegate == null) {
/*     */         MimeMultipartParser mpp;
/*     */         
/*     */         try {
/* 367 */           mpp = new MimeMultipartParser(this.dataSource.getInputStream(), this.dataSource.getContentType(), this.feature);
/* 368 */         } catch (IOException ioe) {
/* 369 */           throw new WebServiceException(ioe);
/*     */         } 
/* 371 */         InputStream in = mpp.getRootPart().asInputStream();
/* 372 */         assert in != null;
/* 373 */         this.delegate = (Message)new PayloadSourceMessage((MessageHeaders)this.headerList, new StreamSource(in), (AttachmentSet)new MimeAttachmentSet(mpp), SOAPVersion.SOAP_11);
/*     */       } 
/* 375 */       return this.delegate;
/*     */     }
/*     */     
/*     */     public boolean hasUnconsumedDataSource() {
/* 379 */       return (this.delegate == null);
/*     */     }
/*     */     
/*     */     public DataSource getDataSource() {
/* 383 */       return hasUnconsumedDataSource() ? this.dataSource : 
/* 384 */         XMLMessage.getDataSource(getMessage(), this.features);
/*     */     }
/*     */     
/*     */     public boolean hasHeaders() {
/* 388 */       return false;
/*     */     }
/*     */     @NotNull
/*     */     public MessageHeaders getHeaders() {
/* 392 */       return (MessageHeaders)this.headerList;
/*     */     }
/*     */     
/*     */     public String getPayloadLocalPart() {
/* 396 */       return getMessage().getPayloadLocalPart();
/*     */     }
/*     */     
/*     */     public String getPayloadNamespaceURI() {
/* 400 */       return getMessage().getPayloadNamespaceURI();
/*     */     }
/*     */     
/*     */     public boolean hasPayload() {
/* 404 */       return true;
/*     */     }
/*     */     
/*     */     public boolean isFault() {
/* 408 */       return false;
/*     */     }
/*     */     
/*     */     public Source readEnvelopeAsSource() {
/* 412 */       return getMessage().readEnvelopeAsSource();
/*     */     }
/*     */     
/*     */     public Source readPayloadAsSource() {
/* 416 */       return getMessage().readPayloadAsSource();
/*     */     }
/*     */     
/*     */     public SOAPMessage readAsSOAPMessage() throws SOAPException {
/* 420 */       return getMessage().readAsSOAPMessage();
/*     */     }
/*     */     
/*     */     public SOAPMessage readAsSOAPMessage(Packet packet, boolean inbound) throws SOAPException {
/* 424 */       return getMessage().readAsSOAPMessage(packet, inbound);
/*     */     }
/*     */     
/*     */     public <T> T readPayloadAsJAXB(Unmarshaller unmarshaller) throws JAXBException {
/* 428 */       return (T)getMessage().readPayloadAsJAXB(unmarshaller);
/*     */     }
/*     */     
/*     */     public <T> T readPayloadAsJAXB(Bridge<T> bridge) throws JAXBException {
/* 432 */       return (T)getMessage().readPayloadAsJAXB(bridge);
/*     */     }
/*     */     
/*     */     public XMLStreamReader readPayload() throws XMLStreamException {
/* 436 */       return getMessage().readPayload();
/*     */     }
/*     */     
/*     */     public void writePayloadTo(XMLStreamWriter sw) throws XMLStreamException {
/* 440 */       getMessage().writePayloadTo(sw);
/*     */     }
/*     */     
/*     */     public void writeTo(XMLStreamWriter sw) throws XMLStreamException {
/* 444 */       getMessage().writeTo(sw);
/*     */     }
/*     */     
/*     */     public void writeTo(ContentHandler contentHandler, ErrorHandler errorHandler) throws SAXException {
/* 448 */       getMessage().writeTo(contentHandler, errorHandler);
/*     */     }
/*     */     
/*     */     public Message copy() {
/* 452 */       return getMessage().copy();
/*     */     }
/*     */     
/*     */     protected void writePayloadTo(ContentHandler contentHandler, ErrorHandler errorHandler, boolean fragment) throws SAXException {
/* 456 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isOneWay(@NotNull WSDLPort port) {
/* 461 */       return false;
/*     */     }
/*     */     @NotNull
/*     */     public AttachmentSet getAttachments() {
/* 465 */       return getMessage().getAttachments();
/*     */     }
/*     */   }
/*     */   
/*     */   private static class FaultMessage
/*     */     extends EmptyMessageImpl
/*     */   {
/*     */     public FaultMessage(SOAPVersion version) {
/* 473 */       super(version);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isFault() {
/* 478 */       return true;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static class UnknownContent
/*     */     extends AbstractMessageImpl
/*     */     implements MessageDataSource
/*     */   {
/*     */     private final DataSource ds;
/*     */     
/*     */     private final HeaderList headerList;
/*     */ 
/*     */     
/*     */     public UnknownContent(String ct, InputStream in) {
/* 494 */       this(XMLMessage.createDataSource(ct, in));
/*     */     }
/*     */     
/*     */     public UnknownContent(DataSource ds) {
/* 498 */       super(SOAPVersion.SOAP_11);
/* 499 */       this.ds = ds;
/* 500 */       this.headerList = new HeaderList(SOAPVersion.SOAP_11);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private UnknownContent(UnknownContent that) {
/* 507 */       super(that.soapVersion);
/* 508 */       this.ds = that.ds;
/* 509 */       this.headerList = HeaderList.copy(that.headerList);
/*     */     }
/*     */     
/*     */     public boolean hasUnconsumedDataSource() {
/* 513 */       return true;
/*     */     }
/*     */     
/*     */     public DataSource getDataSource() {
/* 517 */       assert this.ds != null;
/* 518 */       return this.ds;
/*     */     }
/*     */ 
/*     */     
/*     */     protected void writePayloadTo(ContentHandler contentHandler, ErrorHandler errorHandler, boolean fragment) throws SAXException {
/* 523 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public boolean hasHeaders() {
/* 527 */       return false;
/*     */     }
/*     */     
/*     */     public boolean isFault() {
/* 531 */       return false;
/*     */     }
/*     */     
/*     */     public MessageHeaders getHeaders() {
/* 535 */       return (MessageHeaders)this.headerList;
/*     */     }
/*     */     
/*     */     public String getPayloadLocalPart() {
/* 539 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public String getPayloadNamespaceURI() {
/* 543 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public boolean hasPayload() {
/* 547 */       return false;
/*     */     }
/*     */     
/*     */     public Source readPayloadAsSource() {
/* 551 */       return null;
/*     */     }
/*     */     
/*     */     public XMLStreamReader readPayload() throws XMLStreamException {
/* 555 */       throw new WebServiceException("There isn't XML payload. Shouldn't come here.");
/*     */     }
/*     */ 
/*     */     
/*     */     public void writePayloadTo(XMLStreamWriter sw) throws XMLStreamException {}
/*     */ 
/*     */     
/*     */     public Message copy() {
/* 563 */       return (Message)new UnknownContent(this);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static DataSource getDataSource(Message msg, WSFeatureList f) {
/* 569 */     if (msg == null)
/* 570 */       return null; 
/* 571 */     if (msg instanceof MessageDataSource) {
/* 572 */       return ((MessageDataSource)msg).getDataSource();
/*     */     }
/* 574 */     AttachmentSet atts = msg.getAttachments();
/* 575 */     if (atts != null && !atts.isEmpty()) {
/* 576 */       ByteArrayBuffer byteArrayBuffer = new ByteArrayBuffer();
/*     */       try {
/* 578 */         XMLHTTPBindingCodec xMLHTTPBindingCodec = new XMLHTTPBindingCodec(f);
/* 579 */         Packet packet = new Packet(msg);
/* 580 */         ContentType ct = xMLHTTPBindingCodec.getStaticContentType(packet);
/* 581 */         xMLHTTPBindingCodec.encode(packet, (OutputStream)byteArrayBuffer);
/* 582 */         return createDataSource(ct.getContentType(), byteArrayBuffer.newInputStream());
/* 583 */       } catch (IOException ioe) {
/* 584 */         throw new WebServiceException(ioe);
/*     */       } 
/*     */     } 
/*     */     
/* 588 */     ByteArrayBuffer bos = new ByteArrayBuffer();
/* 589 */     XMLStreamWriter writer = XMLStreamWriterFactory.create((OutputStream)bos);
/*     */     try {
/* 591 */       msg.writePayloadTo(writer);
/* 592 */       writer.flush();
/* 593 */     } catch (XMLStreamException e) {
/* 594 */       throw new WebServiceException(e);
/*     */     } 
/* 596 */     return createDataSource("text/xml", bos.newInputStream());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static DataSource createDataSource(String contentType, InputStream is) {
/* 602 */     return new XmlDataSource(contentType, is);
/*     */   }
/*     */   
/*     */   private static class XmlDataSource implements DataSource {
/*     */     private final String contentType;
/*     */     private final InputStream is;
/*     */     private boolean consumed;
/*     */     
/*     */     XmlDataSource(String contentType, InputStream is) {
/* 611 */       this.contentType = contentType;
/* 612 */       this.is = is;
/*     */     }
/*     */     
/*     */     public boolean consumed() {
/* 616 */       return this.consumed;
/*     */     }
/*     */     
/*     */     public InputStream getInputStream() {
/* 620 */       this.consumed = !this.consumed;
/* 621 */       return this.is;
/*     */     }
/*     */     
/*     */     public OutputStream getOutputStream() {
/* 625 */       return null;
/*     */     }
/*     */     
/*     */     public String getContentType() {
/* 629 */       return this.contentType;
/*     */     }
/*     */     
/*     */     public String getName() {
/* 633 */       return "";
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/encoding/xml/XMLMessage.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */