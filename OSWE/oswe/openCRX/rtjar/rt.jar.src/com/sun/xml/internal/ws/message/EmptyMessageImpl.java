/*     */ package com.sun.xml.internal.ws.message;
/*     */ 
/*     */ import com.sun.istack.internal.NotNull;
/*     */ import com.sun.xml.internal.ws.api.SOAPVersion;
/*     */ import com.sun.xml.internal.ws.api.message.AttachmentSet;
/*     */ import com.sun.xml.internal.ws.api.message.HeaderList;
/*     */ import com.sun.xml.internal.ws.api.message.Message;
/*     */ import com.sun.xml.internal.ws.api.message.MessageHeaders;
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
/*     */ public class EmptyMessageImpl
/*     */   extends AbstractMessageImpl
/*     */ {
/*     */   private final MessageHeaders headers;
/*     */   private final AttachmentSet attachmentSet;
/*     */   
/*     */   public EmptyMessageImpl(SOAPVersion version) {
/*  59 */     super(version);
/*  60 */     this.headers = (MessageHeaders)new HeaderList(version);
/*  61 */     this.attachmentSet = new AttachmentSetImpl();
/*     */   }
/*     */   
/*     */   public EmptyMessageImpl(MessageHeaders headers, @NotNull AttachmentSet attachmentSet, SOAPVersion version) {
/*  65 */     super(version); HeaderList headerList;
/*  66 */     if (headers == null)
/*  67 */       headerList = new HeaderList(version); 
/*  68 */     this.attachmentSet = attachmentSet;
/*  69 */     this.headers = (MessageHeaders)headerList;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private EmptyMessageImpl(EmptyMessageImpl that) {
/*  76 */     super(that);
/*  77 */     this.headers = (MessageHeaders)new HeaderList(that.headers);
/*  78 */     this.attachmentSet = that.attachmentSet;
/*     */   }
/*     */   
/*     */   public boolean hasHeaders() {
/*  82 */     return this.headers.hasHeaders();
/*     */   }
/*     */   
/*     */   public MessageHeaders getHeaders() {
/*  86 */     return this.headers;
/*     */   }
/*     */   
/*     */   public String getPayloadLocalPart() {
/*  90 */     return null;
/*     */   }
/*     */   
/*     */   public String getPayloadNamespaceURI() {
/*  94 */     return null;
/*     */   }
/*     */   
/*     */   public boolean hasPayload() {
/*  98 */     return false;
/*     */   }
/*     */   
/*     */   public Source readPayloadAsSource() {
/* 102 */     return null;
/*     */   }
/*     */   
/*     */   public XMLStreamReader readPayload() throws XMLStreamException {
/* 106 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void writePayloadTo(XMLStreamWriter sw) throws XMLStreamException {}
/*     */ 
/*     */   
/*     */   public void writePayloadTo(ContentHandler contentHandler, ErrorHandler errorHandler, boolean fragment) throws SAXException {}
/*     */ 
/*     */   
/*     */   public Message copy() {
/* 118 */     return new EmptyMessageImpl(this);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/message/EmptyMessageImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */