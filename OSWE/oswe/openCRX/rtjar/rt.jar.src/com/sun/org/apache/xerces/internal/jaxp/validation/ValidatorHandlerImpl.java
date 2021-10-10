/*      */ package com.sun.org.apache.xerces.internal.jaxp.validation;
/*      */ 
/*      */ import com.sun.org.apache.xerces.internal.impl.XMLEntityManager;
/*      */ import com.sun.org.apache.xerces.internal.impl.XMLErrorReporter;
/*      */ import com.sun.org.apache.xerces.internal.impl.dv.XSSimpleType;
/*      */ import com.sun.org.apache.xerces.internal.impl.validation.EntityState;
/*      */ import com.sun.org.apache.xerces.internal.impl.validation.ValidationManager;
/*      */ import com.sun.org.apache.xerces.internal.impl.xs.XMLSchemaValidator;
/*      */ import com.sun.org.apache.xerces.internal.util.AttributesProxy;
/*      */ import com.sun.org.apache.xerces.internal.util.SAXLocatorWrapper;
/*      */ import com.sun.org.apache.xerces.internal.util.SAXMessageFormatter;
/*      */ import com.sun.org.apache.xerces.internal.util.Status;
/*      */ import com.sun.org.apache.xerces.internal.util.SymbolTable;
/*      */ import com.sun.org.apache.xerces.internal.util.URI;
/*      */ import com.sun.org.apache.xerces.internal.util.XMLAttributesImpl;
/*      */ import com.sun.org.apache.xerces.internal.util.XMLSymbols;
/*      */ import com.sun.org.apache.xerces.internal.utils.XMLSecurityManager;
/*      */ import com.sun.org.apache.xerces.internal.utils.XMLSecurityPropertyManager;
/*      */ import com.sun.org.apache.xerces.internal.xni.Augmentations;
/*      */ import com.sun.org.apache.xerces.internal.xni.NamespaceContext;
/*      */ import com.sun.org.apache.xerces.internal.xni.QName;
/*      */ import com.sun.org.apache.xerces.internal.xni.XMLAttributes;
/*      */ import com.sun.org.apache.xerces.internal.xni.XMLDocumentHandler;
/*      */ import com.sun.org.apache.xerces.internal.xni.XMLLocator;
/*      */ import com.sun.org.apache.xerces.internal.xni.XMLResourceIdentifier;
/*      */ import com.sun.org.apache.xerces.internal.xni.XMLString;
/*      */ import com.sun.org.apache.xerces.internal.xni.XNIException;
/*      */ import com.sun.org.apache.xerces.internal.xni.parser.XMLConfigurationException;
/*      */ import com.sun.org.apache.xerces.internal.xni.parser.XMLDocumentSource;
/*      */ import com.sun.org.apache.xerces.internal.xni.parser.XMLParseException;
/*      */ import com.sun.org.apache.xerces.internal.xs.AttributePSVI;
/*      */ import com.sun.org.apache.xerces.internal.xs.ElementPSVI;
/*      */ import com.sun.org.apache.xerces.internal.xs.ItemPSVI;
/*      */ import com.sun.org.apache.xerces.internal.xs.PSVIProvider;
/*      */ import com.sun.org.apache.xerces.internal.xs.XSTypeDefinition;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.io.Reader;
/*      */ import java.io.StringReader;
/*      */ import java.util.HashMap;
/*      */ import javax.xml.parsers.FactoryConfigurationError;
/*      */ import javax.xml.transform.Result;
/*      */ import javax.xml.transform.Source;
/*      */ import javax.xml.transform.sax.SAXResult;
/*      */ import javax.xml.transform.sax.SAXSource;
/*      */ import javax.xml.validation.TypeInfoProvider;
/*      */ import javax.xml.validation.ValidatorHandler;
/*      */ import jdk.xml.internal.JdkXmlUtils;
/*      */ import org.w3c.dom.TypeInfo;
/*      */ import org.w3c.dom.ls.LSInput;
/*      */ import org.w3c.dom.ls.LSResourceResolver;
/*      */ import org.xml.sax.Attributes;
/*      */ import org.xml.sax.ContentHandler;
/*      */ import org.xml.sax.DTDHandler;
/*      */ import org.xml.sax.ErrorHandler;
/*      */ import org.xml.sax.InputSource;
/*      */ import org.xml.sax.Locator;
/*      */ import org.xml.sax.SAXException;
/*      */ import org.xml.sax.SAXNotRecognizedException;
/*      */ import org.xml.sax.SAXNotSupportedException;
/*      */ import org.xml.sax.XMLReader;
/*      */ import org.xml.sax.ext.Attributes2;
/*      */ import org.xml.sax.ext.EntityResolver2;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ final class ValidatorHandlerImpl
/*      */   extends ValidatorHandler
/*      */   implements DTDHandler, EntityState, PSVIProvider, ValidatorHelper, XMLDocumentHandler
/*      */ {
/*      */   private static final String NAMESPACE_PREFIXES = "http://xml.org/sax/features/namespace-prefixes";
/*      */   protected static final String STRING_INTERNING = "http://xml.org/sax/features/string-interning";
/*      */   private static final String ERROR_REPORTER = "http://apache.org/xml/properties/internal/error-reporter";
/*      */   private static final String NAMESPACE_CONTEXT = "http://apache.org/xml/properties/internal/namespace-context";
/*      */   private static final String SCHEMA_VALIDATOR = "http://apache.org/xml/properties/internal/validator/schema";
/*      */   private static final String SECURITY_MANAGER = "http://apache.org/xml/properties/security-manager";
/*      */   private static final String SYMBOL_TABLE = "http://apache.org/xml/properties/internal/symbol-table";
/*      */   private static final String VALIDATION_MANAGER = "http://apache.org/xml/properties/internal/validation-manager";
/*      */   private static final String XML_SECURITY_PROPERTY_MANAGER = "http://www.oracle.com/xml/jaxp/properties/xmlSecurityPropertyManager";
/*      */   private XMLErrorReporter fErrorReporter;
/*      */   private NamespaceContext fNamespaceContext;
/*      */   private XMLSchemaValidator fSchemaValidator;
/*      */   private SymbolTable fSymbolTable;
/*      */   private ValidationManager fValidationManager;
/*      */   private XMLSchemaValidatorComponentManager fComponentManager;
/*  163 */   private final SAXLocatorWrapper fSAXLocatorWrapper = new SAXLocatorWrapper();
/*      */ 
/*      */   
/*      */   private boolean fNeedPushNSContext = true;
/*      */ 
/*      */   
/*  169 */   private HashMap fUnparsedEntities = null;
/*      */ 
/*      */   
/*      */   private boolean fStringsInternalized = false;
/*      */ 
/*      */   
/*  175 */   private final QName fElementQName = new QName();
/*  176 */   private final QName fAttributeQName = new QName();
/*  177 */   private final XMLAttributesImpl fAttributes = new XMLAttributesImpl();
/*  178 */   private final AttributesProxy fAttrAdapter = new AttributesProxy(this.fAttributes);
/*  179 */   private final XMLString fTempString = new XMLString();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  185 */   private ContentHandler fContentHandler = null;
/*      */   
/*      */   private final XMLSchemaTypeInfoProvider fTypeInfoProvider;
/*      */   
/*      */   private final ResolutionForwarder fResolutionForwarder;
/*      */   
/*      */   public ValidatorHandlerImpl(XSGrammarPoolContainer grammarContainer) {
/*  192 */     this(new XMLSchemaValidatorComponentManager(grammarContainer));
/*  193 */     this.fComponentManager.addRecognizedFeatures(new String[] { "http://xml.org/sax/features/namespace-prefixes" });
/*  194 */     this.fComponentManager.setFeature("http://xml.org/sax/features/namespace-prefixes", false);
/*  195 */     setErrorHandler(null);
/*  196 */     setResourceResolver(null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setContentHandler(ContentHandler receiver) {
/*  213 */     this.fContentHandler = receiver;
/*      */   }
/*      */   
/*      */   public ContentHandler getContentHandler() {
/*  217 */     return this.fContentHandler;
/*      */   }
/*      */   
/*      */   public void setErrorHandler(ErrorHandler errorHandler) {
/*  221 */     this.fComponentManager.setErrorHandler(errorHandler);
/*      */   }
/*      */   
/*      */   public ErrorHandler getErrorHandler() {
/*  225 */     return this.fComponentManager.getErrorHandler();
/*      */   }
/*      */   
/*      */   public void setResourceResolver(LSResourceResolver resourceResolver) {
/*  229 */     this.fComponentManager.setResourceResolver(resourceResolver);
/*      */   }
/*      */   
/*      */   public LSResourceResolver getResourceResolver() {
/*  233 */     return this.fComponentManager.getResourceResolver();
/*      */   }
/*      */   
/*      */   public TypeInfoProvider getTypeInfoProvider() {
/*  237 */     return this.fTypeInfoProvider;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean getFeature(String name) throws SAXNotRecognizedException, SAXNotSupportedException {
/*  242 */     if (name == null) {
/*  243 */       throw new NullPointerException();
/*      */     }
/*      */     try {
/*  246 */       return this.fComponentManager.getFeature(name);
/*      */     }
/*  248 */     catch (XMLConfigurationException e) {
/*  249 */       String identifier = e.getIdentifier();
/*  250 */       String key = (e.getType() == Status.NOT_RECOGNIZED) ? "feature-not-recognized" : "feature-not-supported";
/*      */       
/*  252 */       throw new SAXNotRecognizedException(
/*  253 */           SAXMessageFormatter.formatMessage(this.fComponentManager.getLocale(), key, new Object[] { identifier }));
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void setFeature(String name, boolean value) throws SAXNotRecognizedException, SAXNotSupportedException {
/*  260 */     if (name == null) {
/*  261 */       throw new NullPointerException();
/*      */     }
/*      */     try {
/*  264 */       this.fComponentManager.setFeature(name, value);
/*      */     }
/*  266 */     catch (XMLConfigurationException e) {
/*  267 */       String key, identifier = e.getIdentifier();
/*      */       
/*  269 */       if (e.getType() == Status.NOT_ALLOWED)
/*      */       {
/*  271 */         throw new SAXNotSupportedException(
/*  272 */             SAXMessageFormatter.formatMessage(this.fComponentManager.getLocale(), "jaxp-secureprocessing-feature", null));
/*      */       }
/*  274 */       if (e.getType() == Status.NOT_RECOGNIZED) {
/*  275 */         key = "feature-not-recognized";
/*      */       } else {
/*  277 */         key = "feature-not-supported";
/*      */       } 
/*  279 */       throw new SAXNotRecognizedException(
/*  280 */           SAXMessageFormatter.formatMessage(this.fComponentManager.getLocale(), key, new Object[] { identifier }));
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Object getProperty(String name) throws SAXNotRecognizedException, SAXNotSupportedException {
/*  287 */     if (name == null) {
/*  288 */       throw new NullPointerException();
/*      */     }
/*      */     try {
/*  291 */       return this.fComponentManager.getProperty(name);
/*      */     }
/*  293 */     catch (XMLConfigurationException e) {
/*  294 */       String identifier = e.getIdentifier();
/*  295 */       String key = (e.getType() == Status.NOT_RECOGNIZED) ? "property-not-recognized" : "property-not-supported";
/*      */       
/*  297 */       throw new SAXNotRecognizedException(
/*  298 */           SAXMessageFormatter.formatMessage(this.fComponentManager.getLocale(), key, new Object[] { identifier }));
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void setProperty(String name, Object object) throws SAXNotRecognizedException, SAXNotSupportedException {
/*  305 */     if (name == null) {
/*  306 */       throw new NullPointerException();
/*      */     }
/*      */     try {
/*  309 */       this.fComponentManager.setProperty(name, object);
/*      */     }
/*  311 */     catch (XMLConfigurationException e) {
/*  312 */       String identifier = e.getIdentifier();
/*  313 */       String key = (e.getType() == Status.NOT_RECOGNIZED) ? "property-not-recognized" : "property-not-supported";
/*      */       
/*  315 */       throw new SAXNotRecognizedException(
/*  316 */           SAXMessageFormatter.formatMessage(this.fComponentManager.getLocale(), key, new Object[] { identifier }));
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isEntityDeclared(String name) {
/*  326 */     return false;
/*      */   }
/*      */   
/*      */   public boolean isEntityUnparsed(String name) {
/*  330 */     if (this.fUnparsedEntities != null) {
/*  331 */       return this.fUnparsedEntities.containsKey(name);
/*      */     }
/*  333 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void startDocument(XMLLocator locator, String encoding, NamespaceContext namespaceContext, Augmentations augs) throws XNIException {
/*  343 */     if (this.fContentHandler != null) {
/*      */       try {
/*  345 */         this.fContentHandler.startDocument();
/*      */       }
/*  347 */       catch (SAXException e) {
/*  348 */         throw new XNIException(e);
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public void xmlDecl(String version, String encoding, String standalone, Augmentations augs) throws XNIException {}
/*      */ 
/*      */   
/*      */   public void doctypeDecl(String rootElement, String publicId, String systemId, Augmentations augs) throws XNIException {}
/*      */ 
/*      */   
/*      */   public void comment(XMLString text, Augmentations augs) throws XNIException {}
/*      */   
/*      */   public void processingInstruction(String target, XMLString data, Augmentations augs) throws XNIException {
/*  363 */     if (this.fContentHandler != null) {
/*      */       try {
/*  365 */         this.fContentHandler.processingInstruction(target, data.toString());
/*      */       }
/*  367 */       catch (SAXException e) {
/*  368 */         throw new XNIException(e);
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public void startElement(QName element, XMLAttributes attributes, Augmentations augs) throws XNIException {
/*  375 */     if (this.fContentHandler != null) {
/*      */       try {
/*  377 */         this.fTypeInfoProvider.beginStartElement(augs, attributes);
/*  378 */         this.fContentHandler.startElement((element.uri != null) ? element.uri : XMLSymbols.EMPTY_STRING, element.localpart, element.rawname, this.fAttrAdapter);
/*      */       
/*      */       }
/*  381 */       catch (SAXException e) {
/*  382 */         throw new XNIException(e);
/*      */       } finally {
/*      */         
/*  385 */         this.fTypeInfoProvider.finishStartElement();
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void emptyElement(QName element, XMLAttributes attributes, Augmentations augs) throws XNIException {
/*  393 */     startElement(element, attributes, augs);
/*  394 */     endElement(element, augs);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void startGeneralEntity(String name, XMLResourceIdentifier identifier, String encoding, Augmentations augs) throws XNIException {}
/*      */ 
/*      */   
/*      */   public void textDecl(String version, String encoding, Augmentations augs) throws XNIException {}
/*      */ 
/*      */   
/*      */   public void endGeneralEntity(String name, Augmentations augs) throws XNIException {}
/*      */ 
/*      */   
/*      */   public void characters(XMLString text, Augmentations augs) throws XNIException {
/*  409 */     if (this.fContentHandler != null) {
/*      */ 
/*      */       
/*  412 */       if (text.length == 0) {
/*      */         return;
/*      */       }
/*      */       try {
/*  416 */         this.fContentHandler.characters(text.ch, text.offset, text.length);
/*      */       }
/*  418 */       catch (SAXException e) {
/*  419 */         throw new XNIException(e);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void ignorableWhitespace(XMLString text, Augmentations augs) throws XNIException {
/*  426 */     if (this.fContentHandler != null) {
/*      */       try {
/*  428 */         this.fContentHandler.ignorableWhitespace(text.ch, text.offset, text.length);
/*      */       }
/*  430 */       catch (SAXException e) {
/*  431 */         throw new XNIException(e);
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public void endElement(QName element, Augmentations augs) throws XNIException {
/*  438 */     if (this.fContentHandler != null) {
/*      */       try {
/*  440 */         this.fTypeInfoProvider.beginEndElement(augs);
/*  441 */         this.fContentHandler.endElement((element.uri != null) ? element.uri : XMLSymbols.EMPTY_STRING, element.localpart, element.rawname);
/*      */       
/*      */       }
/*  444 */       catch (SAXException e) {
/*  445 */         throw new XNIException(e);
/*      */       } finally {
/*      */         
/*  448 */         this.fTypeInfoProvider.finishEndElement();
/*      */       } 
/*      */     }
/*      */   }
/*      */   
/*      */   public void startCDATA(Augmentations augs) throws XNIException {}
/*      */   
/*      */   public void endCDATA(Augmentations augs) throws XNIException {}
/*      */   
/*      */   public void endDocument(Augmentations augs) throws XNIException {
/*  458 */     if (this.fContentHandler != null) {
/*      */       try {
/*  460 */         this.fContentHandler.endDocument();
/*      */       }
/*  462 */       catch (SAXException e) {
/*  463 */         throw new XNIException(e);
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public void setDocumentSource(XMLDocumentSource source) {}
/*      */   
/*      */   public XMLDocumentSource getDocumentSource() {
/*  472 */     return this.fSchemaValidator;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setDocumentLocator(Locator locator) {
/*  480 */     this.fSAXLocatorWrapper.setLocator(locator);
/*  481 */     if (this.fContentHandler != null) {
/*  482 */       this.fContentHandler.setDocumentLocator(locator);
/*      */     }
/*      */   }
/*      */   
/*      */   public void startDocument() throws SAXException {
/*  487 */     this.fComponentManager.reset();
/*  488 */     this.fSchemaValidator.setDocumentHandler(this);
/*  489 */     this.fValidationManager.setEntityState(this);
/*  490 */     this.fTypeInfoProvider.finishStartElement();
/*  491 */     this.fNeedPushNSContext = true;
/*  492 */     if (this.fUnparsedEntities != null && !this.fUnparsedEntities.isEmpty())
/*      */     {
/*  494 */       this.fUnparsedEntities.clear();
/*      */     }
/*  496 */     this.fErrorReporter.setDocumentLocator(this.fSAXLocatorWrapper);
/*      */     try {
/*  498 */       this.fSchemaValidator.startDocument(this.fSAXLocatorWrapper, this.fSAXLocatorWrapper.getEncoding(), this.fNamespaceContext, null);
/*      */     }
/*  500 */     catch (XMLParseException e) {
/*  501 */       throw Util.toSAXParseException(e);
/*      */     }
/*  503 */     catch (XNIException e) {
/*  504 */       throw Util.toSAXException(e);
/*      */     } 
/*      */   }
/*      */   
/*      */   public void endDocument() throws SAXException {
/*  509 */     this.fSAXLocatorWrapper.setLocator(null);
/*      */     try {
/*  511 */       this.fSchemaValidator.endDocument(null);
/*      */     }
/*  513 */     catch (XMLParseException e) {
/*  514 */       throw Util.toSAXParseException(e);
/*      */     }
/*  516 */     catch (XNIException e) {
/*  517 */       throw Util.toSAXException(e);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void startPrefixMapping(String prefix, String uri) throws SAXException {
/*      */     String prefixSymbol, uriSymbol;
/*  525 */     if (!this.fStringsInternalized) {
/*  526 */       prefixSymbol = (prefix != null) ? this.fSymbolTable.addSymbol(prefix) : XMLSymbols.EMPTY_STRING;
/*  527 */       uriSymbol = (uri != null && uri.length() > 0) ? this.fSymbolTable.addSymbol(uri) : null;
/*      */     } else {
/*      */       
/*  530 */       prefixSymbol = (prefix != null) ? prefix : XMLSymbols.EMPTY_STRING;
/*  531 */       uriSymbol = (uri != null && uri.length() > 0) ? uri : null;
/*      */     } 
/*  533 */     if (this.fNeedPushNSContext) {
/*  534 */       this.fNeedPushNSContext = false;
/*  535 */       this.fNamespaceContext.pushContext();
/*      */     } 
/*  537 */     this.fNamespaceContext.declarePrefix(prefixSymbol, uriSymbol);
/*  538 */     if (this.fContentHandler != null) {
/*  539 */       this.fContentHandler.startPrefixMapping(prefix, uri);
/*      */     }
/*      */   }
/*      */   
/*      */   public void endPrefixMapping(String prefix) throws SAXException {
/*  544 */     if (this.fContentHandler != null) {
/*  545 */       this.fContentHandler.endPrefixMapping(prefix);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public void startElement(String uri, String localName, String qName, Attributes atts) throws SAXException {
/*  551 */     if (this.fNeedPushNSContext) {
/*  552 */       this.fNamespaceContext.pushContext();
/*      */     }
/*  554 */     this.fNeedPushNSContext = true;
/*      */ 
/*      */     
/*  557 */     fillQName(this.fElementQName, uri, localName, qName);
/*      */ 
/*      */     
/*  560 */     if (atts instanceof Attributes2) {
/*  561 */       fillXMLAttributes2((Attributes2)atts);
/*      */     } else {
/*      */       
/*  564 */       fillXMLAttributes(atts);
/*      */     } 
/*      */     
/*      */     try {
/*  568 */       this.fSchemaValidator.startElement(this.fElementQName, this.fAttributes, null);
/*      */     }
/*  570 */     catch (XMLParseException e) {
/*  571 */       throw Util.toSAXParseException(e);
/*      */     }
/*  573 */     catch (XNIException e) {
/*  574 */       throw Util.toSAXException(e);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void endElement(String uri, String localName, String qName) throws SAXException {
/*  580 */     fillQName(this.fElementQName, uri, localName, qName);
/*      */     try {
/*  582 */       this.fSchemaValidator.endElement(this.fElementQName, null);
/*      */     }
/*  584 */     catch (XMLParseException e) {
/*  585 */       throw Util.toSAXParseException(e);
/*      */     }
/*  587 */     catch (XNIException e) {
/*  588 */       throw Util.toSAXException(e);
/*      */     } finally {
/*      */       
/*  591 */       this.fNamespaceContext.popContext();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void characters(char[] ch, int start, int length) throws SAXException {
/*      */     try {
/*  598 */       this.fTempString.setValues(ch, start, length);
/*  599 */       this.fSchemaValidator.characters(this.fTempString, null);
/*      */     }
/*  601 */     catch (XMLParseException e) {
/*  602 */       throw Util.toSAXParseException(e);
/*      */     }
/*  604 */     catch (XNIException e) {
/*  605 */       throw Util.toSAXException(e);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void ignorableWhitespace(char[] ch, int start, int length) throws SAXException {
/*      */     try {
/*  612 */       this.fTempString.setValues(ch, start, length);
/*  613 */       this.fSchemaValidator.ignorableWhitespace(this.fTempString, null);
/*      */     }
/*  615 */     catch (XMLParseException e) {
/*  616 */       throw Util.toSAXParseException(e);
/*      */     }
/*  618 */     catch (XNIException e) {
/*  619 */       throw Util.toSAXException(e);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void processingInstruction(String target, String data) throws SAXException {
/*  630 */     if (this.fContentHandler != null) {
/*  631 */       this.fContentHandler.processingInstruction(target, data);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void skippedEntity(String name) throws SAXException {
/*  638 */     if (this.fContentHandler != null) {
/*  639 */       this.fContentHandler.skippedEntity(name);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void notationDecl(String name, String publicId, String systemId) throws SAXException {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void unparsedEntityDecl(String name, String publicId, String systemId, String notationName) throws SAXException {
/*  652 */     if (this.fUnparsedEntities == null) {
/*  653 */       this.fUnparsedEntities = new HashMap<>();
/*      */     }
/*  655 */     this.fUnparsedEntities.put(name, name);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void validate(Source source, Result result) throws SAXException, IOException {
/*  664 */     if (result instanceof SAXResult || result == null) {
/*  665 */       SAXSource saxSource = (SAXSource)source;
/*  666 */       SAXResult saxResult = (SAXResult)result;
/*      */       
/*  668 */       if (result != null) {
/*  669 */         setContentHandler(saxResult.getHandler());
/*      */       }
/*      */       
/*      */       try {
/*  673 */         XMLReader reader = saxSource.getXMLReader();
/*  674 */         if (reader == null) {
/*      */           
/*  676 */           reader = JdkXmlUtils.getXMLReader(this.fComponentManager.getFeature("jdk.xml.overrideDefaultParser"), this.fComponentManager
/*  677 */               .getFeature("http://javax.xml.XMLConstants/feature/secure-processing"));
/*      */ 
/*      */           
/*      */           try {
/*  681 */             if (reader instanceof com.sun.org.apache.xerces.internal.parsers.SAXParser) {
/*      */               
/*  683 */               XMLSecurityManager securityManager = (XMLSecurityManager)this.fComponentManager.getProperty("http://apache.org/xml/properties/security-manager");
/*  684 */               if (securityManager != null) {
/*      */                 try {
/*  686 */                   reader.setProperty("http://apache.org/xml/properties/security-manager", securityManager);
/*      */                 
/*      */                 }
/*  689 */                 catch (SAXException sAXException) {}
/*      */               }
/*      */               
/*      */               try {
/*  693 */                 XMLSecurityPropertyManager spm = (XMLSecurityPropertyManager)this.fComponentManager.getProperty("http://www.oracle.com/xml/jaxp/properties/xmlSecurityPropertyManager");
/*  694 */                 reader.setProperty("http://javax.xml.XMLConstants/property/accessExternalDTD", spm
/*  695 */                     .getValue(XMLSecurityPropertyManager.Property.ACCESS_EXTERNAL_DTD));
/*  696 */               } catch (SAXException exc) {
/*  697 */                 XMLSecurityManager.printWarning(reader.getClass().getName(), "http://javax.xml.XMLConstants/property/accessExternalDTD", exc);
/*      */               }
/*      */             
/*      */             } 
/*  701 */           } catch (Exception e) {
/*      */             
/*  703 */             throw new FactoryConfigurationError(e);
/*      */           } 
/*      */         } 
/*      */ 
/*      */ 
/*      */         
/*      */         try {
/*  710 */           this.fStringsInternalized = reader.getFeature("http://xml.org/sax/features/string-interning");
/*      */         }
/*  712 */         catch (SAXException exc) {
/*      */ 
/*      */           
/*  715 */           this.fStringsInternalized = false;
/*      */         } 
/*      */         
/*  718 */         ErrorHandler errorHandler = this.fComponentManager.getErrorHandler();
/*  719 */         reader.setErrorHandler((errorHandler != null) ? errorHandler : DraconianErrorHandler.getInstance());
/*  720 */         reader.setEntityResolver(this.fResolutionForwarder);
/*  721 */         this.fResolutionForwarder.setEntityResolver(this.fComponentManager.getResourceResolver());
/*  722 */         reader.setContentHandler(this);
/*  723 */         reader.setDTDHandler(this);
/*      */         
/*  725 */         InputSource is = saxSource.getInputSource();
/*  726 */         reader.parse(is);
/*      */       }
/*      */       finally {
/*      */         
/*  730 */         setContentHandler(null);
/*      */       } 
/*      */       return;
/*      */     } 
/*  734 */     throw new IllegalArgumentException(JAXPValidationMessageFormatter.formatMessage(this.fComponentManager.getLocale(), "SourceResultMismatch", new Object[] { source
/*      */             
/*  736 */             .getClass().getName(), result.getClass().getName() }));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ElementPSVI getElementPSVI() {
/*  744 */     return this.fTypeInfoProvider.getElementPSVI();
/*      */   }
/*      */   
/*      */   public AttributePSVI getAttributePSVI(int index) {
/*  748 */     return this.fTypeInfoProvider.getAttributePSVI(index);
/*      */   }
/*      */   
/*      */   public AttributePSVI getAttributePSVIByName(String uri, String localname) {
/*  752 */     return this.fTypeInfoProvider.getAttributePSVIByName(uri, localname);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void fillQName(QName toFill, String uri, String localpart, String raw) {
/*  763 */     if (!this.fStringsInternalized) {
/*  764 */       uri = (uri != null && uri.length() > 0) ? this.fSymbolTable.addSymbol(uri) : null;
/*  765 */       localpart = (localpart != null) ? this.fSymbolTable.addSymbol(localpart) : XMLSymbols.EMPTY_STRING;
/*  766 */       raw = (raw != null) ? this.fSymbolTable.addSymbol(raw) : XMLSymbols.EMPTY_STRING;
/*      */     } else {
/*      */       
/*  769 */       if (uri != null && uri.length() == 0) {
/*  770 */         uri = null;
/*      */       }
/*  772 */       if (localpart == null) {
/*  773 */         localpart = XMLSymbols.EMPTY_STRING;
/*      */       }
/*  775 */       if (raw == null) {
/*  776 */         raw = XMLSymbols.EMPTY_STRING;
/*      */       }
/*      */     } 
/*  779 */     String prefix = XMLSymbols.EMPTY_STRING;
/*  780 */     int prefixIdx = raw.indexOf(':');
/*  781 */     if (prefixIdx != -1) {
/*  782 */       prefix = this.fSymbolTable.addSymbol(raw.substring(0, prefixIdx));
/*      */     }
/*  784 */     toFill.setValues(prefix, localpart, raw, uri);
/*      */   }
/*      */ 
/*      */   
/*      */   private void fillXMLAttributes(Attributes att) {
/*  789 */     this.fAttributes.removeAllAttributes();
/*  790 */     int len = att.getLength();
/*  791 */     for (int i = 0; i < len; i++) {
/*  792 */       fillXMLAttribute(att, i);
/*  793 */       this.fAttributes.setSpecified(i, true);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void fillXMLAttributes2(Attributes2 att) {
/*  799 */     this.fAttributes.removeAllAttributes();
/*  800 */     int len = att.getLength();
/*  801 */     for (int i = 0; i < len; i++) {
/*  802 */       fillXMLAttribute(att, i);
/*  803 */       this.fAttributes.setSpecified(i, att.isSpecified(i));
/*  804 */       if (att.isDeclared(i)) {
/*  805 */         this.fAttributes.getAugmentations(i).putItem("ATTRIBUTE_DECLARED", Boolean.TRUE);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void fillXMLAttribute(Attributes att, int index) {
/*  812 */     fillQName(this.fAttributeQName, att.getURI(index), att.getLocalName(index), att.getQName(index));
/*  813 */     String type = att.getType(index);
/*  814 */     this.fAttributes.addAttributeNS(this.fAttributeQName, (type != null) ? type : XMLSymbols.fCDATASymbol, att.getValue(index)); }
/*      */   private class XMLSchemaTypeInfoProvider extends TypeInfoProvider {
/*      */     private Augmentations fElementAugs;
/*      */     private XMLAttributes fAttributes;
/*      */     private boolean fInStartElement = false;
/*      */     private boolean fInEndElement = false;
/*      */     void beginStartElement(Augmentations elementAugs, XMLAttributes attributes) { this.fInStartElement = true; this.fElementAugs = elementAugs; this.fAttributes = attributes; }
/*      */     void finishStartElement() { this.fInStartElement = false; this.fElementAugs = null; this.fAttributes = null; }
/*  822 */     void beginEndElement(Augmentations elementAugs) { this.fInEndElement = true; this.fElementAugs = elementAugs; } void finishEndElement() { this.fInEndElement = false; this.fElementAugs = null; } private void checkState(boolean forElementInfo) { if (!this.fInStartElement && (!this.fInEndElement || !forElementInfo)) throw new IllegalStateException(JAXPValidationMessageFormatter.formatMessage(ValidatorHandlerImpl.this.fComponentManager.getLocale(), "TypeInfoProviderIllegalState", null));  } public TypeInfo getAttributeTypeInfo(int index) { checkState(false); return getAttributeType(index); } private TypeInfo getAttributeType(int index) { checkState(false); if (index < 0 || this.fAttributes.getLength() <= index) throw new IndexOutOfBoundsException(Integer.toString(index));  Augmentations augs = this.fAttributes.getAugmentations(index); if (augs == null) return null;  AttributePSVI psvi = (AttributePSVI)augs.getItem("ATTRIBUTE_PSVI"); return getTypeInfoFromPSVI(psvi); } public TypeInfo getAttributeTypeInfo(String attributeUri, String attributeLocalName) { checkState(false); return getAttributeTypeInfo(this.fAttributes.getIndex(attributeUri, attributeLocalName)); } public TypeInfo getAttributeTypeInfo(String attributeQName) { checkState(false); return getAttributeTypeInfo(this.fAttributes.getIndex(attributeQName)); } public TypeInfo getElementTypeInfo() { checkState(true); if (this.fElementAugs == null) return null;  ElementPSVI psvi = (ElementPSVI)this.fElementAugs.getItem("ELEMENT_PSVI"); return getTypeInfoFromPSVI(psvi); } private TypeInfo getTypeInfoFromPSVI(ItemPSVI psvi) { if (psvi == null) return null;  if (psvi.getValidity() == 2) { XSTypeDefinition xSTypeDefinition = psvi.getMemberTypeDefinition(); if (xSTypeDefinition != null) return (xSTypeDefinition instanceof TypeInfo) ? (TypeInfo)xSTypeDefinition : null;  }  XSTypeDefinition t = psvi.getTypeDefinition(); if (t != null) return (t instanceof TypeInfo) ? (TypeInfo)t : null;  return null; } public boolean isIdAttribute(int index) { checkState(false); XSSimpleType type = (XSSimpleType)getAttributeType(index); if (type == null) return false;  return type.isIDType(); } public boolean isSpecified(int index) { checkState(false); return this.fAttributes.isSpecified(index); } ElementPSVI getElementPSVI() { return (this.fElementAugs != null) ? (ElementPSVI)this.fElementAugs.getItem("ELEMENT_PSVI") : null; } AttributePSVI getAttributePSVI(int index) { if (this.fAttributes != null) { Augmentations augs = this.fAttributes.getAugmentations(index); if (augs != null) return (AttributePSVI)augs.getItem("ATTRIBUTE_PSVI");  }  return null; } AttributePSVI getAttributePSVIByName(String uri, String localname) { if (this.fAttributes != null) { Augmentations augs = this.fAttributes.getAugmentations(uri, localname); if (augs != null) return (AttributePSVI)augs.getItem("ATTRIBUTE_PSVI");  }  return null; } private XMLSchemaTypeInfoProvider() {} } public ValidatorHandlerImpl(XMLSchemaValidatorComponentManager componentManager) { this.fTypeInfoProvider = new XMLSchemaTypeInfoProvider();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  972 */     this.fResolutionForwarder = new ResolutionForwarder(null);
/*      */     this.fComponentManager = componentManager;
/*      */     this.fErrorReporter = (XMLErrorReporter)this.fComponentManager.getProperty("http://apache.org/xml/properties/internal/error-reporter");
/*      */     this.fNamespaceContext = (NamespaceContext)this.fComponentManager.getProperty("http://apache.org/xml/properties/internal/namespace-context");
/*      */     this.fSchemaValidator = (XMLSchemaValidator)this.fComponentManager.getProperty("http://apache.org/xml/properties/internal/validator/schema");
/*      */     this.fSymbolTable = (SymbolTable)this.fComponentManager.getProperty("http://apache.org/xml/properties/internal/symbol-table");
/*      */     this.fValidationManager = (ValidationManager)this.fComponentManager.getProperty("http://apache.org/xml/properties/internal/validation-manager"); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static final class ResolutionForwarder
/*      */     implements EntityResolver2
/*      */   {
/*      */     public ResolutionForwarder(LSResourceResolver entityResolver) {
/*  995 */       setEntityResolver(entityResolver);
/*      */     }
/*      */     
/*      */     private static final String XML_TYPE = "http://www.w3.org/TR/REC-xml";
/*      */     protected LSResourceResolver fEntityResolver;
/*      */     
/*      */     public ResolutionForwarder() {}
/*      */     
/*      */     public void setEntityResolver(LSResourceResolver entityResolver) {
/* 1004 */       this.fEntityResolver = entityResolver;
/*      */     }
/*      */ 
/*      */     
/*      */     public LSResourceResolver getEntityResolver() {
/* 1009 */       return this.fEntityResolver;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public InputSource getExternalSubset(String name, String baseURI) throws SAXException, IOException {
/* 1017 */       return null;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public InputSource resolveEntity(String name, String publicId, String baseURI, String systemId) throws SAXException, IOException {
/* 1026 */       if (this.fEntityResolver != null) {
/* 1027 */         LSInput lsInput = this.fEntityResolver.resolveResource("http://www.w3.org/TR/REC-xml", null, publicId, systemId, baseURI);
/* 1028 */         if (lsInput != null) {
/* 1029 */           String pubId = lsInput.getPublicId();
/* 1030 */           String sysId = lsInput.getSystemId();
/* 1031 */           String baseSystemId = lsInput.getBaseURI();
/* 1032 */           Reader charStream = lsInput.getCharacterStream();
/* 1033 */           InputStream byteStream = lsInput.getByteStream();
/* 1034 */           String data = lsInput.getStringData();
/* 1035 */           String encoding = lsInput.getEncoding();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 1044 */           InputSource inputSource = new InputSource();
/* 1045 */           inputSource.setPublicId(pubId);
/* 1046 */           inputSource.setSystemId((baseSystemId != null) ? resolveSystemId(systemId, baseSystemId) : systemId);
/*      */           
/* 1048 */           if (charStream != null) {
/* 1049 */             inputSource.setCharacterStream(charStream);
/*      */           }
/* 1051 */           else if (byteStream != null) {
/* 1052 */             inputSource.setByteStream(byteStream);
/*      */           }
/* 1054 */           else if (data != null && data.length() != 0) {
/* 1055 */             inputSource.setCharacterStream(new StringReader(data));
/*      */           } 
/* 1057 */           inputSource.setEncoding(encoding);
/* 1058 */           return inputSource;
/*      */         } 
/*      */       } 
/* 1061 */       return null;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public InputSource resolveEntity(String publicId, String systemId) throws SAXException, IOException {
/* 1067 */       return resolveEntity(null, publicId, null, systemId);
/*      */     }
/*      */ 
/*      */     
/*      */     private String resolveSystemId(String systemId, String baseURI) {
/*      */       try {
/* 1073 */         return XMLEntityManager.expandSystemId(systemId, baseURI, false);
/*      */ 
/*      */ 
/*      */       
/*      */       }
/* 1078 */       catch (com.sun.org.apache.xerces.internal.util.URI.MalformedURIException ex) {
/* 1079 */         return systemId;
/*      */       } 
/*      */     }
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xerces/internal/jaxp/validation/ValidatorHandlerImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */