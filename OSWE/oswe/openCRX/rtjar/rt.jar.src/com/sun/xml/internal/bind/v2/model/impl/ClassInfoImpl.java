/*      */ package com.sun.xml.internal.bind.v2.model.impl;
/*      */ 
/*      */ import com.sun.istack.internal.FinalArrayList;
/*      */ import com.sun.xml.internal.bind.annotation.OverrideAnnotationOf;
/*      */ import com.sun.xml.internal.bind.v2.model.annotation.Locatable;
/*      */ import com.sun.xml.internal.bind.v2.model.annotation.MethodLocatable;
/*      */ import com.sun.xml.internal.bind.v2.model.core.ClassInfo;
/*      */ import com.sun.xml.internal.bind.v2.model.core.Element;
/*      */ import com.sun.xml.internal.bind.v2.model.core.ID;
/*      */ import com.sun.xml.internal.bind.v2.model.core.NonElement;
/*      */ import com.sun.xml.internal.bind.v2.model.core.PropertyInfo;
/*      */ import com.sun.xml.internal.bind.v2.model.core.PropertyKind;
/*      */ import com.sun.xml.internal.bind.v2.runtime.IllegalAnnotationException;
/*      */ import com.sun.xml.internal.bind.v2.runtime.Location;
/*      */ import com.sun.xml.internal.bind.v2.util.EditDistance;
/*      */ import java.lang.annotation.Annotation;
/*      */ import java.lang.reflect.Method;
/*      */ import java.util.AbstractList;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Collection;
/*      */ import java.util.Collections;
/*      */ import java.util.Comparator;
/*      */ import java.util.HashMap;
/*      */ import java.util.HashSet;
/*      */ import java.util.LinkedHashMap;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Set;
/*      */ import java.util.TreeSet;
/*      */ import javax.xml.bind.annotation.XmlAccessOrder;
/*      */ import javax.xml.bind.annotation.XmlAccessType;
/*      */ import javax.xml.bind.annotation.XmlAccessorOrder;
/*      */ import javax.xml.bind.annotation.XmlAccessorType;
/*      */ import javax.xml.bind.annotation.XmlAnyAttribute;
/*      */ import javax.xml.bind.annotation.XmlAnyElement;
/*      */ import javax.xml.bind.annotation.XmlAttachmentRef;
/*      */ import javax.xml.bind.annotation.XmlAttribute;
/*      */ import javax.xml.bind.annotation.XmlElement;
/*      */ import javax.xml.bind.annotation.XmlElementRef;
/*      */ import javax.xml.bind.annotation.XmlElementRefs;
/*      */ import javax.xml.bind.annotation.XmlElementWrapper;
/*      */ import javax.xml.bind.annotation.XmlElements;
/*      */ import javax.xml.bind.annotation.XmlID;
/*      */ import javax.xml.bind.annotation.XmlIDREF;
/*      */ import javax.xml.bind.annotation.XmlInlineBinaryData;
/*      */ import javax.xml.bind.annotation.XmlList;
/*      */ import javax.xml.bind.annotation.XmlMimeType;
/*      */ import javax.xml.bind.annotation.XmlMixed;
/*      */ import javax.xml.bind.annotation.XmlSchemaType;
/*      */ import javax.xml.bind.annotation.XmlTransient;
/*      */ import javax.xml.bind.annotation.XmlType;
/*      */ import javax.xml.bind.annotation.XmlValue;
/*      */ import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
/*      */ import javax.xml.namespace.QName;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class ClassInfoImpl<T, C, F, M>
/*      */   extends TypeInfoImpl<T, C, F, M>
/*      */   implements ClassInfo<T, C>, Element<T, C>
/*      */ {
/*      */   protected final C clazz;
/*      */   private final QName elementName;
/*      */   private final QName typeName;
/*      */   private FinalArrayList<PropertyInfoImpl<T, C, F, M>> properties;
/*      */   private String[] propOrder;
/*      */   private ClassInfoImpl<T, C, F, M> baseClass;
/*      */   private boolean baseClassComputed = false;
/*      */   private boolean hasSubClasses = false;
/*      */   protected PropertySeed<T, C, F, M> attributeWildcard;
/*  146 */   private M factoryMethod = null;
/*      */   
/*      */   ClassInfoImpl(ModelBuilder<T, C, F, M> builder, Locatable upstream, C clazz) {
/*  149 */     super(builder, upstream);
/*  150 */     this.clazz = clazz;
/*  151 */     assert clazz != null;
/*      */ 
/*      */     
/*  154 */     this.elementName = parseElementName(clazz);
/*      */ 
/*      */     
/*  157 */     XmlType t = (XmlType)reader().getClassAnnotation(XmlType.class, clazz, this);
/*  158 */     this.typeName = parseTypeName(clazz, t);
/*      */     
/*  160 */     if (t != null) {
/*  161 */       String[] propOrder = t.propOrder();
/*  162 */       if (propOrder.length == 0) {
/*  163 */         this.propOrder = null;
/*      */       }
/*  165 */       else if (propOrder[0].length() == 0) {
/*  166 */         this.propOrder = DEFAULT_ORDER;
/*      */       } else {
/*  168 */         this.propOrder = propOrder;
/*      */       } 
/*      */     } else {
/*  171 */       this.propOrder = DEFAULT_ORDER;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  176 */     XmlAccessorOrder xao = (XmlAccessorOrder)reader().getPackageAnnotation(XmlAccessorOrder.class, clazz, this);
/*  177 */     if (xao != null && xao.value() == XmlAccessOrder.UNDEFINED) {
/*  178 */       this.propOrder = null;
/*      */     }
/*      */ 
/*      */     
/*  182 */     xao = (XmlAccessorOrder)reader().getClassAnnotation(XmlAccessorOrder.class, clazz, this);
/*  183 */     if (xao != null && xao.value() == XmlAccessOrder.UNDEFINED) {
/*  184 */       this.propOrder = null;
/*      */     }
/*      */     
/*  187 */     if (nav().isInterface(clazz)) {
/*  188 */       builder.reportError(new IllegalAnnotationException(Messages.CANT_HANDLE_INTERFACE
/*  189 */             .format(new Object[] { nav().getClassName(clazz) }, ), this));
/*      */     }
/*      */ 
/*      */     
/*  193 */     if (!hasFactoryConstructor(t) && 
/*  194 */       !nav().hasDefaultConstructor(clazz)) {
/*  195 */       if (nav().isInnerClass(clazz)) {
/*  196 */         builder.reportError(new IllegalAnnotationException(Messages.CANT_HANDLE_INNER_CLASS
/*  197 */               .format(new Object[] { nav().getClassName(clazz) }, ), this));
/*  198 */       } else if (this.elementName != null) {
/*  199 */         builder.reportError(new IllegalAnnotationException(Messages.NO_DEFAULT_CONSTRUCTOR
/*  200 */               .format(new Object[] { nav().getClassName(clazz) }, ), this));
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public ClassInfoImpl<T, C, F, M> getBaseClass() {
/*  207 */     if (!this.baseClassComputed) {
/*      */       
/*  209 */       C s = (C)nav().getSuperClass(this.clazz);
/*  210 */       if (s == null || s == nav().asDecl(Object.class)) {
/*  211 */         this.baseClass = null;
/*      */       } else {
/*  213 */         NonElement<T, C> b = this.builder.getClassInfo(s, true, this);
/*  214 */         if (b instanceof ClassInfoImpl) {
/*  215 */           this.baseClass = (ClassInfoImpl)b;
/*  216 */           this.baseClass.hasSubClasses = true;
/*      */         } else {
/*  218 */           this.baseClass = null;
/*      */         } 
/*      */       } 
/*  221 */       this.baseClassComputed = true;
/*      */     } 
/*  223 */     return this.baseClass;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final Element<T, C> getSubstitutionHead() {
/*  232 */     ClassInfoImpl<T, C, F, M> c = getBaseClass();
/*  233 */     while (c != null && !c.isElement())
/*  234 */       c = c.getBaseClass(); 
/*  235 */     return c;
/*      */   }
/*      */   
/*      */   public final C getClazz() {
/*  239 */     return this.clazz;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ClassInfoImpl<T, C, F, M> getScope() {
/*  250 */     return null;
/*      */   }
/*      */   
/*      */   public final T getType() {
/*  254 */     return (T)nav().use(this.clazz);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean canBeReferencedByIDREF() {
/*  262 */     for (PropertyInfo<T, C> p : getProperties()) {
/*  263 */       if (p.id() == ID.ID)
/*  264 */         return true; 
/*      */     } 
/*  266 */     ClassInfoImpl<T, C, F, M> base = getBaseClass();
/*  267 */     if (base != null) {
/*  268 */       return base.canBeReferencedByIDREF();
/*      */     }
/*  270 */     return false;
/*      */   }
/*      */   
/*      */   public final String getName() {
/*  274 */     return nav().getClassName(this.clazz);
/*      */   }
/*      */   
/*      */   public <A extends Annotation> A readAnnotation(Class<A> a) {
/*  278 */     return (A)reader().getClassAnnotation(a, this.clazz, this);
/*      */   }
/*      */   
/*      */   public Element<T, C> asElement() {
/*  282 */     if (isElement()) {
/*  283 */       return this;
/*      */     }
/*  285 */     return null;
/*      */   }
/*      */   
/*      */   public List<? extends PropertyInfo<T, C>> getProperties() {
/*  289 */     if (this.properties != null) return (List)this.properties;
/*      */ 
/*      */     
/*  292 */     XmlAccessType at = getAccessType();
/*      */     
/*  294 */     this.properties = new FinalArrayList();
/*      */     
/*  296 */     findFieldProperties(this.clazz, at);
/*      */     
/*  298 */     findGetterSetterProperties(at);
/*      */     
/*  300 */     if (this.propOrder == DEFAULT_ORDER || this.propOrder == null) {
/*  301 */       XmlAccessOrder ao = getAccessorOrder();
/*  302 */       if (ao == XmlAccessOrder.ALPHABETICAL) {
/*  303 */         Collections.sort((List<PropertyInfoImpl<T, C, F, M>>)this.properties);
/*      */       }
/*      */     } else {
/*  306 */       PropertySorter sorter = new PropertySorter();
/*  307 */       for (PropertyInfoImpl<T, C, F, M> p : this.properties) {
/*  308 */         sorter.checkedGet(p);
/*      */       }
/*  310 */       Collections.sort((List<PropertyInfoImpl<T, C, F, M>>)this.properties, sorter);
/*  311 */       sorter.checkUnusedProperties();
/*      */     } 
/*      */ 
/*      */     
/*  315 */     PropertyInfoImpl<T, C, F, M> vp = null;
/*  316 */     PropertyInfoImpl<T, C, F, M> ep = null;
/*      */     
/*  318 */     for (PropertyInfoImpl<T, C, F, M> p : this.properties) {
/*  319 */       switch (p.kind()) {
/*      */         case TRANSIENT:
/*      */         case ANY_ATTRIBUTE:
/*      */         case ATTRIBUTE:
/*  323 */           ep = p;
/*      */           continue;
/*      */         case VALUE:
/*  326 */           if (vp != null)
/*      */           {
/*  328 */             this.builder.reportError(new IllegalAnnotationException(Messages.MULTIPLE_VALUE_PROPERTY
/*  329 */                   .format(new Object[0]), vp, p));
/*      */           }
/*      */           
/*  332 */           if (getBaseClass() != null) {
/*  333 */             this.builder.reportError(new IllegalAnnotationException(Messages.XMLVALUE_IN_DERIVED_TYPE
/*  334 */                   .format(new Object[0]), p));
/*      */           }
/*  336 */           vp = p;
/*      */           continue;
/*      */         
/*      */         case ELEMENT:
/*      */           continue;
/*      */       } 
/*      */       
/*      */       assert false;
/*      */     } 
/*  345 */     if (ep != null && vp != null)
/*      */     {
/*  347 */       this.builder.reportError(new IllegalAnnotationException(Messages.ELEMENT_AND_VALUE_PROPERTY
/*  348 */             .format(new Object[0]), vp, ep));
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  354 */     return (List)this.properties;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void findFieldProperties(C c, XmlAccessType at) {
/*  360 */     C sc = (C)nav().getSuperClass(c);
/*  361 */     if (shouldRecurseSuperClass(sc)) {
/*  362 */       findFieldProperties(sc, at);
/*      */     }
/*      */     
/*  365 */     for (F f : nav().getDeclaredFields(c)) {
/*  366 */       Annotation[] annotations = reader().getAllFieldAnnotations(f, this);
/*  367 */       boolean isDummy = reader().hasFieldAnnotation(OverrideAnnotationOf.class, f);
/*      */       
/*  369 */       if (nav().isTransient(f)) {
/*      */         
/*  371 */         if (hasJAXBAnnotation(annotations))
/*  372 */           this.builder.reportError(new IllegalAnnotationException(Messages.TRANSIENT_FIELD_NOT_BINDABLE
/*  373 */                 .format(new Object[] { nav().getFieldName(f)
/*  374 */                   }, ), getSomeJAXBAnnotation(annotations)));  continue;
/*      */       } 
/*  376 */       if (nav().isStaticField(f)) {
/*      */         
/*  378 */         if (hasJAXBAnnotation(annotations))
/*  379 */           addProperty(createFieldSeed(f), annotations, false);  continue;
/*      */       } 
/*  381 */       if (at == XmlAccessType.FIELD || (at == XmlAccessType.PUBLIC_MEMBER && 
/*  382 */         nav().isPublicField(f)) || 
/*  383 */         hasJAXBAnnotation(annotations)) {
/*  384 */         if (isDummy) {
/*  385 */           ClassInfo<T, C> top = getBaseClass();
/*  386 */           while (top != null && top.getProperty("content") == null) {
/*  387 */             top = top.getBaseClass();
/*      */           }
/*  389 */           DummyPropertyInfo<T, C, F, M> prop = (DummyPropertyInfo)top.getProperty("content");
/*  390 */           PropertySeed<T, C, F, M> seed = createFieldSeed(f);
/*  391 */           prop.addType(createReferenceProperty(seed));
/*      */         } else {
/*  393 */           addProperty(createFieldSeed(f), annotations, false);
/*      */         } 
/*      */       }
/*  396 */       checkFieldXmlLocation(f);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public final boolean hasValueProperty() {
/*  402 */     ClassInfoImpl<T, C, F, M> bc = getBaseClass();
/*  403 */     if (bc != null && bc.hasValueProperty()) {
/*  404 */       return true;
/*      */     }
/*  406 */     for (PropertyInfo<T, C> p : getProperties()) {
/*  407 */       if (p instanceof com.sun.xml.internal.bind.v2.model.core.ValuePropertyInfo) return true;
/*      */     
/*      */     } 
/*  410 */     return false;
/*      */   }
/*      */   
/*      */   public PropertyInfo<T, C> getProperty(String name) {
/*  414 */     for (PropertyInfo<T, C> p : getProperties()) {
/*  415 */       if (p.getName().equals(name))
/*  416 */         return p; 
/*      */     } 
/*  418 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void checkFieldXmlLocation(F f) {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private <T extends Annotation> T getClassOrPackageAnnotation(Class<T> type) {
/*  431 */     Annotation annotation = reader().getClassAnnotation(type, this.clazz, this);
/*  432 */     if (annotation != null) {
/*  433 */       return (T)annotation;
/*      */     }
/*  435 */     return (T)reader().getPackageAnnotation(type, this.clazz, this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private XmlAccessType getAccessType() {
/*  443 */     XmlAccessorType xat = getClassOrPackageAnnotation(XmlAccessorType.class);
/*  444 */     if (xat != null) {
/*  445 */       return xat.value();
/*      */     }
/*  447 */     return XmlAccessType.PUBLIC_MEMBER;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private XmlAccessOrder getAccessorOrder() {
/*  454 */     XmlAccessorOrder xao = getClassOrPackageAnnotation(XmlAccessorOrder.class);
/*  455 */     if (xao != null) {
/*  456 */       return xao.value();
/*      */     }
/*  458 */     return XmlAccessOrder.UNDEFINED;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final class PropertySorter
/*      */     extends HashMap<String, Integer>
/*      */     implements Comparator<PropertyInfoImpl>
/*      */   {
/*  471 */     PropertyInfoImpl[] used = new PropertyInfoImpl[ClassInfoImpl.this.propOrder.length];
/*      */ 
/*      */ 
/*      */     
/*      */     private Set<String> collidedNames;
/*      */ 
/*      */ 
/*      */     
/*      */     PropertySorter() {
/*  480 */       super(ClassInfoImpl.this.propOrder.length);
/*  481 */       for (String name : ClassInfoImpl.this.propOrder) {
/*  482 */         if (put(name, Integer.valueOf(size())) != null)
/*      */         {
/*  484 */           ClassInfoImpl.this.builder.reportError(new IllegalAnnotationException(Messages.DUPLICATE_ENTRY_IN_PROP_ORDER
/*  485 */                 .format(new Object[] { name }, ), ClassInfoImpl.this)); } 
/*      */       } 
/*      */     }
/*      */     
/*      */     public int compare(PropertyInfoImpl o1, PropertyInfoImpl o2) {
/*  490 */       int lhs = checkedGet(o1);
/*  491 */       int rhs = checkedGet(o2);
/*      */       
/*  493 */       return lhs - rhs;
/*      */     }
/*      */     
/*      */     private int checkedGet(PropertyInfoImpl p) {
/*  497 */       Integer i = get(p.getName());
/*  498 */       if (i == null) {
/*      */         
/*  500 */         if ((p.kind()).isOrdered) {
/*  501 */           ClassInfoImpl.this.builder.reportError(new IllegalAnnotationException(Messages.PROPERTY_MISSING_FROM_ORDER
/*  502 */                 .format(new Object[] { p.getName() }, ), p));
/*      */         }
/*      */         
/*  505 */         i = Integer.valueOf(size());
/*  506 */         put(p.getName(), i);
/*      */       } 
/*      */ 
/*      */       
/*  510 */       int ii = i.intValue();
/*  511 */       if (ii < this.used.length) {
/*  512 */         if (this.used[ii] != null && this.used[ii] != p) {
/*  513 */           if (this.collidedNames == null) this.collidedNames = new HashSet<>();
/*      */           
/*  515 */           if (this.collidedNames.add(p.getName()))
/*      */           {
/*  517 */             ClassInfoImpl.this.builder.reportError(new IllegalAnnotationException(Messages.DUPLICATE_PROPERTIES
/*  518 */                   .format(new Object[] { p.getName() }, ), p, this.used[ii])); } 
/*      */         } 
/*  520 */         this.used[ii] = p;
/*      */       } 
/*      */       
/*  523 */       return i.intValue();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void checkUnusedProperties() {
/*  530 */       for (int i = 0; i < this.used.length; i++) {
/*  531 */         if (this.used[i] == null) {
/*  532 */           String unusedName = ClassInfoImpl.this.propOrder[i];
/*  533 */           String nearest = EditDistance.findNearest(unusedName, new AbstractList<String>() {
/*      */                 public String get(int index) {
/*  535 */                   return ((PropertyInfoImpl)ClassInfoImpl.this.properties.get(index)).getName();
/*      */                 }
/*      */                 
/*      */                 public int size() {
/*  539 */                   return ClassInfoImpl.this.properties.size();
/*      */                 }
/*      */               });
/*  542 */           boolean isOverriding = (i > ClassInfoImpl.this.properties.size() - 1) ? false : ((PropertyInfoImpl)ClassInfoImpl.this.properties.get(i)).hasAnnotation((Class)OverrideAnnotationOf.class);
/*  543 */           if (!isOverriding)
/*  544 */             ClassInfoImpl.this.builder.reportError(new IllegalAnnotationException(Messages.PROPERTY_ORDER_CONTAINS_UNUSED_ENTRY
/*  545 */                   .format(new Object[] { unusedName, nearest }, ), ClassInfoImpl.this)); 
/*      */         } 
/*      */       } 
/*      */     }
/*      */   }
/*      */   
/*      */   public boolean hasProperties() {
/*  552 */     return !this.properties.isEmpty();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static <T> T pickOne(T... args) {
/*  560 */     for (T arg : args) {
/*  561 */       if (arg != null)
/*  562 */         return arg; 
/*  563 */     }  return null;
/*      */   }
/*      */   
/*      */   private static <T> List<T> makeSet(T... args) {
/*  567 */     FinalArrayList<T> finalArrayList = new FinalArrayList();
/*  568 */     for (T arg : args) {
/*  569 */       if (arg != null) finalArrayList.add(arg); 
/*  570 */     }  return (List<T>)finalArrayList;
/*      */   }
/*      */   
/*      */   private static final class ConflictException extends Exception {
/*      */     final List<Annotation> annotations;
/*      */     
/*      */     public ConflictException(List<Annotation> one) {
/*  577 */       this.annotations = one;
/*      */     } }
/*      */   
/*      */   private static final class DuplicateException extends Exception {
/*      */     final Annotation a1;
/*      */     
/*      */     public DuplicateException(Annotation a1, Annotation a2) {
/*  584 */       this.a1 = a1;
/*  585 */       this.a2 = a2;
/*      */     }
/*      */     
/*      */     final Annotation a2;
/*      */   }
/*      */   
/*      */   private enum SecondaryAnnotation
/*      */   {
/*  593 */     JAVA_TYPE(1, new Class[] { XmlJavaTypeAdapter.class }),
/*  594 */     ID_IDREF(2, new Class[] { XmlID.class, XmlIDREF.class }),
/*  595 */     BINARY(4, new Class[] { XmlInlineBinaryData.class, XmlMimeType.class, XmlAttachmentRef.class }),
/*  596 */     ELEMENT_WRAPPER(8, new Class[] { XmlElementWrapper.class }),
/*  597 */     LIST(16, new Class[] { XmlList.class }),
/*  598 */     SCHEMA_TYPE(32, new Class[] { XmlSchemaType.class });
/*      */ 
/*      */ 
/*      */     
/*      */     final int bitMask;
/*      */ 
/*      */ 
/*      */     
/*      */     final Class<? extends Annotation>[] members;
/*      */ 
/*      */ 
/*      */     
/*      */     SecondaryAnnotation(int bitMask, Class<? extends Annotation>... members) {
/*  611 */       this.bitMask = bitMask;
/*  612 */       this.members = members;
/*      */     }
/*      */   }
/*      */   
/*  616 */   private static final SecondaryAnnotation[] SECONDARY_ANNOTATIONS = SecondaryAnnotation.values();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private enum PropertyGroup
/*      */   {
/*  625 */     TRANSIENT((String)new boolean[] { false, false, false, false, false, false }),
/*  626 */     ANY_ATTRIBUTE((String)new boolean[] { true, false, false, false, false, false }),
/*  627 */     ATTRIBUTE((String)new boolean[] { true, true, true, false, true, true }),
/*  628 */     VALUE((String)new boolean[] { true, true, true, false, true, true }),
/*  629 */     ELEMENT((String)new boolean[] { true, true, true, true, true, true }),
/*  630 */     ELEMENT_REF((String)new boolean[] { true, false, false, true, false, false }),
/*  631 */     MAP((String)new boolean[] { false, false, false, true, false, false });
/*      */ 
/*      */ 
/*      */     
/*      */     final int allowedsecondaryAnnotations;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     PropertyGroup(boolean... bits) {
/*  641 */       int mask = 0;
/*  642 */       assert bits.length == ClassInfoImpl.SECONDARY_ANNOTATIONS.length;
/*  643 */       for (int i = 0; i < bits.length; i++) {
/*  644 */         if (bits[i])
/*  645 */           mask |= (ClassInfoImpl.SECONDARY_ANNOTATIONS[i]).bitMask; 
/*      */       } 
/*  647 */       this.allowedsecondaryAnnotations = mask ^ 0xFFFFFFFF;
/*      */     }
/*      */     
/*      */     boolean allows(ClassInfoImpl.SecondaryAnnotation a) {
/*  651 */       return ((this.allowedsecondaryAnnotations & a.bitMask) == 0);
/*      */     }
/*      */   }
/*      */   
/*  655 */   private static final Annotation[] EMPTY_ANNOTATIONS = new Annotation[0];
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  660 */   private static final HashMap<Class, Integer> ANNOTATION_NUMBER_MAP = new HashMap<>();
/*      */   static {
/*  662 */     Class[] annotations = { XmlTransient.class, XmlAnyAttribute.class, XmlAttribute.class, XmlValue.class, XmlElement.class, XmlElements.class, XmlElementRef.class, XmlElementRefs.class, XmlAnyElement.class, XmlMixed.class, OverrideAnnotationOf.class };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  676 */     HashMap<Class<?>, Integer> m = ANNOTATION_NUMBER_MAP;
/*      */ 
/*      */     
/*  679 */     for (Class<?> c : annotations) {
/*  680 */       m.put(c, Integer.valueOf(m.size()));
/*      */     }
/*      */     
/*  683 */     int index = 20;
/*  684 */     for (SecondaryAnnotation sa : SECONDARY_ANNOTATIONS) {
/*  685 */       for (Class<? extends Annotation> member : sa.members)
/*  686 */         m.put(member, Integer.valueOf(index)); 
/*  687 */       index++;
/*      */     } 
/*      */   }
/*      */   
/*      */   private void checkConflict(Annotation a, Annotation b) throws DuplicateException {
/*  692 */     assert b != null;
/*  693 */     if (a != null) {
/*  694 */       throw new DuplicateException(a, b);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void addProperty(PropertySeed<T, C, F, M> seed, Annotation[] annotations, boolean dummy) {
/*  717 */     XmlTransient t = null;
/*  718 */     XmlAnyAttribute aa = null;
/*  719 */     XmlAttribute a = null;
/*  720 */     XmlValue v = null;
/*  721 */     XmlElement e1 = null;
/*  722 */     XmlElements e2 = null;
/*  723 */     XmlElementRef r1 = null;
/*  724 */     XmlElementRefs r2 = null;
/*  725 */     XmlAnyElement xae = null;
/*  726 */     XmlMixed mx = null;
/*  727 */     OverrideAnnotationOf ov = null;
/*      */ 
/*      */     
/*  730 */     int secondaryAnnotations = 0;
/*      */     
/*      */     try {
/*  733 */       for (Annotation ann : annotations) {
/*  734 */         Integer index = ANNOTATION_NUMBER_MAP.get(ann.annotationType());
/*  735 */         if (index != null) {
/*  736 */           switch (index.intValue()) { case 0:
/*  737 */               checkConflict((Annotation)t, ann); t = (XmlTransient)ann; break;
/*  738 */             case 1: checkConflict((Annotation)aa, ann); aa = (XmlAnyAttribute)ann; break;
/*  739 */             case 2: checkConflict((Annotation)a, ann); a = (XmlAttribute)ann; break;
/*  740 */             case 3: checkConflict((Annotation)v, ann); v = (XmlValue)ann; break;
/*  741 */             case 4: checkConflict((Annotation)e1, ann); e1 = (XmlElement)ann; break;
/*  742 */             case 5: checkConflict((Annotation)e2, ann); e2 = (XmlElements)ann; break;
/*  743 */             case 6: checkConflict((Annotation)r1, ann); r1 = (XmlElementRef)ann; break;
/*  744 */             case 7: checkConflict((Annotation)r2, ann); r2 = (XmlElementRefs)ann; break;
/*  745 */             case 8: checkConflict((Annotation)xae, ann); xae = (XmlAnyElement)ann; break;
/*  746 */             case 9: checkConflict((Annotation)mx, ann); mx = (XmlMixed)ann; break;
/*  747 */             case 10: checkConflict((Annotation)ov, ann); ov = (OverrideAnnotationOf)ann;
/*      */               break;
/*      */             default:
/*  750 */               secondaryAnnotations |= 1 << index.intValue() - 20;
/*      */               break; }
/*      */ 
/*      */ 
/*      */         
/*      */         }
/*      */       } 
/*  757 */       PropertyGroup group = null;
/*  758 */       int groupCount = 0;
/*      */       
/*  760 */       if (t != null) {
/*  761 */         group = PropertyGroup.TRANSIENT;
/*  762 */         groupCount++;
/*      */       } 
/*  764 */       if (aa != null) {
/*  765 */         group = PropertyGroup.ANY_ATTRIBUTE;
/*  766 */         groupCount++;
/*      */       } 
/*  768 */       if (a != null) {
/*  769 */         group = PropertyGroup.ATTRIBUTE;
/*  770 */         groupCount++;
/*      */       } 
/*  772 */       if (v != null) {
/*  773 */         group = PropertyGroup.VALUE;
/*  774 */         groupCount++;
/*      */       } 
/*  776 */       if (e1 != null || e2 != null) {
/*  777 */         group = PropertyGroup.ELEMENT;
/*  778 */         groupCount++;
/*      */       } 
/*  780 */       if (r1 != null || r2 != null || xae != null || mx != null || ov != null) {
/*  781 */         group = PropertyGroup.ELEMENT_REF;
/*  782 */         groupCount++;
/*      */       } 
/*      */       
/*  785 */       if (groupCount > 1) {
/*      */         
/*  787 */         List<Annotation> err = makeSet(new Annotation[] { (Annotation)t, (Annotation)aa, (Annotation)a, (Annotation)v, pickOne(new Annotation[] { (Annotation)e1, (Annotation)e2 }), pickOne(new Annotation[] { (Annotation)r1, (Annotation)r2, (Annotation)xae }) });
/*  788 */         throw new ConflictException(err);
/*      */       } 
/*      */       
/*  791 */       if (group == null) {
/*      */ 
/*      */         
/*  794 */         assert groupCount == 0;
/*      */ 
/*      */         
/*  797 */         if (nav().isSubClassOf(seed.getRawType(), nav().ref(Map.class)) && 
/*  798 */           !seed.hasAnnotation(XmlJavaTypeAdapter.class))
/*  799 */         { group = PropertyGroup.MAP; }
/*      */         else
/*  801 */         { group = PropertyGroup.ELEMENT; } 
/*  802 */       } else if (group.equals(PropertyGroup.ELEMENT) && 
/*  803 */         nav().isSubClassOf(seed.getRawType(), nav().ref(Map.class)) && !seed.hasAnnotation(XmlJavaTypeAdapter.class)) {
/*  804 */         group = PropertyGroup.MAP;
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  810 */       if ((secondaryAnnotations & group.allowedsecondaryAnnotations) != 0) {
/*      */         
/*  812 */         for (SecondaryAnnotation sa : SECONDARY_ANNOTATIONS) {
/*  813 */           if (!group.allows(sa))
/*      */           {
/*  815 */             for (Class<? extends Annotation> m : sa.members) {
/*  816 */               Annotation offender = seed.readAnnotation(m);
/*  817 */               if (offender != null) {
/*      */                 
/*  819 */                 this.builder.reportError(new IllegalAnnotationException(Messages.ANNOTATION_NOT_ALLOWED
/*  820 */                       .format(new Object[] { m.getSimpleName() }, ), offender));
/*      */                 
/*      */                 return;
/*      */               } 
/*      */             } 
/*      */           }
/*      */         } 
/*      */         
/*      */         assert false;
/*      */       } 
/*  830 */       switch (group) {
/*      */         case TRANSIENT:
/*      */           return;
/*      */         
/*      */         case ANY_ATTRIBUTE:
/*  835 */           if (this.attributeWildcard != null) {
/*  836 */             this.builder.reportError(new IllegalAnnotationException(Messages.TWO_ATTRIBUTE_WILDCARDS
/*  837 */                   .format(new Object[] {
/*  838 */                       nav().getClassName(getClazz()) }, ), (Annotation)aa, this.attributeWildcard));
/*      */             return;
/*      */           } 
/*  841 */           this.attributeWildcard = seed;
/*      */           
/*  843 */           if (inheritsAttributeWildcard()) {
/*  844 */             this.builder.reportError(new IllegalAnnotationException(Messages.SUPER_CLASS_HAS_WILDCARD
/*  845 */                   .format(new Object[0]), (Annotation)aa, 
/*  846 */                   getInheritedAttributeWildcard()));
/*      */             
/*      */             return;
/*      */           } 
/*      */           
/*  851 */           if (!nav().isSubClassOf(seed.getRawType(), nav().ref(Map.class))) {
/*  852 */             this.builder.reportError(new IllegalAnnotationException(Messages.INVALID_ATTRIBUTE_WILDCARD_TYPE
/*  853 */                   .format(new Object[] { nav().getTypeName(seed.getRawType()) }, ), (Annotation)aa, 
/*  854 */                   getInheritedAttributeWildcard()));
/*      */             return;
/*      */           } 
/*      */           return;
/*      */ 
/*      */         
/*      */         case ATTRIBUTE:
/*  861 */           this.properties.add(createAttributeProperty(seed));
/*      */           return;
/*      */         case VALUE:
/*  864 */           this.properties.add(createValueProperty(seed));
/*      */           return;
/*      */         case ELEMENT:
/*  867 */           this.properties.add(createElementProperty(seed));
/*      */           return;
/*      */         case ELEMENT_REF:
/*  870 */           this.properties.add(createReferenceProperty(seed));
/*      */           return;
/*      */         case MAP:
/*  873 */           this.properties.add(createMapProperty(seed));
/*      */           return;
/*      */       } 
/*      */       
/*      */       assert false;
/*  878 */     } catch (ConflictException x) {
/*      */       
/*  880 */       List<Annotation> err = x.annotations;
/*      */       
/*  882 */       this.builder.reportError(new IllegalAnnotationException(Messages.MUTUALLY_EXCLUSIVE_ANNOTATIONS
/*  883 */             .format(new Object[] {
/*  884 */                 nav().getClassName(getClazz()) + '#' + seed.getName(), ((Annotation)err
/*  885 */                 .get(0)).annotationType().getName(), ((Annotation)err.get(1)).annotationType().getName()
/*  886 */               }, ), err.get(0), err.get(1)));
/*      */     
/*      */     }
/*  889 */     catch (DuplicateException e) {
/*      */       
/*  891 */       this.builder.reportError(new IllegalAnnotationException(Messages.DUPLICATE_ANNOTATIONS
/*  892 */             .format(new Object[] { e.a1.annotationType().getName() }, ), e.a1, e.a2));
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected ReferencePropertyInfoImpl<T, C, F, M> createReferenceProperty(PropertySeed<T, C, F, M> seed) {
/*  900 */     return new ReferencePropertyInfoImpl<>(this, seed);
/*      */   }
/*      */   
/*      */   protected AttributePropertyInfoImpl<T, C, F, M> createAttributeProperty(PropertySeed<T, C, F, M> seed) {
/*  904 */     return new AttributePropertyInfoImpl<>(this, seed);
/*      */   }
/*      */   
/*      */   protected ValuePropertyInfoImpl<T, C, F, M> createValueProperty(PropertySeed<T, C, F, M> seed) {
/*  908 */     return new ValuePropertyInfoImpl<>(this, seed);
/*      */   }
/*      */   
/*      */   protected ElementPropertyInfoImpl<T, C, F, M> createElementProperty(PropertySeed<T, C, F, M> seed) {
/*  912 */     return new ElementPropertyInfoImpl<>(this, seed);
/*      */   }
/*      */   
/*      */   protected MapPropertyInfoImpl<T, C, F, M> createMapProperty(PropertySeed<T, C, F, M> seed) {
/*  916 */     return new MapPropertyInfoImpl<>(this, seed);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void findGetterSetterProperties(XmlAccessType at) {
/*  926 */     Map<String, M> getters = new LinkedHashMap<>();
/*  927 */     Map<String, M> setters = new LinkedHashMap<>();
/*      */     
/*  929 */     C c = this.clazz;
/*      */     do {
/*  931 */       collectGetterSetters(this.clazz, getters, setters);
/*      */ 
/*      */       
/*  934 */       c = (C)nav().getSuperClass(c);
/*  935 */     } while (shouldRecurseSuperClass(c));
/*      */ 
/*      */ 
/*      */     
/*  939 */     Set<String> complete = new TreeSet<>(getters.keySet());
/*  940 */     complete.retainAll(setters.keySet());
/*      */     
/*  942 */     resurrect(getters, complete);
/*  943 */     resurrect(setters, complete);
/*      */ 
/*      */     
/*  946 */     for (String name : complete) {
/*  947 */       M getter = getters.get(name);
/*  948 */       M setter = setters.get(name);
/*      */       
/*  950 */       Annotation[] ga = (getter != null) ? reader().getAllMethodAnnotations(getter, (Locatable)new MethodLocatable(this, getter, nav())) : EMPTY_ANNOTATIONS;
/*  951 */       Annotation[] sa = (setter != null) ? reader().getAllMethodAnnotations(setter, (Locatable)new MethodLocatable(this, setter, nav())) : EMPTY_ANNOTATIONS;
/*      */       
/*  953 */       boolean hasAnnotation = (hasJAXBAnnotation(ga) || hasJAXBAnnotation(sa));
/*  954 */       boolean isOverriding = false;
/*  955 */       if (!hasAnnotation)
/*      */       {
/*      */ 
/*      */         
/*  959 */         isOverriding = (getter != null && nav().isOverriding(getter, c) && setter != null && nav().isOverriding(setter, c));
/*      */       }
/*      */       
/*  962 */       if ((at == XmlAccessType.PROPERTY && !isOverriding) || (at == XmlAccessType.PUBLIC_MEMBER && 
/*  963 */         isConsideredPublic(getter) && isConsideredPublic(setter) && !isOverriding) || hasAnnotation) {
/*      */         Annotation[] r;
/*      */         
/*  966 */         if (getter != null && setter != null && 
/*  967 */           !nav().isSameType(nav().getReturnType(getter), nav().getMethodParameters(setter)[0])) {
/*      */           
/*  969 */           this.builder.reportError(new IllegalAnnotationException(Messages.GETTER_SETTER_INCOMPATIBLE_TYPE
/*  970 */                 .format(new Object[] {
/*  971 */                     nav().getTypeName(nav().getReturnType(getter)), 
/*  972 */                     nav().getTypeName(nav().getMethodParameters(setter)[0])
/*      */                   },
/*  974 */                 ), (Locatable)new MethodLocatable(this, getter, nav()), (Locatable)new MethodLocatable(this, setter, 
/*  975 */                   nav())));
/*      */ 
/*      */           
/*      */           continue;
/*      */         } 
/*      */         
/*  981 */         if (ga.length == 0) {
/*  982 */           r = sa;
/*      */         }
/*  984 */         else if (sa.length == 0) {
/*  985 */           r = ga;
/*      */         } else {
/*  987 */           r = new Annotation[ga.length + sa.length];
/*  988 */           System.arraycopy(ga, 0, r, 0, ga.length);
/*  989 */           System.arraycopy(sa, 0, r, ga.length, sa.length);
/*      */         } 
/*      */         
/*  992 */         addProperty(createAccessorSeed(getter, setter), r, false);
/*      */       } 
/*      */     } 
/*      */     
/*  996 */     getters.keySet().removeAll(complete);
/*  997 */     setters.keySet().removeAll(complete);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void collectGetterSetters(C c, Map<String, M> getters, Map<String, M> setters) {
/* 1014 */     C sc = (C)nav().getSuperClass(c);
/* 1015 */     if (shouldRecurseSuperClass(sc)) {
/* 1016 */       collectGetterSetters(sc, getters, setters);
/*      */     }
/* 1018 */     Collection<? extends M> methods = nav().getDeclaredMethods(c);
/* 1019 */     Map<String, List<M>> allSetters = new LinkedHashMap<>();
/* 1020 */     for (M method : methods) {
/* 1021 */       boolean used = false;
/*      */       
/* 1023 */       if (nav().isBridgeMethod(method)) {
/*      */         continue;
/*      */       }
/* 1026 */       String name = nav().getMethodName(method);
/* 1027 */       int arity = (nav().getMethodParameters(method)).length;
/*      */       
/* 1029 */       if (nav().isStaticMethod(method)) {
/* 1030 */         ensureNoAnnotation(method);
/*      */         
/*      */         continue;
/*      */       } 
/*      */       
/* 1035 */       String propName = getPropertyNameFromGetMethod(name);
/* 1036 */       if (propName != null && arity == 0) {
/* 1037 */         getters.put(propName, method);
/* 1038 */         used = true;
/*      */       } 
/*      */ 
/*      */       
/* 1042 */       propName = getPropertyNameFromSetMethod(name);
/* 1043 */       if (propName != null && arity == 1) {
/* 1044 */         List<M> propSetters = allSetters.get(propName);
/* 1045 */         if (null == propSetters) {
/* 1046 */           propSetters = new ArrayList<>();
/* 1047 */           allSetters.put(propName, propSetters);
/*      */         } 
/* 1049 */         propSetters.add(method);
/* 1050 */         used = true;
/*      */       } 
/*      */       
/* 1053 */       if (!used) {
/* 1054 */         ensureNoAnnotation(method);
/*      */       }
/*      */     } 
/*      */     
/* 1058 */     for (Map.Entry<String, M> entry : getters.entrySet()) {
/* 1059 */       String propName = entry.getKey();
/* 1060 */       M getter = entry.getValue();
/* 1061 */       List<M> propSetters = allSetters.remove(propName);
/* 1062 */       if (null == propSetters) {
/*      */         continue;
/*      */       }
/*      */       
/* 1066 */       T getterType = (T)nav().getReturnType(getter);
/* 1067 */       for (M setter : propSetters) {
/* 1068 */         T setterType = (T)nav().getMethodParameters(setter)[0];
/* 1069 */         if (nav().isSameType(setterType, getterType)) {
/* 1070 */           setters.put(propName, setter);
/*      */         }
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1077 */     for (Map.Entry<String, List<M>> e : allSetters.entrySet()) {
/* 1078 */       setters.put(e.getKey(), ((List<M>)e.getValue()).get(0));
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean shouldRecurseSuperClass(C sc) {
/* 1086 */     return (sc != null && (this.builder
/* 1087 */       .isReplaced(sc) || reader().hasClassAnnotation(sc, XmlTransient.class)));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean isConsideredPublic(M m) {
/* 1094 */     return (m == null || nav().isPublicMethod(m));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void resurrect(Map<String, M> methods, Set<String> complete) {
/* 1102 */     for (Map.Entry<String, M> e : methods.entrySet()) {
/* 1103 */       if (complete.contains(e.getKey()))
/*      */         continue; 
/* 1105 */       if (hasJAXBAnnotation(reader().getAllMethodAnnotations(e.getValue(), this))) {
/* 1106 */         complete.add(e.getKey());
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void ensureNoAnnotation(M method) {
/* 1115 */     Annotation[] annotations = reader().getAllMethodAnnotations(method, this);
/* 1116 */     for (Annotation a : annotations) {
/* 1117 */       if (isJAXBAnnotation(a)) {
/* 1118 */         this.builder.reportError(new IllegalAnnotationException(Messages.ANNOTATION_ON_WRONG_METHOD
/* 1119 */               .format(new Object[0]), a));
/*      */         return;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static boolean isJAXBAnnotation(Annotation a) {
/* 1130 */     return ANNOTATION_NUMBER_MAP.containsKey(a.annotationType());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static boolean hasJAXBAnnotation(Annotation[] annotations) {
/* 1137 */     return (getSomeJAXBAnnotation(annotations) != null);
/*      */   }
/*      */   
/*      */   private static Annotation getSomeJAXBAnnotation(Annotation[] annotations) {
/* 1141 */     for (Annotation a : annotations) {
/* 1142 */       if (isJAXBAnnotation(a))
/* 1143 */         return a; 
/* 1144 */     }  return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static String getPropertyNameFromGetMethod(String name) {
/* 1155 */     if (name.startsWith("get") && name.length() > 3)
/* 1156 */       return name.substring(3); 
/* 1157 */     if (name.startsWith("is") && name.length() > 2)
/* 1158 */       return name.substring(2); 
/* 1159 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static String getPropertyNameFromSetMethod(String name) {
/* 1169 */     if (name.startsWith("set") && name.length() > 3)
/* 1170 */       return name.substring(3); 
/* 1171 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected PropertySeed<T, C, F, M> createFieldSeed(F f) {
/* 1181 */     return new FieldPropertySeed<>(this, f);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected PropertySeed<T, C, F, M> createAccessorSeed(M getter, M setter) {
/* 1188 */     return new GetterSetterPropertySeed<>(this, getter, setter);
/*      */   }
/*      */   
/*      */   public final boolean isElement() {
/* 1192 */     return (this.elementName != null);
/*      */   }
/*      */   
/*      */   public boolean isAbstract() {
/* 1196 */     return nav().isAbstract(this.clazz);
/*      */   }
/*      */   
/*      */   public boolean isOrdered() {
/* 1200 */     return (this.propOrder != null);
/*      */   }
/*      */   
/*      */   public final boolean isFinal() {
/* 1204 */     return nav().isFinal(this.clazz);
/*      */   }
/*      */   
/*      */   public final boolean hasSubClasses() {
/* 1208 */     return this.hasSubClasses;
/*      */   }
/*      */   
/*      */   public final boolean hasAttributeWildcard() {
/* 1212 */     return (declaresAttributeWildcard() || inheritsAttributeWildcard());
/*      */   }
/*      */   
/*      */   public final boolean inheritsAttributeWildcard() {
/* 1216 */     return (getInheritedAttributeWildcard() != null);
/*      */   }
/*      */   
/*      */   public final boolean declaresAttributeWildcard() {
/* 1220 */     return (this.attributeWildcard != null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private PropertySeed<T, C, F, M> getInheritedAttributeWildcard() {
/* 1227 */     for (ClassInfoImpl<T, C, F, M> c = getBaseClass(); c != null; c = c.getBaseClass()) {
/* 1228 */       if (c.attributeWildcard != null)
/* 1229 */         return c.attributeWildcard; 
/* 1230 */     }  return null;
/*      */   }
/*      */   
/*      */   public final QName getElementName() {
/* 1234 */     return this.elementName;
/*      */   }
/*      */   
/*      */   public final QName getTypeName() {
/* 1238 */     return this.typeName;
/*      */   }
/*      */   
/*      */   public final boolean isSimpleType() {
/* 1242 */     List<? extends PropertyInfo> props = (List)getProperties();
/* 1243 */     if (props.size() != 1) return false; 
/* 1244 */     return (((PropertyInfo)props.get(0)).kind() == PropertyKind.VALUE);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void link() {
/* 1252 */     getProperties();
/*      */ 
/*      */     
/* 1255 */     Map<String, PropertyInfoImpl> names = new HashMap<>();
/* 1256 */     for (PropertyInfoImpl<T, C, F, M> p : this.properties) {
/* 1257 */       p.link();
/* 1258 */       PropertyInfoImpl old = names.put(p.getName(), p);
/* 1259 */       if (old != null) {
/* 1260 */         this.builder.reportError(new IllegalAnnotationException(Messages.PROPERTY_COLLISION
/* 1261 */               .format(new Object[] { p.getName() }, ), p, old));
/*      */       }
/*      */     } 
/*      */     
/* 1265 */     super.link();
/*      */   }
/*      */   
/*      */   public Location getLocation() {
/* 1269 */     return nav().getClassLocation(this.clazz);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean hasFactoryConstructor(XmlType t) {
/* 1281 */     if (t == null) return false;
/*      */     
/* 1283 */     String method = t.factoryMethod();
/* 1284 */     T fClass = (T)reader().getClassValue((Annotation)t, "factoryClass");
/* 1285 */     if (method.length() > 0) {
/* 1286 */       if (nav().isSameType(fClass, nav().ref(XmlType.DEFAULT.class))) {
/* 1287 */         fClass = (T)nav().use(this.clazz);
/*      */       }
/* 1289 */       for (M m : nav().getDeclaredMethods(nav().asDecl(fClass))) {
/*      */         
/* 1291 */         if (nav().getMethodName(m).equals(method) && 
/* 1292 */           nav().isSameType(nav().getReturnType(m), nav().use(this.clazz)) && (
/* 1293 */           nav().getMethodParameters(m)).length == 0 && 
/* 1294 */           nav().isStaticMethod(m)) {
/* 1295 */           this.factoryMethod = m;
/*      */           break;
/*      */         } 
/*      */       } 
/* 1299 */       if (this.factoryMethod == null) {
/* 1300 */         this.builder.reportError(new IllegalAnnotationException(Messages.NO_FACTORY_METHOD
/* 1301 */               .format(new Object[] { nav().getClassName(nav().asDecl(fClass)), method }, ), this));
/*      */       }
/* 1303 */     } else if (!nav().isSameType(fClass, nav().ref(XmlType.DEFAULT.class))) {
/* 1304 */       this.builder.reportError(new IllegalAnnotationException(Messages.FACTORY_CLASS_NEEDS_FACTORY_METHOD
/* 1305 */             .format(new Object[] { nav().getClassName(nav().asDecl(fClass)) }, ), this));
/*      */     } 
/* 1307 */     return (this.factoryMethod != null);
/*      */   }
/*      */   
/*      */   public Method getFactoryMethod() {
/* 1311 */     return (Method)this.factoryMethod;
/*      */   }
/*      */ 
/*      */   
/*      */   public String toString() {
/* 1316 */     return "ClassInfo(" + this.clazz + ')';
/*      */   }
/*      */   
/* 1319 */   private static final String[] DEFAULT_ORDER = new String[0];
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/bind/v2/model/impl/ClassInfoImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */