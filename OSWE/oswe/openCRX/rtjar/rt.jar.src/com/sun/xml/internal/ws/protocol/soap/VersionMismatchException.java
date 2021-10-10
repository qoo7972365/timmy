/*    */ package com.sun.xml.internal.ws.protocol.soap;
/*    */ 
/*    */ import com.sun.xml.internal.ws.api.SOAPVersion;
/*    */ import com.sun.xml.internal.ws.api.message.ExceptionHasMessage;
/*    */ import com.sun.xml.internal.ws.api.message.Message;
/*    */ import com.sun.xml.internal.ws.encoding.soap.SOAP12Constants;
/*    */ import com.sun.xml.internal.ws.encoding.soap.SOAPConstants;
/*    */ import com.sun.xml.internal.ws.fault.SOAPFaultBuilder;
/*    */ import javax.xml.namespace.QName;
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
/*    */ 
/*    */ 
/*    */ public class VersionMismatchException
/*    */   extends ExceptionHasMessage
/*    */ {
/*    */   private final SOAPVersion soapVersion;
/*    */   
/*    */   public VersionMismatchException(SOAPVersion soapVersion, Object... args) {
/* 49 */     super("soap.version.mismatch.err", args);
/* 50 */     this.soapVersion = soapVersion;
/*    */   }
/*    */   
/*    */   public String getDefaultResourceBundleName() {
/* 54 */     return "com.sun.xml.internal.ws.resources.soap";
/*    */   }
/*    */   
/*    */   public Message getFaultMessage() {
/* 58 */     QName faultCode = (this.soapVersion == SOAPVersion.SOAP_11) ? SOAPConstants.FAULT_CODE_VERSION_MISMATCH : SOAP12Constants.FAULT_CODE_VERSION_MISMATCH;
/*    */ 
/*    */     
/* 61 */     return SOAPFaultBuilder.createSOAPFaultMessage(this.soapVersion, 
/* 62 */         getLocalizedMessage(), faultCode);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/protocol/soap/VersionMismatchException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */