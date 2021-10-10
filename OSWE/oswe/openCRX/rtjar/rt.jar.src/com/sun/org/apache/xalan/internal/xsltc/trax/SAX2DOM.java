/*     */ package com.sun.org.apache.xalan.internal.xsltc.trax;
/*     */ 
/*     */ import com.sun.org.apache.xalan.internal.xsltc.runtime.Constants;
/*     */ import java.util.Stack;
/*     */ import java.util.Vector;
/*     */ import javax.xml.parsers.DocumentBuilderFactory;
/*     */ import javax.xml.parsers.ParserConfigurationException;
/*     */ import jdk.xml.internal.JdkXmlUtils;
/*     */ import org.w3c.dom.Comment;
/*     */ import org.w3c.dom.Document;
/*     */ import org.w3c.dom.Element;
/*     */ import org.w3c.dom.Node;
/*     */ import org.w3c.dom.ProcessingInstruction;
/*     */ import org.xml.sax.Attributes;
/*     */ import org.xml.sax.ContentHandler;
/*     */ import org.xml.sax.Locator;
/*     */ import org.xml.sax.SAXException;
/*     */ import org.xml.sax.ext.LexicalHandler;
/*     */ import org.xml.sax.ext.Locator2;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SAX2DOM
/*     */   implements ContentHandler, LexicalHandler, Constants
/*     */ {
/*  54 */   private Node _root = null;
/*  55 */   private Document _document = null;
/*  56 */   private Node _nextSibling = null;
/*  57 */   private Stack _nodeStk = new Stack();
/*  58 */   private Vector _namespaceDecls = null;
/*  59 */   private Node _lastSibling = null;
/*  60 */   private Locator locator = null;
/*     */   
/*     */   private boolean needToSetDocumentInfo = true;
/*     */   
/*  64 */   private StringBuilder _textBuffer = new StringBuilder();
/*  65 */   private Node _nextSiblingCache = null;
/*     */ 
/*     */   
/*     */   private DocumentBuilderFactory _factory;
/*     */ 
/*     */   
/*     */   private boolean _internal = true;
/*     */ 
/*     */   
/*     */   public SAX2DOM(boolean overrideDefaultParser) throws ParserConfigurationException {
/*  75 */     this._document = createDocument(overrideDefaultParser);
/*  76 */     this._root = this._document;
/*     */   }
/*     */ 
/*     */   
/*     */   public SAX2DOM(Node root, Node nextSibling, boolean overrideDefaultParser) throws ParserConfigurationException {
/*  81 */     this._root = root;
/*  82 */     if (root instanceof Document) {
/*  83 */       this._document = (Document)root;
/*     */     }
/*  85 */     else if (root != null) {
/*  86 */       this._document = root.getOwnerDocument();
/*     */     } else {
/*     */       
/*  89 */       this._document = createDocument(overrideDefaultParser);
/*  90 */       this._root = this._document;
/*     */     } 
/*     */     
/*  93 */     this._nextSibling = nextSibling;
/*     */   }
/*     */ 
/*     */   
/*     */   public SAX2DOM(Node root, boolean overrideDefaultParser) throws ParserConfigurationException {
/*  98 */     this(root, null, overrideDefaultParser);
/*     */   }
/*     */   
/*     */   public Node getDOM() {
/* 102 */     return this._root;
/*     */   }
/*     */ 
/*     */   
/*     */   public void characters(char[] ch, int start, int length) {
/* 107 */     if (length == 0) {
/*     */       return;
/*     */     }
/*     */     
/* 111 */     Node last = this._nodeStk.peek();
/*     */ 
/*     */     
/* 114 */     if (last != this._document) {
/* 115 */       this._nextSiblingCache = this._nextSibling;
/* 116 */       this._textBuffer.append(ch, start, length);
/*     */     } 
/*     */   }
/*     */   private void appendTextNode() {
/* 120 */     if (this._textBuffer.length() > 0) {
/* 121 */       Node last = this._nodeStk.peek();
/* 122 */       if (last == this._root && this._nextSiblingCache != null) {
/* 123 */         this._lastSibling = last.insertBefore(this._document.createTextNode(this._textBuffer.toString()), this._nextSiblingCache);
/*     */       } else {
/*     */         
/* 126 */         this._lastSibling = last.appendChild(this._document.createTextNode(this._textBuffer.toString()));
/*     */       } 
/* 128 */       this._textBuffer.setLength(0);
/*     */     } 
/*     */   }
/*     */   public void startDocument() {
/* 132 */     this._nodeStk.push(this._root);
/*     */   }
/*     */   
/*     */   public void endDocument() {
/* 136 */     this._nodeStk.pop();
/*     */   }
/*     */ 
/*     */   
/*     */   private void setDocumentInfo() {
/* 141 */     if (this.locator == null)
/*     */       return;  try {
/* 143 */       this._document.setXmlVersion(((Locator2)this.locator).getXMLVersion());
/* 144 */     } catch (ClassCastException classCastException) {}
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void startElement(String namespace, String localName, String qName, Attributes attrs) {
/* 151 */     appendTextNode();
/* 152 */     if (this.needToSetDocumentInfo) {
/* 153 */       setDocumentInfo();
/* 154 */       this.needToSetDocumentInfo = false;
/*     */     } 
/*     */     
/* 157 */     Element tmp = this._document.createElementNS(namespace, qName);
/*     */ 
/*     */     
/* 160 */     if (this._namespaceDecls != null) {
/* 161 */       int nDecls = this._namespaceDecls.size();
/* 162 */       for (int j = 0; j < nDecls; j++) {
/* 163 */         String prefix = this._namespaceDecls.elementAt(j++);
/*     */         
/* 165 */         if (prefix == null || prefix.equals("")) {
/* 166 */           tmp.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns", this._namespaceDecls
/* 167 */               .elementAt(j));
/*     */         } else {
/*     */           
/* 170 */           tmp.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:" + prefix, this._namespaceDecls
/* 171 */               .elementAt(j));
/*     */         } 
/*     */       } 
/* 174 */       this._namespaceDecls.clear();
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
/* 191 */     int nattrs = attrs.getLength();
/* 192 */     for (int i = 0; i < nattrs; i++) {
/*     */       
/* 194 */       String attQName = attrs.getQName(i);
/* 195 */       String attURI = attrs.getURI(i);
/* 196 */       if (attrs.getLocalName(i).equals("")) {
/* 197 */         tmp.setAttribute(attQName, attrs.getValue(i));
/* 198 */         if (attrs.getType(i).equals("ID")) {
/* 199 */           tmp.setIdAttribute(attQName, true);
/*     */         }
/*     */       } else {
/* 202 */         tmp.setAttributeNS(attURI, attQName, attrs.getValue(i));
/* 203 */         if (attrs.getType(i).equals("ID")) {
/* 204 */           tmp.setIdAttributeNS(attURI, attrs.getLocalName(i), true);
/*     */         }
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 211 */     Node last = this._nodeStk.peek();
/*     */ 
/*     */ 
/*     */     
/* 215 */     if (last == this._root && this._nextSibling != null) {
/* 216 */       last.insertBefore(tmp, this._nextSibling);
/*     */     } else {
/* 218 */       last.appendChild(tmp);
/*     */     } 
/*     */     
/* 221 */     this._nodeStk.push(tmp);
/* 222 */     this._lastSibling = null;
/*     */   }
/*     */   
/*     */   public void endElement(String namespace, String localName, String qName) {
/* 226 */     appendTextNode();
/* 227 */     this._nodeStk.pop();
/* 228 */     this._lastSibling = null;
/*     */   }
/*     */   
/*     */   public void startPrefixMapping(String prefix, String uri) {
/* 232 */     if (this._namespaceDecls == null) {
/* 233 */       this._namespaceDecls = new Vector(2);
/*     */     }
/* 235 */     this._namespaceDecls.addElement(prefix);
/* 236 */     this._namespaceDecls.addElement(uri);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void endPrefixMapping(String prefix) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void ignorableWhitespace(char[] ch, int start, int length) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void processingInstruction(String target, String data) {
/* 254 */     appendTextNode();
/* 255 */     Node last = this._nodeStk.peek();
/* 256 */     ProcessingInstruction pi = this._document.createProcessingInstruction(target, data);
/*     */     
/* 258 */     if (pi != null) {
/* 259 */       if (last == this._root && this._nextSibling != null) {
/* 260 */         last.insertBefore(pi, this._nextSibling);
/*     */       } else {
/* 262 */         last.appendChild(pi);
/*     */       } 
/* 264 */       this._lastSibling = pi;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDocumentLocator(Locator locator) {
/* 273 */     this.locator = locator;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void skippedEntity(String name) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void comment(char[] ch, int start, int length) {
/* 288 */     appendTextNode();
/* 289 */     Node last = this._nodeStk.peek();
/* 290 */     Comment comment = this._document.createComment(new String(ch, start, length));
/* 291 */     if (comment != null) {
/* 292 */       if (last == this._root && this._nextSibling != null) {
/* 293 */         last.insertBefore(comment, this._nextSibling);
/*     */       } else {
/* 295 */         last.appendChild(comment);
/*     */       } 
/* 297 */       this._lastSibling = comment;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void startCDATA() {}
/*     */ 
/*     */   
/*     */   public void endCDATA() {}
/*     */ 
/*     */   
/*     */   public void startEntity(String name) {}
/*     */   
/*     */   private Document createDocument(boolean overrideDefaultParser) throws ParserConfigurationException {
/*     */     Document doc;
/* 312 */     if (this._factory == null) {
/* 313 */       this._factory = JdkXmlUtils.getDOMFactory(overrideDefaultParser);
/* 314 */       this._internal = true;
/* 315 */       if (!(this._factory instanceof com.sun.org.apache.xerces.internal.jaxp.DocumentBuilderFactoryImpl)) {
/* 316 */         this._internal = false;
/*     */       }
/*     */     } 
/*     */     
/* 320 */     if (this._internal) {
/*     */       
/* 322 */       doc = this._factory.newDocumentBuilder().newDocument();
/*     */     } else {
/* 324 */       synchronized (SAX2DOM.class) {
/* 325 */         doc = this._factory.newDocumentBuilder().newDocument();
/*     */       } 
/*     */     } 
/* 328 */     return doc;
/*     */   }
/*     */   
/*     */   public void endDTD() {}
/*     */   
/*     */   public void endEntity(String name) {}
/*     */   
/*     */   public void startDTD(String name, String publicId, String systemId) throws SAXException {}
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xalan/internal/xsltc/trax/SAX2DOM.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */