/*     */ package com.sun.org.apache.xalan.internal.xsltc.trax;
/*     */ 
/*     */ import com.sun.org.apache.xalan.internal.xsltc.compiler.util.ErrorMsg;
/*     */ import com.sun.org.apache.xalan.internal.xsltc.dom.DOMWSFilter;
/*     */ import com.sun.org.apache.xalan.internal.xsltc.dom.SAXImpl;
/*     */ import com.sun.org.apache.xalan.internal.xsltc.dom.XSLTCDTMManager;
/*     */ import com.sun.org.apache.xalan.internal.xsltc.runtime.AbstractTranslet;
/*     */ import com.sun.org.apache.xml.internal.dtm.DTMWSFilter;
/*     */ import com.sun.org.apache.xml.internal.serializer.SerializationHandler;
/*     */ import javax.xml.transform.Result;
/*     */ import javax.xml.transform.Transformer;
/*     */ import javax.xml.transform.TransformerException;
/*     */ import javax.xml.transform.dom.DOMResult;
/*     */ import javax.xml.transform.sax.TransformerHandler;
/*     */ import org.xml.sax.Attributes;
/*     */ import org.xml.sax.ContentHandler;
/*     */ import org.xml.sax.DTDHandler;
/*     */ import org.xml.sax.Locator;
/*     */ import org.xml.sax.SAXException;
/*     */ import org.xml.sax.ext.DeclHandler;
/*     */ import org.xml.sax.ext.LexicalHandler;
/*     */ import org.xml.sax.helpers.DefaultHandler;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TransformerHandlerImpl
/*     */   implements TransformerHandler, DeclHandler
/*     */ {
/*     */   private TransformerImpl _transformer;
/*  57 */   private AbstractTranslet _translet = null;
/*     */   private String _systemId;
/*  59 */   private SAXImpl _dom = null;
/*  60 */   private ContentHandler _handler = null;
/*  61 */   private LexicalHandler _lexHandler = null;
/*  62 */   private DTDHandler _dtdHandler = null;
/*  63 */   private DeclHandler _declHandler = null;
/*  64 */   private Result _result = null;
/*  65 */   private Locator _locator = null;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean _done = false;
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean _isIdentity = false;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TransformerHandlerImpl(TransformerImpl transformer) {
/*  80 */     this._transformer = transformer;
/*     */     
/*  82 */     if (transformer.isIdentity()) {
/*     */       
/*  84 */       this._handler = new DefaultHandler();
/*  85 */       this._isIdentity = true;
/*     */     }
/*     */     else {
/*     */       
/*  89 */       this._translet = this._transformer.getTranslet();
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
/*     */   public String getSystemId() {
/* 101 */     return this._systemId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSystemId(String id) {
/* 112 */     this._systemId = id;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Transformer getTransformer() {
/* 123 */     return this._transformer;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setResult(Result result) throws IllegalArgumentException {
/* 135 */     this._result = result;
/*     */     
/* 137 */     if (null == result) {
/* 138 */       ErrorMsg err = new ErrorMsg("ER_RESULT_NULL");
/* 139 */       throw new IllegalArgumentException(err.toString());
/*     */     } 
/*     */     
/* 142 */     if (this._isIdentity) {
/*     */ 
/*     */       
/*     */       try {
/* 146 */         SerializationHandler outputHandler = this._transformer.getOutputHandler(result);
/* 147 */         this._transformer.transferOutputProperties(outputHandler);
/*     */         
/* 149 */         this._handler = outputHandler;
/* 150 */         this._lexHandler = outputHandler;
/*     */       }
/* 152 */       catch (TransformerException e) {
/* 153 */         this._result = null;
/*     */       }
/*     */     
/* 156 */     } else if (this._done) {
/*     */       
/*     */       try {
/* 159 */         this._transformer.setDOM(this._dom);
/* 160 */         this._transformer.transform(null, this._result);
/*     */       }
/* 162 */       catch (TransformerException e) {
/*     */         
/* 164 */         throw new IllegalArgumentException(e.getMessage());
/*     */       } 
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
/*     */   public void characters(char[] ch, int start, int length) throws SAXException {
/* 177 */     this._handler.characters(ch, start, length);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void startDocument() throws SAXException {
/* 187 */     if (this._result == null) {
/* 188 */       ErrorMsg err = new ErrorMsg("JAXP_SET_RESULT_ERR");
/* 189 */       throw new SAXException(err.toString());
/*     */     } 
/*     */     
/* 192 */     if (!this._isIdentity) {
/* 193 */       DTMWSFilter wsFilter; boolean hasIdCall = (this._translet != null) ? this._translet.hasIdCall() : false;
/* 194 */       XSLTCDTMManager dtmManager = null;
/*     */ 
/*     */ 
/*     */       
/*     */       try {
/* 199 */         dtmManager = this._transformer.getTransformerFactory().createNewDTMManagerInstance();
/* 200 */       } catch (Exception e) {
/* 201 */         throw new SAXException(e);
/*     */       } 
/*     */ 
/*     */       
/* 205 */       if (this._translet != null && this._translet instanceof com.sun.org.apache.xalan.internal.xsltc.StripFilter) {
/* 206 */         wsFilter = new DOMWSFilter(this._translet);
/*     */       } else {
/* 208 */         wsFilter = null;
/*     */       } 
/*     */ 
/*     */       
/* 212 */       this._dom = (SAXImpl)dtmManager.getDTM(null, false, wsFilter, true, false, hasIdCall);
/*     */ 
/*     */       
/* 215 */       this._handler = this._dom.getBuilder();
/* 216 */       this._lexHandler = (LexicalHandler)this._handler;
/* 217 */       this._dtdHandler = (DTDHandler)this._handler;
/* 218 */       this._declHandler = (DeclHandler)this._handler;
/*     */ 
/*     */ 
/*     */       
/* 222 */       this._dom.setDocumentURI(this._systemId);
/*     */       
/* 224 */       if (this._locator != null) {
/* 225 */         this._handler.setDocumentLocator(this._locator);
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 230 */     this._handler.startDocument();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void endDocument() throws SAXException {
/* 240 */     this._handler.endDocument();
/*     */     
/* 242 */     if (!this._isIdentity) {
/*     */       
/* 244 */       if (this._result != null) {
/*     */         try {
/* 246 */           this._transformer.setDOM(this._dom);
/* 247 */           this._transformer.transform(null, this._result);
/*     */         }
/* 249 */         catch (TransformerException e) {
/* 250 */           throw new SAXException(e);
/*     */         } 
/*     */       }
/*     */       
/* 254 */       this._done = true;
/*     */ 
/*     */       
/* 257 */       this._transformer.setDOM(this._dom);
/*     */     } 
/* 259 */     if (this._isIdentity && this._result instanceof DOMResult) {
/* 260 */       ((DOMResult)this._result).setNode(this._transformer.getTransletOutputHandlerFactory().getNode());
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
/*     */   
/*     */   public void startElement(String uri, String localName, String qname, Attributes attributes) throws SAXException {
/* 273 */     this._handler.startElement(uri, localName, qname, attributes);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void endElement(String namespaceURI, String localName, String qname) throws SAXException {
/* 284 */     this._handler.endElement(namespaceURI, localName, qname);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void processingInstruction(String target, String data) throws SAXException {
/* 295 */     this._handler.processingInstruction(target, data);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void startCDATA() throws SAXException {
/* 303 */     if (this._lexHandler != null) {
/* 304 */       this._lexHandler.startCDATA();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void endCDATA() throws SAXException {
/* 313 */     if (this._lexHandler != null) {
/* 314 */       this._lexHandler.endCDATA();
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
/*     */   public void comment(char[] ch, int start, int length) throws SAXException {
/* 326 */     if (this._lexHandler != null) {
/* 327 */       this._lexHandler.comment(ch, start, length);
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
/*     */   
/*     */   public void ignorableWhitespace(char[] ch, int start, int length) throws SAXException {
/* 340 */     this._handler.ignorableWhitespace(ch, start, length);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDocumentLocator(Locator locator) {
/* 349 */     this._locator = locator;
/*     */     
/* 351 */     if (this._handler != null) {
/* 352 */       this._handler.setDocumentLocator(locator);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void skippedEntity(String name) throws SAXException {
/* 362 */     this._handler.skippedEntity(name);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void startPrefixMapping(String prefix, String uri) throws SAXException {
/* 372 */     this._handler.startPrefixMapping(prefix, uri);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void endPrefixMapping(String prefix) throws SAXException {
/* 381 */     this._handler.endPrefixMapping(prefix);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void startDTD(String name, String publicId, String systemId) throws SAXException {
/* 391 */     if (this._lexHandler != null) {
/* 392 */       this._lexHandler.startDTD(name, publicId, systemId);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void endDTD() throws SAXException {
/* 401 */     if (this._lexHandler != null) {
/* 402 */       this._lexHandler.endDTD();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void startEntity(String name) throws SAXException {
/* 411 */     if (this._lexHandler != null) {
/* 412 */       this._lexHandler.startEntity(name);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void endEntity(String name) throws SAXException {
/* 421 */     if (this._lexHandler != null) {
/* 422 */       this._lexHandler.endEntity(name);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void unparsedEntityDecl(String name, String publicId, String systemId, String notationName) throws SAXException {
/* 433 */     if (this._dtdHandler != null) {
/* 434 */       this._dtdHandler.unparsedEntityDecl(name, publicId, systemId, notationName);
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
/*     */   public void notationDecl(String name, String publicId, String systemId) throws SAXException {
/* 446 */     if (this._dtdHandler != null) {
/* 447 */       this._dtdHandler.notationDecl(name, publicId, systemId);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void attributeDecl(String eName, String aName, String type, String valueDefault, String value) throws SAXException {
/* 458 */     if (this._declHandler != null) {
/* 459 */       this._declHandler.attributeDecl(eName, aName, type, valueDefault, value);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void elementDecl(String name, String model) throws SAXException {
/* 470 */     if (this._declHandler != null) {
/* 471 */       this._declHandler.elementDecl(name, model);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void externalEntityDecl(String name, String publicId, String systemId) throws SAXException {
/* 482 */     if (this._declHandler != null) {
/* 483 */       this._declHandler.externalEntityDecl(name, publicId, systemId);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void internalEntityDecl(String name, String value) throws SAXException {
/* 494 */     if (this._declHandler != null) {
/* 495 */       this._declHandler.internalEntityDecl(name, value);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void reset() {
/* 504 */     this._systemId = null;
/* 505 */     this._dom = null;
/* 506 */     this._handler = null;
/* 507 */     this._lexHandler = null;
/* 508 */     this._dtdHandler = null;
/* 509 */     this._declHandler = null;
/* 510 */     this._result = null;
/* 511 */     this._locator = null;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xalan/internal/xsltc/trax/TransformerHandlerImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */