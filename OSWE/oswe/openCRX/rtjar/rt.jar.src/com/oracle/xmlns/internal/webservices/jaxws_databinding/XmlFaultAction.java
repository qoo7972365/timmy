/*     */ package com.oracle.xmlns.internal.webservices.jaxws_databinding;
/*     */ 
/*     */ import java.lang.annotation.Annotation;
/*     */ import javax.xml.bind.annotation.XmlAccessType;
/*     */ import javax.xml.bind.annotation.XmlAccessorType;
/*     */ import javax.xml.bind.annotation.XmlAttribute;
/*     */ import javax.xml.bind.annotation.XmlRootElement;
/*     */ import javax.xml.bind.annotation.XmlType;
/*     */ import javax.xml.ws.FaultAction;
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
/*     */ @XmlRootElement(name = "fault-action")
/*     */ public class XmlFaultAction
/*     */   implements FaultAction
/*     */ {
/*     */   @XmlAttribute(name = "className", required = true)
/*     */   protected String className;
/*     */   @XmlAttribute(name = "value")
/*     */   protected String value;
/*     */   
/*     */   public String getClassName() {
/*  76 */     return this.className;
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
/*     */   public void setClassName(String value) {
/*  88 */     this.className = value;
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
/*     */   public String getValue() {
/* 100 */     return Util.nullSafe(this.value);
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
/*     */   public void setValue(String value) {
/* 112 */     this.value = value;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Class<? extends Exception> className() {
/* 118 */     return (Class)Util.findClass(this.className);
/*     */   }
/*     */ 
/*     */   
/*     */   public String value() {
/* 123 */     return Util.nullSafe(this.value);
/*     */   }
/*     */ 
/*     */   
/*     */   public Class<? extends Annotation> annotationType() {
/* 128 */     return (Class)FaultAction.class;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/oracle/xmlns/internal/webservices/jaxws_databinding/XmlFaultAction.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */