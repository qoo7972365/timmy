/*    */ package com.sun.xml.internal.bind.v2.model.annotation;
/*    */ 
/*    */ import java.lang.annotation.Annotation;
/*    */ import javax.xml.bind.annotation.XmlEnum;
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
/*    */ final class XmlEnumQuick
/*    */   extends Quick
/*    */   implements XmlEnum
/*    */ {
/*    */   private final XmlEnum core;
/*    */   
/*    */   public XmlEnumQuick(Locatable upstream, XmlEnum core) {
/* 44 */     super(upstream);
/* 45 */     this.core = core;
/*    */   }
/*    */   
/*    */   protected Annotation getAnnotation() {
/* 49 */     return (Annotation)this.core;
/*    */   }
/*    */   
/*    */   protected Quick newInstance(Locatable upstream, Annotation core) {
/* 53 */     return new XmlEnumQuick(upstream, (XmlEnum)core);
/*    */   }
/*    */   
/*    */   public Class<XmlEnum> annotationType() {
/* 57 */     return XmlEnum.class;
/*    */   }
/*    */   
/*    */   public Class value() {
/* 61 */     return this.core.value();
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/bind/v2/model/annotation/XmlEnumQuick.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */