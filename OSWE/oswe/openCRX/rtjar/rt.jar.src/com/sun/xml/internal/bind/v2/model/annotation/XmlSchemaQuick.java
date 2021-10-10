/*    */ package com.sun.xml.internal.bind.v2.model.annotation;
/*    */ 
/*    */ import java.lang.annotation.Annotation;
/*    */ import javax.xml.bind.annotation.XmlNs;
/*    */ import javax.xml.bind.annotation.XmlNsForm;
/*    */ import javax.xml.bind.annotation.XmlSchema;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ final class XmlSchemaQuick
/*    */   extends Quick
/*    */   implements XmlSchema
/*    */ {
/*    */   private final XmlSchema core;
/*    */   
/*    */   public XmlSchemaQuick(Locatable upstream, XmlSchema core) {
/* 46 */     super(upstream);
/* 47 */     this.core = core;
/*    */   }
/*    */   
/*    */   protected Annotation getAnnotation() {
/* 51 */     return (Annotation)this.core;
/*    */   }
/*    */   
/*    */   protected Quick newInstance(Locatable upstream, Annotation core) {
/* 55 */     return new XmlSchemaQuick(upstream, (XmlSchema)core);
/*    */   }
/*    */   
/*    */   public Class<XmlSchema> annotationType() {
/* 59 */     return XmlSchema.class;
/*    */   }
/*    */   
/*    */   public String location() {
/* 63 */     return this.core.location();
/*    */   }
/*    */   
/*    */   public String namespace() {
/* 67 */     return this.core.namespace();
/*    */   }
/*    */   
/*    */   public XmlNs[] xmlns() {
/* 71 */     return this.core.xmlns();
/*    */   }
/*    */   
/*    */   public XmlNsForm elementFormDefault() {
/* 75 */     return this.core.elementFormDefault();
/*    */   }
/*    */   
/*    */   public XmlNsForm attributeFormDefault() {
/* 79 */     return this.core.attributeFormDefault();
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/bind/v2/model/annotation/XmlSchemaQuick.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */