/*    */ package com.sun.xml.internal.ws.wsdl.parser;
/*    */ 
/*    */ import com.sun.xml.internal.ws.api.addressing.AddressingVersion;
/*    */ import com.sun.xml.internal.ws.api.model.wsdl.WSDLFeaturedObject;
/*    */ import com.sun.xml.internal.ws.api.model.wsdl.editable.EditableWSDLBoundOperation;
/*    */ import com.sun.xml.internal.ws.api.model.wsdl.editable.EditableWSDLBoundPortType;
/*    */ import com.sun.xml.internal.ws.api.model.wsdl.editable.EditableWSDLPort;
/*    */ import com.sun.xml.internal.ws.developer.MemberSubmissionAddressingFeature;
/*    */ import com.sun.xml.internal.ws.streaming.XMLStreamReaderUtil;
/*    */ import javax.xml.namespace.QName;
/*    */ import javax.xml.stream.XMLStreamReader;
/*    */ import javax.xml.ws.WebServiceFeature;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MemberSubmissionAddressingWSDLParserExtension
/*    */   extends W3CAddressingWSDLParserExtension
/*    */ {
/*    */   public boolean bindingElements(EditableWSDLBoundPortType binding, XMLStreamReader reader) {
/* 45 */     return addressibleElement(reader, (WSDLFeaturedObject)binding);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean portElements(EditableWSDLPort port, XMLStreamReader reader) {
/* 50 */     return addressibleElement(reader, (WSDLFeaturedObject)port);
/*    */   }
/*    */   
/*    */   private boolean addressibleElement(XMLStreamReader reader, WSDLFeaturedObject binding) {
/* 54 */     QName ua = reader.getName();
/* 55 */     if (ua.equals(AddressingVersion.MEMBER.wsdlExtensionTag)) {
/* 56 */       String required = reader.getAttributeValue("http://schemas.xmlsoap.org/wsdl/", "required");
/* 57 */       binding.addFeature((WebServiceFeature)new MemberSubmissionAddressingFeature(Boolean.parseBoolean(required)));
/* 58 */       XMLStreamReaderUtil.skipElement(reader);
/* 59 */       return true;
/*    */     } 
/*    */     
/* 62 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean bindingOperationElements(EditableWSDLBoundOperation operation, XMLStreamReader reader) {
/* 67 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void patchAnonymousDefault(EditableWSDLBoundPortType binding) {}
/*    */ 
/*    */   
/*    */   protected String getNamespaceURI() {
/* 76 */     return AddressingVersion.MEMBER.wsdlNsUri;
/*    */   }
/*    */ 
/*    */   
/*    */   protected QName getWsdlActionTag() {
/* 81 */     return AddressingVersion.MEMBER.wsdlActionTag;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/wsdl/parser/MemberSubmissionAddressingWSDLParserExtension.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */