/*    */ package com.sun.xml.internal.messaging.saaj.soap;
/*    */ 
/*    */ import com.sun.xml.internal.messaging.saaj.soap.dynamic.SOAPFactoryDynamicImpl;
/*    */ import com.sun.xml.internal.messaging.saaj.soap.dynamic.SOAPMessageFactoryDynamicImpl;
/*    */ import com.sun.xml.internal.messaging.saaj.soap.ver1_1.SOAPFactory1_1Impl;
/*    */ import com.sun.xml.internal.messaging.saaj.soap.ver1_1.SOAPMessageFactory1_1Impl;
/*    */ import com.sun.xml.internal.messaging.saaj.soap.ver1_2.SOAPFactory1_2Impl;
/*    */ import com.sun.xml.internal.messaging.saaj.soap.ver1_2.SOAPMessageFactory1_2Impl;
/*    */ import java.util.logging.Level;
/*    */ import java.util.logging.Logger;
/*    */ import javax.xml.soap.MessageFactory;
/*    */ import javax.xml.soap.SAAJMetaFactory;
/*    */ import javax.xml.soap.SOAPException;
/*    */ import javax.xml.soap.SOAPFactory;
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
/*    */ public class SAAJMetaFactoryImpl
/*    */   extends SAAJMetaFactory
/*    */ {
/* 42 */   protected static final Logger log = Logger.getLogger("com.sun.xml.internal.messaging.saaj.soap", "com.sun.xml.internal.messaging.saaj.soap.LocalStrings");
/*    */ 
/*    */ 
/*    */   
/*    */   protected MessageFactory newMessageFactory(String protocol) throws SOAPException {
/* 47 */     if ("SOAP 1.1 Protocol".equals(protocol))
/* 48 */       return (MessageFactory)new SOAPMessageFactory1_1Impl(); 
/* 49 */     if ("SOAP 1.2 Protocol".equals(protocol))
/* 50 */       return (MessageFactory)new SOAPMessageFactory1_2Impl(); 
/* 51 */     if ("Dynamic Protocol".equals(protocol)) {
/* 52 */       return (MessageFactory)new SOAPMessageFactoryDynamicImpl();
/*    */     }
/* 54 */     log.log(Level.SEVERE, "SAAJ0569.soap.unknown.protocol", new Object[] { protocol, "MessageFactory" });
/*    */ 
/*    */ 
/*    */     
/* 58 */     throw new SOAPException("Unknown Protocol: " + protocol + "  specified for creating MessageFactory");
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected SOAPFactory newSOAPFactory(String protocol) throws SOAPException {
/* 65 */     if ("SOAP 1.1 Protocol".equals(protocol))
/* 66 */       return (SOAPFactory)new SOAPFactory1_1Impl(); 
/* 67 */     if ("SOAP 1.2 Protocol".equals(protocol))
/* 68 */       return (SOAPFactory)new SOAPFactory1_2Impl(); 
/* 69 */     if ("Dynamic Protocol".equals(protocol)) {
/* 70 */       return (SOAPFactory)new SOAPFactoryDynamicImpl();
/*    */     }
/* 72 */     log.log(Level.SEVERE, "SAAJ0569.soap.unknown.protocol", new Object[] { protocol, "SOAPFactory" });
/*    */ 
/*    */ 
/*    */     
/* 76 */     throw new SOAPException("Unknown Protocol: " + protocol + "  specified for creating SOAPFactory");
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/messaging/saaj/soap/SAAJMetaFactoryImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */