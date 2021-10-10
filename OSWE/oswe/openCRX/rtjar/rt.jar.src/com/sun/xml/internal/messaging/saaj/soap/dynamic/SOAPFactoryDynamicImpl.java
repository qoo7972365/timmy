/*    */ package com.sun.xml.internal.messaging.saaj.soap.dynamic;
/*    */ 
/*    */ import com.sun.xml.internal.messaging.saaj.soap.SOAPDocumentImpl;
/*    */ import com.sun.xml.internal.messaging.saaj.soap.SOAPFactoryImpl;
/*    */ import javax.xml.soap.Detail;
/*    */ import javax.xml.soap.SOAPException;
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
/*    */ public class SOAPFactoryDynamicImpl
/*    */   extends SOAPFactoryImpl
/*    */ {
/*    */   protected SOAPDocumentImpl createDocument() {
/* 40 */     return null;
/*    */   }
/*    */   
/*    */   public Detail createDetail() throws SOAPException {
/* 44 */     throw new UnsupportedOperationException("createDetail() not supported for Dynamic Protocol");
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/messaging/saaj/soap/dynamic/SOAPFactoryDynamicImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */