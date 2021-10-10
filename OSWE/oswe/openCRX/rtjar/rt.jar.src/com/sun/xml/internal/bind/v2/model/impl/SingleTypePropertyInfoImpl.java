/*     */ package com.sun.xml.internal.bind.v2.model.impl;
/*     */ 
/*     */ import com.sun.xml.internal.bind.v2.model.core.ID;
/*     */ import com.sun.xml.internal.bind.v2.model.core.NonElement;
/*     */ import com.sun.xml.internal.bind.v2.model.core.PropertyInfo;
/*     */ import com.sun.xml.internal.bind.v2.model.runtime.RuntimeNonElementRef;
/*     */ import com.sun.xml.internal.bind.v2.model.runtime.RuntimePropertyInfo;
/*     */ import com.sun.xml.internal.bind.v2.runtime.IllegalAnnotationException;
/*     */ import com.sun.xml.internal.bind.v2.runtime.Transducer;
/*     */ import com.sun.xml.internal.bind.v2.runtime.reflect.Accessor;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import javax.xml.bind.annotation.XmlList;
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
/*     */ abstract class SingleTypePropertyInfoImpl<T, C, F, M>
/*     */   extends PropertyInfoImpl<T, C, F, M>
/*     */ {
/*     */   private NonElement<T, C> type;
/*     */   private final Accessor acc;
/*     */   private Transducer xducer;
/*     */   
/*     */   public SingleTypePropertyInfoImpl(ClassInfoImpl<T, C, F, M> classInfo, PropertySeed<T, C, F, M> seed) {
/*  60 */     super(classInfo, seed);
/*  61 */     if (this instanceof RuntimePropertyInfo) {
/*  62 */       Accessor rawAcc = ((RuntimeClassInfoImpl.RuntimePropertySeed)seed).getAccessor();
/*  63 */       if (getAdapter() != null && !isCollection())
/*     */       {
/*     */         
/*  66 */         rawAcc = rawAcc.adapt(((RuntimePropertyInfo)this).getAdapter()); } 
/*  67 */       this.acc = rawAcc;
/*     */     } else {
/*  69 */       this.acc = null;
/*     */     } 
/*     */   }
/*     */   public List<? extends NonElement<T, C>> ref() {
/*  73 */     return Collections.singletonList(getTarget());
/*     */   }
/*     */   
/*     */   public NonElement<T, C> getTarget() {
/*  77 */     if (this.type == null) {
/*  78 */       assert this.parent.builder != null : "this method must be called during the build stage";
/*  79 */       this.type = this.parent.builder.getTypeInfo(getIndividualType(), this);
/*     */     } 
/*  81 */     return this.type;
/*     */   }
/*     */   
/*     */   public PropertyInfo<T, C> getSource() {
/*  85 */     return this;
/*     */   }
/*     */   
/*     */   public void link() {
/*  89 */     super.link();
/*     */     
/*  91 */     if (!NonElement.ANYTYPE_NAME.equals(this.type.getTypeName()) && !this.type.isSimpleType() && id() != ID.IDREF) {
/*  92 */       this.parent.builder.reportError(new IllegalAnnotationException(Messages.SIMPLE_TYPE_IS_REQUIRED
/*  93 */             .format(new Object[0]), this.seed));
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*  98 */     if (!isCollection() && this.seed.hasAnnotation(XmlList.class)) {
/*  99 */       this.parent.builder.reportError(new IllegalAnnotationException(Messages.XMLLIST_ON_SINGLE_PROPERTY
/* 100 */             .format(new Object[0]), this));
/*     */     }
/*     */   }
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
/*     */   public Accessor getAccessor() {
/* 119 */     return this.acc;
/*     */   }
/*     */ 
/*     */   
/*     */   public Transducer getTransducer() {
/* 124 */     if (this.xducer == null) {
/* 125 */       this.xducer = RuntimeModelBuilder.createTransducer((RuntimeNonElementRef)this);
/* 126 */       if (this.xducer == null)
/*     */       {
/*     */         
/* 129 */         this.xducer = RuntimeBuiltinLeafInfoImpl.STRING;
/*     */       }
/*     */     } 
/* 132 */     return this.xducer;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/bind/v2/model/impl/SingleTypePropertyInfoImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */