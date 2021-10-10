/*      */ package com.sun.org.apache.xerces.internal.parsers;
/*      */ 
/*      */ import com.sun.org.apache.xerces.internal.impl.XML11DTDScannerImpl;
/*      */ import com.sun.org.apache.xerces.internal.impl.XML11DocumentScannerImpl;
/*      */ import com.sun.org.apache.xerces.internal.impl.XML11NSDocumentScannerImpl;
/*      */ import com.sun.org.apache.xerces.internal.impl.XMLDTDScannerImpl;
/*      */ import com.sun.org.apache.xerces.internal.impl.XMLDocumentScannerImpl;
/*      */ import com.sun.org.apache.xerces.internal.impl.XMLEntityHandler;
/*      */ import com.sun.org.apache.xerces.internal.impl.XMLEntityManager;
/*      */ import com.sun.org.apache.xerces.internal.impl.XMLErrorReporter;
/*      */ import com.sun.org.apache.xerces.internal.impl.XMLNSDocumentScannerImpl;
/*      */ import com.sun.org.apache.xerces.internal.impl.XMLVersionDetector;
/*      */ import com.sun.org.apache.xerces.internal.impl.dtd.XML11DTDProcessor;
/*      */ import com.sun.org.apache.xerces.internal.impl.dtd.XML11DTDValidator;
/*      */ import com.sun.org.apache.xerces.internal.impl.dtd.XML11NSDTDValidator;
/*      */ import com.sun.org.apache.xerces.internal.impl.dtd.XMLDTDProcessor;
/*      */ import com.sun.org.apache.xerces.internal.impl.dtd.XMLDTDValidator;
/*      */ import com.sun.org.apache.xerces.internal.impl.dtd.XMLNSDTDValidator;
/*      */ import com.sun.org.apache.xerces.internal.impl.dv.DTDDVFactory;
/*      */ import com.sun.org.apache.xerces.internal.impl.msg.XMLMessageFormatter;
/*      */ import com.sun.org.apache.xerces.internal.impl.validation.ValidationManager;
/*      */ import com.sun.org.apache.xerces.internal.impl.xs.XMLSchemaValidator;
/*      */ import com.sun.org.apache.xerces.internal.impl.xs.XSMessageFormatter;
/*      */ import com.sun.org.apache.xerces.internal.util.FeatureState;
/*      */ import com.sun.org.apache.xerces.internal.util.ParserConfigurationSettings;
/*      */ import com.sun.org.apache.xerces.internal.util.PropertyState;
/*      */ import com.sun.org.apache.xerces.internal.util.SymbolTable;
/*      */ import com.sun.org.apache.xerces.internal.xni.XMLDTDContentModelHandler;
/*      */ import com.sun.org.apache.xerces.internal.xni.XMLDTDHandler;
/*      */ import com.sun.org.apache.xerces.internal.xni.XMLDocumentHandler;
/*      */ import com.sun.org.apache.xerces.internal.xni.XMLLocator;
/*      */ import com.sun.org.apache.xerces.internal.xni.XNIException;
/*      */ import com.sun.org.apache.xerces.internal.xni.grammars.XMLGrammarPool;
/*      */ import com.sun.org.apache.xerces.internal.xni.parser.XMLComponent;
/*      */ import com.sun.org.apache.xerces.internal.xni.parser.XMLComponentManager;
/*      */ import com.sun.org.apache.xerces.internal.xni.parser.XMLConfigurationException;
/*      */ import com.sun.org.apache.xerces.internal.xni.parser.XMLDTDScanner;
/*      */ import com.sun.org.apache.xerces.internal.xni.parser.XMLDocumentScanner;
/*      */ import com.sun.org.apache.xerces.internal.xni.parser.XMLDocumentSource;
/*      */ import com.sun.org.apache.xerces.internal.xni.parser.XMLEntityResolver;
/*      */ import com.sun.org.apache.xerces.internal.xni.parser.XMLErrorHandler;
/*      */ import com.sun.org.apache.xerces.internal.xni.parser.XMLInputSource;
/*      */ import com.sun.org.apache.xerces.internal.xni.parser.XMLPullParserConfiguration;
/*      */ import java.io.IOException;
/*      */ import java.util.ArrayList;
/*      */ import java.util.HashMap;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class XML11Configuration
/*      */   extends ParserConfigurationSettings
/*      */   implements XMLPullParserConfiguration, XML11Configurable
/*      */ {
/*      */   protected static final String XML11_DATATYPE_VALIDATOR_FACTORY = "com.sun.org.apache.xerces.internal.impl.dv.dtd.XML11DTDDVFactoryImpl";
/*      */   protected static final String WARN_ON_DUPLICATE_ATTDEF = "http://apache.org/xml/features/validation/warn-on-duplicate-attdef";
/*      */   protected static final String WARN_ON_DUPLICATE_ENTITYDEF = "http://apache.org/xml/features/warn-on-duplicate-entitydef";
/*      */   protected static final String WARN_ON_UNDECLARED_ELEMDEF = "http://apache.org/xml/features/validation/warn-on-undeclared-elemdef";
/*      */   protected static final String ALLOW_JAVA_ENCODINGS = "http://apache.org/xml/features/allow-java-encodings";
/*      */   protected static final String CONTINUE_AFTER_FATAL_ERROR = "http://apache.org/xml/features/continue-after-fatal-error";
/*      */   protected static final String LOAD_EXTERNAL_DTD = "http://apache.org/xml/features/nonvalidating/load-external-dtd";
/*      */   protected static final String NOTIFY_BUILTIN_REFS = "http://apache.org/xml/features/scanner/notify-builtin-refs";
/*      */   protected static final String NOTIFY_CHAR_REFS = "http://apache.org/xml/features/scanner/notify-char-refs";
/*      */   protected static final String NORMALIZE_DATA = "http://apache.org/xml/features/validation/schema/normalized-value";
/*      */   protected static final String SCHEMA_ELEMENT_DEFAULT = "http://apache.org/xml/features/validation/schema/element-default";
/*      */   protected static final String SCHEMA_AUGMENT_PSVI = "http://apache.org/xml/features/validation/schema/augment-psvi";
/*      */   protected static final String XMLSCHEMA_VALIDATION = "http://apache.org/xml/features/validation/schema";
/*      */   protected static final String XMLSCHEMA_FULL_CHECKING = "http://apache.org/xml/features/validation/schema-full-checking";
/*      */   protected static final String GENERATE_SYNTHETIC_ANNOTATIONS = "http://apache.org/xml/features/generate-synthetic-annotations";
/*      */   protected static final String VALIDATE_ANNOTATIONS = "http://apache.org/xml/features/validate-annotations";
/*      */   protected static final String HONOUR_ALL_SCHEMALOCATIONS = "http://apache.org/xml/features/honour-all-schemaLocations";
/*      */   protected static final String NAMESPACE_GROWTH = "http://apache.org/xml/features/namespace-growth";
/*      */   protected static final String TOLERATE_DUPLICATES = "http://apache.org/xml/features/internal/tolerate-duplicates";
/*      */   protected static final String USE_GRAMMAR_POOL_ONLY = "http://apache.org/xml/features/internal/validation/schema/use-grammar-pool-only";
/*      */   protected static final String VALIDATION = "http://xml.org/sax/features/validation";
/*      */   protected static final String NAMESPACES = "http://xml.org/sax/features/namespaces";
/*      */   protected static final String EXTERNAL_GENERAL_ENTITIES = "http://xml.org/sax/features/external-general-entities";
/*      */   protected static final String EXTERNAL_PARAMETER_ENTITIES = "http://xml.org/sax/features/external-parameter-entities";
/*      */   protected static final String XML_STRING = "http://xml.org/sax/properties/xml-string";
/*      */   protected static final String SYMBOL_TABLE = "http://apache.org/xml/properties/internal/symbol-table";
/*      */   protected static final String ERROR_HANDLER = "http://apache.org/xml/properties/internal/error-handler";
/*      */   protected static final String ENTITY_RESOLVER = "http://apache.org/xml/properties/internal/entity-resolver";
/*      */   protected static final String SCHEMA_VALIDATOR = "http://apache.org/xml/properties/internal/validator/schema";
/*      */   protected static final String SCHEMA_LOCATION = "http://apache.org/xml/properties/schema/external-schemaLocation";
/*      */   protected static final String SCHEMA_NONS_LOCATION = "http://apache.org/xml/properties/schema/external-noNamespaceSchemaLocation";
/*      */   protected static final String ERROR_REPORTER = "http://apache.org/xml/properties/internal/error-reporter";
/*      */   protected static final String ENTITY_MANAGER = "http://apache.org/xml/properties/internal/entity-manager";
/*      */   protected static final String DOCUMENT_SCANNER = "http://apache.org/xml/properties/internal/document-scanner";
/*      */   protected static final String DTD_SCANNER = "http://apache.org/xml/properties/internal/dtd-scanner";
/*      */   protected static final String XMLGRAMMAR_POOL = "http://apache.org/xml/properties/internal/grammar-pool";
/*      */   protected static final String DTD_PROCESSOR = "http://apache.org/xml/properties/internal/dtd-processor";
/*      */   protected static final String DTD_VALIDATOR = "http://apache.org/xml/properties/internal/validator/dtd";
/*      */   protected static final String NAMESPACE_BINDER = "http://apache.org/xml/properties/internal/namespace-binder";
/*      */   protected static final String DATATYPE_VALIDATOR_FACTORY = "http://apache.org/xml/properties/internal/datatype-validator-factory";
/*      */   protected static final String VALIDATION_MANAGER = "http://apache.org/xml/properties/internal/validation-manager";
/*      */   protected static final String JAXP_SCHEMA_LANGUAGE = "http://java.sun.com/xml/jaxp/properties/schemaLanguage";
/*      */   protected static final String JAXP_SCHEMA_SOURCE = "http://java.sun.com/xml/jaxp/properties/schemaSource";
/*      */   protected static final String LOCALE = "http://apache.org/xml/properties/locale";
/*      */   protected static final String SCHEMA_DV_FACTORY = "http://apache.org/xml/properties/internal/validation/schema/dv-factory";
/*      */   private static final String XML_SECURITY_PROPERTY_MANAGER = "http://www.oracle.com/xml/jaxp/properties/xmlSecurityPropertyManager";
/*      */   private static final String SECURITY_MANAGER = "http://apache.org/xml/properties/security-manager";
/*      */   protected static final boolean PRINT_EXCEPTION_STACK_TRACE = false;
/*      */   protected SymbolTable fSymbolTable;
/*      */   protected XMLInputSource fInputSource;
/*      */   protected ValidationManager fValidationManager;
/*      */   protected XMLVersionDetector fVersionDetector;
/*      */   protected XMLLocator fLocator;
/*      */   protected Locale fLocale;
/*      */   protected ArrayList<XMLComponent> fComponents;
/*  298 */   protected ArrayList<XMLComponent> fXML11Components = null;
/*      */ 
/*      */   
/*  301 */   protected ArrayList<XMLComponent> fCommonComponents = null;
/*      */ 
/*      */ 
/*      */   
/*      */   protected XMLDocumentHandler fDocumentHandler;
/*      */ 
/*      */ 
/*      */   
/*      */   protected XMLDTDHandler fDTDHandler;
/*      */ 
/*      */ 
/*      */   
/*      */   protected XMLDTDContentModelHandler fDTDContentModelHandler;
/*      */ 
/*      */ 
/*      */   
/*      */   protected XMLDocumentSource fLastComponent;
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
/*      */   protected DTDDVFactory fDatatypeValidatorFactory;
/*      */ 
/*      */ 
/*      */   
/*      */   protected XMLNSDocumentScannerImpl fNamespaceScanner;
/*      */ 
/*      */ 
/*      */   
/*      */   protected XMLDocumentScannerImpl fNonNSScanner;
/*      */ 
/*      */   
/*      */   protected XMLDTDValidator fDTDValidator;
/*      */ 
/*      */   
/*      */   protected XMLDTDValidator fNonNSDTDValidator;
/*      */ 
/*      */   
/*      */   protected XMLDTDScanner fDTDScanner;
/*      */ 
/*      */   
/*      */   protected XMLDTDProcessor fDTDProcessor;
/*      */ 
/*      */   
/*  352 */   protected DTDDVFactory fXML11DatatypeFactory = null;
/*      */ 
/*      */   
/*  355 */   protected XML11NSDocumentScannerImpl fXML11NSDocScanner = null;
/*      */ 
/*      */   
/*  358 */   protected XML11DocumentScannerImpl fXML11DocScanner = null;
/*      */ 
/*      */   
/*  361 */   protected XML11NSDTDValidator fXML11NSDTDValidator = null;
/*      */ 
/*      */   
/*  364 */   protected XML11DTDValidator fXML11DTDValidator = null;
/*      */ 
/*      */   
/*  367 */   protected XML11DTDScannerImpl fXML11DTDScanner = null;
/*      */   
/*  369 */   protected XML11DTDProcessor fXML11DTDProcessor = null;
/*      */ 
/*      */ 
/*      */   
/*      */   protected XMLGrammarPool fGrammarPool;
/*      */ 
/*      */ 
/*      */   
/*      */   protected XMLErrorReporter fErrorReporter;
/*      */ 
/*      */ 
/*      */   
/*      */   protected XMLEntityManager fEntityManager;
/*      */ 
/*      */ 
/*      */   
/*      */   protected XMLSchemaValidator fSchemaValidator;
/*      */ 
/*      */ 
/*      */   
/*      */   protected XMLDocumentScanner fCurrentScanner;
/*      */ 
/*      */   
/*      */   protected DTDDVFactory fCurrentDVFactory;
/*      */ 
/*      */   
/*      */   protected XMLDTDScanner fCurrentDTDScanner;
/*      */ 
/*      */   
/*      */   private boolean f11Initialized = false;
/*      */ 
/*      */ 
/*      */   
/*      */   public XML11Configuration() {
/*  403 */     this((SymbolTable)null, (XMLGrammarPool)null, (XMLComponentManager)null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public XML11Configuration(SymbolTable symbolTable) {
/*  412 */     this(symbolTable, (XMLGrammarPool)null, (XMLComponentManager)null);
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
/*      */   public XML11Configuration(SymbolTable symbolTable, XMLGrammarPool grammarPool) {
/*  427 */     this(symbolTable, grammarPool, (XMLComponentManager)null);
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
/*      */   public XML11Configuration(SymbolTable symbolTable, XMLGrammarPool grammarPool, XMLComponentManager parentSettings) {
/*  447 */     super(parentSettings);
/*      */ 
/*      */ 
/*      */     
/*  451 */     this.fComponents = new ArrayList<>();
/*      */     
/*  453 */     this.fXML11Components = new ArrayList<>();
/*      */     
/*  455 */     this.fCommonComponents = new ArrayList<>();
/*      */ 
/*      */     
/*  458 */     this.fFeatures = new HashMap<>();
/*  459 */     this.fProperties = new HashMap<>();
/*      */ 
/*      */     
/*  462 */     String[] recognizedFeatures = { "http://apache.org/xml/features/continue-after-fatal-error", "http://apache.org/xml/features/nonvalidating/load-external-dtd", "http://xml.org/sax/features/validation", "http://xml.org/sax/features/namespaces", "http://apache.org/xml/features/validation/schema/normalized-value", "http://apache.org/xml/features/validation/schema/element-default", "http://apache.org/xml/features/validation/schema/augment-psvi", "http://apache.org/xml/features/generate-synthetic-annotations", "http://apache.org/xml/features/validate-annotations", "http://apache.org/xml/features/honour-all-schemaLocations", "http://apache.org/xml/features/namespace-growth", "http://apache.org/xml/features/internal/tolerate-duplicates", "http://apache.org/xml/features/internal/validation/schema/use-grammar-pool-only", "http://apache.org/xml/features/validation/schema", "http://apache.org/xml/features/validation/schema-full-checking", "http://xml.org/sax/features/external-general-entities", "http://xml.org/sax/features/external-parameter-entities", "http://apache.org/xml/features/internal/parser-settings", "http://javax.xml.XMLConstants/feature/secure-processing", "jdk.xml.overrideDefaultParser" };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  483 */     addRecognizedFeatures(recognizedFeatures);
/*      */     
/*  485 */     this.fFeatures.put("http://xml.org/sax/features/validation", Boolean.FALSE);
/*  486 */     this.fFeatures.put("http://xml.org/sax/features/namespaces", Boolean.TRUE);
/*  487 */     this.fFeatures.put("http://xml.org/sax/features/external-general-entities", Boolean.TRUE);
/*  488 */     this.fFeatures.put("http://xml.org/sax/features/external-parameter-entities", Boolean.TRUE);
/*  489 */     this.fFeatures.put("http://apache.org/xml/features/continue-after-fatal-error", Boolean.FALSE);
/*  490 */     this.fFeatures.put("http://apache.org/xml/features/nonvalidating/load-external-dtd", Boolean.TRUE);
/*  491 */     this.fFeatures.put("http://apache.org/xml/features/validation/schema/element-default", Boolean.TRUE);
/*  492 */     this.fFeatures.put("http://apache.org/xml/features/validation/schema/normalized-value", Boolean.TRUE);
/*  493 */     this.fFeatures.put("http://apache.org/xml/features/validation/schema/augment-psvi", Boolean.TRUE);
/*  494 */     this.fFeatures.put("http://apache.org/xml/features/generate-synthetic-annotations", Boolean.FALSE);
/*  495 */     this.fFeatures.put("http://apache.org/xml/features/validate-annotations", Boolean.FALSE);
/*  496 */     this.fFeatures.put("http://apache.org/xml/features/honour-all-schemaLocations", Boolean.FALSE);
/*  497 */     this.fFeatures.put("http://apache.org/xml/features/namespace-growth", Boolean.FALSE);
/*  498 */     this.fFeatures.put("http://apache.org/xml/features/internal/tolerate-duplicates", Boolean.FALSE);
/*  499 */     this.fFeatures.put("http://apache.org/xml/features/internal/validation/schema/use-grammar-pool-only", Boolean.FALSE);
/*  500 */     this.fFeatures.put("http://apache.org/xml/features/internal/parser-settings", Boolean.TRUE);
/*  501 */     this.fFeatures.put("http://javax.xml.XMLConstants/feature/secure-processing", Boolean.TRUE);
/*  502 */     this.fFeatures.put("jdk.xml.overrideDefaultParser", Boolean.valueOf(JdkXmlUtils.OVERRIDE_PARSER_DEFAULT));
/*      */ 
/*      */     
/*  505 */     String[] recognizedProperties = { "http://apache.org/xml/properties/internal/symbol-table", "http://apache.org/xml/properties/internal/error-handler", "http://apache.org/xml/properties/internal/entity-resolver", "http://apache.org/xml/properties/internal/error-reporter", "http://apache.org/xml/properties/internal/entity-manager", "http://apache.org/xml/properties/internal/document-scanner", "http://apache.org/xml/properties/internal/dtd-scanner", "http://apache.org/xml/properties/internal/dtd-processor", "http://apache.org/xml/properties/internal/validator/dtd", "http://apache.org/xml/properties/internal/datatype-validator-factory", "http://apache.org/xml/properties/internal/validation-manager", "http://apache.org/xml/properties/internal/validator/schema", "http://xml.org/sax/properties/xml-string", "http://apache.org/xml/properties/internal/grammar-pool", "http://java.sun.com/xml/jaxp/properties/schemaSource", "http://java.sun.com/xml/jaxp/properties/schemaLanguage", "http://apache.org/xml/properties/schema/external-schemaLocation", "http://apache.org/xml/properties/schema/external-noNamespaceSchemaLocation", "http://apache.org/xml/properties/locale", "http://apache.org/xml/properties/internal/validation/schema/dv-factory", "http://apache.org/xml/properties/security-manager", "http://www.oracle.com/xml/jaxp/properties/xmlSecurityPropertyManager" };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  534 */     addRecognizedProperties(recognizedProperties);
/*      */     
/*  536 */     if (symbolTable == null) {
/*  537 */       symbolTable = new SymbolTable();
/*      */     }
/*  539 */     this.fSymbolTable = symbolTable;
/*  540 */     this.fProperties.put("http://apache.org/xml/properties/internal/symbol-table", this.fSymbolTable);
/*      */     
/*  542 */     this.fGrammarPool = grammarPool;
/*  543 */     if (this.fGrammarPool != null) {
/*  544 */       this.fProperties.put("http://apache.org/xml/properties/internal/grammar-pool", this.fGrammarPool);
/*      */     }
/*      */     
/*  547 */     this.fEntityManager = new XMLEntityManager();
/*  548 */     this.fProperties.put("http://apache.org/xml/properties/internal/entity-manager", this.fEntityManager);
/*  549 */     addCommonComponent(this.fEntityManager);
/*      */     
/*  551 */     this.fErrorReporter = new XMLErrorReporter();
/*  552 */     this.fErrorReporter.setDocumentLocator(this.fEntityManager.getEntityScanner());
/*  553 */     this.fProperties.put("http://apache.org/xml/properties/internal/error-reporter", this.fErrorReporter);
/*  554 */     addCommonComponent(this.fErrorReporter);
/*      */     
/*  556 */     this.fNamespaceScanner = new XMLNSDocumentScannerImpl();
/*  557 */     this.fProperties.put("http://apache.org/xml/properties/internal/document-scanner", this.fNamespaceScanner);
/*  558 */     addComponent(this.fNamespaceScanner);
/*      */     
/*  560 */     this.fDTDScanner = new XMLDTDScannerImpl();
/*  561 */     this.fProperties.put("http://apache.org/xml/properties/internal/dtd-scanner", this.fDTDScanner);
/*  562 */     addComponent((XMLComponent)this.fDTDScanner);
/*      */     
/*  564 */     this.fDTDProcessor = new XMLDTDProcessor();
/*  565 */     this.fProperties.put("http://apache.org/xml/properties/internal/dtd-processor", this.fDTDProcessor);
/*  566 */     addComponent(this.fDTDProcessor);
/*      */     
/*  568 */     this.fDTDValidator = new XMLNSDTDValidator();
/*  569 */     this.fProperties.put("http://apache.org/xml/properties/internal/validator/dtd", this.fDTDValidator);
/*  570 */     addComponent(this.fDTDValidator);
/*      */     
/*  572 */     this.fDatatypeValidatorFactory = DTDDVFactory.getInstance();
/*  573 */     this.fProperties.put("http://apache.org/xml/properties/internal/datatype-validator-factory", this.fDatatypeValidatorFactory);
/*      */     
/*  575 */     this.fValidationManager = new ValidationManager();
/*  576 */     this.fProperties.put("http://apache.org/xml/properties/internal/validation-manager", this.fValidationManager);
/*      */     
/*  578 */     this.fVersionDetector = new XMLVersionDetector();
/*      */ 
/*      */     
/*  581 */     if (this.fErrorReporter.getMessageFormatter("http://www.w3.org/TR/1998/REC-xml-19980210") == null) {
/*  582 */       XMLMessageFormatter xmft = new XMLMessageFormatter();
/*  583 */       this.fErrorReporter.putMessageFormatter("http://www.w3.org/TR/1998/REC-xml-19980210", xmft);
/*  584 */       this.fErrorReporter.putMessageFormatter("http://www.w3.org/TR/1999/REC-xml-names-19990114", xmft);
/*      */     } 
/*      */ 
/*      */     
/*      */     try {
/*  589 */       setLocale(Locale.getDefault());
/*  590 */     } catch (XNIException xNIException) {}
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  595 */     this.fConfigUpdated = false;
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
/*      */   public void setInputSource(XMLInputSource inputSource) throws XMLConfigurationException, IOException {
/*  622 */     this.fInputSource = inputSource;
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
/*  635 */     this.fLocale = locale;
/*  636 */     this.fErrorReporter.setLocale(locale);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setDocumentHandler(XMLDocumentHandler documentHandler) {
/*  645 */     this.fDocumentHandler = documentHandler;
/*  646 */     if (this.fLastComponent != null) {
/*  647 */       this.fLastComponent.setDocumentHandler(this.fDocumentHandler);
/*  648 */       if (this.fDocumentHandler != null) {
/*  649 */         this.fDocumentHandler.setDocumentSource(this.fLastComponent);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public XMLDocumentHandler getDocumentHandler() {
/*  656 */     return this.fDocumentHandler;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setDTDHandler(XMLDTDHandler dtdHandler) {
/*  665 */     this.fDTDHandler = dtdHandler;
/*      */   }
/*      */ 
/*      */   
/*      */   public XMLDTDHandler getDTDHandler() {
/*  670 */     return this.fDTDHandler;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setDTDContentModelHandler(XMLDTDContentModelHandler handler) {
/*  679 */     this.fDTDContentModelHandler = handler;
/*      */   }
/*      */ 
/*      */   
/*      */   public XMLDTDContentModelHandler getDTDContentModelHandler() {
/*  684 */     return this.fDTDContentModelHandler;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setEntityResolver(XMLEntityResolver resolver) {
/*  695 */     this.fProperties.put("http://apache.org/xml/properties/internal/entity-resolver", resolver);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public XMLEntityResolver getEntityResolver() {
/*  706 */     return (XMLEntityResolver)this.fProperties.get("http://apache.org/xml/properties/internal/entity-resolver");
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
/*      */   public void setErrorHandler(XMLErrorHandler errorHandler) {
/*  728 */     this.fProperties.put("http://apache.org/xml/properties/internal/error-handler", errorHandler);
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
/*      */   public XMLErrorHandler getErrorHandler() {
/*  740 */     return (XMLErrorHandler)this.fProperties.get("http://apache.org/xml/properties/internal/error-handler");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void cleanup() {
/*  750 */     this.fEntityManager.closeReaders();
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
/*      */   public void parse(XMLInputSource source) throws XNIException, IOException {
/*  763 */     if (this.fParseInProgress)
/*      */     {
/*  765 */       throw new XNIException("FWK005 parse may not be called while parsing.");
/*      */     }
/*  767 */     this.fParseInProgress = true;
/*      */     
/*      */     try {
/*  770 */       setInputSource(source);
/*  771 */       parse(true);
/*  772 */     } catch (XNIException ex) {
/*      */ 
/*      */       
/*  775 */       throw ex;
/*  776 */     } catch (IOException ex) {
/*      */ 
/*      */       
/*  779 */       throw ex;
/*  780 */     } catch (RuntimeException ex) {
/*      */ 
/*      */       
/*  783 */       throw ex;
/*  784 */     } catch (Exception ex) {
/*      */ 
/*      */       
/*  787 */       throw new XNIException(ex);
/*      */     } finally {
/*  789 */       this.fParseInProgress = false;
/*      */       
/*  791 */       cleanup();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean parse(boolean complete) throws XNIException, IOException {
/*  799 */     if (this.fInputSource != null) {
/*      */       try {
/*  801 */         this.fValidationManager.reset();
/*  802 */         this.fVersionDetector.reset(this);
/*  803 */         this.fConfigUpdated = true;
/*  804 */         resetCommon();
/*      */         
/*  806 */         short version = this.fVersionDetector.determineDocVersion(this.fInputSource);
/*  807 */         if (version == 2) {
/*  808 */           initXML11Components();
/*  809 */           configureXML11Pipeline();
/*  810 */           resetXML11();
/*      */         } else {
/*  812 */           configurePipeline();
/*  813 */           reset();
/*      */         } 
/*      */ 
/*      */         
/*  817 */         this.fConfigUpdated = false;
/*      */ 
/*      */         
/*  820 */         this.fVersionDetector.startDocumentParsing((XMLEntityHandler)this.fCurrentScanner, version);
/*  821 */         this.fInputSource = null;
/*  822 */       } catch (XNIException ex) {
/*      */ 
/*      */         
/*  825 */         throw ex;
/*  826 */       } catch (IOException ex) {
/*      */ 
/*      */         
/*  829 */         throw ex;
/*  830 */       } catch (RuntimeException ex) {
/*      */ 
/*      */         
/*  833 */         throw ex;
/*  834 */       } catch (Exception ex) {
/*      */ 
/*      */         
/*  837 */         throw new XNIException(ex);
/*      */       } 
/*      */     }
/*      */     
/*      */     try {
/*  842 */       return this.fCurrentScanner.scanDocument(complete);
/*  843 */     } catch (XNIException ex) {
/*      */ 
/*      */       
/*  846 */       throw ex;
/*  847 */     } catch (IOException ex) {
/*      */ 
/*      */       
/*  850 */       throw ex;
/*  851 */     } catch (RuntimeException ex) {
/*      */ 
/*      */       
/*  854 */       throw ex;
/*  855 */     } catch (Exception ex) {
/*      */ 
/*      */       
/*  858 */       throw new XNIException(ex);
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
/*      */   public FeatureState getFeatureState(String featureId) throws XMLConfigurationException {
/*  878 */     if (featureId.equals("http://apache.org/xml/features/internal/parser-settings")) {
/*  879 */       return FeatureState.is(this.fConfigUpdated);
/*      */     }
/*  881 */     return super.getFeatureState(featureId);
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
/*      */   public void setFeature(String featureId, boolean state) throws XMLConfigurationException {
/*  900 */     this.fConfigUpdated = true;
/*      */     
/*  902 */     int count = this.fComponents.size(); int i;
/*  903 */     for (i = 0; i < count; i++) {
/*  904 */       XMLComponent c = this.fComponents.get(i);
/*  905 */       c.setFeature(featureId, state);
/*      */     } 
/*      */     
/*  908 */     count = this.fCommonComponents.size();
/*  909 */     for (i = 0; i < count; i++) {
/*  910 */       XMLComponent c = this.fCommonComponents.get(i);
/*  911 */       c.setFeature(featureId, state);
/*      */     } 
/*      */ 
/*      */     
/*  915 */     count = this.fXML11Components.size();
/*  916 */     for (i = 0; i < count; i++) {
/*  917 */       XMLComponent c = this.fXML11Components.get(i);
/*      */       try {
/*  919 */         c.setFeature(featureId, state);
/*      */       }
/*  921 */       catch (Exception exception) {}
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  926 */     super.setFeature(featureId, state);
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
/*  944 */     if ("http://apache.org/xml/properties/locale".equals(propertyId)) {
/*  945 */       return PropertyState.is(getLocale());
/*      */     }
/*  947 */     return super.getPropertyState(propertyId);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setProperty(String propertyId, Object value) throws XMLConfigurationException {
/*  958 */     this.fConfigUpdated = true;
/*  959 */     if ("http://apache.org/xml/properties/locale".equals(propertyId)) {
/*  960 */       setLocale((Locale)value);
/*      */     }
/*      */     
/*  963 */     int count = this.fComponents.size(); int i;
/*  964 */     for (i = 0; i < count; i++) {
/*  965 */       XMLComponent c = this.fComponents.get(i);
/*  966 */       c.setProperty(propertyId, value);
/*      */     } 
/*      */     
/*  969 */     count = this.fCommonComponents.size();
/*  970 */     for (i = 0; i < count; i++) {
/*  971 */       XMLComponent c = this.fCommonComponents.get(i);
/*  972 */       c.setProperty(propertyId, value);
/*      */     } 
/*      */     
/*  975 */     count = this.fXML11Components.size();
/*  976 */     for (i = 0; i < count; i++) {
/*  977 */       XMLComponent c = this.fXML11Components.get(i);
/*      */       try {
/*  979 */         c.setProperty(propertyId, value);
/*      */       }
/*  981 */       catch (Exception exception) {}
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  987 */     super.setProperty(propertyId, value);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Locale getLocale() {
/*  994 */     return this.fLocale;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void reset() throws XNIException {
/* 1001 */     int count = this.fComponents.size();
/* 1002 */     for (int i = 0; i < count; i++) {
/* 1003 */       XMLComponent c = this.fComponents.get(i);
/* 1004 */       c.reset(this);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void resetCommon() throws XNIException {
/* 1014 */     int count = this.fCommonComponents.size();
/* 1015 */     for (int i = 0; i < count; i++) {
/* 1016 */       XMLComponent c = this.fCommonComponents.get(i);
/* 1017 */       c.reset(this);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void resetXML11() throws XNIException {
/* 1028 */     int count = this.fXML11Components.size();
/* 1029 */     for (int i = 0; i < count; i++) {
/* 1030 */       XMLComponent c = this.fXML11Components.get(i);
/* 1031 */       c.reset(this);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void configureXML11Pipeline() {
/* 1042 */     if (this.fCurrentDVFactory != this.fXML11DatatypeFactory) {
/* 1043 */       this.fCurrentDVFactory = this.fXML11DatatypeFactory;
/* 1044 */       setProperty("http://apache.org/xml/properties/internal/datatype-validator-factory", this.fCurrentDVFactory);
/*      */     } 
/* 1046 */     if (this.fCurrentDTDScanner != this.fXML11DTDScanner) {
/* 1047 */       this.fCurrentDTDScanner = this.fXML11DTDScanner;
/* 1048 */       setProperty("http://apache.org/xml/properties/internal/dtd-scanner", this.fCurrentDTDScanner);
/* 1049 */       setProperty("http://apache.org/xml/properties/internal/dtd-processor", this.fXML11DTDProcessor);
/*      */     } 
/*      */     
/* 1052 */     this.fXML11DTDScanner.setDTDHandler(this.fXML11DTDProcessor);
/* 1053 */     this.fXML11DTDProcessor.setDTDSource(this.fXML11DTDScanner);
/* 1054 */     this.fXML11DTDProcessor.setDTDHandler(this.fDTDHandler);
/* 1055 */     if (this.fDTDHandler != null) {
/* 1056 */       this.fDTDHandler.setDTDSource(this.fXML11DTDProcessor);
/*      */     }
/*      */     
/* 1059 */     this.fXML11DTDScanner.setDTDContentModelHandler(this.fXML11DTDProcessor);
/* 1060 */     this.fXML11DTDProcessor.setDTDContentModelSource(this.fXML11DTDScanner);
/* 1061 */     this.fXML11DTDProcessor.setDTDContentModelHandler(this.fDTDContentModelHandler);
/* 1062 */     if (this.fDTDContentModelHandler != null) {
/* 1063 */       this.fDTDContentModelHandler.setDTDContentModelSource(this.fXML11DTDProcessor);
/*      */     }
/*      */ 
/*      */     
/* 1067 */     if (this.fFeatures.get("http://xml.org/sax/features/namespaces") == Boolean.TRUE) {
/* 1068 */       if (this.fCurrentScanner != this.fXML11NSDocScanner) {
/* 1069 */         this.fCurrentScanner = this.fXML11NSDocScanner;
/* 1070 */         setProperty("http://apache.org/xml/properties/internal/document-scanner", this.fXML11NSDocScanner);
/* 1071 */         setProperty("http://apache.org/xml/properties/internal/validator/dtd", this.fXML11NSDTDValidator);
/*      */       } 
/*      */       
/* 1074 */       this.fXML11NSDocScanner.setDTDValidator(this.fXML11NSDTDValidator);
/* 1075 */       this.fXML11NSDocScanner.setDocumentHandler(this.fXML11NSDTDValidator);
/* 1076 */       this.fXML11NSDTDValidator.setDocumentSource(this.fXML11NSDocScanner);
/* 1077 */       this.fXML11NSDTDValidator.setDocumentHandler(this.fDocumentHandler);
/*      */       
/* 1079 */       if (this.fDocumentHandler != null) {
/* 1080 */         this.fDocumentHandler.setDocumentSource(this.fXML11NSDTDValidator);
/*      */       }
/* 1082 */       this.fLastComponent = this.fXML11NSDTDValidator;
/*      */     }
/*      */     else {
/*      */       
/* 1086 */       if (this.fXML11DocScanner == null) {
/*      */         
/* 1088 */         this.fXML11DocScanner = new XML11DocumentScannerImpl();
/* 1089 */         addXML11Component(this.fXML11DocScanner);
/* 1090 */         this.fXML11DTDValidator = new XML11DTDValidator();
/* 1091 */         addXML11Component(this.fXML11DTDValidator);
/*      */       } 
/* 1093 */       if (this.fCurrentScanner != this.fXML11DocScanner) {
/* 1094 */         this.fCurrentScanner = this.fXML11DocScanner;
/* 1095 */         setProperty("http://apache.org/xml/properties/internal/document-scanner", this.fXML11DocScanner);
/* 1096 */         setProperty("http://apache.org/xml/properties/internal/validator/dtd", this.fXML11DTDValidator);
/*      */       } 
/* 1098 */       this.fXML11DocScanner.setDocumentHandler(this.fXML11DTDValidator);
/* 1099 */       this.fXML11DTDValidator.setDocumentSource(this.fXML11DocScanner);
/* 1100 */       this.fXML11DTDValidator.setDocumentHandler(this.fDocumentHandler);
/*      */       
/* 1102 */       if (this.fDocumentHandler != null) {
/* 1103 */         this.fDocumentHandler.setDocumentSource(this.fXML11DTDValidator);
/*      */       }
/* 1105 */       this.fLastComponent = this.fXML11DTDValidator;
/*      */     } 
/*      */ 
/*      */     
/* 1109 */     if (this.fFeatures.get("http://apache.org/xml/features/validation/schema") == Boolean.TRUE) {
/*      */       
/* 1111 */       if (this.fSchemaValidator == null) {
/* 1112 */         this.fSchemaValidator = new XMLSchemaValidator();
/*      */         
/* 1114 */         setProperty("http://apache.org/xml/properties/internal/validator/schema", this.fSchemaValidator);
/* 1115 */         addCommonComponent(this.fSchemaValidator);
/* 1116 */         this.fSchemaValidator.reset(this);
/*      */         
/* 1118 */         if (this.fErrorReporter.getMessageFormatter("http://www.w3.org/TR/xml-schema-1") == null) {
/* 1119 */           XSMessageFormatter xmft = new XSMessageFormatter();
/* 1120 */           this.fErrorReporter.putMessageFormatter("http://www.w3.org/TR/xml-schema-1", xmft);
/*      */         } 
/*      */       } 
/*      */       
/* 1124 */       this.fLastComponent.setDocumentHandler(this.fSchemaValidator);
/* 1125 */       this.fSchemaValidator.setDocumentSource(this.fLastComponent);
/* 1126 */       this.fSchemaValidator.setDocumentHandler(this.fDocumentHandler);
/* 1127 */       if (this.fDocumentHandler != null) {
/* 1128 */         this.fDocumentHandler.setDocumentSource(this.fSchemaValidator);
/*      */       }
/* 1130 */       this.fLastComponent = this.fSchemaValidator;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected void configurePipeline() {
/* 1137 */     if (this.fCurrentDVFactory != this.fDatatypeValidatorFactory) {
/* 1138 */       this.fCurrentDVFactory = this.fDatatypeValidatorFactory;
/*      */       
/* 1140 */       setProperty("http://apache.org/xml/properties/internal/datatype-validator-factory", this.fCurrentDVFactory);
/*      */     } 
/*      */ 
/*      */     
/* 1144 */     if (this.fCurrentDTDScanner != this.fDTDScanner) {
/* 1145 */       this.fCurrentDTDScanner = this.fDTDScanner;
/* 1146 */       setProperty("http://apache.org/xml/properties/internal/dtd-scanner", this.fCurrentDTDScanner);
/* 1147 */       setProperty("http://apache.org/xml/properties/internal/dtd-processor", this.fDTDProcessor);
/*      */     } 
/* 1149 */     this.fDTDScanner.setDTDHandler(this.fDTDProcessor);
/* 1150 */     this.fDTDProcessor.setDTDSource(this.fDTDScanner);
/* 1151 */     this.fDTDProcessor.setDTDHandler(this.fDTDHandler);
/* 1152 */     if (this.fDTDHandler != null) {
/* 1153 */       this.fDTDHandler.setDTDSource(this.fDTDProcessor);
/*      */     }
/*      */     
/* 1156 */     this.fDTDScanner.setDTDContentModelHandler(this.fDTDProcessor);
/* 1157 */     this.fDTDProcessor.setDTDContentModelSource(this.fDTDScanner);
/* 1158 */     this.fDTDProcessor.setDTDContentModelHandler(this.fDTDContentModelHandler);
/* 1159 */     if (this.fDTDContentModelHandler != null) {
/* 1160 */       this.fDTDContentModelHandler.setDTDContentModelSource(this.fDTDProcessor);
/*      */     }
/*      */ 
/*      */     
/* 1164 */     if (this.fFeatures.get("http://xml.org/sax/features/namespaces") == Boolean.TRUE) {
/* 1165 */       if (this.fCurrentScanner != this.fNamespaceScanner) {
/* 1166 */         this.fCurrentScanner = this.fNamespaceScanner;
/* 1167 */         setProperty("http://apache.org/xml/properties/internal/document-scanner", this.fNamespaceScanner);
/* 1168 */         setProperty("http://apache.org/xml/properties/internal/validator/dtd", this.fDTDValidator);
/*      */       } 
/* 1170 */       this.fNamespaceScanner.setDTDValidator(this.fDTDValidator);
/* 1171 */       this.fNamespaceScanner.setDocumentHandler(this.fDTDValidator);
/* 1172 */       this.fDTDValidator.setDocumentSource(this.fNamespaceScanner);
/* 1173 */       this.fDTDValidator.setDocumentHandler(this.fDocumentHandler);
/* 1174 */       if (this.fDocumentHandler != null) {
/* 1175 */         this.fDocumentHandler.setDocumentSource(this.fDTDValidator);
/*      */       }
/* 1177 */       this.fLastComponent = this.fDTDValidator;
/*      */     } else {
/*      */       
/* 1180 */       if (this.fNonNSScanner == null) {
/* 1181 */         this.fNonNSScanner = new XMLDocumentScannerImpl();
/* 1182 */         this.fNonNSDTDValidator = new XMLDTDValidator();
/*      */         
/* 1184 */         addComponent(this.fNonNSScanner);
/* 1185 */         addComponent(this.fNonNSDTDValidator);
/*      */       } 
/* 1187 */       if (this.fCurrentScanner != this.fNonNSScanner) {
/* 1188 */         this.fCurrentScanner = this.fNonNSScanner;
/* 1189 */         setProperty("http://apache.org/xml/properties/internal/document-scanner", this.fNonNSScanner);
/* 1190 */         setProperty("http://apache.org/xml/properties/internal/validator/dtd", this.fNonNSDTDValidator);
/*      */       } 
/*      */       
/* 1193 */       this.fNonNSScanner.setDocumentHandler(this.fNonNSDTDValidator);
/* 1194 */       this.fNonNSDTDValidator.setDocumentSource(this.fNonNSScanner);
/* 1195 */       this.fNonNSDTDValidator.setDocumentHandler(this.fDocumentHandler);
/* 1196 */       if (this.fDocumentHandler != null) {
/* 1197 */         this.fDocumentHandler.setDocumentSource(this.fNonNSDTDValidator);
/*      */       }
/* 1199 */       this.fLastComponent = this.fNonNSDTDValidator;
/*      */     } 
/*      */ 
/*      */     
/* 1203 */     if (this.fFeatures.get("http://apache.org/xml/features/validation/schema") == Boolean.TRUE) {
/*      */       
/* 1205 */       if (this.fSchemaValidator == null) {
/* 1206 */         this.fSchemaValidator = new XMLSchemaValidator();
/*      */         
/* 1208 */         setProperty("http://apache.org/xml/properties/internal/validator/schema", this.fSchemaValidator);
/* 1209 */         addCommonComponent(this.fSchemaValidator);
/* 1210 */         this.fSchemaValidator.reset(this);
/*      */         
/* 1212 */         if (this.fErrorReporter.getMessageFormatter("http://www.w3.org/TR/xml-schema-1") == null) {
/* 1213 */           XSMessageFormatter xmft = new XSMessageFormatter();
/* 1214 */           this.fErrorReporter.putMessageFormatter("http://www.w3.org/TR/xml-schema-1", xmft);
/*      */         } 
/*      */       } 
/*      */       
/* 1218 */       this.fLastComponent.setDocumentHandler(this.fSchemaValidator);
/* 1219 */       this.fSchemaValidator.setDocumentSource(this.fLastComponent);
/* 1220 */       this.fSchemaValidator.setDocumentHandler(this.fDocumentHandler);
/* 1221 */       if (this.fDocumentHandler != null) {
/* 1222 */         this.fDocumentHandler.setDocumentSource(this.fSchemaValidator);
/*      */       }
/* 1224 */       this.fLastComponent = this.fSchemaValidator;
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
/*      */   protected FeatureState checkFeature(String featureId) throws XMLConfigurationException {
/* 1249 */     if (featureId.startsWith("http://apache.org/xml/features/")) {
/* 1250 */       int suffixLength = featureId.length() - "http://apache.org/xml/features/".length();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1258 */       if (suffixLength == "validation/dynamic".length() && featureId
/* 1259 */         .endsWith("validation/dynamic")) {
/* 1260 */         return FeatureState.RECOGNIZED;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1266 */       if (suffixLength == "validation/default-attribute-values".length() && featureId
/* 1267 */         .endsWith("validation/default-attribute-values"))
/*      */       {
/* 1269 */         return FeatureState.NOT_SUPPORTED;
/*      */       }
/*      */ 
/*      */ 
/*      */       
/* 1274 */       if (suffixLength == "validation/validate-content-models".length() && featureId
/* 1275 */         .endsWith("validation/validate-content-models"))
/*      */       {
/* 1277 */         return FeatureState.NOT_SUPPORTED;
/*      */       }
/*      */ 
/*      */ 
/*      */       
/* 1282 */       if (suffixLength == "nonvalidating/load-dtd-grammar".length() && featureId
/* 1283 */         .endsWith("nonvalidating/load-dtd-grammar")) {
/* 1284 */         return FeatureState.RECOGNIZED;
/*      */       }
/*      */ 
/*      */ 
/*      */       
/* 1289 */       if (suffixLength == "nonvalidating/load-external-dtd".length() && featureId
/* 1290 */         .endsWith("nonvalidating/load-external-dtd")) {
/* 1291 */         return FeatureState.RECOGNIZED;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1297 */       if (suffixLength == "validation/validate-datatypes".length() && featureId
/* 1298 */         .endsWith("validation/validate-datatypes")) {
/* 1299 */         return FeatureState.NOT_SUPPORTED;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1306 */       if (suffixLength == "validation/schema".length() && featureId
/* 1307 */         .endsWith("validation/schema")) {
/* 1308 */         return FeatureState.RECOGNIZED;
/*      */       }
/*      */       
/* 1311 */       if (suffixLength == "validation/schema-full-checking".length() && featureId
/* 1312 */         .endsWith("validation/schema-full-checking")) {
/* 1313 */         return FeatureState.RECOGNIZED;
/*      */       }
/*      */ 
/*      */       
/* 1317 */       if (suffixLength == "validation/schema/normalized-value".length() && featureId
/* 1318 */         .endsWith("validation/schema/normalized-value")) {
/* 1319 */         return FeatureState.RECOGNIZED;
/*      */       }
/*      */ 
/*      */       
/* 1323 */       if (suffixLength == "validation/schema/element-default".length() && featureId
/* 1324 */         .endsWith("validation/schema/element-default")) {
/* 1325 */         return FeatureState.RECOGNIZED;
/*      */       }
/*      */ 
/*      */       
/* 1329 */       if (suffixLength == "internal/parser-settings".length() && featureId
/* 1330 */         .endsWith("internal/parser-settings")) {
/* 1331 */         return FeatureState.NOT_SUPPORTED;
/*      */       }
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1340 */     return super.checkFeature(featureId);
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
/*      */   protected PropertyState checkProperty(String propertyId) throws XMLConfigurationException {
/* 1363 */     if (propertyId.startsWith("http://apache.org/xml/properties/")) {
/* 1364 */       int suffixLength = propertyId.length() - "http://apache.org/xml/properties/".length();
/*      */       
/* 1366 */       if (suffixLength == "internal/dtd-scanner".length() && propertyId
/* 1367 */         .endsWith("internal/dtd-scanner")) {
/* 1368 */         return PropertyState.RECOGNIZED;
/*      */       }
/* 1370 */       if (suffixLength == "schema/external-schemaLocation".length() && propertyId
/* 1371 */         .endsWith("schema/external-schemaLocation")) {
/* 1372 */         return PropertyState.RECOGNIZED;
/*      */       }
/* 1374 */       if (suffixLength == "schema/external-noNamespaceSchemaLocation".length() && propertyId
/* 1375 */         .endsWith("schema/external-noNamespaceSchemaLocation")) {
/* 1376 */         return PropertyState.RECOGNIZED;
/*      */       }
/*      */     } 
/*      */     
/* 1380 */     if (propertyId.startsWith("http://java.sun.com/xml/jaxp/properties/")) {
/* 1381 */       int suffixLength = propertyId.length() - "http://java.sun.com/xml/jaxp/properties/".length();
/*      */       
/* 1383 */       if (suffixLength == "schemaSource".length() && propertyId
/* 1384 */         .endsWith("schemaSource")) {
/* 1385 */         return PropertyState.RECOGNIZED;
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/* 1390 */     if (propertyId.startsWith("http://xml.org/sax/properties/")) {
/* 1391 */       int suffixLength = propertyId.length() - "http://xml.org/sax/properties/".length();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1403 */       if (suffixLength == "xml-string".length() && propertyId
/* 1404 */         .endsWith("xml-string"))
/*      */       {
/*      */ 
/*      */         
/* 1408 */         return PropertyState.NOT_SUPPORTED;
/*      */       }
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1416 */     return super.checkProperty(propertyId);
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
/*      */   protected void addComponent(XMLComponent component) {
/* 1431 */     if (this.fComponents.contains(component)) {
/*      */       return;
/*      */     }
/* 1434 */     this.fComponents.add(component);
/* 1435 */     addRecognizedParamsAndSetDefaults(component);
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
/*      */   protected void addCommonComponent(XMLComponent component) {
/* 1449 */     if (this.fCommonComponents.contains(component)) {
/*      */       return;
/*      */     }
/* 1452 */     this.fCommonComponents.add(component);
/* 1453 */     addRecognizedParamsAndSetDefaults(component);
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
/*      */   protected void addXML11Component(XMLComponent component) {
/* 1467 */     if (this.fXML11Components.contains(component)) {
/*      */       return;
/*      */     }
/* 1470 */     this.fXML11Components.add(component);
/* 1471 */     addRecognizedParamsAndSetDefaults(component);
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
/*      */   protected void addRecognizedParamsAndSetDefaults(XMLComponent component) {
/* 1487 */     String[] recognizedFeatures = component.getRecognizedFeatures();
/* 1488 */     addRecognizedFeatures(recognizedFeatures);
/*      */ 
/*      */     
/* 1491 */     String[] recognizedProperties = component.getRecognizedProperties();
/* 1492 */     addRecognizedProperties(recognizedProperties);
/*      */ 
/*      */     
/* 1495 */     if (recognizedFeatures != null) {
/* 1496 */       for (int i = 0; i < recognizedFeatures.length; i++) {
/* 1497 */         String featureId = recognizedFeatures[i];
/* 1498 */         Boolean state = component.getFeatureDefault(featureId);
/* 1499 */         if (state != null)
/*      */         {
/* 1501 */           if (!this.fFeatures.containsKey(featureId)) {
/* 1502 */             this.fFeatures.put(featureId, state);
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 1507 */             this.fConfigUpdated = true;
/*      */           } 
/*      */         }
/*      */       } 
/*      */     }
/* 1512 */     if (recognizedProperties != null) {
/* 1513 */       for (int i = 0; i < recognizedProperties.length; i++) {
/* 1514 */         String propertyId = recognizedProperties[i];
/* 1515 */         Object value = component.getPropertyDefault(propertyId);
/* 1516 */         if (value != null)
/*      */         {
/* 1518 */           if (!this.fProperties.containsKey(propertyId)) {
/* 1519 */             this.fProperties.put(propertyId, value);
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 1524 */             this.fConfigUpdated = true;
/*      */           } 
/*      */         }
/*      */       } 
/*      */     }
/*      */   }
/*      */   
/*      */   private void initXML11Components() {
/* 1532 */     if (!this.f11Initialized) {
/*      */ 
/*      */       
/* 1535 */       this.fXML11DatatypeFactory = DTDDVFactory.getInstance("com.sun.org.apache.xerces.internal.impl.dv.dtd.XML11DTDDVFactoryImpl");
/*      */ 
/*      */       
/* 1538 */       this.fXML11DTDScanner = new XML11DTDScannerImpl();
/* 1539 */       addXML11Component(this.fXML11DTDScanner);
/* 1540 */       this.fXML11DTDProcessor = new XML11DTDProcessor();
/* 1541 */       addXML11Component(this.fXML11DTDProcessor);
/*      */ 
/*      */       
/* 1544 */       this.fXML11NSDocScanner = new XML11NSDocumentScannerImpl();
/* 1545 */       addXML11Component(this.fXML11NSDocScanner);
/* 1546 */       this.fXML11NSDTDValidator = new XML11NSDTDValidator();
/* 1547 */       addXML11Component(this.fXML11NSDTDValidator);
/*      */       
/* 1549 */       this.f11Initialized = true;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   FeatureState getFeatureState0(String featureId) throws XMLConfigurationException {
/* 1560 */     return super.getFeatureState(featureId);
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xerces/internal/parsers/XML11Configuration.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */