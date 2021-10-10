/*    */ package com.sun.xml.internal.bind.v2.model.annotation;
/*    */ 
/*    */ import java.lang.annotation.Annotation;
/*    */ import javax.xml.bind.annotation.XmlElementRef;
/*    */ import javax.xml.bind.annotation.XmlElementRefs;
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
/*    */ final class XmlElementRefsQuick
/*    */   extends Quick
/*    */   implements XmlElementRefs
/*    */ {
/*    */   private final XmlElementRefs core;
/*    */   
/*    */   public XmlElementRefsQuick(Locatable upstream, XmlElementRefs core) {
/* 45 */     super(upstream);
/* 46 */     this.core = core;
/*    */   }
/*    */   
/*    */   protected Annotation getAnnotation() {
/* 50 */     return (Annotation)this.core;
/*    */   }
/*    */   
/*    */   protected Quick newInstance(Locatable upstream, Annotation core) {
/* 54 */     return new XmlElementRefsQuick(upstream, (XmlElementRefs)core);
/*    */   }
/*    */   
/*    */   public Class<XmlElementRefs> annotationType() {
/* 58 */     return XmlElementRefs.class;
/*    */   }
/*    */   
/*    */   public XmlElementRef[] value() {
/* 62 */     return this.core.value();
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/bind/v2/model/annotation/XmlElementRefsQuick.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */