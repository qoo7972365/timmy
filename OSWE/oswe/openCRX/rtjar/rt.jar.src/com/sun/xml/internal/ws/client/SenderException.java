/*    */ package com.sun.xml.internal.ws.client;
/*    */ 
/*    */ import com.sun.istack.internal.localization.Localizable;
/*    */ import com.sun.xml.internal.ws.util.exception.JAXWSExceptionBase;
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
/*    */ public class SenderException
/*    */   extends JAXWSExceptionBase
/*    */ {
/*    */   public SenderException(String key, Object... args) {
/* 37 */     super(key, args);
/*    */   }
/*    */   
/*    */   public SenderException(Throwable throwable) {
/* 41 */     super(throwable);
/*    */   }
/*    */   
/*    */   public SenderException(Localizable arg) {
/* 45 */     super("sender.nestedError", new Object[] { arg });
/*    */   }
/*    */   
/*    */   public String getDefaultResourceBundleName() {
/* 49 */     return "com.sun.xml.internal.ws.resources.sender";
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/client/SenderException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */