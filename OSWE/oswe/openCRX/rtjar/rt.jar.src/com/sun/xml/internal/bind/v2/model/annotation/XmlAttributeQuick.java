/*    */ package com.sun.xml.internal.bind.v2.model.annotation;
/*    */ 
/*    */ import java.lang.annotation.Annotation;
/*    */ import javax.xml.bind.annotation.XmlAttribute;
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
/*    */ final class XmlAttributeQuick
/*    */   extends Quick
/*    */   implements XmlAttribute
/*    */ {
/*    */   private final XmlAttribute core;
/*    */   
/*    */   public XmlAttributeQuick(Locatable upstream, XmlAttribute core) {
/* 44 */     super(upstream);
/* 45 */     this.core = core;
/*    */   }
/*    */   
/*    */   protected Annotation getAnnotation() {
/* 49 */     return (Annotation)this.core;
/*    */   }
/*    */   
/*    */   protected Quick newInstance(Locatable upstream, Annotation core) {
/* 53 */     return new XmlAttributeQuick(upstream, (XmlAttribute)core);
/*    */   }
/*    */   
/*    */   public Class<XmlAttribute> annotationType() {
/* 57 */     return XmlAttribute.class;
/*    */   }
/*    */   
/*    */   public String name() {
/* 61 */     return this.core.name();
/*    */   }
/*    */   
/*    */   public String namespace() {
/* 65 */     return this.core.namespace();
/*    */   }
/*    */   
/*    */   public boolean required() {
/* 69 */     return this.core.required();
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/bind/v2/model/annotation/XmlAttributeQuick.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */