/*     */ package com.oracle.xmlns.internal.webservices.jaxws_databinding;
/*     */ 
/*     */ import java.lang.annotation.Annotation;
/*     */ import javax.jws.soap.SOAPBinding;
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
/*     */ @XmlRootElement(name = "soap-binding")
/*     */ public class XmlSOAPBinding
/*     */   implements SOAPBinding
/*     */ {
/*     */   @XmlAttribute(name = "style")
/*     */   protected SoapBindingStyle style;
/*     */   @XmlAttribute(name = "use")
/*     */   protected SoapBindingUse use;
/*     */   @XmlAttribute(name = "parameter-style")
/*     */   protected SoapBindingParameterStyle parameterStyle;
/*     */   
/*     */   public SoapBindingStyle getStyle() {
/*  78 */     if (this.style == null) {
/*  79 */       return SoapBindingStyle.DOCUMENT;
/*     */     }
/*  81 */     return this.style;
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
/*     */   public void setStyle(SoapBindingStyle value) {
/*  94 */     this.style = value;
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
/*     */   public SoapBindingUse getUse() {
/* 106 */     if (this.use == null) {
/* 107 */       return SoapBindingUse.LITERAL;
/*     */     }
/* 109 */     return this.use;
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
/*     */   public void setUse(SoapBindingUse value) {
/* 122 */     this.use = value;
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
/*     */   public SoapBindingParameterStyle getParameterStyle() {
/* 134 */     if (this.parameterStyle == null) {
/* 135 */       return SoapBindingParameterStyle.WRAPPED;
/*     */     }
/* 137 */     return this.parameterStyle;
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
/*     */   public void setParameterStyle(SoapBindingParameterStyle value) {
/* 150 */     this.parameterStyle = value;
/*     */   }
/*     */ 
/*     */   
/*     */   public SOAPBinding.Style style() {
/* 155 */     return Util.<SOAPBinding.Style>nullSafe(this.style, SOAPBinding.Style.DOCUMENT);
/*     */   }
/*     */ 
/*     */   
/*     */   public SOAPBinding.Use use() {
/* 160 */     return Util.<SOAPBinding.Use>nullSafe(this.use, SOAPBinding.Use.LITERAL);
/*     */   }
/*     */ 
/*     */   
/*     */   public SOAPBinding.ParameterStyle parameterStyle() {
/* 165 */     return Util.<SOAPBinding.ParameterStyle>nullSafe(this.parameterStyle, SOAPBinding.ParameterStyle.WRAPPED);
/*     */   }
/*     */ 
/*     */   
/*     */   public Class<? extends Annotation> annotationType() {
/* 170 */     return (Class)SOAPBinding.class;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/oracle/xmlns/internal/webservices/jaxws_databinding/XmlSOAPBinding.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */