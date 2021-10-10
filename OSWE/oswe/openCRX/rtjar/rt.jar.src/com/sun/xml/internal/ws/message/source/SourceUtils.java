/*     */ package com.sun.xml.internal.ws.message.source;
/*     */ 
/*     */ import com.sun.xml.internal.ws.message.RootElementSniffer;
/*     */ import com.sun.xml.internal.ws.streaming.SourceReaderFactory;
/*     */ import com.sun.xml.internal.ws.util.xml.XmlUtil;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import javax.xml.stream.XMLStreamReader;
/*     */ import javax.xml.stream.XMLStreamWriter;
/*     */ import javax.xml.transform.Source;
/*     */ import javax.xml.transform.Transformer;
/*     */ import javax.xml.transform.TransformerConfigurationException;
/*     */ import javax.xml.transform.TransformerException;
/*     */ import javax.xml.transform.dom.DOMSource;
/*     */ import javax.xml.transform.sax.SAXResult;
/*     */ import javax.xml.transform.sax.SAXSource;
/*     */ import javax.xml.ws.WebServiceException;
/*     */ import org.w3c.dom.Document;
/*     */ import org.w3c.dom.Node;
/*     */ import org.xml.sax.ContentHandler;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class SourceUtils
/*     */ {
/*     */   int srcType;
/*     */   private static final int domSource = 1;
/*     */   private static final int streamSource = 2;
/*     */   private static final int saxSource = 4;
/*     */   
/*     */   public SourceUtils(Source src) {
/*  62 */     if (src instanceof javax.xml.transform.stream.StreamSource) {
/*  63 */       this.srcType = 2;
/*  64 */     } else if (src instanceof DOMSource) {
/*  65 */       this.srcType = 1;
/*  66 */     } else if (src instanceof SAXSource) {
/*  67 */       this.srcType = 4;
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean isDOMSource() {
/*  72 */     return ((this.srcType & 0x1) == 1);
/*     */   }
/*     */   
/*     */   public boolean isStreamSource() {
/*  76 */     return ((this.srcType & 0x2) == 2);
/*     */   }
/*     */   
/*     */   public boolean isSaxSource() {
/*  80 */     return ((this.srcType & 0x4) == 4);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public QName sniff(Source src) {
/*  90 */     return sniff(src, new RootElementSniffer());
/*     */   }
/*     */   
/*     */   public QName sniff(Source src, RootElementSniffer sniffer) {
/*  94 */     String localName = null;
/*  95 */     String namespaceUri = null;
/*     */     
/*  97 */     if (isDOMSource()) {
/*  98 */       DOMSource domSrc = (DOMSource)src;
/*  99 */       Node n = domSrc.getNode();
/* 100 */       if (n.getNodeType() == 9) {
/* 101 */         n = ((Document)n).getDocumentElement();
/*     */       }
/* 103 */       localName = n.getLocalName();
/* 104 */       namespaceUri = n.getNamespaceURI();
/* 105 */     } else if (isSaxSource()) {
/* 106 */       SAXSource saxSrc = (SAXSource)src;
/* 107 */       SAXResult saxResult = new SAXResult((ContentHandler)sniffer);
/*     */       try {
/* 109 */         Transformer tr = XmlUtil.newTransformer();
/* 110 */         tr.transform(saxSrc, saxResult);
/* 111 */       } catch (TransformerConfigurationException e) {
/* 112 */         throw new WebServiceException(e);
/* 113 */       } catch (TransformerException e) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 120 */         localName = sniffer.getLocalName();
/* 121 */         namespaceUri = sniffer.getNsUri();
/*     */       } 
/*     */     } 
/* 124 */     return new QName(namespaceUri, localName);
/*     */   }
/*     */   
/*     */   public static void serializeSource(Source src, XMLStreamWriter writer) throws XMLStreamException {
/* 128 */     XMLStreamReader reader = SourceReaderFactory.createSourceReader(src, true);
/*     */     while (true) {
/*     */       String uri, prefix, localName;
/* 131 */       int n, i, state = reader.next();
/* 132 */       switch (state) {
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         case 1:
/* 138 */           uri = reader.getNamespaceURI();
/* 139 */           prefix = reader.getPrefix();
/* 140 */           localName = reader.getLocalName();
/*     */           
/* 142 */           if (prefix == null) {
/* 143 */             if (uri == null) {
/* 144 */               writer.writeStartElement(localName);
/*     */             } else {
/* 146 */               writer.writeStartElement(uri, localName);
/*     */             
/*     */             }
/*     */           
/*     */           }
/* 151 */           else if (prefix.length() > 0) {
/*     */ 
/*     */ 
/*     */             
/* 155 */             String writerURI = null;
/* 156 */             if (writer.getNamespaceContext() != null) {
/* 157 */               writerURI = writer.getNamespaceContext().getNamespaceURI(prefix);
/*     */             }
/* 159 */             String writerPrefix = writer.getPrefix(uri);
/* 160 */             if (declarePrefix(prefix, uri, writerPrefix, writerURI)) {
/* 161 */               writer.writeStartElement(prefix, localName, uri);
/* 162 */               writer.setPrefix(prefix, (uri != null) ? uri : "");
/* 163 */               writer.writeNamespace(prefix, uri);
/*     */             } else {
/* 165 */               writer.writeStartElement(prefix, localName, uri);
/*     */             } 
/*     */           } else {
/* 168 */             writer.writeStartElement(prefix, localName, uri);
/*     */           } 
/*     */ 
/*     */           
/* 172 */           n = reader.getNamespaceCount();
/*     */           
/* 174 */           for (i = 0; i < n; i++) {
/* 175 */             String nsPrefix = reader.getNamespacePrefix(i);
/* 176 */             if (nsPrefix == null) {
/* 177 */               nsPrefix = "";
/*     */             }
/*     */             
/* 180 */             String writerURI = null;
/* 181 */             if (writer.getNamespaceContext() != null) {
/* 182 */               writerURI = writer.getNamespaceContext().getNamespaceURI(nsPrefix);
/*     */             }
/*     */ 
/*     */ 
/*     */             
/* 187 */             String readerURI = reader.getNamespaceURI(i);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 196 */             if (writerURI == null || nsPrefix.length() == 0 || prefix.length() == 0 || (
/* 197 */               !nsPrefix.equals(prefix) && !writerURI.equals(readerURI))) {
/* 198 */               writer.setPrefix(nsPrefix, (readerURI != null) ? readerURI : "");
/* 199 */               writer.writeNamespace(nsPrefix, (readerURI != null) ? readerURI : "");
/*     */             } 
/*     */           } 
/*     */ 
/*     */           
/* 204 */           n = reader.getAttributeCount();
/* 205 */           for (i = 0; i < n; i++) {
/* 206 */             String attrPrefix = reader.getAttributePrefix(i);
/* 207 */             String attrURI = reader.getAttributeNamespace(i);
/*     */             
/* 209 */             writer.writeAttribute((attrPrefix != null) ? attrPrefix : "", (attrURI != null) ? attrURI : "", reader
/*     */                 
/* 211 */                 .getAttributeLocalName(i), reader
/* 212 */                 .getAttributeValue(i));
/*     */             
/* 214 */             setUndeclaredPrefix(attrPrefix, attrURI, writer);
/*     */           } 
/*     */           break;
/*     */         case 2:
/* 218 */           writer.writeEndElement();
/*     */           break;
/*     */         case 4:
/* 221 */           writer.writeCharacters(reader.getText());
/*     */           break;
/*     */       } 
/*     */ 
/*     */       
/* 226 */       if (state == 8) {
/* 227 */         reader.close();
/*     */         return;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void setUndeclaredPrefix(String prefix, String readerURI, XMLStreamWriter writer) throws XMLStreamException {
/* 237 */     String writerURI = null;
/* 238 */     if (writer.getNamespaceContext() != null) {
/* 239 */       writerURI = writer.getNamespaceContext().getNamespaceURI(prefix);
/*     */     }
/*     */     
/* 242 */     if (writerURI == null) {
/* 243 */       writer.setPrefix(prefix, (readerURI != null) ? readerURI : "");
/* 244 */       writer.writeNamespace(prefix, (readerURI != null) ? readerURI : "");
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
/*     */   private static boolean declarePrefix(String rPrefix, String rUri, String wPrefix, String wUri) {
/* 256 */     if (wUri == null || (wPrefix != null && !rPrefix.equals(wPrefix)) || (rUri != null && 
/* 257 */       !wUri.equals(rUri))) {
/* 258 */       return true;
/*     */     }
/* 260 */     return false;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/message/source/SourceUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */