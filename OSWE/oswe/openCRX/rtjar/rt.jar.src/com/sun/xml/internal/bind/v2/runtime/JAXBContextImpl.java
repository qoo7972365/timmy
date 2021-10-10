/*      */ package com.sun.xml.internal.bind.v2.runtime;
/*      */ 
/*      */ import com.sun.istack.internal.NotNull;
/*      */ import com.sun.istack.internal.Pool;
/*      */ import com.sun.xml.internal.bind.api.AccessorException;
/*      */ import com.sun.xml.internal.bind.api.Bridge;
/*      */ import com.sun.xml.internal.bind.api.BridgeContext;
/*      */ import com.sun.xml.internal.bind.api.CompositeStructure;
/*      */ import com.sun.xml.internal.bind.api.ErrorListener;
/*      */ import com.sun.xml.internal.bind.api.JAXBRIContext;
/*      */ import com.sun.xml.internal.bind.api.RawAccessor;
/*      */ import com.sun.xml.internal.bind.api.TypeReference;
/*      */ import com.sun.xml.internal.bind.unmarshaller.DOMScanner;
/*      */ import com.sun.xml.internal.bind.unmarshaller.InfosetScanner;
/*      */ import com.sun.xml.internal.bind.util.Which;
/*      */ import com.sun.xml.internal.bind.v2.model.annotation.AnnotationReader;
/*      */ import com.sun.xml.internal.bind.v2.model.annotation.RuntimeAnnotationReader;
/*      */ import com.sun.xml.internal.bind.v2.model.annotation.RuntimeInlineAnnotationReader;
/*      */ import com.sun.xml.internal.bind.v2.model.core.Adapter;
/*      */ import com.sun.xml.internal.bind.v2.model.core.NonElement;
/*      */ import com.sun.xml.internal.bind.v2.model.core.Ref;
/*      */ import com.sun.xml.internal.bind.v2.model.core.TypeInfoSet;
/*      */ import com.sun.xml.internal.bind.v2.model.impl.RuntimeBuiltinLeafInfoImpl;
/*      */ import com.sun.xml.internal.bind.v2.model.impl.RuntimeModelBuilder;
/*      */ import com.sun.xml.internal.bind.v2.model.nav.Navigator;
/*      */ import com.sun.xml.internal.bind.v2.model.runtime.RuntimeArrayInfo;
/*      */ import com.sun.xml.internal.bind.v2.model.runtime.RuntimeBuiltinLeafInfo;
/*      */ import com.sun.xml.internal.bind.v2.model.runtime.RuntimeClassInfo;
/*      */ import com.sun.xml.internal.bind.v2.model.runtime.RuntimeElementInfo;
/*      */ import com.sun.xml.internal.bind.v2.model.runtime.RuntimeEnumLeafInfo;
/*      */ import com.sun.xml.internal.bind.v2.model.runtime.RuntimeLeafInfo;
/*      */ import com.sun.xml.internal.bind.v2.model.runtime.RuntimeTypeInfo;
/*      */ import com.sun.xml.internal.bind.v2.model.runtime.RuntimeTypeInfoSet;
/*      */ import com.sun.xml.internal.bind.v2.runtime.output.Encoded;
/*      */ import com.sun.xml.internal.bind.v2.runtime.property.AttributeProperty;
/*      */ import com.sun.xml.internal.bind.v2.runtime.property.Property;
/*      */ import com.sun.xml.internal.bind.v2.runtime.reflect.Accessor;
/*      */ import com.sun.xml.internal.bind.v2.runtime.unmarshaller.Loader;
/*      */ import com.sun.xml.internal.bind.v2.runtime.unmarshaller.TagName;
/*      */ import com.sun.xml.internal.bind.v2.runtime.unmarshaller.UnmarshallerImpl;
/*      */ import com.sun.xml.internal.bind.v2.runtime.unmarshaller.UnmarshallingContext;
/*      */ import com.sun.xml.internal.bind.v2.schemagen.XmlSchemaGenerator;
/*      */ import com.sun.xml.internal.bind.v2.util.EditDistance;
/*      */ import com.sun.xml.internal.bind.v2.util.QNameMap;
/*      */ import com.sun.xml.internal.bind.v2.util.XmlFactory;
/*      */ import com.sun.xml.internal.txw2.output.ResultFactory;
/*      */ import java.io.IOException;
/*      */ import java.lang.ref.WeakReference;
/*      */ import java.lang.reflect.Field;
/*      */ import java.lang.reflect.Method;
/*      */ import java.lang.reflect.Type;
/*      */ import java.util.Arrays;
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
/*      */ import javax.xml.bind.Binder;
/*      */ import javax.xml.bind.JAXBElement;
/*      */ import javax.xml.bind.JAXBException;
/*      */ import javax.xml.bind.JAXBIntrospector;
/*      */ import javax.xml.bind.Marshaller;
/*      */ import javax.xml.bind.SchemaOutputResolver;
/*      */ import javax.xml.bind.Unmarshaller;
/*      */ import javax.xml.bind.Validator;
/*      */ import javax.xml.bind.annotation.XmlAttachmentRef;
/*      */ import javax.xml.bind.annotation.XmlList;
/*      */ import javax.xml.bind.annotation.XmlNs;
/*      */ import javax.xml.bind.annotation.XmlSchema;
/*      */ import javax.xml.bind.annotation.adapters.XmlAdapter;
/*      */ import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
/*      */ import javax.xml.namespace.QName;
/*      */ import javax.xml.parsers.DocumentBuilder;
/*      */ import javax.xml.parsers.DocumentBuilderFactory;
/*      */ import javax.xml.parsers.FactoryConfigurationError;
/*      */ import javax.xml.parsers.ParserConfigurationException;
/*      */ import javax.xml.transform.Result;
/*      */ import javax.xml.transform.Transformer;
/*      */ import javax.xml.transform.TransformerConfigurationException;
/*      */ import javax.xml.transform.sax.SAXTransformerFactory;
/*      */ import javax.xml.transform.sax.TransformerHandler;
/*      */ import org.w3c.dom.Document;
/*      */ import org.w3c.dom.Node;
/*      */ import org.xml.sax.SAXException;
/*      */ import org.xml.sax.SAXParseException;
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
/*      */ public final class JAXBContextImpl
/*      */   extends JAXBRIContext
/*      */ {
/*      */   private final Map<TypeReference, Bridge> bridges;
/*      */   private static DocumentBuilder db;
/*      */   private final QNameMap<JaxBeanInfo> rootMap;
/*      */   private final HashMap<QName, JaxBeanInfo> typeMap;
/*      */   private final Map<Class, JaxBeanInfo> beanInfoMap;
/*      */   protected Map<RuntimeTypeInfo, JaxBeanInfo> beanInfos;
/*      */   private final Map<Class, Map<QName, ElementBeanInfoImpl>> elements;
/*      */   public final Pool<Marshaller> marshallerPool;
/*      */   public final Pool<Unmarshaller> unmarshallerPool;
/*      */   public NameBuilder nameBuilder;
/*      */   public final NameList nameList;
/*      */   private final String defaultNsUri;
/*      */   private final Class[] classes;
/*      */   protected final boolean c14nSupport;
/*      */   public final boolean xmlAccessorFactorySupport;
/*      */   public final boolean allNillable;
/*      */   public final boolean retainPropertyInfo;
/*      */   public final boolean supressAccessorWarnings;
/*      */   public final boolean improvedXsiTypeHandling;
/*      */   public final boolean disableSecurityProcessing;
/*      */   private WeakReference<RuntimeTypeInfoSet> typeInfoSetCache;
/*      */   @NotNull
/*      */   private RuntimeAnnotationReader annotationReader;
/*      */   private boolean hasSwaRef;
/*      */   @NotNull
/*      */   private final Map<Class, Class> subclassReplacements;
/*      */   public final boolean fastBoot;
/*      */   private Set<XmlNs> xmlNsSet;
/*      */   private Encoded[] utf8nameTable;
/*      */   
/*      */   public Set<XmlNs> getXmlNsSet() {
/*  250 */     return this.xmlNsSet;
/*      */   } private JAXBContextImpl(JAXBContextBuilder builder) throws JAXBException { boolean fastB; this.bridges = new LinkedHashMap<>(); this.rootMap = new QNameMap(); this.typeMap = new HashMap<>(); this.beanInfoMap = (Map)new LinkedHashMap<>(); this.beanInfos = new LinkedHashMap<>(); this.elements = (Map)new LinkedHashMap<>(); this.marshallerPool = (Pool<Marshaller>)new Pool.Impl<Marshaller>() { @NotNull protected Marshaller create() { return (Marshaller)JAXBContextImpl.this.createMarshaller(); } }
/*      */       ; this.unmarshallerPool = (Pool<Unmarshaller>)new Pool.Impl<Unmarshaller>() { @NotNull protected Unmarshaller create() { return (Unmarshaller)JAXBContextImpl.this.createUnmarshaller(); } };
/*      */     this.nameBuilder = new NameBuilder();
/*      */     this.xmlNsSet = null;
/*  255 */     this.defaultNsUri = builder.defaultNsUri;
/*  256 */     this.retainPropertyInfo = builder.retainPropertyInfo;
/*  257 */     this.annotationReader = builder.annotationReader;
/*  258 */     this.subclassReplacements = builder.subclassReplacements;
/*  259 */     this.c14nSupport = builder.c14nSupport;
/*  260 */     this.classes = builder.classes;
/*  261 */     this.xmlAccessorFactorySupport = builder.xmlAccessorFactorySupport;
/*  262 */     this.allNillable = builder.allNillable;
/*  263 */     this.supressAccessorWarnings = builder.supressAccessorWarnings;
/*  264 */     this.improvedXsiTypeHandling = builder.improvedXsiTypeHandling;
/*  265 */     this.disableSecurityProcessing = builder.disableSecurityProcessing;
/*      */     
/*  267 */     Collection<TypeReference> typeRefs = builder.typeRefs;
/*      */ 
/*      */     
/*      */     try {
/*  271 */       fastB = Boolean.getBoolean(JAXBContextImpl.class.getName() + ".fastBoot");
/*  272 */     } catch (SecurityException e) {
/*  273 */       fastB = false;
/*      */     } 
/*  275 */     this.fastBoot = fastB;
/*      */     
/*  277 */     RuntimeTypeInfoSet typeSet = getTypeInfoSet();
/*      */ 
/*      */     
/*  280 */     this.elements.put(null, new LinkedHashMap<>());
/*      */ 
/*      */     
/*  283 */     for (RuntimeBuiltinLeafInfo leaf : RuntimeBuiltinLeafInfoImpl.builtinBeanInfos) {
/*  284 */       LeafBeanInfoImpl<?> bi = new LeafBeanInfoImpl(this, (RuntimeLeafInfo)leaf);
/*  285 */       this.beanInfoMap.put(leaf.getClazz(), bi);
/*  286 */       for (QName t : bi.getTypeNames()) {
/*  287 */         this.typeMap.put(t, bi);
/*      */       }
/*      */     } 
/*  290 */     for (RuntimeEnumLeafInfo e : typeSet.enums().values()) {
/*  291 */       JaxBeanInfo<?> bi = getOrCreate(e);
/*  292 */       for (QName qn : bi.getTypeNames())
/*  293 */         this.typeMap.put(qn, bi); 
/*  294 */       if (e.isElement()) {
/*  295 */         this.rootMap.put(e.getElementName(), bi);
/*      */       }
/*      */     } 
/*  298 */     for (RuntimeArrayInfo a : typeSet.arrays().values()) {
/*  299 */       JaxBeanInfo<?> ai = getOrCreate(a);
/*  300 */       for (QName qn : ai.getTypeNames()) {
/*  301 */         this.typeMap.put(qn, ai);
/*      */       }
/*      */     } 
/*  304 */     for (Map.Entry<Class<?>, ? extends RuntimeClassInfo> e : (Iterable<Map.Entry<Class<?>, ? extends RuntimeClassInfo>>)typeSet.beans().entrySet()) {
/*  305 */       ClassBeanInfoImpl<?> bi = getOrCreate(e.getValue());
/*      */       
/*  307 */       XmlSchema xs = (XmlSchema)this.annotationReader.getPackageAnnotation(XmlSchema.class, e.getKey(), null);
/*  308 */       if (xs != null && 
/*  309 */         xs.xmlns() != null && (xs.xmlns()).length > 0) {
/*  310 */         if (this.xmlNsSet == null)
/*  311 */           this.xmlNsSet = new HashSet<>(); 
/*  312 */         this.xmlNsSet.addAll(Arrays.asList(xs.xmlns()));
/*      */       } 
/*      */ 
/*      */       
/*  316 */       if (bi.isElement()) {
/*  317 */         this.rootMap.put(((RuntimeClassInfo)e.getValue()).getElementName(), bi);
/*      */       }
/*  319 */       for (QName qn : bi.getTypeNames()) {
/*  320 */         this.typeMap.put(qn, bi);
/*      */       }
/*      */     } 
/*      */     
/*  324 */     for (RuntimeElementInfo n : typeSet.getAllElements()) {
/*  325 */       ElementBeanInfoImpl bi = getOrCreate(n);
/*  326 */       if (n.getScope() == null) {
/*  327 */         this.rootMap.put(n.getElementName(), bi);
/*      */       }
/*  329 */       RuntimeClassInfo scope = n.getScope();
/*  330 */       Class scopeClazz = (scope == null) ? null : (Class)scope.getClazz();
/*  331 */       Map<QName, ElementBeanInfoImpl> m = this.elements.get(scopeClazz);
/*  332 */       if (m == null) {
/*  333 */         m = new LinkedHashMap<>();
/*  334 */         this.elements.put(scopeClazz, m);
/*      */       } 
/*  336 */       m.put(n.getElementName(), bi);
/*      */     } 
/*      */ 
/*      */     
/*  340 */     this.beanInfoMap.put(JAXBElement.class, new ElementBeanInfoImpl(this));
/*      */     
/*  342 */     this.beanInfoMap.put(CompositeStructure.class, new CompositeStructureBeanInfo(this));
/*      */     
/*  344 */     getOrCreate((RuntimeTypeInfo)typeSet.getAnyTypeInfo());
/*      */ 
/*      */     
/*  347 */     for (JaxBeanInfo bi : this.beanInfos.values()) {
/*  348 */       bi.link(this);
/*      */     }
/*      */     
/*  351 */     for (Map.Entry<Class<?>, Class<?>> e : RuntimeUtil.primitiveToBox.entrySet()) {
/*  352 */       this.beanInfoMap.put(e.getKey(), this.beanInfoMap.get(e.getValue()));
/*      */     }
/*      */     
/*  355 */     Navigator<Type, Class<?>, Field, Method> nav = typeSet.getNavigator();
/*      */     
/*  357 */     for (TypeReference tr : typeRefs) {
/*  358 */       InternalBridge<?> bridge; XmlJavaTypeAdapter xjta = (XmlJavaTypeAdapter)tr.get(XmlJavaTypeAdapter.class);
/*  359 */       Adapter<Type, Class<?>> a = null;
/*  360 */       XmlList xl = (XmlList)tr.get(XmlList.class);
/*      */ 
/*      */       
/*  363 */       Class<?> erasedType = (Class)nav.erasure(tr.type);
/*      */       
/*  365 */       if (xjta != null) {
/*  366 */         a = new Adapter(xjta.value(), nav);
/*      */       }
/*  368 */       if (tr.get(XmlAttachmentRef.class) != null) {
/*  369 */         a = new Adapter(SwaRefAdapter.class, nav);
/*  370 */         this.hasSwaRef = true;
/*      */       } 
/*      */       
/*  373 */       if (a != null) {
/*  374 */         erasedType = (Class)nav.erasure(a.defaultType);
/*      */       }
/*      */       
/*  377 */       Name name = this.nameBuilder.createElementName(tr.tagName);
/*      */ 
/*      */       
/*  380 */       if (xl == null) {
/*  381 */         bridge = new BridgeImpl(this, name, getBeanInfo(erasedType, true), tr);
/*      */       } else {
/*  383 */         bridge = new BridgeImpl(this, name, new ValueListBeanInfoImpl(this, erasedType), tr);
/*      */       } 
/*  385 */       if (a != null) {
/*  386 */         bridge = new BridgeAdapter<>(bridge, (Class<? extends XmlAdapter<?, ?>>)a.adapterType);
/*      */       }
/*  388 */       this.bridges.put(tr, bridge);
/*      */     } 
/*      */     
/*  391 */     this.nameList = this.nameBuilder.conclude();
/*      */     
/*  393 */     for (JaxBeanInfo bi : this.beanInfos.values()) {
/*  394 */       bi.wrapUp();
/*      */     }
/*      */     
/*  397 */     this.nameBuilder = null;
/*  398 */     this.beanInfos = null; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean hasSwaRef() {
/*  405 */     return this.hasSwaRef;
/*      */   }
/*      */   
/*      */   public RuntimeTypeInfoSet getRuntimeTypeInfoSet() {
/*      */     try {
/*  410 */       return getTypeInfoSet();
/*  411 */     } catch (IllegalAnnotationsException e) {
/*      */       
/*  413 */       throw new AssertionError(e);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public RuntimeTypeInfoSet getTypeInfoSet() throws IllegalAnnotationsException {
/*  423 */     if (this.typeInfoSetCache != null) {
/*  424 */       RuntimeTypeInfoSet runtimeTypeInfoSet = this.typeInfoSetCache.get();
/*  425 */       if (runtimeTypeInfoSet != null) {
/*  426 */         return runtimeTypeInfoSet;
/*      */       }
/*      */     } 
/*  429 */     RuntimeModelBuilder builder = new RuntimeModelBuilder(this, this.annotationReader, this.subclassReplacements, this.defaultNsUri);
/*      */     
/*  431 */     IllegalAnnotationsException.Builder errorHandler = new IllegalAnnotationsException.Builder();
/*  432 */     builder.setErrorHandler(errorHandler);
/*      */     
/*  434 */     for (Class<CompositeStructure> c : this.classes) {
/*  435 */       if (c != CompositeStructure.class)
/*      */       {
/*      */ 
/*      */         
/*  439 */         builder.getTypeInfo(new Ref(c));
/*      */       }
/*      */     } 
/*  442 */     this.hasSwaRef |= builder.hasSwaRef;
/*  443 */     RuntimeTypeInfoSet r = builder.link();
/*      */     
/*  445 */     errorHandler.check();
/*  446 */     assert r != null : "if no error was reported, the link must be a success";
/*      */     
/*  448 */     this.typeInfoSetCache = new WeakReference<>(r);
/*      */     
/*  450 */     return r;
/*      */   }
/*      */ 
/*      */   
/*      */   public ElementBeanInfoImpl getElement(Class scope, QName name) {
/*  455 */     Map<QName, ElementBeanInfoImpl> m = this.elements.get(scope);
/*  456 */     if (m != null) {
/*  457 */       ElementBeanInfoImpl bi = m.get(name);
/*  458 */       if (bi != null)
/*  459 */         return bi; 
/*      */     } 
/*  461 */     m = this.elements.get(null);
/*  462 */     return m.get(name);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private ElementBeanInfoImpl getOrCreate(RuntimeElementInfo rei) {
/*  470 */     JaxBeanInfo bi = this.beanInfos.get(rei);
/*  471 */     if (bi != null) return (ElementBeanInfoImpl)bi;
/*      */ 
/*      */     
/*  474 */     return new ElementBeanInfoImpl(this, rei);
/*      */   }
/*      */   
/*      */   protected JaxBeanInfo getOrCreate(RuntimeEnumLeafInfo eli) {
/*  478 */     JaxBeanInfo bi = this.beanInfos.get(eli);
/*  479 */     if (bi != null) return bi; 
/*  480 */     bi = new LeafBeanInfoImpl(this, (RuntimeLeafInfo)eli);
/*  481 */     this.beanInfoMap.put(bi.jaxbType, bi);
/*  482 */     return bi;
/*      */   }
/*      */   
/*      */   protected ClassBeanInfoImpl getOrCreate(RuntimeClassInfo ci) {
/*  486 */     ClassBeanInfoImpl bi = (ClassBeanInfoImpl)this.beanInfos.get(ci);
/*  487 */     if (bi != null) return bi; 
/*  488 */     bi = new ClassBeanInfoImpl(this, ci);
/*  489 */     this.beanInfoMap.put(bi.jaxbType, bi);
/*  490 */     return bi;
/*      */   }
/*      */   
/*      */   protected JaxBeanInfo getOrCreate(RuntimeArrayInfo ai) {
/*  494 */     JaxBeanInfo abi = this.beanInfos.get(ai);
/*  495 */     if (abi != null) return abi;
/*      */     
/*  497 */     abi = new ArrayBeanInfoImpl(this, ai);
/*      */     
/*  499 */     this.beanInfoMap.put(ai.getType(), abi);
/*  500 */     return abi;
/*      */   }
/*      */   
/*      */   public JaxBeanInfo getOrCreate(RuntimeTypeInfo e) {
/*  504 */     if (e instanceof RuntimeElementInfo)
/*  505 */       return getOrCreate((RuntimeElementInfo)e); 
/*  506 */     if (e instanceof RuntimeClassInfo)
/*  507 */       return getOrCreate((RuntimeClassInfo)e); 
/*  508 */     if (e instanceof RuntimeLeafInfo) {
/*  509 */       JaxBeanInfo bi = this.beanInfos.get(e);
/*  510 */       assert bi != null;
/*  511 */       return bi;
/*      */     } 
/*  513 */     if (e instanceof RuntimeArrayInfo)
/*  514 */       return getOrCreate((RuntimeArrayInfo)e); 
/*  515 */     if (e.getType() == Object.class) {
/*      */       
/*  517 */       JaxBeanInfo bi = this.beanInfoMap.get(Object.class);
/*  518 */       if (bi == null) {
/*  519 */         bi = new AnyTypeBeanInfo(this, e);
/*  520 */         this.beanInfoMap.put(Object.class, bi);
/*      */       } 
/*  522 */       return bi;
/*      */     } 
/*      */     
/*  525 */     throw new IllegalArgumentException();
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
/*      */   public final JaxBeanInfo getBeanInfo(Object o) {
/*  540 */     for (Class<?> c = o.getClass(); c != Object.class; c = c.getSuperclass()) {
/*  541 */       JaxBeanInfo bi = this.beanInfoMap.get(c);
/*  542 */       if (bi != null) return bi; 
/*      */     } 
/*  544 */     if (o instanceof org.w3c.dom.Element)
/*  545 */       return this.beanInfoMap.get(Object.class); 
/*  546 */     for (Class<?> clazz : o.getClass().getInterfaces()) {
/*  547 */       JaxBeanInfo bi = this.beanInfoMap.get(clazz);
/*  548 */       if (bi != null) return bi; 
/*      */     } 
/*  550 */     return null;
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
/*      */   public final JaxBeanInfo getBeanInfo(Object o, boolean fatal) throws JAXBException {
/*  562 */     JaxBeanInfo bi = getBeanInfo(o);
/*  563 */     if (bi != null) return bi; 
/*  564 */     if (fatal) {
/*  565 */       if (o instanceof Document)
/*  566 */         throw new JAXBException(Messages.ELEMENT_NEEDED_BUT_FOUND_DOCUMENT.format(new Object[] { o.getClass() })); 
/*  567 */       throw new JAXBException(Messages.UNKNOWN_CLASS.format(new Object[] { o.getClass() }));
/*      */     } 
/*  569 */     return null;
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
/*      */   public final <T> JaxBeanInfo<T> getBeanInfo(Class<T> clazz) {
/*  583 */     return this.beanInfoMap.get(clazz);
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
/*      */   public final <T> JaxBeanInfo<T> getBeanInfo(Class<T> clazz, boolean fatal) throws JAXBException {
/*  595 */     JaxBeanInfo<T> bi = getBeanInfo(clazz);
/*  596 */     if (bi != null) return bi; 
/*  597 */     if (fatal)
/*  598 */       throw new JAXBException(clazz.getName() + " is not known to this context"); 
/*  599 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final Loader selectRootLoader(UnmarshallingContext.State state, TagName tag) {
/*  610 */     JaxBeanInfo beanInfo = (JaxBeanInfo)this.rootMap.get(tag.uri, tag.local);
/*  611 */     if (beanInfo == null) {
/*  612 */       return null;
/*      */     }
/*  614 */     return beanInfo.getLoader(this, true);
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
/*      */   public JaxBeanInfo getGlobalType(QName name) {
/*  626 */     return this.typeMap.get(name);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getNearestTypeName(QName name) {
/*  637 */     String[] all = new String[this.typeMap.size()];
/*  638 */     int i = 0;
/*  639 */     for (QName qn : this.typeMap.keySet()) {
/*  640 */       if (qn.getLocalPart().equals(name.getLocalPart()))
/*  641 */         return qn.toString(); 
/*  642 */       all[i++] = qn.toString();
/*      */     } 
/*      */     
/*  645 */     String nearest = EditDistance.findNearest(name.toString(), all);
/*      */     
/*  647 */     if (EditDistance.editDistance(nearest, name.toString()) > 10) {
/*  648 */       return null;
/*      */     }
/*  650 */     return nearest;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Set<QName> getValidRootNames() {
/*  658 */     Set<QName> r = new TreeSet<>(QNAME_COMPARATOR);
/*  659 */     for (QNameMap.Entry e : this.rootMap.entrySet()) {
/*  660 */       r.add(e.createQName());
/*      */     }
/*  662 */     return r;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized Encoded[] getUTF8NameTable() {
/*  671 */     if (this.utf8nameTable == null) {
/*  672 */       Encoded[] x = new Encoded[this.nameList.localNames.length];
/*  673 */       for (int i = 0; i < x.length; i++) {
/*  674 */         Encoded e = new Encoded(this.nameList.localNames[i]);
/*  675 */         e.compact();
/*  676 */         x[i] = e;
/*      */       } 
/*  678 */       this.utf8nameTable = x;
/*      */     } 
/*  680 */     return this.utf8nameTable;
/*      */   }
/*      */   
/*      */   public int getNumberOfLocalNames() {
/*  684 */     return this.nameList.localNames.length;
/*      */   }
/*      */   
/*      */   public int getNumberOfElementNames() {
/*  688 */     return this.nameList.numberOfElementNames;
/*      */   }
/*      */   
/*      */   public int getNumberOfAttributeNames() {
/*  692 */     return this.nameList.numberOfAttributeNames;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static Transformer createTransformer(boolean disableSecureProcessing) {
/*      */     try {
/*  700 */       SAXTransformerFactory tf = (SAXTransformerFactory)XmlFactory.createTransformerFactory(disableSecureProcessing);
/*  701 */       return tf.newTransformer();
/*  702 */     } catch (TransformerConfigurationException e) {
/*  703 */       throw new Error(e);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static TransformerHandler createTransformerHandler(boolean disableSecureProcessing) {
/*      */     try {
/*  712 */       SAXTransformerFactory tf = (SAXTransformerFactory)XmlFactory.createTransformerFactory(disableSecureProcessing);
/*  713 */       return tf.newTransformerHandler();
/*  714 */     } catch (TransformerConfigurationException e) {
/*  715 */       throw new Error(e);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static Document createDom(boolean disableSecurityProcessing) {
/*  723 */     synchronized (JAXBContextImpl.class) {
/*  724 */       if (db == null) {
/*      */         try {
/*  726 */           DocumentBuilderFactory dbf = XmlFactory.createDocumentBuilderFactory(disableSecurityProcessing);
/*  727 */           db = dbf.newDocumentBuilder();
/*  728 */         } catch (ParserConfigurationException e) {
/*      */           
/*  730 */           throw new FactoryConfigurationError(e);
/*      */         } 
/*      */       }
/*  733 */       return db.newDocument();
/*      */     } 
/*      */   }
/*      */   
/*      */   public MarshallerImpl createMarshaller() {
/*  738 */     return new MarshallerImpl(this, null);
/*      */   }
/*      */   
/*      */   public UnmarshallerImpl createUnmarshaller() {
/*  742 */     return new UnmarshallerImpl(this, null);
/*      */   }
/*      */   
/*      */   public Validator createValidator() {
/*  746 */     throw new UnsupportedOperationException(Messages.NOT_IMPLEMENTED_IN_2_0.format(new Object[0]));
/*      */   }
/*      */ 
/*      */   
/*      */   public JAXBIntrospector createJAXBIntrospector() {
/*  751 */     return new JAXBIntrospector() {
/*      */         public boolean isElement(Object object) {
/*  753 */           return (getElementName(object) != null);
/*      */         }
/*      */         
/*      */         public QName getElementName(Object jaxbElement) {
/*      */           try {
/*  758 */             return JAXBContextImpl.this.getElementName(jaxbElement);
/*  759 */           } catch (JAXBException e) {
/*  760 */             return null;
/*      */           } 
/*      */         }
/*      */       };
/*      */   }
/*      */   
/*      */   private NonElement<Type, Class> getXmlType(RuntimeTypeInfoSet tis, TypeReference tr) {
/*  767 */     if (tr == null) {
/*  768 */       throw new IllegalArgumentException();
/*      */     }
/*  770 */     XmlJavaTypeAdapter xjta = (XmlJavaTypeAdapter)tr.get(XmlJavaTypeAdapter.class);
/*  771 */     XmlList xl = (XmlList)tr.get(XmlList.class);
/*      */     
/*  773 */     Ref<Type, Class<?>> ref = new Ref((AnnotationReader)this.annotationReader, tis.getNavigator(), tr.type, xjta, xl);
/*      */     
/*  775 */     return tis.getTypeInfo(ref);
/*      */   }
/*      */ 
/*      */   
/*      */   public void generateEpisode(Result output) {
/*  780 */     if (output == null)
/*  781 */       throw new IllegalArgumentException(); 
/*  782 */     createSchemaGenerator().writeEpisodeFile(ResultFactory.createSerializer(output));
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void generateSchema(SchemaOutputResolver outputResolver) throws IOException {
/*  788 */     if (outputResolver == null) {
/*  789 */       throw new IOException(Messages.NULL_OUTPUT_RESOLVER.format(new Object[0]));
/*      */     }
/*  791 */     final SAXParseException[] e = new SAXParseException[1];
/*  792 */     final SAXParseException[] w = new SAXParseException[1];
/*      */     
/*  794 */     createSchemaGenerator().write(outputResolver, new ErrorListener() {
/*      */           public void error(SAXParseException exception) {
/*  796 */             e[0] = exception;
/*      */           }
/*      */           
/*      */           public void fatalError(SAXParseException exception) {
/*  800 */             e[0] = exception;
/*      */           }
/*      */           
/*      */           public void warning(SAXParseException exception) {
/*  804 */             w[0] = exception;
/*      */           }
/*      */ 
/*      */           
/*      */           public void info(SAXParseException exception) {}
/*      */         });
/*  810 */     if (e[0] != null) {
/*  811 */       IOException x = new IOException(Messages.FAILED_TO_GENERATE_SCHEMA.format(new Object[0]));
/*  812 */       x.initCause(e[0]);
/*  813 */       throw x;
/*      */     } 
/*  815 */     if (w[0] != null) {
/*  816 */       IOException x = new IOException(Messages.ERROR_PROCESSING_SCHEMA.format(new Object[0]));
/*  817 */       x.initCause(w[0]);
/*  818 */       throw x;
/*      */     } 
/*      */   }
/*      */   
/*      */   private XmlSchemaGenerator<Type, Class, Field, Method> createSchemaGenerator() {
/*      */     RuntimeTypeInfoSet tis;
/*      */     try {
/*  825 */       tis = getTypeInfoSet();
/*  826 */     } catch (IllegalAnnotationsException e) {
/*      */       
/*  828 */       throw new AssertionError(e);
/*      */     } 
/*      */ 
/*      */     
/*  832 */     XmlSchemaGenerator<Type, Class<?>, Field, Method> xsdgen = new XmlSchemaGenerator(tis.getNavigator(), (TypeInfoSet)tis);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  837 */     Set<QName> rootTagNames = new HashSet<>();
/*  838 */     for (RuntimeElementInfo ei : tis.getAllElements()) {
/*  839 */       rootTagNames.add(ei.getElementName());
/*      */     }
/*  841 */     for (RuntimeClassInfo ci : tis.beans().values()) {
/*  842 */       if (ci.isElement()) {
/*  843 */         rootTagNames.add(ci.asElement().getElementName());
/*      */       }
/*      */     } 
/*  846 */     for (TypeReference tr : this.bridges.keySet()) {
/*  847 */       if (rootTagNames.contains(tr.tagName)) {
/*      */         continue;
/*      */       }
/*  850 */       if (tr.type == void.class || tr.type == Void.class) {
/*  851 */         xsdgen.add(tr.tagName, false, null); continue;
/*      */       } 
/*  853 */       if (tr.type == CompositeStructure.class) {
/*      */         continue;
/*      */       }
/*  856 */       NonElement<Type, Class<?>> typeInfo = getXmlType(tis, tr);
/*  857 */       xsdgen.add(tr.tagName, !tis.getNavigator().isPrimitive(tr.type), typeInfo);
/*      */     } 
/*      */     
/*  860 */     return xsdgen;
/*      */   }
/*      */   
/*      */   public QName getTypeName(TypeReference tr) {
/*      */     try {
/*  865 */       NonElement<Type, Class<?>> xt = getXmlType(getTypeInfoSet(), tr);
/*  866 */       if (xt == null) throw new IllegalArgumentException(); 
/*  867 */       return xt.getTypeName();
/*  868 */     } catch (IllegalAnnotationsException e) {
/*      */       
/*  870 */       throw new AssertionError(e);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public <T> Binder<T> createBinder(Class<T> domType) {
/*  876 */     if (domType == Node.class) {
/*  877 */       return (Binder)createBinder();
/*      */     }
/*  879 */     return super.createBinder(domType);
/*      */   }
/*      */ 
/*      */   
/*      */   public Binder<Node> createBinder() {
/*  884 */     return new BinderImpl<>(this, (InfosetScanner<Node>)new DOMScanner());
/*      */   }
/*      */   
/*      */   public QName getElementName(Object o) throws JAXBException {
/*  888 */     JaxBeanInfo<Object> bi = getBeanInfo(o, true);
/*  889 */     if (!bi.isElement())
/*  890 */       return null; 
/*  891 */     return new QName(bi.getElementNamespaceURI(o), bi.getElementLocalName(o));
/*      */   }
/*      */   
/*      */   public QName getElementName(Class<?> o) throws JAXBException {
/*  895 */     JaxBeanInfo<?> bi = getBeanInfo(o, true);
/*  896 */     if (!bi.isElement())
/*  897 */       return null; 
/*  898 */     return new QName(bi.getElementNamespaceURI(o), bi.getElementLocalName(o));
/*      */   }
/*      */   
/*      */   public Bridge createBridge(TypeReference ref) {
/*  902 */     return this.bridges.get(ref);
/*      */   }
/*      */   @NotNull
/*      */   public BridgeContext createBridgeContext() {
/*  906 */     return new BridgeContextImpl(this);
/*      */   }
/*      */   
/*      */   public RawAccessor getElementPropertyAccessor(Class<?> wrapperBean, String nsUri, String localName) throws JAXBException {
/*  910 */     JaxBeanInfo<?> bi = getBeanInfo(wrapperBean, true);
/*  911 */     if (!(bi instanceof ClassBeanInfoImpl)) {
/*  912 */       throw new JAXBException(wrapperBean + " is not a bean");
/*      */     }
/*  914 */     for (ClassBeanInfoImpl cb = (ClassBeanInfoImpl)bi; cb != null; cb = cb.superClazz) {
/*  915 */       for (Property p : cb.properties) {
/*  916 */         final Accessor acc = p.getElementPropertyAccessor(nsUri, localName);
/*  917 */         if (acc != null)
/*  918 */           return new RawAccessor()
/*      */             {
/*      */ 
/*      */ 
/*      */               
/*      */               public Object get(Object bean) throws AccessorException
/*      */               {
/*  925 */                 return acc.getUnadapted(bean);
/*      */               }
/*      */               
/*      */               public void set(Object bean, Object value) throws AccessorException {
/*  929 */                 acc.setUnadapted(bean, value);
/*      */               }
/*      */             }; 
/*      */       } 
/*      */     } 
/*  934 */     throw new JAXBException(new QName(nsUri, localName) + " is not a valid property on " + wrapperBean);
/*      */   }
/*      */   
/*      */   public List<String> getKnownNamespaceURIs() {
/*  938 */     return Arrays.asList(this.nameList.namespaceURIs);
/*      */   }
/*      */   
/*      */   public String getBuildId() {
/*  942 */     Package pkg = getClass().getPackage();
/*  943 */     if (pkg == null) return null; 
/*  944 */     return pkg.getImplementationVersion();
/*      */   }
/*      */ 
/*      */   
/*      */   public String toString() {
/*  949 */     StringBuilder buf = new StringBuilder(Which.which(getClass()) + " Build-Id: " + getBuildId());
/*  950 */     buf.append("\nClasses known to this context:\n");
/*      */     
/*  952 */     Set<String> names = new TreeSet<>();
/*      */     
/*  954 */     for (Class key : this.beanInfoMap.keySet()) {
/*  955 */       names.add(key.getName());
/*      */     }
/*  957 */     for (String name : names) {
/*  958 */       buf.append("  ").append(name).append('\n');
/*      */     }
/*  960 */     return buf.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getXMIMEContentType(Object o) {
/*  968 */     JaxBeanInfo bi = getBeanInfo(o);
/*  969 */     if (!(bi instanceof ClassBeanInfoImpl)) {
/*  970 */       return null;
/*      */     }
/*  972 */     ClassBeanInfoImpl cb = (ClassBeanInfoImpl)bi;
/*  973 */     for (Property p : cb.properties) {
/*  974 */       if (p instanceof AttributeProperty) {
/*  975 */         AttributeProperty ap = (AttributeProperty)p;
/*  976 */         if (ap.attName.equals("http://www.w3.org/2005/05/xmlmime", "contentType"))
/*      */           try {
/*  978 */             return (String)ap.xacc.print(o);
/*  979 */           } catch (AccessorException e) {
/*  980 */             return null;
/*  981 */           } catch (SAXException e) {
/*  982 */             return null;
/*  983 */           } catch (ClassCastException e) {
/*  984 */             return null;
/*      */           }  
/*      */       } 
/*      */     } 
/*  988 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JAXBContextImpl createAugmented(Class<?> clazz) throws JAXBException {
/*  995 */     Class[] newList = new Class[this.classes.length + 1];
/*  996 */     System.arraycopy(this.classes, 0, newList, 0, this.classes.length);
/*  997 */     newList[this.classes.length] = clazz;
/*      */     
/*  999 */     JAXBContextBuilder builder = new JAXBContextBuilder(this);
/* 1000 */     builder.setClasses(newList);
/* 1001 */     return builder.build();
/*      */   }
/*      */   
/* 1004 */   private static final Comparator<QName> QNAME_COMPARATOR = new Comparator<QName>() {
/*      */       public int compare(QName lhs, QName rhs) {
/* 1006 */         int r = lhs.getLocalPart().compareTo(rhs.getLocalPart());
/* 1007 */         if (r != 0) return r;
/*      */         
/* 1009 */         return lhs.getNamespaceURI().compareTo(rhs.getNamespaceURI());
/*      */       }
/*      */     };
/*      */   
/*      */   public static class JAXBContextBuilder
/*      */   {
/*      */     private boolean retainPropertyInfo = false;
/*      */     private boolean supressAccessorWarnings = false;
/* 1017 */     private String defaultNsUri = ""; @NotNull
/* 1018 */     private RuntimeAnnotationReader annotationReader = (RuntimeAnnotationReader)new RuntimeInlineAnnotationReader(); @NotNull
/* 1019 */     private Map<Class, Class> subclassReplacements = Collections.emptyMap();
/*      */     
/*      */     private boolean c14nSupport = false;
/*      */     
/*      */     private Class[] classes;
/*      */     private Collection<TypeReference> typeRefs;
/*      */     private boolean xmlAccessorFactorySupport = false;
/*      */     private boolean allNillable;
/*      */     private boolean improvedXsiTypeHandling = true;
/*      */     private boolean disableSecurityProcessing = true;
/*      */     
/*      */     public JAXBContextBuilder(JAXBContextImpl baseImpl) {
/* 1031 */       this.supressAccessorWarnings = baseImpl.supressAccessorWarnings;
/* 1032 */       this.retainPropertyInfo = baseImpl.retainPropertyInfo;
/* 1033 */       this.defaultNsUri = baseImpl.defaultNsUri;
/* 1034 */       this.annotationReader = baseImpl.annotationReader;
/* 1035 */       this.subclassReplacements = baseImpl.subclassReplacements;
/* 1036 */       this.c14nSupport = baseImpl.c14nSupport;
/* 1037 */       this.classes = baseImpl.classes;
/* 1038 */       this.typeRefs = baseImpl.bridges.keySet();
/* 1039 */       this.xmlAccessorFactorySupport = baseImpl.xmlAccessorFactorySupport;
/* 1040 */       this.allNillable = baseImpl.allNillable;
/* 1041 */       this.disableSecurityProcessing = baseImpl.disableSecurityProcessing;
/*      */     }
/*      */     
/*      */     public JAXBContextBuilder setRetainPropertyInfo(boolean val) {
/* 1045 */       this.retainPropertyInfo = val;
/* 1046 */       return this;
/*      */     }
/*      */     
/*      */     public JAXBContextBuilder setSupressAccessorWarnings(boolean val) {
/* 1050 */       this.supressAccessorWarnings = val;
/* 1051 */       return this;
/*      */     }
/*      */     
/*      */     public JAXBContextBuilder setC14NSupport(boolean val) {
/* 1055 */       this.c14nSupport = val;
/* 1056 */       return this;
/*      */     }
/*      */     
/*      */     public JAXBContextBuilder setXmlAccessorFactorySupport(boolean val) {
/* 1060 */       this.xmlAccessorFactorySupport = val;
/* 1061 */       return this;
/*      */     }
/*      */     
/*      */     public JAXBContextBuilder setDefaultNsUri(String val) {
/* 1065 */       this.defaultNsUri = val;
/* 1066 */       return this;
/*      */     }
/*      */     
/*      */     public JAXBContextBuilder setAllNillable(boolean val) {
/* 1070 */       this.allNillable = val;
/* 1071 */       return this;
/*      */     }
/*      */     
/*      */     public JAXBContextBuilder setClasses(Class[] val) {
/* 1075 */       this.classes = val;
/* 1076 */       return this;
/*      */     }
/*      */     
/*      */     public JAXBContextBuilder setAnnotationReader(RuntimeAnnotationReader val) {
/* 1080 */       this.annotationReader = val;
/* 1081 */       return this;
/*      */     }
/*      */     
/*      */     public JAXBContextBuilder setSubclassReplacements(Map<Class<?>, Class<?>> val) {
/* 1085 */       this.subclassReplacements = val;
/* 1086 */       return this;
/*      */     }
/*      */     
/*      */     public JAXBContextBuilder setTypeRefs(Collection<TypeReference> val) {
/* 1090 */       this.typeRefs = val;
/* 1091 */       return this;
/*      */     }
/*      */     
/*      */     public JAXBContextBuilder setImprovedXsiTypeHandling(boolean val) {
/* 1095 */       this.improvedXsiTypeHandling = val;
/* 1096 */       return this;
/*      */     }
/*      */     
/*      */     public JAXBContextBuilder setDisableSecurityProcessing(boolean val) {
/* 1100 */       this.disableSecurityProcessing = val;
/* 1101 */       return this;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public JAXBContextImpl build() throws JAXBException {
/* 1107 */       if (this.defaultNsUri == null) {
/* 1108 */         this.defaultNsUri = "";
/*      */       }
/*      */       
/* 1111 */       if (this.subclassReplacements == null) {
/* 1112 */         this.subclassReplacements = Collections.emptyMap();
/*      */       }
/*      */       
/* 1115 */       if (this.annotationReader == null) {
/* 1116 */         this.annotationReader = (RuntimeAnnotationReader)new RuntimeInlineAnnotationReader();
/*      */       }
/*      */       
/* 1119 */       if (this.typeRefs == null) {
/* 1120 */         this.typeRefs = Collections.emptyList();
/*      */       }
/*      */       
/* 1123 */       return new JAXBContextImpl(this);
/*      */     }
/*      */     
/*      */     public JAXBContextBuilder() {}
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/bind/v2/runtime/JAXBContextImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */