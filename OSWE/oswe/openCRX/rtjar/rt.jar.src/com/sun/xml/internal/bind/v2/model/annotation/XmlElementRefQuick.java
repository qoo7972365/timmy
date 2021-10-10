/*    */ package com.sun.xml.internal.bind.v2.model.annotation;
/*    */ 
/*    */ import java.lang.annotation.Annotation;
/*    */ import javax.xml.bind.annotation.XmlElementRef;
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
/*    */ final class XmlElementRefQuick
/*    */   extends Quick
/*    */   implements XmlElementRef
/*    */ {
/*    */   private final XmlElementRef core;
/*    */   
/*    */   public XmlElementRefQuick(Locatable upstream, XmlElementRef core) {
/* 44 */     super(upstream);
/* 45 */     this.core = core;
/*    */   }
/*    */   
/*    */   protected Annotation getAnnotation() {
/* 49 */     return (Annotation)this.core;
/*    */   }
/*    */   
/*    */   protected Quick newInstance(Locatable upstream, Annotation core) {
/* 53 */     return new XmlElementRefQuick(upstream, (XmlElementRef)core);
/*    */   }
/*    */   
/*    */   public Class<XmlElementRef> annotationType() {
/* 57 */     return XmlElementRef.class;
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
/*    */   public boolean required() {
/* 73 */     return this.core.required();
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/bind/v2/model/annotation/XmlElementRefQuick.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */