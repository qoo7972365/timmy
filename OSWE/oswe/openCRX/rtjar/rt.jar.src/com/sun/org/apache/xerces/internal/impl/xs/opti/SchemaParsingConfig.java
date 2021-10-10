/*      */ package com.sun.org.apache.xerces.internal.impl.xs.opti;
/*      */ 
/*      */ import com.sun.org.apache.xerces.internal.impl.XML11DTDScannerImpl;
/*      */ import com.sun.org.apache.xerces.internal.impl.XML11NSDocumentScannerImpl;
/*      */ import com.sun.org.apache.xerces.internal.impl.XMLDTDScannerImpl;
/*      */ import com.sun.org.apache.xerces.internal.impl.XMLEntityHandler;
/*      */ import com.sun.org.apache.xerces.internal.impl.XMLEntityManager;
/*      */ import com.sun.org.apache.xerces.internal.impl.XMLErrorReporter;
/*      */ import com.sun.org.apache.xerces.internal.impl.XMLNSDocumentScannerImpl;
/*      */ import com.sun.org.apache.xerces.internal.impl.XMLVersionDetector;
/*      */ import com.sun.org.apache.xerces.internal.impl.dv.DTDDVFactory;
/*      */ import com.sun.org.apache.xerces.internal.impl.msg.XMLMessageFormatter;
/*      */ import com.sun.org.apache.xerces.internal.impl.validation.ValidationManager;
/*      */ import com.sun.org.apache.xerces.internal.impl.xs.XSMessageFormatter;
/*      */ import com.sun.org.apache.xerces.internal.parsers.BasicParserConfiguration;
/*      */ import com.sun.org.apache.xerces.internal.util.FeatureState;
/*      */ import com.sun.org.apache.xerces.internal.util.PropertyState;
/*      */ import com.sun.org.apache.xerces.internal.util.SymbolTable;
/*      */ import com.sun.org.apache.xerces.internal.xni.XMLLocator;
/*      */ import com.sun.org.apache.xerces.internal.xni.XNIException;
/*      */ import com.sun.org.apache.xerces.internal.xni.grammars.XMLGrammarPool;
/*      */ import com.sun.org.apache.xerces.internal.xni.parser.XMLComponent;
/*      */ import com.sun.org.apache.xerces.internal.xni.parser.XMLComponentManager;
/*      */ import com.sun.org.apache.xerces.internal.xni.parser.XMLConfigurationException;
/*      */ import com.sun.org.apache.xerces.internal.xni.parser.XMLDTDScanner;
/*      */ import com.sun.org.apache.xerces.internal.xni.parser.XMLDocumentScanner;
/*      */ import com.sun.org.apache.xerces.internal.xni.parser.XMLInputSource;
/*      */ import com.sun.org.apache.xerces.internal.xni.parser.XMLPullParserConfiguration;
/*      */ import java.io.IOException;
/*      */ import java.util.Locale;
/*      */ import jdk.xml.internal.JdkXmlUtils;
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
/*      */ public class SchemaParsingConfig
/*      */   extends BasicParserConfiguration
/*      */   implements XMLPullParserConfiguration
/*      */ {
/*      */   protected static final String XML11_DATATYPE_VALIDATOR_FACTORY = "com.sun.org.apache.xerces.internal.impl.dv.dtd.XML11DTDDVFactoryImpl";
/*      */   protected static final String WARN_ON_DUPLICATE_ATTDEF = "http://apache.org/xml/features/validation/warn-on-duplicate-attdef";
/*      */   protected static final String WARN_ON_UNDECLARED_ELEMDEF = "http://apache.org/xml/features/validation/warn-on-undeclared-elemdef";
/*      */   protected static final String ALLOW_JAVA_ENCODINGS = "http://apache.org/xml/features/allow-java-encodings";
/*      */   protected static final String CONTINUE_AFTER_FATAL_ERROR = "http://apache.org/xml/features/continue-after-fatal-error";
/*      */   protected static final String LOAD_EXTERNAL_DTD = "http://apache.org/xml/features/nonvalidating/load-external-dtd";
/*      */   protected static final String NOTIFY_BUILTIN_REFS = "http://apache.org/xml/features/scanner/notify-builtin-refs";
/*      */   protected static final String NOTIFY_CHAR_REFS = "http://apache.org/xml/features/scanner/notify-char-refs";
/*      */   protected static final String NORMALIZE_DATA = "http://apache.org/xml/features/validation/schema/normalized-value";
/*      */   protected static final String SCHEMA_ELEMENT_DEFAULT = "http://apache.org/xml/features/validation/schema/element-default";
/*      */   protected static final String GENERATE_SYNTHETIC_ANNOTATIONS = "http://apache.org/xml/features/generate-synthetic-annotations";
/*      */   protected static final String ERROR_REPORTER = "http://apache.org/xml/properties/internal/error-reporter";
/*      */   protected static final String ENTITY_MANAGER = "http://apache.org/xml/properties/internal/entity-manager";
/*      */   protected static final String DOCUMENT_SCANNER = "http://apache.org/xml/properties/internal/document-scanner";
/*      */   protected static final String DTD_SCANNER = "http://apache.org/xml/properties/internal/dtd-scanner";
/*      */   protected static final String XMLGRAMMAR_POOL = "http://apache.org/xml/properties/internal/grammar-pool";
/*      */   protected static final String DTD_VALIDATOR = "http://apache.org/xml/properties/internal/validator/dtd";
/*      */   protected static final String NAMESPACE_BINDER = "http://apache.org/xml/properties/internal/namespace-binder";
/*      */   protected static final String DATATYPE_VALIDATOR_FACTORY = "http://apache.org/xml/properties/internal/datatype-validator-factory";
/*      */   protected static final String VALIDATION_MANAGER = "http://apache.org/xml/properties/internal/validation-manager";
/*      */   protected static final String SCHEMA_VALIDATOR = "http://apache.org/xml/properties/internal/validator/schema";
/*      */   protected static final String LOCALE = "http://apache.org/xml/properties/locale";
/*      */   private static final boolean PRINT_EXCEPTION_STACK_TRACE = false;
/*      */   protected final DTDDVFactory fDatatypeValidatorFactory;
/*      */   protected final XMLNSDocumentScannerImpl fNamespaceScanner;
/*      */   protected final XMLDTDScannerImpl fDTDScanner;
/*  190 */   protected DTDDVFactory fXML11DatatypeFactory = null;
/*      */ 
/*      */   
/*  193 */   protected XML11NSDocumentScannerImpl fXML11NSDocScanner = null;
/*      */ 
/*      */   
/*  196 */   protected XML11DTDScannerImpl fXML11DTDScanner = null;
/*      */ 
/*      */ 
/*      */   
/*      */   protected DTDDVFactory fCurrentDVFactory;
/*      */ 
/*      */ 
/*      */   
/*      */   protected XMLDocumentScanner fCurrentScanner;
/*      */ 
/*      */ 
/*      */   
/*      */   protected XMLDTDScanner fCurrentDTDScanner;
/*      */ 
/*      */ 
/*      */   
/*      */   protected XMLGrammarPool fGrammarPool;
/*      */ 
/*      */ 
/*      */   
/*      */   protected final XMLVersionDetector fVersionDetector;
/*      */ 
/*      */ 
/*      */   
/*      */   protected final XMLErrorReporter fErrorReporter;
/*      */ 
/*      */ 
/*      */   
/*      */   protected final XMLEntityManager fEntityManager;
/*      */ 
/*      */ 
/*      */   
/*      */   protected XMLInputSource fInputSource;
/*      */ 
/*      */ 
/*      */   
/*      */   protected final ValidationManager fValidationManager;
/*      */ 
/*      */ 
/*      */   
/*      */   protected XMLLocator fLocator;
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean fParseInProgress = false;
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean fConfigUpdated = false;
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean f11Initialized = false;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public SchemaParsingConfig() {
/*  254 */     this((SymbolTable)null, (XMLGrammarPool)null, (XMLComponentManager)null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public SchemaParsingConfig(SymbolTable symbolTable) {
/*  263 */     this(symbolTable, (XMLGrammarPool)null, (XMLComponentManager)null);
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
/*      */   public SchemaParsingConfig(SymbolTable symbolTable, XMLGrammarPool grammarPool) {
/*  279 */     this(symbolTable, grammarPool, (XMLComponentManager)null);
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
/*      */   
/*      */   public SchemaParsingConfig(SymbolTable symbolTable, XMLGrammarPool grammarPool, XMLComponentManager parentSettings) {
/*  297 */     super(symbolTable, parentSettings);
/*      */ 
/*      */     
/*  300 */     String[] recognizedFeatures = { "http://apache.org/xml/features/internal/parser-settings", "http://apache.org/xml/features/validation/warn-on-duplicate-attdef", "http://apache.org/xml/features/validation/warn-on-undeclared-elemdef", "http://apache.org/xml/features/allow-java-encodings", "http://apache.org/xml/features/continue-after-fatal-error", "http://apache.org/xml/features/nonvalidating/load-external-dtd", "http://apache.org/xml/features/scanner/notify-builtin-refs", "http://apache.org/xml/features/scanner/notify-char-refs", "http://apache.org/xml/features/generate-synthetic-annotations", "jdk.xml.overrideDefaultParser" };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  307 */     addRecognizedFeatures(recognizedFeatures);
/*  308 */     this.fFeatures.put("http://apache.org/xml/features/internal/parser-settings", Boolean.TRUE);
/*      */     
/*  310 */     this.fFeatures.put("http://apache.org/xml/features/validation/warn-on-duplicate-attdef", Boolean.FALSE);
/*      */     
/*  312 */     this.fFeatures.put("http://apache.org/xml/features/validation/warn-on-undeclared-elemdef", Boolean.FALSE);
/*  313 */     this.fFeatures.put("http://apache.org/xml/features/allow-java-encodings", Boolean.FALSE);
/*  314 */     this.fFeatures.put("http://apache.org/xml/features/continue-after-fatal-error", Boolean.FALSE);
/*  315 */     this.fFeatures.put("http://apache.org/xml/features/nonvalidating/load-external-dtd", Boolean.TRUE);
/*  316 */     this.fFeatures.put("http://apache.org/xml/features/scanner/notify-builtin-refs", Boolean.FALSE);
/*  317 */     this.fFeatures.put("http://apache.org/xml/features/scanner/notify-char-refs", Boolean.FALSE);
/*  318 */     this.fFeatures.put("http://apache.org/xml/features/generate-synthetic-annotations", Boolean.FALSE);
/*  319 */     this.fFeatures.put("jdk.xml.overrideDefaultParser", Boolean.valueOf(JdkXmlUtils.OVERRIDE_PARSER_DEFAULT));
/*      */ 
/*      */     
/*  322 */     String[] recognizedProperties = { "http://apache.org/xml/properties/internal/error-reporter", "http://apache.org/xml/properties/internal/entity-manager", "http://apache.org/xml/properties/internal/document-scanner", "http://apache.org/xml/properties/internal/dtd-scanner", "http://apache.org/xml/properties/internal/validator/dtd", "http://apache.org/xml/properties/internal/namespace-binder", "http://apache.org/xml/properties/internal/grammar-pool", "http://apache.org/xml/properties/internal/datatype-validator-factory", "http://apache.org/xml/properties/internal/validation-manager", "http://apache.org/xml/features/generate-synthetic-annotations", "http://apache.org/xml/properties/locale" };
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
/*  335 */     addRecognizedProperties(recognizedProperties);
/*      */     
/*  337 */     this.fGrammarPool = grammarPool;
/*  338 */     if (this.fGrammarPool != null) {
/*  339 */       setProperty("http://apache.org/xml/properties/internal/grammar-pool", this.fGrammarPool);
/*      */     }
/*      */     
/*  342 */     this.fEntityManager = new XMLEntityManager();
/*  343 */     this.fProperties.put("http://apache.org/xml/properties/internal/entity-manager", this.fEntityManager);
/*  344 */     addComponent(this.fEntityManager);
/*      */     
/*  346 */     this.fErrorReporter = new XMLErrorReporter();
/*  347 */     this.fErrorReporter.setDocumentLocator(this.fEntityManager.getEntityScanner());
/*  348 */     this.fProperties.put("http://apache.org/xml/properties/internal/error-reporter", this.fErrorReporter);
/*  349 */     addComponent(this.fErrorReporter);
/*      */     
/*  351 */     this.fNamespaceScanner = new XMLNSDocumentScannerImpl();
/*  352 */     this.fProperties.put("http://apache.org/xml/properties/internal/document-scanner", this.fNamespaceScanner);
/*  353 */     addRecognizedParamsAndSetDefaults(this.fNamespaceScanner);
/*      */     
/*  355 */     this.fDTDScanner = new XMLDTDScannerImpl();
/*  356 */     this.fProperties.put("http://apache.org/xml/properties/internal/dtd-scanner", this.fDTDScanner);
/*  357 */     addRecognizedParamsAndSetDefaults(this.fDTDScanner);
/*      */     
/*  359 */     this.fDatatypeValidatorFactory = DTDDVFactory.getInstance();
/*  360 */     this.fProperties.put("http://apache.org/xml/properties/internal/datatype-validator-factory", this.fDatatypeValidatorFactory);
/*      */ 
/*      */     
/*  363 */     this.fValidationManager = new ValidationManager();
/*  364 */     this.fProperties.put("http://apache.org/xml/properties/internal/validation-manager", this.fValidationManager);
/*      */     
/*  366 */     this.fVersionDetector = new XMLVersionDetector();
/*      */ 
/*      */     
/*  369 */     if (this.fErrorReporter.getMessageFormatter("http://www.w3.org/TR/1998/REC-xml-19980210") == null) {
/*  370 */       XMLMessageFormatter xmft = new XMLMessageFormatter();
/*  371 */       this.fErrorReporter.putMessageFormatter("http://www.w3.org/TR/1998/REC-xml-19980210", xmft);
/*  372 */       this.fErrorReporter.putMessageFormatter("http://www.w3.org/TR/1999/REC-xml-names-19990114", xmft);
/*      */     } 
/*      */     
/*  375 */     if (this.fErrorReporter.getMessageFormatter("http://www.w3.org/TR/xml-schema-1") == null) {
/*  376 */       XSMessageFormatter xmft = new XSMessageFormatter();
/*  377 */       this.fErrorReporter.putMessageFormatter("http://www.w3.org/TR/xml-schema-1", xmft);
/*      */     } 
/*      */ 
/*      */     
/*      */     try {
/*  382 */       setLocale(Locale.getDefault());
/*      */     }
/*  384 */     catch (XNIException xNIException) {}
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public FeatureState getFeatureState(String featureId) throws XMLConfigurationException {
/*  410 */     if (featureId.equals("http://apache.org/xml/features/internal/parser-settings")) {
/*  411 */       return FeatureState.is(this.fConfigUpdated);
/*      */     }
/*  413 */     return super.getFeatureState(featureId);
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
/*      */ 
/*      */ 
/*      */   
/*      */   public void setFeature(String featureId, boolean state) throws XMLConfigurationException {
/*  433 */     this.fConfigUpdated = true;
/*      */ 
/*      */     
/*  436 */     this.fNamespaceScanner.setFeature(featureId, state);
/*  437 */     this.fDTDScanner.setFeature(featureId, state);
/*      */ 
/*      */     
/*  440 */     if (this.f11Initialized) {
/*      */       try {
/*  442 */         this.fXML11DTDScanner.setFeature(featureId, state);
/*      */       
/*      */       }
/*  445 */       catch (Exception exception) {}
/*      */       try {
/*  447 */         this.fXML11NSDocScanner.setFeature(featureId, state);
/*      */       
/*      */       }
/*  450 */       catch (Exception exception) {}
/*      */     } 
/*      */ 
/*      */     
/*  454 */     super.setFeature(featureId, state);
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
/*      */   
/*      */   public PropertyState getPropertyState(String propertyId) throws XMLConfigurationException {
/*  472 */     if ("http://apache.org/xml/properties/locale".equals(propertyId)) {
/*  473 */       return PropertyState.is(getLocale());
/*      */     }
/*  475 */     return super.getPropertyState(propertyId);
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
/*      */   public void setProperty(String propertyId, Object value) throws XMLConfigurationException {
/*  487 */     this.fConfigUpdated = true;
/*  488 */     if ("http://apache.org/xml/properties/locale".equals(propertyId)) {
/*  489 */       setLocale((Locale)value);
/*      */     }
/*      */ 
/*      */     
/*  493 */     this.fNamespaceScanner.setProperty(propertyId, value);
/*  494 */     this.fDTDScanner.setProperty(propertyId, value);
/*      */ 
/*      */     
/*  497 */     if (this.f11Initialized) {
/*      */       try {
/*  499 */         this.fXML11DTDScanner.setProperty(propertyId, value);
/*      */       
/*      */       }
/*  502 */       catch (Exception exception) {}
/*      */       try {
/*  504 */         this.fXML11NSDocScanner.setProperty(propertyId, value);
/*      */       
/*      */       }
/*  507 */       catch (Exception exception) {}
/*      */     } 
/*      */ 
/*      */     
/*  511 */     super.setProperty(propertyId, value);
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
/*      */   public void setLocale(Locale locale) throws XNIException {
/*  524 */     super.setLocale(locale);
/*  525 */     this.fErrorReporter.setLocale(locale);
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
/*      */   public void setInputSource(XMLInputSource inputSource) throws XMLConfigurationException, IOException {
/*  554 */     this.fInputSource = inputSource;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean parse(boolean complete) throws XNIException, IOException {
/*  577 */     if (this.fInputSource != null) {
/*      */       try {
/*  579 */         this.fValidationManager.reset();
/*  580 */         this.fVersionDetector.reset(this);
/*  581 */         reset();
/*      */         
/*  583 */         short version = this.fVersionDetector.determineDocVersion(this.fInputSource);
/*      */         
/*  585 */         if (version == 1) {
/*  586 */           configurePipeline();
/*  587 */           resetXML10();
/*      */         
/*      */         }
/*  590 */         else if (version == 2) {
/*  591 */           initXML11Components();
/*  592 */           configureXML11Pipeline();
/*  593 */           resetXML11();
/*      */         }
/*      */         else {
/*      */           
/*  597 */           return false;
/*      */         } 
/*      */ 
/*      */         
/*  601 */         this.fConfigUpdated = false;
/*      */ 
/*      */         
/*  604 */         this.fVersionDetector.startDocumentParsing((XMLEntityHandler)this.fCurrentScanner, version);
/*  605 */         this.fInputSource = null;
/*      */       }
/*  607 */       catch (XNIException ex) {
/*      */ 
/*      */         
/*  610 */         throw ex;
/*      */       }
/*  612 */       catch (IOException ex) {
/*      */ 
/*      */         
/*  615 */         throw ex;
/*      */       }
/*  617 */       catch (RuntimeException ex) {
/*      */ 
/*      */         
/*  620 */         throw ex;
/*      */       }
/*  622 */       catch (Exception ex) {
/*      */ 
/*      */         
/*  625 */         throw new XNIException(ex);
/*      */       } 
/*      */     }
/*      */     
/*      */     try {
/*  630 */       return this.fCurrentScanner.scanDocument(complete);
/*      */     }
/*  632 */     catch (XNIException ex) {
/*      */ 
/*      */       
/*  635 */       throw ex;
/*      */     }
/*  637 */     catch (IOException ex) {
/*      */ 
/*      */       
/*  640 */       throw ex;
/*      */     }
/*  642 */     catch (RuntimeException ex) {
/*      */ 
/*      */       
/*  645 */       throw ex;
/*      */     }
/*  647 */     catch (Exception ex) {
/*      */ 
/*      */       
/*  650 */       throw new XNIException(ex);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void cleanup() {
/*  661 */     this.fEntityManager.closeReaders();
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
/*      */   public void parse(XMLInputSource source) throws XNIException, IOException {
/*  678 */     if (this.fParseInProgress)
/*      */     {
/*  680 */       throw new XNIException("FWK005 parse may not be called while parsing.");
/*      */     }
/*  682 */     this.fParseInProgress = true;
/*      */     
/*      */     try {
/*  685 */       setInputSource(source);
/*  686 */       parse(true);
/*      */     }
/*  688 */     catch (XNIException ex) {
/*      */ 
/*      */       
/*  691 */       throw ex;
/*      */     }
/*  693 */     catch (IOException ex) {
/*      */ 
/*      */       
/*  696 */       throw ex;
/*      */     }
/*  698 */     catch (RuntimeException ex) {
/*      */ 
/*      */       
/*  701 */       throw ex;
/*      */     }
/*  703 */     catch (Exception ex) {
/*      */ 
/*      */       
/*  706 */       throw new XNIException(ex);
/*      */     } finally {
/*      */       
/*  709 */       this.fParseInProgress = false;
/*      */       
/*  711 */       cleanup();
/*      */     } 
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
/*      */   public void reset() throws XNIException {
/*  728 */     super.reset();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void configurePipeline() {
/*  735 */     if (this.fCurrentDVFactory != this.fDatatypeValidatorFactory) {
/*  736 */       this.fCurrentDVFactory = this.fDatatypeValidatorFactory;
/*      */       
/*  738 */       setProperty("http://apache.org/xml/properties/internal/datatype-validator-factory", this.fCurrentDVFactory);
/*      */     } 
/*      */ 
/*      */     
/*  742 */     if (this.fCurrentScanner != this.fNamespaceScanner) {
/*  743 */       this.fCurrentScanner = this.fNamespaceScanner;
/*  744 */       setProperty("http://apache.org/xml/properties/internal/document-scanner", this.fCurrentScanner);
/*      */     } 
/*  746 */     this.fNamespaceScanner.setDocumentHandler(this.fDocumentHandler);
/*  747 */     if (this.fDocumentHandler != null) {
/*  748 */       this.fDocumentHandler.setDocumentSource(this.fNamespaceScanner);
/*      */     }
/*  750 */     this.fLastComponent = this.fNamespaceScanner;
/*      */ 
/*      */     
/*  753 */     if (this.fCurrentDTDScanner != this.fDTDScanner) {
/*  754 */       this.fCurrentDTDScanner = this.fDTDScanner;
/*  755 */       setProperty("http://apache.org/xml/properties/internal/dtd-scanner", this.fCurrentDTDScanner);
/*      */     } 
/*  757 */     this.fDTDScanner.setDTDHandler(this.fDTDHandler);
/*  758 */     if (this.fDTDHandler != null) {
/*  759 */       this.fDTDHandler.setDTDSource(this.fDTDScanner);
/*      */     }
/*  761 */     this.fDTDScanner.setDTDContentModelHandler(this.fDTDContentModelHandler);
/*  762 */     if (this.fDTDContentModelHandler != null) {
/*  763 */       this.fDTDContentModelHandler.setDTDContentModelSource(this.fDTDScanner);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void configureXML11Pipeline() {
/*  771 */     if (this.fCurrentDVFactory != this.fXML11DatatypeFactory) {
/*  772 */       this.fCurrentDVFactory = this.fXML11DatatypeFactory;
/*      */       
/*  774 */       setProperty("http://apache.org/xml/properties/internal/datatype-validator-factory", this.fCurrentDVFactory);
/*      */     } 
/*      */ 
/*      */     
/*  778 */     if (this.fCurrentScanner != this.fXML11NSDocScanner) {
/*  779 */       this.fCurrentScanner = this.fXML11NSDocScanner;
/*  780 */       setProperty("http://apache.org/xml/properties/internal/document-scanner", this.fCurrentScanner);
/*      */     } 
/*  782 */     this.fXML11NSDocScanner.setDocumentHandler(this.fDocumentHandler);
/*  783 */     if (this.fDocumentHandler != null) {
/*  784 */       this.fDocumentHandler.setDocumentSource(this.fXML11NSDocScanner);
/*      */     }
/*  786 */     this.fLastComponent = this.fXML11NSDocScanner;
/*      */ 
/*      */     
/*  789 */     if (this.fCurrentDTDScanner != this.fXML11DTDScanner) {
/*  790 */       this.fCurrentDTDScanner = this.fXML11DTDScanner;
/*  791 */       setProperty("http://apache.org/xml/properties/internal/dtd-scanner", this.fCurrentDTDScanner);
/*      */     } 
/*  793 */     this.fXML11DTDScanner.setDTDHandler(this.fDTDHandler);
/*  794 */     if (this.fDTDHandler != null) {
/*  795 */       this.fDTDHandler.setDTDSource(this.fXML11DTDScanner);
/*      */     }
/*  797 */     this.fXML11DTDScanner.setDTDContentModelHandler(this.fDTDContentModelHandler);
/*  798 */     if (this.fDTDContentModelHandler != null) {
/*  799 */       this.fDTDContentModelHandler.setDTDContentModelSource(this.fXML11DTDScanner);
/*      */     }
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected FeatureState checkFeature(String featureId) throws XMLConfigurationException {
/*  825 */     if (featureId.startsWith("http://apache.org/xml/features/")) {
/*  826 */       int suffixLength = featureId.length() - "http://apache.org/xml/features/".length();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  834 */       if (suffixLength == "validation/dynamic".length() && featureId
/*  835 */         .endsWith("validation/dynamic")) {
/*  836 */         return FeatureState.RECOGNIZED;
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*  841 */       if (suffixLength == "validation/default-attribute-values".length() && featureId
/*  842 */         .endsWith("validation/default-attribute-values"))
/*      */       {
/*  844 */         return FeatureState.NOT_SUPPORTED;
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*  849 */       if (suffixLength == "validation/validate-content-models".length() && featureId
/*  850 */         .endsWith("validation/validate-content-models"))
/*      */       {
/*  852 */         return FeatureState.NOT_SUPPORTED;
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*  857 */       if (suffixLength == "nonvalidating/load-dtd-grammar".length() && featureId
/*  858 */         .endsWith("nonvalidating/load-dtd-grammar")) {
/*  859 */         return FeatureState.RECOGNIZED;
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*  864 */       if (suffixLength == "nonvalidating/load-external-dtd".length() && featureId
/*  865 */         .endsWith("nonvalidating/load-external-dtd")) {
/*  866 */         return FeatureState.RECOGNIZED;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  872 */       if (suffixLength == "validation/validate-datatypes".length() && featureId
/*  873 */         .endsWith("validation/validate-datatypes")) {
/*  874 */         return FeatureState.NOT_SUPPORTED;
/*      */       }
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  882 */     return super.checkFeature(featureId);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected PropertyState checkProperty(String propertyId) throws XMLConfigurationException {
/*  906 */     if (propertyId.startsWith("http://apache.org/xml/properties/")) {
/*  907 */       int suffixLength = propertyId.length() - "http://apache.org/xml/properties/".length();
/*      */       
/*  909 */       if (suffixLength == "internal/dtd-scanner".length() && propertyId
/*  910 */         .endsWith("internal/dtd-scanner")) {
/*  911 */         return PropertyState.RECOGNIZED;
/*      */       }
/*      */     } 
/*      */     
/*  915 */     if (propertyId.startsWith("http://java.sun.com/xml/jaxp/properties/")) {
/*  916 */       int suffixLength = propertyId.length() - "http://java.sun.com/xml/jaxp/properties/".length();
/*      */       
/*  918 */       if (suffixLength == "schemaSource".length() && propertyId
/*  919 */         .endsWith("schemaSource")) {
/*  920 */         return PropertyState.RECOGNIZED;
/*      */       }
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  928 */     return super.checkProperty(propertyId);
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
/*      */   private void addRecognizedParamsAndSetDefaults(XMLComponent component) {
/*  944 */     String[] recognizedFeatures = component.getRecognizedFeatures();
/*  945 */     addRecognizedFeatures(recognizedFeatures);
/*      */ 
/*      */     
/*  948 */     String[] recognizedProperties = component.getRecognizedProperties();
/*  949 */     addRecognizedProperties(recognizedProperties);
/*      */ 
/*      */     
/*  952 */     if (recognizedFeatures != null) {
/*  953 */       for (int i = 0; i < recognizedFeatures.length; i++) {
/*  954 */         String featureId = recognizedFeatures[i];
/*  955 */         Boolean state = component.getFeatureDefault(featureId);
/*  956 */         if (state != null)
/*      */         {
/*  958 */           if (!this.fFeatures.containsKey(featureId)) {
/*  959 */             this.fFeatures.put(featureId, state);
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  964 */             this.fConfigUpdated = true;
/*      */           } 
/*      */         }
/*      */       } 
/*      */     }
/*  969 */     if (recognizedProperties != null) {
/*  970 */       for (int i = 0; i < recognizedProperties.length; i++) {
/*  971 */         String propertyId = recognizedProperties[i];
/*  972 */         Object value = component.getPropertyDefault(propertyId);
/*  973 */         if (value != null)
/*      */         {
/*  975 */           if (!this.fProperties.containsKey(propertyId)) {
/*  976 */             this.fProperties.put(propertyId, value);
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  981 */             this.fConfigUpdated = true;
/*      */           } 
/*      */         }
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final void resetXML10() throws XNIException {
/*  993 */     this.fNamespaceScanner.reset(this);
/*  994 */     this.fDTDScanner.reset(this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final void resetXML11() throws XNIException {
/* 1002 */     this.fXML11NSDocScanner.reset(this);
/* 1003 */     this.fXML11DTDScanner.reset(this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void resetNodePool() {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void initXML11Components() {
/* 1017 */     if (!this.f11Initialized) {
/*      */       
/* 1019 */       this.fXML11DatatypeFactory = DTDDVFactory.getInstance("com.sun.org.apache.xerces.internal.impl.dv.dtd.XML11DTDDVFactoryImpl");
/*      */ 
/*      */       
/* 1022 */       this.fXML11DTDScanner = new XML11DTDScannerImpl();
/* 1023 */       addRecognizedParamsAndSetDefaults(this.fXML11DTDScanner);
/*      */ 
/*      */       
/* 1026 */       this.fXML11NSDocScanner = new XML11NSDocumentScannerImpl();
/* 1027 */       addRecognizedParamsAndSetDefaults(this.fXML11NSDocScanner);
/*      */       
/* 1029 */       this.f11Initialized = true;
/*      */     } 
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xerces/internal/impl/xs/opti/SchemaParsingConfig.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */