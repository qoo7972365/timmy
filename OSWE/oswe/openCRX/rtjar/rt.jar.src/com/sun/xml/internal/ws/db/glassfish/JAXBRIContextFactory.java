/*     */ package com.sun.xml.internal.ws.db.glassfish;
/*     */ 
/*     */ import com.sun.xml.internal.bind.api.CompositeStructure;
/*     */ import com.sun.xml.internal.bind.api.JAXBRIContext;
/*     */ import com.sun.xml.internal.bind.api.TypeReference;
/*     */ import com.sun.xml.internal.bind.v2.ContextFactory;
/*     */ import com.sun.xml.internal.bind.v2.model.annotation.RuntimeAnnotationReader;
/*     */ import com.sun.xml.internal.bind.v2.runtime.MarshallerImpl;
/*     */ import com.sun.xml.internal.ws.developer.JAXBContextFactory;
/*     */ import com.sun.xml.internal.ws.spi.db.BindingContext;
/*     */ import com.sun.xml.internal.ws.spi.db.BindingContextFactory;
/*     */ import com.sun.xml.internal.ws.spi.db.BindingInfo;
/*     */ import com.sun.xml.internal.ws.spi.db.DatabindingException;
/*     */ import com.sun.xml.internal.ws.spi.db.TypeInfo;
/*     */ import com.sun.xml.internal.ws.spi.db.WrapperComposite;
/*     */ import java.lang.reflect.Type;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import javax.xml.bind.JAXBContext;
/*     */ import javax.xml.bind.Marshaller;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class JAXBRIContextFactory
/*     */   extends BindingContextFactory
/*     */ {
/*     */   public BindingContext newContext(JAXBContext context) {
/*  60 */     return new JAXBRIContextWrapper((JAXBRIContext)context, null);
/*     */   }
/*     */ 
/*     */   
/*     */   public BindingContext newContext(BindingInfo bi) {
/*  65 */     Class[] classes = (Class[])bi.contentClasses().toArray((Object[])new Class[bi.contentClasses().size()]);
/*  66 */     for (int i = 0; i < classes.length; i++) {
/*  67 */       if (WrapperComposite.class.equals(classes[i])) {
/*  68 */         classes[i] = CompositeStructure.class;
/*     */       }
/*     */     } 
/*  71 */     Map<TypeInfo, TypeReference> typeInfoMappings = typeInfoMappings(bi.typeInfos());
/*  72 */     Map<Class<?>, Class<?>> subclassReplacements = bi.subclassReplacements();
/*  73 */     String defaultNamespaceRemap = bi.getDefaultNamespace();
/*  74 */     Boolean c14nSupport = (Boolean)bi.properties().get("c14nSupport");
/*  75 */     RuntimeAnnotationReader ar = (RuntimeAnnotationReader)bi.properties().get("com.sun.xml.internal.bind.v2.model.annotation.RuntimeAnnotationReader");
/*  76 */     JAXBContextFactory jaxbContextFactory = (JAXBContextFactory)bi.properties().get(JAXBContextFactory.class.getName());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/*  83 */       JAXBRIContext context = (jaxbContextFactory != null) ? jaxbContextFactory.createJAXBContext(bi.getSEIModel(), toList((Class<?>[][])classes), toList(typeInfoMappings.values())) : ContextFactory.createContext(classes, typeInfoMappings
/*  84 */           .values(), subclassReplacements, defaultNamespaceRemap, (c14nSupport != null) ? c14nSupport
/*     */           
/*  86 */           .booleanValue() : false, ar, false, false, false);
/*     */       
/*  88 */       return new JAXBRIContextWrapper(context, typeInfoMappings);
/*  89 */     } catch (Exception e) {
/*  90 */       throw new DatabindingException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   private <T> List<T> toList(T[] a) {
/*  95 */     List<T> l = new ArrayList<>();
/*  96 */     l.addAll(Arrays.asList(a));
/*  97 */     return l;
/*     */   }
/*     */   
/*     */   private <T> List<T> toList(Collection<T> col) {
/* 101 */     if (col instanceof List) {
/* 102 */       return (List<T>)col;
/*     */     }
/* 104 */     List<T> l = new ArrayList<>();
/* 105 */     l.addAll(col);
/* 106 */     return l;
/*     */   }
/*     */   
/*     */   private Map<TypeInfo, TypeReference> typeInfoMappings(Collection<TypeInfo> typeInfos) {
/* 110 */     Map<TypeInfo, TypeReference> map = new HashMap<>();
/* 111 */     for (TypeInfo ti : typeInfos) {
/* 112 */       Type type = WrapperComposite.class.equals(ti.type) ? CompositeStructure.class : ti.type;
/* 113 */       TypeReference tr = new TypeReference(ti.tagName, type, ti.annotations);
/* 114 */       map.put(ti, tr);
/*     */     } 
/* 116 */     return map;
/*     */   }
/*     */ 
/*     */   
/*     */   protected BindingContext getContext(Marshaller m) {
/* 121 */     return newContext((JAXBContext)((MarshallerImpl)m).getContext());
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean isFor(String str) {
/* 126 */     return (str.equals("glassfish.jaxb") || str
/* 127 */       .equals(getClass().getName()) || str
/* 128 */       .equals("com.sun.xml.internal.bind.v2.runtime"));
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/db/glassfish/JAXBRIContextFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */