/*     */ package com.sun.org.apache.xalan.internal.xsltc.trax;
/*     */ 
/*     */ import com.sun.org.apache.xalan.internal.xsltc.dom.SAXImpl;
/*     */ import com.sun.org.apache.xalan.internal.xsltc.runtime.BasisLibrary;
/*     */ import java.io.IOException;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.Stack;
/*     */ import java.util.Vector;
/*     */ import org.w3c.dom.NamedNodeMap;
/*     */ import org.w3c.dom.Node;
/*     */ import org.xml.sax.ContentHandler;
/*     */ import org.xml.sax.DTDHandler;
/*     */ import org.xml.sax.EntityResolver;
/*     */ import org.xml.sax.ErrorHandler;
/*     */ import org.xml.sax.InputSource;
/*     */ import org.xml.sax.Locator;
/*     */ import org.xml.sax.SAXException;
/*     */ import org.xml.sax.SAXNotRecognizedException;
/*     */ import org.xml.sax.SAXNotSupportedException;
/*     */ import org.xml.sax.XMLReader;
/*     */ import org.xml.sax.ext.LexicalHandler;
/*     */ import org.xml.sax.helpers.AttributesImpl;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DOM2SAX
/*     */   implements XMLReader, Locator
/*     */ {
/*     */   private static final String EMPTYSTRING = "";
/*     */   private static final String XMLNS_PREFIX = "xmlns";
/*  57 */   private Node _dom = null;
/*  58 */   private ContentHandler _sax = null;
/*  59 */   private LexicalHandler _lex = null;
/*  60 */   private SAXImpl _saxImpl = null;
/*  61 */   private Map<String, Stack> _nsPrefixes = new HashMap<>();
/*     */   
/*     */   public DOM2SAX(Node root) {
/*  64 */     this._dom = root;
/*     */   }
/*     */   
/*     */   public ContentHandler getContentHandler() {
/*  68 */     return this._sax;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setContentHandler(ContentHandler handler) throws NullPointerException {
/*  74 */     this._sax = handler;
/*  75 */     if (handler instanceof LexicalHandler) {
/*  76 */       this._lex = (LexicalHandler)handler;
/*     */     }
/*     */     
/*  79 */     if (handler instanceof SAXImpl) {
/*  80 */       this._saxImpl = (SAXImpl)handler;
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
/*     */   private boolean startPrefixMapping(String prefix, String uri) throws SAXException {
/*  92 */     boolean pushed = true;
/*  93 */     Stack<String> uriStack = this._nsPrefixes.get(prefix);
/*     */     
/*  95 */     if (uriStack != null) {
/*  96 */       if (uriStack.isEmpty()) {
/*  97 */         this._sax.startPrefixMapping(prefix, uri);
/*  98 */         uriStack.push(uri);
/*     */       } else {
/*     */         
/* 101 */         String lastUri = uriStack.peek();
/* 102 */         if (!lastUri.equals(uri)) {
/* 103 */           this._sax.startPrefixMapping(prefix, uri);
/* 104 */           uriStack.push(uri);
/*     */         } else {
/*     */           
/* 107 */           pushed = false;
/*     */         } 
/*     */       } 
/*     */     } else {
/*     */       
/* 112 */       this._sax.startPrefixMapping(prefix, uri);
/* 113 */       this._nsPrefixes.put(prefix, uriStack = new Stack<>());
/* 114 */       uriStack.push(uri);
/*     */     } 
/* 116 */     return pushed;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void endPrefixMapping(String prefix) throws SAXException {
/* 126 */     Stack uriStack = this._nsPrefixes.get(prefix);
/*     */     
/* 128 */     if (uriStack != null) {
/* 129 */       this._sax.endPrefixMapping(prefix);
/* 130 */       uriStack.pop();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static String getLocalName(Node node) {
/* 140 */     String localName = node.getLocalName();
/*     */     
/* 142 */     if (localName == null) {
/* 143 */       String qname = node.getNodeName();
/* 144 */       int col = qname.lastIndexOf(':');
/* 145 */       return (col > 0) ? qname.substring(col + 1) : qname;
/*     */     } 
/* 147 */     return localName;
/*     */   }
/*     */   
/*     */   public void parse(InputSource unused) throws IOException, SAXException {
/* 151 */     parse(this._dom);
/*     */   }
/*     */   
/*     */   public void parse() throws IOException, SAXException {
/* 155 */     if (this._dom != null) {
/*     */       
/* 157 */       boolean isIncomplete = (this._dom.getNodeType() != 9);
/*     */       
/* 159 */       if (isIncomplete) {
/* 160 */         this._sax.startDocument();
/* 161 */         parse(this._dom);
/* 162 */         this._sax.endDocument();
/*     */       } else {
/*     */         
/* 165 */         parse(this._dom);
/*     */       } 
/*     */     }  } private void parse(Node node) throws IOException, SAXException { String cdata;
/*     */     Node next;
/*     */     Vector<String> pushedPrefixes;
/*     */     AttributesImpl attrs;
/*     */     NamedNodeMap map;
/*     */     int length, i;
/*     */     String qname, uri, localName;
/*     */     int nPushedPrefixes, j;
/*     */     String data;
/* 176 */     Node first = null;
/* 177 */     if (node == null)
/*     */       return; 
/* 179 */     switch (node.getNodeType()) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       case 4:
/* 189 */         cdata = node.getNodeValue();
/* 190 */         if (this._lex != null) {
/* 191 */           this._lex.startCDATA();
/* 192 */           this._sax.characters(cdata.toCharArray(), 0, cdata.length());
/* 193 */           this._lex.endCDATA();
/*     */           
/*     */           break;
/*     */         } 
/*     */         
/* 198 */         this._sax.characters(cdata.toCharArray(), 0, cdata.length());
/*     */         break;
/*     */ 
/*     */       
/*     */       case 8:
/* 203 */         if (this._lex != null) {
/* 204 */           String value = node.getNodeValue();
/* 205 */           this._lex.comment(value.toCharArray(), 0, value.length());
/*     */         } 
/*     */         break;
/*     */       case 9:
/* 209 */         this._sax.setDocumentLocator(this);
/*     */         
/* 211 */         this._sax.startDocument();
/* 212 */         next = node.getFirstChild();
/* 213 */         while (next != null) {
/* 214 */           parse(next);
/* 215 */           next = next.getNextSibling();
/*     */         } 
/* 217 */         this._sax.endDocument();
/*     */         break;
/*     */ 
/*     */       
/*     */       case 1:
/* 222 */         pushedPrefixes = new Vector();
/* 223 */         attrs = new AttributesImpl();
/* 224 */         map = node.getAttributes();
/* 225 */         length = map.getLength();
/*     */ 
/*     */         
/* 228 */         for (i = 0; i < length; i++) {
/* 229 */           Node attr = map.item(i);
/* 230 */           String qnameAttr = attr.getNodeName();
/*     */ 
/*     */           
/* 233 */           if (qnameAttr.startsWith("xmlns")) {
/* 234 */             String uriAttr = attr.getNodeValue();
/* 235 */             int colon = qnameAttr.lastIndexOf(':');
/* 236 */             String prefix = (colon > 0) ? qnameAttr.substring(colon + 1) : "";
/* 237 */             if (startPrefixMapping(prefix, uriAttr)) {
/* 238 */               pushedPrefixes.addElement(prefix);
/*     */             }
/*     */           } 
/*     */         } 
/*     */ 
/*     */         
/* 244 */         for (i = 0; i < length; i++) {
/* 245 */           Node attr = map.item(i);
/* 246 */           String qnameAttr = attr.getNodeName();
/*     */ 
/*     */           
/* 249 */           if (!qnameAttr.startsWith("xmlns")) {
/* 250 */             String uriAttr = attr.getNamespaceURI();
/* 251 */             String localNameAttr = getLocalName(attr);
/*     */ 
/*     */             
/* 254 */             if (uriAttr != null) {
/* 255 */               String prefix; int colon = qnameAttr.lastIndexOf(':');
/* 256 */               if (colon > 0) {
/* 257 */                 prefix = qnameAttr.substring(0, colon);
/*     */               
/*     */               }
/*     */               else {
/*     */                 
/* 262 */                 prefix = BasisLibrary.generatePrefix();
/* 263 */                 qnameAttr = prefix + ':' + qnameAttr;
/*     */               } 
/* 265 */               if (startPrefixMapping(prefix, uriAttr)) {
/* 266 */                 pushedPrefixes.addElement(prefix);
/*     */               }
/*     */             } 
/*     */ 
/*     */             
/* 271 */             attrs.addAttribute(attr.getNamespaceURI(), getLocalName(attr), qnameAttr, "CDATA", attr
/* 272 */                 .getNodeValue());
/*     */           } 
/*     */         } 
/*     */ 
/*     */         
/* 277 */         qname = node.getNodeName();
/* 278 */         uri = node.getNamespaceURI();
/* 279 */         localName = getLocalName(node);
/*     */ 
/*     */         
/* 282 */         if (uri != null) {
/* 283 */           int colon = qname.lastIndexOf(':');
/* 284 */           String prefix = (colon > 0) ? qname.substring(0, colon) : "";
/* 285 */           if (startPrefixMapping(prefix, uri)) {
/* 286 */             pushedPrefixes.addElement(prefix);
/*     */           }
/*     */         } 
/*     */ 
/*     */         
/* 291 */         if (this._saxImpl != null) {
/* 292 */           this._saxImpl.startElement(uri, localName, qname, attrs, node);
/*     */         } else {
/*     */           
/* 295 */           this._sax.startElement(uri, localName, qname, attrs);
/*     */         } 
/*     */ 
/*     */         
/* 299 */         next = node.getFirstChild();
/* 300 */         while (next != null) {
/* 301 */           parse(next);
/* 302 */           next = next.getNextSibling();
/*     */         } 
/*     */ 
/*     */         
/* 306 */         this._sax.endElement(uri, localName, qname);
/*     */ 
/*     */         
/* 309 */         nPushedPrefixes = pushedPrefixes.size();
/* 310 */         for (j = 0; j < nPushedPrefixes; j++) {
/* 311 */           endPrefixMapping(pushedPrefixes.elementAt(j));
/*     */         }
/*     */         break;
/*     */       
/*     */       case 7:
/* 316 */         this._sax.processingInstruction(node.getNodeName(), node
/* 317 */             .getNodeValue());
/*     */         break;
/*     */       
/*     */       case 3:
/* 321 */         data = node.getNodeValue();
/* 322 */         this._sax.characters(data.toCharArray(), 0, data.length());
/*     */         break;
/*     */     }  }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DTDHandler getDTDHandler() {
/* 332 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ErrorHandler getErrorHandler() {
/* 340 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getFeature(String name) throws SAXNotRecognizedException, SAXNotSupportedException {
/* 350 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setFeature(String name, boolean value) throws SAXNotRecognizedException, SAXNotSupportedException {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void parse(String sysId) throws IOException, SAXException {
/* 367 */     throw new IOException("This method is not yet implemented.");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDTDHandler(DTDHandler handler) throws NullPointerException {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setEntityResolver(EntityResolver resolver) throws NullPointerException {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EntityResolver getEntityResolver() {
/* 391 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setErrorHandler(ErrorHandler handler) throws NullPointerException {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setProperty(String name, Object value) throws SAXNotRecognizedException, SAXNotSupportedException {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object getProperty(String name) throws SAXNotRecognizedException, SAXNotSupportedException {
/* 418 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getColumnNumber() {
/* 426 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getLineNumber() {
/* 434 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getPublicId() {
/* 442 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getSystemId() {
/* 450 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   private String getNodeTypeFromCode(short code) {
/* 455 */     String retval = null;
/* 456 */     switch (code) {
/*     */       case 2:
/* 458 */         retval = "ATTRIBUTE_NODE"; break;
/*     */       case 4:
/* 460 */         retval = "CDATA_SECTION_NODE"; break;
/*     */       case 8:
/* 462 */         retval = "COMMENT_NODE"; break;
/*     */       case 11:
/* 464 */         retval = "DOCUMENT_FRAGMENT_NODE"; break;
/*     */       case 9:
/* 466 */         retval = "DOCUMENT_NODE"; break;
/*     */       case 10:
/* 468 */         retval = "DOCUMENT_TYPE_NODE"; break;
/*     */       case 1:
/* 470 */         retval = "ELEMENT_NODE"; break;
/*     */       case 6:
/* 472 */         retval = "ENTITY_NODE"; break;
/*     */       case 5:
/* 474 */         retval = "ENTITY_REFERENCE_NODE"; break;
/*     */       case 12:
/* 476 */         retval = "NOTATION_NODE"; break;
/*     */       case 7:
/* 478 */         retval = "PROCESSING_INSTRUCTION_NODE"; break;
/*     */       case 3:
/* 480 */         retval = "TEXT_NODE"; break;
/*     */     } 
/* 482 */     return retval;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xalan/internal/xsltc/trax/DOM2SAX.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */