/*     */ package com.oracle.xmlns.internal.webservices.jaxws_databinding;
/*     */ 
/*     */ import java.lang.annotation.Annotation;
/*     */ import javax.jws.WebParam;
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
/*     */ @XmlAccessorType(XmlAccessType.FIELD)
/*     */ @XmlType(name = "")
/*     */ @XmlRootElement(name = "web-param")
/*     */ public class XmlWebParam
/*     */   implements WebParam
/*     */ {
/*     */   @XmlAttribute(name = "header")
/*     */   protected Boolean header;
/*     */   @XmlAttribute(name = "mode")
/*     */   protected WebParamMode mode;
/*     */   @XmlAttribute(name = "name")
/*     */   protected String name;
/*     */   @XmlAttribute(name = "part-name")
/*     */   protected String partName;
/*     */   @XmlAttribute(name = "target-namespace")
/*     */   protected String targetNamespace;
/*     */   
/*     */   public boolean isHeader() {
/*  84 */     if (this.header == null) {
/*  85 */       return false;
/*     */     }
/*  87 */     return this.header.booleanValue();
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
/*     */   public void setHeader(Boolean value) {
/* 100 */     this.header = value;
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
/*     */   public WebParamMode getMode() {
/* 112 */     if (this.mode == null) {
/* 113 */       return WebParamMode.IN;
/*     */     }
/* 115 */     return this.mode;
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
/*     */   public void setMode(WebParamMode value) {
/* 128 */     this.mode = value;
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
/* 140 */     if (this.name == null) {
/* 141 */       return "";
/*     */     }
/* 143 */     return this.name;
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
/* 156 */     this.name = value;
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
/*     */   public String getPartName() {
/* 168 */     if (this.partName == null) {
/* 169 */       return "";
/*     */     }
/* 171 */     return this.partName;
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
/*     */   public void setPartName(String value) {
/* 184 */     this.partName = value;
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
/* 196 */     if (this.targetNamespace == null) {
/* 197 */       return "";
/*     */     }
/* 199 */     return this.targetNamespace;
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
/* 212 */     this.targetNamespace = value;
/*     */   }
/*     */ 
/*     */   
/*     */   public String name() {
/* 217 */     return Util.nullSafe(this.name);
/*     */   }
/*     */ 
/*     */   
/*     */   public String partName() {
/* 222 */     return Util.nullSafe(this.partName);
/*     */   }
/*     */ 
/*     */   
/*     */   public String targetNamespace() {
/* 227 */     return Util.nullSafe(this.targetNamespace);
/*     */   }
/*     */ 
/*     */   
/*     */   public WebParam.Mode mode() {
/* 232 */     return Util.<WebParam.Mode>nullSafe(this.mode, WebParam.Mode.IN);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean header() {
/* 237 */     return ((Boolean)Util.<Boolean>nullSafe(this.header, Boolean.valueOf(false))).booleanValue();
/*     */   }
/*     */ 
/*     */   
/*     */   public Class<? extends Annotation> annotationType() {
/* 242 */     return (Class)WebParam.class;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/oracle/xmlns/internal/webservices/jaxws_databinding/XmlWebParam.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */