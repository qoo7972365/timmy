/*     */ package com.oracle.xmlns.internal.webservices.jaxws_databinding;
/*     */ 
/*     */ import java.lang.annotation.Annotation;
/*     */ import javax.jws.WebService;
/*     */ import javax.xml.bind.annotation.XmlAccessType;
/*     */ import javax.xml.bind.annotation.XmlAccessorType;
/*     */ import javax.xml.bind.annotation.XmlAttribute;
/*     */ import javax.xml.bind.annotation.XmlRootElement;
/*     */ import javax.xml.bind.annotation.XmlType;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @XmlAccessorType(XmlAccessType.FIELD)
/*     */ @XmlType(name = "")
/*     */ @XmlRootElement(name = "web-service")
/*     */ public class XmlWebService
/*     */   implements WebService
/*     */ {
/*     */   @XmlAttribute(name = "endpoint-interface")
/*     */   protected String endpointInterface;
/*     */   @XmlAttribute(name = "name")
/*     */   protected String name;
/*     */   @XmlAttribute(name = "port-name")
/*     */   protected String portName;
/*     */   @XmlAttribute(name = "service-name")
/*     */   protected String serviceName;
/*     */   @XmlAttribute(name = "target-namespace")
/*     */   protected String targetNamespace;
/*     */   @XmlAttribute(name = "wsdl-location")
/*     */   protected String wsdlLocation;
/*     */   
/*     */   public String getEndpointInterface() {
/*  89 */     if (this.endpointInterface == null) {
/*  90 */       return "";
/*     */     }
/*  92 */     return this.endpointInterface;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setEndpointInterface(String value) {
/* 105 */     this.endpointInterface = value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/* 117 */     if (this.name == null) {
/* 118 */       return "";
/*     */     }
/* 120 */     return this.name;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setName(String value) {
/* 133 */     this.name = value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getPortName() {
/* 145 */     if (this.portName == null) {
/* 146 */       return "";
/*     */     }
/* 148 */     return this.portName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPortName(String value) {
/* 161 */     this.portName = value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getServiceName() {
/* 173 */     if (this.serviceName == null) {
/* 174 */       return "";
/*     */     }
/* 176 */     return this.serviceName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setServiceName(String value) {
/* 189 */     this.serviceName = value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getTargetNamespace() {
/* 201 */     if (this.targetNamespace == null) {
/* 202 */       return "";
/*     */     }
/* 204 */     return this.targetNamespace;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTargetNamespace(String value) {
/* 217 */     this.targetNamespace = value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getWsdlLocation() {
/* 229 */     if (this.wsdlLocation == null) {
/* 230 */       return "";
/*     */     }
/* 232 */     return this.wsdlLocation;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setWsdlLocation(String value) {
/* 245 */     this.wsdlLocation = value;
/*     */   }
/*     */ 
/*     */   
/*     */   public String name() {
/* 250 */     return Util.nullSafe(this.name);
/*     */   }
/*     */ 
/*     */   
/*     */   public String targetNamespace() {
/* 255 */     return Util.nullSafe(this.targetNamespace);
/*     */   }
/*     */ 
/*     */   
/*     */   public String serviceName() {
/* 260 */     return Util.nullSafe(this.serviceName);
/*     */   }
/*     */ 
/*     */   
/*     */   public String portName() {
/* 265 */     return Util.nullSafe(this.portName);
/*     */   }
/*     */ 
/*     */   
/*     */   public String wsdlLocation() {
/* 270 */     return Util.nullSafe(this.wsdlLocation);
/*     */   }
/*     */ 
/*     */   
/*     */   public String endpointInterface() {
/* 275 */     return Util.nullSafe(this.endpointInterface);
/*     */   }
/*     */ 
/*     */   
/*     */   public Class<? extends Annotation> annotationType() {
/* 280 */     return (Class)WebService.class;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/oracle/xmlns/internal/webservices/jaxws_databinding/XmlWebService.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */