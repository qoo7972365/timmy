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
/*     */ public final class AddressingMessages
/*     */ {
/*  39 */   private static final LocalizableMessageFactory messageFactory = new LocalizableMessageFactory("com.sun.xml.internal.ws.resources.addressing");
/*  40 */   private static final Localizer localizer = new Localizer();
/*     */   
/*     */   public static Localizable localizableNON_ANONYMOUS_RESPONSE_ONEWAY() {
/*  43 */     return messageFactory.getMessage("nonAnonymous.response.oneway", new Object[0]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String NON_ANONYMOUS_RESPONSE_ONEWAY() {
/*  51 */     return localizer.localize(localizableNON_ANONYMOUS_RESPONSE_ONEWAY());
/*     */   }
/*     */   
/*     */   public static Localizable localizableNULL_WSA_HEADERS() {
/*  55 */     return messageFactory.getMessage("null.wsa.headers", new Object[0]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String NULL_WSA_HEADERS() {
/*  63 */     return localizer.localize(localizableNULL_WSA_HEADERS());
/*     */   }
/*     */   
/*     */   public static Localizable localizableUNKNOWN_WSA_HEADER() {
/*  67 */     return messageFactory.getMessage("unknown.wsa.header", new Object[0]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String UNKNOWN_WSA_HEADER() {
/*  75 */     return localizer.localize(localizableUNKNOWN_WSA_HEADER());
/*     */   }
/*     */   
/*     */   public static Localizable localizableNULL_ACTION() {
/*  79 */     return messageFactory.getMessage("null.action", new Object[0]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String NULL_ACTION() {
/*  87 */     return localizer.localize(localizableNULL_ACTION());
/*     */   }
/*     */   
/*     */   public static Localizable localizableINVALID_WSAW_ANONYMOUS(Object arg0) {
/*  91 */     return messageFactory.getMessage("invalid.wsaw.anonymous", new Object[] { arg0 });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String INVALID_WSAW_ANONYMOUS(Object arg0) {
/*  99 */     return localizer.localize(localizableINVALID_WSAW_ANONYMOUS(arg0));
/*     */   }
/*     */   
/*     */   public static Localizable localizableNULL_SOAP_VERSION() {
/* 103 */     return messageFactory.getMessage("null.soap.version", new Object[0]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String NULL_SOAP_VERSION() {
/* 111 */     return localizer.localize(localizableNULL_SOAP_VERSION());
/*     */   }
/*     */   
/*     */   public static Localizable localizableWSDL_BOUND_OPERATION_NOT_FOUND(Object arg0) {
/* 115 */     return messageFactory.getMessage("wsdlBoundOperation.notFound", new Object[] { arg0 });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String WSDL_BOUND_OPERATION_NOT_FOUND(Object arg0) {
/* 123 */     return localizer.localize(localizableWSDL_BOUND_OPERATION_NOT_FOUND(arg0));
/*     */   }
/*     */   
/*     */   public static Localizable localizableNON_UNIQUE_OPERATION_SIGNATURE(Object arg0, Object arg1, Object arg2, Object arg3) {
/* 127 */     return messageFactory.getMessage("non.unique.operation.signature", new Object[] { arg0, arg1, arg2, arg3 });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String NON_UNIQUE_OPERATION_SIGNATURE(Object arg0, Object arg1, Object arg2, Object arg3) {
/* 135 */     return localizer.localize(localizableNON_UNIQUE_OPERATION_SIGNATURE(arg0, arg1, arg2, arg3));
/*     */   }
/*     */   
/*     */   public static Localizable localizableNON_ANONYMOUS_RESPONSE() {
/* 139 */     return messageFactory.getMessage("nonAnonymous.response", new Object[0]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String NON_ANONYMOUS_RESPONSE() {
/* 147 */     return localizer.localize(localizableNON_ANONYMOUS_RESPONSE());
/*     */   }
/*     */   
/*     */   public static Localizable localizableVALIDATION_SERVER_NULL_ACTION() {
/* 151 */     return messageFactory.getMessage("validation.server.nullAction", new Object[0]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String VALIDATION_SERVER_NULL_ACTION() {
/* 159 */     return localizer.localize(localizableVALIDATION_SERVER_NULL_ACTION());
/*     */   }
/*     */   
/*     */   public static Localizable localizableFAULT_TO_CANNOT_PARSE() {
/* 163 */     return messageFactory.getMessage("faultTo.cannot.parse", new Object[0]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String FAULT_TO_CANNOT_PARSE() {
/* 171 */     return localizer.localize(localizableFAULT_TO_CANNOT_PARSE());
/*     */   }
/*     */   
/*     */   public static Localizable localizableVALIDATION_CLIENT_NULL_ACTION() {
/* 175 */     return messageFactory.getMessage("validation.client.nullAction", new Object[0]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String VALIDATION_CLIENT_NULL_ACTION() {
/* 183 */     return localizer.localize(localizableVALIDATION_CLIENT_NULL_ACTION());
/*     */   }
/*     */   
/*     */   public static Localizable localizableNULL_MESSAGE() {
/* 187 */     return messageFactory.getMessage("null.message", new Object[0]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String NULL_MESSAGE() {
/* 195 */     return localizer.localize(localizableNULL_MESSAGE());
/*     */   }
/*     */   
/*     */   public static Localizable localizableACTION_NOT_SUPPORTED_EXCEPTION(Object arg0) {
/* 199 */     return messageFactory.getMessage("action.not.supported.exception", new Object[] { arg0 });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String ACTION_NOT_SUPPORTED_EXCEPTION(Object arg0) {
/* 207 */     return localizer.localize(localizableACTION_NOT_SUPPORTED_EXCEPTION(arg0));
/*     */   }
/*     */   
/*     */   public static Localizable localizableNON_ANONYMOUS_RESPONSE_NULL_HEADERS(Object arg0) {
/* 211 */     return messageFactory.getMessage("nonAnonymous.response.nullHeaders", new Object[] { arg0 });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String NON_ANONYMOUS_RESPONSE_NULL_HEADERS(Object arg0) {
/* 219 */     return localizer.localize(localizableNON_ANONYMOUS_RESPONSE_NULL_HEADERS(arg0));
/*     */   }
/*     */   
/*     */   public static Localizable localizableNON_ANONYMOUS_RESPONSE_SENDING(Object arg0) {
/* 223 */     return messageFactory.getMessage("nonAnonymous.response.sending", new Object[] { arg0 });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String NON_ANONYMOUS_RESPONSE_SENDING(Object arg0) {
/* 231 */     return localizer.localize(localizableNON_ANONYMOUS_RESPONSE_SENDING(arg0));
/*     */   }
/*     */   
/*     */   public static Localizable localizableREPLY_TO_CANNOT_PARSE() {
/* 235 */     return messageFactory.getMessage("replyTo.cannot.parse", new Object[0]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String REPLY_TO_CANNOT_PARSE() {
/* 243 */     return localizer.localize(localizableREPLY_TO_CANNOT_PARSE());
/*     */   }
/*     */   
/*     */   public static Localizable localizableINVALID_ADDRESSING_HEADER_EXCEPTION(Object arg0, Object arg1) {
/* 247 */     return messageFactory.getMessage("invalid.addressing.header.exception", new Object[] { arg0, arg1 });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String INVALID_ADDRESSING_HEADER_EXCEPTION(Object arg0, Object arg1) {
/* 255 */     return localizer.localize(localizableINVALID_ADDRESSING_HEADER_EXCEPTION(arg0, arg1));
/*     */   }
/*     */   
/*     */   public static Localizable localizableWSAW_ANONYMOUS_PROHIBITED() {
/* 259 */     return messageFactory.getMessage("wsaw.anonymousProhibited", new Object[0]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String WSAW_ANONYMOUS_PROHIBITED() {
/* 267 */     return localizer.localize(localizableWSAW_ANONYMOUS_PROHIBITED());
/*     */   }
/*     */   
/*     */   public static Localizable localizableNULL_WSDL_PORT() {
/* 271 */     return messageFactory.getMessage("null.wsdlPort", new Object[0]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String NULL_WSDL_PORT() {
/* 279 */     return localizer.localize(localizableNULL_WSDL_PORT());
/*     */   }
/*     */   
/*     */   public static Localizable localizableADDRESSING_SHOULD_BE_ENABLED() {
/* 283 */     return messageFactory.getMessage("addressing.should.be.enabled.", new Object[0]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String ADDRESSING_SHOULD_BE_ENABLED() {
/* 291 */     return localizer.localize(localizableADDRESSING_SHOULD_BE_ENABLED());
/*     */   }
/*     */   
/*     */   public static Localizable localizableNULL_ADDRESSING_VERSION() {
/* 295 */     return messageFactory.getMessage("null.addressing.version", new Object[0]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String NULL_ADDRESSING_VERSION() {
/* 303 */     return localizer.localize(localizableNULL_ADDRESSING_VERSION());
/*     */   }
/*     */   
/*     */   public static Localizable localizableMISSING_HEADER_EXCEPTION(Object arg0) {
/* 307 */     return messageFactory.getMessage("missing.header.exception", new Object[] { arg0 });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String MISSING_HEADER_EXCEPTION(Object arg0) {
/* 315 */     return localizer.localize(localizableMISSING_HEADER_EXCEPTION(arg0));
/*     */   }
/*     */   
/*     */   public static Localizable localizableNULL_PACKET() {
/* 319 */     return messageFactory.getMessage("null.packet", new Object[0]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String NULL_PACKET() {
/* 327 */     return localizer.localize(localizableNULL_PACKET());
/*     */   }
/*     */   
/*     */   public static Localizable localizableWRONG_ADDRESSING_VERSION(Object arg0, Object arg1) {
/* 331 */     return messageFactory.getMessage("wrong.addressing.version", new Object[] { arg0, arg1 });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String WRONG_ADDRESSING_VERSION(Object arg0, Object arg1) {
/* 339 */     return localizer.localize(localizableWRONG_ADDRESSING_VERSION(arg0, arg1));
/*     */   }
/*     */   
/*     */   public static Localizable localizableADDRESSING_NOT_ENABLED(Object arg0) {
/* 343 */     return messageFactory.getMessage("addressing.notEnabled", new Object[] { arg0 });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String ADDRESSING_NOT_ENABLED(Object arg0) {
/* 351 */     return localizer.localize(localizableADDRESSING_NOT_ENABLED(arg0));
/*     */   }
/*     */   
/*     */   public static Localizable localizableNON_ANONYMOUS_UNKNOWN_PROTOCOL(Object arg0) {
/* 355 */     return messageFactory.getMessage("nonAnonymous.unknown.protocol", new Object[] { arg0 });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String NON_ANONYMOUS_UNKNOWN_PROTOCOL(Object arg0) {
/* 363 */     return localizer.localize(localizableNON_ANONYMOUS_UNKNOWN_PROTOCOL(arg0));
/*     */   }
/*     */   
/*     */   public static Localizable localizableNULL_HEADERS() {
/* 367 */     return messageFactory.getMessage("null.headers", new Object[0]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String NULL_HEADERS() {
/* 375 */     return localizer.localize(localizableNULL_HEADERS());
/*     */   }
/*     */   
/*     */   public static Localizable localizableNULL_BINDING() {
/* 379 */     return messageFactory.getMessage("null.binding", new Object[0]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String NULL_BINDING() {
/* 387 */     return localizer.localize(localizableNULL_BINDING());
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/resources/AddressingMessages.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */