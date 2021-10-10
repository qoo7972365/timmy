/*    */ package com.sun.xml.internal.bind.v2.model.annotation;
/*    */ 
/*    */ import java.lang.annotation.Annotation;
/*    */ import javax.xml.bind.annotation.XmlElementDecl;
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
/*    */ final class XmlElementDeclQuick
/*    */   extends Quick
/*    */   implements XmlElementDecl
/*    */ {
/*    */   private final XmlElementDecl core;
/*    */   
/*    */   public XmlElementDeclQuick(Locatable upstream, XmlElementDecl core) {
/* 44 */     super(upstream);
/* 45 */     this.core = core;
/*    */   }
/*    */   
/*    */   protected Annotation getAnnotation() {
/* 49 */     return (Annotation)this.core;
/*    */   }
/*    */   
/*    */   protected Quick newInstance(Locatable upstream, Annotation core) {
/* 53 */     return new XmlElementDeclQuick(upstream, (XmlElementDecl)core);
/*    */   }
/*    */   
/*    */   public Class<XmlElementDecl> annotationType() {
/* 57 */     return XmlElementDecl.class;
/*    */   }
/*    */   
/*    */   public String name() {
/* 61 */     return this.core.name();
/*    */   }
/*    */   
/*    */   public Class scope() {
/* 65 */     return this.core.scope();
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
/*    */   public String substitutionHeadNamespace() {
/* 77 */     return this.core.substitutionHeadNamespace();
/*    */   }
/*    */   
/*    */   public String substitutionHeadName() {
/* 81 */     return this.core.substitutionHeadName();
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/bind/v2/model/annotation/XmlElementDeclQuick.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */