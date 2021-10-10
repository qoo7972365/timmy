/*     */ package com.oracle.xmlns.internal.webservices.jaxws_databinding;
/*     */ 
/*     */ import java.lang.annotation.Annotation;
/*     */ import javax.xml.bind.annotation.XmlAccessType;
/*     */ import javax.xml.bind.annotation.XmlAccessorType;
/*     */ import javax.xml.bind.annotation.XmlAttribute;
/*     */ import javax.xml.bind.annotation.XmlRootElement;
/*     */ import javax.xml.bind.annotation.XmlType;
/*     */ import javax.xml.ws.soap.Addressing;
/*     */ import javax.xml.ws.soap.AddressingFeature;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */ @XmlRootElement(name = "addressing")
/*     */ public class XmlAddressing
/*     */   implements Addressing
/*     */ {
/*     */   @XmlAttribute(name = "enabled")
/*     */   protected Boolean enabled;
/*     */   @XmlAttribute(name = "required")
/*     */   protected Boolean required;
/*     */   
/*     */   public Boolean getEnabled() {
/*  68 */     return Boolean.valueOf(enabled());
/*     */   }
/*     */   
/*     */   public void setEnabled(Boolean enabled) {
/*  72 */     this.enabled = enabled;
/*     */   }
/*     */   
/*     */   public Boolean getRequired() {
/*  76 */     return Boolean.valueOf(required());
/*     */   }
/*     */   
/*     */   public void setRequired(Boolean required) {
/*  80 */     this.required = required;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean enabled() {
/*  85 */     return ((Boolean)Util.<Boolean>nullSafe(this.enabled, Boolean.valueOf(true))).booleanValue();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean required() {
/*  90 */     return ((Boolean)Util.<Boolean>nullSafe(this.required, Boolean.valueOf(false))).booleanValue();
/*     */   }
/*     */ 
/*     */   
/*     */   public AddressingFeature.Responses responses() {
/*  95 */     return AddressingFeature.Responses.ALL;
/*     */   }
/*     */ 
/*     */   
/*     */   public Class<? extends Annotation> annotationType() {
/* 100 */     return (Class)Addressing.class;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/oracle/xmlns/internal/webservices/jaxws_databinding/XmlAddressing.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */