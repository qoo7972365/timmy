/*    */ package com.sun.xml.internal.bind.v2.model.impl;
/*    */ 
/*    */ import com.sun.xml.internal.bind.v2.runtime.IllegalAnnotationException;
/*    */ import java.lang.annotation.Annotation;
/*    */ import javax.xml.bind.annotation.XmlElementWrapper;
/*    */ import javax.xml.namespace.QName;
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
/*    */ abstract class ERPropertyInfoImpl<TypeT, ClassDeclT, FieldT, MethodT>
/*    */   extends PropertyInfoImpl<TypeT, ClassDeclT, FieldT, MethodT>
/*    */ {
/*    */   private final QName xmlName;
/*    */   private final boolean wrapperNillable;
/*    */   private final boolean wrapperRequired;
/*    */   
/*    */   public ERPropertyInfoImpl(ClassInfoImpl<TypeT, ClassDeclT, FieldT, MethodT> classInfo, PropertySeed<TypeT, ClassDeclT, FieldT, MethodT> propertySeed) {
/* 42 */     super(classInfo, propertySeed);
/*    */     
/* 44 */     XmlElementWrapper e = (XmlElementWrapper)this.seed.readAnnotation(XmlElementWrapper.class);
/*    */     
/* 46 */     boolean nil = false;
/* 47 */     boolean required = false;
/* 48 */     if (!isCollection()) {
/* 49 */       this.xmlName = null;
/* 50 */       if (e != null) {
/* 51 */         classInfo.builder.reportError(new IllegalAnnotationException(Messages.XML_ELEMENT_WRAPPER_ON_NON_COLLECTION
/* 52 */               .format(new Object[] {
/* 53 */                   nav().getClassName(this.parent.getClazz()) + '.' + this.seed.getName()
/*    */                 }, ), (Annotation)e));
/*    */       }
/*    */     }
/* 57 */     else if (e != null) {
/* 58 */       this.xmlName = calcXmlName(e);
/* 59 */       nil = e.nillable();
/* 60 */       required = e.required();
/*    */     } else {
/* 62 */       this.xmlName = null;
/*    */     } 
/*    */     
/* 65 */     this.wrapperNillable = nil;
/* 66 */     this.wrapperRequired = required;
/*    */   }
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
/*    */   public final QName getXmlName() {
/* 85 */     return this.xmlName;
/*    */   }
/*    */   
/*    */   public final boolean isCollectionNillable() {
/* 89 */     return this.wrapperNillable;
/*    */   }
/*    */   
/*    */   public final boolean isCollectionRequired() {
/* 93 */     return this.wrapperRequired;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/bind/v2/model/impl/ERPropertyInfoImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */