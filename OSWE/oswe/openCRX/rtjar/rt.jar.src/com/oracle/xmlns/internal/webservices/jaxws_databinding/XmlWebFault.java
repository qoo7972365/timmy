/*     */ package com.oracle.xmlns.internal.webservices.jaxws_databinding;
/*     */ 
/*     */ import java.lang.annotation.Annotation;
/*     */ import javax.xml.bind.annotation.XmlAccessType;
/*     */ import javax.xml.bind.annotation.XmlAccessorType;
/*     */ import javax.xml.bind.annotation.XmlAttribute;
/*     */ import javax.xml.bind.annotation.XmlRootElement;
/*     */ import javax.xml.bind.annotation.XmlType;
/*     */ import javax.xml.ws.WebFault;
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
/*     */ @XmlRootElement(name = "web-fault")
/*     */ public class XmlWebFault
/*     */   implements WebFault
/*     */ {
/*     */   @XmlAttribute(name = "name")
/*     */   protected String name;
/*     */   @XmlAttribute(name = "targetNamespace")
/*     */   protected String targetNamespace;
/*     */   @XmlAttribute(name = "faultBean")
/*     */   protected String faultBean;
/*     */   @XmlAttribute(name = "messageName")
/*     */   protected String messageName;
/*     */   
/*     */   public String getName() {
/*  82 */     return this.name;
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
/*     */   public void setName(String value) {
/*  94 */     this.name = value;
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
/* 106 */     return this.targetNamespace;
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
/*     */   public void setTargetNamespace(String value) {
/* 118 */     this.targetNamespace = value;
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
/*     */   public String getFaultBean() {
/* 130 */     return this.faultBean;
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
/*     */   public void setFaultBean(String value) {
/* 142 */     this.faultBean = value;
/*     */   }
/*     */ 
/*     */   
/*     */   public String name() {
/* 147 */     return Util.nullSafe(this.name);
/*     */   }
/*     */ 
/*     */   
/*     */   public String targetNamespace() {
/* 152 */     return Util.nullSafe(this.targetNamespace);
/*     */   }
/*     */ 
/*     */   
/*     */   public String faultBean() {
/* 157 */     return Util.nullSafe(this.faultBean);
/*     */   }
/*     */ 
/*     */   
/*     */   public String messageName() {
/* 162 */     return Util.nullSafe(this.messageName);
/*     */   }
/*     */ 
/*     */   
/*     */   public Class<? extends Annotation> annotationType() {
/* 167 */     return (Class)WebFault.class;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/oracle/xmlns/internal/webservices/jaxws_databinding/XmlWebFault.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */