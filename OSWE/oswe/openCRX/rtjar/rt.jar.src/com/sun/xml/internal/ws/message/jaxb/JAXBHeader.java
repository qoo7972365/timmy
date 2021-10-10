/*     */ package com.sun.xml.internal.ws.message.jaxb;
/*     */ 
/*     */ import com.sun.istack.internal.NotNull;
/*     */ import com.sun.istack.internal.XMLStreamException2;
/*     */ import com.sun.xml.internal.bind.api.Bridge;
/*     */ import com.sun.xml.internal.stream.buffer.MutableXMLStreamBuffer;
/*     */ import com.sun.xml.internal.stream.buffer.XMLStreamBuffer;
/*     */ import com.sun.xml.internal.ws.message.AbstractHeaderImpl;
/*     */ import com.sun.xml.internal.ws.message.RootElementSniffer;
/*     */ import com.sun.xml.internal.ws.spi.db.BindingContext;
/*     */ import com.sun.xml.internal.ws.spi.db.XMLBridge;
/*     */ import com.sun.xml.internal.ws.streaming.XMLStreamWriterUtil;
/*     */ import java.io.OutputStream;
/*     */ import javax.xml.bind.JAXBElement;
/*     */ import javax.xml.bind.JAXBException;
/*     */ import javax.xml.bind.Unmarshaller;
/*     */ import javax.xml.bind.util.JAXBResult;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.soap.SOAPException;
/*     */ import javax.xml.soap.SOAPHeader;
/*     */ import javax.xml.soap.SOAPMessage;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import javax.xml.stream.XMLStreamReader;
/*     */ import javax.xml.stream.XMLStreamWriter;
/*     */ import javax.xml.transform.Result;
/*     */ import org.w3c.dom.Node;
/*     */ import org.xml.sax.Attributes;
/*     */ import org.xml.sax.ContentHandler;
/*     */ import org.xml.sax.ErrorHandler;
/*     */ import org.xml.sax.SAXException;
/*     */ import org.xml.sax.SAXParseException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class JAXBHeader
/*     */   extends AbstractHeaderImpl
/*     */ {
/*     */   private final Object jaxbObject;
/*     */   private final XMLBridge bridge;
/*     */   private String nsUri;
/*     */   private String localName;
/*     */   private Attributes atts;
/*     */   private XMLStreamBuffer infoset;
/*     */   
/*     */   public JAXBHeader(BindingContext context, Object jaxbObject) {
/*  86 */     this.jaxbObject = jaxbObject;
/*     */     
/*  88 */     this.bridge = context.createFragmentBridge();
/*     */     
/*  90 */     if (jaxbObject instanceof JAXBElement) {
/*  91 */       JAXBElement e = (JAXBElement)jaxbObject;
/*  92 */       this.nsUri = e.getName().getNamespaceURI();
/*  93 */       this.localName = e.getName().getLocalPart();
/*     */     } 
/*     */   }
/*     */   
/*     */   public JAXBHeader(XMLBridge bridge, Object jaxbObject) {
/*  98 */     this.jaxbObject = jaxbObject;
/*  99 */     this.bridge = bridge;
/*     */     
/* 101 */     QName tagName = (bridge.getTypeInfo()).tagName;
/* 102 */     this.nsUri = tagName.getNamespaceURI();
/* 103 */     this.localName = tagName.getLocalPart();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void parse() {
/* 110 */     RootElementSniffer sniffer = new RootElementSniffer();
/*     */     try {
/* 112 */       this.bridge.marshal(this.jaxbObject, (ContentHandler)sniffer, null);
/* 113 */     } catch (JAXBException e) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 120 */       this.nsUri = sniffer.getNsUri();
/* 121 */       this.localName = sniffer.getLocalName();
/* 122 */       this.atts = sniffer.getAttributes();
/*     */     } 
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public String getNamespaceURI() {
/* 128 */     if (this.nsUri == null)
/* 129 */       parse(); 
/* 130 */     return this.nsUri;
/*     */   }
/*     */   @NotNull
/*     */   public String getLocalPart() {
/* 134 */     if (this.localName == null)
/* 135 */       parse(); 
/* 136 */     return this.localName;
/*     */   }
/*     */   
/*     */   public String getAttribute(String nsUri, String localName) {
/* 140 */     if (this.atts == null)
/* 141 */       parse(); 
/* 142 */     return this.atts.getValue(nsUri, localName);
/*     */   }
/*     */   
/*     */   public XMLStreamReader readHeader() throws XMLStreamException {
/* 146 */     if (this.infoset == null) {
/* 147 */       MutableXMLStreamBuffer buffer = new MutableXMLStreamBuffer();
/* 148 */       writeTo(buffer.createFromXMLStreamWriter());
/* 149 */       this.infoset = (XMLStreamBuffer)buffer;
/*     */     } 
/* 151 */     return (XMLStreamReader)this.infoset.readAsXMLStreamReader();
/*     */   }
/*     */   
/*     */   public <T> T readAsJAXB(Unmarshaller unmarshaller) throws JAXBException {
/*     */     try {
/* 156 */       JAXBResult r = new JAXBResult(unmarshaller);
/*     */       
/* 158 */       r.getHandler().startDocument();
/* 159 */       this.bridge.marshal(this.jaxbObject, (Result)r);
/* 160 */       r.getHandler().endDocument();
/* 161 */       return (T)r.getResult();
/* 162 */     } catch (SAXException e) {
/* 163 */       throw new JAXBException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public <T> T readAsJAXB(Bridge<T> bridge) throws JAXBException {
/* 168 */     return (T)bridge.unmarshal(new JAXBBridgeSource(this.bridge, this.jaxbObject));
/*     */   }
/*     */   
/*     */   public <T> T readAsJAXB(XMLBridge<T> bond) throws JAXBException {
/* 172 */     return (T)bond.unmarshal(new JAXBBridgeSource(this.bridge, this.jaxbObject), null);
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeTo(XMLStreamWriter sw) throws XMLStreamException {
/*     */     try {
/* 178 */       String encoding = XMLStreamWriterUtil.getEncoding(sw);
/*     */ 
/*     */       
/* 181 */       OutputStream os = this.bridge.supportOutputStream() ? XMLStreamWriterUtil.getOutputStream(sw) : null;
/* 182 */       if (os != null && encoding != null && encoding.equalsIgnoreCase("utf-8")) {
/* 183 */         this.bridge.marshal(this.jaxbObject, os, sw.getNamespaceContext(), null);
/*     */       } else {
/* 185 */         this.bridge.marshal(this.jaxbObject, sw, null);
/*     */       } 
/* 187 */     } catch (JAXBException e) {
/* 188 */       throw new XMLStreamException2(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void writeTo(SOAPMessage saaj) throws SOAPException {
/*     */     try {
/* 194 */       SOAPHeader header = saaj.getSOAPHeader();
/* 195 */       if (header == null)
/* 196 */         header = saaj.getSOAPPart().getEnvelope().addHeader(); 
/* 197 */       this.bridge.marshal(this.jaxbObject, (Node)header);
/* 198 */     } catch (JAXBException e) {
/* 199 */       throw new SOAPException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void writeTo(ContentHandler contentHandler, ErrorHandler errorHandler) throws SAXException {
/*     */     try {
/* 205 */       this.bridge.marshal(this.jaxbObject, contentHandler, null);
/* 206 */     } catch (JAXBException e) {
/* 207 */       SAXParseException x = new SAXParseException(e.getMessage(), null, null, -1, -1, (Exception)e);
/* 208 */       errorHandler.fatalError(x);
/* 209 */       throw x;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/message/jaxb/JAXBHeader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */