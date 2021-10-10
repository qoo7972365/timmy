/*     */ package com.sun.org.apache.xerces.internal.jaxp;
/*     */ 
/*     */ import com.sun.org.apache.xerces.internal.impl.Constants;
/*     */ import com.sun.org.apache.xerces.internal.impl.validation.ValidationManager;
/*     */ import com.sun.org.apache.xerces.internal.impl.xs.XMLSchemaValidator;
/*     */ import com.sun.org.apache.xerces.internal.jaxp.validation.XSGrammarPoolContainer;
/*     */ import com.sun.org.apache.xerces.internal.parsers.SAXParser;
/*     */ import com.sun.org.apache.xerces.internal.util.SAXMessageFormatter;
/*     */ import com.sun.org.apache.xerces.internal.util.Status;
/*     */ import com.sun.org.apache.xerces.internal.utils.XMLSecurityManager;
/*     */ import com.sun.org.apache.xerces.internal.utils.XMLSecurityPropertyManager;
/*     */ import com.sun.org.apache.xerces.internal.xni.XMLDocumentHandler;
/*     */ import com.sun.org.apache.xerces.internal.xni.parser.XMLComponent;
/*     */ import com.sun.org.apache.xerces.internal.xni.parser.XMLComponentManager;
/*     */ import com.sun.org.apache.xerces.internal.xni.parser.XMLConfigurationException;
/*     */ import com.sun.org.apache.xerces.internal.xni.parser.XMLDocumentSource;
/*     */ import com.sun.org.apache.xerces.internal.xni.parser.XMLParserConfiguration;
/*     */ import com.sun.org.apache.xerces.internal.xs.AttributePSVI;
/*     */ import com.sun.org.apache.xerces.internal.xs.ElementPSVI;
/*     */ import com.sun.org.apache.xerces.internal.xs.PSVIProvider;
/*     */ import java.io.IOException;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import javax.xml.parsers.SAXParser;
/*     */ import javax.xml.validation.Schema;
/*     */ import org.xml.sax.ContentHandler;
/*     */ import org.xml.sax.DTDHandler;
/*     */ import org.xml.sax.DocumentHandler;
/*     */ import org.xml.sax.EntityResolver;
/*     */ import org.xml.sax.ErrorHandler;
/*     */ import org.xml.sax.HandlerBase;
/*     */ import org.xml.sax.InputSource;
/*     */ import org.xml.sax.Parser;
/*     */ import org.xml.sax.SAXException;
/*     */ import org.xml.sax.SAXNotRecognizedException;
/*     */ import org.xml.sax.SAXNotSupportedException;
/*     */ import org.xml.sax.XMLReader;
/*     */ import org.xml.sax.helpers.DefaultHandler;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SAXParserImpl
/*     */   extends SAXParser
/*     */   implements JAXPConstants, PSVIProvider
/*     */ {
/*     */   private static final String NAMESPACES_FEATURE = "http://xml.org/sax/features/namespaces";
/*     */   private static final String NAMESPACE_PREFIXES_FEATURE = "http://xml.org/sax/features/namespace-prefixes";
/*     */   private static final String VALIDATION_FEATURE = "http://xml.org/sax/features/validation";
/*     */   private static final String XMLSCHEMA_VALIDATION_FEATURE = "http://apache.org/xml/features/validation/schema";
/*     */   private static final String XINCLUDE_FEATURE = "http://apache.org/xml/features/xinclude";
/*     */   private static final String SECURITY_MANAGER = "http://apache.org/xml/properties/security-manager";
/*     */   private static final String XML_SECURITY_PROPERTY_MANAGER = "http://www.oracle.com/xml/jaxp/properties/xmlSecurityPropertyManager";
/*     */   private final JAXPSAXParser xmlReader;
/*  99 */   private String schemaLanguage = null;
/*     */ 
/*     */   
/*     */   private final Schema grammar;
/*     */   
/*     */   private final XMLComponent fSchemaValidator;
/*     */   
/*     */   private final XMLComponentManager fSchemaValidatorComponentManager;
/*     */   
/*     */   private final ValidationManager fSchemaValidationManager;
/*     */   
/*     */   private final UnparsedEntityHandler fUnparsedEntityHandler;
/*     */   
/*     */   private final ErrorHandler fInitErrorHandler;
/*     */   
/*     */   private final EntityResolver fInitEntityResolver;
/*     */   
/*     */   private final XMLSecurityManager fSecurityManager;
/*     */   
/*     */   private final XMLSecurityPropertyManager fSecurityPropertyMgr;
/*     */ 
/*     */   
/*     */   SAXParserImpl(SAXParserFactoryImpl spf, Map<String, Boolean> features) throws SAXException {
/* 122 */     this(spf, features, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   SAXParserImpl(SAXParserFactoryImpl spf, Map<String, Boolean> features, boolean secureProcessing) throws SAXException {
/* 132 */     this.fSecurityManager = new XMLSecurityManager(secureProcessing);
/* 133 */     this.fSecurityPropertyMgr = new XMLSecurityPropertyManager();
/*     */     
/* 135 */     this.xmlReader = new JAXPSAXParser(this, this.fSecurityPropertyMgr, this.fSecurityManager);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 140 */     this.xmlReader.setFeature0("http://xml.org/sax/features/namespaces", spf.isNamespaceAware());
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 145 */     this.xmlReader.setFeature0("http://xml.org/sax/features/namespace-prefixes", !spf.isNamespaceAware());
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 150 */     if (spf.isXIncludeAware()) {
/* 151 */       this.xmlReader.setFeature0("http://apache.org/xml/features/xinclude", true);
/*     */     }
/*     */     
/* 154 */     this.xmlReader.setProperty0("http://www.oracle.com/xml/jaxp/properties/xmlSecurityPropertyManager", this.fSecurityPropertyMgr);
/*     */     
/* 156 */     this.xmlReader.setProperty0("http://apache.org/xml/properties/security-manager", this.fSecurityManager);
/*     */     
/* 158 */     if (secureProcessing)
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 165 */       if (features != null) {
/*     */         
/* 167 */         Boolean temp = features.get("http://javax.xml.XMLConstants/feature/secure-processing");
/* 168 */         if (temp != null && 
/* 169 */           temp.booleanValue() && Constants.IS_JDK8_OR_ABOVE) {
/* 170 */           this.fSecurityPropertyMgr.setValue(XMLSecurityPropertyManager.Property.ACCESS_EXTERNAL_DTD, XMLSecurityPropertyManager.State.FSP, "");
/*     */           
/* 172 */           this.fSecurityPropertyMgr.setValue(XMLSecurityPropertyManager.Property.ACCESS_EXTERNAL_SCHEMA, XMLSecurityPropertyManager.State.FSP, "");
/*     */         } 
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 181 */     setFeatures(features);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 186 */     if (spf.isValidating()) {
/* 187 */       this.fInitErrorHandler = new DefaultValidationErrorHandler(this.xmlReader.getLocale());
/* 188 */       this.xmlReader.setErrorHandler(this.fInitErrorHandler);
/*     */     } else {
/*     */       
/* 191 */       this.fInitErrorHandler = this.xmlReader.getErrorHandler();
/*     */     } 
/* 193 */     this.xmlReader.setFeature0("http://xml.org/sax/features/validation", spf.isValidating());
/*     */ 
/*     */     
/* 196 */     this.grammar = spf.getSchema();
/* 197 */     if (this.grammar != null) {
/* 198 */       XMLParserConfiguration config = this.xmlReader.getXMLParserConfiguration();
/* 199 */       XMLComponent validatorComponent = null;
/*     */       
/* 201 */       if (this.grammar instanceof XSGrammarPoolContainer) {
/* 202 */         validatorComponent = new XMLSchemaValidator();
/* 203 */         this.fSchemaValidationManager = new ValidationManager();
/* 204 */         this.fUnparsedEntityHandler = new UnparsedEntityHandler(this.fSchemaValidationManager);
/* 205 */         config.setDTDHandler(this.fUnparsedEntityHandler);
/* 206 */         this.fUnparsedEntityHandler.setDTDHandler(this.xmlReader);
/* 207 */         this.xmlReader.setDTDSource(this.fUnparsedEntityHandler);
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
/* 220 */       config.setDocumentHandler((XMLDocumentHandler)validatorComponent);
/* 221 */       ((XMLDocumentSource)validatorComponent).setDocumentHandler(this.xmlReader);
/* 222 */       this.xmlReader.setDocumentSource((XMLDocumentSource)validatorComponent);
/* 223 */       this.fSchemaValidator = validatorComponent;
/*     */     } else {
/*     */       
/* 226 */       this.fSchemaValidationManager = null;
/* 227 */       this.fUnparsedEntityHandler = null;
/* 228 */       this.fSchemaValidatorComponentManager = null;
/* 229 */       this.fSchemaValidator = null;
/*     */     } 
/*     */ 
/*     */     
/* 233 */     this.fInitEntityResolver = this.xmlReader.getEntityResolver();
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
/*     */   private void setFeatures(Map<String, Boolean> features) throws SAXNotSupportedException, SAXNotRecognizedException {
/* 245 */     if (features != null) {
/* 246 */       for (Map.Entry<String, Boolean> entry : features.entrySet()) {
/* 247 */         this.xmlReader.setFeature0(entry.getKey(), ((Boolean)entry.getValue()).booleanValue());
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Parser getParser() throws SAXException {
/* 255 */     return this.xmlReader;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XMLReader getXMLReader() {
/* 263 */     return this.xmlReader;
/*     */   }
/*     */   
/*     */   public boolean isNamespaceAware() {
/*     */     try {
/* 268 */       return this.xmlReader.getFeature("http://xml.org/sax/features/namespaces");
/*     */     }
/* 270 */     catch (SAXException x) {
/* 271 */       throw new IllegalStateException(x.getMessage());
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean isValidating() {
/*     */     try {
/* 277 */       return this.xmlReader.getFeature("http://xml.org/sax/features/validation");
/*     */     }
/* 279 */     catch (SAXException x) {
/* 280 */       throw new IllegalStateException(x.getMessage());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isXIncludeAware() {
/*     */     try {
/* 290 */       return this.xmlReader.getFeature("http://apache.org/xml/features/xinclude");
/*     */     }
/* 292 */     catch (SAXException exc) {
/* 293 */       return false;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setProperty(String name, Object value) throws SAXNotRecognizedException, SAXNotSupportedException {
/* 303 */     this.xmlReader.setProperty(name, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object getProperty(String name) throws SAXNotRecognizedException, SAXNotSupportedException {
/* 312 */     return this.xmlReader.getProperty(name);
/*     */   }
/*     */ 
/*     */   
/*     */   public void parse(InputSource is, DefaultHandler dh) throws SAXException, IOException {
/* 317 */     if (is == null) {
/* 318 */       throw new IllegalArgumentException();
/*     */     }
/* 320 */     if (dh != null) {
/* 321 */       this.xmlReader.setContentHandler(dh);
/* 322 */       this.xmlReader.setEntityResolver(dh);
/* 323 */       this.xmlReader.setErrorHandler(dh);
/* 324 */       this.xmlReader.setDTDHandler(dh);
/* 325 */       this.xmlReader.setDocumentHandler((DocumentHandler)null);
/*     */     } 
/* 327 */     this.xmlReader.parse(is);
/*     */   }
/*     */ 
/*     */   
/*     */   public void parse(InputSource is, HandlerBase hb) throws SAXException, IOException {
/* 332 */     if (is == null) {
/* 333 */       throw new IllegalArgumentException();
/*     */     }
/* 335 */     if (hb != null) {
/* 336 */       this.xmlReader.setDocumentHandler(hb);
/* 337 */       this.xmlReader.setEntityResolver(hb);
/* 338 */       this.xmlReader.setErrorHandler(hb);
/* 339 */       this.xmlReader.setDTDHandler(hb);
/* 340 */       this.xmlReader.setContentHandler((ContentHandler)null);
/*     */     } 
/* 342 */     this.xmlReader.parse(is);
/*     */   }
/*     */   
/*     */   public Schema getSchema() {
/* 346 */     return this.grammar;
/*     */   }
/*     */ 
/*     */   
/*     */   public void reset() {
/*     */     try {
/* 352 */       this.xmlReader.restoreInitState();
/*     */     }
/* 354 */     catch (SAXException sAXException) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 360 */     this.xmlReader.setContentHandler((ContentHandler)null);
/* 361 */     this.xmlReader.setDTDHandler((DTDHandler)null);
/* 362 */     if (this.xmlReader.getErrorHandler() != this.fInitErrorHandler) {
/* 363 */       this.xmlReader.setErrorHandler(this.fInitErrorHandler);
/*     */     }
/* 365 */     if (this.xmlReader.getEntityResolver() != this.fInitEntityResolver) {
/* 366 */       this.xmlReader.setEntityResolver(this.fInitEntityResolver);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ElementPSVI getElementPSVI() {
/* 375 */     return this.xmlReader.getElementPSVI();
/*     */   }
/*     */   
/*     */   public AttributePSVI getAttributePSVI(int index) {
/* 379 */     return this.xmlReader.getAttributePSVI(index);
/*     */   }
/*     */   
/*     */   public AttributePSVI getAttributePSVIByName(String uri, String localname) {
/* 383 */     return this.xmlReader.getAttributePSVIByName(uri, localname);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class JAXPSAXParser
/*     */     extends SAXParser
/*     */   {
/* 393 */     private final HashMap fInitFeatures = new HashMap<>();
/* 394 */     private final HashMap fInitProperties = new HashMap<>();
/*     */     
/*     */     private final SAXParserImpl fSAXParser;
/*     */     private XMLSecurityManager fSecurityManager;
/*     */     private XMLSecurityPropertyManager fSecurityPropertyMgr;
/*     */     
/*     */     public JAXPSAXParser() {
/* 401 */       this((SAXParserImpl)null, (XMLSecurityPropertyManager)null, (XMLSecurityManager)null);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     JAXPSAXParser(SAXParserImpl saxParser, XMLSecurityPropertyManager securityPropertyMgr, XMLSecurityManager securityManager) {
/* 407 */       this.fSAXParser = saxParser;
/* 408 */       this.fSecurityManager = securityManager;
/* 409 */       this.fSecurityPropertyMgr = securityPropertyMgr;
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 414 */       if (this.fSecurityManager == null) {
/* 415 */         this.fSecurityManager = new XMLSecurityManager(true);
/*     */         try {
/* 417 */           super.setProperty("http://apache.org/xml/properties/security-manager", this.fSecurityManager);
/* 418 */         } catch (SAXException e) {
/* 419 */           throw new UnsupportedOperationException(
/* 420 */               SAXMessageFormatter.formatMessage(this.fConfiguration.getLocale(), "property-not-recognized", new Object[] { "http://apache.org/xml/properties/security-manager" }), e);
/*     */         } 
/*     */       } 
/*     */       
/* 424 */       if (this.fSecurityPropertyMgr == null) {
/* 425 */         this.fSecurityPropertyMgr = new XMLSecurityPropertyManager();
/*     */         try {
/* 427 */           super.setProperty("http://www.oracle.com/xml/jaxp/properties/xmlSecurityPropertyManager", this.fSecurityPropertyMgr);
/* 428 */         } catch (SAXException e) {
/* 429 */           throw new UnsupportedOperationException(
/* 430 */               SAXMessageFormatter.formatMessage(this.fConfiguration.getLocale(), "property-not-recognized", new Object[] { "http://apache.org/xml/properties/security-manager" }), e);
/*     */         } 
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public synchronized void setFeature(String name, boolean value) throws SAXNotRecognizedException, SAXNotSupportedException {
/* 443 */       if (name == null)
/*     */       {
/* 445 */         throw new NullPointerException();
/*     */       }
/* 447 */       if (name.equals("http://javax.xml.XMLConstants/feature/secure-processing")) {
/*     */         try {
/* 449 */           this.fSecurityManager.setSecureProcessing(value);
/* 450 */           setProperty("http://apache.org/xml/properties/security-manager", this.fSecurityManager);
/*     */         }
/* 452 */         catch (SAXNotRecognizedException exc) {
/*     */ 
/*     */           
/* 455 */           if (value) {
/* 456 */             throw exc;
/*     */           }
/*     */         }
/* 459 */         catch (SAXNotSupportedException exc) {
/*     */ 
/*     */           
/* 462 */           if (value) {
/* 463 */             throw exc;
/*     */           }
/*     */         } 
/*     */         return;
/*     */       } 
/* 468 */       if (!this.fInitFeatures.containsKey(name)) {
/* 469 */         boolean current = super.getFeature(name);
/* 470 */         this.fInitFeatures.put(name, current ? Boolean.TRUE : Boolean.FALSE);
/*     */       } 
/*     */       
/* 473 */       if (this.fSAXParser != null && this.fSAXParser.fSchemaValidator != null) {
/* 474 */         setSchemaValidatorFeature(name, value);
/*     */       }
/* 476 */       super.setFeature(name, value);
/*     */     }
/*     */ 
/*     */     
/*     */     public synchronized boolean getFeature(String name) throws SAXNotRecognizedException, SAXNotSupportedException {
/* 481 */       if (name == null)
/*     */       {
/* 483 */         throw new NullPointerException();
/*     */       }
/* 485 */       if (name.equals("http://javax.xml.XMLConstants/feature/secure-processing")) {
/* 486 */         return this.fSecurityManager.isSecureProcessing();
/*     */       }
/* 488 */       return super.getFeature(name);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public synchronized void setProperty(String name, Object value) throws SAXNotRecognizedException, SAXNotSupportedException {
/* 498 */       if (name == null)
/*     */       {
/* 500 */         throw new NullPointerException();
/*     */       }
/* 502 */       if (this.fSAXParser != null) {
/*     */         
/* 504 */         if ("http://java.sun.com/xml/jaxp/properties/schemaLanguage".equals(name)) {
/*     */ 
/*     */           
/* 507 */           if (this.fSAXParser.grammar != null) {
/* 508 */             throw new SAXNotSupportedException(
/* 509 */                 SAXMessageFormatter.formatMessage(this.fConfiguration.getLocale(), "schema-already-specified", new Object[] { name }));
/*     */           }
/* 511 */           if ("http://www.w3.org/2001/XMLSchema".equals(value)) {
/*     */             
/* 513 */             if (this.fSAXParser.isValidating()) {
/* 514 */               this.fSAXParser.schemaLanguage = "http://www.w3.org/2001/XMLSchema";
/* 515 */               setFeature("http://apache.org/xml/features/validation/schema", true);
/*     */ 
/*     */               
/* 518 */               if (!this.fInitProperties.containsKey("http://java.sun.com/xml/jaxp/properties/schemaLanguage")) {
/* 519 */                 this.fInitProperties.put("http://java.sun.com/xml/jaxp/properties/schemaLanguage", super.getProperty("http://java.sun.com/xml/jaxp/properties/schemaLanguage"));
/*     */               }
/* 521 */               super.setProperty("http://java.sun.com/xml/jaxp/properties/schemaLanguage", "http://www.w3.org/2001/XMLSchema");
/*     */             }
/*     */           
/*     */           }
/* 525 */           else if (value == null) {
/* 526 */             this.fSAXParser.schemaLanguage = null;
/* 527 */             setFeature("http://apache.org/xml/features/validation/schema", false);
/*     */           
/*     */           }
/*     */           else {
/*     */ 
/*     */             
/* 533 */             throw new SAXNotSupportedException(
/* 534 */                 SAXMessageFormatter.formatMessage(this.fConfiguration.getLocale(), "schema-not-supported", null));
/*     */           } 
/*     */           return;
/*     */         } 
/* 538 */         if ("http://java.sun.com/xml/jaxp/properties/schemaSource".equals(name)) {
/*     */ 
/*     */           
/* 541 */           if (this.fSAXParser.grammar != null) {
/* 542 */             throw new SAXNotSupportedException(
/* 543 */                 SAXMessageFormatter.formatMessage(this.fConfiguration.getLocale(), "schema-already-specified", new Object[] { name }));
/*     */           }
/* 545 */           String val = (String)getProperty("http://java.sun.com/xml/jaxp/properties/schemaLanguage");
/* 546 */           if (val != null && "http://www.w3.org/2001/XMLSchema".equals(val)) {
/* 547 */             if (!this.fInitProperties.containsKey("http://java.sun.com/xml/jaxp/properties/schemaSource")) {
/* 548 */               this.fInitProperties.put("http://java.sun.com/xml/jaxp/properties/schemaSource", super.getProperty("http://java.sun.com/xml/jaxp/properties/schemaSource"));
/*     */             }
/* 550 */             super.setProperty(name, value);
/*     */           } else {
/*     */             
/* 553 */             throw new SAXNotSupportedException(
/* 554 */                 SAXMessageFormatter.formatMessage(this.fConfiguration.getLocale(), "jaxp-order-not-supported", new Object[] { "http://java.sun.com/xml/jaxp/properties/schemaLanguage", "http://java.sun.com/xml/jaxp/properties/schemaSource" }));
/*     */           } 
/*     */ 
/*     */           
/*     */           return;
/*     */         } 
/*     */       } 
/*     */       
/* 562 */       if (this.fSAXParser != null && this.fSAXParser.fSchemaValidator != null) {
/* 563 */         setSchemaValidatorProperty(name, value);
/*     */       }
/*     */ 
/*     */       
/* 567 */       if (this.fSecurityManager == null || 
/* 568 */         !this.fSecurityManager.setLimit(name, XMLSecurityManager.State.APIPROPERTY, value))
/*     */       {
/* 570 */         if (this.fSecurityPropertyMgr == null || 
/* 571 */           !this.fSecurityPropertyMgr.setValue(name, XMLSecurityPropertyManager.State.APIPROPERTY, value)) {
/*     */           
/* 573 */           if (!this.fInitProperties.containsKey(name)) {
/* 574 */             this.fInitProperties.put(name, super.getProperty(name));
/*     */           }
/* 576 */           super.setProperty(name, value);
/*     */         } 
/*     */       }
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public synchronized Object getProperty(String name) throws SAXNotRecognizedException, SAXNotSupportedException {
/* 584 */       if (name == null)
/*     */       {
/* 586 */         throw new NullPointerException();
/*     */       }
/* 588 */       if (this.fSAXParser != null && "http://java.sun.com/xml/jaxp/properties/schemaLanguage".equals(name))
/*     */       {
/* 590 */         return this.fSAXParser.schemaLanguage;
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 595 */       String propertyValue = (this.fSecurityManager != null) ? this.fSecurityManager.getLimitAsString(name) : null;
/* 596 */       if (propertyValue != null) {
/* 597 */         return propertyValue;
/*     */       }
/*     */       
/* 600 */       propertyValue = (this.fSecurityPropertyMgr != null) ? this.fSecurityPropertyMgr.getValue(name) : null;
/* 601 */       if (propertyValue != null) {
/* 602 */         return propertyValue;
/*     */       }
/*     */ 
/*     */       
/* 606 */       return super.getProperty(name);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     synchronized void restoreInitState() throws SAXNotRecognizedException, SAXNotSupportedException {
/* 612 */       if (!this.fInitFeatures.isEmpty()) {
/* 613 */         Iterator<Map.Entry> iter = this.fInitFeatures.entrySet().iterator();
/* 614 */         while (iter.hasNext()) {
/* 615 */           Map.Entry entry = iter.next();
/* 616 */           String name = (String)entry.getKey();
/* 617 */           boolean value = ((Boolean)entry.getValue()).booleanValue();
/* 618 */           super.setFeature(name, value);
/*     */         } 
/* 620 */         this.fInitFeatures.clear();
/*     */       } 
/* 622 */       if (!this.fInitProperties.isEmpty()) {
/* 623 */         Iterator<Map.Entry> iter = this.fInitProperties.entrySet().iterator();
/* 624 */         while (iter.hasNext()) {
/* 625 */           Map.Entry entry = iter.next();
/* 626 */           String name = (String)entry.getKey();
/* 627 */           Object value = entry.getValue();
/* 628 */           super.setProperty(name, value);
/*     */         } 
/* 630 */         this.fInitProperties.clear();
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void parse(InputSource inputSource) throws SAXException, IOException {
/* 636 */       if (this.fSAXParser != null && this.fSAXParser.fSchemaValidator != null) {
/* 637 */         if (this.fSAXParser.fSchemaValidationManager != null) {
/* 638 */           this.fSAXParser.fSchemaValidationManager.reset();
/* 639 */           this.fSAXParser.fUnparsedEntityHandler.reset();
/*     */         } 
/* 641 */         resetSchemaValidator();
/*     */       } 
/* 643 */       super.parse(inputSource);
/*     */     }
/*     */ 
/*     */     
/*     */     public void parse(String systemId) throws SAXException, IOException {
/* 648 */       if (this.fSAXParser != null && this.fSAXParser.fSchemaValidator != null) {
/* 649 */         if (this.fSAXParser.fSchemaValidationManager != null) {
/* 650 */           this.fSAXParser.fSchemaValidationManager.reset();
/* 651 */           this.fSAXParser.fUnparsedEntityHandler.reset();
/*     */         } 
/* 653 */         resetSchemaValidator();
/*     */       } 
/* 655 */       super.parse(systemId);
/*     */     }
/*     */     
/*     */     XMLParserConfiguration getXMLParserConfiguration() {
/* 659 */       return this.fConfiguration;
/*     */     }
/*     */ 
/*     */     
/*     */     void setFeature0(String name, boolean value) throws SAXNotRecognizedException, SAXNotSupportedException {
/* 664 */       super.setFeature(name, value);
/*     */     }
/*     */ 
/*     */     
/*     */     boolean getFeature0(String name) throws SAXNotRecognizedException, SAXNotSupportedException {
/* 669 */       return super.getFeature(name);
/*     */     }
/*     */ 
/*     */     
/*     */     void setProperty0(String name, Object value) throws SAXNotRecognizedException, SAXNotSupportedException {
/* 674 */       super.setProperty(name, value);
/*     */     }
/*     */ 
/*     */     
/*     */     Object getProperty0(String name) throws SAXNotRecognizedException, SAXNotSupportedException {
/* 679 */       return super.getProperty(name);
/*     */     }
/*     */     
/*     */     Locale getLocale() {
/* 683 */       return this.fConfiguration.getLocale();
/*     */     }
/*     */ 
/*     */     
/*     */     private void setSchemaValidatorFeature(String name, boolean value) throws SAXNotRecognizedException, SAXNotSupportedException {
/*     */       try {
/* 689 */         this.fSAXParser.fSchemaValidator.setFeature(name, value);
/*     */       
/*     */       }
/* 692 */       catch (XMLConfigurationException e) {
/* 693 */         String identifier = e.getIdentifier();
/* 694 */         if (e.getType() == Status.NOT_RECOGNIZED) {
/* 695 */           throw new SAXNotRecognizedException(
/* 696 */               SAXMessageFormatter.formatMessage(this.fConfiguration.getLocale(), "feature-not-recognized", new Object[] { identifier }));
/*     */         }
/*     */ 
/*     */         
/* 700 */         throw new SAXNotSupportedException(
/* 701 */             SAXMessageFormatter.formatMessage(this.fConfiguration.getLocale(), "feature-not-supported", new Object[] { identifier }));
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private void setSchemaValidatorProperty(String name, Object value) throws SAXNotRecognizedException, SAXNotSupportedException {
/*     */       try {
/* 710 */         this.fSAXParser.fSchemaValidator.setProperty(name, value);
/*     */       
/*     */       }
/* 713 */       catch (XMLConfigurationException e) {
/* 714 */         String identifier = e.getIdentifier();
/* 715 */         if (e.getType() == Status.NOT_RECOGNIZED) {
/* 716 */           throw new SAXNotRecognizedException(
/* 717 */               SAXMessageFormatter.formatMessage(this.fConfiguration.getLocale(), "property-not-recognized", new Object[] { identifier }));
/*     */         }
/*     */ 
/*     */         
/* 721 */         throw new SAXNotSupportedException(
/* 722 */             SAXMessageFormatter.formatMessage(this.fConfiguration.getLocale(), "property-not-supported", new Object[] { identifier }));
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     private void resetSchemaValidator() throws SAXException {
/*     */       try {
/* 730 */         this.fSAXParser.fSchemaValidator.reset(this.fSAXParser.fSchemaValidatorComponentManager);
/*     */       
/*     */       }
/* 733 */       catch (XMLConfigurationException e) {
/* 734 */         throw new SAXException(e);
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xerces/internal/jaxp/SAXParserImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */