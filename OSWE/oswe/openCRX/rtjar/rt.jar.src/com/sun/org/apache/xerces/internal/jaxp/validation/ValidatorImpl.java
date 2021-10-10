/*     */ package com.sun.org.apache.xerces.internal.jaxp.validation;
/*     */ 
/*     */ import com.sun.org.apache.xerces.internal.util.SAXMessageFormatter;
/*     */ import com.sun.org.apache.xerces.internal.util.Status;
/*     */ import com.sun.org.apache.xerces.internal.xni.parser.XMLConfigurationException;
/*     */ import com.sun.org.apache.xerces.internal.xs.AttributePSVI;
/*     */ import com.sun.org.apache.xerces.internal.xs.ElementPSVI;
/*     */ import com.sun.org.apache.xerces.internal.xs.PSVIProvider;
/*     */ import java.io.IOException;
/*     */ import javax.xml.transform.Result;
/*     */ import javax.xml.transform.Source;
/*     */ import javax.xml.validation.Validator;
/*     */ import org.w3c.dom.ls.LSResourceResolver;
/*     */ import org.xml.sax.ErrorHandler;
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
/*     */ final class ValidatorImpl
/*     */   extends Validator
/*     */   implements PSVIProvider
/*     */ {
/*     */   private XMLSchemaValidatorComponentManager fComponentManager;
/*     */   private ValidatorHandlerImpl fSAXValidatorHelper;
/*     */   private DOMValidatorHelper fDOMValidatorHelper;
/*     */   private StreamValidatorHelper fStreamValidatorHelper;
/*     */   private StAXValidatorHelper fStaxValidatorHelper;
/*     */   private boolean fConfigurationChanged = false;
/*     */   private boolean fErrorHandlerChanged = false;
/*     */   private boolean fResourceResolverChanged = false;
/*     */   private static final String CURRENT_ELEMENT_NODE = "http://apache.org/xml/properties/dom/current-element-node";
/*     */   
/*     */   public ValidatorImpl(XSGrammarPoolContainer grammarContainer) {
/*  90 */     this.fComponentManager = new XMLSchemaValidatorComponentManager(grammarContainer);
/*  91 */     setErrorHandler(null);
/*  92 */     setResourceResolver(null);
/*     */   }
/*     */ 
/*     */   
/*     */   public void validate(Source source, Result result) throws SAXException, IOException {
/*  97 */     if (source instanceof javax.xml.transform.sax.SAXSource) {
/*     */       
/*  99 */       if (this.fSAXValidatorHelper == null) {
/* 100 */         this.fSAXValidatorHelper = new ValidatorHandlerImpl(this.fComponentManager);
/*     */       }
/* 102 */       this.fSAXValidatorHelper.validate(source, result);
/*     */     }
/* 104 */     else if (source instanceof javax.xml.transform.dom.DOMSource) {
/*     */       
/* 106 */       if (this.fDOMValidatorHelper == null) {
/* 107 */         this.fDOMValidatorHelper = new DOMValidatorHelper(this.fComponentManager);
/*     */       }
/* 109 */       this.fDOMValidatorHelper.validate(source, result);
/*     */     }
/* 111 */     else if (source instanceof javax.xml.transform.stream.StreamSource) {
/*     */       
/* 113 */       if (this.fStreamValidatorHelper == null) {
/* 114 */         this.fStreamValidatorHelper = new StreamValidatorHelper(this.fComponentManager);
/*     */       }
/* 116 */       this.fStreamValidatorHelper.validate(source, result);
/*     */     }
/* 118 */     else if (source instanceof javax.xml.transform.stax.StAXSource) {
/*     */       
/* 120 */       if (this.fStaxValidatorHelper == null) {
/* 121 */         this.fStaxValidatorHelper = new StAXValidatorHelper(this.fComponentManager);
/*     */       }
/* 123 */       this.fStaxValidatorHelper.validate(source, result);
/*     */     } else {
/*     */       
/* 126 */       if (source == null) {
/* 127 */         throw new NullPointerException(JAXPValidationMessageFormatter.formatMessage(this.fComponentManager.getLocale(), "SourceParameterNull", null));
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 132 */       throw new IllegalArgumentException(JAXPValidationMessageFormatter.formatMessage(this.fComponentManager.getLocale(), "SourceNotAccepted", new Object[] { source
/* 133 */               .getClass().getName() }));
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setErrorHandler(ErrorHandler errorHandler) {
/* 138 */     this.fErrorHandlerChanged = (errorHandler != null);
/* 139 */     this.fComponentManager.setErrorHandler(errorHandler);
/*     */   }
/*     */   
/*     */   public ErrorHandler getErrorHandler() {
/* 143 */     return this.fComponentManager.getErrorHandler();
/*     */   }
/*     */   
/*     */   public void setResourceResolver(LSResourceResolver resourceResolver) {
/* 147 */     this.fResourceResolverChanged = (resourceResolver != null);
/* 148 */     this.fComponentManager.setResourceResolver(resourceResolver);
/*     */   }
/*     */   
/*     */   public LSResourceResolver getResourceResolver() {
/* 152 */     return this.fComponentManager.getResourceResolver();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean getFeature(String name) throws SAXNotRecognizedException, SAXNotSupportedException {
/* 157 */     if (name == null) {
/* 158 */       throw new NullPointerException();
/*     */     }
/*     */     try {
/* 161 */       return this.fComponentManager.getFeature(name);
/*     */     }
/* 163 */     catch (XMLConfigurationException e) {
/* 164 */       String identifier = e.getIdentifier();
/* 165 */       String key = (e.getType() == Status.NOT_RECOGNIZED) ? "feature-not-recognized" : "feature-not-supported";
/*     */       
/* 167 */       throw new SAXNotRecognizedException(
/* 168 */           SAXMessageFormatter.formatMessage(this.fComponentManager.getLocale(), key, new Object[] { identifier }));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setFeature(String name, boolean value) throws SAXNotRecognizedException, SAXNotSupportedException {
/* 175 */     if (name == null) {
/* 176 */       throw new NullPointerException();
/*     */     }
/*     */     try {
/* 179 */       this.fComponentManager.setFeature(name, value);
/*     */     }
/* 181 */     catch (XMLConfigurationException e) {
/* 182 */       String key, identifier = e.getIdentifier();
/*     */       
/* 184 */       if (e.getType() == Status.NOT_ALLOWED)
/*     */       {
/* 186 */         throw new SAXNotSupportedException(
/* 187 */             SAXMessageFormatter.formatMessage(this.fComponentManager.getLocale(), "jaxp-secureprocessing-feature", null));
/*     */       }
/* 189 */       if (e.getType() == Status.NOT_RECOGNIZED) {
/* 190 */         key = "feature-not-recognized";
/*     */       } else {
/* 192 */         key = "feature-not-supported";
/*     */       } 
/* 194 */       throw new SAXNotRecognizedException(
/* 195 */           SAXMessageFormatter.formatMessage(this.fComponentManager.getLocale(), key, new Object[] { identifier }));
/*     */     } 
/*     */     
/* 198 */     this.fConfigurationChanged = true;
/*     */   }
/*     */ 
/*     */   
/*     */   public Object getProperty(String name) throws SAXNotRecognizedException, SAXNotSupportedException {
/* 203 */     if (name == null) {
/* 204 */       throw new NullPointerException();
/*     */     }
/*     */     
/* 207 */     if ("http://apache.org/xml/properties/dom/current-element-node".equals(name)) {
/* 208 */       return (this.fDOMValidatorHelper != null) ? this.fDOMValidatorHelper.getCurrentElement() : null;
/*     */     }
/*     */     try {
/* 211 */       return this.fComponentManager.getProperty(name);
/*     */     }
/* 213 */     catch (XMLConfigurationException e) {
/* 214 */       String identifier = e.getIdentifier();
/* 215 */       String key = (e.getType() == Status.NOT_RECOGNIZED) ? "property-not-recognized" : "property-not-supported";
/*     */       
/* 217 */       throw new SAXNotRecognizedException(
/* 218 */           SAXMessageFormatter.formatMessage(this.fComponentManager.getLocale(), key, new Object[] { identifier }));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setProperty(String name, Object object) throws SAXNotRecognizedException, SAXNotSupportedException {
/* 225 */     if (name == null) {
/* 226 */       throw new NullPointerException();
/*     */     }
/*     */     try {
/* 229 */       this.fComponentManager.setProperty(name, object);
/*     */     }
/* 231 */     catch (XMLConfigurationException e) {
/* 232 */       String identifier = e.getIdentifier();
/* 233 */       String key = (e.getType() == Status.NOT_RECOGNIZED) ? "property-not-recognized" : "property-not-supported";
/*     */       
/* 235 */       throw new SAXNotRecognizedException(
/* 236 */           SAXMessageFormatter.formatMessage(this.fComponentManager.getLocale(), key, new Object[] { identifier }));
/*     */     } 
/*     */     
/* 239 */     this.fConfigurationChanged = true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void reset() {
/* 245 */     if (this.fConfigurationChanged) {
/* 246 */       this.fComponentManager.restoreInitialState();
/* 247 */       setErrorHandler(null);
/* 248 */       setResourceResolver(null);
/* 249 */       this.fConfigurationChanged = false;
/* 250 */       this.fErrorHandlerChanged = false;
/* 251 */       this.fResourceResolverChanged = false;
/*     */     } else {
/*     */       
/* 254 */       if (this.fErrorHandlerChanged) {
/* 255 */         setErrorHandler(null);
/* 256 */         this.fErrorHandlerChanged = false;
/*     */       } 
/* 258 */       if (this.fResourceResolverChanged) {
/* 259 */         setResourceResolver(null);
/* 260 */         this.fResourceResolverChanged = false;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ElementPSVI getElementPSVI() {
/* 270 */     return (this.fSAXValidatorHelper != null) ? this.fSAXValidatorHelper.getElementPSVI() : null;
/*     */   }
/*     */   
/*     */   public AttributePSVI getAttributePSVI(int index) {
/* 274 */     return (this.fSAXValidatorHelper != null) ? this.fSAXValidatorHelper.getAttributePSVI(index) : null;
/*     */   }
/*     */   
/*     */   public AttributePSVI getAttributePSVIByName(String uri, String localname) {
/* 278 */     return (this.fSAXValidatorHelper != null) ? this.fSAXValidatorHelper.getAttributePSVIByName(uri, localname) : null;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xerces/internal/jaxp/validation/ValidatorImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */