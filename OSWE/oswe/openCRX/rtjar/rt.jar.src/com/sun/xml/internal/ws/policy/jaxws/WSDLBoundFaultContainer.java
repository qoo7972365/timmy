/*    */ package com.sun.xml.internal.ws.policy.jaxws;
/*    */ 
/*    */ import com.sun.xml.internal.ws.api.model.wsdl.WSDLBoundFault;
/*    */ import com.sun.xml.internal.ws.api.model.wsdl.WSDLBoundOperation;
/*    */ import com.sun.xml.internal.ws.api.model.wsdl.WSDLObject;
/*    */ import org.xml.sax.Locator;
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
/*    */ class WSDLBoundFaultContainer
/*    */   implements WSDLObject
/*    */ {
/*    */   private final WSDLBoundFault boundFault;
/*    */   private final WSDLBoundOperation boundOperation;
/*    */   
/*    */   public WSDLBoundFaultContainer(WSDLBoundFault fault, WSDLBoundOperation operation) {
/* 46 */     this.boundFault = fault;
/* 47 */     this.boundOperation = operation;
/*    */   }
/*    */   
/*    */   public Locator getLocation() {
/* 51 */     return null;
/*    */   }
/*    */   
/*    */   public WSDLBoundFault getBoundFault() {
/* 55 */     return this.boundFault;
/*    */   }
/*    */   
/*    */   public WSDLBoundOperation getBoundOperation() {
/* 59 */     return this.boundOperation;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/policy/jaxws/WSDLBoundFaultContainer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */