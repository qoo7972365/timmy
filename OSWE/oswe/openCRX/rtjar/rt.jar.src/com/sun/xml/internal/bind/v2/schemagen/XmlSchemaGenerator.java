/*      */ package com.sun.xml.internal.bind.v2.schemagen;
/*      */ 
/*      */ import com.sun.istack.internal.NotNull;
/*      */ import com.sun.istack.internal.Nullable;
/*      */ import com.sun.xml.internal.bind.Util;
/*      */ import com.sun.xml.internal.bind.api.CompositeStructure;
/*      */ import com.sun.xml.internal.bind.api.ErrorListener;
/*      */ import com.sun.xml.internal.bind.v2.TODO;
/*      */ import com.sun.xml.internal.bind.v2.model.core.Adapter;
/*      */ import com.sun.xml.internal.bind.v2.model.core.ArrayInfo;
/*      */ import com.sun.xml.internal.bind.v2.model.core.AttributePropertyInfo;
/*      */ import com.sun.xml.internal.bind.v2.model.core.ClassInfo;
/*      */ import com.sun.xml.internal.bind.v2.model.core.Element;
/*      */ import com.sun.xml.internal.bind.v2.model.core.ElementInfo;
/*      */ import com.sun.xml.internal.bind.v2.model.core.ElementPropertyInfo;
/*      */ import com.sun.xml.internal.bind.v2.model.core.EnumConstant;
/*      */ import com.sun.xml.internal.bind.v2.model.core.EnumLeafInfo;
/*      */ import com.sun.xml.internal.bind.v2.model.core.ID;
/*      */ import com.sun.xml.internal.bind.v2.model.core.MapPropertyInfo;
/*      */ import com.sun.xml.internal.bind.v2.model.core.MaybeElement;
/*      */ import com.sun.xml.internal.bind.v2.model.core.NonElement;
/*      */ import com.sun.xml.internal.bind.v2.model.core.NonElementRef;
/*      */ import com.sun.xml.internal.bind.v2.model.core.PropertyInfo;
/*      */ import com.sun.xml.internal.bind.v2.model.core.PropertyKind;
/*      */ import com.sun.xml.internal.bind.v2.model.core.ReferencePropertyInfo;
/*      */ import com.sun.xml.internal.bind.v2.model.core.TypeInfo;
/*      */ import com.sun.xml.internal.bind.v2.model.core.TypeInfoSet;
/*      */ import com.sun.xml.internal.bind.v2.model.core.TypeRef;
/*      */ import com.sun.xml.internal.bind.v2.model.core.ValuePropertyInfo;
/*      */ import com.sun.xml.internal.bind.v2.model.core.WildcardMode;
/*      */ import com.sun.xml.internal.bind.v2.model.impl.ClassInfoImpl;
/*      */ import com.sun.xml.internal.bind.v2.model.nav.Navigator;
/*      */ import com.sun.xml.internal.bind.v2.runtime.SwaRefAdapter;
/*      */ import com.sun.xml.internal.bind.v2.schemagen.episode.Bindings;
/*      */ import com.sun.xml.internal.bind.v2.schemagen.xmlschema.Any;
/*      */ import com.sun.xml.internal.bind.v2.schemagen.xmlschema.AttrDecls;
/*      */ import com.sun.xml.internal.bind.v2.schemagen.xmlschema.AttributeType;
/*      */ import com.sun.xml.internal.bind.v2.schemagen.xmlschema.ComplexExtension;
/*      */ import com.sun.xml.internal.bind.v2.schemagen.xmlschema.ComplexType;
/*      */ import com.sun.xml.internal.bind.v2.schemagen.xmlschema.ComplexTypeHost;
/*      */ import com.sun.xml.internal.bind.v2.schemagen.xmlschema.ContentModelContainer;
/*      */ import com.sun.xml.internal.bind.v2.schemagen.xmlschema.ExplicitGroup;
/*      */ import com.sun.xml.internal.bind.v2.schemagen.xmlschema.Import;
/*      */ import com.sun.xml.internal.bind.v2.schemagen.xmlschema.List;
/*      */ import com.sun.xml.internal.bind.v2.schemagen.xmlschema.LocalAttribute;
/*      */ import com.sun.xml.internal.bind.v2.schemagen.xmlschema.LocalElement;
/*      */ import com.sun.xml.internal.bind.v2.schemagen.xmlschema.Occurs;
/*      */ import com.sun.xml.internal.bind.v2.schemagen.xmlschema.Schema;
/*      */ import com.sun.xml.internal.bind.v2.schemagen.xmlschema.SimpleExtension;
/*      */ import com.sun.xml.internal.bind.v2.schemagen.xmlschema.SimpleRestriction;
/*      */ import com.sun.xml.internal.bind.v2.schemagen.xmlschema.SimpleType;
/*      */ import com.sun.xml.internal.bind.v2.schemagen.xmlschema.SimpleTypeHost;
/*      */ import com.sun.xml.internal.bind.v2.schemagen.xmlschema.TopLevelAttribute;
/*      */ import com.sun.xml.internal.bind.v2.schemagen.xmlschema.TopLevelElement;
/*      */ import com.sun.xml.internal.bind.v2.schemagen.xmlschema.TypeDefParticle;
/*      */ import com.sun.xml.internal.bind.v2.schemagen.xmlschema.TypeHost;
/*      */ import com.sun.xml.internal.bind.v2.util.CollisionCheckStack;
/*      */ import com.sun.xml.internal.bind.v2.util.StackRecorder;
/*      */ import com.sun.xml.internal.txw2.TXW;
/*      */ import com.sun.xml.internal.txw2.TxwException;
/*      */ import com.sun.xml.internal.txw2.TypedXmlWriter;
/*      */ import com.sun.xml.internal.txw2.output.ResultFactory;
/*      */ import com.sun.xml.internal.txw2.output.XmlSerializer;
/*      */ import java.io.File;
/*      */ import java.io.IOException;
/*      */ import java.io.OutputStream;
/*      */ import java.io.Writer;
/*      */ import java.net.URI;
/*      */ import java.net.URISyntaxException;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Collection;
/*      */ import java.util.Comparator;
/*      */ import java.util.HashMap;
/*      */ import java.util.HashSet;
/*      */ import java.util.LinkedHashSet;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Set;
/*      */ import java.util.TreeMap;
/*      */ import java.util.logging.Level;
/*      */ import java.util.logging.Logger;
/*      */ import javax.activation.MimeType;
/*      */ import javax.xml.bind.SchemaOutputResolver;
/*      */ import javax.xml.bind.annotation.XmlElement;
/*      */ import javax.xml.namespace.QName;
/*      */ import javax.xml.transform.Result;
/*      */ import javax.xml.transform.stream.StreamResult;
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
/*      */ public final class XmlSchemaGenerator<T, C, F, M>
/*      */ {
/*  133 */   private static final Logger logger = Util.getClassLogger();
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
/*  144 */   private final Map<String, Namespace> namespaces = new TreeMap<>(NAMESPACE_COMPARATOR);
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private ErrorListener errorListener;
/*      */ 
/*      */ 
/*      */   
/*      */   private Navigator<T, C, F, M> navigator;
/*      */ 
/*      */ 
/*      */   
/*      */   private final TypeInfoSet<T, C, F, M> types;
/*      */ 
/*      */ 
/*      */   
/*      */   private final NonElement<T, C> stringType;
/*      */ 
/*      */ 
/*      */   
/*      */   private final NonElement<T, C> anyType;
/*      */ 
/*      */ 
/*      */   
/*  169 */   private final CollisionCheckStack<ClassInfo<T, C>> collisionChecker = new CollisionCheckStack();
/*      */   
/*      */   public XmlSchemaGenerator(Navigator<T, C, F, M> navigator, TypeInfoSet<T, C, F, M> types) {
/*  172 */     this.navigator = navigator;
/*  173 */     this.types = types;
/*      */     
/*  175 */     this.stringType = types.getTypeInfo(navigator.ref(String.class));
/*  176 */     this.anyType = types.getAnyTypeInfo();
/*      */ 
/*      */     
/*  179 */     for (ClassInfo<T, C> ci : (Iterable<ClassInfo<T, C>>)types.beans().values())
/*  180 */       add(ci); 
/*  181 */     for (ElementInfo<T, C> ei1 : (Iterable<ElementInfo<T, C>>)types.getElementMappings(null).values())
/*  182 */       add(ei1); 
/*  183 */     for (EnumLeafInfo<T, C> ei : (Iterable<EnumLeafInfo<T, C>>)types.enums().values())
/*  184 */       add(ei); 
/*  185 */     for (ArrayInfo<T, C> a : (Iterable<ArrayInfo<T, C>>)types.arrays().values())
/*  186 */       add(a); 
/*      */   }
/*      */   
/*      */   private Namespace getNamespace(String uri) {
/*  190 */     Namespace n = this.namespaces.get(uri);
/*  191 */     if (n == null)
/*  192 */       this.namespaces.put(uri, n = new Namespace(uri)); 
/*  193 */     return n;
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
/*      */   public void add(ClassInfo<T, C> clazz) {
/*  205 */     assert clazz != null;
/*      */     
/*  207 */     String nsUri = null;
/*      */     
/*  209 */     if (clazz.getClazz() == this.navigator.asDecl(CompositeStructure.class)) {
/*      */       return;
/*      */     }
/*  212 */     if (clazz.isElement()) {
/*      */       
/*  214 */       nsUri = clazz.getElementName().getNamespaceURI();
/*  215 */       Namespace ns = getNamespace(nsUri);
/*  216 */       ns.classes.add(clazz);
/*  217 */       ns.addDependencyTo(clazz.getTypeName());
/*      */ 
/*      */       
/*  220 */       add(clazz.getElementName(), false, (NonElement<T, C>)clazz);
/*      */     } 
/*      */     
/*  223 */     QName tn = clazz.getTypeName();
/*  224 */     if (tn != null) {
/*  225 */       nsUri = tn.getNamespaceURI();
/*      */     
/*      */     }
/*  228 */     else if (nsUri == null) {
/*      */       return;
/*      */     } 
/*      */     
/*  232 */     Namespace n = getNamespace(nsUri);
/*  233 */     n.classes.add(clazz);
/*      */ 
/*      */     
/*  236 */     for (PropertyInfo<T, C> p : (Iterable<PropertyInfo<T, C>>)clazz.getProperties()) {
/*  237 */       n.processForeignNamespaces(p, 1);
/*  238 */       if (p instanceof AttributePropertyInfo) {
/*  239 */         AttributePropertyInfo<T, C> ap = (AttributePropertyInfo<T, C>)p;
/*  240 */         String aUri = ap.getXmlName().getNamespaceURI();
/*  241 */         if (aUri.length() > 0) {
/*      */           
/*  243 */           getNamespace(aUri).addGlobalAttribute(ap);
/*  244 */           n.addDependencyTo(ap.getXmlName());
/*      */         } 
/*      */       } 
/*  247 */       if (p instanceof ElementPropertyInfo) {
/*  248 */         ElementPropertyInfo<T, C> ep = (ElementPropertyInfo<T, C>)p;
/*  249 */         for (TypeRef<T, C> tref : (Iterable<TypeRef<T, C>>)ep.getTypes()) {
/*  250 */           String eUri = tref.getTagName().getNamespaceURI();
/*  251 */           if (eUri.length() > 0 && !eUri.equals(n.uri)) {
/*  252 */             getNamespace(eUri).addGlobalElement(tref);
/*  253 */             n.addDependencyTo(tref.getTagName());
/*      */           } 
/*      */         } 
/*      */       } 
/*      */       
/*  258 */       if (generateSwaRefAdapter(p)) {
/*  259 */         n.useSwaRef = true;
/*      */       }
/*  261 */       MimeType mimeType = p.getExpectedMimeType();
/*  262 */       if (mimeType != null) {
/*  263 */         n.useMimeNs = true;
/*      */       }
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  269 */     ClassInfo<T, C> bc = clazz.getBaseClass();
/*  270 */     if (bc != null) {
/*  271 */       add(bc);
/*  272 */       n.addDependencyTo(bc.getTypeName());
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void add(ElementInfo<T, C> elem) {
/*      */     ElementInfo ei;
/*  280 */     assert elem != null;
/*      */ 
/*      */     
/*  283 */     boolean nillable = false;
/*      */     
/*  285 */     QName name = elem.getElementName();
/*  286 */     Namespace n = getNamespace(name.getNamespaceURI());
/*      */ 
/*      */     
/*  289 */     if (elem.getScope() != null) {
/*  290 */       ei = this.types.getElementInfo(elem.getScope().getClazz(), name);
/*      */     } else {
/*  292 */       ei = this.types.getElementInfo(null, name);
/*      */     } 
/*      */     
/*  295 */     XmlElement xmlElem = (XmlElement)ei.getProperty().readAnnotation(XmlElement.class);
/*      */     
/*  297 */     if (xmlElem == null) {
/*  298 */       nillable = false;
/*      */     } else {
/*  300 */       nillable = xmlElem.nillable();
/*      */     } 
/*      */     
/*  303 */     n.getClass(); n.elementDecls.put(name.getLocalPart(), new Namespace.ElementWithType(nillable, elem.getContentType()));
/*      */ 
/*      */     
/*  306 */     n.processForeignNamespaces((PropertyInfo<T, C>)elem.getProperty(), 1);
/*      */   }
/*      */   
/*      */   public void add(EnumLeafInfo<T, C> envm) {
/*  310 */     assert envm != null;
/*      */     
/*  312 */     String nsUri = null;
/*      */     
/*  314 */     if (envm.isElement()) {
/*      */       
/*  316 */       nsUri = envm.getElementName().getNamespaceURI();
/*  317 */       Namespace ns = getNamespace(nsUri);
/*  318 */       ns.enums.add(envm);
/*  319 */       ns.addDependencyTo(envm.getTypeName());
/*      */ 
/*      */       
/*  322 */       add(envm.getElementName(), false, (NonElement<T, C>)envm);
/*      */     } 
/*      */     
/*  325 */     QName typeName = envm.getTypeName();
/*  326 */     if (typeName != null) {
/*  327 */       nsUri = typeName.getNamespaceURI();
/*      */     }
/*  329 */     else if (nsUri == null) {
/*      */       return;
/*      */     } 
/*      */     
/*  333 */     Namespace n = getNamespace(nsUri);
/*  334 */     n.enums.add(envm);
/*      */ 
/*      */     
/*  337 */     n.addDependencyTo(envm.getBaseType().getTypeName());
/*      */   }
/*      */   
/*      */   public void add(ArrayInfo<T, C> a) {
/*  341 */     assert a != null;
/*      */     
/*  343 */     String namespaceURI = a.getTypeName().getNamespaceURI();
/*  344 */     Namespace n = getNamespace(namespaceURI);
/*  345 */     n.arrays.add(a);
/*      */ 
/*      */     
/*  348 */     n.addDependencyTo(a.getItemType().getTypeName());
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
/*      */   public void add(QName tagName, boolean isNillable, NonElement<T, C> type) {
/*  362 */     if (type != null && type.getType() == this.navigator.ref(CompositeStructure.class)) {
/*      */       return;
/*      */     }
/*      */     
/*  366 */     Namespace n = getNamespace(tagName.getNamespaceURI());
/*  367 */     n.getClass(); n.elementDecls.put(tagName.getLocalPart(), new Namespace.ElementWithType(isNillable, type));
/*      */ 
/*      */     
/*  370 */     if (type != null) {
/*  371 */       n.addDependencyTo(type.getTypeName());
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void writeEpisodeFile(XmlSerializer out) {
/*  378 */     Bindings root = (Bindings)TXW.create(Bindings.class, out);
/*      */     
/*  380 */     if (this.namespaces.containsKey(""))
/*  381 */       root._namespace("http://java.sun.com/xml/ns/jaxb", "jaxb"); 
/*  382 */     root.version("2.1");
/*      */ 
/*      */ 
/*      */     
/*  386 */     for (Map.Entry<String, Namespace> e : this.namespaces.entrySet()) {
/*  387 */       String prefix; Bindings group = root.bindings();
/*      */ 
/*      */       
/*  390 */       String tns = e.getKey();
/*  391 */       if (!tns.equals("")) {
/*  392 */         group._namespace(tns, "tns");
/*  393 */         prefix = "tns:";
/*      */       } else {
/*  395 */         prefix = "";
/*      */       } 
/*      */       
/*  398 */       group.scd("x-schema::" + (tns.equals("") ? "" : "tns"));
/*  399 */       group.schemaBindings().map(false);
/*      */       
/*  401 */       for (ClassInfo<T, C> ci : (Iterable<ClassInfo<T, C>>)(e.getValue()).classes) {
/*  402 */         if (ci.getTypeName() == null)
/*      */           continue; 
/*  404 */         if (ci.getTypeName().getNamespaceURI().equals(tns)) {
/*  405 */           Bindings child = group.bindings();
/*  406 */           child.scd('~' + prefix + ci.getTypeName().getLocalPart());
/*  407 */           child.klass().ref(ci.getName());
/*      */         } 
/*      */         
/*  410 */         if (ci.isElement() && ci.getElementName().getNamespaceURI().equals(tns)) {
/*  411 */           Bindings child = group.bindings();
/*  412 */           child.scd(prefix + ci.getElementName().getLocalPart());
/*  413 */           child.klass().ref(ci.getName());
/*      */         } 
/*      */       } 
/*      */       
/*  417 */       for (EnumLeafInfo<T, C> en : (Iterable<EnumLeafInfo<T, C>>)(e.getValue()).enums) {
/*  418 */         if (en.getTypeName() == null)
/*      */           continue; 
/*  420 */         Bindings child = group.bindings();
/*  421 */         child.scd('~' + prefix + en.getTypeName().getLocalPart());
/*  422 */         child.klass().ref(this.navigator.getClassName(en.getClazz()));
/*      */       } 
/*      */       
/*  425 */       group.commit(true);
/*      */     } 
/*      */     
/*  428 */     root.commit();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void write(SchemaOutputResolver resolver, ErrorListener errorListener) throws IOException {
/*  435 */     if (resolver == null) {
/*  436 */       throw new IllegalArgumentException();
/*      */     }
/*  438 */     if (logger.isLoggable(Level.FINE))
/*      */     {
/*  440 */       logger.log(Level.FINE, "Writing XML Schema for " + toString(), (Throwable)new StackRecorder());
/*      */     }
/*      */ 
/*      */     
/*  444 */     resolver = new FoolProofResolver(resolver);
/*  445 */     this.errorListener = errorListener;
/*      */     
/*  447 */     Map<String, String> schemaLocations = this.types.getSchemaLocations();
/*      */     
/*  449 */     Map<Namespace, Result> out = new HashMap<>();
/*  450 */     Map<Namespace, String> systemIds = new HashMap<>();
/*      */ 
/*      */ 
/*      */     
/*  454 */     this.namespaces.remove("http://www.w3.org/2001/XMLSchema");
/*      */ 
/*      */ 
/*      */     
/*  458 */     for (Namespace n : this.namespaces.values()) {
/*  459 */       String schemaLocation = schemaLocations.get(n.uri);
/*  460 */       if (schemaLocation != null) {
/*  461 */         systemIds.put(n, schemaLocation);
/*      */       } else {
/*  463 */         Result output = resolver.createOutput(n.uri, "schema" + (out.size() + 1) + ".xsd");
/*  464 */         if (output != null) {
/*  465 */           out.put(n, output);
/*  466 */           systemIds.put(n, output.getSystemId());
/*      */         } 
/*      */       } 
/*      */       
/*  470 */       n.resetWritten();
/*      */     } 
/*      */ 
/*      */     
/*  474 */     for (Map.Entry<Namespace, Result> e : out.entrySet()) {
/*  475 */       Result result = e.getValue();
/*  476 */       ((Namespace)e.getKey()).writeTo(result, systemIds);
/*  477 */       if (result instanceof StreamResult) {
/*  478 */         OutputStream outputStream = ((StreamResult)result).getOutputStream();
/*  479 */         if (outputStream != null) {
/*  480 */           outputStream.close(); continue;
/*      */         } 
/*  482 */         Writer writer = ((StreamResult)result).getWriter();
/*  483 */         if (writer != null) writer.close();
/*      */       
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private class Namespace
/*      */   {
/*      */     @NotNull
/*      */     final String uri;
/*      */ 
/*      */ 
/*      */     
/*  500 */     private final Set<Namespace> depends = new LinkedHashSet<>();
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private boolean selfReference;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  510 */     private final Set<ClassInfo<T, C>> classes = new LinkedHashSet<>();
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  515 */     private final Set<EnumLeafInfo<T, C>> enums = new LinkedHashSet<>();
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  520 */     private final Set<ArrayInfo<T, C>> arrays = new LinkedHashSet<>();
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  525 */     private final MultiMap<String, AttributePropertyInfo<T, C>> attributeDecls = new MultiMap<>(null);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  530 */     private final MultiMap<String, ElementDeclaration> elementDecls = new MultiMap<>(new ElementWithType(true, XmlSchemaGenerator.this
/*  531 */           .anyType));
/*      */ 
/*      */ 
/*      */     
/*      */     private Form attributeFormDefault;
/*      */ 
/*      */ 
/*      */     
/*      */     private Form elementFormDefault;
/*      */ 
/*      */ 
/*      */     
/*      */     private boolean useSwaRef;
/*      */ 
/*      */ 
/*      */     
/*      */     private boolean useMimeNs;
/*      */ 
/*      */ 
/*      */     
/*  551 */     private final Set<ClassInfo> written = new HashSet<>();
/*      */     
/*      */     public Namespace(String uri) {
/*  554 */       this.uri = uri;
/*  555 */       assert !XmlSchemaGenerator.access$1000(XmlSchemaGenerator.this).containsKey(uri);
/*  556 */       XmlSchemaGenerator.access$1000(XmlSchemaGenerator.this).put(uri, this);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void resetWritten() {
/*  563 */       this.written.clear();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private void processForeignNamespaces(PropertyInfo<T, C> p, int processingDepth) {
/*  575 */       for (TypeInfo<T, C> t : (Iterable<TypeInfo<T, C>>)p.ref()) {
/*  576 */         if (t instanceof ClassInfo && processingDepth > 0) {
/*  577 */           List<PropertyInfo> l = ((ClassInfo)t).getProperties();
/*  578 */           for (PropertyInfo<T, C> subp : l) {
/*  579 */             processForeignNamespaces(subp, --processingDepth);
/*      */           }
/*      */         } 
/*  582 */         if (t instanceof Element) {
/*  583 */           addDependencyTo(((Element)t).getElementName());
/*      */         }
/*  585 */         if (t instanceof NonElement) {
/*  586 */           addDependencyTo(((NonElement)t).getTypeName());
/*      */         }
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private void addDependencyTo(@Nullable QName qname) {
/*  595 */       if (qname == null) {
/*      */         return;
/*      */       }
/*      */       
/*  599 */       String nsUri = qname.getNamespaceURI();
/*      */       
/*  601 */       if (nsUri.equals("http://www.w3.org/2001/XMLSchema")) {
/*      */         return;
/*      */       }
/*      */ 
/*      */       
/*  606 */       if (nsUri.equals(this.uri)) {
/*  607 */         this.selfReference = true;
/*      */         
/*      */         return;
/*      */       } 
/*      */       
/*  612 */       this.depends.add(XmlSchemaGenerator.this.getNamespace(nsUri));
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private void writeTo(Result result, Map<Namespace, String> systemIds) throws IOException {
/*      */       try {
/*  623 */         Schema schema = (Schema)TXW.create(Schema.class, ResultFactory.createSerializer(result));
/*      */ 
/*      */         
/*  626 */         Map<String, String> xmlNs = XmlSchemaGenerator.this.types.getXmlNs(this.uri);
/*      */         
/*  628 */         for (Map.Entry<String, String> e : xmlNs.entrySet()) {
/*  629 */           schema._namespace(e.getValue(), e.getKey());
/*      */         }
/*      */         
/*  632 */         if (this.useSwaRef) {
/*  633 */           schema._namespace("http://ws-i.org/profiles/basic/1.1/xsd", "swaRef");
/*      */         }
/*  635 */         if (this.useMimeNs) {
/*  636 */           schema._namespace("http://www.w3.org/2005/05/xmlmime", "xmime");
/*      */         }
/*  638 */         this.attributeFormDefault = Form.get(XmlSchemaGenerator.this.types.getAttributeFormDefault(this.uri));
/*  639 */         this.attributeFormDefault.declare("attributeFormDefault", schema);
/*      */         
/*  641 */         this.elementFormDefault = Form.get(XmlSchemaGenerator.this.types.getElementFormDefault(this.uri));
/*      */         
/*  643 */         this.elementFormDefault.declare("elementFormDefault", schema);
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  648 */         if (!xmlNs.containsValue("http://www.w3.org/2001/XMLSchema") && 
/*  649 */           !xmlNs.containsKey("xs"))
/*  650 */           schema._namespace("http://www.w3.org/2001/XMLSchema", "xs"); 
/*  651 */         schema.version("1.0");
/*      */         
/*  653 */         if (this.uri.length() != 0) {
/*  654 */           schema.targetNamespace(this.uri);
/*      */         }
/*      */ 
/*      */         
/*  658 */         for (Namespace ns : this.depends) {
/*  659 */           schema._namespace(ns.uri);
/*      */         }
/*      */         
/*  662 */         if (this.selfReference && this.uri.length() != 0)
/*      */         {
/*      */           
/*  665 */           schema._namespace(this.uri, "tns");
/*      */         }
/*      */         
/*  668 */         schema._pcdata("\n");
/*      */ 
/*      */         
/*  671 */         for (Namespace n : this.depends) {
/*  672 */           Import imp = schema._import();
/*  673 */           if (n.uri.length() != 0)
/*  674 */             imp.namespace(n.uri); 
/*  675 */           String refSystemId = systemIds.get(n);
/*  676 */           if (refSystemId != null && !refSystemId.equals(""))
/*      */           {
/*  678 */             imp.schemaLocation(XmlSchemaGenerator.relativize(refSystemId, result.getSystemId()));
/*      */           }
/*  680 */           schema._pcdata("\n");
/*      */         } 
/*  682 */         if (this.useSwaRef) {
/*  683 */           schema._import().namespace("http://ws-i.org/profiles/basic/1.1/xsd").schemaLocation("http://ws-i.org/profiles/basic/1.1/swaref.xsd");
/*      */         }
/*  685 */         if (this.useMimeNs) {
/*  686 */           schema._import().namespace("http://www.w3.org/2005/05/xmlmime").schemaLocation("http://www.w3.org/2005/05/xmlmime");
/*      */         }
/*      */ 
/*      */         
/*  690 */         for (Map.Entry<String, ElementDeclaration> e : this.elementDecls.entrySet()) {
/*  691 */           ((ElementDeclaration)e.getValue()).writeTo(e.getKey(), schema);
/*  692 */           schema._pcdata("\n");
/*      */         } 
/*  694 */         for (ClassInfo<T, C> c : this.classes) {
/*  695 */           if (c.getTypeName() == null) {
/*      */             continue;
/*      */           }
/*      */           
/*  699 */           if (this.uri.equals(c.getTypeName().getNamespaceURI()))
/*  700 */             writeClass(c, (TypeHost)schema); 
/*  701 */           schema._pcdata("\n");
/*      */         } 
/*  703 */         for (EnumLeafInfo<T, C> e : this.enums) {
/*  704 */           if (e.getTypeName() == null) {
/*      */             continue;
/*      */           }
/*      */           
/*  708 */           if (this.uri.equals(e.getTypeName().getNamespaceURI()))
/*  709 */             writeEnum(e, (SimpleTypeHost)schema); 
/*  710 */           schema._pcdata("\n");
/*      */         } 
/*  712 */         for (ArrayInfo<T, C> a : this.arrays) {
/*  713 */           writeArray(a, schema);
/*  714 */           schema._pcdata("\n");
/*      */         } 
/*  716 */         for (Map.Entry<String, AttributePropertyInfo<T, C>> e : this.attributeDecls.entrySet()) {
/*  717 */           TopLevelAttribute a = schema.attribute();
/*  718 */           a.name(e.getKey());
/*  719 */           if (e.getValue() == null) {
/*  720 */             writeTypeRef((TypeHost)a, XmlSchemaGenerator.this.stringType, "type");
/*      */           } else {
/*  722 */             writeAttributeTypeRef(e.getValue(), (AttributeType)a);
/*  723 */           }  schema._pcdata("\n");
/*      */         } 
/*      */ 
/*      */         
/*  727 */         schema.commit();
/*  728 */       } catch (TxwException e) {
/*  729 */         XmlSchemaGenerator.logger.log(Level.INFO, e.getMessage(), (Throwable)e);
/*  730 */         throw new IOException(e.getMessage());
/*      */       } 
/*      */     }
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
/*      */     private void writeTypeRef(TypeHost th, NonElementRef<T, C> typeRef, String refAttName) {
/*  747 */       switch (typeRef.getSource().id()) {
/*      */         case LAX:
/*  749 */           th._attribute(refAttName, new QName("http://www.w3.org/2001/XMLSchema", "ID"));
/*      */           return;
/*      */         case SKIP:
/*  752 */           th._attribute(refAttName, new QName("http://www.w3.org/2001/XMLSchema", "IDREF"));
/*      */           return;
/*      */         
/*      */         case STRICT:
/*      */           break;
/*      */         default:
/*  758 */           throw new IllegalStateException();
/*      */       } 
/*      */ 
/*      */       
/*  762 */       MimeType mimeType = typeRef.getSource().getExpectedMimeType();
/*  763 */       if (mimeType != null) {
/*  764 */         th._attribute(new QName("http://www.w3.org/2005/05/xmlmime", "expectedContentTypes", "xmime"), mimeType.toString());
/*      */       }
/*      */ 
/*      */       
/*  768 */       if (XmlSchemaGenerator.this.generateSwaRefAdapter(typeRef)) {
/*  769 */         th._attribute(refAttName, new QName("http://ws-i.org/profiles/basic/1.1/xsd", "swaRef", "ref"));
/*      */         
/*      */         return;
/*      */       } 
/*      */       
/*  774 */       if (typeRef.getSource().getSchemaType() != null) {
/*  775 */         th._attribute(refAttName, typeRef.getSource().getSchemaType());
/*      */         
/*      */         return;
/*      */       } 
/*      */       
/*  780 */       writeTypeRef(th, typeRef.getTarget(), refAttName);
/*      */     }
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
/*      */     private void writeTypeRef(TypeHost th, NonElement<T, C> type, String refAttName) {
/*  796 */       Element e = null;
/*  797 */       if (type instanceof MaybeElement) {
/*  798 */         MaybeElement me = (MaybeElement)type;
/*  799 */         boolean isElement = me.isElement();
/*  800 */         if (isElement) e = me.asElement(); 
/*      */       } 
/*  802 */       if (type instanceof Element) {
/*  803 */         e = (Element)type;
/*      */       }
/*  805 */       if (type.getTypeName() == null) {
/*  806 */         if (e != null && e.getElementName() != null) {
/*  807 */           th.block();
/*  808 */           if (type instanceof ClassInfo) {
/*  809 */             writeClass((ClassInfo<T, C>)type, th);
/*      */           } else {
/*  811 */             writeEnum((EnumLeafInfo<T, C>)type, (SimpleTypeHost)th);
/*      */           } 
/*      */         } else {
/*      */           
/*  815 */           th.block();
/*  816 */           if (type instanceof ClassInfo) {
/*  817 */             if (XmlSchemaGenerator.this.collisionChecker.push(type)) {
/*  818 */               XmlSchemaGenerator.this.errorListener.warning(new SAXParseException(Messages.ANONYMOUS_TYPE_CYCLE
/*  819 */                     .format(new Object[] { XmlSchemaGenerator.access$1600(this.this$0).getCycleString() }, ), null));
/*      */             }
/*      */             else {
/*      */               
/*  823 */               writeClass((ClassInfo<T, C>)type, th);
/*      */             } 
/*  825 */             XmlSchemaGenerator.this.collisionChecker.pop();
/*      */           } else {
/*  827 */             writeEnum((EnumLeafInfo<T, C>)type, (SimpleTypeHost)th);
/*      */           } 
/*      */         } 
/*      */       } else {
/*  831 */         th._attribute(refAttName, type.getTypeName());
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private void writeArray(ArrayInfo<T, C> a, Schema schema) {
/*  839 */       ComplexType ct = schema.complexType().name(a.getTypeName().getLocalPart());
/*  840 */       ct._final("#all");
/*  841 */       LocalElement le = ct.sequence().element().name("item");
/*  842 */       le.type(a.getItemType().getTypeName());
/*  843 */       le.minOccurs(0).maxOccurs("unbounded");
/*  844 */       le.nillable(true);
/*  845 */       ct.commit();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private void writeEnum(EnumLeafInfo<T, C> e, SimpleTypeHost th) {
/*  852 */       SimpleType st = th.simpleType();
/*  853 */       writeName((NonElement<T, C>)e, (TypedXmlWriter)st);
/*      */       
/*  855 */       SimpleRestriction simpleRestriction = st.restriction();
/*  856 */       writeTypeRef((TypeHost)simpleRestriction, e.getBaseType(), "base");
/*      */       
/*  858 */       for (EnumConstant c : e.getConstants()) {
/*  859 */         simpleRestriction.enumeration().value(c.getLexicalValue());
/*      */       }
/*  861 */       st.commit();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private void writeClass(ClassInfo<T, C> c, TypeHost parent) {
/*      */       ComplexExtension complexExtension1, complexExtension2;
/*  871 */       if (this.written.contains(c)) {
/*      */         return;
/*      */       }
/*  874 */       this.written.add(c);
/*      */       
/*  876 */       if (containsValueProp(c)) {
/*  877 */         if (c.getProperties().size() == 1) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  883 */           ValuePropertyInfo<T, C> vp = c.getProperties().get(0);
/*  884 */           SimpleType st = ((SimpleTypeHost)parent).simpleType();
/*  885 */           writeName((NonElement<T, C>)c, (TypedXmlWriter)st);
/*  886 */           if (vp.isCollection()) {
/*  887 */             writeTypeRef((TypeHost)st.list(), vp.getTarget(), "itemType");
/*      */           } else {
/*  889 */             writeTypeRef((TypeHost)st.restriction(), vp.getTarget(), "base");
/*      */           } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*      */           return;
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  905 */         ComplexType complexType = ((ComplexTypeHost)parent).complexType();
/*  906 */         writeName((NonElement<T, C>)c, (TypedXmlWriter)complexType);
/*  907 */         if (c.isFinal()) {
/*  908 */           complexType._final("extension restriction");
/*      */         }
/*  910 */         SimpleExtension se = complexType.simpleContent().extension();
/*  911 */         se.block();
/*  912 */         for (PropertyInfo<T, C> p : (Iterable<PropertyInfo<T, C>>)c.getProperties()) {
/*  913 */           ValuePropertyInfo vp; switch (p.kind()) {
/*      */             case LAX:
/*  915 */               handleAttributeProp((AttributePropertyInfo<T, C>)p, (AttrDecls)se);
/*      */               continue;
/*      */             case SKIP:
/*  918 */               TODO.checkSpec("what if vp.isCollection() == true?");
/*  919 */               vp = (ValuePropertyInfo)p;
/*  920 */               se.base(vp.getTarget().getTypeName());
/*      */               continue;
/*      */           } 
/*      */ 
/*      */           
/*      */           assert false;
/*  926 */           throw new IllegalStateException();
/*      */         } 
/*      */         
/*  929 */         se.commit();
/*      */         
/*  931 */         TODO.schemaGenerator("figure out what to do if bc != null");
/*  932 */         TODO.checkSpec("handle sec 8.9.5.2, bullet #4");
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         return;
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/*  942 */       ComplexType ct = ((ComplexTypeHost)parent).complexType();
/*  943 */       writeName((NonElement<T, C>)c, (TypedXmlWriter)ct);
/*  944 */       if (c.isFinal())
/*  945 */         ct._final("extension restriction"); 
/*  946 */       if (c.isAbstract()) {
/*  947 */         ct._abstract(true);
/*      */       }
/*      */       
/*  950 */       ComplexType complexType1 = ct;
/*  951 */       ComplexType complexType2 = ct;
/*      */ 
/*      */       
/*  954 */       ClassInfo<T, C> bc = c.getBaseClass();
/*  955 */       if (bc != null) {
/*  956 */         if (bc.hasValueProperty()) {
/*      */           
/*  958 */           SimpleExtension se = ct.simpleContent().extension();
/*  959 */           SimpleExtension simpleExtension1 = se;
/*  960 */           complexType2 = null;
/*  961 */           se.base(bc.getTypeName());
/*      */         } else {
/*  963 */           ComplexExtension ce = ct.complexContent().extension();
/*  964 */           complexExtension1 = ce;
/*  965 */           complexExtension2 = ce;
/*      */           
/*  967 */           ce.base(bc.getTypeName());
/*      */         } 
/*      */       }
/*      */ 
/*      */       
/*  972 */       if (complexExtension2 != null) {
/*      */         
/*  974 */         ArrayList<Tree> children = new ArrayList<>();
/*  975 */         for (PropertyInfo<T, C> p : (Iterable<PropertyInfo<T, C>>)c.getProperties()) {
/*      */           
/*  977 */           if (p instanceof ReferencePropertyInfo && ((ReferencePropertyInfo)p).isMixed()) {
/*  978 */             ct.mixed(true);
/*      */           }
/*  980 */           Tree t = buildPropertyContentModel(p);
/*  981 */           if (t != null) {
/*  982 */             children.add(t);
/*      */           }
/*      */         } 
/*  985 */         Tree top = Tree.makeGroup(c.isOrdered() ? GroupKind.SEQUENCE : GroupKind.ALL, children);
/*      */ 
/*      */         
/*  988 */         top.write((TypeDefParticle)complexExtension2);
/*      */       } 
/*      */ 
/*      */       
/*  992 */       for (PropertyInfo<T, C> p : (Iterable<PropertyInfo<T, C>>)c.getProperties()) {
/*  993 */         if (p instanceof AttributePropertyInfo) {
/*  994 */           handleAttributeProp((AttributePropertyInfo<T, C>)p, (AttrDecls)complexExtension1);
/*      */         }
/*      */       } 
/*  997 */       if (c.hasAttributeWildcard()) {
/*  998 */         complexExtension1.anyAttribute().namespace("##other").processContents("skip");
/*      */       }
/* 1000 */       ct.commit();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private void writeName(NonElement<T, C> c, TypedXmlWriter xw) {
/* 1007 */       QName tn = c.getTypeName();
/* 1008 */       if (tn != null)
/* 1009 */         xw._attribute("name", tn.getLocalPart()); 
/*      */     }
/*      */     
/*      */     private boolean containsValueProp(ClassInfo<T, C> c) {
/* 1013 */       for (PropertyInfo p : c.getProperties()) {
/* 1014 */         if (p instanceof ValuePropertyInfo) return true; 
/*      */       } 
/* 1016 */       return false;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private Tree buildPropertyContentModel(PropertyInfo<T, C> p) {
/* 1023 */       switch (p.kind()) {
/*      */         case STRICT:
/* 1025 */           return handleElementProp((ElementPropertyInfo<T, C>)p);
/*      */         
/*      */         case LAX:
/* 1028 */           return null;
/*      */         case null:
/* 1030 */           return handleReferenceProp((ReferencePropertyInfo<T, C>)p);
/*      */         case null:
/* 1032 */           return handleMapProp((MapPropertyInfo<T, C>)p);
/*      */         
/*      */         case SKIP:
/*      */           assert false;
/* 1036 */           throw new IllegalStateException();
/*      */       } 
/*      */       assert false;
/* 1039 */       throw new IllegalStateException();
/*      */     }
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
/*      */     private Tree handleElementProp(final ElementPropertyInfo<T, C> ep) {
/* 1053 */       if (ep.isValueList()) {
/* 1054 */         return new Tree.Term() {
/*      */             protected void write(ContentModelContainer parent, boolean isOptional, boolean repeated) {
/* 1056 */               TypeRef<T, C> t = ep.getTypes().get(0);
/* 1057 */               LocalElement e = parent.element();
/* 1058 */               e.block();
/* 1059 */               QName tn = t.getTagName();
/* 1060 */               e.name(tn.getLocalPart());
/* 1061 */               List lst = e.simpleType().list();
/* 1062 */               XmlSchemaGenerator.Namespace.this.writeTypeRef((TypeHost)lst, (NonElementRef<T, C>)t, "itemType");
/* 1063 */               XmlSchemaGenerator.Namespace.this.elementFormDefault.writeForm(e, tn);
/* 1064 */               writeOccurs((Occurs)e, (isOptional || !ep.isRequired()), repeated);
/*      */             }
/*      */           };
/*      */       }
/*      */       
/* 1069 */       ArrayList<Tree> children = new ArrayList<>();
/* 1070 */       for (TypeRef<T, C> t : (Iterable<TypeRef<T, C>>)ep.getTypes()) {
/* 1071 */         children.add(new Tree.Term() {
/*      */               protected void write(ContentModelContainer parent, boolean isOptional, boolean repeated) {
/* 1073 */                 LocalElement e = parent.element();
/*      */                 
/* 1075 */                 QName tn = t.getTagName();
/*      */                 
/* 1077 */                 PropertyInfo propInfo = t.getSource();
/* 1078 */                 TypeInfo parentInfo = (propInfo == null) ? null : propInfo.parent();
/*      */                 
/* 1080 */                 if (XmlSchemaGenerator.Namespace.this.canBeDirectElementRef(t, tn, parentInfo)) {
/* 1081 */                   if (!t.getTarget().isSimpleType() && t.getTarget() instanceof ClassInfo && XmlSchemaGenerator.this.collisionChecker.findDuplicate(t.getTarget())) {
/* 1082 */                     e.ref(new QName(XmlSchemaGenerator.Namespace.this.uri, tn.getLocalPart()));
/*      */                   } else {
/*      */                     
/* 1085 */                     QName elemName = null;
/* 1086 */                     if (t.getTarget() instanceof Element) {
/* 1087 */                       Element te = (Element)t.getTarget();
/* 1088 */                       elemName = te.getElementName();
/*      */                     } 
/*      */                     
/* 1091 */                     Collection<TypeInfo> refs = propInfo.ref();
/*      */                     TypeInfo ti;
/* 1093 */                     if (refs != null && !refs.isEmpty() && elemName != null && ((
/* 1094 */                       ti = refs.iterator().next()) == null || ti instanceof ClassInfoImpl)) {
/* 1095 */                       ClassInfoImpl cImpl = (ClassInfoImpl)ti;
/* 1096 */                       if (cImpl != null && cImpl.getElementName() != null) {
/* 1097 */                         e.ref(new QName(cImpl.getElementName().getNamespaceURI(), tn.getLocalPart()));
/*      */                       } else {
/* 1099 */                         e.ref(new QName("", tn.getLocalPart()));
/*      */                       } 
/*      */                     } else {
/* 1102 */                       e.ref(tn);
/*      */                     } 
/*      */                   } 
/*      */                 } else {
/* 1106 */                   e.name(tn.getLocalPart());
/* 1107 */                   XmlSchemaGenerator.Namespace.this.writeTypeRef((TypeHost)e, (NonElementRef<T, C>)t, "type");
/* 1108 */                   XmlSchemaGenerator.Namespace.this.elementFormDefault.writeForm(e, tn);
/*      */                 } 
/*      */                 
/* 1111 */                 if (t.isNillable()) {
/* 1112 */                   e.nillable(true);
/*      */                 }
/* 1114 */                 if (t.getDefaultValue() != null)
/* 1115 */                   e._default(t.getDefaultValue()); 
/* 1116 */                 writeOccurs((Occurs)e, isOptional, repeated);
/*      */               }
/*      */             });
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 1123 */       final Tree choice = Tree.makeGroup(GroupKind.CHOICE, children).makeOptional(!ep.isRequired()).makeRepeated(ep.isCollection());
/*      */ 
/*      */       
/* 1126 */       final QName ename = ep.getXmlName();
/* 1127 */       if (ename != null) {
/* 1128 */         return new Tree.Term() {
/*      */             protected void write(ContentModelContainer parent, boolean isOptional, boolean repeated) {
/* 1130 */               LocalElement e = parent.element();
/* 1131 */               if (ename.getNamespaceURI().length() > 0 && 
/* 1132 */                 !ename.getNamespaceURI().equals(XmlSchemaGenerator.Namespace.this.uri)) {
/*      */ 
/*      */                 
/* 1135 */                 e.ref(new QName(ename.getNamespaceURI(), ename.getLocalPart()));
/*      */                 
/*      */                 return;
/*      */               } 
/* 1139 */               e.name(ename.getLocalPart());
/* 1140 */               XmlSchemaGenerator.Namespace.this.elementFormDefault.writeForm(e, ename);
/*      */               
/* 1142 */               if (ep.isCollectionNillable()) {
/* 1143 */                 e.nillable(true);
/*      */               }
/* 1145 */               writeOccurs((Occurs)e, !ep.isCollectionRequired(), repeated);
/*      */               
/* 1147 */               ComplexType p = e.complexType();
/* 1148 */               choice.write((TypeDefParticle)p);
/*      */             }
/*      */           };
/*      */       }
/* 1152 */       return choice;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private boolean canBeDirectElementRef(TypeRef<T, C> t, QName tn, TypeInfo parentInfo) {
/* 1163 */       Element te = null;
/* 1164 */       ClassInfo ci = null;
/* 1165 */       QName targetTagName = null;
/*      */       
/* 1167 */       if (t.isNillable() || t.getDefaultValue() != null)
/*      */       {
/* 1169 */         return false;
/*      */       }
/*      */       
/* 1172 */       if (t.getTarget() instanceof Element) {
/* 1173 */         te = (Element)t.getTarget();
/* 1174 */         targetTagName = te.getElementName();
/* 1175 */         if (te instanceof ClassInfo) {
/* 1176 */           ci = (ClassInfo)te;
/*      */         }
/*      */       } 
/*      */       
/* 1180 */       String nsUri = tn.getNamespaceURI();
/* 1181 */       if (!nsUri.equals(this.uri) && nsUri.length() > 0 && (!(parentInfo instanceof ClassInfo) || ((ClassInfo)parentInfo).getTypeName() != null)) {
/* 1182 */         return true;
/*      */       }
/*      */       
/* 1185 */       if (ci != null && targetTagName != null && te.getScope() == null && targetTagName.getNamespaceURI() == null && 
/* 1186 */         targetTagName.equals(tn)) {
/* 1187 */         return true;
/*      */       }
/*      */ 
/*      */ 
/*      */       
/* 1192 */       if (te != null) {
/* 1193 */         return (targetTagName != null && targetTagName.equals(tn));
/*      */       }
/*      */       
/* 1196 */       return false;
/*      */     }
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
/*      */     private void handleAttributeProp(AttributePropertyInfo<T, C> ap, AttrDecls attr) {
/* 1229 */       LocalAttribute localAttribute = attr.attribute();
/*      */       
/* 1231 */       String attrURI = ap.getXmlName().getNamespaceURI();
/* 1232 */       if (attrURI.equals("")) {
/* 1233 */         localAttribute.name(ap.getXmlName().getLocalPart());
/*      */         
/* 1235 */         writeAttributeTypeRef(ap, (AttributeType)localAttribute);
/*      */         
/* 1237 */         this.attributeFormDefault.writeForm(localAttribute, ap.getXmlName());
/*      */       } else {
/* 1239 */         localAttribute.ref(ap.getXmlName());
/*      */       } 
/*      */       
/* 1242 */       if (ap.isRequired())
/*      */       {
/* 1244 */         localAttribute.use("required");
/*      */       }
/*      */     }
/*      */     
/*      */     private void writeAttributeTypeRef(AttributePropertyInfo<T, C> ap, AttributeType a) {
/* 1249 */       if (ap.isCollection()) {
/* 1250 */         writeTypeRef((TypeHost)a.simpleType().list(), (NonElementRef<T, C>)ap, "itemType");
/*      */       } else {
/* 1252 */         writeTypeRef((TypeHost)a, (NonElementRef<T, C>)ap, "type");
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private Tree handleReferenceProp(final ReferencePropertyInfo<T, C> rp) {
/* 1264 */       ArrayList<Tree> children = new ArrayList<>();
/*      */       
/* 1266 */       for (Element<T, C> e : (Iterable<Element<T, C>>)rp.getElements()) {
/* 1267 */         children.add(new Tree.Term() {
/*      */               protected void write(ContentModelContainer parent, boolean isOptional, boolean repeated) {
/* 1269 */                 LocalElement eref = parent.element();
/*      */                 
/* 1271 */                 boolean local = false;
/*      */                 
/* 1273 */                 QName en = e.getElementName();
/* 1274 */                 if (e.getScope() != null) {
/*      */                   
/* 1276 */                   boolean qualified = en.getNamespaceURI().equals(XmlSchemaGenerator.Namespace.this.uri);
/* 1277 */                   boolean unqualified = en.getNamespaceURI().equals("");
/* 1278 */                   if (qualified || unqualified) {
/*      */ 
/*      */ 
/*      */                     
/* 1282 */                     if (unqualified) {
/* 1283 */                       if (XmlSchemaGenerator.Namespace.this.elementFormDefault.isEffectivelyQualified) {
/* 1284 */                         eref.form("unqualified");
/*      */                       }
/* 1286 */                     } else if (!XmlSchemaGenerator.Namespace.this.elementFormDefault.isEffectivelyQualified) {
/* 1287 */                       eref.form("qualified");
/*      */                     } 
/*      */                     
/* 1290 */                     local = true;
/* 1291 */                     eref.name(en.getLocalPart());
/*      */ 
/*      */                     
/* 1294 */                     if (e instanceof ClassInfo) {
/* 1295 */                       XmlSchemaGenerator.Namespace.this.writeTypeRef((TypeHost)eref, (NonElement<T, C>)e, "type");
/*      */                     } else {
/* 1297 */                       XmlSchemaGenerator.Namespace.this.writeTypeRef((TypeHost)eref, ((ElementInfo)e).getContentType(), "type");
/*      */                     } 
/*      */                   } 
/*      */                 } 
/* 1301 */                 if (!local)
/* 1302 */                   eref.ref(en); 
/* 1303 */                 writeOccurs((Occurs)eref, isOptional, repeated);
/*      */               }
/*      */             });
/*      */       } 
/*      */       
/* 1308 */       final WildcardMode wc = rp.getWildcard();
/* 1309 */       if (wc != null) {
/* 1310 */         children.add(new Tree.Term() {
/*      */               protected void write(ContentModelContainer parent, boolean isOptional, boolean repeated) {
/* 1312 */                 Any any = parent.any();
/* 1313 */                 String pcmode = XmlSchemaGenerator.getProcessContentsModeName(wc);
/* 1314 */                 if (pcmode != null) any.processContents(pcmode); 
/* 1315 */                 any.namespace("##other");
/* 1316 */                 writeOccurs((Occurs)any, isOptional, repeated);
/*      */               }
/*      */             });
/*      */       }
/*      */ 
/*      */       
/* 1322 */       final Tree choice = Tree.makeGroup(GroupKind.CHOICE, children).makeRepeated(rp.isCollection()).makeOptional(!rp.isRequired());
/*      */       
/* 1324 */       final QName ename = rp.getXmlName();
/*      */       
/* 1326 */       if (ename != null) {
/* 1327 */         return new Tree.Term() {
/*      */             protected void write(ContentModelContainer parent, boolean isOptional, boolean repeated) {
/* 1329 */               LocalElement e = parent.element().name(ename.getLocalPart());
/* 1330 */               XmlSchemaGenerator.Namespace.this.elementFormDefault.writeForm(e, ename);
/* 1331 */               if (rp.isCollectionNillable())
/* 1332 */                 e.nillable(true); 
/* 1333 */               writeOccurs((Occurs)e, true, repeated);
/*      */               
/* 1335 */               ComplexType p = e.complexType();
/* 1336 */               choice.write((TypeDefParticle)p);
/*      */             }
/*      */           };
/*      */       }
/* 1340 */       return choice;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private Tree handleMapProp(final MapPropertyInfo<T, C> mp) {
/* 1351 */       return new Tree.Term() {
/*      */           protected void write(ContentModelContainer parent, boolean isOptional, boolean repeated) {
/* 1353 */             QName ename = mp.getXmlName();
/*      */             
/* 1355 */             LocalElement e = parent.element();
/* 1356 */             XmlSchemaGenerator.Namespace.this.elementFormDefault.writeForm(e, ename);
/* 1357 */             if (mp.isCollectionNillable()) {
/* 1358 */               e.nillable(true);
/*      */             }
/* 1360 */             e = e.name(ename.getLocalPart());
/* 1361 */             writeOccurs((Occurs)e, isOptional, repeated);
/* 1362 */             ComplexType p = e.complexType();
/*      */ 
/*      */ 
/*      */             
/* 1366 */             e = p.sequence().element();
/* 1367 */             e.name("entry").minOccurs(0).maxOccurs("unbounded");
/*      */             
/* 1369 */             ExplicitGroup seq = e.complexType().sequence();
/* 1370 */             XmlSchemaGenerator.Namespace.this.writeKeyOrValue(seq, "key", mp.getKeyType());
/* 1371 */             XmlSchemaGenerator.Namespace.this.writeKeyOrValue(seq, "value", mp.getValueType());
/*      */           }
/*      */         };
/*      */     }
/*      */     
/*      */     private void writeKeyOrValue(ExplicitGroup seq, String tagName, NonElement<T, C> typeRef) {
/* 1377 */       LocalElement key = seq.element().name(tagName);
/* 1378 */       key.minOccurs(0);
/* 1379 */       writeTypeRef((TypeHost)key, typeRef, "type");
/*      */     }
/*      */     
/*      */     public void addGlobalAttribute(AttributePropertyInfo<T, C> ap) {
/* 1383 */       this.attributeDecls.put(ap.getXmlName().getLocalPart(), ap);
/* 1384 */       addDependencyTo(ap.getTarget().getTypeName());
/*      */     }
/*      */     
/*      */     public void addGlobalElement(TypeRef<T, C> tref) {
/* 1388 */       this.elementDecls.put(tref.getTagName().getLocalPart(), new ElementWithType(false, tref.getTarget()));
/* 1389 */       addDependencyTo(tref.getTarget().getTypeName());
/*      */     }
/*      */ 
/*      */     
/*      */     public String toString() {
/* 1394 */       StringBuilder buf = new StringBuilder();
/* 1395 */       buf.append("[classes=").append(this.classes);
/* 1396 */       buf.append(",elementDecls=").append(this.elementDecls);
/* 1397 */       buf.append(",enums=").append(this.enums);
/* 1398 */       buf.append("]");
/* 1399 */       return super.toString();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     abstract class ElementDeclaration
/*      */     {
/*      */       public abstract boolean equals(Object param2Object);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public abstract int hashCode();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public abstract void writeTo(String param2String, Schema param2Schema);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     class ElementWithType
/*      */       extends ElementDeclaration
/*      */     {
/*      */       private final boolean nillable;
/*      */ 
/*      */ 
/*      */       
/*      */       private final NonElement<T, C> type;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public ElementWithType(boolean nillable, NonElement<T, C> type) {
/* 1440 */         this.type = type;
/* 1441 */         this.nillable = nillable;
/*      */       }
/*      */       
/*      */       public void writeTo(String localName, Schema schema) {
/* 1445 */         TopLevelElement e = schema.element().name(localName);
/* 1446 */         if (this.nillable)
/* 1447 */           e.nillable(true); 
/* 1448 */         if (this.type != null) {
/* 1449 */           XmlSchemaGenerator.Namespace.this.writeTypeRef((TypeHost)e, this.type, "type");
/*      */         } else {
/* 1451 */           e.complexType();
/*      */         } 
/* 1453 */         e.commit();
/*      */       }
/*      */       
/*      */       public boolean equals(Object o) {
/* 1457 */         if (this == o) return true; 
/* 1458 */         if (o == null || getClass() != o.getClass()) return false;
/*      */         
/* 1460 */         ElementWithType that = (ElementWithType)o;
/* 1461 */         return this.type.equals(that.type);
/*      */       }
/*      */       
/*      */       public int hashCode() {
/* 1465 */         return this.type.hashCode(); } } } class ElementWithType extends Namespace.ElementDeclaration { private final boolean nillable; private final NonElement<T, C> type; public ElementWithType(boolean nillable, NonElement<T, C> type) { this.type = type; this.nillable = nillable; } public void writeTo(String localName, Schema schema) { TopLevelElement e = schema.element().name(localName); if (this.nillable) e.nillable(true);  if (this.type != null) { XmlSchemaGenerator.Namespace.this.writeTypeRef((TypeHost)e, this.type, "type"); } else { e.complexType(); }  e.commit(); } public int hashCode() { return this.type.hashCode(); }
/*      */      public boolean equals(Object o) {
/*      */       if (this == o)
/*      */         return true; 
/*      */       if (o == null || getClass() != o.getClass())
/*      */         return false; 
/*      */       ElementWithType that = (ElementWithType)o;
/*      */       return this.type.equals(that.type);
/*      */     } }
/*      */   private boolean generateSwaRefAdapter(NonElementRef<T, C> typeRef) {
/* 1475 */     return generateSwaRefAdapter(typeRef.getSource());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean generateSwaRefAdapter(PropertyInfo<T, C> prop) {
/* 1482 */     Adapter<T, C> adapter = prop.getAdapter();
/* 1483 */     if (adapter == null) return false; 
/* 1484 */     Object o = this.navigator.asDecl(SwaRefAdapter.class);
/* 1485 */     if (o == null) return false; 
/* 1486 */     return o.equals(adapter.adapterType);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String toString() {
/* 1494 */     StringBuilder buf = new StringBuilder();
/* 1495 */     for (Namespace ns : this.namespaces.values()) {
/* 1496 */       if (buf.length() > 0) buf.append(','); 
/* 1497 */       buf.append(ns.uri).append('=').append(ns);
/*      */     } 
/* 1499 */     return super.toString() + '[' + buf + ']';
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static String getProcessContentsModeName(WildcardMode wc) {
/* 1508 */     switch (wc) {
/*      */       case LAX:
/*      */       case SKIP:
/* 1511 */         return wc.name().toLowerCase();
/*      */       case STRICT:
/* 1513 */         return null;
/*      */     } 
/* 1515 */     throw new IllegalStateException();
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
/*      */   protected static String relativize(String uri, String baseUri) {
/*      */     try {
/* 1538 */       assert uri != null;
/*      */       
/* 1540 */       if (baseUri == null) return uri;
/*      */       
/* 1542 */       URI theUri = new URI(Util.escapeURI(uri));
/* 1543 */       URI theBaseUri = new URI(Util.escapeURI(baseUri));
/*      */       
/* 1545 */       if (theUri.isOpaque() || theBaseUri.isOpaque()) {
/* 1546 */         return uri;
/*      */       }
/* 1548 */       if (!Util.equalsIgnoreCase(theUri.getScheme(), theBaseUri.getScheme()) || 
/* 1549 */         !Util.equal(theUri.getAuthority(), theBaseUri.getAuthority())) {
/* 1550 */         return uri;
/*      */       }
/* 1552 */       String uriPath = theUri.getPath();
/* 1553 */       String basePath = theBaseUri.getPath();
/*      */ 
/*      */       
/* 1556 */       if (!basePath.endsWith("/")) {
/* 1557 */         basePath = Util.normalizeUriPath(basePath);
/*      */       }
/*      */       
/* 1560 */       if (uriPath.equals(basePath)) {
/* 1561 */         return ".";
/*      */       }
/* 1563 */       String relPath = calculateRelativePath(uriPath, basePath, fixNull(theUri.getScheme()).equals("file"));
/*      */       
/* 1565 */       if (relPath == null)
/* 1566 */         return uri; 
/* 1567 */       StringBuilder relUri = new StringBuilder();
/* 1568 */       relUri.append(relPath);
/* 1569 */       if (theUri.getQuery() != null)
/* 1570 */         relUri.append('?').append(theUri.getQuery()); 
/* 1571 */       if (theUri.getFragment() != null) {
/* 1572 */         relUri.append('#').append(theUri.getFragment());
/*      */       }
/* 1574 */       return relUri.toString();
/* 1575 */     } catch (URISyntaxException e) {
/* 1576 */       throw new InternalError("Error escaping one of these uris:\n\t" + uri + "\n\t" + baseUri);
/*      */     } 
/*      */   }
/*      */   
/*      */   private static String fixNull(String s) {
/* 1581 */     if (s == null) return ""; 
/* 1582 */     return s;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static String calculateRelativePath(String uri, String base, boolean fileUrl) {
/* 1588 */     boolean onWindows = (File.pathSeparatorChar == ';');
/*      */     
/* 1590 */     if (base == null) {
/* 1591 */       return null;
/*      */     }
/* 1593 */     if ((fileUrl && onWindows && startsWithIgnoreCase(uri, base)) || uri.startsWith(base)) {
/* 1594 */       return uri.substring(base.length());
/*      */     }
/* 1596 */     return "../" + calculateRelativePath(uri, Util.getParentUriPath(base), fileUrl);
/*      */   }
/*      */ 
/*      */   
/*      */   private static boolean startsWithIgnoreCase(String s, String t) {
/* 1601 */     return s.toUpperCase().startsWith(t.toUpperCase());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1608 */   private static final Comparator<String> NAMESPACE_COMPARATOR = new Comparator<String>() {
/*      */       public int compare(String lhs, String rhs) {
/* 1610 */         return -lhs.compareTo(rhs);
/*      */       }
/*      */     };
/*      */   
/*      */   private static final String newline = "\n";
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/bind/v2/schemagen/XmlSchemaGenerator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */