/*    */ package com.sun.xml.internal.ws.api.model.soap;
/*    */ 
/*    */ import com.sun.xml.internal.ws.api.SOAPVersion;
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
/*    */ public abstract class SOAPBinding
/*    */ {
/* 42 */   protected javax.jws.soap.SOAPBinding.Use use = javax.jws.soap.SOAPBinding.Use.LITERAL;
/* 43 */   protected javax.jws.soap.SOAPBinding.Style style = javax.jws.soap.SOAPBinding.Style.DOCUMENT;
/* 44 */   protected SOAPVersion soapVersion = SOAPVersion.SOAP_11;
/* 45 */   protected String soapAction = "";
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public javax.jws.soap.SOAPBinding.Use getUse() {
/* 51 */     return this.use;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public javax.jws.soap.SOAPBinding.Style getStyle() {
/* 58 */     return this.style;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public SOAPVersion getSOAPVersion() {
/* 65 */     return this.soapVersion;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isDocLit() {
/* 72 */     return (this.style == javax.jws.soap.SOAPBinding.Style.DOCUMENT && this.use == javax.jws.soap.SOAPBinding.Use.LITERAL);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isRpcLit() {
/* 79 */     return (this.style == javax.jws.soap.SOAPBinding.Style.RPC && this.use == javax.jws.soap.SOAPBinding.Use.LITERAL);
/*    */   }
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
/*    */   public String getSOAPAction() {
/* 99 */     return this.soapAction;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/api/model/soap/SOAPBinding.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */