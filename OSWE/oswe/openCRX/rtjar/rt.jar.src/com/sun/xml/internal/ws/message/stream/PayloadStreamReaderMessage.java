/*     */ package com.sun.xml.internal.ws.message.stream;
/*     */ 
/*     */ import com.sun.istack.internal.NotNull;
/*     */ import com.sun.istack.internal.Nullable;
/*     */ import com.sun.xml.internal.ws.api.SOAPVersion;
/*     */ import com.sun.xml.internal.ws.api.message.AttachmentSet;
/*     */ import com.sun.xml.internal.ws.api.message.Message;
/*     */ import com.sun.xml.internal.ws.api.message.MessageHeaders;
/*     */ import com.sun.xml.internal.ws.message.AbstractMessageImpl;
/*     */ import com.sun.xml.internal.ws.message.AttachmentSetImpl;
/*     */ import javax.xml.bind.JAXBException;
/*     */ import javax.xml.bind.Unmarshaller;
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
/*     */ public class PayloadStreamReaderMessage
/*     */   extends AbstractMessageImpl
/*     */ {
/*     */   private final StreamMessage message;
/*     */   
/*     */   public PayloadStreamReaderMessage(XMLStreamReader reader, SOAPVersion soapVer) {
/*  56 */     this(null, reader, (AttachmentSet)new AttachmentSetImpl(), soapVer);
/*     */   }
/*     */ 
/*     */   
/*     */   public PayloadStreamReaderMessage(@Nullable MessageHeaders headers, @NotNull XMLStreamReader reader, @NotNull AttachmentSet attSet, @NotNull SOAPVersion soapVersion) {
/*  61 */     super(soapVersion);
/*  62 */     this.message = new StreamMessage(headers, attSet, reader, soapVersion);
/*     */   }
/*     */   
/*     */   public boolean hasHeaders() {
/*  66 */     return this.message.hasHeaders();
/*     */   }
/*     */   
/*     */   public AttachmentSet getAttachments() {
/*  70 */     return this.message.getAttachments();
/*     */   }
/*     */   
/*     */   public String getPayloadLocalPart() {
/*  74 */     return this.message.getPayloadLocalPart();
/*     */   }
/*     */   
/*     */   public String getPayloadNamespaceURI() {
/*  78 */     return this.message.getPayloadNamespaceURI();
/*     */   }
/*     */   
/*     */   public boolean hasPayload() {
/*  82 */     return true;
/*     */   }
/*     */   
/*     */   public Source readPayloadAsSource() {
/*  86 */     return this.message.readPayloadAsSource();
/*     */   }
/*     */   
/*     */   public XMLStreamReader readPayload() throws XMLStreamException {
/*  90 */     return this.message.readPayload();
/*     */   }
/*     */   
/*     */   public void writePayloadTo(XMLStreamWriter sw) throws XMLStreamException {
/*  94 */     this.message.writePayloadTo(sw);
/*     */   }
/*     */   
/*     */   public <T> T readPayloadAsJAXB(Unmarshaller unmarshaller) throws JAXBException {
/*  98 */     return (T)this.message.readPayloadAsJAXB(unmarshaller);
/*     */   }
/*     */   
/*     */   public void writeTo(ContentHandler contentHandler, ErrorHandler errorHandler) throws SAXException {
/* 102 */     this.message.writeTo(contentHandler, errorHandler);
/*     */   }
/*     */   
/*     */   protected void writePayloadTo(ContentHandler contentHandler, ErrorHandler errorHandler, boolean fragment) throws SAXException {
/* 106 */     this.message.writePayloadTo(contentHandler, errorHandler, fragment);
/*     */   }
/*     */   
/*     */   public Message copy() {
/* 110 */     return this.message.copy();
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public MessageHeaders getHeaders() {
/* 115 */     return this.message.getHeaders();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/message/stream/PayloadStreamReaderMessage.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */