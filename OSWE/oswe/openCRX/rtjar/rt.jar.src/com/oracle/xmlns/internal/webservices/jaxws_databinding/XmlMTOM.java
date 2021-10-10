/*     */ package com.oracle.xmlns.internal.webservices.jaxws_databinding;
/*     */ 
/*     */ import java.lang.annotation.Annotation;
/*     */ import javax.xml.bind.annotation.XmlAccessType;
/*     */ import javax.xml.bind.annotation.XmlAccessorType;
/*     */ import javax.xml.bind.annotation.XmlAttribute;
/*     */ import javax.xml.bind.annotation.XmlRootElement;
/*     */ import javax.xml.bind.annotation.XmlType;
/*     */ import javax.xml.ws.soap.MTOM;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */ @XmlRootElement(name = "mtom")
/*     */ public class XmlMTOM
/*     */   implements MTOM
/*     */ {
/*     */   @XmlAttribute(name = "enabled")
/*     */   protected Boolean enabled;
/*     */   @XmlAttribute(name = "threshold")
/*     */   protected Integer threshold;
/*     */   
/*     */   public boolean isEnabled() {
/*  78 */     if (this.enabled == null) {
/*  79 */       return true;
/*     */     }
/*  81 */     return this.enabled.booleanValue();
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
/*     */   public void setEnabled(Boolean value) {
/*  94 */     this.enabled = value;
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
/*     */   public int getThreshold() {
/* 106 */     if (this.threshold == null) {
/* 107 */       return 0;
/*     */     }
/* 109 */     return this.threshold.intValue();
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
/*     */   public void setThreshold(Integer value) {
/* 122 */     this.threshold = value;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean enabled() {
/* 127 */     return ((Boolean)Util.<Boolean>nullSafe(this.enabled, Boolean.TRUE)).booleanValue();
/*     */   }
/*     */ 
/*     */   
/*     */   public int threshold() {
/* 132 */     return ((Integer)Util.<Integer>nullSafe(this.threshold, Integer.valueOf(0))).intValue();
/*     */   }
/*     */ 
/*     */   
/*     */   public Class<? extends Annotation> annotationType() {
/* 137 */     return (Class)MTOM.class;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/oracle/xmlns/internal/webservices/jaxws_databinding/XmlMTOM.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */