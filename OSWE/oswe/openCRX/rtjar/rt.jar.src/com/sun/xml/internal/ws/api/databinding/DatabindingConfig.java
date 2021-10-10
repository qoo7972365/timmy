/*     */ package com.sun.xml.internal.ws.api.databinding;
/*     */ 
/*     */ import com.sun.xml.internal.ws.api.WSBinding;
/*     */ import com.sun.xml.internal.ws.api.model.wsdl.WSDLPort;
/*     */ import com.sun.xml.internal.ws.binding.WebServiceFeatureList;
/*     */ import java.net.URL;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import javax.xml.transform.Source;
/*     */ import javax.xml.ws.WebServiceFeature;
/*     */ import org.xml.sax.EntityResolver;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DatabindingConfig
/*     */ {
/*     */   protected Class contractClass;
/*     */   protected Class endpointClass;
/*  54 */   protected Set<Class> additionalValueTypes = (Set)new HashSet<>();
/*  55 */   protected MappingInfo mappingInfo = new MappingInfo();
/*     */   protected URL wsdlURL;
/*     */   protected ClassLoader classLoader;
/*     */   protected Iterable<WebServiceFeature> features;
/*     */   protected WSBinding wsBinding;
/*     */   protected WSDLPort wsdlPort;
/*     */   protected MetadataReader metadataReader;
/*  62 */   protected Map<String, Object> properties = new HashMap<>();
/*     */   protected Source wsdlSource;
/*     */   protected EntityResolver entityResolver;
/*     */   
/*     */   public Class getContractClass() {
/*  67 */     return this.contractClass;
/*     */   }
/*     */   public void setContractClass(Class contractClass) {
/*  70 */     this.contractClass = contractClass;
/*     */   }
/*     */   public Class getEndpointClass() {
/*  73 */     return this.endpointClass;
/*     */   }
/*     */   public void setEndpointClass(Class implBeanClass) {
/*  76 */     this.endpointClass = implBeanClass;
/*     */   }
/*     */   public MappingInfo getMappingInfo() {
/*  79 */     return this.mappingInfo;
/*     */   }
/*     */   public void setMappingInfo(MappingInfo mappingInfo) {
/*  82 */     this.mappingInfo = mappingInfo;
/*     */   }
/*     */   public URL getWsdlURL() {
/*  85 */     return this.wsdlURL;
/*     */   }
/*     */   public void setWsdlURL(URL wsdlURL) {
/*  88 */     this.wsdlURL = wsdlURL;
/*     */   }
/*     */   public ClassLoader getClassLoader() {
/*  91 */     return this.classLoader;
/*     */   }
/*     */   public void setClassLoader(ClassLoader classLoader) {
/*  94 */     this.classLoader = classLoader;
/*     */   }
/*     */   public Iterable<WebServiceFeature> getFeatures() {
/*  97 */     if (this.features == null && this.wsBinding != null) return (Iterable<WebServiceFeature>)this.wsBinding.getFeatures(); 
/*  98 */     return this.features;
/*     */   }
/*     */   public void setFeatures(WebServiceFeature[] features) {
/* 101 */     setFeatures((Iterable<WebServiceFeature>)new WebServiceFeatureList(features));
/*     */   }
/*     */   public void setFeatures(Iterable<WebServiceFeature> features) {
/* 104 */     this.features = (Iterable<WebServiceFeature>)WebServiceFeatureList.toList(features);
/*     */   }
/*     */   public WSDLPort getWsdlPort() {
/* 107 */     return this.wsdlPort;
/*     */   }
/*     */   public void setWsdlPort(WSDLPort wsdlPort) {
/* 110 */     this.wsdlPort = wsdlPort;
/*     */   }
/*     */   public Set<Class> additionalValueTypes() {
/* 113 */     return this.additionalValueTypes;
/*     */   }
/*     */   public Map<String, Object> properties() {
/* 116 */     return this.properties;
/*     */   }
/*     */   public WSBinding getWSBinding() {
/* 119 */     return this.wsBinding;
/*     */   }
/*     */   public void setWSBinding(WSBinding wsBinding) {
/* 122 */     this.wsBinding = wsBinding;
/*     */   }
/*     */   public MetadataReader getMetadataReader() {
/* 125 */     return this.metadataReader;
/*     */   }
/*     */   public void setMetadataReader(MetadataReader reader) {
/* 128 */     this.metadataReader = reader;
/*     */   }
/*     */   
/*     */   public Source getWsdlSource() {
/* 132 */     return this.wsdlSource;
/*     */   }
/*     */   public void setWsdlSource(Source wsdlSource) {
/* 135 */     this.wsdlSource = wsdlSource;
/*     */   }
/*     */   public EntityResolver getEntityResolver() {
/* 138 */     return this.entityResolver;
/*     */   }
/*     */   public void setEntityResolver(EntityResolver entityResolver) {
/* 141 */     this.entityResolver = entityResolver;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/api/databinding/DatabindingConfig.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */