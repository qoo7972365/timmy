/*    */ package com.sun.xml.internal.bind.v2.model.annotation;
/*    */ 
/*    */ import java.lang.annotation.Annotation;
/*    */ import javax.xml.bind.annotation.XmlType;
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
/*    */ final class XmlTypeQuick
/*    */   extends Quick
/*    */   implements XmlType
/*    */ {
/*    */   private final XmlType core;
/*    */   
/*    */   public XmlTypeQuick(Locatable upstream, XmlType core) {
/* 44 */     super(upstream);
/* 45 */     this.core = core;
/*    */   }
/*    */   
/*    */   protected Annotation getAnnotation() {
/* 49 */     return (Annotation)this.core;
/*    */   }
/*    */   
/*    */   protected Quick newInstance(Locatable upstream, Annotation core) {
/* 53 */     return new XmlTypeQuick(upstream, (XmlType)core);
/*    */   }
/*    */   
/*    */   public Class<XmlType> annotationType() {
/* 57 */     return XmlType.class;
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
/*    */   public String[] propOrder() {
/* 69 */     return this.core.propOrder();
/*    */   }
/*    */   
/*    */   public Class factoryClass() {
/* 73 */     return this.core.factoryClass();
/*    */   }
/*    */   
/*    */   public String factoryMethod() {
/* 77 */     return this.core.factoryMethod();
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/bind/v2/model/annotation/XmlTypeQuick.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */