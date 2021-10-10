/*    */ package com.sun.xml.internal.ws.api.databinding;
/*    */ 
/*    */ import com.sun.xml.internal.ws.api.BindingID;
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
/*    */ 
/*    */ 
/*    */ public class MappingInfo
/*    */ {
/*    */   protected String targetNamespace;
/*    */   protected String databindingMode;
/*    */   protected SoapBodyStyle soapBodyStyle;
/*    */   protected BindingID bindingID;
/*    */   protected QName serviceName;
/*    */   protected QName portName;
/*    */   protected String defaultSchemaNamespaceSuffix;
/*    */   
/*    */   public String getTargetNamespace() {
/* 51 */     return this.targetNamespace;
/*    */   }
/*    */   public void setTargetNamespace(String targetNamespace) {
/* 54 */     this.targetNamespace = targetNamespace;
/*    */   }
/*    */   public String getDatabindingMode() {
/* 57 */     return this.databindingMode;
/*    */   }
/*    */   public void setDatabindingMode(String databindingMode) {
/* 60 */     this.databindingMode = databindingMode;
/*    */   }
/*    */   public SoapBodyStyle getSoapBodyStyle() {
/* 63 */     return this.soapBodyStyle;
/*    */   }
/*    */   public void setSoapBodyStyle(SoapBodyStyle soapBodyStyle) {
/* 66 */     this.soapBodyStyle = soapBodyStyle;
/*    */   }
/*    */   public BindingID getBindingID() {
/* 69 */     return this.bindingID;
/*    */   }
/*    */   public void setBindingID(BindingID bindingID) {
/* 72 */     this.bindingID = bindingID;
/*    */   }
/*    */   public QName getServiceName() {
/* 75 */     return this.serviceName;
/*    */   }
/*    */   public void setServiceName(QName serviceName) {
/* 78 */     this.serviceName = serviceName;
/*    */   }
/*    */   public QName getPortName() {
/* 81 */     return this.portName;
/*    */   }
/*    */   public void setPortName(QName portName) {
/* 84 */     this.portName = portName;
/*    */   }
/*    */   public String getDefaultSchemaNamespaceSuffix() {
/* 87 */     return this.defaultSchemaNamespaceSuffix;
/*    */   }
/*    */   public void setDefaultSchemaNamespaceSuffix(String defaultSchemaNamespaceSuffix) {
/* 90 */     this.defaultSchemaNamespaceSuffix = defaultSchemaNamespaceSuffix;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/api/databinding/MappingInfo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */