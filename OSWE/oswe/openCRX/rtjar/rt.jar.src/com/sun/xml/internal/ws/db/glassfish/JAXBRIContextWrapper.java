/*     */ package com.sun.xml.internal.ws.db.glassfish;
/*     */ 
/*     */ import com.sun.xml.internal.bind.api.Bridge;
/*     */ import com.sun.xml.internal.bind.api.JAXBRIContext;
/*     */ import com.sun.xml.internal.bind.api.TypeReference;
/*     */ import com.sun.xml.internal.bind.v2.model.runtime.RuntimeTypeInfoSet;
/*     */ import com.sun.xml.internal.bind.v2.runtime.JAXBContextImpl;
/*     */ import com.sun.xml.internal.ws.spi.db.BindingContext;
/*     */ import com.sun.xml.internal.ws.spi.db.PropertyAccessor;
/*     */ import com.sun.xml.internal.ws.spi.db.TypeInfo;
/*     */ import com.sun.xml.internal.ws.spi.db.WrapperComposite;
/*     */ import com.sun.xml.internal.ws.spi.db.XMLBridge;
/*     */ import java.io.IOException;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import javax.xml.bind.JAXBContext;
/*     */ import javax.xml.bind.JAXBException;
/*     */ import javax.xml.bind.Marshaller;
/*     */ import javax.xml.bind.SchemaOutputResolver;
/*     */ import javax.xml.bind.Unmarshaller;
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
/*     */ class JAXBRIContextWrapper
/*     */   implements BindingContext
/*     */ {
/*     */   private Map<TypeInfo, TypeReference> typeRefs;
/*     */   private Map<TypeReference, TypeInfo> typeInfos;
/*     */   private JAXBRIContext context;
/*     */   
/*     */   JAXBRIContextWrapper(JAXBRIContext cxt, Map<TypeInfo, TypeReference> refs) {
/*  53 */     this.context = cxt;
/*  54 */     this.typeRefs = refs;
/*  55 */     if (refs != null) {
/*  56 */       this.typeInfos = new HashMap<>();
/*  57 */       for (TypeInfo ti : refs.keySet()) {
/*  58 */         this.typeInfos.put(this.typeRefs.get(ti), ti);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   TypeReference typeReference(TypeInfo ti) {
/*  64 */     return (this.typeRefs != null) ? this.typeRefs.get(ti) : null;
/*     */   }
/*     */   
/*     */   TypeInfo typeInfo(TypeReference tr) {
/*  68 */     return (this.typeInfos != null) ? this.typeInfos.get(tr) : null;
/*     */   }
/*     */ 
/*     */   
/*     */   public Marshaller createMarshaller() throws JAXBException {
/*  73 */     return this.context.createMarshaller();
/*     */   }
/*     */ 
/*     */   
/*     */   public Unmarshaller createUnmarshaller() throws JAXBException {
/*  78 */     return this.context.createUnmarshaller();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void generateSchema(SchemaOutputResolver outputResolver) throws IOException {
/*  84 */     this.context.generateSchema(outputResolver);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getBuildId() {
/*  89 */     return this.context.getBuildId();
/*     */   }
/*     */ 
/*     */   
/*     */   public QName getElementName(Class o) throws JAXBException {
/*  94 */     return this.context.getElementName(o);
/*     */   }
/*     */ 
/*     */   
/*     */   public QName getElementName(Object o) throws JAXBException {
/*  99 */     return this.context.getElementName(o);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <B, V> PropertyAccessor<B, V> getElementPropertyAccessor(Class<B> wrapperBean, String nsUri, String localName) throws JAXBException {
/* 106 */     return new RawAccessorWrapper(this.context.getElementPropertyAccessor(wrapperBean, nsUri, localName));
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String> getKnownNamespaceURIs() {
/* 111 */     return this.context.getKnownNamespaceURIs();
/*     */   }
/*     */   
/*     */   public RuntimeTypeInfoSet getRuntimeTypeInfoSet() {
/* 115 */     return this.context.getRuntimeTypeInfoSet();
/*     */   }
/*     */   
/*     */   public QName getTypeName(TypeReference tr) {
/* 119 */     return this.context.getTypeName(tr);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 124 */     return this.context.hashCode();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/* 129 */     if (obj == null) {
/* 130 */       return false;
/*     */     }
/* 132 */     if (getClass() != obj.getClass()) {
/* 133 */       return false;
/*     */     }
/* 135 */     JAXBRIContextWrapper other = (JAXBRIContextWrapper)obj;
/* 136 */     if (this.context != other.context && (this.context == null || !this.context.equals(other.context))) {
/* 137 */       return false;
/*     */     }
/* 139 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasSwaRef() {
/* 144 */     return this.context.hasSwaRef();
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 149 */     return JAXBRIContextWrapper.class.getName() + " : " + this.context.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   public XMLBridge createBridge(TypeInfo ti) {
/* 154 */     TypeReference tr = this.typeRefs.get(ti);
/* 155 */     Bridge<?> b = this.context.createBridge(tr);
/* 156 */     return WrapperComposite.class.equals(ti.type) ? new WrapperBridge(this, b) : new BridgeWrapper(this, b);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JAXBContext getJAXBContext() {
/* 163 */     return (JAXBContext)this.context;
/*     */   }
/*     */ 
/*     */   
/*     */   public QName getTypeName(TypeInfo ti) {
/* 168 */     TypeReference tr = this.typeRefs.get(ti);
/* 169 */     return this.context.getTypeName(tr);
/*     */   }
/*     */ 
/*     */   
/*     */   public XMLBridge createFragmentBridge() {
/* 174 */     return new MarshallerBridge((JAXBContextImpl)this.context);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Object newWrapperInstace(Class<?> wrapperType) throws InstantiationException, IllegalAccessException {
/* 180 */     return wrapperType.newInstance();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/db/glassfish/JAXBRIContextWrapper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */