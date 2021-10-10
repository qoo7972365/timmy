/*     */ package com.sun.org.apache.xerces.internal.impl;
/*     */ 
/*     */ import com.sun.org.apache.xerces.internal.util.DefaultErrorHandler;
/*     */ import com.sun.org.apache.xerces.internal.util.ErrorHandlerProxy;
/*     */ import com.sun.org.apache.xerces.internal.util.MessageFormatter;
/*     */ import com.sun.org.apache.xerces.internal.xni.XMLLocator;
/*     */ import com.sun.org.apache.xerces.internal.xni.XNIException;
/*     */ import com.sun.org.apache.xerces.internal.xni.parser.XMLComponent;
/*     */ import com.sun.org.apache.xerces.internal.xni.parser.XMLComponentManager;
/*     */ import com.sun.org.apache.xerces.internal.xni.parser.XMLConfigurationException;
/*     */ import com.sun.org.apache.xerces.internal.xni.parser.XMLErrorHandler;
/*     */ import com.sun.org.apache.xerces.internal.xni.parser.XMLParseException;
/*     */ import java.util.HashMap;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import org.xml.sax.ErrorHandler;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class XMLErrorReporter
/*     */   implements XMLComponent
/*     */ {
/*     */   public static final short SEVERITY_WARNING = 0;
/*     */   public static final short SEVERITY_ERROR = 1;
/*     */   public static final short SEVERITY_FATAL_ERROR = 2;
/*     */   protected static final String CONTINUE_AFTER_FATAL_ERROR = "http://apache.org/xml/features/continue-after-fatal-error";
/*     */   protected static final String ERROR_HANDLER = "http://apache.org/xml/properties/internal/error-handler";
/* 124 */   private static final String[] RECOGNIZED_FEATURES = new String[] { "http://apache.org/xml/features/continue-after-fatal-error" };
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 129 */   private static final Boolean[] FEATURE_DEFAULTS = new Boolean[] { null };
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 134 */   private static final String[] RECOGNIZED_PROPERTIES = new String[] { "http://apache.org/xml/properties/internal/error-handler" };
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 139 */   private static final Object[] PROPERTY_DEFAULTS = new Object[] { null };
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Locale fLocale;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Map<String, MessageFormatter> fMessageFormatters;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected XMLErrorHandler fErrorHandler;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected XMLLocator fLocator;
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean fContinueAfterFatalError;
/*     */ 
/*     */ 
/*     */   
/*     */   protected XMLErrorHandler fDefaultErrorHandler;
/*     */ 
/*     */ 
/*     */   
/* 171 */   private ErrorHandler fSaxProxy = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XMLErrorReporter() {
/* 195 */     this.fMessageFormatters = new HashMap<>();
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
/*     */   public void setLocale(Locale locale) {
/* 209 */     this.fLocale = locale;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Locale getLocale() {
/* 218 */     return this.fLocale;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDocumentLocator(XMLLocator locator) {
/* 227 */     this.fLocator = locator;
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
/*     */   public void putMessageFormatter(String domain, MessageFormatter messageFormatter) {
/* 243 */     this.fMessageFormatters.put(domain, messageFormatter);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MessageFormatter getMessageFormatter(String domain) {
/* 253 */     return this.fMessageFormatters.get(domain);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MessageFormatter removeMessageFormatter(String domain) {
/* 263 */     return this.fMessageFormatters.remove(domain);
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
/*     */   public String reportError(String domain, String key, Object[] arguments, short severity) throws XNIException {
/* 284 */     return reportError(this.fLocator, domain, key, arguments, severity);
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
/*     */   public String reportError(String domain, String key, Object[] arguments, short severity, Exception exception) throws XNIException {
/* 306 */     return reportError(this.fLocator, domain, key, arguments, severity, exception);
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
/*     */   public String reportError(XMLLocator location, String domain, String key, Object[] arguments, short severity) throws XNIException {
/* 327 */     return reportError(location, domain, key, arguments, severity, null);
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
/*     */   public String reportError(XMLLocator location, String domain, String key, Object[] arguments, short severity, Exception exception) throws XNIException {
/*     */     String message;
/* 354 */     MessageFormatter messageFormatter = getMessageFormatter(domain);
/*     */     
/* 356 */     if (messageFormatter != null) {
/* 357 */       message = messageFormatter.formatMessage(this.fLocale, key, arguments);
/*     */     } else {
/*     */       
/* 360 */       StringBuffer str = new StringBuffer();
/* 361 */       str.append(domain);
/* 362 */       str.append('#');
/* 363 */       str.append(key);
/* 364 */       int argCount = (arguments != null) ? arguments.length : 0;
/* 365 */       if (argCount > 0) {
/* 366 */         str.append('?');
/* 367 */         for (int i = 0; i < argCount; i++) {
/* 368 */           str.append(arguments[i]);
/* 369 */           if (i < argCount - 1) {
/* 370 */             str.append('&');
/*     */           }
/*     */         } 
/*     */       } 
/* 374 */       message = str.toString();
/*     */     } 
/* 376 */     XMLParseException parseException = (exception != null) ? new XMLParseException(location, message, exception) : new XMLParseException(location, message);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 381 */     XMLErrorHandler errorHandler = this.fErrorHandler;
/* 382 */     if (errorHandler == null) {
/* 383 */       if (this.fDefaultErrorHandler == null) {
/* 384 */         this.fDefaultErrorHandler = new DefaultErrorHandler();
/*     */       }
/* 386 */       errorHandler = this.fDefaultErrorHandler;
/*     */     } 
/*     */ 
/*     */     
/* 390 */     switch (severity) {
/*     */       case 0:
/* 392 */         errorHandler.warning(domain, key, parseException);
/*     */         break;
/*     */       
/*     */       case 1:
/* 396 */         errorHandler.error(domain, key, parseException);
/*     */         break;
/*     */       
/*     */       case 2:
/* 400 */         errorHandler.fatalError(domain, key, parseException);
/* 401 */         if (!this.fContinueAfterFatalError) {
/* 402 */           throw parseException;
/*     */         }
/*     */         break;
/*     */     } 
/*     */     
/* 407 */     return message;
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
/*     */   public void reset(XMLComponentManager componentManager) throws XNIException {
/* 433 */     this.fContinueAfterFatalError = componentManager.getFeature("http://apache.org/xml/features/continue-after-fatal-error", false);
/*     */ 
/*     */     
/* 436 */     this.fErrorHandler = (XMLErrorHandler)componentManager.getProperty("http://apache.org/xml/properties/internal/error-handler");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String[] getRecognizedFeatures() {
/* 446 */     return (String[])RECOGNIZED_FEATURES.clone();
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
/*     */   public void setFeature(String featureId, boolean state) throws XMLConfigurationException {
/* 471 */     if (featureId.startsWith("http://apache.org/xml/features/")) {
/* 472 */       int suffixLength = featureId.length() - "http://apache.org/xml/features/".length();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 479 */       if (suffixLength == "continue-after-fatal-error".length() && featureId
/* 480 */         .endsWith("continue-after-fatal-error")) {
/* 481 */         this.fContinueAfterFatalError = state;
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
/*     */   public boolean getFeature(String featureId) throws XMLConfigurationException {
/* 495 */     if (featureId.startsWith("http://apache.org/xml/features/")) {
/* 496 */       int suffixLength = featureId.length() - "http://apache.org/xml/features/".length();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 503 */       if (suffixLength == "continue-after-fatal-error".length() && featureId
/* 504 */         .endsWith("continue-after-fatal-error")) {
/* 505 */         return this.fContinueAfterFatalError;
/*     */       }
/*     */     } 
/* 508 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String[] getRecognizedProperties() {
/* 518 */     return (String[])RECOGNIZED_PROPERTIES.clone();
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
/*     */   public void setProperty(String propertyId, Object value) throws XMLConfigurationException {
/* 543 */     if (propertyId.startsWith("http://apache.org/xml/properties/")) {
/* 544 */       int suffixLength = propertyId.length() - "http://apache.org/xml/properties/".length();
/*     */       
/* 546 */       if (suffixLength == "internal/error-handler".length() && propertyId
/* 547 */         .endsWith("internal/error-handler")) {
/* 548 */         this.fErrorHandler = (XMLErrorHandler)value;
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
/*     */   public Boolean getFeatureDefault(String featureId) {
/* 564 */     for (int i = 0; i < RECOGNIZED_FEATURES.length; i++) {
/* 565 */       if (RECOGNIZED_FEATURES[i].equals(featureId)) {
/* 566 */         return FEATURE_DEFAULTS[i];
/*     */       }
/*     */     } 
/* 569 */     return null;
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
/*     */   public Object getPropertyDefault(String propertyId) {
/* 582 */     for (int i = 0; i < RECOGNIZED_PROPERTIES.length; i++) {
/* 583 */       if (RECOGNIZED_PROPERTIES[i].equals(propertyId)) {
/* 584 */         return PROPERTY_DEFAULTS[i];
/*     */       }
/*     */     } 
/* 587 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XMLErrorHandler getErrorHandler() {
/* 594 */     return this.fErrorHandler;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ErrorHandler getSAXErrorHandler() {
/* 602 */     if (this.fSaxProxy == null) {
/* 603 */       this.fSaxProxy = new ErrorHandlerProxy() {
/*     */           protected XMLErrorHandler getErrorHandler() {
/* 605 */             return XMLErrorReporter.this.fErrorHandler;
/*     */           }
/*     */         };
/*     */     }
/* 609 */     return this.fSaxProxy;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xerces/internal/impl/XMLErrorReporter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */