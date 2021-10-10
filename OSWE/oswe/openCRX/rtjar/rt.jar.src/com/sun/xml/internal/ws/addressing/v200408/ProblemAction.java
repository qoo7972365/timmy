/*    */ package com.sun.xml.internal.ws.addressing.v200408;
/*    */ 
/*    */ import javax.xml.bind.annotation.XmlElement;
/*    */ import javax.xml.bind.annotation.XmlRootElement;
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
/*    */ @XmlRootElement(name = "ProblemAction", namespace = "http://schemas.xmlsoap.org/ws/2004/08/addressing")
/*    */ public class ProblemAction
/*    */ {
/*    */   @XmlElement(name = "Action", namespace = "http://schemas.xmlsoap.org/ws/2004/08/addressing")
/*    */   private String action;
/*    */   @XmlElement(name = "SoapAction", namespace = "http://schemas.xmlsoap.org/ws/2004/08/addressing")
/*    */   private String soapAction;
/*    */   
/*    */   public ProblemAction() {}
/*    */   
/*    */   public ProblemAction(String action) {
/* 49 */     this.action = action;
/*    */   }
/*    */   
/*    */   public ProblemAction(String action, String soapAction) {
/* 53 */     this.action = action;
/* 54 */     this.soapAction = soapAction;
/*    */   }
/*    */   
/*    */   public String getAction() {
/* 58 */     return this.action;
/*    */   }
/*    */   
/*    */   public String getSoapAction() {
/* 62 */     return this.soapAction;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/addressing/v200408/ProblemAction.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */