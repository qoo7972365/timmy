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
/*     */ public final class ServerMessages
/*     */ {
/*  39 */   private static final LocalizableMessageFactory messageFactory = new LocalizableMessageFactory("com.sun.xml.internal.ws.resources.server");
/*  40 */   private static final Localizer localizer = new Localizer();
/*     */   
/*     */   public static Localizable localizableRUNTIME_PARSER_WSDL_INCORRECTSERVICE(Object arg0, Object arg1) {
/*  43 */     return messageFactory.getMessage("runtime.parser.wsdl.incorrectservice", new Object[] { arg0, arg1 });
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
/*     */   public static String RUNTIME_PARSER_WSDL_INCORRECTSERVICE(Object arg0, Object arg1) {
/*  60 */     return localizer.localize(localizableRUNTIME_PARSER_WSDL_INCORRECTSERVICE(arg0, arg1));
/*     */   }
/*     */   
/*     */   public static Localizable localizableRUNTIME_PARSER_MISSING_ATTRIBUTE_NO_LINE() {
/*  64 */     return messageFactory.getMessage("runtime.parser.missing.attribute.no.line", new Object[0]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String RUNTIME_PARSER_MISSING_ATTRIBUTE_NO_LINE() {
/*  72 */     return localizer.localize(localizableRUNTIME_PARSER_MISSING_ATTRIBUTE_NO_LINE());
/*     */   }
/*     */   
/*     */   public static Localizable localizableSTATEFUL_COOKIE_HEADER_INCORRECT(Object arg0, Object arg1) {
/*  76 */     return messageFactory.getMessage("stateful.cookie.header.incorrect", new Object[] { arg0, arg1 });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String STATEFUL_COOKIE_HEADER_INCORRECT(Object arg0, Object arg1) {
/*  84 */     return localizer.localize(localizableSTATEFUL_COOKIE_HEADER_INCORRECT(arg0, arg1));
/*     */   }
/*     */   
/*     */   public static Localizable localizableNOT_IMPLEMENT_PROVIDER(Object arg0) {
/*  88 */     return messageFactory.getMessage("not.implement.provider", new Object[] { arg0 });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String NOT_IMPLEMENT_PROVIDER(Object arg0) {
/*  96 */     return localizer.localize(localizableNOT_IMPLEMENT_PROVIDER(arg0));
/*     */   }
/*     */   
/*     */   public static Localizable localizableSTATEFUL_REQURES_ADDRESSING(Object arg0) {
/* 100 */     return messageFactory.getMessage("stateful.requres.addressing", new Object[] { arg0 });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String STATEFUL_REQURES_ADDRESSING(Object arg0) {
/* 108 */     return localizer.localize(localizableSTATEFUL_REQURES_ADDRESSING(arg0));
/*     */   }
/*     */   
/*     */   public static Localizable localizableSOAPDECODER_ERR() {
/* 112 */     return messageFactory.getMessage("soapdecoder.err", new Object[0]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String SOAPDECODER_ERR() {
/* 120 */     return localizer.localize(localizableSOAPDECODER_ERR());
/*     */   }
/*     */   
/*     */   public static Localizable localizableGENERATE_NON_STANDARD_WSDL() {
/* 124 */     return messageFactory.getMessage("generate.non.standard.wsdl", new Object[0]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String GENERATE_NON_STANDARD_WSDL() {
/* 132 */     return localizer.localize(localizableGENERATE_NON_STANDARD_WSDL());
/*     */   }
/*     */   
/*     */   public static Localizable localizableDISPATCH_CANNOT_FIND_METHOD(Object arg0) {
/* 136 */     return messageFactory.getMessage("dispatch.cannotFindMethod", new Object[] { arg0 });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String DISPATCH_CANNOT_FIND_METHOD(Object arg0) {
/* 144 */     return localizer.localize(localizableDISPATCH_CANNOT_FIND_METHOD(arg0));
/*     */   }
/*     */   
/*     */   public static Localizable localizableNO_CONTENT_TYPE() {
/* 148 */     return messageFactory.getMessage("no.contentType", new Object[0]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String NO_CONTENT_TYPE() {
/* 156 */     return localizer.localize(localizableNO_CONTENT_TYPE());
/*     */   }
/*     */   
/*     */   public static Localizable localizableRUNTIME_PARSER_INVALID_VERSION_NUMBER() {
/* 160 */     return messageFactory.getMessage("runtime.parser.invalidVersionNumber", new Object[0]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String RUNTIME_PARSER_INVALID_VERSION_NUMBER() {
/* 168 */     return localizer.localize(localizableRUNTIME_PARSER_INVALID_VERSION_NUMBER());
/*     */   }
/*     */   
/*     */   public static Localizable localizablePROVIDER_INVALID_PARAMETER_TYPE(Object arg0, Object arg1) {
/* 172 */     return messageFactory.getMessage("provider.invalid.parameterType", new Object[] { arg0, arg1 });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String PROVIDER_INVALID_PARAMETER_TYPE(Object arg0, Object arg1) {
/* 180 */     return localizer.localize(localizablePROVIDER_INVALID_PARAMETER_TYPE(arg0, arg1));
/*     */   }
/*     */   
/*     */   public static Localizable localizableWRONG_NO_PARAMETERS(Object arg0) {
/* 184 */     return messageFactory.getMessage("wrong.no.parameters", new Object[] { arg0 });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String WRONG_NO_PARAMETERS(Object arg0) {
/* 192 */     return localizer.localize(localizableWRONG_NO_PARAMETERS(arg0));
/*     */   }
/*     */   
/*     */   public static Localizable localizableANNOTATION_ONLY_ONCE(Object arg0) {
/* 196 */     return messageFactory.getMessage("annotation.only.once", new Object[] { arg0 });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String ANNOTATION_ONLY_ONCE(Object arg0) {
/* 204 */     return localizer.localize(localizableANNOTATION_ONLY_ONCE(arg0));
/*     */   }
/*     */   
/*     */   public static Localizable localizableALREADY_HTTPS_SERVER(Object arg0) {
/* 208 */     return messageFactory.getMessage("already.https.server", new Object[] { arg0 });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String ALREADY_HTTPS_SERVER(Object arg0) {
/* 216 */     return localizer.localize(localizableALREADY_HTTPS_SERVER(arg0));
/*     */   }
/*     */   
/*     */   public static Localizable localizableRUNTIME_PARSER_XML_READER(Object arg0) {
/* 220 */     return messageFactory.getMessage("runtime.parser.xmlReader", new Object[] { arg0 });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String RUNTIME_PARSER_XML_READER(Object arg0) {
/* 228 */     return localizer.localize(localizableRUNTIME_PARSER_XML_READER(arg0));
/*     */   }
/*     */   
/*     */   public static Localizable localizableRUNTIME_PARSER_WSDL_INCORRECTSERVICEPORT(Object arg0, Object arg1, Object arg2) {
/* 232 */     return messageFactory.getMessage("runtime.parser.wsdl.incorrectserviceport", new Object[] { arg0, arg1, arg2 });
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
/*     */   public static String RUNTIME_PARSER_WSDL_INCORRECTSERVICEPORT(Object arg0, Object arg1, Object arg2) {
/* 247 */     return localizer.localize(localizableRUNTIME_PARSER_WSDL_INCORRECTSERVICEPORT(arg0, arg1, arg2));
/*     */   }
/*     */   
/*     */   public static Localizable localizableSERVER_RT_ERR(Object arg0) {
/* 251 */     return messageFactory.getMessage("server.rt.err", new Object[] { arg0 });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String SERVER_RT_ERR(Object arg0) {
/* 259 */     return localizer.localize(localizableSERVER_RT_ERR(arg0));
/*     */   }
/*     */   
/*     */   public static Localizable localizableRUNTIME_PARSER_INVALID_ATTRIBUTE_VALUE(Object arg0, Object arg1, Object arg2) {
/* 263 */     return messageFactory.getMessage("runtime.parser.invalidAttributeValue", new Object[] { arg0, arg1, arg2 });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String RUNTIME_PARSER_INVALID_ATTRIBUTE_VALUE(Object arg0, Object arg1, Object arg2) {
/* 271 */     return localizer.localize(localizableRUNTIME_PARSER_INVALID_ATTRIBUTE_VALUE(arg0, arg1, arg2));
/*     */   }
/*     */   
/*     */   public static Localizable localizableNO_CURRENT_PACKET() {
/* 275 */     return messageFactory.getMessage("no.current.packet", new Object[0]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String NO_CURRENT_PACKET() {
/* 283 */     return localizer.localize(localizableNO_CURRENT_PACKET());
/*     */   }
/*     */   
/*     */   public static Localizable localizableRUNTIME_PARSER_UNEXPECTED_CONTENT(Object arg0) {
/* 287 */     return messageFactory.getMessage("runtime.parser.unexpectedContent", new Object[] { arg0 });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String RUNTIME_PARSER_UNEXPECTED_CONTENT(Object arg0) {
/* 295 */     return localizer.localize(localizableRUNTIME_PARSER_UNEXPECTED_CONTENT(arg0));
/*     */   }
/*     */   
/*     */   public static Localizable localizableSTATEFUL_COOKIE_HEADER_REQUIRED(Object arg0) {
/* 299 */     return messageFactory.getMessage("stateful.cookie.header.required", new Object[] { arg0 });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String STATEFUL_COOKIE_HEADER_REQUIRED(Object arg0) {
/* 307 */     return localizer.localize(localizableSTATEFUL_COOKIE_HEADER_REQUIRED(arg0));
/*     */   }
/*     */   
/*     */   public static Localizable localizableNULL_IMPLEMENTOR() {
/* 311 */     return messageFactory.getMessage("null.implementor", new Object[0]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String NULL_IMPLEMENTOR() {
/* 319 */     return localizer.localize(localizableNULL_IMPLEMENTOR());
/*     */   }
/*     */   
/*     */   public static Localizable localizableRUNTIME_PARSER_WSDL(Object arg0) {
/* 323 */     return messageFactory.getMessage("runtime.parser.wsdl", new Object[] { arg0 });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String RUNTIME_PARSER_WSDL(Object arg0) {
/* 331 */     return localizer.localize(localizableRUNTIME_PARSER_WSDL(arg0));
/*     */   }
/*     */   
/*     */   public static Localizable localizableSOAPENCODER_ERR() {
/* 335 */     return messageFactory.getMessage("soapencoder.err", new Object[0]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String SOAPENCODER_ERR() {
/* 343 */     return localizer.localize(localizableSOAPENCODER_ERR());
/*     */   }
/*     */   
/*     */   public static Localizable localizableWSDL_REQUIRED() {
/* 347 */     return messageFactory.getMessage("wsdl.required", new Object[0]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String WSDL_REQUIRED() {
/* 355 */     return localizer.localize(localizableWSDL_REQUIRED());
/*     */   }
/*     */   
/*     */   public static Localizable localizableRUNTIME_PARSER_WSDL_NOSERVICE_IN_WSDLMODEL(Object arg0) {
/* 359 */     return messageFactory.getMessage("runtime.parser.wsdl.noservice.in.wsdlmodel", new Object[] { arg0 });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String RUNTIME_PARSER_WSDL_NOSERVICE_IN_WSDLMODEL(Object arg0) {
/* 367 */     return localizer.localize(localizableRUNTIME_PARSER_WSDL_NOSERVICE_IN_WSDLMODEL(arg0));
/*     */   }
/*     */   
/*     */   public static Localizable localizablePORT_NAME_REQUIRED() {
/* 371 */     return messageFactory.getMessage("port.name.required", new Object[0]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String PORT_NAME_REQUIRED() {
/* 379 */     return localizer.localize(localizablePORT_NAME_REQUIRED());
/*     */   }
/*     */   
/*     */   public static Localizable localizableWRONG_TNS_FOR_PORT(Object arg0) {
/* 383 */     return messageFactory.getMessage("wrong.tns.for.port", new Object[] { arg0 });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String WRONG_TNS_FOR_PORT(Object arg0) {
/* 391 */     return localizer.localize(localizableWRONG_TNS_FOR_PORT(arg0));
/*     */   }
/*     */   
/*     */   public static Localizable localizableRUNTIME_PARSER_WSDL_MULTIPLEBINDING(Object arg0, Object arg1, Object arg2) {
/* 395 */     return messageFactory.getMessage("runtime.parser.wsdl.multiplebinding", new Object[] { arg0, arg1, arg2 });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String RUNTIME_PARSER_WSDL_MULTIPLEBINDING(Object arg0, Object arg1, Object arg2) {
/* 403 */     return localizer.localize(localizableRUNTIME_PARSER_WSDL_MULTIPLEBINDING(arg0, arg1, arg2));
/*     */   }
/*     */   
/*     */   public static Localizable localizableNOT_KNOW_HTTP_CONTEXT_TYPE(Object arg0, Object arg1, Object arg2) {
/* 407 */     return messageFactory.getMessage("not.know.HttpContext.type", new Object[] { arg0, arg1, arg2 });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String NOT_KNOW_HTTP_CONTEXT_TYPE(Object arg0, Object arg1, Object arg2) {
/* 415 */     return localizer.localize(localizableNOT_KNOW_HTTP_CONTEXT_TYPE(arg0, arg1, arg2));
/*     */   }
/*     */   
/*     */   public static Localizable localizableNON_UNIQUE_DISPATCH_QNAME(Object arg0, Object arg1) {
/* 419 */     return messageFactory.getMessage("non.unique.dispatch.qname", new Object[] { arg0, arg1 });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String NON_UNIQUE_DISPATCH_QNAME(Object arg0, Object arg1) {
/* 427 */     return localizer.localize(localizableNON_UNIQUE_DISPATCH_QNAME(arg0, arg1));
/*     */   }
/*     */   
/*     */   public static Localizable localizableALREADY_HTTP_SERVER(Object arg0) {
/* 431 */     return messageFactory.getMessage("already.http.server", new Object[] { arg0 });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String ALREADY_HTTP_SERVER(Object arg0) {
/* 439 */     return localizer.localize(localizableALREADY_HTTP_SERVER(arg0));
/*     */   }
/*     */   
/*     */   public static Localizable localizableCAN_NOT_GENERATE_WSDL(Object arg0) {
/* 443 */     return messageFactory.getMessage("can.not.generate.wsdl", new Object[] { arg0 });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String CAN_NOT_GENERATE_WSDL(Object arg0) {
/* 451 */     return localizer.localize(localizableCAN_NOT_GENERATE_WSDL(arg0));
/*     */   }
/*     */   
/*     */   public static Localizable localizableRUNTIME_PARSER_INVALID_ATTRIBUTE_VALUE(Object arg0, Object arg1) {
/* 455 */     return messageFactory.getMessage("runtime.parser.invalid.attribute.value", new Object[] { arg0, arg1 });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String RUNTIME_PARSER_INVALID_ATTRIBUTE_VALUE(Object arg0, Object arg1) {
/* 463 */     return localizer.localize(localizableRUNTIME_PARSER_INVALID_ATTRIBUTE_VALUE(arg0, arg1));
/*     */   }
/*     */   
/*     */   public static Localizable localizableRUNTIME_PARSER_WRONG_ELEMENT(Object arg0, Object arg1, Object arg2) {
/* 467 */     return messageFactory.getMessage("runtime.parser.wrong.element", new Object[] { arg0, arg1, arg2 });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String RUNTIME_PARSER_WRONG_ELEMENT(Object arg0, Object arg1, Object arg2) {
/* 475 */     return localizer.localize(localizableRUNTIME_PARSER_WRONG_ELEMENT(arg0, arg1, arg2));
/*     */   }
/*     */   
/*     */   public static Localizable localizableRUNTIMEMODELER_INVALIDANNOTATION_ON_IMPL(Object arg0, Object arg1, Object arg2) {
/* 479 */     return messageFactory.getMessage("runtimemodeler.invalidannotationOnImpl", new Object[] { arg0, arg1, arg2 });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String RUNTIMEMODELER_INVALIDANNOTATION_ON_IMPL(Object arg0, Object arg1, Object arg2) {
/* 487 */     return localizer.localize(localizableRUNTIMEMODELER_INVALIDANNOTATION_ON_IMPL(arg0, arg1, arg2));
/*     */   }
/*     */   
/*     */   public static Localizable localizableSERVICE_NAME_REQUIRED() {
/* 491 */     return messageFactory.getMessage("service.name.required", new Object[0]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String SERVICE_NAME_REQUIRED() {
/* 499 */     return localizer.localize(localizableSERVICE_NAME_REQUIRED());
/*     */   }
/*     */   
/*     */   public static Localizable localizablePROVIDER_NOT_PARAMETERIZED(Object arg0) {
/* 503 */     return messageFactory.getMessage("provider.not.parameterized", new Object[] { arg0 });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String PROVIDER_NOT_PARAMETERIZED(Object arg0) {
/* 511 */     return localizer.localize(localizablePROVIDER_NOT_PARAMETERIZED(arg0));
/*     */   }
/*     */   
/*     */   public static Localizable localizableRUNTIME_WSDL_PATCHER() {
/* 515 */     return messageFactory.getMessage("runtime.wsdl.patcher", new Object[0]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String RUNTIME_WSDL_PATCHER() {
/* 523 */     return localizer.localize(localizableRUNTIME_WSDL_PATCHER());
/*     */   }
/*     */   
/*     */   public static Localizable localizableRUNTIME_SAXPARSER_EXCEPTION(Object arg0, Object arg1) {
/* 527 */     return messageFactory.getMessage("runtime.saxparser.exception", new Object[] { arg0, arg1 });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String RUNTIME_SAXPARSER_EXCEPTION(Object arg0, Object arg1) {
/* 536 */     return localizer.localize(localizableRUNTIME_SAXPARSER_EXCEPTION(arg0, arg1));
/*     */   }
/*     */   
/*     */   public static Localizable localizableWRONG_PARAMETER_TYPE(Object arg0) {
/* 540 */     return messageFactory.getMessage("wrong.parameter.type", new Object[] { arg0 });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String WRONG_PARAMETER_TYPE(Object arg0) {
/* 548 */     return localizer.localize(localizableWRONG_PARAMETER_TYPE(arg0));
/*     */   }
/*     */   
/*     */   public static Localizable localizableRUNTIME_PARSER_WSDL_NOT_FOUND(Object arg0) {
/* 552 */     return messageFactory.getMessage("runtime.parser.wsdl.not.found", new Object[] { arg0 });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String RUNTIME_PARSER_WSDL_NOT_FOUND(Object arg0) {
/* 560 */     return localizer.localize(localizableRUNTIME_PARSER_WSDL_NOT_FOUND(arg0));
/*     */   }
/*     */   
/*     */   public static Localizable localizableRUNTIME_PARSER_CLASS_NOT_FOUND(Object arg0) {
/* 564 */     return messageFactory.getMessage("runtime.parser.classNotFound", new Object[] { arg0 });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String RUNTIME_PARSER_CLASS_NOT_FOUND(Object arg0) {
/* 572 */     return localizer.localize(localizableRUNTIME_PARSER_CLASS_NOT_FOUND(arg0));
/*     */   }
/*     */   
/*     */   public static Localizable localizableUNSUPPORTED_CHARSET(Object arg0) {
/* 576 */     return messageFactory.getMessage("unsupported.charset", new Object[] { arg0 });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String UNSUPPORTED_CHARSET(Object arg0) {
/* 584 */     return localizer.localize(localizableUNSUPPORTED_CHARSET(arg0));
/*     */   }
/*     */   
/*     */   public static Localizable localizableSTATIC_RESOURCE_INJECTION_ONLY(Object arg0, Object arg1) {
/* 588 */     return messageFactory.getMessage("static.resource.injection.only", new Object[] { arg0, arg1 });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String STATIC_RESOURCE_INJECTION_ONLY(Object arg0, Object arg1) {
/* 596 */     return localizer.localize(localizableSTATIC_RESOURCE_INJECTION_ONLY(arg0, arg1));
/*     */   }
/*     */   
/*     */   public static Localizable localizableNOT_ZERO_PARAMETERS(Object arg0) {
/* 600 */     return messageFactory.getMessage("not.zero.parameters", new Object[] { arg0 });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String NOT_ZERO_PARAMETERS(Object arg0) {
/* 608 */     return localizer.localize(localizableNOT_ZERO_PARAMETERS(arg0));
/*     */   }
/*     */   
/*     */   public static Localizable localizableDUPLICATE_PRIMARY_WSDL(Object arg0) {
/* 612 */     return messageFactory.getMessage("duplicate.primary.wsdl", new Object[] { arg0 });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String DUPLICATE_PRIMARY_WSDL(Object arg0) {
/* 620 */     return localizer.localize(localizableDUPLICATE_PRIMARY_WSDL(arg0));
/*     */   }
/*     */   
/*     */   public static Localizable localizableDUPLICATE_ABSTRACT_WSDL(Object arg0) {
/* 624 */     return messageFactory.getMessage("duplicate.abstract.wsdl", new Object[] { arg0 });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String DUPLICATE_ABSTRACT_WSDL(Object arg0) {
/* 632 */     return localizer.localize(localizableDUPLICATE_ABSTRACT_WSDL(arg0));
/*     */   }
/*     */   
/*     */   public static Localizable localizableSTATEFUL_INVALID_WEBSERVICE_CONTEXT(Object arg0) {
/* 636 */     return messageFactory.getMessage("stateful.invalid.webservice.context", new Object[] { arg0 });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String STATEFUL_INVALID_WEBSERVICE_CONTEXT(Object arg0) {
/* 644 */     return localizer.localize(localizableSTATEFUL_INVALID_WEBSERVICE_CONTEXT(arg0));
/*     */   }
/*     */   
/*     */   public static Localizable localizableRUNTIME_PARSER_INVALID_ELEMENT(Object arg0, Object arg1) {
/* 648 */     return messageFactory.getMessage("runtime.parser.invalidElement", new Object[] { arg0, arg1 });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String RUNTIME_PARSER_INVALID_ELEMENT(Object arg0, Object arg1) {
/* 656 */     return localizer.localize(localizableRUNTIME_PARSER_INVALID_ELEMENT(arg0, arg1));
/*     */   }
/*     */   
/*     */   public static Localizable localizableRUNTIME_PARSER_MISSING_ATTRIBUTE(Object arg0, Object arg1, Object arg2) {
/* 660 */     return messageFactory.getMessage("runtime.parser.missing.attribute", new Object[] { arg0, arg1, arg2 });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String RUNTIME_PARSER_MISSING_ATTRIBUTE(Object arg0, Object arg1, Object arg2) {
/* 668 */     return localizer.localize(localizableRUNTIME_PARSER_MISSING_ATTRIBUTE(arg0, arg1, arg2));
/*     */   }
/*     */   
/*     */   public static Localizable localizableWRONG_FIELD_TYPE(Object arg0) {
/* 672 */     return messageFactory.getMessage("wrong.field.type", new Object[] { arg0 });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String WRONG_FIELD_TYPE(Object arg0) {
/* 680 */     return localizer.localize(localizableWRONG_FIELD_TYPE(arg0));
/*     */   }
/*     */   
/*     */   public static Localizable localizableDUPLICATE_PORT_KNOWN_HEADER(Object arg0) {
/* 684 */     return messageFactory.getMessage("duplicate.portKnownHeader", new Object[] { arg0 });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String DUPLICATE_PORT_KNOWN_HEADER(Object arg0) {
/* 692 */     return localizer.localize(localizableDUPLICATE_PORT_KNOWN_HEADER(arg0));
/*     */   }
/*     */   
/*     */   public static Localizable localizableUNSUPPORTED_CONTENT_TYPE(Object arg0, Object arg1) {
/* 696 */     return messageFactory.getMessage("unsupported.contentType", new Object[] { arg0, arg1 });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String UNSUPPORTED_CONTENT_TYPE(Object arg0, Object arg1) {
/* 704 */     return localizer.localize(localizableUNSUPPORTED_CONTENT_TYPE(arg0, arg1));
/*     */   }
/*     */   
/*     */   public static Localizable localizableFAILED_TO_INSTANTIATE_INSTANCE_RESOLVER(Object arg0, Object arg1, Object arg2) {
/* 708 */     return messageFactory.getMessage("failed.to.instantiate.instanceResolver", new Object[] { arg0, arg1, arg2 });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String FAILED_TO_INSTANTIATE_INSTANCE_RESOLVER(Object arg0, Object arg1, Object arg2) {
/* 716 */     return localizer.localize(localizableFAILED_TO_INSTANTIATE_INSTANCE_RESOLVER(arg0, arg1, arg2));
/*     */   }
/*     */   
/*     */   public static Localizable localizableDD_MTOM_CONFLICT(Object arg0, Object arg1) {
/* 720 */     return messageFactory.getMessage("dd.mtom.conflict", new Object[] { arg0, arg1 });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String DD_MTOM_CONFLICT(Object arg0, Object arg1) {
/* 728 */     return localizer.localize(localizableDD_MTOM_CONFLICT(arg0, arg1));
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/resources/ServerMessages.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */