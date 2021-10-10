/*     */ package com.sun.xml.internal.bind.v2.model.impl;
/*     */ 
/*     */ import com.sun.xml.internal.bind.v2.model.annotation.Locatable;
/*     */ import com.sun.xml.internal.bind.v2.model.annotation.MethodLocatable;
/*     */ import com.sun.xml.internal.bind.v2.model.core.RegistryInfo;
/*     */ import com.sun.xml.internal.bind.v2.model.core.TypeInfo;
/*     */ import com.sun.xml.internal.bind.v2.model.nav.Navigator;
/*     */ import com.sun.xml.internal.bind.v2.runtime.IllegalAnnotationException;
/*     */ import com.sun.xml.internal.bind.v2.runtime.Location;
/*     */ import java.util.LinkedHashSet;
/*     */ import java.util.Set;
/*     */ import javax.xml.bind.annotation.XmlElementDecl;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ final class RegistryInfoImpl<T, C, F, M>
/*     */   implements Locatable, RegistryInfo<T, C>
/*     */ {
/*     */   final C registryClass;
/*     */   private final Locatable upstream;
/*     */   private final Navigator<T, C, F, M> nav;
/*  59 */   private final Set<TypeInfo<T, C>> references = new LinkedHashSet<>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   RegistryInfoImpl(ModelBuilder<T, C, F, M> builder, Locatable upstream, C registryClass) {
/*  65 */     this.nav = builder.nav;
/*  66 */     this.registryClass = registryClass;
/*  67 */     this.upstream = upstream;
/*  68 */     builder.registries.put(getPackageName(), this);
/*     */     
/*  70 */     if (this.nav.getDeclaredField(registryClass, "_useJAXBProperties") != null) {
/*     */ 
/*     */       
/*  73 */       builder.reportError(new IllegalAnnotationException(Messages.MISSING_JAXB_PROPERTIES
/*  74 */             .format(new Object[] { getPackageName() }, ), this));
/*     */ 
/*     */       
/*     */       return;
/*     */     } 
/*     */ 
/*     */     
/*  81 */     for (M m : this.nav.getDeclaredMethods(registryClass)) {
/*  82 */       ElementInfoImpl<T, C, F, M> ei; XmlElementDecl em = (XmlElementDecl)builder.reader.getMethodAnnotation(XmlElementDecl.class, m, this);
/*     */ 
/*     */       
/*  85 */       if (em == null) {
/*  86 */         if (this.nav.getMethodName(m).startsWith("create"))
/*     */         {
/*  88 */           this.references.add((TypeInfo<T, C>)builder
/*  89 */               .getTypeInfo((T)this.nav.getReturnType(m), (Locatable)new MethodLocatable(this, m, this.nav)));
/*     */         }
/*     */ 
/*     */         
/*     */         continue;
/*     */       } 
/*     */ 
/*     */       
/*     */       try {
/*  98 */         ei = builder.createElementInfo(this, m);
/*  99 */       } catch (IllegalAnnotationException e) {
/* 100 */         builder.reportError(e);
/*     */ 
/*     */         
/*     */         continue;
/*     */       } 
/*     */       
/* 106 */       builder.typeInfoSet.add(ei, builder);
/* 107 */       this.references.add(ei);
/*     */     } 
/*     */   }
/*     */   
/*     */   public Locatable getUpstream() {
/* 112 */     return this.upstream;
/*     */   }
/*     */   
/*     */   public Location getLocation() {
/* 116 */     return this.nav.getClassLocation(this.registryClass);
/*     */   }
/*     */   
/*     */   public Set<TypeInfo<T, C>> getReferences() {
/* 120 */     return this.references;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getPackageName() {
/* 127 */     return this.nav.getPackageName(this.registryClass);
/*     */   }
/*     */   
/*     */   public C getClazz() {
/* 131 */     return this.registryClass;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/bind/v2/model/impl/RegistryInfoImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */