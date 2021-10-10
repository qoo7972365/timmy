/*     */ package com.sun.xml.internal.bind.v2.model.impl;
/*     */ 
/*     */ import com.sun.xml.internal.bind.v2.model.annotation.AnnotationReader;
/*     */ import com.sun.xml.internal.bind.v2.model.core.ClassInfo;
/*     */ import com.sun.xml.internal.bind.v2.model.core.Element;
/*     */ import com.sun.xml.internal.bind.v2.model.core.ElementInfo;
/*     */ import com.sun.xml.internal.bind.v2.model.core.NonElement;
/*     */ import com.sun.xml.internal.bind.v2.model.core.PropertyKind;
/*     */ import com.sun.xml.internal.bind.v2.model.core.ReferencePropertyInfo;
/*     */ import com.sun.xml.internal.bind.v2.model.core.WildcardMode;
/*     */ import com.sun.xml.internal.bind.v2.model.nav.Navigator;
/*     */ import com.sun.xml.internal.bind.v2.runtime.IllegalAnnotationException;
/*     */ import java.lang.annotation.Annotation;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.LinkedHashSet;
/*     */ import java.util.Set;
/*     */ import javax.xml.bind.JAXBElement;
/*     */ import javax.xml.bind.annotation.XmlAnyElement;
/*     */ import javax.xml.bind.annotation.XmlElementRef;
/*     */ import javax.xml.bind.annotation.XmlElementRefs;
/*     */ import javax.xml.bind.annotation.XmlMixed;
/*     */ import javax.xml.bind.annotation.XmlNsForm;
/*     */ import javax.xml.bind.annotation.XmlSchema;
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
/*     */ class ReferencePropertyInfoImpl<T, C, F, M>
/*     */   extends ERPropertyInfoImpl<T, C, F, M>
/*     */   implements ReferencePropertyInfo<T, C>, DummyPropertyInfo<T, C, F, M>
/*     */ {
/*     */   private Set<Element<T, C>> types;
/*  67 */   private Set<ReferencePropertyInfoImpl<T, C, F, M>> subTypes = new LinkedHashSet<>();
/*     */ 
/*     */   
/*     */   private final boolean isMixed;
/*     */ 
/*     */   
/*     */   private final WildcardMode wildcard;
/*     */ 
/*     */   
/*     */   private final C domHandler;
/*     */ 
/*     */   
/*     */   private Boolean isRequired;
/*     */ 
/*     */   
/*     */   public ReferencePropertyInfoImpl(ClassInfoImpl<T, C, F, M> classInfo, PropertySeed<T, C, F, M> seed) {
/*  83 */     super(classInfo, seed);
/*     */     
/*  85 */     this.isMixed = (seed.readAnnotation(XmlMixed.class) != null);
/*     */     
/*  87 */     XmlAnyElement xae = (XmlAnyElement)seed.readAnnotation(XmlAnyElement.class);
/*  88 */     if (xae == null) {
/*  89 */       this.wildcard = null;
/*  90 */       this.domHandler = null;
/*     */     } else {
/*  92 */       this.wildcard = xae.lax() ? WildcardMode.LAX : WildcardMode.SKIP;
/*  93 */       this.domHandler = (C)nav().asDecl(reader().getClassValue((Annotation)xae, "value"));
/*     */     } 
/*     */   }
/*     */   
/*     */   public Set<? extends Element<T, C>> ref() {
/*  98 */     return getElements();
/*     */   }
/*     */   
/*     */   public PropertyKind kind() {
/* 102 */     return PropertyKind.REFERENCE;
/*     */   }
/*     */   
/*     */   public Set<? extends Element<T, C>> getElements() {
/* 106 */     if (this.types == null)
/* 107 */       calcTypes(false); 
/* 108 */     assert this.types != null;
/* 109 */     return this.types;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void calcTypes(boolean last) {
/*     */     XmlElementRef[] ann;
/* 120 */     this.types = new LinkedHashSet<>();
/* 121 */     XmlElementRefs refs = (XmlElementRefs)this.seed.readAnnotation(XmlElementRefs.class);
/* 122 */     XmlElementRef ref = (XmlElementRef)this.seed.readAnnotation(XmlElementRef.class);
/*     */     
/* 124 */     if (refs != null && ref != null) {
/* 125 */       this.parent.builder.reportError(new IllegalAnnotationException(Messages.MUTUALLY_EXCLUSIVE_ANNOTATIONS
/* 126 */             .format(new Object[] {
/* 127 */                 nav().getClassName(this.parent.getClazz()) + '#' + this.seed.getName(), ref
/* 128 */                 .annotationType().getName(), refs.annotationType().getName()
/*     */               }, ), (Annotation)ref, (Annotation)refs));
/*     */     }
/*     */     
/* 132 */     if (refs != null) {
/* 133 */       ann = refs.value();
/*     */     }
/* 135 */     else if (ref != null) {
/* 136 */       ann = new XmlElementRef[] { ref };
/*     */     } else {
/* 138 */       ann = null;
/*     */     } 
/*     */     
/* 141 */     this.isRequired = Boolean.valueOf(!isCollection());
/*     */     
/* 143 */     if (ann != null) {
/* 144 */       Navigator<T, C, F, M> nav = nav();
/* 145 */       AnnotationReader<T, C, F, M> reader = reader();
/*     */       
/* 147 */       T defaultType = (T)nav.ref(XmlElementRef.DEFAULT.class);
/* 148 */       C je = (C)nav.asDecl(JAXBElement.class);
/*     */       
/* 150 */       for (XmlElementRef r : ann) {
/*     */         boolean yield;
/* 152 */         T type = (T)reader.getClassValue((Annotation)r, "type");
/* 153 */         if (nav().isSameType(type, defaultType))
/* 154 */           type = (T)nav.erasure(getIndividualType()); 
/* 155 */         if (nav.getBaseClass(type, je) != null) {
/* 156 */           yield = addGenericElement(r);
/*     */         } else {
/* 158 */           yield = addAllSubtypes(type);
/*     */         } 
/*     */ 
/*     */         
/* 162 */         if (this.isRequired.booleanValue() && !isRequired(r)) {
/* 163 */           this.isRequired = Boolean.valueOf(false);
/*     */         }
/* 165 */         if (last && !yield) {
/*     */ 
/*     */           
/* 168 */           if (nav().isSameType(type, nav.ref(JAXBElement.class))) {
/*     */             
/* 170 */             this.parent.builder.reportError(new IllegalAnnotationException(Messages.NO_XML_ELEMENT_DECL
/* 171 */                   .format(new Object[] {
/* 172 */                       getEffectiveNamespaceFor(r), r.name()
/*     */                     }, ), this));
/*     */           } else {
/*     */             
/* 176 */             this.parent.builder.reportError(new IllegalAnnotationException(Messages.INVALID_XML_ELEMENT_REF
/* 177 */                   .format(new Object[] { type }, ), this));
/*     */           } 
/*     */ 
/*     */ 
/*     */           
/*     */           return;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 189 */     for (ReferencePropertyInfoImpl<T, C, F, M> info : this.subTypes) {
/* 190 */       PropertySeed<T, C, F, M> sd = info.seed;
/* 191 */       refs = (XmlElementRefs)sd.readAnnotation(XmlElementRefs.class);
/* 192 */       ref = (XmlElementRef)sd.readAnnotation(XmlElementRef.class);
/*     */       
/* 194 */       if (refs != null && ref != null) {
/* 195 */         this.parent.builder.reportError(new IllegalAnnotationException(Messages.MUTUALLY_EXCLUSIVE_ANNOTATIONS
/* 196 */               .format(new Object[] {
/* 197 */                   nav().getClassName(this.parent.getClazz()) + '#' + this.seed.getName(), ref
/* 198 */                   .annotationType().getName(), refs.annotationType().getName()
/*     */                 }, ), (Annotation)ref, (Annotation)refs));
/*     */       }
/*     */       
/* 202 */       if (refs != null) {
/* 203 */         ann = refs.value();
/*     */       }
/* 205 */       else if (ref != null) {
/* 206 */         ann = new XmlElementRef[] { ref };
/*     */       } else {
/* 208 */         ann = null;
/*     */       } 
/*     */ 
/*     */       
/* 212 */       if (ann != null) {
/* 213 */         Navigator<T, C, F, M> nav = nav();
/* 214 */         AnnotationReader<T, C, F, M> reader = reader();
/*     */         
/* 216 */         T defaultType = (T)nav.ref(XmlElementRef.DEFAULT.class);
/* 217 */         C je = (C)nav.asDecl(JAXBElement.class);
/*     */         
/* 219 */         for (XmlElementRef r : ann) {
/*     */           boolean yield;
/* 221 */           T type = (T)reader.getClassValue((Annotation)r, "type");
/* 222 */           if (nav().isSameType(type, defaultType)) {
/* 223 */             type = (T)nav.erasure(getIndividualType());
/*     */           }
/* 225 */           if (nav.getBaseClass(type, je) != null) {
/* 226 */             yield = addGenericElement(r, info);
/*     */           } else {
/*     */             
/* 229 */             yield = addAllSubtypes(type);
/*     */           } 
/*     */           
/* 232 */           if (last && !yield) {
/*     */ 
/*     */             
/* 235 */             if (nav().isSameType(type, nav.ref(JAXBElement.class))) {
/*     */               
/* 237 */               this.parent.builder.reportError(new IllegalAnnotationException(Messages.NO_XML_ELEMENT_DECL
/* 238 */                     .format(new Object[] {
/* 239 */                         getEffectiveNamespaceFor(r), r.name()
/*     */                       }, ), this));
/*     */             } else {
/*     */               
/* 243 */               this.parent.builder.reportError(new IllegalAnnotationException(Messages.INVALID_XML_ELEMENT_REF
/* 244 */                     .format(new Object[0]), this));
/*     */             } 
/*     */ 
/*     */ 
/*     */             
/*     */             return;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 257 */     this.types = Collections.unmodifiableSet(this.types);
/*     */   }
/*     */   
/*     */   public boolean isRequired() {
/* 261 */     if (this.isRequired == null)
/* 262 */       calcTypes(false); 
/* 263 */     return this.isRequired.booleanValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean is2_2 = true;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isRequired(XmlElementRef ref) {
/* 278 */     if (!is2_2) return true;
/*     */     
/*     */     try {
/* 281 */       return ref.required();
/* 282 */     } catch (LinkageError e) {
/* 283 */       is2_2 = false;
/* 284 */       return true;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean addGenericElement(XmlElementRef r) {
/* 293 */     String nsUri = getEffectiveNamespaceFor(r);
/*     */     
/* 295 */     return addGenericElement(this.parent.owner.getElementInfo(this.parent.getClazz(), new QName(nsUri, r.name())));
/*     */   }
/*     */   
/*     */   private boolean addGenericElement(XmlElementRef r, ReferencePropertyInfoImpl<T, C, F, M> info) {
/* 299 */     String nsUri = info.getEffectiveNamespaceFor(r);
/* 300 */     ElementInfo<T, C, F, M> ei = (ElementInfo<T, C, F, M>)this.parent.owner.getElementInfo(info.parent.getClazz(), new QName(nsUri, r.name()));
/* 301 */     this.types.add(ei);
/* 302 */     return true;
/*     */   }
/*     */   
/*     */   private String getEffectiveNamespaceFor(XmlElementRef r) {
/* 306 */     String nsUri = r.namespace();
/*     */     
/* 308 */     XmlSchema xs = (XmlSchema)reader().getPackageAnnotation(XmlSchema.class, this.parent.getClazz(), this);
/* 309 */     if (xs != null && xs.attributeFormDefault() == XmlNsForm.QUALIFIED)
/*     */     {
/*     */       
/* 312 */       if (nsUri.length() == 0) {
/* 313 */         nsUri = this.parent.builder.defaultNsUri;
/*     */       }
/*     */     }
/* 316 */     return nsUri;
/*     */   }
/*     */   
/*     */   private boolean addGenericElement(ElementInfo<T, C> ei) {
/* 320 */     if (ei == null)
/* 321 */       return false; 
/* 322 */     this.types.add(ei);
/* 323 */     for (ElementInfo<T, C> subst : (Iterable<ElementInfo<T, C>>)ei.getSubstitutionMembers())
/* 324 */       addGenericElement(subst); 
/* 325 */     return true;
/*     */   }
/*     */   
/*     */   private boolean addAllSubtypes(T type) {
/* 329 */     Navigator<T, C, F, M> nav = nav();
/*     */ 
/*     */     
/* 332 */     NonElement<T, C> t = this.parent.builder.getClassInfo((C)nav.asDecl(type), this);
/* 333 */     if (!(t instanceof ClassInfo))
/*     */     {
/* 335 */       return false;
/*     */     }
/* 337 */     boolean result = false;
/*     */     
/* 339 */     ClassInfo<T, C> c = (ClassInfo<T, C>)t;
/* 340 */     if (c.isElement()) {
/* 341 */       this.types.add(c.asElement());
/* 342 */       result = true;
/*     */     } 
/*     */ 
/*     */     
/* 346 */     for (ClassInfo<T, C> ci : (Iterable<ClassInfo<T, C>>)this.parent.owner.beans().values()) {
/* 347 */       if (ci.isElement() && nav.isSubClassOf(ci.getType(), type)) {
/* 348 */         this.types.add(ci.asElement());
/* 349 */         result = true;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 354 */     for (ElementInfo<T, C> ei : (Iterable<ElementInfo<T, C>>)this.parent.owner.getElementMappings(null).values()) {
/* 355 */       if (nav.isSubClassOf(ei.getType(), type)) {
/* 356 */         this.types.add(ei);
/* 357 */         result = true;
/*     */       } 
/*     */     } 
/*     */     
/* 361 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void link() {
/* 367 */     super.link();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 372 */     calcTypes(true);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public final void addType(PropertyInfoImpl<T, C, F, M> info) {
/* 378 */     this.subTypes.add((ReferencePropertyInfoImpl<T, C, F, M>)info);
/*     */   }
/*     */   
/*     */   public final boolean isMixed() {
/* 382 */     return this.isMixed;
/*     */   }
/*     */   
/*     */   public final WildcardMode getWildcard() {
/* 386 */     return this.wildcard;
/*     */   }
/*     */   
/*     */   public final C getDOMHandler() {
/* 390 */     return this.domHandler;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/bind/v2/model/impl/ReferencePropertyInfoImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */