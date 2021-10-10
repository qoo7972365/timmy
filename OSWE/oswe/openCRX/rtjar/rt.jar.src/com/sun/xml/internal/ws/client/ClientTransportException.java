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
/*    */ public class ClientTransportException
/*    */   extends JAXWSExceptionBase
/*    */ {
/*    */   public ClientTransportException(Localizable msg) {
/* 37 */     super(msg);
/*    */   }
/*    */   
/*    */   public ClientTransportException(Localizable msg, Throwable cause) {
/* 41 */     super(msg, cause);
/*    */   }
/*    */   
/*    */   public ClientTransportException(Throwable throwable) {
/* 45 */     super(throwable);
/*    */   }
/*    */   
/*    */   public String getDefaultResourceBundleName() {
/* 49 */     return "com.sun.xml.internal.ws.resources.client";
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/client/ClientTransportException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */