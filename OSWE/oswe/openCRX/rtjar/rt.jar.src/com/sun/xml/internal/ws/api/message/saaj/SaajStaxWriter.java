/*     */ package com.sun.xml.internal.ws.api.message.saaj;
/*     */ 
/*     */ import java.util.Arrays;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.NoSuchElementException;
/*     */ import javax.xml.namespace.NamespaceContext;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.soap.SOAPElement;
/*     */ import javax.xml.soap.SOAPException;
/*     */ import javax.xml.soap.SOAPMessage;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import javax.xml.stream.XMLStreamWriter;
/*     */ import org.w3c.dom.Comment;
/*     */ import org.w3c.dom.Node;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SaajStaxWriter
/*     */   implements XMLStreamWriter
/*     */ {
/*     */   protected SOAPMessage soap;
/*     */   protected String envURI;
/*     */   protected SOAPElement currentElement;
/*     */   protected DeferredElement deferredElement;
/*     */   protected static final String Envelope = "Envelope";
/*     */   protected static final String Header = "Header";
/*     */   protected static final String Body = "Body";
/*     */   protected static final String xmlns = "xmlns";
/*     */   
/*     */   public SaajStaxWriter(SOAPMessage msg) throws SOAPException {
/*  73 */     this.soap = msg;
/*  74 */     this.currentElement = (SOAPElement)this.soap.getSOAPPart().getEnvelope();
/*  75 */     this.envURI = this.currentElement.getNamespaceURI();
/*  76 */     this.deferredElement = new DeferredElement();
/*     */   }
/*     */   
/*     */   public SOAPMessage getSOAPMessage() {
/*  80 */     return this.soap;
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeStartElement(String localName) throws XMLStreamException {
/*  85 */     this.currentElement = this.deferredElement.flushTo(this.currentElement);
/*  86 */     this.deferredElement.setLocalName(localName);
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeStartElement(String ns, String ln) throws XMLStreamException {
/*  91 */     writeStartElement(null, ln, ns);
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeStartElement(String prefix, String ln, String ns) throws XMLStreamException {
/*  96 */     this.currentElement = this.deferredElement.flushTo(this.currentElement);
/*     */     
/*  98 */     if (this.envURI.equals(ns)) {
/*     */       try {
/* 100 */         if ("Envelope".equals(ln)) {
/* 101 */           this.currentElement = (SOAPElement)this.soap.getSOAPPart().getEnvelope();
/* 102 */           fixPrefix(prefix); return;
/*     */         } 
/* 104 */         if ("Header".equals(ln)) {
/* 105 */           this.currentElement = (SOAPElement)this.soap.getSOAPHeader();
/* 106 */           fixPrefix(prefix); return;
/*     */         } 
/* 108 */         if ("Body".equals(ln)) {
/* 109 */           this.currentElement = (SOAPElement)this.soap.getSOAPBody();
/* 110 */           fixPrefix(prefix);
/*     */           return;
/*     */         } 
/* 113 */       } catch (SOAPException e) {
/* 114 */         throw new XMLStreamException(e);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/* 119 */     this.deferredElement.setLocalName(ln);
/* 120 */     this.deferredElement.setNamespaceUri(ns);
/* 121 */     this.deferredElement.setPrefix(prefix);
/*     */   }
/*     */ 
/*     */   
/*     */   private void fixPrefix(String prfx) throws XMLStreamException {
/* 126 */     String oldPrfx = this.currentElement.getPrefix();
/* 127 */     if (prfx != null && !prfx.equals(oldPrfx)) {
/* 128 */       this.currentElement.setPrefix(prfx);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeEmptyElement(String uri, String ln) throws XMLStreamException {
/* 134 */     writeStartElement(null, ln, uri);
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeEmptyElement(String prefix, String ln, String uri) throws XMLStreamException {
/* 139 */     writeStartElement(prefix, ln, uri);
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeEmptyElement(String ln) throws XMLStreamException {
/* 144 */     writeStartElement(null, ln, null);
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeEndElement() throws XMLStreamException {
/* 149 */     this.currentElement = this.deferredElement.flushTo(this.currentElement);
/* 150 */     if (this.currentElement != null) this.currentElement = this.currentElement.getParentElement();
/*     */   
/*     */   }
/*     */   
/*     */   public void writeEndDocument() throws XMLStreamException {
/* 155 */     this.currentElement = this.deferredElement.flushTo(this.currentElement);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void close() throws XMLStreamException {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void flush() throws XMLStreamException {}
/*     */ 
/*     */   
/*     */   public void writeAttribute(String ln, String val) throws XMLStreamException {
/* 168 */     writeAttribute(null, null, ln, val);
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeAttribute(String prefix, String ns, String ln, String value) throws XMLStreamException {
/* 173 */     if (ns == null && prefix == null && "xmlns".equals(ln)) {
/* 174 */       writeNamespace("", value);
/*     */     }
/* 176 */     else if (this.deferredElement.isInitialized()) {
/* 177 */       this.deferredElement.addAttribute(prefix, ns, ln, value);
/*     */     } else {
/* 179 */       addAttibuteToElement(this.currentElement, prefix, ns, ln, value);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeAttribute(String ns, String ln, String val) throws XMLStreamException {
/* 186 */     writeAttribute(null, ns, ln, val);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeNamespace(String prefix, String uri) throws XMLStreamException {
/* 192 */     String thePrefix = (prefix == null || "xmlns".equals(prefix)) ? "" : prefix;
/* 193 */     if (this.deferredElement.isInitialized()) {
/* 194 */       this.deferredElement.addNamespaceDeclaration(thePrefix, uri);
/*     */     } else {
/*     */       try {
/* 197 */         this.currentElement.addNamespaceDeclaration(thePrefix, uri);
/* 198 */       } catch (SOAPException e) {
/* 199 */         throw new XMLStreamException(e);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeDefaultNamespace(String uri) throws XMLStreamException {
/* 206 */     writeNamespace("", uri);
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeComment(String data) throws XMLStreamException {
/* 211 */     this.currentElement = this.deferredElement.flushTo(this.currentElement);
/* 212 */     Comment c = this.soap.getSOAPPart().createComment(data);
/* 213 */     this.currentElement.appendChild(c);
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeProcessingInstruction(String target) throws XMLStreamException {
/* 218 */     this.currentElement = this.deferredElement.flushTo(this.currentElement);
/* 219 */     Node n = this.soap.getSOAPPart().createProcessingInstruction(target, "");
/* 220 */     this.currentElement.appendChild(n);
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeProcessingInstruction(String target, String data) throws XMLStreamException {
/* 225 */     this.currentElement = this.deferredElement.flushTo(this.currentElement);
/* 226 */     Node n = this.soap.getSOAPPart().createProcessingInstruction(target, data);
/* 227 */     this.currentElement.appendChild(n);
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeCData(String data) throws XMLStreamException {
/* 232 */     this.currentElement = this.deferredElement.flushTo(this.currentElement);
/* 233 */     Node n = this.soap.getSOAPPart().createCDATASection(data);
/* 234 */     this.currentElement.appendChild(n);
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeDTD(String dtd) throws XMLStreamException {
/* 239 */     this.currentElement = this.deferredElement.flushTo(this.currentElement);
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeEntityRef(String name) throws XMLStreamException {
/* 244 */     this.currentElement = this.deferredElement.flushTo(this.currentElement);
/* 245 */     Node n = this.soap.getSOAPPart().createEntityReference(name);
/* 246 */     this.currentElement.appendChild(n);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeStartDocument() throws XMLStreamException {}
/*     */ 
/*     */   
/*     */   public void writeStartDocument(String version) throws XMLStreamException {
/* 255 */     if (version != null) this.soap.getSOAPPart().setXmlVersion(version);
/*     */   
/*     */   }
/*     */   
/*     */   public void writeStartDocument(String encoding, String version) throws XMLStreamException {
/* 260 */     if (version != null) this.soap.getSOAPPart().setXmlVersion(version); 
/* 261 */     if (encoding != null) {
/*     */       try {
/* 263 */         this.soap.setProperty("javax.xml.soap.character-set-encoding", encoding);
/* 264 */       } catch (SOAPException e) {
/* 265 */         throw new XMLStreamException(e);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeCharacters(String text) throws XMLStreamException {
/* 272 */     this.currentElement = this.deferredElement.flushTo(this.currentElement);
/*     */     try {
/* 274 */       this.currentElement.addTextNode(text);
/* 275 */     } catch (SOAPException e) {
/* 276 */       throw new XMLStreamException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeCharacters(char[] text, int start, int len) throws XMLStreamException {
/* 282 */     this.currentElement = this.deferredElement.flushTo(this.currentElement);
/* 283 */     char[] chr = (start == 0 && len == text.length) ? text : Arrays.copyOfRange(text, start, start + len);
/*     */     try {
/* 285 */       this.currentElement.addTextNode(new String(chr));
/* 286 */     } catch (SOAPException e) {
/* 287 */       throw new XMLStreamException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public String getPrefix(String uri) throws XMLStreamException {
/* 293 */     return this.currentElement.lookupPrefix(uri);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPrefix(String prefix, String uri) throws XMLStreamException {
/* 304 */     if (this.deferredElement.isInitialized()) {
/* 305 */       this.deferredElement.addNamespaceDeclaration(prefix, uri);
/*     */     } else {
/* 307 */       throw new XMLStreamException("Namespace not associated with any element");
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void setDefaultNamespace(String uri) throws XMLStreamException {
/* 313 */     setPrefix("", uri);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setNamespaceContext(NamespaceContext context) throws XMLStreamException {
/* 318 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Object getProperty(String name) throws IllegalArgumentException {
/* 324 */     if ("javax.xml.stream.isRepairingNamespaces".equals(name)) return Boolean.FALSE; 
/* 325 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public NamespaceContext getNamespaceContext() {
/* 330 */     return new NamespaceContext() {
/*     */         public String getNamespaceURI(String prefix) {
/* 332 */           return SaajStaxWriter.this.currentElement.getNamespaceURI(prefix);
/*     */         }
/*     */         public String getPrefix(String namespaceURI) {
/* 335 */           return SaajStaxWriter.this.currentElement.lookupPrefix(namespaceURI);
/*     */         }
/*     */         public Iterator getPrefixes(final String namespaceURI) {
/* 338 */           return new Iterator<String>() {
/* 339 */               String prefix = SaajStaxWriter.null.this.getPrefix(namespaceURI);
/*     */               public boolean hasNext() {
/* 341 */                 return (this.prefix != null);
/*     */               }
/*     */               public String next() {
/* 344 */                 if (!hasNext()) throw new NoSuchElementException(); 
/* 345 */                 String next = this.prefix;
/* 346 */                 this.prefix = null;
/* 347 */                 return next;
/*     */               }
/*     */               
/*     */               public void remove() {}
/*     */             };
/*     */         }
/*     */       };
/*     */   }
/*     */   
/*     */   static void addAttibuteToElement(SOAPElement element, String prefix, String ns, String ln, String value) throws XMLStreamException {
/*     */     try {
/* 358 */       if (ns == null) {
/* 359 */         element.setAttributeNS("", ln, value);
/*     */       } else {
/* 361 */         QName name = (prefix == null) ? new QName(ns, ln) : new QName(ns, ln, prefix);
/* 362 */         element.addAttribute(name, value);
/*     */       } 
/* 364 */     } catch (SOAPException e) {
/* 365 */       throw new XMLStreamException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static class DeferredElement
/*     */   {
/*     */     private String prefix;
/*     */ 
/*     */ 
/*     */     
/*     */     private String localName;
/*     */ 
/*     */ 
/*     */     
/*     */     private String namespaceUri;
/*     */ 
/*     */ 
/*     */     
/*     */     private final List<SaajStaxWriter.NamespaceDeclaration> namespaceDeclarations;
/*     */ 
/*     */ 
/*     */     
/*     */     private final List<SaajStaxWriter.AttributeDeclaration> attributeDeclarations;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     DeferredElement() {
/* 397 */       this.namespaceDeclarations = new LinkedList<>();
/* 398 */       this.attributeDeclarations = new LinkedList<>();
/* 399 */       reset();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void setPrefix(String prefix) {
/* 408 */       this.prefix = prefix;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void setLocalName(String localName) {
/* 421 */       if (localName == null) {
/* 422 */         throw new IllegalArgumentException("localName can not be null");
/*     */       }
/* 424 */       this.localName = localName;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void setNamespaceUri(String namespaceUri) {
/* 433 */       this.namespaceUri = namespaceUri;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void addNamespaceDeclaration(String prefix, String namespaceUri) {
/* 443 */       if (null == this.namespaceUri && null != namespaceUri && prefix.equals(emptyIfNull(this.prefix))) {
/* 444 */         this.namespaceUri = namespaceUri;
/*     */       }
/* 446 */       this.namespaceDeclarations.add(new SaajStaxWriter.NamespaceDeclaration(prefix, namespaceUri));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void addAttribute(String prefix, String ns, String ln, String value) {
/* 457 */       if (ns == null && prefix == null && "xmlns".equals(ln)) {
/* 458 */         addNamespaceDeclaration(prefix, value);
/*     */       } else {
/* 460 */         this.attributeDeclarations.add(new SaajStaxWriter.AttributeDeclaration(prefix, ns, ln, value));
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public SOAPElement flushTo(SOAPElement target) throws XMLStreamException {
/*     */       try {
/* 482 */         if (this.localName != null) {
/*     */           SOAPElement newElement;
/*     */           
/* 485 */           if (this.namespaceUri == null) {
/*     */             
/* 487 */             newElement = target.addChildElement(this.localName);
/* 488 */           } else if (this.prefix == null) {
/* 489 */             newElement = target.addChildElement(new QName(this.namespaceUri, this.localName));
/*     */           } else {
/* 491 */             newElement = target.addChildElement(this.localName, this.prefix, this.namespaceUri);
/*     */           } 
/*     */           
/* 494 */           for (SaajStaxWriter.NamespaceDeclaration namespace : this.namespaceDeclarations) {
/* 495 */             newElement.addNamespaceDeclaration(namespace.prefix, namespace.namespaceUri);
/*     */           }
/*     */           
/* 498 */           for (SaajStaxWriter.AttributeDeclaration attribute : this.attributeDeclarations) {
/* 499 */             SaajStaxWriter.addAttibuteToElement(newElement, attribute.prefix, attribute.namespaceUri, attribute.localName, attribute.value);
/*     */           }
/*     */ 
/*     */           
/* 503 */           reset();
/*     */           
/* 505 */           return newElement;
/*     */         } 
/* 507 */         return target;
/*     */       
/*     */       }
/* 510 */       catch (SOAPException e) {
/* 511 */         throw new XMLStreamException(e);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean isInitialized() {
/* 520 */       return (this.localName != null);
/*     */     }
/*     */     
/*     */     private void reset() {
/* 524 */       this.localName = null;
/* 525 */       this.prefix = null;
/* 526 */       this.namespaceUri = null;
/* 527 */       this.namespaceDeclarations.clear();
/* 528 */       this.attributeDeclarations.clear();
/*     */     }
/*     */     
/*     */     private static String emptyIfNull(String s) {
/* 532 */       return (s == null) ? "" : s;
/*     */     }
/*     */   }
/*     */   
/*     */   static class NamespaceDeclaration {
/*     */     final String prefix;
/*     */     final String namespaceUri;
/*     */     
/*     */     NamespaceDeclaration(String prefix, String namespaceUri) {
/* 541 */       this.prefix = prefix;
/* 542 */       this.namespaceUri = namespaceUri;
/*     */     }
/*     */   }
/*     */   
/*     */   static class AttributeDeclaration {
/*     */     final String prefix;
/*     */     final String namespaceUri;
/*     */     final String localName;
/*     */     final String value;
/*     */     
/*     */     AttributeDeclaration(String prefix, String namespaceUri, String localName, String value) {
/* 553 */       this.prefix = prefix;
/* 554 */       this.namespaceUri = namespaceUri;
/* 555 */       this.localName = localName;
/* 556 */       this.value = value;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/api/message/saaj/SaajStaxWriter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */