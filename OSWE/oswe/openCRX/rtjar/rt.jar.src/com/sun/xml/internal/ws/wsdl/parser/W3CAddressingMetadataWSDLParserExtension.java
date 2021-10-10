/*    */ package com.sun.xml.internal.ws.wsdl.parser;
/*    */ 
/*    */ import com.sun.xml.internal.ws.api.model.wsdl.editable.EditableWSDLBoundOperation;
/*    */ import com.sun.xml.internal.ws.api.model.wsdl.editable.EditableWSDLBoundPortType;
/*    */ import com.sun.xml.internal.ws.api.model.wsdl.editable.EditableWSDLPort;
/*    */ import javax.xml.namespace.QName;
/*    */ import javax.xml.stream.XMLStreamReader;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class W3CAddressingMetadataWSDLParserExtension
/*    */   extends W3CAddressingWSDLParserExtension
/*    */ {
/* 41 */   String METADATA_WSDL_EXTN_NS = "http://www.w3.org/2007/05/addressing/metadata";
/* 42 */   QName METADATA_WSDL_ACTION_TAG = new QName(this.METADATA_WSDL_EXTN_NS, "Action", "wsam");
/*    */   
/*    */   public boolean bindingElements(EditableWSDLBoundPortType binding, XMLStreamReader reader) {
/* 45 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean portElements(EditableWSDLPort port, XMLStreamReader reader) {
/* 50 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean bindingOperationElements(EditableWSDLBoundOperation operation, XMLStreamReader reader) {
/* 55 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void patchAnonymousDefault(EditableWSDLBoundPortType binding) {}
/*    */ 
/*    */   
/*    */   protected String getNamespaceURI() {
/* 64 */     return this.METADATA_WSDL_EXTN_NS;
/*    */   }
/*    */ 
/*    */   
/*    */   protected QName getWsdlActionTag() {
/* 69 */     return this.METADATA_WSDL_ACTION_TAG;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/wsdl/parser/W3CAddressingMetadataWSDLParserExtension.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */