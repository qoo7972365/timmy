/*     */ package com.sun.xml.internal.ws.api.addressing;
/*     */ 
/*     */ import com.sun.istack.internal.NotNull;
/*     */ import com.sun.istack.internal.Nullable;
/*     */ import com.sun.xml.internal.ws.message.AbstractHeaderImpl;
/*     */ import com.sun.xml.internal.ws.util.xml.XmlUtil;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.parsers.DocumentBuilderFactory;
/*     */ import javax.xml.soap.SOAPException;
/*     */ import javax.xml.soap.SOAPHeader;
/*     */ import javax.xml.soap.SOAPMessage;
/*     */ import javax.xml.stream.XMLOutputFactory;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import javax.xml.stream.XMLStreamReader;
/*     */ import javax.xml.stream.XMLStreamWriter;
/*     */ import javax.xml.transform.Transformer;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class EPRHeader
/*     */   extends AbstractHeaderImpl
/*     */ {
/*     */   private final String nsUri;
/*     */   private final String localName;
/*     */   private final WSEndpointReference epr;
/*     */   
/*     */   EPRHeader(QName tagName, WSEndpointReference epr) {
/*  70 */     this.nsUri = tagName.getNamespaceURI();
/*  71 */     this.localName = tagName.getLocalPart();
/*  72 */     this.epr = epr;
/*     */   }
/*     */   @NotNull
/*     */   public String getNamespaceURI() {
/*  76 */     return this.nsUri;
/*     */   }
/*     */   @NotNull
/*     */   public String getLocalPart() {
/*  80 */     return this.localName;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public String getAttribute(@NotNull String nsUri, @NotNull String localName) {
/*     */     try {
/*  86 */       XMLStreamReader sr = this.epr.read("EndpointReference");
/*  87 */       while (sr.getEventType() != 1) {
/*  88 */         sr.next();
/*     */       }
/*  90 */       return sr.getAttributeValue(nsUri, localName);
/*  91 */     } catch (XMLStreamException e) {
/*     */       
/*  93 */       throw new AssertionError(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public XMLStreamReader readHeader() throws XMLStreamException {
/*  98 */     return this.epr.read(this.localName);
/*     */   }
/*     */   
/*     */   public void writeTo(XMLStreamWriter w) throws XMLStreamException {
/* 102 */     this.epr.writeTo(this.localName, w);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeTo(SOAPMessage saaj) throws SOAPException {
/*     */     try {
/* 110 */       Transformer t = XmlUtil.newTransformer();
/* 111 */       SOAPHeader header = saaj.getSOAPHeader();
/* 112 */       if (header == null) {
/* 113 */         header = saaj.getSOAPPart().getEnvelope().addHeader();
/*     */       }
/*     */ 
/*     */       
/* 117 */       ByteArrayOutputStream baos = new ByteArrayOutputStream();
/* 118 */       XMLStreamWriter w = XMLOutputFactory.newFactory().createXMLStreamWriter(baos);
/* 119 */       this.epr.writeTo(this.localName, w);
/* 120 */       w.flush();
/* 121 */       ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
/* 122 */       DocumentBuilderFactory fac = DocumentBuilderFactory.newInstance();
/* 123 */       fac.setNamespaceAware(true);
/* 124 */       Node eprNode = fac.newDocumentBuilder().parse(bais).getDocumentElement();
/* 125 */       Node eprNodeToAdd = header.getOwnerDocument().importNode(eprNode, true);
/* 126 */       header.appendChild(eprNodeToAdd);
/* 127 */     } catch (Exception e) {
/* 128 */       throw new SOAPException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void writeTo(ContentHandler contentHandler, ErrorHandler errorHandler) throws SAXException {
/* 133 */     this.epr.writeTo(this.localName, contentHandler, errorHandler, true);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/api/addressing/EPRHeader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */