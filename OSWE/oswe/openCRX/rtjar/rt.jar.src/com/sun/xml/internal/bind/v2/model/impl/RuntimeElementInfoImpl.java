/*     */ package com.sun.xml.internal.bind.v2.model.impl;
/*     */ 
/*     */ import com.sun.xml.internal.bind.v2.model.core.Adapter;
/*     */ import com.sun.xml.internal.bind.v2.model.core.ClassInfo;
/*     */ import com.sun.xml.internal.bind.v2.model.core.ElementPropertyInfo;
/*     */ import com.sun.xml.internal.bind.v2.model.core.NonElement;
/*     */ import com.sun.xml.internal.bind.v2.model.core.PropertyInfo;
/*     */ import com.sun.xml.internal.bind.v2.model.runtime.RuntimeClassInfo;
/*     */ import com.sun.xml.internal.bind.v2.model.runtime.RuntimeElementInfo;
/*     */ import com.sun.xml.internal.bind.v2.model.runtime.RuntimeElementPropertyInfo;
/*     */ import com.sun.xml.internal.bind.v2.model.runtime.RuntimeNonElement;
/*     */ import com.sun.xml.internal.bind.v2.model.runtime.RuntimeNonElementRef;
/*     */ import com.sun.xml.internal.bind.v2.model.runtime.RuntimePropertyInfo;
/*     */ import com.sun.xml.internal.bind.v2.model.runtime.RuntimeTypeRef;
/*     */ import com.sun.xml.internal.bind.v2.runtime.IllegalAnnotationException;
/*     */ import com.sun.xml.internal.bind.v2.runtime.Transducer;
/*     */ import com.sun.xml.internal.bind.v2.runtime.reflect.Accessor;
/*     */ import java.lang.reflect.Field;
/*     */ import java.lang.reflect.Method;
/*     */ import java.lang.reflect.Type;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import javax.xml.bind.JAXBElement;
/*     */ import javax.xml.bind.annotation.adapters.XmlAdapter;
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
/*     */ final class RuntimeElementInfoImpl
/*     */   extends ElementInfoImpl<Type, Class, Field, Method>
/*     */   implements RuntimeElementInfo
/*     */ {
/*     */   private final Class<? extends XmlAdapter> adapterType;
/*     */   
/*     */   public RuntimeElementInfoImpl(RuntimeModelBuilder modelBuilder, RegistryInfoImpl<Type, Class, Field, Method> registry, Method method) throws IllegalAnnotationException {
/*  56 */     super(modelBuilder, registry, method);
/*     */     
/*  58 */     Adapter<Type, Class<?>> a = getProperty().getAdapter();
/*     */     
/*  60 */     if (a != null) {
/*  61 */       this.adapterType = (Class<? extends XmlAdapter>)a.adapterType;
/*     */     } else {
/*  63 */       this.adapterType = null;
/*     */     } 
/*     */   }
/*     */   
/*     */   protected ElementInfoImpl<Type, Class, Field, Method>.PropertyImpl createPropertyImpl() {
/*  68 */     return new RuntimePropertyImpl();
/*     */   }
/*     */   
/*     */   class RuntimePropertyImpl extends ElementInfoImpl<Type, Class, Field, Method>.PropertyImpl implements RuntimeElementPropertyInfo, RuntimeTypeRef {
/*     */     public Accessor getAccessor() {
/*  73 */       if (RuntimeElementInfoImpl.this.adapterType == null) {
/*  74 */         return Accessor.JAXB_ELEMENT_VALUE;
/*     */       }
/*  76 */       return Accessor.JAXB_ELEMENT_VALUE.adapt(
/*  77 */           (Class)(getAdapter()).defaultType, RuntimeElementInfoImpl.this.adapterType);
/*     */     }
/*     */     
/*     */     public Type getRawType() {
/*  81 */       return Collection.class;
/*     */     }
/*     */     
/*     */     public Type getIndividualType() {
/*  85 */       return (Type)RuntimeElementInfoImpl.this.getContentType().getType();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean elementOnlyContent() {
/*  90 */       return false;
/*     */     }
/*     */     
/*     */     public List<? extends RuntimeTypeRef> getTypes() {
/*  94 */       return Collections.singletonList(this);
/*     */     }
/*     */     
/*     */     public List<? extends RuntimeNonElement> ref() {
/*  98 */       return (List)super.ref();
/*     */     }
/*     */     
/*     */     public RuntimeNonElement getTarget() {
/* 102 */       return (RuntimeNonElement)super.getTarget();
/*     */     }
/*     */     
/*     */     public RuntimePropertyInfo getSource() {
/* 106 */       return (RuntimePropertyInfo)this;
/*     */     }
/*     */     
/*     */     public Transducer getTransducer() {
/* 110 */       return RuntimeModelBuilder.createTransducer((RuntimeNonElementRef)this);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RuntimeElementPropertyInfo getProperty() {
/* 120 */     return (RuntimeElementPropertyInfo)super.getProperty();
/*     */   }
/*     */ 
/*     */   
/*     */   public Class<? extends JAXBElement> getType() {
/* 125 */     return (Class<? extends JAXBElement>)Utils.REFLECTION_NAVIGATOR.erasure(super.getType());
/*     */   }
/*     */   
/*     */   public RuntimeClassInfo getScope() {
/* 129 */     return (RuntimeClassInfo)super.getScope();
/*     */   }
/*     */   
/*     */   public RuntimeNonElement getContentType() {
/* 133 */     return (RuntimeNonElement)super.getContentType();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/bind/v2/model/impl/RuntimeElementInfoImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */