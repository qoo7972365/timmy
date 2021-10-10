/*     */ package com.sun.xml.internal.bind.v2.runtime;
/*     */ 
/*     */ import com.sun.istack.internal.FinalArrayList;
/*     */ import com.sun.xml.internal.bind.Util;
/*     */ import com.sun.xml.internal.bind.api.AccessorException;
/*     */ import com.sun.xml.internal.bind.v2.ClassFactory;
/*     */ import com.sun.xml.internal.bind.v2.model.core.ID;
/*     */ import com.sun.xml.internal.bind.v2.model.runtime.RuntimeClassInfo;
/*     */ import com.sun.xml.internal.bind.v2.model.runtime.RuntimePropertyInfo;
/*     */ import com.sun.xml.internal.bind.v2.model.runtime.RuntimeTypeInfo;
/*     */ import com.sun.xml.internal.bind.v2.runtime.property.AttributeProperty;
/*     */ import com.sun.xml.internal.bind.v2.runtime.property.Property;
/*     */ import com.sun.xml.internal.bind.v2.runtime.property.PropertyFactory;
/*     */ import com.sun.xml.internal.bind.v2.runtime.reflect.Accessor;
/*     */ import com.sun.xml.internal.bind.v2.runtime.unmarshaller.Loader;
/*     */ import com.sun.xml.internal.bind.v2.runtime.unmarshaller.StructureLoader;
/*     */ import com.sun.xml.internal.bind.v2.runtime.unmarshaller.UnmarshallingContext;
/*     */ import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiTypeLoader;
/*     */ import java.io.IOException;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.lang.reflect.Method;
/*     */ import java.lang.reflect.Modifier;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.xml.bind.ValidationEvent;
/*     */ import javax.xml.bind.annotation.XmlRootElement;
/*     */ import javax.xml.bind.helpers.ValidationEventImpl;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import org.xml.sax.Locator;
/*     */ import org.xml.sax.SAXException;
/*     */ import org.xml.sax.helpers.LocatorImpl;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class ClassBeanInfoImpl<BeanT>
/*     */   extends JaxBeanInfo<BeanT>
/*     */   implements AttributeAccessor<BeanT>
/*     */ {
/*     */   public final Property<BeanT>[] properties;
/*     */   private Property<? super BeanT> idProperty;
/*     */   private Loader loader;
/*     */   private Loader loaderWithTypeSubst;
/*     */   private RuntimeClassInfo ci;
/*     */   private final Accessor<? super BeanT, Map<QName, String>> inheritedAttWildcard;
/*     */   private final Transducer<BeanT> xducer;
/*     */   public final ClassBeanInfoImpl<? super BeanT> superClazz;
/*     */   private final Accessor<? super BeanT, Locator> xmlLocatorField;
/*     */   private final Name tagName;
/*     */   private boolean retainPropertyInfo = false;
/*     */   private AttributeProperty<BeanT>[] attributeProperties;
/*     */   private Property<BeanT>[] uriProperties;
/*     */   private final Method factoryMethod;
/*     */   
/*     */   ClassBeanInfoImpl(JAXBContextImpl owner, RuntimeClassInfo ci) {
/* 125 */     super(owner, (RuntimeTypeInfo)ci, (Class<BeanT>)ci.getClazz(), ci.getTypeName(), ci.isElement(), false, true);
/*     */     
/* 127 */     this.ci = ci;
/* 128 */     this.inheritedAttWildcard = ci.getAttributeWildcard();
/* 129 */     this.xducer = ci.getTransducer();
/* 130 */     this.factoryMethod = ci.getFactoryMethod();
/* 131 */     this.retainPropertyInfo = owner.retainPropertyInfo;
/*     */ 
/*     */     
/* 134 */     if (this.factoryMethod != null) {
/* 135 */       int classMod = this.factoryMethod.getDeclaringClass().getModifiers();
/*     */       
/* 137 */       if (!Modifier.isPublic(classMod) || !Modifier.isPublic(this.factoryMethod.getModifiers())) {
/*     */         
/*     */         try {
/* 140 */           this.factoryMethod.setAccessible(true);
/* 141 */         } catch (SecurityException e) {
/*     */           
/* 143 */           logger.log(Level.FINE, "Unable to make the method of " + this.factoryMethod + " accessible", e);
/* 144 */           throw e;
/*     */         } 
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 150 */     if (ci.getBaseClass() == null) {
/* 151 */       this.superClazz = null;
/*     */     } else {
/* 153 */       this.superClazz = owner.getOrCreate(ci.getBaseClass());
/*     */     } 
/* 155 */     if (this.superClazz != null && this.superClazz.xmlLocatorField != null) {
/* 156 */       this.xmlLocatorField = this.superClazz.xmlLocatorField;
/*     */     } else {
/* 158 */       this.xmlLocatorField = ci.getLocatorField();
/*     */     } 
/*     */     
/* 161 */     Collection<? extends RuntimePropertyInfo> ps = ci.getProperties();
/* 162 */     this.properties = (Property<BeanT>[])new Property[ps.size()];
/* 163 */     int idx = 0;
/* 164 */     boolean elementOnly = true;
/* 165 */     for (RuntimePropertyInfo info : ps) {
/* 166 */       Property<? super BeanT> p = PropertyFactory.create(owner, info);
/* 167 */       if (info.id() == ID.ID)
/* 168 */         this.idProperty = p; 
/* 169 */       this.properties[idx++] = (Property)p;
/* 170 */       elementOnly &= info.elementOnlyContent();
/* 171 */       checkOverrideProperties(p);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 176 */     hasElementOnlyContentModel(elementOnly);
/*     */ 
/*     */     
/* 179 */     if (ci.isElement()) {
/* 180 */       this.tagName = owner.nameBuilder.createElementName(ci.getElementName());
/*     */     } else {
/* 182 */       this.tagName = null;
/*     */     } 
/* 184 */     setLifecycleFlags();
/*     */   }
/*     */   
/*     */   private void checkOverrideProperties(Property p) {
/* 188 */     ClassBeanInfoImpl<BeanT> bi = this; ClassBeanInfoImpl<? super BeanT> classBeanInfoImpl;
/* 189 */     while ((classBeanInfoImpl = bi.superClazz) != null) {
/* 190 */       Property<BeanT>[] arrayOfProperty = classBeanInfoImpl.properties;
/* 191 */       if (arrayOfProperty == null)
/* 192 */         break;  for (Property<BeanT> superProperty : arrayOfProperty) {
/* 193 */         if (superProperty != null) {
/* 194 */           String spName = superProperty.getFieldName();
/* 195 */           if (spName != null && spName.equals(p.getFieldName())) {
/* 196 */             superProperty.setHiddenByOverride(true);
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void link(JAXBContextImpl grammar) {
/* 205 */     if (this.uriProperties != null) {
/*     */       return;
/*     */     }
/* 208 */     super.link(grammar);
/*     */     
/* 210 */     if (this.superClazz != null) {
/* 211 */       this.superClazz.link(grammar);
/*     */     }
/* 213 */     getLoader(grammar, true);
/*     */ 
/*     */     
/* 216 */     if (this.superClazz != null) {
/* 217 */       if (this.idProperty == null) {
/* 218 */         this.idProperty = this.superClazz.idProperty;
/*     */       }
/* 220 */       if (!this.superClazz.hasElementOnlyContentModel()) {
/* 221 */         hasElementOnlyContentModel(false);
/*     */       }
/*     */     } 
/*     */     
/* 225 */     FinalArrayList<AttributeProperty> finalArrayList = new FinalArrayList();
/* 226 */     FinalArrayList<Property<BeanT>> finalArrayList1 = new FinalArrayList();
/* 227 */     for (ClassBeanInfoImpl<BeanT> bi = this; bi != null; classBeanInfoImpl = bi.superClazz) {
/* 228 */       ClassBeanInfoImpl<? super BeanT> classBeanInfoImpl; for (int i = 0; i < bi.properties.length; i++) {
/* 229 */         Property<BeanT> p = bi.properties[i];
/* 230 */         if (p instanceof AttributeProperty)
/* 231 */           finalArrayList.add((AttributeProperty)p); 
/* 232 */         if (p.hasSerializeURIAction())
/* 233 */           finalArrayList1.add(p); 
/*     */       } 
/*     */     } 
/* 236 */     if (grammar.c14nSupport) {
/* 237 */       Collections.sort((List<AttributeProperty>)finalArrayList);
/*     */     }
/* 239 */     if (finalArrayList.isEmpty()) {
/* 240 */       this.attributeProperties = (AttributeProperty<BeanT>[])EMPTY_PROPERTIES;
/*     */     } else {
/* 242 */       this.attributeProperties = finalArrayList.<AttributeProperty<BeanT>>toArray((AttributeProperty<BeanT>[])new AttributeProperty[finalArrayList.size()]);
/*     */     } 
/* 244 */     if (finalArrayList1.isEmpty()) {
/* 245 */       this.uriProperties = (Property<BeanT>[])EMPTY_PROPERTIES;
/*     */     } else {
/* 247 */       this.uriProperties = finalArrayList1.<Property<BeanT>>toArray((Property<BeanT>[])new Property[finalArrayList1.size()]);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void wrapUp() {
/* 252 */     for (Property<BeanT> p : this.properties)
/* 253 */       p.wrapUp(); 
/* 254 */     this.ci = null;
/* 255 */     super.wrapUp();
/*     */   }
/*     */   
/*     */   public String getElementNamespaceURI(BeanT bean) {
/* 259 */     return this.tagName.nsUri;
/*     */   }
/*     */   
/*     */   public String getElementLocalName(BeanT bean) {
/* 263 */     return this.tagName.localName;
/*     */   }
/*     */ 
/*     */   
/*     */   public BeanT createInstance(UnmarshallingContext context) throws IllegalAccessException, InvocationTargetException, InstantiationException, SAXException {
/* 268 */     BeanT bean = null;
/* 269 */     if (this.factoryMethod == null) {
/* 270 */       bean = (BeanT)ClassFactory.create0(this.jaxbType);
/*     */     } else {
/* 272 */       Object o = ClassFactory.create(this.factoryMethod);
/* 273 */       if (this.jaxbType.isInstance(o)) {
/* 274 */         bean = (BeanT)o;
/*     */       } else {
/* 276 */         throw new InstantiationException("The factory method didn't return a correct object");
/*     */       } 
/*     */     } 
/*     */     
/* 280 */     if (this.xmlLocatorField != null)
/*     */       
/*     */       try {
/* 283 */         this.xmlLocatorField.set(bean, new LocatorImpl((Locator)context.getLocator()));
/* 284 */       } catch (AccessorException e) {
/* 285 */         context.handleError((Exception)e);
/*     */       }  
/* 287 */     return bean;
/*     */   }
/*     */   
/*     */   public boolean reset(BeanT bean, UnmarshallingContext context) throws SAXException {
/*     */     try {
/* 292 */       if (this.superClazz != null)
/* 293 */         this.superClazz.reset(bean, context); 
/* 294 */       for (Property<BeanT> p : this.properties)
/* 295 */         p.reset(bean); 
/* 296 */       return true;
/* 297 */     } catch (AccessorException e) {
/* 298 */       context.handleError((Exception)e);
/* 299 */       return false;
/*     */     } 
/*     */   }
/*     */   
/*     */   public String getId(BeanT bean, XMLSerializer target) throws SAXException {
/* 304 */     if (this.idProperty != null) {
/*     */       try {
/* 306 */         return this.idProperty.getIdValue(bean);
/* 307 */       } catch (AccessorException e) {
/* 308 */         target.reportError(null, (Throwable)e);
/*     */       } 
/*     */     }
/* 311 */     return null;
/*     */   }
/*     */   
/*     */   public void serializeRoot(BeanT bean, XMLSerializer target) throws SAXException, IOException, XMLStreamException {
/* 315 */     if (this.tagName == null) {
/* 316 */       String message; Class<?> beanClass = bean.getClass();
/*     */       
/* 318 */       if (beanClass.isAnnotationPresent((Class)XmlRootElement.class)) {
/* 319 */         message = Messages.UNABLE_TO_MARSHAL_UNBOUND_CLASS.format(new Object[] { beanClass.getName() });
/*     */       } else {
/* 321 */         message = Messages.UNABLE_TO_MARSHAL_NON_ELEMENT.format(new Object[] { beanClass.getName() });
/*     */       } 
/* 323 */       target.reportError((ValidationEvent)new ValidationEventImpl(1, message, null, null));
/*     */     } else {
/* 325 */       target.startElement(this.tagName, bean);
/* 326 */       target.childAsSoleContent(bean, null);
/* 327 */       target.endElement();
/* 328 */       if (this.retainPropertyInfo) {
/* 329 */         target.currentProperty.remove();
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public void serializeBody(BeanT bean, XMLSerializer target) throws SAXException, IOException, XMLStreamException {
/* 335 */     if (this.superClazz != null) {
/* 336 */       this.superClazz.serializeBody(bean, target);
/*     */     }
/*     */     try {
/* 339 */       for (Property<BeanT> p : this.properties) {
/* 340 */         if (this.retainPropertyInfo) {
/* 341 */           target.currentProperty.set(p);
/*     */         }
/* 343 */         boolean isThereAnOverridingProperty = p.isHiddenByOverride();
/* 344 */         if (!isThereAnOverridingProperty || bean.getClass().equals(this.jaxbType)) {
/* 345 */           p.serializeBody(bean, target, null);
/* 346 */         } else if (isThereAnOverridingProperty) {
/*     */           
/* 348 */           Class<?> beanClass = bean.getClass();
/* 349 */           if (Utils.REFLECTION_NAVIGATOR.getDeclaredField(beanClass, p.getFieldName()) == null) {
/* 350 */             p.serializeBody(bean, target, null);
/*     */           }
/*     */         } 
/*     */       } 
/* 354 */     } catch (AccessorException e) {
/* 355 */       target.reportError(null, (Throwable)e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void serializeAttributes(BeanT bean, XMLSerializer target) throws SAXException, IOException, XMLStreamException {
/* 360 */     for (AttributeProperty<BeanT> p : this.attributeProperties) {
/*     */       try {
/* 362 */         if (this.retainPropertyInfo) {
/* 363 */           Property parentProperty = target.getCurrentProperty();
/* 364 */           target.currentProperty.set(p);
/* 365 */           p.serializeAttributes(bean, target);
/* 366 */           target.currentProperty.set(parentProperty);
/*     */         } else {
/* 368 */           p.serializeAttributes(bean, target);
/*     */         } 
/* 370 */         if (p.attName.equals("http://www.w3.org/2001/XMLSchema-instance", "nil")) {
/* 371 */           this.isNilIncluded = true;
/*     */         }
/* 373 */       } catch (AccessorException e) {
/* 374 */         target.reportError(null, (Throwable)e);
/*     */       } 
/*     */     } 
/*     */     try {
/* 378 */       if (this.inheritedAttWildcard != null) {
/* 379 */         Map<QName, String> map = (Map<QName, String>)this.inheritedAttWildcard.get(bean);
/* 380 */         target.attWildcardAsAttributes(map, null);
/*     */       } 
/* 382 */     } catch (AccessorException e) {
/* 383 */       target.reportError(null, (Throwable)e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void serializeURIs(BeanT bean, XMLSerializer target) throws SAXException {
/*     */     try {
/* 389 */       if (this.retainPropertyInfo) {
/* 390 */         Property parentProperty = target.getCurrentProperty();
/* 391 */         for (Property<BeanT> p : this.uriProperties) {
/* 392 */           target.currentProperty.set(p);
/* 393 */           p.serializeURIs(bean, target);
/*     */         } 
/* 395 */         target.currentProperty.set(parentProperty);
/*     */       } else {
/* 397 */         for (Property<BeanT> p : this.uriProperties) {
/* 398 */           p.serializeURIs(bean, target);
/*     */         }
/*     */       } 
/* 401 */       if (this.inheritedAttWildcard != null) {
/* 402 */         Map<QName, String> map = (Map<QName, String>)this.inheritedAttWildcard.get(bean);
/* 403 */         target.attWildcardAsURIs(map, null);
/*     */       } 
/* 405 */     } catch (AccessorException e) {
/* 406 */       target.reportError(null, (Throwable)e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public Loader getLoader(JAXBContextImpl context, boolean typeSubstitutionCapable) {
/* 411 */     if (this.loader == null) {
/*     */ 
/*     */       
/* 414 */       StructureLoader sl = new StructureLoader(this);
/* 415 */       this.loader = (Loader)sl;
/* 416 */       if (this.ci.hasSubClasses()) {
/* 417 */         this.loaderWithTypeSubst = (Loader)new XsiTypeLoader(this);
/*     */       } else {
/*     */         
/* 420 */         this.loaderWithTypeSubst = this.loader;
/*     */       } 
/*     */       
/* 423 */       sl.init(context, this, this.ci.getAttributeWildcard());
/*     */     } 
/* 425 */     if (typeSubstitutionCapable) {
/* 426 */       return this.loaderWithTypeSubst;
/*     */     }
/* 428 */     return this.loader;
/*     */   }
/*     */   
/*     */   public Transducer<BeanT> getTransducer() {
/* 432 */     return this.xducer;
/*     */   }
/*     */   
/* 435 */   private static final AttributeProperty[] EMPTY_PROPERTIES = new AttributeProperty[0];
/*     */   
/* 437 */   private static final Logger logger = Util.getClassLogger();
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/bind/v2/runtime/ClassBeanInfoImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */