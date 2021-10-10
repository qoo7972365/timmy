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
/*    */ public final class HttpserverMessages
/*    */ {
/* 39 */   private static final LocalizableMessageFactory messageFactory = new LocalizableMessageFactory("com.sun.xml.internal.ws.resources.httpserver");
/* 40 */   private static final Localizer localizer = new Localizer();
/*    */   
/*    */   public static Localizable localizableUNEXPECTED_HTTP_METHOD(Object arg0) {
/* 43 */     return messageFactory.getMessage("unexpected.http.method", new Object[] { arg0 });
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static String UNEXPECTED_HTTP_METHOD(Object arg0) {
/* 51 */     return localizer.localize(localizableUNEXPECTED_HTTP_METHOD(arg0));
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/resources/HttpserverMessages.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */