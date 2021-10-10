/*     */ package com.sun.org.apache.xerces.internal.parsers;
/*     */ 
/*     */ import com.sun.org.apache.xerces.internal.util.EntityResolver2Wrapper;
/*     */ import com.sun.org.apache.xerces.internal.util.EntityResolverWrapper;
/*     */ import com.sun.org.apache.xerces.internal.util.ErrorHandlerWrapper;
/*     */ import com.sun.org.apache.xerces.internal.util.SAXMessageFormatter;
/*     */ import com.sun.org.apache.xerces.internal.util.Status;
/*     */ import com.sun.org.apache.xerces.internal.util.SymbolTable;
/*     */ import com.sun.org.apache.xerces.internal.utils.XMLSecurityManager;
/*     */ import com.sun.org.apache.xerces.internal.utils.XMLSecurityPropertyManager;
/*     */ import com.sun.org.apache.xerces.internal.xni.XNIException;
/*     */ import com.sun.org.apache.xerces.internal.xni.grammars.XMLGrammarPool;
/*     */ import com.sun.org.apache.xerces.internal.xni.parser.XMLConfigurationException;
/*     */ import com.sun.org.apache.xerces.internal.xni.parser.XMLEntityResolver;
/*     */ import com.sun.org.apache.xerces.internal.xni.parser.XMLErrorHandler;
/*     */ import com.sun.org.apache.xerces.internal.xni.parser.XMLInputSource;
/*     */ import com.sun.org.apache.xerces.internal.xni.parser.XMLParseException;
/*     */ import com.sun.org.apache.xerces.internal.xni.parser.XMLParserConfiguration;
/*     */ import java.io.IOException;
/*     */ import org.xml.sax.EntityResolver;
/*     */ import org.xml.sax.ErrorHandler;
/*     */ import org.xml.sax.InputSource;
/*     */ import org.xml.sax.SAXException;
/*     */ import org.xml.sax.SAXNotRecognizedException;
/*     */ import org.xml.sax.SAXNotSupportedException;
/*     */ import org.xml.sax.SAXParseException;
/*     */ import org.xml.sax.ext.EntityResolver2;
/*     */ import org.xml.sax.helpers.LocatorImpl;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DOMParser
/*     */   extends AbstractDOMParser
/*     */ {
/*     */   protected static final String USE_ENTITY_RESOLVER2 = "http://xml.org/sax/features/use-entity-resolver2";
/*     */   protected static final String REPORT_WHITESPACE = "http://java.sun.com/xml/schema/features/report-ignored-element-content-whitespace";
/*     */   private static final String XML_SECURITY_PROPERTY_MANAGER = "http://www.oracle.com/xml/jaxp/properties/xmlSecurityPropertyManager";
/*  84 */   private static final String[] RECOGNIZED_FEATURES = new String[] { "http://java.sun.com/xml/schema/features/report-ignored-element-content-whitespace" };
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static final String SYMBOL_TABLE = "http://apache.org/xml/properties/internal/symbol-table";
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static final String XMLGRAMMAR_POOL = "http://apache.org/xml/properties/internal/grammar-pool";
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  99 */   private static final String[] RECOGNIZED_PROPERTIES = new String[] { "http://apache.org/xml/properties/internal/symbol-table", "http://apache.org/xml/properties/internal/grammar-pool" };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean fUseEntityResolver2 = true;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DOMParser(XMLParserConfiguration config) {
/* 121 */     super(config);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DOMParser() {
/* 128 */     this((SymbolTable)null, (XMLGrammarPool)null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DOMParser(SymbolTable symbolTable) {
/* 135 */     this(symbolTable, (XMLGrammarPool)null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DOMParser(SymbolTable symbolTable, XMLGrammarPool grammarPool) {
/* 144 */     super(new XIncludeAwareParserConfiguration());
/*     */ 
/*     */     
/* 147 */     this.fConfiguration.addRecognizedProperties(RECOGNIZED_PROPERTIES);
/* 148 */     if (symbolTable != null) {
/* 149 */       this.fConfiguration.setProperty("http://apache.org/xml/properties/internal/symbol-table", symbolTable);
/*     */     }
/* 151 */     if (grammarPool != null) {
/* 152 */       this.fConfiguration.setProperty("http://apache.org/xml/properties/internal/grammar-pool", grammarPool);
/*     */     }
/*     */     
/* 155 */     this.fConfiguration.addRecognizedFeatures(RECOGNIZED_FEATURES);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void parse(String systemId) throws SAXException, IOException {
/* 179 */     XMLInputSource source = new XMLInputSource(null, systemId, null);
/*     */     try {
/* 181 */       parse(source);
/*     */ 
/*     */     
/*     */     }
/* 185 */     catch (XMLParseException e) {
/* 186 */       Exception ex = e.getException();
/* 187 */       if (ex == null) {
/*     */ 
/*     */         
/* 190 */         LocatorImpl locatorImpl = new LocatorImpl();
/* 191 */         locatorImpl.setPublicId(e.getPublicId());
/* 192 */         locatorImpl.setSystemId(e.getExpandedSystemId());
/* 193 */         locatorImpl.setLineNumber(e.getLineNumber());
/* 194 */         locatorImpl.setColumnNumber(e.getColumnNumber());
/* 195 */         throw new SAXParseException(e.getMessage(), locatorImpl);
/*     */       } 
/* 197 */       if (ex instanceof SAXException)
/*     */       {
/* 199 */         throw (SAXException)ex;
/*     */       }
/* 201 */       if (ex instanceof IOException) {
/* 202 */         throw (IOException)ex;
/*     */       }
/* 204 */       throw new SAXException(ex);
/*     */     }
/* 206 */     catch (XNIException e) {
/* 207 */       e.printStackTrace();
/* 208 */       Exception ex = e.getException();
/* 209 */       if (ex == null) {
/* 210 */         throw new SAXException(e.getMessage());
/*     */       }
/* 212 */       if (ex instanceof SAXException) {
/* 213 */         throw (SAXException)ex;
/*     */       }
/* 215 */       if (ex instanceof IOException) {
/* 216 */         throw (IOException)ex;
/*     */       }
/* 218 */       throw new SAXException(ex);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void parse(InputSource inputSource) throws SAXException, IOException {
/*     */     try {
/* 238 */       XMLInputSource xmlInputSource = new XMLInputSource(inputSource.getPublicId(), inputSource.getSystemId(), null);
/*     */       
/* 240 */       xmlInputSource.setByteStream(inputSource.getByteStream());
/* 241 */       xmlInputSource.setCharacterStream(inputSource.getCharacterStream());
/* 242 */       xmlInputSource.setEncoding(inputSource.getEncoding());
/* 243 */       parse(xmlInputSource);
/*     */ 
/*     */     
/*     */     }
/* 247 */     catch (XMLParseException e) {
/* 248 */       Exception ex = e.getException();
/* 249 */       if (ex == null) {
/*     */ 
/*     */         
/* 252 */         LocatorImpl locatorImpl = new LocatorImpl();
/* 253 */         locatorImpl.setPublicId(e.getPublicId());
/* 254 */         locatorImpl.setSystemId(e.getExpandedSystemId());
/* 255 */         locatorImpl.setLineNumber(e.getLineNumber());
/* 256 */         locatorImpl.setColumnNumber(e.getColumnNumber());
/* 257 */         throw new SAXParseException(e.getMessage(), locatorImpl);
/*     */       } 
/* 259 */       if (ex instanceof SAXException)
/*     */       {
/* 261 */         throw (SAXException)ex;
/*     */       }
/* 263 */       if (ex instanceof IOException) {
/* 264 */         throw (IOException)ex;
/*     */       }
/* 266 */       throw new SAXException(ex);
/*     */     }
/* 268 */     catch (XNIException e) {
/* 269 */       Exception ex = e.getException();
/* 270 */       if (ex == null) {
/* 271 */         throw new SAXException(e.getMessage());
/*     */       }
/* 273 */       if (ex instanceof SAXException) {
/* 274 */         throw (SAXException)ex;
/*     */       }
/* 276 */       if (ex instanceof IOException) {
/* 277 */         throw (IOException)ex;
/*     */       }
/* 279 */       throw new SAXException(ex);
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
/*     */   
/*     */   public void setEntityResolver(EntityResolver resolver) {
/*     */     try {
/* 294 */       XMLEntityResolver xer = (XMLEntityResolver)this.fConfiguration.getProperty("http://apache.org/xml/properties/internal/entity-resolver");
/* 295 */       if (this.fUseEntityResolver2 && resolver instanceof EntityResolver2) {
/* 296 */         if (xer instanceof EntityResolver2Wrapper) {
/* 297 */           EntityResolver2Wrapper er2w = (EntityResolver2Wrapper)xer;
/* 298 */           er2w.setEntityResolver((EntityResolver2)resolver);
/*     */         } else {
/*     */           
/* 301 */           this.fConfiguration.setProperty("http://apache.org/xml/properties/internal/entity-resolver", new EntityResolver2Wrapper((EntityResolver2)resolver));
/*     */         
/*     */         }
/*     */       
/*     */       }
/* 306 */       else if (xer instanceof EntityResolverWrapper) {
/* 307 */         EntityResolverWrapper erw = (EntityResolverWrapper)xer;
/* 308 */         erw.setEntityResolver(resolver);
/*     */       } else {
/*     */         
/* 311 */         this.fConfiguration.setProperty("http://apache.org/xml/properties/internal/entity-resolver", new EntityResolverWrapper(resolver));
/*     */       
/*     */       }
/*     */     
/*     */     }
/* 316 */     catch (XMLConfigurationException xMLConfigurationException) {}
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
/*     */   public EntityResolver getEntityResolver() {
/* 331 */     EntityResolver entityResolver = null;
/*     */     
/*     */     try {
/* 334 */       XMLEntityResolver xmlEntityResolver = (XMLEntityResolver)this.fConfiguration.getProperty("http://apache.org/xml/properties/internal/entity-resolver");
/* 335 */       if (xmlEntityResolver != null) {
/* 336 */         if (xmlEntityResolver instanceof EntityResolverWrapper)
/*     */         {
/* 338 */           entityResolver = ((EntityResolverWrapper)xmlEntityResolver).getEntityResolver();
/*     */         }
/* 340 */         else if (xmlEntityResolver instanceof EntityResolver2Wrapper)
/*     */         {
/* 342 */           entityResolver = ((EntityResolver2Wrapper)xmlEntityResolver).getEntityResolver();
/*     */         }
/*     */       
/*     */       }
/* 346 */     } catch (XMLConfigurationException xMLConfigurationException) {}
/*     */ 
/*     */     
/* 349 */     return entityResolver;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setErrorHandler(ErrorHandler errorHandler) {
/*     */     try {
/* 374 */       XMLErrorHandler xeh = (XMLErrorHandler)this.fConfiguration.getProperty("http://apache.org/xml/properties/internal/error-handler");
/* 375 */       if (xeh instanceof ErrorHandlerWrapper) {
/* 376 */         ErrorHandlerWrapper ehw = (ErrorHandlerWrapper)xeh;
/* 377 */         ehw.setErrorHandler(errorHandler);
/*     */       } else {
/*     */         
/* 380 */         this.fConfiguration.setProperty("http://apache.org/xml/properties/internal/error-handler", new ErrorHandlerWrapper(errorHandler));
/*     */       }
/*     */     
/*     */     }
/* 384 */     catch (XMLConfigurationException xMLConfigurationException) {}
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
/*     */   public ErrorHandler getErrorHandler() {
/* 399 */     ErrorHandler errorHandler = null;
/*     */     
/*     */     try {
/* 402 */       XMLErrorHandler xmlErrorHandler = (XMLErrorHandler)this.fConfiguration.getProperty("http://apache.org/xml/properties/internal/error-handler");
/* 403 */       if (xmlErrorHandler != null && xmlErrorHandler instanceof ErrorHandlerWrapper)
/*     */       {
/* 405 */         errorHandler = ((ErrorHandlerWrapper)xmlErrorHandler).getErrorHandler();
/*     */       }
/*     */     }
/* 408 */     catch (XMLConfigurationException xMLConfigurationException) {}
/*     */ 
/*     */     
/* 411 */     return errorHandler;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setFeature(String featureId, boolean state) throws SAXNotRecognizedException, SAXNotSupportedException {
/*     */     try {
/* 438 */       if (featureId.equals("http://xml.org/sax/features/use-entity-resolver2")) {
/* 439 */         if (state != this.fUseEntityResolver2) {
/* 440 */           this.fUseEntityResolver2 = state;
/*     */           
/* 442 */           setEntityResolver(getEntityResolver());
/*     */         } 
/*     */ 
/*     */ 
/*     */         
/*     */         return;
/*     */       } 
/*     */ 
/*     */       
/* 451 */       this.fConfiguration.setFeature(featureId, state);
/*     */     }
/* 453 */     catch (XMLConfigurationException e) {
/* 454 */       String identifier = e.getIdentifier();
/* 455 */       if (e.getType() == Status.NOT_RECOGNIZED) {
/* 456 */         throw new SAXNotRecognizedException(
/* 457 */             SAXMessageFormatter.formatMessage(this.fConfiguration.getLocale(), "feature-not-recognized", new Object[] { identifier }));
/*     */       }
/*     */ 
/*     */       
/* 461 */       throw new SAXNotSupportedException(
/* 462 */           SAXMessageFormatter.formatMessage(this.fConfiguration.getLocale(), "feature-not-supported", new Object[] { identifier }));
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getFeature(String featureId) throws SAXNotRecognizedException, SAXNotSupportedException {
/*     */     try {
/* 492 */       if (featureId.equals("http://xml.org/sax/features/use-entity-resolver2")) {
/* 493 */         return this.fUseEntityResolver2;
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 500 */       return this.fConfiguration.getFeature(featureId);
/*     */     }
/* 502 */     catch (XMLConfigurationException e) {
/* 503 */       String identifier = e.getIdentifier();
/* 504 */       if (e.getType() == Status.NOT_RECOGNIZED) {
/* 505 */         throw new SAXNotRecognizedException(
/* 506 */             SAXMessageFormatter.formatMessage(this.fConfiguration.getLocale(), "feature-not-recognized", new Object[] { identifier }));
/*     */       }
/*     */ 
/*     */       
/* 510 */       throw new SAXNotSupportedException(
/* 511 */           SAXMessageFormatter.formatMessage(this.fConfiguration.getLocale(), "feature-not-supported", new Object[] { identifier }));
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setProperty(String propertyId, Object value) throws SAXNotRecognizedException, SAXNotSupportedException {
/* 539 */     if (propertyId.equals("http://apache.org/xml/properties/security-manager")) {
/* 540 */       this.securityManager = XMLSecurityManager.convert(value, this.securityManager);
/* 541 */       setProperty0("http://apache.org/xml/properties/security-manager", this.securityManager);
/*     */       return;
/*     */     } 
/* 544 */     if (propertyId.equals("http://www.oracle.com/xml/jaxp/properties/xmlSecurityPropertyManager")) {
/* 545 */       if (value == null) {
/* 546 */         this.securityPropertyManager = new XMLSecurityPropertyManager();
/*     */       } else {
/* 548 */         this.securityPropertyManager = (XMLSecurityPropertyManager)value;
/*     */       } 
/* 550 */       setProperty0("http://www.oracle.com/xml/jaxp/properties/xmlSecurityPropertyManager", this.securityPropertyManager);
/*     */       
/*     */       return;
/*     */     } 
/* 554 */     if (this.securityManager == null) {
/* 555 */       this.securityManager = new XMLSecurityManager(true);
/* 556 */       setProperty0("http://apache.org/xml/properties/security-manager", this.securityManager);
/*     */     } 
/*     */     
/* 559 */     if (this.securityPropertyManager == null) {
/* 560 */       this.securityPropertyManager = new XMLSecurityPropertyManager();
/* 561 */       setProperty0("http://www.oracle.com/xml/jaxp/properties/xmlSecurityPropertyManager", this.securityPropertyManager);
/*     */     } 
/* 563 */     int index = this.securityPropertyManager.getIndex(propertyId);
/*     */     
/* 565 */     if (index > -1) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 571 */       this.securityPropertyManager.setValue(index, XMLSecurityPropertyManager.State.APIPROPERTY, (String)value);
/*     */     
/*     */     }
/* 574 */     else if (!this.securityManager.setLimit(propertyId, XMLSecurityManager.State.APIPROPERTY, value)) {
/*     */       
/* 576 */       setProperty0(propertyId, value);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setProperty0(String propertyId, Object value) throws SAXNotRecognizedException, SAXNotSupportedException {
/*     */     try {
/* 584 */       this.fConfiguration.setProperty(propertyId, value);
/*     */     }
/* 586 */     catch (XMLConfigurationException e) {
/* 587 */       String identifier = e.getIdentifier();
/* 588 */       if (e.getType() == Status.NOT_RECOGNIZED) {
/* 589 */         throw new SAXNotRecognizedException(
/* 590 */             SAXMessageFormatter.formatMessage(this.fConfiguration.getLocale(), "property-not-recognized", new Object[] { identifier }));
/*     */       }
/*     */ 
/*     */       
/* 594 */       throw new SAXNotSupportedException(
/* 595 */           SAXMessageFormatter.formatMessage(this.fConfiguration.getLocale(), "property-not-supported", new Object[] { identifier }));
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object getProperty(String propertyId) throws SAXNotRecognizedException, SAXNotSupportedException {
/* 619 */     if (propertyId.equals("http://apache.org/xml/properties/dom/current-element-node")) {
/* 620 */       boolean deferred = false;
/*     */       try {
/* 622 */         deferred = getFeature("http://apache.org/xml/features/dom/defer-node-expansion");
/*     */       }
/* 624 */       catch (XMLConfigurationException xMLConfigurationException) {}
/*     */ 
/*     */       
/* 627 */       if (deferred) {
/* 628 */         throw new SAXNotSupportedException("Current element node cannot be queried when node expansion is deferred.");
/*     */       }
/* 630 */       return (this.fCurrentNode != null && this.fCurrentNode
/* 631 */         .getNodeType() == 1) ? this.fCurrentNode : null;
/*     */     } 
/*     */ 
/*     */     
/*     */     try {
/* 636 */       XMLSecurityPropertyManager spm = (XMLSecurityPropertyManager)this.fConfiguration.getProperty("http://www.oracle.com/xml/jaxp/properties/xmlSecurityPropertyManager");
/* 637 */       int index = spm.getIndex(propertyId);
/* 638 */       if (index > -1) {
/* 639 */         return spm.getValueByIndex(index);
/*     */       }
/*     */       
/* 642 */       return this.fConfiguration.getProperty(propertyId);
/*     */     }
/* 644 */     catch (XMLConfigurationException e) {
/* 645 */       String identifier = e.getIdentifier();
/* 646 */       if (e.getType() == Status.NOT_RECOGNIZED) {
/* 647 */         throw new SAXNotRecognizedException(
/* 648 */             SAXMessageFormatter.formatMessage(this.fConfiguration.getLocale(), "property-not-recognized", new Object[] { identifier }));
/*     */       }
/*     */ 
/*     */       
/* 652 */       throw new SAXNotSupportedException(
/* 653 */           SAXMessageFormatter.formatMessage(this.fConfiguration.getLocale(), "property-not-supported", new Object[] { identifier }));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XMLParserConfiguration getXMLParserConfiguration() {
/* 664 */     return this.fConfiguration;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xerces/internal/parsers/DOMParser.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */