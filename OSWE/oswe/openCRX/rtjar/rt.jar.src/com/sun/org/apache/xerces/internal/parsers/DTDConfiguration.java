/*     */ package com.sun.org.apache.xerces.internal.parsers;
/*     */ 
/*     */ import com.sun.org.apache.xerces.internal.impl.XMLDTDScannerImpl;
/*     */ import com.sun.org.apache.xerces.internal.impl.XMLDocumentScannerImpl;
/*     */ import com.sun.org.apache.xerces.internal.impl.XMLEntityManager;
/*     */ import com.sun.org.apache.xerces.internal.impl.XMLErrorReporter;
/*     */ import com.sun.org.apache.xerces.internal.impl.XMLNamespaceBinder;
/*     */ import com.sun.org.apache.xerces.internal.impl.dtd.XMLDTDProcessor;
/*     */ import com.sun.org.apache.xerces.internal.impl.dtd.XMLDTDValidator;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DTDConfiguration
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
/*     */   protected static final String ERROR_REPORTER = "http://apache.org/xml/properties/internal/error-reporter";
/*     */   protected static final String ENTITY_MANAGER = "http://apache.org/xml/properties/internal/entity-manager";
/*     */   protected static final String DOCUMENT_SCANNER = "http://apache.org/xml/properties/internal/document-scanner";
/*     */   protected static final String DTD_SCANNER = "http://apache.org/xml/properties/internal/dtd-scanner";
/*     */   protected static final String XMLGRAMMAR_POOL = "http://apache.org/xml/properties/internal/grammar-pool";
/*     */   protected static final String DTD_PROCESSOR = "http://apache.org/xml/properties/internal/dtd-processor";
/*     */   protected static final String DTD_VALIDATOR = "http://apache.org/xml/properties/internal/validator/dtd";
/*     */   protected static final String NAMESPACE_BINDER = "http://apache.org/xml/properties/internal/namespace-binder";
/*     */   protected static final String DATATYPE_VALIDATOR_FACTORY = "http://apache.org/xml/properties/internal/datatype-validator-factory";
/*     */   protected static final String VALIDATION_MANAGER = "http://apache.org/xml/properties/internal/validation-manager";
/*     */   protected static final String JAXP_SCHEMA_LANGUAGE = "http://java.sun.com/xml/jaxp/properties/schemaLanguage";
/*     */   protected static final String JAXP_SCHEMA_SOURCE = "http://java.sun.com/xml/jaxp/properties/schemaSource";
/*     */   protected static final String LOCALE = "http://apache.org/xml/properties/locale";
/*     */   protected static final String XML_SECURITY_PROPERTY_MANAGER = "http://www.oracle.com/xml/jaxp/properties/xmlSecurityPropertyManager";
/*     */   private static final String SECURITY_MANAGER = "http://apache.org/xml/properties/security-manager";
/*     */   protected static final boolean PRINT_EXCEPTION_STACK_TRACE = false;
/*     */   protected XMLGrammarPool fGrammarPool;
/*     */   protected DTDDVFactory fDatatypeValidatorFactory;
/*     */   protected XMLErrorReporter fErrorReporter;
/*     */   protected XMLEntityManager fEntityManager;
/*     */   protected XMLDocumentScanner fScanner;
/*     */   protected XMLInputSource fInputSource;
/*     */   protected XMLDTDScanner fDTDScanner;
/*     */   protected XMLDTDProcessor fDTDProcessor;
/*     */   protected XMLDTDValidator fDTDValidator;
/*     */   protected XMLNamespaceBinder fNamespaceBinder;
/*     */   protected ValidationManager fValidationManager;
/*     */   protected XMLLocator fLocator;
/*     */   protected boolean fParseInProgress = false;
/*     */   
/*     */   public DTDConfiguration() {
/* 256 */     this((SymbolTable)null, (XMLGrammarPool)null, (XMLComponentManager)null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DTDConfiguration(SymbolTable symbolTable) {
/* 265 */     this(symbolTable, (XMLGrammarPool)null, (XMLComponentManager)null);
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
/*     */   public DTDConfiguration(SymbolTable symbolTable, XMLGrammarPool grammarPool) {
/* 281 */     this(symbolTable, grammarPool, (XMLComponentManager)null);
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
/*     */   public DTDConfiguration(SymbolTable symbolTable, XMLGrammarPool grammarPool, XMLComponentManager parentSettings) {
/* 299 */     super(symbolTable, parentSettings);
/*     */ 
/*     */     
/* 302 */     String[] recognizedFeatures = { "http://apache.org/xml/features/continue-after-fatal-error", "http://apache.org/xml/features/nonvalidating/load-external-dtd", "jdk.xml.overrideDefaultParser" };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 313 */     addRecognizedFeatures(recognizedFeatures);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 319 */     setFeature("http://apache.org/xml/features/continue-after-fatal-error", false);
/* 320 */     setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", true);
/*     */ 
/*     */ 
/*     */     
/* 324 */     this.fFeatures.put("jdk.xml.overrideDefaultParser", Boolean.valueOf(JdkXmlUtils.OVERRIDE_PARSER_DEFAULT));
/*     */ 
/*     */     
/* 327 */     String[] recognizedProperties = { "http://apache.org/xml/properties/internal/error-reporter", "http://apache.org/xml/properties/internal/entity-manager", "http://apache.org/xml/properties/internal/document-scanner", "http://apache.org/xml/properties/internal/dtd-scanner", "http://apache.org/xml/properties/internal/dtd-processor", "http://apache.org/xml/properties/internal/validator/dtd", "http://apache.org/xml/properties/internal/namespace-binder", "http://apache.org/xml/properties/internal/grammar-pool", "http://apache.org/xml/properties/internal/datatype-validator-factory", "http://apache.org/xml/properties/internal/validation-manager", "http://java.sun.com/xml/jaxp/properties/schemaSource", "http://java.sun.com/xml/jaxp/properties/schemaLanguage", "http://apache.org/xml/properties/locale", "http://apache.org/xml/properties/security-manager", "http://www.oracle.com/xml/jaxp/properties/xmlSecurityPropertyManager" };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 344 */     addRecognizedProperties(recognizedProperties);
/*     */     
/* 346 */     this.fGrammarPool = grammarPool;
/* 347 */     if (this.fGrammarPool != null) {
/* 348 */       setProperty("http://apache.org/xml/properties/internal/grammar-pool", this.fGrammarPool);
/*     */     }
/*     */     
/* 351 */     this.fEntityManager = createEntityManager();
/* 352 */     setProperty("http://apache.org/xml/properties/internal/entity-manager", this.fEntityManager);
/* 353 */     addComponent(this.fEntityManager);
/*     */     
/* 355 */     this.fErrorReporter = createErrorReporter();
/* 356 */     this.fErrorReporter.setDocumentLocator(this.fEntityManager.getEntityScanner());
/* 357 */     setProperty("http://apache.org/xml/properties/internal/error-reporter", this.fErrorReporter);
/* 358 */     addComponent(this.fErrorReporter);
/*     */     
/* 360 */     this.fScanner = createDocumentScanner();
/* 361 */     setProperty("http://apache.org/xml/properties/internal/document-scanner", this.fScanner);
/* 362 */     if (this.fScanner instanceof XMLComponent) {
/* 363 */       addComponent((XMLComponent)this.fScanner);
/*     */     }
/*     */     
/* 366 */     this.fDTDScanner = createDTDScanner();
/* 367 */     if (this.fDTDScanner != null) {
/* 368 */       setProperty("http://apache.org/xml/properties/internal/dtd-scanner", this.fDTDScanner);
/* 369 */       if (this.fDTDScanner instanceof XMLComponent) {
/* 370 */         addComponent((XMLComponent)this.fDTDScanner);
/*     */       }
/*     */     } 
/*     */     
/* 374 */     this.fDTDProcessor = createDTDProcessor();
/* 375 */     if (this.fDTDProcessor != null) {
/* 376 */       setProperty("http://apache.org/xml/properties/internal/dtd-processor", this.fDTDProcessor);
/* 377 */       if (this.fDTDProcessor instanceof XMLComponent) {
/* 378 */         addComponent(this.fDTDProcessor);
/*     */       }
/*     */     } 
/*     */     
/* 382 */     this.fDTDValidator = createDTDValidator();
/* 383 */     if (this.fDTDValidator != null) {
/* 384 */       setProperty("http://apache.org/xml/properties/internal/validator/dtd", this.fDTDValidator);
/* 385 */       addComponent(this.fDTDValidator);
/*     */     } 
/*     */     
/* 388 */     this.fNamespaceBinder = createNamespaceBinder();
/* 389 */     if (this.fNamespaceBinder != null) {
/* 390 */       setProperty("http://apache.org/xml/properties/internal/namespace-binder", this.fNamespaceBinder);
/* 391 */       addComponent(this.fNamespaceBinder);
/*     */     } 
/*     */     
/* 394 */     this.fDatatypeValidatorFactory = createDatatypeValidatorFactory();
/* 395 */     if (this.fDatatypeValidatorFactory != null) {
/* 396 */       setProperty("http://apache.org/xml/properties/internal/datatype-validator-factory", this.fDatatypeValidatorFactory);
/*     */     }
/*     */     
/* 399 */     this.fValidationManager = createValidationManager();
/*     */     
/* 401 */     if (this.fValidationManager != null) {
/* 402 */       setProperty("http://apache.org/xml/properties/internal/validation-manager", this.fValidationManager);
/*     */     }
/*     */     
/* 405 */     if (this.fErrorReporter.getMessageFormatter("http://www.w3.org/TR/1998/REC-xml-19980210") == null) {
/* 406 */       XMLMessageFormatter xmft = new XMLMessageFormatter();
/* 407 */       this.fErrorReporter.putMessageFormatter("http://www.w3.org/TR/1998/REC-xml-19980210", xmft);
/* 408 */       this.fErrorReporter.putMessageFormatter("http://www.w3.org/TR/1999/REC-xml-names-19990114", xmft);
/*     */     } 
/*     */ 
/*     */     
/*     */     try {
/* 413 */       setLocale(Locale.getDefault());
/*     */     }
/* 415 */     catch (XNIException xNIException) {}
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 420 */     setProperty("http://www.oracle.com/xml/jaxp/properties/xmlSecurityPropertyManager", new XMLSecurityPropertyManager());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PropertyState getPropertyState(String propertyId) throws XMLConfigurationException {
/* 429 */     if ("http://apache.org/xml/properties/locale".equals(propertyId)) {
/* 430 */       return PropertyState.is(getLocale());
/*     */     }
/* 432 */     return super.getPropertyState(propertyId);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setProperty(String propertyId, Object value) throws XMLConfigurationException {
/* 437 */     if ("http://apache.org/xml/properties/locale".equals(propertyId)) {
/* 438 */       setLocale((Locale)value);
/*     */     }
/* 440 */     super.setProperty(propertyId, value);
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
/* 452 */     super.setLocale(locale);
/* 453 */     this.fErrorReporter.setLocale(locale);
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
/* 482 */     this.fInputSource = inputSource;
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
/* 505 */     if (this.fInputSource != null) {
/*     */       
/*     */       try {
/* 508 */         reset();
/* 509 */         this.fScanner.setInputSource(this.fInputSource);
/* 510 */         this.fInputSource = null;
/*     */       }
/* 512 */       catch (XNIException ex) {
/*     */ 
/*     */         
/* 515 */         throw ex;
/*     */       }
/* 517 */       catch (IOException ex) {
/*     */ 
/*     */         
/* 520 */         throw ex;
/*     */       }
/* 522 */       catch (RuntimeException ex) {
/*     */ 
/*     */         
/* 525 */         throw ex;
/*     */       }
/* 527 */       catch (Exception ex) {
/*     */ 
/*     */         
/* 530 */         throw new XNIException(ex);
/*     */       } 
/*     */     }
/*     */     
/*     */     try {
/* 535 */       return this.fScanner.scanDocument(complete);
/*     */     }
/* 537 */     catch (XNIException ex) {
/*     */ 
/*     */       
/* 540 */       throw ex;
/*     */     }
/* 542 */     catch (IOException ex) {
/*     */ 
/*     */       
/* 545 */       throw ex;
/*     */     }
/* 547 */     catch (RuntimeException ex) {
/*     */ 
/*     */       
/* 550 */       throw ex;
/*     */     }
/* 552 */     catch (Exception ex) {
/*     */ 
/*     */       
/* 555 */       throw new XNIException(ex);
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
/* 566 */     this.fEntityManager.closeReaders();
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
/* 583 */     if (this.fParseInProgress)
/*     */     {
/* 585 */       throw new XNIException("FWK005 parse may not be called while parsing.");
/*     */     }
/* 587 */     this.fParseInProgress = true;
/*     */     
/*     */     try {
/* 590 */       setInputSource(source);
/* 591 */       parse(true);
/*     */     }
/* 593 */     catch (XNIException ex) {
/*     */ 
/*     */       
/* 596 */       throw ex;
/*     */     }
/* 598 */     catch (IOException ex) {
/*     */ 
/*     */       
/* 601 */       throw ex;
/*     */     }
/* 603 */     catch (RuntimeException ex) {
/*     */ 
/*     */       
/* 606 */       throw ex;
/*     */     }
/* 608 */     catch (Exception ex) {
/*     */ 
/*     */       
/* 611 */       throw new XNIException(ex);
/*     */     } finally {
/*     */       
/* 614 */       this.fParseInProgress = false;
/*     */       
/* 616 */       cleanup();
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
/* 632 */     if (this.fValidationManager != null) {
/* 633 */       this.fValidationManager.reset();
/*     */     }
/* 635 */     configurePipeline();
/* 636 */     super.reset();
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
/*     */   protected void configurePipeline() {
/* 650 */     if (this.fDTDValidator != null) {
/* 651 */       this.fScanner.setDocumentHandler(this.fDTDValidator);
/* 652 */       if (this.fFeatures.get("http://xml.org/sax/features/namespaces") == Boolean.TRUE) {
/*     */ 
/*     */         
/* 655 */         this.fDTDValidator.setDocumentHandler(this.fNamespaceBinder);
/* 656 */         this.fDTDValidator.setDocumentSource(this.fScanner);
/* 657 */         this.fNamespaceBinder.setDocumentHandler(this.fDocumentHandler);
/* 658 */         this.fNamespaceBinder.setDocumentSource(this.fDTDValidator);
/* 659 */         this.fLastComponent = this.fNamespaceBinder;
/*     */       } else {
/*     */         
/* 662 */         this.fDTDValidator.setDocumentHandler(this.fDocumentHandler);
/* 663 */         this.fDTDValidator.setDocumentSource(this.fScanner);
/* 664 */         this.fLastComponent = this.fDTDValidator;
/*     */       }
/*     */     
/*     */     }
/* 668 */     else if (this.fFeatures.get("http://xml.org/sax/features/namespaces") == Boolean.TRUE) {
/* 669 */       this.fScanner.setDocumentHandler(this.fNamespaceBinder);
/* 670 */       this.fNamespaceBinder.setDocumentHandler(this.fDocumentHandler);
/* 671 */       this.fNamespaceBinder.setDocumentSource(this.fScanner);
/* 672 */       this.fLastComponent = this.fNamespaceBinder;
/*     */     } else {
/*     */       
/* 675 */       this.fScanner.setDocumentHandler(this.fDocumentHandler);
/* 676 */       this.fLastComponent = this.fScanner;
/*     */     } 
/*     */ 
/*     */     
/* 680 */     configureDTDPipeline();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void configureDTDPipeline() {
/* 686 */     if (this.fDTDScanner != null) {
/* 687 */       this.fProperties.put("http://apache.org/xml/properties/internal/dtd-scanner", this.fDTDScanner);
/* 688 */       if (this.fDTDProcessor != null) {
/* 689 */         this.fProperties.put("http://apache.org/xml/properties/internal/dtd-processor", this.fDTDProcessor);
/* 690 */         this.fDTDScanner.setDTDHandler(this.fDTDProcessor);
/* 691 */         this.fDTDProcessor.setDTDSource(this.fDTDScanner);
/* 692 */         this.fDTDProcessor.setDTDHandler(this.fDTDHandler);
/* 693 */         if (this.fDTDHandler != null) {
/* 694 */           this.fDTDHandler.setDTDSource(this.fDTDProcessor);
/*     */         }
/*     */         
/* 697 */         this.fDTDScanner.setDTDContentModelHandler(this.fDTDProcessor);
/* 698 */         this.fDTDProcessor.setDTDContentModelSource(this.fDTDScanner);
/* 699 */         this.fDTDProcessor.setDTDContentModelHandler(this.fDTDContentModelHandler);
/* 700 */         if (this.fDTDContentModelHandler != null) {
/* 701 */           this.fDTDContentModelHandler.setDTDContentModelSource(this.fDTDProcessor);
/*     */         }
/*     */       } else {
/*     */         
/* 705 */         this.fDTDScanner.setDTDHandler(this.fDTDHandler);
/* 706 */         if (this.fDTDHandler != null) {
/* 707 */           this.fDTDHandler.setDTDSource(this.fDTDScanner);
/*     */         }
/* 709 */         this.fDTDScanner.setDTDContentModelHandler(this.fDTDContentModelHandler);
/* 710 */         if (this.fDTDContentModelHandler != null) {
/* 711 */           this.fDTDContentModelHandler.setDTDContentModelSource(this.fDTDScanner);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/* 740 */     if (featureId.startsWith("http://apache.org/xml/features/")) {
/* 741 */       int suffixLength = featureId.length() - "http://apache.org/xml/features/".length();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 749 */       if (suffixLength == "validation/dynamic".length() && featureId
/* 750 */         .endsWith("validation/dynamic")) {
/* 751 */         return FeatureState.RECOGNIZED;
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 757 */       if (suffixLength == "validation/default-attribute-values".length() && featureId
/* 758 */         .endsWith("validation/default-attribute-values"))
/*     */       {
/* 760 */         return FeatureState.NOT_SUPPORTED;
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 765 */       if (suffixLength == "validation/validate-content-models".length() && featureId
/* 766 */         .endsWith("validation/validate-content-models"))
/*     */       {
/* 768 */         return FeatureState.NOT_SUPPORTED;
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 773 */       if (suffixLength == "nonvalidating/load-dtd-grammar".length() && featureId
/* 774 */         .endsWith("nonvalidating/load-dtd-grammar")) {
/* 775 */         return FeatureState.RECOGNIZED;
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 780 */       if (suffixLength == "nonvalidating/load-external-dtd".length() && featureId
/* 781 */         .endsWith("nonvalidating/load-external-dtd")) {
/* 782 */         return FeatureState.RECOGNIZED;
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 788 */       if (suffixLength == "validation/validate-datatypes".length() && featureId
/* 789 */         .endsWith("validation/validate-datatypes")) {
/* 790 */         return FeatureState.NOT_SUPPORTED;
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 798 */     return super.checkFeature(featureId);
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
/* 822 */     if (propertyId.startsWith("http://apache.org/xml/properties/")) {
/* 823 */       int suffixLength = propertyId.length() - "http://apache.org/xml/properties/".length();
/*     */       
/* 825 */       if (suffixLength == "internal/dtd-scanner".length() && propertyId
/* 826 */         .endsWith("internal/dtd-scanner")) {
/* 827 */         return PropertyState.RECOGNIZED;
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 835 */     return super.checkProperty(propertyId);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected XMLEntityManager createEntityManager() {
/* 843 */     return new XMLEntityManager();
/*     */   }
/*     */ 
/*     */   
/*     */   protected XMLErrorReporter createErrorReporter() {
/* 848 */     return new XMLErrorReporter();
/*     */   }
/*     */ 
/*     */   
/*     */   protected XMLDocumentScanner createDocumentScanner() {
/* 853 */     return new XMLDocumentScannerImpl();
/*     */   }
/*     */ 
/*     */   
/*     */   protected XMLDTDScanner createDTDScanner() {
/* 858 */     return new XMLDTDScannerImpl();
/*     */   }
/*     */ 
/*     */   
/*     */   protected XMLDTDProcessor createDTDProcessor() {
/* 863 */     return new XMLDTDProcessor();
/*     */   }
/*     */ 
/*     */   
/*     */   protected XMLDTDValidator createDTDValidator() {
/* 868 */     return new XMLDTDValidator();
/*     */   }
/*     */ 
/*     */   
/*     */   protected XMLNamespaceBinder createNamespaceBinder() {
/* 873 */     return new XMLNamespaceBinder();
/*     */   }
/*     */ 
/*     */   
/*     */   protected DTDDVFactory createDatatypeValidatorFactory() {
/* 878 */     return DTDDVFactory.getInstance();
/*     */   }
/*     */   protected ValidationManager createValidationManager() {
/* 881 */     return new ValidationManager();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xerces/internal/parsers/DTDConfiguration.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */