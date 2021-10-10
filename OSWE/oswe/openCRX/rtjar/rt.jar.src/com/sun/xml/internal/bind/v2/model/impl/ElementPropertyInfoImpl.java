/*     */ package com.sun.xml.internal.bind.v2.model.impl;
/*     */ 
/*     */ import com.sun.istack.internal.FinalArrayList;
/*     */ import com.sun.xml.internal.bind.v2.model.core.ElementPropertyInfo;
/*     */ import com.sun.xml.internal.bind.v2.model.core.ID;
/*     */ import com.sun.xml.internal.bind.v2.model.core.PropertyKind;
/*     */ import com.sun.xml.internal.bind.v2.model.core.TypeInfo;
/*     */ import com.sun.xml.internal.bind.v2.runtime.IllegalAnnotationException;
/*     */ import java.lang.annotation.Annotation;
/*     */ import java.util.AbstractList;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import javax.xml.bind.annotation.XmlElement;
/*     */ import javax.xml.bind.annotation.XmlElements;
/*     */ import javax.xml.bind.annotation.XmlList;
/*     */ import javax.xml.namespace.QName;
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
/*     */ class ElementPropertyInfoImpl<TypeT, ClassDeclT, FieldT, MethodT>
/*     */   extends ERPropertyInfoImpl<TypeT, ClassDeclT, FieldT, MethodT>
/*     */   implements ElementPropertyInfo<TypeT, ClassDeclT>
/*     */ {
/*     */   private List<TypeRefImpl<TypeT, ClassDeclT>> types;
/*     */   
/*  61 */   private final List<TypeInfo<TypeT, ClassDeclT>> ref = new AbstractList<TypeInfo<TypeT, ClassDeclT>>() {
/*     */       public TypeInfo<TypeT, ClassDeclT> get(int index) {
/*  63 */         return (TypeInfo<TypeT, ClassDeclT>)((TypeRefImpl)ElementPropertyInfoImpl.this.getTypes().get(index)).getTarget();
/*     */       }
/*     */       
/*     */       public int size() {
/*  67 */         return ElementPropertyInfoImpl.this.getTypes().size();
/*     */       }
/*     */     };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Boolean isRequired;
/*     */ 
/*     */ 
/*     */   
/*     */   private final boolean isValueList;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   ElementPropertyInfoImpl(ClassInfoImpl<TypeT, ClassDeclT, FieldT, MethodT> parent, PropertySeed<TypeT, ClassDeclT, FieldT, MethodT> propertySeed) {
/*  85 */     super(parent, propertySeed);
/*     */     
/*  87 */     this.isValueList = this.seed.hasAnnotation(XmlList.class);
/*     */   }
/*     */ 
/*     */   
/*     */   public List<? extends TypeRefImpl<TypeT, ClassDeclT>> getTypes() {
/*  92 */     if (this.types == null) {
/*  93 */       this.types = (List<TypeRefImpl<TypeT, ClassDeclT>>)new FinalArrayList();
/*  94 */       XmlElement[] ann = null;
/*     */       
/*  96 */       XmlElement xe = (XmlElement)this.seed.readAnnotation(XmlElement.class);
/*  97 */       XmlElements xes = (XmlElements)this.seed.readAnnotation(XmlElements.class);
/*     */       
/*  99 */       if (xe != null && xes != null) {
/* 100 */         this.parent.builder.reportError(new IllegalAnnotationException(Messages.MUTUALLY_EXCLUSIVE_ANNOTATIONS
/* 101 */               .format(new Object[] {
/* 102 */                   nav().getClassName(this.parent.getClazz()) + '#' + this.seed.getName(), xe
/* 103 */                   .annotationType().getName(), xes.annotationType().getName()
/*     */                 }, ), (Annotation)xe, (Annotation)xes));
/*     */       }
/*     */       
/* 107 */       this.isRequired = Boolean.valueOf(true);
/*     */       
/* 109 */       if (xe != null) {
/* 110 */         ann = new XmlElement[] { xe };
/*     */       }
/* 112 */       else if (xes != null) {
/* 113 */         ann = xes.value();
/*     */       } 
/* 115 */       if (ann == null) {
/*     */         
/* 117 */         TypeT t = getIndividualType();
/* 118 */         if (!nav().isPrimitive(t) || isCollection()) {
/* 119 */           this.isRequired = Boolean.valueOf(false);
/*     */         }
/* 121 */         this.types.add(createTypeRef(calcXmlName((XmlElement)null), t, isCollection(), (String)null));
/*     */       } else {
/* 123 */         for (XmlElement item : ann) {
/*     */           
/* 125 */           QName name = calcXmlName(item);
/* 126 */           TypeT type = (TypeT)reader().getClassValue((Annotation)item, "type");
/* 127 */           if (nav().isSameType(type, nav().ref(XmlElement.DEFAULT.class)))
/* 128 */             type = getIndividualType(); 
/* 129 */           if ((!nav().isPrimitive(type) || isCollection()) && !item.required())
/* 130 */             this.isRequired = Boolean.valueOf(false); 
/* 131 */           this.types.add(createTypeRef(name, type, item.nillable(), getDefaultValue(item.defaultValue())));
/*     */         } 
/*     */       } 
/* 134 */       this.types = Collections.unmodifiableList(this.types);
/* 135 */       assert !this.types.contains(null);
/*     */     } 
/* 137 */     return this.types;
/*     */   }
/*     */   
/*     */   private String getDefaultValue(String value) {
/* 141 */     if (value.equals("\000")) {
/* 142 */       return null;
/*     */     }
/* 144 */     return value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected TypeRefImpl<TypeT, ClassDeclT> createTypeRef(QName name, TypeT type, boolean isNillable, String defaultValue) {
/* 151 */     return new TypeRefImpl<>(this, name, type, isNillable, defaultValue);
/*     */   }
/*     */   
/*     */   public boolean isValueList() {
/* 155 */     return this.isValueList;
/*     */   }
/*     */   
/*     */   public boolean isRequired() {
/* 159 */     if (this.isRequired == null)
/* 160 */       getTypes(); 
/* 161 */     return this.isRequired.booleanValue();
/*     */   }
/*     */   
/*     */   public List<? extends TypeInfo<TypeT, ClassDeclT>> ref() {
/* 165 */     return this.ref;
/*     */   }
/*     */   
/*     */   public final PropertyKind kind() {
/* 169 */     return PropertyKind.ELEMENT;
/*     */   }
/*     */   
/*     */   protected void link() {
/* 173 */     super.link();
/* 174 */     for (TypeRefImpl<TypeT, ClassDeclT> ref : getTypes()) {
/* 175 */       ref.link();
/*     */     }
/*     */     
/* 178 */     if (isValueList()) {
/*     */ 
/*     */       
/* 181 */       if (id() != ID.IDREF)
/*     */       {
/*     */ 
/*     */         
/* 185 */         for (TypeRefImpl<TypeT, ClassDeclT> ref : this.types) {
/* 186 */           if (!ref.getTarget().isSimpleType()) {
/* 187 */             this.parent.builder.reportError(new IllegalAnnotationException(Messages.XMLLIST_NEEDS_SIMPLETYPE
/* 188 */                   .format(new Object[] {
/* 189 */                       nav().getTypeName(ref.getTarget().getType())
/*     */                     }, ), this));
/*     */             break;
/*     */           } 
/*     */         } 
/*     */       }
/* 195 */       if (!isCollection())
/* 196 */         this.parent.builder.reportError(new IllegalAnnotationException(Messages.XMLLIST_ON_SINGLE_PROPERTY
/* 197 */               .format(new Object[0]), this)); 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/bind/v2/model/impl/ElementPropertyInfoImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */