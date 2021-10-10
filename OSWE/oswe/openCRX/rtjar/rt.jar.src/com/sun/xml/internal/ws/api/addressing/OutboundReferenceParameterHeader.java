/*     */ package com.sun.xml.internal.ws.api.addressing;
/*     */ 
/*     */ import com.sun.istack.internal.FinalArrayList;
/*     */ import com.sun.istack.internal.NotNull;
/*     */ import com.sun.xml.internal.stream.buffer.XMLStreamBuffer;
/*     */ import com.sun.xml.internal.stream.buffer.XMLStreamBufferException;
/*     */ import com.sun.xml.internal.ws.message.AbstractHeaderImpl;
/*     */ import com.sun.xml.internal.ws.util.xml.XMLStreamWriterFilter;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.soap.SOAPException;
/*     */ import javax.xml.soap.SOAPHeader;
/*     */ import javax.xml.soap.SOAPMessage;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import javax.xml.stream.XMLStreamReader;
/*     */ import javax.xml.stream.XMLStreamWriter;
/*     */ import javax.xml.stream.util.StreamReaderDelegate;
/*     */ import javax.xml.ws.WebServiceException;
/*     */ import org.w3c.dom.Element;
/*     */ import org.w3c.dom.Node;
/*     */ import org.xml.sax.Attributes;
/*     */ import org.xml.sax.ContentHandler;
/*     */ import org.xml.sax.ErrorHandler;
/*     */ import org.xml.sax.SAXException;
/*     */ import org.xml.sax.helpers.AttributesImpl;
/*     */ import org.xml.sax.helpers.XMLFilterImpl;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class OutboundReferenceParameterHeader
/*     */   extends AbstractHeaderImpl
/*     */ {
/*     */   private final XMLStreamBuffer infoset;
/*     */   private final String nsUri;
/*     */   private final String localName;
/*     */   private FinalArrayList<Attribute> attributes;
/*     */   private static final String TRUE_VALUE = "1";
/*     */   private static final String IS_REFERENCE_PARAMETER = "IsReferenceParameter";
/*     */   
/*     */   OutboundReferenceParameterHeader(XMLStreamBuffer infoset, String nsUri, String localName) {
/*  78 */     this.infoset = infoset;
/*  79 */     this.nsUri = nsUri;
/*  80 */     this.localName = localName;
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public String getNamespaceURI() {
/*  85 */     return this.nsUri;
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public String getLocalPart() {
/*  90 */     return this.localName;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getAttribute(String nsUri, String localName) {
/*  95 */     if (this.attributes == null) {
/*  96 */       parseAttributes();
/*     */     }
/*  98 */     for (int i = this.attributes.size() - 1; i >= 0; i--) {
/*  99 */       Attribute a = (Attribute)this.attributes.get(i);
/* 100 */       if (a.localName.equals(localName) && a.nsUri.equals(nsUri)) {
/* 101 */         return a.value;
/*     */       }
/*     */     } 
/* 104 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void parseAttributes() {
/*     */     try {
/* 115 */       XMLStreamReader reader = readHeader();
/* 116 */       reader.nextTag();
/*     */       
/* 118 */       this.attributes = new FinalArrayList();
/* 119 */       boolean refParamAttrWritten = false;
/* 120 */       for (int i = 0; i < reader.getAttributeCount(); i++) {
/* 121 */         String attrLocalName = reader.getAttributeLocalName(i);
/* 122 */         String namespaceURI = reader.getAttributeNamespace(i);
/* 123 */         String value = reader.getAttributeValue(i);
/* 124 */         if (namespaceURI.equals(AddressingVersion.W3C.nsUri) && attrLocalName.equals("IS_REFERENCE_PARAMETER")) {
/* 125 */           refParamAttrWritten = true;
/*     */         }
/* 127 */         this.attributes.add(new Attribute(namespaceURI, attrLocalName, value));
/*     */       } 
/*     */       
/* 130 */       if (!refParamAttrWritten) {
/* 131 */         this.attributes.add(new Attribute(AddressingVersion.W3C.nsUri, "IsReferenceParameter", "1"));
/*     */       }
/* 133 */     } catch (XMLStreamException e) {
/* 134 */       throw new WebServiceException("Unable to read the attributes for {" + this.nsUri + "}" + this.localName + " header", e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public XMLStreamReader readHeader() throws XMLStreamException {
/* 140 */     return new StreamReaderDelegate((XMLStreamReader)this.infoset.readAsXMLStreamReader()) {
/* 141 */         int state = 0;
/*     */         
/*     */         public int next() throws XMLStreamException {
/* 144 */           return check(super.next());
/*     */         }
/*     */ 
/*     */         
/*     */         public int nextTag() throws XMLStreamException {
/* 149 */           return check(super.nextTag());
/*     */         }
/*     */         
/*     */         private int check(int type) {
/* 153 */           switch (this.state) {
/*     */             case 0:
/* 155 */               if (type == 1) {
/* 156 */                 this.state = 1;
/*     */               }
/*     */               break;
/*     */             case 1:
/* 160 */               this.state = 2;
/*     */               break;
/*     */           } 
/*     */ 
/*     */ 
/*     */           
/* 166 */           return type;
/*     */         }
/*     */ 
/*     */         
/*     */         public int getAttributeCount() {
/* 171 */           if (this.state == 1) {
/* 172 */             return super.getAttributeCount() + 1;
/*     */           }
/* 174 */           return super.getAttributeCount();
/*     */         }
/*     */ 
/*     */ 
/*     */         
/*     */         public String getAttributeLocalName(int index) {
/* 180 */           if (this.state == 1 && index == super.getAttributeCount()) {
/* 181 */             return "IsReferenceParameter";
/*     */           }
/* 183 */           return super.getAttributeLocalName(index);
/*     */         }
/*     */ 
/*     */ 
/*     */         
/*     */         public String getAttributeNamespace(int index) {
/* 189 */           if (this.state == 1 && index == super.getAttributeCount()) {
/* 190 */             return AddressingVersion.W3C.nsUri;
/*     */           }
/*     */           
/* 193 */           return super.getAttributeNamespace(index);
/*     */         }
/*     */ 
/*     */ 
/*     */         
/*     */         public String getAttributePrefix(int index) {
/* 199 */           if (this.state == 1 && index == super.getAttributeCount()) {
/* 200 */             return "wsa";
/*     */           }
/* 202 */           return super.getAttributePrefix(index);
/*     */         }
/*     */ 
/*     */ 
/*     */         
/*     */         public String getAttributeType(int index) {
/* 208 */           if (this.state == 1 && index == super.getAttributeCount()) {
/* 209 */             return "CDATA";
/*     */           }
/* 211 */           return super.getAttributeType(index);
/*     */         }
/*     */ 
/*     */ 
/*     */         
/*     */         public String getAttributeValue(int index) {
/* 217 */           if (this.state == 1 && index == super.getAttributeCount()) {
/* 218 */             return "1";
/*     */           }
/* 220 */           return super.getAttributeValue(index);
/*     */         }
/*     */ 
/*     */ 
/*     */         
/*     */         public QName getAttributeName(int index) {
/* 226 */           if (this.state == 1 && index == super.getAttributeCount()) {
/* 227 */             return new QName(AddressingVersion.W3C.nsUri, "IsReferenceParameter", "wsa");
/*     */           }
/* 229 */           return super.getAttributeName(index);
/*     */         }
/*     */ 
/*     */ 
/*     */         
/*     */         public String getAttributeValue(String namespaceUri, String localName) {
/* 235 */           if (this.state == 1 && localName.equals("IsReferenceParameter") && namespaceUri.equals(AddressingVersion.W3C.nsUri)) {
/* 236 */             return "1";
/*     */           }
/* 238 */           return super.getAttributeValue(namespaceUri, localName);
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeTo(XMLStreamWriter w) throws XMLStreamException {
/* 246 */     this.infoset.writeToXMLStreamWriter((XMLStreamWriter)new XMLStreamWriterFilter(w) {
/*     */           private boolean root = true;
/*     */           private boolean onRootEl = true;
/*     */           
/*     */           public void writeStartElement(String localName) throws XMLStreamException {
/* 251 */             super.writeStartElement(localName);
/* 252 */             writeAddedAttribute();
/*     */           }
/*     */           
/*     */           private void writeAddedAttribute() throws XMLStreamException {
/* 256 */             if (!this.root) {
/* 257 */               this.onRootEl = false;
/*     */               return;
/*     */             } 
/* 260 */             this.root = false;
/* 261 */             writeNamespace("wsa", AddressingVersion.W3C.nsUri);
/* 262 */             super.writeAttribute("wsa", AddressingVersion.W3C.nsUri, "IsReferenceParameter", "1");
/*     */           }
/*     */ 
/*     */           
/*     */           public void writeStartElement(String namespaceURI, String localName) throws XMLStreamException {
/* 267 */             super.writeStartElement(namespaceURI, localName);
/* 268 */             writeAddedAttribute();
/*     */           }
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           public void writeStartElement(String prefix, String localName, String namespaceURI) throws XMLStreamException {
/* 275 */             boolean prefixDeclared = isPrefixDeclared(prefix, namespaceURI);
/* 276 */             super.writeStartElement(prefix, localName, namespaceURI);
/* 277 */             if (!prefixDeclared && !prefix.equals("")) {
/* 278 */               super.writeNamespace(prefix, namespaceURI);
/*     */             }
/* 280 */             writeAddedAttribute();
/*     */           }
/*     */           
/*     */           public void writeNamespace(String prefix, String namespaceURI) throws XMLStreamException {
/* 284 */             if (!isPrefixDeclared(prefix, namespaceURI)) {
/* 285 */               super.writeNamespace(prefix, namespaceURI);
/*     */             }
/*     */           }
/*     */ 
/*     */ 
/*     */           
/*     */           public void writeAttribute(String prefix, String namespaceURI, String localName, String value) throws XMLStreamException {
/* 292 */             if (this.onRootEl && namespaceURI.equals(AddressingVersion.W3C.nsUri) && localName
/* 293 */               .equals("IsReferenceParameter")) {
/*     */               return;
/*     */             }
/* 296 */             this.writer.writeAttribute(prefix, namespaceURI, localName, value);
/*     */           }
/*     */ 
/*     */           
/*     */           public void writeAttribute(String namespaceURI, String localName, String value) throws XMLStreamException {
/* 301 */             this.writer.writeAttribute(namespaceURI, localName, value);
/*     */           }
/*     */           private boolean isPrefixDeclared(String prefix, String namespaceURI) {
/* 304 */             return namespaceURI.equals(getNamespaceContext().getNamespaceURI(prefix));
/*     */           }
/*     */         }true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeTo(SOAPMessage saaj) throws SOAPException {
/*     */     try {
/* 315 */       SOAPHeader header = saaj.getSOAPHeader();
/* 316 */       if (header == null) {
/* 317 */         header = saaj.getSOAPPart().getEnvelope().addHeader();
/*     */       }
/* 319 */       Element node = (Element)this.infoset.writeTo((Node)header);
/* 320 */       node.setAttributeNS(AddressingVersion.W3C.nsUri, AddressingVersion.W3C.getPrefix() + ":" + "IsReferenceParameter", "1");
/* 321 */     } catch (XMLStreamBufferException e) {
/* 322 */       throw new SOAPException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void writeTo(ContentHandler contentHandler, ErrorHandler errorHandler) throws SAXException {
/*     */     class Filter extends XMLFilterImpl { private int depth;
/*     */       
/*     */       Filter(ContentHandler ch) {
/* 330 */         this.depth = 0;
/*     */         setContentHandler(ch);
/*     */       } public void startElement(String uri, String localName, String qName, Attributes atts) throws SAXException {
/* 333 */         if (this.depth++ == 0) {
/*     */           
/* 335 */           startPrefixMapping("wsa", AddressingVersion.W3C.nsUri);
/*     */ 
/*     */           
/* 338 */           if (atts.getIndex(AddressingVersion.W3C.nsUri, "IsReferenceParameter") == -1) {
/* 339 */             AttributesImpl atts2 = new AttributesImpl(atts);
/* 340 */             atts2.addAttribute(AddressingVersion.W3C.nsUri, "IsReferenceParameter", "wsa:IsReferenceParameter", "CDATA", "1");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 346 */             atts = atts2;
/*     */           } 
/*     */         } 
/*     */         
/* 350 */         super.startElement(uri, localName, qName, atts);
/*     */       }
/*     */ 
/*     */       
/*     */       public void endElement(String uri, String localName, String qName) throws SAXException {
/* 355 */         super.endElement(uri, localName, qName);
/* 356 */         if (--this.depth == 0) {
/* 357 */           endPrefixMapping("wsa");
/*     */         }
/*     */       } }
/*     */     ;
/*     */     
/* 362 */     this.infoset.writeTo(new Filter(contentHandler), errorHandler);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static final class Attribute
/*     */   {
/*     */     final String nsUri;
/*     */ 
/*     */     
/*     */     final String localName;
/*     */     
/*     */     final String value;
/*     */ 
/*     */     
/*     */     public Attribute(String nsUri, String localName, String value) {
/* 378 */       this.nsUri = fixNull(nsUri);
/* 379 */       this.localName = localName;
/* 380 */       this.value = value;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private static String fixNull(String s) {
/* 387 */       return (s == null) ? "" : s;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/api/addressing/OutboundReferenceParameterHeader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */