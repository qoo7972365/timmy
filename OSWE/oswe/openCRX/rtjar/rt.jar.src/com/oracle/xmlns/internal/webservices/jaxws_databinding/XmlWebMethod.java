/*     */ package com.oracle.xmlns.internal.webservices.jaxws_databinding;
/*     */ 
/*     */ import java.lang.annotation.Annotation;
/*     */ import javax.jws.WebMethod;
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
/*     */ @XmlAccessorType(XmlAccessType.FIELD)
/*     */ @XmlType(name = "")
/*     */ @XmlRootElement(name = "web-method")
/*     */ public class XmlWebMethod
/*     */   implements WebMethod
/*     */ {
/*     */   @XmlAttribute(name = "action")
/*     */   protected String action;
/*     */   @XmlAttribute(name = "exclude")
/*     */   protected Boolean exclude;
/*     */   @XmlAttribute(name = "operation-name")
/*     */   protected String operationName;
/*     */   
/*     */   public String getAction() {
/*  78 */     if (this.action == null) {
/*  79 */       return "";
/*     */     }
/*  81 */     return this.action;
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
/*     */   public void setAction(String value) {
/*  94 */     this.action = value;
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
/*     */   public boolean isExclude() {
/* 106 */     if (this.exclude == null) {
/* 107 */       return false;
/*     */     }
/* 109 */     return this.exclude.booleanValue();
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
/*     */   public void setExclude(Boolean value) {
/* 122 */     this.exclude = value;
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
/*     */   public String getOperationName() {
/* 134 */     if (this.operationName == null) {
/* 135 */       return "";
/*     */     }
/* 137 */     return this.operationName;
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
/*     */   public void setOperationName(String value) {
/* 150 */     this.operationName = value;
/*     */   }
/*     */ 
/*     */   
/*     */   public String operationName() {
/* 155 */     return Util.nullSafe(this.operationName);
/*     */   }
/*     */ 
/*     */   
/*     */   public String action() {
/* 160 */     return Util.nullSafe(this.action);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean exclude() {
/* 165 */     return ((Boolean)Util.<Boolean>nullSafe(this.exclude, Boolean.valueOf(false))).booleanValue();
/*     */   }
/*     */ 
/*     */   
/*     */   public Class<? extends Annotation> annotationType() {
/* 170 */     return (Class)WebMethod.class;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/oracle/xmlns/internal/webservices/jaxws_databinding/XmlWebMethod.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */