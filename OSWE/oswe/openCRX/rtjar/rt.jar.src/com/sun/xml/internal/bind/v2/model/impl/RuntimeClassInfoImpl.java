/*     */ package com.sun.xml.internal.bind.v2.model.impl;
/*     */ 
/*     */ import com.sun.istack.internal.NotNull;
/*     */ import com.sun.xml.internal.bind.AccessorFactory;
/*     */ import com.sun.xml.internal.bind.AccessorFactoryImpl;
/*     */ import com.sun.xml.internal.bind.InternalAccessorFactory;
/*     */ import com.sun.xml.internal.bind.XmlAccessorFactory;
/*     */ import com.sun.xml.internal.bind.annotation.XmlLocation;
/*     */ import com.sun.xml.internal.bind.api.AccessorException;
/*     */ import com.sun.xml.internal.bind.v2.ClassFactory;
/*     */ import com.sun.xml.internal.bind.v2.model.annotation.Locatable;
/*     */ import com.sun.xml.internal.bind.v2.model.core.ClassInfo;
/*     */ import com.sun.xml.internal.bind.v2.model.core.PropertyInfo;
/*     */ import com.sun.xml.internal.bind.v2.model.core.PropertyKind;
/*     */ import com.sun.xml.internal.bind.v2.model.runtime.RuntimeClassInfo;
/*     */ import com.sun.xml.internal.bind.v2.model.runtime.RuntimeElement;
/*     */ import com.sun.xml.internal.bind.v2.model.runtime.RuntimeNonElementRef;
/*     */ import com.sun.xml.internal.bind.v2.model.runtime.RuntimePropertyInfo;
/*     */ import com.sun.xml.internal.bind.v2.model.runtime.RuntimeValuePropertyInfo;
/*     */ import com.sun.xml.internal.bind.v2.runtime.IllegalAnnotationException;
/*     */ import com.sun.xml.internal.bind.v2.runtime.JAXBContextImpl;
/*     */ import com.sun.xml.internal.bind.v2.runtime.Location;
/*     */ import com.sun.xml.internal.bind.v2.runtime.Name;
/*     */ import com.sun.xml.internal.bind.v2.runtime.Transducer;
/*     */ import com.sun.xml.internal.bind.v2.runtime.XMLSerializer;
/*     */ import com.sun.xml.internal.bind.v2.runtime.reflect.Accessor;
/*     */ import com.sun.xml.internal.bind.v2.runtime.reflect.TransducedAccessor;
/*     */ import com.sun.xml.internal.bind.v2.runtime.unmarshaller.UnmarshallingContext;
/*     */ import java.io.IOException;
/*     */ import java.lang.annotation.Annotation;
/*     */ import java.lang.reflect.Field;
/*     */ import java.lang.reflect.Method;
/*     */ import java.lang.reflect.Modifier;
/*     */ import java.lang.reflect.Type;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import javax.xml.bind.JAXBException;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import org.xml.sax.Locator;
/*     */ import org.xml.sax.SAXException;
/*     */ 
/*     */ class RuntimeClassInfoImpl
/*     */   extends ClassInfoImpl<Type, Class, Field, Method> implements RuntimeClassInfo, RuntimeElement {
/*     */   private Accessor<?, Locator> xmlLocationAccessor;
/*     */   private AccessorFactory accessorFactory;
/*     */   private boolean supressAccessorWarnings = false;
/*     */   private Accessor<?, Map<QName, String>> attributeWildcardAccessor;
/*     */   private boolean computedTransducer;
/*     */   private Transducer xducer;
/*     */   
/*     */   protected AccessorFactory createAccessorFactory(Class clazz) {
/*     */     AccessorFactoryImpl accessorFactoryImpl;
/*     */     AccessorFactory accFactory = null;
/*     */     JAXBContextImpl context = ((RuntimeModelBuilder)this.builder).context;
/*     */     if (context != null) {
/*     */       this.supressAccessorWarnings = context.supressAccessorWarnings;
/*     */       if (context.xmlAccessorFactorySupport) {
/*     */         XmlAccessorFactory factoryAnn = findXmlAccessorFactoryAnnotation(clazz);
/*     */         if (factoryAnn != null)
/*     */           try {
/*     */             accFactory = factoryAnn.value().newInstance();
/*     */           } catch (InstantiationException e) {
/*     */             this.builder.reportError(new IllegalAnnotationException(Messages.ACCESSORFACTORY_INSTANTIATION_EXCEPTION.format(new Object[] { factoryAnn.getClass().getName(), nav().getClassName(clazz) }, ), this));
/*     */           } catch (IllegalAccessException e) {
/*     */             this.builder.reportError(new IllegalAnnotationException(Messages.ACCESSORFACTORY_ACCESS_EXCEPTION.format(new Object[] { factoryAnn.getClass().getName(), nav().getClassName(clazz) }, ), this));
/*     */           }  
/*     */       } 
/*     */     } 
/*     */     if (accFactory == null)
/*     */       accessorFactoryImpl = AccessorFactoryImpl.getInstance(); 
/*     */     return (AccessorFactory)accessorFactoryImpl;
/*     */   }
/*     */   
/*     */   protected XmlAccessorFactory findXmlAccessorFactoryAnnotation(Class clazz) {
/*     */     XmlAccessorFactory factoryAnn = (XmlAccessorFactory)reader().getClassAnnotation(XmlAccessorFactory.class, clazz, this);
/*     */     if (factoryAnn == null)
/*     */       factoryAnn = (XmlAccessorFactory)reader().getPackageAnnotation(XmlAccessorFactory.class, clazz, this); 
/*     */     return factoryAnn;
/*     */   }
/*     */   
/*     */   public Method getFactoryMethod() {
/*     */     return super.getFactoryMethod();
/*     */   }
/*     */   
/*     */   public RuntimeClassInfoImpl(RuntimeModelBuilder modelBuilder, Locatable upstream, Class clazz) {
/*  87 */     super(modelBuilder, upstream, clazz);
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
/* 198 */     this.computedTransducer = false;
/* 199 */     this.xducer = null; this.accessorFactory = createAccessorFactory(clazz); } public final RuntimeClassInfoImpl getBaseClass() { return (RuntimeClassInfoImpl)super.getBaseClass(); }
/*     */   protected ReferencePropertyInfoImpl createReferenceProperty(PropertySeed<Type, Class<?>, Field, Method> seed) { return new RuntimeReferencePropertyInfoImpl(this, seed); }
/*     */   protected AttributePropertyInfoImpl createAttributeProperty(PropertySeed<Type, Class<?>, Field, Method> seed) { return new RuntimeAttributePropertyInfoImpl(this, seed); }
/* 202 */   public Transducer getTransducer() { if (!this.computedTransducer) {
/* 203 */       this.computedTransducer = true;
/* 204 */       this.xducer = calcTransducer();
/*     */     } 
/* 206 */     return this.xducer; } protected ValuePropertyInfoImpl createValueProperty(PropertySeed<Type, Class<?>, Field, Method> seed) {
/*     */     return new RuntimeValuePropertyInfoImpl(this, seed);
/*     */   } protected ElementPropertyInfoImpl createElementProperty(PropertySeed<Type, Class<?>, Field, Method> seed) {
/*     */     return new RuntimeElementPropertyInfoImpl(this, seed);
/*     */   } protected MapPropertyInfoImpl createMapProperty(PropertySeed<Type, Class<?>, Field, Method> seed) {
/*     */     return new RuntimeMapPropertyInfoImpl(this, seed);
/*     */   }
/* 213 */   private Transducer calcTransducer() { RuntimeValuePropertyInfo valuep = null;
/* 214 */     if (hasAttributeWildcard())
/* 215 */       return null; 
/* 216 */     for (RuntimeClassInfoImpl ci = this; ci != null; ci = ci.getBaseClass()) {
/* 217 */       for (RuntimePropertyInfo pi : ci.getProperties()) {
/* 218 */         if (pi.kind() == PropertyKind.VALUE) {
/* 219 */           valuep = (RuntimeValuePropertyInfo)pi;
/*     */           continue;
/*     */         } 
/* 222 */         return null;
/*     */       } 
/*     */     } 
/* 225 */     if (valuep == null)
/* 226 */       return null; 
/* 227 */     if (!valuep.getTarget().isSimpleType()) {
/* 228 */       return null;
/*     */     }
/* 230 */     return new TransducerImpl(getClazz(), TransducedAccessor.get(((RuntimeModelBuilder)this.builder).context, (RuntimeNonElementRef)valuep)); }
/*     */   
/*     */   public List<? extends RuntimePropertyInfo> getProperties() {
/*     */     return (List)super.getProperties();
/*     */   }
/*     */   public RuntimePropertyInfo getProperty(String name) {
/*     */     return (RuntimePropertyInfo)super.getProperty(name);
/*     */   }
/* 238 */   private Accessor<?, Map<QName, String>> createAttributeWildcardAccessor() { assert this.attributeWildcard != null;
/* 239 */     return ((RuntimePropertySeed)this.attributeWildcard).getAccessor(); }
/*     */   public void link() { getTransducer(); super.link(); }
/*     */   public <B> Accessor<B, Map<QName, String>> getAttributeWildcard() { for (RuntimeClassInfoImpl c = this; c != null; c = c.getBaseClass()) { if (c.attributeWildcard != null) { if (c.attributeWildcardAccessor == null)
/*     */           c.attributeWildcardAccessor = c.createAttributeWildcardAccessor();  return (Accessor)c.attributeWildcardAccessor; }
/*     */        }
/* 244 */      return null; } protected RuntimePropertySeed createFieldSeed(Field field) { Accessor acc; boolean readOnly = Modifier.isStatic(field.getModifiers());
/*     */     
/*     */     try {
/* 247 */       if (this.supressAccessorWarnings) {
/* 248 */         acc = ((InternalAccessorFactory)this.accessorFactory).createFieldAccessor(this.clazz, field, readOnly, this.supressAccessorWarnings);
/*     */       } else {
/* 250 */         acc = this.accessorFactory.createFieldAccessor(this.clazz, field, readOnly);
/*     */       } 
/* 252 */     } catch (JAXBException e) {
/* 253 */       this.builder.reportError(new IllegalAnnotationException(Messages.CUSTOM_ACCESSORFACTORY_FIELD_ERROR
/* 254 */             .format(new Object[] {
/* 255 */                 nav().getClassName(this.clazz), e.toString() }, ), this));
/* 256 */       acc = Accessor.getErrorInstance();
/*     */     } 
/* 258 */     return new RuntimePropertySeed(super.createFieldSeed(field), acc); }
/*     */ 
/*     */ 
/*     */   
/*     */   public RuntimePropertySeed createAccessorSeed(Method getter, Method setter) {
/*     */     Accessor acc;
/*     */     try {
/* 265 */       acc = this.accessorFactory.createPropertyAccessor(this.clazz, getter, setter);
/* 266 */     } catch (JAXBException e) {
/* 267 */       this.builder.reportError(new IllegalAnnotationException(Messages.CUSTOM_ACCESSORFACTORY_PROPERTY_ERROR
/* 268 */             .format(new Object[] {
/* 269 */                 nav().getClassName(this.clazz), e.toString() }, ), this));
/* 270 */       acc = Accessor.getErrorInstance();
/*     */     } 
/* 272 */     return new RuntimePropertySeed(super.createAccessorSeed(getter, setter), acc);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void checkFieldXmlLocation(Field f) {
/* 278 */     if (reader().hasFieldAnnotation(XmlLocation.class, f))
/*     */     {
/*     */       
/* 281 */       this.xmlLocationAccessor = (Accessor<?, Locator>)new Accessor.FieldReflection(f); } 
/*     */   }
/*     */   
/*     */   public Accessor<?, Locator> getLocatorField() {
/* 285 */     return this.xmlLocationAccessor;
/*     */   }
/*     */ 
/*     */   
/*     */   static final class RuntimePropertySeed
/*     */     implements PropertySeed<Type, Class, Field, Method>
/*     */   {
/*     */     private final Accessor acc;
/*     */     
/*     */     private final PropertySeed<Type, Class, Field, Method> core;
/*     */     
/*     */     public RuntimePropertySeed(PropertySeed<Type, Class<?>, Field, Method> core, Accessor acc) {
/* 297 */       this.core = core;
/* 298 */       this.acc = acc;
/*     */     }
/*     */     
/*     */     public String getName() {
/* 302 */       return this.core.getName();
/*     */     }
/*     */     
/*     */     public <A extends Annotation> A readAnnotation(Class<A> annotationType) {
/* 306 */       return (A)this.core.readAnnotation(annotationType);
/*     */     }
/*     */     
/*     */     public boolean hasAnnotation(Class<? extends Annotation> annotationType) {
/* 310 */       return this.core.hasAnnotation(annotationType);
/*     */     }
/*     */     
/*     */     public Type getRawType() {
/* 314 */       return this.core.getRawType();
/*     */     }
/*     */     
/*     */     public Location getLocation() {
/* 318 */       return this.core.getLocation();
/*     */     }
/*     */     
/*     */     public Locatable getUpstream() {
/* 322 */       return this.core.getUpstream();
/*     */     }
/*     */     
/*     */     public Accessor getAccessor() {
/* 326 */       return this.acc;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static final class TransducerImpl<BeanT>
/*     */     implements Transducer<BeanT>
/*     */   {
/*     */     private final TransducedAccessor<BeanT> xacc;
/*     */ 
/*     */     
/*     */     private final Class<BeanT> ownerClass;
/*     */ 
/*     */     
/*     */     public TransducerImpl(Class<BeanT> ownerClass, TransducedAccessor<BeanT> xacc) {
/* 342 */       this.xacc = xacc;
/* 343 */       this.ownerClass = ownerClass;
/*     */     }
/*     */     
/*     */     public boolean useNamespace() {
/* 347 */       return this.xacc.useNamespace();
/*     */     }
/*     */     
/*     */     public boolean isDefault() {
/* 351 */       return false;
/*     */     }
/*     */     
/*     */     public void declareNamespace(BeanT bean, XMLSerializer w) throws AccessorException {
/*     */       try {
/* 356 */         this.xacc.declareNamespace(bean, w);
/* 357 */       } catch (SAXException e) {
/* 358 */         throw new AccessorException(e);
/*     */       } 
/*     */     }
/*     */     @NotNull
/*     */     public CharSequence print(BeanT o) throws AccessorException {
/*     */       try {
/* 364 */         CharSequence value = this.xacc.print(o);
/* 365 */         if (value == null)
/* 366 */           throw new AccessorException(Messages.THERE_MUST_BE_VALUE_IN_XMLVALUE.format(new Object[] { o })); 
/* 367 */         return value;
/* 368 */       } catch (SAXException e) {
/* 369 */         throw new AccessorException(e);
/*     */       } 
/*     */     }
/*     */     public BeanT parse(CharSequence lexical) throws AccessorException, SAXException {
/*     */       BeanT inst;
/* 374 */       UnmarshallingContext ctxt = UnmarshallingContext.getInstance();
/*     */       
/* 376 */       if (ctxt != null) {
/* 377 */         inst = (BeanT)ctxt.createInstance(this.ownerClass);
/*     */       }
/*     */       else {
/*     */         
/* 381 */         inst = (BeanT)ClassFactory.create(this.ownerClass);
/*     */       } 
/* 383 */       this.xacc.parse(inst, lexical);
/* 384 */       return inst;
/*     */     }
/*     */     
/*     */     public void writeText(XMLSerializer w, BeanT o, String fieldName) throws IOException, SAXException, XMLStreamException, AccessorException {
/* 388 */       if (!this.xacc.hasValue(o))
/* 389 */         throw new AccessorException(Messages.THERE_MUST_BE_VALUE_IN_XMLVALUE.format(new Object[] { o })); 
/* 390 */       this.xacc.writeText(w, o, fieldName);
/*     */     }
/*     */     
/*     */     public void writeLeafElement(XMLSerializer w, Name tagName, BeanT o, String fieldName) throws IOException, SAXException, XMLStreamException, AccessorException {
/* 394 */       if (!this.xacc.hasValue(o))
/* 395 */         throw new AccessorException(Messages.THERE_MUST_BE_VALUE_IN_XMLVALUE.format(new Object[] { o })); 
/* 396 */       this.xacc.writeLeafElement(w, tagName, o, fieldName);
/*     */     }
/*     */     
/*     */     public QName getTypeName(BeanT instance) {
/* 400 */       return null;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/bind/v2/model/impl/RuntimeClassInfoImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */