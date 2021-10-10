/*    */ package javax.xml.ws.soap;
/*    */ 
/*    */ import javax.xml.soap.SOAPFault;
/*    */ import javax.xml.ws.ProtocolException;
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
/*    */ public class SOAPFaultException
/*    */   extends ProtocolException
/*    */ {
/*    */   private SOAPFault fault;
/*    */   
/*    */   public SOAPFaultException(SOAPFault fault) {
/* 63 */     super(fault.getFaultString());
/* 64 */     this.fault = fault;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public SOAPFault getFault() {
/* 73 */     return this.fault;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/xml/ws/soap/SOAPFaultException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */