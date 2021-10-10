/*     */ package com.sun.xml.internal.bind.v2.model.impl;
/*     */ 
/*     */ import com.sun.istack.internal.FinalArrayList;
/*     */ import com.sun.xml.internal.bind.v2.TODO;
/*     */ import com.sun.xml.internal.bind.v2.model.annotation.AnnotationSource;
/*     */ import com.sun.xml.internal.bind.v2.model.core.Adapter;
/*     */ import com.sun.xml.internal.bind.v2.model.core.ClassInfo;
/*     */ import com.sun.xml.internal.bind.v2.model.core.Element;
/*     */ import com.sun.xml.internal.bind.v2.model.core.ElementInfo;
/*     */ import com.sun.xml.internal.bind.v2.model.core.ElementPropertyInfo;
/*     */ import com.sun.xml.internal.bind.v2.model.core.ID;
/*     */ import com.sun.xml.internal.bind.v2.model.core.NonElement;
/*     */ import com.sun.xml.internal.bind.v2.model.core.PropertyInfo;
/*     */ import com.sun.xml.internal.bind.v2.model.core.PropertyKind;
/*     */ import com.sun.xml.internal.bind.v2.model.core.TypeInfo;
/*     */ import com.sun.xml.internal.bind.v2.model.core.TypeRef;
/*     */ import com.sun.xml.internal.bind.v2.runtime.IllegalAnnotationException;
/*     */ import com.sun.xml.internal.bind.v2.runtime.Location;
/*     */ import com.sun.xml.internal.bind.v2.runtime.SwaRefAdapter;
/*     */ import java.lang.annotation.Annotation;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import javax.activation.MimeType;
/*     */ import javax.xml.bind.JAXBElement;
/*     */ import javax.xml.bind.annotation.XmlAttachmentRef;
/*     */ import javax.xml.bind.annotation.XmlElementDecl;
/*     */ import javax.xml.bind.annotation.XmlID;
/*     */ import javax.xml.bind.annotation.XmlIDREF;
/*     */ import javax.xml.bind.annotation.XmlInlineBinaryData;
/*     */ import javax.xml.bind.annotation.XmlSchema;
/*     */ import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
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
/*     */ class ElementInfoImpl<T, C, F, M>
/*     */   extends TypeInfoImpl<T, C, F, M>
/*     */   implements ElementInfo<T, C>
/*     */ {
/*     */   private final QName tagName;
/*     */   private final NonElement<T, C> contentType;
/*     */   private final T tOfJAXBElementT;
/*     */   private final T elementType;
/*     */   private final ClassInfo<T, C> scope;
/*     */   private final XmlElementDecl anno;
/*     */   private ElementInfoImpl<T, C, F, M> substitutionHead;
/*     */   private FinalArrayList<ElementInfoImpl<T, C, F, M>> substitutionMembers;
/*     */   private final M method;
/*     */   private final Adapter<T, C> adapter;
/*     */   private final boolean isCollection;
/*     */   private final ID id;
/*     */   private final PropertyImpl property;
/*     */   private final MimeType expectedMimeType;
/*     */   private final boolean inlineBinary;
/*     */   private final QName schemaType;
/*     */   
/*     */   protected class PropertyImpl
/*     */     implements ElementPropertyInfo<T, C>, TypeRef<T, C>, AnnotationSource
/*     */   {
/*     */     public NonElement<T, C> getTarget() {
/* 127 */       return ElementInfoImpl.this.contentType;
/*     */     }
/*     */     public QName getTagName() {
/* 130 */       return ElementInfoImpl.this.tagName;
/*     */     }
/*     */     
/*     */     public List<? extends TypeRef<T, C>> getTypes() {
/* 134 */       return Collections.singletonList(this);
/*     */     }
/*     */     
/*     */     public List<? extends NonElement<T, C>> ref() {
/* 138 */       return Collections.singletonList(ElementInfoImpl.this.contentType);
/*     */     }
/*     */     
/*     */     public QName getXmlName() {
/* 142 */       return ElementInfoImpl.this.tagName;
/*     */     }
/*     */     
/*     */     public boolean isCollectionRequired() {
/* 146 */       return false;
/*     */     }
/*     */     
/*     */     public boolean isCollectionNillable() {
/* 150 */       return true;
/*     */     }
/*     */     
/*     */     public boolean isNillable() {
/* 154 */       return true;
/*     */     }
/*     */     
/*     */     public String getDefaultValue() {
/* 158 */       String v = ElementInfoImpl.this.anno.defaultValue();
/* 159 */       if (v.equals("\000")) {
/* 160 */         return null;
/*     */       }
/* 162 */       return v;
/*     */     }
/*     */     
/*     */     public ElementInfoImpl<T, C, F, M> parent() {
/* 166 */       return ElementInfoImpl.this;
/*     */     }
/*     */     
/*     */     public String getName() {
/* 170 */       return "value";
/*     */     }
/*     */     
/*     */     public String displayName() {
/* 174 */       return "JAXBElement#value";
/*     */     }
/*     */     
/*     */     public boolean isCollection() {
/* 178 */       return ElementInfoImpl.this.isCollection;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean isValueList() {
/* 185 */       return ElementInfoImpl.this.isCollection;
/*     */     }
/*     */     
/*     */     public boolean isRequired() {
/* 189 */       return true;
/*     */     }
/*     */     
/*     */     public PropertyKind kind() {
/* 193 */       return PropertyKind.ELEMENT;
/*     */     }
/*     */     
/*     */     public Adapter<T, C> getAdapter() {
/* 197 */       return ElementInfoImpl.this.adapter;
/*     */     }
/*     */     
/*     */     public ID id() {
/* 201 */       return ElementInfoImpl.this.id;
/*     */     }
/*     */     
/*     */     public MimeType getExpectedMimeType() {
/* 205 */       return ElementInfoImpl.this.expectedMimeType;
/*     */     }
/*     */     
/*     */     public QName getSchemaType() {
/* 209 */       return ElementInfoImpl.this.schemaType;
/*     */     }
/*     */     
/*     */     public boolean inlineBinaryData() {
/* 213 */       return ElementInfoImpl.this.inlineBinary;
/*     */     }
/*     */     
/*     */     public PropertyInfo<T, C> getSource() {
/* 217 */       return (PropertyInfo<T, C>)this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public <A extends Annotation> A readAnnotation(Class<A> annotationType) {
/* 226 */       return (A)ElementInfoImpl.this.reader().getMethodAnnotation(annotationType, ElementInfoImpl.this.method, ElementInfoImpl.this);
/*     */     }
/*     */     
/*     */     public boolean hasAnnotation(Class<? extends Annotation> annotationType) {
/* 230 */       return ElementInfoImpl.this.reader().hasMethodAnnotation(annotationType, ElementInfoImpl.this.method);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ElementInfoImpl(ModelBuilder<T, C, F, M> builder, RegistryInfoImpl<T, C, F, M> registry, M m) throws IllegalAnnotationException {
/* 240 */     super(builder, registry);
/*     */     
/* 242 */     this.method = m;
/* 243 */     this.anno = (XmlElementDecl)reader().getMethodAnnotation(XmlElementDecl.class, m, this);
/* 244 */     assert this.anno != null;
/* 245 */     assert this.anno instanceof com.sun.xml.internal.bind.v2.model.annotation.Locatable;
/*     */     
/* 247 */     this.elementType = (T)nav().getReturnType(m);
/* 248 */     T baseClass = (T)nav().getBaseClass(this.elementType, nav().asDecl(JAXBElement.class));
/* 249 */     if (baseClass == null) {
/* 250 */       throw new IllegalAnnotationException(Messages.XML_ELEMENT_MAPPING_ON_NON_IXMLELEMENT_METHOD
/* 251 */           .format(new Object[] { nav().getMethodName(m) }, ), this.anno);
/*     */     }
/*     */     
/* 254 */     this.tagName = parseElementName(this.anno);
/* 255 */     T[] methodParams = (T[])nav().getMethodParameters(m);
/*     */ 
/*     */     
/* 258 */     Adapter<T, C> a = null;
/* 259 */     if (methodParams.length > 0) {
/* 260 */       XmlJavaTypeAdapter adapter = (XmlJavaTypeAdapter)reader().getMethodAnnotation(XmlJavaTypeAdapter.class, m, this);
/* 261 */       if (adapter != null) {
/* 262 */         a = new Adapter(adapter, reader(), nav());
/*     */       } else {
/* 264 */         XmlAttachmentRef xsa = (XmlAttachmentRef)reader().getMethodAnnotation(XmlAttachmentRef.class, m, this);
/* 265 */         if (xsa != null) {
/* 266 */           TODO.prototype("in Annotation Processing swaRefAdapter isn't avaialble, so this returns null");
/* 267 */           a = new Adapter(this.owner.nav.asDecl(SwaRefAdapter.class), this.owner.nav);
/*     */         } 
/*     */       } 
/*     */     } 
/* 271 */     this.adapter = a;
/*     */ 
/*     */     
/* 274 */     this
/*     */       
/* 276 */       .tOfJAXBElementT = (methodParams.length > 0) ? methodParams[0] : (T)nav().getTypeArgument(baseClass, 0);
/*     */     
/* 278 */     if (this.adapter == null) {
/* 279 */       T list = (T)nav().getBaseClass(this.tOfJAXBElementT, nav().asDecl(List.class));
/* 280 */       if (list == null) {
/* 281 */         this.isCollection = false;
/* 282 */         this.contentType = builder.getTypeInfo(this.tOfJAXBElementT, this);
/*     */       } else {
/* 284 */         this.isCollection = true;
/* 285 */         this.contentType = builder.getTypeInfo((T)nav().getTypeArgument(list, 0), this);
/*     */       } 
/*     */     } else {
/*     */       
/* 289 */       this.contentType = builder.getTypeInfo((T)this.adapter.defaultType, this);
/* 290 */       this.isCollection = false;
/*     */     } 
/*     */ 
/*     */     
/* 294 */     T s = (T)reader().getClassValue((Annotation)this.anno, "scope");
/* 295 */     if (nav().isSameType(s, nav().ref(XmlElementDecl.GLOBAL.class))) {
/* 296 */       this.scope = null;
/*     */     } else {
/*     */       
/* 299 */       NonElement<T, C> scp = builder.getClassInfo((C)nav().asDecl(s), this);
/* 300 */       if (!(scp instanceof ClassInfo)) {
/* 301 */         throw new IllegalAnnotationException(Messages.SCOPE_IS_NOT_COMPLEXTYPE
/* 302 */             .format(new Object[] { nav().getTypeName(s) }, ), this.anno);
/*     */       }
/*     */       
/* 305 */       this.scope = (ClassInfo<T, C>)scp;
/*     */     } 
/*     */     
/* 308 */     this.id = calcId();
/*     */     
/* 310 */     this.property = createPropertyImpl();
/*     */     
/* 312 */     this.expectedMimeType = Util.calcExpectedMediaType(this.property, builder);
/* 313 */     this.inlineBinary = reader().hasMethodAnnotation(XmlInlineBinaryData.class, this.method);
/* 314 */     this.schemaType = Util.calcSchemaType(reader(), this.property, registry.registryClass, 
/* 315 */         getContentInMemoryType(), this);
/*     */   }
/*     */   
/*     */   final QName parseElementName(XmlElementDecl e) {
/* 319 */     String local = e.name();
/* 320 */     String nsUri = e.namespace();
/* 321 */     if (nsUri.equals("##default")) {
/*     */       
/* 323 */       XmlSchema xs = (XmlSchema)reader().getPackageAnnotation(XmlSchema.class, 
/* 324 */           nav().getDeclaringClassForMethod(this.method), this);
/* 325 */       if (xs != null) {
/* 326 */         nsUri = xs.namespace();
/*     */       } else {
/* 328 */         nsUri = this.builder.defaultNsUri;
/*     */       } 
/*     */     } 
/*     */     
/* 332 */     return new QName(nsUri.intern(), local.intern());
/*     */   }
/*     */   
/*     */   protected PropertyImpl createPropertyImpl() {
/* 336 */     return new PropertyImpl();
/*     */   }
/*     */   
/*     */   public ElementPropertyInfo<T, C> getProperty() {
/* 340 */     return this.property;
/*     */   }
/*     */   
/*     */   public NonElement<T, C> getContentType() {
/* 344 */     return this.contentType;
/*     */   }
/*     */   
/*     */   public T getContentInMemoryType() {
/* 348 */     if (this.adapter == null) {
/* 349 */       return this.tOfJAXBElementT;
/*     */     }
/* 351 */     return (T)this.adapter.customType;
/*     */   }
/*     */ 
/*     */   
/*     */   public QName getElementName() {
/* 356 */     return this.tagName;
/*     */   }
/*     */   
/*     */   public T getType() {
/* 360 */     return this.elementType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final boolean canBeReferencedByIDREF() {
/* 370 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   private ID calcId() {
/* 375 */     if (reader().hasMethodAnnotation(XmlID.class, this.method)) {
/* 376 */       return ID.ID;
/*     */     }
/* 378 */     if (reader().hasMethodAnnotation(XmlIDREF.class, this.method)) {
/* 379 */       return ID.IDREF;
/*     */     }
/* 381 */     return ID.NONE;
/*     */   }
/*     */ 
/*     */   
/*     */   public ClassInfo<T, C> getScope() {
/* 386 */     return this.scope;
/*     */   }
/*     */   
/*     */   public ElementInfo<T, C> getSubstitutionHead() {
/* 390 */     return this.substitutionHead;
/*     */   }
/*     */   
/*     */   public Collection<? extends ElementInfoImpl<T, C, F, M>> getSubstitutionMembers() {
/* 394 */     if (this.substitutionMembers == null) {
/* 395 */       return Collections.emptyList();
/*     */     }
/* 397 */     return (Collection<? extends ElementInfoImpl<T, C, F, M>>)this.substitutionMembers;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void link() {
/* 405 */     if (this.anno.substitutionHeadName().length() != 0)
/*     */     
/* 407 */     { QName name = new QName(this.anno.substitutionHeadNamespace(), this.anno.substitutionHeadName());
/* 408 */       this.substitutionHead = this.owner.getElementInfo((C)null, name);
/* 409 */       if (this.substitutionHead == null) {
/* 410 */         this.builder.reportError(new IllegalAnnotationException(Messages.NON_EXISTENT_ELEMENT_MAPPING
/* 411 */               .format(new Object[] {
/* 412 */                   name.getNamespaceURI(), name.getLocalPart()
/*     */                 }, ), (Annotation)this.anno));
/*     */       } else {
/* 415 */         this.substitutionHead.addSubstitutionMember(this);
/*     */       }  }
/* 417 */     else { this.substitutionHead = null; }
/* 418 */      super.link();
/*     */   }
/*     */   
/*     */   private void addSubstitutionMember(ElementInfoImpl<T, C, F, M> child) {
/* 422 */     if (this.substitutionMembers == null)
/* 423 */       this.substitutionMembers = new FinalArrayList(); 
/* 424 */     this.substitutionMembers.add(child);
/*     */   }
/*     */   
/*     */   public Location getLocation() {
/* 428 */     return nav().getMethodLocation(this.method);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/bind/v2/model/impl/ElementInfoImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */