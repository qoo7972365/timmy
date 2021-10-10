/*     */ package com.sun.xml.internal.bind.v2;
/*     */ 
/*     */ import com.sun.istack.internal.FinalArrayList;
/*     */ import com.sun.xml.internal.bind.Util;
/*     */ import com.sun.xml.internal.bind.api.JAXBRIContext;
/*     */ import com.sun.xml.internal.bind.api.TypeReference;
/*     */ import com.sun.xml.internal.bind.v2.model.annotation.RuntimeAnnotationReader;
/*     */ import com.sun.xml.internal.bind.v2.runtime.JAXBContextImpl;
/*     */ import com.sun.xml.internal.bind.v2.util.TypeCast;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.StringTokenizer;
/*     */ import java.util.logging.Level;
/*     */ import javax.xml.bind.JAXBContext;
/*     */ import javax.xml.bind.JAXBException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ContextFactory
/*     */ {
/*     */   public static final String USE_JAXB_PROPERTIES = "_useJAXBProperties";
/*     */   
/*     */   public static JAXBContext createContext(Class[] classes, Map<String, Object> properties) throws JAXBException {
/*     */     Map<Class<?>, Class<?>> subclassReplacements;
/*  69 */     if (properties == null) {
/*  70 */       properties = Collections.emptyMap();
/*     */     } else {
/*  72 */       properties = new HashMap<>(properties);
/*     */     } 
/*  74 */     String defaultNsUri = getPropertyValue(properties, "com.sun.xml.internal.bind.defaultNamespaceRemap", String.class);
/*     */     
/*  76 */     Boolean c14nSupport = getPropertyValue(properties, "com.sun.xml.internal.bind.c14n", Boolean.class);
/*  77 */     if (c14nSupport == null) {
/*  78 */       c14nSupport = Boolean.valueOf(false);
/*     */     }
/*  80 */     Boolean disablesecurityProcessing = getPropertyValue(properties, "com.sun.xml.internal.bind.disableXmlSecurity", Boolean.class);
/*  81 */     if (disablesecurityProcessing == null) {
/*  82 */       disablesecurityProcessing = Boolean.valueOf(false);
/*     */     }
/*  84 */     Boolean allNillable = getPropertyValue(properties, "com.sun.xml.internal.bind.treatEverythingNillable", Boolean.class);
/*  85 */     if (allNillable == null) {
/*  86 */       allNillable = Boolean.valueOf(false);
/*     */     }
/*  88 */     Boolean retainPropertyInfo = getPropertyValue(properties, "retainReferenceToInfo", Boolean.class);
/*  89 */     if (retainPropertyInfo == null) {
/*  90 */       retainPropertyInfo = Boolean.valueOf(false);
/*     */     }
/*  92 */     Boolean supressAccessorWarnings = getPropertyValue(properties, "supressAccessorWarnings", Boolean.class);
/*  93 */     if (supressAccessorWarnings == null) {
/*  94 */       supressAccessorWarnings = Boolean.valueOf(false);
/*     */     }
/*  96 */     Boolean improvedXsiTypeHandling = getPropertyValue(properties, "com.sun.xml.internal.bind.improvedXsiTypeHandling", Boolean.class);
/*  97 */     if (improvedXsiTypeHandling == null) {
/*  98 */       String improvedXsiSystemProperty = Util.getSystemProperty("com.sun.xml.internal.bind.improvedXsiTypeHandling");
/*  99 */       if (improvedXsiSystemProperty == null) {
/* 100 */         improvedXsiTypeHandling = Boolean.valueOf(true);
/*     */       } else {
/* 102 */         improvedXsiTypeHandling = Boolean.valueOf(improvedXsiSystemProperty);
/*     */       } 
/*     */     } 
/*     */     
/* 106 */     Boolean xmlAccessorFactorySupport = getPropertyValue(properties, "com.sun.xml.internal.bind.XmlAccessorFactory", Boolean.class);
/*     */     
/* 108 */     if (xmlAccessorFactorySupport == null) {
/* 109 */       xmlAccessorFactorySupport = Boolean.valueOf(false);
/* 110 */       Util.getClassLogger().log(Level.FINE, "Property com.sun.xml.internal.bind.XmlAccessorFactoryis not active.  Using JAXB's implementation");
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 115 */     RuntimeAnnotationReader ar = getPropertyValue(properties, JAXBRIContext.ANNOTATION_READER, RuntimeAnnotationReader.class);
/*     */     
/* 117 */     Collection<TypeReference> tr = getPropertyValue(properties, "com.sun.xml.internal.bind.typeReferences", (Class)Collection.class);
/* 118 */     if (tr == null) {
/* 119 */       tr = Collections.emptyList();
/*     */     }
/*     */ 
/*     */     
/*     */     try {
/* 124 */       subclassReplacements = TypeCast.checkedCast(
/* 125 */           getPropertyValue(properties, "com.sun.xml.internal.bind.subclassReplacements", Map.class), Class.class, Class.class);
/* 126 */     } catch (ClassCastException e) {
/* 127 */       throw new JAXBException(Messages.INVALID_TYPE_IN_MAP.format(new Object[0]), e);
/*     */     } 
/*     */     
/* 130 */     if (!properties.isEmpty()) {
/* 131 */       throw new JAXBException(Messages.UNSUPPORTED_PROPERTY.format(new Object[] { properties.keySet().iterator().next() }));
/*     */     }
/*     */     
/* 134 */     JAXBContextImpl.JAXBContextBuilder builder = new JAXBContextImpl.JAXBContextBuilder();
/* 135 */     builder.setClasses(classes);
/* 136 */     builder.setTypeRefs(tr);
/* 137 */     builder.setSubclassReplacements(subclassReplacements);
/* 138 */     builder.setDefaultNsUri(defaultNsUri);
/* 139 */     builder.setC14NSupport(c14nSupport.booleanValue());
/* 140 */     builder.setAnnotationReader(ar);
/* 141 */     builder.setXmlAccessorFactorySupport(xmlAccessorFactorySupport.booleanValue());
/* 142 */     builder.setAllNillable(allNillable.booleanValue());
/* 143 */     builder.setRetainPropertyInfo(retainPropertyInfo.booleanValue());
/* 144 */     builder.setSupressAccessorWarnings(supressAccessorWarnings.booleanValue());
/* 145 */     builder.setImprovedXsiTypeHandling(improvedXsiTypeHandling.booleanValue());
/* 146 */     builder.setDisableSecurityProcessing(disablesecurityProcessing.booleanValue());
/* 147 */     return (JAXBContext)builder.build();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static <T> T getPropertyValue(Map<String, Object> properties, String keyName, Class<T> type) throws JAXBException {
/* 154 */     Object o = properties.get(keyName);
/* 155 */     if (o == null) return null;
/*     */     
/* 157 */     properties.remove(keyName);
/* 158 */     if (!type.isInstance(o)) {
/* 159 */       throw new JAXBException(Messages.INVALID_PROPERTY_VALUE.format(new Object[] { keyName, o }));
/*     */     }
/* 161 */     return type.cast(o);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public static JAXBRIContext createContext(Class[] classes, Collection<TypeReference> typeRefs, Map<Class<?>, Class<?>> subclassReplacements, String defaultNsUri, boolean c14nSupport, RuntimeAnnotationReader ar, boolean xmlAccessorFactorySupport, boolean allNillable, boolean retainPropertyInfo) throws JAXBException {
/* 185 */     return createContext(classes, typeRefs, subclassReplacements, defaultNsUri, c14nSupport, ar, xmlAccessorFactorySupport, allNillable, retainPropertyInfo, false);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public static JAXBRIContext createContext(Class[] classes, Collection<TypeReference> typeRefs, Map<Class<?>, Class<?>> subclassReplacements, String defaultNsUri, boolean c14nSupport, RuntimeAnnotationReader ar, boolean xmlAccessorFactorySupport, boolean allNillable, boolean retainPropertyInfo, boolean improvedXsiTypeHandling) throws JAXBException {
/* 212 */     JAXBContextImpl.JAXBContextBuilder builder = new JAXBContextImpl.JAXBContextBuilder();
/* 213 */     builder.setClasses(classes);
/* 214 */     builder.setTypeRefs(typeRefs);
/* 215 */     builder.setSubclassReplacements(subclassReplacements);
/* 216 */     builder.setDefaultNsUri(defaultNsUri);
/* 217 */     builder.setC14NSupport(c14nSupport);
/* 218 */     builder.setAnnotationReader(ar);
/* 219 */     builder.setXmlAccessorFactorySupport(xmlAccessorFactorySupport);
/* 220 */     builder.setAllNillable(allNillable);
/* 221 */     builder.setRetainPropertyInfo(retainPropertyInfo);
/* 222 */     builder.setImprovedXsiTypeHandling(improvedXsiTypeHandling);
/* 223 */     return (JAXBRIContext)builder.build();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static JAXBContext createContext(String contextPath, ClassLoader classLoader, Map<String, Object> properties) throws JAXBException {
/* 231 */     FinalArrayList<Class<?>> classes = new FinalArrayList();
/* 232 */     StringTokenizer tokens = new StringTokenizer(contextPath, ":");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 239 */     while (tokens.hasMoreTokens()) {
/* 240 */       List<Class<?>> indexedClasses; boolean foundJaxbIndex = false, foundObjectFactory = foundJaxbIndex;
/* 241 */       String pkg = tokens.nextToken();
/*     */ 
/*     */ 
/*     */       
/*     */       try {
/* 246 */         Class<?> o = classLoader.loadClass(pkg + ".ObjectFactory");
/* 247 */         classes.add(o);
/* 248 */         foundObjectFactory = true;
/* 249 */       } catch (ClassNotFoundException classNotFoundException) {}
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       try {
/* 255 */         indexedClasses = loadIndexedClasses(pkg, classLoader);
/* 256 */       } catch (IOException e) {
/*     */         
/* 258 */         throw new JAXBException(e);
/*     */       } 
/* 260 */       if (indexedClasses != null) {
/* 261 */         classes.addAll(indexedClasses);
/* 262 */         foundJaxbIndex = true;
/*     */       } 
/*     */       
/* 265 */       if (!foundObjectFactory && !foundJaxbIndex) {
/* 266 */         throw new JAXBException(Messages.BROKEN_CONTEXTPATH.format(new Object[] { pkg }));
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 271 */     return createContext((Class[])classes.toArray((Object[])new Class[classes.size()]), properties);
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
/*     */   private static List<Class> loadIndexedClasses(String pkg, ClassLoader classLoader) throws IOException, JAXBException {
/* 284 */     String resource = pkg.replace('.', '/') + "/jaxb.index";
/* 285 */     InputStream resourceAsStream = classLoader.getResourceAsStream(resource);
/*     */     
/* 287 */     if (resourceAsStream == null) {
/* 288 */       return null;
/*     */     }
/*     */     
/* 291 */     BufferedReader in = new BufferedReader(new InputStreamReader(resourceAsStream, "UTF-8"));
/*     */     
/*     */     try {
/* 294 */       FinalArrayList<Class<?>> classes = new FinalArrayList();
/* 295 */       String className = in.readLine();
/* 296 */       while (className != null) {
/* 297 */         className = className.trim();
/* 298 */         if (className.startsWith("#") || className.length() == 0) {
/* 299 */           className = in.readLine();
/*     */           
/*     */           continue;
/*     */         } 
/* 303 */         if (className.endsWith(".class")) {
/* 304 */           throw new JAXBException(Messages.ILLEGAL_ENTRY.format(new Object[] { className }));
/*     */         }
/*     */         
/*     */         try {
/* 308 */           classes.add(classLoader.loadClass(pkg + '.' + className));
/* 309 */         } catch (ClassNotFoundException e) {
/* 310 */           throw new JAXBException(Messages.ERROR_LOADING_CLASS.format(new Object[] { className, resource }, ), e);
/*     */         } 
/*     */         
/* 313 */         className = in.readLine();
/*     */       } 
/* 315 */       return (List)classes;
/*     */     } finally {
/* 317 */       in.close();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/bind/v2/ContextFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */