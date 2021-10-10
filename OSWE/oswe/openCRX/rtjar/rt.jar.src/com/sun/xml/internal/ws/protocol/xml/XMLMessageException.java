/*    */ package com.sun.xml.internal.ws.protocol.xml;
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
/*    */ public class XMLMessageException
/*    */   extends JAXWSExceptionBase
/*    */ {
/*    */   public XMLMessageException(String key, Object... args) {
/* 37 */     super(key, args);
/*    */   }
/*    */   
/*    */   public XMLMessageException(Throwable throwable) {
/* 41 */     super(throwable);
/*    */   }
/*    */   
/*    */   public XMLMessageException(Localizable arg) {
/* 45 */     super("server.rt.err", new Object[] { arg });
/*    */   }
/*    */   
/*    */   public String getDefaultResourceBundleName() {
/* 49 */     return "com.sun.xml.internal.ws.resources.xmlmessage";
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/protocol/xml/XMLMessageException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */