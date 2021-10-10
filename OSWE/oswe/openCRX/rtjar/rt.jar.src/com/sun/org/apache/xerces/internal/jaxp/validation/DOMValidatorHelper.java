/*     */ package com.sun.org.apache.xerces.internal.jaxp.validation;
/*     */ 
/*     */ import com.sun.org.apache.xerces.internal.impl.XMLErrorReporter;
/*     */ import com.sun.org.apache.xerces.internal.impl.validation.EntityState;
/*     */ import com.sun.org.apache.xerces.internal.impl.validation.ValidationManager;
/*     */ import com.sun.org.apache.xerces.internal.impl.xs.XMLSchemaValidator;
/*     */ import com.sun.org.apache.xerces.internal.impl.xs.util.SimpleLocator;
/*     */ import com.sun.org.apache.xerces.internal.util.NamespaceSupport;
/*     */ import com.sun.org.apache.xerces.internal.util.SymbolTable;
/*     */ import com.sun.org.apache.xerces.internal.util.XMLAttributesImpl;
/*     */ import com.sun.org.apache.xerces.internal.util.XMLSymbols;
/*     */ import com.sun.org.apache.xerces.internal.xni.NamespaceContext;
/*     */ import com.sun.org.apache.xerces.internal.xni.QName;
/*     */ import com.sun.org.apache.xerces.internal.xni.XMLString;
/*     */ import com.sun.org.apache.xerces.internal.xni.XNIException;
/*     */ import com.sun.org.apache.xerces.internal.xni.parser.XMLParseException;
/*     */ import java.io.IOException;
/*     */ import java.util.Enumeration;
/*     */ import javax.xml.parsers.DocumentBuilder;
/*     */ import javax.xml.parsers.DocumentBuilderFactory;
/*     */ import javax.xml.parsers.ParserConfigurationException;
/*     */ import javax.xml.transform.Result;
/*     */ import javax.xml.transform.Source;
/*     */ import javax.xml.transform.dom.DOMResult;
/*     */ import javax.xml.transform.dom.DOMSource;
/*     */ import jdk.xml.internal.JdkXmlUtils;
/*     */ import org.w3c.dom.Attr;
/*     */ import org.w3c.dom.CDATASection;
/*     */ import org.w3c.dom.Comment;
/*     */ import org.w3c.dom.Document;
/*     */ import org.w3c.dom.DocumentType;
/*     */ import org.w3c.dom.Entity;
/*     */ import org.w3c.dom.NamedNodeMap;
/*     */ import org.w3c.dom.Node;
/*     */ import org.w3c.dom.ProcessingInstruction;
/*     */ import org.w3c.dom.Text;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class DOMValidatorHelper
/*     */   implements ValidatorHelper, EntityState
/*     */ {
/*     */   private static final int CHUNK_SIZE = 1024;
/*     */   private static final int CHUNK_MASK = 1023;
/*     */   private static final String ERROR_REPORTER = "http://apache.org/xml/properties/internal/error-reporter";
/*     */   private static final String NAMESPACE_CONTEXT = "http://apache.org/xml/properties/internal/namespace-context";
/*     */   private static final String SCHEMA_VALIDATOR = "http://apache.org/xml/properties/internal/validator/schema";
/*     */   private static final String SYMBOL_TABLE = "http://apache.org/xml/properties/internal/symbol-table";
/*     */   private static final String VALIDATION_MANAGER = "http://apache.org/xml/properties/internal/validation-manager";
/*     */   private XMLErrorReporter fErrorReporter;
/*     */   private NamespaceSupport fNamespaceContext;
/* 113 */   private DOMNamespaceContext fDOMNamespaceContext = new DOMNamespaceContext();
/*     */ 
/*     */   
/*     */   private XMLSchemaValidator fSchemaValidator;
/*     */ 
/*     */   
/*     */   private SymbolTable fSymbolTable;
/*     */ 
/*     */   
/*     */   private ValidationManager fValidationManager;
/*     */ 
/*     */   
/*     */   private XMLSchemaValidatorComponentManager fComponentManager;
/*     */ 
/*     */   
/* 128 */   private final SimpleLocator fXMLLocator = new SimpleLocator(null, null, -1, -1, -1);
/*     */ 
/*     */   
/*     */   private DOMDocumentHandler fDOMValidatorHandler;
/*     */ 
/*     */   
/* 134 */   private final DOMResultAugmentor fDOMResultAugmentor = new DOMResultAugmentor(this);
/*     */ 
/*     */   
/* 137 */   private final DOMResultBuilder fDOMResultBuilder = new DOMResultBuilder();
/*     */ 
/*     */   
/* 140 */   private NamedNodeMap fEntities = null;
/*     */ 
/*     */   
/* 143 */   private char[] fCharBuffer = new char[1024];
/*     */ 
/*     */   
/*     */   private Node fRoot;
/*     */ 
/*     */   
/*     */   private Node fCurrentElement;
/*     */ 
/*     */   
/* 152 */   final QName fElementQName = new QName();
/* 153 */   final QName fAttributeQName = new QName();
/* 154 */   final XMLAttributesImpl fAttributes = new XMLAttributesImpl();
/* 155 */   final XMLString fTempString = new XMLString();
/*     */   
/*     */   public DOMValidatorHelper(XMLSchemaValidatorComponentManager componentManager) {
/* 158 */     this.fComponentManager = componentManager;
/* 159 */     this.fErrorReporter = (XMLErrorReporter)this.fComponentManager.getProperty("http://apache.org/xml/properties/internal/error-reporter");
/* 160 */     this.fNamespaceContext = (NamespaceSupport)this.fComponentManager.getProperty("http://apache.org/xml/properties/internal/namespace-context");
/* 161 */     this.fSchemaValidator = (XMLSchemaValidator)this.fComponentManager.getProperty("http://apache.org/xml/properties/internal/validator/schema");
/* 162 */     this.fSymbolTable = (SymbolTable)this.fComponentManager.getProperty("http://apache.org/xml/properties/internal/symbol-table");
/* 163 */     this.fValidationManager = (ValidationManager)this.fComponentManager.getProperty("http://apache.org/xml/properties/internal/validation-manager");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void validate(Source source, Result result) throws SAXException, IOException {
/* 172 */     if (result instanceof DOMResult || result == null) {
/* 173 */       DOMSource domSource = (DOMSource)source;
/* 174 */       DOMResult domResult = (DOMResult)result;
/* 175 */       Node node = domSource.getNode();
/* 176 */       this.fRoot = node;
/* 177 */       if (node != null) {
/* 178 */         this.fComponentManager.reset();
/* 179 */         this.fValidationManager.setEntityState(this);
/* 180 */         this.fDOMNamespaceContext.reset();
/* 181 */         String systemId = domSource.getSystemId();
/* 182 */         this.fXMLLocator.setLiteralSystemId(systemId);
/* 183 */         this.fXMLLocator.setExpandedSystemId(systemId);
/* 184 */         this.fErrorReporter.setDocumentLocator(this.fXMLLocator);
/*     */         
/*     */         try {
/* 187 */           setupEntityMap((node.getNodeType() == 9) ? (Document)node : node.getOwnerDocument());
/* 188 */           setupDOMResultHandler(domSource, domResult);
/* 189 */           this.fSchemaValidator.startDocument(this.fXMLLocator, null, this.fDOMNamespaceContext, null);
/* 190 */           validate(node);
/* 191 */           this.fSchemaValidator.endDocument(null);
/*     */         }
/* 193 */         catch (XMLParseException e) {
/* 194 */           throw Util.toSAXParseException(e);
/*     */         }
/* 196 */         catch (XNIException e) {
/* 197 */           throw Util.toSAXException(e);
/*     */         }
/*     */         finally {
/*     */           
/* 201 */           this.fRoot = null;
/*     */           
/* 203 */           this.fEntities = null;
/* 204 */           if (this.fDOMValidatorHandler != null) {
/* 205 */             this.fDOMValidatorHandler.setDOMResult(null);
/*     */           }
/*     */         } 
/*     */       } 
/*     */       return;
/*     */     } 
/* 211 */     throw new IllegalArgumentException(JAXPValidationMessageFormatter.formatMessage(this.fComponentManager.getLocale(), "SourceResultMismatch", new Object[] { source
/*     */             
/* 213 */             .getClass().getName(), result.getClass().getName() }));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isEntityDeclared(String name) {
/* 221 */     return false;
/*     */   }
/*     */   
/*     */   public boolean isEntityUnparsed(String name) {
/* 225 */     if (this.fEntities != null) {
/* 226 */       Entity entity = (Entity)this.fEntities.getNamedItem(name);
/* 227 */       if (entity != null) {
/* 228 */         return (entity.getNotationName() != null);
/*     */       }
/*     */     } 
/* 231 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void validate(Node node) {
/* 240 */     Node top = node;
/*     */ 
/*     */     
/* 243 */     while (node != null) {
/* 244 */       beginNode(node);
/* 245 */       Node next = node.getFirstChild();
/* 246 */       while (next == null) {
/* 247 */         finishNode(node);
/* 248 */         if (top == node) {
/*     */           break;
/*     */         }
/* 251 */         next = node.getNextSibling();
/* 252 */         if (next == null) {
/* 253 */           node = node.getParentNode();
/* 254 */           if (node == null || top == node) {
/* 255 */             if (node != null) {
/* 256 */               finishNode(node);
/*     */             }
/* 258 */             next = null;
/*     */             break;
/*     */           } 
/*     */         } 
/*     */       } 
/* 263 */       node = next;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void beginNode(Node node) {
/* 269 */     switch (node.getNodeType()) {
/*     */       case 1:
/* 271 */         this.fCurrentElement = node;
/*     */         
/* 273 */         this.fNamespaceContext.pushContext();
/*     */         
/* 275 */         fillQName(this.fElementQName, node);
/* 276 */         processAttributes(node.getAttributes());
/* 277 */         this.fSchemaValidator.startElement(this.fElementQName, this.fAttributes, null);
/*     */         break;
/*     */       case 3:
/* 280 */         if (this.fDOMValidatorHandler != null) {
/* 281 */           this.fDOMValidatorHandler.setIgnoringCharacters(true);
/* 282 */           sendCharactersToValidator(node.getNodeValue());
/* 283 */           this.fDOMValidatorHandler.setIgnoringCharacters(false);
/* 284 */           this.fDOMValidatorHandler.characters((Text)node);
/*     */           break;
/*     */         } 
/* 287 */         sendCharactersToValidator(node.getNodeValue());
/*     */         break;
/*     */       
/*     */       case 4:
/* 291 */         if (this.fDOMValidatorHandler != null) {
/* 292 */           this.fDOMValidatorHandler.setIgnoringCharacters(true);
/* 293 */           this.fSchemaValidator.startCDATA(null);
/* 294 */           sendCharactersToValidator(node.getNodeValue());
/* 295 */           this.fSchemaValidator.endCDATA(null);
/* 296 */           this.fDOMValidatorHandler.setIgnoringCharacters(false);
/* 297 */           this.fDOMValidatorHandler.cdata((CDATASection)node);
/*     */           break;
/*     */         } 
/* 300 */         this.fSchemaValidator.startCDATA(null);
/* 301 */         sendCharactersToValidator(node.getNodeValue());
/* 302 */         this.fSchemaValidator.endCDATA(null);
/*     */         break;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       case 7:
/* 310 */         if (this.fDOMValidatorHandler != null) {
/* 311 */           this.fDOMValidatorHandler.processingInstruction((ProcessingInstruction)node);
/*     */         }
/*     */         break;
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       case 8:
/* 319 */         if (this.fDOMValidatorHandler != null) {
/* 320 */           this.fDOMValidatorHandler.comment((Comment)node);
/*     */         }
/*     */         break;
/*     */ 
/*     */ 
/*     */       
/*     */       case 10:
/* 327 */         if (this.fDOMValidatorHandler != null) {
/* 328 */           this.fDOMValidatorHandler.doctypeDecl((DocumentType)node);
/*     */         }
/*     */         break;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void finishNode(Node node) {
/* 338 */     if (node.getNodeType() == 1) {
/* 339 */       this.fCurrentElement = node;
/*     */       
/* 341 */       fillQName(this.fElementQName, node);
/* 342 */       this.fSchemaValidator.endElement(this.fElementQName, null);
/*     */       
/* 344 */       this.fNamespaceContext.popContext();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void setupEntityMap(Document doc) {
/* 354 */     if (doc != null) {
/* 355 */       DocumentType docType = doc.getDoctype();
/* 356 */       if (docType != null) {
/* 357 */         this.fEntities = docType.getEntities();
/*     */         return;
/*     */       } 
/*     */     } 
/* 361 */     this.fEntities = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void setupDOMResultHandler(DOMSource source, DOMResult result) throws SAXException {
/* 369 */     if (result == null) {
/* 370 */       this.fDOMValidatorHandler = null;
/* 371 */       this.fSchemaValidator.setDocumentHandler(null);
/*     */       return;
/*     */     } 
/* 374 */     Node nodeResult = result.getNode();
/*     */ 
/*     */     
/* 377 */     if (source.getNode() == nodeResult) {
/* 378 */       this.fDOMValidatorHandler = this.fDOMResultAugmentor;
/* 379 */       this.fDOMResultAugmentor.setDOMResult(result);
/* 380 */       this.fSchemaValidator.setDocumentHandler(this.fDOMResultAugmentor);
/*     */       return;
/*     */     } 
/* 383 */     if (result.getNode() == null) {
/*     */       try {
/* 385 */         DocumentBuilderFactory factory = JdkXmlUtils.getDOMFactory(this.fComponentManager
/* 386 */             .getFeature("jdk.xml.overrideDefaultParser"));
/* 387 */         DocumentBuilder builder = factory.newDocumentBuilder();
/* 388 */         result.setNode(builder.newDocument());
/*     */       }
/* 390 */       catch (ParserConfigurationException e) {
/* 391 */         throw new SAXException(e);
/*     */       } 
/*     */     }
/* 394 */     this.fDOMValidatorHandler = this.fDOMResultBuilder;
/* 395 */     this.fDOMResultBuilder.setDOMResult(result);
/* 396 */     this.fSchemaValidator.setDocumentHandler(this.fDOMResultBuilder);
/*     */   }
/*     */   
/*     */   private void fillQName(QName toFill, Node node) {
/* 400 */     String prefix = node.getPrefix();
/* 401 */     String localName = node.getLocalName();
/* 402 */     String rawName = node.getNodeName();
/* 403 */     String namespace = node.getNamespaceURI();
/*     */     
/* 405 */     toFill.uri = (namespace != null && namespace.length() > 0) ? this.fSymbolTable.addSymbol(namespace) : null;
/* 406 */     toFill.rawname = (rawName != null) ? this.fSymbolTable.addSymbol(rawName) : XMLSymbols.EMPTY_STRING;
/*     */ 
/*     */     
/* 409 */     if (localName == null) {
/* 410 */       int k = rawName.indexOf(':');
/* 411 */       if (k > 0) {
/* 412 */         toFill.prefix = this.fSymbolTable.addSymbol(rawName.substring(0, k));
/* 413 */         toFill.localpart = this.fSymbolTable.addSymbol(rawName.substring(k + 1));
/*     */       } else {
/*     */         
/* 416 */         toFill.prefix = XMLSymbols.EMPTY_STRING;
/* 417 */         toFill.localpart = toFill.rawname;
/*     */       } 
/*     */     } else {
/*     */       
/* 421 */       toFill.prefix = (prefix != null) ? this.fSymbolTable.addSymbol(prefix) : XMLSymbols.EMPTY_STRING;
/* 422 */       toFill.localpart = (localName != null) ? this.fSymbolTable.addSymbol(localName) : XMLSymbols.EMPTY_STRING;
/*     */     } 
/*     */   }
/*     */   
/*     */   private void processAttributes(NamedNodeMap attrMap) {
/* 427 */     int attrCount = attrMap.getLength();
/* 428 */     this.fAttributes.removeAllAttributes();
/* 429 */     for (int i = 0; i < attrCount; i++) {
/* 430 */       Attr attr = (Attr)attrMap.item(i);
/* 431 */       String value = attr.getValue();
/* 432 */       if (value == null) {
/* 433 */         value = XMLSymbols.EMPTY_STRING;
/*     */       }
/* 435 */       fillQName(this.fAttributeQName, attr);
/*     */       
/* 437 */       this.fAttributes.addAttributeNS(this.fAttributeQName, XMLSymbols.fCDATASymbol, value);
/* 438 */       this.fAttributes.setSpecified(i, attr.getSpecified());
/*     */ 
/*     */ 
/*     */       
/* 442 */       if (this.fAttributeQName.uri == NamespaceContext.XMLNS_URI)
/*     */       {
/* 444 */         if (this.fAttributeQName.prefix == XMLSymbols.PREFIX_XMLNS) {
/* 445 */           this.fNamespaceContext.declarePrefix(this.fAttributeQName.localpart, (value.length() != 0) ? this.fSymbolTable.addSymbol(value) : null);
/*     */         } else {
/*     */           
/* 448 */           this.fNamespaceContext.declarePrefix(XMLSymbols.EMPTY_STRING, (value.length() != 0) ? this.fSymbolTable.addSymbol(value) : null);
/*     */         } 
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
/*     */   private void sendCharactersToValidator(String str) {
/*     */     // Byte code:
/*     */     //   0: aload_1
/*     */     //   1: ifnull -> 113
/*     */     //   4: aload_1
/*     */     //   5: invokevirtual length : ()I
/*     */     //   8: istore_2
/*     */     //   9: iload_2
/*     */     //   10: sipush #1023
/*     */     //   13: iand
/*     */     //   14: istore_3
/*     */     //   15: iload_3
/*     */     //   16: ifle -> 55
/*     */     //   19: aload_1
/*     */     //   20: iconst_0
/*     */     //   21: iload_3
/*     */     //   22: aload_0
/*     */     //   23: getfield fCharBuffer : [C
/*     */     //   26: iconst_0
/*     */     //   27: invokevirtual getChars : (II[CI)V
/*     */     //   30: aload_0
/*     */     //   31: getfield fTempString : Lcom/sun/org/apache/xerces/internal/xni/XMLString;
/*     */     //   34: aload_0
/*     */     //   35: getfield fCharBuffer : [C
/*     */     //   38: iconst_0
/*     */     //   39: iload_3
/*     */     //   40: invokevirtual setValues : ([CII)V
/*     */     //   43: aload_0
/*     */     //   44: getfield fSchemaValidator : Lcom/sun/org/apache/xerces/internal/impl/xs/XMLSchemaValidator;
/*     */     //   47: aload_0
/*     */     //   48: getfield fTempString : Lcom/sun/org/apache/xerces/internal/xni/XMLString;
/*     */     //   51: aconst_null
/*     */     //   52: invokevirtual characters : (Lcom/sun/org/apache/xerces/internal/xni/XMLString;Lcom/sun/org/apache/xerces/internal/xni/Augmentations;)V
/*     */     //   55: iload_3
/*     */     //   56: istore #4
/*     */     //   58: iload #4
/*     */     //   60: iload_2
/*     */     //   61: if_icmpge -> 113
/*     */     //   64: aload_1
/*     */     //   65: iload #4
/*     */     //   67: wide iinc #4 1024
/*     */     //   73: iload #4
/*     */     //   75: aload_0
/*     */     //   76: getfield fCharBuffer : [C
/*     */     //   79: iconst_0
/*     */     //   80: invokevirtual getChars : (II[CI)V
/*     */     //   83: aload_0
/*     */     //   84: getfield fTempString : Lcom/sun/org/apache/xerces/internal/xni/XMLString;
/*     */     //   87: aload_0
/*     */     //   88: getfield fCharBuffer : [C
/*     */     //   91: iconst_0
/*     */     //   92: sipush #1024
/*     */     //   95: invokevirtual setValues : ([CII)V
/*     */     //   98: aload_0
/*     */     //   99: getfield fSchemaValidator : Lcom/sun/org/apache/xerces/internal/impl/xs/XMLSchemaValidator;
/*     */     //   102: aload_0
/*     */     //   103: getfield fTempString : Lcom/sun/org/apache/xerces/internal/xni/XMLString;
/*     */     //   106: aconst_null
/*     */     //   107: invokevirtual characters : (Lcom/sun/org/apache/xerces/internal/xni/XMLString;Lcom/sun/org/apache/xerces/internal/xni/Augmentations;)V
/*     */     //   110: goto -> 58
/*     */     //   113: return
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #455	-> 0
/*     */     //   #456	-> 4
/*     */     //   #457	-> 9
/*     */     //   #458	-> 15
/*     */     //   #459	-> 19
/*     */     //   #460	-> 30
/*     */     //   #461	-> 43
/*     */     //   #463	-> 55
/*     */     //   #464	-> 58
/*     */     //   #465	-> 64
/*     */     //   #466	-> 83
/*     */     //   #467	-> 98
/*     */     //   #470	-> 113
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   9	104	2	length	I
/*     */     //   15	98	3	remainder	I
/*     */     //   58	55	4	i	I
/*     */     //   0	114	0	this	Lcom/sun/org/apache/xerces/internal/jaxp/validation/DOMValidatorHelper;
/*     */     //   0	114	1	str	Ljava/lang/String;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   Node getCurrentElement() {
/* 473 */     return this.fCurrentElement;
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
/*     */ 
/*     */ 
/*     */   
/*     */   final class DOMNamespaceContext
/*     */     implements NamespaceContext
/*     */   {
/* 490 */     protected String[] fNamespace = new String[32];
/*     */ 
/*     */     
/* 493 */     protected int fNamespaceSize = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected boolean fDOMContextBuilt = false;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void pushContext() {
/* 506 */       DOMValidatorHelper.this.fNamespaceContext.pushContext();
/*     */     }
/*     */     
/*     */     public void popContext() {
/* 510 */       DOMValidatorHelper.this.fNamespaceContext.popContext();
/*     */     }
/*     */     
/*     */     public boolean declarePrefix(String prefix, String uri) {
/* 514 */       return DOMValidatorHelper.this.fNamespaceContext.declarePrefix(prefix, uri);
/*     */     }
/*     */     
/*     */     public String getURI(String prefix) {
/* 518 */       String uri = DOMValidatorHelper.this.fNamespaceContext.getURI(prefix);
/* 519 */       if (uri == null) {
/* 520 */         if (!this.fDOMContextBuilt) {
/* 521 */           fillNamespaceContext();
/* 522 */           this.fDOMContextBuilt = true;
/*     */         } 
/* 524 */         if (this.fNamespaceSize > 0 && 
/* 525 */           !DOMValidatorHelper.this.fNamespaceContext.containsPrefix(prefix)) {
/* 526 */           uri = getURI0(prefix);
/*     */         }
/*     */       } 
/* 529 */       return uri;
/*     */     }
/*     */     
/*     */     public String getPrefix(String uri) {
/* 533 */       return DOMValidatorHelper.this.fNamespaceContext.getPrefix(uri);
/*     */     }
/*     */     
/*     */     public int getDeclaredPrefixCount() {
/* 537 */       return DOMValidatorHelper.this.fNamespaceContext.getDeclaredPrefixCount();
/*     */     }
/*     */     
/*     */     public String getDeclaredPrefixAt(int index) {
/* 541 */       return DOMValidatorHelper.this.fNamespaceContext.getDeclaredPrefixAt(index);
/*     */     }
/*     */     
/*     */     public Enumeration getAllPrefixes() {
/* 545 */       return DOMValidatorHelper.this.fNamespaceContext.getAllPrefixes();
/*     */     }
/*     */     
/*     */     public void reset() {
/* 549 */       this.fDOMContextBuilt = false;
/* 550 */       this.fNamespaceSize = 0;
/*     */     }
/*     */     
/*     */     private void fillNamespaceContext() {
/* 554 */       if (DOMValidatorHelper.this.fRoot != null) {
/* 555 */         Node currentNode = DOMValidatorHelper.this.fRoot.getParentNode();
/* 556 */         while (currentNode != null) {
/* 557 */           if (1 == currentNode.getNodeType()) {
/* 558 */             NamedNodeMap attributes = currentNode.getAttributes();
/* 559 */             int attrCount = attributes.getLength();
/* 560 */             for (int i = 0; i < attrCount; i++) {
/* 561 */               Attr attr = (Attr)attributes.item(i);
/* 562 */               String value = attr.getValue();
/* 563 */               if (value == null) {
/* 564 */                 value = XMLSymbols.EMPTY_STRING;
/*     */               }
/* 566 */               DOMValidatorHelper.this.fillQName(DOMValidatorHelper.this.fAttributeQName, attr);
/*     */ 
/*     */ 
/*     */               
/* 570 */               if (DOMValidatorHelper.this.fAttributeQName.uri == NamespaceContext.XMLNS_URI)
/*     */               {
/* 572 */                 if (DOMValidatorHelper.this.fAttributeQName.prefix == XMLSymbols.PREFIX_XMLNS) {
/* 573 */                   declarePrefix0(DOMValidatorHelper.this.fAttributeQName.localpart, (value.length() != 0) ? DOMValidatorHelper.this.fSymbolTable.addSymbol(value) : null);
/*     */                 } else {
/*     */                   
/* 576 */                   declarePrefix0(XMLSymbols.EMPTY_STRING, (value.length() != 0) ? DOMValidatorHelper.this.fSymbolTable.addSymbol(value) : null);
/*     */                 } 
/*     */               }
/*     */             } 
/*     */           } 
/*     */           
/* 582 */           currentNode = currentNode.getParentNode();
/*     */         } 
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     private void declarePrefix0(String prefix, String uri) {
/* 589 */       if (this.fNamespaceSize == this.fNamespace.length) {
/* 590 */         String[] namespacearray = new String[this.fNamespaceSize * 2];
/* 591 */         System.arraycopy(this.fNamespace, 0, namespacearray, 0, this.fNamespaceSize);
/* 592 */         this.fNamespace = namespacearray;
/*     */       } 
/*     */ 
/*     */       
/* 596 */       this.fNamespace[this.fNamespaceSize++] = prefix;
/* 597 */       this.fNamespace[this.fNamespaceSize++] = uri;
/*     */     }
/*     */ 
/*     */     
/*     */     private String getURI0(String prefix) {
/* 602 */       for (int i = 0; i < this.fNamespaceSize; i += 2) {
/* 603 */         if (this.fNamespace[i] == prefix) {
/* 604 */           return this.fNamespace[i + 1];
/*     */         }
/*     */       } 
/*     */       
/* 608 */       return null;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xerces/internal/jaxp/validation/DOMValidatorHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */