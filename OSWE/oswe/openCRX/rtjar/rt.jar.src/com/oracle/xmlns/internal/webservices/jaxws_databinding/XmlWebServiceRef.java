/*     */ package com.oracle.xmlns.internal.webservices.jaxws_databinding;
/*     */ 
/*     */ import java.lang.annotation.Annotation;
/*     */ import javax.xml.bind.annotation.XmlAccessType;
/*     */ import javax.xml.bind.annotation.XmlAccessorType;
/*     */ import javax.xml.bind.annotation.XmlAttribute;
/*     */ import javax.xml.bind.annotation.XmlRootElement;
/*     */ import javax.xml.bind.annotation.XmlType;
/*     */ import javax.xml.ws.Service;
/*     */ import javax.xml.ws.WebServiceRef;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */ @XmlRootElement(name = "web-service-ref")
/*     */ public class XmlWebServiceRef
/*     */   implements WebServiceRef
/*     */ {
/*     */   @XmlAttribute(name = "name")
/*     */   protected String name;
/*     */   @XmlAttribute(name = "type")
/*     */   protected String type;
/*     */   @XmlAttribute(name = "mappedName")
/*     */   protected String mappedName;
/*     */   @XmlAttribute(name = "value")
/*     */   protected String value;
/*     */   @XmlAttribute(name = "wsdlLocation")
/*     */   protected String wsdlLocation;
/*     */   @XmlAttribute(name = "lookup")
/*     */   protected String lookup;
/*     */   
/*     */   public String getName() {
/*  88 */     return this.name;
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
/* 100 */     this.name = value;
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
/*     */   public String getType() {
/* 112 */     return this.type;
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
/*     */   public void setType(String value) {
/* 124 */     this.type = value;
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
/*     */   public String getMappedName() {
/* 136 */     return this.mappedName;
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
/*     */   public void setMappedName(String value) {
/* 148 */     this.mappedName = value;
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
/* 160 */     return this.value;
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
/* 172 */     this.value = value;
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
/* 184 */     return this.wsdlLocation;
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
/*     */   public void setWsdlLocation(String value) {
/* 196 */     this.wsdlLocation = value;
/*     */   }
/*     */   
/*     */   public String getLookup() {
/* 200 */     return this.lookup;
/*     */   }
/*     */   
/*     */   public void setLookup(String lookup) {
/* 204 */     this.lookup = lookup;
/*     */   }
/*     */ 
/*     */   
/*     */   public String name() {
/* 209 */     return Util.nullSafe(this.name);
/*     */   }
/*     */ 
/*     */   
/*     */   public Class<?> type() {
/* 214 */     if (this.type == null) {
/* 215 */       return Object.class;
/*     */     }
/* 217 */     return Util.findClass(this.type);
/*     */   }
/*     */ 
/*     */   
/*     */   public String mappedName() {
/* 222 */     return Util.nullSafe(this.mappedName);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Class<? extends Service> value() {
/* 228 */     if (this.value == null) {
/* 229 */       return Service.class;
/*     */     }
/* 231 */     return (Class)Util.findClass(this.value);
/*     */   }
/*     */ 
/*     */   
/*     */   public String wsdlLocation() {
/* 236 */     return Util.nullSafe(this.wsdlLocation);
/*     */   }
/*     */ 
/*     */   
/*     */   public String lookup() {
/* 241 */     return Util.nullSafe(this.lookup);
/*     */   }
/*     */ 
/*     */   
/*     */   public Class<? extends Annotation> annotationType() {
/* 246 */     return (Class)WebServiceRef.class;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/oracle/xmlns/internal/webservices/jaxws_databinding/XmlWebServiceRef.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */