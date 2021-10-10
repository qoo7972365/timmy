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
/*     */ public final class SoapMessages
/*     */ {
/*  39 */   private static final LocalizableMessageFactory messageFactory = new LocalizableMessageFactory("com.sun.xml.internal.ws.resources.soap");
/*  40 */   private static final Localizer localizer = new Localizer();
/*     */   
/*     */   public static Localizable localizableSOAP_FAULT_CREATE_ERR(Object arg0) {
/*  43 */     return messageFactory.getMessage("soap.fault.create.err", new Object[] { arg0 });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String SOAP_FAULT_CREATE_ERR(Object arg0) {
/*  51 */     return localizer.localize(localizableSOAP_FAULT_CREATE_ERR(arg0));
/*     */   }
/*     */   
/*     */   public static Localizable localizableSOAP_MSG_FACTORY_CREATE_ERR(Object arg0) {
/*  55 */     return messageFactory.getMessage("soap.msg.factory.create.err", new Object[] { arg0 });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String SOAP_MSG_FACTORY_CREATE_ERR(Object arg0) {
/*  63 */     return localizer.localize(localizableSOAP_MSG_FACTORY_CREATE_ERR(arg0));
/*     */   }
/*     */   
/*     */   public static Localizable localizableSOAP_MSG_CREATE_ERR(Object arg0) {
/*  67 */     return messageFactory.getMessage("soap.msg.create.err", new Object[] { arg0 });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String SOAP_MSG_CREATE_ERR(Object arg0) {
/*  75 */     return localizer.localize(localizableSOAP_MSG_CREATE_ERR(arg0));
/*     */   }
/*     */   
/*     */   public static Localizable localizableSOAP_FACTORY_CREATE_ERR(Object arg0) {
/*  79 */     return messageFactory.getMessage("soap.factory.create.err", new Object[] { arg0 });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String SOAP_FACTORY_CREATE_ERR(Object arg0) {
/*  87 */     return localizer.localize(localizableSOAP_FACTORY_CREATE_ERR(arg0));
/*     */   }
/*     */   
/*     */   public static Localizable localizableSOAP_PROTOCOL_INVALID_FAULT_CODE(Object arg0) {
/*  91 */     return messageFactory.getMessage("soap.protocol.invalidFaultCode", new Object[] { arg0 });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String SOAP_PROTOCOL_INVALID_FAULT_CODE(Object arg0) {
/*  99 */     return localizer.localize(localizableSOAP_PROTOCOL_INVALID_FAULT_CODE(arg0));
/*     */   }
/*     */   
/*     */   public static Localizable localizableSOAP_VERSION_MISMATCH_ERR(Object arg0, Object arg1) {
/* 103 */     return messageFactory.getMessage("soap.version.mismatch.err", new Object[] { arg0, arg1 });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String SOAP_VERSION_MISMATCH_ERR(Object arg0, Object arg1) {
/* 111 */     return localizer.localize(localizableSOAP_VERSION_MISMATCH_ERR(arg0, arg1));
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/resources/SoapMessages.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */