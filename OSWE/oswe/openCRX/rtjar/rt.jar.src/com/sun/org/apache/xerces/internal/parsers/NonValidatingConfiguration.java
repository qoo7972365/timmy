/*     */ package com.sun.org.apache.xerces.internal.parsers;
/*     */ 
/*     */ import com.sun.org.apache.xerces.internal.impl.XMLDTDScannerImpl;
/*     */ import com.sun.org.apache.xerces.internal.impl.XMLDocumentScannerImpl;
/*     */ import com.sun.org.apache.xerces.internal.impl.XMLEntityManager;
/*     */ import com.sun.org.apache.xerces.internal.impl.XMLErrorReporter;
/*     */ import com.sun.org.apache.xerces.internal.impl.XMLNSDocumentScannerImpl;
/*     */ import com.sun.org.apache.xerces.internal.impl.dv.DTDDVFactory;
/*     */ import com.sun.org.apache.xerces.internal.impl.msg.XMLMessageFormatter;
/*     */ import com.sun.org.apache.xerces.internal.impl.validation.ValidationManager;
/*     */ import com.sun.org.apache.xerces.internal.util.FeatureState;
/*     */ import com.sun.org.apache.xerces.internal.util.PropertyState;
/*     */ import com.sun.org.apache.xerces.internal.util.SymbolTable;
/*     */ import com.sun.org.apache.xerces.internal.utils.XMLSecurityPropertyManager;
/*     */ import com.sun.org.apache.xerces.internal.xni.XMLLocator;
/*     */ import com.sun.org.apache.xerces.internal.xni.XNIException;
/*     */ import com.sun.org.apache.xerces.internal.xni.grammars.XMLGrammarPool;
/*     */ import com.sun.org.apache.xerces.internal.xni.parser.XMLComponent;
/*     */ import com.sun.org.apache.xerces.internal.xni.parser.XMLComponentManager;
/*     */ import com.sun.org.apache.xerces.internal.xni.parser.XMLConfigurationException;
/*     */ import com.sun.org.apache.xerces.internal.xni.parser.XMLDTDScanner;
/*     */ import com.sun.org.apache.xerces.internal.xni.parser.XMLDocumentScanner;
/*     */ import com.sun.org.apache.xerces.internal.xni.parser.XMLInputSource;
/*     */ import com.sun.org.apache.xerces.internal.xni.parser.XMLPullParserConfiguration;
/*     */ import java.io.IOException;
/*     */ import java.util.Locale;
/*     */ import jdk.xml.internal.JdkXmlUtils;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class NonValidatingConfiguration
/*     */   extends BasicParserConfiguration
/*     */   implements XMLPullParserConfiguration
/*     */ {
/*     */   protected static final String WARN_ON_DUPLICATE_ATTDEF = "http://apache.org/xml/features/validation/warn-on-duplicate-attdef";
/*     */   protected static final String WARN_ON_DUPLICATE_ENTITYDEF = "http://apache.org/xml/features/warn-on-duplicate-entitydef";
/*     */   protected static final String WARN_ON_UNDECLARED_ELEMDEF = "http://apache.org/xml/features/validation/warn-on-undeclared-elemdef";
/*     */   protected static final String ALLOW_JAVA_ENCODINGS = "http://apache.org/xml/features/allow-java-encodings";
/*     */   protected static final String CONTINUE_AFTER_FATAL_ERROR = "http://apache.org/xml/features/continue-after-fatal-error";
/*     */   protected static final String LOAD_EXTERNAL_DTD = "http://apache.org/xml/features/nonvalidating/load-external-dtd";
/*     */   protected static final String NOTIFY_BUILTIN_REFS = "http://apache.org/xml/features/scanner/notify-builtin-refs";
/*     */   protected static final String NOTIFY_CHAR_REFS = "http://apache.org/xml/features/scanner/notify-char-refs";
/*     */   protected static final String NORMALIZE_DATA = "http://apache.org/xml/features/validation/schema/normalized-value";
/*     */   protected static final String SCHEMA_ELEMENT_DEFAULT = "http://apache.org/xml/features/validation/schema/element-default";
/*     */   protected static final String ERROR_REPORTER = "http://apache.org/xml/properties/internal/error-reporter";
/*     */   protected static final String ENTITY_MANAGER = "http://apache.org/xml/properties/internal/entity-manager";
/*     */   protected static final String DOCUMENT_SCANNER = "http://apache.org/xml/properties/internal/document-scanner";
/*     */   protected static final String DTD_SCANNER = "http://apache.org/xml/properties/internal/dtd-scanner";
/*     */   protected static final String XMLGRAMMAR_POOL = "http://apache.org/xml/properties/internal/grammar-pool";
/*     */   protected static final String DTD_VALIDATOR = "http://apache.org/xml/properties/internal/validator/dtd";
/*     */   protected static final String NAMESPACE_BINDER = "http://apache.org/xml/properties/internal/namespace-binder";
/*     */   protected static final String DATATYPE_VALIDATOR_FACTORY = "http://apache.org/xml/properties/internal/datatype-validator-factory";
/*     */   protected static final String VALIDATION_MANAGER = "http://apache.org/xml/properties/internal/validation-manager";
/*     */   protected static final String SCHEMA_VALIDATOR = "http://apache.org/xml/properties/internal/validator/schema";
/*     */   protected static final String LOCALE = "http://apache.org/xml/properties/locale";
/*     */   protected static final String XML_SECURITY_PROPERTY_MANAGER = "http://www.oracle.com/xml/jaxp/properties/xmlSecurityPropertyManager";
/*     */   private static final String SECURITY_MANAGER = "http://apache.org/xml/properties/security-manager";
/*     */   private static final boolean PRINT_EXCEPTION_STACK_TRACE = false;
/*     */   protected XMLGrammarPool fGrammarPool;
/*     */   protected DTDDVFactory fDatatypeValidatorFactory;
/*     */   protected XMLErrorReporter fErrorReporter;
/*     */   protected XMLEntityManager fEntityManager;
/*     */   protected XMLDocumentScanner fScanner;
/*     */   protected XMLInputSource fInputSource;
/*     */   protected XMLDTDScanner fDTDScanner;
/*     */   protected ValidationManager fValidationManager;
/*     */   private XMLNSDocumentScannerImpl fNamespaceScanner;
/*     */   private XMLDocumentScannerImpl fNonNSScanner;
/*     */   protected boolean fConfigUpdated = false;
/*     */   protected XMLLocator fLocator;
/*     */   protected boolean fParseInProgress = false;
/*     */   
/*     */   public NonValidatingConfiguration() {
/* 237 */     this((SymbolTable)null, (XMLGrammarPool)null, (XMLComponentManager)null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NonValidatingConfiguration(SymbolTable symbolTable) {
/* 246 */     this(symbolTable, (XMLGrammarPool)null, (XMLComponentManager)null);
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
/*     */   public NonValidatingConfiguration(SymbolTable symbolTable, XMLGrammarPool grammarPool) {
/* 262 */     this(symbolTable, grammarPool, (XMLComponentManager)null);
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
/*     */   public NonValidatingConfiguration(SymbolTable symbolTable, XMLGrammarPool grammarPool, XMLComponentManager parentSettings) {
/* 280 */     super(symbolTable, parentSettings);
/*     */ 
/*     */     
/* 283 */     String[] recognizedFeatures = { "http://apache.org/xml/features/internal/parser-settings", "http://xml.org/sax/features/namespaces", "http://apache.org/xml/features/continue-after-fatal-error", "jdk.xml.overrideDefaultParser" };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 296 */     addRecognizedFeatures(recognizedFeatures);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 302 */     this.fFeatures.put("http://apache.org/xml/features/continue-after-fatal-error", Boolean.FALSE);
/* 303 */     this.fFeatures.put("http://apache.org/xml/features/internal/parser-settings", Boolean.TRUE);
/* 304 */     this.fFeatures.put("http://xml.org/sax/features/namespaces", Boolean.TRUE);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 309 */     this.fFeatures.put("jdk.xml.overrideDefaultParser", Boolean.valueOf(JdkXmlUtils.OVERRIDE_PARSER_DEFAULT));
/*     */ 
/*     */     
/* 312 */     String[] recognizedProperties = { "http://apache.org/xml/properties/internal/error-reporter", "http://apache.org/xml/properties/internal/entity-manager", "http://apache.org/xml/properties/internal/document-scanner", "http://apache.org/xml/properties/internal/dtd-scanner", "http://apache.org/xml/properties/internal/validator/dtd", "http://apache.org/xml/properties/internal/namespace-binder", "http://apache.org/xml/properties/internal/grammar-pool", "http://apache.org/xml/properties/internal/datatype-validator-factory", "http://apache.org/xml/properties/internal/validation-manager", "http://apache.org/xml/properties/locale", "http://apache.org/xml/properties/security-manager", "http://www.oracle.com/xml/jaxp/properties/xmlSecurityPropertyManager" };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 326 */     addRecognizedProperties(recognizedProperties);
/*     */     
/* 328 */     this.fGrammarPool = grammarPool;
/* 329 */     if (this.fGrammarPool != null) {
/* 330 */       this.fProperties.put("http://apache.org/xml/properties/internal/grammar-pool", this.fGrammarPool);
/*     */     }
/*     */     
/* 333 */     this.fEntityManager = createEntityManager();
/* 334 */     this.fProperties.put("http://apache.org/xml/properties/internal/entity-manager", this.fEntityManager);
/* 335 */     addComponent(this.fEntityManager);
/*     */     
/* 337 */     this.fErrorReporter = createErrorReporter();
/* 338 */     this.fErrorReporter.setDocumentLocator(this.fEntityManager.getEntityScanner());
/* 339 */     this.fProperties.put("http://apache.org/xml/properties/internal/error-reporter", this.fErrorReporter);
/* 340 */     addComponent(this.fErrorReporter);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 345 */     this.fDTDScanner = createDTDScanner();
/* 346 */     if (this.fDTDScanner != null) {
/* 347 */       this.fProperties.put("http://apache.org/xml/properties/internal/dtd-scanner", this.fDTDScanner);
/* 348 */       if (this.fDTDScanner instanceof XMLComponent) {
/* 349 */         addComponent((XMLComponent)this.fDTDScanner);
/*     */       }
/*     */     } 
/*     */     
/* 353 */     this.fDatatypeValidatorFactory = createDatatypeValidatorFactory();
/* 354 */     if (this.fDatatypeValidatorFactory != null) {
/* 355 */       this.fProperties.put("http://apache.org/xml/properties/internal/datatype-validator-factory", this.fDatatypeValidatorFactory);
/*     */     }
/*     */     
/* 358 */     this.fValidationManager = createValidationManager();
/*     */     
/* 360 */     if (this.fValidationManager != null) {
/* 361 */       this.fProperties.put("http://apache.org/xml/properties/internal/validation-manager", this.fValidationManager);
/*     */     }
/*     */     
/* 364 */     if (this.fErrorReporter.getMessageFormatter("http://www.w3.org/TR/1998/REC-xml-19980210") == null) {
/* 365 */       XMLMessageFormatter xmft = new XMLMessageFormatter();
/* 366 */       this.fErrorReporter.putMessageFormatter("http://www.w3.org/TR/1998/REC-xml-19980210", xmft);
/* 367 */       this.fErrorReporter.putMessageFormatter("http://www.w3.org/TR/1999/REC-xml-names-19990114", xmft);
/*     */     } 
/*     */     
/* 370 */     this.fConfigUpdated = false;
/*     */ 
/*     */     
/*     */     try {
/* 374 */       setLocale(Locale.getDefault());
/*     */     }
/* 376 */     catch (XNIException xNIException) {}
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 381 */     setProperty("http://www.oracle.com/xml/jaxp/properties/xmlSecurityPropertyManager", new XMLSecurityPropertyManager());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setFeature(String featureId, boolean state) throws XMLConfigurationException {
/* 389 */     this.fConfigUpdated = true;
/* 390 */     super.setFeature(featureId, state);
/*     */   }
/*     */ 
/*     */   
/*     */   public PropertyState getPropertyState(String propertyId) throws XMLConfigurationException {
/* 395 */     if ("http://apache.org/xml/properties/locale".equals(propertyId)) {
/* 396 */       return PropertyState.is(getLocale());
/*     */     }
/* 398 */     return super.getPropertyState(propertyId);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setProperty(String propertyId, Object value) throws XMLConfigurationException {
/* 403 */     this.fConfigUpdated = true;
/* 404 */     if ("http://apache.org/xml/properties/locale".equals(propertyId)) {
/* 405 */       setLocale((Locale)value);
/*     */     }
/* 407 */     super.setProperty(propertyId, value);
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
/*     */   public void setLocale(Locale locale) throws XNIException {
/* 419 */     super.setLocale(locale);
/* 420 */     this.fErrorReporter.setLocale(locale);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public FeatureState getFeatureState(String featureId) throws XMLConfigurationException {
/* 426 */     if (featureId.equals("http://apache.org/xml/features/internal/parser-settings")) {
/* 427 */       return FeatureState.is(this.fConfigUpdated);
/*     */     }
/* 429 */     return super.getFeatureState(featureId);
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
/*     */   
/*     */   public void setInputSource(XMLInputSource inputSource) throws XMLConfigurationException, IOException {
/* 458 */     this.fInputSource = inputSource;
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
/*     */   public boolean parse(boolean complete) throws XNIException, IOException {
/* 481 */     if (this.fInputSource != null) {
/*     */       
/*     */       try {
/* 484 */         reset();
/* 485 */         this.fScanner.setInputSource(this.fInputSource);
/* 486 */         this.fInputSource = null;
/*     */       }
/* 488 */       catch (XNIException ex) {
/*     */ 
/*     */         
/* 491 */         throw ex;
/*     */       }
/* 493 */       catch (IOException ex) {
/*     */ 
/*     */         
/* 496 */         throw ex;
/*     */       }
/* 498 */       catch (RuntimeException ex) {
/*     */ 
/*     */         
/* 501 */         throw ex;
/*     */       }
/* 503 */       catch (Exception ex) {
/*     */ 
/*     */         
/* 506 */         throw new XNIException(ex);
/*     */       } 
/*     */     }
/*     */     
/*     */     try {
/* 511 */       return this.fScanner.scanDocument(complete);
/*     */     }
/* 513 */     catch (XNIException ex) {
/*     */ 
/*     */       
/* 516 */       throw ex;
/*     */     }
/* 518 */     catch (IOException ex) {
/*     */ 
/*     */       
/* 521 */       throw ex;
/*     */     }
/* 523 */     catch (RuntimeException ex) {
/*     */ 
/*     */       
/* 526 */       throw ex;
/*     */     }
/* 528 */     catch (Exception ex) {
/*     */ 
/*     */       
/* 531 */       throw new XNIException(ex);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void cleanup() {
/* 542 */     this.fEntityManager.closeReaders();
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
/*     */   public void parse(XMLInputSource source) throws XNIException, IOException {
/* 559 */     if (this.fParseInProgress)
/*     */     {
/* 561 */       throw new XNIException("FWK005 parse may not be called while parsing.");
/*     */     }
/* 563 */     this.fParseInProgress = true;
/*     */     
/*     */     try {
/* 566 */       setInputSource(source);
/* 567 */       parse(true);
/*     */     }
/* 569 */     catch (XNIException ex) {
/*     */ 
/*     */       
/* 572 */       throw ex;
/*     */     }
/* 574 */     catch (IOException ex) {
/*     */ 
/*     */       
/* 577 */       throw ex;
/*     */     }
/* 579 */     catch (RuntimeException ex) {
/*     */ 
/*     */       
/* 582 */       throw ex;
/*     */     }
/* 584 */     catch (Exception ex) {
/*     */ 
/*     */       
/* 587 */       throw new XNIException(ex);
/*     */     } finally {
/*     */       
/* 590 */       this.fParseInProgress = false;
/*     */       
/* 592 */       cleanup();
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
/*     */   protected void reset() throws XNIException {
/* 608 */     if (this.fValidationManager != null) {
/* 609 */       this.fValidationManager.reset();
/*     */     }
/* 611 */     configurePipeline();
/* 612 */     super.reset();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void configurePipeline() {
/* 620 */     if (this.fFeatures.get("http://xml.org/sax/features/namespaces") == Boolean.TRUE) {
/* 621 */       if (this.fNamespaceScanner == null) {
/* 622 */         this.fNamespaceScanner = new XMLNSDocumentScannerImpl();
/* 623 */         addComponent(this.fNamespaceScanner);
/*     */       } 
/* 625 */       this.fProperties.put("http://apache.org/xml/properties/internal/document-scanner", this.fNamespaceScanner);
/* 626 */       this.fNamespaceScanner.setDTDValidator(null);
/* 627 */       this.fScanner = this.fNamespaceScanner;
/*     */     } else {
/*     */       
/* 630 */       if (this.fNonNSScanner == null) {
/* 631 */         this.fNonNSScanner = new XMLDocumentScannerImpl();
/* 632 */         addComponent(this.fNonNSScanner);
/*     */       } 
/* 634 */       this.fProperties.put("http://apache.org/xml/properties/internal/document-scanner", this.fNonNSScanner);
/* 635 */       this.fScanner = this.fNonNSScanner;
/*     */     } 
/*     */     
/* 638 */     this.fScanner.setDocumentHandler(this.fDocumentHandler);
/* 639 */     this.fLastComponent = this.fScanner;
/*     */     
/* 641 */     if (this.fDTDScanner != null) {
/* 642 */       this.fDTDScanner.setDTDHandler(this.fDTDHandler);
/* 643 */       this.fDTDScanner.setDTDContentModelHandler(this.fDTDContentModelHandler);
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
/*     */   protected FeatureState checkFeature(String featureId) throws XMLConfigurationException {
/* 670 */     if (featureId.startsWith("http://apache.org/xml/features/")) {
/* 671 */       int suffixLength = featureId.length() - "http://apache.org/xml/features/".length();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 679 */       if (suffixLength == "validation/dynamic".length() && featureId
/* 680 */         .endsWith("validation/dynamic")) {
/* 681 */         return FeatureState.RECOGNIZED;
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 686 */       if (suffixLength == "validation/default-attribute-values".length() && featureId
/* 687 */         .endsWith("validation/default-attribute-values"))
/*     */       {
/* 689 */         return FeatureState.NOT_SUPPORTED;
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 694 */       if (suffixLength == "validation/validate-content-models".length() && featureId
/* 695 */         .endsWith("validation/validate-content-models"))
/*     */       {
/* 697 */         return FeatureState.NOT_SUPPORTED;
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 702 */       if (suffixLength == "nonvalidating/load-dtd-grammar".length() && featureId
/* 703 */         .endsWith("nonvalidating/load-dtd-grammar")) {
/* 704 */         return FeatureState.RECOGNIZED;
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 709 */       if (suffixLength == "nonvalidating/load-external-dtd".length() && featureId
/* 710 */         .endsWith("nonvalidating/load-external-dtd")) {
/* 711 */         return FeatureState.RECOGNIZED;
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 717 */       if (suffixLength == "validation/validate-datatypes".length() && featureId
/* 718 */         .endsWith("validation/validate-datatypes")) {
/* 719 */         return FeatureState.NOT_SUPPORTED;
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 727 */     return super.checkFeature(featureId);
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
/*     */   protected PropertyState checkProperty(String propertyId) throws XMLConfigurationException {
/* 751 */     if (propertyId.startsWith("http://apache.org/xml/properties/")) {
/* 752 */       int suffixLength = propertyId.length() - "http://apache.org/xml/properties/".length();
/*     */       
/* 754 */       if (suffixLength == "internal/dtd-scanner".length() && propertyId
/* 755 */         .endsWith("internal/dtd-scanner")) {
/* 756 */         return PropertyState.RECOGNIZED;
/*     */       }
/*     */     } 
/*     */     
/* 760 */     if (propertyId.startsWith("http://java.sun.com/xml/jaxp/properties/")) {
/* 761 */       int suffixLength = propertyId.length() - "http://java.sun.com/xml/jaxp/properties/".length();
/*     */       
/* 763 */       if (suffixLength == "schemaSource".length() && propertyId
/* 764 */         .endsWith("schemaSource")) {
/* 765 */         return PropertyState.RECOGNIZED;
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 773 */     return super.checkProperty(propertyId);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected XMLEntityManager createEntityManager() {
/* 781 */     return new XMLEntityManager();
/*     */   }
/*     */ 
/*     */   
/*     */   protected XMLErrorReporter createErrorReporter() {
/* 786 */     return new XMLErrorReporter();
/*     */   }
/*     */ 
/*     */   
/*     */   protected XMLDocumentScanner createDocumentScanner() {
/* 791 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   protected XMLDTDScanner createDTDScanner() {
/* 796 */     return new XMLDTDScannerImpl();
/*     */   }
/*     */ 
/*     */   
/*     */   protected DTDDVFactory createDatatypeValidatorFactory() {
/* 801 */     return DTDDVFactory.getInstance();
/*     */   }
/*     */   protected ValidationManager createValidationManager() {
/* 804 */     return new ValidationManager();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xerces/internal/parsers/NonValidatingConfiguration.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */