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
/*     */ public final class EncodingMessages
/*     */ {
/*  39 */   private static final LocalizableMessageFactory messageFactory = new LocalizableMessageFactory("com.sun.xml.internal.ws.resources.encoding");
/*  40 */   private static final Localizer localizer = new Localizer();
/*     */   
/*     */   public static Localizable localizableFAILED_TO_READ_RESPONSE(Object arg0) {
/*  43 */     return messageFactory.getMessage("failed.to.read.response", new Object[] { arg0 });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String FAILED_TO_READ_RESPONSE(Object arg0) {
/*  51 */     return localizer.localize(localizableFAILED_TO_READ_RESPONSE(arg0));
/*     */   }
/*     */   
/*     */   public static Localizable localizableEXCEPTION_INCORRECT_TYPE(Object arg0) {
/*  55 */     return messageFactory.getMessage("exception.incorrectType", new Object[] { arg0 });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String EXCEPTION_INCORRECT_TYPE(Object arg0) {
/*  63 */     return localizer.localize(localizableEXCEPTION_INCORRECT_TYPE(arg0));
/*     */   }
/*     */   
/*     */   public static Localizable localizableEXCEPTION_NOTFOUND(Object arg0) {
/*  67 */     return messageFactory.getMessage("exception.notfound", new Object[] { arg0 });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String EXCEPTION_NOTFOUND(Object arg0) {
/*  75 */     return localizer.localize(localizableEXCEPTION_NOTFOUND(arg0));
/*     */   }
/*     */   
/*     */   public static Localizable localizableXSD_UNEXPECTED_ELEMENT_NAME(Object arg0, Object arg1) {
/*  79 */     return messageFactory.getMessage("xsd.unexpectedElementName", new Object[] { arg0, arg1 });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String XSD_UNEXPECTED_ELEMENT_NAME(Object arg0, Object arg1) {
/*  87 */     return localizer.localize(localizableXSD_UNEXPECTED_ELEMENT_NAME(arg0, arg1));
/*     */   }
/*     */   
/*     */   public static Localizable localizableNESTED_DESERIALIZATION_ERROR(Object arg0) {
/*  91 */     return messageFactory.getMessage("nestedDeserializationError", new Object[] { arg0 });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String NESTED_DESERIALIZATION_ERROR(Object arg0) {
/*  99 */     return localizer.localize(localizableNESTED_DESERIALIZATION_ERROR(arg0));
/*     */   }
/*     */   
/*     */   public static Localizable localizableNESTED_ENCODING_ERROR(Object arg0) {
/* 103 */     return messageFactory.getMessage("nestedEncodingError", new Object[] { arg0 });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String NESTED_ENCODING_ERROR(Object arg0) {
/* 111 */     return localizer.localize(localizableNESTED_ENCODING_ERROR(arg0));
/*     */   }
/*     */   
/*     */   public static Localizable localizableXSD_UNKNOWN_PREFIX(Object arg0) {
/* 115 */     return messageFactory.getMessage("xsd.unknownPrefix", new Object[] { arg0 });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String XSD_UNKNOWN_PREFIX(Object arg0) {
/* 123 */     return localizer.localize(localizableXSD_UNKNOWN_PREFIX(arg0));
/*     */   }
/*     */   
/*     */   public static Localizable localizableNESTED_SERIALIZATION_ERROR(Object arg0) {
/* 127 */     return messageFactory.getMessage("nestedSerializationError", new Object[] { arg0 });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String NESTED_SERIALIZATION_ERROR(Object arg0) {
/* 135 */     return localizer.localize(localizableNESTED_SERIALIZATION_ERROR(arg0));
/*     */   }
/*     */   
/*     */   public static Localizable localizableNO_SUCH_CONTENT_ID(Object arg0) {
/* 139 */     return messageFactory.getMessage("noSuchContentId", new Object[] { arg0 });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String NO_SUCH_CONTENT_ID(Object arg0) {
/* 147 */     return localizer.localize(localizableNO_SUCH_CONTENT_ID(arg0));
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/resources/EncodingMessages.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */