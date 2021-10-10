/*      */ package com.sun.org.apache.xerces.internal.dom;
/*      */ 
/*      */ import com.sun.org.apache.xerces.internal.impl.Constants;
/*      */ import com.sun.org.apache.xerces.internal.impl.XMLEntityManager;
/*      */ import com.sun.org.apache.xerces.internal.impl.XMLErrorReporter;
/*      */ import com.sun.org.apache.xerces.internal.impl.dv.DTDDVFactory;
/*      */ import com.sun.org.apache.xerces.internal.impl.msg.XMLMessageFormatter;
/*      */ import com.sun.org.apache.xerces.internal.impl.validation.ValidationManager;
/*      */ import com.sun.org.apache.xerces.internal.util.DOMEntityResolverWrapper;
/*      */ import com.sun.org.apache.xerces.internal.util.DOMErrorHandlerWrapper;
/*      */ import com.sun.org.apache.xerces.internal.util.MessageFormatter;
/*      */ import com.sun.org.apache.xerces.internal.util.ParserConfigurationSettings;
/*      */ import com.sun.org.apache.xerces.internal.util.PropertyState;
/*      */ import com.sun.org.apache.xerces.internal.util.SymbolTable;
/*      */ import com.sun.org.apache.xerces.internal.utils.ObjectFactory;
/*      */ import com.sun.org.apache.xerces.internal.utils.XMLSecurityManager;
/*      */ import com.sun.org.apache.xerces.internal.utils.XMLSecurityPropertyManager;
/*      */ import com.sun.org.apache.xerces.internal.xni.XMLDTDContentModelHandler;
/*      */ import com.sun.org.apache.xerces.internal.xni.XMLDTDHandler;
/*      */ import com.sun.org.apache.xerces.internal.xni.XMLDocumentHandler;
/*      */ import com.sun.org.apache.xerces.internal.xni.XNIException;
/*      */ import com.sun.org.apache.xerces.internal.xni.parser.XMLComponent;
/*      */ import com.sun.org.apache.xerces.internal.xni.parser.XMLComponentManager;
/*      */ import com.sun.org.apache.xerces.internal.xni.parser.XMLConfigurationException;
/*      */ import com.sun.org.apache.xerces.internal.xni.parser.XMLEntityResolver;
/*      */ import com.sun.org.apache.xerces.internal.xni.parser.XMLErrorHandler;
/*      */ import com.sun.org.apache.xerces.internal.xni.parser.XMLInputSource;
/*      */ import com.sun.org.apache.xerces.internal.xni.parser.XMLParserConfiguration;
/*      */ import java.io.IOException;
/*      */ import java.util.ArrayList;
/*      */ import java.util.HashMap;
/*      */ import java.util.Locale;
/*      */ import java.util.Vector;
/*      */ import jdk.xml.internal.JdkXmlUtils;
/*      */ import org.w3c.dom.DOMConfiguration;
/*      */ import org.w3c.dom.DOMErrorHandler;
/*      */ import org.w3c.dom.DOMException;
/*      */ import org.w3c.dom.DOMStringList;
/*      */ import org.w3c.dom.ls.LSResourceResolver;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class DOMConfigurationImpl
/*      */   extends ParserConfigurationSettings
/*      */   implements XMLParserConfiguration, DOMConfiguration
/*      */ {
/*      */   protected static final String XERCES_VALIDATION = "http://xml.org/sax/features/validation";
/*      */   protected static final String XERCES_NAMESPACES = "http://xml.org/sax/features/namespaces";
/*      */   protected static final String SCHEMA = "http://apache.org/xml/features/validation/schema";
/*      */   protected static final String SCHEMA_FULL_CHECKING = "http://apache.org/xml/features/validation/schema-full-checking";
/*      */   protected static final String DYNAMIC_VALIDATION = "http://apache.org/xml/features/validation/dynamic";
/*      */   protected static final String NORMALIZE_DATA = "http://apache.org/xml/features/validation/schema/normalized-value";
/*      */   protected static final String SEND_PSVI = "http://apache.org/xml/features/validation/schema/augment-psvi";
/*      */   protected static final String DTD_VALIDATOR_FACTORY_PROPERTY = "http://apache.org/xml/properties/internal/datatype-validator-factory";
/*      */   protected static final String NAMESPACE_GROWTH = "http://apache.org/xml/features/namespace-growth";
/*      */   protected static final String TOLERATE_DUPLICATES = "http://apache.org/xml/features/internal/tolerate-duplicates";
/*      */   protected static final String ENTITY_MANAGER = "http://apache.org/xml/properties/internal/entity-manager";
/*      */   protected static final String ERROR_REPORTER = "http://apache.org/xml/properties/internal/error-reporter";
/*      */   protected static final String XML_STRING = "http://xml.org/sax/properties/xml-string";
/*      */   protected static final String SYMBOL_TABLE = "http://apache.org/xml/properties/internal/symbol-table";
/*      */   protected static final String GRAMMAR_POOL = "http://apache.org/xml/properties/internal/grammar-pool";
/*      */   protected static final String ERROR_HANDLER = "http://apache.org/xml/properties/internal/error-handler";
/*      */   protected static final String ENTITY_RESOLVER = "http://apache.org/xml/properties/internal/entity-resolver";
/*      */   protected static final String JAXP_SCHEMA_LANGUAGE = "http://java.sun.com/xml/jaxp/properties/schemaLanguage";
/*      */   protected static final String JAXP_SCHEMA_SOURCE = "http://java.sun.com/xml/jaxp/properties/schemaSource";
/*      */   protected static final String VALIDATION_MANAGER = "http://apache.org/xml/properties/internal/validation-manager";
/*      */   protected static final String SCHEMA_DV_FACTORY = "http://apache.org/xml/properties/internal/validation/schema/dv-factory";
/*      */   private static final String SECURITY_MANAGER = "http://apache.org/xml/properties/security-manager";
/*      */   private static final String XML_SECURITY_PROPERTY_MANAGER = "http://www.oracle.com/xml/jaxp/properties/xmlSecurityPropertyManager";
/*      */   XMLDocumentHandler fDocumentHandler;
/*  173 */   protected short features = 0;
/*      */   
/*      */   protected static final short NAMESPACES = 1;
/*      */   
/*      */   protected static final short DTNORMALIZATION = 2;
/*      */   
/*      */   protected static final short ENTITIES = 4;
/*      */   
/*      */   protected static final short CDATA = 8;
/*      */   
/*      */   protected static final short SPLITCDATA = 16;
/*      */   
/*      */   protected static final short COMMENTS = 32;
/*      */   
/*      */   protected static final short VALIDATE = 64;
/*      */   
/*      */   protected static final short PSVI = 128;
/*      */   
/*      */   protected static final short WELLFORMED = 256;
/*      */   
/*      */   protected static final short NSDECL = 512;
/*      */   
/*      */   protected static final short INFOSET_TRUE_PARAMS = 801;
/*      */   
/*      */   protected static final short INFOSET_FALSE_PARAMS = 14;
/*      */   
/*      */   protected static final short INFOSET_MASK = 815;
/*      */   
/*      */   protected SymbolTable fSymbolTable;
/*      */   protected ArrayList fComponents;
/*      */   protected ValidationManager fValidationManager;
/*      */   protected Locale fLocale;
/*      */   protected XMLErrorReporter fErrorReporter;
/*  206 */   protected final DOMErrorHandlerWrapper fErrorHandlerWrapper = new DOMErrorHandlerWrapper();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private DOMStringList fRecognizedParameters;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected DOMConfigurationImpl() {
/*  220 */     this((SymbolTable)null, (XMLComponentManager)null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected DOMConfigurationImpl(SymbolTable symbolTable) {
/*  229 */     this(symbolTable, (XMLComponentManager)null);
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
/*      */   protected DOMConfigurationImpl(SymbolTable symbolTable, XMLComponentManager parentSettings) {
/*  241 */     super(parentSettings);
/*      */ 
/*      */ 
/*      */     
/*  245 */     this.fFeatures = new HashMap<>();
/*  246 */     this.fProperties = new HashMap<>();
/*      */ 
/*      */     
/*  249 */     String[] recognizedFeatures = { "http://xml.org/sax/features/validation", "http://xml.org/sax/features/namespaces", "http://apache.org/xml/features/validation/schema", "http://apache.org/xml/features/validation/schema-full-checking", "http://apache.org/xml/features/validation/dynamic", "http://apache.org/xml/features/validation/schema/normalized-value", "http://apache.org/xml/features/validation/schema/augment-psvi", "http://apache.org/xml/features/namespace-growth", "http://apache.org/xml/features/internal/tolerate-duplicates", "jdk.xml.overrideDefaultParser" };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  261 */     addRecognizedFeatures(recognizedFeatures);
/*      */ 
/*      */     
/*  264 */     setFeature("http://xml.org/sax/features/validation", false);
/*  265 */     setFeature("http://apache.org/xml/features/validation/schema", false);
/*  266 */     setFeature("http://apache.org/xml/features/validation/schema-full-checking", false);
/*  267 */     setFeature("http://apache.org/xml/features/validation/dynamic", false);
/*  268 */     setFeature("http://apache.org/xml/features/validation/schema/normalized-value", false);
/*  269 */     setFeature("http://xml.org/sax/features/namespaces", true);
/*  270 */     setFeature("http://apache.org/xml/features/validation/schema/augment-psvi", true);
/*  271 */     setFeature("http://apache.org/xml/features/namespace-growth", false);
/*  272 */     setFeature("jdk.xml.overrideDefaultParser", JdkXmlUtils.OVERRIDE_PARSER_DEFAULT);
/*      */ 
/*      */     
/*  275 */     String[] recognizedProperties = { "http://xml.org/sax/properties/xml-string", "http://apache.org/xml/properties/internal/symbol-table", "http://apache.org/xml/properties/internal/error-handler", "http://apache.org/xml/properties/internal/entity-resolver", "http://apache.org/xml/properties/internal/error-reporter", "http://apache.org/xml/properties/internal/entity-manager", "http://apache.org/xml/properties/internal/validation-manager", "http://apache.org/xml/properties/internal/grammar-pool", "http://java.sun.com/xml/jaxp/properties/schemaSource", "http://java.sun.com/xml/jaxp/properties/schemaLanguage", "http://apache.org/xml/properties/internal/datatype-validator-factory", "http://apache.org/xml/properties/internal/validation/schema/dv-factory", "http://apache.org/xml/properties/security-manager", "http://www.oracle.com/xml/jaxp/properties/xmlSecurityPropertyManager" };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  291 */     addRecognizedProperties(recognizedProperties);
/*      */ 
/*      */     
/*  294 */     this.features = (short)(this.features | 0x1);
/*  295 */     this.features = (short)(this.features | 0x4);
/*  296 */     this.features = (short)(this.features | 0x20);
/*  297 */     this.features = (short)(this.features | 0x8);
/*  298 */     this.features = (short)(this.features | 0x10);
/*  299 */     this.features = (short)(this.features | 0x100);
/*  300 */     this.features = (short)(this.features | 0x200);
/*      */     
/*  302 */     if (symbolTable == null) {
/*  303 */       symbolTable = new SymbolTable();
/*      */     }
/*  305 */     this.fSymbolTable = symbolTable;
/*      */     
/*  307 */     this.fComponents = new ArrayList();
/*      */     
/*  309 */     setProperty("http://apache.org/xml/properties/internal/symbol-table", this.fSymbolTable);
/*  310 */     this.fErrorReporter = new XMLErrorReporter();
/*  311 */     setProperty("http://apache.org/xml/properties/internal/error-reporter", this.fErrorReporter);
/*  312 */     addComponent(this.fErrorReporter);
/*      */     
/*  314 */     setProperty("http://apache.org/xml/properties/internal/datatype-validator-factory", DTDDVFactory.getInstance());
/*      */     
/*  316 */     XMLEntityManager manager = new XMLEntityManager();
/*  317 */     setProperty("http://apache.org/xml/properties/internal/entity-manager", manager);
/*  318 */     addComponent(manager);
/*      */     
/*  320 */     this.fValidationManager = createValidationManager();
/*  321 */     setProperty("http://apache.org/xml/properties/internal/validation-manager", this.fValidationManager);
/*      */     
/*  323 */     setProperty("http://apache.org/xml/properties/security-manager", new XMLSecurityManager(true));
/*      */     
/*  325 */     setProperty("http://www.oracle.com/xml/jaxp/properties/xmlSecurityPropertyManager", new XMLSecurityPropertyManager());
/*      */ 
/*      */ 
/*      */     
/*  329 */     if (this.fErrorReporter.getMessageFormatter("http://www.w3.org/TR/1998/REC-xml-19980210") == null) {
/*  330 */       XMLMessageFormatter xmft = new XMLMessageFormatter();
/*  331 */       this.fErrorReporter.putMessageFormatter("http://www.w3.org/TR/1998/REC-xml-19980210", xmft);
/*  332 */       this.fErrorReporter.putMessageFormatter("http://www.w3.org/TR/1999/REC-xml-names-19990114", xmft);
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  338 */     if (this.fErrorReporter.getMessageFormatter("http://www.w3.org/TR/xml-schema-1") == null) {
/*  339 */       MessageFormatter xmft = null;
/*      */       
/*      */       try {
/*  342 */         xmft = (MessageFormatter)ObjectFactory.newInstance("com.sun.org.apache.xerces.internal.impl.xs.XSMessageFormatter", true);
/*  343 */       } catch (Exception exception) {}
/*      */ 
/*      */       
/*  346 */       if (xmft != null) {
/*  347 */         this.fErrorReporter.putMessageFormatter("http://www.w3.org/TR/xml-schema-1", xmft);
/*      */       }
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/*  354 */       setLocale(Locale.getDefault());
/*      */     }
/*  356 */     catch (XNIException xNIException) {}
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
/*      */   public void parse(XMLInputSource inputSource) throws XNIException, IOException {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setDocumentHandler(XMLDocumentHandler documentHandler) {
/*  405 */     this.fDocumentHandler = documentHandler;
/*      */   }
/*      */ 
/*      */   
/*      */   public XMLDocumentHandler getDocumentHandler() {
/*  410 */     return this.fDocumentHandler;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setDTDHandler(XMLDTDHandler dtdHandler) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public XMLDTDHandler getDTDHandler() {
/*  424 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setDTDContentModelHandler(XMLDTDContentModelHandler handler) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public XMLDTDContentModelHandler getDTDContentModelHandler() {
/*  439 */     return null;
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
/*  450 */     if (resolver != null) {
/*  451 */       this.fProperties.put("http://apache.org/xml/properties/internal/entity-resolver", resolver);
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
/*      */   public XMLEntityResolver getEntityResolver() {
/*  463 */     return (XMLEntityResolver)this.fProperties.get("http://apache.org/xml/properties/internal/entity-resolver");
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
/*  485 */     if (errorHandler != null) {
/*  486 */       this.fProperties.put("http://apache.org/xml/properties/internal/error-handler", errorHandler);
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
/*      */   public XMLErrorHandler getErrorHandler() {
/*  498 */     return (XMLErrorHandler)this.fProperties.get("http://apache.org/xml/properties/internal/error-handler");
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
/*  518 */     super.setFeature(featureId, state);
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
/*      */   public void setProperty(String propertyId, Object value) throws XMLConfigurationException {
/*  532 */     super.setProperty(propertyId, value);
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
/*  545 */     this.fLocale = locale;
/*  546 */     this.fErrorReporter.setLocale(locale);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Locale getLocale() {
/*  552 */     return this.fLocale;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setParameter(String name, Object value) throws DOMException {
/*  560 */     boolean found = true;
/*      */ 
/*      */ 
/*      */     
/*  564 */     if (value instanceof Boolean) {
/*  565 */       boolean state = ((Boolean)value).booleanValue();
/*      */       
/*  567 */       if (name.equalsIgnoreCase("comments")) {
/*  568 */         this.features = (short)(state ? (this.features | 0x20) : (this.features & 0xFFFFFFDF));
/*      */       }
/*  570 */       else if (name.equalsIgnoreCase("datatype-normalization")) {
/*  571 */         setFeature("http://apache.org/xml/features/validation/schema/normalized-value", state);
/*  572 */         this.features = (short)(state ? (this.features | 0x2) : (this.features & 0xFFFFFFFD));
/*      */         
/*  574 */         if (state) {
/*  575 */           this.features = (short)(this.features | 0x40);
/*      */         }
/*      */       }
/*  578 */       else if (name.equalsIgnoreCase("namespaces")) {
/*  579 */         this.features = (short)(state ? (this.features | 0x1) : (this.features & 0xFFFFFFFE));
/*      */       }
/*  581 */       else if (name.equalsIgnoreCase("cdata-sections")) {
/*  582 */         this.features = (short)(state ? (this.features | 0x8) : (this.features & 0xFFFFFFF7));
/*      */       }
/*  584 */       else if (name.equalsIgnoreCase("entities")) {
/*  585 */         this.features = (short)(state ? (this.features | 0x4) : (this.features & 0xFFFFFFFB));
/*      */       }
/*  587 */       else if (name.equalsIgnoreCase("split-cdata-sections")) {
/*  588 */         this.features = (short)(state ? (this.features | 0x10) : (this.features & 0xFFFFFFEF));
/*      */       }
/*  590 */       else if (name.equalsIgnoreCase("validate")) {
/*  591 */         this.features = (short)(state ? (this.features | 0x40) : (this.features & 0xFFFFFFBF));
/*      */       }
/*  593 */       else if (name.equalsIgnoreCase("well-formed")) {
/*  594 */         this.features = (short)(state ? (this.features | 0x100) : (this.features & 0xFFFFFEFF));
/*      */       }
/*  596 */       else if (name.equalsIgnoreCase("namespace-declarations")) {
/*  597 */         this.features = (short)(state ? (this.features | 0x200) : (this.features & 0xFFFFFDFF));
/*      */       }
/*  599 */       else if (name.equalsIgnoreCase("infoset")) {
/*      */         
/*  601 */         if (state) {
/*  602 */           this.features = (short)(this.features | 0x321);
/*  603 */           this.features = (short)(this.features & 0xFFFFFFF1);
/*  604 */           setFeature("http://apache.org/xml/features/validation/schema/normalized-value", false);
/*      */         }
/*      */       
/*  607 */       } else if (name.equalsIgnoreCase("normalize-characters") || name
/*  608 */         .equalsIgnoreCase("canonical-form") || name
/*  609 */         .equalsIgnoreCase("validate-if-schema") || name
/*  610 */         .equalsIgnoreCase("check-character-normalization")) {
/*      */         
/*  612 */         if (state)
/*      */         {
/*  614 */           String msg = DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "FEATURE_NOT_SUPPORTED", new Object[] { name });
/*      */ 
/*      */ 
/*      */           
/*  618 */           throw new DOMException((short)9, msg);
/*      */         }
/*      */       
/*  621 */       } else if (name.equalsIgnoreCase("element-content-whitespace")) {
/*  622 */         if (!state)
/*      */         {
/*  624 */           String msg = DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "FEATURE_NOT_SUPPORTED", new Object[] { name });
/*      */ 
/*      */ 
/*      */           
/*  628 */           throw new DOMException((short)9, msg);
/*      */         }
/*      */       
/*  631 */       } else if (name.equalsIgnoreCase("http://apache.org/xml/features/validation/schema/augment-psvi")) {
/*      */ 
/*      */ 
/*      */         
/*  635 */         if (!state)
/*      */         {
/*  637 */           String msg = DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "FEATURE_NOT_SUPPORTED", new Object[] { name });
/*      */ 
/*      */ 
/*      */           
/*  641 */           throw new DOMException((short)9, msg);
/*      */         }
/*      */       
/*  644 */       } else if (name.equalsIgnoreCase("psvi")) {
/*  645 */         this.features = (short)(state ? (this.features | 0x80) : (this.features & 0xFFFFFF7F));
/*      */       } else {
/*      */         
/*  648 */         found = false;
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  661 */     if (!found || !(value instanceof Boolean)) {
/*  662 */       found = true;
/*      */       
/*  664 */       if (name.equalsIgnoreCase("error-handler")) {
/*  665 */         if (value instanceof DOMErrorHandler || value == null) {
/*  666 */           this.fErrorHandlerWrapper.setErrorHandler((DOMErrorHandler)value);
/*  667 */           setErrorHandler(this.fErrorHandlerWrapper);
/*      */         
/*      */         }
/*      */         else {
/*      */ 
/*      */           
/*  673 */           String msg = DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "TYPE_MISMATCH_ERR", new Object[] { name });
/*      */ 
/*      */ 
/*      */           
/*  677 */           throw new DOMException((short)17, msg);
/*      */         }
/*      */       
/*  680 */       } else if (name.equalsIgnoreCase("resource-resolver")) {
/*  681 */         if (value instanceof LSResourceResolver || value == null) {
/*      */           try {
/*  683 */             setEntityResolver(new DOMEntityResolverWrapper((LSResourceResolver)value));
/*      */           }
/*  685 */           catch (XMLConfigurationException xMLConfigurationException) {}
/*      */         
/*      */         }
/*      */         else {
/*      */           
/*  690 */           String msg = DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "TYPE_MISMATCH_ERR", new Object[] { name });
/*      */ 
/*      */ 
/*      */           
/*  694 */           throw new DOMException((short)17, msg);
/*      */         }
/*      */       
/*      */       }
/*  698 */       else if (name.equalsIgnoreCase("schema-location")) {
/*  699 */         if (value instanceof String || value == null)
/*      */         {
/*      */           try {
/*  702 */             setProperty("http://java.sun.com/xml/jaxp/properties/schemaSource", value);
/*      */ 
/*      */           
/*      */           }
/*  706 */           catch (XMLConfigurationException xMLConfigurationException) {}
/*      */         
/*      */         }
/*      */         else
/*      */         {
/*  711 */           String msg = DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "TYPE_MISMATCH_ERR", new Object[] { name });
/*      */ 
/*      */ 
/*      */           
/*  715 */           throw new DOMException((short)17, msg);
/*      */         }
/*      */       
/*      */       }
/*  719 */       else if (name.equalsIgnoreCase("schema-type")) {
/*  720 */         if (value instanceof String || value == null) {
/*      */           try {
/*  722 */             if (value == null) {
/*  723 */               setProperty("http://java.sun.com/xml/jaxp/properties/schemaLanguage", (Object)null);
/*      */ 
/*      */             
/*      */             }
/*  727 */             else if (value.equals(Constants.NS_XMLSCHEMA)) {
/*      */               
/*  729 */               setProperty("http://java.sun.com/xml/jaxp/properties/schemaLanguage", Constants.NS_XMLSCHEMA);
/*      */ 
/*      */             
/*      */             }
/*  733 */             else if (value.equals(Constants.NS_DTD)) {
/*      */               
/*  735 */               setProperty("http://java.sun.com/xml/jaxp/properties/schemaLanguage", Constants.NS_DTD);
/*      */             }
/*      */           
/*      */           }
/*  739 */           catch (XMLConfigurationException xMLConfigurationException) {}
/*      */         }
/*      */         else {
/*      */           
/*  743 */           String msg = DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "TYPE_MISMATCH_ERR", new Object[] { name });
/*      */ 
/*      */ 
/*      */           
/*  747 */           throw new DOMException((short)17, msg);
/*      */         }
/*      */       
/*      */       }
/*  751 */       else if (name.equalsIgnoreCase("http://apache.org/xml/properties/internal/symbol-table")) {
/*      */         
/*  753 */         if (value instanceof SymbolTable) {
/*  754 */           setProperty("http://apache.org/xml/properties/internal/symbol-table", value);
/*      */         
/*      */         }
/*      */         else {
/*      */           
/*  759 */           String msg = DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "TYPE_MISMATCH_ERR", new Object[] { name });
/*      */ 
/*      */ 
/*      */           
/*  763 */           throw new DOMException((short)17, msg);
/*      */         }
/*      */       
/*  766 */       } else if (name.equalsIgnoreCase("http://apache.org/xml/properties/internal/grammar-pool")) {
/*  767 */         if (value instanceof com.sun.org.apache.xerces.internal.xni.grammars.XMLGrammarPool) {
/*  768 */           setProperty("http://apache.org/xml/properties/internal/grammar-pool", value);
/*      */         
/*      */         }
/*      */         else {
/*      */           
/*  773 */           String msg = DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "TYPE_MISMATCH_ERR", new Object[] { name });
/*      */ 
/*      */ 
/*      */           
/*  777 */           throw new DOMException((short)17, msg);
/*      */         
/*      */         }
/*      */ 
/*      */       
/*      */       }
/*      */       else {
/*      */         
/*  785 */         String msg = DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "FEATURE_NOT_FOUND", new Object[] { name });
/*      */ 
/*      */ 
/*      */         
/*  789 */         throw new DOMException((short)8, msg);
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
/*      */ 
/*      */   
/*      */   public Object getParameter(String name) throws DOMException {
/*  805 */     if (name.equalsIgnoreCase("comments")) {
/*  806 */       return ((this.features & 0x20) != 0) ? Boolean.TRUE : Boolean.FALSE;
/*      */     }
/*  808 */     if (name.equalsIgnoreCase("namespaces")) {
/*  809 */       return ((this.features & 0x1) != 0) ? Boolean.TRUE : Boolean.FALSE;
/*      */     }
/*  811 */     if (name.equalsIgnoreCase("datatype-normalization"))
/*      */     {
/*  813 */       return ((this.features & 0x2) != 0) ? Boolean.TRUE : Boolean.FALSE;
/*      */     }
/*  815 */     if (name.equalsIgnoreCase("cdata-sections")) {
/*  816 */       return ((this.features & 0x8) != 0) ? Boolean.TRUE : Boolean.FALSE;
/*      */     }
/*  818 */     if (name.equalsIgnoreCase("entities")) {
/*  819 */       return ((this.features & 0x4) != 0) ? Boolean.TRUE : Boolean.FALSE;
/*      */     }
/*  821 */     if (name.equalsIgnoreCase("split-cdata-sections")) {
/*  822 */       return ((this.features & 0x10) != 0) ? Boolean.TRUE : Boolean.FALSE;
/*      */     }
/*  824 */     if (name.equalsIgnoreCase("validate")) {
/*  825 */       return ((this.features & 0x40) != 0) ? Boolean.TRUE : Boolean.FALSE;
/*      */     }
/*  827 */     if (name.equalsIgnoreCase("well-formed")) {
/*  828 */       return ((this.features & 0x100) != 0) ? Boolean.TRUE : Boolean.FALSE;
/*      */     }
/*  830 */     if (name.equalsIgnoreCase("namespace-declarations")) {
/*  831 */       return ((this.features & 0x200) != 0) ? Boolean.TRUE : Boolean.FALSE;
/*      */     }
/*  833 */     if (name.equalsIgnoreCase("infoset")) {
/*  834 */       return ((this.features & 0x32F) == 801) ? Boolean.TRUE : Boolean.FALSE;
/*      */     }
/*  836 */     if (name.equalsIgnoreCase("normalize-characters") || name
/*  837 */       .equalsIgnoreCase("canonical-form") || name
/*  838 */       .equalsIgnoreCase("validate-if-schema") || name
/*  839 */       .equalsIgnoreCase("check-character-normalization"))
/*      */     {
/*  841 */       return Boolean.FALSE;
/*      */     }
/*  843 */     if (name.equalsIgnoreCase("http://apache.org/xml/features/validation/schema/augment-psvi")) {
/*  844 */       return Boolean.TRUE;
/*      */     }
/*  846 */     if (name.equalsIgnoreCase("psvi")) {
/*  847 */       return ((this.features & 0x80) != 0) ? Boolean.TRUE : Boolean.FALSE;
/*      */     }
/*  849 */     if (name.equalsIgnoreCase("element-content-whitespace")) {
/*  850 */       return Boolean.TRUE;
/*      */     }
/*  852 */     if (name.equalsIgnoreCase("error-handler")) {
/*  853 */       return this.fErrorHandlerWrapper.getErrorHandler();
/*      */     }
/*  855 */     if (name.equalsIgnoreCase("resource-resolver")) {
/*  856 */       XMLEntityResolver entityResolver = getEntityResolver();
/*  857 */       if (entityResolver != null && entityResolver instanceof DOMEntityResolverWrapper) {
/*  858 */         return ((DOMEntityResolverWrapper)entityResolver).getEntityResolver();
/*      */       }
/*  860 */       return null;
/*      */     } 
/*  862 */     if (name.equalsIgnoreCase("schema-type")) {
/*  863 */       return getProperty("http://java.sun.com/xml/jaxp/properties/schemaLanguage");
/*      */     }
/*  865 */     if (name.equalsIgnoreCase("schema-location")) {
/*  866 */       return getProperty("http://java.sun.com/xml/jaxp/properties/schemaSource");
/*      */     }
/*  868 */     if (name.equalsIgnoreCase("http://apache.org/xml/properties/internal/symbol-table")) {
/*  869 */       return getProperty("http://apache.org/xml/properties/internal/symbol-table");
/*      */     }
/*  871 */     if (name.equalsIgnoreCase("http://apache.org/xml/properties/internal/grammar-pool")) {
/*  872 */       return getProperty("http://apache.org/xml/properties/internal/grammar-pool");
/*      */     }
/*      */ 
/*      */     
/*  876 */     String msg = DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "FEATURE_NOT_FOUND", new Object[] { name });
/*      */ 
/*      */ 
/*      */     
/*  880 */     throw new DOMException((short)8, msg);
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
/*      */   public boolean canSetParameter(String name, Object value) {
/*  900 */     if (value == null)
/*      */     {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  907 */       return true;
/*      */     }
/*  909 */     if (value instanceof Boolean) {
/*      */ 
/*      */ 
/*      */       
/*  913 */       if (name.equalsIgnoreCase("comments") || name
/*  914 */         .equalsIgnoreCase("datatype-normalization") || name
/*  915 */         .equalsIgnoreCase("cdata-sections") || name
/*  916 */         .equalsIgnoreCase("entities") || name
/*  917 */         .equalsIgnoreCase("split-cdata-sections") || name
/*  918 */         .equalsIgnoreCase("namespaces") || name
/*  919 */         .equalsIgnoreCase("validate") || name
/*  920 */         .equalsIgnoreCase("well-formed") || name
/*  921 */         .equalsIgnoreCase("infoset") || name
/*  922 */         .equalsIgnoreCase("namespace-declarations"))
/*      */       {
/*  924 */         return true;
/*      */       }
/*  926 */       if (name
/*  927 */         .equalsIgnoreCase("normalize-characters") || name
/*  928 */         .equalsIgnoreCase("canonical-form") || name
/*  929 */         .equalsIgnoreCase("validate-if-schema") || name
/*  930 */         .equalsIgnoreCase("check-character-normalization"))
/*      */       {
/*  932 */         return !value.equals(Boolean.TRUE);
/*      */       }
/*  934 */       if (name.equalsIgnoreCase("element-content-whitespace") || name
/*  935 */         .equalsIgnoreCase("http://apache.org/xml/features/validation/schema/augment-psvi"))
/*      */       {
/*  937 */         return value.equals(Boolean.TRUE);
/*      */       }
/*      */       
/*  940 */       return false;
/*      */     } 
/*      */     
/*  943 */     if (name.equalsIgnoreCase("error-handler")) {
/*  944 */       return (value instanceof DOMErrorHandler);
/*      */     }
/*  946 */     if (name.equalsIgnoreCase("resource-resolver")) {
/*  947 */       return (value instanceof LSResourceResolver);
/*      */     }
/*  949 */     if (name.equalsIgnoreCase("schema-location")) {
/*  950 */       return (value instanceof String);
/*      */     }
/*  952 */     if (name.equalsIgnoreCase("schema-type"))
/*      */     {
/*      */       
/*  955 */       return (value instanceof String && value.equals(Constants.NS_XMLSCHEMA));
/*      */     }
/*  957 */     if (name.equalsIgnoreCase("http://apache.org/xml/properties/internal/symbol-table"))
/*      */     {
/*  959 */       return (value instanceof SymbolTable);
/*      */     }
/*  961 */     if (name.equalsIgnoreCase("http://apache.org/xml/properties/internal/grammar-pool")) {
/*  962 */       return (value instanceof com.sun.org.apache.xerces.internal.xni.grammars.XMLGrammarPool);
/*      */     }
/*      */ 
/*      */     
/*  966 */     return false;
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
/*      */   public DOMStringList getParameterNames() {
/*  980 */     if (this.fRecognizedParameters == null) {
/*  981 */       Vector<String> parameters = new Vector();
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  986 */       parameters.add("comments");
/*  987 */       parameters.add("datatype-normalization");
/*  988 */       parameters.add("cdata-sections");
/*  989 */       parameters.add("entities");
/*  990 */       parameters.add("split-cdata-sections");
/*  991 */       parameters.add("namespaces");
/*  992 */       parameters.add("validate");
/*      */       
/*  994 */       parameters.add("infoset");
/*  995 */       parameters.add("normalize-characters");
/*  996 */       parameters.add("canonical-form");
/*  997 */       parameters.add("validate-if-schema");
/*  998 */       parameters.add("check-character-normalization");
/*  999 */       parameters.add("well-formed");
/*      */       
/* 1001 */       parameters.add("namespace-declarations");
/* 1002 */       parameters.add("element-content-whitespace");
/*      */       
/* 1004 */       parameters.add("error-handler");
/* 1005 */       parameters.add("schema-type");
/* 1006 */       parameters.add("schema-location");
/* 1007 */       parameters.add("resource-resolver");
/*      */ 
/*      */       
/* 1010 */       parameters.add("http://apache.org/xml/properties/internal/grammar-pool");
/* 1011 */       parameters.add("http://apache.org/xml/properties/internal/symbol-table");
/* 1012 */       parameters.add("http://apache.org/xml/features/validation/schema/augment-psvi");
/*      */       
/* 1014 */       this.fRecognizedParameters = new DOMStringListImpl(parameters);
/*      */     } 
/*      */ 
/*      */     
/* 1018 */     return this.fRecognizedParameters;
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
/*      */   protected void reset() throws XNIException {
/* 1030 */     if (this.fValidationManager != null) {
/* 1031 */       this.fValidationManager.reset();
/*      */     }
/* 1033 */     int count = this.fComponents.size();
/* 1034 */     for (int i = 0; i < count; i++) {
/* 1035 */       XMLComponent c = this.fComponents.get(i);
/* 1036 */       c.reset(this);
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
/*      */   protected PropertyState checkProperty(String propertyId) throws XMLConfigurationException {
/* 1054 */     if (propertyId.startsWith("http://xml.org/sax/properties/")) {
/* 1055 */       int suffixLength = propertyId.length() - "http://xml.org/sax/properties/".length();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1067 */       if (suffixLength == "xml-string".length() && propertyId
/* 1068 */         .endsWith("xml-string"))
/*      */       {
/*      */ 
/*      */         
/* 1072 */         return PropertyState.NOT_SUPPORTED;
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/* 1077 */     return super.checkProperty(propertyId);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void addComponent(XMLComponent component) {
/* 1085 */     if (this.fComponents.contains(component)) {
/*      */       return;
/*      */     }
/* 1088 */     this.fComponents.add(component);
/*      */ 
/*      */     
/* 1091 */     String[] recognizedFeatures = component.getRecognizedFeatures();
/* 1092 */     addRecognizedFeatures(recognizedFeatures);
/*      */ 
/*      */     
/* 1095 */     String[] recognizedProperties = component.getRecognizedProperties();
/* 1096 */     addRecognizedProperties(recognizedProperties);
/*      */   }
/*      */ 
/*      */   
/*      */   protected ValidationManager createValidationManager() {
/* 1101 */     return new ValidationManager();
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xerces/internal/dom/DOMConfigurationImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */