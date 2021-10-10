/*     */ package com.sun.org.apache.xerces.internal.jaxp;
/*     */ 
/*     */ import com.sun.org.apache.xerces.internal.dom.DOMImplementationImpl;
/*     */ import com.sun.org.apache.xerces.internal.dom.DOMMessageFormatter;
/*     */ import com.sun.org.apache.xerces.internal.dom.DocumentImpl;
/*     */ import com.sun.org.apache.xerces.internal.impl.Constants;
/*     */ import com.sun.org.apache.xerces.internal.impl.validation.ValidationManager;
/*     */ import com.sun.org.apache.xerces.internal.impl.xs.XMLSchemaValidator;
/*     */ import com.sun.org.apache.xerces.internal.jaxp.validation.XSGrammarPoolContainer;
/*     */ import com.sun.org.apache.xerces.internal.parsers.DOMParser;
/*     */ import com.sun.org.apache.xerces.internal.utils.XMLSecurityManager;
/*     */ import com.sun.org.apache.xerces.internal.utils.XMLSecurityPropertyManager;
/*     */ import com.sun.org.apache.xerces.internal.xni.XMLDocumentHandler;
/*     */ import com.sun.org.apache.xerces.internal.xni.parser.XMLComponent;
/*     */ import com.sun.org.apache.xerces.internal.xni.parser.XMLComponentManager;
/*     */ import com.sun.org.apache.xerces.internal.xni.parser.XMLConfigurationException;
/*     */ import com.sun.org.apache.xerces.internal.xni.parser.XMLDocumentSource;
/*     */ import com.sun.org.apache.xerces.internal.xni.parser.XMLParserConfiguration;
/*     */ import java.io.IOException;
/*     */ import java.util.Map;
/*     */ import javax.xml.parsers.DocumentBuilder;
/*     */ import javax.xml.validation.Schema;
/*     */ import org.w3c.dom.DOMImplementation;
/*     */ import org.w3c.dom.Document;
/*     */ import org.xml.sax.EntityResolver;
/*     */ import org.xml.sax.ErrorHandler;
/*     */ import org.xml.sax.InputSource;
/*     */ import org.xml.sax.SAXException;
/*     */ import org.xml.sax.SAXNotRecognizedException;
/*     */ import org.xml.sax.SAXNotSupportedException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DocumentBuilderImpl
/*     */   extends DocumentBuilder
/*     */   implements JAXPConstants
/*     */ {
/*     */   private static final String NAMESPACES_FEATURE = "http://xml.org/sax/features/namespaces";
/*     */   private static final String INCLUDE_IGNORABLE_WHITESPACE = "http://apache.org/xml/features/dom/include-ignorable-whitespace";
/*     */   private static final String CREATE_ENTITY_REF_NODES_FEATURE = "http://apache.org/xml/features/dom/create-entity-ref-nodes";
/*     */   private static final String INCLUDE_COMMENTS_FEATURE = "http://apache.org/xml/features/include-comments";
/*     */   private static final String CREATE_CDATA_NODES_FEATURE = "http://apache.org/xml/features/create-cdata-nodes";
/*     */   private static final String XINCLUDE_FEATURE = "http://apache.org/xml/features/xinclude";
/*     */   private static final String XMLSCHEMA_VALIDATION_FEATURE = "http://apache.org/xml/features/validation/schema";
/*     */   private static final String VALIDATION_FEATURE = "http://xml.org/sax/features/validation";
/*     */   private static final String SECURITY_MANAGER = "http://apache.org/xml/properties/security-manager";
/*     */   private static final String XML_SECURITY_PROPERTY_MANAGER = "http://www.oracle.com/xml/jaxp/properties/xmlSecurityPropertyManager";
/*     */   public static final String ACCESS_EXTERNAL_DTD = "http://javax.xml.XMLConstants/property/accessExternalDTD";
/*     */   public static final String ACCESS_EXTERNAL_SCHEMA = "http://javax.xml.XMLConstants/property/accessExternalSchema";
/*     */   
/*     */   DocumentBuilderImpl(DocumentBuilderFactoryImpl dbf, Map<String, Object> dbfAttrs, Map<String, Boolean> features) throws SAXNotRecognizedException, SAXNotSupportedException {
/* 129 */     this(dbf, dbfAttrs, features, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 136 */   private final DOMParser domParser = new DOMParser(); private final Schema grammar; private final XMLComponent fSchemaValidator;
/*     */   private final XMLComponentManager fSchemaValidatorComponentManager;
/*     */   private final ValidationManager fSchemaValidationManager;
/*     */   
/*     */   DocumentBuilderImpl(DocumentBuilderFactoryImpl dbf, Map<String, Object> dbfAttrs, Map<String, Boolean> features, boolean secureProcessing) throws SAXNotRecognizedException, SAXNotSupportedException {
/* 141 */     if (dbf.isValidating()) {
/* 142 */       this.fInitErrorHandler = new DefaultValidationErrorHandler(this.domParser.getXMLParserConfiguration().getLocale());
/* 143 */       setErrorHandler(this.fInitErrorHandler);
/*     */     } else {
/*     */       
/* 146 */       this.fInitErrorHandler = this.domParser.getErrorHandler();
/*     */     } 
/*     */     
/* 149 */     this.domParser.setFeature("http://xml.org/sax/features/validation", dbf.isValidating());
/*     */ 
/*     */     
/* 152 */     this.domParser.setFeature("http://xml.org/sax/features/namespaces", dbf.isNamespaceAware());
/*     */ 
/*     */     
/* 155 */     this.domParser.setFeature("http://apache.org/xml/features/dom/include-ignorable-whitespace", 
/* 156 */         !dbf.isIgnoringElementContentWhitespace());
/* 157 */     this.domParser.setFeature("http://apache.org/xml/features/dom/create-entity-ref-nodes", 
/* 158 */         !dbf.isExpandEntityReferences());
/* 159 */     this.domParser.setFeature("http://apache.org/xml/features/include-comments", 
/* 160 */         !dbf.isIgnoringComments());
/* 161 */     this.domParser.setFeature("http://apache.org/xml/features/create-cdata-nodes", 
/* 162 */         !dbf.isCoalescing());
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 167 */     if (dbf.isXIncludeAware()) {
/* 168 */       this.domParser.setFeature("http://apache.org/xml/features/xinclude", true);
/*     */     }
/*     */     
/* 171 */     this.fSecurityPropertyMgr = new XMLSecurityPropertyManager();
/* 172 */     this.domParser.setProperty("http://www.oracle.com/xml/jaxp/properties/xmlSecurityPropertyManager", this.fSecurityPropertyMgr);
/*     */     
/* 174 */     this.fSecurityManager = new XMLSecurityManager(secureProcessing);
/* 175 */     this.domParser.setProperty("http://apache.org/xml/properties/security-manager", this.fSecurityManager);
/*     */     
/* 177 */     if (secureProcessing)
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 183 */       if (features != null) {
/* 184 */         Boolean temp = features.get("http://javax.xml.XMLConstants/feature/secure-processing");
/* 185 */         if (temp != null && 
/* 186 */           temp.booleanValue() && Constants.IS_JDK8_OR_ABOVE) {
/* 187 */           this.fSecurityPropertyMgr.setValue(XMLSecurityPropertyManager.Property.ACCESS_EXTERNAL_DTD, XMLSecurityPropertyManager.State.FSP, "");
/*     */           
/* 189 */           this.fSecurityPropertyMgr.setValue(XMLSecurityPropertyManager.Property.ACCESS_EXTERNAL_SCHEMA, XMLSecurityPropertyManager.State.FSP, "");
/*     */         } 
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 196 */     this.grammar = dbf.getSchema();
/* 197 */     if (this.grammar != null) {
/* 198 */       XMLParserConfiguration config = this.domParser.getXMLParserConfiguration();
/* 199 */       XMLComponent validatorComponent = null;
/*     */       
/* 201 */       if (this.grammar instanceof XSGrammarPoolContainer) {
/* 202 */         validatorComponent = new XMLSchemaValidator();
/* 203 */         this.fSchemaValidationManager = new ValidationManager();
/* 204 */         this.fUnparsedEntityHandler = new UnparsedEntityHandler(this.fSchemaValidationManager);
/* 205 */         config.setDTDHandler(this.fUnparsedEntityHandler);
/* 206 */         this.fUnparsedEntityHandler.setDTDHandler(this.domParser);
/* 207 */         this.domParser.setDTDSource(this.fUnparsedEntityHandler);
/* 208 */         this.fSchemaValidatorComponentManager = new SchemaValidatorConfiguration(config, (XSGrammarPoolContainer)this.grammar, this.fSchemaValidationManager);
/*     */       
/*     */       }
/*     */       else {
/*     */         
/* 213 */         validatorComponent = new JAXPValidatorComponent(this.grammar.newValidatorHandler());
/* 214 */         this.fSchemaValidationManager = null;
/* 215 */         this.fUnparsedEntityHandler = null;
/* 216 */         this.fSchemaValidatorComponentManager = config;
/*     */       } 
/* 218 */       config.addRecognizedFeatures(validatorComponent.getRecognizedFeatures());
/* 219 */       config.addRecognizedProperties(validatorComponent.getRecognizedProperties());
/* 220 */       setFeatures(features);
/* 221 */       config.setDocumentHandler((XMLDocumentHandler)validatorComponent);
/* 222 */       ((XMLDocumentSource)validatorComponent).setDocumentHandler(this.domParser);
/* 223 */       this.domParser.setDocumentSource((XMLDocumentSource)validatorComponent);
/* 224 */       this.fSchemaValidator = validatorComponent;
/*     */     } else {
/*     */       
/* 227 */       this.fSchemaValidationManager = null;
/* 228 */       this.fUnparsedEntityHandler = null;
/* 229 */       this.fSchemaValidatorComponentManager = null;
/* 230 */       this.fSchemaValidator = null;
/* 231 */       setFeatures(features);
/*     */     } 
/*     */ 
/*     */     
/* 235 */     setDocumentBuilderFactoryAttributes(dbfAttrs);
/*     */ 
/*     */     
/* 238 */     this.fInitEntityResolver = this.domParser.getEntityResolver();
/*     */   }
/*     */   private final UnparsedEntityHandler fUnparsedEntityHandler; private final ErrorHandler fInitErrorHandler; private final EntityResolver fInitEntityResolver; private XMLSecurityManager fSecurityManager; private XMLSecurityPropertyManager fSecurityPropertyMgr;
/*     */   
/*     */   private void setFeatures(Map<String, Boolean> features) throws SAXNotSupportedException, SAXNotRecognizedException {
/* 243 */     if (features != null) {
/* 244 */       for (Map.Entry<String, Boolean> entry : features.entrySet()) {
/* 245 */         this.domParser.setFeature(entry.getKey(), ((Boolean)entry.getValue()).booleanValue());
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
/*     */   private void setDocumentBuilderFactoryAttributes(Map<String, Object> dbfAttrs) throws SAXNotSupportedException, SAXNotRecognizedException {
/* 260 */     if (dbfAttrs == null) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 265 */     for (Map.Entry<String, Object> entry : dbfAttrs.entrySet()) {
/* 266 */       String name = entry.getKey();
/* 267 */       Object val = entry.getValue();
/* 268 */       if (val instanceof Boolean) {
/*     */         
/* 270 */         this.domParser.setFeature(name, ((Boolean)val).booleanValue());
/*     */         continue;
/*     */       } 
/* 273 */       if ("http://java.sun.com/xml/jaxp/properties/schemaLanguage".equals(name)) {
/*     */ 
/*     */         
/* 276 */         if ("http://www.w3.org/2001/XMLSchema".equals(val) && 
/* 277 */           isValidating()) {
/* 278 */           this.domParser.setFeature("http://apache.org/xml/features/validation/schema", true);
/*     */ 
/*     */           
/* 281 */           this.domParser.setProperty("http://java.sun.com/xml/jaxp/properties/schemaLanguage", "http://www.w3.org/2001/XMLSchema");
/*     */         }  continue;
/*     */       } 
/* 284 */       if ("http://java.sun.com/xml/jaxp/properties/schemaSource".equals(name)) {
/* 285 */         if (isValidating()) {
/* 286 */           String value = (String)dbfAttrs.get("http://java.sun.com/xml/jaxp/properties/schemaLanguage");
/* 287 */           if (value != null && "http://www.w3.org/2001/XMLSchema".equals(value)) {
/* 288 */             this.domParser.setProperty(name, val); continue;
/*     */           } 
/* 290 */           throw new IllegalArgumentException(
/* 291 */               DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "jaxp-order-not-supported", new Object[] { "http://java.sun.com/xml/jaxp/properties/schemaLanguage", "http://java.sun.com/xml/jaxp/properties/schemaSource" }));
/*     */         } 
/*     */ 
/*     */         
/*     */         continue;
/*     */       } 
/*     */       
/* 298 */       if (this.fSecurityManager == null || 
/* 299 */         !this.fSecurityManager.setLimit(name, XMLSecurityManager.State.APIPROPERTY, val))
/*     */       {
/* 301 */         if (this.fSecurityPropertyMgr == null || 
/* 302 */           !this.fSecurityPropertyMgr.setValue(name, XMLSecurityPropertyManager.State.APIPROPERTY, val))
/*     */         {
/* 304 */           this.domParser.setProperty(name, val);
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
/*     */   public Document newDocument() {
/* 319 */     return new DocumentImpl();
/*     */   }
/*     */   
/*     */   public DOMImplementation getDOMImplementation() {
/* 323 */     return DOMImplementationImpl.getDOMImplementation();
/*     */   }
/*     */   
/*     */   public Document parse(InputSource is) throws SAXException, IOException {
/* 327 */     if (is == null) {
/* 328 */       throw new IllegalArgumentException(
/* 329 */           DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "jaxp-null-input-source", null));
/*     */     }
/*     */     
/* 332 */     if (this.fSchemaValidator != null) {
/* 333 */       if (this.fSchemaValidationManager != null) {
/* 334 */         this.fSchemaValidationManager.reset();
/* 335 */         this.fUnparsedEntityHandler.reset();
/*     */       } 
/* 337 */       resetSchemaValidator();
/*     */     } 
/* 339 */     this.domParser.parse(is);
/* 340 */     Document doc = this.domParser.getDocument();
/* 341 */     this.domParser.dropDocumentReferences();
/* 342 */     return doc;
/*     */   }
/*     */   
/*     */   public boolean isNamespaceAware() {
/*     */     try {
/* 347 */       return this.domParser.getFeature("http://xml.org/sax/features/namespaces");
/*     */     }
/* 349 */     catch (SAXException x) {
/* 350 */       throw new IllegalStateException(x.getMessage());
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean isValidating() {
/*     */     try {
/* 356 */       return this.domParser.getFeature("http://xml.org/sax/features/validation");
/*     */     }
/* 358 */     catch (SAXException x) {
/* 359 */       throw new IllegalStateException(x.getMessage());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isXIncludeAware() {
/*     */     try {
/* 369 */       return this.domParser.getFeature("http://apache.org/xml/features/xinclude");
/*     */     }
/* 371 */     catch (SAXException exc) {
/* 372 */       return false;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setEntityResolver(EntityResolver er) {
/* 377 */     this.domParser.setEntityResolver(er);
/*     */   }
/*     */   
/*     */   public void setErrorHandler(ErrorHandler eh) {
/* 381 */     this.domParser.setErrorHandler(eh);
/*     */   }
/*     */   
/*     */   public Schema getSchema() {
/* 385 */     return this.grammar;
/*     */   }
/*     */ 
/*     */   
/*     */   public void reset() {
/* 390 */     if (this.domParser.getErrorHandler() != this.fInitErrorHandler) {
/* 391 */       this.domParser.setErrorHandler(this.fInitErrorHandler);
/*     */     }
/*     */     
/* 394 */     if (this.domParser.getEntityResolver() != this.fInitEntityResolver) {
/* 395 */       this.domParser.setEntityResolver(this.fInitEntityResolver);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   DOMParser getDOMParser() {
/* 401 */     return this.domParser;
/*     */   }
/*     */   
/*     */   private void resetSchemaValidator() throws SAXException {
/*     */     try {
/* 406 */       this.fSchemaValidator.reset(this.fSchemaValidatorComponentManager);
/*     */     
/*     */     }
/* 409 */     catch (XMLConfigurationException e) {
/* 410 */       throw new SAXException(e);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xerces/internal/jaxp/DocumentBuilderImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */