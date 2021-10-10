/*     */ package com.sun.xml.internal.ws.model;
/*     */ 
/*     */ import com.oracle.xmlns.internal.webservices.jaxws_databinding.ExistingAnnotationsType;
/*     */ import com.oracle.xmlns.internal.webservices.jaxws_databinding.JavaMethod;
/*     */ import com.oracle.xmlns.internal.webservices.jaxws_databinding.JavaParam;
/*     */ import com.oracle.xmlns.internal.webservices.jaxws_databinding.JavaWsdlMappingType;
/*     */ import com.oracle.xmlns.internal.webservices.jaxws_databinding.ObjectFactory;
/*     */ import com.sun.xml.internal.ws.streaming.XMLStreamReaderUtil;
/*     */ import com.sun.xml.internal.ws.util.xml.XmlUtil;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.lang.annotation.Annotation;
/*     */ import java.lang.reflect.Method;
/*     */ import java.net.URL;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import javax.xml.bind.JAXBContext;
/*     */ import javax.xml.bind.JAXBElement;
/*     */ import javax.xml.bind.JAXBException;
/*     */ import javax.xml.bind.Unmarshaller;
/*     */ import javax.xml.bind.util.JAXBResult;
/*     */ import javax.xml.stream.XMLInputFactory;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import javax.xml.stream.XMLStreamReader;
/*     */ import javax.xml.transform.Result;
/*     */ import javax.xml.transform.Source;
/*     */ import javax.xml.transform.Transformer;
/*     */ import javax.xml.transform.TransformerException;
/*     */ import javax.xml.transform.TransformerFactory;
/*     */ import javax.xml.transform.stream.StreamSource;
/*     */ import javax.xml.validation.Schema;
/*     */ import javax.xml.validation.SchemaFactory;
/*     */ import org.w3c.dom.Element;
/*     */ import org.xml.sax.SAXException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ExternalMetadataReader
/*     */   extends ReflectAnnotationReader
/*     */ {
/*     */   private static final String NAMESPACE_WEBLOGIC_WSEE_DATABINDING = "http://xmlns.oracle.com/weblogic/weblogic-wsee-databinding";
/*     */   private static final String NAMESPACE_JAXWS_RI_EXTERNAL_METADATA = "http://xmlns.oracle.com/webservices/jaxws-databinding";
/*  75 */   private Map<String, JavaWsdlMappingType> readers = new HashMap<>();
/*     */ 
/*     */ 
/*     */   
/*     */   public ExternalMetadataReader(Collection<File> files, Collection<String> resourcePaths, ClassLoader classLoader, boolean xsdValidation, boolean disableXmlSecurity) {
/*  80 */     if (files != null) {
/*  81 */       for (File file : files) {
/*     */         try {
/*  83 */           String namespace = Util.documentRootNamespace(newSource(file), disableXmlSecurity);
/*  84 */           JavaWsdlMappingType externalMapping = parseMetadata(xsdValidation, newSource(file), namespace, disableXmlSecurity);
/*  85 */           this.readers.put(externalMapping.getJavaTypeName(), externalMapping);
/*  86 */         } catch (Exception e) {
/*  87 */           throw new RuntimeModelerException("runtime.modeler.external.metadata.unable.to.read", new Object[] { file.getAbsolutePath() });
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/*  92 */     if (resourcePaths != null) {
/*  93 */       for (String resourcePath : resourcePaths) {
/*     */         try {
/*  95 */           String namespace = Util.documentRootNamespace(newSource(resourcePath, classLoader), disableXmlSecurity);
/*  96 */           JavaWsdlMappingType externalMapping = parseMetadata(xsdValidation, newSource(resourcePath, classLoader), namespace, disableXmlSecurity);
/*  97 */           this.readers.put(externalMapping.getJavaTypeName(), externalMapping);
/*  98 */         } catch (Exception e) {
/*  99 */           throw new RuntimeModelerException("runtime.modeler.external.metadata.unable.to.read", new Object[] { resourcePath });
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   private StreamSource newSource(String resourcePath, ClassLoader classLoader) {
/* 106 */     InputStream is = classLoader.getResourceAsStream(resourcePath);
/* 107 */     return new StreamSource(is);
/*     */   }
/*     */   
/*     */   private JavaWsdlMappingType parseMetadata(boolean xsdValidation, StreamSource source, String namespace, boolean disableXmlSecurity) throws JAXBException, IOException, TransformerException {
/* 111 */     if ("http://xmlns.oracle.com/weblogic/weblogic-wsee-databinding".equals(namespace))
/* 112 */       return Util.transformAndRead(source, disableXmlSecurity); 
/* 113 */     if ("http://xmlns.oracle.com/webservices/jaxws-databinding".equals(namespace)) {
/* 114 */       return Util.read(source, xsdValidation, disableXmlSecurity);
/*     */     }
/* 116 */     throw new RuntimeModelerException("runtime.modeler.external.metadata.unsupported.schema", new Object[] { namespace, Arrays.<String>asList(new String[] { "http://xmlns.oracle.com/weblogic/weblogic-wsee-databinding", "http://xmlns.oracle.com/webservices/jaxws-databinding" }).toString() });
/*     */   }
/*     */ 
/*     */   
/*     */   private StreamSource newSource(File file) {
/*     */     try {
/* 122 */       return new StreamSource(new FileInputStream(file));
/* 123 */     } catch (FileNotFoundException e) {
/* 124 */       throw new RuntimeModelerException("runtime.modeler.external.metadata.unable.to.read", new Object[] { file.getAbsolutePath() });
/*     */     } 
/*     */   }
/*     */   
/*     */   public <A extends Annotation> A getAnnotation(Class<A> annType, Class<?> cls) {
/* 129 */     JavaWsdlMappingType r = reader(cls);
/* 130 */     return (r == null) ? super.<A>getAnnotation(annType, cls) : (A)Util.<Annotation>annotation(r, annType);
/*     */   }
/*     */   
/*     */   private JavaWsdlMappingType reader(Class<?> cls) {
/* 134 */     return this.readers.get(cls.getName());
/*     */   }
/*     */   
/*     */   Annotation[] getAnnotations(List<Object> objects) {
/* 138 */     ArrayList<Annotation> list = new ArrayList<>();
/* 139 */     for (Object a : objects) {
/* 140 */       if (Annotation.class.isInstance(a)) {
/* 141 */         list.add(Annotation.class.cast(a));
/*     */       }
/*     */     } 
/* 144 */     return list.<Annotation>toArray(new Annotation[list.size()]);
/*     */   }
/*     */ 
/*     */   
/*     */   public Annotation[] getAnnotations(final Class<?> c) {
/* 149 */     Merger<Annotation[]> merger = new Merger<Annotation[]>(reader(c)) {
/*     */         Annotation[] reflection() {
/* 151 */           return ExternalMetadataReader.this.getAnnotations(c);
/*     */         }
/*     */         
/*     */         Annotation[] external() {
/* 155 */           return ExternalMetadataReader.this.getAnnotations(this.reader.getClassAnnotation());
/*     */         }
/*     */       };
/* 158 */     return merger.merge();
/*     */   }
/*     */   
/*     */   public Annotation[] getAnnotations(final Method m) {
/* 162 */     Merger<Annotation[]> merger = new Merger<Annotation[]>(reader(m.getDeclaringClass())) {
/*     */         Annotation[] reflection() {
/* 164 */           return ExternalMetadataReader.this.getAnnotations(m);
/*     */         }
/*     */         
/*     */         Annotation[] external() {
/* 168 */           JavaMethod jm = ExternalMetadataReader.this.getJavaMethod(m, this.reader);
/* 169 */           return (jm == null) ? new Annotation[0] : ExternalMetadataReader.this.getAnnotations(jm.getMethodAnnotation());
/*     */         }
/*     */       };
/* 172 */     return merger.merge();
/*     */   }
/*     */ 
/*     */   
/*     */   public <A extends Annotation> A getAnnotation(final Class<A> annType, final Method m) {
/* 177 */     Merger<Annotation> merger = new Merger<Annotation>(reader(m.getDeclaringClass())) {
/*     */         Annotation reflection() {
/* 179 */           return (Annotation)ExternalMetadataReader.this.getAnnotation(annType, m);
/*     */         }
/*     */         
/*     */         Annotation external() {
/* 183 */           JavaMethod jm = ExternalMetadataReader.this.getJavaMethod(m, this.reader);
/* 184 */           return ExternalMetadataReader.Util.<Annotation>annotation(jm, annType);
/*     */         }
/*     */       };
/* 187 */     return (A)merger.merge();
/*     */   }
/*     */   
/*     */   public Annotation[][] getParameterAnnotations(final Method m) {
/* 191 */     Merger<Annotation[][]> merger = new Merger<Annotation[][]>(reader(m.getDeclaringClass())) {
/*     */         Annotation[][] reflection() {
/* 193 */           return ExternalMetadataReader.this.getParameterAnnotations(m);
/*     */         }
/*     */         
/*     */         Annotation[][] external() {
/* 197 */           JavaMethod jm = ExternalMetadataReader.this.getJavaMethod(m, this.reader);
/* 198 */           Annotation[][] a = m.getParameterAnnotations();
/* 199 */           for (int i = 0; i < (m.getParameterTypes()).length; i++) {
/* 200 */             if (jm != null) {
/* 201 */               JavaParam jp = jm.getJavaParams().getJavaParam().get(i);
/* 202 */               a[i] = ExternalMetadataReader.this.getAnnotations(jp.getParamAnnotation());
/*     */             } 
/* 204 */           }  return a;
/*     */         }
/*     */       };
/* 207 */     return merger.merge();
/*     */   }
/*     */ 
/*     */   
/*     */   public void getProperties(Map<String, Object> prop, Class<?> cls) {
/* 212 */     JavaWsdlMappingType r = reader(cls);
/*     */ 
/*     */     
/* 215 */     if (r == null || ExistingAnnotationsType.MERGE.equals(r.getExistingAnnotations())) {
/* 216 */       super.getProperties(prop, cls);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void getProperties(Map<String, Object> prop, Method m) {
/* 223 */     JavaWsdlMappingType r = reader(m.getDeclaringClass());
/*     */ 
/*     */     
/* 226 */     if (r == null || ExistingAnnotationsType.MERGE.equals(r.getExistingAnnotations())) {
/* 227 */       super.getProperties(prop, m);
/*     */     }
/*     */     
/* 230 */     if (r != null) {
/* 231 */       JavaMethod jm = getJavaMethod(m, r);
/* 232 */       Element[] e = Util.annotation(jm);
/* 233 */       prop.put("eclipselink-oxm-xml.xml-element", findXmlElement(e));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void getProperties(Map<String, Object> prop, Method m, int pos) {
/* 240 */     JavaWsdlMappingType r = reader(m.getDeclaringClass());
/*     */ 
/*     */     
/* 243 */     if (r == null || ExistingAnnotationsType.MERGE.equals(r.getExistingAnnotations())) {
/* 244 */       super.getProperties(prop, m, pos);
/*     */     }
/*     */     
/* 247 */     if (r != null) {
/* 248 */       JavaMethod jm = getJavaMethod(m, r);
/* 249 */       if (jm == null)
/* 250 */         return;  JavaParam jp = jm.getJavaParams().getJavaParam().get(pos);
/* 251 */       Element[] e = Util.annotation(jp);
/* 252 */       prop.put("eclipselink-oxm-xml.xml-element", findXmlElement(e));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   JavaMethod getJavaMethod(Method method, JavaWsdlMappingType r) {
/* 258 */     JavaWsdlMappingType.JavaMethods javaMethods = r.getJavaMethods();
/* 259 */     if (javaMethods == null) {
/* 260 */       return null;
/*     */     }
/*     */     
/* 263 */     List<JavaMethod> sameName = new ArrayList<>();
/* 264 */     for (JavaMethod jm : javaMethods.getJavaMethod()) {
/* 265 */       if (method.getName().equals(jm.getName())) {
/* 266 */         sameName.add(jm);
/*     */       }
/*     */     } 
/*     */     
/* 270 */     if (sameName.isEmpty()) {
/* 271 */       return null;
/*     */     }
/* 273 */     if (sameName.size() == 1) {
/* 274 */       return sameName.get(0);
/*     */     }
/* 276 */     Class<?>[] argCls = method.getParameterTypes();
/* 277 */     for (JavaMethod jm : sameName) {
/* 278 */       JavaMethod.JavaParams params = jm.getJavaParams();
/* 279 */       if (params != null && params.getJavaParam() != null && params.getJavaParam().size() == argCls.length) {
/* 280 */         int count = 0;
/* 281 */         for (int i = 0; i < argCls.length; i++) {
/* 282 */           JavaParam jp = params.getJavaParam().get(i);
/* 283 */           if (argCls[i].getName().equals(jp.getJavaType())) {
/* 284 */             count++;
/*     */           }
/*     */         } 
/* 287 */         if (count == argCls.length) {
/* 288 */           return jm;
/*     */         }
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 294 */     return null;
/*     */   }
/*     */   
/*     */   Element findXmlElement(Element[] xa) {
/* 298 */     if (xa == null) return null; 
/* 299 */     for (Element e : xa) {
/* 300 */       if (e.getLocalName().equals("java-type")) return e; 
/* 301 */       if (e.getLocalName().equals("xml-element")) return e; 
/*     */     } 
/* 303 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static abstract class Merger<T>
/*     */   {
/*     */     JavaWsdlMappingType reader;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     Merger(JavaWsdlMappingType r) {
/* 322 */       this.reader = r;
/*     */     }
/*     */ 
/*     */     
/*     */     abstract T reflection();
/*     */     
/*     */     abstract T external();
/*     */     
/*     */     T merge() {
/* 331 */       T reflection = reflection();
/* 332 */       if (this.reader == null) {
/* 333 */         return reflection;
/*     */       }
/*     */       
/* 336 */       T external = external();
/* 337 */       if (!ExistingAnnotationsType.MERGE.equals(this.reader.getExistingAnnotations())) {
/* 338 */         return external;
/*     */       }
/*     */       
/* 341 */       if (reflection instanceof Annotation)
/* 342 */         return (T)doMerge((Annotation)reflection, (Annotation)external); 
/* 343 */       if (reflection instanceof Annotation[][]) {
/* 344 */         return (T)doMerge((Annotation[][])reflection, (Annotation[][])external);
/*     */       }
/* 346 */       return (T)doMerge((Annotation[])reflection, (Annotation[])external);
/*     */     }
/*     */ 
/*     */     
/*     */     private Annotation doMerge(Annotation reflection, Annotation external) {
/* 351 */       return (external != null) ? external : reflection;
/*     */     }
/*     */     
/*     */     private Annotation[][] doMerge(Annotation[][] reflection, Annotation[][] external) {
/* 355 */       for (int i = 0; i < reflection.length; i++) {
/* 356 */         reflection[i] = doMerge(reflection[i], (external.length > i) ? external[i] : null);
/*     */       }
/* 358 */       return reflection;
/*     */     }
/*     */     
/*     */     private Annotation[] doMerge(Annotation[] annotations, Annotation[] externalAnnotations) {
/* 362 */       HashMap<String, Annotation> mergeMap = new HashMap<>();
/* 363 */       if (annotations != null) {
/* 364 */         for (Annotation reflectionAnnotation : annotations) {
/* 365 */           mergeMap.put(reflectionAnnotation.annotationType().getName(), reflectionAnnotation);
/*     */         }
/*     */       }
/*     */ 
/*     */       
/* 370 */       if (externalAnnotations != null) {
/* 371 */         for (Annotation externalAnnotation : externalAnnotations) {
/* 372 */           mergeMap.put(externalAnnotation.annotationType().getName(), externalAnnotation);
/*     */         }
/*     */       }
/* 375 */       Collection<Annotation> values = mergeMap.values();
/* 376 */       int size = values.size();
/* 377 */       return (size == 0) ? null : values.<Annotation>toArray(new Annotation[size]);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static class Util
/*     */   {
/*     */     private static final String DATABINDING_XSD = "jaxws-databinding.xsd";
/*     */     
/*     */     private static final String TRANSLATE_NAMESPACES_XSL = "jaxws-databinding-translate-namespaces.xml";
/*     */     
/*     */     static Schema schema;
/*     */ 
/*     */     
/*     */     static {
/* 393 */       SchemaFactory sf = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
/*     */       try {
/* 395 */         URL xsdUrl = getResource();
/* 396 */         if (xsdUrl != null) {
/* 397 */           schema = sf.newSchema(xsdUrl);
/*     */         }
/* 399 */       } catch (SAXException sAXException) {}
/*     */     }
/*     */ 
/*     */     
/* 403 */     static JAXBContext jaxbContext = createJaxbContext(false);
/*     */ 
/*     */     
/*     */     private static URL getResource() {
/* 407 */       ClassLoader classLoader = Util.class.getClassLoader();
/* 408 */       return (classLoader != null) ? classLoader.getResource("jaxws-databinding.xsd") : ClassLoader.getSystemResource("jaxws-databinding.xsd");
/*     */     }
/*     */     
/*     */     private static JAXBContext createJaxbContext(boolean disableXmlSecurity) {
/* 412 */       Class[] cls = { ObjectFactory.class };
/*     */       try {
/* 414 */         if (disableXmlSecurity) {
/* 415 */           Map<String, Object> properties = new HashMap<>();
/* 416 */           properties.put("com.sun.xml.internal.bind.disableXmlSecurity", Boolean.valueOf(disableXmlSecurity));
/* 417 */           return JAXBContext.newInstance(cls, properties);
/*     */         } 
/* 419 */         return JAXBContext.newInstance(cls);
/*     */       }
/* 421 */       catch (JAXBException e) {
/* 422 */         e.printStackTrace();
/* 423 */         return null;
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public static JavaWsdlMappingType read(Source src, boolean xsdValidation, boolean disableXmlSecurity) throws IOException, JAXBException {
/* 429 */       JAXBContext ctx = jaxbContext(disableXmlSecurity);
/*     */       try {
/* 431 */         Unmarshaller um = ctx.createUnmarshaller();
/* 432 */         if (xsdValidation) {
/* 433 */           if (schema == null);
/*     */ 
/*     */           
/* 436 */           um.setSchema(schema);
/*     */         } 
/* 438 */         Object o = um.unmarshal(src);
/* 439 */         return getJavaWsdlMapping(o);
/* 440 */       } catch (JAXBException e) {
/*     */ 
/*     */ 
/*     */         
/* 444 */         URL url = new URL(src.getSystemId());
/* 445 */         Source s = new StreamSource(url.openStream());
/* 446 */         Unmarshaller um = ctx.createUnmarshaller();
/* 447 */         if (xsdValidation) {
/* 448 */           if (schema == null);
/*     */ 
/*     */           
/* 451 */           um.setSchema(schema);
/*     */         } 
/* 453 */         Object o = um.unmarshal(s);
/* 454 */         return getJavaWsdlMapping(o);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     private static JAXBContext jaxbContext(boolean disableXmlSecurity) {
/* 461 */       return disableXmlSecurity ? createJaxbContext(true) : jaxbContext;
/*     */     }
/*     */     
/*     */     public static JavaWsdlMappingType transformAndRead(Source src, boolean disableXmlSecurity) throws TransformerException, JAXBException {
/* 465 */       Source xsl = new StreamSource(Util.class.getResourceAsStream("jaxws-databinding-translate-namespaces.xml"));
/* 466 */       JAXBResult result = new JAXBResult(jaxbContext(disableXmlSecurity));
/* 467 */       TransformerFactory tf = XmlUtil.newTransformerFactory(!disableXmlSecurity);
/* 468 */       Transformer transformer = tf.newTemplates(xsl).newTransformer();
/* 469 */       transformer.transform(src, (Result)result);
/* 470 */       return getJavaWsdlMapping(result.getResult());
/*     */     }
/*     */ 
/*     */     
/*     */     static JavaWsdlMappingType getJavaWsdlMapping(Object o) {
/* 475 */       Object val = (o instanceof JAXBElement) ? ((JAXBElement)o).getValue() : o;
/* 476 */       if (val instanceof JavaWsdlMappingType) return (JavaWsdlMappingType)val;
/*     */ 
/*     */ 
/*     */       
/* 480 */       return null;
/*     */     }
/*     */     
/*     */     static <T> T findInstanceOf(Class<T> type, List<Object> objects) {
/* 484 */       for (Object o : objects) {
/* 485 */         if (type.isInstance(o)) {
/* 486 */           return type.cast(o);
/*     */         }
/*     */       } 
/* 489 */       return null;
/*     */     }
/*     */     
/*     */     public static <T> T annotation(JavaWsdlMappingType jwse, Class<T> anntype) {
/* 493 */       if (jwse == null || jwse.getClassAnnotation() == null) {
/* 494 */         return null;
/*     */       }
/* 496 */       return findInstanceOf(anntype, jwse.getClassAnnotation());
/*     */     }
/*     */     
/*     */     public static <T> T annotation(JavaMethod jm, Class<T> anntype) {
/* 500 */       if (jm == null || jm.getMethodAnnotation() == null) {
/* 501 */         return null;
/*     */       }
/* 503 */       return findInstanceOf(anntype, jm.getMethodAnnotation());
/*     */     }
/*     */     
/*     */     public static <T> T annotation(JavaParam jp, Class<T> anntype) {
/* 507 */       if (jp == null || jp.getParamAnnotation() == null) {
/* 508 */         return null;
/*     */       }
/* 510 */       return findInstanceOf(anntype, jp.getParamAnnotation());
/*     */     }
/*     */     
/*     */     public static Element[] annotation(JavaMethod jm) {
/* 514 */       if (jm == null || jm.getMethodAnnotation() == null) {
/* 515 */         return null;
/*     */       }
/* 517 */       return findElements(jm.getMethodAnnotation());
/*     */     }
/*     */     
/*     */     public static Element[] annotation(JavaParam jp) {
/* 521 */       if (jp == null || jp.getParamAnnotation() == null) {
/* 522 */         return null;
/*     */       }
/* 524 */       return findElements(jp.getParamAnnotation());
/*     */     }
/*     */     
/*     */     private static Element[] findElements(List<Object> objects) {
/* 528 */       List<Element> elems = new ArrayList<>();
/* 529 */       for (Object o : objects) {
/* 530 */         if (o instanceof Element) {
/* 531 */           elems.add((Element)o);
/*     */         }
/*     */       } 
/* 534 */       return elems.<Element>toArray(new Element[elems.size()]);
/*     */     }
/*     */ 
/*     */     
/*     */     static String documentRootNamespace(Source src, boolean disableXmlSecurity) throws XMLStreamException {
/* 539 */       XMLInputFactory factory = XmlUtil.newXMLInputFactory(!disableXmlSecurity);
/* 540 */       XMLStreamReader streamReader = factory.createXMLStreamReader(src);
/* 541 */       XMLStreamReaderUtil.nextElementContent(streamReader);
/* 542 */       String namespaceURI = streamReader.getName().getNamespaceURI();
/* 543 */       XMLStreamReaderUtil.close(streamReader);
/* 544 */       return namespaceURI;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/model/ExternalMetadataReader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */