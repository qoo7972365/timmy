/*    */ package com.sun.xml.internal.ws.fault;
/*    */ 
/*    */ import javax.xml.soap.SOAPFault;
/*    */ import javax.xml.ws.soap.SOAPFaultException;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ServerSOAPFaultException
/*    */   extends SOAPFaultException
/*    */ {
/*    */   public ServerSOAPFaultException(SOAPFault soapFault) {
/* 46 */     super(soapFault);
/*    */   }
/*    */   
/*    */   public String getMessage() {
/* 50 */     return "Client received SOAP Fault from server: " + super
/* 51 */       .getMessage() + " Please see the server log to find more detail regarding exact cause of the failure.";
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/fault/ServerSOAPFaultException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */