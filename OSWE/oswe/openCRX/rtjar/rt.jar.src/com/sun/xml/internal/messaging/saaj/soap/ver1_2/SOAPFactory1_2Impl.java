/*    */ package com.sun.xml.internal.messaging.saaj.soap.ver1_2;
/*    */ 
/*    */ import com.sun.xml.internal.messaging.saaj.soap.SOAPDocumentImpl;
/*    */ import com.sun.xml.internal.messaging.saaj.soap.SOAPFactoryImpl;
/*    */ import javax.xml.namespace.QName;
/*    */ import javax.xml.soap.Detail;
/*    */ import javax.xml.soap.SOAPException;
/*    */ import javax.xml.soap.SOAPFault;
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
/*    */ public class SOAPFactory1_2Impl
/*    */   extends SOAPFactoryImpl
/*    */ {
/*    */   protected SOAPDocumentImpl createDocument() {
/* 43 */     return (new SOAPPart1_2Impl()).getDocument();
/*    */   }
/*    */   
/*    */   public Detail createDetail() throws SOAPException {
/* 47 */     return (Detail)new Detail1_2Impl(createDocument());
/*    */   }
/*    */ 
/*    */   
/*    */   public SOAPFault createFault(String reasonText, QName faultCode) throws SOAPException {
/* 52 */     if (faultCode == null) {
/* 53 */       throw new IllegalArgumentException("faultCode argument for createFault was passed NULL");
/*    */     }
/* 55 */     if (reasonText == null) {
/* 56 */       throw new IllegalArgumentException("reasonText argument for createFault was passed NULL");
/*    */     }
/* 58 */     Fault1_2Impl fault = new Fault1_2Impl(createDocument(), null);
/* 59 */     fault.setFaultCode(faultCode);
/* 60 */     fault.setFaultString(reasonText);
/* 61 */     return (SOAPFault)fault;
/*    */   }
/*    */   
/*    */   public SOAPFault createFault() throws SOAPException {
/* 65 */     Fault1_2Impl fault = new Fault1_2Impl(createDocument(), null);
/* 66 */     fault.setFaultCode(fault.getDefaultFaultCode());
/* 67 */     fault.setFaultString("Fault string, and possibly fault code, not set");
/* 68 */     return (SOAPFault)fault;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/messaging/saaj/soap/ver1_2/SOAPFactory1_2Impl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */