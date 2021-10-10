/*    */ package com.sun.xml.internal.ws.api.databinding;
/*    */ 
/*    */ import com.oracle.webservices.internal.api.databinding.WSDLResolver;
/*    */ import com.sun.xml.internal.ws.api.server.Container;
/*    */ import com.sun.xml.internal.ws.api.wsdl.writer.WSDLGeneratorExtension;
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
/*    */ public class WSDLGenInfo
/*    */ {
/*    */   WSDLResolver wsdlResolver;
/*    */   Container container;
/*    */   boolean inlineSchemas;
/*    */   boolean secureXmlProcessingDisabled;
/*    */   WSDLGeneratorExtension[] extensions;
/*    */   
/*    */   public WSDLResolver getWsdlResolver() {
/* 45 */     return this.wsdlResolver;
/*    */   }
/*    */   public void setWsdlResolver(WSDLResolver wsdlResolver) {
/* 48 */     this.wsdlResolver = wsdlResolver;
/*    */   }
/*    */   public Container getContainer() {
/* 51 */     return this.container;
/*    */   }
/*    */   public void setContainer(Container container) {
/* 54 */     this.container = container;
/*    */   }
/*    */   public boolean isInlineSchemas() {
/* 57 */     return this.inlineSchemas;
/*    */   }
/*    */   public void setInlineSchemas(boolean inlineSchemas) {
/* 60 */     this.inlineSchemas = inlineSchemas;
/*    */   }
/*    */   public WSDLGeneratorExtension[] getExtensions() {
/* 63 */     if (this.extensions == null) return new WSDLGeneratorExtension[0]; 
/* 64 */     return this.extensions;
/*    */   }
/*    */   public void setExtensions(WSDLGeneratorExtension[] extensions) {
/* 67 */     this.extensions = extensions;
/*    */   }
/*    */   
/*    */   public void setSecureXmlProcessingDisabled(boolean secureXmlProcessingDisabled) {
/* 71 */     this.secureXmlProcessingDisabled = secureXmlProcessingDisabled;
/*    */   }
/*    */   
/*    */   public boolean isSecureXmlProcessingDisabled() {
/* 75 */     return this.secureXmlProcessingDisabled;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/api/databinding/WSDLGenInfo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */