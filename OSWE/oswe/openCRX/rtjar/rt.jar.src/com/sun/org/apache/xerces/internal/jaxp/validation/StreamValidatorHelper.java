/*     */ package com.sun.org.apache.xerces.internal.jaxp.validation;
/*     */ 
/*     */ import com.sun.org.apache.xerces.internal.impl.XMLErrorReporter;
/*     */ import com.sun.org.apache.xerces.internal.impl.msg.XMLMessageFormatter;
/*     */ import com.sun.org.apache.xerces.internal.impl.xs.XMLSchemaValidator;
/*     */ import com.sun.org.apache.xerces.internal.parsers.XML11Configuration;
/*     */ import com.sun.org.apache.xerces.internal.utils.XMLSecurityManager;
/*     */ import com.sun.org.apache.xerces.internal.xni.XNIException;
/*     */ import com.sun.org.apache.xerces.internal.xni.parser.XMLInputSource;
/*     */ import com.sun.org.apache.xerces.internal.xni.parser.XMLParseException;
/*     */ import com.sun.org.apache.xerces.internal.xni.parser.XMLParserConfiguration;
/*     */ import java.io.IOException;
/*     */ import java.lang.ref.SoftReference;
/*     */ import javax.xml.transform.Result;
/*     */ import javax.xml.transform.Source;
/*     */ import javax.xml.transform.TransformerConfigurationException;
/*     */ import javax.xml.transform.TransformerFactoryConfigurationError;
/*     */ import javax.xml.transform.sax.SAXTransformerFactory;
/*     */ import javax.xml.transform.sax.TransformerHandler;
/*     */ import javax.xml.transform.stream.StreamSource;
/*     */ import jdk.xml.internal.JdkXmlUtils;
/*     */ import org.xml.sax.SAXException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class StreamValidatorHelper
/*     */   implements ValidatorHelper
/*     */ {
/*     */   private static final String PARSER_SETTINGS = "http://apache.org/xml/features/internal/parser-settings";
/*     */   private static final String ENTITY_RESOLVER = "http://apache.org/xml/properties/internal/entity-resolver";
/*     */   private static final String ERROR_HANDLER = "http://apache.org/xml/properties/internal/error-handler";
/*     */   private static final String ERROR_REPORTER = "http://apache.org/xml/properties/internal/error-reporter";
/*     */   private static final String SCHEMA_VALIDATOR = "http://apache.org/xml/properties/internal/validator/schema";
/*     */   private static final String SYMBOL_TABLE = "http://apache.org/xml/properties/internal/symbol-table";
/*     */   private static final String VALIDATION_MANAGER = "http://apache.org/xml/properties/internal/validation-manager";
/*     */   private static final String SECURITY_MANAGER = "http://apache.org/xml/properties/security-manager";
/*  95 */   private SoftReference fConfiguration = new SoftReference(null);
/*     */ 
/*     */   
/*     */   private XMLSchemaValidator fSchemaValidator;
/*     */ 
/*     */   
/*     */   private XMLSchemaValidatorComponentManager fComponentManager;
/*     */   
/* 103 */   private ValidatorHandlerImpl handler = null;
/*     */   
/*     */   public StreamValidatorHelper(XMLSchemaValidatorComponentManager componentManager) {
/* 106 */     this.fComponentManager = componentManager;
/* 107 */     this.fSchemaValidator = (XMLSchemaValidator)this.fComponentManager.getProperty("http://apache.org/xml/properties/internal/validator/schema");
/*     */   }
/*     */ 
/*     */   
/*     */   public void validate(Source source, Result result) throws SAXException, IOException {
/* 112 */     if (result == null || result instanceof javax.xml.transform.stream.StreamResult) {
/* 113 */       StreamSource streamSource = (StreamSource)source;
/*     */ 
/*     */       
/* 116 */       if (result != null) {
/*     */         TransformerHandler identityTransformerHandler; try {
/* 118 */           SAXTransformerFactory tf = JdkXmlUtils.getSAXTransformFactory(this.fComponentManager
/* 119 */               .getFeature("jdk.xml.overrideDefaultParser"));
/*     */           
/* 121 */           identityTransformerHandler = tf.newTransformerHandler();
/* 122 */         } catch (TransformerConfigurationException e) {
/* 123 */           throw new TransformerFactoryConfigurationError(e);
/*     */         } 
/*     */         
/* 126 */         this.handler = new ValidatorHandlerImpl(this.fComponentManager);
/* 127 */         this.handler.setContentHandler(identityTransformerHandler);
/* 128 */         identityTransformerHandler.setResult(result);
/*     */       } 
/*     */       
/* 131 */       XMLInputSource input = new XMLInputSource(streamSource.getPublicId(), streamSource.getSystemId(), null);
/* 132 */       input.setByteStream(streamSource.getInputStream());
/* 133 */       input.setCharacterStream(streamSource.getReader());
/*     */ 
/*     */ 
/*     */       
/* 137 */       XMLParserConfiguration config = this.fConfiguration.get();
/* 138 */       if (config == null) {
/* 139 */         config = initialize();
/*     */       
/*     */       }
/* 142 */       else if (this.fComponentManager.getFeature("http://apache.org/xml/features/internal/parser-settings")) {
/* 143 */         config.setProperty("http://apache.org/xml/properties/internal/entity-resolver", this.fComponentManager.getProperty("http://apache.org/xml/properties/internal/entity-resolver"));
/* 144 */         config.setProperty("http://apache.org/xml/properties/internal/error-handler", this.fComponentManager.getProperty("http://apache.org/xml/properties/internal/error-handler"));
/*     */       } 
/*     */ 
/*     */       
/* 148 */       this.fComponentManager.reset();
/* 149 */       this.fSchemaValidator.setDocumentHandler(this.handler);
/*     */       
/*     */       try {
/* 152 */         config.parse(input);
/*     */       }
/* 154 */       catch (XMLParseException e) {
/* 155 */         throw Util.toSAXParseException(e);
/*     */       }
/* 157 */       catch (XNIException e) {
/* 158 */         throw Util.toSAXException(e);
/*     */       } 
/*     */       return;
/*     */     } 
/* 162 */     throw new IllegalArgumentException(JAXPValidationMessageFormatter.formatMessage(this.fComponentManager.getLocale(), "SourceResultMismatch", new Object[] { source
/*     */             
/* 164 */             .getClass().getName(), result.getClass().getName() }));
/*     */   }
/*     */   
/*     */   private XMLParserConfiguration initialize() {
/* 168 */     XML11Configuration config = new XML11Configuration();
/* 169 */     if (this.fComponentManager.getFeature("http://javax.xml.XMLConstants/feature/secure-processing")) {
/* 170 */       config.setProperty("http://apache.org/xml/properties/security-manager", new XMLSecurityManager());
/*     */     }
/* 172 */     config.setProperty("http://apache.org/xml/properties/internal/entity-resolver", this.fComponentManager.getProperty("http://apache.org/xml/properties/internal/entity-resolver"));
/* 173 */     config.setProperty("http://apache.org/xml/properties/internal/error-handler", this.fComponentManager.getProperty("http://apache.org/xml/properties/internal/error-handler"));
/* 174 */     XMLErrorReporter errorReporter = (XMLErrorReporter)this.fComponentManager.getProperty("http://apache.org/xml/properties/internal/error-reporter");
/* 175 */     config.setProperty("http://apache.org/xml/properties/internal/error-reporter", errorReporter);
/*     */     
/* 177 */     if (errorReporter.getMessageFormatter("http://www.w3.org/TR/1998/REC-xml-19980210") == null) {
/* 178 */       XMLMessageFormatter xmft = new XMLMessageFormatter();
/* 179 */       errorReporter.putMessageFormatter("http://www.w3.org/TR/1998/REC-xml-19980210", xmft);
/* 180 */       errorReporter.putMessageFormatter("http://www.w3.org/TR/1999/REC-xml-names-19990114", xmft);
/*     */     } 
/* 182 */     config.setProperty("http://apache.org/xml/properties/internal/symbol-table", this.fComponentManager.getProperty("http://apache.org/xml/properties/internal/symbol-table"));
/* 183 */     config.setProperty("http://apache.org/xml/properties/internal/validation-manager", this.fComponentManager.getProperty("http://apache.org/xml/properties/internal/validation-manager"));
/* 184 */     config.setDocumentHandler(this.fSchemaValidator);
/* 185 */     config.setDTDHandler(null);
/* 186 */     config.setDTDContentModelHandler(null);
/* 187 */     config.setProperty("http://www.oracle.com/xml/jaxp/properties/xmlSecurityPropertyManager", this.fComponentManager
/* 188 */         .getProperty("http://www.oracle.com/xml/jaxp/properties/xmlSecurityPropertyManager"));
/* 189 */     config.setProperty("http://apache.org/xml/properties/security-manager", this.fComponentManager
/* 190 */         .getProperty("http://apache.org/xml/properties/security-manager"));
/* 191 */     this.fConfiguration = new SoftReference<>(config);
/* 192 */     return config;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xerces/internal/jaxp/validation/StreamValidatorHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */