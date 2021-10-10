/*    */ package com.sun.xml.internal.ws.wsdl.parser;
/*    */ 
/*    */ import com.sun.xml.internal.ws.api.model.wsdl.editable.EditableWSDLModel;
/*    */ import com.sun.xml.internal.ws.api.policy.PolicyResolver;
/*    */ import com.sun.xml.internal.ws.api.server.Container;
/*    */ import com.sun.xml.internal.ws.api.wsdl.parser.WSDLParserExtensionContext;
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
/*    */ final class WSDLParserExtensionContextImpl
/*    */   implements WSDLParserExtensionContext
/*    */ {
/*    */   private final boolean isClientSide;
/*    */   private final EditableWSDLModel wsdlModel;
/*    */   private final Container container;
/*    */   private final PolicyResolver policyResolver;
/*    */   
/*    */   protected WSDLParserExtensionContextImpl(EditableWSDLModel model, boolean isClientSide, Container container, PolicyResolver policyResolver) {
/* 50 */     this.wsdlModel = model;
/* 51 */     this.isClientSide = isClientSide;
/* 52 */     this.container = container;
/* 53 */     this.policyResolver = policyResolver;
/*    */   }
/*    */   
/*    */   public boolean isClientSide() {
/* 57 */     return this.isClientSide;
/*    */   }
/*    */   
/*    */   public EditableWSDLModel getWSDLModel() {
/* 61 */     return this.wsdlModel;
/*    */   }
/*    */   
/*    */   public Container getContainer() {
/* 65 */     return this.container;
/*    */   }
/*    */   
/*    */   public PolicyResolver getPolicyResolver() {
/* 69 */     return this.policyResolver;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/wsdl/parser/WSDLParserExtensionContextImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */