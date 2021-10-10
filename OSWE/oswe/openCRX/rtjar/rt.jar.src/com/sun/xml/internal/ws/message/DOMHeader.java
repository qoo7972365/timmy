/*     */ package com.sun.xml.internal.ws.message;
/*     */ 
/*     */ import com.sun.xml.internal.bind.api.Bridge;
/*     */ import com.sun.xml.internal.bind.unmarshaller.DOMScanner;
/*     */ import com.sun.xml.internal.ws.streaming.DOMStreamReader;
/*     */ import com.sun.xml.internal.ws.util.DOMUtil;
/*     */ import javax.xml.bind.JAXBException;
/*     */ import javax.xml.bind.Unmarshaller;
/*     */ import javax.xml.soap.SOAPException;
/*     */ import javax.xml.soap.SOAPHeader;
/*     */ import javax.xml.soap.SOAPMessage;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import javax.xml.stream.XMLStreamReader;
/*     */ import javax.xml.stream.XMLStreamWriter;
/*     */ import org.w3c.dom.Element;
/*     */ import org.w3c.dom.Node;
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
/*     */ public class DOMHeader<N extends Element>
/*     */   extends AbstractHeaderImpl
/*     */ {
/*     */   protected final N node;
/*     */   private final String nsUri;
/*     */   private final String localName;
/*     */   
/*     */   public DOMHeader(N node) {
/*  59 */     assert node != null;
/*  60 */     this.node = node;
/*     */     
/*  62 */     this.nsUri = fixNull(node.getNamespaceURI());
/*  63 */     this.localName = node.getLocalName();
/*     */   }
/*     */ 
/*     */   
/*     */   public String getNamespaceURI() {
/*  68 */     return this.nsUri;
/*     */   }
/*     */   
/*     */   public String getLocalPart() {
/*  72 */     return this.localName;
/*     */   }
/*     */   
/*     */   public XMLStreamReader readHeader() throws XMLStreamException {
/*  76 */     DOMStreamReader r = new DOMStreamReader((Node)this.node);
/*  77 */     r.nextTag();
/*  78 */     return (XMLStreamReader)r;
/*     */   }
/*     */   
/*     */   public <T> T readAsJAXB(Unmarshaller unmarshaller) throws JAXBException {
/*  82 */     return (T)unmarshaller.unmarshal((Node)this.node);
/*     */   }
/*     */   
/*     */   public <T> T readAsJAXB(Bridge<T> bridge) throws JAXBException {
/*  86 */     return (T)bridge.unmarshal((Node)this.node);
/*     */   }
/*     */   
/*     */   public void writeTo(XMLStreamWriter w) throws XMLStreamException {
/*  90 */     DOMUtil.serializeNode((Element)this.node, w);
/*     */   }
/*     */   
/*     */   private static String fixNull(String s) {
/*  94 */     if (s != null) return s; 
/*  95 */     return "";
/*     */   }
/*     */   
/*     */   public void writeTo(ContentHandler contentHandler, ErrorHandler errorHandler) throws SAXException {
/*  99 */     DOMScanner ds = new DOMScanner();
/* 100 */     ds.setContentHandler(contentHandler);
/* 101 */     ds.scan((Element)this.node);
/*     */   }
/*     */   
/*     */   public String getAttribute(String nsUri, String localName) {
/* 105 */     if (nsUri.length() == 0) nsUri = null; 
/* 106 */     return this.node.getAttributeNS(nsUri, localName);
/*     */   }
/*     */   
/*     */   public void writeTo(SOAPMessage saaj) throws SOAPException {
/* 110 */     SOAPHeader header = saaj.getSOAPHeader();
/* 111 */     if (header == null)
/* 112 */       header = saaj.getSOAPPart().getEnvelope().addHeader(); 
/* 113 */     Node clone = header.getOwnerDocument().importNode((Node)this.node, true);
/* 114 */     header.appendChild(clone);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getStringContent() {
/* 119 */     return this.node.getTextContent();
/*     */   }
/*     */   
/*     */   public N getWrappedNode() {
/* 123 */     return this.node;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 129 */     return getWrappedNode().hashCode();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/* 135 */     if (obj instanceof DOMHeader) {
/* 136 */       return getWrappedNode().equals(((DOMHeader)obj).getWrappedNode());
/*     */     }
/* 138 */     return false;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/message/DOMHeader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */