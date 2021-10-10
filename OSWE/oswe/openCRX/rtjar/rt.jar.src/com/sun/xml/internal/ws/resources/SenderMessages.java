/*    */ package com.sun.xml.internal.ws.resources;
/*    */ 
/*    */ import com.sun.istack.internal.localization.Localizable;
/*    */ import com.sun.istack.internal.localization.LocalizableMessageFactory;
/*    */ import com.sun.istack.internal.localization.Localizer;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class SenderMessages
/*    */ {
/* 39 */   private static final LocalizableMessageFactory messageFactory = new LocalizableMessageFactory("com.sun.xml.internal.ws.resources.sender");
/* 40 */   private static final Localizer localizer = new Localizer();
/*    */   
/*    */   public static Localizable localizableSENDER_REQUEST_ILLEGAL_VALUE_FOR_CONTENT_NEGOTIATION(Object arg0) {
/* 43 */     return messageFactory.getMessage("sender.request.illegalValueForContentNegotiation", new Object[] { arg0 });
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static String SENDER_REQUEST_ILLEGAL_VALUE_FOR_CONTENT_NEGOTIATION(Object arg0) {
/* 51 */     return localizer.localize(localizableSENDER_REQUEST_ILLEGAL_VALUE_FOR_CONTENT_NEGOTIATION(arg0));
/*    */   }
/*    */   
/*    */   public static Localizable localizableSENDER_RESPONSE_CANNOT_DECODE_FAULT_DETAIL() {
/* 55 */     return messageFactory.getMessage("sender.response.cannotDecodeFaultDetail", new Object[0]);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static String SENDER_RESPONSE_CANNOT_DECODE_FAULT_DETAIL() {
/* 63 */     return localizer.localize(localizableSENDER_RESPONSE_CANNOT_DECODE_FAULT_DETAIL());
/*    */   }
/*    */   
/*    */   public static Localizable localizableSENDER_NESTED_ERROR(Object arg0) {
/* 67 */     return messageFactory.getMessage("sender.nestedError", new Object[] { arg0 });
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static String SENDER_NESTED_ERROR(Object arg0) {
/* 75 */     return localizer.localize(localizableSENDER_NESTED_ERROR(arg0));
/*    */   }
/*    */   
/*    */   public static Localizable localizableSENDER_REQUEST_MESSAGE_NOT_READY() {
/* 79 */     return messageFactory.getMessage("sender.request.messageNotReady", new Object[0]);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static String SENDER_REQUEST_MESSAGE_NOT_READY() {
/* 87 */     return localizer.localize(localizableSENDER_REQUEST_MESSAGE_NOT_READY());
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/resources/SenderMessages.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */