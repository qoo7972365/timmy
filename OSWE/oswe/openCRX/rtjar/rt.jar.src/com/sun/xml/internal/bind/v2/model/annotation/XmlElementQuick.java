/*    */ package com.sun.xml.internal.bind.v2.model.annotation;
/*    */ 
/*    */ import java.lang.annotation.Annotation;
/*    */ import javax.xml.bind.annotation.XmlElement;
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
/*    */ final class XmlElementQuick
/*    */   extends Quick
/*    */   implements XmlElement
/*    */ {
/*    */   private final XmlElement core;
/*    */   
/*    */   public XmlElementQuick(Locatable upstream, XmlElement core) {
/* 44 */     super(upstream);
/* 45 */     this.core = core;
/*    */   }
/*    */   
/*    */   protected Annotation getAnnotation() {
/* 49 */     return (Annotation)this.core;
/*    */   }
/*    */   
/*    */   protected Quick newInstance(Locatable upstream, Annotation core) {
/* 53 */     return new XmlElementQuick(upstream, (XmlElement)core);
/*    */   }
/*    */   
/*    */   public Class<XmlElement> annotationType() {
/* 57 */     return XmlElement.class;
/*    */   }
/*    */   
/*    */   public String name() {
/* 61 */     return this.core.name();
/*    */   }
/*    */   
/*    */   public Class type() {
/* 65 */     return this.core.type();
/*    */   }
/*    */   
/*    */   public String namespace() {
/* 69 */     return this.core.namespace();
/*    */   }
/*    */   
/*    */   public String defaultValue() {
/* 73 */     return this.core.defaultValue();
/*    */   }
/*    */   
/*    */   public boolean required() {
/* 77 */     return this.core.required();
/*    */   }
/*    */   
/*    */   public boolean nillable() {
/* 81 */     return this.core.nillable();
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/bind/v2/model/annotation/XmlElementQuick.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */