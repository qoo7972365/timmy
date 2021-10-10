/*     */ package com.sun.xml.internal.ws.message.jaxb;
/*     */ 
/*     */ import com.sun.xml.internal.ws.api.SOAPVersion;
/*     */ import com.sun.xml.internal.ws.api.message.Message;
/*     */ import com.sun.xml.internal.ws.api.message.MessageHeaders;
/*     */ import com.sun.xml.internal.ws.message.AbstractMessageImpl;
/*     */ import com.sun.xml.internal.ws.message.PayloadElementSniffer;
/*     */ import com.sun.xml.internal.ws.spi.db.BindingContext;
/*     */ import com.sun.xml.internal.ws.spi.db.XMLBridge;
/*     */ import com.sun.xml.internal.ws.streaming.MtomStreamWriter;
/*     */ import com.sun.xml.internal.ws.streaming.XMLStreamWriterUtil;
/*     */ import java.io.OutputStream;
/*     */ import javax.xml.bind.JAXBContext;
/*     */ import javax.xml.bind.JAXBException;
/*     */ import javax.xml.bind.Marshaller;
/*     */ import javax.xml.bind.attachment.AttachmentMarshaller;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import javax.xml.stream.XMLStreamReader;
/*     */ import javax.xml.stream.XMLStreamWriter;
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
/*     */ public class JAXBDispatchMessage
/*     */   extends AbstractMessageImpl
/*     */ {
/*     */   private final Object jaxbObject;
/*     */   private final XMLBridge bridge;
/*     */   private final JAXBContext rawContext;
/*     */   private QName payloadQName;
/*     */   
/*     */   private JAXBDispatchMessage(JAXBDispatchMessage that) {
/*  86 */     super(that);
/*  87 */     this.jaxbObject = that.jaxbObject;
/*  88 */     this.rawContext = that.rawContext;
/*  89 */     this.bridge = that.bridge;
/*     */   }
/*     */   
/*     */   public JAXBDispatchMessage(JAXBContext rawContext, Object jaxbObject, SOAPVersion soapVersion) {
/*  93 */     super(soapVersion);
/*  94 */     this.bridge = null;
/*  95 */     this.rawContext = rawContext;
/*  96 */     this.jaxbObject = jaxbObject;
/*     */   }
/*     */   
/*     */   public JAXBDispatchMessage(BindingContext context, Object jaxbObject, SOAPVersion soapVersion) {
/* 100 */     super(soapVersion);
/* 101 */     this.bridge = context.createFragmentBridge();
/* 102 */     this.rawContext = null;
/* 103 */     this.jaxbObject = jaxbObject;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void writePayloadTo(ContentHandler contentHandler, ErrorHandler errorHandler, boolean fragment) throws SAXException {
/* 108 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasHeaders() {
/* 113 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public MessageHeaders getHeaders() {
/* 118 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getPayloadLocalPart() {
/* 123 */     if (this.payloadQName == null) {
/* 124 */       readPayloadElement();
/*     */     }
/* 126 */     return this.payloadQName.getLocalPart();
/*     */   }
/*     */ 
/*     */   
/*     */   public String getPayloadNamespaceURI() {
/* 131 */     if (this.payloadQName == null) {
/* 132 */       readPayloadElement();
/*     */     }
/* 134 */     return this.payloadQName.getNamespaceURI();
/*     */   }
/*     */   
/*     */   private void readPayloadElement() {
/* 138 */     PayloadElementSniffer sniffer = new PayloadElementSniffer();
/*     */     try {
/* 140 */       if (this.rawContext != null) {
/* 141 */         Marshaller m = this.rawContext.createMarshaller();
/* 142 */         m.setProperty("jaxb.fragment", Boolean.FALSE);
/* 143 */         m.marshal(this.jaxbObject, (ContentHandler)sniffer);
/*     */       } else {
/* 145 */         this.bridge.marshal(this.jaxbObject, (ContentHandler)sniffer, null);
/*     */       }
/*     */     
/* 148 */     } catch (JAXBException e) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 155 */       this.payloadQName = sniffer.getPayloadQName();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasPayload() {
/* 161 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public Source readPayloadAsSource() {
/* 166 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */   
/*     */   public XMLStreamReader readPayload() throws XMLStreamException {
/* 171 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */   
/*     */   public void writePayloadTo(XMLStreamWriter sw) throws XMLStreamException {
/* 176 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */   
/*     */   public Message copy() {
/* 181 */     return (Message)new JAXBDispatchMessage(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeTo(XMLStreamWriter sw) throws XMLStreamException {
/*     */     try {
/* 190 */       AttachmentMarshaller am = (sw instanceof MtomStreamWriter) ? ((MtomStreamWriter)sw).getAttachmentMarshaller() : new AttachmentMarshallerImpl(this.attachmentSet);
/*     */ 
/*     */ 
/*     */       
/* 194 */       String encoding = XMLStreamWriterUtil.getEncoding(sw);
/*     */ 
/*     */       
/* 197 */       OutputStream os = this.bridge.supportOutputStream() ? XMLStreamWriterUtil.getOutputStream(sw) : null;
/* 198 */       if (this.rawContext != null) {
/* 199 */         Marshaller m = this.rawContext.createMarshaller();
/* 200 */         m.setProperty("jaxb.fragment", Boolean.FALSE);
/* 201 */         m.setAttachmentMarshaller(am);
/* 202 */         if (os != null) {
/* 203 */           m.marshal(this.jaxbObject, os);
/*     */         } else {
/* 205 */           m.marshal(this.jaxbObject, sw);
/*     */         
/*     */         }
/*     */       
/*     */       }
/* 210 */       else if (os != null && encoding != null && encoding.equalsIgnoreCase("utf-8")) {
/* 211 */         this.bridge.marshal(this.jaxbObject, os, sw.getNamespaceContext(), am);
/*     */       } else {
/* 213 */         this.bridge.marshal(this.jaxbObject, sw, am);
/*     */       }
/*     */     
/*     */     }
/* 217 */     catch (JAXBException e) {
/*     */       
/* 219 */       throw new WebServiceException(e);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/message/jaxb/JAXBDispatchMessage.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */