/*     */ package com.oracle.xmlns.internal.webservices.jaxws_databinding;
/*     */ 
/*     */ import java.lang.annotation.Annotation;
/*     */ import javax.xml.bind.annotation.XmlAccessType;
/*     */ import javax.xml.bind.annotation.XmlAccessorType;
/*     */ import javax.xml.bind.annotation.XmlAttribute;
/*     */ import javax.xml.bind.annotation.XmlRootElement;
/*     */ import javax.xml.bind.annotation.XmlType;
/*     */ import javax.xml.ws.ResponseWrapper;
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
/*     */ @XmlRootElement(name = "response-wrapper")
/*     */ public class XmlResponseWrapper
/*     */   implements ResponseWrapper
/*     */ {
/*     */   @XmlAttribute(name = "local-name")
/*     */   protected String localName;
/*     */   @XmlAttribute(name = "target-namespace")
/*     */   protected String targetNamespace;
/*     */   @XmlAttribute(name = "class-name")
/*     */   protected String className;
/*     */   @XmlAttribute(name = "part-name")
/*     */   protected String partName;
/*     */   
/*     */   public String getLocalName() {
/*  81 */     if (this.localName == null) {
/*  82 */       return "";
/*     */     }
/*  84 */     return this.localName;
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
/*     */   public void setLocalName(String value) {
/*  97 */     this.localName = value;
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
/* 109 */     if (this.targetNamespace == null) {
/* 110 */       return "";
/*     */     }
/* 112 */     return this.targetNamespace;
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
/* 125 */     this.targetNamespace = value;
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
/*     */   public String getClassName() {
/* 137 */     if (this.className == null) {
/* 138 */       return "";
/*     */     }
/* 140 */     return this.className;
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
/*     */   public void setClassName(String value) {
/* 153 */     this.className = value;
/*     */   }
/*     */   
/*     */   public String getPartName() {
/* 157 */     return this.partName;
/*     */   }
/*     */   
/*     */   public void setPartName(String partName) {
/* 161 */     this.partName = partName;
/*     */   }
/*     */ 
/*     */   
/*     */   public String localName() {
/* 166 */     return Util.nullSafe(this.localName);
/*     */   }
/*     */ 
/*     */   
/*     */   public String targetNamespace() {
/* 171 */     return Util.nullSafe(this.targetNamespace);
/*     */   }
/*     */ 
/*     */   
/*     */   public String className() {
/* 176 */     return Util.nullSafe(this.className);
/*     */   }
/*     */ 
/*     */   
/*     */   public String partName() {
/* 181 */     return Util.nullSafe(this.partName);
/*     */   }
/*     */ 
/*     */   
/*     */   public Class<? extends Annotation> annotationType() {
/* 186 */     return (Class)ResponseWrapper.class;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/oracle/xmlns/internal/webservices/jaxws_databinding/XmlResponseWrapper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */