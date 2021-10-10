/*     */ package com.sun.xml.internal.bind.v2.model.impl;
/*     */ 
/*     */ import com.sun.xml.internal.bind.v2.model.annotation.AnnotationReader;
/*     */ import com.sun.xml.internal.bind.v2.model.core.BuiltinLeafInfo;
/*     */ import com.sun.xml.internal.bind.v2.model.core.ElementInfo;
/*     */ import com.sun.xml.internal.bind.v2.model.core.LeafInfo;
/*     */ import com.sun.xml.internal.bind.v2.model.core.NonElement;
/*     */ import com.sun.xml.internal.bind.v2.model.core.Ref;
/*     */ import com.sun.xml.internal.bind.v2.model.core.TypeInfoSet;
/*     */ import com.sun.xml.internal.bind.v2.model.nav.Navigator;
/*     */ import com.sun.xml.internal.bind.v2.runtime.IllegalAnnotationException;
/*     */ import com.sun.xml.internal.bind.v2.runtime.RuntimeUtil;
/*     */ import com.sun.xml.internal.bind.v2.util.FlattenIterator;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.Map;
/*     */ import javax.xml.bind.JAXBContext;
/*     */ import javax.xml.bind.JAXBException;
/*     */ import javax.xml.bind.Marshaller;
/*     */ import javax.xml.bind.annotation.XmlNs;
/*     */ import javax.xml.bind.annotation.XmlNsForm;
/*     */ import javax.xml.bind.annotation.XmlRegistry;
/*     */ import javax.xml.bind.annotation.XmlSchema;
/*     */ import javax.xml.bind.annotation.XmlTransient;
/*     */ import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.transform.Result;
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
/*     */ class TypeInfoSetImpl<T, C, F, M>
/*     */   implements TypeInfoSet<T, C, F, M>
/*     */ {
/*     */   @XmlTransient
/*     */   public final Navigator<T, C, F, M> nav;
/*     */   @XmlTransient
/*     */   public final AnnotationReader<T, C, F, M> reader;
/*  81 */   private final Map<T, BuiltinLeafInfo<T, C>> builtins = new LinkedHashMap<>();
/*     */ 
/*     */ 
/*     */   
/*  85 */   private final Map<C, EnumLeafInfoImpl<T, C, F, M>> enums = new LinkedHashMap<>();
/*     */ 
/*     */ 
/*     */   
/*  89 */   private final Map<T, ArrayInfoImpl<T, C, F, M>> arrays = new LinkedHashMap<>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlJavaTypeAdapter(RuntimeUtil.ToStringAdapter.class)
/* 100 */   private final Map<C, ClassInfoImpl<T, C, F, M>> beans = new LinkedHashMap<>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlTransient
/* 106 */   private final Map<C, ClassInfoImpl<T, C, F, M>> beansView = Collections.unmodifiableMap(this.beans);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 111 */   private final Map<C, Map<QName, ElementInfoImpl<T, C, F, M>>> elementMappings = new LinkedHashMap<>();
/*     */ 
/*     */   
/* 114 */   private final Iterable<? extends ElementInfoImpl<T, C, F, M>> allElements = (Iterable)new Iterable<ElementInfoImpl<ElementInfoImpl<T, C, F, M>, C, F, M>>()
/*     */     {
/*     */       public Iterator<ElementInfoImpl<T, C, F, M>> iterator() {
/* 117 */         return (Iterator<ElementInfoImpl<T, C, F, M>>)new FlattenIterator(TypeInfoSetImpl.this.elementMappings.values());
/*     */       }
/*     */     };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final NonElement<T, C> anyType;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Map<String, Map<String, String>> xmlNsCache;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TypeInfoSetImpl(Navigator<T, C, F, M> nav, AnnotationReader<T, C, F, M> reader, Map<T, ? extends BuiltinLeafInfoImpl<T, C>> leaves) {
/* 139 */     this.nav = nav;
/* 140 */     this.reader = reader;
/* 141 */     this.builtins.putAll((Map)leaves);
/*     */     
/* 143 */     this.anyType = createAnyType();
/*     */ 
/*     */     
/* 146 */     for (Map.Entry<Class<?>, Class<?>> e : (Iterable<Map.Entry<Class<?>, Class<?>>>)RuntimeUtil.primitiveToBox.entrySet()) {
/* 147 */       this.builtins.put((T)nav.getPrimitive(e.getKey()), leaves.get(nav.ref(e.getValue())));
/*     */     }
/*     */ 
/*     */     
/* 151 */     this.elementMappings.put(null, new LinkedHashMap<>());
/*     */   }
/*     */   
/*     */   protected NonElement<T, C> createAnyType() {
/* 155 */     return new AnyTypeImpl<>(this.nav);
/*     */   }
/*     */   
/*     */   public Navigator<T, C, F, M> getNavigator() {
/* 159 */     return this.nav;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void add(ClassInfoImpl<T, C, F, M> ci) {
/* 166 */     this.beans.put(ci.getClazz(), ci);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void add(EnumLeafInfoImpl<T, C, F, M> li) {
/* 173 */     this.enums.put(li.clazz, li);
/*     */   }
/*     */   
/*     */   public void add(ArrayInfoImpl<T, C, F, M> ai) {
/* 177 */     this.arrays.put(ai.getType(), ai);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NonElement<T, C> getTypeInfo(T type) {
/* 188 */     type = (T)this.nav.erasure(type);
/*     */     
/* 190 */     LeafInfo<T, C> l = (LeafInfo<T, C>)this.builtins.get(type);
/* 191 */     if (l != null) return (NonElement<T, C>)l;
/*     */     
/* 193 */     if (this.nav.isArray(type)) {
/* 194 */       return (NonElement<T, C>)this.arrays.get(type);
/*     */     }
/*     */     
/* 197 */     C d = (C)this.nav.asDecl(type);
/* 198 */     if (d == null) return null; 
/* 199 */     return getClassInfo(d);
/*     */   }
/*     */   
/*     */   public NonElement<T, C> getAnyTypeInfo() {
/* 203 */     return this.anyType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NonElement<T, C> getTypeInfo(Ref<T, C> ref) {
/* 211 */     assert !ref.valueList;
/* 212 */     C c = (C)this.nav.asDecl(ref.type);
/* 213 */     if (c != null && this.reader.getClassAnnotation(XmlRegistry.class, c, null) != null) {
/* 214 */       return null;
/*     */     }
/* 216 */     return getTypeInfo((T)ref.type);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Map<C, ? extends ClassInfoImpl<T, C, F, M>> beans() {
/* 223 */     return this.beansView;
/*     */   }
/*     */   
/*     */   public Map<T, ? extends BuiltinLeafInfo<T, C>> builtins() {
/* 227 */     return this.builtins;
/*     */   }
/*     */   
/*     */   public Map<C, ? extends EnumLeafInfoImpl<T, C, F, M>> enums() {
/* 231 */     return this.enums;
/*     */   }
/*     */   
/*     */   public Map<? extends T, ? extends ArrayInfoImpl<T, C, F, M>> arrays() {
/* 235 */     return this.arrays;
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
/*     */   public NonElement<T, C> getClassInfo(C type) {
/* 250 */     LeafInfo<T, C> l = (LeafInfo<T, C>)this.builtins.get(this.nav.use(type));
/* 251 */     if (l != null) return (NonElement<T, C>)l;
/*     */     
/* 253 */     l = (LeafInfo<T, C>)this.enums.get(type);
/* 254 */     if (l != null) return (NonElement<T, C>)l;
/*     */     
/* 256 */     if (this.nav.asDecl(Object.class).equals(type)) {
/* 257 */       return this.anyType;
/*     */     }
/* 259 */     return (NonElement<T, C>)this.beans.get(type);
/*     */   }
/*     */   
/*     */   public ElementInfoImpl<T, C, F, M> getElementInfo(C scope, QName name) {
/* 263 */     while (scope != null) {
/* 264 */       Map<QName, ElementInfoImpl<T, C, F, M>> m = this.elementMappings.get(scope);
/* 265 */       if (m != null) {
/* 266 */         ElementInfoImpl<T, C, F, M> r = m.get(name);
/* 267 */         if (r != null) return r; 
/*     */       } 
/* 269 */       scope = (C)this.nav.getSuperClass(scope);
/*     */     } 
/* 271 */     return (ElementInfoImpl<T, C, F, M>)((Map)this.elementMappings.get(null)).get(name);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void add(ElementInfoImpl<T, C, F, M> ei, ModelBuilder<T, C, F, M> builder) {
/* 279 */     C scope = null;
/* 280 */     if (ei.getScope() != null) {
/* 281 */       scope = (C)ei.getScope().getClazz();
/*     */     }
/* 283 */     Map<QName, ElementInfoImpl<T, C, F, M>> m = this.elementMappings.get(scope);
/* 284 */     if (m == null) {
/* 285 */       this.elementMappings.put(scope, m = new LinkedHashMap<>());
/*     */     }
/* 287 */     ElementInfoImpl<T, C, F, M> existing = m.put(ei.getElementName(), ei);
/*     */     
/* 289 */     if (existing != null) {
/* 290 */       QName en = ei.getElementName();
/* 291 */       builder.reportError(new IllegalAnnotationException(Messages.CONFLICTING_XML_ELEMENT_MAPPING
/*     */             
/* 293 */             .format(new Object[] { en.getNamespaceURI(), en.getLocalPart() }, ), ei, existing));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public Map<QName, ? extends ElementInfoImpl<T, C, F, M>> getElementMappings(C scope) {
/* 299 */     return this.elementMappings.get(scope);
/*     */   }
/*     */   
/*     */   public Iterable<? extends ElementInfoImpl<T, C, F, M>> getAllElements() {
/* 303 */     return this.allElements;
/*     */   }
/*     */   
/*     */   public Map<String, String> getXmlNs(String namespaceUri) {
/* 307 */     if (this.xmlNsCache == null) {
/* 308 */       this.xmlNsCache = new HashMap<>();
/*     */       
/* 310 */       for (ClassInfoImpl<T, C, F, M> ci : beans().values()) {
/* 311 */         XmlSchema xs = (XmlSchema)this.reader.getPackageAnnotation(XmlSchema.class, ci.getClazz(), null);
/* 312 */         if (xs == null) {
/*     */           continue;
/*     */         }
/* 315 */         String uri = xs.namespace();
/* 316 */         Map<String, String> m = this.xmlNsCache.get(uri);
/* 317 */         if (m == null) {
/* 318 */           this.xmlNsCache.put(uri, m = new HashMap<>());
/*     */         }
/* 320 */         for (XmlNs xns : xs.xmlns()) {
/* 321 */           m.put(xns.prefix(), xns.namespaceURI());
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 326 */     Map<String, String> r = this.xmlNsCache.get(namespaceUri);
/* 327 */     if (r != null) return r; 
/* 328 */     return Collections.emptyMap();
/*     */   }
/*     */   
/*     */   public Map<String, String> getSchemaLocations() {
/* 332 */     Map<String, String> r = new HashMap<>();
/* 333 */     for (ClassInfoImpl<T, C, F, M> ci : beans().values()) {
/* 334 */       XmlSchema xs = (XmlSchema)this.reader.getPackageAnnotation(XmlSchema.class, ci.getClazz(), null);
/* 335 */       if (xs == null) {
/*     */         continue;
/*     */       }
/* 338 */       String loc = xs.location();
/* 339 */       if (loc.equals("##generate")) {
/*     */         continue;
/*     */       }
/* 342 */       r.put(xs.namespace(), loc);
/*     */     } 
/* 344 */     return r;
/*     */   }
/*     */   
/*     */   public final XmlNsForm getElementFormDefault(String nsUri) {
/* 348 */     for (ClassInfoImpl<T, C, F, M> ci : beans().values()) {
/* 349 */       XmlSchema xs = (XmlSchema)this.reader.getPackageAnnotation(XmlSchema.class, ci.getClazz(), null);
/* 350 */       if (xs == null) {
/*     */         continue;
/*     */       }
/* 353 */       if (!xs.namespace().equals(nsUri)) {
/*     */         continue;
/*     */       }
/* 356 */       XmlNsForm xnf = xs.elementFormDefault();
/* 357 */       if (xnf != XmlNsForm.UNSET)
/* 358 */         return xnf; 
/*     */     } 
/* 360 */     return XmlNsForm.UNSET;
/*     */   }
/*     */   
/*     */   public final XmlNsForm getAttributeFormDefault(String nsUri) {
/* 364 */     for (ClassInfoImpl<T, C, F, M> ci : beans().values()) {
/* 365 */       XmlSchema xs = (XmlSchema)this.reader.getPackageAnnotation(XmlSchema.class, ci.getClazz(), null);
/* 366 */       if (xs == null) {
/*     */         continue;
/*     */       }
/* 369 */       if (!xs.namespace().equals(nsUri)) {
/*     */         continue;
/*     */       }
/* 372 */       XmlNsForm xnf = xs.attributeFormDefault();
/* 373 */       if (xnf != XmlNsForm.UNSET)
/* 374 */         return xnf; 
/*     */     } 
/* 376 */     return XmlNsForm.UNSET;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void dump(Result out) throws JAXBException {
/* 387 */     JAXBContext context = JAXBContext.newInstance(new Class[] { getClass() });
/* 388 */     Marshaller m = context.createMarshaller();
/* 389 */     m.marshal(this, out);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/bind/v2/model/impl/TypeInfoSetImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */