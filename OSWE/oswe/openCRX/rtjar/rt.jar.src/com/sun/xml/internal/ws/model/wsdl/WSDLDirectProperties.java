/*    */ package com.sun.xml.internal.ws.model.wsdl;
/*    */ 
/*    */ import com.sun.xml.internal.ws.api.model.SEIModel;
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
/*    */ public final class WSDLDirectProperties
/*    */   extends WSDLProperties
/*    */ {
/*    */   private final QName serviceName;
/*    */   private final QName portName;
/*    */   
/*    */   public WSDLDirectProperties(QName serviceName, QName portName) {
/* 42 */     this(serviceName, portName, null);
/*    */   }
/*    */   
/*    */   public WSDLDirectProperties(QName serviceName, QName portName, SEIModel seiModel) {
/* 46 */     super(seiModel);
/* 47 */     this.serviceName = serviceName;
/* 48 */     this.portName = portName;
/*    */   }
/*    */   
/*    */   public QName getWSDLService() {
/* 52 */     return this.serviceName;
/*    */   }
/*    */   
/*    */   public QName getWSDLPort() {
/* 56 */     return this.portName;
/*    */   }
/*    */   
/*    */   public QName getWSDLPortType() {
/* 60 */     return null;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/model/wsdl/WSDLDirectProperties.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */