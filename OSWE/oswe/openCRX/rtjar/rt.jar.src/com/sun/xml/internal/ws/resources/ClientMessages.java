/*     */ package com.sun.xml.internal.ws.resources;
/*     */ 
/*     */ import com.sun.istack.internal.localization.Localizable;
/*     */ import com.sun.istack.internal.localization.LocalizableMessageFactory;
/*     */ import com.sun.istack.internal.localization.Localizer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class ClientMessages
/*     */ {
/*  39 */   private static final LocalizableMessageFactory messageFactory = new LocalizableMessageFactory("com.sun.xml.internal.ws.resources.client");
/*  40 */   private static final Localizer localizer = new Localizer();
/*     */   
/*     */   public static Localizable localizableFAILED_TO_PARSE(Object arg0, Object arg1) {
/*  43 */     return messageFactory.getMessage("failed.to.parse", new Object[] { arg0, arg1 });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String FAILED_TO_PARSE(Object arg0, Object arg1) {
/*  52 */     return localizer.localize(localizableFAILED_TO_PARSE(arg0, arg1));
/*     */   }
/*     */   
/*     */   public static Localizable localizableINVALID_BINDING_ID(Object arg0, Object arg1) {
/*  56 */     return messageFactory.getMessage("invalid.binding.id", new Object[] { arg0, arg1 });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String INVALID_BINDING_ID(Object arg0, Object arg1) {
/*  64 */     return localizer.localize(localizableINVALID_BINDING_ID(arg0, arg1));
/*     */   }
/*     */   
/*     */   public static Localizable localizableEPR_WITHOUT_ADDRESSING_ON() {
/*  68 */     return messageFactory.getMessage("epr.without.addressing.on", new Object[0]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String EPR_WITHOUT_ADDRESSING_ON() {
/*  76 */     return localizer.localize(localizableEPR_WITHOUT_ADDRESSING_ON());
/*     */   }
/*     */   
/*     */   public static Localizable localizableINVALID_SERVICE_NO_WSDL(Object arg0) {
/*  80 */     return messageFactory.getMessage("invalid.service.no.wsdl", new Object[] { arg0 });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String INVALID_SERVICE_NO_WSDL(Object arg0) {
/*  88 */     return localizer.localize(localizableINVALID_SERVICE_NO_WSDL(arg0));
/*     */   }
/*     */   
/*     */   public static Localizable localizableINVALID_SOAP_ROLE_NONE() {
/*  92 */     return messageFactory.getMessage("invalid.soap.role.none", new Object[0]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String INVALID_SOAP_ROLE_NONE() {
/* 100 */     return localizer.localize(localizableINVALID_SOAP_ROLE_NONE());
/*     */   }
/*     */   
/*     */   public static Localizable localizableUNDEFINED_BINDING(Object arg0) {
/* 104 */     return messageFactory.getMessage("undefined.binding", new Object[] { arg0 });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String UNDEFINED_BINDING(Object arg0) {
/* 112 */     return localizer.localize(localizableUNDEFINED_BINDING(arg0));
/*     */   }
/*     */   
/*     */   public static Localizable localizableHTTP_NOT_FOUND(Object arg0) {
/* 116 */     return messageFactory.getMessage("http.not.found", new Object[] { arg0 });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String HTTP_NOT_FOUND(Object arg0) {
/* 124 */     return localizer.localize(localizableHTTP_NOT_FOUND(arg0));
/*     */   }
/*     */   
/*     */   public static Localizable localizableINVALID_EPR_PORT_NAME(Object arg0, Object arg1) {
/* 128 */     return messageFactory.getMessage("invalid.epr.port.name", new Object[] { arg0, arg1 });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String INVALID_EPR_PORT_NAME(Object arg0, Object arg1) {
/* 136 */     return localizer.localize(localizableINVALID_EPR_PORT_NAME(arg0, arg1));
/*     */   }
/*     */   
/*     */   public static Localizable localizableFAILED_TO_PARSE_WITH_MEX(Object arg0, Object arg1, Object arg2) {
/* 140 */     return messageFactory.getMessage("failed.to.parseWithMEX", new Object[] { arg0, arg1, arg2 });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String FAILED_TO_PARSE_WITH_MEX(Object arg0, Object arg1, Object arg2) {
/* 151 */     return localizer.localize(localizableFAILED_TO_PARSE_WITH_MEX(arg0, arg1, arg2));
/*     */   }
/*     */   
/*     */   public static Localizable localizableHTTP_STATUS_CODE(Object arg0, Object arg1) {
/* 155 */     return messageFactory.getMessage("http.status.code", new Object[] { arg0, arg1 });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String HTTP_STATUS_CODE(Object arg0, Object arg1) {
/* 163 */     return localizer.localize(localizableHTTP_STATUS_CODE(arg0, arg1));
/*     */   }
/*     */   
/*     */   public static Localizable localizableINVALID_ADDRESS(Object arg0) {
/* 167 */     return messageFactory.getMessage("invalid.address", new Object[] { arg0 });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String INVALID_ADDRESS(Object arg0) {
/* 175 */     return localizer.localize(localizableINVALID_ADDRESS(arg0));
/*     */   }
/*     */   
/*     */   public static Localizable localizableUNDEFINED_PORT_TYPE(Object arg0) {
/* 179 */     return messageFactory.getMessage("undefined.portType", new Object[] { arg0 });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String UNDEFINED_PORT_TYPE(Object arg0) {
/* 187 */     return localizer.localize(localizableUNDEFINED_PORT_TYPE(arg0));
/*     */   }
/*     */   
/*     */   public static Localizable localizableWSDL_CONTAINS_NO_SERVICE(Object arg0) {
/* 191 */     return messageFactory.getMessage("wsdl.contains.no.service", new Object[] { arg0 });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String WSDL_CONTAINS_NO_SERVICE(Object arg0) {
/* 199 */     return localizer.localize(localizableWSDL_CONTAINS_NO_SERVICE(arg0));
/*     */   }
/*     */   
/*     */   public static Localizable localizableINVALID_SOAP_ACTION() {
/* 203 */     return messageFactory.getMessage("invalid.soap.action", new Object[0]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String INVALID_SOAP_ACTION() {
/* 211 */     return localizer.localize(localizableINVALID_SOAP_ACTION());
/*     */   }
/*     */   
/*     */   public static Localizable localizableNON_LOGICAL_HANDLER_SET(Object arg0) {
/* 215 */     return messageFactory.getMessage("non.logical.handler.set", new Object[] { arg0 });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String NON_LOGICAL_HANDLER_SET(Object arg0) {
/* 223 */     return localizer.localize(localizableNON_LOGICAL_HANDLER_SET(arg0));
/*     */   }
/*     */   
/*     */   public static Localizable localizableLOCAL_CLIENT_FAILED(Object arg0) {
/* 227 */     return messageFactory.getMessage("local.client.failed", new Object[] { arg0 });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String LOCAL_CLIENT_FAILED(Object arg0) {
/* 235 */     return localizer.localize(localizableLOCAL_CLIENT_FAILED(arg0));
/*     */   }
/*     */   
/*     */   public static Localizable localizableRUNTIME_WSDLPARSER_INVALID_WSDL(Object arg0, Object arg1, Object arg2, Object arg3) {
/* 239 */     return messageFactory.getMessage("runtime.wsdlparser.invalidWSDL", new Object[] { arg0, arg1, arg2, arg3 });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String RUNTIME_WSDLPARSER_INVALID_WSDL(Object arg0, Object arg1, Object arg2, Object arg3) {
/* 247 */     return localizer.localize(localizableRUNTIME_WSDLPARSER_INVALID_WSDL(arg0, arg1, arg2, arg3));
/*     */   }
/*     */   
/*     */   public static Localizable localizableWSDL_NOT_FOUND(Object arg0) {
/* 251 */     return messageFactory.getMessage("wsdl.not.found", new Object[] { arg0 });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String WSDL_NOT_FOUND(Object arg0) {
/* 259 */     return localizer.localize(localizableWSDL_NOT_FOUND(arg0));
/*     */   }
/*     */   
/*     */   public static Localizable localizableHTTP_CLIENT_FAILED(Object arg0) {
/* 263 */     return messageFactory.getMessage("http.client.failed", new Object[] { arg0 });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String HTTP_CLIENT_FAILED(Object arg0) {
/* 271 */     return localizer.localize(localizableHTTP_CLIENT_FAILED(arg0));
/*     */   }
/*     */   
/*     */   public static Localizable localizableINVALID_SERVICE_NAME_NULL(Object arg0) {
/* 275 */     return messageFactory.getMessage("invalid.service.name.null", new Object[] { arg0 });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String INVALID_SERVICE_NAME_NULL(Object arg0) {
/* 283 */     return localizer.localize(localizableINVALID_SERVICE_NAME_NULL(arg0));
/*     */   }
/*     */   
/*     */   public static Localizable localizableINVALID_WSDL_URL(Object arg0) {
/* 287 */     return messageFactory.getMessage("invalid.wsdl.url", new Object[] { arg0 });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String INVALID_WSDL_URL(Object arg0) {
/* 295 */     return localizer.localize(localizableINVALID_WSDL_URL(arg0));
/*     */   }
/*     */   
/*     */   public static Localizable localizableINVALID_PORT_NAME(Object arg0, Object arg1) {
/* 299 */     return messageFactory.getMessage("invalid.port.name", new Object[] { arg0, arg1 });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String INVALID_PORT_NAME(Object arg0, Object arg1) {
/* 307 */     return localizer.localize(localizableINVALID_PORT_NAME(arg0, arg1));
/*     */   }
/*     */   
/*     */   public static Localizable localizableINVALID_SERVICE_NAME(Object arg0, Object arg1) {
/* 311 */     return messageFactory.getMessage("invalid.service.name", new Object[] { arg0, arg1 });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String INVALID_SERVICE_NAME(Object arg0, Object arg1) {
/* 319 */     return localizer.localize(localizableINVALID_SERVICE_NAME(arg0, arg1));
/*     */   }
/*     */   
/*     */   public static Localizable localizableUNSUPPORTED_OPERATION(Object arg0, Object arg1, Object arg2) {
/* 323 */     return messageFactory.getMessage("unsupported.operation", new Object[] { arg0, arg1, arg2 });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String UNSUPPORTED_OPERATION(Object arg0, Object arg1, Object arg2) {
/* 331 */     return localizer.localize(localizableUNSUPPORTED_OPERATION(arg0, arg1, arg2));
/*     */   }
/*     */   
/*     */   public static Localizable localizableFAILED_TO_PARSE_EPR(Object arg0) {
/* 335 */     return messageFactory.getMessage("failed.to.parse.epr", new Object[] { arg0 });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String FAILED_TO_PARSE_EPR(Object arg0) {
/* 343 */     return localizer.localize(localizableFAILED_TO_PARSE_EPR(arg0));
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/resources/ClientMessages.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */