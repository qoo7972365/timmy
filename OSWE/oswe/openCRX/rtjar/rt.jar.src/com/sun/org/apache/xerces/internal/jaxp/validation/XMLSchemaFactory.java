/*     */ package com.sun.org.apache.xerces.internal.jaxp.validation;
/*     */ 
/*     */ import com.sun.org.apache.xerces.internal.impl.Constants;
/*     */ import com.sun.org.apache.xerces.internal.impl.xs.XMLSchemaLoader;
/*     */ import com.sun.org.apache.xerces.internal.util.DOMEntityResolverWrapper;
/*     */ import com.sun.org.apache.xerces.internal.util.DOMInputSource;
/*     */ import com.sun.org.apache.xerces.internal.util.ErrorHandlerWrapper;
/*     */ import com.sun.org.apache.xerces.internal.util.SAXInputSource;
/*     */ import com.sun.org.apache.xerces.internal.util.SAXMessageFormatter;
/*     */ import com.sun.org.apache.xerces.internal.util.StAXInputSource;
/*     */ import com.sun.org.apache.xerces.internal.util.Status;
/*     */ import com.sun.org.apache.xerces.internal.util.XMLGrammarPoolImpl;
/*     */ import com.sun.org.apache.xerces.internal.utils.XMLSecurityManager;
/*     */ import com.sun.org.apache.xerces.internal.utils.XMLSecurityPropertyManager;
/*     */ import com.sun.org.apache.xerces.internal.xni.XNIException;
/*     */ import com.sun.org.apache.xerces.internal.xni.grammars.Grammar;
/*     */ import com.sun.org.apache.xerces.internal.xni.grammars.XMLGrammarDescription;
/*     */ import com.sun.org.apache.xerces.internal.xni.grammars.XMLGrammarPool;
/*     */ import com.sun.org.apache.xerces.internal.xni.parser.XMLConfigurationException;
/*     */ import com.sun.org.apache.xerces.internal.xni.parser.XMLInputSource;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.Reader;
/*     */ import javax.xml.stream.XMLEventReader;
/*     */ import javax.xml.transform.Source;
/*     */ import javax.xml.transform.dom.DOMSource;
/*     */ import javax.xml.transform.sax.SAXSource;
/*     */ import javax.xml.transform.stax.StAXSource;
/*     */ import javax.xml.transform.stream.StreamSource;
/*     */ import javax.xml.validation.Schema;
/*     */ import javax.xml.validation.SchemaFactory;
/*     */ import jdk.xml.internal.JdkXmlFeatures;
/*     */ import org.w3c.dom.Node;
/*     */ import org.w3c.dom.ls.LSResourceResolver;
/*     */ import org.xml.sax.ErrorHandler;
/*     */ import org.xml.sax.InputSource;
/*     */ import org.xml.sax.SAXException;
/*     */ import org.xml.sax.SAXNotRecognizedException;
/*     */ import org.xml.sax.SAXNotSupportedException;
/*     */ import org.xml.sax.SAXParseException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class XMLSchemaFactory
/*     */   extends SchemaFactory
/*     */ {
/*     */   private static final String SCHEMA_FULL_CHECKING = "http://apache.org/xml/features/validation/schema-full-checking";
/*     */   private static final String XMLGRAMMAR_POOL = "http://apache.org/xml/properties/internal/grammar-pool";
/*     */   private static final String SECURITY_MANAGER = "http://apache.org/xml/properties/security-manager";
/*     */   private static final String XML_SECURITY_PROPERTY_MANAGER = "http://www.oracle.com/xml/jaxp/properties/xmlSecurityPropertyManager";
/*  97 */   private final XMLSchemaLoader fXMLSchemaLoader = new XMLSchemaLoader();
/*     */ 
/*     */ 
/*     */   
/*     */   private ErrorHandler fErrorHandler;
/*     */ 
/*     */   
/*     */   private LSResourceResolver fLSResourceResolver;
/*     */ 
/*     */   
/*     */   private final DOMEntityResolverWrapper fDOMEntityResolverWrapper;
/*     */ 
/*     */   
/*     */   private ErrorHandlerWrapper fErrorHandlerWrapper;
/*     */ 
/*     */   
/*     */   private XMLSecurityManager fSecurityManager;
/*     */ 
/*     */   
/*     */   private XMLSecurityPropertyManager fSecurityPropertyMgr;
/*     */ 
/*     */   
/*     */   private XMLGrammarPoolWrapper fXMLGrammarPoolWrapper;
/*     */ 
/*     */   
/*     */   private final JdkXmlFeatures fXmlFeatures;
/*     */ 
/*     */   
/*     */   private final boolean fOverrideDefaultParser;
/*     */ 
/*     */ 
/*     */   
/*     */   public XMLSchemaFactory() {
/* 130 */     this.fErrorHandlerWrapper = new ErrorHandlerWrapper(DraconianErrorHandler.getInstance());
/* 131 */     this.fDOMEntityResolverWrapper = new DOMEntityResolverWrapper();
/* 132 */     this.fXMLGrammarPoolWrapper = new XMLGrammarPoolWrapper();
/* 133 */     this.fXMLSchemaLoader.setFeature("http://apache.org/xml/features/validation/schema-full-checking", true);
/* 134 */     this.fXMLSchemaLoader.setProperty("http://apache.org/xml/properties/internal/grammar-pool", this.fXMLGrammarPoolWrapper);
/* 135 */     this.fXMLSchemaLoader.setEntityResolver(this.fDOMEntityResolverWrapper);
/* 136 */     this.fXMLSchemaLoader.setErrorHandler(this.fErrorHandlerWrapper);
/*     */ 
/*     */     
/* 139 */     this.fSecurityManager = new XMLSecurityManager(true);
/* 140 */     this.fXMLSchemaLoader.setProperty("http://apache.org/xml/properties/security-manager", this.fSecurityManager);
/*     */     
/* 142 */     this.fSecurityPropertyMgr = new XMLSecurityPropertyManager();
/* 143 */     this.fXMLSchemaLoader.setProperty("http://www.oracle.com/xml/jaxp/properties/xmlSecurityPropertyManager", this.fSecurityPropertyMgr);
/*     */     
/* 145 */     this.fXmlFeatures = new JdkXmlFeatures(this.fSecurityManager.isSecureProcessing());
/* 146 */     this.fOverrideDefaultParser = this.fXmlFeatures.getFeature(JdkXmlFeatures.XmlFeature.JDK_OVERRIDE_PARSER);
/*     */     
/* 148 */     this.fXMLSchemaLoader.setFeature("jdk.xml.overrideDefaultParser", this.fOverrideDefaultParser);
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
/*     */   public boolean isSchemaLanguageSupported(String schemaLanguage) {
/* 164 */     if (schemaLanguage == null) {
/* 165 */       throw new NullPointerException(JAXPValidationMessageFormatter.formatMessage(this.fXMLSchemaLoader.getLocale(), "SchemaLanguageNull", null));
/*     */     }
/*     */     
/* 168 */     if (schemaLanguage.length() == 0) {
/* 169 */       throw new IllegalArgumentException(JAXPValidationMessageFormatter.formatMessage(this.fXMLSchemaLoader.getLocale(), "SchemaLanguageLengthZero", null));
/*     */     }
/*     */ 
/*     */     
/* 173 */     return schemaLanguage.equals("http://www.w3.org/2001/XMLSchema");
/*     */   }
/*     */   
/*     */   public LSResourceResolver getResourceResolver() {
/* 177 */     return this.fLSResourceResolver;
/*     */   }
/*     */   
/*     */   public void setResourceResolver(LSResourceResolver resourceResolver) {
/* 181 */     this.fLSResourceResolver = resourceResolver;
/* 182 */     this.fDOMEntityResolverWrapper.setEntityResolver(resourceResolver);
/* 183 */     this.fXMLSchemaLoader.setEntityResolver(this.fDOMEntityResolverWrapper);
/*     */   }
/*     */   
/*     */   public ErrorHandler getErrorHandler() {
/* 187 */     return this.fErrorHandler;
/*     */   }
/*     */   
/*     */   public void setErrorHandler(ErrorHandler errorHandler) {
/* 191 */     this.fErrorHandler = errorHandler;
/* 192 */     this.fErrorHandlerWrapper.setErrorHandler((errorHandler != null) ? errorHandler : DraconianErrorHandler.getInstance());
/* 193 */     this.fXMLSchemaLoader.setErrorHandler(this.fErrorHandlerWrapper);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Schema newSchema(Source[] schemas) throws SAXException {
/* 199 */     XMLGrammarPoolImplExtension pool = new XMLGrammarPoolImplExtension();
/* 200 */     this.fXMLGrammarPoolWrapper.setGrammarPool(pool);
/*     */     
/* 202 */     XMLInputSource[] xmlInputSources = new XMLInputSource[schemas.length];
/*     */ 
/*     */     
/* 205 */     for (int i = 0; i < schemas.length; i++) {
/* 206 */       Source source = schemas[i];
/* 207 */       if (source instanceof StreamSource) {
/* 208 */         StreamSource streamSource = (StreamSource)source;
/* 209 */         String publicId = streamSource.getPublicId();
/* 210 */         String systemId = streamSource.getSystemId();
/* 211 */         InputStream inputStream = streamSource.getInputStream();
/* 212 */         Reader reader = streamSource.getReader();
/* 213 */         xmlInputSources[i] = new XMLInputSource(publicId, systemId, null);
/* 214 */         xmlInputSources[i].setByteStream(inputStream);
/* 215 */         xmlInputSources[i].setCharacterStream(reader);
/*     */       }
/* 217 */       else if (source instanceof SAXSource) {
/* 218 */         SAXSource saxSource = (SAXSource)source;
/* 219 */         InputSource inputSource = saxSource.getInputSource();
/* 220 */         if (inputSource == null) {
/* 221 */           throw new SAXException(JAXPValidationMessageFormatter.formatMessage(this.fXMLSchemaLoader.getLocale(), "SAXSourceNullInputSource", null));
/*     */         }
/*     */         
/* 224 */         xmlInputSources[i] = new SAXInputSource(saxSource.getXMLReader(), inputSource);
/*     */       }
/* 226 */       else if (source instanceof DOMSource) {
/* 227 */         DOMSource domSource = (DOMSource)source;
/* 228 */         Node node = domSource.getNode();
/* 229 */         String systemID = domSource.getSystemId();
/* 230 */         xmlInputSources[i] = new DOMInputSource(node, systemID);
/*     */       }
/* 232 */       else if (source instanceof StAXSource) {
/* 233 */         StAXSource staxSource = (StAXSource)source;
/* 234 */         XMLEventReader eventReader = staxSource.getXMLEventReader();
/* 235 */         if (eventReader != null) {
/* 236 */           xmlInputSources[i] = new StAXInputSource(eventReader);
/*     */         } else {
/*     */           
/* 239 */           xmlInputSources[i] = new StAXInputSource(staxSource.getXMLStreamReader());
/*     */         } 
/*     */       } else {
/* 242 */         if (source == null) {
/* 243 */           throw new NullPointerException(JAXPValidationMessageFormatter.formatMessage(this.fXMLSchemaLoader.getLocale(), "SchemaSourceArrayMemberNull", null));
/*     */         }
/*     */ 
/*     */         
/* 247 */         throw new IllegalArgumentException(JAXPValidationMessageFormatter.formatMessage(this.fXMLSchemaLoader.getLocale(), "SchemaFactorySourceUnrecognized", new Object[] { source
/*     */                 
/* 249 */                 .getClass().getName() }));
/*     */       } 
/*     */     } 
/*     */     
/*     */     try {
/* 254 */       this.fXMLSchemaLoader.loadGrammar(xmlInputSources);
/*     */     }
/* 256 */     catch (XNIException e) {
/*     */       
/* 258 */       throw Util.toSAXException(e);
/*     */     }
/* 260 */     catch (IOException e) {
/*     */       
/* 262 */       SAXParseException se = new SAXParseException(e.getMessage(), null, e);
/* 263 */       this.fErrorHandler.error(se);
/* 264 */       throw se;
/*     */     } 
/*     */ 
/*     */     
/* 268 */     this.fXMLGrammarPoolWrapper.setGrammarPool(null);
/*     */ 
/*     */     
/* 271 */     int grammarCount = pool.getGrammarCount();
/* 272 */     AbstractXMLSchema schema = null;
/* 273 */     if (grammarCount > 1) {
/* 274 */       schema = new XMLSchema(new ReadOnlyGrammarPool(pool));
/*     */     }
/* 276 */     else if (grammarCount == 1) {
/* 277 */       Grammar[] grammars = pool.retrieveInitialGrammarSet("http://www.w3.org/2001/XMLSchema");
/* 278 */       schema = new SimpleXMLSchema(grammars[0]);
/*     */     } else {
/*     */       
/* 281 */       schema = new EmptyXMLSchema();
/*     */     } 
/* 283 */     propagateFeatures(schema);
/* 284 */     propagateProperties(schema);
/* 285 */     return schema;
/*     */   }
/*     */ 
/*     */   
/*     */   public Schema newSchema() throws SAXException {
/* 290 */     AbstractXMLSchema schema = new WeakReferenceXMLSchema();
/* 291 */     propagateFeatures(schema);
/* 292 */     propagateProperties(schema);
/* 293 */     return schema;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean getFeature(String name) throws SAXNotRecognizedException, SAXNotSupportedException {
/* 298 */     if (name == null) {
/* 299 */       throw new NullPointerException(JAXPValidationMessageFormatter.formatMessage(this.fXMLSchemaLoader.getLocale(), "FeatureNameNull", null));
/*     */     }
/*     */     
/* 302 */     if (name.equals("http://javax.xml.XMLConstants/feature/secure-processing")) {
/* 303 */       return (this.fSecurityManager != null && this.fSecurityManager.isSecureProcessing());
/*     */     }
/*     */     try {
/* 306 */       return this.fXMLSchemaLoader.getFeature(name);
/*     */     }
/* 308 */     catch (XMLConfigurationException e) {
/* 309 */       String identifier = e.getIdentifier();
/* 310 */       if (e.getType() == Status.NOT_RECOGNIZED) {
/* 311 */         throw new SAXNotRecognizedException(
/* 312 */             SAXMessageFormatter.formatMessage(this.fXMLSchemaLoader.getLocale(), "feature-not-recognized", new Object[] { identifier }));
/*     */       }
/*     */ 
/*     */       
/* 316 */       throw new SAXNotSupportedException(
/* 317 */           SAXMessageFormatter.formatMessage(this.fXMLSchemaLoader.getLocale(), "feature-not-supported", new Object[] { identifier }));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object getProperty(String name) throws SAXNotRecognizedException, SAXNotSupportedException {
/* 325 */     if (name == null) {
/* 326 */       throw new NullPointerException(JAXPValidationMessageFormatter.formatMessage(this.fXMLSchemaLoader.getLocale(), "ProperyNameNull", null));
/*     */     }
/*     */     
/* 329 */     if (name.equals("http://apache.org/xml/properties/security-manager")) {
/* 330 */       return this.fSecurityManager;
/*     */     }
/* 332 */     if (name.equals("http://apache.org/xml/properties/internal/grammar-pool")) {
/* 333 */       throw new SAXNotSupportedException(
/* 334 */           SAXMessageFormatter.formatMessage(this.fXMLSchemaLoader.getLocale(), "property-not-supported", new Object[] { name }));
/*     */     }
/*     */ 
/*     */     
/* 338 */     int index = this.fXmlFeatures.getIndex(name);
/* 339 */     if (index > -1) {
/* 340 */       return Boolean.valueOf(this.fXmlFeatures.getFeature(index));
/*     */     }
/*     */     try {
/* 343 */       return this.fXMLSchemaLoader.getProperty(name);
/*     */     }
/* 345 */     catch (XMLConfigurationException e) {
/* 346 */       String identifier = e.getIdentifier();
/* 347 */       if (e.getType() == Status.NOT_RECOGNIZED) {
/* 348 */         throw new SAXNotRecognizedException(
/* 349 */             SAXMessageFormatter.formatMessage(this.fXMLSchemaLoader.getLocale(), "property-not-recognized", new Object[] { identifier }));
/*     */       }
/*     */ 
/*     */       
/* 353 */       throw new SAXNotSupportedException(
/* 354 */           SAXMessageFormatter.formatMessage(this.fXMLSchemaLoader.getLocale(), "property-not-supported", new Object[] { identifier }));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setFeature(String name, boolean value) throws SAXNotRecognizedException, SAXNotSupportedException {
/* 362 */     if (name == null) {
/* 363 */       throw new NullPointerException(JAXPValidationMessageFormatter.formatMessage(this.fXMLSchemaLoader.getLocale(), "FeatureNameNull", null));
/*     */     }
/*     */     
/* 366 */     if (name.equals("http://javax.xml.XMLConstants/feature/secure-processing")) {
/* 367 */       if (System.getSecurityManager() != null && !value) {
/* 368 */         throw new SAXNotSupportedException(
/* 369 */             SAXMessageFormatter.formatMessage(null, "jaxp-secureprocessing-feature", null));
/*     */       }
/*     */ 
/*     */       
/* 373 */       this.fSecurityManager.setSecureProcessing(value);
/* 374 */       if (value && 
/* 375 */         Constants.IS_JDK8_OR_ABOVE) {
/* 376 */         this.fSecurityPropertyMgr.setValue(XMLSecurityPropertyManager.Property.ACCESS_EXTERNAL_DTD, XMLSecurityPropertyManager.State.FSP, "");
/*     */         
/* 378 */         this.fSecurityPropertyMgr.setValue(XMLSecurityPropertyManager.Property.ACCESS_EXTERNAL_SCHEMA, XMLSecurityPropertyManager.State.FSP, "");
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 383 */       this.fXMLSchemaLoader.setProperty("http://apache.org/xml/properties/security-manager", this.fSecurityManager); return;
/*     */     } 
/* 385 */     if (name.equals("http://www.oracle.com/feature/use-service-mechanism"))
/*     */     {
/* 387 */       if (System.getSecurityManager() != null) {
/*     */         return;
/*     */       }
/*     */     }
/* 391 */     if (this.fXmlFeatures != null && this.fXmlFeatures
/* 392 */       .setFeature(name, JdkXmlFeatures.State.APIPROPERTY, Boolean.valueOf(value))) {
/* 393 */       if (name.equals("jdk.xml.overrideDefaultParser") || name
/* 394 */         .equals("http://www.oracle.com/feature/use-service-mechanism")) {
/* 395 */         this.fXMLSchemaLoader.setFeature(name, value);
/*     */       }
/*     */       return;
/*     */     } 
/*     */     try {
/* 400 */       this.fXMLSchemaLoader.setFeature(name, value);
/*     */     }
/* 402 */     catch (XMLConfigurationException e) {
/* 403 */       String identifier = e.getIdentifier();
/* 404 */       if (e.getType() == Status.NOT_RECOGNIZED) {
/* 405 */         throw new SAXNotRecognizedException(
/* 406 */             SAXMessageFormatter.formatMessage(this.fXMLSchemaLoader.getLocale(), "feature-not-recognized", new Object[] { identifier }));
/*     */       }
/*     */ 
/*     */       
/* 410 */       throw new SAXNotSupportedException(
/* 411 */           SAXMessageFormatter.formatMessage(this.fXMLSchemaLoader.getLocale(), "feature-not-supported", new Object[] { identifier }));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setProperty(String name, Object object) throws SAXNotRecognizedException, SAXNotSupportedException {
/* 419 */     if (name == null) {
/* 420 */       throw new NullPointerException(JAXPValidationMessageFormatter.formatMessage(this.fXMLSchemaLoader.getLocale(), "ProperyNameNull", null));
/*     */     }
/*     */     
/* 423 */     if (name.equals("http://apache.org/xml/properties/security-manager")) {
/* 424 */       this.fSecurityManager = XMLSecurityManager.convert(object, this.fSecurityManager);
/* 425 */       this.fXMLSchemaLoader.setProperty("http://apache.org/xml/properties/security-manager", this.fSecurityManager); return;
/*     */     } 
/* 427 */     if (name.equals("http://www.oracle.com/xml/jaxp/properties/xmlSecurityPropertyManager")) {
/* 428 */       if (object == null) {
/* 429 */         this.fSecurityPropertyMgr = new XMLSecurityPropertyManager();
/*     */       } else {
/* 431 */         this.fSecurityPropertyMgr = (XMLSecurityPropertyManager)object;
/*     */       } 
/* 433 */       this.fXMLSchemaLoader.setProperty("http://www.oracle.com/xml/jaxp/properties/xmlSecurityPropertyManager", this.fSecurityPropertyMgr);
/*     */       return;
/*     */     } 
/* 436 */     if (name.equals("http://apache.org/xml/properties/internal/grammar-pool")) {
/* 437 */       throw new SAXNotSupportedException(
/* 438 */           SAXMessageFormatter.formatMessage(this.fXMLSchemaLoader.getLocale(), "property-not-supported", new Object[] { name }));
/*     */     }
/*     */ 
/*     */     
/*     */     try {
/* 443 */       if (this.fSecurityManager == null || 
/* 444 */         !this.fSecurityManager.setLimit(name, XMLSecurityManager.State.APIPROPERTY, object))
/*     */       {
/* 446 */         if (this.fSecurityPropertyMgr == null || 
/* 447 */           !this.fSecurityPropertyMgr.setValue(name, XMLSecurityPropertyManager.State.APIPROPERTY, object))
/*     */         {
/* 449 */           this.fXMLSchemaLoader.setProperty(name, object);
/*     */         }
/*     */       }
/*     */     }
/* 453 */     catch (XMLConfigurationException e) {
/* 454 */       String identifier = e.getIdentifier();
/* 455 */       if (e.getType() == Status.NOT_RECOGNIZED) {
/* 456 */         throw new SAXNotRecognizedException(
/* 457 */             SAXMessageFormatter.formatMessage(this.fXMLSchemaLoader.getLocale(), "property-not-recognized", new Object[] { identifier }));
/*     */       }
/*     */ 
/*     */       
/* 461 */       throw new SAXNotSupportedException(
/* 462 */           SAXMessageFormatter.formatMessage(this.fXMLSchemaLoader.getLocale(), "property-not-supported", new Object[] { identifier }));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void propagateFeatures(AbstractXMLSchema schema) {
/* 469 */     schema.setFeature("http://javax.xml.XMLConstants/feature/secure-processing", (this.fSecurityManager != null && this.fSecurityManager
/* 470 */         .isSecureProcessing()));
/* 471 */     schema.setFeature("jdk.xml.overrideDefaultParser", this.fOverrideDefaultParser);
/* 472 */     String[] features = this.fXMLSchemaLoader.getRecognizedFeatures();
/* 473 */     for (int i = 0; i < features.length; i++) {
/* 474 */       boolean state = this.fXMLSchemaLoader.getFeature(features[i]);
/* 475 */       schema.setFeature(features[i], state);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void propagateProperties(AbstractXMLSchema schema) {
/* 480 */     String[] properties = this.fXMLSchemaLoader.getRecognizedProperties();
/* 481 */     for (int i = 0; i < properties.length; i++) {
/* 482 */       Object state = this.fXMLSchemaLoader.getProperty(properties[i]);
/* 483 */       schema.setProperty(properties[i], state);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static class XMLGrammarPoolImplExtension
/*     */     extends XMLGrammarPoolImpl
/*     */   {
/*     */     public XMLGrammarPoolImplExtension() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public XMLGrammarPoolImplExtension(int initialCapacity) {
/* 501 */       super(initialCapacity);
/*     */     }
/*     */ 
/*     */     
/*     */     int getGrammarCount() {
/* 506 */       return this.fGrammarCount;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static class XMLGrammarPoolWrapper
/*     */     implements XMLGrammarPool
/*     */   {
/*     */     private XMLGrammarPool fGrammarPool;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Grammar[] retrieveInitialGrammarSet(String grammarType) {
/* 523 */       return this.fGrammarPool.retrieveInitialGrammarSet(grammarType);
/*     */     }
/*     */     
/*     */     public void cacheGrammars(String grammarType, Grammar[] grammars) {
/* 527 */       this.fGrammarPool.cacheGrammars(grammarType, grammars);
/*     */     }
/*     */     
/*     */     public Grammar retrieveGrammar(XMLGrammarDescription desc) {
/* 531 */       return this.fGrammarPool.retrieveGrammar(desc);
/*     */     }
/*     */     
/*     */     public void lockPool() {
/* 535 */       this.fGrammarPool.lockPool();
/*     */     }
/*     */     
/*     */     public void unlockPool() {
/* 539 */       this.fGrammarPool.unlockPool();
/*     */     }
/*     */     
/*     */     public void clear() {
/* 543 */       this.fGrammarPool.clear();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     void setGrammarPool(XMLGrammarPool grammarPool) {
/* 551 */       this.fGrammarPool = grammarPool;
/*     */     }
/*     */     
/*     */     XMLGrammarPool getGrammarPool() {
/* 555 */       return this.fGrammarPool;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xerces/internal/jaxp/validation/XMLSchemaFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */