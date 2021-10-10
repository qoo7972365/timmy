/*      */ package com.sun.org.apache.xerces.internal.impl.xs;
/*      */ 
/*      */ import com.sun.org.apache.xerces.internal.dom.DOMErrorImpl;
/*      */ import com.sun.org.apache.xerces.internal.dom.DOMMessageFormatter;
/*      */ import com.sun.org.apache.xerces.internal.dom.DOMStringListImpl;
/*      */ import com.sun.org.apache.xerces.internal.impl.XMLEntityManager;
/*      */ import com.sun.org.apache.xerces.internal.impl.XMLErrorReporter;
/*      */ import com.sun.org.apache.xerces.internal.impl.dv.InvalidDatatypeValueException;
/*      */ import com.sun.org.apache.xerces.internal.impl.dv.SchemaDVFactory;
/*      */ import com.sun.org.apache.xerces.internal.impl.dv.ValidatedInfo;
/*      */ import com.sun.org.apache.xerces.internal.impl.dv.ValidationContext;
/*      */ import com.sun.org.apache.xerces.internal.impl.dv.xs.SchemaDVFactoryImpl;
/*      */ import com.sun.org.apache.xerces.internal.impl.xs.models.CMBuilder;
/*      */ import com.sun.org.apache.xerces.internal.impl.xs.models.CMNodeFactory;
/*      */ import com.sun.org.apache.xerces.internal.impl.xs.traversers.XSDHandler;
/*      */ import com.sun.org.apache.xerces.internal.util.DOMEntityResolverWrapper;
/*      */ import com.sun.org.apache.xerces.internal.util.DOMErrorHandlerWrapper;
/*      */ import com.sun.org.apache.xerces.internal.util.DefaultErrorHandler;
/*      */ import com.sun.org.apache.xerces.internal.util.ParserConfigurationSettings;
/*      */ import com.sun.org.apache.xerces.internal.util.Status;
/*      */ import com.sun.org.apache.xerces.internal.util.SymbolTable;
/*      */ import com.sun.org.apache.xerces.internal.util.XMLSymbols;
/*      */ import com.sun.org.apache.xerces.internal.utils.SecuritySupport;
/*      */ import com.sun.org.apache.xerces.internal.utils.XMLSecurityManager;
/*      */ import com.sun.org.apache.xerces.internal.utils.XMLSecurityPropertyManager;
/*      */ import com.sun.org.apache.xerces.internal.xni.XNIException;
/*      */ import com.sun.org.apache.xerces.internal.xni.grammars.Grammar;
/*      */ import com.sun.org.apache.xerces.internal.xni.grammars.XMLGrammarLoader;
/*      */ import com.sun.org.apache.xerces.internal.xni.grammars.XMLGrammarPool;
/*      */ import com.sun.org.apache.xerces.internal.xni.grammars.XSGrammar;
/*      */ import com.sun.org.apache.xerces.internal.xni.parser.XMLComponent;
/*      */ import com.sun.org.apache.xerces.internal.xni.parser.XMLComponentManager;
/*      */ import com.sun.org.apache.xerces.internal.xni.parser.XMLConfigurationException;
/*      */ import com.sun.org.apache.xerces.internal.xni.parser.XMLEntityResolver;
/*      */ import com.sun.org.apache.xerces.internal.xni.parser.XMLErrorHandler;
/*      */ import com.sun.org.apache.xerces.internal.xni.parser.XMLInputSource;
/*      */ import com.sun.org.apache.xerces.internal.xs.LSInputList;
/*      */ import com.sun.org.apache.xerces.internal.xs.StringList;
/*      */ import com.sun.org.apache.xerces.internal.xs.XSLoader;
/*      */ import com.sun.org.apache.xerces.internal.xs.XSModel;
/*      */ import java.io.BufferedInputStream;
/*      */ import java.io.File;
/*      */ import java.io.FileInputStream;
/*      */ import java.io.FileNotFoundException;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.io.Reader;
/*      */ import java.io.StringReader;
/*      */ import java.util.HashMap;
/*      */ import java.util.Locale;
/*      */ import java.util.Map;
/*      */ import java.util.StringTokenizer;
/*      */ import java.util.Vector;
/*      */ import org.w3c.dom.DOMConfiguration;
/*      */ import org.w3c.dom.DOMErrorHandler;
/*      */ import org.w3c.dom.DOMException;
/*      */ import org.w3c.dom.DOMStringList;
/*      */ import org.w3c.dom.ls.LSInput;
/*      */ import org.w3c.dom.ls.LSResourceResolver;
/*      */ import org.xml.sax.InputSource;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class XMLSchemaLoader
/*      */   implements XMLGrammarLoader, XMLComponent, XSLoader, DOMConfiguration
/*      */ {
/*      */   protected static final String SCHEMA_FULL_CHECKING = "http://apache.org/xml/features/validation/schema-full-checking";
/*      */   protected static final String CONTINUE_AFTER_FATAL_ERROR = "http://apache.org/xml/features/continue-after-fatal-error";
/*      */   protected static final String ALLOW_JAVA_ENCODINGS = "http://apache.org/xml/features/allow-java-encodings";
/*      */   protected static final String STANDARD_URI_CONFORMANT_FEATURE = "http://apache.org/xml/features/standard-uri-conformant";
/*      */   protected static final String VALIDATE_ANNOTATIONS = "http://apache.org/xml/features/validate-annotations";
/*      */   protected static final String DISALLOW_DOCTYPE = "http://apache.org/xml/features/disallow-doctype-decl";
/*      */   protected static final String GENERATE_SYNTHETIC_ANNOTATIONS = "http://apache.org/xml/features/generate-synthetic-annotations";
/*      */   protected static final String HONOUR_ALL_SCHEMALOCATIONS = "http://apache.org/xml/features/honour-all-schemaLocations";
/*      */   protected static final String AUGMENT_PSVI = "http://apache.org/xml/features/validation/schema/augment-psvi";
/*      */   protected static final String PARSER_SETTINGS = "http://apache.org/xml/features/internal/parser-settings";
/*      */   protected static final String NAMESPACE_GROWTH = "http://apache.org/xml/features/namespace-growth";
/*      */   protected static final String TOLERATE_DUPLICATES = "http://apache.org/xml/features/internal/tolerate-duplicates";
/*      */   protected static final String SCHEMA_DV_FACTORY = "http://apache.org/xml/properties/internal/validation/schema/dv-factory";
/*      */   protected static final String OVERRIDE_PARSER = "jdk.xml.overrideDefaultParser";
/*  162 */   private static final String[] RECOGNIZED_FEATURES = new String[] { "http://apache.org/xml/features/validation/schema-full-checking", "http://apache.org/xml/features/validation/schema/augment-psvi", "http://apache.org/xml/features/continue-after-fatal-error", "http://apache.org/xml/features/allow-java-encodings", "http://apache.org/xml/features/standard-uri-conformant", "http://apache.org/xml/features/disallow-doctype-decl", "http://apache.org/xml/features/generate-synthetic-annotations", "http://apache.org/xml/features/validate-annotations", "http://apache.org/xml/features/honour-all-schemaLocations", "http://apache.org/xml/features/namespace-growth", "http://apache.org/xml/features/internal/tolerate-duplicates", "jdk.xml.overrideDefaultParser" };
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final String SYMBOL_TABLE = "http://apache.org/xml/properties/internal/symbol-table";
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final String ERROR_REPORTER = "http://apache.org/xml/properties/internal/error-reporter";
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected static final String ERROR_HANDLER = "http://apache.org/xml/properties/internal/error-handler";
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final String ENTITY_RESOLVER = "http://apache.org/xml/properties/internal/entity-resolver";
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final String XMLGRAMMAR_POOL = "http://apache.org/xml/properties/internal/grammar-pool";
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected static final String SCHEMA_LOCATION = "http://apache.org/xml/properties/schema/external-schemaLocation";
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected static final String SCHEMA_NONS_LOCATION = "http://apache.org/xml/properties/schema/external-noNamespaceSchemaLocation";
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected static final String JAXP_SCHEMA_SOURCE = "http://java.sun.com/xml/jaxp/properties/schemaSource";
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected static final String SECURITY_MANAGER = "http://apache.org/xml/properties/security-manager";
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected static final String LOCALE = "http://apache.org/xml/properties/locale";
/*      */ 
/*      */ 
/*      */   
/*      */   protected static final String ENTITY_MANAGER = "http://apache.org/xml/properties/internal/entity-manager";
/*      */ 
/*      */ 
/*      */   
/*      */   private static final String XML_SECURITY_PROPERTY_MANAGER = "http://www.oracle.com/xml/jaxp/properties/xmlSecurityPropertyManager";
/*      */ 
/*      */ 
/*      */   
/*      */   public static final String ACCESS_EXTERNAL_DTD = "http://javax.xml.XMLConstants/property/accessExternalDTD";
/*      */ 
/*      */ 
/*      */   
/*      */   public static final String ACCESS_EXTERNAL_SCHEMA = "http://javax.xml.XMLConstants/property/accessExternalSchema";
/*      */ 
/*      */ 
/*      */   
/*  232 */   private static final String[] RECOGNIZED_PROPERTIES = new String[] { "http://apache.org/xml/properties/internal/entity-manager", "http://apache.org/xml/properties/internal/symbol-table", "http://apache.org/xml/properties/internal/error-reporter", "http://apache.org/xml/properties/internal/error-handler", "http://apache.org/xml/properties/internal/entity-resolver", "http://apache.org/xml/properties/internal/grammar-pool", "http://apache.org/xml/properties/schema/external-schemaLocation", "http://apache.org/xml/properties/schema/external-noNamespaceSchemaLocation", "http://java.sun.com/xml/jaxp/properties/schemaSource", "http://apache.org/xml/properties/security-manager", "http://apache.org/xml/properties/locale", "http://apache.org/xml/properties/internal/validation/schema/dv-factory", "http://www.oracle.com/xml/jaxp/properties/xmlSecurityPropertyManager" };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  251 */   private ParserConfigurationSettings fLoaderConfig = new ParserConfigurationSettings();
/*  252 */   private SymbolTable fSymbolTable = null;
/*  253 */   private XMLErrorReporter fErrorReporter = new XMLErrorReporter();
/*  254 */   private XMLEntityManager fEntityManager = null;
/*  255 */   private XMLEntityResolver fUserEntityResolver = null;
/*  256 */   private XMLGrammarPool fGrammarPool = null;
/*  257 */   private String fExternalSchemas = null;
/*  258 */   private String fExternalNoNSSchema = null;
/*      */   
/*  260 */   private Object fJAXPSource = null;
/*      */   
/*      */   private boolean fIsCheckedFully = false;
/*      */   
/*      */   private boolean fJAXPProcessed = false;
/*      */   
/*      */   private boolean fSettingsChanged = true;
/*      */   
/*      */   private XSDHandler fSchemaHandler;
/*      */   
/*      */   private XSGrammarBucket fGrammarBucket;
/*  271 */   private XSDeclarationPool fDeclPool = null;
/*      */   private SubstitutionGroupHandler fSubGroupHandler;
/*  273 */   private final CMNodeFactory fNodeFactory = new CMNodeFactory();
/*      */   private CMBuilder fCMBuilder;
/*  275 */   private XSDDescription fXSDDescription = new XSDDescription();
/*  276 */   private String faccessExternalSchema = "all";
/*      */   
/*      */   private Map fJAXPCache;
/*  279 */   private Locale fLocale = Locale.getDefault();
/*      */ 
/*      */   
/*  282 */   private DOMStringList fRecognizedParameters = null;
/*      */ 
/*      */   
/*  285 */   private DOMErrorHandlerWrapper fErrorHandler = null;
/*      */ 
/*      */   
/*  288 */   private DOMEntityResolverWrapper fResourceResolver = null;
/*      */ 
/*      */   
/*      */   public XMLSchemaLoader() {
/*  292 */     this(new SymbolTable(), null, new XMLEntityManager(), null, null, null);
/*      */   }
/*      */   
/*      */   public XMLSchemaLoader(SymbolTable symbolTable) {
/*  296 */     this(symbolTable, null, new XMLEntityManager(), null, null, null);
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
/*      */   XMLSchemaLoader(XMLErrorReporter errorReporter, XSGrammarBucket grammarBucket, SubstitutionGroupHandler sHandler, CMBuilder builder) {
/*  309 */     this(null, errorReporter, null, grammarBucket, sHandler, builder);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   XMLSchemaLoader(SymbolTable symbolTable, XMLErrorReporter errorReporter, XMLEntityManager entityResolver, XSGrammarBucket grammarBucket, SubstitutionGroupHandler sHandler, CMBuilder builder) {
/*  317 */     this.fLoaderConfig.addRecognizedFeatures(RECOGNIZED_FEATURES);
/*  318 */     this.fLoaderConfig.addRecognizedProperties(RECOGNIZED_PROPERTIES);
/*  319 */     if (symbolTable != null) {
/*  320 */       this.fLoaderConfig.setProperty("http://apache.org/xml/properties/internal/symbol-table", symbolTable);
/*      */     }
/*      */     
/*  323 */     if (errorReporter == null) {
/*  324 */       errorReporter = new XMLErrorReporter();
/*  325 */       errorReporter.setLocale(this.fLocale);
/*  326 */       errorReporter.setProperty("http://apache.org/xml/properties/internal/error-handler", new DefaultErrorHandler());
/*      */     } 
/*      */     
/*  329 */     this.fErrorReporter = errorReporter;
/*      */     
/*  331 */     if (this.fErrorReporter.getMessageFormatter("http://www.w3.org/TR/xml-schema-1") == null) {
/*  332 */       this.fErrorReporter.putMessageFormatter("http://www.w3.org/TR/xml-schema-1", new XSMessageFormatter());
/*      */     }
/*  334 */     this.fLoaderConfig.setProperty("http://apache.org/xml/properties/internal/error-reporter", this.fErrorReporter);
/*  335 */     this.fEntityManager = entityResolver;
/*      */     
/*  337 */     if (this.fEntityManager != null) {
/*  338 */       this.fLoaderConfig.setProperty("http://apache.org/xml/properties/internal/entity-manager", this.fEntityManager);
/*      */     }
/*      */ 
/*      */     
/*  342 */     this.fLoaderConfig.setFeature("http://apache.org/xml/features/validation/schema/augment-psvi", true);
/*      */     
/*  344 */     if (grammarBucket == null) {
/*  345 */       grammarBucket = new XSGrammarBucket();
/*      */     }
/*  347 */     this.fGrammarBucket = grammarBucket;
/*  348 */     if (sHandler == null) {
/*  349 */       sHandler = new SubstitutionGroupHandler(this.fGrammarBucket);
/*      */     }
/*  351 */     this.fSubGroupHandler = sHandler;
/*      */     
/*  353 */     if (builder == null) {
/*  354 */       builder = new CMBuilder(this.fNodeFactory);
/*      */     }
/*  356 */     this.fCMBuilder = builder;
/*  357 */     this.fSchemaHandler = new XSDHandler(this.fGrammarBucket);
/*  358 */     if (this.fDeclPool != null) {
/*  359 */       this.fDeclPool.reset();
/*      */     }
/*  361 */     this.fJAXPCache = new HashMap<>();
/*      */     
/*  363 */     this.fSettingsChanged = true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String[] getRecognizedFeatures() {
/*  372 */     return (String[])RECOGNIZED_FEATURES.clone();
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
/*      */   public boolean getFeature(String featureId) throws XMLConfigurationException {
/*  384 */     return this.fLoaderConfig.getFeature(featureId);
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
/*      */   public void setFeature(String featureId, boolean state) throws XMLConfigurationException {
/*  398 */     this.fSettingsChanged = true;
/*  399 */     if (featureId.equals("http://apache.org/xml/features/continue-after-fatal-error")) {
/*  400 */       this.fErrorReporter.setFeature("http://apache.org/xml/features/continue-after-fatal-error", state);
/*      */     }
/*  402 */     else if (featureId.equals("http://apache.org/xml/features/generate-synthetic-annotations")) {
/*  403 */       this.fSchemaHandler.setGenerateSyntheticAnnotations(state);
/*      */     } 
/*  405 */     this.fLoaderConfig.setFeature(featureId, state);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String[] getRecognizedProperties() {
/*  414 */     return (String[])RECOGNIZED_PROPERTIES.clone();
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
/*      */   public Object getProperty(String propertyId) throws XMLConfigurationException {
/*  426 */     return this.fLoaderConfig.getProperty(propertyId);
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
/*      */   public void setProperty(String propertyId, Object state) throws XMLConfigurationException {
/*  440 */     this.fSettingsChanged = true;
/*  441 */     this.fLoaderConfig.setProperty(propertyId, state);
/*  442 */     if (propertyId.equals("http://java.sun.com/xml/jaxp/properties/schemaSource")) {
/*  443 */       this.fJAXPSource = state;
/*  444 */       this.fJAXPProcessed = false;
/*      */     }
/*  446 */     else if (propertyId.equals("http://apache.org/xml/properties/internal/grammar-pool")) {
/*  447 */       this.fGrammarPool = (XMLGrammarPool)state;
/*      */     }
/*  449 */     else if (propertyId.equals("http://apache.org/xml/properties/schema/external-schemaLocation")) {
/*  450 */       this.fExternalSchemas = (String)state;
/*      */     }
/*  452 */     else if (propertyId.equals("http://apache.org/xml/properties/schema/external-noNamespaceSchemaLocation")) {
/*  453 */       this.fExternalNoNSSchema = (String)state;
/*      */     }
/*  455 */     else if (propertyId.equals("http://apache.org/xml/properties/locale")) {
/*  456 */       setLocale((Locale)state);
/*      */     }
/*  458 */     else if (propertyId.equals("http://apache.org/xml/properties/internal/entity-resolver")) {
/*  459 */       this.fEntityManager.setProperty("http://apache.org/xml/properties/internal/entity-resolver", state);
/*      */     }
/*  461 */     else if (propertyId.equals("http://apache.org/xml/properties/internal/error-reporter")) {
/*  462 */       this.fErrorReporter = (XMLErrorReporter)state;
/*  463 */       if (this.fErrorReporter.getMessageFormatter("http://www.w3.org/TR/xml-schema-1") == null) {
/*  464 */         this.fErrorReporter.putMessageFormatter("http://www.w3.org/TR/xml-schema-1", new XSMessageFormatter());
/*      */       }
/*      */     }
/*  467 */     else if (propertyId.equals("http://www.oracle.com/xml/jaxp/properties/xmlSecurityPropertyManager")) {
/*  468 */       XMLSecurityPropertyManager spm = (XMLSecurityPropertyManager)state;
/*  469 */       this.faccessExternalSchema = spm.getValue(XMLSecurityPropertyManager.Property.ACCESS_EXTERNAL_SCHEMA);
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
/*      */   public void setLocale(Locale locale) {
/*  482 */     this.fLocale = locale;
/*  483 */     this.fErrorReporter.setLocale(locale);
/*      */   }
/*      */ 
/*      */   
/*      */   public Locale getLocale() {
/*  488 */     return this.fLocale;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setErrorHandler(XMLErrorHandler errorHandler) {
/*  497 */     this.fErrorReporter.setProperty("http://apache.org/xml/properties/internal/error-handler", errorHandler);
/*      */   }
/*      */ 
/*      */   
/*      */   public XMLErrorHandler getErrorHandler() {
/*  502 */     return this.fErrorReporter.getErrorHandler();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setEntityResolver(XMLEntityResolver entityResolver) {
/*  511 */     this.fUserEntityResolver = entityResolver;
/*  512 */     this.fLoaderConfig.setProperty("http://apache.org/xml/properties/internal/entity-resolver", entityResolver);
/*  513 */     this.fEntityManager.setProperty("http://apache.org/xml/properties/internal/entity-resolver", entityResolver);
/*      */   }
/*      */ 
/*      */   
/*      */   public XMLEntityResolver getEntityResolver() {
/*  518 */     return this.fUserEntityResolver;
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
/*      */   public void loadGrammar(XMLInputSource[] source) throws IOException, XNIException {
/*  533 */     int numSource = source.length;
/*  534 */     for (int i = 0; i < numSource; i++) {
/*  535 */       loadGrammar(source[i]);
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
/*      */   public Grammar loadGrammar(XMLInputSource source) throws IOException, XNIException {
/*  556 */     reset(this.fLoaderConfig);
/*  557 */     this.fSettingsChanged = false;
/*  558 */     XSDDescription desc = new XSDDescription();
/*  559 */     desc.fContextType = 3;
/*  560 */     desc.setBaseSystemId(source.getBaseSystemId());
/*  561 */     desc.setLiteralSystemId(source.getSystemId());
/*      */     
/*  563 */     Map<Object, Object> locationPairs = new HashMap<>();
/*      */ 
/*      */ 
/*      */     
/*  567 */     processExternalHints(this.fExternalSchemas, this.fExternalNoNSSchema, (Map)locationPairs, this.fErrorReporter);
/*      */     
/*  569 */     SchemaGrammar grammar = loadSchema(desc, source, (Map)locationPairs);
/*      */     
/*  571 */     if (grammar != null && this.fGrammarPool != null) {
/*  572 */       this.fGrammarPool.cacheGrammars("http://www.w3.org/2001/XMLSchema", (Grammar[])this.fGrammarBucket.getGrammars());
/*      */ 
/*      */       
/*  575 */       if (this.fIsCheckedFully && this.fJAXPCache.get(grammar) != grammar) {
/*  576 */         XSConstraints.fullSchemaChecking(this.fGrammarBucket, this.fSubGroupHandler, this.fCMBuilder, this.fErrorReporter);
/*      */       }
/*      */     } 
/*  579 */     return grammar;
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
/*      */   SchemaGrammar loadSchema(XSDDescription desc, XMLInputSource source, Map<String, LocationArray> locationPairs) throws IOException, XNIException {
/*  598 */     if (!this.fJAXPProcessed) {
/*  599 */       processJAXPSchemaSource(locationPairs);
/*      */     }
/*      */     
/*  602 */     if (desc.isExternal()) {
/*  603 */       String accessError = SecuritySupport.checkAccess(desc.getExpandedSystemId(), this.faccessExternalSchema, "all");
/*  604 */       if (accessError != null) {
/*  605 */         throw new XNIException(this.fErrorReporter.reportError("http://www.w3.org/TR/xml-schema-1", "schema_reference.access", new Object[] {
/*      */                 
/*  607 */                 SecuritySupport.sanitizePath(desc.getExpandedSystemId()), accessError }, (short)1));
/*      */       }
/*      */     } 
/*  610 */     SchemaGrammar grammar = this.fSchemaHandler.parseSchema(source, desc, locationPairs);
/*      */     
/*  612 */     return grammar;
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
/*      */   public static XMLInputSource resolveDocument(XSDDescription desc, Map<String, LocationArray> locationPairs, XMLEntityResolver entityResolver) throws IOException {
/*  630 */     String loc = null;
/*      */     
/*  632 */     if (desc.getContextType() == 2 || desc
/*  633 */       .fromInstance()) {
/*      */       
/*  635 */       String namespace = desc.getTargetNamespace();
/*  636 */       String ns = (namespace == null) ? XMLSymbols.EMPTY_STRING : namespace;
/*      */       
/*  638 */       LocationArray tempLA = locationPairs.get(ns);
/*  639 */       if (tempLA != null) {
/*  640 */         loc = tempLA.getFirstLocation();
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/*  645 */     if (loc == null) {
/*  646 */       String[] hints = desc.getLocationHints();
/*  647 */       if (hints != null && hints.length > 0) {
/*  648 */         loc = hints[0];
/*      */       }
/*      */     } 
/*  651 */     String expandedLoc = XMLEntityManager.expandSystemId(loc, desc.getBaseSystemId(), false);
/*  652 */     desc.setLiteralSystemId(loc);
/*  653 */     desc.setExpandedSystemId(expandedLoc);
/*  654 */     return entityResolver.resolveEntity(desc);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void processExternalHints(String sl, String nsl, Map<String, LocationArray> locations, XMLErrorReporter er) {
/*  661 */     if (sl != null) {
/*      */       
/*      */       try {
/*      */ 
/*      */         
/*  666 */         XSAttributeDecl attrDecl = SchemaGrammar.SG_XSI.getGlobalAttributeDecl(SchemaSymbols.XSI_SCHEMALOCATION);
/*      */         
/*  668 */         attrDecl.fType.validate(sl, (ValidationContext)null, (ValidatedInfo)null);
/*  669 */         if (!tokenizeSchemaLocationStr(sl, locations))
/*      */         {
/*  671 */           er.reportError("http://www.w3.org/TR/xml-schema-1", "SchemaLocation", new Object[] { sl }, (short)0);
/*      */ 
/*      */         
/*      */         }
/*      */       
/*      */       }
/*  677 */       catch (InvalidDatatypeValueException ex) {
/*      */         
/*  679 */         er.reportError("http://www.w3.org/TR/xml-schema-1", ex
/*  680 */             .getKey(), ex.getArgs(), (short)0);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*  685 */     if (nsl != null) {
/*      */       
/*      */       try {
/*  688 */         XSAttributeDecl attrDecl = SchemaGrammar.SG_XSI.getGlobalAttributeDecl(SchemaSymbols.XSI_NONAMESPACESCHEMALOCATION);
/*  689 */         attrDecl.fType.validate(nsl, (ValidationContext)null, (ValidatedInfo)null);
/*  690 */         LocationArray la = locations.get(XMLSymbols.EMPTY_STRING);
/*  691 */         if (la == null) {
/*  692 */           la = new LocationArray();
/*  693 */           locations.put(XMLSymbols.EMPTY_STRING, la);
/*      */         } 
/*  695 */         la.addLocation(nsl);
/*      */       }
/*  697 */       catch (InvalidDatatypeValueException ex) {
/*      */         
/*  699 */         er.reportError("http://www.w3.org/TR/xml-schema-1", ex
/*  700 */             .getKey(), ex.getArgs(), (short)0);
/*      */       } 
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
/*      */   public static boolean tokenizeSchemaLocationStr(String schemaStr, Map<String, LocationArray> locations) {
/*  714 */     if (schemaStr != null) {
/*  715 */       StringTokenizer t = new StringTokenizer(schemaStr, " \n\t\r");
/*      */       
/*  717 */       while (t.hasMoreTokens()) {
/*  718 */         String namespace = t.nextToken();
/*  719 */         if (!t.hasMoreTokens()) {
/*  720 */           return false;
/*      */         }
/*  722 */         String location = t.nextToken();
/*  723 */         LocationArray la = locations.get(namespace);
/*  724 */         if (la == null) {
/*  725 */           la = new LocationArray();
/*  726 */           locations.put(namespace, la);
/*      */         } 
/*  728 */         la.addLocation(location);
/*      */       } 
/*      */     } 
/*  731 */     return true;
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
/*      */   private void processJAXPSchemaSource(Map<String, LocationArray> locationPairs) throws IOException {
/*  746 */     this.fJAXPProcessed = true;
/*  747 */     if (this.fJAXPSource == null) {
/*      */       return;
/*      */     }
/*      */     
/*  751 */     Class<?> componentType = this.fJAXPSource.getClass().getComponentType();
/*  752 */     XMLInputSource xis = null;
/*  753 */     String sid = null;
/*  754 */     if (componentType == null) {
/*      */       
/*  756 */       if (this.fJAXPSource instanceof InputStream || this.fJAXPSource instanceof InputSource) {
/*      */         
/*  758 */         SchemaGrammar schemaGrammar = (SchemaGrammar)this.fJAXPCache.get(this.fJAXPSource);
/*  759 */         if (schemaGrammar != null) {
/*  760 */           this.fGrammarBucket.putGrammar(schemaGrammar);
/*      */           return;
/*      */         } 
/*      */       } 
/*  764 */       this.fXSDDescription.reset();
/*  765 */       xis = xsdToXMLInputSource(this.fJAXPSource);
/*  766 */       sid = xis.getSystemId();
/*  767 */       this.fXSDDescription.fContextType = 3;
/*  768 */       if (sid != null) {
/*  769 */         this.fXSDDescription.setBaseSystemId(xis.getBaseSystemId());
/*  770 */         this.fXSDDescription.setLiteralSystemId(sid);
/*  771 */         this.fXSDDescription.setExpandedSystemId(sid);
/*  772 */         this.fXSDDescription.fLocationHints = new String[] { sid };
/*      */       } 
/*  774 */       SchemaGrammar g = loadSchema(this.fXSDDescription, xis, locationPairs);
/*      */       
/*  776 */       if (g != null) {
/*  777 */         if (this.fJAXPSource instanceof InputStream || this.fJAXPSource instanceof InputSource) {
/*      */           
/*  779 */           this.fJAXPCache.put(this.fJAXPSource, g);
/*  780 */           if (this.fIsCheckedFully) {
/*  781 */             XSConstraints.fullSchemaChecking(this.fGrammarBucket, this.fSubGroupHandler, this.fCMBuilder, this.fErrorReporter);
/*      */           }
/*      */         } 
/*  784 */         this.fGrammarBucket.putGrammar(g);
/*      */       }  return;
/*      */     } 
/*  787 */     if (componentType != Object.class && componentType != String.class && componentType != File.class && componentType != InputStream.class && componentType != InputSource.class)
/*      */     {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  794 */       throw new XMLConfigurationException(Status.NOT_SUPPORTED, "\"http://java.sun.com/xml/jaxp/properties/schemaSource\" property cannot have an array of type {" + componentType
/*      */           
/*  796 */           .getName() + "}. Possible types of the array supported are Object, String, File, InputStream, InputSource.");
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  803 */     Object[] objArr = (Object[])this.fJAXPSource;
/*      */     
/*  805 */     Vector<String> jaxpSchemaSourceNamespaces = new Vector();
/*  806 */     for (int i = 0; i < objArr.length; i++) {
/*  807 */       if (objArr[i] instanceof InputStream || objArr[i] instanceof InputSource) {
/*      */         
/*  809 */         SchemaGrammar g = (SchemaGrammar)this.fJAXPCache.get(objArr[i]);
/*  810 */         if (g != null) {
/*  811 */           this.fGrammarBucket.putGrammar(g);
/*      */           continue;
/*      */         } 
/*      */       } 
/*  815 */       this.fXSDDescription.reset();
/*  816 */       xis = xsdToXMLInputSource(objArr[i]);
/*  817 */       sid = xis.getSystemId();
/*  818 */       this.fXSDDescription.fContextType = 3;
/*  819 */       if (sid != null) {
/*  820 */         this.fXSDDescription.setBaseSystemId(xis.getBaseSystemId());
/*  821 */         this.fXSDDescription.setLiteralSystemId(sid);
/*  822 */         this.fXSDDescription.setExpandedSystemId(sid);
/*  823 */         this.fXSDDescription.fLocationHints = new String[] { sid };
/*      */       } 
/*  825 */       String targetNamespace = null;
/*      */       
/*  827 */       SchemaGrammar grammar = this.fSchemaHandler.parseSchema(xis, this.fXSDDescription, locationPairs);
/*      */       
/*  829 */       if (this.fIsCheckedFully) {
/*  830 */         XSConstraints.fullSchemaChecking(this.fGrammarBucket, this.fSubGroupHandler, this.fCMBuilder, this.fErrorReporter);
/*      */       }
/*  832 */       if (grammar != null) {
/*  833 */         targetNamespace = grammar.getTargetNamespace();
/*  834 */         if (jaxpSchemaSourceNamespaces.contains(targetNamespace))
/*      */         {
/*  836 */           throw new IllegalArgumentException(" When using array of Objects as the value of SCHEMA_SOURCE property , no two Schemas should share the same targetNamespace. ");
/*      */         }
/*      */ 
/*      */ 
/*      */         
/*  841 */         jaxpSchemaSourceNamespaces.add(targetNamespace);
/*      */         
/*  843 */         if (objArr[i] instanceof InputStream || objArr[i] instanceof InputSource)
/*      */         {
/*  845 */           this.fJAXPCache.put(objArr[i], grammar);
/*      */         }
/*  847 */         this.fGrammarBucket.putGrammar(grammar);
/*      */       } 
/*      */       continue;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private XMLInputSource xsdToXMLInputSource(Object val) {
/*  858 */     if (val instanceof String) {
/*      */ 
/*      */       
/*  861 */       String loc = (String)val;
/*  862 */       this.fXSDDescription.reset();
/*  863 */       this.fXSDDescription.setValues(null, loc, null, null);
/*  864 */       XMLInputSource xis = null;
/*      */       try {
/*  866 */         xis = this.fEntityManager.resolveEntity(this.fXSDDescription);
/*  867 */       } catch (IOException ex) {
/*  868 */         this.fErrorReporter.reportError("http://www.w3.org/TR/xml-schema-1", "schema_reference.4", new Object[] { loc }, (short)1);
/*      */       } 
/*      */ 
/*      */       
/*  872 */       if (xis == null)
/*      */       {
/*      */         
/*  875 */         return new XMLInputSource(null, loc, null);
/*      */       }
/*  877 */       return xis;
/*  878 */     }  if (val instanceof InputSource)
/*  879 */       return saxToXMLInputSource((InputSource)val); 
/*  880 */     if (val instanceof InputStream) {
/*  881 */       return new XMLInputSource(null, null, null, (InputStream)val, null);
/*      */     }
/*  883 */     if (val instanceof File) {
/*  884 */       File file = (File)val;
/*  885 */       InputStream is = null;
/*      */       try {
/*  887 */         is = new BufferedInputStream(new FileInputStream(file));
/*  888 */       } catch (FileNotFoundException ex) {
/*  889 */         this.fErrorReporter.reportError("http://www.w3.org/TR/xml-schema-1", "schema_reference.4", new Object[] { file
/*  890 */               .toString() }, (short)1);
/*      */       } 
/*      */       
/*  893 */       return new XMLInputSource(null, null, null, is, null);
/*      */     } 
/*  895 */     throw new XMLConfigurationException(Status.NOT_SUPPORTED, "\"http://java.sun.com/xml/jaxp/properties/schemaSource\" property cannot have a value of type {" + val
/*      */         
/*  897 */         .getClass().getName() + "}. Possible types of the value supported are String, File, InputStream, InputSource OR an array of these types.");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static XMLInputSource saxToXMLInputSource(InputSource sis) {
/*  906 */     String publicId = sis.getPublicId();
/*  907 */     String systemId = sis.getSystemId();
/*      */     
/*  909 */     Reader charStream = sis.getCharacterStream();
/*  910 */     if (charStream != null) {
/*  911 */       return new XMLInputSource(publicId, systemId, null, charStream, null);
/*      */     }
/*      */ 
/*      */     
/*  915 */     InputStream byteStream = sis.getByteStream();
/*  916 */     if (byteStream != null) {
/*  917 */       return new XMLInputSource(publicId, systemId, null, byteStream, sis
/*  918 */           .getEncoding());
/*      */     }
/*      */     
/*  921 */     return new XMLInputSource(publicId, systemId, null);
/*      */   }
/*      */   
/*      */   public static class LocationArray
/*      */   {
/*      */     int length;
/*  927 */     String[] locations = new String[2];
/*      */     
/*      */     public void resize(int oldLength, int newLength) {
/*  930 */       String[] temp = new String[newLength];
/*  931 */       System.arraycopy(this.locations, 0, temp, 0, Math.min(oldLength, newLength));
/*  932 */       this.locations = temp;
/*  933 */       this.length = Math.min(oldLength, newLength);
/*      */     }
/*      */     
/*      */     public void addLocation(String location) {
/*  937 */       if (this.length >= this.locations.length) {
/*  938 */         resize(this.length, Math.max(1, this.length * 2));
/*      */       }
/*  940 */       this.locations[this.length++] = location;
/*      */     }
/*      */     
/*      */     public String[] getLocationArray() {
/*  944 */       if (this.length < this.locations.length) {
/*  945 */         resize(this.locations.length, this.length);
/*      */       }
/*  947 */       return this.locations;
/*      */     }
/*      */     
/*      */     public String getFirstLocation() {
/*  951 */       return (this.length > 0) ? this.locations[0] : null;
/*      */     }
/*      */     
/*      */     public int getLength() {
/*  955 */       return this.length;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Boolean getFeatureDefault(String featureId) {
/*  964 */     if (featureId.equals("http://apache.org/xml/features/validation/schema/augment-psvi")) {
/*  965 */       return Boolean.TRUE;
/*      */     }
/*  967 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object getPropertyDefault(String propertyId) {
/*  975 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void reset(XMLComponentManager componentManager) throws XMLConfigurationException {
/*  983 */     XMLSecurityPropertyManager spm = (XMLSecurityPropertyManager)componentManager.getProperty("http://www.oracle.com/xml/jaxp/properties/xmlSecurityPropertyManager");
/*  984 */     if (spm == null) {
/*  985 */       spm = new XMLSecurityPropertyManager();
/*  986 */       setProperty("http://www.oracle.com/xml/jaxp/properties/xmlSecurityPropertyManager", spm);
/*      */     } 
/*      */     
/*  989 */     XMLSecurityManager sm = (XMLSecurityManager)componentManager.getProperty("http://apache.org/xml/properties/security-manager");
/*  990 */     if (sm == null) {
/*  991 */       setProperty("http://apache.org/xml/properties/security-manager", new XMLSecurityManager(true));
/*      */     }
/*  993 */     this.faccessExternalSchema = spm.getValue(XMLSecurityPropertyManager.Property.ACCESS_EXTERNAL_SCHEMA);
/*      */     
/*  995 */     this.fGrammarBucket.reset();
/*      */     
/*  997 */     this.fSubGroupHandler.reset();
/*      */     
/*  999 */     boolean parser_settings = componentManager.getFeature("http://apache.org/xml/features/internal/parser-settings", true);
/*      */     
/* 1001 */     if (!parser_settings || !this.fSettingsChanged) {
/*      */       
/* 1003 */       this.fJAXPProcessed = false;
/*      */       
/* 1005 */       initGrammarBucket();
/*      */       
/*      */       return;
/*      */     } 
/*      */     
/* 1010 */     this.fNodeFactory.reset(componentManager);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1015 */     this.fEntityManager = (XMLEntityManager)componentManager.getProperty("http://apache.org/xml/properties/internal/entity-manager");
/*      */ 
/*      */     
/* 1018 */     this.fErrorReporter = (XMLErrorReporter)componentManager.getProperty("http://apache.org/xml/properties/internal/error-reporter");
/*      */ 
/*      */     
/* 1021 */     SchemaDVFactory dvFactory = null;
/* 1022 */     dvFactory = this.fSchemaHandler.getDVFactory();
/* 1023 */     if (dvFactory == null) {
/* 1024 */       dvFactory = SchemaDVFactory.getInstance();
/* 1025 */       this.fSchemaHandler.setDVFactory(dvFactory);
/*      */     } 
/*      */     
/* 1028 */     boolean psvi = componentManager.getFeature("http://apache.org/xml/features/validation/schema/augment-psvi", false);
/*      */     
/* 1030 */     if (!psvi) {
/* 1031 */       if (this.fDeclPool != null) {
/* 1032 */         this.fDeclPool.reset();
/*      */       } else {
/*      */         
/* 1035 */         this.fDeclPool = new XSDeclarationPool();
/*      */       } 
/* 1037 */       this.fCMBuilder.setDeclPool(this.fDeclPool);
/* 1038 */       this.fSchemaHandler.setDeclPool(this.fDeclPool);
/* 1039 */       if (dvFactory instanceof SchemaDVFactoryImpl) {
/* 1040 */         this.fDeclPool.setDVFactory((SchemaDVFactoryImpl)dvFactory);
/* 1041 */         ((SchemaDVFactoryImpl)dvFactory).setDeclPool(this.fDeclPool);
/*      */       } 
/*      */     } else {
/* 1044 */       this.fCMBuilder.setDeclPool(null);
/* 1045 */       this.fSchemaHandler.setDeclPool(null);
/*      */     } 
/*      */ 
/*      */     
/*      */     try {
/* 1050 */       this.fExternalSchemas = (String)componentManager.getProperty("http://apache.org/xml/properties/schema/external-schemaLocation");
/* 1051 */       this.fExternalNoNSSchema = (String)componentManager.getProperty("http://apache.org/xml/properties/schema/external-noNamespaceSchemaLocation");
/* 1052 */     } catch (XMLConfigurationException e) {
/* 1053 */       this.fExternalSchemas = null;
/* 1054 */       this.fExternalNoNSSchema = null;
/*      */     } 
/*      */ 
/*      */     
/* 1058 */     this.fJAXPSource = componentManager.getProperty("http://java.sun.com/xml/jaxp/properties/schemaSource", null);
/* 1059 */     this.fJAXPProcessed = false;
/*      */ 
/*      */     
/* 1062 */     this.fGrammarPool = (XMLGrammarPool)componentManager.getProperty("http://apache.org/xml/properties/internal/grammar-pool", null);
/* 1063 */     initGrammarBucket();
/*      */     
/*      */     try {
/* 1066 */       boolean fatalError = componentManager.getFeature("http://apache.org/xml/features/continue-after-fatal-error", false);
/* 1067 */       if (!fatalError) {
/* 1068 */         this.fErrorReporter.setFeature("http://apache.org/xml/features/continue-after-fatal-error", fatalError);
/*      */       }
/* 1070 */     } catch (XMLConfigurationException xMLConfigurationException) {}
/*      */ 
/*      */     
/* 1073 */     this.fIsCheckedFully = componentManager.getFeature("http://apache.org/xml/features/validation/schema-full-checking", false);
/*      */ 
/*      */     
/* 1076 */     this.fSchemaHandler.setGenerateSyntheticAnnotations(componentManager.getFeature("http://apache.org/xml/features/generate-synthetic-annotations", false));
/* 1077 */     this.fSchemaHandler.reset(componentManager);
/*      */   }
/*      */   
/*      */   private void initGrammarBucket() {
/* 1081 */     if (this.fGrammarPool != null) {
/* 1082 */       Grammar[] initialGrammars = this.fGrammarPool.retrieveInitialGrammarSet("http://www.w3.org/2001/XMLSchema");
/* 1083 */       for (int i = 0; i < initialGrammars.length; i++) {
/*      */ 
/*      */         
/* 1086 */         if (!this.fGrammarBucket.putGrammar((SchemaGrammar)initialGrammars[i], true))
/*      */         {
/*      */           
/* 1089 */           this.fErrorReporter.reportError("http://www.w3.org/TR/xml-schema-1", "GrammarConflict", null, (short)0);
/*      */         }
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DOMConfiguration getConfig() {
/* 1102 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public XSModel load(LSInput is) {
/*      */     try {
/* 1110 */       Grammar g = loadGrammar(dom2xmlInputSource(is));
/* 1111 */       return ((XSGrammar)g).toXSModel();
/* 1112 */     } catch (Exception e) {
/* 1113 */       reportDOMFatalError(e);
/* 1114 */       return null;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public XSModel loadInputList(LSInputList is) {
/* 1122 */     int length = is.getLength();
/* 1123 */     SchemaGrammar[] gs = new SchemaGrammar[length];
/* 1124 */     for (int i = 0; i < length; i++) {
/*      */       try {
/* 1126 */         gs[i] = (SchemaGrammar)loadGrammar(dom2xmlInputSource(is.item(i)));
/* 1127 */       } catch (Exception e) {
/* 1128 */         reportDOMFatalError(e);
/* 1129 */         return null;
/*      */       } 
/*      */     } 
/* 1132 */     return new XSModelImpl(gs);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public XSModel loadURI(String uri) {
/*      */     try {
/* 1140 */       Grammar g = loadGrammar(new XMLInputSource(null, uri, null));
/* 1141 */       return ((XSGrammar)g).toXSModel();
/*      */     }
/* 1143 */     catch (Exception e) {
/* 1144 */       reportDOMFatalError(e);
/* 1145 */       return null;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public XSModel loadURIList(StringList uriList) {
/* 1153 */     int length = uriList.getLength();
/* 1154 */     SchemaGrammar[] gs = new SchemaGrammar[length];
/* 1155 */     for (int i = 0; i < length; i++) {
/*      */       try {
/* 1157 */         gs[i] = (SchemaGrammar)
/* 1158 */           loadGrammar(new XMLInputSource(null, uriList.item(i), null));
/* 1159 */       } catch (Exception e) {
/* 1160 */         reportDOMFatalError(e);
/* 1161 */         return null;
/*      */       } 
/*      */     } 
/* 1164 */     return new XSModelImpl(gs);
/*      */   }
/*      */   
/*      */   void reportDOMFatalError(Exception e) {
/* 1168 */     if (this.fErrorHandler != null) {
/* 1169 */       DOMErrorImpl error = new DOMErrorImpl();
/* 1170 */       error.fException = e;
/* 1171 */       error.fMessage = e.getMessage();
/* 1172 */       error.fSeverity = 3;
/* 1173 */       this.fErrorHandler.getErrorHandler().handleError(error);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean canSetParameter(String name, Object value) {
/* 1181 */     if (value instanceof Boolean) {
/* 1182 */       if (name.equals("validate") || name
/* 1183 */         .equals("http://apache.org/xml/features/validation/schema-full-checking") || name
/* 1184 */         .equals("http://apache.org/xml/features/validate-annotations") || name
/* 1185 */         .equals("http://apache.org/xml/features/continue-after-fatal-error") || name
/* 1186 */         .equals("http://apache.org/xml/features/allow-java-encodings") || name
/* 1187 */         .equals("http://apache.org/xml/features/standard-uri-conformant") || name
/* 1188 */         .equals("http://apache.org/xml/features/generate-synthetic-annotations") || name
/* 1189 */         .equals("http://apache.org/xml/features/honour-all-schemaLocations") || name
/* 1190 */         .equals("http://apache.org/xml/features/namespace-growth") || name
/* 1191 */         .equals("http://apache.org/xml/features/internal/tolerate-duplicates") || name
/* 1192 */         .equals("jdk.xml.overrideDefaultParser")) {
/* 1193 */         return true;
/*      */       }
/*      */       
/* 1196 */       return false;
/*      */     } 
/* 1198 */     if (name.equals("error-handler") || name
/* 1199 */       .equals("resource-resolver") || name
/* 1200 */       .equals("http://apache.org/xml/properties/internal/symbol-table") || name
/* 1201 */       .equals("http://apache.org/xml/properties/internal/error-reporter") || name
/* 1202 */       .equals("http://apache.org/xml/properties/internal/error-handler") || name
/* 1203 */       .equals("http://apache.org/xml/properties/internal/entity-resolver") || name
/* 1204 */       .equals("http://apache.org/xml/properties/internal/grammar-pool") || name
/* 1205 */       .equals("http://apache.org/xml/properties/schema/external-schemaLocation") || name
/* 1206 */       .equals("http://apache.org/xml/properties/schema/external-noNamespaceSchemaLocation") || name
/* 1207 */       .equals("http://java.sun.com/xml/jaxp/properties/schemaSource") || name
/* 1208 */       .equals("http://apache.org/xml/properties/internal/validation/schema/dv-factory")) {
/* 1209 */       return true;
/*      */     }
/* 1211 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object getParameter(String name) throws DOMException {
/* 1219 */     if (name.equals("error-handler")) {
/* 1220 */       return (this.fErrorHandler != null) ? this.fErrorHandler.getErrorHandler() : null;
/*      */     }
/* 1222 */     if (name.equals("resource-resolver")) {
/* 1223 */       return (this.fResourceResolver != null) ? this.fResourceResolver.getEntityResolver() : null;
/*      */     }
/*      */     
/*      */     try {
/* 1227 */       boolean feature = getFeature(name);
/* 1228 */       return feature ? Boolean.TRUE : Boolean.FALSE;
/* 1229 */     } catch (Exception e) {
/*      */       
/*      */       try {
/* 1232 */         Object property = getProperty(name);
/* 1233 */         return property;
/* 1234 */       } catch (Exception ex) {
/*      */         
/* 1236 */         String msg = DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "FEATURE_NOT_SUPPORTED", new Object[] { name });
/*      */ 
/*      */ 
/*      */         
/* 1240 */         throw new DOMException((short)9, msg);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DOMStringList getParameterNames() {
/* 1249 */     if (this.fRecognizedParameters == null) {
/* 1250 */       Vector<String> v = new Vector();
/* 1251 */       v.add("validate");
/* 1252 */       v.add("error-handler");
/* 1253 */       v.add("resource-resolver");
/* 1254 */       v.add("http://apache.org/xml/properties/internal/symbol-table");
/* 1255 */       v.add("http://apache.org/xml/properties/internal/error-reporter");
/* 1256 */       v.add("http://apache.org/xml/properties/internal/error-handler");
/* 1257 */       v.add("http://apache.org/xml/properties/internal/entity-resolver");
/* 1258 */       v.add("http://apache.org/xml/properties/internal/grammar-pool");
/* 1259 */       v.add("http://apache.org/xml/properties/schema/external-schemaLocation");
/* 1260 */       v.add("http://apache.org/xml/properties/schema/external-noNamespaceSchemaLocation");
/* 1261 */       v.add("http://java.sun.com/xml/jaxp/properties/schemaSource");
/* 1262 */       v.add("http://apache.org/xml/features/validation/schema-full-checking");
/* 1263 */       v.add("http://apache.org/xml/features/continue-after-fatal-error");
/* 1264 */       v.add("http://apache.org/xml/features/allow-java-encodings");
/* 1265 */       v.add("http://apache.org/xml/features/standard-uri-conformant");
/* 1266 */       v.add("http://apache.org/xml/features/validate-annotations");
/* 1267 */       v.add("http://apache.org/xml/features/generate-synthetic-annotations");
/* 1268 */       v.add("http://apache.org/xml/features/honour-all-schemaLocations");
/* 1269 */       v.add("http://apache.org/xml/features/namespace-growth");
/* 1270 */       v.add("http://apache.org/xml/features/internal/tolerate-duplicates");
/* 1271 */       v.add("jdk.xml.overrideDefaultParser");
/* 1272 */       this.fRecognizedParameters = new DOMStringListImpl(v);
/*      */     } 
/* 1274 */     return this.fRecognizedParameters;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setParameter(String name, Object value) throws DOMException {
/* 1281 */     if (value instanceof Boolean) {
/* 1282 */       boolean state = ((Boolean)value).booleanValue();
/* 1283 */       if (name.equals("validate") && state) {
/*      */         return;
/*      */       }
/*      */       try {
/* 1287 */         setFeature(name, state);
/* 1288 */       } catch (Exception e) {
/*      */         
/* 1290 */         String msg = DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "FEATURE_NOT_SUPPORTED", new Object[] { name });
/*      */ 
/*      */ 
/*      */         
/* 1294 */         throw new DOMException((short)9, msg);
/*      */       } 
/*      */       return;
/*      */     } 
/* 1298 */     if (name.equals("error-handler")) {
/* 1299 */       if (value instanceof DOMErrorHandler) {
/*      */         try {
/* 1301 */           this.fErrorHandler = new DOMErrorHandlerWrapper((DOMErrorHandler)value);
/* 1302 */           setErrorHandler(this.fErrorHandler);
/* 1303 */         } catch (XMLConfigurationException xMLConfigurationException) {}
/*      */       
/*      */       }
/*      */       else {
/*      */         
/* 1308 */         String msg = DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "FEATURE_NOT_SUPPORTED", new Object[] { name });
/*      */ 
/*      */ 
/*      */         
/* 1312 */         throw new DOMException((short)9, msg);
/*      */       } 
/*      */       
/*      */       return;
/*      */     } 
/* 1317 */     if (name.equals("resource-resolver")) {
/* 1318 */       if (value instanceof LSResourceResolver) {
/*      */         try {
/* 1320 */           this.fResourceResolver = new DOMEntityResolverWrapper((LSResourceResolver)value);
/* 1321 */           setEntityResolver(this.fResourceResolver);
/*      */         }
/* 1323 */         catch (XMLConfigurationException xMLConfigurationException) {}
/*      */       }
/*      */       else {
/*      */         
/* 1327 */         String msg = DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "FEATURE_NOT_SUPPORTED", new Object[] { name });
/*      */ 
/*      */ 
/*      */         
/* 1331 */         throw new DOMException((short)9, msg);
/*      */       } 
/*      */       
/*      */       return;
/*      */     } 
/*      */     try {
/* 1337 */       setProperty(name, value);
/* 1338 */     } catch (Exception ex) {
/*      */ 
/*      */       
/* 1341 */       String msg = DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "FEATURE_NOT_SUPPORTED", new Object[] { name });
/*      */ 
/*      */ 
/*      */       
/* 1345 */       throw new DOMException((short)9, msg);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   XMLInputSource dom2xmlInputSource(LSInput is) {
/* 1353 */     XMLInputSource xis = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1364 */     if (is.getCharacterStream() != null) {
/*      */       
/* 1366 */       xis = new XMLInputSource(is.getPublicId(), is.getSystemId(), is.getBaseURI(), is.getCharacterStream(), "UTF-16");
/*      */ 
/*      */     
/*      */     }
/* 1370 */     else if (is.getByteStream() != null) {
/*      */ 
/*      */       
/* 1373 */       xis = new XMLInputSource(is.getPublicId(), is.getSystemId(), is.getBaseURI(), is.getByteStream(), is.getEncoding());
/*      */ 
/*      */     
/*      */     }
/* 1377 */     else if (is.getStringData() != null && is.getStringData().length() != 0) {
/*      */       
/* 1379 */       xis = new XMLInputSource(is.getPublicId(), is.getSystemId(), is.getBaseURI(), new StringReader(is.getStringData()), "UTF-16");
/*      */     
/*      */     }
/*      */     else {
/*      */ 
/*      */       
/* 1385 */       xis = new XMLInputSource(is.getPublicId(), is.getSystemId(), is.getBaseURI());
/*      */     } 
/*      */     
/* 1388 */     return xis;
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xerces/internal/impl/xs/XMLSchemaLoader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */